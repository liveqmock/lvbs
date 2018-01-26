package com.daishumovie.base.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/5/23
 * @since 1.0
 */
@Data
public class LoginDto implements Serializable {

    private String sessionId;
    private UserInfo userInfo;
    private Integer mustBindMobile;
}
