package com.daishumovie.api.controller.v1;

import java.util.List;

import com.daishumovie.api.service.ReportService;
import com.daishumovie.base.dto.user.UserFollowDto;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.mapper.smallbronze.SbUserReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daishumovie.api.service.PraiseService;
import com.daishumovie.api.service.ReportService;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.album.AlbumDto;
import com.daishumovie.base.dto.album.IdTitleImage;
import com.daishumovie.base.dto.category.ChannelDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.enums.db.ReportProblem;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 顶 踩
 *
 * @author Cheng Yufei
 * @create 2017-09-06 14:36
 **/
@RestController
@RequestMapping("/v1")
public class PraiseController {

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private ReportService reportService;

    /**
     * 顶
     *
     * @param targetType
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/praise/{targetType:[1-4]}/{targetId:\\d+}", method = RequestMethod.POST)
    public Response<String> praise(@PathVariable Integer targetType, @PathVariable Long targetId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        String appId = header.getAppId();

        return praiseService.praise(targetId, targetType, uid, UserPraiseType.PRAISE.getValue(), appId);
    }


    /**
     * 踩
     *
     * @param targetType
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/criticize/{targetType:[1-4]}/{targetId:\\d+}", method = RequestMethod.POST)
    public Response<String> criticize(@PathVariable Integer targetType, @PathVariable Long targetId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        String appId = header.getAppId();

        return praiseService.criticize(targetId, targetType, uid, UserPraiseType.CRITICISM.getValue(), appId);
    }

    /**
     * 取消 顶、踩
     *
     * @param targetType
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/cancel/{targetType:[1-5]}/{targetId:\\d+}/{type:[1-2]}", method = RequestMethod.POST)
    public Response<String> cancel(@PathVariable Integer targetType, @PathVariable Long targetId, @PathVariable Integer type) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        String appId = header.getAppId();

        return praiseService.cancel(targetId, targetType, uid, type,appId);
    }

    /**
     * 关注\取消关注
     *
     * @param targetType
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/follow/{targetType:[1-5]}/{targetId:\\d+}", method = RequestMethod.POST)
    public Response<String> follow(@PathVariable Integer targetType, @PathVariable Long targetId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        String appId = header.getAppId();

        return praiseService.follow(targetId, targetType, uid, UserPraiseType.FOLLOW.getValue(), appId);
    }


    /**
     * 关注 话题、频道、用户 的 用户列表
     *
     * @param targetType
     * @return
     */
    @RequestMapping(value = "/followUserList/{targetType:[1-4]}/{targetId:\\d+}/{pageIndex}", method = RequestMethod.GET)
    public Response followUsers(@PathVariable Integer targetType, @PathVariable Long targetId,
                                @PathVariable("pageIndex") Integer pageIndex, @RequestHeader(value = "appId", required = false) Integer appId) throws Exception {

        BaseListDto<UserFollowDto> baseListDto = new BaseListDto<UserFollowDto>();
        PageInDto pageInfo = new PageInDto(pageIndex, 10);
        List<UserFollowDto> userFollowDtos = praiseService.followUserList(targetId, targetType, pageInfo, appId,null);

        baseListDto.setList(userFollowDtos);
        baseListDto.setPageIndex(pageInfo.getPageIndex());
        baseListDto.setHasNext(userFollowDtos.size() < pageInfo.getPageSize() ? 0 : 1);
        return new Response(baseListDto);
    }

    /**
     * 个人用户关注的话题列表
     *
     * @param pageIndex
     * @param appId
     * @return
     */
    @RequestMapping(value = "/follow/topic/{pageIndex}")
    public Response<BaseListDto<TopicDto>> followTopicList(@PathVariable("pageIndex") Integer pageIndex,
                                                           @RequestHeader(value = "appId", required = false) Integer appId) {

        PageInDto pageInfo = new PageInDto(pageIndex, 10);
        return praiseService.getFollowTopicByPageSimple(pageInfo, appId, null);
    }

    /**
     * 他人用户关注的话题列表
     *
     * @param pageIndex
     * @param appId
     * @return
     */
    @RequestMapping(value = "/other/follow/topic/{userId}/{pageIndex}")
    public Response<BaseListDto<TopicDto>> userFollowTopicList(@PathVariable("userId") Integer userId,
                                                               @PathVariable("pageIndex") Integer pageIndex, @RequestHeader(value = "appId") Integer appId) {

        PageInDto pageInfo = new PageInDto(pageIndex, 10);
        return praiseService.getFollowTopicByPageSimple(pageInfo, appId, userId);
    }

    /**
     * 用户关注的频道列表
     *
     * @param pageIndex
     * @param appId
     * @return
     */
    @RequestMapping(value = "/follow/channel/{pageIndex}")
    public Response<BaseListDto<ChannelDto>> followChannelList(@PathVariable("pageIndex") Integer pageIndex,
                                                               @RequestHeader(value = "appId", required = false) Integer appId) {

        Response<BaseListDto<ChannelDto>> resp = new Response<BaseListDto<ChannelDto>>();
        BaseListDto<ChannelDto> baseDto = new BaseListDto<ChannelDto>();
        PageInDto pageInfo = new PageInDto(pageIndex, 10);

        List<ChannelDto> topicList = praiseService.getFollowChannelByPage(pageInfo, appId);
        baseDto.setList(topicList);
        baseDto.setPageIndex(pageIndex);
        baseDto.setHasNext(topicList.size() >= pageInfo.getPageSize() ? 1 : 0);
        resp.setResult(baseDto);
        return resp;

    }

    /**
     * 举报API
     *
     * @param problemId
     * @return
     */
    @RequestMapping(value = "/report/{contentId:\\d+}/{type:\\d+}/{problemId:\\d+}", method = RequestMethod.POST)
    public Response report(@PathVariable Integer contentId, @PathVariable Integer type,
                           @PathVariable Integer problemId, @RequestHeader(value = "appId", required = false) Integer appId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        Object problem = null;
        if (type.equals(ReportType.VIDEO.getCode())) {
            problem = ReportProblem.get(problemId);
        } else if (type.equals(ReportType.COMMENT.getCode())) {
            problem = CommentReportProblem.get(problemId);
        } else if (type.equals(ReportType.USER.getCode())) {
            problem = UserReportProblem.get(problemId);
        }
        if (problem == null) {
            return new Response<>();
        }
        return reportService.save(contentId, type, uid, appId, problemId);
    }

    /**
     * 用户关注的用户列表
     *
     * @param pageIndex
     * @param appId
     * @return
     */
    @RequestMapping(value = "/userFollowList/{uid}/{pageIndex}")
    public Response<BaseListDto<UserFollowDto>> followUserList(@PathVariable("pageIndex") Integer pageIndex, @PathVariable("uid") Integer uid,
                                                               @RequestHeader(value = "appId", required = false) Integer appId) throws Exception {

        Response<BaseListDto<UserFollowDto>> resp = new Response<BaseListDto<UserFollowDto>>();
        BaseListDto<UserFollowDto> baseDto = new BaseListDto<UserFollowDto>();
        PageInDto pageInfo = new PageInDto(pageIndex, 8);

        List<UserFollowDto> followUserByPage = praiseService.getFollowUserByPage(pageInfo, appId, uid);
        baseDto.setList(followUserByPage);
        baseDto.setPageIndex(pageIndex);
        baseDto.setHasNext(followUserByPage.size() >= pageInfo.getPageSize() ? 1 : 0);
        resp.setResult(baseDto);
        return resp;

    }


    /**
     * 用户关注 用户所发表的话题列表
     *
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/followTopics/{pageIndex}", method = RequestMethod.GET)
    public Response<BaseListDto<TopicDto>> getFollowTopics(@PathVariable("pageIndex") Integer pageIndex, @RequestHeader(value = "appId", required = false) Integer appId) {
        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();

        Response<BaseListDto<TopicDto>> resp = new Response<BaseListDto<TopicDto>>();
        BaseListDto<TopicDto> baseDto = new BaseListDto<TopicDto>();
        PageInDto pageInDto = new PageInDto(pageIndex, 10);
        List<TopicDto> userTopic = praiseService.getFollowUserTopic(pageInDto, uid, appId);
        baseDto.setList(userTopic);
        baseDto.setPageIndex(pageIndex);
        baseDto.setHasNext(userTopic.size() >= pageInDto.getPageSize() ? 1 : 0);
        resp.setResult(baseDto);
        return resp;
    }


    /**
     * 加入黑名单（针对用户）
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/addBlack/{targetId:\\d+}", method = RequestMethod.POST)
    public Response<String> addBlack( @PathVariable Long targetId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        String appId = header.getAppId();

        return praiseService.addBlack(targetId,uid,appId);
    }

    /**
     * 移除黑名单（针对用户）
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/removeBlack/{targetId:\\d+}", method = RequestMethod.POST)
    public Response<String> removeBlack(@PathVariable Long targetId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        String appId = header.getAppId();

        return praiseService.removeBlack(targetId, uid, appId);
    }

    /**
     * 黑名单列表（针对用户）
     * @return
     */
    @RequestMapping(value = "/getBlackList/{pageIndex}", method = RequestMethod.GET)
    public  Response<BaseListDto<UserFollowDto>> getBlackList(@PathVariable("pageIndex") Integer pageIndex) throws Exception {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        String appId = header.getAppId();

        Response<BaseListDto<UserFollowDto>> resp = new Response<BaseListDto<UserFollowDto>>();
        BaseListDto<UserFollowDto> baseDto = new BaseListDto<UserFollowDto>();
        PageInDto pageInfo = new PageInDto(pageIndex, 8);

        List<UserFollowDto> followUserByPage = praiseService.getBlackListByPage(pageInfo,uid, appId);
        baseDto.setList(followUserByPage);
        baseDto.setPageIndex(pageIndex);
        baseDto.setHasNext(followUserByPage.size() >= pageInfo.getPageSize() ? 1 : 0);
        resp.setResult(baseDto);
        return resp;
    }

}
