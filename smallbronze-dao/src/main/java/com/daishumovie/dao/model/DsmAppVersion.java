package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class DsmAppVersion implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer appId;

    /**
     * 版本号
     */
    private String versionNum;

    /**
     * 序号暂时不用
     */
    private Integer build;

    /**
     * 平台 1 IOS 2Android
     */
    private Integer plat;

    /**
     * 最小支持版本号
     */
    private String minVersion;

    /**
     * 更新描述
     */
    private String updateDesc;

    /**
     * 备注
     */
    private String remark;

    /**
     * 下载地址
     */
    private String downUrl;

    /**
     * 渠道ID 默认10000 是我司
     */
    private String channelId;

    /**
     * 操作者ID
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

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getBuild() {
        return build;
    }

    public void setBuild(Integer build) {
        this.build = build;
    }

    public Integer getPlat() {
        return plat;
    }

    public void setPlat(Integer plat) {
        this.plat = plat;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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