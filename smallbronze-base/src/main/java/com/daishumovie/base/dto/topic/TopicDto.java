package com.daishumovie.base.dto.topic;

import java.io.Serializable;
import java.util.List;

import com.daishumovie.base.dto.activity.ActivityDto;
import com.daishumovie.base.dto.category.CategoryDto;
import com.daishumovie.base.dto.user.UserBriefDto;

import com.daishumovie.base.dto.user.UserFollowDto;
import com.daishumovie.base.enums.front.BaseUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicDto implements Serializable {

	private static final long serialVersionUID = 133243487768614402L;

	private Integer id;

	private String title;

	private String describe;

	private UserBriefDto user;

	//详情页 关注话题的用户名称
	private List<UserFollowDto> followUsers;

	private CategoryDto category;

	private String followNum;

	private String diffValue;

	private String replyNum;
	//是否关注话题
	private Integer hasFollow;
	//是否关注话题的发布者
	private Integer isFollowUser;
	// 顶踩状态 : 0 没有顶踩过 1 顶过 2 踩过
	private Integer praiseStatus;

	private List<VideoDto> videos;
	// 关注话题列表中题主发布的视频；
	private VideoDto video;

	private String unReadNum;
	/**
	 * 审核状态 0 未审核 1 机器审核已通过 2 机器审核未通过 3 人工审核未通过 4 人工审核已通过
	 */
	private Integer auditStatus;

	private String auditDesc;

	//详情页中 xx 天/小时/分钟 前
	private String time;

	private Integer activityId;

	private ActivityDto activity;

	//活动详情页中关注量超过阀值的排序（1,2,3）字段
	private Integer activityDetailOrder;
	//活动详情页中单独使用显示视频截图
	private String image;

	private Float duration;

	private Integer historyId;

	public String getDuration() {
		return BaseUtil.instance.getDuration(duration);
	}
}
