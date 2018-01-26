package com.daishumovie.admin.service.auth.impl;

import com.daishumovie.admin.service.auth.IAdminRoleService;
import com.daishumovie.dao.mapper.smallbronzeadmin.AdminRoleEntityMapper;
import com.daishumovie.dao.model.auth.AdminRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 管理员-角色操作接口实现类 User: tianrui.lin@renren-inc.com Date: 13-6-6 Time: 下午5:50 To
 * change this template use File | Settings | File Templates.
 */
@Service("adminRoleService")
public class AdminRoleServiceImpl implements IAdminRoleService {

	@Autowired
	private AdminRoleEntityMapper adminRoleEntityMapper;

	@Override
	public AdminRoleEntity save(AdminRoleEntity entity) {
		adminRoleEntityMapper.insert(entity);
		return entity;
	}

	@Override
	public boolean delete(AdminRoleEntity entity) {
		return adminRoleEntityMapper.deleteByCondition(entity) > 0;
	}

	@Override
	public List<AdminRoleEntity> query(Long adminId, Long roleId) {
		AdminRoleEntity condition = new AdminRoleEntity();
		condition.setAdminId(adminId);
		condition.setRoleId(roleId);
		return adminRoleEntityMapper.selectByParam(condition, null, null, null, null, null, null, null);
	}
}
