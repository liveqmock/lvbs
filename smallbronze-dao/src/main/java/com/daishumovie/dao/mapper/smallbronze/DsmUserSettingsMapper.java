package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmUserSettings;
import com.daishumovie.dao.model.DsmUserSettingsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmUserSettingsMapper {
    long countByExample(DsmUserSettingsExample example);

    int deleteByExample(DsmUserSettingsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DsmUserSettings record);

    int insertSelective(DsmUserSettings record);

    int selfPlusMinus(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("example") Object example);

    int selfPlusMinusByPrimaryKey(@Param("columnName") String columnName, @Param("operator") String operator, @Param("count") int count, @Param("id") int id);

    List<DsmUserSettings> selectByExample(DsmUserSettingsExample example);

    DsmUserSettings selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DsmUserSettings record, @Param("example") DsmUserSettingsExample example);

    int updateByExample(@Param("record") DsmUserSettings record, @Param("example") DsmUserSettingsExample example);

    int updateByPrimaryKeySelective(DsmUserSettings record);

    int updateByPrimaryKey(DsmUserSettings record);
}