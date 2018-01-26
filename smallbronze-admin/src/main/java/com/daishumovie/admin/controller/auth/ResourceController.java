package com.daishumovie.admin.controller.auth;


import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.service.auth.IDataResourceService;
import com.daishumovie.dao.model.auth.ResourceEntity;
import com.daishumovie.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 系统管理： 包括资源 角色 以及关联
 * User: tianrui.lin@renren-inc.com
 * Date: 13-5-31
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/resource/")
public class ResourceController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private IDataResourceService resourceService;

    /**
     * 资源树首页
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/admin/resource/index");
        } catch (Exception e) {
            LOGGER.error("Controller exception.", e);
        }
        return modelAndView;
    }

    /**
     * 初始化编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "toEditResource")
    public ModelAndView toEditResource(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        try {
            String uuid = request.getParameter("uuid");
            if (!StringUtil.isEmpty(uuid)){
                ResourceEntity resourceEntity = resourceService.getByUuid(uuid);
                modelAndView.addObject("resource",resourceEntity);
            }
            modelAndView.addObject("parentUuid", request.getParameter("parentUuid"));
            modelAndView.addObject("parentsUuids", request.getParameter("parentsUuids"));
            modelAndView.setViewName("/admin/resource/initEdit");
        } catch (Exception e) {
            LOGGER.error("Controller exception.", e);
        }
        return modelAndView;
    }
}
