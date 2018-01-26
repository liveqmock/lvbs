package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbUserWatchHistory;
import com.daishumovie.dao.model.SbUserWatchHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbUserWatchHistoryMapper {
    long countByExample(SbUserWatchHistoryExample example);

    int deleteByExample(SbUserWatchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbUserWatchHistory record);

    int insertSelective(SbUserWatchHistory record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbUserWatchHistory> selectByExample(SbUserWatchHistoryExample example);

    SbUserWatchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbUserWatchHistory record, @Param("example") SbUserWatchHistoryExample example);

    int updateByExample(@Param("record") SbUserWatchHistory record, @Param("example") SbUserWatchHistoryExample example);

    int updateByPrimaryKeySelective(SbUserWatchHistory record);

    int updateByPrimaryKey(SbUserWatchHistory record);
}