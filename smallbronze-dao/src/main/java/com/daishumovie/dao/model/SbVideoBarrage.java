package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbVideoBarrage implements Serializable {
    private Integer id;

    private Integer appId;

    /**
     * 视频id
     */
    private Integer videoId;

    /**
     * 发布话题用户id
     */
    private Integer uid;

    /**
     * 弹幕内容
     */
    private String content;

    /**
     * 发送弹幕数时间点
     */
    private Integer timeMillisecond;

    private Date createTime;

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

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTimeMillisecond() {
        return timeMillisecond;
    }

    public void setTimeMillisecond(Integer timeMillisecond) {
        this.timeMillisecond = timeMillisecond;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}