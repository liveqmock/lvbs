package com.daishumovie.utils.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.schedule.ScheduleClient;
import cn.jpush.api.schedule.ScheduleResult;
import cn.jpush.api.schedule.model.SchedulePayload;
import cn.jpush.api.schedule.model.TriggerPayload;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Maps;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/10/19 14:16.
 */
public class ScheduleUtil extends Base {

    private static final ScheduleClient CLIENT;

    private static ScheduleUtil INSTANCE;

    static {
        CLIENT = new ScheduleClient(master_secret, app_key, null, ClientConfig.getInstance());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleUtil.class);

    public static final String schedule_id = "schedule_id";
    public static final String schedule_name = "schedule_name";

    private ScheduleUtil() {

    }

    public static ScheduleUtil instance() {

        if (null == INSTANCE) {
            INSTANCE = new ScheduleUtil();
        }
        return INSTANCE;
    }

    public Result jPushSchedule(SchedulePayload schedulePayload) {

        Result result;
        try {
            String requestJson = schedulePayload.toJSON().toString();
            LOGGER.info("j_push_schedule request ===> \r {}", StringUtil.formatJson(requestJson));
            ScheduleResult scheduleResult = CLIENT.createSchedule(schedulePayload);
            String responseJson = FastJsonUtils.toJSONString(scheduleResult);
            LOGGER.info("j_push_schedule response ===> \r {}", StringUtil.formatJson(responseJson));
            if (scheduleResult.getResponseCode() != HttpStatus.SC_OK) { //失败
                result = Result.instance.fail("推送失败");
            } else {
                Map<String, String> recordMap = Maps.newHashMap();
                recordMap.put(schedule_id, StringUtil.trim(scheduleResult.getSchedule_id()));
                recordMap.put(schedule_name, StringUtil.trim(scheduleResult.getName()));
                recordMap.put(input_json, requestJson);
                recordMap.put(output_json, responseJson);
                result = Result.instance.success(recordMap);
            }
        }catch (Exception e) {
            LOGGER.info("j_push_schedule exception ===> \r" + e.getMessage(), e);
            result = Result.instance.fail();
        }
        return result;
    }

    private SchedulePayload schedulePayload(PushPayload payload , Date pushTime) {

        //格式: schedule-uuid-[push_time]
        String formatTime = DateUtil.BASIC.format(pushTime);
        String name = "schedule_" + StringUtil.uuid().toLowerCase() + "_" + new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(pushTime);

        return SchedulePayload.newBuilder()
                .setEnabled(true)
                .setName(name)
                .setPush(payload)
                .setTrigger(TriggerPayload.newBuilder().setSingleTime(formatTime).buildSingle()).build();
    }

    public Result cancelSchedule(String scheduleId) {

        if (StringUtil.isEmpty(scheduleId)) {
            return Result.instance.fail("定时推送任务ID不能为空");
        }
        try {
            CLIENT.deleteSchedule(scheduleId);
        } catch (Exception e) {
            LOGGER.info("delete schedule[schedule_id:" + scheduleId + "] exception ===>\r" + e.getMessage(), e);
            return Result.instance.fail();
        }
        return Result.instance.success();
    }
}
