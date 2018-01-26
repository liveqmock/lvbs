package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.base.enums.db.AuditStatus;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbVideo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/8/28 15:32.
 */
public interface ITopicService {


    ReturnDto<SbTopicDto> paginate(ParamDto param, String name, String createTime,Integer status,Integer auditStatus,Integer source);

    SbTopicDto findSbTopicDtoById(Integer id);

    void update(SbTopic sbTopic);

    Map<Integer,SbTopic> getTopicDtoLists(Set<Integer> topicIds);

    Map<Integer,String> getVideoMap(Set<Integer> topicIdSet);

    Map<Integer, SbVideo> videoInfoMap(Set<Integer> videoIdSet);

    void passOrNot(Integer id, String reason, AuditStatus auditStatus, Integer operatorId);

    void batchOperate(Integer[] ids, String reason, AuditStatus auditStatus, Integer operatorId);

    ReturnDto<SbTopicDto> paginate(ParamDto param, String publishTime, Integer videoId, Integer publishStatus, String title, Integer uploader,Integer channelType);

    void publish(Integer id, Integer channelId, String title, Integer publisher, Integer operatorId, Float coverTime, boolean isDefault, String newCover);

    void offline(Integer id);

    void up(Integer id);

    void delete(Integer topicId);

    void edit(Integer topicId, String title, Float coverTime, Boolean isDefault, String uploadCover);

    List<Integer> getTopicIds(String name);
}
