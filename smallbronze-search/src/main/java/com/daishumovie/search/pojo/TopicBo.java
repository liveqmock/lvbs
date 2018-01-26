package com.daishumovie.search.pojo;

import io.searchbox.annotations.JestId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Cheng Yufei
 * @create 2017-11-03 16:28
 **/
@Data
@ToString
public class TopicBo {

    @JestId
    private Integer id;

    private Integer appId;

    private Integer channelId;

    private Integer uid;

    private Integer videoId;

    private Integer activityId;

    private Integer followNum;

    private Integer replyNum;

    private Integer playNum;

    private String title;

    private Date publishTime;
}
