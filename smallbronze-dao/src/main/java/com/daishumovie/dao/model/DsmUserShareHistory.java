package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class DsmUserShareHistory implements Serializable {
    private Integer id;

    private Integer appId;

    private Integer uid;

    private String did;

    private Integer topicId;

    private Integer channelId;

    private Integer shareTimes;

    private Integer status;

    /**
     * 分享平台 0 微信朋友圈 1 微信好友 2 QQ好友 3 QQ空间 4 新浪微博
     */
    private Integer sharePlat;

    private Date createTime;

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

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSharePlat() {
        return sharePlat;
    }

    public void setSharePlat(Integer sharePlat) {
        this.sharePlat = sharePlat;
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