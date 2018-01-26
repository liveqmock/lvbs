package com.daishumovie.dao.model;

import java.io.Serializable;

/**
 * Created by yang on 2017/10/26.
 */
public class UdMap implements Serializable {

    private Integer key;
    private Integer value;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
