package com.daishumovie.base.enums.db;

import lombok.Getter;

@Getter
public enum YesNoEnum {

    NO(0,"No."),
    YES(1,"Yes."),
    TEMPORARY(2,"临时下线")
    ;

    private Integer code;
    private String desc;

    YesNoEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static YesNoEnum get(Integer code){
        if (code == null) return null;
        YesNoEnum[] values = YesNoEnum.values();
        for (YesNoEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
