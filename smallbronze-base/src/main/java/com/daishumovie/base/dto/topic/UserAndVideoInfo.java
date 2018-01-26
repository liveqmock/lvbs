package com.daishumovie.base.dto.topic;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAndVideoInfo implements Serializable {

	private static final long serialVersionUID = -4692042520984852500L;

	private String headImg;

	private Integer uid;

	private Integer startMillisecond;

	private Integer videoId;
	
	private Integer width;
	
	private Integer height;
}
