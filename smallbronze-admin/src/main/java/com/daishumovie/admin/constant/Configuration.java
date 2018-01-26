package com.daishumovie.admin.constant;

import com.daishumovie.base.enums.db.App;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by feiFan.gou on 2017/7/12 14:45.
 */
@Service
public class Configuration {


    @Value("${video.admin.cover_prefix_domain_name}")
    public String cover_prefix_domain_name;

    @Value("${video.admin.cover_absolute_path}")
    public String cover_absolute_path;

    @Value("${ali.oss.endpoint}")
    public String endpoint;

    @Value("${push.msg.environment}")
    public boolean push_msg_environment;

    public static Configuration INSTANCE;

    public static final String temp_path = "/temporary/";

    /**
     * 200服务器远程访问视频地址
     */
    public static final String video_remote_url = "http://daishumovie.f3322.net:8088/uploads/";

    /**
     * 服务器视频存放路径
     */
    public static final String local_video_path;
    /**
     * 服务器图片存放路径
     */
    public static final String local_image_path;

    /**
     * 桶中视频最大数量
     */
    public static final int bucket_min = 1;
    public static final int bucket_max = 8;
    /**
     * 合辑中视频范围
     */
    public static final int album_max = 24;
    public static final int album_min = 6;

    public @PostConstruct
    void init() {
        INSTANCE = this;
    }

    /**
     * 当前使用的应用
     */
    public static final App current_app = App.app_small_bronze;

    static {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            local_video_path = "d://videos//";
            local_image_path = "d://images//";
        } else {
            local_video_path = "/data/xtr/download/video/";
            local_image_path = "/data/xtr/download/img/";
        }
    }
}
