package com.daishumovie.admin.service.impl;
import cn.jpush.api.push.model.audience.AudienceType;
import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.PushTaskDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IPushService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.base.enums.front.PushKey;
import com.daishumovie.dao.mapper.smallbronze.*;
import com.daishumovie.dao.model.*;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.daishumovie.utils.jpush.PushUtil;
import com.daishumovie.utils.jpush.Result;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static com.daishumovie.base.enums.db.PushTargetType.activity;
import static com.daishumovie.base.enums.db.PushTargetType.video;
/**
 * Created by feiFan.gou on 2017/10/16 11:10.
 */
@Service
public class PushService extends BaseService implements IPushService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushService.class);
    private @Autowired
    SbPushTaskMapper taskMapper;
    private @Autowired
    SbTopicMapper topicMapper;
    @Autowired
    private DsmPushTokenMapper tokenMapper;
    @Autowired
    private DsmUserSettingsMapper settingsMapper;
    @Autowired
    private SbTopicAlbumMapper albumMapper;
    private @Autowired
    SbActivityMapper activityMapper;
    private @Autowired
    IAdminService adminService;
    @Override
    @SuppressWarnings("unchecked")
    public void push(SbPushTask task, Integer operator) {
        validate(task, false);
        try {
            PushWay way = PushWay.get(task.getWay());
            switch (way) {
                case immediately:
                    // 1.do push
                    PushPlatform platform = PushPlatform.get(task.getPlatform());
                    Result result;
                    List<String> audienceValues = Lists.newArrayList(PushTagEnum.notify.name());
                    String alert = StringUtil.trim(task.getAlert());
                    PushUtil pushUtil = PushUtil.instance(Configuration.INSTANCE.push_msg_environment);
                    PushTargetType targetType = PushTargetType.get(task.getTargetType());
                    if (null == targetType) {
                        throw new ResultException("推送目标有误");
                    }
                    Map<String, String> extras = Maps.newHashMap();
                    extras.put(PushKey.target.name(), task.getTargetId());
                    extras.put(PushKey.targetType.name(), targetType.getValue().toString());
                    Integer iosReceivedCount = 0, androidReceivedCount = 0;
                    switch (platform) {
                        case all:
                            result = pushUtil.pushWhole(alert, extras);
                            iosReceivedCount = sendCount(PushPlatform.ios);
                            androidReceivedCount = sendCount(PushPlatform.android);
                            break;
                        case android:
                            result = pushUtil.push2android(AudienceType.TAG, audienceValues, alert, extras);
                            androidReceivedCount = sendCount(PushPlatform.android);
                            break;
                        case ios:
                            iosReceivedCount = sendCount(PushPlatform.ios);
                            result = pushUtil.push2ios(AudienceType.TAG, audienceValues, alert, extras);
                            break;
                        default:
                            throw new ResultException("推送平台有误");
                    }
                    task.setIosReceivedCount(iosReceivedCount);
                    task.setAndroidReceivedCount(androidReceivedCount);
                    if (!result.isOk()) {
                        throw new ResultException("推送失败");
                    } else {
                        Map<String, String> recordMap = (Map<String, String>) result.getData();
                        task.setInputJson(recordMap.get(PushUtil.input_json));
                        task.setOutputJson(recordMap.get(PushUtil.output_json));
                        task.setMsgId(recordMap.get(PushUtil.msg_id));
                    }
                    break;
                case schedule:
            }
            // 2.save data
            insertTask(way, task, operator);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.info("push exception ===>\r" + e.getMessage(), e);
            throw new ResultException();
        }
    }
    @Override
    public void cancelSchedule(Integer taskId, Integer operatorId) {
        if (null == taskId) {
            throw new ResultException(ErrMsg.param_error);
        }
        SbPushTask task = taskMapper.selectByPrimaryKey(taskId);
        if (null == task) {
            throw new ResultException("该任务不存在");
        }
        try {
            task.setModifyTime(new Date());
            task.setModifier(operatorId);
            task.setStatus(PushStatus.cancel.getValue());
            taskMapper.updateByPrimaryKeySelective(task);
        } catch (Exception e) {
            LOGGER.info("cancel_schedule exception ===> \r" + e.getMessage(), e);
            throw new ResultException();
        }
    }
    public ReturnDto<PushTaskDto> paginate(ParamDto param, String pushTime, Integer status, String alert, Integer targetType, Integer platform) {
        try {
            SbPushTaskExample example = condition(pushTime, status, alert, targetType, platform);
            Long total = taskMapper.countByExample(example);
            List<PushTaskDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                List<SbPushTask> tasks = taskMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(tasks)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    tasks.forEach(task -> {
                        PushTaskDto dto = new PushTaskDto();
                        BeanUtils.copyProperties(task, dto);
                        if (null != task.getPusherId()) {
                            adminIdSet.add(Long.valueOf(task.getPusherId()));
                        }
                        if (null != task.getModifier()) {
                            adminIdSet.add(Long.valueOf(task.getModifier()));
                        }
                        dtoList.add(dto);
                    });
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    dtoList.forEach(dto -> {
                        Integer pusher = dto.getPusherId();
                        if (null != pusher && adminNameMap.containsKey(pusher)) {
                            dto.setPusher(adminNameMap.get(pusher));
                        }
                        Integer canceler = dto.getModifier();
                        if (null != canceler && adminNameMap.containsKey(canceler)) {
                            dto.setCanceler(adminNameMap.get(canceler));
                        }
                    });
                }
            }
            Page<PushTaskDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("push paginate exception ===> \r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }
    @Override
    public String getAlert(Integer targetType, String targetId) {
        if (null == targetType || StringUtil.isEmpty(targetId)) {
            throw new ResultException(ErrMsg.param_error);
        }
        PushTargetType type = PushTargetType.get(targetType);
        if (null == type) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            switch (type) {
                case video:
                    SbTopic topic = topicMapper.selectByPrimaryKey(Integer.valueOf(targetId));
                    if (null == topic) {
                        throw new ResultException("推送视频不存在");
                    }
                    if (!topic.getStatus().equals(TopicStatus.published.getValue())) {
                        throw new ResultException("该视频无效");
                    }
                    return StringUtil.trim(topic.getTitle());
                case activity:
                    SbActivity activity = activityMapper.selectByPrimaryKey(Integer.valueOf(targetId));
                    if (null == activity) {
                        throw new ResultException("推送活动不存在");
                    }
                    if (activity.getWhetherOnline().equals(Whether.no.getValue()) ||
                            !(activity.getStatus().equals(ActivityStatus.preheating.getValue()) || activity.getStatus().equals(ActivityStatus.ongoing.getValue()))) {
                        throw new ResultException("该活动无效");
                    }
                    return StringUtil.trim(activity.getTitle());
                case album:
                    SbTopicAlbum album = albumMapper.selectByPrimaryKey(Integer.valueOf(targetId));
                    if (null == album) {
                        throw new ResultException("推送合辑不存在");
                    }
                    if (album.getStatus().equals(AlbumStatus.offline.getValue())) {
                        throw new ResultException("该合辑已下线");
                    }
                    if (album.getStatus().equals(AlbumStatus.publishing.getValue())) {
                        throw new ResultException("该合辑还未发布");
                    }
                    return StringUtil.trim(album.getTitle());
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.info("get_alert exception ===>\r" + e.getMessage(), e);
        }
        throw new ResultException();
    }
    @Override
    protected void specificVerify(Object model, boolean isUpdate) {
        SbPushTask task = (SbPushTask) model;
        if (StringUtil.isEmpty(task.getAlert())) {
            throw new ResultException("推送文案不能为空");
        }
        if (isUpdate) {
            if (null == task.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbPushTask dbTask = taskMapper.selectByPrimaryKey(task.getId());
            if (null == dbTask) {
                throw new ResultException("该推送不存在");
            }
        }
        if (null == task.getPlatform()) {
            throw new ResultException("推送平台不能为空");
        }
        PushPlatform platform = PushPlatform.get(task.getPlatform());
        if (null == platform) {
            throw new ResultException("推送平台格式有误");
        }
        if (null == task.getWay()) {
            throw new ResultException("推送方式不能为空");
        }
        PushWay way = PushWay.get(task.getWay());
        if (null == way) {
            throw new ResultException("推送方式格式有误");
        }
        if (way.equals(PushWay.schedule) && null == task.getPushTime()) {
            throw new ResultException("【定时推送】推送时间不能为空");
        } else if (way.equals(PushWay.immediately) && null == task.getPushTime()) {
            task.setPushTime(new Date());
        }
        if (null == task.getTargetType() || null == task.getTargetId()) {
            throw new ResultException("推送对象不能为空");
        }
        PushTargetType targetType = PushTargetType.get(task.getTargetType());
        if (null == targetType) {
            throw new ResultException("推送对象类型格式有误");
        }
        switch (targetType) {
            case video:
                SbTopic topic = topicMapper.selectByPrimaryKey(Integer.valueOf(task.getTargetId()));
                if (null == topic) {
                    throw new ResultException("推送视频不存在");
                }
                if (!topic.getStatus().equals(TopicStatus.published.getValue())) {
                    throw new ResultException("该视频无效");
                }
                break;
            case activity:
                SbActivity activity = activityMapper.selectByPrimaryKey(Integer.valueOf(task.getTargetId()));
                if (null == activity) {
                    throw new ResultException("推送活动不存在");
                }
                if (activity.getWhetherOnline().equals(Whether.no.getValue()) ||
                        !(activity.getStatus().equals(ActivityStatus.preheating.getValue()) || activity.getStatus().equals(ActivityStatus.ongoing.getValue()))) {
                    throw new ResultException("该活动无效");
                }
                break;
            case album:
                SbTopicAlbum album = albumMapper.selectByPrimaryKey(Integer.valueOf(task.getTargetId()));
                if (null == album) {
                    throw new ResultException("推送合辑不存在");
                }
        }
    }
    private void insertTask(PushWay way, SbPushTask task, Integer operator) {
        task.setCreateTime(new Date());
        task.setModifyTime(task.getCreateTime());
        task.setPusherId(operator);
        task.setModifier(operator);
        if (way.equals(PushWay.immediately)) {
            task.setStatus(PushStatus.success.getValue());
        } else {
            task.setStatus(PushStatus.pushing.getValue());
        }
        taskMapper.insertSelective(task);
    }
    private SbPushTaskExample condition(String pushTime, Integer status, String alert, Integer targetType, Integer platform) {
        SbPushTaskExample example = new SbPushTaskExample();
        SbPushTaskExample.Criteria criteria = example.createCriteria();
        if (null != status) {
            String orderStr;
            if (status.equals(PushStatus.pushing.getValue())) { //待推送
                if (StringUtil.isNotEmpty(pushTime)) {
                    criteria.andPushTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(pushTime));
                }
                orderStr = "push_time";
            } else if (status.equals(PushStatus.cancel.getValue())) { //已取消
                if (StringUtil.isNotEmpty(pushTime)) {
                    criteria.andModifyTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(pushTime));
                }
                orderStr = "modify_time";
            } else { //已推送
                if (StringUtil.isNotEmpty(pushTime)) {
                    criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(pushTime));
                }
                orderStr = "create_time";
            }
            example.setOrderByClause(orderStr + " desc");
            criteria.andStatusEqualTo(status);
        }
        if (null != targetType) {
            criteria.andTargetTypeEqualTo(targetType);
        }
        if (StringUtil.isNotEmpty(alert)) {
            criteria.andAlertLike(StringUtil.sqlLike(alert));
        }
        if (null != platform) {
            criteria.andPlatformEqualTo(platform);
        }
        return example;
    }
    private Integer sendCount(PushPlatform platform) {
        if (null == platform) {
            return 0;
        }
        DsmPushTokenExample example = new DsmPushTokenExample();
        DsmPushTokenExample.Criteria criteria = example.createCriteria();
        criteria.andRegistrationIdIsNotNull();
        switch (platform) {
            case ios:
                criteria.andOsEqualTo(AppPlatEnum.IOS.getCode());
                break;
            case all:
                break;
            case android:
                criteria.andOsEqualTo(AppPlatEnum.ANDROID.getCode());
                break;
            default:
                return 0;
        }
        List<DsmPushToken> tokens = tokenMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(tokens)) {
            return 0;
        }
        List<String> didList = Lists.newArrayList();
        tokens.forEach(toke -> {
            if (StringUtil.isNotEmpty(toke.getDid())) {
                didList.add(toke.getDid());
            }
        });
        DsmUserSettingsExample settingsExample = new DsmUserSettingsExample();
        DsmUserSettingsExample.Criteria settingsExampleCriteria = settingsExample.createCriteria();
        if (!platform.equals(PushPlatform.all)) {
            settingsExampleCriteria.andDidIn(didList);
        }
        settingsExampleCriteria.andReceiveNotificationEqualTo(Whether.yes.getValue());
        Long total = settingsMapper.countByExample(settingsExample);
        return tokens.size() - total.intValue();
    }
}