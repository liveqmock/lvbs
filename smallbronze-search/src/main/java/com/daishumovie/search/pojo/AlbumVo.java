package com.daishumovie.search.pojo;

import io.searchbox.annotations.JestId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Cheng Yufei
 * @create 2017-11-03 10:22
 **/
@Data
@ToString
public class AlbumVo {

    @JestId
    private Integer id;

    private Integer appId;

    private Integer replyNum;

    private String topicIds;

    private String title;

    private String subtitle;

}
