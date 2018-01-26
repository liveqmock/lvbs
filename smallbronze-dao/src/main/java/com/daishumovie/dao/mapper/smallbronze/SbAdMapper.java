package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbAd;
import com.daishumovie.dao.model.SbAdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbAdMapper {
    long countByExample(SbAdExample example);

    int deleteByExample(SbAdExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbAd record);

    int insertSelective(SbAd record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbAd> selectByExample(SbAdExample example);

    SbAd selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbAd record, @Param("example") SbAdExample example);

    int updateByExample(@Param("record") SbAd record, @Param("example") SbAdExample example);

    int updateByPrimaryKeySelective(SbAd record);

    int updateByPrimaryKey(SbAd record);
}