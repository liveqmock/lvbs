package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/5/12 14:53.
 */
@Getter
public enum ReportStatusEnum {

    untreated(0,"待处理"),
    offline(1,"已处理"),
    online_for_now(2,"暂不处理");

    private Integer code;
    private String name;

    ReportStatusEnum(Integer code, String name) {

        this.code = code;
        this.name = name;
    }

    public static ReportStatusEnum get(Integer code) {

        ReportStatusEnum[] enums = ReportStatusEnum.values();
        for (ReportStatusEnum anEnum : enums) {
            if (Objects.equals(anEnum.code, code)) {
                return anEnum;
            }
        }
        return null;
    }
}
