package com.daishumovie.admin.dto;

import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/9/22 10:42.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VideoInfoDto {

    private String link;
    private String cover;
    private String title;
    private String playNumber;
    private String ago;
    private String publisher;
    private String likeNumber;
    private String commentNumber;
    private Integer topicId;



    public VideoInfoDto(SbVideo video, String title) {

        if (null != video) {
            this.link = StringUtil.trim(video.getOriUrl());
            this.cover = StringUtil.trim(video.getCover());
        } else {
            this.link = StringUtil.empty;
            this.cover = StringUtil.empty;
        }
        this.title = StringUtil.trim(title);
    }

    public VideoInfoDto() {

    }
}
