package com.daishumovie.timer.service;
import cn.jpush.api.push.model.audience.AudienceType;
import cn.jpush.api.report.ReceivedsResult;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.base.enums.front.PushKey;
import com.daishumovie.dao.mapper.smallbronze.DsmPushTokenMapper;
import com.daishumovie.dao.mapper.smallbronze.DsmUserSettingsMapper;
import com.daishumovie.dao.mapper.smallbronze.SbPushTaskMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.JacksonUtil;
import com.daishumovie.utils.StringUtil;
import com.daishumovie.utils.jpush.PushUtil;
import com.daishumovie.utils.jpush.ReportUtil;
import com.daishumovie.utils.jpush.Result;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static com.daishumovie.base.enums.db.PushTargetType.activity;
import static com.daishumovie.base.enums.db.PushTargetType.video;
/**
 * Created by feiFan.gou on 2017/10/19 15:54.
 */
@Service
public class SchedulePushService {
    @Autowired
    private SbPushTaskMapper taskMapper;
    @Autowired
    private DsmPushTokenMapper tokenMapper;
    @Autowired
    private DsmUserSettingsMapper settingsMapper;
    @Value("${push.environment}")
    private boolean pushEnvironment;
    private static final Logger logger = LoggerFactory.getLogger(SchedulePushService.class);
    /**
     * 扫描待处理的定时推送
     * @return
     */
    public List<SbPushTask> scheduleList() {
        SbPushTaskExample example = new SbPushTaskExample();
        example.createCriteria()
                .andWayEqualTo(PushWay.schedule.getValue())
                .andPushTimeLessThanOrEqualTo(new Date())
                .andStatusEqualTo(PushStatus.pushing.getValue());
        return taskMapper.selectByExample(example);
    }
    /**
     * 执行推送
     * @param scheduleList
     */
    @SuppressWarnings("unchecked")
    public void push(List<SbPushTask> scheduleList) {
        if (!CollectionUtils.isNullOrEmpty(scheduleList)) {
            logger.info("----------------------- scan schedule push {}, at {} -------------------------",scheduleList.size(), DateUtil.BASIC.format(new Date()));
            List<String> audienceValues = Lists.newArrayList(PushTagEnum.notify.name());
            List<Thread> threads = Lists.newArrayList();
            scheduleList.forEach(task -> {
                Runnable runnable = () -> {
                    try {
                        PushPlatform platform = PushPlatform.get(task.getPlatform());
                        PushUtil pushUtil = PushUtil.instance(pushEnvironment);
                        PushTargetType targetType = PushTargetType.get(task.getTargetType());
                        if (null == targetType) {
                            return;
                        }
                        Map<String, String> extras = Maps.newHashMap();
                        extras.put(PushKey.target.name(), task.getTargetId());
                        extras.put(PushKey.targetType.name(), targetType.getValue().toString());
                        String alert = StringUtil.trim(task.getAlert());
                        Result result;
                        Integer iosReceivedCount = 0 ,androidReceivedCount = 0;
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
                                return;
                        }
                        task.setIosReceivedCount(iosReceivedCount);
                        task.setAndroidReceivedCount(androidReceivedCount);
                        if (!result.isOk()) {
                            task.setStatus(PushStatus.fail.getValue());
                        } else {
                            task.setStatus(PushStatus.success.getValue());
                        }
                        Map<String, String> recordMap = (Map<String, String>) result.getData();
                        task.setInputJson(recordMap.get(PushUtil.input_json));
                        task.setOutputJson(recordMap.get(PushUtil.output_json));
                        task.setMsgId(recordMap.get(PushUtil.msg_id));
                        task.setModifyTime(new Date());
                        taskMapper.updateByPrimaryKeySelective(task);
                    } catch (Exception e) {
                        logger.info("push error ===> push_id[{}],push_alert[{}]", task.getId(), task.getAlert());
                        logger.info("push exception ===>\r" + e.getMessage(), e);
                    }
                };
                threads.add(new Thread(runnable));
                threads.forEach(Thread::start);
            });
        }
    }
    /**
     * 扫描需要统计的推送
     */
    public List<SbPushTask> reportList() {
        SbPushTaskExample example = new SbPushTaskExample();
        example.createCriteria()
                .andStatusEqualTo(PushStatus.success.getValue())
                .andPushTimeLessThanOrEqualTo(new Date())
                .andPushTimeGreaterThanOrEqualTo(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24));
        return taskMapper.selectByExample(example);
    }
    /**
     * 请求极光进行统计
     * @param reportList
     */
    @SuppressWarnings("unchecked")
    public void report(List<SbPushTask> reportList){
        if (!CollectionUtils.isNullOrEmpty(reportList)) {
            logger.info("----------------------- scan report {}, at {} -------------------------",reportList.size(), DateUtil.BASIC.format(new Date()));
            try {
                List<String> msgIdList = Lists.newArrayList();
                Map<String, SbPushTask> msgIdMap = Maps.newHashMap();
                reportList.forEach(task -> {
                    String msgId = StringUtil.trim(task.getMsgId());
                    if (msgId.length() == 0) {
                        logger.info("report error ===> push_id[{}],push_alert[{}] msg_id is empty !!");
                    } else {
                        msgIdMap.put(msgId, task);
                        msgIdList.add(msgId);
                    }
                });
                String[] msgIds = new String[msgIdList.size()];
                msgIdList.toArray(msgIds);
                Result result = ReportUtil.instance().report(msgIds);
                if (result.isOk()) {
                    List<ReceivedsResult.Received> receivedList = (List<ReceivedsResult.Received>) result.getData();
                    if (!CollectionUtils.isNullOrEmpty(receivedList)) {
                        receivedList.forEach(received -> {
                            String msgId = String.valueOf(received.msg_id);
                            if (msgIdMap.containsKey(msgId)) {
                                SbPushTask task = msgIdMap.get(msgId);
                                if (null != task) {
                                    if(task.getIosReceivedCount() < received.ios_apns_sent || task.getAndroidReceivedCount() < received.android_received) { //有数据增加才做更新
                                        String reportOutputJson = StringUtil.trim(task.getReportOutputJson());
                                        String receiveJson = "{error:'json format error'}";
                                        try {
                                            receiveJson = JacksonUtil.obj2json(received);
                                        } catch (Exception ignored) {}
                                        if (reportOutputJson.length() == 0) {
                                            task.setReportOutputJson("["+receiveJson + "]");
                                        } else {
                                            task.setReportOutputJson(reportOutputJson.substring(0, reportOutputJson.length() - 1 ) + "," + receiveJson + "]");
                                        }
                                        if (task.getIosReceivedCount() < received.ios_apns_sent) {
                                            task.setIosReceivedCount(received.ios_apns_sent);
                                        }
                                        if (task.getAndroidReceivedCount() < received.android_received) {
                                            task.setAndroidReceivedCount(received.android_received);
                                        }
                                        task.setModifyTime(new Date());
                                        taskMapper.updateByPrimaryKeySelective(task);
                                    }
                                }
                            }
                        });
                    }
                } else {
                    logger.info("report fail ===>\r{}" + result.getMsg());
                }
            } catch (Exception e) {
                logger.info("report timer exception ===>\r" + e.getMessage(), e);
            }
        }
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
            case android:
                criteria.andOsEqualTo(AppPlatEnum.ANDROID.getCode());
                break;
            case all:
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