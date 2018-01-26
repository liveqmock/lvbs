package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by yang on 2017/9/26.
 * 本地上传视频状态
 */
@Getter
public enum UploadVideosStatus {

    //上传状态 0 等待上传 1 正在上传 2上传失败 3 上传完成

    WAIT("等待上传",0),
    UPLOADING("正在上传",1),
    UPLOAD_FAIL("上传失败",2),
    FINISH("上传完成",3);


    private  String name; //名称
    private  Integer value; //数据库存储至

    UploadVideosStatus(String name, Integer value) {

        this.name = name;
        this.value = value;
    }

    public static UploadVideosStatus get(Integer value) {

        if (null != value) {
            for (UploadVideosStatus type : UploadVideosStatus.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
        }
        return null;
    }
}
