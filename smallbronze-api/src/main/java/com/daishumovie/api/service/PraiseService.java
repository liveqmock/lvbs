package com.daishumovie.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.album.AlbumDto;
import com.daishumovie.base.dto.album.IdTitleImage;
import com.daishumovie.base.dto.category.ChannelDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.dto.topic.VideoDto;
import com.daishumovie.base.dto.user.UserFollowDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.UserPraiseTargetType;
import com.daishumovie.base.enums.db.UserPraiseType;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.BaseUtil;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbChannelMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserCommentMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserPraiseMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.DsmUserExample;
import com.daishumovie.dao.model.SbChannel;
import com.daishumovie.dao.model.SbChannelExample;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicAlbum;
import com.daishumovie.dao.model.SbTopicAlbumExample;
import com.daishumovie.dao.model.SbTopicExample;
import com.daishumovie.dao.model.SbUserComment;
import com.daishumovie.dao.model.SbUserCommentExample;
import com.daishumovie.dao.model.SbUserPraise;
import com.daishumovie.dao.model.SbUserPraiseExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.VersionUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PraiseService {
    @Autowired
    private SbUserPraiseMapper praiseMapper;

    @Autowired
    private SbUserPraiseMapper sbUserPraiseMapper;

    @Autowired
    private SbTopicMapper sbTopicMapper;

    @Autowired
    private SbUserCommentMapper sbUserCommentMapper;

    @Autowired
    private SbChannelMapper sbChannelMapper;

    @Autowired
    private DsmUserMapper dsmUserMapper;

    @Autowired
    private TopicListService topicListService;

    @Value("#{'${audit_status}'.split(',')}")
    private List<Integer> auditStatusList;

    @Autowired
    private SbTopicMapper topicMapper;

    @Autowired
    private SbVideoMapper videoMapper;

    @Autowired
    private SbTopicAlbumMapper albumMapper;

    @Autowired
    private AlbumService albumService;

    private List<Integer> allStatusList = Arrays.asList(0, 1, 3);

    private List<Integer> allAlbumStatusList = Arrays.asList(1, 2);

    private List<Integer> typeList = Arrays.asList(UserPraiseType.PRAISE.getValue(),
            UserPraiseType.CRITICISM.getValue());

    public boolean hasFollow(Integer uid, Integer targetId, Integer targetType) {
        if (uid == null) {
            return false;
        }
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setLimit(1);
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(targetId.longValue());
        criteria.andTargetTypeEqualTo(targetType);
        criteria.andUidEqualTo(uid);
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andStatusEqualTo(Whether.yes.getValue());
        long count = praiseMapper.countByExample(example);
        return count > 0;
    }

    public int getPraiseStatus(Integer uid, Integer targetId, Integer targetType) {
        if (uid == null) {
            return 0;
        }
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setLimit(1);
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(targetId.longValue());
        criteria.andTargetTypeEqualTo(targetType);
        criteria.andUidEqualTo(uid);
        criteria.andTypeIn(typeList);
        criteria.andStatusEqualTo(Whether.yes.getValue());
        List<SbUserPraise> praiseList = praiseMapper.selectByExample(example);
        return praiseList.size() == 0 ? 0
                : praiseList.get(0).getType().equals(UserPraiseType.PRAISE.getValue()) ? 1 : 2;
    }


    @Transactional
    public Response praise(Long targetId, Integer targetType, Integer uid, Integer type, String appId) {

        try {
            boolean praise = isPraise(targetId, targetType, uid, type, appId);
            if (praise) {
                return new Response<>(RespStatusEnum.PRAISE_REPEAT);
            }

            updateData(targetId, targetType, "UP", 0, uid);
            //插入记录
            SbUserPraise sbUserPraise = new SbUserPraise();
            sbUserPraise.setTargetId(targetId);
            sbUserPraise.setTargetType(targetType);
            sbUserPraise.setUid(uid);
            sbUserPraise.setType(type);
            if (StringUtils.isNotBlank(appId)) {
                sbUserPraise.setAppId(Integer.valueOf(appId));
            }

            sbUserPraiseMapper.insertSelective(sbUserPraise);
            return new Response();
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }

    }

    @Transactional
    public Response addBlack(Long targetId, Integer uid, String appId) {
        try {
            boolean praise = isPraise(targetId, UserPraiseTargetType.USER.getValue(), uid, UserPraiseType.FOLLOW.getValue(), appId);
            if (praise) {
                //移除关注记录
                SbUserPraiseExample praiseExample = new SbUserPraiseExample();
                praiseExample.setLimit(1);
                SbUserPraiseExample.Criteria praiseCriteria = praiseExample.createCriteria();
                praiseCriteria.andTargetIdEqualTo(targetId);
                praiseCriteria.andUidEqualTo(uid);
                praiseCriteria.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue());
                praiseCriteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
                praiseCriteria.andAppIdEqualTo(Integer.valueOf(appId));

                SbUserPraise userPraise = new SbUserPraise();
                userPraise.setStatus(YesNoEnum.NO.getCode());
                sbUserPraiseMapper.updateByExampleSelective(userPraise, praiseExample);

                //粉丝、关注数更改
                DsmUserExample userExample = new DsmUserExample();
                userExample.setLimit(1);
                DsmUserExample.Criteria userCriteria = userExample.createCriteria();
                userCriteria.andUidEqualTo(targetId.intValue());
                userCriteria.andAppIdEqualTo(Integer.valueOf(appId));
                userCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
                List<DsmUser> dsmUsers = dsmUserMapper.selectByExample(userExample);
                if (!CollectionUtils.isNullOrEmpty(dsmUsers)) {
                    DsmUser user = dsmUsers.get(0);
                    dsmUserMapper.selfPlusMinusByPrimaryKey("fans_num", "-", 1, user.getUid());
                }

                dsmUserMapper.selfPlusMinusByPrimaryKey("follow_num", "-", 1, uid);

            }

            SbUserPraiseExample praiseExample = new SbUserPraiseExample();
            praiseExample.setLimit(1);
            SbUserPraiseExample.Criteria praiseCriteria = praiseExample.createCriteria();
            praiseCriteria.andTargetIdEqualTo(targetId);
            praiseCriteria.andUidEqualTo(uid);
            praiseCriteria.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue());
            praiseCriteria.andTypeEqualTo(UserPraiseType.BLACKLIST.getValue());
            praiseCriteria.andAppIdEqualTo(Integer.valueOf(appId));
            praiseCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            List<SbUserPraise> sbUserPraiseList = sbUserPraiseMapper.selectByExample(praiseExample);
            if (!CollectionUtils.isNullOrEmpty(sbUserPraiseList)) {
                return new Response();
            }

            //插入黑名单记录
            SbUserPraise sbUserPraise = new SbUserPraise();
            sbUserPraise.setTargetId(targetId);
            sbUserPraise.setTargetType(UserPraiseTargetType.USER.getValue());
            sbUserPraise.setUid(uid);
            sbUserPraise.setType(UserPraiseType.BLACKLIST.getValue());
            if (StringUtils.isNotBlank(appId)) {
                sbUserPraise.setAppId(Integer.valueOf(appId));
            }
            sbUserPraiseMapper.insertSelective(sbUserPraise);
            return new Response();
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }
    }

    @Transactional
    public Response removeBlack(Long targetId, Integer uid, String appId) {
        try {
            SbUserPraiseExample example = new SbUserPraiseExample();
            example.setLimit(1);
            SbUserPraiseExample.Criteria criteria = example.createCriteria();
            criteria.andUidEqualTo(uid);
            criteria.andTargetIdEqualTo(targetId);
            criteria.andAppIdEqualTo(Integer.valueOf(appId));
            criteria.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue());
            criteria.andTypeEqualTo(UserPraiseType.BLACKLIST.getValue());
            criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            List<SbUserPraise> praises = sbUserPraiseMapper.selectByExample(example);
            if (CollectionUtils.isNullOrEmpty(praises)) {
                return new Response();
            }
            SbUserPraise praise = new SbUserPraise();
            praise.setStatus(YesNoEnum.NO.getCode());
            praise.setId(praises.get(0).getId());
            sbUserPraiseMapper.updateByPrimaryKeySelective(praise);
            return new Response();
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }
    }

    public List<UserFollowDto> getBlackListByPage(PageInDto pageInfo, Integer uid, String appId) throws Exception {

        List<UserFollowDto> userFollowDtoList = new ArrayList<>();

        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        example.setOrderByClause("create_time desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue());
        criteria.andUidEqualTo(uid);
        if (null != appId) {
            criteria.andAppIdEqualTo(Integer.valueOf(appId));
        }
        criteria.andTypeEqualTo(UserPraiseType.BLACKLIST.getValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());

        List<SbUserPraise> sbUserPraiseList = sbUserPraiseMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(sbUserPraiseList)) {
            return userFollowDtoList;
        }

        List<Integer> targetIdList = new ArrayList<>();
        for (SbUserPraise sbUserPraise : sbUserPraiseList) {
            targetIdList.add(sbUserPraise.getTargetId().intValue());
        }
        DsmUserExample userExample = new DsmUserExample();
        DsmUserExample.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andUidIn(targetIdList);
        userCriteria.andAppIdEqualTo(Integer.valueOf(appId));
        userCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<DsmUser> dsmUserList = dsmUserMapper.selectByExample(userExample);
        if (CollectionUtils.isNullOrEmpty(dsmUserList)) {
            return userFollowDtoList;
        }
        Map<Integer, DsmUser> userMap = CollectionUtils.convert2Map(dsmUserList, "getUid");
        for (Integer targetId : targetIdList) {
            if (null == userMap.get(targetId)) {
                continue;
            }
            UserFollowDto userFollowDto = new UserFollowDto();
            userFollowDto.setId(targetId);
            userFollowDto.setIntroduce(userMap.get(targetId).getIntroduce());
            if (StringUtils.isBlank(userFollowDto.getIntroduce())) {
                userFollowDto.setIntroduce("这个人很懒，什么也不想写~");
            }
            userFollowDto.setAvatar(userMap.get(targetId).getAvatar());
            userFollowDto.setSex(userMap.get(targetId).getSex());
            userFollowDto.setNickname(userMap.get(targetId).getNickName());
            userFollowDto.setUserType(userMap.get(targetId).getType());
            userFollowDtoList.add(userFollowDto);
        }
        return userFollowDtoList;

    }

    @Transactional
    public Response criticize(Long targetId, Integer targetType, Integer uid, Integer type, String appId) {
        try {
            boolean praise = isPraise(targetId, targetType, uid, type, appId);
            if (praise) {
                return new Response(RespStatusEnum.CRITICIZE_REPEAT);
            }
            updateData(targetId, targetType, "DOWN", 0, uid);

            SbUserPraise sbUserPraise = new SbUserPraise();
            sbUserPraise.setTargetId(targetId);
            sbUserPraise.setTargetType(targetType);
            sbUserPraise.setUid(uid);
            sbUserPraise.setType(type);
            if (StringUtils.isNotBlank(appId)) {
                sbUserPraise.setAppId(Integer.valueOf(appId));
            }

            sbUserPraiseMapper.insertSelective(sbUserPraise);

           /* SbUserPraiseExample example = new SbUserPraiseExample();
            example.setLimit(1);
            SbUserPraiseExample.Criteria criteria = example.createCriteria();
            criteria.andTargetIdEqualTo(targetId);
            criteria.andTargetTypeEqualTo(targetType);
            criteria.andUidEqualTo(uid);
            SbUserPraise sbUserPraise = new SbUserPraise();
            sbUserPraise.setStatus(YesNoEnum.NO.getCode());
            sbUserPraise.setType(SbUserPraiseTypeEnum.CRITICIZE.getCode());
            sbUserPraiseMapper.updateByExampleSelective(sbUserPraise, example);*/

            return new Response();
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }
    }

    @Transactional
    public Response cancel(Long targetId, Integer targetType, Integer uid, Integer type, String appId) {
        try {
            boolean praise = isPraise(targetId, targetType, uid, type, appId);
            if (!praise) {
                return new Response(RespStatusEnum.CANCEL_REPEAT);
            }

            updateData(targetId, targetType, "CANCEL", type, uid);

            SbUserPraise sbUserPraise = new SbUserPraise();
            sbUserPraise.setStatus(YesNoEnum.NO.getCode());

            SbUserPraiseExample example = new SbUserPraiseExample();
            example.setLimit(1);
            SbUserPraiseExample.Criteria criteria = example.createCriteria();
            criteria.andTargetIdEqualTo(targetId);
            criteria.andTargetTypeEqualTo(targetType);
            criteria.andTypeEqualTo(type);
            criteria.andUidEqualTo(uid);
            if (StringUtils.isNotBlank(appId)) {
                criteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            sbUserPraiseMapper.updateByExampleSelective(sbUserPraise, example);
            return new Response();
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }
    }

    @Transactional
    public Response follow(Long targetId, Integer targetType, Integer uid, Integer type, String appId) {
        try {

            boolean praise = isPraise(targetId, targetType, uid, type, appId);
            //判断目标是否存在
            if (!targetIsExist(targetId, targetType, appId, YesNoEnum.YES.getCode()) && !praise) {
                return new Response(RespStatusEnum.TARGET_NOT_EXIST);
            }

            if (UserPraiseTargetType.USER.getValue().equals(targetType)) {
                SbUserPraiseExample praiseExample = new SbUserPraiseExample();
                praiseExample.setLimit(1);
                SbUserPraiseExample.Criteria praiseCriteria = praiseExample.createCriteria();
                praiseCriteria.andUidEqualTo(uid);
                praiseCriteria.andTargetIdEqualTo(targetId);
                praiseCriteria.andAppIdEqualTo(Integer.valueOf(appId));
                praiseCriteria.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue());
                praiseCriteria.andTypeEqualTo(UserPraiseType.BLACKLIST.getValue());
                praiseCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
                List<SbUserPraise> userPraises = sbUserPraiseMapper.selectByExample(praiseExample);
                if (!CollectionUtils.isNullOrEmpty(userPraises)) {
                    return new Response(RespStatusEnum.NOT_BLACK_FOLLOW);
                }

                SbUserPraiseExample example = new SbUserPraiseExample();
                example.setLimit(1);
                SbUserPraiseExample.Criteria criteria = example.createCriteria();
                criteria.andUidEqualTo(targetId.intValue());
                criteria.andTargetIdEqualTo(uid.longValue());
                criteria.andAppIdEqualTo(Integer.valueOf(appId));
                criteria.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue());
                criteria.andTypeEqualTo(UserPraiseType.BLACKLIST.getValue());
                criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
                List<SbUserPraise> praises = sbUserPraiseMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(praises) && !praise) {
                    return new Response(RespStatusEnum.BLACK_NOT_FOLLOW);
                }
            }

            if (praise) {
                return cancelFollow(targetId, targetType, uid, type, appId);
            }

            updateData(targetId, targetType, "FOLLOW", type, uid);

            SbUserPraise sbUserPraise = new SbUserPraise();
            sbUserPraise.setTargetId(targetId);
            sbUserPraise.setTargetType(targetType);
            sbUserPraise.setUid(uid);
            sbUserPraise.setType(type);
            if (StringUtils.isNotBlank(appId)) {
                sbUserPraise.setAppId(Integer.valueOf(appId));
            }

            sbUserPraiseMapper.insertSelective(sbUserPraise);
            return new Response();
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }
    }

    @Transactional
    public Response cancelFollow(Long targetId, Integer targetType, Integer uid, Integer type, String appId) {
        try {
           /* boolean praise = isPraise(targetId, targetType, uid, type);
            if (!praise) {
                return new Response(RespStatusEnum.CANCEL_REPEAT);
            }*/
            //判断目标是否存在
            if (!targetIsExist(targetId, targetType, appId, null)) {
                return new Response(RespStatusEnum.TARGET_NOT_EXIST);
            }
            updateData(targetId, targetType, "CANCEL_FOLLOW", type, uid);

            SbUserPraise sbUserPraise = new SbUserPraise();
            sbUserPraise.setStatus(YesNoEnum.NO.getCode());

            SbUserPraiseExample example = new SbUserPraiseExample();
            example.setLimit(1);
            SbUserPraiseExample.Criteria criteria = example.createCriteria();
            criteria.andTargetIdEqualTo(targetId);
            criteria.andTargetTypeEqualTo(targetType);
            criteria.andTypeEqualTo(type);
            criteria.andUidEqualTo(uid);
            if (StringUtils.isNotBlank(appId)) {
                sbUserPraise.setAppId(Integer.valueOf(appId));
            }
            sbUserPraiseMapper.updateByExampleSelective(sbUserPraise, example);
            return new Response();
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }
    }


    public List<UserFollowDto> followUserList(Long targetId, Integer targetType, PageInDto pageInfo, Integer appId, PageSource page) throws Exception {
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        example.setOrderByClause("create_time desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andTargetTypeEqualTo(targetType);
        criteria.andTargetIdEqualTo(targetId);
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbUserPraise> sbUserPraises = sbUserPraiseMapper.selectByExample(example);

        List<UserFollowDto> userFollowDtos = new ArrayList<>();


        if (CollectionUtils.isNullOrEmpty(sbUserPraises)) {
            return userFollowDtos;
        }

        List<Integer> uidList = new ArrayList<>();
        for (SbUserPraise sbUserPraise : sbUserPraises) {
            uidList.add(sbUserPraise.getUid());
        }
        DsmUserExample userExample = new DsmUserExample();
        DsmUserExample.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andUidIn(uidList);
        userCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<DsmUser> dsmUserList = dsmUserMapper.selectByExample(userExample);
        if (CollectionUtils.isNullOrEmpty(dsmUserList)) {
            return userFollowDtos;
        }

        Map<Integer, DsmUser> userMap = com.daishumovie.utils.CollectionUtils.convert2Map(dsmUserList, "getUid");
        for (Integer userId : uidList) {
            if (null == userMap.get(userId)) {
                continue;
            }
            UserFollowDto userFollowDto = new UserFollowDto();
            userFollowDto.setId(userId);
            userFollowDto.setNickname(userMap.get(userId).getNickName());
            if (PageSource.TOPIC_DETAIL != page) {
                userFollowDto.setAvatar(userMap.get(userId).getAvatar());
                userFollowDto.setNickname(userMap.get(userId).getNickName());
                userFollowDto.setSex(userMap.get(userId).getSex());
                userFollowDto.setIntroduce(userMap.get(userId).getIntroduce());
                userFollowDto.setUserType(userMap.get(userId).getType());
                if (StringUtils.isBlank(userFollowDto.getIntroduce())) {
                    userFollowDto.setIntroduce("这个人很懒，什么也不想写~");
                }
            }
            userFollowDtos.add(userFollowDto);
        }

        return userFollowDtos;
    }

    public List<TopicDto> getFollowUserTopic(PageInDto pageInDto, Integer uid, Integer appId) {

        List<TopicDto> dtoList = new ArrayList<>();
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setOrderByClause("create_time desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue());
        criteria.andUidEqualTo(uid);
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbUserPraise> sbUserPraiseList = sbUserPraiseMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(sbUserPraiseList)) {
            return dtoList;
        }
        List<Integer> targetIdList = new ArrayList<>();
        for (SbUserPraise sbUserPraise : sbUserPraiseList) {
            targetIdList.add(sbUserPraise.getTargetId().intValue());
        }
        SbTopicExample topicExample = new SbTopicExample();
        topicExample.setOffset(pageInDto.getOffset());
        topicExample.setLimit(pageInDto.getLimit());
        topicExample.setOrderByClause("create_time desc");
        SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
        if (appId != null) {
            topicCriteria.andAppIdEqualTo(appId);
        }
        // 符合配置的审核过的话题才展示出来；
        topicCriteria.andAuditStatusIn(auditStatusList);
        topicCriteria.andUidIn(targetIdList);
        topicCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbTopic> sbTopicList = sbTopicMapper.selectByExample(topicExample);
        if (CollectionUtils.isNullOrEmpty(sbTopicList)) {
            return dtoList;
        }
        for (SbTopic sbTopic : sbTopicList) {
            TopicDto dto = topicListService.wrapTopicDto(sbTopic, uid, null, PageSource.OTHER_TOPIC_LIST);
            if (sbTopic.getVideoId() != null) {
                SbVideo video = videoMapper.selectByPrimaryKey(sbTopic.getVideoId());
                VideoDto videoDto = new VideoDto();
                videoDto.setId(video.getId());
                //暂不提供视频地址，重新接口获取
                // videoDto.setUrl(video.getFormatUrl() != null ? video.getFormatUrl() : video.getOriUrl());
                videoDto.setCover(video.getCover());
                videoDto.setPlayNum(BaseUtil.instance.formatNum(video.getPlayNum() + video.getvPlayNum(), false));
                videoDto.setBarrageNum(BaseUtil.instance.formatNum(video.getBarrageNum(), false));
                videoDto.setDuration(video.getDuration());
                videoDto.setDimension(video.getDimension());
                videoDto.setSize(video.getSize());
                dto.setVideo(videoDto);
            }
            dtoList.add(dto);
        }

        return dtoList;
    }

    public void updateData(Long targetId, Integer targetType, String flag, Integer type, Integer uid) {
        if (UserPraiseTargetType.TOPIC.getValue().equals(targetType)) {

            if ("UP".equals(flag)) {
                sbTopicMapper.selfPlusMinusByPrimaryKey("praise_num ", "+1,diff_value = diff_value +", 1, targetId.intValue());
            } else if ("DOWN".equals(flag)) {
                sbTopicMapper.selfPlusMinusByPrimaryKey("criticism_num ", "+1,diff_value = diff_value -", 1, targetId.intValue());
            } else if ("CANCEL".equals(flag)) {
                if (UserPraiseType.PRAISE.getValue().equals(type)) {
                    sbTopicMapper.selfPlusMinusByPrimaryKey("praise_num ", "-1,diff_value = diff_value -", 1, targetId.intValue());
                } else if (UserPraiseType.CRITICISM.getValue().equals(type)) {
                    sbTopicMapper.selfPlusMinusByPrimaryKey("criticism_num ", "-1,diff_value = diff_value +", 1, targetId.intValue());
                }
            } else if ("FOLLOW".equals(flag)) {
                sbTopicMapper.selfPlusMinusByPrimaryKey("follow_num", "+", 1, targetId.intValue());
                dsmUserMapper.selfPlusMinusByPrimaryKey("like_num", "+", 1, uid);
            } else if ("CANCEL_FOLLOW".equals(flag)) {
                sbTopicMapper.selfPlusMinusByPrimaryKey("follow_num", "-", 1, targetId.intValue());
                dsmUserMapper.selfPlusMinusByPrimaryKey("like_num", "-", 1, uid);
            }

        } else if (UserPraiseTargetType.COMMENT.getValue().equals(targetType)) {

            if ("UP".equals(flag)) {
                sbUserCommentMapper.selfPlusMinusByPrimaryKey("praise_num ", "+1,diff_value = diff_value +", 1, targetId.intValue());
            } else if ("DOWN".equals(flag)) {
                sbUserCommentMapper.selfPlusMinusByPrimaryKey("criticism_num ", "+1,diff_value = diff_value -", 1, targetId.intValue());
            } else if ("CANCEL".equals(flag)) {
                if (UserPraiseType.PRAISE.getValue().equals(type)) {
                    sbTopicMapper.selfPlusMinusByPrimaryKey("praise_num ", "-1,diff_value = diff_value -", 1, targetId.intValue());
                } else if (UserPraiseType.CRITICISM.getValue().equals(type)) {
                    sbTopicMapper.selfPlusMinusByPrimaryKey("criticism_num ", "-1,diff_value = diff_value +", 1, targetId.intValue());
                }
            }
        } else if (UserPraiseTargetType.CHANNEL.getValue().equals(targetType)) {
            if ("FOLLOW".equals(flag)) {
                sbChannelMapper.selfPlusMinusByPrimaryKey("follow_num", "+", 1, targetId.intValue());
            } else if ("CANCEL_FOLLOW".equals(flag)) {
                sbChannelMapper.selfPlusMinusByPrimaryKey("follow_num", "-", 1, targetId.intValue());
            }

        } else if (UserPraiseTargetType.USER.getValue().equals(targetType)) {
            if ("FOLLOW".equals(flag)) {
                dsmUserMapper.selfPlusMinusByPrimaryKey("follow_num", "+", 1, uid);
                dsmUserMapper.selfPlusMinusByPrimaryKey("fans_num", "+", 1, targetId.intValue());
            } else if ("CANCEL_FOLLOW".equals(flag)) {
                dsmUserMapper.selfPlusMinusByPrimaryKey("follow_num", "-", 1, uid);
                dsmUserMapper.selfPlusMinusByPrimaryKey("fans_num", "-", 1, targetId.intValue());
            }
        } else if (UserPraiseTargetType.ALBUM.getValue().equals(targetType)) {
            if ("FOLLOW".equals(flag)) {
                dsmUserMapper.selfPlusMinusByPrimaryKey("like_album_num", "+", 1, uid);
            } else if ("CANCEL_FOLLOW".equals(flag)) {
                dsmUserMapper.selfPlusMinusByPrimaryKey("like_album_num", "-", 1, uid);
            }

        }
    }

    public boolean isExist(Long targetId, UserPraiseTargetType targetType, String appId, Integer uid, UserPraiseType type) {

        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setLimit(1);
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(targetId);
        criteria.andTargetTypeEqualTo(targetType.getValue());
        criteria.andAppIdEqualTo(Integer.valueOf(appId));
        criteria.andUidEqualTo(uid);
        criteria.andTypeEqualTo(type.getValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());

        long num = praiseMapper.countByExample(example);
        return num > 0;

    }

    public boolean targetIsExist(Long targetId, Integer targetType, String appId, Integer status) {
        if (UserPraiseTargetType.TOPIC.getValue().equals(targetType)) {
            SbTopicExample topicExample = new SbTopicExample();
            topicExample.setLimit(1);
            SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
            topicCriteria.andIdEqualTo(targetId.intValue());
            if (null != status) {
                topicCriteria.andStatusEqualTo(status);
            }
            topicCriteria.andAuditStatusIn(auditStatusList);
            if (StringUtils.isNotBlank(appId)) {
                topicCriteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            List<SbTopic> sbTopics = sbTopicMapper.selectByExample(topicExample);
            if (CollectionUtils.isNullOrEmpty(sbTopics)) {
                return false;
            }
        } else if (UserPraiseTargetType.CHANNEL.getValue().equals(targetType)) {
            SbChannelExample channelExample = new SbChannelExample();
            channelExample.setLimit(1);
            SbChannelExample.Criteria channelCriteria = channelExample.createCriteria();
            channelCriteria.andIdEqualTo(targetId.intValue());
            if (null != status) {

                channelCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            }
            if (StringUtils.isNotBlank(appId)) {
                channelCriteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            List<SbChannel> sbChannels = sbChannelMapper.selectByExample(channelExample);
            if (CollectionUtils.isNullOrEmpty(sbChannels)) {
                return false;
            }
        } else if (UserPraiseTargetType.USER.getValue().equals(targetType)) {
            DsmUserExample userExample = new DsmUserExample();
            userExample.setLimit(1);
            DsmUserExample.Criteria userCriteria = userExample.createCriteria();
            userCriteria.andUidEqualTo(targetId.intValue());
            if (null != status) {

                userCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            }
            if (StringUtils.isNotBlank(appId)) {
                userCriteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            List<DsmUser> dsmUsers = dsmUserMapper.selectByExample(userExample);
            if (CollectionUtils.isNullOrEmpty(dsmUsers)) {
                return false;
            }
        } else if (UserPraiseTargetType.COMMENT.getValue().equals(targetType)) {
            SbUserCommentExample commentExample = new SbUserCommentExample();
            commentExample.setLimit(1);
            SbUserCommentExample.Criteria commentCriteria = commentExample.createCriteria();
            commentCriteria.andIdEqualTo(targetId.intValue());
            if (StringUtils.isNotBlank(appId)) {
                commentCriteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            if (null != status) {

                commentCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            }
            commentCriteria.andAuditStatusIn(auditStatusList);
            List<SbUserComment> sbUserComments = sbUserCommentMapper.selectByExample(commentExample);
            if (CollectionUtils.isNullOrEmpty(sbUserComments)) {
                return false;
            }
        } else if (UserPraiseTargetType.ALBUM.getValue().equals(targetType)) {
            SbTopicAlbumExample albumExample = new SbTopicAlbumExample();
            albumExample.setLimit(1);
            SbTopicAlbumExample.Criteria albumCriteria = albumExample.createCriteria();
            albumCriteria.andIdEqualTo(targetId.intValue());
            if (StringUtils.isNotBlank(appId)) {
                albumCriteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            if (null != status) {

                albumCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            }
            List<SbTopicAlbum> sbTopicAlbum = albumMapper.selectByExample(albumExample);
            if (CollectionUtils.isNullOrEmpty(sbTopicAlbum)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判定是否顶踩
     *
     * @param targetId
     * @param targetType
     * @param uid
     * @param type
     * @return
     */
    public boolean isPraise(Long targetId, Integer targetType, Integer uid, Integer type, String appId) {

        if (targetId == null) {
            return false;
        }
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setLimit(1);
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(targetId);
        criteria.andTargetTypeEqualTo(targetType);
        criteria.andUidEqualTo(uid);
        criteria.andTypeEqualTo(type);
        if (StringUtils.isNotBlank(appId)) {
            criteria.andAppIdEqualTo(Integer.valueOf(appId));
        }
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        long result = sbUserPraiseMapper.countByExample(example);
        return result > 0;
    }


    public List<UserFollowDto> getFollowUserByPage(PageInDto pageInfo, Integer appId, Integer uid) throws Exception {
        List<UserFollowDto> userFollowDtoList = new ArrayList<>();
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        example.setOrderByClause("create_time desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.USER.getValue());

        criteria.andUidEqualTo(uid);
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbUserPraise> sbUserPraiseList = sbUserPraiseMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(sbUserPraiseList)) {
            return userFollowDtoList;
        }
        List<Integer> targetIdList = new ArrayList<>();
        for (SbUserPraise sbUserPraise : sbUserPraiseList) {
            targetIdList.add(sbUserPraise.getTargetId().intValue());
        }
        DsmUserExample userExample = new DsmUserExample();
        DsmUserExample.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andUidIn(targetIdList);
        userCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<DsmUser> dsmUserList = dsmUserMapper.selectByExample(userExample);
        if (CollectionUtils.isNullOrEmpty(dsmUserList)) {
            return userFollowDtoList;
        }
        Map<Integer, DsmUser> userMap = CollectionUtils.convert2Map(dsmUserList, "getUid");
        for (Integer targetId : targetIdList) {
            if (null == userMap.get(targetId)) {
                continue;
            }
            UserFollowDto userFollowDto = new UserFollowDto();
            userFollowDto.setId(targetId);
            userFollowDto.setIntroduce(userMap.get(targetId).getIntroduce());
            if (StringUtils.isBlank(userFollowDto.getIntroduce())) {
                userFollowDto.setIntroduce("这个人很懒，什么也不想写~");
            }
            userFollowDto.setAvatar(userMap.get(targetId).getAvatar());
            userFollowDto.setSex(userMap.get(targetId).getSex());
            userFollowDto.setNickname(userMap.get(targetId).getNickName());
            userFollowDto.setUserType(userMap.get(targetId).getType());
            userFollowDtoList.add(userFollowDto);
        }
        return userFollowDtoList;
    }

    /**
     * 关注话题列表
     *
     * @param pageInfo
     * @param appId
     * @return
     */
    public List<TopicDto> getFollowTopicByPage(PageInDto pageInfo, Integer appId) {
        List<TopicDto> dtoList = new ArrayList<>();
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        example.setOrderByClause("un_read_num desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.TOPIC.getValue());
        Header header = LocalData.HEADER.get();
        Integer localUid = header.getUid();
        criteria.andUidEqualTo(localUid);
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbUserPraise> userFollowTopicList = sbUserPraiseMapper.selectByExample(example);
        List<Integer> topicIds = new ArrayList<>();
        for (SbUserPraise followTopic : userFollowTopicList) {
            topicIds.add(followTopic.getTargetId().intValue());
        }

        SbTopicExample topicExample = new SbTopicExample();
        SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
        if (appId != null) {
            topicCriteria.andAppIdEqualTo(appId);
        }
        // 正常在线的话题；
        topicCriteria.andStatusEqualTo(Whether.yes.getValue());
        // 符合配置的审核过的话题才展示出来；
        topicCriteria.andAuditStatusIn(auditStatusList);
        List<SbTopic> topicList = topicMapper.selectByExample(topicExample);
        try {
            Map<Integer, SbTopic> videoMap = CollectionUtils.convert2Map(topicList, "getId");
            Map<Long, SbUserPraise> followMap = CollectionUtils.convert2MapGeneric(userFollowTopicList, "getTargetId");
            for (Integer topicId : topicIds) {
                SbTopic topic = videoMap.get(topicId);
                if (topic != null) {
                    TopicDto dto = topicListService.wrapTopicDto(topic, localUid, null, PageSource.FOLLOW_TOPIC_LIST);
                    dto.setUnReadNum(followMap.get(topicId.longValue()).getUnReadNum().toString());
                    if (topic.getVideoId() != null) {
                        SbVideo video = videoMapper.selectByPrimaryKey(topic.getVideoId());
                        VideoDto videoDto = new VideoDto();
                        videoDto.setId(video.getId());
                        videoDto.setUrl(video.getFormatUrl() != null ? video.getFormatUrl() : video.getOriUrl());
                        videoDto.setCover(video.getCover());
                        dto.setVideo(videoDto);
                    }
                    dtoList.add(dto);
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return dtoList;
    }

    /**
     * 关注频道列表
     *
     * @param pageInfo
     * @param appId
     * @return
     */
    public List<ChannelDto> getFollowChannelByPage(PageInDto pageInfo, Integer appId) {
        List<ChannelDto> dtoList = new ArrayList<>();
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        example.setOrderByClause("un_read_num desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.CHANNEL.getValue());
        Header header = LocalData.HEADER.get();
        Integer localUid = header.getUid();
        criteria.andUidEqualTo(localUid);
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbUserPraise> userFollowChannelList = sbUserPraiseMapper.selectByExample(example);
        if (userFollowChannelList.size() == 0) {
            return dtoList;
        }
        List<Integer> channelIds = new ArrayList<>();
        for (SbUserPraise followChannel : userFollowChannelList) {
            channelIds.add(followChannel.getTargetId().intValue());
        }
        SbChannelExample channelExample = new SbChannelExample();
        SbChannelExample.Criteria channelCriteria = channelExample.createCriteria();
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        channelCriteria.andIdIn(channelIds);
        channelCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        if (appId != null) {
            channelCriteria.andAppIdEqualTo(appId);
        }
        List<SbChannel> sbChannels = sbChannelMapper.selectByExample(channelExample);
        try {
            Map<Integer, SbChannel> channelMap = CollectionUtils.convert2Map(sbChannels, "getId");
            Map<Long, SbUserPraise> followMap = CollectionUtils.convert2MapGeneric(userFollowChannelList,
                    "getTargetId");
            for (Integer channelId : channelIds) {
                ChannelDto dto = new ChannelDto();
                SbChannel channel = channelMap.get(channelId);
                SbUserPraise followInfo = followMap.get(channelId.longValue());
                dto.setId(channel.getId());
                dto.setName(channel.getName());
                dto.setUrl(channel.getUrl());
                dto.setUnReadNum(followInfo.getUnReadNum().toString());
                dtoList.add(dto);
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return dtoList;
    }

    /**
     * 个人/他人关注话题列表
     *
     * @param pageInfo
     * @param appId
     * @return
     */
    public Response<BaseListDto<TopicDto>> getFollowTopicByPageSimple(PageInDto pageInfo, Integer appId, Integer userId) {
        Response<BaseListDto<TopicDto>> resp = new Response<>();
        List<TopicDto> dtoList = new ArrayList<>();
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        example.setOrderByClause("create_time desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.TOPIC.getValue());
        if (userId != null) {
            criteria.andUidEqualTo(userId);
        } else {
            Header header = LocalData.HEADER.get();
            Integer localUid = header.getUid();
            criteria.andUidEqualTo(localUid);
        }
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andStatusEqualTo(Whether.yes.getValue());
        List<SbUserPraise> userFollowTopicList = sbUserPraiseMapper.selectByExample(example);
        if (userFollowTopicList.size() == 0) {
            resp.setResult(new BaseListDto<TopicDto>(new ArrayList<>(), pageInfo));
            return resp;
        }
        List<Integer> topicIds = new ArrayList<>();
        for (SbUserPraise followTopic : userFollowTopicList) {
            topicIds.add(followTopic.getTargetId().intValue());
        }

        SbTopicExample topicExample = new SbTopicExample();
        SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
        if (appId != null) {
            topicCriteria.andAppIdEqualTo(appId);
        }
        topicCriteria.andIdIn(topicIds);
        // 正常在线、被下线的、被删除的话题；
        String version = LocalData.HEADER.get().getVersion();
        try {
            if (version != null && VersionUtil.compareVersion(version, "1.1.0") >= 0) {
                topicCriteria.andStatusIn(allStatusList);
            } else {
                topicCriteria.andStatusEqualTo(Whether.yes.getValue());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // 符合配置的审核过的话题才展示出来；
        topicCriteria.andAuditStatusIn(auditStatusList);
        List<SbTopic> topicList = topicMapper.selectByExample(topicExample);
        try {
            Map<Integer, SbTopic> videoMap = CollectionUtils.convert2Map(topicList, "getId");
            for (Integer topicId : topicIds) {
                SbTopic topic = videoMap.get(topicId);
                if (topic != null) {
                    TopicDto dto = topicListService.sbTopic2Dto(topic, PageSource.FOLLOW_TOPIC_LIST);
                    if (dto == null) {
                        continue;
                    }
                    dtoList.add(dto);
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        BaseListDto<TopicDto> dto = new BaseListDto<>(dtoList, pageInfo);
        if (topicIds.size() >= pageInfo.getPageSize()) {
            dto.setHasNext(Whether.yes.getValue());
        }
        resp.setResult(dto);
        return resp;
    }

    public Response<BaseListDto<AlbumDto<IdTitleImage>>> getFollowAlbumByPageSimple(PageInDto pageInfo, Integer appId,
                                                                                    Integer userId) {
        Response<BaseListDto<AlbumDto<IdTitleImage>>> resp = new Response<>();
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        example.setOrderByClause("create_time desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.ALBUM.getValue());
        if (userId != null) {
            criteria.andUidEqualTo(userId);
        } else {
            Header header = LocalData.HEADER.get();
            Integer localUid = header.getUid();
            criteria.andUidEqualTo(localUid);
        }
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbUserPraise> userFollowAlbumist = sbUserPraiseMapper.selectByExample(example);
        if (userFollowAlbumist.size() == 0) {
            resp.setResult(new BaseListDto<AlbumDto<IdTitleImage>>(new ArrayList<>(), pageInfo));
            return resp;
        }
        List<Integer> albumIds = new ArrayList<>();
        for (SbUserPraise followAlbum : userFollowAlbumist) {
            albumIds.add(followAlbum.getTargetId().intValue());
        }

        SbTopicAlbumExample albumExample = new SbTopicAlbumExample();
        SbTopicAlbumExample.Criteria AlbumCriteria = albumExample.createCriteria();
        if (appId != null) {
            AlbumCriteria.andAppIdEqualTo(appId);
        }
        AlbumCriteria.andIdIn(albumIds);
        AlbumCriteria.andStatusIn(allAlbumStatusList);
        List<SbTopicAlbum> albumList = albumMapper.selectByExample(albumExample);
        // 处理收藏的排序
        try {
            Map<Integer, SbTopicAlbum> albumMap = CollectionUtils.convert2MapGeneric(albumList, "getId");
            List<SbTopicAlbum> albumListNew = new ArrayList<>();
            for (Integer albumId : albumIds) {
                SbTopicAlbum album = albumMap.get(albumId);
                if (album != null) {
                    albumListNew.add(album);
                }
            }
            albumList = albumListNew;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return albumService.getAlbumList(appId, pageInfo, albumList);
    }
}