package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/9/27 19:05.
 */
@Getter
public enum VideoSource {

    local(0,"本地"), remote(1,"远程"), crawler(2, "爬虫"),;

    private Integer value;
    private String name;
    VideoSource(Integer value, String name) {

        this.value = value;
        this.name = name;
    }

    public static VideoSource get(Integer value) {

        if (null != value) {
            for (VideoSource source : VideoSource.values()) {

                if (Objects.equals(value, source.getValue())) {
                    return source;
                }
            }
        }
        return null;
    }
}
