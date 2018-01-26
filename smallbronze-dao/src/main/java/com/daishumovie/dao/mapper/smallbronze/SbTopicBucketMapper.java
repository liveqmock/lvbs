package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbTopicBucket;
import com.daishumovie.dao.model.SbTopicBucketExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbTopicBucketMapper {
    long countByExample(SbTopicBucketExample example);

    int deleteByExample(SbTopicBucketExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbTopicBucket record);

    int insertSelective(SbTopicBucket record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbTopicBucket> selectByExample(SbTopicBucketExample example);

    SbTopicBucket selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbTopicBucket record, @Param("example") SbTopicBucketExample example);

    int updateByExample(@Param("record") SbTopicBucket record, @Param("example") SbTopicBucketExample example);

    int updateByPrimaryKeySelective(SbTopicBucket record);

    int updateByPrimaryKey(SbTopicBucket record);
}