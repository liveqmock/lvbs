package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbUserComment;
import com.daishumovie.dao.model.SbUserCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbUserCommentMapper {
    long countByExample(SbUserCommentExample example);

    int deleteByExample(SbUserCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbUserComment record);

    int insertSelective(SbUserComment record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbUserComment> selectByExample(SbUserCommentExample example);

    SbUserComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbUserComment record, @Param("example") SbUserCommentExample example);

    int updateByExample(@Param("record") SbUserComment record, @Param("example") SbUserCommentExample example);

    int updateByPrimaryKeySelective(SbUserComment record);

    int updateByPrimaryKey(SbUserComment record);
}