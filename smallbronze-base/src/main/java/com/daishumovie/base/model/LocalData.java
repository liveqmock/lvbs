package com.daishumovie.base.model;

/**
 * @author zhuruisong on 2017/2/10
 * @since 1.0
 */
public class LocalData {

    public static ThreadLocal<String> UUID = new ThreadLocal<>();

    public static ThreadLocal<Header> HEADER = new ThreadLocal<>();

    public static ThreadLocal<String> USER_JSON = new ThreadLocal<>();
}
