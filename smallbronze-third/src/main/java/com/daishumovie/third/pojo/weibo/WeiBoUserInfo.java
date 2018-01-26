package com.daishumovie.third.pojo.weibo;

import lombok.Data;

import java.io.Serializable;

/**
 * 微博返回的部分用户信息
 * @author zhuruisong on 2017/5/11
 * @since 1.0
 */
@Data
public class WeiBoUserInfo implements Serializable {

    private String  id;
    private String  screen_name;//用户昵称
    private String  name; //友好显示名称?
    private String  profile_image_url;
    private String  gender; //性别，m：男、f：女、n：未知
}
