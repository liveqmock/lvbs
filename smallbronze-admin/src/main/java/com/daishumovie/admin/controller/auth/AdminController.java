package com.daishumovie.admin.controller.auth;

import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by feiFan.gou on 2017/8/23 10:17.
 */
@RequestMapping("/admin/")
@Controller
public class AdminController extends BaseController {

    /**
     * 跳转登录页
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login() {

        return new ModelAndView("login");
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/initChangePwd", method = RequestMethod.GET)
    public ModelAndView initChangePwd() {
        return new ModelAndView("/admin/user/changePwd");
    }

}
