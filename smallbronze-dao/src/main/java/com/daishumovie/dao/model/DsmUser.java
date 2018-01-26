package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class DsmUser implements Serializable {
    private Integer uid;

    /**
     * 来源应用id
     */
    private Integer appId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 自我介绍
     */
    private String introduce;

    /**
     * 1男 2女 0未知
     */
    private Integer sex;

    /**
     * 0：禁用\n1：在用
     */
    private Integer status;

    /**
     * 用户类型 0：UGC用户 1：PGC用户
     */
    private Integer type;

    /**
     * 是否有发送话题权限 0 否 1有
     */
    private Integer isTopicAuth;

    /**
     * 是否有回复评论权限 0 否 1有
     */
    private Integer isReplyAuth;

    /**
     * 关注数
     */
    private Integer followNum;

    /**
     * 粉丝数
     */
    private Integer fansNum;

    /**
     * 获赞数
     */
    private Integer praiseNum;

    /**
     * 喜欢数
     */
    private Integer likeNum;

    /**
     * 合辑喜欢数
     */
    private Integer likeAlbumNum;

    /**
     * 注册手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐值
     */
    private String securitySalt;

    /**
     * 微信唯一id
     */
    private String wxUnionId;

    /**
     * 微博的id
     */
    private String wbUid;

    /**
     * qq的id
     */
    private String qqOpenid;

    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 是否是虚拟用户 0：否 1：是
     */
    private Integer fictitious;

    /**
     * 发布量
     */
    private Integer publishCount;

    /**
     * 后台操作人ID,默认为-1,表示暂时无人操作过
     */
    private Integer operatorId;

    private static final long serialVersionUID = 1L;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsTopicAuth() {
        return isTopicAuth;
    }

    public void setIsTopicAuth(Integer isTopicAuth) {
        this.isTopicAuth = isTopicAuth;
    }

    public Integer getIsReplyAuth() {
        return isReplyAuth;
    }

    public void setIsReplyAuth(Integer isReplyAuth) {
        this.isReplyAuth = isReplyAuth;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getLikeAlbumNum() {
        return likeAlbumNum;
    }

    public void setLikeAlbumNum(Integer likeAlbumNum) {
        this.likeAlbumNum = likeAlbumNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecuritySalt() {
        return securitySalt;
    }

    public void setSecuritySalt(String securitySalt) {
        this.securitySalt = securitySalt;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public String getWbUid() {
        return wbUid;
    }

    public void setWbUid(String wbUid) {
        this.wbUid = wbUid;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getFictitious() {
        return fictitious;
    }

    public void setFictitious(Integer fictitious) {
        this.fictitious = fictitious;
    }

    public Integer getPublishCount() {
        return publishCount;
    }

    public void setPublishCount(Integer publishCount) {
        this.publishCount = publishCount;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}