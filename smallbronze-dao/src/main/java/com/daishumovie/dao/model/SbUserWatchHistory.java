package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbUserWatchHistory implements Serializable {
    private Integer id;

    private Integer appId;

    private Integer uid;

    private String did;

    /**
     * 话题id
     */
    private Integer topicId;

    private Integer videoId;

    private Integer watchTimes;

    private Integer status;

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

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getWatchTimes() {
        return watchTimes;
    }

    public void setWatchTimes(Integer watchTimes) {
        this.watchTimes = watchTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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