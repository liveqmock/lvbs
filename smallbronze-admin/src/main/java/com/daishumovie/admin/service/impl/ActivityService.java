package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.ActivityDto;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IActivityService;
import com.daishumovie.admin.service.ITopicService;
import com.daishumovie.admin.service.IUserService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.mapper.smallbronze.DsmConfigMapper;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * Created by feiFan.gou on 2017/10/20 11:47.
 */
@Service
public class ActivityService extends BaseService implements IActivityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private SbActivityMapper activityMapper;
    @Autowired
    private SbTopicMapper topicMapper;
    private @Autowired
    IAdminService adminService;
    private @Autowired
    IUserService userService;
    private @Autowired
    ITopicService topicService;
    @Autowired
    private DsmConfigMapper configMapper;
    private static final int default_threshold = 1000;

    public static final String java_start_time = "2017-01-01 00:00:00";

    @Override
    public ReturnDto<ActivityDto> paginate(ParamDto param, String title, String topic, Integer status, Integer whetherOnline, String preTime, String startTime) {

        try {
            SbActivityExample example = condition(title, topic, status, whetherOnline, preTime, startTime);
            Long total = activityMapper.countByExample(example);
            List<ActivityDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setOffset(param.offset());
                example.setLimit(param.limit());
                example.setOrderByClause("start_time desc");
                List<SbActivity> activities = activityMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(activities)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    activities.forEach(activity -> {
                        ActivityDto dto = new ActivityDto();
                        BeanUtils.copyProperties(activity, dto);
                        dtoList.add(dto);
                        if (null != activity.getOperatorId()) {
                            adminIdSet.add(Long.valueOf(activity.getOperatorId()));
                        }
                        if (null != activity.getModifier()) {
                            adminIdSet.add(Long.valueOf(activity.getModifier()));
                        }
                    });
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    dtoList.forEach(dto -> {
                        Integer operator = dto.getOperatorId();
                        if (null != operator && adminNameMap.containsKey(operator)) {
                            dto.setOperatorName(adminNameMap.get(operator));
                        }
                        Integer modifier = dto.getModifier();
                        if (null != modifier && adminNameMap.containsKey(modifier)) {
                            dto.setModifierName(adminNameMap.get(modifier));
                        }
                        topicCount(dto);
                    });
                }
            }
            Page<ActivityDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("activity paginate exception ===>\r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    public void save(SbActivity activity, Integer operatorId) {

        validate(activity, false);
        try {
            //1.封面,缩略图上传阿里云
            handelCover(activity);
            handelThumb(activity);
            //2.数据保存
            activity.setOperatorId(operatorId);
            activity.setCreateTime(new Date());
            activity.setModifier(operatorId);
            activity.setModifyTime(activity.getCreateTime());
            activity.setWhetherOnline(Whether.yes.getValue());
            activity.setType(ActivityType.contribute.getValue());//投稿
            activity.setReplyNum(0);
            activity.setAppId(Configuration.current_app.getId());
            activityMapper.insert(activity);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("activity", "save", e);
            throw new ResultException();
        }
    }

    @Override
    public void update(SbActivity activity, Integer operatorId) {

        validate(activity, true);

        try {
            //1.封面,缩略图上传阿里云
            handelCover(activity);
            handelThumb(activity);
            //2.数据跟新
            activity.setModifier(operatorId);
            activity.setModifyTime(new Date());
            activityMapper.updateByPrimaryKey(activity);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("activity", "update", e);
            throw new ResultException();
        }
    }

    @Override
    public void updateReplyNum(Integer id, Integer replayNum) {
        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        SbActivity record = new SbActivity();
        record.setId(id);
        record.setReplyNum(replayNum);
        activityMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public void offline(Integer id, Integer operatorId) {

        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbActivity activity = activityMapper.selectByPrimaryKey(id);
            if (null == activity) {
                throw new ResultException("活动不存在");
            }
            activity.setWhetherOnline(Whether.no.getValue());
            activity.setModifier(operatorId);
            activity.setModifyTime(new Date());
            activityMapper.updateByPrimaryKeySelective(activity);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("activity", "offline", e);
            throw new ResultException();
        }
    }

    @Override
    public List<SbTopicDto> topicListByActivityId(Integer activityId) {

        Integer threshold = default_threshold;
        { //获取默认阈值
            DsmConfigExample configExample = new DsmConfigExample();
            configExample.createCriteria().andCodeEqualTo("threshold");
            List<DsmConfig> configs = configMapper.selectByExample(configExample);
            if (!CollectionUtils.isNullOrEmpty(configs)) {
                DsmConfig config = configs.get(0);
                if (null != config) {
                    if (StringUtil.isNotEmpty(config.getValue())) {
                        try {
                            threshold = Integer.parseInt(StringUtil.trim(config.getValue()));
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        }
        SbTopicExample example = new SbTopicExample();
        example.createCriteria()
                .andActivityIdEqualTo(activityId)
                .andPraiseNumGreaterThanOrEqualTo(threshold)
                .andAuditStatusEqualTo(AuditStatus.MAN_AUDIT_PASS.getValue());
        example.setOrderByClause("follow_num desc,publish_time desc");
        List<SbTopic> praiseList = topicMapper.selectByExample(example);
        List<Integer> praiseIdList = Lists.newArrayList();
        if (!CollectionUtils.isNullOrEmpty(praiseList)) {
            praiseList.forEach(topic->praiseIdList.add(topic.getId()));
        } else {
            praiseList = Lists.newArrayList();
        }
        example.clear();
        SbTopicExample.Criteria criteria = example.createCriteria();
        criteria.andAuditStatusEqualTo(AuditStatus.MAN_AUDIT_PASS.getValue())
                .andActivityIdEqualTo(activityId);
        if (!praiseIdList.isEmpty()) {
            example.createCriteria().andIdNotIn(praiseIdList);
        }
        example.setOrderByClause("publish_time desc");
        List<SbTopic> topics = topicMapper.selectByExample(example);
        List<SbTopicDto> dtoList = Lists.newArrayList();
        praiseList.addAll(topics);
        if (!CollectionUtils.isNullOrEmpty(praiseList)) {
            Set<Integer> userIdSet = Sets.newHashSet();
            Set<Integer> videoIdSet = Sets.newHashSet();
            praiseList.forEach(topic -> {
                if (null != topic.getUid()) {
                    userIdSet.add(topic.getUid());
                }
                if (null != topic.getVideoId()) {
                    videoIdSet.add(topic.getVideoId());
                }
                SbTopicDto dto = new SbTopicDto();
                BeanUtils.copyProperties(topic, dto);
                dtoList.add(dto);
            });
            Map<Integer, DsmUser> userMap = userService.userMapByUid(userIdSet);
            Map<Integer, SbVideo> videoMap = topicService.videoInfoMap(videoIdSet);
            dtoList.forEach(dto -> topicInfo(dto, userMap, videoMap));
        }
        return dtoList;
    }

    public Map<Integer, String> getActivityMap(Set<Integer> actIdSet) {
        Map<Integer, String> map = Maps.newHashMap();
        if (actIdSet.size() <= 0) {
            return map;
        }
        SbActivityExample example = new SbActivityExample();
        SbActivityExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(new ArrayList<>(actIdSet));
        List<SbActivity> list = activityMapper.selectByExample(example);
        for (SbActivity activity : list) {
            if (!map.containsKey(activity.getId()))
                map.put(activity.getId(), activity.getTitle());
        }
        return map;
    }

    @Override
    public SbTopicDto topicByActivityId(Integer topicId, Integer activityId) {

        SbTopicDto dto = new SbTopicDto();
        SbTopic topic = validate(topicId, activityId);
        BeanUtils.copyProperties(topic, dto);
        Map<Integer, DsmUser> userMap = userService.userMapByUid(Sets.newHashSet(topic.getUid()));
        Map<Integer, SbVideo> videoMap = topicService.videoInfoMap(Sets.newHashSet(topic.getVideoId()));
        topicInfo(dto, userMap, videoMap);
        return dto;
    }

    @Override
    public void putVideo(Integer topicId, Integer activityId) {

        try {
            SbTopic topic = validate(topicId, activityId);
            topic.setActivityId(activityId);
            topic.setModifyTime(new Date());
            topicMapper.updateByPrimaryKeySelective(topic);
        } catch (Exception e) {
            printException("activity", "putVideo", e);
            throw new ResultException();
        }
    }

    public void updateLikeCount(Integer topicId, Integer likeCount) {

        if (null == topicId) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
            if (null == topic) {
                throw new ResultException("视频不存在");
            }
            topic.setFollowNum(likeCount);
            topic.setModifyTime(new Date());
            topicMapper.updateByPrimaryKeySelective(topic);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("activity", "updateLikeCount", e);
            throw new ResultException();
        }

    }

    @Override
    public void removeVideoFromActivity(Integer topicId) {

        if (null == topicId) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
            if (null == topic) {
                throw new ResultException("视频不存在");
            }
            topic.setActivityId(null);
            topic.setModifyTime(new Date());
            topicMapper.updateByPrimaryKey(topic);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("activity", "removeVideoFromActivity", e);
            throw new ResultException();
        }
    }

    @Override
    public SbActivity getActivityById(Integer id) {
        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        return activityMapper.selectByPrimaryKey(id);
    }

    private SbTopic validate(Integer topicId, Integer activityId) {

        if (null == topicId || null == activityId) {
            throw new ResultException(ErrMsg.param_error);
        }
        SbActivity activity = activityMapper.selectByPrimaryKey(activityId);
        if (null == activity) {
            throw new ResultException("当前活动不存在");
        }
        if (Objects.equals(activity.getWhetherOnline(), Whether.no.getValue())) {
            throw new ResultException("当前活动已下线");
        }
        if (!Objects.equals(activity.getStatus(), ActivityStatus.ongoing.getValue())) {
            throw new ResultException("当前活动未在进行中");
        }
        SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
        if (null == topic) {
            throw new ResultException("视频不存在");
        }
        if (null != topic.getActivityId()) {
            if (Objects.equals(topic.getActivityId(), activityId)) {
                throw new ResultException("该视频已被添加，请重新选择");
            } else {
                throw new ResultException("该视频参加其他活动，请重新选择");
            }
        }
        Integer status = topic.getStatus();
        if (Objects.equals(status, TopicStatus.publishing.getValue())) {
            throw new ResultException("该视频未发布");
        }
        if (Objects.equals(status, TopicStatus.offline.getValue())) {
            throw new ResultException("该视频已下线");
        }
        if (topic.getPublishTime().before(activity.getStartTime())) {
            throw new ResultException("该视频不符合添加规则，请重新选择");
        }
        return topic;
    }

    private SbActivityExample condition(String title, String topic, Integer status, Integer whetherOnline, String preTime, String startTime) {

        SbActivityExample example = new SbActivityExample();
        SbActivityExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(title)) {
            criteria.andTitleLike(StringUtil.sqlLike(title));
        }
        if (StringUtil.isNotEmpty(topic)) {
            criteria.andTopicLike(StringUtil.sqlLike(topic));
        }
        if (StringUtil.isNotEmpty(preTime)) {
            criteria.andPreTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(preTime));
        }
        if (StringUtil.isNotEmpty(startTime)) {
            criteria.andStartTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(startTime));
        }
        if (null != status) {
            criteria.andStatusEqualTo(status);
        }

        if (null != whetherOnline) {
            criteria.andWhetherOnlineEqualTo(whetherOnline);
        }
        return example;
    }

    @Override
    protected void specificVerify(Object model, boolean isUpdate) {

        SbActivity activity = (SbActivity) model;
        try {
            if (activity.getPreTime().equals(DateUtil.BASIC.parse(java_start_time))) {
                activity.setPreTime(null);
            }
        } catch (ParseException e) {
            activity.setPreTime(null);
        }
        if (isUpdate) {
            if (null == activity.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbActivity dbActivity = activityMapper.selectByPrimaryKey(activity.getId());
            if (null == dbActivity) {
                throw new ResultException("活动不存在");
            }
            activity.setType(dbActivity.getType());
            activity.setWhetherOnline(dbActivity.getWhetherOnline());
            activity.setAppId(dbActivity.getAppId());
            activity.setOperatorId(dbActivity.getOperatorId());
            activity.setCreateTime(dbActivity.getCreateTime());
            activity.setReplyNum(dbActivity.getReplyNum());
        }

        if (StringUtil.isEmpty(activity.getTitle())) {
            throw new ResultException("活动标题不能为空");
        }
        if (StringUtil.isEmpty(activity.getTopic())) {
            throw new ResultException("活动话题不能为空");
        }
        if (StringUtil.isEmpty(activity.getDescription())) {
            throw new ResultException("活动描述不能为空");
        }
        if (StringUtil.isEmpty(activity.getInstruction())) {
            throw new ResultException("活动规则不能为空");
        }
        if (StringUtil.isEmpty(activity.getCover())) {
            throw new ResultException("活动封面不能为空");
        }
        if (StringUtil.isEmpty(activity.getThumbCover())) {
            throw new ResultException("活动缩略图不能为空");
        }
        Date startTime = activity.getStartTime();
        Date endTime = activity.getEndTime();
        Date preTime = activity.getPreTime();
        Date now = new Date();

        if (null == startTime) {
            throw new ResultException("活动开始时间不能为空");
        }
        if (null == endTime) {
            throw new ResultException("活动结束时间不能为空");
        }
        if (startTime.after(endTime)) {
            throw new ResultException("活动开始时间不能大于结束时间");
        }

        if (null == preTime) {
            if (now.before(startTime)) {
                activity.setStatus(ActivityStatus.un_started.getValue());
            } else if (now.after(startTime) && now.before(endTime)) {
                activity.setStatus(ActivityStatus.ongoing.getValue());
            } else if (now.after(endTime)) {
                activity.setStatus(ActivityStatus.ended.getValue());
            }
        } else {
            if (preTime.after(startTime)) {
                throw new ResultException("活动预热时间不能大于开始时间");
            }
            if (now.before(preTime)) {
                activity.setStatus(ActivityStatus.un_started.getValue());
            } else if (now.after(preTime) && now.before(startTime)) {
                activity.setStatus(ActivityStatus.preheating.getValue());
            } else if (now.after(startTime) && now.before(endTime)) {
                activity.setStatus(ActivityStatus.ongoing.getValue());
            } else if (now.after(endTime)) {
                activity.setStatus(ActivityStatus.ended.getValue());
            }
        }


    }

    private void handelCover(SbActivity activity) throws Exception {

        String cover = StringUtil.trim(activity.getCover());
        if (cover.length() > 0) {
            if (cover.contains(Configuration.temp_path)) { //重新上传的
                cover = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(cover, OSSClientUtil.upload_type.activity_icon);
                activity.setCover(cover);
            }
        }
    }

    private void handelThumb(SbActivity activity) throws Exception {

        String thumbCover = StringUtil.trim(activity.getThumbCover());
        if (thumbCover.length() > 0) {
            if (thumbCover.contains(Configuration.temp_path)) { //重新上传的
                thumbCover = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(thumbCover, OSSClientUtil.upload_type.activity_thumb);
                activity.setThumbCover(thumbCover);
            }
        }
    }

    private void topicCount(ActivityDto dto) {

        if (null != dto) {
            Integer activityId = dto.getId();
            if (null != activityId) {
                SbTopicExample example = new SbTopicExample();
                example.createCriteria().andActivityIdEqualTo(activityId).andAuditStatusEqualTo(AuditStatus.MAN_AUDIT_PASS.getValue());
                Long count = topicMapper.countByExample(example);
                dto.setTopicCount(count.intValue());
            }
        }

    }

    private void topicInfo(SbTopicDto dto, Map<Integer, DsmUser> userMap, Map<Integer, SbVideo> videoMap) {

        if (null != dto) {
            Integer uid = dto.getUid();
            Integer videoId = dto.getVideoId();
            if (null != uid && userMap.containsKey(uid)) {
                DsmUser user = userMap.get(uid);
                if (null != user) {
                    dto.setPublisherName(user.getNickName());
                    Integer fictitious = user.getFictitious();
                    dto.setFictitious(Whether.no.getValue() == fictitious);
                } else {
                    dto.setPublisherName(StringUtil.empty);
                }
            }
            if (null != videoId && videoMap.containsKey(videoId)) {
                SbVideo video = videoMap.get(videoId);
                if (null != video) {
                    dto.setCover(video.getCover());
                    dto.setPlayCount(video.getPlayNum());
                    String dimension = StringUtil.trim(video.getDimension());
                    if (StringUtil.isEmpty(dimension)) {
                        dto.setTransverse(false);
                    } else {
                        String[] dimensions = dimension.split("x");
                        if(Integer.parseInt(dimensions[0]) > Integer.parseInt(dimensions[1])){
                            dto.setTransverse(true);
                        }
                    }
                }

            }
        }
    }
}
