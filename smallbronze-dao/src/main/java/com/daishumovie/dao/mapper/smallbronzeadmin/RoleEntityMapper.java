package com.daishumovie.dao.mapper.smallbronzeadmin;

import com.daishumovie.dao.model.auth.RoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleEntityMapper extends GenericMapper<Long, RoleEntity> {

	List<RoleEntity> queryRoleByUser(@Param("userName") String userName);
}