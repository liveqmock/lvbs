package com.daishumovie.third.pojo.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/4/25
 * @since 1.0
 */
@Data
public class TicketBo implements Serializable {

    private String errcode;
    private String errmsg;
    private String ticket;
    private Long expires_in;
}
