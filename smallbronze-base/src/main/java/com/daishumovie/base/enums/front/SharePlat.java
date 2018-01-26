package com.daishumovie.base.enums.front;

import lombok.Getter;

@Getter
public enum SharePlat {

	SHARE_CHANNEL_WECHAT_TIMELINE(0), 
	SHARE_CHANNEL_WECHAT_FRIEND(1), 
	SHARE_CHANNEL_QQ_FRIEND(2), 
	SHARE_CHANNEL_QQ_QZONE(3), 
	SHARE_CHANNEL_SINA_WEIBO(4);

	private Integer code;

	SharePlat(Integer code) {
		this.code = code;
	}

	public static SharePlat get(Integer code) {
		SharePlat[] values = SharePlat.values();
		for (SharePlat value : values) {
			if (value.getCode().equals(code)) {
				return value;
			}
		}
		return null;
	}
}
