package com.daishumovie.admin.controller.auth;


import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.service.auth.IDataResourceService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.dao.model.auth.ResourceEntity;
import com.daishumovie.dao.model.auth.RoleEntity;
import com.daishumovie.dao.model.auth.UserInfo;
import com.daishumovie.dao.model.auth.enums.EnumRoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping("/role/")
public class RoleController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private IDataResourceService resourceService;


	/**
	 * 角色首页
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index() {

		return new ModelAndView("admin/role/index");
	}

	/**
	 * 初始化新增/编辑角色
	 * @param name
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "initAdd", method = RequestMethod.GET)
	public ModelAndView initAdd(String name,String desc) {

		ModelAndView view = new ModelAndView("admin/role/add");
		view.addObject("name", name);
		view.addObject("desc", desc);
		return view;
	}

	/**
	 * 给角色赋权限
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "manage/{id}", method = RequestMethod.GET)
	public ModelAndView manage(@PathVariable("id") long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			UserInfo userInfo = SessionUtil.getLoginUserInfo(request);
			if (userInfo.containRoleType(EnumRoleType.ADMIN)) {
				modelAndView.addObject("availableResources", resourceService.queryByCondition(new HashMap()));
			} else {
				Set<RoleEntity> roles = userInfo.getRoles();
				Set<ResourceEntity> availableResources = new HashSet<>();
				for (RoleEntity ignored : roles) {
					availableResources.addAll(userInfo.getResources());
					Set<String> uuidSet = new HashSet<>();
					for (ResourceEntity ar : availableResources) {
						if (!StringUtils.isEmpty(ar.getParentsUuids()))
							uuidSet.addAll(new HashSet<>(Arrays.asList(ar.getParentsUuids().split(","))));
					}
					availableResources.addAll(resourceService.queryMenuEntities(uuidSet));
				}
				modelAndView.addObject("availableResources", availableResources);
			}

			List<ResourceEntity> roleResources = resourceService.queryRoleResource(id);

			modelAndView.addObject("roleId", id);
			modelAndView.addObject("roleResources", roleResources);
			modelAndView.setViewName("/admin/rel/roleResource");
		} catch (Exception e) {
			LOGGER.error("Controller exception.", e);
		}
		return modelAndView;
	}

}
