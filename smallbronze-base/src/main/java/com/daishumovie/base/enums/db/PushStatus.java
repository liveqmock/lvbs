package com.daishumovie.base.enums.db;

import lombok.Getter;
import java.util.Objects;
/**
 * Created by feiFan.gou on 2017/10/17 14:42.
 */
@Getter
public enum PushStatus {

    pushing(0, "待推送"),
    success(1, "推送成功"),
    fail(2, "推送失败"),
    cancel(3, "已取消"),
    ;
    private final Integer value;
    private final String name;

    PushStatus(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static PushStatus get(Integer value) {

        if (null != value) {
            for (PushStatus status : PushStatus.values()) {
                if (Objects.equals(value, status.getValue())) {
                    return status;
                }
            }
        }
        return null;
    }
}
