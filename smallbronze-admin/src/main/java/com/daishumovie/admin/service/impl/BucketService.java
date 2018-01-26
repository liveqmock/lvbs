package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.BucketDto;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.VideoInfoDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IBucketService;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.admin.service.IUserService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.mapper.smallbronze.SbTopicBucketMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/9/21 15:34.
 */
@Service
public class BucketService implements IBucketService {

    @Autowired
    private SbTopicBucketMapper bucketMapper;
    @Autowired
    private SbTopicMapper topicMapper;
    @Autowired
    private SbVideoMapper videoMapper;

    @Autowired
    private IChannelService channelService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAdminService adminService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BucketService.class);

    @Override
    public ReturnDto<SbTopicDto> paginate(ParamDto param, String idsStr, Integer channelId, String createTime, Integer inside, String username, String topicIds, Boolean...isAdd) {

        try {
            SbTopicExample example = new SbTopicExample();
            SbTopicExample.Criteria criteria = example.createCriteria();
            if (!CollectionUtils.arrayIsNullOrEmpty(isAdd) && isAdd[0]) {
                criteria.andStatusEqualTo(TopicStatus.published.getValue());
            }
            criteria.andAuditStatusEqualTo(AuditStatus.MAN_AUDIT_PASS.getValue());
            if (StringUtil.isNotEmpty(idsStr)) {
                List<Integer> idList = Lists.newArrayList();
                for (String id : idsStr.split("[\\s]+")) {
                    idList.add(Integer.parseInt(id));
                }
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
                    return new ReturnDto<>(null);
                }
            }
            if (null != inside) {
                Whether whether = Whether.get(inside);
                if (null != whether) {
                    List<Integer> insideList = insideList();
                    switch (whether) {
                        case no:
                            if (!insideList.isEmpty()) {
                                criteria.andIdNotIn(insideList);
                            }
                            break;
                        case yes:
                            if (!insideList.isEmpty()) {
                                criteria.andIdIn(insideList);
                            }
                    }
                }
            }
            List<SbTopicDto> dtoList = Lists.newArrayList();
            Long total = topicMapper.countByExample(example);
            if (StringUtil.isNotEmpty(topicIds)) {
                if(param.getPage_number() == 1){
                    List<Integer> chosenIdList = Lists.newArrayList();
                    for (String topicId : topicIds.split(",")) {
                        chosenIdList.add(Integer.parseInt(topicId));
                    }
                    if (!chosenIdList.isEmpty()) {
                        criteria.andIdIn(chosenIdList);
                    }
                    StringBuilder builder = new StringBuilder();
                    for (Integer chosenId : chosenIdList) builder.append(",").append(chosenId);
                    example.setOrderByClause("field(id," + builder.toString().substring(1) + ")");
                }
            }
            if (total > 0) {
                if (StringUtil.isNotEmpty(topicIds)) {
                    if(param.getPage_number() != 1){
                        param.setPage_number(param.getPage_number() - 1);
                        example.setOrderByClause("create_time desc");
                    }
                } else {
                    example.setOrderByClause("create_time desc");
                }
                example.setOffset(param.offset());
                example.setLimit(param.limit());
                List<SbTopic> topicList = topicMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(topicList)) {
                    Set<Integer> channelIdSet = Sets.newHashSet();
                    Set<Integer> userIdSet = Sets.newHashSet();
                    topicList.forEach(topic -> {
                        if (null != topic.getChannelId()) {
                            channelIdSet.add(topic.getChannelId());
                        }
                        if (null != topic.getUid()) {
                            userIdSet.add(topic.getUid());
                        }
                        SbTopicDto dto = new SbTopicDto();
                        BeanUtils.copyProperties(topic, dto);
                        dtoList.add(dto);
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
                                if (null != userType) {
                                    dto.setUserType(userType.getName());
                                }
                                dto.setNickName(user.getNickName());
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

    @Override
    public ReturnDto<BucketDto> paginate(ParamDto param, Integer id, String createTime, String adminName) {

        try {
            SbTopicBucketExample example = new SbTopicBucketExample();
            SbTopicBucketExample.Criteria criteria = example.createCriteria();
            if (null != id) {
                criteria.andIdEqualTo(id);
            }
            if (StringUtil.isNotEmpty(createTime)) {
                criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(createTime));
            }
            if (StringUtil.isNotEmpty(adminName)) {
                List<Integer> uidList = adminService.userIdListByNameLike(adminName);
                if (!uidList.isEmpty()) {
                    criteria.andOptIdIn(uidList);
                } else {
                    return new ReturnDto<>(null);
                }
            }
            Long total = bucketMapper.countByExample(example);
            List<BucketDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                example.setOrderByClause("id desc");
                List<SbTopicBucket> bucketList = bucketMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(bucketList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    bucketList.forEach(bucket -> {
                        BucketDto dto = new BucketDto();
                        BeanUtils.copyProperties(bucket, dto);
                        dtoList.add(dto);
                        if (null != bucket.getOptId()) {
                            adminIdSet.add(Long.valueOf(bucket.getOptId()));
                        }
                    });
                    Map<Integer, String> adminMap = adminService.userNameMap(adminIdSet);
                    dtoList.forEach(dto -> {
                        Integer operatorId = dto.getOptId();
                        if (null != operatorId && adminMap.containsKey(operatorId)) {
                            dto.setOperatorName(adminMap.get(operatorId));
                        }
                        //计算桶里面有几个视频,暂时去掉,固定为8个
                        /*String videoIdStr = StringUtil.trim(dto.getTopicIds());
                        if (videoIdStr.length() > 0) {
                            String videoIds[] = videoIdStr.split(",");
                            dto.setVideoCount(videoIds.length);
                        }*/
                    });
                }
            }
            Page<BucketDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("paginate error ===>\r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }

    }

    @Override
    public List<VideoInfoDto> videoList(Integer[] topicIds) {

        List<VideoInfoDto> videoList = Lists.newArrayList();
        if (!CollectionUtils.arrayIsNullOrEmpty(topicIds)) {
            SbTopicExample example = new SbTopicExample();
            SbTopicExample.Criteria criteria = example.createCriteria();
            criteria.andAuditStatusEqualTo(AuditStatus.MAN_AUDIT_PASS.getValue());
            criteria.andIdIn(Lists.newArrayList(topicIds));
            StringBuilder builder = new StringBuilder("");
            for (Integer topicId : topicIds) {
                builder.append(",").append(topicId);
            }
            example.setOrderByClause("field(id," + builder.toString().substring(1) + ")");
            Map<Integer, SbTopic> videoIdMap = Maps.newHashMap();
            List<SbTopic> topicList = topicMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(topicList)) {
                List<Integer> videoIdList = Lists.newArrayList();
                topicList.forEach(topic -> videoIdMap.put(topic.getVideoId(), topic));
                topicList.forEach(topic -> videoIdList.add(topic.getVideoId()));

                SbVideoExample videoExample = new SbVideoExample();
                SbVideoExample.Criteria videoCriteria = videoExample.createCriteria();
                builder = new StringBuilder();
                for (Integer videoId : videoIdList) builder.append(",").append(videoId);
                videoCriteria.andIdIn(videoIdList);
                videoExample.setOrderByClause("field(id," + builder.toString().substring(1) + ")");
                List<SbVideo> videos = videoMapper.selectByExample(videoExample);
                if (!CollectionUtils.isNullOrEmpty(videos)) {
                    videos.forEach(video -> {
                        SbTopic topic = videoIdMap.get(video.getId());
                        if (null != topic) {
                            VideoInfoDto videoInfo = new VideoInfoDto(video,topic.getTitle());
                            videoInfo.setTopicId(topic.getId());
                            videoList.add(videoInfo);
                        }
                    });
                }
            }
        }
        return videoList;
    }

    public VideoInfoDto videoInfo(Integer videoId) {

        SbVideo video = videoMapper.selectByPrimaryKey(videoId);
        return new VideoInfoDto(video, StringUtil.empty);
    }

    @Override
    public void save(String videoIds, String remarks, Integer operatorId) {

        if (StringUtil.isEmpty(videoIds)) {
            throw new ResultException(ErrMsg.param_error);
        }
        //        if (videoIds.split(",").length != 8) {
//            throw new ResultException("请保证选择8个视频");
//        }
        if (videoIds.split(",").length > Configuration.bucket_max || videoIds.split(",").length < Configuration.bucket_min) {
            throw new ResultException("请保证选择1~8个视频");
        }
        try {
            SbTopicBucket bucket = new SbTopicBucket();
            bucket.setCreateTime(new Date());
            bucket.setOptId(operatorId);
            bucket.setTopicIds(videoIds);
            bucket.setRemarks(StringUtil.trim(remarks));
            bucketMapper.insertSelective(bucket);
        } catch (Exception e) {
            LOGGER.info("save bucket error ===>\r" + e.getMessage(), e);
            throw new ResultException();
        }
    }

    @Override
    public void update(Integer id, String videoIds, String remarks, Integer operatorId) {

        if (StringUtil.isEmpty(videoIds) || null == id || StringUtil.isEmpty(remarks)) {
            throw new ResultException(ErrMsg.param_error);
        }
        //        if (videoIds.split(",").length != 8) {
//            throw new ResultException("请保证选择8个视频");
//        }
        if (videoIds.split(",").length > Configuration.bucket_max || videoIds.split(",").length < Configuration.bucket_min) {
            throw new ResultException("请保证选择1~8个视频");
        }
        try {
            SbTopicBucket bucket = bucketMapper.selectByPrimaryKey(id);
            if (null== bucket) {
                throw new ResultException("该桶不存在");
            }
            bucket.setOptId(operatorId);
            bucket.setTopicIds(videoIds);
            bucket.setRemarks(StringUtil.trim(remarks));
            bucketMapper.updateByPrimaryKeySelective(bucket);
        } catch (Exception e) {
            LOGGER.info("update bucket error ===>\r" + e.getMessage(), e);
            throw new ResultException();
        }
    }

    private List<Integer> insideList() {

        SbTopicBucketExample example = new SbTopicBucketExample();
        List<Integer> insideIdList = Lists.newArrayList();
        List<SbTopicBucket> insideList = bucketMapper.selectByExample(example);
        if (!CollectionUtils.isNullOrEmpty(insideList)) {
            insideList.forEach(bucket -> {
                if (StringUtil.isNotEmpty(bucket.getTopicIds())) {
                    String[] topicIds = StringUtil.trim(bucket.getTopicIds()).split(",");
                    for (String topicId : topicIds) insideIdList.add(Integer.parseInt(topicId));
                }
            });
        }
        return insideIdList;
    }

    private String description(String videoId) {

        StringBuilder builder = new StringBuilder();
        SbTopicBucketExample example = new SbTopicBucketExample();
        SbTopicBucketExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdsLike(StringUtil.sqlLike("," + videoId + ","));
        example.or().andTopicIdsLike(videoId + ",%");
        example.or().andTopicIdsLike("%,"+videoId);
        List<SbTopicBucket> bucketList = bucketMapper.selectByExample(example);
        if (!CollectionUtils.isNullOrEmpty(bucketList)) {
            builder.append("存在于");
            bucketList.forEach(bucket -> builder.append("【").append(bucket.getId()).append("号】"));
            builder.append("桶中");
        } else {
            builder.append("否");
        }
        return builder.toString();
    }

}
