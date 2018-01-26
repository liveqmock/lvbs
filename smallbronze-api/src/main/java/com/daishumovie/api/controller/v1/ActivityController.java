package com.daishumovie.api.controller.v1;


import com.aliyuncs.exceptions.ClientException;
import com.daishumovie.api.service.ActivityService;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.activity.ActivityDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.utils.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Cheng Yufei
 * @create 2017-09-12 10:39
 **/
@RestController
@RequestMapping("/v1/activity")
public class ActivityController {


    @Autowired
    private ActivityService activityService;


    /**
     * 活动列表
     * @return
     */
    @RequestMapping(value = "/list/{pageIndex}")
    public Response getActivityList(@PathVariable Integer pageIndex) {

        Header header = LocalData.HEADER.get();
        String appId = header.getAppId();

        Response<BaseListDto<ActivityDto>> resp = new Response<BaseListDto<ActivityDto>>();
        BaseListDto<ActivityDto> baseDto = new BaseListDto<ActivityDto>();
        PageInDto pageInDto = new PageInDto(pageIndex, 10);
        List<ActivityDto> activityList = activityService.getActivityListNew(pageInDto, appId);

        baseDto.setList(activityList);
        baseDto.setPageIndex(pageIndex);
        baseDto.setHasNext(activityList.size() >= pageInDto.getPageSize() ? 1 : 0);
        resp.setResult(baseDto);

        return resp;
    }

    /**
     * 活动详情 - Top
     * @return
     */
    @RequestMapping(value = "/detailTop/{activityId}")
    public Response detailTop(@PathVariable Integer activityId) {
        if (null == activityId) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        Header header = LocalData.HEADER.get();
        String appId = header.getAppId();
        ActivityDto dto = activityService.detailTop(activityId, appId);
        if(dto == null){
            return new Response<>(RespStatusEnum.ACTIVITY_INVALID);
        }
        return new Response<>(dto);
    }

    /**
     * 活动详情 - Middle
     * @return
     */
    @RequestMapping(value = "/detailMiddle/{activityId}/{pageIndex}")
    public Response detailMiddle(@PathVariable Integer activityId,@PathVariable Integer pageIndex) {
        if (null == activityId) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }

        Header header = LocalData.HEADER.get();
        String appId = header.getAppId();

        Response<BaseListDto<TopicDto>> resp = new Response<BaseListDto<TopicDto>>();
        BaseListDto<TopicDto> baseDto = new BaseListDto<TopicDto>();
        PageInDto pageInDto = new PageInDto(pageIndex, 6);
        List<TopicDto> dtos = activityService.detailMiddle(pageInDto, activityId, appId);

        baseDto.setList(dtos);
        baseDto.setPageIndex(pageIndex);

        baseDto.setHasNext( pageIndex == 1? dtos.size() >= 5 ? 1 : 0:dtos.size() >= pageInDto.getPageSize() ? 1 : 0);
        resp.setResult(baseDto);

        return resp;
    }

    /**
     * 活动报名
     * @param activityId
     * @return
     */
	@RequestMapping(value = "/signUp/{activityId}")
	public Response<String> signUp(@PathVariable Integer activityId) {
		DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		return activityService.signUpActivity(user.getUid(), activityId);
	}

    /**
     * 抽奖活动短信发送
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/sendMsg/{activityId}/{mobile}")
    public Response<String> sendMsg(@PathVariable Integer activityId,@PathVariable String mobile) throws ClientException {

        return activityService.sendMsg(activityId, mobile);
    }

    /**
     *抽奖活动报名（停用）
     * @param activityId
     * @param mobile
     * @return
     * @throws ClientException
     */
    @RequestMapping(value = "/enrol/{activityId}/{mobile}/{code}")
    public Response<String> enrol(@PathVariable Integer activityId,@PathVariable String mobile,@PathVariable String code) throws ClientException {

        return activityService.enrol(activityId, mobile, code);
    }

    /**
     *抽奖活动报名 新版 11/9
     * @param activityId
     * @return
     * @throws ClientException
     */
    @RequestMapping(value = "/enrolNew/{activityId}")
    public Response<String> enrolNew(@PathVariable Integer activityId) throws ClientException {

        DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        String did = LocalData.HEADER.get().getDid();
        return activityService.enrolNew(activityId, user.getUid(),did);
    }

    /**
     * 是否参加抽奖活动
     * @param activityId
     * @return
     * @throws ClientException
     */
    @RequestMapping(value = "/isEnrol/{activityId}")
    public Response<String> isEnrol(@PathVariable Integer activityId) throws ClientException {

        DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        String did = LocalData.HEADER.get().getDid();
        return activityService.isEnrol(activityId, user.getUid(), did);

    }
    /**
     * 获奖列表
     * @param activityId
     * @return
     * @throws ClientException
     */
	@RequestMapping(value = "/awardList/{activityId}")
	public Response<BaseListDto<Map.Entry<String, String>>> awardList(@PathVariable Integer activityId)
			throws ClientException {
		return activityService.getAwardList(activityId);

	}

    /**
     * 是否展示抽奖活动
     * @return
     * @throws ClientException
     */
    @RequestMapping(value = "/isShowAward")
    public Response isShowAward()
            throws ClientException {
        return activityService.isShowAward();
    }

}
