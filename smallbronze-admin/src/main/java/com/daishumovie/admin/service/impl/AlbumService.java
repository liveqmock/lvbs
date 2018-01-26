package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.AlbumDto;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IAlbumService;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.admin.service.IUserService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by feiFan.gou on 2017/10/25 9:32.
 */
@Service
public class AlbumService extends BaseService implements IAlbumService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumService.class);

    private @Autowired
    SbTopicAlbumMapper albumMapper;
    @Autowired
    private SbTopicMapper topicMapper;
    private @Autowired
    IAdminService adminService;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private IUserService userService;

    public ReturnDto<AlbumDto> paginate(ParamDto param, String title, String subtitle, Integer status, String paramTime) {

        try {
            SbTopicAlbumExample example = cnd(title, subtitle, status, paramTime);
            Long total = albumMapper.countByExample(example);
            List<AlbumDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                List<SbTopicAlbum> albums = albumMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(albums)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    albums.forEach(album -> {
                        AlbumDto dto = new AlbumDto();
                        BeanUtils.copyProperties(album, dto);
                        dtoList.add(dto);
                        if (null != album.getOperatorId()) {
                            adminIdSet.add(Long.valueOf(album.getOperatorId()));
                        }
                        if (null != album.getModifierId()) {
                            adminIdSet.add(Long.valueOf(album.getModifierId()));
                        }
                    });
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    dtoList.forEach(dto -> {
                        Integer operator = dto.getOperatorId();
                        if (null != operator && adminNameMap.containsKey(operator)) {
                            dto.setOperator(adminNameMap.get(operator));
                        }
                        Integer modifier = dto.getModifierId();
                        if (null != modifier && adminNameMap.containsKey(modifier)) {
                            dto.setModifier(adminNameMap.get(modifier));
                        }
                    });
                }
            }
            Page<AlbumDto> page = param.page();
            page.setItems(dtoList);
            page.setTotal(total.intValue());
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("album paginate exception ===>\r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    public ReturnDto<SbTopicDto> paginate(ParamDto param, String idsStr, Integer channelId, String createTime, String username, String topicIds) {

        try {
            SbTopicExample example = cnd(idsStr, channelId, createTime, username, topicIds);
            List<SbTopicDto> dtoList = Lists.newArrayList();
            Long total = topicMapper.countByExample(example);
            boolean enableOtherCnd = true; //默认可以查询其他条件
            if (!CollectionUtils.isNullOrEmpty(example.getOredCriteria()) && example.getOredCriteria().get(0).getCriteria().size() == 1) { //如果没有其他条件
                enableOtherCnd = false;//只查出第一页
            }
            if (StringUtil.isNotEmpty(topicIds) && !enableOtherCnd) {
                List<Integer> chosenIdList = Lists.newArrayList();
                for (String topicId : topicIds.split(",")) {
                    chosenIdList.add(Integer.parseInt(topicId));
                }
                if (!chosenIdList.isEmpty()) {
                    total += chosenIdList.size();
                }
                if (param.getPage_number() == 1) {
                    example.getOredCriteria().get(0).andIdIn(chosenIdList);
                    StringBuilder builder = new StringBuilder();
                    for (Integer chosenId : chosenIdList) builder.append(",").append(chosenId);
                    example.setOrderByClause("field(id," + builder.toString().substring(1) + ")");
                }
            }
            if (total > 0) {
                if (StringUtil.isNotEmpty(topicIds)) {
                    if (param.getPage_number() != 1  && !enableOtherCnd) {
                        example.setOrderByClause("create_time desc");
                        param.setPage_number(param.getPage_number() - 1);
                    }
                } else {
                    example.setOrderByClause("create_time desc");
                }
                example.setOffset(param.offset());
                example.setLimit(param.limit());
                List<SbTopic> topicList = topicMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(topicList)) {
                    Set<Integer> userIdSet = Sets.newHashSet();
                    Set<Integer> channelIdSet = Sets.newHashSet();
                    topicList.forEach(topic -> {
                        SbTopicDto dto = new SbTopicDto();
                        BeanUtils.copyProperties(topic, dto);
                        dtoList.add(dto);
                        if (null != topic.getChannelId()) {
                            channelIdSet.add(topic.getChannelId());
                        }
                        if (null != topic.getUid()) {
                            userIdSet.add(topic.getUid());
                        }
                    });
                    Map<Integer, String> channelMap = channelService.channelMap(channelIdSet);
                    Map<Integer, DsmUser> userMap = userService.userMapByUid(userIdSet);
                    dtoList.forEach(dto -> {
                        Integer cId = dto.getChannelId();
                        if (null != cId && channelMap.containsKey(cId)) {
                            dto.setCategoryName(channelMap.get(cId));
                        }
                        Integer uid = dto.getUid();
                        if (null != uid && userMap.containsKey(uid)) {
                            DsmUser user = userMap.get(uid);
                            if (null != user) {
                                UserType userType = UserType.get(user.getType());
                                dto.setNickName(user.getNickName());
                                if (null != userType) {
                                    dto.setUserType(userType.getName());
                                }
                            }
                        }
                        dto.setDescription(description(String.valueOf(dto.getId())));
                    });
                }
            }
            Page<SbTopicDto> page = param.page();
            page.setItems(dtoList);
            page.setTotal(total.intValue());
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("paginate for video error ===>\r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }
    public void save(SbTopicAlbum album, Integer operatorId) {

        validate(album, false);
        try {
            album.setAppId(Configuration.current_app.getId());
            album.setStatus(AlbumStatus.publishing.getValue());
            cover(album);
            album.setTopicIds(StringUtil.empty);
            album.setReplyNum(0);
            album.setOperatorId(operatorId);
            album.setModifierId(operatorId);
            album.setCreateTime(new Date());
            album.setModifyTime(album.getCreateTime());
            albumMapper.insertSelective(album);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("album", "save", e);
            throw new ResultException();
        }
    }

    public void update(SbTopicAlbum album, Integer operatorId) {

        validate(album, true);
        try {
            cover(album);
            album.setModifierId(operatorId);
            album.setModifyTime(album.getCreateTime());
            albumMapper.updateByPrimaryKeySelective(album);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("album", "save", e);
            throw new ResultException();
        }
    }

    public void putVideo(Integer albumId, String topicIds, Integer operatorId) {

        if (null == albumId || StringUtil.isEmpty(topicIds)) {
            throw new ResultException(ErrMsg.param_error);
        }
        SbTopicAlbum album = albumMapper.selectByPrimaryKey(albumId);
        if (null == album) {
            throw new ResultException("合辑不存在");
        }
        try {
            album.setTopicIds(topicIds);
            album.setOperatorId(operatorId);
            album.setModifyTime(new Date());
            albumMapper.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            printException("album", "update", e);
            throw new ResultException();
        }
    }

    public void publish(Integer albumId, Integer operatorId) {

        if (null == albumId) {
            throw new ResultException(ErrMsg.param_error);
        }
        SbTopicAlbum album = albumMapper.selectByPrimaryKey(albumId);
        if (null == album) {
            throw new ResultException("合辑不存在");
        }
        try {
            album.setStatus(AlbumStatus.published.getValue());
            album.setOperatorId(operatorId);
            album.setModifyTime(new Date());
            album.setPublishTime(album.getModifyTime());
            albumMapper.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            printException("album", "publish", e);
            throw new ResultException();
        }
    }

    public void offline(Integer albumId, Integer operatorId) {

        if (null == albumId) {
            throw new ResultException(ErrMsg.param_error);
        }
        SbTopicAlbum album = albumMapper.selectByPrimaryKey(albumId);
        if (null == album) {
            throw new ResultException("合辑不存在");
        }
        try {
            album.setStatus(AlbumStatus.offline.getValue());
            album.setOperatorId(operatorId);
            album.setModifyTime(new Date());
            albumMapper.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            printException("album", "offline", e);
            throw new ResultException();
        }
    }

    @Override
    public Map<Integer, String> getAlbumMap(Set<Integer> albumIdSet) {
        Map<Integer, String> map = Maps.newHashMap();
        if (albumIdSet.size() <= 0){
            return map;
        }
        SbTopicAlbumExample example = new SbTopicAlbumExample();
        SbTopicAlbumExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(new ArrayList<>(albumIdSet));
        List<SbTopicAlbum> list = albumMapper.selectByExample(example);
        for (SbTopicAlbum album : list){
            if (!map.containsKey(album.getId()))
                map.put(album.getId(),album.getTitle());
        }
        return map;
    }

    @Override
    public SbTopicAlbum getAlbumById(Integer id) {
        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        return albumMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateReplyNum(Integer id, Integer replayNum) {
        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        SbTopicAlbum record = new SbTopicAlbum();
        record.setId(id);
        record.setReplyNum(replayNum);
        albumMapper.updateByPrimaryKeySelective(record);
    };

    @Override
    protected void specificVerify(Object model, boolean isUpdate) {

        SbTopicAlbum album = (SbTopicAlbum) model;

        if (isUpdate) {
            if (null == album.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbTopicAlbum dbAlbum = albumMapper.selectByPrimaryKey(album.getId());
            if (null == dbAlbum) {
                throw new ResultException("合辑不存在");
            }
        }
        if (StringUtil.isEmpty(album.getTitle())) {
            throw new ResultException("标题不能为空");
        }
        if (StringUtil.isEmpty(album.getSubtitle())) {
            throw new ResultException("副标题不能为空");
        }
        if (StringUtil.isEmpty(album.getCover())) {
            throw new ResultException("封面不能为空");
        }
    }

    private void cover(SbTopicAlbum album) throws Exception {

        String cover = StringUtil.trim(album.getCover());
        if (cover.length() > 0) {
            if (cover.contains(Configuration.temp_path)) { //重新上传的
                cover = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(cover, OSSClientUtil.upload_type.album_icon);
                album.setCover(cover);
            }
        }
    }

    private SbTopicAlbumExample cnd(String title, String subtitle, Integer status, String paramTime) {

        SbTopicAlbumExample example = new SbTopicAlbumExample();
        SbTopicAlbumExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(title)) {
            criteria.andTitleLike(StringUtil.sqlLike(title));
        }
        if (StringUtil.isNotEmpty(subtitle)) {
            criteria.andSubtitleLike(StringUtil.sqlLike(subtitle));
        }
        if (null != status) {
            if (status.equals(AlbumStatus.publishing.getValue())) {
                example.setOrderByClause("create_time desc");
            } else if(status.equals(AlbumStatus.published.getValue())) {
                example.setOrderByClause("publish_time desc");
            } else {
                example.setOrderByClause("modify_time desc");
            }
            criteria.andStatusEqualTo(status);
        }
        if (StringUtil.isNotEmpty(paramTime)) {
            if (null != status) {
                if (status.equals(AlbumStatus.publishing.getValue())) {
                    criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(paramTime));
                }else if(status.equals(AlbumStatus.published.getValue())) {
                    criteria.andPublishTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(paramTime));
                } else {
                    criteria.andModifyTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(paramTime));
                }
            }
        }
        return example;
    }

    private SbTopicExample cnd(String idsStr, Integer channelId, String createTime, String username, String topicIds) throws Exception {

        SbTopicExample example = new SbTopicExample();
        SbTopicExample.Criteria criteria = example.createCriteria();
        criteria.andAuditStatusEqualTo(AuditStatus.MAN_AUDIT_PASS.getValue());
        if (StringUtil.isEmpty(topicIds)) {
            criteria.andStatusEqualTo(TopicStatus.published.getValue());
        }
        if (StringUtil.isNotEmpty(idsStr)) {
            List<Integer> idList = Lists.newArrayList();
            for (String id : idsStr.split("[\\s]+"))idList.add(Integer.parseInt(id));
            criteria.andIdIn(idList);
        }
        if (null != channelId) {
            criteria.andChannelIdEqualTo(channelId);
        }
        if (StringUtil.isNotEmpty(createTime)) {
            criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(createTime));
        }
        if (StringUtil.isNotEmpty(username)) {
            List<Integer> uidList = userService.getUidByNameLike(username);
            if (!uidList.isEmpty()) {
                criteria.andUidIn(uidList);
            } else {
                throw new Exception();
            }
        }
        return example;
    }

    private String description(String videoId) {

        StringBuilder builder = new StringBuilder();
        SbTopicAlbumExample example = new SbTopicAlbumExample();
        SbTopicAlbumExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdsLike(StringUtil.sqlLike("," + videoId + ","));
        example.or().andTopicIdsLike(videoId + ",%");
        example.or().andTopicIdsLike("%,"+videoId);
        List<SbTopicAlbum> bucketList = albumMapper.selectByExample(example);
        if (!CollectionUtils.isNullOrEmpty(bucketList)) {
            bucketList.forEach(album -> builder.append(",").append(album.getId()));
        } else {
            builder.append(",否");
        }
        return builder.substring(1);
    }
}
