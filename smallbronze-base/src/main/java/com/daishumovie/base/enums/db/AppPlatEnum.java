package com.daishumovie.base.enums.db;

import lombok.Getter;

@Getter
public enum AppPlatEnum {

	IOS(1,"IOS"),
	ANDROID(2,"ANDROID");

    private Integer code;
    private String name;

    AppPlatEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static AppPlatEnum get(Integer code){
        AppPlatEnum[] values = AppPlatEnum.values();
        for (AppPlatEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
    
    public static AppPlatEnum get(String name){
        AppPlatEnum[] values = AppPlatEnum.values();
        for (AppPlatEnum value : values) {
            if(value.getName().equals(name)){
                return value;
            }
        }
        return null;
    }
}
