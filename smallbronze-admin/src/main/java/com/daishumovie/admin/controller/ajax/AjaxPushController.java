package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.PushTaskDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IPushService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.dao.model.SbPushTask;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/10/16 10:34.
 */
@RestController
@RequestMapping("/ajax/push/")
public class AjaxPushController {

    private @Autowired
    IPushService pushService;

    @RequestMapping(value = "push", method = RequestMethod.POST)
    public JSONResult push(SbPushTask task, HttpServletRequest request) {

        try {
            pushService.push(task, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "cancelSchedule", method = RequestMethod.POST)
    public JSONResult cancelSchedule(Integer taskId, HttpServletRequest request) {

        try {
            pushService.cancelSchedule(taskId, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "paginate", method = RequestMethod.POST)
    public ReturnDto<PushTaskDto> paginate(ParamDto param, String pushTime, Integer status, String alert, Integer targetType, Integer platform) {

        return pushService.paginate(param, pushTime, status, alert, targetType, platform);
    }

    @RequestMapping(value = "getAlert", method = RequestMethod.GET)
    public Map<String, String> getAlert(Integer targetType, String targetId) {

        Map<String, String> result = Maps.newHashMap();
        try {
            result.put("alert", pushService.getAlert(targetType, targetId));
        } catch (ResultException e) {
            result.put("error", e.getMessage());
        }
        return result;
    }
}
