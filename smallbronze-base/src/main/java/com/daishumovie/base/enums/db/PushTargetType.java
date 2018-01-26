package com.daishumovie.base.enums.db;

import lombok.Getter;
import java.util.Objects;
/**
 * Created by feiFan.gou on 2017/10/17 14:56.
 */
@Getter
public enum PushTargetType {
    //跳转类型 1 话题/视频 2 用户 3 APP首页 4 WebUrl 5 活动 6 合辑
    video(1, "话题/视频"),
    user(2, "用户"),
    app_home(3, "APP首页"),
    web_url(4, "H5页面"),
    activity(5, "活动"),
    album(6, "合辑"),
    ;

    private final Integer value;
    private final String name;

    PushTargetType(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static PushTargetType get(Integer value) {

        if (null != value) {
            for (PushTargetType targetType : PushTargetType.values()) {
                if (Objects.equals(value, targetType.getValue())) {
                    return targetType;
                }
            }
        }
        return null;
    }

    public static PushTargetType[] template() {
        return new PushTargetType[]{
                video,activity,album
        };
    }
}
