package com.daishumovie.third.pojo.weibo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/5/11
 * @since 1.0
 */
@Data
public class WeiBoTokenBo implements Serializable{

    private String access_token;
    private Integer expires_in;
    private String remind_in;
    private String uid;
}
