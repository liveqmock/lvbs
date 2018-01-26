package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.ITopicService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.AuditStatus;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.base.enums.db.TopicSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by feiFan.gou on 2017/8/28 10:23.
 * 话题管理列表，同步请求相关接口
 */
@RestController
@RequestMapping("/ajax/topic/")
public class AjaxTopicController extends BaseController {

    @Autowired
    private ITopicService topicService;

    @RequestMapping(value = "paginate",method = RequestMethod.POST)
    public ReturnDto paginate(ParamDto param, String title, String createTime, Integer status,Integer auditStatus,Integer source) {
        if (auditStatus != null && (auditStatus.intValue() == AuditStatus.MAN_AUDIT_PASS.getValue() || auditStatus.intValue() == AuditStatus.MAN_AUDIT_NOT_PASS.getValue())){
            param.setSort_name("audit_time");
            if (null == param.getSort_order())
                param.setSort_order(ParamDto.order.desc);
        }else{

            if (null == param.getSort_order()){
                param.setSort_name("create_time");
                param.setSort_order(ParamDto.order.desc);
            }
        }

        return topicService.paginate(param, title, createTime, status,auditStatus,TopicSource.USER.getValue());
    }

    @RequestMapping(value = "publish/paginate")
    public ReturnDto<SbTopicDto> paginate(ParamDto param, String publishTime, Integer videoId, Integer publishStatus, String title, Integer uploader,Integer channelId) {

        return topicService.paginate(param, publishTime, videoId, publishStatus, title, uploader,channelId);

    }

    @RequestMapping(value = "pass", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.pass)
    public JSONResult pass(Integer id, String reason, HttpServletRequest request) {

        try {
            topicService.passOrNot(id, reason, AuditStatus.MAN_AUDIT_PASS, SessionUtil.getLoginAdminUid(request));
            //视频需要转化成m3u8格式
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }


    @RequestMapping(value = "unPass", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.un_pass)
    public JSONResult unPass(Integer id, String reason, HttpServletRequest request) {

        try {
            topicService.passOrNot(id, reason, AuditStatus.MAN_AUDIT_NOT_PASS, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "batchPass", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.pass)
    public JSONResult batchPass(@RequestParam("ids[]") Integer[] ids, String reason, HttpServletRequest request) {

        try {
            topicService.batchOperate(ids, reason, AuditStatus.MAN_AUDIT_PASS, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }


    @RequestMapping(value = "batchUnPass", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.un_pass)
    public JSONResult batchUnPass(@RequestParam("ids[]") Integer[] ids, String reason, HttpServletRequest request) {

        try {
            topicService.batchOperate(ids, reason, AuditStatus.MAN_AUDIT_NOT_PASS, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "offline", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.off_shelf)
    public JSONResult offline(Integer id) {

        try {
            topicService.offline(id);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "up", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.up)
    public JSONResult up(Integer id) {

        try {
            topicService.up(id);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }


    @RequestMapping(value = "publish", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.publish)
    public JSONResult publish(Integer id, Integer channelId, String title, Integer publisher, Float coverTime, Boolean isDefault, String newCover, HttpServletRequest request) {

        try {
            topicService.publish(id, channelId, title, publisher, SessionUtil.getLoginAdminUid(request), coverTime, isDefault, newCover);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.delete)
    public JSONResult delete(Integer id) {

        try {
            topicService.delete(id);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    /**
     * 对已发布视频进行编辑
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic,type = OperationType.edit)
    public JSONResult edit(Integer topicId, String title, Float coverTime, Boolean isDefault, String uploadCover) {

        try {
            topicService.edit(topicId, title, coverTime, isDefault, uploadCover);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
}
