package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmAppVersion;
import com.daishumovie.dao.model.DsmAppVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmAppVersionMapper {
    long countByExample(DsmAppVersionExample example);

    int deleteByExample(DsmAppVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DsmAppVersion record);

    int insertSelective(DsmAppVersion record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmAppVersion> selectByExample(DsmAppVersionExample example);

    DsmAppVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DsmAppVersion record, @Param("example") DsmAppVersionExample example);

    int updateByExample(@Param("record") DsmAppVersion record, @Param("example") DsmAppVersionExample example);

    int updateByPrimaryKeySelective(DsmAppVersion record);

    int updateByPrimaryKey(DsmAppVersion record);
}