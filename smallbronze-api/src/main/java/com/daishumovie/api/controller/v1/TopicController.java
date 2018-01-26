package com.daishumovie.api.controller.v1;

import java.util.ArrayList;
import java.util.List;

import com.daishumovie.api.service.ActivityService;
import com.daishumovie.api.service.HistoryService;
import com.daishumovie.base.enums.db.TopicStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daishumovie.api.service.CommentService;
import com.daishumovie.api.service.TopicListService;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.VideoUrl;
import com.daishumovie.base.dto.topic.RecommendBaseListDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.UserCommentTargetType;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicBucketMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicBucket;
import com.daishumovie.dao.model.SbTopicBucketExample;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.sensitiveword.SensitivewordUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuruisong on 2017/9/6
 * @since 1.0
 */
@RestController("v1.TopicController")
@RequestMapping("/v1/topic")
@Slf4j
public class TopicController {

    @Autowired
    private SbTopicMapper sbTopicMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private SbTopicBucketMapper sbTopicBucketMapper;
    @Autowired
    private TopicListService topicListService;
    @Autowired
    private DsmUserMapper dsmUserMapper;

    @Autowired
    private ActivityService activityService;

    @Value("#{'${audit_status}'.split(',')}")
    private List<Integer> auditStatusList;

    /**
     * 发布话题
     * @param title
     * //@param description
     * @param channelId
     * @param videoId
     * @return
     */
    @RequestMapping(value="/publish", method = RequestMethod.POST)
    public Response publish(@RequestParam String title,
                        /*@RequestParam(required = false) String description,*/
                            @RequestParam Integer channelId,
                            @RequestParam Integer videoId,
                            @RequestParam(required = false) Integer activityId
    ){

        DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        DsmUser userByUid = dsmUserMapper.selectByPrimaryKey(user.getUid());
        if(YesNoEnum.YES.getCode().intValue() != userByUid.getIsTopicAuth()){
            return new Response(RespStatusEnum.TOPIC_NOT_AUTH);
        }

        //敏感词
        if(SensitivewordUtil.isContaintSensitiveWord(title, SensitivewordUtil.minMatchTYpe)
                /*|| SensitivewordUtil.isContaintSensitiveWord(description, SensitivewordUtil.minMatchTYpe)*/){
            return new Response(RespStatusEnum.PARAM_SENSITIVE_WORD);
        }

//        if(title.length()>50 || description.length() >500){
//            return new Response(RespStatusEnum.PARAM_FAIL);
//        }

        SbTopic sbTopic = new SbTopic();
        if(activityId != null){
            Boolean underway = activityService.isUnderway(activityId);
            if(!underway){
                return new Response(RespStatusEnum.ACTIVITY_INVALID);
            }
            sbTopic.setActivityId(activityId);
        }

        sbTopic.setTitle(title);
   /*     sbTopic.setDescription(description);*/
        sbTopic.setChannelId(channelId);
        sbTopic.setVideoId(videoId);
        sbTopic.setUid(user.getUid());
        Header header = LocalData.HEADER.get();
        sbTopic.setAppId(Integer.valueOf(header.getAppId()));

        sbTopicMapper.insertSelective(sbTopic);

        //作品数加1
//        dsmUserMapper.selfPlusMinusByPrimaryKey("opus_num", "+", 1, user.getUid());

        return new Response();

    }

    /**
     * 删除话题
     *
     * @param topicId
     * @return
     */
    @RequestMapping(value="/del")
    public Response del(@RequestParam Integer topicId ){

        DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        Header header = LocalData.HEADER.get();

        SbTopic sbTopic = sbTopicMapper.selectByPrimaryKey(topicId);
        if(sbTopic == null){
            return new Response(RespStatusEnum.TOPIC_NOT_EXIST);
        }
        if(!header.getAppId().equals(sbTopic.getAppId().toString())){
            return new Response(RespStatusEnum.TOPIC_NOT_EXIST);
        }

        if(!sbTopic.getUid().equals(user.getUid())){
            return new Response(RespStatusEnum.COMMENT_DEL_NOT_AUTH);
        }
        if(sbTopic.getStatus().equals(TopicStatus.deleted.getValue())){
            return new Response();
        }

        SbTopic record = new SbTopic();
        record.setId(topicId);
        record.setStatus(TopicStatus.deleted.getValue());
        sbTopicMapper.updateByPrimaryKeySelective(record);
        //作品数-1
        if(auditStatusList.contains(sbTopic.getAuditStatus()) && sbTopic.getStatus().intValue() == YesNoEnum.YES.getCode()) {
            dsmUserMapper.selfPlusMinusByPrimaryKey("publish_count", "-", 1, user.getUid());
        }
        return new Response();

    }


    /**
     * 首页话题列表
     *
     * @param type
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/index/{type}/{pageIndex}")
    public Response<BaseListDto<TopicDto>> indexList(@PathVariable("type") Integer type,
                                                     @PathVariable("pageIndex") Integer pageIndex,
                                                     @RequestHeader(value = "appId", required = false) Integer appId) {

        Response<BaseListDto<TopicDto>> resp = new Response<BaseListDto<TopicDto>>();
        BaseListDto<TopicDto> baseDto = new BaseListDto<TopicDto>();
        PageInDto pageInfo = new PageInDto(pageIndex, 10);

        List<TopicDto> topicList = topicListService.getTopicByPage(pageInfo, appId, null, null, type,
                LocalData.HEADER.get().getOsEnum() == Header.OsEnum.IOS, PageSource.TOPIC_LIST);
        baseDto.setList(topicList);
        baseDto.setPageIndex(pageIndex);
        baseDto.setHasNext(topicList.size() >= pageInfo.getPageSize() ? 1 : 0);
        resp.setResult(baseDto);
        return resp;

    }

    /**
     * 话题详情
     *
     * @return
     */
    @RequestMapping(value = "/detail/{topicId}")
    public Response<TopicDto> topicDetail(@PathVariable("topicId") Integer topicId,
                                          @RequestHeader(value = "appId", required = false) Integer appId) throws Exception {

//        return topicListService.getTopicDetail(topicId, appId, LocalData.HEADER.get().getOsEnum() == Header.OsEnum.IOS);
        return topicListService.getTopicDetailSimple(topicId, appId);
    }

    /**
     * 主题评论/回复评论
     * @param topicId
     * @param pid
     * @param content
     * @param imgList
     * @param videoId
     * @return
     */
    @RequestMapping(value="/comment/add", method = RequestMethod.POST)
    public Response<String> addComment(@RequestParam Integer topicId,
                                       @RequestParam(required = false) Integer pid,
                                       @RequestParam(required = false) Integer pcid,
                                       @RequestParam(required = false)  String content,
                                       @RequestParam(required = false)  String imgList,
                                       @RequestParam(required = false)  Integer videoId){

        return commentService.save(topicId, UserCommentTargetType.TOPIC.getValue(), pid, pcid, content, imgList, videoId);
    }
    /**
     * 获取视频播放地址
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/getVideoUrl/{videoId:\\d+}/{location}")
    public Response getVideoUrl(@PathVariable Integer videoId,@PathVariable Integer location,
                                @RequestHeader(value = "appId", required = false) Integer appId) {
        if (null == videoId) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        VideoUrl videoUrl = new VideoUrl(topicListService.getVideoUrl(videoId, appId));
        return new Response(videoUrl);
    }

    /**
     * 获取视频播放地址（新版 增加观看历史记录）
     *
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/getVideoUrlNew/{topicId:\\d+}/{videoId:\\d+}/{location}")
    public Response getVideoUrlNew(@PathVariable Integer topicId, @PathVariable Integer videoId, @PathVariable Integer location,
                                   @RequestHeader(value = "appId", required = false) Integer appId) {
        if (null == videoId) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }

        Header header = LocalData.HEADER.get();
        String did = header.getDid();
        Integer uid = header.getUid();
        VideoUrl videoUrl = new VideoUrl(topicListService.getVideoUrlNew(topicId, videoId, appId, did, uid));
        return new Response(videoUrl);
    }



    //推荐列表
    @RequestMapping("/recommendList")
    public Response<BaseListDto<TopicDto>> getRecommendList(Integer pageIndex){

        //获取最大桶号
        SbTopicBucketExample sbTopicBucketExample = new SbTopicBucketExample();
        sbTopicBucketExample.setOrderByClause("id desc");
        sbTopicBucketExample.setLimit(1);
        List<SbTopicBucket> sbTopicBuckets = sbTopicBucketMapper.selectByExample(sbTopicBucketExample);
        if(sbTopicBuckets.isEmpty()){
            return new Response<>();//无可推荐
        }

        int maxBucketIndex = sbTopicBuckets.get(0).getId();
        Header header = LocalData.HEADER.get();
        // 测试写死1
        Integer nextBucketIndex = topicListService.getNextBucketIndex( maxBucketIndex);
        if(nextBucketIndex == null){
            return new Response<>();//无可推荐
        }
        SbTopicBucket sbTopicBucket = sbTopicBucketMapper.selectByPrimaryKey(nextBucketIndex);
        String topicIds = sbTopicBucket.getTopicIds();
        String[] topicIdArr = StringUtils.splitByWholeSeparator(topicIds, ",");
        List<Integer> topicIdIntArr = new ArrayList<>();
        for (String id : topicIdArr) {
            topicIdIntArr.add(Integer.valueOf(id));
        }
        List<TopicDto> topicList = topicListService.getTopicList(topicIdIntArr, PageSource.TOPIC_LIST);

        BaseListDto<TopicDto> dto = new RecommendBaseListDto<>(topicList, new PageInDto(pageIndex, 100), nextBucketIndex);
        dto.setHasNext(1);
        return new Response<>(dto);

    }

    @RequestMapping(value = "/channelTopicList/{id:\\d+}/{pageIndex}", method = RequestMethod.GET)
    public Response<BaseListDto<TopicDto>> channelTopicList(@PathVariable Integer id, @PathVariable Integer pageIndex,
                                                            @RequestHeader(value = "appId", required = false) Integer appId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        PageInDto pageInDto = new PageInDto(pageIndex, 10);
        if (id.intValue() == 0) {
            return getRecommendList(pageIndex);
        }

        return topicListService.getTopicByPageSimple(pageInDto, appId, id, uid, 1,
                PageSource.TOPIC_LIST);

    }

    /**
     * 历史桶信息，桶号为0时走推荐接口
     * @param bucketId
     * @return
     */
    @RequestMapping("/recommendHistory/{bucketId:\\d+}")
    public Response<BaseListDto<TopicDto>> recommendHistory(@PathVariable Integer bucketId) {
        if (bucketId.intValue() == 0) {
            return getRecommendList(1);
        }

        SbTopicBucket sbTopicBucket = sbTopicBucketMapper.selectByPrimaryKey(bucketId);
        if (sbTopicBucket == null) {
            return new Response<>();// 无可推荐
        }
        String topicIds = sbTopicBucket.getTopicIds();
        String[] topicIdArr = StringUtils.splitByWholeSeparator(topicIds, ",");
        List<Integer> topicIdIntArr = new ArrayList<>();
        for (String id : topicIdArr) {
            topicIdIntArr.add(Integer.valueOf(id));
        }
        List<TopicDto> topicList = topicListService.getTopicList(topicIdIntArr, PageSource.TOPIC_LIST);

        return new Response<>(new RecommendBaseListDto<>(topicList, new PageInDto(1, 100), bucketId));

    }
}
