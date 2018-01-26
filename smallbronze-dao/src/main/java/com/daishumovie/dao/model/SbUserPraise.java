package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbUserPraise implements Serializable {
    private Integer id;

    private Integer appId;

    private Long targetId;

    /**
     * 1 话题 2 频道 3用户 4回复 5合辑
     */
    private Integer targetType;

    private Integer uid;

    private String did;

    /**
     *  1 顶 2 踩  3 关注
     */
    private Integer type;

    /**
     * 未读个数 如果关注对象为话题 未读回复数 如果关注对象频道 未读话题数
     */
    private Integer unReadNum;

    /**
     * 0删除 1正常
     */
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

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(Integer unReadNum) {
        this.unReadNum = unReadNum;
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