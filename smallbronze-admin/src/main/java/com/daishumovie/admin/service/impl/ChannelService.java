package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.ChannelDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.enums.db.CommonStatus;
import com.daishumovie.dao.mapper.smallbronze.SbChannelMapper;
import com.daishumovie.dao.model.SbChannel;
import com.daishumovie.dao.model.SbChannelExample;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.OSSClientUtil;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/9/7 17:05.
 */
@Service
public class ChannelService extends BaseService implements IChannelService {

    private static final int first_level_pid = 0;

    @Autowired
    private SbChannelMapper channelMapper;
    @Override
    public List<ChannelDto> firstLevelList(Integer appId) {

        List<ChannelDto> dtoList = Lists.newArrayList();
        try {
            SbChannelExample example = new SbChannelExample();
            SbChannelExample.Criteria criteria = example.createCriteria();
            criteria.andPidEqualTo(first_level_pid);
            if (null == appId) {
                appId = Configuration.current_app.getId();
            }
            criteria.andAppIdEqualTo(appId);
            criteria.andStatusEqualTo(CommonStatus.normal.getValue());
            List<SbChannel> channelList = channelMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(channelList)) {
                channelList.forEach(channel -> {
                    ChannelDto dto = new ChannelDto();
                    BeanUtils.copyProperties(channel, dto);
                    dtoList.add(dto);
                });
            }
        } catch (Exception e) {
            printException("channel","firstLevelList",e);
        }
        return dtoList;
    }

    @Override
    public List<ChannelDto> secondLevelList(Integer firstChannelId, Integer appId){

        List<ChannelDto> dtoList = Lists.newArrayList();
        try {
            SbChannelExample example = new SbChannelExample();
            SbChannelExample.Criteria criteria = example.createCriteria();
            if (firstChannelId != null)
                criteria.andPidEqualTo(firstChannelId);
            else
                criteria.andPidNotEqualTo(first_level_pid);
            if (null == appId) {
                appId = Configuration.current_app.getId();
            }
            criteria.andAppIdEqualTo(appId);
            criteria.andStatusEqualTo(CommonStatus.normal.getValue());
            example.setOrderByClause("create_time desc");
            List<SbChannel> channelList = channelMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(channelList)) {
                channelList.forEach(channel -> {
                    ChannelDto dto = new ChannelDto();
                    dtoList.add(dto);
                    BeanUtils.copyProperties(channel, dto);
                });
            }
        } catch (Exception e) {
            printException("channel","secondLevelList",e);
        }
        return dtoList;
    }

    public List<ChannelDto> classifyList() {

        List<ChannelDto> dtoList = Lists.newArrayList();
        try {
            SbChannelExample example = new SbChannelExample();
            SbChannelExample.Criteria criteria = example.createCriteria();
            criteria.andAppIdEqualTo(Configuration.current_app.getId());
            criteria.andStatusEqualTo(CommonStatus.normal.getValue());
            example.setOrderByClause("create_time desc");
            List<SbChannel> channelList = channelMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(channelList)) {
                Map<Integer, List<ChannelDto>> channelMap = Maps.newHashMap();
                channelList.forEach(channel -> {
                    ChannelDto dto;
                    BeanUtils.copyProperties(channel, dto = new ChannelDto());
                    Integer pid = dto.getPid();
                    if (null != pid) {
                        if (channelMap.containsKey(pid)) {
                            channelMap.get(pid).add(dto);
                        } else {
                            channelMap.put(pid, Lists.newArrayList(dto));
                        }
                    }
                });
                if (channelMap.containsKey(first_level_pid)) {
                    dtoList = channelMap.get(first_level_pid);
                    dtoList.forEach(dto -> dto.setChildren(channelMap.get(dto.getId())));
                }
            }
        } catch (Exception e) {
            printException("channel","classifyList",e);
        }
        return dtoList;
    }

    @Override
    public void save(SbChannel channel, Integer operatorId) {

        try {
            validate(channel,false);
            channel.setStatus(CommonStatus.normal.getValue());
            channel.setCreateTime(new Date());
            channel.setUpdateTime(channel.getCreateTime());
            channel.setOperatorId(operatorId);
            uploadLogo(channel);
            uploadDefaultUrl(channel);
            channelMapper.insertSelective(channel);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("channel", "save", e);
            throw new ResultException();
        }
    }

    @Override
    public void update(SbChannel channel, Integer operatorId) {

        try {
            validate(channel,true);
            channel.setOperatorId(operatorId);
            channel.setUpdateTime(new Date());
            uploadLogo(channel);
            uploadDefaultUrl(channel);
            channelMapper.updateByPrimaryKeySelective(channel);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("channel", "update", e);
            throw new ResultException();
        }
    }

    @Override
    public void delete(Integer id, Integer operatorId) {

        try {
            if (null == id) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbChannel channel = channelMapper.selectByPrimaryKey(id);
            if (null == channel) {
                throw new ResultException("频道不存在");
            }
            channel.setStatus(CommonStatus.invalid.getValue());
            channel.setUpdateTime(new Date());
            {
                SbChannelExample example = new SbChannelExample();
                SbChannelExample.Criteria criteria = example.createCriteria();
                criteria.andPidEqualTo(id).andStatusEqualTo(CommonStatus.normal.getValue());
                List<SbChannel> children = channelMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(children)) {
                    children.forEach(child -> {
                        child.setStatus(CommonStatus.invalid.getValue());
                        child.setUpdateTime(new Date());
                        child.setOperatorId(operatorId);
                        channelMapper.updateByPrimaryKeySelective(child);
                    });
                }
            }
            channelMapper.updateByPrimaryKeySelective(channel);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("channel", "delete", e);
            throw new ResultException();
        }
    }

    @Override
    protected void specificVerify(Object model, boolean isUpdate) {

        SbChannel channel = (SbChannel) model;
        if (StringUtil.isEmpty(channel.getName())) {
            throw new ResultException("频道名称不能为空");
        }
        if (null == channel.getPid()) {
            throw new ResultException(ErrMsg.param_error);
        }
        if (channel.getPid() == first_level_pid) {
            if (!(channel.getName().length() >= 1 && channel.getName().length() <= 4)) {
                throw new ResultException("一级频道名称长度【1-4】");
            }
        } else {
            if (!(channel.getName().length() >= 1 && channel.getName().length() <= 14)) {
                throw new ResultException("二级频道名称长度【1-14】");
            }
        }
        if (!checkOnly(channel.getName(), channel.getId())) {
            throw new ResultException("频道名称不能重复");
        }
        if (StringUtil.isEmpty(channel.getUrl())) {
            throw new ResultException("素材logo不能为空");
        }
        if (null == channel.getAppId()) {
            throw new ResultException("请选择APP");
        }
        if (isUpdate) {
            if (null == channel.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbChannel dbChannel = channelMapper.selectByPrimaryKey(channel.getId());
            if (null == dbChannel) {
                throw new ResultException("频道不存在");
            }
        }
    }

    public Map<Integer, String> channelMap(Set<Integer> channelSet) {

        Map<Integer, String> channelMap = Maps.newHashMap();
        if (!CollectionUtils.isNullOrEmpty(channelSet)) {
            SbChannelExample example = new SbChannelExample();
            SbChannelExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(Lists.newArrayList(channelSet));
            List<SbChannel> channelList = channelMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(channelList)) {
                channelList.forEach(channel -> {
                    if (!channelMap.containsKey(channel.getId())) {
                        channelMap.put(channel.getId(), channel.getName());
                    }
                });
            }
        }
        return channelMap;
    }

    private void uploadLogo(SbChannel channel) throws Exception {

        String url = StringUtil.trim(channel.getUrl());
        if (url.contains(Configuration.temp_path)) {
            url = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(url, OSSClientUtil.upload_type.channel_icon);
            channel.setUrl(url);
        }
    }

    private void uploadDefaultUrl(SbChannel channel) throws Exception {

        String url = StringUtil.trim(channel.getDefaultUrl());
        if (url.contains(Configuration.temp_path)) {
            url = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(url, OSSClientUtil.upload_type.channel_icon);
            channel.setDefaultUrl(url);
        }
    }

    private boolean checkOnly(String name, Integer id) {

        if (StringUtil.isEmpty(name)) {
            return false;
        }
        SbChannelExample example = new SbChannelExample();
        SbChannelExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        if (null != id) {
            criteria.andIdNotEqualTo(id);
        }
        List<SbChannel> channelList = channelMapper.selectByExample(example);
        return CollectionUtils.isNullOrEmpty(channelList);
    }
}
