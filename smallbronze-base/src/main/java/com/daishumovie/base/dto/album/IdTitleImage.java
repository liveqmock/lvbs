package com.daishumovie.base.dto.album;

import java.io.Serializable;

import com.daishumovie.base.enums.front.BaseUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdTitleImage implements Serializable {

	private static final long serialVersionUID = 6661398067484392872L;

	private Integer id;

	private String title;

	private String image;

	private Float duration;

	public String getDuration() {
		return BaseUtil.instance.getDuration(duration);
	}
}
