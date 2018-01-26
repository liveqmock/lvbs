package com.daishumovie.api.service;

import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.search.SearchDto;
import com.daishumovie.base.dto.search.SearchListDto;
import com.daishumovie.base.enums.db.UserPraiseTargetType;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.BaseUtil;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.search.enums.IndexType;
import com.daishumovie.search.pojo.AlbumVo;
import com.daishumovie.search.pojo.TopicVo;
import com.daishumovie.search.pojo.UserBo;
import com.daishumovie.search.service.impl.SearchListService;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.FastJsonUtils;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    @Autowired
    private SearchListService searchListService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private DsmUserMapper dsmUserMapper;

	@Autowired
	private SbTopicAlbumMapper albumMapper;

	@Autowired
	private SbTopicMapper topicMapper;

	@Autowired
	private DsmUserMapper userMapper;
	@Autowired
	private SbVideoMapper videoMapper;

	@Value("#{'${audit_status}'.split(',')}")
	private List<Integer> auditStatusList;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Integer SIZE = 6;

	private static final String KEY = "search_hot_word";

	public Response<SearchListDto<SearchDto>> searchUserByKeyword(Integer appId, String keywords, PageInDto pageInfo) {
		Response<SearchListDto<SearchDto>> resp = new Response<>();
		try {
			SearchListDto<SearchDto> dto = new SearchListDto<>();
			resp.setResult(dto);
			List<SearchDto> searchDtoList = new ArrayList<>();
			dto.setList(searchDtoList);
			dto.setPageIndex(pageInfo.getPageIndex());
			SearchResult searchUserResult = searchListService.searchByKeywords(appId, pageInfo.getOffset(),
					pageInfo.getLimit(), IndexType.user, keywords, "nickName");
			List<SearchResult.Hit<UserBo, Void>> hits = searchUserResult.getHits(UserBo.class);
			dto.setUserCount(BaseUtil.instance.formatNum(searchUserResult.getTotal(), true));
			if (pageInfo.getPageIndex() == 1) {
				SearchResult searchAlbumResult = searchListService.searchByKeywords(appId, 0, 0, IndexType.album,
						keywords, "title");
				SearchResult searchTopicResult = searchListService.searchByKeywords(appId, 0, 0, IndexType.topic,
						keywords, "title");
				dto.setAlbumCount(BaseUtil.instance.formatNum(searchAlbumResult.getTotal(), true));
				dto.setTopicCount(BaseUtil.instance.formatNum(searchTopicResult.getTotal(), true));
			}
			List<Integer> uidList = new ArrayList<>();
			Map<Integer, SearchResult.Hit<UserBo, Void>> hitsMap = new HashMap<>();
			if (hits != null && hits.size() > 0) {
				for (SearchResult.Hit<UserBo, Void> userBoVoidHit : hits) {
					UserBo source = userBoVoidHit.source;
					uidList.add(source.getUid());
					hitsMap.put(source.getUid(), userBoVoidHit);
				}
			}
			if (CollectionUtils.isNullOrEmpty(uidList)) {
				return resp;
			}
			DsmUserExample userExample = new DsmUserExample();
			DsmUserExample.Criteria userCriteria = userExample.createCriteria();
			userCriteria.andUidIn(uidList);
			userCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
			List<DsmUser> dsmUserList = dsmUserMapper.selectByExample(userExample);
			if (CollectionUtils.isNullOrEmpty(dsmUserList)) {
				return resp;
			}
			DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
			Integer localUid = user == null ? null : user.getUid();
			Map<Integer, DsmUser> userMap = CollectionUtils.convert2Map(dsmUserList, "getUid");
			for (Integer uid : uidList) {
				DsmUser userInfo = userMap.get(uid);
				if (null == userInfo) {
					continue;
				}
				SearchDto searchUserDto = new SearchDto();
				searchUserDto.setId(uid);
				searchUserDto.setIntroduce(userInfo.getIntroduce());
				if (StringUtils.isBlank(searchUserDto.getIntroduce())) {
					searchUserDto.setIntroduce("这个人很懒，什么也不想写~");
				}
				searchUserDto.setImage(userInfo.getAvatar());
				searchUserDto.setUserName(userInfo.getNickName());
				searchUserDto.setCount(userInfo.getPublishCount());
				// 粉丝数格式化;
				searchUserDto.setFansNum(BaseUtil.instance.formatNum(userInfo.getFansNum(), true));
				searchUserDto.setUserType(userInfo.getType());
				SearchResult.Hit<UserBo, Void> userBoVoidHit = hitsMap.get(uid);
				searchUserDto.setUserName(userBoVoidHit.highlight.get("nickName").get(0));
				searchUserDto.setHasFollow(
						praiseService.hasFollow(localUid, uid, UserPraiseTargetType.USER.getValue()) ? 1 : 0);

				searchDtoList.add(searchUserDto);
			}
			if (!CollectionUtils.isNullOrEmpty(searchDtoList)) {
				setHotWord(keywords);
			}
			dto.setList(searchDtoList, pageInfo);
			resp.setResult(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public Response<SearchListDto<SearchDto>> searchAlbumByKeyword(Integer appId, String keywords, PageInDto pageInfo) {
		Response<SearchListDto<SearchDto>> resp = new Response<>();
		try {
			SearchListDto<SearchDto> dto = new SearchListDto<>();
			resp.setResult(dto);
			List<SearchDto> searchDtoList = new ArrayList<>();
			dto.setList(searchDtoList);
			dto.setPageIndex(pageInfo.getPageIndex());
			SearchResult searchAlbumResult = searchListService.searchByKeywords(appId, pageInfo.getOffset(),
					pageInfo.getLimit(), IndexType.album, keywords, "title");
			List<SearchResult.Hit<AlbumVo, Void>> hits = searchAlbumResult.getHits(AlbumVo.class);
			dto.setAlbumCount(BaseUtil.instance.formatNum(searchAlbumResult.getTotal(), true));
			if (pageInfo.getPageIndex() == 1) {
				SearchResult searchUserResult = searchListService.searchByKeywords(appId, 0, 0, IndexType.user,
						keywords, "nickName");
				SearchResult searchTopicResult = searchListService.searchByKeywords(appId, 0, 0, IndexType.topic,
						keywords, "title");
				dto.setUserCount(BaseUtil.instance.formatNum(searchUserResult.getTotal(), true));
				dto.setTopicCount(BaseUtil.instance.formatNum(searchTopicResult.getTotal(), true));
			}
			List<Integer> albumIdList = new ArrayList<>();
			Map<Integer, SearchResult.Hit<AlbumVo, Void>> hitsMap = new HashMap<>();
			if (hits != null && hits.size() > 0) {
				for (SearchResult.Hit<AlbumVo, Void> albumVoVoidHit : hits) {
					AlbumVo source = albumVoVoidHit.source;
					albumIdList.add(source.getId());
					hitsMap.put(source.getId(), albumVoVoidHit);
				}
			}
			if (CollectionUtils.isNullOrEmpty(albumIdList)) {
				return resp;
			}
			SbTopicAlbumExample albumExample = new SbTopicAlbumExample();
			SbTopicAlbumExample.Criteria AlbumCriteria = albumExample.createCriteria();
			if (appId != null) {
				AlbumCriteria.andAppIdEqualTo(appId);
			}
			AlbumCriteria.andIdIn(albumIdList);
			AlbumCriteria.andStatusEqualTo(Whether.yes.getValue());
			List<SbTopicAlbum> albumList = albumMapper.selectByExample(albumExample);
			// 处理收藏的排序
			Map<Integer, SbTopicAlbum> albumMap = CollectionUtils.convert2MapGeneric(albumList, "getId");
			if (CollectionUtils.isNullOrEmpty(albumList)) {
				return resp;
			}
			DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
			Integer localUid = user == null ? null : user.getUid();
			for (Integer albumId : albumIdList) {
				SbTopicAlbum album = albumMap.get(albumId);
				if (null == album) {
					continue;
				}
				SearchDto searchAlbumDto = new SearchDto();
				searchAlbumDto.setId(albumId);
				searchAlbumDto.setImage(album.getCover());
				searchAlbumDto.setCount(album.getTopicIds().split(",").length);
				SearchResult.Hit<AlbumVo, Void> albumBoVoidHit = hitsMap.get(albumId);
				searchAlbumDto.setTitle(albumBoVoidHit.highlight.get("title").get(0));
				searchAlbumDto.setHasFollow(
						praiseService.hasFollow(localUid, albumId, UserPraiseTargetType.ALBUM.getValue()) ? 1 : 0);
				searchAlbumDto.setTime(BaseUtil.instance.formatTime(album.getPublishTime()));
				searchDtoList.add(searchAlbumDto);
			}
			if (!CollectionUtils.isNullOrEmpty(searchDtoList)) {
				setHotWord(keywords);
			}
			dto.setList(searchDtoList, pageInfo);
			resp.setResult(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public Response<SearchListDto<SearchDto>> searchTopicByKeyword(Integer appId, String keywords, PageInDto pageInfo) {
		Response<SearchListDto<SearchDto>> resp = new Response<>();
		try {
			SearchListDto<SearchDto> dto = new SearchListDto<>();
			resp.setResult(dto);
			List<SearchDto> searchDtoList = new ArrayList<>();
			dto.setList(searchDtoList);
			dto.setPageIndex(pageInfo.getPageIndex());
			SearchResult searchTopicResult = searchListService.searchByKeywords(appId, pageInfo.getOffset(),
					pageInfo.getLimit(), IndexType.topic, keywords, "title");
			List<SearchResult.Hit<TopicVo, Void>> hits = searchTopicResult.getHits(TopicVo.class);
			dto.setTopicCount(BaseUtil.instance.formatNum(searchTopicResult.getTotal(), true));
			if (pageInfo.getPageIndex() == 1) {
				SearchResult searchAlbumResult = searchListService.searchByKeywords(appId, 0, 0, IndexType.album,
						keywords, "title");
				SearchResult searchUserResult = searchListService.searchByKeywords(appId, 0, 0, IndexType.user,
						keywords, "nickName");
				dto.setAlbumCount(BaseUtil.instance.formatNum(searchAlbumResult.getTotal(), true));
				dto.setUserCount(BaseUtil.instance.formatNum(searchUserResult.getTotal(), true));
			}
			List<Integer> topicIdList = new ArrayList<>();
			Map<Integer, SearchResult.Hit<TopicVo, Void>> hitsMap = new HashMap<>();
			if (hits != null && hits.size() > 0) {
				for (SearchResult.Hit<TopicVo, Void> topicVoVoidHit : hits) {
					TopicVo source = topicVoVoidHit.source;
					topicIdList.add(source.getId());
					hitsMap.put(source.getId(), topicVoVoidHit);
				}
			}
			if (CollectionUtils.isNullOrEmpty(topicIdList)) {
				return resp;
			}
			SbTopicExample sbTopicExample = new SbTopicExample();
			SbTopicExample.Criteria sbTopicExampleCriteria = sbTopicExample.createCriteria();
			sbTopicExampleCriteria.andIdIn(topicIdList);
			sbTopicExampleCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
			sbTopicExampleCriteria.andAuditStatusIn(auditStatusList);
			sbTopicExampleCriteria.andAppIdEqualTo(appId);
			List<SbTopic> sbTopics = topicMapper.selectByExample(sbTopicExample);
			if (CollectionUtils.isNullOrEmpty(sbTopics)) {
				return resp;
			}
			Map<Integer, SbTopic> topicMap = CollectionUtils.convert2Map(sbTopics, "getId");
			for (Integer topicId : topicIdList) {
				SbTopic topicInfo = topicMap.get(topicId);
				if (null == topicInfo) {
					continue;
				}
				SearchDto searchTopicDto = new SearchDto();
				searchTopicDto.setId(topicId);
				DsmUser owner = userMapper.selectByPrimaryKey(topicInfo.getUid());
				SbVideo video = videoMapper.selectByPrimaryKey(topicInfo.getVideoId());
				searchTopicDto.setDuration(video.getDuration());
				searchTopicDto.setImage(video.getCover());
				searchTopicDto.setUserName(owner.getNickName());
				searchTopicDto.setPlayNum(BaseUtil.instance.formatNum(video.getPlayNum() + video.getvPlayNum(), true));
				SearchResult.Hit<TopicVo, Void> topicVoVoidHit = hitsMap.get(topicId);
				searchTopicDto.setTitle(topicVoVoidHit.highlight.get("title").get(0));
				searchDtoList.add(searchTopicDto);
			}
			if (!CollectionUtils.isNullOrEmpty(searchDtoList)) {
				setHotWord(keywords);
			}
			dto.setList(searchDtoList, pageInfo);
			resp.setResult(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

    public void setHotWord(String word) {
        String hotWord = redisTemplate.opsForValue().get(KEY);
        LinkedList<String> list = null;
        if (StringUtils.isNotBlank(hotWord)) {
            String s = StringUtils.deleteWhitespace(StringUtils.replaceEachRepeatedly(hotWord, new String[]{"[", "]"}, new String[]{"", ""}));
            list = new LinkedList<>(Arrays.asList(s.split(",")));
            if (list.contains(word)) {
                list.remove(word);
                list.addFirst(word);
            } else {
                if (list.size() >= SIZE) {
                    list.removeLast();
                    list.addFirst(word);
                } else {
                    list.addFirst(word);
                }
            }
        } else {
            list = new LinkedList<>();
            list.add(word);
        }
        redisTemplate.opsForValue().set(KEY, list.toString());
    }

    public List<String> getHotWord() {
        String hotWord = redisTemplate.opsForValue().get(KEY);
        LinkedList<String> list = null;
        if (StringUtils.isNotBlank(hotWord)) {
            String s = StringUtils.deleteWhitespace(StringUtils.replaceEachRepeatedly(hotWord, new String[]{"[", "]"}, new String[]{"", ""}));
            list = new LinkedList<>(Arrays.asList(s.split(",")));
        } else {
            list = new LinkedList<>();
        }
        return list;
    }
}
