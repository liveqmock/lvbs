package com.daishumovie.third.pojo.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/3/31
 * @since 1.0
 */
@Data
public class PageTokenBo implements Serializable {

    private Integer errcode = 0;

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
