package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmWeiboUser;
import com.daishumovie.dao.model.DsmWeiboUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmWeiboUserMapper {
    long countByExample(DsmWeiboUserExample example);

    int deleteByExample(DsmWeiboUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(DsmWeiboUser record);

    int insertSelective(DsmWeiboUser record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmWeiboUser> selectByExample(DsmWeiboUserExample example);

    DsmWeiboUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DsmWeiboUser record, @Param("example") DsmWeiboUserExample example);

    int updateByExample(@Param("record") DsmWeiboUser record, @Param("example") DsmWeiboUserExample example);

    int updateByPrimaryKeySelective(DsmWeiboUser record);

    int updateByPrimaryKey(DsmWeiboUser record);
}