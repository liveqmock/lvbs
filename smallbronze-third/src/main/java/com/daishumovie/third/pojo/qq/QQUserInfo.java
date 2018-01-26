package com.daishumovie.third.pojo.qq;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/5/11
 * @since 1.0
 */
@Data
public class QQUserInfo implements Serializable{

    private Integer ret;
    private String msg;
//    private String openid;
    private String nickname;
    private String gender;
    private String figureurl;

}
