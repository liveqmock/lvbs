package com.daishumovie.dao.mapper.smallbronze;


import com.daishumovie.dao.model.UDTjVideoReport;
import com.daishumovie.dao.model.UdMap;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UDTjVideoReportMapper {

    public List<UDTjVideoReport> selectByExample(@Param("ids")List<Integer> ids,@Param("type")Integer type,
                                                    @Param("startTime")Date startTime, @Param("endTime")Date endTime,
                                                    @Param("orderBy")String orderBy, @Param("sequence")String sequence,@Param("offset") int offset, @Param("limit") int limit);

    public Long countByExample(@Param("ids")List<Integer> ids,@Param("type")Integer type,
                                                       @Param("startTime")Date startTime, @Param("endTime")Date endTime);


    public List<UDTjVideoReport> selectSumByExample(@Param("ids")List<Integer> ids,@Param("type")Integer type,
                                                 @Param("startTime")Date startTime, @Param("endTime")Date endTime,
                                                 @Param("orderBy")String orderBy,@Param("sequence")String sequence, @Param("offset") int offset, @Param("limit") int limit);

    public Long countSumByExample(@Param("ids")List<Integer> ids,@Param("type")Integer type,
                                                    @Param("startTime")Date startTime, @Param("endTime")Date endTime);


    public List<UdMap> getVideoUvMap(@Param("ids")List<Integer> ids, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
}