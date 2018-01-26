package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.service.IAppService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.model.DsmAppVersion;
import com.daishumovie.dao.model.auth.UserInfo;
import com.daishumovie.utils.StringUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/5/12 13:42.
 */
@Log4j
@RestController
@RequestMapping("/ajax/app/")
public class AjaxAppVersionController extends BaseController {

    @Autowired
    private IAppService appService;

    /**
     * 异步获取APP版本列表数据
     * ModelAndView @throws
     */
    @RequestMapping(value = "ajaxAppVersionList",method = RequestMethod.POST)
    public ReturnDto ajaxAppVersionList(ParamDto param,Integer appId,String versionNum,Integer plat) {
        if (StringUtil.isEmpty(param.getSort_name())){
            param.setSort_name("create_time");
            param.setSort_order(ParamDto.order.desc);
        }
        return appService.getDsmAppVersionByConditions(param,appId,versionNum,plat);
    }
    @RequestMapping(value = "getAppVersionByPlat", method = RequestMethod.POST)
    public ModelAndView getAppVersionByPlat(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        model.put("success", false);
        try {
            List<DsmAppVersion> list = this.appService.getDsmAppVersionList(Integer.valueOf(request.getParameter("appId")),Integer.valueOf(request.getParameter("plat")));
            model.put("success", true);
            model.put("appVersionList",list);
            return new ModelAndView(new MappingJackson2JsonView(), model);
        } catch (Exception e) {
            e.printStackTrace();
            model.put("errMsg", "操作失败");
            return new ModelAndView(new MappingJackson2JsonView(), model);
        }
    }

    @RequestMapping(value = "saveAppVersion", method = RequestMethod.POST)
    public ModelAndView saveAppVersion(HttpServletRequest request) {

        ModelMap model = new ModelMap();
        model.put("success", false);
        try {
            DsmAppVersion record = encapsulatedAppVersion(request);
            Response resp;
            if (!StringUtil.isEmpty(request.getParameter("_id"))){
                resp = this.appService.update(record);
            }else{
                resp = this.appService.save(record);
            }
            if (resp.getResult() != null){
                model.put("errMsg", resp.getResult());
            }else{
                model.put("success", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView(new MappingJackson2JsonView(), model);
    }

    private DsmAppVersion encapsulatedAppVersion(HttpServletRequest request){

        DsmAppVersion dsmAppVersion = new DsmAppVersion();
        UserInfo userInfo = SessionUtil.getLoginUserInfo(request);
        if (userInfo != null)
            dsmAppVersion.setOperatorId(userInfo.getAdmin().getId().intValue());
        String id = request.getParameter("_id");
        if (!StringUtil.isEmpty(id)){
            dsmAppVersion.setId(Integer.valueOf(request.getParameter("_id")));
        }
        dsmAppVersion.setVersionNum(request.getParameter("versionNum"));
        dsmAppVersion.setPlat(Integer.valueOf(request.getParameter("plat")));
        dsmAppVersion.setRemark(request.getParameter("remark"));
        dsmAppVersion.setMinVersion(request.getParameter("minVersion"));
        dsmAppVersion.setUpdateDesc(request.getParameter("updateDesc"));
        dsmAppVersion.setDownUrl(request.getParameter("downUrl"));
        dsmAppVersion.setAppId(Integer.valueOf(request.getParameter("appId")));
        return dsmAppVersion;
    }
}
