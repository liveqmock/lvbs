package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbPushTask implements Serializable {
    private Integer id;

    /**
     * 推送文案
     */
    private String alert;

    /**
     * 推送平台 0:ios 1:android 2:all
     */
    private Integer platform;

    /**
     * 推送方式 0:立即推送 1:定时推送
     */
    private Integer way;

    /**
     * 推送时间（推送为定时推送时候有值）
     */
    private Date pushTime;

    /**
     * 跳转类型 1 话题/视频 2 用户 3 APP首页 4 WebUrl 5 活动 6 合辑
     */
    private Integer targetType;

    /**
     * 视频id
     */
    private String targetId;

    /**
     * 推送状态 0:待推送 1:推送成功 2:推动失败 3:已取消
     */
    private Integer status;

    /**
     * 请求极光参数json
     */
    private String inputJson;

    /**
     * 极光返回结果json
     */
    private String outputJson;

    /**
     * 请求极光报告返回结果的json
     */
    private String reportOutputJson;

    /**
     * 极光推送返回的msg_id，请求report时候用。
     */
    private String msgId;

    /**
     * IOS成功接收量
     */
    private Integer iosReceivedCount;

    /**
     * IOS点击量
     */
    private Integer iosClickTimes;

    /**
     * 安卓成功接收量
     */
    private Integer androidReceivedCount;

    /**
     * 安卓点击量
     */
    private Integer androidClickTimes;

    /**
     * 创建推送任务用户id
     */
    private Integer pusherId;

    /**
     * 更新者
     */
    private Integer modifier;

    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInputJson() {
        return inputJson;
    }

    public void setInputJson(String inputJson) {
        this.inputJson = inputJson;
    }

    public String getOutputJson() {
        return outputJson;
    }

    public void setOutputJson(String outputJson) {
        this.outputJson = outputJson;
    }

    public String getReportOutputJson() {
        return reportOutputJson;
    }

    public void setReportOutputJson(String reportOutputJson) {
        this.reportOutputJson = reportOutputJson;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getIosReceivedCount() {
        return iosReceivedCount;
    }

    public void setIosReceivedCount(Integer iosReceivedCount) {
        this.iosReceivedCount = iosReceivedCount;
    }

    public Integer getIosClickTimes() {
        return iosClickTimes;
    }

    public void setIosClickTimes(Integer iosClickTimes) {
        this.iosClickTimes = iosClickTimes;
    }

    public Integer getAndroidReceivedCount() {
        return androidReceivedCount;
    }

    public void setAndroidReceivedCount(Integer androidReceivedCount) {
        this.androidReceivedCount = androidReceivedCount;
    }

    public Integer getAndroidClickTimes() {
        return androidClickTimes;
    }

    public void setAndroidClickTimes(Integer androidClickTimes) {
        this.androidClickTimes = androidClickTimes;
    }

    public Integer getPusherId() {
        return pusherId;
    }

    public void setPusherId(Integer pusherId) {
        this.pusherId = pusherId;
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
}