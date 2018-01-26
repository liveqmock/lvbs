package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.ICategoryService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationResult;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.model.SbCommonCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by feiFan.gou on 2017/8/31 18:25.
 */
@RestController
@RequestMapping("/ajax/category/")
public class AjaxCategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("paginate")
    public ReturnDto paginate(ParamDto param, String name, Integer type, String createTime) {

        return categoryService.paginate(param, name, type, createTime);
    }

    @LogConfig(object = OperationObject.material_category,type = OperationType.insert)
    @RequestMapping("save")
    public JSONResult save(SbCommonCategory category, HttpServletRequest request) {

        try {
            categoryService.save(category, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping("update")
    @LogConfig(object = OperationObject.material_category,type = OperationType.edit)
    public JSONResult update(SbCommonCategory category, HttpServletRequest request){

        try {
            categoryService.update(category, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping("delete")
    @LogConfig(object = OperationObject.material_category,type = OperationType.delete)
    public JSONResult delete(Integer id) {

        try {
            categoryService.delete(id);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
}
