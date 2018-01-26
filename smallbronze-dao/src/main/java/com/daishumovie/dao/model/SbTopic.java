package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbTopic implements Serializable {
    private Integer id;

    /**
     * appId
     */
    private Integer appId;

    /**
     * 话题标题
     */
    private String title;

    /**
     * 话题描述
     */
    private String description;

    /**
     * 频道id
     */
    private Integer channelId;

    /**
     * 发布话题用户id
     */
    private Integer uid;

    /**
     * 题主视频id
     */
    private Integer videoId;

    /**
     * 视频合集 json格式 {"m3u8Url": "m3u8Url文件地址","cover":"http://", "list":[{ "headImg":"用户头像","videoId":1,"uid":1//用户id, "startMillisecond":2//该视频在视频合集里开始播放时间点, "width":1920,"height":1080}]}}
     */
    private String videoCollection;

    /**
     * 关注数
     */
    private Integer followNum;

    /**
     * 顶数
     */
    private Integer praiseNum;

    /**
     * 踩数
     */
    private Integer criticismNum;

    /**
     * 顶与踩差值
     */
    private Integer diffValue;

    /**
     * 后台创建该话题的用户id
     */
    private Integer createOpeUid;

    /**
     * 总回复数
     */
    private Integer replyNum;

    /**
     * 审核状态 0 未审核 1 机器审核已通过 2 机器审核未通过 3 人工审核未通过 4 人工审核已通过
     */
    private Integer auditStatus;

    /**
     * 审核人ID
     */
    private Integer auditOpUid;

    /**
     * 审核人不通过原因
     */
    private String auditDesc;

    /**
     * 取值范围：[“pass”, “review”, “block”], pass:图片正常，review：需要人工审核，block：图片违规
     */
    private String suggestion;

    /**
     * 鉴黄api值越高 取值为[0.00-100.00]
     */
    private Double rate;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 机器审核时间
     */
    private Date machineAuditTime;

    /**
     * 话题来源方式 默认 0 用户 1 内部运营
     */
    private Integer source;

    /**
     * 状态 0 已下线 1 已发布 2 待发布 3 已删除
     */
    private Integer status;

    /**
     * 后台发布人
     */
    private Integer publisher;

    /**
     * 参与活动的id
     */
    private Integer activityId;

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 后台发布人
     */
    private Date publishTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoCollection() {
        return videoCollection;
    }

    public void setVideoCollection(String videoCollection) {
        this.videoCollection = videoCollection;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getCriticismNum() {
        return criticismNum;
    }

    public void setCriticismNum(Integer criticismNum) {
        this.criticismNum = criticismNum;
    }

    public Integer getDiffValue() {
        return diffValue;
    }

    public void setDiffValue(Integer diffValue) {
        this.diffValue = diffValue;
    }

    public Integer getCreateOpeUid() {
        return createOpeUid;
    }

    public void setCreateOpeUid(Integer createOpeUid) {
        this.createOpeUid = createOpeUid;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditOpUid() {
        return auditOpUid;
    }

    public void setAuditOpUid(Integer auditOpUid) {
        this.auditOpUid = auditOpUid;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getMachineAuditTime() {
        return machineAuditTime;
    }

    public void setMachineAuditTime(Date machineAuditTime) {
        this.machineAuditTime = machineAuditTime;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}