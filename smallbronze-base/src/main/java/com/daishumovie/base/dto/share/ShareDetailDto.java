package com.daishumovie.base.dto.share;

import java.io.Serializable;
import java.util.List;

import com.daishumovie.base.dto.activity.ActivityDto;
import com.daishumovie.base.dto.topic.TopicDto;

import lombok.Data;

@Data
public class ShareDetailDto implements Serializable {

	private static final long serialVersionUID = 6500514125419853881L;
	
	private TopicDto detail;
	/**
	 * 更多精彩列表
	 */
	private List<TopicDto> brilliantList;
	/**
	 * 分享详情的底部广告列表；
	 */
	private List<ShareAd> adList;
	
	private List<TopicDto> topicList; //14个话题列表   参加活动的话题才返回该字段
    private Integer hasMoreTopic;  //是否有更多话题   参加活动的话题才返回该字段

    private List<ActivityDto> activifyList; //4个活动列表   参加活动的话题才返回该字段
    private Integer hasMoreActivity;  //是否有更多活动   参加活动的话题才返回该字段
}
