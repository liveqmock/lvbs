package com.daishumovie.base.dto.mine;

import lombok.Data;

/**
 * @author zhuruisong on 2017/9/9
 * @since 1.0
 */
@Data
public class MyInfoDto {

    /**
     * id
     */
    private Integer uid;

    /**
     * 昵称
     */
    private String nickname;

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
    private String followNum;

    /**
     * 粉丝数
     */
    private String fansNum;

    /**
     * 获赞数
     */
    private Integer praiseNum;
    /**
     * 作品数
     */
    private String opusNum;
    /**
     * 喜欢作品数
     */
    private String likeNum;
    /**
     * 喜欢总数
     */
    private String likeTotalNum;
    /**
     * 喜欢合辑数
     */
    private String likeAlbumNum;
    /**
     * 参数的活动数
     */
    private String activifyNum;
    /**
     * 播放历史数量
     */
    private String watchHistoryNum;

    /**
     * 注册手机号
     */
    private String mobile;
    /**
     * 是否关注
     */
    private Integer isFollow;

    /**
     * 是否在对方黑名单
     */
    private Integer isInBlack;

    /**
     * 用户类型 0：UGC用户 1：PGC用户
     */
    private Integer userType;

}
