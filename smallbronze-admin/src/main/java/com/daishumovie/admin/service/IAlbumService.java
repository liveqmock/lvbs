package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.AlbumDto;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.dao.model.SbTopicAlbum;

import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/10/25 11:12.
 * 合辑
 */
public interface IAlbumService {

    ReturnDto<AlbumDto> paginate(ParamDto param, String title, String subtitle, Integer status, String paramTime);

    ReturnDto<SbTopicDto> paginate(ParamDto param, String idsStr, Integer channelId, String createTime, String username, String topicIds);

    void save(SbTopicAlbum album, Integer operatorId);

    void update(SbTopicAlbum album, Integer operatorId);

    void putVideo(Integer albumId, String topicIds, Integer operatorId);

    void publish(Integer albumId, Integer operatorId);

    void offline(Integer albumId, Integer operatorId);

    Map<Integer,String> getAlbumMap(Set<Integer> albumIdSet);

    SbTopicAlbum getAlbumById(Integer id);

    void updateReplyNum(Integer id,Integer replayNum);
}
