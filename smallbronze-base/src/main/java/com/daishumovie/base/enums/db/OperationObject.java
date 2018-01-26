package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/6/30 10:04.
 */
@Getter
public enum OperationObject {

    material("素材"),
    material_category("素材分类"),
    topic("视频"),
    report("举报"),
    channel("频道"),
    comment("评论"),
    system("后台系统"),
    file("文件"),
    app_version("APP版本管理"),
    user("用户"),
    bucket("桶数据"),
    ad("广告"),
    activity("活动"),
    album("合辑");

    public final String value;

    OperationObject(String value) {

        this.value = value;
    }

    public static String getValue(String name) {

        if (null != name && name.length() > 0) {
            OperationObject[] objects = OperationObject.values();
            for (OperationObject object : objects) {
                if (name.equals(object.name())) {
                    return object.value;
                }
            }
        }
        return "";
    }
}
