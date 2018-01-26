package com.daishumovie.api.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daishumovie.api.service.ActivityService;
import com.daishumovie.api.service.AlbumService;
import com.daishumovie.api.service.HistoryService;
import com.daishumovie.api.service.TopicListService;
import com.daishumovie.base.dto.share.ShareActivityDto;
import com.daishumovie.base.dto.share.ShareAlbumDto;
import com.daishumovie.base.dto.share.ShareDetailDto;
import com.daishumovie.base.dto.share.ShareInfo;
import com.daishumovie.base.enums.db.ActivityType;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.enums.front.SharePlat;
import com.daishumovie.base.enums.front.ShareTargetType;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicAlbum;
import com.daishumovie.dao.model.SbVideo;

import lombok.extern.slf4j.Slf4j;

@RestController("v1.ShareController")
@RequestMapping("/v1/share")
@Slf4j
public class ShareController {

	@Autowired
	private HistoryService historyService;
	@Autowired
	private TopicListService topicService;
	@Autowired
	private DsmUserMapper userMapper;
	@Autowired
	private SbActivityMapper activityMapper;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private ActivityService activityService;
	@Value("${app.sharePrefix}")
	private String sharePrefix;
	@Value("${spring.profiles.active}")
	private String profile;

	/**
	 * 获取分享信息
	 * 
	 * @param episodeId
	 * @return
	 */
	@RequestMapping("/info/{sharePlate}/{targetType}/{targetId:\\d+}")
	public Response<ShareInfo> shareInfo(@RequestHeader(value = "appId") Integer appId, @PathVariable Integer targetId,
			@PathVariable Integer sharePlate, @PathVariable Integer targetType) {
		if (targetId == null) {
			return new Response<>(RespStatusEnum.PARAM_FAIL);
		}
		ShareTargetType shareTargetType = ShareTargetType.get(targetType);
		if (shareTargetType == null) {
			return new Response<>(RespStatusEnum.PARAM_FAIL);
		}
		ShareInfo info = new ShareInfo();
		SharePlat plat = SharePlat.get(sharePlate);
		switch (shareTargetType) {
		case TOPIC:
			SbTopic topic = topicService.getTopicBean(targetId);
			SbVideo video = topicService.getVideoBean(topic.getVideoId());
			Header header = LocalData.HEADER.get();
			String did = header.getDid();
			Integer uid = header.getUid();
			historyService.shareRecord(appId, targetId, uid, did, sharePlate);
			DsmUser user = userMapper.selectByPrimaryKey(topic.getUid());
			// 未参加活动的视频;
			info.setImage(video.getCover() + "?x-oss-process=image/resize,m_fill,h_100,w_100");
			if (topic.getActivityId() == null) {
				info.setTitle(topic.getTitle());
				info.setDesc("【小铜人】请你围观" + user.getNickName() + "的视频");
				info.setUrl(sharePrefix + "/copperShare/1/" + targetId);
				switch (plat) {
				case SHARE_CHANNEL_WECHAT_TIMELINE:
					info.setDesc(topic.getTitle());
					break;
				case SHARE_CHANNEL_WECHAT_FRIEND:
					break;
				case SHARE_CHANNEL_QQ_FRIEND:
					break;
				case SHARE_CHANNEL_QQ_QZONE:
					info.setDesc(topic.getTitle());
					break;
				case SHARE_CHANNEL_SINA_WEIBO:
					info.setDesc("给这条视频跪了【" + topic.getTitle() + "】(分享自#小铜人#) 【小铜人】请你围观" + user.getNickName()
							+ "的视频。" + info.getUrl());
					info.setTitle("");
					info.setImage(video.getCover() + "?x-oss-process=image/resize,m_fill,h_400,w_400");
					break;
				default:
					break;
				}
			} else {
				SbActivity activity = activityMapper.selectByPrimaryKey(topic.getActivityId());
				info.setDesc("请你围观" + user.getNickName() + "的视频" + "，#" + activity.getTopic() + "#");
				info.setTitle(topic.getTitle());
				// TODO change url;
				info.setUrl(sharePrefix + "/activityVideo/1/" + targetId);
				switch (plat) {
				case SHARE_CHANNEL_WECHAT_TIMELINE:
					info.setDesc("#" + activity.getTopic() + "#" + topic.getTitle());
					break;
				case SHARE_CHANNEL_WECHAT_FRIEND:
					break;
				case SHARE_CHANNEL_QQ_FRIEND:
					break;
				case SHARE_CHANNEL_QQ_QZONE:
					info.setDesc("#" + activity.getTopic() + "#" + topic.getTitle());
					break;
				case SHARE_CHANNEL_SINA_WEIBO:
					info.setDesc("#" + activity.getTopic() + "#【" + topic.getTitle() + "】(分享自#小铜人#) 【小铜人】请你围观"
							+ user.getNickName() + "的视频。" + info.getUrl());
					info.setTitle("");
					info.setImage(video.getCover() + "?x-oss-process=image/resize,m_fill,h_400,w_400");
					break;
				default:
					break;
				}
			}
			break;
		case ACTIVITY:
			SbActivity activity = activityMapper.selectByPrimaryKey(targetId);
			if (activity == null) {
				return new Response<>(RespStatusEnum.TARGET_NOT_EXIST);
			}
			ActivityType activityType = ActivityType.get(activity.getType());
			switch (activityType) {
			case contribute:
				info.setDesc(activity.getDescription());
				info.setUrl(sharePrefix + "/activity/1/" + targetId);
				info.setImage(activity.getThumbCover() + "?x-oss-process=image/resize,m_fill,h_100,w_100");
				switch (plat) {
				case SHARE_CHANNEL_WECHAT_FRIEND:
				case SHARE_CHANNEL_QQ_FRIEND:
					info.setTitle("快来围观【小铜人】" + activity.getTitle());
					break;
				case SHARE_CHANNEL_WECHAT_TIMELINE:
				case SHARE_CHANNEL_QQ_QZONE:
					info.setDesc("快来围观【小铜人】" + activity.getTitle() + ",更多有趣短视频请戳这里");
					break;
				case SHARE_CHANNEL_SINA_WEIBO:
					info.setDesc("快来围观【小铜人】#" + activity.getTopic() + "#" + activity.getDescription()
							+ "(分享自#小铜人#) 网页链接" + info.getUrl());
					info.setTitle("");
					info.setImage(activity.getThumbCover() + "?x-oss-process=image/resize,m_fill,h_400,w_400");
					break;
				default:
					break;
				}
				break;
			case awards:
				info.setDesc("iPhoneX10台/数万电影票/视频VIP会员~等你拿走~~~");
				info.setUrl("dev".equals(profile) ? "http://m.daishumovie.cn/activityLuck/shareIndex.html?from=share"
						: "http://m.daishumovie.com/activityLuck/shareIndex.html?from=share");
				info.setImage(activity.getThumbCover() + "?x-oss-process=image/resize,m_fill,h_100,w_100");
				switch (plat) {
				case SHARE_CHANNEL_WECHAT_FRIEND:
				case SHARE_CHANNEL_QQ_FRIEND:
					info.setTitle("小铜人免费送iphoneX 啦~");
					break;
				case SHARE_CHANNEL_WECHAT_TIMELINE:
				case SHARE_CHANNEL_QQ_QZONE:
					info.setDesc("小铜人喊你来领奖啦~iPhone X10台/数万电影票/视频VIP会员~等你拿！");
					break;
				case SHARE_CHANNEL_SINA_WEIBO:
					info.setDesc("下载小铜人APP，赢取抽奖机会，十台iPhoneX以及数万电影票/视频VIP会员等你来抽抽抽~（分享自#小铜人#）" + info.getUrl());
					info.setTitle("");
					info.setImage("http://small-bronze.oss-cn-shanghai.aliyuncs.com/image/activity/thumb/2017/11/10/QQ%e5%9b%be%e7%89%8720171111170344.png");
					break;
				default:
					break;
				}
			}
			break;
		case ALBUM:
			SbTopicAlbum album = albumService.getPojo(targetId);
			if (album == null) {
				return new Response<>(RespStatusEnum.TARGET_NOT_EXIST);
			}
			info.setDesc(album.getSubtitle());
			// TODO change url;
			info.setUrl(sharePrefix + "/activityAlbum/1/" + targetId);
			info.setImage(album.getCover() + "?x-oss-process=image/resize,m_fill,h_100,w_100");
			switch (plat) {
			case SHARE_CHANNEL_WECHAT_FRIEND:
			case SHARE_CHANNEL_QQ_FRIEND:
				info.setTitle("发现一个新梗：【" + album.getTitle() + "】");
				break;
			case SHARE_CHANNEL_WECHAT_TIMELINE:
			case SHARE_CHANNEL_QQ_QZONE:
				info.setDesc("发现一个新梗：【" + album.getTitle() + "】@小铜人");
				break;
			case SHARE_CHANNEL_SINA_WEIBO:
				info.setDesc(
						"发现一个新梗：【" + album.getTitle() + "】" + album.getSubtitle() + "(分享自#小铜人#) " + info.getUrl());
				info.setTitle("");
				info.setImage(album.getCover() + "?x-oss-process=image/resize,m_fill,h_400,w_400");
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return new Response<>(info);
	}

	/**
	 * 
	 * @param topicId
	 * @return
	 */
	@RequestMapping("/detail/{appId}/{topicId:\\d+}")
	public Response<ShareDetailDto> getShareDetail(@PathVariable Integer topicId, @PathVariable Integer appId) {
		return topicService.getShareDetail(topicId, appId, PageSource.H5_TOPIC_DETAIL);
	}

	/**
	 * 
	 * @param topicId
	 * @return
	 */
	@RequestMapping("/activityTopic/{appId}/{topicId:\\d+}")
	public Response<ShareDetailDto> getActivityTopicDetail(@PathVariable Integer topicId, @PathVariable Integer appId) {
		return topicService.getShareDetail(topicId, appId, PageSource.H5_ACTIVITY_TOPIC_DETAIL);
	}

	/**
	 * 
	 * @param albumId
	 * @return
	 */
	@RequestMapping("/album/{appId}/{albumId:\\d+}")
	public Response<ShareAlbumDto> getAlbumDetail(@PathVariable Integer albumId, @PathVariable Integer appId) {
		return albumService.getShareDetail(appId, albumId);
	}

	/**
	 *
	 * @param activityId
	 * @return
	 */
	@RequestMapping("/activity/{appId}/{activityId:\\d+}")
	public Response<ShareActivityDto> getActivityDetail(@PathVariable Integer activityId, @PathVariable Integer appId) {

		return activityService.getShareDetail(activityId, appId);

	}
}
