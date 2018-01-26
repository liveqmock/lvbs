package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbChannelHot;
import com.daishumovie.dao.model.SbChannelHotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbChannelHotMapper {
    long countByExample(SbChannelHotExample example);

    int deleteByExample(SbChannelHotExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbChannelHot record);

    int insertSelective(SbChannelHot record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbChannelHot> selectByExample(SbChannelHotExample example);

    SbChannelHot selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbChannelHot record, @Param("example") SbChannelHotExample example);

    int updateByExample(@Param("record") SbChannelHot record, @Param("example") SbChannelHotExample example);

    int updateByPrimaryKeySelective(SbChannelHot record);

    int updateByPrimaryKey(SbChannelHot record);
}