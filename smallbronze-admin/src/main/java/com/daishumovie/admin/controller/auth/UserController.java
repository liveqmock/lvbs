package com.daishumovie.admin.controller.auth;


import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.dao.mapper.smallbronzeadmin.AdminRoleEntityMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.RoleEntityMapper;
import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.AdminRoleEntity;
import com.daishumovie.dao.model.auth.RoleEntity;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user/")
public class UserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IAdminService adminService;

    @Autowired
    private AdminRoleEntityMapper relMapper;

    @Autowired
    private RoleEntityMapper roleMapper;

    /**
     * 用户首页
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/admin/user/index");
        } catch (Exception e) {
            LOGGER.error("Controller exception.", e);
        }
        return modelAndView;
    }

    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value = "addUserIndex", method = RequestMethod.GET)
    public ModelAndView addUserIndex(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Integer uid;
            if (!StringUtil.isEmpty(request.getParameter("id"))){
                uid = Integer.valueOf(request.getParameter("id"));
                AdminEntity adminEntity = adminService.get(uid);
                modelAndView.addObject("user", adminEntity);
                modelAndView.addObject("user", adminEntity);
            }

            modelAndView.setViewName("/admin/user/add");
        } catch (Exception e) {
            LOGGER.error("Controller exception.", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "roleList", method = RequestMethod.GET)
    public ModelAndView roleList(String adminId) {

        List<RoleEntity> roleList = roleMapper.selectByParam(new RoleEntity(),
                null,null,null,null,null,null,null);
        if (!CollectionUtils.isNullOrEmpty(roleList)) {
            AdminRoleEntity entity = new AdminRoleEntity();
            entity.setAdminId(Long.valueOf(adminId));
            List<AdminRoleEntity> relList = relMapper.selectByParam(entity,
                    null,null,null,null,null,null,null);
            List<Long> roleIdList = Lists.newArrayList();
            relList.forEach(rel -> roleIdList.add(rel.getRoleId()));
            roleList.forEach(role -> relList.forEach(rel -> {
                if (roleIdList.contains(role.getId())) {
                    role.setIncluding(true);
                } else {
                    role.setIncluding(false);
                }
            }));
        }
        AdminEntity admin = adminService.get(Long.valueOf(adminId));
        ModelAndView view = new ModelAndView("admin/rel/userRole");
        view.addObject("admin", admin);
        view.addObject("role_list", roleList);
        return view;
    }
}
