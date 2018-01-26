package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/8/28 15:46.
 */
@Getter
public enum App {

    app_small_bronze("小铜人", 2000),
    app_ao_ao("嗷嗷", 1000);

    private final String appName;
    private final int id;

    App(String appName, int id) {

        this.appName = appName;
        this.id = id;
    }

    public static App get(Integer id) {

        if (null != id) {
            for (App app : App.values()) {
                if (id == app.id) {
                    return app;
                }
            }
        }
        return null;
    }
}
