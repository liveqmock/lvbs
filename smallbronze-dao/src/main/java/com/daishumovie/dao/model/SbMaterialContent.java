package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbMaterialContent implements Serializable {
    /**
     * 素材内容主键
     */
    private Integer id;

    /**
     * 素材名称
     */
    private String name;

    /**
     * 素材icon
     */
    private String icon;

    /**
     * 预览地址 
     */
    private String previewUrl;

    /**
     * 如果是音乐，为音乐播放时长，其它可以为空
     */
    private Integer duration;

    /**
     * 素材下载地址
     */
    private String contentPath;

    /**
     * 素材状态 0 废弃 1 正常
     */
    private Integer status;

    /**
     * 素材是否上架 0 下架 1 上架，前台只能去以上架的内容
     */
    private Integer isOnShelf;

    /**
     * 上架操作者
     */
    private Integer operatorId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsOnShelf() {
        return isOnShelf;
    }

    public void setIsOnShelf(Integer isOnShelf) {
        this.isOnShelf = isOnShelf;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}