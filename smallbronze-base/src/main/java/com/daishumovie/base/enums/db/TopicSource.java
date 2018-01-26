package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by yang on 2017/9/6.
 * 0 用户 1 内部运营
 */
@Getter
public enum TopicSource {

    USER("用户",0),
    OWER("内部运营",1);


    private  String name; //名称
    private  Integer value; //数据库存储至

    TopicSource(String name, Integer value) {

        this.name = name;
        this.value = value;
    }

    public static TopicSource get(Integer value) {

        if (null != value) {
            for (TopicSource type : TopicSource.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
        }
        return null;
    }
}
