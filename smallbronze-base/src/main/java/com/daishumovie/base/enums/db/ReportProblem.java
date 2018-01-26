package com.daishumovie.base.enums.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReportProblem {
	// 1：色情低俗 2 违规违法 3 标题党、封面党、骗点击   4播放问题 5内容质量差 6 疑似抄袭 7其他问题
    A(1,"色情低俗"),
    B(2,"违规违法"),
    C(3,"标题党、封面党、骗点击"),
    D(4,"播放问题"),
    E(5,"内容质量差"),
    F(6,"疑似抄袭"),
    G(7,"其他问题");

    private Integer code;
    private String name;

    ReportProblem(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static ReportProblem get(Integer code){
        ReportProblem[] values = ReportProblem.values();
        for (ReportProblem value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

}
