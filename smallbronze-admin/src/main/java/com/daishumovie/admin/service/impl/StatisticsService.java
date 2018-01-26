package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.dto.ChannelReportDto;
import com.daishumovie.admin.dto.VideoReportDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.admin.service.IStatisticsService;
import com.daishumovie.admin.service.ITopicService;
import com.daishumovie.base.enums.db.StatisticsType;
import com.daishumovie.dao.mapper.smallbronze.UDTjChannelReportMapper;
import com.daishumovie.dao.mapper.smallbronze.UDTjVideoReportMapper;
import com.daishumovie.dao.model.UDTjChannelReport;
import com.daishumovie.dao.model.UDTjVideoReport;
import com.daishumovie.dao.model.UdMap;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yang on 2017/10/24.
 */
@Service
public class StatisticsService implements IStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsService.class);

    @Autowired
    private UDTjVideoReportMapper videoReportMapper;
    @Autowired
    private UDTjChannelReportMapper channelReportMapper;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private ITopicService topicService;
    @Override
    public ReturnDto<VideoReportDto> videopaginate(ParamDto param, Integer type,Integer id, String name, String startTime, String endTime) {
        try {
            List<VideoReportDto> dtoList = Lists.newArrayList();

            Date start = DateUtil.todayZeroClock(startTime);
            Date end = DateUtil.tonight(endTime);
            List<Integer> topicIds = null;
            if (StringUtil.isNotEmpty(name)){
                topicIds = topicService.getTopicIds(name);
            }
            if (StringUtil.isNotEmpty(name) && CollectionUtils.isNullOrEmpty(topicIds)){
                Page<VideoReportDto> page = param.page();
                page.setTotal(0);
                page.setItems(dtoList);
                return new ReturnDto<>(page);
            }
            if (id != null){
                topicIds = Lists.newArrayList();
                topicIds.add(id);
            }
            Long total = videoReportMapper.countByExample(topicIds,type,start,end);

            if (total > 0) {
                List<UDTjVideoReport> dayReports = videoReportMapper.selectByExample(topicIds,type,start,end,param.getSort_name(),param.getSort_order().name(),param.offset(),param.limit());
                if (!CollectionUtils.isNullOrEmpty(dayReports)) {
                    Set<Integer> channelIdSet = Sets.newHashSet();
                    Set<Integer> topicIdSet = Sets.newHashSet();
                    dayReports.forEach(report -> {
                        VideoReportDto dto = new VideoReportDto();
                        BeanUtils.copyProperties(report, dto);
                        if (null != report.getChannel_id()) {
                            channelIdSet.add(report.getChannel_id());
                        }
                        topicIdSet.add(report.getVideo_id());
                        dtoList.add(dto);
                    });
                    Map<Integer, String> channelMap = channelService.channelMap(channelIdSet);
                    Map<Integer, String> videoMap = topicService.getVideoMap(topicIdSet);
                    dtoList.forEach(dto -> {
                        dto.setChannelName(channelMap.get(dto.getChannel_id()));
                        dto.setName(videoMap.get(dto.getVideo_id()));
                        dto.setT_f(StatisticsType.HOUR.getCode()==type.intValue() ? DateUtil.YMDHH.format(dto.getT()) : DateUtil.ymd.format(dto.getT()));
                    });
                }
            }
            Page<VideoReportDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("StatisticsService videopaginate exception ===>\r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    public ReturnDto<VideoReportDto> videoCollpaginate(ParamDto param, Integer type, Integer id, String name, String startTime, String endTime) {
        try {
            List<VideoReportDto> dtoList = Lists.newArrayList();
            List<Integer> topicIds = null;
            if (StringUtil.isNotEmpty(name)){
                topicIds = topicService.getTopicIds(name);
            }
            if (StringUtil.isNotEmpty(name) && CollectionUtils.isNullOrEmpty(topicIds)){
                Page<VideoReportDto> page = param.page();
                page.setTotal(0);
                page.setItems(dtoList);
                return new ReturnDto<>(page);
            }
            if (id != null){
                topicIds = Lists.newArrayList();
                topicIds.add(id);
            }
            Date start = DateUtil.todayZeroClock(startTime);
            Date end = DateUtil.tonight(endTime);
            Long total = videoReportMapper.countSumByExample(topicIds,type,start,end);

            if (total > 0) {
                List<UDTjVideoReport> dayReports = videoReportMapper.selectSumByExample(topicIds,type,start,
                        end,param.getSort_name(),param.getSort_order().name(),param.offset(),param.limit());
                if (!CollectionUtils.isNullOrEmpty(dayReports)) {
                    Set<Integer> channelIdSet = Sets.newHashSet();
                    Set<Integer> videoIdSet = Sets.newHashSet();
                    dayReports.forEach(report -> {
                        VideoReportDto dto = new VideoReportDto();
                        BeanUtils.copyProperties(report, dto);
                        if (null != report.getChannel_id()) {
                            channelIdSet.add(report.getChannel_id());
                        }
                        videoIdSet.add(report.getVideo_id());
                        dtoList.add(dto);
                    });
                    Map<Integer, String> channelMap = channelService.channelMap(channelIdSet);
                    Map<Integer, String> videoMap = topicService.getVideoMap(videoIdSet);
                    Map<Integer,Integer> udmap = Maps.newHashMap();
                    List<UdMap> mapList = videoReportMapper.getVideoUvMap(new ArrayList<>(videoIdSet),start,end);
                    if (!CollectionUtils.isNullOrEmpty(mapList)){
                        for (UdMap udMap : mapList){
                            if (!udmap.containsKey(udMap.getKey()))
                                udmap.put(udMap.getKey(),udMap.getValue());
                        }
                    }

                    dtoList.forEach(dto -> {
                        dto.setChannelName(channelMap.get(dto.getChannel_id()));
                        dto.setName(videoMap.get(dto.getVideo_id()));
                        dto.setT_f(startTime.concat("~").concat(endTime));
                        dto.setPlay_uv( udmap.get(dto.getVideo_id()));
                    });
                }
            }
            Page<VideoReportDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("StatisticsService videopaginate exception ===>\r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }


    @Override
    public ReturnDto<ChannelReportDto> channelpaginate(ParamDto param, Integer type, Integer id, String startTime, String endTime) {
        try {
            Date start = DateUtil.todayZeroClock(startTime);
            Date end = DateUtil.tonight(endTime);
            Long total = channelReportMapper.countByExample(id,type,start,end);
            List<ChannelReportDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                List<UDTjChannelReport> hourReports = channelReportMapper.selectByExample(id,type,start,end,param.getSort_name(),param.getSort_order().name(),param.offset(),param.limit());
                if (!CollectionUtils.isNullOrEmpty(hourReports)) {
                    Set<Integer> channelIdSet = Sets.newHashSet();
                    hourReports.forEach(report -> {
                        ChannelReportDto dto = new ChannelReportDto();
                        BeanUtils.copyProperties(report, dto);
                        if (null != report.getChannel_id()) {
                            channelIdSet.add(report.getChannel_id());
                        }
                        dtoList.add(dto);
                    });
                    Map<Integer, String> channelMap = channelService.channelMap(channelIdSet);
                    dtoList.forEach(dto -> {
                        dto.setName(channelMap.get(dto.getChannel_id()));
                        dto.setT_f(StatisticsType.HOUR.getCode()==type.intValue() ? DateUtil.YMDHH.format(dto.getT()) : DateUtil.ymd.format(dto.getT()));
                    });
                }
            }
            Page<ChannelReportDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("StatisticsService channelpaginate exception ===>\r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    public ReturnDto<ChannelReportDto> channelCollpaginate(ParamDto param, Integer type, Integer id, String startTime, String endTime) {
        try {
            Date start = DateUtil.todayZeroClock(startTime);
            Date end = DateUtil.tonight(endTime);
            Long total = channelReportMapper.countSumByExample(id,type,start,end);
            List<ChannelReportDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                List<UDTjChannelReport> hourReports = channelReportMapper.selectSumByExample(id,type,start,end,param.getSort_name(),param.getSort_order().name(),param.offset(),param.limit());
                if (!CollectionUtils.isNullOrEmpty(hourReports)) {
                    Set<Integer> channelIdSet = Sets.newHashSet();
                    hourReports.forEach(report -> {
                        ChannelReportDto dto = new ChannelReportDto();
                        BeanUtils.copyProperties(report, dto);
                        if (null != report.getChannel_id()) {
                            channelIdSet.add(report.getChannel_id());
                        }
                        dtoList.add(dto);
                    });
                    Map<Integer, String> channelMap = channelService.channelMap(channelIdSet);
                    List<UdMap> mapList = channelReportMapper.getChannelUvMap(new ArrayList<>(channelIdSet),start,end);
                    Map<Integer,Integer> udmap = Maps.newHashMap();
                    if (!CollectionUtils.isNullOrEmpty(mapList)){
                        for (UdMap udMap : mapList){
                            if (!udmap.containsKey(udMap.getKey()))
                                udmap.put(udMap.getKey(),udMap.getValue());
                        }
                    }


                    dtoList.forEach(dto -> {
                        dto.setName(channelMap.get(dto.getChannel_id()));
                        dto.setT_f(startTime.concat("~").concat(endTime));
                        dto.setPlay_uv( udmap.get(dto.getChannel_id()));
                    });
                }
            }
            Page<ChannelReportDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("StatisticsService channelpaginate exception ===>\r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }
}
