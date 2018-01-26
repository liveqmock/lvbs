package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/8/26 18:14.
 */
@Getter
public enum CategoryType {

    //音乐,滤镜,贴纸,动图,字幕

    music("音乐",1),
    filter("滤镜",2),
    sticker("贴纸",3),
    gif("动图",4),
    subtitle("字幕",5);

    private final String name; //名称
    private final Integer value; //数据库存储至

    CategoryType(String name, Integer value) {

        this.name = name;
        this.value = value;
    }

    public static CategoryType get(Integer value) {

        if (null != value) {
            for (CategoryType type : CategoryType.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
        }
        return null;
    }

}
