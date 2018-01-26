package com.daishumovie.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.album.AlbumDto;
import com.daishumovie.base.dto.album.IdTitleImage;
import com.daishumovie.base.dto.share.ShareAlbumDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.UserPraiseTargetType;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.base.enums.front.BaseUtil;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicAlbum;
import com.daishumovie.dao.model.SbTopicAlbumExample;
import com.daishumovie.dao.model.SbTopicExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.FastJsonUtils;

@Service
public class AlbumService {

	@Autowired
	private SbTopicAlbumMapper albumMapper;

	@Autowired
	private SbTopicMapper topicMapper;

	@Autowired
	private SbVideoMapper videoMapper;

	@Autowired
	private PraiseService praiseService;

	@Value("#{'${audit_status}'.split(',')}")
	private List<Integer> auditStatusList;

	@Autowired
	private TopicListService topicListService;

	/**
	 * 合辑列表
	 *
	 * @param appId
	 * @param pageInfo
	 * @return
	 */
	public Response<BaseListDto<AlbumDto<IdTitleImage>>> getAlbumList(Integer appId, PageInDto pageInfo) {
		SbTopicAlbumExample albumExample = new SbTopicAlbumExample();
		albumExample.setOffset(pageInfo.getOffset());
		albumExample.setLimit(pageInfo.getLimit());
		albumExample.setOrderByClause("publish_time desc");
		SbTopicAlbumExample.Criteria albumCriteria = albumExample.createCriteria();
		albumCriteria.andAppIdEqualTo(appId);
		albumCriteria.andStatusEqualTo(Whether.yes.getValue());
		List<SbTopicAlbum> albumList = albumMapper.selectByExample(albumExample);
		return getAlbumList(appId, pageInfo, albumList);
	}

	public Response<BaseListDto<AlbumDto<IdTitleImage>>> getAlbumList(Integer appId, PageInDto pageInfo,
																	  List<SbTopicAlbum> albumList) {
		Response<BaseListDto<AlbumDto<IdTitleImage>>> resp = new Response<>();
		if (albumList == null || albumList.size() == 0) {
			BaseListDto<AlbumDto<IdTitleImage>> dto = new BaseListDto<>(new ArrayList<>(), pageInfo);
			resp.setResult(dto);
			return resp;
		}
		List<AlbumDto<IdTitleImage>> albumDtoList = new ArrayList<>();
		List<Integer> topicIdList = new ArrayList<>();
		for (SbTopicAlbum album : albumList) {
			String topicIds = album.getTopicIds();
			String[] topicIdArr = StringUtils.splitByWholeSeparator(topicIds, ",");
			for (String id : topicIdArr) {
				topicIdList.add(Integer.valueOf(id));
			}
		}
		SbTopicExample topicExample = new SbTopicExample();
		SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
		topicCriteria.andAppIdEqualTo(appId);
		topicCriteria.andIdIn(topicIdList);
		topicCriteria.andStatusEqualTo(Whether.yes.getValue());
		topicCriteria.andAuditStatusIn(auditStatusList);
		List<SbTopic> topicList = topicMapper.selectByExample(topicExample);
		try {
			Map<Integer, SbTopic> topicMap = CollectionUtils.convert2MapGeneric(topicList, "getId");
			for (SbTopicAlbum album : albumList) {
				AlbumDto<IdTitleImage> albumDto = new AlbumDto<>();
				BeanUtils.copyProperties(album, albumDto);
				String topicIds = album.getTopicIds();
				String[] topicIdArr = StringUtils.splitByWholeSeparator(topicIds, ",");
				List<IdTitleImage> dtoList = new ArrayList<>();
				for (String id : topicIdArr) {
					Integer topicId = Integer.valueOf(id);
					SbTopic topic = topicMap.get(topicId);
					if (topic == null) {
						continue;
					}
					SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
					dtoList.add(new IdTitleImage(topicId, topic.getTitle(), video.getCover(), video.getDuration()));
				}
				albumDto.setCount(dtoList.size());
				albumDto.setList(dtoList);
				albumDto.setTime(formatTime(album.getPublishTime()));
				albumDto.setReplyNum(BaseUtil.instance.formatNum(album.getReplyNum(),true));
				// 返回合辑状态
				albumDto.setStatus(album.getStatus());
				albumDtoList.add(albumDto);
			}
			resp.setResult(new BaseListDto<>(albumDtoList, pageInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 合辑详情
	 *
	 * @param albumId
	 * @param appId
	 * @return
	 */
	public Response<AlbumDto<IdTitleImage>> getAlbumDetail(Integer albumId, Integer appId) {
		Response<AlbumDto<IdTitleImage>> resp = new Response<>();
		SbTopicAlbumExample albumExample = new SbTopicAlbumExample();
		albumExample.setLimit(1);
		SbTopicAlbumExample.Criteria albumCriteria = albumExample.createCriteria();
		albumCriteria.andIdEqualTo(albumId);
		albumCriteria.andAppIdEqualTo(appId);
		albumCriteria.andStatusEqualTo(Whether.yes.getValue());
		List<SbTopicAlbum> albumList = albumMapper.selectByExample(albumExample);
		if (albumList.size() == 0) {
			return new Response<>(RespStatusEnum.TARGET_NOT_EXIST);
		}
		SbTopicAlbum album = albumList.get(0);
		List<IdTitleImage> dtoList = new ArrayList<>();
		AlbumDto<IdTitleImage> albumDto = new AlbumDto<>();
		BeanUtils.copyProperties(album, albumDto);
		albumDto.setList(dtoList);
		albumDto.setReplyNum(BaseUtil.instance.formatNum(album.getReplyNum(),true));
		albumDto.setTime(formatTime(album.getPublishTime()));
		DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		Integer localUid = null;
		if (user != null) {
			localUid = user.getUid();
		}
		albumDto.setHasFollow(
				praiseService.hasFollow(localUid, albumId, UserPraiseTargetType.ALBUM.getValue()) ? 1 : 0);
		List<Integer> topicIdList = new ArrayList<>();
		String topicIds = album.getTopicIds();
		String[] topicIdArr = StringUtils.splitByWholeSeparator(topicIds, ",");
		for (String id : topicIdArr) {
			topicIdList.add(Integer.valueOf(id));
		}
		SbTopicExample topicExample = new SbTopicExample();
		SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
		topicCriteria.andAppIdEqualTo(appId);
		topicCriteria.andIdIn(topicIdList);
		topicCriteria.andStatusEqualTo(Whether.yes.getValue());
		topicCriteria.andAuditStatusIn(auditStatusList);
		List<SbTopic> topicList = topicMapper.selectByExample(topicExample);
		try {
			Map<Integer, SbTopic> topicMap = CollectionUtils.convert2MapGeneric(topicList, "getId");
			for (String id : topicIdArr) {
				Integer topicId = Integer.valueOf(id);
				SbTopic topic = topicMap.get(topicId);
				if (topic == null) {
					continue;
				}
				SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
				dtoList.add(new IdTitleImage(topicId, topic.getTitle(), video.getCover(), video.getDuration()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		albumDto.setCount(dtoList.size());
		resp.setResult(albumDto);
		return resp;
	}

	private String formatTime(Date date) {
		int diff = DateUtil.getPeriodOfDay(new Date(), date);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.setTime(date);
		if (diff == 0) {
			return "今日更新";
		} else if (diff == 1) {
			return "昨日更新";
		} else if(cal.get(Calendar.YEAR)==year) {
			return DateUtil.mmDDFormatter.format(date) + " 更新";
		} else {
			return DateUtil.yyMMddFormatter.format(date) + " 更新";
		}
	}

	public SbTopicAlbum getPojo(Integer albumId) {
		return albumMapper.selectByPrimaryKey(albumId);
	}

	public Response<ShareAlbumDto> getShareDetail(Integer appId, Integer albumId) {
		Response<ShareAlbumDto> resp = new Response<>();
		ShareAlbumDto dto = new ShareAlbumDto();
		Response<AlbumDto<IdTitleImage>> detailResp = getAlbumDetail(albumId, appId);
		dto.setAlbum(detailResp.getResult());
		if (dto.getAlbum() != null && dto.getAlbum().getList() != null) {
			dto.getAlbum().setCount(dto.getAlbum().getList().size());
			if (dto.getAlbum().getCount().intValue() > 6) {
				for (int i = dto.getAlbum().getCount() - 1; i >= 6; i--) {
					dto.getAlbum().getList().remove(i);
				}
				dto.setHasMoreTopic(Whether.yes.getValue());
			} else {
				dto.setHasMoreTopic(Whether.no.getValue());
			}
			dto.getAlbum().setHasFollow(null);
			dto.getAlbum().setReplyNum(null);
		}
		dto.setAdList(topicListService.getAdList(appId, null));
		Response<BaseListDto<AlbumDto<IdTitleImage>>> listResp = getAlbumList(appId, new PageInDto(1, 5));
		dto.setMoreAlbumList(listResp.getResult().getList());
		List<AlbumDto<IdTitleImage>> moreAlbumList = new ArrayList<>();
		int count = 0;
		for (AlbumDto<IdTitleImage> albumDto : dto.getMoreAlbumList()) {
			if (!albumDto.getId().equals(albumId)) {
				count++;
				if (moreAlbumList.size() < 3) {
					albumDto.setList(null);
					moreAlbumList.add(albumDto);
				}
			}
		}
		dto.setMoreAlbumList(moreAlbumList);
		dto.setHasMoreAlbum(count > 3 ? 1 : 0);
		resp.setResult(dto);
		return resp;
	}
}
