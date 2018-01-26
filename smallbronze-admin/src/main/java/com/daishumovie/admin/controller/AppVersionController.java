package com.daishumovie.admin.controller;

import com.daishumovie.admin.dto.DsmAppVersionDto;
import com.daishumovie.admin.service.IAppService;
import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.enums.db.AppPlatEnum;
import com.daishumovie.dao.model.DsmAppVersion;
import com.daishumovie.utils.StringUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/5/12 13:42.
 */
@Log4j
@Controller
@RequestMapping("/app/")
public class AppVersionController extends BaseController {

    @Autowired
    private IAppService appService;

    @RequestMapping("index")
    public ModelAndView index() {

        ModelAndView view = new ModelAndView();
        view.addObject("platList", AppPlatEnum.values());
        view.addObject("app_list", App.values());
        view.setViewName("/app/versions/index");
        return view;
    }


    @RequestMapping("versionMethod")
    public ModelAndView versionMethod(HttpServletRequest request) {

        ModelAndView view = new ModelAndView();
        view.addObject("platList", AppPlatEnum.values());
        DsmAppVersionDto dto = null;
        if (!StringUtil.isEmpty(request.getParameter("_id"))){
            dto = appService.getDsmAppVersionDtoById(Integer.valueOf(request.getParameter("_id")));
        }
        view.addObject("appVersion",dto);
        List<DsmAppVersion> appVersionList =  appService.getDsmAppVersionList(StringUtil.isNotEmpty(request.getParameter("appId")) ? Integer.valueOf(request.getParameter("appId")) : null,AppPlatEnum.IOS.getCode());
        view.addObject("appVersionList",appVersionList);
        view.addObject("app_list", App.values());
        view.setViewName("/app/versions/add");
        return view;
    }


}
