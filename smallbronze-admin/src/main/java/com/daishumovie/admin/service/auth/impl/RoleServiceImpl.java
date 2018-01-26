package com.daishumovie.admin.service.auth.impl;


import com.daishumovie.admin.service.auth.IRoleService;
import com.daishumovie.dao.mapper.smallbronzeadmin.ResourceRoleEntityMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.RoleEntityMapper;
import com.daishumovie.dao.model.auth.ResourceRoleEntity;
import com.daishumovie.dao.model.auth.RoleEntity;
import com.daishumovie.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private RoleEntityMapper roleEntityMapper;

	@Autowired
	private ResourceRoleEntityMapper resourceRoleEntityMapper;

	@Override
	public RoleEntity get(long id) {
		return roleEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<RoleEntity> queryRoleById(int id) {
		RoleEntity roleEntity = roleEntityMapper.selectByPrimaryKey(id + 0L);
		List<RoleEntity> list = new ArrayList<RoleEntity>();
		if (roleEntity != null) {
			list.add(roleEntity);
		}
		return list;
	}
	@Override
	public RoleEntity addRole(RoleEntity roleEntity) {
		roleEntityMapper.insert(roleEntity);
		return roleEntity;
	}

	@Override
	public boolean updateRole(RoleEntity roleEntity) {
		return roleEntityMapper.updateByPrimaryKeySelective(roleEntity) > 0;
	}

	@Override
	public void deleteRole(RoleEntity roleEntity) {
		// 需要同时删除 管理员角色表
		ResourceRoleEntity condition = new ResourceRoleEntity();
		condition.setRoleId(roleEntity.getId());
		resourceRoleEntityMapper.deleteByCondition(condition);
		roleEntityMapper.deleteByPrimaryKey(roleEntity.getId());
	}

	@Override
	public List<RoleEntity> queryRoleByUser(String userName) {
		return roleEntityMapper.queryRoleByUser(userName);
	}

	@Override
	public Page<RoleEntity> queryByPage(Page<RoleEntity> page, Map conditions) {
		RoleEntity condition = new RoleEntity();
		if (conditions.get("name") != null)
			condition.setName(conditions.get("name").toString());
		if (conditions.get("type") != null)
			condition.setType(Integer.valueOf(conditions.get("type").toString()));
		if (conditions.get("status") != null)
			condition.setStatus(Integer.valueOf(conditions.get("status").toString()));

		int resultCount = roleEntityMapper.selectResultCountByParam(condition, null, null, null);
		page.setTotal(resultCount);
		if (page.getTotal() > 0) {
			Integer start;
			Integer offset;
			if (page.getPageSize() == 0) {
				start = 0;
				offset = page.getTotal();
			} else {
				start = page.getFromIndex();
				offset = page.getPageSize();
			}

			List<RoleEntity> items = roleEntityMapper.selectByParam(condition, null, null, start, offset, null, null, null);
			page.setItems(items);
		} else
			page.setItems(new ArrayList<>(0));
		return page;
	}
}
