package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by yang on 2017/9/20.
 */

@Getter
public enum UserType {

    UGC(0, "UGC"),
    PGC(1, "PGC"),
    ;

    private Integer value;
    private String name;

    UserType(Integer value, String name) {

        this.value = value;
        this.name = name;
    }

    public static UserType get(Integer value) {

        if (null != value) {
            for (UserType type : UserType.values()) {
                if (Objects.equals(value, type.getValue())) {
                    return type;
                }
            }
        }
        return null;
    }
}
