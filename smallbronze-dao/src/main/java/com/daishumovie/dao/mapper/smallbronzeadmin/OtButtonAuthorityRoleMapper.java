package com.daishumovie.dao.mapper.smallbronzeadmin;

import com.daishumovie.dao.model.auth.OtButtonAuthorityRole;
import com.daishumovie.dao.model.auth.OtButtonAuthorityRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OtButtonAuthorityRoleMapper {
    long countByExample(OtButtonAuthorityRoleExample example);

    int deleteByExample(OtButtonAuthorityRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OtButtonAuthorityRole record);

    int insertSelective(OtButtonAuthorityRole record);

    List<OtButtonAuthorityRole> selectByExample(OtButtonAuthorityRoleExample example);

    OtButtonAuthorityRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OtButtonAuthorityRole record, @Param("example") OtButtonAuthorityRoleExample example);

    int updateByExample(@Param("record") OtButtonAuthorityRole record, @Param("example") OtButtonAuthorityRoleExample example);

    int updateByPrimaryKeySelective(OtButtonAuthorityRole record);

    int updateByPrimaryKey(OtButtonAuthorityRole record);
}