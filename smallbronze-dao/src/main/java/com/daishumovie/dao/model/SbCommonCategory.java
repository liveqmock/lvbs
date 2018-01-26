package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbCommonCategory implements Serializable {
    /**
     * 分类id id根据类型值生成比如音乐 1 * 1000 开始自增 滤镜 2*1000 开始自增
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类icon
     */
    private String icon;

    /**
     * 类型 1 音乐 2 滤镜 3 贴纸 4 动图 5 字幕
     */
    private Integer type;

    /**
     * 0：废弃；1：正常
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;

    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private Integer operatorId;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}