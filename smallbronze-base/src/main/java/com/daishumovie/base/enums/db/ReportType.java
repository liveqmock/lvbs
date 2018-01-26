package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/5/12 14:53.
 * 1 视频举报 2 评论举报 3 用户举报
 */
@Getter
public enum ReportType {

    VIDEO(1,"视频举报"),
    COMMENT(2,"评论举报"),
    USER(3,"用户举报");

    private Integer code;
    private String name;

    ReportType(Integer code, String name) {

        this.code = code;
        this.name = name;
    }

    public static ReportType get(Integer code) {

        ReportType[] enums = ReportType.values();
        for (ReportType anEnum : enums) {
            if (Objects.equals(anEnum.code, code)) {
                return anEnum;
            }
        }
        return null;
    }
}
