package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.ActivityDto;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.dao.model.SbActivity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/10/20 11:46.
 * =======
 * import java.util.List;
 * import java.util.Map;
 * import java.util.Set;
 * <p>
 * /**
 * Created by feiFan.gou on 2017/10/20 11:46.‘
 * 活动
 * >>>>>>> admin-v2-20171031
 */
public interface IActivityService {


    ReturnDto<ActivityDto> paginate(ParamDto param, String title, String topic, Integer status, Integer whetherOnline, String preTime, String startTime);

    void save(SbActivity activity, Integer operatorId);

    void update(SbActivity activity, Integer operatorId);

    void offline(Integer id, Integer operatorId);

    void updateReplyNum(Integer id, Integer replayNum);

    List<SbTopicDto> topicListByActivityId(Integer activityId);

    Map<Integer, String> getActivityMap(Set<Integer> actIdSet);

    SbTopicDto topicByActivityId(Integer topicId, Integer activityId);

    void putVideo(Integer topicId, Integer activityId);

    void updateLikeCount(Integer topicId, Integer likeCount);

    void removeVideoFromActivity(Integer topicId);

    SbActivity getActivityById(Integer id);
}
