package com.daishumovie.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.daishumovie.base.dto.AttributeEntry;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.KeyValue;
import com.daishumovie.base.dto.activity.ShowAward;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.aliyuncs.exceptions.ClientException;
import com.daishumovie.api.configuration.AliYunSmsProperties;
import com.daishumovie.api.service.tempData.luckyDrawData;
import com.daishumovie.base.Constants;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.activity.ActivityDto;
import com.daishumovie.base.dto.share.ShareActivityDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.enums.db.ActivityStatus;
import com.daishumovie.base.enums.db.ActivityType;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.BaseUtil;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmConfigMapper;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserSignUpMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.DsmConfig;
import com.daishumovie.dao.model.DsmConfigExample;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.DsmUserExample;
import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.dao.model.SbActivityExample;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicExample;
import com.daishumovie.dao.model.SbUserSignUp;
import com.daishumovie.dao.model.SbUserSignUpExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.RegexUtil;
import com.daishumovie.utils.SendSmsUtil;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Cheng Yufei
 * @create 2017-10-19 10:54
 **/
@Service
@Slf4j
public class ActivityService {

    @Autowired
    private SbActivityMapper sbActivityMapper;

    @Autowired
    private SbTopicMapper sbTopicMapper;

    @Autowired
    private DsmConfigMapper dsmConfigMapper;

    @Value("#{'${audit_status}'.split(',')}")
    private List<Integer> auditStatusList;

    @Autowired
    private SbVideoMapper videoMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TopicListService topicListService;

    @Autowired
    private SbUserSignUpMapper signUpMapper;

    @Autowired
    private DsmUserMapper userMapper;

    @Autowired
    private AliYunSmsProperties aliyunSmsProperties;

    @Value("${webUrlForAward}")
    private String webUrlForAward;

    //抽奖活动自增num：头部 key
    private static final String awardsActivityIncrement = "awards_activity_increment_";

    private static List<Integer> bonus = Lists.newArrayList(1, 2, 3, 4);


    public List<ActivityDto> getActivityList(PageInDto pageInDto, String appId) {

        List<ActivityDto> activityDtoList = new ArrayList<>();

        SbActivityExample example = new SbActivityExample();
        example.setOffset(pageInDto.getOffset());
        example.setLimit(pageInDto.getLimit());
        SbActivityExample.Criteria criteria = example.createCriteria();
        criteria.andWhetherOnlineEqualTo(YesNoEnum.YES.getCode());
        criteria.andAppIdEqualTo(Integer.valueOf(appId));
        criteria.andTypeEqualTo(ActivityType.contribute.getValue());
        List<SbActivity> activityList = sbActivityMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(activityList)) {
            return activityDtoList;
        }
        for (SbActivity activity : activityList) {
            ActivityDto dto = new ActivityDto();
            dto.setId(activity.getId());
            dto.setTitle(activity.getTitle());
            dto.setThumbCover(activity.getThumbCover());
            dto.setReplyNum(BaseUtil.instance.formatNum(activity.getReplyNum(), true));
            setActivityInfo(activity, dto);
            activityDtoList.add(dto);
        }
        //以 进行中 - 未开始 -已结束 排序
        activityDtoList.sort(Comparator.comparing(ActivityDto::getActivityStatusNumForOrder));
        return activityDtoList;
    }

    /**
     * 预热：当前时间距离活动开始时间越近，靠前
     * 进行：now 距离结束越近，靠前
     * 结束： 结束时间越近    -靠前
     * <p>
     * 第一页数据：预热 + 正在进行中 的个数最多为10
     *
     * @param pageInDto
     * @param appId
     * @return
     */
    public List<ActivityDto> getActivityListNew(PageInDto pageInDto, String appId) {

        List<ActivityDto> activityDtoList = new ArrayList<>();

        List<SbActivity> result = new ArrayList<>();

        if (pageInDto.getPageIndex() == 1) {
            //第一页数据处理
            //预热活动
            List<SbActivity> preActivityList = getSbActivity(null, pageInDto.getPageSize(), appId, "start_time asc", "ADVANCE", null);
            int preActivityListSize = preActivityList.size();
            if (CollectionUtils.isNullOrEmpty(preActivityList)) {
                //进行中
                List<SbActivity> underwayActivityList = getSbActivity(null, pageInDto.getPageSize(), appId, "end_time asc", "UNDERWAY", null);
                int underwayActivityListSize = underwayActivityList.size();
                if (CollectionUtils.isNullOrEmpty(underwayActivityList)) {
                    //进行中为空直接取结束
                    List<SbActivity> endActivityList = getSbActivity(null, pageInDto.getPageSize(), appId, "end_time desc", "END", null);
                    result.addAll(endActivityList);
                } else {
                    result.addAll(underwayActivityList);
                    if (underwayActivityListSize < pageInDto.getPageSize()) {
                        //取结束
                        List<SbActivity> endActivityList = getSbActivity(null, pageInDto.getPageSize() - underwayActivityListSize, appId, "end_time desc", "END", null);
                        result.addAll(endActivityList);
                    }
                }

            } else {
                result.addAll(preActivityList);
                if (preActivityListSize < pageInDto.getPageSize()) {
                    //进行中
                    List<SbActivity> underwayActivityList = getSbActivity(null, pageInDto.getPageSize() - preActivityListSize, appId, "end_time asc", "UNDERWAY", null);
                    int underwayActivityListSize = underwayActivityList.size();
                    if (CollectionUtils.isNullOrEmpty(underwayActivityList)) {
                        //取结束
                        List<SbActivity> endActivityList = getSbActivity(null, pageInDto.getPageSize() - preActivityListSize, appId, "end_time desc", "END", null);
                        result.addAll(endActivityList);
                    } else {
                        result.addAll(underwayActivityList);
                        if ((preActivityListSize + underwayActivityListSize) < pageInDto.getPageSize()) {
                            //取结束
                            List<SbActivity> endActivityList = getSbActivity(null, pageInDto.getPageSize() - preActivityListSize - underwayActivityListSize, appId, "end_time desc", "END", null);
                            result.addAll(endActivityList);
                        }
                    }

                }
            }
            if (CollectionUtils.isNullOrEmpty(result)) {
                return activityDtoList;
            }
            List<Integer> onePageActivityIds = new ArrayList<>();
            result.forEach(r -> onePageActivityIds.add(r.getId()));

            if (redisTemplate.hasKey("onePageActivityIds")) {
                redisTemplate.delete("onePageActivityIds");
            }

            //存储第一页ActivityId
            redisTemplate.opsForList().leftPushAll("onePageActivityIds", onePageActivityIds);

        }
        if (pageInDto.getPageIndex() > 1) {
            //直接取结束
            List<SbActivity> endActivityList = getSbActivity(pageInDto.getOffsetForActivity(), pageInDto.getLimit(), appId, "end_time desc", "END", redisTemplate.opsForList().range("onePageActivityIds", 0, pageInDto.getPageSize()));
            result.addAll(endActivityList);
        }
        if (!CollectionUtils.isNullOrEmpty(result)) {
            for (SbActivity activity : result) {
                ActivityDto dto = new ActivityDto();
                dto.setId(activity.getId());
                dto.setTitle(activity.getTitle());
                dto.setThumbCover(activity.getThumbCover());
                dto.setReplyNum(BaseUtil.instance.formatNum(activity.getReplyNum(), true));

                dto.setStartTime(activity.getStartTime());
                dto.setEndTime(activity.getEndTime());

                setActivityInfo(activity, dto);
                activityDtoList.add(dto);
            }

        }

        return activityDtoList;
    }

    public List<SbActivity> getSbActivity(Integer offSet, Integer limit, String appId, String order, String flag, List<Integer> ids) {
        SbActivityExample example = new SbActivityExample();
        Date now = new Date();
        if (null != offSet) {
            example.setOffset(offSet);
        }
        example.setLimit(limit);
        example.setOrderByClause(order);
        SbActivityExample.Criteria criteria = example.createCriteria();
        criteria.andWhetherOnlineEqualTo(YesNoEnum.YES.getCode());
        criteria.andAppIdEqualTo(Integer.valueOf(appId));
        criteria.andTypeEqualTo(ActivityType.contribute.getValue());
        if (!CollectionUtils.isNullOrEmpty(ids)) {
            criteria.andIdNotIn(ids);
        }
        if (flag.equals("ADVANCE")) {
            criteria.andPreTimeLessThanOrEqualTo(now);
            criteria.andStartTimeGreaterThan(now);
        } else if (flag.equals("UNDERWAY")) {
            criteria.andStartTimeLessThanOrEqualTo(now);
            criteria.andEndTimeGreaterThan(now);
        } else if (flag.equals("END")) {
            criteria.andEndTimeLessThanOrEqualTo(now);
        }
        List<SbActivity> activityList = sbActivityMapper.selectByExample(example);
        return activityList;
    }

    public ActivityDto detailTop(Integer id, String appId) {
        SbActivityExample example = new SbActivityExample();
        example.setLimit(1);
        SbActivityExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andAppIdEqualTo(Integer.valueOf(appId));
        criteria.andWhetherOnlineEqualTo(YesNoEnum.YES.getCode());
        List<SbActivity> sbActivities = sbActivityMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isNullOrEmpty(sbActivities)) {
            return null;
        }
        SbTopicExample topicExample = new SbTopicExample();
        SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
        topicCriteria.andAppIdEqualTo(Integer.valueOf(appId));
        topicCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        topicCriteria.andAuditStatusIn(auditStatusList);
        topicCriteria.andActivityIdEqualTo(id);
        Long total = sbTopicMapper.countByExample(topicExample);
        ActivityDto dto = new ActivityDto();
        BeanUtils.copyProperties(sbActivities.get(0), dto);
        //#号补全
        dto.setTopic("#" + sbActivities.get(0).getTopic() + "#");
        dto.setTopicTotal(total.intValue());
        dto.setReplyNum(BaseUtil.instance.formatNum(sbActivities.get(0).getReplyNum(), true));
        setActivityInfo(sbActivities.get(0), dto);
        DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        dto.setHasSignUp(hasSignUp(user != null ? user.getUid() : null, id));
        return dto;


    }

    public List<TopicDto> detailMiddle(PageInDto pageInDto, Integer activityId, String appId) {

        List<TopicDto> topicDtoList = new ArrayList<>();

        SbActivity sbActivity = sbActivityMapper.selectByPrimaryKey(activityId);
        if (null == sbActivity || sbActivity.getWhetherOnline().equals(YesNoEnum.NO.getCode())) {
            return topicDtoList;
        }

        List<SbTopic> resultTopiclist = new ArrayList<>();

        //1,2,3名的topic
        List<SbTopic> topicList = new ArrayList<>();
        //1,2,3名的topicId
        List<String> sbTopicIdOrder = new ArrayList<>();
        List<Integer> sbTopicIdOrderIn = new ArrayList<>();
        //第一页的topicId
        List<Integer> sbTopicIdFirstPage = new ArrayList<>();

        DsmConfigExample configExample = new DsmConfigExample();
        configExample.setLimit(1);
        DsmConfigExample.Criteria configCriteria = configExample.createCriteria();
        configCriteria.andCodeEqualTo("threshold");
        List<DsmConfig> dsmConfigs = dsmConfigMapper.selectByExample(configExample);
        DsmConfig dsmConfig = dsmConfigs.get(0);
        //关注数阀值
        String threshold = dsmConfig.getValue();
        Integer thresholdInt = Integer.valueOf(threshold);

        SbTopicExample topicExample = new SbTopicExample();
        topicExample.setLimit(3);
        topicExample.setOrderByClause("follow_num desc");
        SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
        topicCriteria.andAppIdEqualTo(Integer.valueOf(appId));
        topicCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        topicCriteria.andAuditStatusIn(auditStatusList);
        topicCriteria.andActivityIdEqualTo(activityId);
        List<SbTopic> sbTopicList = sbTopicMapper.selectByExample(topicExample);
        if (CollectionUtils.isNullOrEmpty(sbTopicList)) {
            return topicDtoList;
        }

        for (SbTopic sbTopic : sbTopicList) {
            if (sbTopic.getFollowNum() > thresholdInt) {
                sbTopicIdOrder.add(String.valueOf(sbTopic.getId()));
                sbTopicIdOrderIn.add(sbTopic.getId());
                topicList.add(sbTopic);
            }
        }

        Date now = new Date();
        //第一页数据处理
        if (CollectionUtils.isNullOrEmpty(topicList)) {
            //没有超过阀值，全部按发布时间排序
            if (pageInDto.getPageIndex() == 1) {

                List<SbTopic> sbTopicListOne = new ArrayList<>();
                // 已结束的活动显示 6 个topic 否则显示 5个
                if (now.after(sbActivity.getEndTime())) {
                    sbTopicListOne = getSbTopicList(null, 6, appId, activityId, null);
                } else {
                    sbTopicListOne = getSbTopicList(null, 5, appId, activityId, null);
                }

                for (SbTopic topic : sbTopicListOne) {
                    sbTopicIdFirstPage.add(topic.getId());
                }

                if (redisTemplate.hasKey("sbTopicIdFirstPage_" + activityId)) {
                    redisTemplate.delete("sbTopicIdFirstPage_" + activityId);
                }

                //存储第一页topicId
                redisTemplate.opsForList().leftPushAll("sbTopicIdFirstPage_" + activityId, sbTopicIdFirstPage);
                resultTopiclist.addAll(sbTopicListOne);
            }
        } else {
            if (pageInDto.getPageIndex() == 1) {
                //超过阀值的topic集合
                resultTopiclist.addAll(topicList);
                sbTopicIdFirstPage.addAll(sbTopicIdOrderIn);

                List<SbTopic> sbTopicListOne = new ArrayList<>();

                // 已结束的活动显示 6 个topic 否则显示 5个
                if (now.after(sbActivity.getEndTime())) {
                    sbTopicListOne = getSbTopicList(null, 6 - topicList.size(), appId, activityId, sbTopicIdOrderIn);
                } else {
                    sbTopicListOne = getSbTopicList(null, 5 - topicList.size(), appId, activityId, sbTopicIdOrderIn);
                }

                for (SbTopic topic : sbTopicListOne) {
                    sbTopicIdFirstPage.add(topic.getId());
                }
                if (redisTemplate.hasKey("sbTopicIdFirstPage_" + activityId)) {
                    redisTemplate.delete("sbTopicIdFirstPage_" + activityId);
                }
                //存储第一页topicId
                redisTemplate.opsForList().leftPushAll("sbTopicIdFirstPage_" + activityId, sbTopicIdFirstPage);
                resultTopiclist.addAll(sbTopicListOne);
            }
        }
        //剩余页数据
        if (pageInDto.getPageIndex() > 1) {
            //清空sbTopicIdOrderIn 防止第二页标识1，2，3
            sbTopicIdOrderIn.clear();
            List<SbTopic> sbTopicListOther = getSbTopicList(pageInDto.getOffsetForActivity(), pageInDto.getLimit(), appId, activityId, redisTemplate.opsForList().range("sbTopicIdFirstPage_" + activityId, 0, 5));
            resultTopiclist.addAll(sbTopicListOther);

        }

        //dto对象转换
        if (!CollectionUtils.isNullOrEmpty(resultTopiclist)) {

            for (int i = 0; i < resultTopiclist.size(); i++) {
               /* TopicDto dto = new TopicDto();
                if (!CollectionUtils.isNullOrEmpty(sbTopicIdOrderIn)) {
                    if (sbTopicIdOrderIn.size() > i) {
                        dto.setActivityDetailOrder(i + 1);
                    }
                }
                dto.setId(resultTopiclist.get(i).getId());
                dto.setTitle(resultTopiclist.get(i).getTitle());
                SbVideo video = videoMapper.selectByPrimaryKey(resultTopiclist.get(i).getVideoId());
                VideoDto videoDto = new VideoDto();
                videoDto.setId(video.getId());
                videoDto.setCover(video.getCover());
                dto.setVideo(videoDto);
                topicDtoList.add(dto);*/
                TopicDto dto = new TopicDto();
                if (!CollectionUtils.isNullOrEmpty(sbTopicIdOrderIn)) {
                    if (sbTopicIdOrderIn.size() > i) {
                        dto.setActivityDetailOrder(i + 1);
                    }
                }
                dto.setId(resultTopiclist.get(i).getId());
                dto.setTitle(resultTopiclist.get(i).getTitle());
                SbVideo video = videoMapper.selectByPrimaryKey(resultTopiclist.get(i).getVideoId());
                dto.setImage(video.getCover());
                dto.setDuration(video.getDuration());
                topicDtoList.add(dto);
            }

        }

        return topicDtoList;
    }

    public void setActivityInfo(SbActivity activity, ActivityDto dto) {
        //是否预热
        Date preTime = activity.getPreTime();
        Date now = new Date();
        if (null != preTime) {
            dto.setPreheatStatus(now.getTime() > preTime.getTime() ? YesNoEnum.NO.getCode() : YesNoEnum.YES.getCode());
        }
        Date startTime = activity.getStartTime();
        Date endTime = activity.getEndTime();
        if (null != preTime && startTime.getTime() > now.getTime() && preTime.getTime() <= now.getTime()) {
            dto.setActivityStatus(ActivityStatus.preheating.getValue());
            dto.setActivityStatusText("即将开始");
            dto.setActivityStatusNumForOrder(2);
            dto.setRestOfTime(DateUtil.getRestTime(startTime, now));
        } else if (now.getTime() >= endTime.getTime()) {
            dto.setActivityStatus(ActivityStatus.ended.getValue());
            dto.setActivityStatusText("已结束");
            dto.setActivityStatusNumForOrder(3);
        } else if (startTime.getTime() <= now.getTime() && now.getTime() < endTime.getTime()) {
            dto.setActivityStatus(ActivityStatus.ongoing.getValue());
            dto.setActivityStatusText("进行中");
            dto.setActivityStatusNumForOrder(1);
            //剩余时间
            dto.setRestOfTime(DateUtil.getRestTime(endTime, now));
        }
    }

    private List<SbTopic> getSbTopicList(Integer offSet, Integer limit, String appId, Integer activityId, List<Integer> ids) {
        SbTopicExample topicExample = new SbTopicExample();
        topicExample.setLimit(limit);
        if (null != offSet) {
            topicExample.setOffset(offSet);
        }
        topicExample.setOrderByClause("publish_time desc");
        SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
        topicCriteria.andAppIdEqualTo(Integer.valueOf(appId));
        topicCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        topicCriteria.andAuditStatusIn(auditStatusList);
        topicCriteria.andActivityIdEqualTo(activityId);
        if (!CollectionUtils.isNullOrEmpty(ids)) {
           /* List<Integer> list = new ArrayList<>(ids.size());
            org.apache.commons.collections.CollectionUtils.collect(ids,
                    new Transformer() {
                        @Override
                        public java.lang.Object transform(java.lang.Object input) {
                            return new Integer((String) input);
                        }
                    }, list);
            topicCriteria.andIdNotIn(list);*/
            topicCriteria.andIdNotIn(ids);
        }
        List<SbTopic> sbTopicList = sbTopicMapper.selectByExample(topicExample);
        return sbTopicList;
    }

    /**
     * 活动是否进行中
     *
     * @param id
     * @return
     */
    public Boolean isUnderway(Integer id) {
        SbActivity sbActivity = sbActivityMapper.selectByPrimaryKey(id);

        if (sbActivity == null) {
            return false;
        }
        if (YesNoEnum.NO.getCode().equals(sbActivity.getWhetherOnline())) {
            return false;
        }
        Date currDate = new Date();
        if (sbActivity.getStartTime().after(currDate)) {
            return false;
        }
        if (currDate.after(sbActivity.getEndTime())) {
            return false;
        }
        return true;
    }

    public Response<ShareActivityDto> getShareDetail(Integer activityId, Integer appId) {

        ActivityDto activityDto = this.detailTop(activityId, appId + "");
        if (activityDto == null) {
            return new Response<>(RespStatusEnum.ACTIVITY_INVALID);
        }

        ShareActivityDto shareActivityDto = new ShareActivityDto();
        shareActivityDto.setActivity(activityDto);

        List<TopicDto> topicDtoListMoreOne = this.detailMiddle(new PageInDto(1, 14), activityId, appId + "");
        if (topicDtoListMoreOne.size() == 15) {
            List<TopicDto> topicDtoList = topicDtoListMoreOne.stream().limit(14).collect(Collectors.toList());
            shareActivityDto.setTopicList(topicDtoList);
            shareActivityDto.setHasMoreTopic(YesNoEnum.YES.getCode());
        } else {
            shareActivityDto.setTopicList(topicDtoListMoreOne);
            shareActivityDto.setHasMoreTopic(YesNoEnum.NO.getCode());
        }

        List<ActivityDto> activityListMoreOne = this.getActivityList(new PageInDto(1, 5), appId + "");
        if (activityListMoreOne.size() == 5) {
            List<ActivityDto> activityList = activityListMoreOne.stream().limit(4).collect(Collectors.toList());
            shareActivityDto.setActivityList(activityList);
            shareActivityDto.setHasMoreActivity(YesNoEnum.YES.getCode());
        } else {
            shareActivityDto.setActivityList(activityListMoreOne);
            shareActivityDto.setHasMoreActivity(YesNoEnum.NO.getCode());
        }

        shareActivityDto.setAdList(topicListService.getAdList(appId, null));

        return new Response<>(shareActivityDto);

    }

    public Response<String> signUpActivity(Integer userId, Integer activityId) {
        Response<String> resp = new Response<>();
        SbActivity sbActivity = sbActivityMapper.selectByPrimaryKey(activityId);
        if (sbActivity == null || sbActivity.getWhetherOnline().intValue() != Whether.yes.getValue()) {
            resp.setRespStatus(RespStatusEnum.ACTIVITY_INVALID);
            return resp;
        }
        Date current = new Date();
        if (current.after(sbActivity.getStartTime()) || current.before(sbActivity.getPreTime())) {
            resp.setRespStatus(RespStatusEnum.ACTIVITY_NOT_ALLOW_SIGN_UP);
            return resp;
        }
        SbUserSignUpExample example = new SbUserSignUpExample();
        example.setLimit(1);
        SbUserSignUpExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(userId);
        criteria.andActivityIdEqualTo(activityId);
        if (signUpMapper.countByExample(example) > 0) {
            resp.setRespStatus(RespStatusEnum.ACTIVITY_ALREADY_SIGN_UP);
            return resp;
        }
        SbUserSignUp signUp = new SbUserSignUp();
        signUp.setUid(userId);
        signUp.setActivityId(activityId);
        signUpMapper.insert(signUp);
        return resp;
    }

    private Integer hasSignUp(Integer userId, Integer activityId) {
        if (userId == null || activityId == null) {
            return Whether.no.getValue();
        }
        SbUserSignUpExample example = new SbUserSignUpExample();
        example.setLimit(1);
        SbUserSignUpExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(userId);
        criteria.andActivityIdEqualTo(activityId);
        return signUpMapper.countByExample(example) > 0 ? 1 : 0;
    }

    public Response sendMsg(Integer activityId, String mobile) throws ClientException {

        if (!RegexUtil.isMobile(mobile)) {
            return new Response(RespStatusEnum.MOBILE_ERROR);
        }
        SbActivity sbActivity = sbActivityMapper.selectByPrimaryKey(activityId);
        if (sbActivity == null || sbActivity.getWhetherOnline().intValue() != Whether.yes.getValue() || !sbActivity.getType().equals(ActivityType.awards.getValue())) {
            return new Response(RespStatusEnum.ACTIVITY_INVALID);
        }
        Date now = new Date();
        if (!(now.after(sbActivity.getStartTime()) && now.before(sbActivity.getEndTime()))) {
            return new Response(RespStatusEnum.ACTIVITY_NOT_ALLOW_SIGN_UP);
        }
        //验证手机号绑定
        DsmUserExample userExample = new DsmUserExample();
        userExample.setLimit(1);
        userExample.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(YesNoEnum.YES.getCode());
        List<DsmUser> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isNullOrEmpty(users)) {
            return new Response(RespStatusEnum.MOBILE_NOT_BIND);
        }
        //验证是否参加过活动
        DsmUser user = users.get(0);
        Integer hasSignUp = hasSignUp(user.getUid(), activityId);
        if (hasSignUp > 0) {
            return new Response(RespStatusEnum.ACTIVITY_ALREADY_SIGN_UP);
        }

        String yyyyMMdd = DateFormatUtils.format(now, "yyyyMMdd");
        String sendNumStr = stringRedisTemplate.opsForValue().get(Constants.USER_AWARDS_ENROL_SEND_SMS_NUM_KEY + activityId + user.getUid() + yyyyMMdd);
        Integer sendNum = sendNumStr == null ? 0 : Integer.valueOf(sendNumStr);
        if (sendNum > 5) {
            return new Response(RespStatusEnum.SEND_SMS_LIMIT);
        }
        //发送短信
        String sixCode = (int) ((Math.random() * 9 + 1) * 100000) + "";
        if (aliyunSmsProperties.getSmsIsSend()) {
            Map<String, String> map = new HashMap<>();
            map.put("code", sixCode);
            SendSmsUtil.send(aliyunSmsProperties.getSmsSign(), aliyunSmsProperties.getSmsTemplateBinding(), map, mobile);
        } else {
            sixCode = "123456";
            log.info("此环境配置不发短信 默认使用使用{}", sixCode);
        }
        stringRedisTemplate.opsForValue().set(Constants.USER_AWARDS_ENROL_SEND_SMS_KEY + activityId + user.getUid() + mobile, sixCode, 10, TimeUnit.MINUTES);
        //存储次数
        stringRedisTemplate.opsForValue().set(Constants.USER_AWARDS_ENROL_SEND_SMS_NUM_KEY + activityId + user.getUid() + yyyyMMdd, ++sendNum + "", 1, TimeUnit.DAYS);

        return new Response();
    }

    public Response enrol(Integer activityId, String mobile, String code) {
        if (!RegexUtil.isMobile(mobile)) {
            return new Response(RespStatusEnum.MOBILE_ERROR);
        }
        DsmUserExample userExample = new DsmUserExample();
        userExample.setLimit(1);
        userExample.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(YesNoEnum.YES.getCode());
        List<DsmUser> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isNullOrEmpty(users)) {
            return new Response(RespStatusEnum.MOBILE_NOT_BIND);
        }
        Integer uid = users.get(0).getUid();
        String rightCode = stringRedisTemplate.opsForValue().get(Constants.USER_AWARDS_ENROL_SEND_SMS_KEY + activityId + uid + mobile);
        if (StringUtils.isBlank(rightCode) || !rightCode.equals(code)) {
            return new Response(RespStatusEnum.SMS_ERROR);
        }

        SbActivity sbActivity = sbActivityMapper.selectByPrimaryKey(activityId);
        if (sbActivity == null || sbActivity.getWhetherOnline().intValue() != Whether.yes.getValue() || !sbActivity.getType().equals(ActivityType.awards.getValue())) {
            return new Response(RespStatusEnum.ACTIVITY_INVALID);
        }
        Date now = new Date();
        if (!(now.after(sbActivity.getStartTime()) && now.before(sbActivity.getEndTime()))) {
            return new Response(RespStatusEnum.ACTIVITY_NOT_ALLOW_SIGN_UP);
        }

        Integer hasSignUp = hasSignUp(uid, activityId);
        if (hasSignUp > 0) {
            return new Response(RespStatusEnum.ACTIVITY_ALREADY_SIGN_UP);
        }

        SbUserSignUp userSignUp = new SbUserSignUp();
        userSignUp.setActivityId(activityId);
        userSignUp.setUid(uid);
        userSignUp.setMobile(mobile);
        userSignUp.setNickname(users.get(0).getNickName());
        Long increment = stringRedisTemplate.opsForValue().increment(awardsActivityIncrement + activityId, 1);
        userSignUp.setNum(increment.intValue());
        if (String.valueOf(increment).endsWith("99")) {
            int index = new Random().nextInt(bonus.size());
            userSignUp.setBonus(bonus.get(index));
        }
        signUpMapper.insertSelective(userSignUp);
        KeyValue keyValue = new KeyValue();
        keyValue.setValue(userSignUp.getBonus() == null ? "-1" : String.valueOf(userSignUp.getBonus()));
        return new Response(keyValue);

    }

    public Response enrolNew(Integer activityId, Integer uid, String did) {

        SbActivity sbActivity = sbActivityMapper.selectByPrimaryKey(activityId);
        if (sbActivity == null || sbActivity.getWhetherOnline().intValue() != Whether.yes.getValue() || !sbActivity.getType().equals(ActivityType.awards.getValue())) {
            return new Response(RespStatusEnum.ACTIVITY_INVALID);
        }
        Date now = new Date();
        if (!(now.after(sbActivity.getStartTime()) && now.before(sbActivity.getEndTime()))) {
            return new Response(RespStatusEnum.ACTIVITY_NOT_ALLOW_SIGN_UP);
        }
        DsmUser dsmUser = userMapper.selectByPrimaryKey(uid);
        String mobile = dsmUser.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return new Response(RespStatusEnum.MOBILE_NOT_BIND);
        }

        SbUserSignUpExample example = new SbUserSignUpExample();
        example.setLimit(1);
        example.createCriteria().andUidEqualTo(uid).andActivityIdEqualTo(activityId);
        example.or().andDidEqualTo(did).andActivityIdEqualTo(activityId);
        example.or().andMobileEqualTo(mobile).andActivityIdEqualTo(activityId);
        List<SbUserSignUp> userSignUps = signUpMapper.selectByExample(example);
        if (!CollectionUtils.isNullOrEmpty(userSignUps)) {
            KeyValue keyValue = new KeyValue();
            keyValue.setKey("-2");
            keyValue.setValue(userSignUps.get(0).getBonus() == null ? "-1" : String.valueOf(userSignUps.get(0).getBonus()));
            return new Response(keyValue);
        }

        SbUserSignUp userSignUp = new SbUserSignUp();
        userSignUp.setActivityId(activityId);
        userSignUp.setUid(uid);
        userSignUp.setMobile(mobile);
        userSignUp.setNickname(dsmUser.getNickName());
        userSignUp.setDid(did);
        Long increment = stringRedisTemplate.opsForValue().increment(awardsActivityIncrement + activityId, 1);
        userSignUp.setNum(increment.intValue());
        if (String.valueOf(increment).endsWith("99")) {
            int index = new Random().nextInt(bonus.size());
            userSignUp.setBonus(bonus.get(index));
        }
        signUpMapper.insertSelective(userSignUp);
        KeyValue keyValue = new KeyValue();
        keyValue.setKey(userSignUp.getBonus() == null ? "未中奖" : "中奖");
        keyValue.setValue(userSignUp.getBonus() == null ? "-1" : String.valueOf(userSignUp.getBonus()));
        return new Response(keyValue);
    }

    public Response isEnrol(Integer activityId, Integer uid, String did) {

        DsmUser dsmUser = userMapper.selectByPrimaryKey(uid);
        String mobile = dsmUser.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return new Response(RespStatusEnum.MOBILE_NOT_BIND);
        }
        SbUserSignUpExample example = new SbUserSignUpExample();
        example.setLimit(1);
        example.createCriteria().andUidEqualTo(uid).andActivityIdEqualTo(activityId);
        example.or().andDidEqualTo(did).andActivityIdEqualTo(activityId);
        example.or().andMobileEqualTo(mobile).andActivityIdEqualTo(activityId);
        KeyValue keyValue = new KeyValue();
        keyValue.setValue(signUpMapper.countByExample(example) > 0 ? "1" : "0");
        if (keyValue.getValue().equals("1")) {
            SbUserSignUp userSignUp = signUpMapper.selectByExample(example).get(0);
            keyValue.setKey(userSignUp.getBonus() == null ? "-1" : String.valueOf(userSignUp.getBonus()));
        }
        return new Response(keyValue);
    }

    public Response isShowAward() {
        ShowAward showAward = new ShowAward();
        showAward.setIcon("http://small-bronze.oss-cn-shanghai.aliyuncs.com/image/activity/icon/2017/11/13/20171114101034.png");
        showAward.setWebUrl(webUrlForAward);
        SbActivity sbActivity = sbActivityMapper.selectByPrimaryKey(10001);
        if (sbActivity == null || sbActivity.getWhetherOnline().intValue() != Whether.yes.getValue() || !sbActivity.getType().equals(ActivityType.awards.getValue())) {
            showAward.setIsShow(YesNoEnum.NO.getCode());
            return new Response(showAward);
        }
        Date now = new Date();
        if (!(now.after(sbActivity.getStartTime()) && now.before(sbActivity.getEndTime()))) {
            showAward.setIsShow(YesNoEnum.NO.getCode());
            return new Response(showAward);
        }
        showAward.setIsShow(YesNoEnum.YES.getCode());
        return new Response(showAward);
    }


    public Response<BaseListDto<Map.Entry<String, String>>> getAwardList(Integer activityId) {
        Response<BaseListDto<Map.Entry<String, String>>> resp = new Response<>();
        BaseListDto<Map.Entry<String, String>> dto = new BaseListDto<>();
        SbUserSignUpExample example = new SbUserSignUpExample();
        example.setLimit(10);
        example.createCriteria().andActivityIdEqualTo(activityId).andBonusIn(bonus);
        List<SbUserSignUp> signUps = signUpMapper.selectByExample(example);
        List<Map.Entry<String, String>> list = new ArrayList<>();
        for (SbUserSignUp signUp : signUps) {
            AttributeEntry<String, String> node = new AttributeEntry<>(signUp.getNickname(),
                    luckyDrawData.instance.awardMap.get(signUp.getBonus()));
            list.add(node);
        }
        if (list.size() < 10) {
            for (int i = 10 - list.size(); i > 0; i--) {
                list.add(appendRandom(list));
            }
        }
        dto.setList(list);
        resp.setResult(dto);
        return resp;
    }

    private Random random = new Random();

    private Map.Entry<String, String> appendRandom(List<Map.Entry<String, String>> list) {
        AttributeEntry<String, Integer> user = luckyDrawData.instance.userList
                .get(random.nextInt(luckyDrawData.instance.userList.size()));
        AttributeEntry<String, String> node = new AttributeEntry<>(user.getKey(),
                luckyDrawData.instance.awardMap.get(user.getValue()));
        if (list.contains(node)) {
            return appendRandom(list);
        }
        return node;
    }
}
