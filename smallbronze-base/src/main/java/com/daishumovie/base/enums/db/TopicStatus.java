package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/9/27 17:03.
 */
@Getter
public enum TopicStatus {

    publishing(2, "待发布"),
    published(1, "已发布"),
    offline(0, "已下线"),
    deleted(3, "已删除"),
    ;

    private Integer value;
    private String name;

    TopicStatus(Integer value, String name) {

        this.value = value;
        this.name = name;
    }

    public static TopicStatus get(Integer value) {

        if (null != value) {
            for (TopicStatus status : TopicStatus.values()) {
                if (Objects.equals(value, status.getValue())) {
                    return status;
                }
            }
        }
        return null;
    }
}
