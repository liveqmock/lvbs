package com.daishumovie.base.dto.activity;

import lombok.Data;

/**
 * @author Cheng Yufei
 * @create 2017-11-13 14:59
 **/
@Data
public class ShowAward {

    /**
     *  0: 不展示  1：展示
     */
    private Integer isShow;

    private String webUrl;

    private String icon;
}
