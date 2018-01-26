package com.daishumovie.admin.service.auth;


import com.daishumovie.dao.model.auth.RoleEntity;
import com.daishumovie.utils.Page;

import java.util.List;
import java.util.Map;

/**
 * 角色服务service 
 */
public interface IRoleService {

	/**
	 * 通过Id获得某个角色实体 by tianrui.lin@renren-inc.com
	 * 返回包括DepartmentEntity
	 * @param id
	 * @return
	 */
	RoleEntity get(long id);

	List<RoleEntity> queryRoleById(int id);

	RoleEntity addRole(RoleEntity roleEntity);

	boolean updateRole(RoleEntity roleEntity);

	void deleteRole(RoleEntity roleEntity);

	List<RoleEntity> queryRoleByUser(String userName);
	
	Page<RoleEntity> queryByPage(Page<RoleEntity> page, Map conditions);
}
