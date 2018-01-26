package com.daishumovie.base.enums.db;

import lombok.Getter;
import java.util.Objects;
/**
 * Created by feiFan.gou on 2017/10/20 11:30.
 */
@Getter
public enum ActivityStatus {

    un_started(0, "未开始"),
    preheating(1, "预热中"),
    ongoing(2, "进行中"),
    ended(3, "已结束"),
    ;
    private final Integer value;
    private final String name;

    ActivityStatus(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static ActivityStatus get(Integer value) {

        if (null != value) {
            for (ActivityStatus status : ActivityStatus.values()) {
                if (Objects.equals(value, status.getValue())) {
                    return status;
                }
            }
        }
        return null;
    }
}
