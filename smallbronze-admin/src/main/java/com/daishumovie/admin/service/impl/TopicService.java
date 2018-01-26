package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.UserDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.admin.service.ITopicService;
import com.daishumovie.admin.service.IUserService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.mapper.smallbronze.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

/**
 * Created by feiFan.gou on 2017/8/28 15:37.
 * 素材管理service
 */
@Service
public class TopicService extends BaseService implements ITopicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicService.class);
    @Autowired
    private SbChannelMapper channelMapper;
    @Autowired
    private SbVideoMapper videoMapper;
    @Autowired
    private DsmUserMapper userMapper;
    @Autowired
    private SbTopicMapper topicMapper;
    @Autowired
    private SbTopicBucketMapper bucketMapper;
    @Autowired
    private SbActivityMapper activityMapper;
    @Autowired
    private SbUploadVideosMapper uploadMapper;
    @Autowired
    private SbTopicAlbumMapper albumMapper;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IChannelService channelService;

    @Override
    public ReturnDto<SbTopicDto> paginate(ParamDto param, String title, String createTime,Integer status,Integer auditStatus,Integer source) {

        try {
            SbTopicExample example = new SbTopicExample();
            SbTopicExample.Criteria criteria = example.createCriteria();
            {
                if (StringUtil.isNotEmpty(title)) {
                    criteria.andTitleLike(StringUtil.sqlLike(title));
                }
                if (StringUtil.isNotEmpty(createTime)) {
                    if (auditStatus != null && (auditStatus.intValue() == AuditStatus.MAN_AUDIT_PASS.getValue() || auditStatus.intValue() == AuditStatus.MAN_AUDIT_NOT_PASS.getValue())){
                        criteria.andAuditTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(createTime));
                    }else{
                        criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(createTime));
                    }
                }
                if (null != status) {
                    criteria.andStatusEqualTo(CommonStatus.get(status).getValue());
                }
                if (null != auditStatus){
                    List<Integer> audits = new ArrayList<>();
                    if (auditStatus.intValue() == AuditStatus.WAIT.getValue()){
                        audits.add(AuditStatus.WAIT.getValue());
                        audits.add(AuditStatus.MACHINE_AUDIT_PASS.getValue());
                        audits.add(AuditStatus.MACHINE_AUDIT_NOT_PASS.getValue());
                    }else{
                        audits.add(auditStatus);
                    }
                    criteria.andAuditStatusIn(audits);
                }
                if (null != source){
                    criteria.andSourceEqualTo(source);
                }
            }
            Page<SbTopicDto> page = param.page();
            Long total = topicMapper.countByExample(example);
            List<SbTopicDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setOrderByClause(param.orderString());
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                List<SbTopic> topicList = topicMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(topicList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    Set<Integer> uidsSet = Sets.newHashSet();
                    topicList.forEach(topic -> {
                        SbTopicDto dto = new SbTopicDto();
                        if (null != topic.getAuditOpUid()) {
                            adminIdSet.add(Long.valueOf(topic.getAuditOpUid()));
                        }
                        if (null != topic.getUid()){
                            uidsSet.add(topic.getUid());
                        }
                        BeanUtils.copyProperties(topic, dto);
                        //查询视频地址
                        SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
                        if (null != video){
                            dto.setCover(video.getCover());
                            dto.setVideoUrl(video.getOriUrl());
                        }
                        //查询二级频道
                        SbChannel channel = channelMapper.selectByPrimaryKey(topic.getChannelId());
                        if (null != channel)
                            dto.setCategoryName(channel.getName());
                        dtoList.add(dto);
                    });
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    dtoList.forEach(dto -> {
                        if (null != dto.getAuditOpUid()) {
                            dto.setOperatorName(adminNameMap.get(dto.getAuditOpUid()));
                        }
                    });
                    //发送者昵称
                    Map<Integer, DsmUser> uidMap = userService.userMapByUid(uidsSet);
                    dtoList.forEach(dto -> {
                        if (null != dto.getUid()) {
                            DsmUser user = uidMap.get(dto.getUid());
                            dto.setNickName(user == null ? "" : user.getNickName());
                            dto.setMobile(user == null ? "" : user.getMobile());
                            dto.setUserType(user == null ? UserType.UGC.getName() : UserType.get(user.getType()).getName());
                        }
                    });
                }
            }
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("topic paginate error --- > \r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    public SbTopicDto findSbTopicDtoById(Integer id) {
        SbTopic topic = topicMapper.selectByPrimaryKey(id);
        SbTopicDto dto = new SbTopicDto();
        if (topic != null){
            BeanUtils.copyProperties(topic,dto);
            if (dto.getVideoId() != null){
                SbVideo video = videoMapper.selectByPrimaryKey(dto.getVideoId());
                if (video != null){
                    dto.setVideoUrl(video.getFormatUrl());
                }
            }
        }
        return dto;
    }

    @Override
    public void update(SbTopic sbTopic) {
        try {
            //3.更新数据库
            topicMapper.updateByPrimaryKeySelective(sbTopic);
        } catch (Exception e) {
            printException("topic", "update", e);
            throw new ResultException();
        }

    }

    @Override
    public Map<Integer,SbTopic> getTopicDtoLists(Set<Integer> topicIds) {
        Map<Integer, SbTopic> map = Maps.newHashMap();
        if (topicIds.size() <= 0){
            return map;
        }
        SbTopicExample example = new SbTopicExample();
        SbTopicExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(new ArrayList<>(topicIds));
        List<SbTopic> list = topicMapper.selectByExample(example);
        for (SbTopic topic : list){
            if (!map.containsKey(topic.getId()))
                map.put(topic.getId(),topic);
        }
        return map;
    }

    @Override
    public Map<Integer, String> getVideoMap(Set<Integer> topicIds) {
        Map<Integer, String> map = Maps.newHashMap();
        if (topicIds.size() <= 0){
            return map;
        }
        SbTopicExample example = new SbTopicExample();
        SbTopicExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(new ArrayList<>(topicIds));
        List<SbTopic> list = topicMapper.selectByExample(example);
        for (SbTopic topic : list){
            if (!map.containsKey(topic.getId()))
                map.put(topic.getId(),topic.getTitle());
        }
        return map;
    }

    public Map<Integer, SbVideo> videoInfoMap(Set<Integer> videoIdSet) {

        Map<Integer, SbVideo> map = Maps.newHashMap();
        if (CollectionUtils.isNullOrEmpty(videoIdSet)) {
            return map;
        }
        SbVideoExample example = new SbVideoExample();
        example.createCriteria().andIdIn(new ArrayList<>(videoIdSet));
        List<SbVideo> videoList = videoMapper.selectByExample(example);
        for (SbVideo video : videoList){
            if (!map.containsKey(video.getId()))
                map.put(video.getId(),video);
        }
        return map;
    }

    public void offline(Integer id) {

        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbTopic topic = topicMapper.selectByPrimaryKey(id);
            if (null == topic) {
                throw new ResultException("视频不存在");
            }
            //校验视频是否被使用
            checkTopic(topic);

            topic.setModifyTime(new Date());
            topic.setStatus(TopicStatus.offline.getValue());
            topicMapper.updateByPrimaryKeySelective(topic);
            { //发布人(uid)发布视频数 -1
                Integer uid = topic.getUid();
                if (null != uid) {
                    DsmUser user = userMapper.selectByPrimaryKey(uid);
                    if (null != user) {
                        user.setPublishCount(user.getPublishCount() - 1);
                        userMapper.updateByPrimaryKeySelective(user);
                    }
                }
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("topic", "offline", e);
            throw new ResultException();
        }
    }

    @Override
    public void up(Integer id) {
        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbTopic topic = topicMapper.selectByPrimaryKey(id);
            if (null == topic) {
                throw new ResultException("视频不存在");
            }

            SbTopicExample example = new SbTopicExample();
            example.setLimit(1);
            example.setOffset(0);
            example.setOrderByClause("orders desc");
            SbTopicExample.Criteria criteria = example.createCriteria();
            criteria.andChannelIdEqualTo(topic.getChannelId());
            List<SbTopic> list = topicMapper.selectByExample(example);
            topic.setModifyTime(new Date());
            topic.setOrders(list.get(0).getOrders()+1);
            topicMapper.updateByPrimaryKeySelective(topic);

        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("topic", "offline", e);
            throw new ResultException();
        }
    }

    @Override
    public void delete(Integer topicId) {

        if (null == topicId) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
            if (null == topic) {
                throw new ResultException("视频不存在");
            }
            //校验视频是否被使用
            checkTopic(topic);

            { //删除video
                Integer videoId = topic.getVideoId();
                if (null != videoId) {
                    videoMapper.deleteByPrimaryKey(videoId);
                }
            }
            { //删除upload_video
                SbUploadVideosExample example = new SbUploadVideosExample();
                example.createCriteria().andTopicIdEqualTo(topicId);
                uploadMapper.deleteByExample(example);
            }
            { //删除topic
                topicMapper.deleteByPrimaryKey(topicId);
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.info("delete topic error ===>" + e.getMessage(), e);
            throw new ResultException();
        }

    }

    @Override
    @Transactional
    public void edit(Integer topicId, String title, Float coverTime, Boolean isDefault, String uploadCover) {

        if (null == topicId || null == isDefault) {
            throw new ResultException(ErrMsg.param_error);
        }
        if (StringUtil.isEmpty(title)) {
            throw new ResultException("视频标题不能为空");
        }
        SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
        if (null == topic) {
            throw new ResultException("视频不存在");
        }
        try {
            { //1.topic更新
                topic.setModifyTime(new Date());
                topic.setTitle(title);
                topicMapper.updateByPrimaryKeySelective(topic);
            }
            { //2.video更换封面
                if (!isDefault || StringUtil.isNotEmpty(uploadCover)) {

                    SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
                    if (null == video) {
                        throw new ResultException("视频信息丢失");
                    }
                    String newCover;
                    if (StringUtil.isNotEmpty(uploadCover)) { //优先选择重新上传的封面
                        if(!uploadCover.contains(Configuration.temp_path)){
                            throw new ResultException("重新上传文件有误");
                        }
                        newCover = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(uploadCover, OSSClientUtil.upload_type.video_cover);
                    } else {
                        //2.1 根据upload_video 获取本地视频
                        SbUploadVideosExample example = new SbUploadVideosExample();
                        example.createCriteria().andTopicIdEqualTo(topicId);
                        List<SbUploadVideos> uploadList = uploadMapper.selectByExample(example);
                        if (CollectionUtils.isNullOrEmpty(uploadList) || null == uploadList.get(0)) {
                            throw new ResultException("本地视频丢失");
                        }
                        SbUploadVideos upload = uploadList.get(0);
                        //2.2 本地视频
                        String localPath = StringUtil.trim(upload.getLocalUrl());
                        File LocalFile = new File(localPath);
                        if (localPath.isEmpty() || !LocalFile.exists()) {
                            throw new ResultException("本地视频丢失");
                        }
                        //2.3 截取封面
                        File newCoverFile = new File(Configuration.local_image_path + StringUtil.uuid() + OSSClientUtil.upload_type.image.suffix);
                        if (!newCoverFile.createNewFile()) {
                            throw new ResultException("创建临时文件失败");
                        }
                        RunShellUtils.getCover(localPath, newCoverFile.getAbsolutePath(), String.valueOf(coverTime));
                        //2.4 上传阿里云
                        newCover = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(newCoverFile.getAbsolutePath(), OSSClientUtil.upload_type.video_cover);
                    }
                    //2.5 更新新的封面
                    video.setCover(newCover);
                    video.setModifyTime(new Date());
                    videoMapper.updateByPrimaryKeySelective(video);
                }
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.info("edit topic error ===>\r" + e.getMessage(), e);
            throw new ResultException();
        }
    }

    @Override
    public List<Integer> getTopicIds(String name) {
        List<Integer> topicIds = null;
        SbTopicExample example = new SbTopicExample();
        SbTopicExample.Criteria criteria = example.createCriteria();
        criteria.andTitleLike(name);
        List<SbTopic> list = topicMapper.selectByExample(example);
        if (!CollectionUtils.isNullOrEmpty(list)){
            topicIds = new ArrayList<>();
            for (SbTopic t : list){
                if (t.getVideoId() != null)
                    topicIds.add(t.getId());
            }
        }
        return topicIds;
    }

    @Transactional
    public void publish(Integer id, Integer channelId, String title, Integer publisher, Integer operatorId, Float coverTime, boolean isDefault, String newCover) {

        if (null == id || null == channelId || StringUtil.isEmpty(title) || null == publisher) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbTopic topic = topicMapper.selectByPrimaryKey(id);
            if (null == topic) {
                throw new ResultException("视频不存在");
            }
            topic.setPublishTime(new Date());
            topic.setModifyTime(topic.getPublishTime());
            topic.setUid(publisher); //模拟用户
            topic.setPublisher(operatorId); //后台发布人
            topic.setChannelId(channelId);
            topic.setTitle(title);
            topic.setAppId(Configuration.current_app.getId());
            topic.setStatus(TopicStatus.published.getValue());
            topic.setAuditTime(new Date());
            topic.setAuditOpUid(operatorId);
            topic.setAuditStatus(AuditStatus.MAN_AUDIT_PASS.getValue());
            topicMapper.updateByPrimaryKeySelective(topic);
            { //截取封面
                SbUploadVideosExample example = new SbUploadVideosExample();
                SbUploadVideosExample.Criteria criteria = example.createCriteria();
                criteria.andTopicIdEqualTo(id);
                List<SbUploadVideos> videoList = uploadMapper.selectByExample(example);
                if (CollectionUtils.isNullOrEmpty(videoList)) {
                    throw new ResultException("上传视频数据为空");
                }
                SbUploadVideos uploadVideos = videoList.get(0);
                String localUrl = StringUtil.trim(uploadVideos.getLocalUrl());
                if (localUrl.length() == 0 || !new File(localUrl).exists()) {
                    throw new ResultException("本地视频丢失");
                }
                //视频数据
                SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
                if (null == video) {
                    throw new ResultException("视频数据不存在");
                }
                //原封面图(默认抽取)
                String cover = StringUtil.trim(video.getCover());
                if(StringUtil.isEmpty(newCover)) { //未重新选择封面
                    if (!isDefault) {
                        String coverName = StringUtil.uuid();
                        File file = new File(Configuration.local_image_path + coverName + OSSClientUtil.upload_type.image.suffix);
                        if (!file.createNewFile()) {
                            throw new ResultException("创建临时文件失败");
                        }
                        RunShellUtils.getCover(localUrl, file.getAbsolutePath(), String.valueOf(coverTime));
                        cover = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(file.getAbsolutePath(), OSSClientUtil.upload_type.video_cover);
                    }
                } else { //一旦重新选择,优先使用重新上传的封面
                    if(!newCover.contains(Configuration.temp_path)){
                        throw new ResultException("重新上传封面有误");
                    }
                    cover = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(newCover, OSSClientUtil.upload_type.video_cover);
                }
                { //视频表同步
                    video.setAppId(Configuration.current_app.getId());
                    video.setUid(publisher);
                    video.setModifyTime(new Date());
                    video.setCover(cover);
                    videoMapper.updateByPrimaryKeySelective(video);
                }
            }
            { //用户发布量+1
                DsmUserExample example = new DsmUserExample();
                DsmUserExample.Criteria criteria = example.createCriteria();
                criteria.andUidEqualTo(publisher);
                List<DsmUser> userList = userMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(userList)) {

                    DsmUser user = userList.get(0);
                    user.setPublishCount(user.getPublishCount() + 1);
                    userMapper.updateByPrimaryKeySelective(user);
                } else {
                    throw new ResultException("发布人不存在");
                }
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("topic", "publish", e);
            throw new ResultException();
        }

    }

    /**
     * 批量通过or不通过
     * @param ids
     * @param reason
     * @param auditStatus
     * @param operatorId
     */
    @Transactional
    public void batchOperate(Integer[] ids, String reason, AuditStatus auditStatus, Integer operatorId) {

        if (CollectionUtils.arrayIsNullOrEmpty(ids)) {
            throw new ResultException(ErrMsg.param_error);
        }
        for (Integer id : ids) {
            passOrNot(id, reason, auditStatus, operatorId);
        }
    }

    @Override
    public ReturnDto<SbTopicDto> paginate(ParamDto param, String publishTime, Integer videoId, Integer publishStatus, String title, Integer uploader,Integer channelId) {

        try {
            SbTopicExample example = new SbTopicExample();
            SbTopicExample.Criteria criteria = example.createCriteria();
            if (null != videoId) {
                criteria.andIdEqualTo(videoId);
            }
            if (StringUtil.isNotEmpty(publishTime)) {
                criteria.andPublishTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(publishTime));
            }
            if (null != publishStatus) {
                criteria.andStatusEqualTo(publishStatus);
                if (publishStatus.equals(TopicStatus.publishing.getValue())) {
                    example.setOrderByClause("create_time desc");
                } else {
                    example.setOrderByClause("orders desc, publish_time desc");
                }
            }
            if (StringUtil.isNotEmpty(title)) {
                criteria.andTitleLike(StringUtil.sqlLike(title));
            }
            if (null != uploader) {
                criteria.andCreateOpeUidEqualTo(uploader);
            }
            if (null  != channelId)
                criteria.andChannelIdEqualTo(channelId);
            criteria.andVideoIdIsNotNull();
            criteria.andSourceEqualTo(TopicSource.OWER.getValue());
            Long total = topicMapper.countByExample(example);
            List<SbTopicDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                List<SbTopic> topicList = topicMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(topicList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    Set<Integer> channelIdSet = Sets.newHashSet();
                    Set<Integer> videoIdSet = Sets.newHashSet();
                    Set<Integer> userIdSet = Sets.newHashSet();
                    topicList.forEach(topic -> {
                        SbTopicDto dto;
                        BeanUtils.copyProperties(topic, dto = new SbTopicDto());
                        dtoList.add(dto);
                        if (null != topic.getUid()) {
                            userIdSet.add(topic.getUid());
                        }
                        if (null != topic.getCreateOpeUid()) {
                            adminIdSet.add(Long.valueOf(topic.getCreateOpeUid()));
                        }
                        if (null != topic.getPublisher()) {
                            adminIdSet.add(Long.valueOf(topic.getPublisher()));
                        }
                        if (null != topic.getChannelId()) {
                            channelIdSet.add(topic.getChannelId());
                        }
                        if (null != topic.getVideoId()) {
                            videoIdSet.add(topic.getVideoId());
                        }
                    });
                    Map<Integer, String> adminMap = adminService.userNameMap(adminIdSet);
                    Map<Integer, String> channelMap = channelService.channelMap(channelIdSet);
                    Map<Integer, DsmUser> userMap = userService.userMapByUid(userIdSet);
                    Map<Integer, SbVideo> videoMap = videoMap(videoIdSet);
                    dtoList.forEach(dto -> {
                        Integer publisher = dto.getPublisher(),uid = dto.getUid(), adminUploader = dto.getCreateOpeUid(),cId = dto.getChannelId(), vId = dto.getVideoId();
                        if (null != cId && channelMap.containsKey(cId)) {
                            dto.setCategoryName(channelMap.get(cId));
                        }
                        if (null != uid && userMap.containsKey(uid)) {
                            DsmUser user = userMap.get(uid);
                            if (null != user) {
                                dto.setNickName(user.getNickName());
                            }
                        }
                        if (null != adminUploader && adminMap.containsKey(adminUploader)) {
                            dto.setOperatorName(adminMap.get(adminUploader));
                        }
                        if (null != publisher && adminMap.containsKey(publisher)) {
                            dto.setPublisherName(adminMap.get(publisher));
                        }
                        if (null != vId && videoMap.containsKey(vId)) {
                            SbVideo video = videoMap.get(vId);
                            if (null != video) {
                                dto.setVideoUrl(StringUtil.trim(video.getOriUrl()));
                                dto.setCover(StringUtil.trim(video.getCover()));
                            }
                        }
                    });
                }
            }
            Page<SbTopicDto> page = param.page();
            page.setItems(dtoList);
            page.setTotal(total.intValue());
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("paginate for publish error ===> \r" + e.getMessage());
            return new ReturnDto<>(null);
        }
    }




    /**
     * 通过or不通过
     * @param id
     * @param reason
     * @param auditStatus
     * @param operatorId
     */
    public void passOrNot(Integer id, String reason, AuditStatus auditStatus, Integer operatorId) {

        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbTopic topic = topicMapper.selectByPrimaryKey(id);
            if (null == topic) {
                throw new ResultException("话题不存在");
            }
            topic.setAuditStatus(auditStatus.getValue());
            topic.setAuditOpUid(operatorId);
            topic.setAuditTime(new Date());
            topic.setModifyTime(topic.getAuditTime());
            topic.setAuditDesc(reason);
            topic.setPublisher(operatorId);
            topic.setPublishTime(new Date());
            topicMapper.updateByPrimaryKeySelective(topic);
            //如果通过审核该用户的发布量要加一
            if (AuditStatus.MAN_AUDIT_PASS == auditStatus){
                UserDto userDto = userService.getUser(topic.getUid());
                if (userDto != null){
                    DsmUser record = new DsmUser();
                    record.setUid(userDto.getUid());
                    record.setPublishCount(userDto.getPublishCount()+1);
                    userService.update(record);
                }
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("topic", "passOrNot - " + auditStatus.getName(), e);
            throw new ResultException();
        }

    }

    @Override
    protected void specificVerify(Object model, boolean isUpdate) {

        SbTopic topic = (SbTopic) model;
        if (StringUtil.isEmpty(topic.getTitle())) {
            throw new ResultException("名称不能为空");
        }
        if (isUpdate) {
            if (null == topic.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
        }
    }

    private void checkTopic(SbTopic topic) {

        if (null != topic && null != topic.getId()) {
            String idStr = String.valueOf(topic.getId());
            {// 1.是否存在于桶中
                SbTopicBucketExample example = new SbTopicBucketExample();
                example.createCriteria().andTopicIdsLike(idStr + ",%");
                example.or().andTopicIdsLike(StringUtil.sqlLike(","+idStr + ","));
                example.or().andTopicIdsLike("%,"+idStr);
                List<SbTopicBucket> bucketList = bucketMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(bucketList)) {
                    SbTopicBucket bucket = bucketList.get(0);
                    throw new ResultException(String.format("该视频存在于【%d】号桶中,暂时不可操作", bucket.getId()));
                }
            }
            {// 2.是否存在于活动中
                if (null != topic.getActivityId()) {
                    SbActivity activity = activityMapper.selectByPrimaryKey(topic.getActivityId());
                    if (null != activity && activity.getWhetherOnline().equals(Whether.yes.getValue())) {
                        throw new ResultException(String.format("该视频已参加【%s】活动,暂时不可操作", topic.getTitle()));
                    }
                }
            }
            { //3.是否存在合辑中
                SbTopicAlbumExample example = new SbTopicAlbumExample();
                example.createCriteria().andTopicIdsLike(idStr + ",%");
                example.or().andTopicIdsLike(StringUtil.sqlLike(","+idStr + ","));
                example.or().andTopicIdsLike("%,"+idStr);
                List<SbTopicAlbum> albumList = albumMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(albumList)) {
                    SbTopicAlbum album = albumList.get(0);
                    if(null != album && !album.getStatus().equals(AlbumStatus.offline.getValue())) {
                        throw new ResultException(String.format("该视频存在于【%s】合辑中,暂时不可操作", album.getTitle()));
                    }
                }
            }
        }
    }

    private Map<Integer, SbVideo> videoMap(Set<Integer> videoIdSet) {

        Map<Integer, SbVideo> videoMap = Maps.newHashMap();
        if (!CollectionUtils.isNullOrEmpty(videoIdSet)) {
            SbVideoExample example = new SbVideoExample();
            SbVideoExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(Lists.newArrayList(videoIdSet));
            List<SbVideo> videoList = videoMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(videoList))
                videoList.forEach(video -> videoMap.put(video.getId(), video));
        }
        return videoMap;
    }
}
