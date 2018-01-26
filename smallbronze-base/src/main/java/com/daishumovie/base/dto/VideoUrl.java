package com.daishumovie.base.dto;

import lombok.Data;

/**
 * @author Cheng Yufei
 * @create 2017-09-21 11:45
 **/
@Data
public class VideoUrl {
    private String url;

    public VideoUrl(String url) {
        this.url = url;
    }
}
