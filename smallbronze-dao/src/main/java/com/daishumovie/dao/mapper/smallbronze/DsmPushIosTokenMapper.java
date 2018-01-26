package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmPushIosToken;
import com.daishumovie.dao.model.DsmPushIosTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmPushIosTokenMapper {
    long countByExample(DsmPushIosTokenExample example);

    int deleteByExample(DsmPushIosTokenExample example);

    int deleteByPrimaryKey(String token);

    int insert(DsmPushIosToken record);

    int insertSelective(DsmPushIosToken record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmPushIosToken> selectByExample(DsmPushIosTokenExample example);

    DsmPushIosToken selectByPrimaryKey(String token);

    int updateByExampleSelective(@Param("record") DsmPushIosToken record, @Param("example") DsmPushIosTokenExample example);

    int updateByExample(@Param("record") DsmPushIosToken record, @Param("example") DsmPushIosTokenExample example);

    int updateByPrimaryKeySelective(DsmPushIosToken record);

    int updateByPrimaryKey(DsmPushIosToken record);
}