package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbUploadVideos;
import com.daishumovie.dao.model.SbUploadVideosExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbUploadVideosMapper {
    long countByExample(SbUploadVideosExample example);

    int deleteByExample(SbUploadVideosExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbUploadVideos record);

    int insertSelective(SbUploadVideos record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbUploadVideos> selectByExample(SbUploadVideosExample example);

    SbUploadVideos selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbUploadVideos record, @Param("example") SbUploadVideosExample example);

    int updateByExample(@Param("record") SbUploadVideos record, @Param("example") SbUploadVideosExample example);

    int updateByPrimaryKeySelective(SbUploadVideos record);

    int updateByPrimaryKey(SbUploadVideos record);
}