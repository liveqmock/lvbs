package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbVideo implements Serializable {
    private Integer id;

    /**
     * 补刀视频id
     */
    private Integer refId;

    private Integer appId;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 原视频url
     */
    private String oriUrl;

    /**
     * 格式化后视频url
     */
    private String formatUrl;

    /**
     * m3u8文件格式地址
     */
    private String hlsUrl;

    /**
     * 视频时长
     */
    private Float duration;

    /**
     * 视频尺寸 宽*高
     */
    private String dimension;

    /**
     * 视频大小（字节）
     */
    private Integer size;

    /**
     * 视频切图
     */
    private String cover;

    /**
     * 播放数
     */
    private Integer playNum;

    /**
     * 虚拟播放数
     */
    private Integer vPlayNum;

    /**
     * 弹幕数
     */
    private Integer barrageNum;

    /**
     * 虚拟弹幕数
     */
    private Integer vBarrageNum;

    /**
     * 视频来源 0 用户 1 内部运营
     */
    private Integer source;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
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

    public String getOriUrl() {
        return oriUrl;
    }

    public void setOriUrl(String oriUrl) {
        this.oriUrl = oriUrl;
    }

    public String getFormatUrl() {
        return formatUrl;
    }

    public void setFormatUrl(String formatUrl) {
        this.formatUrl = formatUrl;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    public Integer getvPlayNum() {
        return vPlayNum;
    }

    public void setvPlayNum(Integer vPlayNum) {
        this.vPlayNum = vPlayNum;
    }

    public Integer getBarrageNum() {
        return barrageNum;
    }

    public void setBarrageNum(Integer barrageNum) {
        this.barrageNum = barrageNum;
    }

    public Integer getvBarrageNum() {
        return vBarrageNum;
    }

    public void setvBarrageNum(Integer vBarrageNum) {
        this.vBarrageNum = vBarrageNum;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
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