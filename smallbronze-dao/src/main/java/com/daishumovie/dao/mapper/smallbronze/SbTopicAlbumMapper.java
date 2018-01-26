package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.SbTopicAlbum;
import com.daishumovie.dao.model.SbTopicAlbumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbTopicAlbumMapper {
    long countByExample(SbTopicAlbumExample example);

    int deleteByExample(SbTopicAlbumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SbTopicAlbum record);

    int insertSelective(SbTopicAlbum record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<SbTopicAlbum> selectByExample(SbTopicAlbumExample example);

    SbTopicAlbum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SbTopicAlbum record, @Param("example") SbTopicAlbumExample example);

    int updateByExample(@Param("record") SbTopicAlbum record, @Param("example") SbTopicAlbumExample example);

    int updateByPrimaryKeySelective(SbTopicAlbum record);

    int updateByPrimaryKey(SbTopicAlbum record);
}