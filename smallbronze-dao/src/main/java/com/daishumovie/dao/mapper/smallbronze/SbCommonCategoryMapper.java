package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbCommonCategory;
import com.daishumovie.dao.model.SbCommonCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbCommonCategoryMapper {
    long countByExample(SbCommonCategoryExample example);

    int deleteByExample(SbCommonCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbCommonCategory record);

    int insertSelective(SbCommonCategory record);

    List<SbCommonCategory> selectByExample(SbCommonCategoryExample example);

    SbCommonCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbCommonCategory record, @Param("example") SbCommonCategoryExample example);

    int updateByExample(@Param("record") SbCommonCategory record, @Param("example") SbCommonCategoryExample example);

    int updateByPrimaryKeySelective(SbCommonCategory record);

    int updateByPrimaryKey(SbCommonCategory record);
}