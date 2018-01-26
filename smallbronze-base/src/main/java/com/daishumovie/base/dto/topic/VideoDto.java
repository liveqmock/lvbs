package com.daishumovie.base.dto.topic;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import com.daishumovie.base.enums.front.BaseUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDto implements Serializable {
	
	private static DecimalFormat df = new DecimalFormat("0.0MB");

	private static final long serialVersionUID = 1277412135348945566L;

	private Integer id;

	private String name;
	/**
	 * 1 题主视频 2 iOs上墙视频集 3 Android上墙视频集
	 */
	private Integer type;

	private String url;
	
	private String cover;

	private List<String> urls;

	private List<UserAndVideoInfo> list;
	
	private Integer refId;

	private String playNum;

	private String barrageNum;

	private Float duration;

	private String dimension;
	
	private Integer width;
	
	private Integer height;
	
	private Integer size;
	
	public String getSize() {
		if (size != null) {
			return df.format(size.doubleValue() / 1048576);
		}
		return null;
	}
	
	public void setDimension(String dimension){
		this.dimension = dimension;
		if (dimension != null && dimension.length() > 1) {
			String[] arr = dimension.split("x");
			width = Integer.valueOf(arr[0]);
			height = Integer.valueOf(arr[1]);
		}
	}

	public String getDuration() {
		return BaseUtil.instance.getDuration(duration);
	}
}
