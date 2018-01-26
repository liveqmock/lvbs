package com.daishumovie.utils.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceType;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;
import com.daishumovie.utils.JacksonUtil;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Maps;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/10/19 14:15.
 */
public class PushUtil extends Base {

    private static final JPushClient CLIENT;
    private static PushUtil INSTANCE;
    private static boolean IS_PRO = false;

    static {
        CLIENT = new JPushClient(master_secret, app_key, null, ClientConfig.getInstance());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PushUtil.class);

    public static final String msg_id = "msg_id";

    private PushUtil() {
    }

    public static PushUtil instance(boolean isPor) {

        if (isPor) {
            IS_PRO = true;
        }
        if (null == INSTANCE) {
            INSTANCE = new PushUtil();
        }
        return INSTANCE;
    }

    public Result push2ios(AudienceType audienceType, List<String> audienceValues, String alert, Map<String, String> extras) {

        return jPush(iosPayload(audienceType, audienceValues, alert, extras));
    }

    public Result push2android(AudienceType audienceType, List<String> audienceValues, String alert, Map<String, String> extras) {

        return jPush(androidPayload(audienceType, audienceValues, alert, extras));
    }

    public Result pushWhole(String alert, Map<String, String> extras) {

        return jPush(wholePayload(alert, extras));
    }

    private Result jPush(PushPayload payload) {

        Result result = Result.instance.success();
        try {
            String requestJson = payload.toJSON().toString();
            LOGGER.info("j_push request ===> \r{}",StringUtil.formatJson(requestJson));
            PushResult pushResult = CLIENT.sendPush(payload);
            String responseJson = JacksonUtil.obj2json(pushResult);
            LOGGER.info("j_push response ===> \r{}",StringUtil.formatJson(responseJson));
            if (pushResult.getResponseCode() != HttpStatus.SC_OK) { //失败
                result = Result.instance.fail("推送失败");
            } else {
                Map<String, String> recordMap = Maps.newHashMap();
                recordMap.put(input_json, requestJson);
                recordMap.put(output_json, responseJson);
                recordMap.put(msg_id, String.valueOf(pushResult.msg_id));
                result.setData(recordMap);
            }
        } catch (Exception e) {
            LOGGER.info("j_push exception ===> \r" + e.getMessage(), e);
            result = Result.instance.fail();
        }
        return result;
    }

    private PushPayload iosPayload(AudienceType audienceType, List<String> audienceValues, String alert, Map<String, String> extras) {

        return PushPayload.newBuilder()
                .setAudience(audience(audienceType, audienceValues))
                .setPlatform(Platform.ios())
                .setNotification(notification(iosNotification(alert, extras)))
                .setOptions(options())
                .build();
    }

    private PushPayload androidPayload(AudienceType audienceType, List<String> audienceValues, String alert, Map<String, String> extras) {

        return PushPayload.newBuilder()
                .setAudience(audience(audienceType, audienceValues))
                .setPlatform(Platform.android())
                .setNotification(notification(androidNotification(alert, extras)))
                .setOptions(options())
                .build();
    }

    private PushPayload wholePayload(String alert, Map<String, String> extras) {

        return PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setNotification(notification(iosNotification(alert, extras), androidNotification(alert, extras)))
                .setOptions(options())
                .build();
    }

    private Audience audience(AudienceType audienceType, List<String> audienceValues) {

        Audience audience = null;
        switch (audienceType) {
            case TAG:
                audience = Audience.tag(audienceValues);
                break;
            case ALIAS:
                audience = Audience.alias(audienceValues);
                break;
            case TAG_AND:
                audience = Audience.tag_and(audienceValues);
                break;
            case TAG_NOT:
                audience = Audience.tag_and(audienceValues);
                break;
            case SEGMENT:
                audience = Audience.segment(audienceValues);
                break;
            case REGISTRATION_ID:
                audience = Audience.registrationId(audienceValues);
                break;
        }
        return audience;
    }

    private Notification notification(PlatformNotification... notifications) {

        Notification.Builder builder = Notification.newBuilder();
        for (PlatformNotification notification : notifications) {
            builder.addPlatformNotification(notification);
        }
        return builder.build();
    }

    private Options options() {

        return Options.newBuilder().setApnsProduction(IS_PRO).build();

    }

    private AndroidNotification androidNotification(String alert, Map<String, String> extras) {

        return AndroidNotification.newBuilder().setAlert(alert).setPriority(2).addExtras(extras).build();
    }

    private IosNotification iosNotification(String alert, Map<String, String> extras) {

        return IosNotification.newBuilder().addExtras(extras).setContentAvailable(true).setAlert(alert).build();
    }
}
