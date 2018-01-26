package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbAd implements Serializable {
    private Integer id;

    /**
     * 如果是轮播广告 orders 不为空
     */
    private Integer orders;

    /**
     * 广告名称
     */
    private String name;

    /**
     * 广告封面图
     */
    private String adCover;

    /**
     * 跳转类型 1 话题/视频 2 用户 3 APP首页 4 WebUrl 5 活动 6 合辑
     */
    private Integer targetType;

    /**
     * 广告跳转地址
     */
    private String target;

    /**
     * 广告开始时间
     */
    private Date startTime;

    /**
     * 广告结束时间
     */
    private Date endTime;

    /**
     * 创建者id
     */
    private Integer creatorId;

    /**
     * 创建者名称
     */
    private String creatorName;

    /**
     * 状态 0删除 1 正常
     */
    private Integer status;

    /**
     * 广告类型 0 启动广告 1 轮播广告
     */
    private Integer adType;

    /**
     * 如果是启动广告,则为启动广告显示时长，轮播广告时为轮播广告间隔切换时间
     */
    private Integer duration;

    private Date createTime;

    /**
     * 更新时间
     */
    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdCover() {
        return adCover;
    }

    public void setAdCover(String adCover) {
        this.adCover = adCover;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}