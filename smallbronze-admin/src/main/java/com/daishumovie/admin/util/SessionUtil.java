package com.daishumovie.admin.util;

import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by feiFan.gou on 2017/8/23 10:19.
 */
public class SessionUtil {

    public static final String admin_session_key = "admin_session_key";

    /**
     * 获取session中的用户信息
     * @param request
     * @return
     */
    public static UserInfo getLoginUserInfo(HttpServletRequest request) {

        if (null != request) {
            HttpSession session = request.getSession();
            if (null != session) {
                return (UserInfo) session.getAttribute(admin_session_key);
            }
        }
        return null;
    }

    /**
     * 获取session中的用户对应数据库的adminEntity
     * @param request
     * @return
     */
    public static AdminEntity getLoginAdmin(HttpServletRequest request) {

        UserInfo info = getLoginUserInfo(request);
        if (null != info) {
            return info.getAdmin();
        }
        return null;
    }

    /**
     * 获取session中登录用的Uid
     * @param request
     * @return
     */
    public static Integer getLoginAdminUid(HttpServletRequest request) {

        AdminEntity entity = getLoginAdmin(request);
        if (null != entity) {
            return entity.getId().intValue();
        }
        return null;
    }

    /**
     * 向session中存入登录用户
     * @param request
     * @param info
     */
    public static void setLoginAdmin(HttpServletRequest request, UserInfo info) {

        if (null != request) {
            HttpSession session = request.getSession();
            if (null != session) {
                session.setAttribute(admin_session_key, info);
            }
        }
    }


    /**
     * 销毁session中的登录用户
     * @param request
     */
    public static void destroy(HttpServletRequest request) {

        if (null != request) {
            HttpSession session = request.getSession();
            if (null != session) {
                session.removeAttribute(admin_session_key);
            }
        }
    }


    /**
     * 是否登录
     * @param request
     * @return
     */
    public static boolean isLogin(HttpServletRequest request) {

        return null != getLoginAdmin(request);
    }
}
