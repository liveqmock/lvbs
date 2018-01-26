package com.daishumovie.base.dto.upload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhuruisong on 2017/9/6
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class UploadDto {

    private String contentType;
    private String url;
    private String dimension;
    private Integer id;

}
