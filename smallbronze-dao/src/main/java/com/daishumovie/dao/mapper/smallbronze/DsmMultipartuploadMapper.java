package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmMultipartupload;
import com.daishumovie.dao.model.DsmMultipartuploadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmMultipartuploadMapper {
    long countByExample(DsmMultipartuploadExample example);

    int deleteByExample(DsmMultipartuploadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DsmMultipartupload record);

    int insertSelective(DsmMultipartupload record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmMultipartupload> selectByExample(DsmMultipartuploadExample example);

    DsmMultipartupload selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DsmMultipartupload record, @Param("example") DsmMultipartuploadExample example);

    int updateByExample(@Param("record") DsmMultipartupload record, @Param("example") DsmMultipartuploadExample example);

    int updateByPrimaryKeySelective(DsmMultipartupload record);

    int updateByPrimaryKey(DsmMultipartupload record);
}