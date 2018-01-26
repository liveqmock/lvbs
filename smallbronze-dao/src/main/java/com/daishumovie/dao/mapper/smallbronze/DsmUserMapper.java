package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.DsmUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmUserMapper {
    long countByExample(DsmUserExample example);

    int deleteByExample(DsmUserExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(DsmUser record);

    int insertSelective(DsmUser record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("uid") int id);

    List<DsmUser> selectByExample(DsmUserExample example);

    DsmUser selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") DsmUser record, @Param("example") DsmUserExample example);

    int updateByExample(@Param("record") DsmUser record, @Param("example") DsmUserExample example);

    int updateByPrimaryKeySelective(DsmUser record);

    int updateByPrimaryKey(DsmUser record);
}