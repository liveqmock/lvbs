package com.daishumovie.dao.mapper.smallbronzeadmin;

import com.daishumovie.dao.model.auth.OtButtonAuthority;
import com.daishumovie.dao.model.auth.OtButtonAuthorityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OtButtonAuthorityMapper {
    long countByExample(OtButtonAuthorityExample example);

    int deleteByExample(OtButtonAuthorityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OtButtonAuthority record);

    int insertSelective(OtButtonAuthority record);

    List<OtButtonAuthority> selectByExample(OtButtonAuthorityExample example);

    OtButtonAuthority selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OtButtonAuthority record, @Param("example") OtButtonAuthorityExample example);

    int updateByExample(@Param("record") OtButtonAuthority record, @Param("example") OtButtonAuthorityExample example);

    int updateByPrimaryKeySelective(OtButtonAuthority record);

    int updateByPrimaryKey(OtButtonAuthority record);
}