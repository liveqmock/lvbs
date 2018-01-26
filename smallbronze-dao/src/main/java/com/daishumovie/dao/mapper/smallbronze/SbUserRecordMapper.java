package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbUserRecord;
import com.daishumovie.dao.model.SbUserRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbUserRecordMapper {
    long countByExample(SbUserRecordExample example);

    int deleteByExample(SbUserRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbUserRecord record);

    int insertSelective(SbUserRecord record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbUserRecord> selectByExample(SbUserRecordExample example);

    SbUserRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbUserRecord record, @Param("example") SbUserRecordExample example);

    int updateByExample(@Param("record") SbUserRecord record, @Param("example") SbUserRecordExample example);

    int updateByPrimaryKeySelective(SbUserRecord record);

    int updateByPrimaryKey(SbUserRecord record);
}