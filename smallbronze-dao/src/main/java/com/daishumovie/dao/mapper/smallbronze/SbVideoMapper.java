package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.dao.model.SbVideoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbVideoMapper {
    long countByExample(SbVideoExample example);

    int deleteByExample(SbVideoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbVideo record);

    int insertSelective(SbVideo record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbVideo> selectByExample(SbVideoExample example);

    SbVideo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbVideo record, @Param("example") SbVideoExample example);

    int updateByExample(@Param("record") SbVideo record, @Param("example") SbVideoExample example);

    int updateByPrimaryKeySelective(SbVideo record);

    int updateByPrimaryKey(SbVideo record);
}