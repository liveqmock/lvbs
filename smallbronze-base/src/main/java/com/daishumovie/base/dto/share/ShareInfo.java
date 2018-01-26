package com.daishumovie.base.dto.share;

import java.io.Serializable;

import lombok.Data;

@Data
public class ShareInfo implements Serializable {

	private static final long serialVersionUID = 710376392384358303L;

	private String title;

	private String desc;

	private String url;

	private String image;
}
