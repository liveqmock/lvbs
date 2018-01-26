package com.daishumovie.admin.controller;

import com.daishumovie.base.enums.db.ActivityStatus;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/10/20 11:27.
 */
@RequestMapping("/activity/")
@Controller
public class ActivityController extends BaseController {

    @Autowired
    private SbActivityMapper activityMapper;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(String title, String topic, Integer status,Integer whetherOnline, String preTime, String startTime) {

        ModelAndView view = new ModelAndView();
        view.addObject("activity_status", ActivityStatus.values());
        view.addObject("whether_online", Whether.values());
        { //请求参数
            view.addObject("param_topic", topic);
            view.addObject("param_title", title);
            view.addObject("param_status", status);
            view.addObject("param_whether_online", whetherOnline);
            view.addObject("param_pre_time", preTime);
            view.addObject("param_start_time", startTime);
        }
        view.setViewName("activity/index");
        return view;
    }

    @RequestMapping(value = "initAdd", method = RequestMethod.GET)
    public ModelAndView initAdd() {

        return new ModelAndView("activity/add");
    }
    @RequestMapping(value = "initEdit", method = RequestMethod.GET)
    public ModelAndView initEdit(Integer id, String title, String topic, Integer status,Integer whetherOnline, String preTime, String startTime) {
        ModelAndView view = new ModelAndView("activity/edit");
        SbActivity activity = activityMapper.selectByPrimaryKey(id);
        if (null != activity) {
            if (null != activity.getPreTime()) {
                view.addObject("pre_time", DateUtil.BASIC.format(activity.getPreTime()));
            }
            view.addObject("start_time", DateUtil.BASIC.format(activity.getStartTime()));
            view.addObject("end_time", DateUtil.BASIC.format(activity.getEndTime()));
        }
        view.addObject("activity", activity);
        { //请求参数
            view.addObject("param_title", title);
            view.addObject("param_topic", topic);
            view.addObject("param_status", status);
            view.addObject("param_whether_online", whetherOnline);
            view.addObject("param_pre_time", preTime);
            view.addObject("param_start_time", startTime);
        }
        return view;
    }

    @RequestMapping(value = "put", method = RequestMethod.GET)
    public ModelAndView put(Integer id) {

        ModelAndView view = new ModelAndView("activity/put");
        view.addObject("activity_id", id);
        return view;
    }


}
