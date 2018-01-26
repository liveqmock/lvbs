package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmMultipartuploadPart;
import com.daishumovie.dao.model.DsmMultipartuploadPartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmMultipartuploadPartMapper {
    long countByExample(DsmMultipartuploadPartExample example);

    int deleteByExample(DsmMultipartuploadPartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DsmMultipartuploadPart record);

    int insertSelective(DsmMultipartuploadPart record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmMultipartuploadPart> selectByExample(DsmMultipartuploadPartExample example);

    DsmMultipartuploadPart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DsmMultipartuploadPart record, @Param("example") DsmMultipartuploadPartExample example);

    int updateByExample(@Param("record") DsmMultipartuploadPart record, @Param("example") DsmMultipartuploadPartExample example);

    int updateByPrimaryKeySelective(DsmMultipartuploadPart record);

    int updateByPrimaryKey(DsmMultipartuploadPart record);
}