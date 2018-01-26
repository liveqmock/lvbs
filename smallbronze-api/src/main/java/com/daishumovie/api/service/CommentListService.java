package com.daishumovie.api.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.comment.CommentDto;
import com.daishumovie.base.dto.comment.ImageDto;
import com.daishumovie.base.dto.topic.VideoDto;
import com.daishumovie.base.dto.user.UserBriefDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.UserPraiseTargetType;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserCommentMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbUserComment;
import com.daishumovie.dao.model.SbUserCommentExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentListService {

    @Autowired
    private SbUserCommentMapper commentMapper;

    @Autowired
    private DsmUserMapper userMapper;

    @Autowired
    private SbVideoMapper videoMapper;

    @Autowired
    private PraiseService praiseService;

    @Value("#{'${comment_audit_status}'.split(',')}")
    private List<Integer> auditStatusList;

    private static DecimalFormat df = new DecimalFormat("0.0万");

    public static final SimpleDateFormat commentFormat = new SimpleDateFormat("MM-dd HH:mm");

    public List<CommentDto> getCommentByPage(PageInDto pageInfo, Integer appId, Integer targetId, Integer targetType, Integer pCommentId,
                                             Integer userId, Integer type, PageSource page) {
        Header header = LocalData.HEADER.get();
        Integer localUid = header.getUid();
        SbUserCommentExample example = new SbUserCommentExample();
        example.setOffset(pageInfo.getOffset());
        example.setLimit(pageInfo.getLimit());
        if (type == 1) {
            example.setOrderByClause("create_time desc");
        } else {
            example.setOrderByClause("diff_value desc");
        }
        SbUserCommentExample.Criteria criteria = example.createCriteria();
        if (appId != null) {
            criteria.andAppIdEqualTo(appId);
        }
        if (targetId != null) {
            criteria.andTargetIdEqualTo(targetId);
            criteria.andTargetTypeEqualTo(targetType);
            criteria.andPidEqualTo(0);
        }
        if (pCommentId != null) {
            criteria.andPidEqualTo(pCommentId);
        }
        // 个人空间内查看回复列表
        if (PageSource.MY_COMMENT_LIST == page) {
            criteria.andUidEqualTo(localUid);
            // 他人空间内查看回复列表；
        } else if (PageSource.OTHER_COMMENT_LIST == page && userId != null) {
            criteria.andUidEqualTo(userId);
        }
        // 正常在线的话题；
        criteria.andStatusEqualTo(Whether.yes.getValue());
        criteria.andAuditStatusIn(auditStatusList);
        List<SbUserComment> commentList = commentMapper.selectByExample(example);
        List<CommentDto> list = new ArrayList<>();
        if (commentList.isEmpty()) {
            return list;
        }

        for (SbUserComment comment : commentList) {
            try {
                CommentDto dto = wrapCommentDto(comment, localUid, page);
                list.add(dto);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }
        return list;
    }


    private CommentDto wrapCommentDto(SbUserComment comment, Integer localUid, PageSource page) {
        CommentDto dto = new CommentDto();
        try {
            BeanUtils.copyProperties(comment, dto);
            dto.setDiffValue(formatNum(comment.getDiffValue()));
            dto.setReplyNum(formatNum(comment.getReplyNum()));

            UserBriefDto user = new UserBriefDto();
            DsmUser owner = userMapper.selectByPrimaryKey(comment.getUid());
            user.setId(owner.getUid());
            user.setNickname(owner.getNickName());
            user.setHeadImg(owner.getAvatar());
            user.setUserType(owner.getType());
            dto.setUser(user);

            dto.setTime(commentFormat.format(comment.getCreateTime()));

            dto.setPraiseStatus(
                    praiseService.getPraiseStatus(localUid, comment.getId(), UserPraiseTargetType.COMMENT.getValue()));

            List<VideoDto> medias = new ArrayList<VideoDto>();
            VideoDto videoDto = new VideoDto();
            // 回复视频
            if (comment.getVideoId() != null) {
                SbVideo video = videoMapper.selectByPrimaryKey(comment.getVideoId());
                videoDto.setId(video.getId());
                videoDto.setUrl(video.getFormatUrl() != null ? video.getFormatUrl() : video.getOriUrl());
                videoDto.setCover(video.getCover());
                videoDto.setRefId(video.getRefId());
                videoDto.setType(1);
                medias.add(videoDto);
            }
            // 一级回复列表时去下级评论的前两条数据；
            if (PageSource.REPLY_LIST == page) {
                wrapReplies(dto, comment);
            }
            List<String> imageList = handleImages(comment);
            for (String imageUrl : imageList) {
                VideoDto image = new VideoDto();
                image.setUrl(imageUrl);
                image.setType(2);
                medias.add(image);
            }
            dto.setMedias(medias);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return dto;
    }

    public List<CommentDto> getCommentList(Integer targetId, Integer targetType, Integer appId, PageInDto pageInDto) {

        SbUserCommentExample example = new SbUserCommentExample();
        example.setOffset(pageInDto.getOffset());
        example.setLimit(pageInDto.getLimit());
        example.setOrderByClause("create_time desc");
        SbUserCommentExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(targetId);
        criteria.andTargetTypeEqualTo(targetType);
        criteria.andPidEqualTo(0);
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        criteria.andAuditStatusIn(auditStatusList);
        List<SbUserComment> commentList = commentMapper.selectByExample(example);

        List<CommentDto> commentDtos = new ArrayList<>();
        if (CollectionUtils.isNullOrEmpty(commentList)) {
            return commentDtos;
        }
        DsmUser accessUser = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        for (SbUserComment comment : commentList) {
            try {
                CommentDto dto = wrapCommentDtoSimple(comment, accessUser);
                commentDtos.add(dto);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }
        return commentDtos;

    }

    private CommentDto wrapCommentDtoSimple(SbUserComment comment, DsmUser accessUser) {
        CommentDto dto = new CommentDto();
        try {
            BeanUtils.copyProperties(comment, dto);

            UserBriefDto user = new UserBriefDto();
            DsmUser owner = userMapper.selectByPrimaryKey(comment.getUid());
            user.setId(owner.getUid());
            user.setNickname(owner.getNickName());
            user.setHeadImg(owner.getAvatar());
            user.setUserType(owner.getType());
			dto.setUser(user);
			if (accessUser == null) {
				dto.setDeletable(Whether.no.getValue());
			} else {
				dto.setDeletable(
						owner.getUid().equals(accessUser.getUid()) ? Whether.yes.getValue() : Whether.no.getValue());
			}

            dto.setTime(DateUtil.getDistanceCustom(new Date(), comment.getCreateTime(), 365, "yyyy-MM-dd", "MM-dd HH:mm"));

            if (null != comment.getPuid()) {
                UserBriefDto targetUser = new UserBriefDto();
                DsmUser dsmUser = userMapper.selectByPrimaryKey(comment.getPuid());
                targetUser.setId(dsmUser.getUid());
                targetUser.setNickname(dsmUser.getNickName());
                targetUser.setUserType(dsmUser.getType());
                dto.setTargetUser(targetUser);
            }
            if (null!=comment.getPcid()) {
                SbUserCommentExample commentExample = new SbUserCommentExample();
                commentExample.setLimit(1);
                SbUserCommentExample.Criteria commentCriteria = commentExample.createCriteria();
                commentCriteria.andIdEqualTo(comment.getPcid());
                commentCriteria.andPidEqualTo(0);
                commentCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
                commentCriteria.andAuditStatusIn(auditStatusList);
                if (!CollectionUtils.isNullOrEmpty(commentMapper.selectByExample(commentExample))) {
                    SbUserComment userComment = commentMapper.selectByExample(commentExample).get(0);
                    dto.setTargetContent(userComment.getContent());
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return dto;
    }


    private String formatNum(Integer num) {
        if (num == null) {
            return "0";
        }
        if (num >= 10000) {
            return df.format(num / 10000.0);
        }
        return num.toString();
    }

    // 查询下降评论；
    public void wrapReplies(CommentDto dto, SbUserComment comment) {
        if (comment.getReplyNum().intValue() == 0) {
            return;
        }
        SbUserCommentExample example = new SbUserCommentExample();
        example.setLimit(2);
        example.setOrderByClause("diff_value desc");
        SbUserCommentExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(comment.getId());
        criteria.andStatusEqualTo(Whether.yes.getValue());
        criteria.andAuditStatusIn(auditStatusList);
        List<SbUserComment> commentList = commentMapper.selectByExample(example);
        List<CommentDto> list = new ArrayList<>();
        if (commentList.isEmpty()) {
            return;
        }
        for (SbUserComment subComment : commentList) {
            try {
                CommentDto subCommentDto = wrapFinalCommentDto(subComment);
                list.add(subCommentDto);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }
        dto.setReplies(list);
    }

    private CommentDto wrapFinalCommentDto(SbUserComment comment) {
        CommentDto dto = new CommentDto();
        try {
            if (comment.getImgList() != null) {
                dto.setContent(comment.getContent() + " 【图片】");
            } else {
                dto.setContent(comment.getContent());
            }
            UserBriefDto user = new UserBriefDto();
            DsmUser owner = userMapper.selectByPrimaryKey(comment.getUid());
            user.setId(owner.getUid());
            user.setNickname(owner.getNickName());
            dto.setUser(user);
            // 末级回复给某个人的回复，此时处理回复用户信息
            if (comment.getPuid() != null) {
                UserBriefDto replyUserDto = new UserBriefDto();
                DsmUser replyUser = userMapper.selectByPrimaryKey(comment.getPuid());
                replyUserDto.setId(replyUser.getUid());
                replyUserDto.setNickname(replyUser.getNickName());
                dto.setReplyUser(replyUserDto);
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return dto;
    }

    private List<String> handleImages(SbUserComment comment) {
        // 存在图片上传
        if (comment.getImgList() != null && comment.getImgList().length() > 10) {
            List<String> images = new ArrayList<>();
            try {
                List<ImageDto> imageDtoList = JacksonUtil.json2pojo(comment.getImgList(),
                        new TypeReference<List<ImageDto>>() {
                        });
                for (ImageDto dto : imageDtoList) {
                    if (dto.getDimension() != null && dto.getDimension().length() > 4) {
                        String[] arr = dto.getDimension().split("x");
                        if (arr.length == 2) {
                            Integer width = Integer.valueOf(arr[0]);
                            Integer height = Integer.valueOf(arr[1]);
                            if (dto.getOriUrl() != null && dto.getOriUrl().length() > 10) {
                                images.add(wapThumbnailUrl(dto.getOriUrl(), width, height, 200));
                            }
                        }
                    } else if (dto.getOriUrl() != null && dto.getOriUrl().length() > 10) {
                        images.add(dto.getOriUrl());
                    }
                }
                return images;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<String>();
    }

    // 产生方形缩略图URL
    private String wapThumbnailUrl(String url, Integer width, Integer height, Integer length) {
        if (width > length && height > length) {
            if (width >= height) {
                Integer targetWidth = Math.round((float) (width / (height + 0.0)) * length);
                url = url + String.format("?x-oss-process=image/resize,m_lfit,h_%d,w_%d/crop,x_%d,y_0,w_%d,h_%d",
                        length, targetWidth, Math.round((targetWidth - length) / 2.0), length, length);
            } else {
                Integer targetHeight = Math.round((float) (height / (width + 0.0)) * length);
                url = url + String.format("?x-oss-process=image/resize,m_lfit,h_%d,w_%d/crop,x_0,y_%d,w_%d,h_%d",
                        targetHeight, length, Math.round((targetHeight - length) / 2.0), length, length);
            }
        }
        return url;
    }

    public Response commentReplyDetailTop(Integer commentId, Integer uid, Integer appId) {

        try {
            SbUserCommentExample example = new SbUserCommentExample();
            example.setLimit(1);
            SbUserCommentExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(commentId);
            if (null != appId) {
                criteria.andAppIdEqualTo(appId);
            }
            criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            List<SbUserComment> sbUserComments = commentMapper.selectByExample(example);
            CommentDto dto = new CommentDto();
            if (CollectionUtils.isNullOrEmpty(sbUserComments)) {
                return new Response(dto);
            }

            SbUserComment sbUserComment = commentMapper.selectByExample(example).get(0);
            dto = wrapCommentDto(sbUserComment, uid, PageSource.REPLY_DETAIL);
//            BeanUtils.copyProperties(sbUserComment, dto);

//            VideoDto videoDto = new VideoDto();
//            // 视频
//            if (sbUserComment.getVideoId() != null) {
//                SbVideo video = videoMapper.selectByPrimaryKey(sbUserComment.getVideoId());
//                videoDto.setId(video.getId());
//                videoDto.setUrl(video.getFormatUrl() != null ? video.getFormatUrl() : video.getOriUrl());
//                videoDto.setCover(video.getCover());
//                videoDto.setRefId(video.getRefId());
//                dto.setVideo(videoDto);
//            }
//
//            dto.setImages(handleImages(sbUserComment));
//
//            dto.setDiffValue(formatNum(sbUserComment.getDiffValue()));
//
//            UserBriefDto user = new UserBriefDto();
//            DsmUser owner = userMapper.selectByPrimaryKey(sbUserComment.getUid());
//            user.setId(owner.getUid());
//            user.setNickname(owner.getNickName());
//            user.setHeadImg(owner.getAvatar());
//            dto.setUser(user);
//
//            dto.setTime(commentFormat.format(sbUserComment.getCreateTime()));
//
//            dto.setPraiseStatus(
//                    praiseService.getPraiseStatus(uid, sbUserComment.getId(), UserPraiseTargetType.COMMENT.getValue()));


            return new Response(dto);
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }

    }
}
