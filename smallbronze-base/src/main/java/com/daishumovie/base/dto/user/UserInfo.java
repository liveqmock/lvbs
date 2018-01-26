package com.daishumovie.base.dto.user;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -8002868451504832540L;

	private String nickname;

	private String avatar;

	private String introduce;

	private Integer userType;

	private Integer idType;

	private Integer authStatus;
	
	private Integer sex;
	
	private String videoName;
	
	private Integer videoType;
	
	private Integer unreadCount;
	
	private Integer uid;

	private String mobile;

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

}
