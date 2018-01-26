package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbUserSignUp;
import com.daishumovie.dao.model.SbUserSignUpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbUserSignUpMapper {
    long countByExample(SbUserSignUpExample example);

    int deleteByExample(SbUserSignUpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbUserSignUp record);

    int insertSelective(SbUserSignUp record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbUserSignUp> selectByExample(SbUserSignUpExample example);

    SbUserSignUp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbUserSignUp record, @Param("example") SbUserSignUpExample example);

    int updateByExample(@Param("record") SbUserSignUp record, @Param("example") SbUserSignUpExample example);

    int updateByPrimaryKeySelective(SbUserSignUp record);

    int updateByPrimaryKey(SbUserSignUp record);
}