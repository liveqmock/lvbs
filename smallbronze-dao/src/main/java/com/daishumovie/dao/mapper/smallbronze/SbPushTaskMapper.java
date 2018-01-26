package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbPushTask;
import com.daishumovie.dao.model.SbPushTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbPushTaskMapper {
    long countByExample(SbPushTaskExample example);

    int deleteByExample(SbPushTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbPushTask record);

    int insertSelective(SbPushTask record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbPushTask> selectByExample(SbPushTaskExample example);

    SbPushTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbPushTask record, @Param("example") SbPushTaskExample example);

    int updateByExample(@Param("record") SbPushTask record, @Param("example") SbPushTaskExample example);

    int updateByPrimaryKeySelective(SbPushTask record);

    int updateByPrimaryKey(SbPushTask record);
}