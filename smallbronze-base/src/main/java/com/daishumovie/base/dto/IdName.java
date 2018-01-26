package com.daishumovie.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/3/23
 * @since 1.0
 */
@Data
public class IdName implements Serializable {

    private String id;
    private String name;

    public IdName(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
