package com.daishumovie.base.dto.user;

import lombok.Data;

/**
 * 关注用户列表
 * @author Cheng Yufei
 * @create 2017-09-08 14:52
 **/
@Data
public class UserFollowDto {

    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 1男 2女 0未知
     */
    private Integer sex;

    // 介绍
    private String introduce;

    /**
     * 用户类型 0：UGC 1：PGC
     */
    private Integer userType;
}
