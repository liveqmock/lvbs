package com.daishumovie.dao.model.auth.enums;

/**
 * Created by feiFan.gou on 2017/6/29 19:58.
 */
public enum ErrMsg {

    un_login("您还为登录"),
    param_error("参数错误"),
    log_error("记录日志错误"),
    system_error("系统错误");

    public final String value;

    ErrMsg(String value) {
        this.value = value;
    }
}
