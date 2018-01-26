package com.daishumovie.admin.controller;

import com.daishumovie.base.enums.db.UploadVideosStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/8/28 10:23.
 * 上传视频管理列表，同步请求相关接口
 */
@Controller
@RequestMapping("/upload/v/")
public class VideoUploadController extends BaseController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView view = new ModelAndView();
        view.addObject("upload_status", UploadVideosStatus.values());
        view.setViewName("videos/upload/index");
        return view;
    }

    @RequestMapping(value = "initAdd", method = RequestMethod.GET)
    public ModelAndView initAdd() {

        ModelAndView view = new ModelAndView();
        view.setViewName("videos/upload/add");
        return view;
    }
}
