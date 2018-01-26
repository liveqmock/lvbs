package com.daishumovie.third.pojo.wechat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuruisong on 2017/3/28
 * @since 1.0
 */
@Setter
@Getter
public class ButtonClick extends ParentButton{

    private String type;
    private String name;
    private String key;
}
