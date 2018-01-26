package com.daishumovie.utils.jpush;

/**
 * Created by feiFan.gou on 2017/10/19 14:27.
 */
abstract class Base {

    static final String master_secret;
    static final String app_key;

    public static final String input_json = "input_json";
    public static final String output_json = "output_json";

    static {
        master_secret = "ee9906d40771bf601cb8dc61";
        app_key = "522c95e6bff6bddc0e3f8ffe";
    }
}
