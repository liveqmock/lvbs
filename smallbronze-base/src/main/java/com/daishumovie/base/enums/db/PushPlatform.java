package com.daishumovie.base.enums.db;

import lombok.Getter;
import java.util.Objects;
/**
 * Created by feiFan.gou on 2017/10/17 14:38.
 */
@Getter
public enum PushPlatform {

    all(2, "IOS/安卓"),
    ios(0, "IOS"),
    android(1, "安卓"),
    ;

    private final Integer value;
    private final String name;

    PushPlatform(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static PushPlatform get(Integer value) {

        if (null != value) {
            for (PushPlatform platform : PushPlatform.values()) {
                if (Objects.equals(value, platform.getValue())) {
                    return platform;
                }
            }
        }
        return null;
    }
}
