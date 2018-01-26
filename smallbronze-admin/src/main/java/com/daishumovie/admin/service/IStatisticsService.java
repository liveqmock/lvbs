package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.ChannelReportDto;
import com.daishumovie.admin.dto.VideoReportDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;

/**
 * Created by yang on 2017/10/24.
 */
public interface IStatisticsService
{
    //单日视频维度统计
    ReturnDto<VideoReportDto> videopaginate(ParamDto param, Integer type, Integer id, String name, String startTime, String endTime);

    //视频集合维度统计
    ReturnDto<VideoReportDto> videoCollpaginate(ParamDto param, Integer type, Integer id, String name, String startTime, String endTime);

    //单日渠道维度视频统计
    ReturnDto<ChannelReportDto> channelpaginate(ParamDto param, Integer type, Integer id, String startTime, String endTime);

    //渠道集合维度视频统计
    ReturnDto<ChannelReportDto> channelCollpaginate(ParamDto param, Integer type, Integer id, String startTime, String endTime);



}
