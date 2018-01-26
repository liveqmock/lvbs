package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbMaterialCategoryApp;
import com.daishumovie.dao.model.SbMaterialCategoryAppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbMaterialCategoryAppMapper {
    long countByExample(SbMaterialCategoryAppExample example);

    int deleteByExample(SbMaterialCategoryAppExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbMaterialCategoryApp record);

    int insertSelective(SbMaterialCategoryApp record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbMaterialCategoryApp> selectByExample(SbMaterialCategoryAppExample example);

    SbMaterialCategoryApp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbMaterialCategoryApp record, @Param("example") SbMaterialCategoryAppExample example);

    int updateByExample(@Param("record") SbMaterialCategoryApp record, @Param("example") SbMaterialCategoryAppExample example);

    int updateByPrimaryKeySelective(SbMaterialCategoryApp record);

    int updateByPrimaryKey(SbMaterialCategoryApp record);
}