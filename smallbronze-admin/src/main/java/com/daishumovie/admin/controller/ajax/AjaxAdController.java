package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.dto.AdDto;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IAdService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.model.SbAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ajax/ad/")
public class AjaxAdController {


    @Autowired
    private IAdService adService;


    @RequestMapping("paginate")
    public ReturnDto<AdDto> paginate(ParamDto param, String name, String createTime, Integer status, Integer orders) {


        return adService.paginate(param,name,createTime,status,orders);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.ad,type = OperationType.insert)
    public JSONResult save(SbAd ad, HttpServletRequest request) {

        try {
            adService.save(ad, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {

            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.ad,type = OperationType.edit)
    public JSONResult update(SbAd ad, HttpServletRequest request) {

        try {
            adService.update(ad, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {

            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.ad,type = OperationType.delete)
    public JSONResult delete(Integer id, HttpServletRequest request) {

        try {
            adService.delete(id, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {

            return JSONResult.fail(e.getMessage());
        }
    }


}
