package com.daishumovie.base.enums.db;

import lombok.Getter;


@Getter
public enum PushTagEnum {

    not_notify,
    notify;

    public static PushTagEnum get(String value){
        PushTagEnum[] values = PushTagEnum.values();
        for (PushTagEnum v : values) {
            if(v.name().equals(value)){
                return v;
            }
        }
        return null;
    }

}
