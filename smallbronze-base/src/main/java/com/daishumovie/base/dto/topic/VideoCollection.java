package com.daishumovie.base.dto.topic;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoCollection implements Serializable {

	private static final long serialVersionUID = -3534943129243909551L;

	private String m3u8Url;
	
	private String cover;

	private List<UserAndVideoInfo> list;
}
