package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmUserShareHistory;
import com.daishumovie.dao.model.DsmUserShareHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmUserShareHistoryMapper {
    long countByExample(DsmUserShareHistoryExample example);

    int deleteByExample(DsmUserShareHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DsmUserShareHistory record);

    int insertSelective(DsmUserShareHistory record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmUserShareHistory> selectByExample(DsmUserShareHistoryExample example);

    DsmUserShareHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DsmUserShareHistory record, @Param("example") DsmUserShareHistoryExample example);

    int updateByExample(@Param("record") DsmUserShareHistory record, @Param("example") DsmUserShareHistoryExample example);

    int updateByPrimaryKeySelective(DsmUserShareHistory record);

    int updateByPrimaryKey(DsmUserShareHistory record);
}