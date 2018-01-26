package com.daishumovie.base.enums.db;

import lombok.Getter;

@Getter
public enum MultipartUploadStatusEnum {
    UPLOAD_NO_SUCCESS(0, "未上传完成"),
    UPLOAD_SUCCESS(1 ,"已上传完成"),
    DELETE_OR_ABORT(-1 ,"删除中止");

    private Integer code;
    private String name;


    MultipartUploadStatusEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static MultipartUploadStatusEnum get(Integer code){
        if (code == null) return null;
        MultipartUploadStatusEnum[] values = MultipartUploadStatusEnum.values();
        for (MultipartUploadStatusEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
