package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmPushToken;
import com.daishumovie.dao.model.DsmPushTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmPushTokenMapper {
    long countByExample(DsmPushTokenExample example);

    int deleteByExample(DsmPushTokenExample example);

    int deleteByPrimaryKey(String did);

    int insert(DsmPushToken record);

    int insertSelective(DsmPushToken record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmPushToken> selectByExample(DsmPushTokenExample example);

    DsmPushToken selectByPrimaryKey(String did);

    int updateByExampleSelective(@Param("record") DsmPushToken record, @Param("example") DsmPushTokenExample example);

    int updateByExample(@Param("record") DsmPushToken record, @Param("example") DsmPushTokenExample example);

    int updateByPrimaryKeySelective(DsmPushToken record);

    int updateByPrimaryKey(DsmPushToken record);
}