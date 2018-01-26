package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.ChannelDto;
import com.daishumovie.dao.model.SbChannel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/9/7 17:04.
 */
public interface IChannelService {

    List<ChannelDto> firstLevelList(Integer appId);

    List<ChannelDto> secondLevelList(Integer firstChannelId, Integer appId);

    void save(SbChannel channel, Integer operatorId);

    void update(SbChannel channel, Integer operatorId);

    void delete(Integer id, Integer operatorId);

    Map<Integer, String> channelMap(Set<Integer> channelSet);

    List<ChannelDto> classifyList();

}
