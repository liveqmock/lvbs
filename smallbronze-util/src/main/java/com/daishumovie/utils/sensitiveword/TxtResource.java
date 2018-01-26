package com.daishumovie.utils.sensitiveword;

import java.io.InputStream;

/**
 * @author zhuruisong on 2017/5/8
 * @since 1.0
 */
public class TxtResource {


    public InputStream get(){
        return this.getClass().getResourceAsStream("/sensitiveword.txt");
    }
}
