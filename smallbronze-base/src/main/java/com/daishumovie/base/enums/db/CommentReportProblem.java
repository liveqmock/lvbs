package com.daishumovie.base.enums.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
//评论举报
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommentReportProblem {
	// 1：垃圾营销 2 淫秽色情 3 违法信息 4人身攻击我 5有害信息
    A(1,"垃圾营销"),
    B(2,"淫秽色情"),
    C(3,"违法信息"),
    D(4,"人身攻击我"),
    E(5,"有害信息");

    private Integer code;
    private String name;

    CommentReportProblem(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static CommentReportProblem get(Integer code){
        CommentReportProblem[] values = CommentReportProblem.values();
        for (CommentReportProblem value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
