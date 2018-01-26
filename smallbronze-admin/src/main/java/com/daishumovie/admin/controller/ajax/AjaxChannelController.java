package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.dto.ChannelDto;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.model.SbChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/9/7 17:15.
 */
@RestController
@RequestMapping("/ajax/channel/")
public class AjaxChannelController {

    @Autowired
    private IChannelService channelService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.channel,type = OperationType.insert)
    public JSONResult save(HttpServletRequest request, SbChannel channel) {

        try {
            channelService.save(channel, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.channel,type = OperationType.edit)
    public JSONResult update(HttpServletRequest request, SbChannel channel) {

        try {
            channelService.update(channel, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.channel,type = OperationType.delete)
    public JSONResult delete(HttpServletRequest request, Integer id) {

        try {
            channelService.delete(id, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "secondChannelByPid", method = RequestMethod.GET)
    public JSONResult secondChannelByPid(Integer pid) {

        try {
            List<ChannelDto> list = channelService.secondLevelList(pid, null);
            return JSONResult.success(list);
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }



}
