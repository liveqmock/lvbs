package com.daishumovie.base.dto.app;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/5/27
 * @since 1.0
 */
@Data
public class CheckNewDto implements Serializable{

    /**
     * 1最新 2提示更新 3强制更新
     */

    private Integer status;

    /**
     * 版本号
     */
    private String versionNum;

    /**
     * 更新描述
     */
    private String updateDesc;

    /**
     * 下载地址
     */
    private String downUrl;

    /**
     * 渠道ID 默认10000 是我司
     */
    private String channelId;

}
