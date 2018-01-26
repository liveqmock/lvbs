package com.daishumovie.dao.mapper.smallbronzeadmin;

import com.daishumovie.dao.model.auth.OtLog;
import com.daishumovie.dao.model.auth.OtLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OtLogMapper {
    long countByExample(OtLogExample example);

    int deleteByExample(OtLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OtLog record);

    int insertSelective(OtLog record);

    List<OtLog> selectByExample(OtLogExample example);

    OtLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OtLog record, @Param("example") OtLogExample example);

    int updateByExample(@Param("record") OtLog record, @Param("example") OtLogExample example);

    int updateByPrimaryKeySelective(OtLog record);

    int updateByPrimaryKey(OtLog record);
}