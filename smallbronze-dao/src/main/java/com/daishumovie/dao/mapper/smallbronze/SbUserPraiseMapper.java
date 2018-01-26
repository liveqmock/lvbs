package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbUserPraise;
import com.daishumovie.dao.model.SbUserPraiseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbUserPraiseMapper {
    long countByExample(SbUserPraiseExample example);

    int deleteByExample(SbUserPraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbUserPraise record);

    int insertSelective(SbUserPraise record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbUserPraise> selectByExample(SbUserPraiseExample example);

    SbUserPraise selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbUserPraise record, @Param("example") SbUserPraiseExample example);

    int updateByExample(@Param("record") SbUserPraise record, @Param("example") SbUserPraiseExample example);

    int updateByPrimaryKeySelective(SbUserPraise record);

    int updateByPrimaryKey(SbUserPraise record);
}