package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbUserDislike;
import com.daishumovie.dao.model.SbUserDislikeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbUserDislikeMapper {
    long countByExample(SbUserDislikeExample example);

    int deleteByExample(SbUserDislikeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbUserDislike record);

    int insertSelective(SbUserDislike record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbUserDislike> selectByExample(SbUserDislikeExample example);

    SbUserDislike selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbUserDislike record, @Param("example") SbUserDislikeExample example);

    int updateByExample(@Param("record") SbUserDislike record, @Param("example") SbUserDislikeExample example);

    int updateByPrimaryKeySelective(SbUserDislike record);

    int updateByPrimaryKey(SbUserDislike record);
}