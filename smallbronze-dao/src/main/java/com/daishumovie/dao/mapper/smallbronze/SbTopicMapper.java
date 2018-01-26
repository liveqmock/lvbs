package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbTopicMapper {
    long countByExample(SbTopicExample example);

    int deleteByExample(SbTopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbTopic record);

    int insertSelective(SbTopic record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbTopic> selectByExample(SbTopicExample example);

    SbTopic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbTopic record, @Param("example") SbTopicExample example);

    int updateByExample(@Param("record") SbTopic record, @Param("example") SbTopicExample example);

    int updateByPrimaryKeySelective(SbTopic record);

    int updateByPrimaryKey(SbTopic record);
}