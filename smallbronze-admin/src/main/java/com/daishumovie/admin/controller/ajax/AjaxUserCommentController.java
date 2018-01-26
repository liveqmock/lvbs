package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IUserCommentService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by feiFan.gou on 2017/8/28 10:23.
 * 话题回复和评论管理列表，同步请求相关接口
 */
@RestController
@RequestMapping("/ajax/comment/")
public class AjaxUserCommentController extends BaseController {

    @Autowired
    private IUserCommentService userCommentService;

    @RequestMapping("paginate")
    public ReturnDto paginate(ParamDto param, String title,Integer type, String createTime, Integer status,Integer auditStatus,Integer appId) {

        return userCommentService.paginate(param, title, type,createTime, status, auditStatus, appId);
    }

    @RequestMapping("fail")
    @LogConfig(object = OperationObject.comment, type = OperationType.un_pass)
    public JSONResult fail(HttpServletRequest request) {

        try {
            userCommentService.fail(SessionUtil.getLoginAdminUid(request),request.getParameter("auditDesc"),request.getParameterValues("ids[]"));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping("through")
    @LogConfig(object = OperationObject.comment, type = OperationType.un_pass)
    public JSONResult through( HttpServletRequest request) {

        try {
            userCommentService.pass(SessionUtil.getLoginAdminUid(request),request.getParameterValues("ids[]"));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }



}
