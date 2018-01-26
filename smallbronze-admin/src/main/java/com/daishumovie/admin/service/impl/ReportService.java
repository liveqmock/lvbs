package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.dto.ReportDto;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.SbUserCommentDto;
import com.daishumovie.admin.dto.UserDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.service.*;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.mapper.smallbronze.SbChannelMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserReportMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/9/6 15:24.
 */
@Service
public class ReportService implements IReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
    @Autowired
    private SbTopicMapper topicMapper;
    @Autowired
    private SbUserReportMapper userReportMapper;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private ITopicService topicService;

    @Autowired
    private IUserCommentService commentService;
    @Autowired
    private SbVideoMapper videoMapper;
    @Autowired
    private SbChannelMapper channelMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IAlbumService albumService;

    @Override
    public ReturnDto<ReportDto> paginate(ParamDto param, ReportType type,String createTime, Integer auditStatus,Integer appId) {

        try {
            SbUserReportExample example = condition(type, auditStatus, createTime, appId);
            Long total = userReportMapper.countByExample(example);
            List<ReportDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                example.setOrderByClause(param.orderString());
                List<SbUserReport> reportList = userReportMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(reportList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    Set<Integer> contentIdSet = Sets.newHashSet();
                    Set<Integer> userIdSet = Sets.newHashSet();
                    reportList.forEach(report -> {
                        ReportDto dto = new ReportDto();
                        BeanUtils.copyProperties(report, dto);
                        if (null != report.getAuditId()) {
                            adminIdSet.add(Long.valueOf(report.getAuditId()));
                        }
                        if (null != report.getContentId()) {
                            contentIdSet.add(report.getContentId());
                        }
                        if (null != report.getUid()) {
                            userIdSet.add(report.getUid());
                        }
                        dtoList.add(dto);
                    });
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    //举报者昵称
                    Map<Integer, DsmUser> uidMap = userService.userMapByUid(userIdSet);
                    dtoList.forEach(dto -> {
                        Integer auditId = dto.getAuditId();
                        if (null != auditId && adminNameMap.containsKey(auditId)) {
                            dto.setAuditName(adminNameMap.get(auditId));
                        }
                        Integer uid = dto.getUid();
                        if (null != uid) {
                            DsmUser user = uidMap.get(uid);
                            if (null != user) {
                                dto.setNickName(user.getNickName());
                                UserType userType = UserType.get(user.getType());
                                if (null != userType) {
                                    dto.setUserType(userType.getName());
                                }
                                dto.setMobile(user.getMobile());
                            }
                        }
                    });
                    if (type == ReportType.VIDEO){
                        Map<Integer,SbTopic> topicMap = topicService.getTopicDtoLists(contentIdSet);
                        dtoList.forEach(dto -> {
                            Integer contentId = dto.getContentId();
                            if (null != contentId) {
                                SbTopic topic = topicMap.get(contentId);
                                if (topic != null){
                                    dto.setContentName(topic.getTitle());
                                    //查询视频地址
                                    SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
                                    if (null != video){
                                        dto.setCover(video.getCover());
                                        dto.setVideoUrl(video.getFormatUrl());
                                        dto.setVideoReleaseTime(DateUtil.BASIC.format(video.getCreateTime()));
                                    }
                                    //查询二级频道
                                    SbChannel channel = channelMapper.selectByPrimaryKey(topic.getChannelId());
                                    if (null != channel)
                                        dto.setCategoryName(channel.getName());
                                    //视频发布者信息
                                    DsmUser user = userService.getUser(topic.getUid());
                                    if (user != null){
                                        dto.setBeMobile(user.getMobile());
                                        dto.setBeNickName(user.getNickName());
                                        UserType userType = UserType.get(user.getType());
                                        if (null != userType) {
                                            dto.setBeUserType(userType.getName());
                                        }
                                    }
                                }
                            }
                        });
                    }else if(type == ReportType.COMMENT){
                        Map<Integer,SbUserComment> usercontentMap = commentService.getUserCommentLists(contentIdSet);
                        dtoList.forEach(dto -> {
                            Integer contentId = dto.getContentId();
                            if (null != contentId) {
                                SbUserComment userComment = usercontentMap.get(contentId);
                                if (userComment != null){
                                    dto.setContentName(userComment.getContent());
                                    dto.setVideoReleaseTime(DateUtil.BASIC.format(userComment.getCreateTime()));
                                    //视频发布者信息
                                    DsmUser user = userService.getUser(userComment.getUid());
                                    if (user != null){
                                        dto.setBeMobile(user.getMobile());
                                        dto.setBeNickName(user.getNickName());
                                        UserType userType = UserType.get(user.getType());
                                        if (null != userType) {
                                            dto.setBeUserType(userType.getName());
                                        }
                                    }
                                }
                            }
                        });
                    }else if(type == ReportType.USER){
                        Map<Integer,DsmUser> userMap = userService.userMapByUid(contentIdSet);
                        dtoList.forEach(dto -> {
                            Integer contentId = dto.getContentId();
                            if (null != contentId) {
                                DsmUser user = userMap.get(contentId);
                                if (user != null){
                                    dto.setContentName(user.getNickName());
                                }
                            }
                        });
                    }
                }
            }
            Page<ReportDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("paginate app user error --- >" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    public void passOrNot(Integer id,Integer contentId, Integer reason, ReportType type,ReportStatusEnum auditStatus, Integer operatorId) {
        if (type == ReportType.VIDEO){
            //step 1 更新话题表状态

            if (auditStatus == ReportStatusEnum.offline){
                SbTopicDto topicDto = topicService.findSbTopicDtoById(contentId);
                if (topicDto != null){
                    //更新视频主体
                    SbTopic sbTopic = new SbTopic();
                    sbTopic.setId(topicDto.getId());
                    sbTopic.setStatus(YesNoEnum.NO.getCode());
                    topicService.update(sbTopic);
                    SbUserReportExample example = new SbUserReportExample();
                    example.createCriteria().andTypeEqualTo(type.getCode()).andContentIdEqualTo(contentId).andStatusEqualTo(ReportStatusEnum.offline.getCode());
                    if (userReportMapper.countByExample(example) == 0 && !topicDto.getStatus().equals(TopicStatus.offline.getValue())) { //第一次举报才-1
                        //更新用户发布量
                        UserDto dto = userService.getUser(topicDto.getUid());
                        if (dto != null){
                            DsmUser record = new DsmUser();
                            record.setUid(dto.getUid());
                            record.setPublishCount(dto.getPublishCount() > 0 ? dto.getPublishCount() - 1 : 0);
                            userService.update(record);
                        }
                    }
                    //TODO 给举报人和被举报人发消息
                }
            }
        }else if (type == ReportType.COMMENT){
            SbUserCommentDto dto = commentService.findUserCommentById(contentId);
            if (dto != null){
                if (auditStatus == ReportStatusEnum.offline){
                    SbUserComment record = new SbUserComment();
                    record.setId(contentId);
                    record.setStatus(YesNoEnum.NO.getCode());
                    commentService.update(record);
                }else{
                    SbUserComment record = new SbUserComment();
                    record.setId(contentId);
                    record.setStatus(YesNoEnum.YES.getCode());
                    commentService.update(record);
                    if (UserCommentTargetType.get(dto.getTargetType()) == UserCommentTargetType.TOPIC){
                        Integer topicId = dto.getTargetId();
                        if (null != topicId) {
                            SbTopicDto topicDto = topicService.findSbTopicDtoById(dto.getTargetId());
                            if (null != topicDto) {
                                SbTopic sbTopic = new SbTopic();
                                sbTopic.setId(topicDto.getId());
                                sbTopic.setReplyNum(topicDto.getReplyNum() + 1);
                                topicService.update(sbTopic);
                            }
                        }
                    }else if(UserCommentTargetType.get(dto.getTargetType()) == UserCommentTargetType.ACTIVITY){
                        Integer actId = dto.getTargetId();
                        if (null != actId) {
                            SbActivity activity = activityService.getActivityById(actId);
                            if (null != activity) {
                                activityService.updateReplyNum(activity.getId(),activity.getReplyNum() + 1);
                            }
                        }
                    }else if(UserCommentTargetType.get(dto.getTargetType()) == UserCommentTargetType.ALBUM){
                        Integer albumId = dto.getTargetId();
                        if (null != albumId) {
                            SbTopicAlbum topicAlbum = albumService.getAlbumById(albumId);
                            if (null != topicAlbum) {
                                albumService.updateReplyNum(topicAlbum.getId(),topicAlbum.getReplyNum()+ 1);
                            }
                        }
                    }
                }
            }

        }else if (type == ReportType.USER){
            if (auditStatus == ReportStatusEnum.offline) {
                DsmUser record = new DsmUser();
                record.setUid(contentId);
//                record.setStatus(YesNoEnum.NO.getCode());
                record.setIsTopicAuth(YesNoEnum.NO.getCode());
                record.setIsReplyAuth(YesNoEnum.NO.getCode());
                userService.update(record);

                //TODO 给举报人和被举报人发消息
            }
        }

        //step 2 更新举报表
        SbUserReport record = new SbUserReport();
        record.setId(id);
        record.setStatus(auditStatus.getCode());
        record.setAuditDesc(ReportResultEnum.get(reason).getCode()+"");
        record.setAuditTime(new Date());
        record.setAuditId(operatorId);
        userReportMapper.updateByPrimaryKeySelective(record);

    }

    private SbUserReportExample condition(ReportType type,Integer status, String registerTime,Integer appId) {

        SbUserReportExample example = new SbUserReportExample();
        SbUserReportExample.Criteria criteria = example.createCriteria();
        if (null != status) {
            criteria.andStatusEqualTo(status);
        }
        if (null != appId){
            criteria.andAppIdEqualTo(appId);
        }
        if (null != type){
            criteria.andTypeEqualTo(type.getCode());
        }
        if (StringUtil.isNotEmpty(registerTime)) {
            criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(registerTime));
        }
        return example;
    }
}
