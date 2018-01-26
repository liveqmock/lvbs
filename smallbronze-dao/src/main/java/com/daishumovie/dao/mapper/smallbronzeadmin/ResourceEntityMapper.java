package com.daishumovie.dao.mapper.smallbronzeadmin;

import com.daishumovie.dao.model.auth.ResourceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ResourceEntityMapper extends GenericMapper<Long, ResourceEntity> {

	List<ResourceEntity> queryByRoleId(@Param("roleId") Long roleId);

	List<ResourceEntity> queryByUserName(@Param("userName") String userName);

	List<ResourceEntity> queryTopMenu();

	List<ResourceEntity> queryFunctionMenuByOrigin(@Param("parent_id")String parentId);
}