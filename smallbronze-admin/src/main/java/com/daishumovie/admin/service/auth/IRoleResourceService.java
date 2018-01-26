package com.daishumovie.admin.service.auth;




import com.daishumovie.dao.model.auth.ResourceRoleEntity;

import java.util.List;

/**
 * 角色服务service 
 */
public interface IRoleResourceService {

	public ResourceRoleEntity addRoleResource(ResourceRoleEntity resourceRoleEntity);

	public boolean deleteRoleResource(ResourceRoleEntity resourceRoleEntity);

	public boolean deleteRoleResourceByRoleId(Long roleId);

	public boolean deleteRoleResourceByResourceId(String resourceUuid);

	public boolean deleteRoleResourceByResourceId(Long roleId, String resourceUuid);

	public List<ResourceRoleEntity> queryRoleResource(Long roleId);

}
