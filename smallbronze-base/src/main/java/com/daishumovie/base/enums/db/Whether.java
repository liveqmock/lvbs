package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/6/6 17:27.
 */
@Getter
public enum Whether {

    no(0,"否"),
    yes(1,"是");

    private final Integer value;
    private final String name;

    Whether(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static Whether get(Integer value){

        if (null != value) {
            Whether[] values = Whether.values();
            for (Whether v : values) {
                if(v.value.equals(value)){
                    return v;
                }
            }
        }
        return null;
    }

    /**
     * 取反
     * @param value
     * @return
     */
    public static Integer invert(Integer value) {

        if (null != value) {
            Whether[] values = Whether.values();
            for (Whether v : values) {
                if(!v.value.equals(value)){
                    return v.value;
                }
            }
        }
        return null;
    }
}
