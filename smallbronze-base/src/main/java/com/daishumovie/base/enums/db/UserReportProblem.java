package com.daishumovie.base.enums.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

//用户举报
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserReportProblem {
	// 1 违法信息 2人身攻击

    A(1,"违法信息"),
    B(2,"人身攻击"),
   ;


    private Integer code;
    private String name;

    UserReportProblem(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static UserReportProblem get(Integer code){
        UserReportProblem[] values = UserReportProblem.values();
        for (UserReportProblem value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
