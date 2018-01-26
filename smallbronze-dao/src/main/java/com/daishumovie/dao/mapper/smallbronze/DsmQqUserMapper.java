package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmQqUser;
import com.daishumovie.dao.model.DsmQqUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmQqUserMapper {
    long countByExample(DsmQqUserExample example);

    int deleteByExample(DsmQqUserExample example);

    int deleteByPrimaryKey(String openid);

    int insert(DsmQqUser record);

    int insertSelective(DsmQqUser record);

    List<DsmQqUser> selectByExample(DsmQqUserExample example);

    DsmQqUser selectByPrimaryKey(String openid);

    int updateByExampleSelective(@Param("record") DsmQqUser record, @Param("example") DsmQqUserExample example);

    int updateByExample(@Param("record") DsmQqUser record, @Param("example") DsmQqUserExample example);

    int updateByPrimaryKeySelective(DsmQqUser record);

    int updateByPrimaryKey(DsmQqUser record);
}