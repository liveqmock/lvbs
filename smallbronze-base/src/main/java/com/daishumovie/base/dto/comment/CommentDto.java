package com.daishumovie.base.dto.comment;

import java.io.Serializable;
import java.util.List;

import com.daishumovie.base.dto.topic.VideoDto;
import com.daishumovie.base.dto.user.UserBriefDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto implements Serializable {

	private static final long serialVersionUID = 133243487768614402L;

	private Integer id;

	private String content;

	private UserBriefDto user;
	
	private UserBriefDto replyUser;

	private String time;

	private String diffValue;

	private String replyNum;

	// 顶踩状态0 没有顶踩过 1 顶过 2 踩过
	private Integer praiseStatus;

	private List<CommentDto> replies;

	private List<VideoDto> medias;

	//一级评论中的目标用户
	private UserBriefDto targetUser;
	//一级评论中的目标用户的内容
	private String targetContent;
	/**
	 * 是否可删除 1 可以 0 不可以；
	 */
	private Integer deletable;
}
