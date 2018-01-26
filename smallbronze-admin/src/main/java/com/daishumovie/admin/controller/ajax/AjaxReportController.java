package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IReportService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.*;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/6/1 17:29.
 */
@RestController
@Log4j
@RequestMapping("/ajax/report/")
public class AjaxReportController extends BaseController {

    @Autowired
    private IReportService reportService;

    @RequestMapping("index")
    public ModelAndView index() {

        Map<String, Object> map = Maps.newHashMap();
        map.put("report_status", ReportProblem.values());
        return new ModelAndView("/report/index", map);
    }

    @RequestMapping(value = "paginate",method = RequestMethod.POST)
    public ReturnDto paginate(ParamDto param, Integer type,Integer appId,String createTime, Integer status) {
        if (status != null && (status.intValue() == ReportStatusEnum.untreated.getCode())){
            param.setSort_name("create_time");
            if (null == param.getSort_order())
                param.setSort_order(ParamDto.order.desc);
        }else{
            if (null == param.getSort_order()){
                param.setSort_name("audit_time");
                param.setSort_order(ParamDto.order.desc);
            }
        }
        return reportService.paginate(param, ReportType.get(type), createTime, status, appId);
    }


    @RequestMapping(value = "pass", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.report,type = OperationType.pass)
    public JSONResult pass(Integer id,Integer contentId, Integer type, HttpServletRequest request) {

        try {
            reportService.passOrNot(id,contentId, ReportResultEnum.YES.getCode(), ReportType.get(type),ReportStatusEnum.offline, SessionUtil.getLoginAdminUid(request));
            //视频需要转化成m3u8格式
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "unpass", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.report,type = OperationType.un_pass)
    public JSONResult unpass(Integer id,Integer contentId, Integer type, HttpServletRequest request) {

        try {
            reportService.passOrNot(id,contentId, ReportResultEnum.NO.getCode(), ReportType.get(type),ReportStatusEnum.online_for_now, SessionUtil.getLoginAdminUid(request));
            //视频需要转化成m3u8格式
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

}
