package com.daishumovie.dao.mapper.smallbronze;


import com.daishumovie.dao.model.UDTjChannelReport;
import com.daishumovie.dao.model.UdMap;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UDTjChannelReportMapper {

    public List<UDTjChannelReport> selectByExample(@Param("id") Integer id, @Param("type") Integer type,
                                                   @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                   @Param("orderBy") String orderBy, @Param("sequence") String sequence, @Param("offset") int offset, @Param("limit") int limit);

    public Long countByExample(@Param("id") Integer id, @Param("type") Integer type,
                               @Param("startTime") Date startTime, @Param("endTime") Date endTime);


    public List<UDTjChannelReport> selectSumByExample(@Param("id") Integer id, @Param("type") Integer type,
                                                    @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                    @Param("orderBy") String orderBy, @Param("sequence") String sequence, @Param("offset") int offset, @Param("limit") int limit);

    public Long countSumByExample(@Param("id") Integer id, @Param("type") Integer type,
                                  @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    public List<UdMap> getChannelUvMap(@Param("ids")List<Integer> ids, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
}