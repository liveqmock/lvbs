package com.daishumovie.base.enums.front;

import lombok.Getter;

@Getter
public enum VideoType {

	OWNER_VIDEO(1, "题主视频"), 
	IOS_VIDEO_COLLECTION(2, "神回复"),
	ANDROID_VIDEO_COLLECTION(3, "神回复"),
	;

	private final Integer value;
	private final String name;

	VideoType(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public static VideoType get(Integer value) {

		if (null != value) {
			VideoType[] values = VideoType.values();
			for (VideoType v : values) {
				if (v.value.equals(value)) {
					return v;
				}
			}
		}
		return null;
	}
}
