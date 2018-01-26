package com.daishumovie.admin.service.auth.impl;


import java.util.*;

import com.daishumovie.admin.service.auth.*;
import com.daishumovie.dao.mapper.smallbronzeadmin.ResourceRoleEntityMapper;
import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.ResourceEntity;
import com.daishumovie.dao.model.auth.RoleEntity;
import com.daishumovie.dao.model.auth.UserInfo;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private ResourceRoleEntityMapper roleMapper;
	

	@Override
	public UserInfo getUserInfo(String username) {
		AdminEntity admin = adminService.getAdmin(username);
		return getUserInfo(admin);
	}

	public UserInfo getUserInfo(AdminEntity admin) {
		if (admin == null) {
			return null;
		}
		UserInfo userInfo = new UserInfo(admin);
		setRoles(admin, userInfo);
		setResources(userInfo);
		return userInfo;
	}

	private void setRoles(AdminEntity admin, UserInfo userInfo) {
		List<RoleEntity> roles = roleService.queryRoleByUser(admin.getUsername());
		if (!CollectionUtils.isNullOrEmpty(roles)) {
			userInfo.setRoles(new HashSet<>(roles));
		}
	}

	private void setResources(UserInfo userInfo) {

		//重新处理权限问题
		Set<RoleEntity> roleSet = userInfo.getRoles();
		Set<Long> roleIdSet = Sets.newHashSet();
		List<ResourceEntity> topMenu = Lists.newArrayList();
		Set<ResourceEntity> entitySet = Sets.newHashSet();
		if (!CollectionUtils.isNullOrEmpty(roleSet)) {
			roleSet.forEach(roleEntity -> roleIdSet.add(roleEntity.getId()));
			List<ResourceEntity> resourceList = roleMapper.selectByRoleIds(roleIdSet);
			if (!CollectionUtils.isNullOrEmpty(resourceList)) {
				entitySet = Sets.newHashSet(resourceList);
				Map<String, List<ResourceEntity>> data = Maps.newHashMap();
				resourceList.forEach(resource -> {
					String parentId = StringUtil.trim(resource.getParentUuid());
					if (data.containsKey(parentId)) {
						data.get(parentId).add(resource);
					} else {
						data.put(parentId, Lists.newArrayList(resource));
					}
				});
				resourceList.forEach(resource -> {
					if (resource.getType() == 0) {
						topMenu.add(resource);
					}
				});
				topMenu.forEach(resource -> resource.setChildren(recursion(StringUtil.trim(resource.getUuid()),data)));
			}
		}
		userInfo.setResources(entitySet);
		ResourceEntity entity = new ResourceEntity();
		entity.setChildren(topMenu);
		userInfo.setMenu(entity);
	}

	private List<ResourceEntity> recursion(String parentId, Map<String,List<ResourceEntity>> data) {

		List<ResourceEntity> resourceList = data.get(parentId);
		if (!CollectionUtils.isNullOrEmpty(resourceList)) {
			resourceList.forEach(resource -> resource.setChildren(recursion(StringUtil.trim(resource.getUuid()),data)));
		}
		return resourceList;
	}

}
