package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SbUploadVideos implements Serializable {
    private Integer id;

    /**
     * 视频名称
     */
    private String name;

    /**
     * 用户提交视频地址
     */
    private String downUrl;

    /**
     * 上传到本地视频地址
     */
    private String localUrl;

    /**
     * 视频上传类型 0 本地 1 网络地址 2爬虫
     */
    private Integer type;

    /**
     * 网络地址 是否已下载，0 否 1是
     */
    private Integer isDownload;

    /**
     * 视频上传者
     */
    private Integer operator;

    /**
     * 上传状态 0 等待上传 1 正在上传 2上传失败 3 上传完成
     */
    private Integer status;

    /**
     * 错误数
     */
    private Integer errNum;

    /**
     * 为了视频上传失败能找到topic对象
     */
    private Integer topicId;

    /**
     * 视频创建时间
     */
    private Date createTime;

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

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(Integer isDownload) {
        this.isDownload = isDownload;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getErrNum() {
        return errNum;
    }

    public void setErrNum(Integer errNum) {
        this.errNum = errNum;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}