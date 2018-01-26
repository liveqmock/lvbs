package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * @author Cheng Yufei
 * @create 2017-10-25 17:55
 *
 **/
@Getter
public enum AdTargetType {

    topic(1,"话题/视频"),
    user(2,"用户"),
    app_firstpage(3,"APP首页"),
    weburl(4,"WebUrl"),
    activity(5,"活动"),
    album(6,"合辑"),

            ;


    private final Integer value;
    private final String name;

    AdTargetType(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static AdTargetType get(Integer value) {

        if (null != value) {
            for (AdTargetType targetType : AdTargetType.values()) {
                if (value.equals(targetType.getValue())) {
                    return targetType;
                }
            }
        }
        return null;
    }
}
