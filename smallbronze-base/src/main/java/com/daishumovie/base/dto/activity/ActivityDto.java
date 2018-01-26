package com.daishumovie.base.dto.activity;

import lombok.Data;

import java.util.Date;

/**
 * @author Cheng Yufei
 * @create 2017-10-19 11:33
 **/
@Data
public class ActivityDto {
    private Integer id;

    /**
     * 标题
     */
    private String title;
    /**
     * 活动话题#xxx#
     */
    private String topic;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动缩略图
     */
    private String thumbCover;

    /**
     * 活动封面
     */
    private String cover;


    /**
     * 活动规则 富文本类型
     */
    private String instruction;

    /**
     * 活动是否预热状态 ： 0 否  1 是
     */
    private Integer preheatStatus;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 活动剩余时间
     */
    private String restOfTime;

    /**
     * 状态文本
     */
    private String activityStatusText;

    /**
     * 状态标识 1, "预热中" 2：进行中  3. 已结束
     */
    private Integer activityStatus;

    /**
     排序使用
     */
    private Integer activityStatusNumForOrder;

    /**
     * 回复数
     */
    private String replyNum;

    /**
     * 该活动的总话题数
     */
    private Integer topicTotal;

    /**
     * 是否已经报名
     */
    private Integer hasSignUp;

}
