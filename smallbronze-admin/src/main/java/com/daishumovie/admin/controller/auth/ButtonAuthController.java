package com.daishumovie.admin.controller.auth;

import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.service.auth.IButtonAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/7/14 13:54.
 */
@Controller
@RequestMapping("buttonAuth/")
public class ButtonAuthController extends BaseController {

    @Autowired
    private IButtonAuthService authService;

    /**
     * 按钮权限index,树形
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam("resource_id") String resourceId) {

        ModelMap model = new ModelMap();
        model.put("button_list", authService.buttonList(resourceId,null));
        model.put("resource_id", resourceId);
        return new ModelAndView("/authority/button/index", model);
    }


    /**
     * 根据roleId,查询该角色下拥有的角色
     * @param roleId
     * @return
     */
    @RequestMapping(value = "authorityByRole", method = RequestMethod.GET)
    public ModelAndView authorityByRole(@RequestParam("role_id") Long roleId) {

        ModelMap model = new ModelMap();
        model.put("button_list", authService.menuList(roleId));
        model.put("role_id", roleId);
        return new ModelAndView("/authority/button/authorityByRole", model);
    }

}
