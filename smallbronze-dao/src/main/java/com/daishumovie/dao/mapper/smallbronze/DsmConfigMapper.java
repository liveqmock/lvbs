package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmConfig;
import com.daishumovie.dao.model.DsmConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmConfigMapper {
    long countByExample(DsmConfigExample example);

    int deleteByExample(DsmConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DsmConfig record);

    int insertSelective(DsmConfig record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmConfig> selectByExample(DsmConfigExample example);

    DsmConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DsmConfig record, @Param("example") DsmConfigExample example);

    int updateByExample(@Param("record") DsmConfig record, @Param("example") DsmConfigExample example);

    int updateByPrimaryKeySelective(DsmConfig record);

    int updateByPrimaryKey(DsmConfig record);
}