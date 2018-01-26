package com.daishumovie.admin.controller;

import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.enums.db.ReportStatusEnum;
import com.daishumovie.base.enums.db.ReportType;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by feiFan.gou on 2017/6/1 17:29.
 */
@Controller
@Log4j
@RequestMapping("/report/")
public class ReportController extends BaseController {

    @RequestMapping("index")
    public ModelAndView index() {

        Map<String, Object> map = Maps.newHashMap();
        map.put("report_status", ReportStatusEnum.values());
        map.put("report_types", ReportType.values());
        map.put("appIds", App.values());
        return new ModelAndView("/report/index", map);
    }


}
