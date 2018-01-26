package com.daishumovie.api.controller.v1;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.daishumovie.api.service.SearchService;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.search.SearchDto;
import com.daishumovie.base.dto.search.SearchListDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.model.Response;
import com.daishumovie.search.enums.IndexType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("v1.SearchController")
@RequestMapping("/v1/search")
@Slf4j
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/keyword/{type}/{keyword}/{pageIndex}")
	public Response<SearchListDto<SearchDto>> searchByKeywordInPath(@PathVariable("pageIndex") Integer pageIndex,
			@PathVariable("type") String type, @PathVariable("keyword") String keyword,
			@RequestHeader(value = "appId") Integer appId) throws APIConnectionException, APIRequestException {
		return searchByKeyword(pageIndex, type, keyword, appId);
	}

	@RequestMapping("/keyword/{type}/{pageIndex}")
	public Response<SearchListDto<SearchDto>> searchByKeyword(@PathVariable("pageIndex") Integer pageIndex,
			@PathVariable("type") String type, @RequestParam("keyword") String keyword,
			@RequestHeader(value = "appId") Integer appId) throws APIConnectionException, APIRequestException {
		Response<SearchListDto<SearchDto>> resp = new Response<>();
		IndexType indexType = IndexType.get(type);
		if (indexType == null) {
			return new Response<>(RespStatusEnum.PARAM_FAIL);
		}
		switch (indexType) {
		case topic:
			resp = searchService.searchTopicByKeyword(appId, keyword, new PageInDto(pageIndex, 10));
			break;

		case album:
			resp = searchService.searchAlbumByKeyword(appId, keyword, new PageInDto(pageIndex, 10));
			break;
		case user:
			resp = searchService.searchUserByKeyword(appId, keyword, new PageInDto(pageIndex, 10));
			break;
		default:
			break;
		}
		return resp;
	}

	@RequestMapping(value = "/suggest")
	public Response<BaseListDto<String>> suggest(@RequestParam("keyword") String keyword,
			@RequestHeader(value = "appId") Integer appId) throws APIConnectionException, APIRequestException {
		Response<BaseListDto<String>> resp = new Response<>();
		BaseListDto<String> dto = new BaseListDto<>();
		List<String> wordList = new ArrayList<>();
		if ("邓先森".startsWith(keyword)) {
			wordList.add("邓先森");
		}
		log.info("suggest->keyword:" + keyword);
		dto.setList(wordList);
		resp.setResult(dto);
		return resp;
	}

    /**
     * 获取热搜词
     * @return
     */
    @RequestMapping(value = "/getSearchHotWord", method = RequestMethod.GET)
    public Response getSearchHotWord() {
        List hotWord = searchService.getHotWord();
		Map map = new HashMap();
		map.put("list", hotWord);
		return new Response(map);
    }
}
