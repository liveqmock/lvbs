package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbActivity implements Serializable {
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 活动话题#xxx#
     */
    private String topic;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动封面
     */
    private String cover;

    /**
     * 活动缩略图
     */
    private String thumbCover;

    /**
     * 活动类型 1 投稿
     */
    private Integer type;

    /**
     * 活动状态 0：未开始 1：预热中 2：进行中 3：已结束
     */
    private Integer status;

    /**
     * 是否上线 0: 否 1: 是
     */
    private Integer whetherOnline;

    /**
     * 活动预热时间
     */
    private Date preTime;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 回复数
     */
    private Integer replyNum;

    private Integer appId;

    /**
     * 创建活动用户id
     */
    private Integer operatorId;

    /**
     * 修改人
     */
    private Integer modifier;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 活动规则 富文本类型
     */
    private String instruction;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getThumbCover() {
        return thumbCover;
    }

    public void setThumbCover(String thumbCover) {
        this.thumbCover = thumbCover;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWhetherOnline() {
        return whetherOnline;
    }

    public void setWhetherOnline(Integer whetherOnline) {
        this.whetherOnline = whetherOnline;
    }

    public Date getPreTime() {
        return preTime;
    }

    public void setPreTime(Date preTime) {
        this.preTime = preTime;
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

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}