package com.daishumovie.dao.mapper.smallbronzeadmin;


import com.daishumovie.dao.model.auth.ResourceEntity;
import com.daishumovie.dao.model.auth.ResourceRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ResourceRoleEntityMapper extends GenericMapper<Long, ResourceRoleEntity> {

    List<ResourceRoleEntity> selectByResourceIds(@Param("resourceIds") List<String> resourceIds);

    List<ResourceEntity> selectByRoleIds(@Param("role_ids") Set<Long> roleIds);
}