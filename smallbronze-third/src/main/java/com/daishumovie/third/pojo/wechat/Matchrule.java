package com.daishumovie.third.pojo.wechat;

import lombok.Data;

/**
 * @author zhuruisong on 2017/3/28
 * @since 1.0
 */
@Data
public class Matchrule {

    private String group_id;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String client_platform_type;
    private String language;
}
