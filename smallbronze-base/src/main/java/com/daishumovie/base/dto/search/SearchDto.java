package com.daishumovie.base.dto.search;

import java.io.Serializable;

import com.daishumovie.base.enums.front.BaseUtil;

import lombok.Data;

@Data
public class SearchDto implements Serializable {

	private static final long serialVersionUID = 2236732303981398496L;

	private String image;

	private String title;

	private String playNum;

	private String userName;

	private Float duration;

	private String time;

	private Integer count;

	private String fansNum;

	private Integer userType;

	private String introduce;

	/**
	 * 是否关注
	 */
	private Integer hasFollow;
	/**
	 * 话题id、合辑id、用户id
	 */
	private Integer id;

	public String getDuration() {
		return BaseUtil.instance.getDuration(duration);
	}

}
