package com.daishumovie.admin.controller;

import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.base.enums.db.StatisticsType;
import com.daishumovie.utils.DateUtil;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/6/1 17:29.
 * 统计相关
 */
@Controller
@Log4j
@RequestMapping("/statistics/")
public class StatisticsController extends BaseController {
    @Autowired
    private IChannelService channelService;

    @RequestMapping("video/index")
    public ModelAndView video_index() {

        Map<String, Object> map = Maps.newHashMap();
        Date date = new Date();
        map.put("startT", DateUtil.ymd.format(DateUtil.addDays(date,-7)));
        map.put("endT", DateUtil.ymd.format(DateUtil.addDays(date,0)));
        map.put("statisticsType", StatisticsType.values());
        return new ModelAndView("/statist/video/index", map);
    }

    @RequestMapping("video/collindex")
    public ModelAndView video_collindex() {

        Map<String, Object> map = Maps.newHashMap();
        Date date = new Date();
        map.put("startT", DateUtil.ymd.format(DateUtil.addDays(date,-7)));
        map.put("endT", DateUtil.ymd.format(DateUtil.addDays(date,0)));
        map.put("statisticsType", StatisticsType.values());
        return new ModelAndView("/statist/video/coll_index", map);
    }

    @RequestMapping("channel/index")
    public ModelAndView channel_index() {

        Map<String, Object> map = Maps.newHashMap();
        Date date = new Date();
        map.put("startT", DateUtil.ymd.format(DateUtil.addDays(date,-7)));
        map.put("endT", DateUtil.ymd.format(DateUtil.addDays(date,0)));

        map.put("channels",channelService.firstLevelList(null));
        map.put("statisticsType", StatisticsType.values());
        return new ModelAndView("/statist/channel/index", map);
    }

    @RequestMapping("channel/collindex")
    public ModelAndView channel_collindex() {

        Map<String, Object> map = Maps.newHashMap();
        Date date = new Date();
        map.put("startT", DateUtil.ymd.format(DateUtil.addDays(date,-7)));
        map.put("endT", DateUtil.ymd.format(DateUtil.addDays(date,0)));
        map.put("channels",channelService.firstLevelList(null));
        map.put("statisticsType", StatisticsType.values());
        return new ModelAndView("/statist/channel/coll_index", map);
    }

}
