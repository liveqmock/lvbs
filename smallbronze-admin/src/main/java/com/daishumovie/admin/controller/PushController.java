package com.daishumovie.admin.controller;

import com.daishumovie.base.enums.db.PushPlatform;
import com.daishumovie.base.enums.db.PushStatus;
import com.daishumovie.base.enums.db.PushTargetType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/10/16 10:05.
 */
@Controller
@RequestMapping("/push/")
public class PushController extends BaseController {


    @RequestMapping("index")
    public ModelAndView index() {

        ModelAndView view = new ModelAndView();
        view.addObject("push_status", PushStatus.values());
        view.addObject("target_type", PushTargetType.template());
        view.addObject("push_platform", PushPlatform.values());
        view.setViewName("push/index");
        return view;
    }

    @RequestMapping("initAdd")
    public ModelAndView initAdd() {

        ModelAndView view = new ModelAndView();
        view.addObject("target_type", PushTargetType.template());
        view.setViewName("push/add");
        return view;
    }
}
