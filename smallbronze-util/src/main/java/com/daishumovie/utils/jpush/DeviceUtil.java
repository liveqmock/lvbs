package com.daishumovie.utils.jpush;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.device.DeviceClient;
import cn.jpush.api.device.TagAliasResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/10/31 9:49.
 */
public class DeviceUtil extends Base{

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceUtil.class);

    private static final DeviceClient CLIENT;

    private static DeviceUtil INSTANCE;

    static {
        CLIENT = new DeviceClient(master_secret, app_key);
    }

    public static DeviceUtil instance() {

        if (null == INSTANCE) {
            INSTANCE = new DeviceUtil();
        }
        return INSTANCE;
    }

    public void addRemoveDevicesFromTag(String tag, Set<String> toAddUsers, Set<String> toRemoveUsers) throws APIConnectionException, APIRequestException {
        LOGGER.info("设备标签: {}, - 添加用户：{},删除用户：{}",tag, toAddUsers.toString(), toRemoveUsers.toString());
        CLIENT.addRemoveDevicesFromTag(tag, toAddUsers, toRemoveUsers);
    }

    public List<String> getDeviceTagAlias(String registrationId) throws APIConnectionException, APIRequestException {
        TagAliasResult tagAlias = CLIENT.getDeviceTagAlias(registrationId);
        List<String> tags = tagAlias.tags;
        LOGGER.info("设备：{}，标签查询：{}",registrationId, tags.toString());
        return tags;
    }
}
