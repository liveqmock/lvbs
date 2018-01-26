package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/8/28 18:51.
 */
@Getter
public enum CommonStatus {

    normal(1,"正常"),
    invalid(0,"废弃");

    private final Integer value;
    private final String name;

    CommonStatus(Integer value, String name) {

        this.value = value;
        this.name = name;
    }

    public static CommonStatus get(Integer value) {

        if (null != value) {
            for (CommonStatus status : CommonStatus.values()) {
                if (Objects.equals(value, status.value)) {
                    return status;
                }
            }
        }
        return null;
    }
}
