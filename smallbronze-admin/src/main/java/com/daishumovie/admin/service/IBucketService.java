package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.BucketDto;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.VideoInfoDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/9/21 15:33.
 */
public interface IBucketService {

    ReturnDto<SbTopicDto> paginate(ParamDto param, String idsStr, Integer channelId, String createTime, Integer inside, String username, String topicIds, Boolean...isAdd);

    ReturnDto<BucketDto> paginate(ParamDto param, Integer id, String createTime, String adminName);

    List<VideoInfoDto> videoList(Integer[] topicIds);

    VideoInfoDto videoInfo(Integer videoId);

    void save(String videoIds, String remarks, Integer operatorId);

    void update(Integer id, String videoIds, String remarks, Integer operatorId);
}
