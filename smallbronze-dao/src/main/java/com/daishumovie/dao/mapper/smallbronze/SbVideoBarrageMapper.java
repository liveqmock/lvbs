package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbVideoBarrage;
import com.daishumovie.dao.model.SbVideoBarrageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbVideoBarrageMapper {
    long countByExample(SbVideoBarrageExample example);

    int deleteByExample(SbVideoBarrageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbVideoBarrage record);

    int insertSelective(SbVideoBarrage record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbVideoBarrage> selectByExample(SbVideoBarrageExample example);

    SbVideoBarrage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbVideoBarrage record, @Param("example") SbVideoBarrageExample example);

    int updateByExample(@Param("record") SbVideoBarrage record, @Param("example") SbVideoBarrageExample example);

    int updateByPrimaryKeySelective(SbVideoBarrage record);

    int updateByPrimaryKey(SbVideoBarrage record);
}