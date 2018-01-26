package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbUserReport implements Serializable {
    private Integer id;

    private Integer appId;

    /**
     * 举报人id
     */
    private Integer uid;

    /**
     * 举报内容id，具体是什么id，根据type 确定
     */
    private Integer contentId;

    /**
     * 举报类型 1 视频举报 2 评论举报 3 用户举报
     */
    private Integer type;

    /**
     * 问题 1：色情低俗 2 违规违法 3 标题党、封面党、骗点击   4播放问题 5内容质量差 6 疑似抄袭 7其他问题
     */
    private Integer problem;

    /**
     * 状态 0：待处理 1 ： 已处理 2：暂不处理
     */
    private Integer status;

    /**
     * 审核说明
     */
    private String auditDesc;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核人
     */
    private Integer auditId;

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

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProblem() {
        return problem;
    }

    public void setProblem(Integer problem) {
        this.problem = problem;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
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