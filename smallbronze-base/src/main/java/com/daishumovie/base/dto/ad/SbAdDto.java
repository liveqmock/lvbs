package com.daishumovie.base.dto.ad;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@ToString
public class SbAdDto implements Serializable {

    private Integer id;

    /**
     * 广告名称
     */
    private String name;

    /**
     * 广告封面图
     */
    private String adCover;

    /**
     * 广告跳转地址视频id、活动id、网页url
     */
    private String target;

    /**
     * 广告跳转类型 1 视频 2 活动 3 网页地址
     */
    private Integer targetType;

    /**
     * 如果是启动广告,则为启动广告显示时长，轮播广告时为轮播广告间隔切换时间
     */
    private Integer duration;



}