package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by yang on 2017/9/6.
 */
@Getter
public enum OnlineStatus {

    OFF_LINE("已下线",0),
    ON_LINE("已上线",1);

    private final String name; //名称
    private final Integer value; //数据库存储至

    OnlineStatus(String name, Integer value) {

        this.name = name;
        this.value = value;
    }

    public static OnlineStatus get(Integer value) {

        if (null != value) {
            for (OnlineStatus type : OnlineStatus.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
        }
        return null;
    }
}
