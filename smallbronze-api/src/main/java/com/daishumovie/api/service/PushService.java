package com.daishumovie.api.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.daishumovie.base.enums.db.PushTagEnum;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmPushTokenMapper;
import com.daishumovie.dao.model.DsmPushToken;
import com.daishumovie.dao.model.DsmPushTokenExample;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.StringUtil;
import com.daishumovie.utils.jpush.DeviceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhuruisong on 2017/5/24
 * @since 1.0
 */
@Service
public class PushService {

    @Autowired
    private DsmPushTokenMapper dsmPushTokenMapper;


    public Response<String> saveToken(String token, String registrationId) throws APIConnectionException, APIRequestException {

        Header header = LocalData.HEADER.get();
        DsmPushToken dsmPushToken = dsmPushTokenMapper.selectByPrimaryKey(header.getDid());

        DsmUser dsmUser = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        Integer uid = dsmUser == null ? null : dsmUser.getUid();
        DsmPushToken record = new DsmPushToken();
        record.setToken(token);
        record.setUid(uid);
        record.setRegistrationId(registrationId);
        record.setDid(header.getDid());
        record.setOs(header.getOsEnum().getOs());

        //数据存在
        if (dsmPushToken != null) {
            if ((uid != null && !uid.equals(dsmPushToken.getUid()))
                    || StringUtil.isNotEmpty(registrationId)&& !registrationId.equals(dsmPushToken.getRegistrationId())) {
                //推送标签添加
				if (StringUtil.isNotEmpty(registrationId) && !registrationId.equals(dsmPushToken.getRegistrationId())) {
					Set<String> toAddUsers = new HashSet<>();
					Set<String> toRemoveUsers = new HashSet<>();
					toAddUsers.add(registrationId);
					// registrationId有变化则从标签内删除原来的registrationId；
					if (dsmPushToken.getRegistrationId() != null) {
						toRemoveUsers.add(dsmPushToken.getRegistrationId());
					}
					DeviceUtil.instance().addRemoveDevicesFromTag(PushTagEnum.notify.name(), toAddUsers, toRemoveUsers);
				}
                dsmPushTokenMapper.updateByPrimaryKeySelective(record);
            }
            return new Response<>();
        }
        record.setAppId(Integer.valueOf(header.getAppId()));
        dsmPushTokenMapper.insertSelective(record);
        //推送标签添加
        if (StringUtil.isNotEmpty(registrationId)) {
            Set<String> toAddUsers = new HashSet<>();
            toAddUsers.add(registrationId);
            DeviceUtil.instance().addRemoveDevicesFromTag(PushTagEnum.notify.name(), toAddUsers, new HashSet<>());
        }
        return new Response<>();

    }

    public void relevanceData(Integer uid, String did) {
        DsmPushToken pushToken = dsmPushTokenMapper.selectByPrimaryKey(did);
        if (pushToken == null) {
            return;
        }
        if (uid.equals(pushToken.getUid())) {
            return;
        }
        DsmPushToken record = new DsmPushToken();
        record.setDid(did);
        record.setUid(uid);
        dsmPushTokenMapper.updateByPrimaryKeySelective(record);

    }

    /**
     * 查询设备是否初次使用；
     *
     * @param did
     * @param os
     * @return
     */
    public boolean isFirstUse(String did, String os) {
        // 苹果用户
        if ("iOS".equals(os)) {
            DsmPushTokenExample example = new DsmPushTokenExample();
            example.setLimit(1);
            DsmPushTokenExample.Criteria criteria = example.createCriteria();
            criteria.andDidEqualTo(did);

            List<DsmPushToken> dsmPushTokenList = dsmPushTokenMapper.selectByExample(example);

            if (dsmPushTokenList.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            // TODO 安卓如何识别初次使用；
        }

        return false;
    }

}
