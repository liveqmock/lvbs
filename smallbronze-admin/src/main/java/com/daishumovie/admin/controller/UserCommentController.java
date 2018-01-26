package com.daishumovie.admin.controller;

import com.daishumovie.base.enums.db.CommentAuditStatus;
import com.daishumovie.base.enums.db.UserCommentTargetType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/8/28 10:23.
 * 话题回复和评论管理列表，同步请求相关接口
 */
@Controller
@RequestMapping("/comment/")
public class UserCommentController extends BaseController {


    @RequestMapping(value = "index", name = "话题回复评论审核列表页", method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView view = new ModelAndView();
        view.addObject("audit_status_list", CommentAuditStatus.values());
        view.addObject("comment_target_types", UserCommentTargetType.values());
        view.setViewName("videos/comment/index");
        return view;
    }


}
