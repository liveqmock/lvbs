package com.daishumovie.base.dto.user;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeviceSettings implements Serializable {

	private static final long serialVersionUID = -8358017339558065887L;

	/**
	 * 接收订阅推送
	 */
	private Integer receiveNotification = 1;
	/**
	 * WIFI下自动播放
	 */
	private Integer autoplay = 1;
	/**
	 * 评论弹幕
	 */
	private Integer allowBulletScreen = 1;
}
