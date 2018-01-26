package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbMaterialContent;
import com.daishumovie.dao.model.SbMaterialContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbMaterialContentMapper {
    long countByExample(SbMaterialContentExample example);

    int deleteByExample(SbMaterialContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbMaterialContent record);

    int insertSelective(SbMaterialContent record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbMaterialContent> selectByExample(SbMaterialContentExample example);

    SbMaterialContent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbMaterialContent record, @Param("example") SbMaterialContentExample example);

    int updateByExample(@Param("record") SbMaterialContent record, @Param("example") SbMaterialContentExample example);

    int updateByPrimaryKeySelective(SbMaterialContent record);

    int updateByPrimaryKey(SbMaterialContent record);
}