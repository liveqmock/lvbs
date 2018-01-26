package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/5/12 14:53.
 */
@Getter
public enum ReportResultEnum {

    NO(0,"不符合举报实情"),
    YES(1,"符合实情");

    private Integer code;
    private String name;

    ReportResultEnum(Integer code, String name) {

        this.code = code;
        this.name = name;
    }

    public static ReportResultEnum get(Integer code) {

        ReportResultEnum[] enums = ReportResultEnum.values();
        for (ReportResultEnum anEnum : enums) {
            if (Objects.equals(anEnum.code, code)) {
                return anEnum;
            }
        }
        return null;
    }
}
