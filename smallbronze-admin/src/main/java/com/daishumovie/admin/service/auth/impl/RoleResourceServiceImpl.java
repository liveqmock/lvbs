package com.daishumovie.admin.service.auth.impl;

import com.daishumovie.admin.service.auth.IRoleResourceService;
import com.daishumovie.dao.mapper.smallbronzeadmin.ResourceRoleEntityMapper;
import com.daishumovie.dao.model.auth.ResourceRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 角色服务service 实现类 User:yuansheng.gao@renren-inc.com Date: 13-5-30 Time: 下午4:50
 */
@Service("roleResourceService")
public class RoleResourceServiceImpl implements IRoleResourceService {
	@Autowired
	private ResourceRoleEntityMapper resourceRoleEntityMapper;

	@Override
	public ResourceRoleEntity addRoleResource(ResourceRoleEntity resourceRoleEntity) {
		resourceRoleEntityMapper.insert(resourceRoleEntity);
		return resourceRoleEntity;
	}

	@Override
	public boolean deleteRoleResourceByRoleId(Long roleId) {
		ResourceRoleEntity condition = new ResourceRoleEntity();
		condition.setRoleId(roleId);
		return resourceRoleEntityMapper.deleteByCondition(condition) > 0;
	}

	@Override
	public boolean deleteRoleResourceByResourceId(String resourceUuid) {
		ResourceRoleEntity condition = new ResourceRoleEntity();
		condition.setResourceUuid(resourceUuid);
		return resourceRoleEntityMapper.deleteByCondition(condition) > 0;
	}

	@Override
	public boolean deleteRoleResourceByResourceId(Long roleId, String resourceUuid) {
		ResourceRoleEntity condition = new ResourceRoleEntity();
		condition.setRoleId(roleId);
		condition.setResourceUuid(resourceUuid);
		return resourceRoleEntityMapper.deleteByCondition(condition) > 0;
	}

	@Override
	public boolean deleteRoleResource(ResourceRoleEntity resourceRoleEntity) {
		return resourceRoleEntityMapper.deleteByCondition(resourceRoleEntity) > 0;
	}

	@Override
	public List<ResourceRoleEntity> queryRoleResource(Long roleId) {
		ResourceRoleEntity condition = new ResourceRoleEntity();
		condition.setRoleId(roleId);
		return resourceRoleEntityMapper.selectByParam(condition, null, null, null, null, null, null, null);
	}

}
