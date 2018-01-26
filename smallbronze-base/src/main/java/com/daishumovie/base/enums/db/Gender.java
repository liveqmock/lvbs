package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/9/6 15:10.
 */
@Getter
public enum Gender {

    male(1,"男"),
    female(2,"女"),
    unknown(0,"未知");

    private Integer value;
    private String name;
    Gender(Integer value, String name) {

        this.value = value;
        this.name = name;
    }

    public static Gender get(Integer value) {

        if (null != value) {
            for (Gender gender : Gender.values()) {
                if (Objects.equals(value,gender.getValue())) {
                    return gender;
                }
            }
        }
        return null;
    }
}
