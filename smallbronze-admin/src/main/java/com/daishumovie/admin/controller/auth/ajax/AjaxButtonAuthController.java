package com.daishumovie.admin.controller.auth.ajax;

import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.auth.IButtonAuthService;
import com.daishumovie.dao.model.auth.OtButtonAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by feiFan.gou on 2017/8/24 18:50.
 */
@RestController
@RequestMapping("/ajax/buttonAuth/")
public class AjaxButtonAuthController {


    @Autowired
    private IButtonAuthService authService;
    /**
     * 按钮权限更新/新建
     * @param authority
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public JSONResult update(OtButtonAuthority authority) {

        try {
            authService.update(authority);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    /**
     * 删除按钮权限
     * @param id
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public JSONResult delete(Integer id) {

        try {
            authService.delete(id);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    /**
     * 分配权限
     * @param roleId
     * @param buttonIds
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JSONResult save(Long roleId, @RequestParam("button_ids[]")Integer[] buttonIds) {

        try {
            authService.save(roleId, buttonIds);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
}
