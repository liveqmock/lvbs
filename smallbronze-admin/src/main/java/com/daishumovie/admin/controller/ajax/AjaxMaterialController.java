package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IMaterialService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.enums.db.CategoryType;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.model.SbCommonCategory;
import com.daishumovie.dao.model.SbMaterialContent;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/8/28 15:27.
 */
@RestController
@RequestMapping("/ajax/material/")
public class AjaxMaterialController {

    @Autowired
    private IMaterialService materialService;

    @RequestMapping("paginate")
    public ReturnDto paginate(ParamDto param, String name, String createTime,Integer categoryType, Integer categoryId, Integer appId, Integer isOnShelf) {

        return materialService.paginate(param, name, createTime, categoryType, categoryId, appId, isOnShelf);
    }

    @RequestMapping("categoryType")
    public List<SbCommonCategory> categoryType(@RequestParam("category_type")Integer type) {

        return materialService.categoryType(type);
    }

    @RequestMapping("save")
    @LogConfig(object = OperationObject.material,type = OperationType.insert)
    public JSONResult save(SbMaterialContent material, Integer categoryId, Integer appId, Integer categoryType, HttpServletRequest request) {

        try {
            App ai = App.get(appId);
            CategoryType cy = CategoryType.get(categoryType);
            if (null == ai || null == cy || null == categoryId) {
                throw new ResultException(ErrMsg.param_error);
            }
            materialService.save(material, SessionUtil.getLoginAdminUid(request), cy, ai, categoryId);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }


    @RequestMapping("update")
    @LogConfig(object = OperationObject.material,type = OperationType.edit)
    public JSONResult update(SbMaterialContent material, Integer categoryId, Integer appId, HttpServletRequest request){

        try {
            App ai = App.get(appId);
            if (null == ai || null == categoryId) {
                throw new ResultException(ErrMsg.param_error);
            }
            materialService.update(material, SessionUtil.getLoginAdminUid(request), categoryId, ai);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }


    @RequestMapping("delete")
    @LogConfig(object = OperationObject.material,type = OperationType.delete)
    public JSONResult delete(Integer id, HttpServletRequest request){

        try {
            materialService.delete(id, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping("offShelf")
    @LogConfig(object = OperationObject.material,type = OperationType.off_shelf)
    public JSONResult offShelf(Integer id, HttpServletRequest request){

        try {
            materialService.offShelf(id, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    @RequestMapping("onShelf")
    @LogConfig(object = OperationObject.material,type = OperationType.on_shelf)
    public JSONResult onShelf(Integer id, HttpServletRequest request){

        try {
            materialService.onShelf(id, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
}