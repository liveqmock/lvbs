package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * @author Cheng Yufei
 * @create 2017-10-25 17:55
 *
 **/
@Getter
public enum AdType {

    start_up(0,"启动广告"),
    Carousel(1,"轮播广告"),


            ;


    private final Integer value;
    private final String name;

    AdType(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static AdType get(Integer value) {

        if (null != value) {
            for (AdType type : AdType.values()) {
                if (value.equals(type.getValue())) {
                    return type;
                }
            }
        }
        return null;
    }
}
