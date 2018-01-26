package com.daishumovie.search.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.rescore.RescoreBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.search.enums.IndexType;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchListService {

	@Autowired
	private JestClient jestClient;

	@Value("${spring.profiles.active}")
	private String profile;

	public SearchResult searchByKeywords(Integer appId, Integer offset, Integer limit, IndexType searchIndexType,
			String keywords, String field) throws IOException {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().disableCoord(true)
				.must(QueryBuilders.matchQuery(field, keywords)).must(QueryBuilders.termQuery("appId", appId));

		searchSourceBuilder.query(queryBuilder).size(limit).from(offset).highlight(
				new HighlightBuilder().field(field).preTags("<span style=\"color:#FF5A5A;\">").postTags("</span>"));

		String query = searchSourceBuilder.toString();

		Header header = LocalData.HEADER.get();
		String preference = header == null ? ""
				: (StringUtils.isNotBlank(header.getDid()) ? header.getDid()
						: header.getSessionId() == null ? "" : header.getSessionId());

		Search search = new Search.Builder(query).setParameter("preference", preference)// 防止结果值跳跃
				.addIndex("audit".equals(profile) ? searchIndexType.getIndex() + "_audit" // 审核环境使用_audit的索引
						: searchIndexType.getIndex())
				.addType(searchIndexType.getType()).build();

		SearchResult searchResult = jestClient.execute(search);
		log.info("uri={}, profile={}", search.getURI(), profile);
		log.info(query);
		log.info(searchResult.getJsonString());
		return searchResult;
	}

	private RescoreBuilder getRescoreBuilder(String dateField, String countField) {
		RescoreBuilder.QueryRescorer queryRescorer = RescoreBuilder
				.queryRescorer(QueryBuilders
						.functionScoreQuery(ScoreFunctionBuilders
								.gaussDecayFunction(dateField,
										DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "1d")
								.setDecay(0.5).setWeight(2.0f))
						.add(ScoreFunctionBuilders.scriptFunction("1.0-1.0/(1+0.001*doc['" + countField + "'].value)")
								.setWeight(0.6f))
						.scoreMode("sum"))
				.setQueryWeight(10.0f).setRescoreQueryWeight(1.0f);
		return new RescoreBuilder().windowSize(50).rescorer(queryRescorer);
	}
}
