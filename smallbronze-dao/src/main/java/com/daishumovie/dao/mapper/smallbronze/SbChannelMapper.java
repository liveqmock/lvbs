package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbChannel;
import com.daishumovie.dao.model.SbChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbChannelMapper {
    long countByExample(SbChannelExample example);

    int deleteByExample(SbChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbChannel record);

    int insertSelective(SbChannel record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbChannel> selectByExample(SbChannelExample example);

    SbChannel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbChannel record, @Param("example") SbChannelExample example);

    int updateByExample(@Param("record") SbChannel record, @Param("example") SbChannelExample example);

    int updateByPrimaryKeySelective(SbChannel record);

    int updateByPrimaryKey(SbChannel record);
}