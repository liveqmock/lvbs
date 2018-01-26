package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class DsmUserSettings implements Serializable {
    private Integer id;

    private Integer appId;

    private Integer uid;

    private String did;

    /**
     * 接收订阅推送
     */
    private Integer receiveNotification;

    /**
     * WIFI下自动播放
     */
    private Integer autoplay;

    /**
     * 评论弹幕
     */
    private Integer allowBulletScreen;

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

    public Integer getReceiveNotification() {
        return receiveNotification;
    }

    public void setReceiveNotification(Integer receiveNotification) {
        this.receiveNotification = receiveNotification;
    }

    public Integer getAutoplay() {
        return autoplay;
    }

    public void setAutoplay(Integer autoplay) {
        this.autoplay = autoplay;
    }

    public Integer getAllowBulletScreen() {
        return allowBulletScreen;
    }

    public void setAllowBulletScreen(Integer allowBulletScreen) {
        this.allowBulletScreen = allowBulletScreen;
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