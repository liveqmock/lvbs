package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.dto.SbUserCommentDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.*;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserCommentMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by feiFan.gou on 2017/8/28 15:37.
 * 素材管理service
 */
@Service
public class UserCommentService implements IUserCommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommentService.class);
    @Autowired
    private SbUserCommentMapper userCommentMapper;
    @Autowired
    private SbTopicMapper topicMapper;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IAlbumService albumService;


    @Override
    public ReturnDto<SbUserCommentDto> paginate(ParamDto param, String content,Integer type, String createTime, Integer status, Integer auditStatus,Integer appId) {

        try {
            SbUserCommentExample example = new SbUserCommentExample();
            SbUserCommentExample.Criteria criteria = example.createCriteria();
            {
                if (StringUtil.isNotEmpty(createTime)) {
                    criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(createTime));
                }
                if (null != auditStatus){
                    if (auditStatus.equals(CommentAuditStatus.auditing.getValue())) {
                        criteria.andAuditStatusIn(Lists.newArrayList(
                                AuditStatus.WAIT.getValue(),
                                AuditStatus.MACHINE_AUDIT_NOT_PASS.getValue(),
                                AuditStatus.MACHINE_AUDIT_PASS.getValue()
                        ));
                        example.setOrderByClause("create_time desc");
                    } else {
                        example.setOrderByClause("audit_time desc");
                        criteria.andAuditStatusEqualTo(auditStatus);
                    }
                }
                if (type != null){
                    criteria.andTargetTypeEqualTo(type);
                }
            }
            criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            Page<SbUserCommentDto> page = param.page();
            Long total = userCommentMapper.countByExample(example);
            List<SbUserCommentDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                List<SbUserComment> userCommentsList = userCommentMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(userCommentsList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    Set<Integer> topicIdSet = Sets.newHashSet();
                    Set<Integer> actIdSet = Sets.newHashSet();
                    Set<Integer> albumIdSet = Sets.newHashSet();
                    Set<Integer> uIdSet = Sets.newHashSet();
                    userCommentsList.forEach(userComment -> {
                        SbUserCommentDto dto = new SbUserCommentDto();
                        if (null != userComment.getAuditOpUid()) {
                            adminIdSet.add(Long.valueOf(userComment.getAuditOpUid()));
                        }
                        if (null != userComment.getTargetId()){
                            if (UserCommentTargetType.TOPIC == UserCommentTargetType.get(userComment.getTargetType())){
                                topicIdSet.add(userComment.getTargetId());
                            }else if (UserCommentTargetType.CHANNEL == UserCommentTargetType.get(userComment.getTargetType())){
                                actIdSet.add(userComment.getTargetId());
                            }else if (UserCommentTargetType.USER == UserCommentTargetType.get(userComment.getTargetType())){

                            }else if (UserCommentTargetType.COMMENT == UserCommentTargetType.get(userComment.getTargetType())){

                            }else if (UserCommentTargetType.ALBUM == UserCommentTargetType.get(userComment.getTargetType())){
                                albumIdSet.add(userComment.getTargetId());
                            }else if (UserCommentTargetType.ACTIVITY == UserCommentTargetType.get(userComment.getTargetType())){
                                actIdSet.add(userComment.getTargetId());
                            }
                        }
                        if (null != userComment.getUid()){
                            uIdSet.add(userComment.getUid());
                        }
                        BeanUtils.copyProperties(userComment, dto);
                        dtoList.add(dto);
                    });
                    //审核者
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    //话题
                    Map<Integer,SbTopic> topicMap = topicService.getTopicDtoLists(topicIdSet);

                    //发送者昵称
                    Map<Integer,DsmUser> userNameMap = userService.userMapByUid(uIdSet);

                    //活动
                    Map<Integer,String> activityMap = activityService.getActivityMap(actIdSet);
                    //合辑
                    Map<Integer,String> albumIdMap = albumService.getAlbumMap(albumIdSet);

                    dtoList.forEach(dto -> {
                        if (null != dto.getAuditOpUid()) {
                            dto.setAuditor(adminNameMap.get(dto.getAuditOpUid()));

                        }
                        if (null != dto.getTargetId()) {
                            if (UserCommentTargetType.TOPIC == UserCommentTargetType.get(dto.getTargetType())){
                                SbTopic topic = topicMap.get(dto.getTargetId());
                                dto.setVideoTitle(topic.getTitle());
                            }else if (UserCommentTargetType.CHANNEL == UserCommentTargetType.get(dto.getTargetType())){

                            }else if (UserCommentTargetType.USER == UserCommentTargetType.get(dto.getTargetType())){

                            }else if (UserCommentTargetType.COMMENT == UserCommentTargetType.get(dto.getTargetType())){

                            }else if (UserCommentTargetType.ALBUM == UserCommentTargetType.get(dto.getTargetType())){
                                dto.setVideoTitle(albumIdMap.get(dto.getTargetId()));
                            }else if (UserCommentTargetType.ACTIVITY == UserCommentTargetType.get(dto.getTargetType())){
                                dto.setVideoTitle(activityMap.get(dto.getTargetId()));
                            }
                        }
                        if (null != dto.getUid()) {
                            DsmUser user = userNameMap.get(dto.getUid());
                            if (null != user) {
                                dto.setReviewer(user.getNickName());
                                if (null != user.getType()) {
                                    UserType userType = UserType.get(user.getType());
                                    if (null != userType) {
                                        dto.setUserType(userType.getName());
                                    }
                                }
                                dto.setMobile(StringUtil.trim(user.getMobile()));
                            }
                        }
                    });
                }
            }
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (ResultException e) {
            LOGGER.info("relation table have no data, so return empty -----");
            return new ReturnDto<>(null);
        } catch (Exception e) {
            LOGGER.info("material paginate error --- > \r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    public SbUserCommentDto findUserCommentById(Integer commentId){
        SbUserComment userComment = userCommentMapper.selectByPrimaryKey(commentId);
        SbUserCommentDto dto = new SbUserCommentDto();
        if (userComment != null){
            BeanUtils.copyProperties(userComment,dto);
        }
        return dto;
    }

    @Override
    public Map<Integer, SbUserComment> getUserCommentLists(Set<Integer> contentIdSet) {
        Map<Integer, SbUserComment> map = Maps.newHashMap();
        SbUserCommentExample example = new SbUserCommentExample();
        SbUserCommentExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(new ArrayList<>(contentIdSet));
        List<SbUserComment> list = userCommentMapper.selectByExample(example);
        for (SbUserComment userComment : list){
            if (!map.containsKey(userComment.getId()))
                map.put(userComment.getId(),userComment);
        }
        return map;
    }

    @Override
    public void fail(Integer operatorId, String auditDesc,String ...ids) {

        try {
            updateAuditStatus(operatorId, CommentAuditStatus.audit_not_pass.getValue(),auditDesc, ids);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            throw new ResultException();
        }

    }

    @Override
    public void pass(Integer operatorId, String ...ids) {

        try {
            updateAuditStatus(operatorId, CommentAuditStatus.audit_pass.getValue(),StringUtil.empty, ids);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            throw new ResultException();
        }

    }

    @Transactional
    void updateAuditStatus(Integer operatorId, Integer auditStatus, String auditDesc,String ...ids) {

        if (CollectionUtils.arrayIsNullOrEmpty(ids)) {
            throw new ResultException(ErrMsg.param_error);
        }
        synchronized (this){
            List<Integer> idList = Lists.newArrayList();
            for (String id : ids) {
                idList.add(Integer.valueOf(id));
            }
            SbUserCommentExample example = new SbUserCommentExample();
            example.createCriteria().andIdIn(idList);
            List<SbUserComment> commentList = userCommentMapper.selectByExample(example);
            if (ids.length != commentList.size()) {
                throw new ResultException("评论不存在");
            }
            SbUserComment condition = new SbUserComment();
            condition.setAuditOpUid(operatorId);
            condition.setAuditTime(new Date());
            condition.setAuditStatus(auditStatus);
            if (StringUtil.isNotEmpty(auditDesc)) {//审核不通过
                condition.setAuditDesc(auditDesc);
                //话题评论数 -1
                {
                    commentList.forEach(comment -> {
                        if(CommonStatus.get(comment.getStatus()) == CommonStatus.normal){
                            //视频
                            if (UserCommentTargetType.get(comment.getTargetType()) == UserCommentTargetType.TOPIC){
                                Integer topicId = comment.getTargetId();
                                if (null != topicId) {
                                    SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
                                    if (null != topic) {
                                        topic.setReplyNum(topic.getReplyNum() - 1);
                                        topicMapper.updateByPrimaryKeySelective(topic);
                                    }
                                }
                            }else if(UserCommentTargetType.get(comment.getTargetType()) == UserCommentTargetType.ACTIVITY){
                                Integer actId = comment.getTargetId();
                                if (null != actId) {
                                    SbActivity activity = activityService.getActivityById(actId);
                                    if (null != activity) {
                                        activity.setReplyNum(activity.getReplyNum() >0 ? activity.getReplyNum() - 1 : 0);
                                        activityService.update(activity,operatorId);
                                    }
                                }
                            }else if(UserCommentTargetType.get(comment.getTargetType()) == UserCommentTargetType.ALBUM){
                                Integer albumId = comment.getTargetId();
                                if (null != albumId) {
                                    SbTopicAlbum topicAlbum = albumService.getAlbumById(albumId);
                                    if (null != topicAlbum) {
                                        topicAlbum.setReplyNum(topicAlbum.getReplyNum() >0 ? topicAlbum.getReplyNum() - 1 : 0);
                                        albumService.update(topicAlbum,operatorId);
                                    }
                                }
                            }
                        }
                    });
                }
            }
            else {
                condition.setAuditDesc("审核通过");
            }
            userCommentMapper.updateByExampleSelective(condition,example);
        }
    }

    public void update(SbUserComment record){
        if (record != null && record.getId() != null){
            userCommentMapper.updateByPrimaryKeySelective(record);
        }
    }

}
