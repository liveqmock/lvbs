package com.daishumovie.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.activity.ActivityDto;
import com.daishumovie.base.dto.category.CategoryDto;
import com.daishumovie.base.dto.share.ShareActivityDto;
import com.daishumovie.base.dto.share.ShareAd;
import com.daishumovie.base.dto.share.ShareDetailDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.dto.topic.UserAndVideoInfo;
import com.daishumovie.base.dto.topic.VideoCollection;
import com.daishumovie.base.dto.topic.VideoDto;
import com.daishumovie.base.dto.user.UserBriefDto;
import com.daishumovie.base.dto.user.UserFollowDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.TopicStatus;
import com.daishumovie.base.enums.db.UserPraiseTargetType;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.BaseUtil;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.enums.front.VideoType;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.mapper.smallbronze.SbChannelMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicBucketMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserDislikeMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.dao.model.SbChannel;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicBucket;
import com.daishumovie.dao.model.SbTopicBucketExample;
import com.daishumovie.dao.model.SbTopicExample;
import com.daishumovie.dao.model.SbUserDislike;
import com.daishumovie.dao.model.SbUserDislikeExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.dao.model.SbVideoExample;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.JacksonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TopicListService {

    @Autowired
    private SbTopicMapper topicMapper;
    @Autowired
    private SbChannelMapper channelMapper;
    @Autowired
    private DsmUserMapper userMapper;
    @Autowired
    private SbVideoMapper videoMapper;
    @Autowired
    private PraiseService praiseService;
    @Resource(name = "hashRedisTemplate")
    private HashOperations<String, Integer, Integer> hashOps;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SbTopicBucketMapper sbTopicBucketMapper;
    @Autowired
    private SbUserDislikeMapper sbUserDislikeMapper;

    @Autowired
    private HistoryService historyService;
    @Autowired
    private SbActivityMapper sbActivityMapper;

    @Value("#{'${audit_status}'.split(',')}")
    private List<Integer> auditStatusList;
    @Autowired
    private ActivityService activityService;
    @Value("${app.recBucketId}")
	private Integer recBucketId;
    /**
     * 所有审核状态，个人中心中-个人作品列表使用；
     */
    private List<Integer> allAuditStatusList = Arrays.asList(0, 1, 2, 3, 4);

    public List<TopicDto> getTopicByPage(PageInDto pageInfo, Integer appId, Integer channelId, Integer userId,
                                         Integer type, boolean isIOS, PageSource page) {
        Header header = LocalData.HEADER.get();
        Integer localUid = header.getUid();
        SbTopicExample example = new SbTopicExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        if (type == 1) {
            example.setOrderByClause("create_time desc");
        } else {
            example.setOrderByClause("diff_value desc");
        }
        SbTopicExample.Criteria criteria = example.createCriteria();
        if (channelId != null) {
            criteria.andChannelIdEqualTo(channelId);
        }
        if (appId != null) {
            criteria.andAppIdEqualTo(appId);
        }
        // 来自个人的发布话题列表请求
        if (page == PageSource.MY_TOPIC_LIST) {
            criteria.andUidEqualTo(localUid);
            // 查看别人发布的话题列表请求
        } else if (page == PageSource.OTHER_TOPIC_LIST && userId != null) {
            criteria.andUidEqualTo(userId);
        }
        // 正常在线的话题；
        criteria.andStatusEqualTo(Whether.yes.getValue());
        // 符合配置的审核过的话题才展示出来；
        criteria.andAuditStatusIn(auditStatusList);
        List<SbTopic> topicList = topicMapper.selectByExample(example);
        List<TopicDto> list = new ArrayList<>();
        if (topicList.isEmpty()) {
            return list;
        }

        for (SbTopic topic : topicList) {
            try {
                TopicDto dto = wrapTopicDto(topic, localUid, isIOS, page);
                list.add(dto);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }
        return list;
    }


    public TopicDto wrapTopicDto(SbTopic topic, Integer localUid, Boolean isIOS, PageSource page) {
        TopicDto dto = new TopicDto();
        try {
            BeanUtils.copyProperties(topic, dto);
            dto.setFollowNum(BaseUtil.instance.formatNum(topic.getFollowNum(), true));
            dto.setDiffValue(BaseUtil.instance.formatNum(topic.getDiffValue(), true));
            dto.setReplyNum(BaseUtil.instance.formatNum(topic.getReplyNum(), true));
            dto.setDescribe(topic.getDescription());

            CategoryDto category = new CategoryDto();
            SbChannel topicCategory = channelMapper.selectByPrimaryKey(topic.getChannelId());
            category.setId(topicCategory.getId());
            category.setName(topicCategory.getName());
            dto.setCategory(category);

            UserBriefDto user = new UserBriefDto();
            DsmUser owner = userMapper.selectByPrimaryKey(topic.getUid());
            user.setId(owner.getUid());
            user.setNickname(owner.getNickName());
            user.setHeadImg(owner.getAvatar());
            user.setHasFollow(praiseService.hasFollow(localUid, owner.getUid(), UserPraiseTargetType.USER.getValue()) ? 1 : 0);
            user.setUserType(owner.getType());
            dto.setUser(user);

            if (page != PageSource.FOLLOW_TOPIC_LIST) {
                dto.setHasFollow(praiseService.hasFollow(localUid, topic.getId(), UserPraiseTargetType.TOPIC.getValue()) ? 1 : 0);
            }
            dto.setPraiseStatus(
                    praiseService.getPraiseStatus(localUid, topic.getId(), UserPraiseTargetType.TOPIC.getValue()));

            // isIOS字段不为空则需要合辑视频字段
            if (isIOS != null) {
                List<VideoDto> videos = new ArrayList<>();
                // 题主视频
                if (topic.getVideoId() != null) {
                    SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
                    VideoDto videoDto = new VideoDto();
                    videoDto.setId(video.getId());
                    videoDto.setName(VideoType.OWNER_VIDEO.getName());
                    videoDto.setType(VideoType.OWNER_VIDEO.getValue());
                    videoDto.setUrl(video.getFormatUrl() != null ? video.getFormatUrl() : video.getOriUrl());
                    videoDto.setCover(video.getCover());
                    videos.add(videoDto);
                }
                if (topic.getVideoCollection() != null) {
                    VideoCollection videoCollection = JacksonUtil.json2pojo(topic.getVideoCollection(),
                            VideoCollection.class);
                    VideoDto videoDto = new VideoDto();
                    if (isIOS) {
                        videoDto.setName(VideoType.IOS_VIDEO_COLLECTION.getName());
                        videoDto.setType(VideoType.IOS_VIDEO_COLLECTION.getValue());
                        videoDto.setUrl(videoCollection.getM3u8Url());
                        videoDto.setCover(videoCollection.getCover());
                        videoDto.setList(videoCollection.getList());
                    } else {
                        videoDto.setName(VideoType.ANDROID_VIDEO_COLLECTION.getName());
                        videoDto.setType(VideoType.ANDROID_VIDEO_COLLECTION.getValue());
                        videoDto.setCover(videoCollection.getCover());
                        videoDto.setList(videoCollection.getList());
                        List<Integer> videoIds = new ArrayList<>();
                        for (UserAndVideoInfo info : videoCollection.getList()) {
                            videoIds.add(info.getVideoId());
                        }
                        SbVideoExample videoExample = new SbVideoExample();
                        SbVideoExample.Criteria videoCriteria = videoExample.createCriteria();
                        videoCriteria.andIdIn(videoIds);
                        List<SbVideo> videoList = videoMapper.selectByExample(videoExample);
                        Map<Integer, SbVideo> videoMap = CollectionUtils.convert2Map(videoList, "getId");
                        List<String> urls = new ArrayList<>();
                        for (UserAndVideoInfo info : videoCollection.getList()) {
                            urls.add(videoMap.get(info.getVideoId()).getFormatUrl() != null
                                    ? videoMap.get(info.getVideoId()).getFormatUrl()
                                    : videoMap.get(info.getVideoId()).getOriUrl());
                        }
                        videoDto.setUrls(urls);
                    }
                    videos.add(videoDto);
                } else {
                    VideoDto videoDto = new VideoDto();
                    videoDto.setName("墙空了");
                    SbChannel pChannel = channelMapper.selectByPrimaryKey(topicCategory.getPid());
                    videoDto.setCover(pChannel.getDefaultUrl());
                    videos.add(videoDto);
                }
                dto.setVideos(videos);
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return dto;
    }

    /**
     * 话题详情 （简化版 9/21）
     *
     * @param topicId
     * @param appId
     * @return
     * @throws Exception
     */
    public Response<TopicDto> getTopicDetailSimple(Integer topicId, Integer appId) throws Exception {
        Response<TopicDto> resp = new Response<>();
        SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
        if (topic == null || !TopicStatus.published.getValue().equals(topic.getStatus())) {
            resp.setRespStatus(RespStatusEnum.TOPIC_NOT_EXIST);
            return resp;
        }
        Header header = LocalData.HEADER.get();
        Integer localUid = header.getUid();
        // 非审核通过状态；
        if (!auditStatusList.contains(topic.getAuditStatus())) {

        }
        // appId与topicId合法校验；
        if (!topic.getAppId().equals(appId)) {

        }
        TopicDto dto = sbTopic2Dto(topic, PageSource.TOPIC_DETAIL);
        //关注用户（取10个）
        List<UserFollowDto> userFollowDtos = praiseService.followUserList(topicId.longValue(), UserPraiseTargetType.TOPIC.getValue(), new PageInDto(1, 10), appId, PageSource.TOPIC_DETAIL);
        dto.setFollowUsers(userFollowDtos);

        resp.setResult(dto);
        return resp;
    }


    /**
     * 话题详情接口
     *
     * @param topicId
     * @param appId
     * @param isIOS
     * @return
     */
    public Response<TopicDto> getTopicDetail(Integer topicId, Integer appId, boolean isIOS) throws Exception {
        Response<TopicDto> resp = new Response<>();
        SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
        if (topic == null) {
            resp.setRespStatus(RespStatusEnum.TOPIC_NOT_EXIST);
            return resp;
        }
        Header header = LocalData.HEADER.get();
        Integer localUid = header.getUid();
        // 非审核通过状态；
        if (!auditStatusList.contains(topic.getAuditStatus())) {

        }
        // appId与topicId合法校验；
        if (!topic.getAppId().equals(appId)) {

        }
        TopicDto dto = wrapTopicDto(topic, localUid, isIOS, PageSource.TOPIC_DETAIL);
        //关注用户（取10个）
        List<UserFollowDto> userFollowDtos = praiseService.followUserList(topicId.longValue(), UserPraiseTargetType.TOPIC.getValue(), new PageInDto(1, 10), appId, PageSource.TOPIC_DETAIL);
        dto.setFollowUsers(userFollowDtos);

        resp.setResult(dto);
        return resp;
    }

    /**
     * @return
     */
    public List<TopicDto> getTopicList(List<Integer> topicIds, PageSource page) {

        Header header = LocalData.HEADER.get();
        SbTopicExample sbTopicExample = new SbTopicExample();
        SbTopicExample.Criteria sbTopicExampleCriteria = sbTopicExample.createCriteria();
        sbTopicExampleCriteria.andIdIn(topicIds);
        sbTopicExampleCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        sbTopicExampleCriteria.andAuditStatusIn(auditStatusList);
        sbTopicExampleCriteria.andAppIdEqualTo(Integer.valueOf(header.getAppId()));
        List<SbTopic> sbTopics = topicMapper.selectByExample(sbTopicExample);

        Map<Integer, SbTopic> sbTopicMap = sbTopics.stream().collect(Collectors.toMap(SbTopic::getId, sbTopic -> sbTopic));
        List<Integer> dislikeIds = new ArrayList<>();
        // 不感兴趣 过滤；
        if (header.getUid() != null) {
            SbUserDislikeExample example = new SbUserDislikeExample();
            SbUserDislikeExample.Criteria criteria = example.createCriteria();
            criteria.andAppIdEqualTo(Integer.valueOf(header.getAppId()));
            criteria.andUidEqualTo(header.getUid());
            criteria.andTopicIdIn(topicIds);
            List<SbUserDislike> dislikeTopicList = sbUserDislikeMapper.selectByExample(example);

            for (SbUserDislike sbDislike : dislikeTopicList) {
                dislikeIds.add(sbDislike.getTopicId());
            }
        }

        List<TopicDto> topicDtoList = new ArrayList<>(sbTopics.size());
        for (Integer topicId : topicIds) {
            if (dislikeIds.contains(topicId)) {
                continue;
            }
            SbTopic sbTopic = sbTopicMap.get(topicId);
            if (sbTopic == null) {
                continue;
            }
            TopicDto topicDto = sbTopic2Dto(sbTopic, page);
            topicDtoList.add(topicDto);
        }

        return topicDtoList;
    }

    public TopicDto sbTopic2Dto(SbTopic topic, PageSource page) {
        TopicDto dto = new TopicDto();
        BeanUtils.copyProperties(topic, dto);
        dto.setFollowNum(BaseUtil.instance.formatNum(topic.getFollowNum(), true));
        dto.setReplyNum(BaseUtil.instance.formatNum(topic.getReplyNum(), true));
        dto.setDescribe(topic.getDescription());

        Integer activityId = topic.getActivityId();
        if(activityId != null) {
            SbActivity sbActivity = sbActivityMapper.selectByPrimaryKey(activityId);
            ActivityDto activityDto = new ActivityDto();
            activityDto.setId(sbActivity.getId());
            activityDto.setTitle(sbActivity.getTitle());
            activityDto.setTopic("#"+sbActivity.getTopic()+"#");
            dto.setActivity(activityDto);
        }

        Date publishTime = topic.getPublishTime();
        // 未发布取创建时间
        if (publishTime == null) {
            publishTime = topic.getCreateTime();
        }
        String distanceDay = DateUtil.getDistance(new Date(), publishTime);
        dto.setTime(distanceDay);

        Header header = LocalData.HEADER.get();

        UserBriefDto user = new UserBriefDto();
        DsmUser owner = userMapper.selectByPrimaryKey(topic.getUid());
        user.setId(owner.getUid());
        user.setNickname(owner.getNickName());
        user.setHeadImg(owner.getAvatar());
        user.setHasFollow(praiseService.hasFollow(header.getUid(), owner.getUid(), UserPraiseTargetType.USER.getValue()) ? 1 : 0);
        user.setUserType(owner.getType());
        dto.setUser(user);

        dto.setHasFollow(praiseService.hasFollow(header.getUid(), topic.getId(), UserPraiseTargetType.TOPIC.getValue()) ? 1 : 0);
//        //是否关注话题发布者
//        dto.setIsFollowUser(praiseService.isPraise(topic.getUid().longValue(), UserPraiseTargetType.USER.getValue(), header.getUid(), UserPraiseType.FOLLOW.getValue()) ? 1 : 0);

        SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
        VideoDto videoDto = new VideoDto();
        videoDto.setId(video.getId());
        videoDto.setCover(video.getCover());
        if (page == PageSource.SHARE_TOPIC_DETAIL) {
            videoDto.setPlayNum(BaseUtil.instance.formatNum(video.getPlayNum() + video.getvPlayNum(), true));
        } else {
            videoDto.setPlayNum(BaseUtil.instance.formatNum(video.getPlayNum() + video.getvPlayNum(), false));
        }
        // 关注的话题，如果被删除了或者下线了，返回数据，并设置审核状态9
        if ((page == PageSource.FOLLOW_TOPIC_LIST || page == PageSource.WATCH_HISTORY)
                && (topic.getStatus().equals(TopicStatus.offline.getValue())
                || topic.getStatus().equals(TopicStatus.deleted.getValue()))) {
            dto.setAuditStatus(9);
        }
        // 我的作品，如果被下线了，返回数据，并设置审核状态9
        if (page == PageSource.MY_TOPIC_LIST && topic.getStatus().intValue() == TopicStatus.offline.getValue()) {
            dto.setAuditStatus(9);
        }
        videoDto.setBarrageNum(BaseUtil.instance.formatNum(video.getBarrageNum(), false));
        videoDto.setDuration(video.getDuration());
        videoDto.setDimension(video.getDimension());
        videoDto.setSize(video.getSize());
        dto.setVideo(videoDto);
        return dto;
    }

    public String getVideoUrl(Integer videoId, Integer appId) {
        SbVideoExample example = new SbVideoExample();
        example.setLimit(1);
        SbVideoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(videoId);
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        List<SbVideo> sbVideos = videoMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(sbVideos)) {
            return "";
        }
        //增加播放数
        videoMapper.selfPlusMinusByPrimaryKey("play_num", "+", 1, videoId);
        return sbVideos.get(0).getFormatUrl() != null ? sbVideos.get(0).getFormatUrl() : sbVideos.get(0).getOriUrl();
    }

    public String getVideoUrlNew(Integer topicId, Integer videoId, Integer appId, String did,Integer uid) {
        SbVideoExample example = new SbVideoExample();
        example.setLimit(1);
        SbVideoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(videoId);
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        List<SbVideo> sbVideos = videoMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(sbVideos)) {
            return "";
        }
        SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
        // 审核通过的视频增加播放历史及次数
        if (auditStatusList.contains(topic.getAuditStatus())) {
            historyService.saveWatchHistory(topicId,videoId,uid,did);
            //增加播放数
            videoMapper.selfPlusMinusByPrimaryKey("play_num", "+", 1, videoId);
        }
        return sbVideos.get(0).getFormatUrl() != null ? sbVideos.get(0).getFormatUrl() : sbVideos.get(0).getOriUrl();

    }

    /**
     * 获取桶号
     *
     * @param
     * @param maxBucketNum 最大桶号，不能小于上次调用的桶号
     * @return int
     */
    public Integer getNextBucketIndex(Integer maxBucketNum) {

        if (maxBucketNum <= 0) {
            return null;
        }

        Header header = LocalData.HEADER.get();

        //使用hash方式存储， uid --> max:min
        String key = "BUCKET_USER_" + header.getAppId() + header.getDid();
		// 第一次访问，返回特定桶号
		if (!redisTemplate.hasKey(key)) {
			hashOps.put(key, recBucketId, recBucketId);
			return recBucketId;
		}

        //查询当前桶记录
        Integer zoneMin = hashOps.get(key, maxBucketNum);

        /*未记录*/
        if (zoneMin == null) {
            //下一个桶号
            int nextBaseBucketIndex = maxBucketNum - 1;
            //查询下一个桶号记录
            Integer nextBaseBucketIndexMin = hashOps.get(key, nextBaseBucketIndex);
            //不存在下一个桶号记录，保存桶到当前桶号并记录
            if (nextBaseBucketIndexMin == null) {
                hashOps.put(key, maxBucketNum, maxBucketNum);
                //返回当前桶号
                return maxBucketNum;
            }
            //存在下一个桶号 更新区间
            hashOps.delete(key, nextBaseBucketIndex);
            hashOps.put(key, maxBucketNum, nextBaseBucketIndexMin);
            //返回当前桶号
            return maxBucketNum;
        }

        /*已记录*/
        //区段最小值减1,此值为下一桶号，做返回值
        int min = zoneMin - 1;

        if (min == 0) {
            return null;
        }

        //区段最小值减2，返回值的下一区段开始值
        int nextMin = zoneMin - 2;
        //返回值的下一区段记录
        Integer nextZoneMin = hashOps.get(key, nextMin);

        //下一区段记录不存在
        if (nextZoneMin == null) {
            hashOps.put(key, maxBucketNum, min);//over
            return min;

        }
        //下一区段记录存在， 与返回值上一个区段合并
        hashOps.delete(key, nextMin);
        hashOps.put(key, maxBucketNum, nextZoneMin);

        //over
        return min;
    }


    public Response<BaseListDto<TopicDto>> getTopicByPageSimple(PageInDto pageInfo, Integer appId, Integer channelId, Integer userId,
                                                                Integer type, PageSource page) {
        Response<BaseListDto<TopicDto>> resp = new Response<>();
        Header header = LocalData.HEADER.get();
        Integer localUid = header.getUid();
        SbTopicExample example = new SbTopicExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        if (type == 1) {
            example.setOrderByClause("orders desc, publish_time desc");
        } else {
            example.setOrderByClause("diff_value desc");
        }
        SbTopicExample.Criteria criteria = example.createCriteria();
        if (channelId != null) {
            criteria.andChannelIdEqualTo(channelId);
        }
        if (appId != null) {
            criteria.andAppIdEqualTo(appId);
        }
        // 来自个人的发布话题列表请求
        if (page == PageSource.MY_TOPIC_LIST) {
            criteria.andUidEqualTo(localUid);
            // 符合配置的审核过的话题才展示出来；
            criteria.andAuditStatusIn(allAuditStatusList);
            // 个人的话题列表以创建时间降序排列
            example.setOrderByClause("create_time desc");
            // 查看别人发布的话题列表请求
        } else if (page == PageSource.OTHER_TOPIC_LIST && userId != null) {
            criteria.andUidEqualTo(userId);
            // 符合配置的审核过的话题才展示出来；
            criteria.andAuditStatusIn(auditStatusList);
        } else {
            // 符合配置的审核过的话题才展示出来；
            criteria.andAuditStatusIn(auditStatusList);
        }

        if (page == PageSource.MY_TOPIC_LIST) {
            criteria.andStatusNotEqualTo(TopicStatus.deleted.getValue());
        }else{
            // 正常在线的话题；
            criteria.andStatusEqualTo(TopicStatus.published.getValue());
        }

        List<SbTopic> topicList = topicMapper.selectByExample(example);
        List<TopicDto> list = new ArrayList<>();
        if (topicList.isEmpty()) {
            BaseListDto<TopicDto> baseDto = new BaseListDto<>(new ArrayList<TopicDto>(), pageInfo);
            resp.setResult(baseDto);
            return resp;
        }
        List<Integer> topicIds = new ArrayList<>();
        for (SbTopic topic : topicList) {
            topicIds.add(topic.getId());
        }

        List<Integer> dislikeIds = new ArrayList<>();
        // 不感兴趣 过滤；
        if (header.getUid() != null && page == PageSource.TOPIC_LIST) {
            SbUserDislikeExample dislikeExample = new SbUserDislikeExample();
            SbUserDislikeExample.Criteria dislikeCriteria = dislikeExample.createCriteria();
            dislikeCriteria.andAppIdEqualTo(Integer.valueOf(header.getAppId()));
            dislikeCriteria.andUidEqualTo(header.getUid());
            dislikeCriteria.andTopicIdIn(topicIds);
            List<SbUserDislike> dislikeTopicList = sbUserDislikeMapper.selectByExample(dislikeExample);

            for (SbUserDislike sbDislike : dislikeTopicList) {
                dislikeIds.add(sbDislike.getTopicId());
            }
        }

        for (SbTopic topic : topicList) {
            if (dislikeIds.contains(topic.getId())) {
                continue;
            }
            try {
                TopicDto dto = sbTopic2Dto(topic, page);
                list.add(dto);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }
        // 由于处理了不感兴趣的话题，需要根据原始数据判定是否存在下一页
        BaseListDto<TopicDto> baseDto = new BaseListDto<>(list, pageInfo);
        if (topicList.size() >= pageInfo.getPageSize()) {
            baseDto.setHasNext(Whether.yes.getValue());
        }
        resp.setResult(baseDto);
        return resp;
    }

    public SbTopic getTopicBean(Integer topicId) {
        return topicMapper.selectByPrimaryKey(topicId);
    }

    public SbVideo getVideoBean(Integer videoId) {
        return videoMapper.selectByPrimaryKey(videoId);
    }

    public Response<ShareDetailDto> getShareDetail(Integer topicId, Integer appId, PageSource page) {
        Response<ShareDetailDto> resp = new Response<>();
        List<Integer> discardIds = new ArrayList<>();
        discardIds.add(topicId);
        ShareDetailDto dto = new ShareDetailDto();
        SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
        // 不存在
        if (topic == null) {
            resp.setRespStatus(RespStatusEnum.TOPIC_NOT_EXIST);
            return resp;
        }
        // 视频已下线
        if (topic.getStatus().intValue() == YesNoEnum.NO.getCode()) {
            resp.setRespStatus(RespStatusEnum.TOPIC_NOT_EXIST);

            return resp;
        }
        // 非审核通过状态；
        if (!auditStatusList.contains(topic.getAuditStatus())) {

        }
        // appId与topicId合法校验；
        if (!topic.getAppId().equals(appId)) {

        }
        TopicDto topicDto = sbTopic2Dto(topic, PageSource.SHARE_TOPIC_DETAIL);
        dto.setDetail(topicDto);
        if (page == PageSource.H5_TOPIC_DETAIL || topic.getActivityId() == null) {
            SbTopicExample example = new SbTopicExample();
            example.setOffset(0);
            example.setLimit(5);
            example.setOrderByClause("follow_num desc");
            SbTopicExample.Criteria criteria = example.createCriteria();
            criteria.andChannelIdEqualTo(topic.getChannelId());
            criteria.andAppIdEqualTo(appId);
            criteria.andAuditStatusIn(auditStatusList);
            criteria.andStatusEqualTo(Whether.yes.getValue());
            criteria.andIdNotEqualTo(topicId);
            List<SbTopic> topicList = topicMapper.selectByExample(example);
            List<TopicDto> brilliantList = new ArrayList<>();
            if (topicList.size() > 0) {
                for (SbTopic sbTopic : topicList) {
                    discardIds.add(sbTopic.getId());
                    TopicDto topicDtoTmp = new TopicDto();
                    BeanUtils.copyProperties(sbTopic, topicDtoTmp);

                    UserBriefDto user = new UserBriefDto();
                    DsmUser owner = userMapper.selectByPrimaryKey(sbTopic.getUid());
                    user.setNickname(owner.getNickName());
                    user.setHeadImg(owner.getAvatar());
                    topicDtoTmp.setUser(user);

                    SbVideo video = videoMapper.selectByPrimaryKey(sbTopic.getVideoId());
                    VideoDto videoDto = new VideoDto();
                    videoDto.setCover(video.getCover());
                    videoDto.setPlayNum(BaseUtil.instance.formatNum(video.getPlayNum() + video.getvPlayNum(), true));
                    videoDto.setDuration(video.getDuration());
                    topicDtoTmp.setVideo(videoDto);
                    brilliantList.add(topicDtoTmp);
                }
            }
            dto.setBrilliantList(brilliantList);
        } else {
            // 活动相关信息
            Response<ShareActivityDto> activityResp = activityService.getShareDetail(topic.getActivityId(), appId);
            ShareActivityDto shareActivityDto = activityResp.getResult();
            if (shareActivityDto != null) {
                dto.setActivifyList(shareActivityDto.getActivityList());
                dto.setHasMoreActivity(shareActivityDto.getHasMoreActivity());
                dto.setTopicList(shareActivityDto.getTopicList().size() > 12
                        ? shareActivityDto.getTopicList().stream().limit(12).collect(Collectors.toList())
                        : shareActivityDto.getTopicList());
                dto.setHasMoreTopic(
                        shareActivityDto.getTopicList().size() > 12 ? YesNoEnum.YES.getCode() : YesNoEnum.NO.getCode());
            }
        }

        List<ShareAd> adList = getAdList(appId, discardIds);
        dto.setAdList(adList);
        resp.setResult(dto);
        return resp;
    }

    public List<ShareAd> getAdList(Integer appId, List<Integer> discardIds) {
        SbTopicBucketExample sbTopicBucketExample = new SbTopicBucketExample();
        sbTopicBucketExample.setOrderByClause("id desc");
        sbTopicBucketExample.setLimit(1);
        List<SbTopicBucket> sbTopicBuckets = sbTopicBucketMapper.selectByExample(sbTopicBucketExample);
        List<ShareAd> adList = new ArrayList<>();
        if (sbTopicBuckets.size() > 0) {
            SbTopicBucket sbTopicBucket = sbTopicBucketMapper.selectByPrimaryKey(sbTopicBuckets.get(0).getId());
            String topicIds = sbTopicBucket.getTopicIds();
            String[] topicIdArr = StringUtils.splitByWholeSeparator(topicIds, ",");
            List<Integer> topicIdIntArr = new ArrayList<>();
            for (String id : topicIdArr) {
                topicIdIntArr.add(Integer.valueOf(id));
            }
            SbTopicExample sbTopicExample = new SbTopicExample();
            SbTopicExample.Criteria sbTopicExampleCriteria = sbTopicExample.createCriteria();
            sbTopicExampleCriteria.andIdIn(topicIdIntArr);
            sbTopicExampleCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            sbTopicExampleCriteria.andAuditStatusIn(auditStatusList);
            sbTopicExampleCriteria.andAppIdEqualTo(appId);
            List<SbTopic> sbTopics = topicMapper.selectByExample(sbTopicExample);
            if (sbTopics.size() > 0) {
                for (SbTopic sbTopic : sbTopics) {
                    if (discardIds != null && discardIds.contains(sbTopic.getId())) {
                        continue;
                    }
                    if (adList.size() >= 5) {
                        break;
                    }
                    ShareAd shareAd = new ShareAd();
                    // 1 详情页
                    shareAd.setTargetType(1);
                    shareAd.setTarget(sbTopic.getId().toString());
                    shareAd.setTitle(sbTopic.getTitle());
                    SbVideo video = videoMapper.selectByPrimaryKey(sbTopic.getVideoId());
                    shareAd.setImgUrl(video.getCover());
                    adList.add(shareAd);
                }
            }
        }
        return adList;
    }
}
