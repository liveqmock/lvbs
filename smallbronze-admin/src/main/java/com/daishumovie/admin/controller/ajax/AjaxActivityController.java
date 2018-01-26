package com.daishumovie.admin.controller.ajax;
import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.dto.ActivityDto;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IActivityService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.model.SbActivity;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * Created by feiFan.gou on 2017/10/20 11:28.
 */
@RestController
@RequestMapping("/ajax/activity/")
public class AjaxActivityController {
    @Autowired
    private IActivityService activityService;
    @Autowired
    private SbActivityMapper activityMapper;
    @RequestMapping("paginate")
    public ReturnDto<ActivityDto> paginate(ParamDto param, String title, String topic, Integer status,Integer whetherOnline, String preTime, String startTime) {
        return activityService.paginate(param, title, topic, status, whetherOnline, preTime, startTime);
    }
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.activity, type = OperationType.insert)
    public JSONResult save(SbActivity activity, HttpServletRequest request) {
        try {
            activityService.save(activity, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.activity, type = OperationType.edit)
    public JSONResult update(SbActivity activity, HttpServletRequest request) {
        try {
            activityService.update(activity, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @LogConfig(object = OperationObject.activity, type = OperationType.off_shelf)
    @RequestMapping(value = "offline", method = RequestMethod.POST)
    public JSONResult offline(Integer id, HttpServletRequest request) {
        try {
            activityService.offline(id, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public SbActivity get(Integer id) {
        return activityMapper.selectByPrimaryKey(id);
    }
    @RequestMapping(value = "topicListByActivityId", method = RequestMethod.GET)
    public List<SbTopicDto> topicListByActivityId(Integer activityId) {
        if (null == activityId) {
            return Lists.newArrayList();
        }
        return activityService.topicListByActivityId(activityId);
    }
    @RequestMapping(value = "topicByActivityId", method = RequestMethod.POST)
    public JSONResult topicByActivityId(Integer topicId,Integer activityId) {
        try {
            return JSONResult.success(activityService.topicByActivityId(topicId, activityId));
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "putVideo", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.activity, type = OperationType.put_video)
    public JSONResult putVideo(Integer topicId,Integer activityId) {
        try {
            activityService.putVideo(topicId, activityId);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "updateLikeCount", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic, type = OperationType.edit)
    public JSONResult updateLikeCount(Integer topicId,Integer likeCount) {
        try {
            activityService.updateLikeCount(topicId, likeCount);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "removeVideoFromActivity", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.activity, type = OperationType.remove_video)
    public JSONResult removeVideoFromActivity(Integer topicId) {
        try {
            activityService.removeVideoFromActivity(topicId);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
}