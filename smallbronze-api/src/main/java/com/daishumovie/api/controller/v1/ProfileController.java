package com.daishumovie.api.controller.v1;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daishumovie.api.service.ActivityService;
import com.daishumovie.api.service.CommentListService;
import com.daishumovie.api.service.HistoryService;
import com.daishumovie.api.service.PraiseService;
import com.daishumovie.api.service.TopicListService;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.activity.ActivityDto;
import com.daishumovie.base.dto.album.AlbumDto;
import com.daishumovie.base.dto.album.IdTitleImage;
import com.daishumovie.base.dto.category.ChannelDto;
import com.daishumovie.base.dto.comment.CommentDto;
import com.daishumovie.base.dto.mine.DeviceInfo;
import com.daishumovie.base.dto.mine.MyInfoDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.UserCommentTargetType;
import com.daishumovie.base.enums.db.UserPraiseTargetType;
import com.daishumovie.base.enums.db.UserPraiseType;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.BaseUtil;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.mapper.smallbronze.SbChannelMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserDislikeMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserPraiseMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserWatchHistoryMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.dao.model.SbActivityExample;
import com.daishumovie.dao.model.SbChannel;
import com.daishumovie.dao.model.SbChannelExample;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicExample;
import com.daishumovie.dao.model.SbUserDislike;
import com.daishumovie.dao.model.SbUserPraise;
import com.daishumovie.dao.model.SbUserPraiseExample;
import com.daishumovie.dao.model.SbUserWatchHistoryExample;
import com.daishumovie.utils.FastJsonUtils;

/**
 * @author zhuruisong on 2017/9/9
 * @since 1.0
 */
@RestController("v1.MineController")
@RequestMapping("/v1/profile")
public class ProfileController {

	@Autowired
	private DsmUserMapper dsmUserMapper;
	@Autowired
	private SbUserPraiseMapper sbUserPraiseMapper;
	@Autowired
	private SbChannelMapper sbChannelMapper;
	@Autowired
	private SbUserDislikeMapper sbUserDislikeMapper;
	@Autowired
	private SbUserWatchHistoryMapper sbUserWatchHistoryMapper;
	@Autowired
	private SbTopicMapper sbTopicMapper;
	@Autowired
	private SbActivityMapper sbActivityMapper;
	@Autowired
	private TopicListService topicListService;
	@Autowired
	private CommentListService commentListService;
	@Autowired
	private PraiseService praiseService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ActivityService activityService;
	@Value("#{'${audit_status}'.split(',')}")
	private List<Integer> auditStatusList;

	/**
	 * 我的个人作品列表
	 *
	 * @param pageIndex
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/myTopicList/{pageIndex}")
	public Response<BaseListDto<TopicDto>> myTopicList(@PathVariable("pageIndex") Integer pageIndex,
													   @RequestHeader(value = "appId") Integer appId) {
		PageInDto pageInDto = new PageInDto(pageIndex, 10);
		return topicListService.getTopicByPageSimple(pageInDto, appId, null, null, 1,
				PageSource.MY_TOPIC_LIST);

	}

	/**
	 * 他人的个人作品列表
	 *
	 * @param pageIndex
	 * @param userId
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/otherTopicList/{userId}/{pageIndex}")
	public Response<BaseListDto<TopicDto>> otherTopicList(@PathVariable("pageIndex") Integer pageIndex,
														  @PathVariable("userId") Integer userId, @RequestHeader(value = "appId") Integer appId) {
		PageInDto pageInDto = new PageInDto(pageIndex, 10);
		return topicListService.getTopicByPageSimple(pageInDto, appId, null, userId, 1,
				PageSource.OTHER_TOPIC_LIST);

	}

	@RequestMapping("/myInfo")
	public Response<MyInfoDto> getUserInfo(){

		DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		DsmUser dsmUser = dsmUserMapper.selectByPrimaryKey(user.getUid());

		MyInfoDto myInfoDto = new MyInfoDto();
		BeanUtils.copyProperties(dsmUser,myInfoDto);

		myInfoDto.setFansNum(BaseUtil.instance.numberFormat(dsmUser.getFansNum()));
		myInfoDto.setFollowNum(BaseUtil.instance.numberFormat(dsmUser.getFollowNum()));
		myInfoDto.setLikeTotalNum(BaseUtil.instance.numberFormat(dsmUser.getLikeNum()+dsmUser.getLikeAlbumNum()));
		myInfoDto.setLikeNum(BaseUtil.instance.numberFormat(dsmUser.getLikeNum()));
		myInfoDto.setLikeAlbumNum(BaseUtil.instance.numberFormat(dsmUser.getLikeAlbumNum()));
		myInfoDto.setOpusNum(BaseUtil.instance.numberFormat(dsmUser.getPublishCount()));
		myInfoDto.setNickname(dsmUser.getNickName());
		myInfoDto.setUserType(dsmUser.getType());


		Header header = LocalData.HEADER.get();

		//※查出全部
		SbTopicExample example = new SbTopicExample();
		SbTopicExample.Criteria criteria = example.createCriteria();
		criteria.andAppIdEqualTo(Integer.valueOf(header.getAppId()));
		criteria.andUidEqualTo(user.getUid());
		criteria.andActivityIdIsNotNull();
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		criteria.andAuditStatusIn(auditStatusList);
		List<SbTopic> sbTopics = sbTopicMapper.selectByExample(example);

		if(sbTopics.isEmpty()) {
			myInfoDto.setActivifyNum("0");
		}else{
			Set<Integer> collect = sbTopics.stream().map(SbTopic::getActivityId).collect(Collectors.toSet());
			myInfoDto.setActivifyNum(collect.size()+"");
		}

		//历史
		SbUserWatchHistoryExample sbUserWatchHistoryExample = new SbUserWatchHistoryExample();
		SbUserWatchHistoryExample.Criteria sbUserWatchHistoryExampleCriteria = sbUserWatchHistoryExample.createCriteria();
		sbUserWatchHistoryExampleCriteria.andUidEqualTo(user.getUid());
		sbUserWatchHistoryExampleCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		long num = sbUserWatchHistoryMapper.countByExample(sbUserWatchHistoryExample);
		myInfoDto.setWatchHistoryNum(BaseUtil.instance.numberFormat(new Long(num).intValue()));

		if(StringUtils.isBlank(myInfoDto.getIntroduce())){
			myInfoDto.setIntroduce("这个人很懒，什么也不想写~");
		}

		return new Response<>(myInfoDto);

	}
	/**
	 * 设备相关信息，目前仅返回历史个数；
	 * @return
	 */
	@RequestMapping("/deviceInfo")
	public Response<DeviceInfo> getDeviceInfo() {
		Response<DeviceInfo> resp = new Response<>();
		DeviceInfo dto = new DeviceInfo();
		dto.setWatchHistoryNum(BaseUtil.instance.numberFormat(historyService.getHistoryCountByDid(LocalData.HEADER.get().getDid())));
		resp.setResult(dto);
		return resp;
	}

	public static void main(String[] args) {
		System.out.println(1300/1000d);
		String a = BaseUtil.instance.numberFormat(1399);
		System.out.println(a);
	}

	@RequestMapping("/otherInfo")
	public Response<MyInfoDto> getUserInfo(@RequestParam Integer uid){


		DsmUser dsmUser = dsmUserMapper.selectByPrimaryKey(uid);
		MyInfoDto myInfoDto = new MyInfoDto();
		BeanUtils.copyProperties(dsmUser,myInfoDto);

		myInfoDto.setFansNum(BaseUtil.instance.numberFormat(dsmUser.getFansNum()));
		myInfoDto.setFollowNum(BaseUtil.instance.numberFormat(dsmUser.getFollowNum()));
		myInfoDto.setLikeTotalNum(BaseUtil.instance.numberFormat(dsmUser.getLikeNum()+dsmUser.getLikeAlbumNum()));
		myInfoDto.setLikeNum(BaseUtil.instance.numberFormat(dsmUser.getLikeNum()));
		myInfoDto.setLikeAlbumNum(BaseUtil.instance.numberFormat(dsmUser.getLikeAlbumNum()));
		myInfoDto.setOpusNum(BaseUtil.instance.numberFormat(dsmUser.getPublishCount()));
		myInfoDto.setNickname(dsmUser.getNickName());
		myInfoDto.setUserType(dsmUser.getType());

		Header header = LocalData.HEADER.get();
		//※查出全部
		SbTopicExample example = new SbTopicExample();
		SbTopicExample.Criteria criteria = example.createCriteria();
		criteria.andAppIdEqualTo(Integer.valueOf(header.getAppId()));
		criteria.andUidEqualTo(uid);
		criteria.andActivityIdIsNotNull();
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		criteria.andAuditStatusIn(auditStatusList);
		List<SbTopic> sbTopics = sbTopicMapper.selectByExample(example);

		if(sbTopics.isEmpty()) {
			myInfoDto.setActivifyNum("0");
		}else{
			Set<Integer> collect = sbTopics.stream().map(SbTopic::getActivityId).collect(Collectors.toSet());
			myInfoDto.setActivifyNum(collect.size()+"");
		}

		if(StringUtils.isBlank(myInfoDto.getIntroduce())){
			myInfoDto.setIntroduce("这个人很懒，什么也不想写~");
		}

		SbUserPraiseExample sbUserPraiseExample = new SbUserPraiseExample();
		SbUserPraiseExample.Criteria sbUserPraiseExampleCriteria = sbUserPraiseExample.createCriteria();

		DsmUser me = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		if(me != null) {
			sbUserPraiseExampleCriteria.andUidEqualTo(me.getUid())
					.andAppIdEqualTo(Integer.valueOf(header.getAppId()))
					.andStatusEqualTo(YesNoEnum.YES.getCode())
					.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue())
					.andTypeEqualTo(UserPraiseType.FOLLOW.getValue())
					.andTargetIdEqualTo(uid.longValue());

			long num = sbUserPraiseMapper.countByExample(sbUserPraiseExample);
			if (num > 0) {
				myInfoDto.setIsFollow(YesNoEnum.YES.getCode());
			}

			boolean isExist = praiseService.isExist(uid.longValue(),UserPraiseTargetType.USER,header.getAppId(),me.getUid(),UserPraiseType.BLACKLIST);
			myInfoDto.setIsInBlack(isExist ? 1 : 0);

		}
		return new Response<>(myInfoDto);
	}

	/**
	 * 关注的频道列表
	 * @return
	 */
	@RequestMapping("/otherChannelList/{pageIndex}")
	public Response<BaseListDto<ChannelDto>> myChannelList(@PathVariable Integer pageIndex,
														   @RequestParam Integer uid ){

		PageInDto pageInDto = new PageInDto(pageIndex);
		SbUserPraiseExample sbUserPraiseExample = new SbUserPraiseExample();
		sbUserPraiseExample.setOffset(pageInDto.getOffset());
		sbUserPraiseExample.setLimit(pageInDto.getLimit());
		sbUserPraiseExample.setOrderByClause("un_read_num desc");
		SbUserPraiseExample.Criteria sbUserPraiseExampleCriteria = sbUserPraiseExample.createCriteria();
		sbUserPraiseExampleCriteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
		sbUserPraiseExampleCriteria.andTargetTypeEqualTo(UserPraiseTargetType.CHANNEL.getValue());
		sbUserPraiseExampleCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		sbUserPraiseExampleCriteria.andUidEqualTo(uid);

		List<SbUserPraise> sbUserPraises = sbUserPraiseMapper.selectByExample(sbUserPraiseExample);

		if(sbUserPraises.isEmpty()){
			return new Response<>();
		}

		//频道id
		List<Integer> channelIdList = sbUserPraises.stream()
				.map(a -> a.getTargetId().intValue())
				.collect(Collectors.toList());

		SbChannelExample sbChannelExample = new SbChannelExample();
		SbChannelExample.Criteria sbChannelExampleCriteria = sbChannelExample.createCriteria();
		sbChannelExampleCriteria.andIdIn(channelIdList);
		sbChannelExampleCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());

		List<SbChannel> sbChannels = sbChannelMapper.selectByExample(sbChannelExample);

		if(sbChannels.isEmpty()){
			return new Response<>();
		}

		//频道id--频道信息
		Map<Integer,SbChannel> channelMap = sbChannels.stream().collect(
				Collectors.toMap(SbChannel::getId, t -> t));


		List<ChannelDto> channelDtoList = sbUserPraises.stream()
				.map(a -> {
					int channelId = a.getTargetId().intValue();
					SbChannel sbChannel = channelMap.get(channelId);
					ChannelDto dto = new ChannelDto();
					dto.setId(channelId);
					dto.setName(sbChannel.getName());
					dto.setUrl(sbChannel.getUrl());
					dto.setUnReadNum(a.getUnReadNum() + "");
					return dto;
				})
				.collect(Collectors.toList());

		BaseListDto<ChannelDto> baseListDto = new BaseListDto<>(channelDtoList,pageInDto);

		return new Response<>(baseListDto);
	}


	/**
	 * 我发表的评论列表
	 *
	 * @param
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/myCommentList/{pageIndex}")
	public Response<BaseListDto<CommentDto>> myCommentList(@PathVariable("pageIndex") Integer pageIndex,
														   @RequestHeader(value = "appId", required = false) Integer appId) {

		Response<BaseListDto<CommentDto>> resp = new Response<BaseListDto<CommentDto>>();
		PageInDto pageInfo = new PageInDto(pageIndex, 10);

		List<CommentDto> topicList = commentListService.getCommentByPage(pageInfo, appId, null, UserCommentTargetType.TOPIC.getValue(), null, null, 1,
				PageSource.MY_COMMENT_LIST);

		resp.setResult(new BaseListDto<>(topicList, pageInfo));
		return resp;

	}

	/**
	 * 他人发表的评论列表
	 *
	 * @param
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/otherCommentList/{userId}/{pageIndex}")
	public Response<BaseListDto<CommentDto>> otherCommentList(@PathVariable("userId") Integer userId,
															  @PathVariable("pageIndex") Integer pageIndex,
															  @RequestHeader(value = "appId", required = false) Integer appId) {

		Response<BaseListDto<CommentDto>> resp = new Response<BaseListDto<CommentDto>>();
		PageInDto pageInfo = new PageInDto(pageIndex, 10);

		List<CommentDto> topicList = commentListService.getCommentByPage(pageInfo, appId, UserCommentTargetType.TOPIC.getValue(), null, null, userId, 1,
				PageSource.OTHER_COMMENT_LIST);

		resp.setResult(new BaseListDto<>(topicList, pageInfo));
		return resp;

	}

	/**
	 * 反感的话题
	 * @param topicId
	 * @return
	 */
	@RequestMapping("/myDislike")
	public Response<String> dislike(@RequestParam int topicId, @RequestHeader(value = "appId") Integer appId){
		DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		SbUserDislike record = new SbUserDislike();
		record.setUid(user.getUid());
		record.setTopicId(topicId);
		record.setAppId(appId);
		sbUserDislikeMapper.insertSelective(record);
		return new Response<>();

	}

	@RequestMapping(value = "/myFollowAlbum/{pageIndex}", method = RequestMethod.GET)
	public Response<BaseListDto<AlbumDto<IdTitleImage>>> getMyFollowAlbums(@PathVariable("pageIndex") Integer pageIndex,
																		   @RequestHeader(value = "appId") Integer appId) {
		Header header = LocalData.HEADER.get();
		Integer uid = header.getUid();
		PageInDto pageInfo = new PageInDto(pageIndex, 8);
		return praiseService.getFollowAlbumByPageSimple(pageInfo, appId, null);
	}

	@RequestMapping(value = "/otherFollowAlbum/{userId}/{pageIndex}", method = RequestMethod.GET)
	public Response<BaseListDto<AlbumDto<IdTitleImage>>> getOtherFollowAlbums(
			@PathVariable("pageIndex") Integer pageIndex, @PathVariable("userId") Integer userId,
			@RequestHeader(value = "appId") Integer appId) {
		Header header = LocalData.HEADER.get();
		Integer uid = header.getUid();
		PageInDto pageInfo = new PageInDto(pageIndex, 8);
		return praiseService.getFollowAlbumByPageSimple(pageInfo, appId, userId);

	}
	/**
	 * 观看历史列表
	 */
	@RequestMapping(value = "/getWatchHistory/{pageIndex}")
	public Response getWatchHistory(@PathVariable Integer pageIndex) throws Exception {
		Header header = LocalData.HEADER.get();
		Integer uid = header.getUid();
		String did = header.getDid();
		String appId = header.getAppId();

		Response<BaseListDto<TopicDto>> resp = new Response<BaseListDto<TopicDto>>();
		BaseListDto<TopicDto> baseDto = new BaseListDto<TopicDto>();
		PageInDto pageInDto = new PageInDto(pageIndex, 10);

		List<TopicDto> userTopic = historyService.getWatchHistoryList(pageInDto, uid, did, appId);

		baseDto.setList(userTopic);
		baseDto.setPageIndex(pageIndex);
		baseDto.setHasNext(userTopic.size() >= pageInDto.getPageSize() ? 1 : 0);
		resp.setResult(baseDto);
		return resp;
	}

	@RequestMapping("/myActivifyList/{pageIndex}")
	public Response<BaseListDto<ActivityDto>> getMyActivifyList(@PathVariable int pageIndex){
		DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		return getActivifyList(pageIndex,user.getUid());

	}

	@RequestMapping("/otherActivifyList/{pageIndex}")
	public Response<BaseListDto<ActivityDto>> getMyActivifyList(@PathVariable Integer pageIndex,@RequestParam Integer uid) {
		DsmUser user = dsmUserMapper.selectByPrimaryKey(uid);
		if(user == null){
			return new Response<>(RespStatusEnum.PARAM_FAIL);
		}
		return getActivifyList(pageIndex,uid);
	}

	private Response<BaseListDto<ActivityDto>> getActivifyList(Integer pageIndex,Integer uid){
		Header header = LocalData.HEADER.get();
		//※查出全部
		SbTopicExample example = new SbTopicExample();
		SbTopicExample.Criteria criteria = example.createCriteria();
		criteria.andAppIdEqualTo(Integer.valueOf(header.getAppId()));
		criteria.andUidEqualTo(uid);
		criteria.andActivityIdIsNotNull();
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		criteria.andAuditStatusIn(auditStatusList);
		List<SbTopic> sbTopics = sbTopicMapper.selectByExample(example);
		if(sbTopics.isEmpty()){
			return new Response<>();
		}

		List<Integer> activifyIdList = sbTopics.stream().map(SbTopic::getActivityId).collect(Collectors.toList());
		SbActivityExample sbActivityExample = new SbActivityExample();
		sbActivityExample.setOrderByClause("start_time desc");
		SbActivityExample.Criteria sbActivityExampleCriteria = sbActivityExample.createCriteria();
		sbActivityExampleCriteria.andIdIn(activifyIdList);
		List<SbActivity> sbActivities = sbActivityMapper.selectByExample(sbActivityExample);

		List<ActivityDto> activityDtoList = sbActivities.stream().map(activity->{
			ActivityDto dto = new ActivityDto();
			dto.setId(activity.getId());
			dto.setTitle(activity.getTitle());
			dto.setThumbCover(activity.getThumbCover());
			dto.setReplyNum(BaseUtil.instance.formatNum(activity.getReplyNum(),true));

			if (YesNoEnum.YES.getCode().equals(activity.getWhetherOnline())) {
				activityService.setActivityInfo(activity, dto);
			}else {
				dto.setActivityStatus(9);
				dto.setActivityStatusText("活动下线");
			}
			return dto;
		}).collect(Collectors.toList());

		PageInDto pageInDto = new PageInDto(pageIndex);
		List<ActivityDto> pageList =activityDtoList.stream().skip(pageInDto.getOffset()).limit(pageInDto.getLimit()).collect(Collectors.toList());
		BaseListDto<ActivityDto> baseDto = new BaseListDto<>(pageList,pageInDto);
		return new Response<>(baseDto);

	}

	/**
	 * 删除观看历史列表
	 */
	@RequestMapping(value = "/delWatchHistory/{historyId}")
	public Response<String> delWatchHistory(@PathVariable Integer historyId) throws Exception {
		return historyService.del(historyId);
	}
}
