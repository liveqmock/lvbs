package com.daishumovie.base.dto.share;

import java.io.Serializable;

import lombok.Data;

@Data
public class ShareAd implements Serializable {

	private static final long serialVersionUID = 8139832656931749392L;

	private Integer targetType;

	private String target;

	private String title;

	private String imgUrl;

}
