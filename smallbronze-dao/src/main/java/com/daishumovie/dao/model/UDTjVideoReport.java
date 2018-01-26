package com.daishumovie.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UDTjVideoReport implements Serializable {
    private Integer id;

    /**
     * 视频id
     */
    private Integer video_id;

    /**
     * 渠道id
     */
    private Integer channel_id;

    /**
     * 播放次数
     */
    private Integer play_pv;

    /**
     * 播放用户数
     */
    private Integer play_uv;

    /**
     * 小于5秒的播放总量
     */
    private Float five_second_pv;

    /**
     * 大于5秒-小于90%的播放总量
     */
    private Float five_ninety_pv;

    /**
     * 大于90%的播放总量
     */
    private Float greater_ninety_pv;

    /**
     * 总播放时长
     */
    private Integer play_t_time;

    /**
     * 评论数
     */
    private Integer comments;

    /**
     * 点赞数
     */
    private Integer praise_num;

    /**
     * 分享数
     */
    private Integer share_num;

    /**
     * 弹幕数
     */
    private Integer barrage_num;

    /**
     * 日期
     */
    private Date t;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Integer video_id) {
        this.video_id = video_id;
    }

    public Integer getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(Integer channel_id) {
        this.channel_id = channel_id;
    }

    public Integer getPlay_pv() {
        return play_pv;
    }

    public void setPlay_pv(Integer play_pv) {
        this.play_pv = play_pv;
    }

    public Integer getPlay_uv() {
        return play_uv;
    }

    public void setPlay_uv(Integer play_uv) {
        this.play_uv = play_uv;
    }

    public Float getFive_second_pv() {
        return five_second_pv;
    }

    public void setFive_second_pv(Float five_second_pv) {
        this.five_second_pv = five_second_pv;
    }

    public Float getFive_ninety_pv() {
        return five_ninety_pv;
    }

    public void setFive_ninety_pv(Float five_ninety_pv) {
        this.five_ninety_pv = five_ninety_pv;
    }

    public Float getGreater_ninety_pv() {
        return greater_ninety_pv;
    }

    public void setGreater_ninety_pv(Float greater_ninety_pv) {
        this.greater_ninety_pv = greater_ninety_pv;
    }

    public Integer getPlay_t_time() {
        return play_t_time;
    }

    public void setPlay_t_time(Integer play_t_time) {
        this.play_t_time = play_t_time;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(Integer praise_num) {
        this.praise_num = praise_num;
    }

    public Integer getShare_num() {
        return share_num;
    }

    public void setShare_num(Integer share_num) {
        this.share_num = share_num;
    }

    public Integer getBarrage_num() {
        return barrage_num;
    }

    public void setBarrage_num(Integer barrage_num) {
        this.barrage_num = barrage_num;
    }

    public Date getT() {
        return t;
    }

    public void setT(Date t) {
        this.t = t;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}