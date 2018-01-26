package com.daishumovie.admin.controller;

import com.daishumovie.admin.service.IUserService;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.dao.model.DsmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/9/6 14:23.
 */
@Controller
@RequestMapping("/app/user")
public class AppUserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView view = new ModelAndView("user/index");
        view.addObject("whether", Whether.values());
        return view;
    }

    @RequestMapping(value = "view/{userId}",method = RequestMethod.GET)
    public ModelAndView view(@PathVariable Integer userId) {
        ModelAndView view = new ModelAndView("user/view");
        DsmUser user = userService.getUser(userId);
        view.addObject("user",user);
        return view;
    }
}
