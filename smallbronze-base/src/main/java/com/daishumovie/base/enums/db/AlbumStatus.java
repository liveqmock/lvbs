package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/10/25 10:25.
 */
@Getter
public enum AlbumStatus {

    publishing(0, "待发布"),
    published(1, "已发布"),
    offline(2, "已下线"),
    ;
    private final Integer value;
    private final String name;

    AlbumStatus(final Integer value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static AlbumStatus get(Integer value) {

        if (null != value) {
            for (AlbumStatus status : AlbumStatus.values()) {
                if (Objects.equals(value, status.getValue())) {
                    return status;
                }
            }
        }
        return null;
    }
}
