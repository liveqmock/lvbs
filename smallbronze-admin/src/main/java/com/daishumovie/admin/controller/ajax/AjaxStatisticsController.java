package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.service.IStatisticsService;
import com.daishumovie.base.enums.db.StatisticsType;
import com.daishumovie.utils.StringUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by feiFan.gou on 2017/6/1 17:29.
 * 统计相关
 */
@RestController
@Log4j
@RequestMapping("/ajax/statistics/")
public class AjaxStatisticsController extends BaseController {

    @Autowired
    private IStatisticsService statisticsService;


    @RequestMapping(value = "video/paginate",method = RequestMethod.POST)
    public ReturnDto videopaginate(ParamDto param, Integer type,Integer videoId,Integer s_type,String name,String createTime, String endTime) {
        if (StringUtil.isEmpty(param.getSort_name())){
            param.setSort_name("t");
            param.setSort_order(ParamDto.order.desc);
        }
        if (s_type == null ||  s_type.intValue() == 0){
            return statisticsService.videopaginate(param,type == null ? StatisticsType.HOUR.getCode() : type,videoId,name,createTime,endTime);
        }else{
            return statisticsService.videoCollpaginate(param,StatisticsType.DAY.getCode(),videoId,name,createTime,endTime);
        }

    }

    @RequestMapping(value = "channel/paginate",method = RequestMethod.POST)
    public ReturnDto channelpaginate(ParamDto param, Integer type,Integer channelId,Integer s_type,String createTime, String endTime) {
        if (StringUtil.isEmpty(param.getSort_name())){
            param.setSort_name("t");
            param.setSort_order(ParamDto.order.desc);
        }
        if (s_type == null ||  s_type.intValue() == 0){
            return statisticsService.channelpaginate(param,type == null ? StatisticsType.HOUR.getCode() : type,channelId,createTime,endTime);
        }else{
            return statisticsService.channelCollpaginate(param,StatisticsType.DAY.getCode(),channelId,createTime,endTime);
        }

    }

}
