package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by yang on 2017/10/24
 * 统计类型.
 */
@Getter
public enum StatisticsType {
    HOUR(1,"小时"),
    DAY(2,"天");

    private Integer code;
    private String name;

    StatisticsType(Integer code, String name) {

        this.code = code;
        this.name = name;
    }

    public static StatisticsType get(Integer code) {

        StatisticsType[] enums = StatisticsType.values();
        for (StatisticsType anEnum : enums) {
            if (Objects.equals(anEnum.code, code)) {
                return anEnum;
            }
        }
        return null;
    }
}
