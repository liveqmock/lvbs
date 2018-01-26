package com.daishumovie.base.dto.share;

import com.daishumovie.base.dto.activity.ActivityDto;
import com.daishumovie.base.dto.album.IdTitleImage;
import com.daishumovie.base.dto.topic.TopicDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhuruisong on 2017/10/23
 * @since 1.0
 */
@Data
public class ShareActivityDto implements Serializable {

//    private String title; //标题
//    private String remark;//其他描述
//    private String cover;//封面
//    private Integer status; //状态
//    private String statusName; //状态名称
//    private String time; //时间
//    private String rule; //活动规则

    private ActivityDto activity;

    private List<TopicDto> topicList; //14个话题列表
    private Integer hasMoreTopic;  //是否有更多话题

    private List<ActivityDto> activityList; //4个活动列表
    private Integer hasMoreActivity;  //是否有更多活动

    /**
     * 分享详情的底部广告列表；
     */
    private List<ShareAd> adList;

}
