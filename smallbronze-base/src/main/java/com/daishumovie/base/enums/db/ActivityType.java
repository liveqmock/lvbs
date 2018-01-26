package com.daishumovie.base.enums.db;

import lombok.Getter;
import java.util.Objects;
/**
 * Created by feiFan.gou on 2017/10/20 18:27.
 */
@Getter
public enum ActivityType {

    contribute(1, "投稿"),
    awards(2, "抽奖"),
    ;
    private final Integer value;
    private final String name;

    ActivityType(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static ActivityType get(Integer value) {

        if (null != value) {
            for (ActivityType type : ActivityType.values()) {
                if (Objects.equals(value, type.getValue())) {
                    return type;
                }
            }
        }
        return null;
    }
}
