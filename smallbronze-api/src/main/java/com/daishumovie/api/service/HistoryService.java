package com.daishumovie.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserShareHistoryMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserWatchHistoryMapper;
import com.daishumovie.dao.mapper.smallbronze.UDUserWatchHistoryMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.DsmUserShareHistory;
import com.daishumovie.dao.model.DsmUserShareHistoryExample;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicExample;
import com.daishumovie.dao.model.SbUserWatchHistory;
import com.daishumovie.dao.model.SbUserWatchHistoryExample;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.FastJsonUtils;

@Service
public class HistoryService {

	@Autowired
	private DsmUserShareHistoryMapper shareHistoryMapper;

	@Autowired
	private SbUserWatchHistoryMapper sbUserWatchHistoryMapper;

	@Autowired
	private SbTopicMapper sbTopicMapper;

	@Value("#{'${audit_status}'.split(',')}")
	private List<Integer> auditStatusList;

	@Autowired
	private TopicListService topicListService;

	@Autowired
	private UDUserWatchHistoryMapper udUserWatchHistoryMapper;

	private List<Integer> allStatusList = Arrays.asList(0, 1, 3);

	public Response<String> shareRecord(Integer appId, Integer topicId, Integer uid, String did, Integer sharePlat) {
		DsmUserShareHistory history = findShare(appId, topicId, uid, did);
		if (history != null) {
			DsmUserShareHistory update = new DsmUserShareHistory();
			update.setId(history.getId());
			update.setShareTimes(history.getShareTimes() + 1);
			update.setStatus(YesNoEnum.YES.getCode());
			update.setSharePlat(sharePlat);
			shareHistoryMapper.updateByPrimaryKeySelective(update);
		} else {
			history = new DsmUserShareHistory();
			history.setDid(did);
			history.setTopicId(topicId);
			history.setUid(uid);
			history.setSharePlat(sharePlat);
			history.setAppId(appId);
			shareHistoryMapper.insertSelective(history);
		}
		return new Response<>();
	}

	private DsmUserShareHistory findShare(Integer appId, Integer topicId, Integer uid, String did) {

		DsmUserShareHistoryExample example = new DsmUserShareHistoryExample();
		example.setLimit(1);
		DsmUserShareHistoryExample.Criteria criteria = example.createCriteria();
		criteria.andAppIdEqualTo(appId);
		if (uid != null) {
			criteria.andUidEqualTo(uid);
		} else {
			criteria.andUidIsNull();
			criteria.andDidEqualTo(did);
		}
		criteria.andTopicIdEqualTo(topicId);
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		List<DsmUserShareHistory> historyList = shareHistoryMapper.selectByExample(example);
		return historyList.size() > 0 ? historyList.get(0) : null;
	}

	public void saveWatchHistory(Integer topicId,Integer videoId, Integer uid, String did) {

		SbUserWatchHistory userWatchHistory = findOne(topicId, videoId, uid, did);
		if (null == userWatchHistory) {
			SbUserWatchHistory sbUserWatchHistory = new SbUserWatchHistory();
			sbUserWatchHistory.setVideoId(videoId);
			sbUserWatchHistory.setTopicId(topicId);
			sbUserWatchHistory.setUid(uid);
			if (StringUtils.isNotBlank(did)) {
				sbUserWatchHistory.setDid(did);
			}
			sbUserWatchHistoryMapper.insertSelective(sbUserWatchHistory);
		}else{
			sbUserWatchHistoryMapper.selfPlusMinusByPrimaryKey("watch_times", "+", 1, userWatchHistory.getId());
		}
	}

	private SbUserWatchHistory findOne(Integer topicId,Integer videoId, Integer uid, String did) {

		SbUserWatchHistoryExample example = new SbUserWatchHistoryExample();
		example.setLimit(1);
		SbUserWatchHistoryExample.Criteria criteria = example.createCriteria();

		if (uid != null) {
			criteria.andUidEqualTo(uid);
		} else {
			criteria.andUidIsNull();
			criteria.andDidEqualTo(did);
		}
		criteria.andVideoIdEqualTo(videoId);
		criteria.andTopicIdEqualTo(topicId);
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		List<SbUserWatchHistory> historyList = sbUserWatchHistoryMapper.selectByExample(example);
		return historyList.size() > 0 ? historyList.get(0) : null;
	}

	public void relevanceData(Integer uid, String did){

		//同步
		SbUserWatchHistoryExample example = new SbUserWatchHistoryExample();
		SbUserWatchHistoryExample.Criteria criteria = example.createCriteria();
		criteria.andDidEqualTo(did);
		criteria.andUidIsNull();
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());

		long l = sbUserWatchHistoryMapper.countByExample(example);
		if(l==0) {
			return;
		}
		SbUserWatchHistory record = new SbUserWatchHistory();
		record.setUid(uid);
		sbUserWatchHistoryMapper.updateByExampleSelective(record, example);

		//去除重复数据
		udUserWatchHistoryMapper.removeDuplicateData(uid);


	}

	public List<TopicDto> getWatchHistoryList(PageInDto pageInDto, Integer uid,String did,String appId) throws Exception {

		List<TopicDto> topicDtoList = new ArrayList<>();
		SbUserWatchHistoryExample example = new SbUserWatchHistoryExample();
		example.setOffset(pageInDto.getOffset());
		example.setLimit(pageInDto.getLimit());
		example.setOrderByClause("modify_time desc");
		SbUserWatchHistoryExample.Criteria criteria = example.createCriteria();
		if (uid != null) {
			criteria.andUidEqualTo(uid);
		} else {
			criteria.andUidIsNull();
			criteria.andDidEqualTo(did);
		}
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		List<SbUserWatchHistory> userWatchHistories = sbUserWatchHistoryMapper.selectByExample(example);
		if (CollectionUtils.isNullOrEmpty(userWatchHistories)) {
			return topicDtoList;
		}
		List<Integer> topicIds = new ArrayList<>();
		for (SbUserWatchHistory history : userWatchHistories) {
			topicIds.add(history.getTopicId());
		}

		SbTopicExample topicExample = new SbTopicExample();
		SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
		if (StringUtils.isNotBlank(appId)) {
			topicCriteria.andAppIdEqualTo(Integer.valueOf(appId));
		}
		// 符合配置的审核过的话题才展示出来；
		topicCriteria.andAuditStatusIn(auditStatusList);
		topicCriteria.andIdIn(topicIds);
		topicCriteria.andStatusIn(allStatusList);
		List<SbTopic> sbTopicList = sbTopicMapper.selectByExample(topicExample);
		if (CollectionUtils.isNullOrEmpty(sbTopicList)) {
			return topicDtoList;
		}
		Map<Integer, SbTopic> videoMap = CollectionUtils.convert2Map(sbTopicList, "getId");
		Map<Integer, SbUserWatchHistory> historyMap = CollectionUtils.convert2Map(userWatchHistories, "getTopicId");
		for (Integer topicId : topicIds) {
			if(null == videoMap.get(topicId)){
				continue;
			}
			SbTopic topic = videoMap.get(topicId);
			TopicDto dto = topicListService.sbTopic2Dto(topic, PageSource.WATCH_HISTORY);
			dto.setHistoryId(historyMap.get(topicId).getId());
			topicDtoList.add(dto);
		}
		return topicDtoList;
	}

	public Response<String> del(Integer historyId) {
		SbUserWatchHistory sbUserWatchHistory = sbUserWatchHistoryMapper.selectByPrimaryKey(historyId);
		if (historyId == null) {
			return new Response<>(RespStatusEnum.TARGET_NOT_EXIST);
		}
		Header header = LocalData.HEADER.get();
		if (!header.getAppId().equals(sbUserWatchHistory.getAppId().toString())) {
			return new Response<>(RespStatusEnum.TARGET_NOT_EXIST);
		}

		DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		if ((sbUserWatchHistory.getUid() != null && !sbUserWatchHistory.getUid().equals(user.getUid()))
				&& !sbUserWatchHistory.getDid().equals(header.getDid())) {
			return new Response<>(RespStatusEnum.COMMENT_DEL_NOT_AUTH);
		}
		if (sbUserWatchHistory.getStatus().equals(YesNoEnum.NO.getCode())) {
			return new Response<>();
		}
		SbUserWatchHistory record = new SbUserWatchHistory();
		record.setId(historyId);
		record.setStatus(YesNoEnum.NO.getCode());
		sbUserWatchHistoryMapper.updateByPrimaryKeySelective(record);
		return new Response<>();
	}

	public Integer getHistoryCountByDid(String did) {
		SbUserWatchHistoryExample example = new SbUserWatchHistoryExample();
		SbUserWatchHistoryExample.Criteria criteria = example.createCriteria();

		criteria.andUidIsNull();
		criteria.andDidEqualTo(did);
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		return new Long(sbUserWatchHistoryMapper.countByExample(example)).intValue();
	}
}
