package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;
/**
 * Created by feiFan.gou on 2017/10/17 14:44.
 */
@Getter
public enum PushWay {

    immediately(0,"立即推送"),
    schedule(1, "定时推送"),
    ;
    private final Integer value;
    private final String name;

    PushWay(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static PushWay get(Integer value) {

        if (null != value) {
            for (PushWay way : PushWay.values()) {
                if (Objects.equals(value, way.getValue())) {
                    return way;
                }
            }
        }
        return null;
    }
}
