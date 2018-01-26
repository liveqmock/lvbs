package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbUserComment implements Serializable {
    private Integer id;

    private Integer appId;

    private Integer targetId;

    /**
     * 1 话题 2 频道 3用户 4回复 5 合辑 6 活动
     */
    private Integer targetType;

    /**
     * 回复父id
     */
    private Integer pid;

    /**
     * 目标用户id
     */
    private Integer puid;

    /**
     * 目标评论id
     */
    private Integer pcid;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 顶数
     */
    private Integer praiseNum;

    /**
     * 踩数
     */
    private Integer criticismNum;

    /**
     * 评论总数
     */
    private Integer replyNum;

    /**
     * 顶与踩差值
     */
    private Integer diffValue;

    /**
     * 回复.评论内容（文本）
     */
    private String content;

    /**
     * 回复.评论图片json格式对象 [{ "oriUrl":"原始图片地址","dimension":"50x60"}]
     */
    private String imgList;

    /**
     * 回复.评论视频id
     */
    private Integer videoId;

    /**
     * 状态 1 正常 0 废弃
     */
    private Integer status;

    /**
     * 审核状态 0 未审核 1 机器审核已通过 2 机器审核未通过 3 人工审核未通过 4 人工审核已通过
     */
    private Integer auditStatus;

    /**
     * 审核人
     */
    private Integer auditOpUid;

    /**
     * 人工审核不通过原因
     */
    private String auditDesc;

    /**
     * 取值范围：[“pass”, “review”, “block”], pass:图片正常，review：需要人工审核，block：图片违规
     */
    private String suggestion;

    /**
     * 鉴黄api值越高 取值为[0.00-100.00]
     */
    private Float rate;

    /**
     * 图片分类 normal 正常图片 sexy 性感图片  porn 色情图片
     */
    private String label;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 机器审核时间
     */
    private Date machineAuditTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
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

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPuid() {
        return puid;
    }

    public void setPuid(Integer puid) {
        this.puid = puid;
    }

    public Integer getPcid() {
        return pcid;
    }

    public void setPcid(Integer pcid) {
        this.pcid = pcid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getCriticismNum() {
        return criticismNum;
    }

    public void setCriticismNum(Integer criticismNum) {
        this.criticismNum = criticismNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getDiffValue() {
        return diffValue;
    }

    public void setDiffValue(Integer diffValue) {
        this.diffValue = diffValue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgList() {
        return imgList;
    }

    public void setImgList(String imgList) {
        this.imgList = imgList;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditOpUid() {
        return auditOpUid;
    }

    public void setAuditOpUid(Integer auditOpUid) {
        this.auditOpUid = auditOpUid;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getMachineAuditTime() {
        return machineAuditTime;
    }

    public void setMachineAuditTime(Date machineAuditTime) {
        this.machineAuditTime = machineAuditTime;
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