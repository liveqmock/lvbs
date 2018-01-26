package com.daishumovie.api.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.daishumovie.base.dto.KeyValue;
import com.daishumovie.base.dto.user.UserInfoEdit;
import com.daishumovie.base.enums.db.PushTagEnum;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.ThirdLoginType;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.DsmUserSettingsMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.DsmUserExample;
import com.daishumovie.dao.model.DsmUserSettings;
import com.daishumovie.dao.model.DsmUserSettingsExample;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.Encryption;
import com.daishumovie.utils.jpush.DeviceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UserService {

    @Autowired
    private DsmUserMapper userMapper;
    @Autowired
    private DsmUserSettingsMapper userSettingsMapper;

    /**
     * 保存用户,没有数据插入;有则取出
     *
     * @param from     来源
     * @param thirdId  openId
     * @param nickname 昵称
     * @param sex      性别
     * @param avatar   头像
     * @return
     */
    public DsmUser save(ThirdLoginType from, String thirdId, String nickname, Integer sex, String avatar) {

        DsmUserExample example = new DsmUserExample();
        DsmUserExample.Criteria criteria = example.createCriteria();
        switch (from) {
            case micro_message:
                criteria.andWxUnionIdEqualTo(thirdId);
                break;
            case micro_blob:
                criteria.andWbUidEqualTo(thirdId);
                break;
            case qq:
                criteria.andQqOpenidEqualTo(thirdId);
                break;
            default:
                throw new IllegalArgumentException("from 参数 错误");
        }
        List<DsmUser> userList = userMapper.selectByExample(example);

        DsmUser user;
        if (CollectionUtils.isNullOrEmpty(userList)) {
            //保存用户
            user = new DsmUser();

            user.setNickName(nickname);
            user.setSex(sex);
            user.setAvatar(avatar);
            user.setLastLoginTime(new Date());
            user.setFansNum(0);
            user.setFollowNum(0);
            user.setLikeAlbumNum(0);
            user.setLikeNum(0);
            user.setPublishCount(0);
            Header header = LocalData.HEADER.get();
            user.setAppId(Integer.valueOf(header.getAppId()));
            switch (from) {
                case micro_message:
                    user.setWxUnionId(thirdId);
                    break;
                case micro_blob:
                    user.setWbUid(thirdId);
                    break;
                case qq:
                    user.setQqOpenid(thirdId);
                    break;
                default:
                    throw new IllegalArgumentException("from 参数 错误");
            }
            userMapper.insertSelective(user);
        } else {
            user = userList.get(0);
            user.setLastLoginTime(new Date());
            user.setUpdateTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
        }
        return user;
    }

    /**
     * 判断手机号是否已经注册过
     *
     * @param mobile
     * @return
     */
    public boolean mobileOnly(String mobile) {

        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("手机号不能为空");
        }
        DsmUserExample userExample = new DsmUserExample();
        DsmUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andMobileEqualTo(mobile);
        return CollectionUtils.isNullOrEmpty(userMapper.selectByExample(userExample));
    }

    //获取该手机用户
    public DsmUser getUserByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("手机号不能为空");
        }
        DsmUserExample example = new DsmUserExample();
        DsmUserExample.Criteria criteria = example.createCriteria();
        criteria.andMobileEqualTo(mobile);
        List<DsmUser> dsmUsers = userMapper.selectByExample(example);
        if (dsmUsers.isEmpty()) {
            return null;
        }
        return dsmUsers.get(0);
    }

    //获取用户
    public DsmUser getUserByUid(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    public Response<DsmUser> login(String mobileNumber, String password) {

        try {
            DsmUserExample userExample = new DsmUserExample();
            DsmUserExample.Criteria criteria = userExample.createCriteria();
            criteria.andMobileEqualTo(mobileNumber);


            List<DsmUser> userList = userMapper.selectByExample(userExample);
            if (CollectionUtils.isNullOrEmpty(userList)) {
                return new Response<>(RespStatusEnum.USER_NOT_EXIST);
            }

            DsmUser user = userList.get(0);
            //校验密码
            if (!user.getPassword().equals(Encryption.newPasswordHash(password, user.getSecuritySalt()))) {
                return new Response<>(RespStatusEnum.USER_PASSWORD_ERROR);
            }
            user = userList.get(0);
            user.setUpdateTime(new Date());
            user.setLastLoginTime(user.getUpdateTime());
            userMapper.updateByPrimaryKeySelective(user);
            return new Response<>(user);
        } catch (Exception e) {
            return new Response<>(RespStatusEnum.ERROR);
        }
    }

    @Transactional
    public Response editUserInfo(UserInfoEdit userInfoEdit) {
        try {
            Header header = LocalData.HEADER.get();
            Integer uid = header.getUid();
            if (null == uid) {
                return new Response(RespStatusEnum.USER_NOT_LOGIN);
            }
            DsmUser user = new DsmUser();
            BeanUtils.copyProperties(userInfoEdit, user);
            user.setUid(uid);
            userMapper.updateByPrimaryKeySelective(user);

            return new Response();
        } catch (Exception e) {
            return new Response<>(RespStatusEnum.ERROR);
        }

    }

    /**
     * 保存或者更新用户设置
     *
     * @param uid
     * @param receiveNotification
     * @param autoplay
     * @param allowBulletScreen
     * @return
     */
    /**
     * 保存或者更新用户设置
     *
     * @param uid
     * @param receiveNotification
     * @param autoplay
     * @param allowBulletScreen
     * @return
     */
    public Response<String> updateSettings(Integer uid, Integer receiveNotification, Integer autoplay,
                                           Integer allowBulletScreen, String registrationId) throws APIConnectionException, APIRequestException {
        Response<String> resp = new Response<>();
        Header header = LocalData.HEADER.get();
        DsmUserSettingsExample example = new DsmUserSettingsExample();
        example.setLimit(1);
        DsmUserSettingsExample.Criteria criteria = example.createCriteria();
        criteria.andDidEqualTo(header.getDid());
        criteria.andAppIdEqualTo(Integer.valueOf(header.getAppId()));
        List<DsmUserSettings> settingsList = userSettingsMapper.selectByExample(example);
        if (settingsList != null && settingsList.size() > 0) {
            DsmUserSettings update = new DsmUserSettings();
            update.setId(settingsList.get(0).getId());
            update.setAllowBulletScreen(allowBulletScreen);
            update.setReceiveNotification(receiveNotification);
            if (StringUtils.isNotBlank(registrationId)) {
                if (0 == receiveNotification) {
                    // 极光推送 取消该 registrationId 的推送标签
                    Set<String> toRemoveUsers = new HashSet<>();
                    toRemoveUsers.add(registrationId);
                    DeviceUtil.instance().addRemoveDevicesFromTag(PushTagEnum.notify.name(), new HashSet<>(), toRemoveUsers);
                } else {
                    Set<String> toAddUsers = new HashSet<>();
                    toAddUsers.add(registrationId);
                    DeviceUtil.instance().addRemoveDevicesFromTag(PushTagEnum.notify.name(), toAddUsers, new HashSet<>());
                }
            }
            update.setAutoplay(autoplay);
            update.setUid(uid);
            userSettingsMapper.updateByPrimaryKeySelective(update);
            return resp;
        }
        DsmUserSettings record = new DsmUserSettings();
        record.setAllowBulletScreen(allowBulletScreen);
        record.setReceiveNotification(receiveNotification);
        if (StringUtils.isNotBlank(registrationId) && 0 == receiveNotification) {
            Set<String> toRemoveUsers = new HashSet<>();
            toRemoveUsers.add(registrationId);
            DeviceUtil.instance().addRemoveDevicesFromTag(PushTagEnum.notify.name(), new HashSet<>(), toRemoveUsers);
        }
        record.setAutoplay(autoplay);
        record.setUid(uid);
        record.setDid(header.getDid());
        record.setAppId(Integer.valueOf(header.getAppId()));
        userSettingsMapper.insertSelective(record);
        return resp;
    }

    public Response isBindMobile(Integer uid) {
        DsmUser dsmUser = userMapper.selectByPrimaryKey(uid);
        String mobile = dsmUser.getMobile();
        KeyValue keyValue = new KeyValue();
        //抽奖H5页面所需
        keyValue.setKey(dsmUser.getNickName());
        keyValue.setValue(StringUtils.isNotBlank(mobile) ? "1" : "0");
        return new Response(keyValue);
    }
}