package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.dao.model.SbActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbActivityMapper {
    long countByExample(SbActivityExample example);

    int deleteByExample(SbActivityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbActivity record);

    int insertSelective(SbActivity record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbActivity> selectByExampleWithBLOBs(SbActivityExample example);

    List<SbActivity> selectByExample(SbActivityExample example);

    SbActivity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbActivity record, @Param("example") SbActivityExample example);

    int updateByExampleWithBLOBs(@Param("record") SbActivity record, @Param("example") SbActivityExample example);

    int updateByExample(@Param("record") SbActivity record, @Param("example") SbActivityExample example);

    int updateByPrimaryKeySelective(SbActivity record);

    int updateByPrimaryKeyWithBLOBs(SbActivity record);

    int updateByPrimaryKey(SbActivity record);
}