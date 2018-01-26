package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbUserReport;
import com.daishumovie.dao.model.SbUserReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbUserReportMapper {
    long countByExample(SbUserReportExample example);

    int deleteByExample(SbUserReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbUserReport record);

    int insertSelective(SbUserReport record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbUserReport> selectByExample(SbUserReportExample example);

    SbUserReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbUserReport record, @Param("example") SbUserReportExample example);

    int updateByExample(@Param("record") SbUserReport record, @Param("example") SbUserReportExample example);

    int updateByPrimaryKeySelective(SbUserReport record);

    int updateByPrimaryKey(SbUserReport record);
}