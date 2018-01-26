package com.daishumovie.admin.service.auth;


import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.UserInfo;

/**
 * 权限控制的用户信息服务类.
 */
public interface IUserInfoService {

    /**
     * 获取用户信息.
     * @param username
     * @return
     */
    UserInfo getUserInfo(String username);

    UserInfo getUserInfo(AdminEntity admin);
}
