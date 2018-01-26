package com.daishumovie.admin.controller;

import com.daishumovie.base.enums.db.AdTargetType;
import com.daishumovie.base.enums.db.AdType;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.dao.mapper.smallbronze.SbAdMapper;
import com.daishumovie.dao.model.SbAd;
import com.daishumovie.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequestMapping("/ad/")
@Controller
public class AdController extends BaseController {


    @Autowired
    private SbAdMapper adMapper;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(String name, String createTime, Integer status, Integer orders) {

        ModelAndView view = new ModelAndView();

        view.addObject("ad_status", Whether.values());

        view.addObject("name", name);
        view.addObject("orders", orders);
        view.addObject("status", status);
        view.addObject("create_time", createTime);

        view.setViewName("ad/index");
        return view;

    }

    @RequestMapping(value = "initAdd", method = RequestMethod.GET)
    public ModelAndView initAdd() {
        ModelAndView view = new ModelAndView();
        view.addObject("targetType_list", AdTargetType.values());
        ArrayList<AdType> adTypes = Lists.newArrayList(AdType.values());
        adTypes.sort(Comparator.reverseOrder());
        view.addObject("adType_list",adTypes);
        view.setViewName("ad/add");
        return view;
    }


    @RequestMapping(value = "initEdit", method = RequestMethod.GET)
    public ModelAndView initEdit(Integer id) {

        ModelAndView view = new ModelAndView("ad/edit");
        SbAd ad = adMapper.selectByPrimaryKey(id);

        if (null != ad) {
            view.addObject("start_time", DateUtil.BASIC.format(ad.getStartTime()));
            view.addObject("end_time", DateUtil.BASIC.format(ad.getEndTime()));
        }
        view.addObject("adType_list", AdType.values());
        view.addObject("targetType_list", AdTargetType.values());
        view.addObject("orders_list", Lists.newArrayList(1,2,3,4,5));
        view.addObject("ad", ad);


        return view;
    }


}
