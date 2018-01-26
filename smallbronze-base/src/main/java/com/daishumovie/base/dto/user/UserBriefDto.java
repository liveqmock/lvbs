package com.daishumovie.base.dto.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserBriefDto implements Serializable {
	
	private static final long serialVersionUID = 1930829354045614518L;
	
	private Integer id;
	
	private String headImg;
	
	private String nickname;

	//是否关注用户
	private Integer hasFollow;

	/**
	 * 用户类型 0 UGC 1 PGC
	 */
	private Integer userType;
}
