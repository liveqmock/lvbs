package com.daishumovie.third.pojo.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/3/27
 * @since 1.0
 */
@Data
public class AccessTokenBo implements Serializable{

	private String access_token;
	private Long expires_in;

}
