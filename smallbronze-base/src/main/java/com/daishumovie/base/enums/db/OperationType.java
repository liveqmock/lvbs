package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/6/29 19:48.
 */
@Getter
public enum OperationType {

    //通用
    insert("新增"),
    edit("编辑"),
    delete("删除"),
    login("登录"),
    change_password("修改密码"),
    logout("退出"),
    upload("上传"),
    //素材管理
    on_shelf("上架"),
    off_shelf("下架"),
    up("置顶"),
    //话题管理
    pass("通过"),
    un_pass("不通过"),
    publish("发布"),
    //视频管理
    //活动管理 & 合辑操作
    put_video("放入视频"),
    remove_video("移除视频")

    ;

    public final String value;
    OperationType(String value) {

        this.value = value;
    }

    public static String getValue(String name) {

        if (null != name && name.length() > 0) {
            OperationType[] types = OperationType.values();
            for (OperationType type : types) {
                if (name.equals(type.name())) {
                    return type.value;
                }
            }
        }
        return "";
    }

}
