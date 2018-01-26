package com.daishumovie.admin.controller.auth.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.admin.service.auth.IUserInfoService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.Md5;
import com.daishumovie.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/8/23 10:56.
 */
@RestController
@RequestMapping("/ajax/admin/")
public class AjaxAdminController{

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IUserInfoService infoService;

    /**
     * 异步登录
     * @param adminName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.system, type = OperationType.login)
    public JSONResult login(@RequestParam("admin_name") String adminName, String password, HttpServletRequest request) {

        try {
            if (StringUtil.isEmpty(adminName) || StringUtil.isEmpty(password)) {
                throw new ResultException(ErrMsg.param_error);
            }
            AdminEntity entity = adminService.getAdmin(adminName);
            if (null == entity) {
                throw new ResultException("用户不存在");
            }
            if (!Objects.equals(Md5.getMD5(password), entity.getPassword())) {
                throw new ResultException("密码错误");
            }
            entity.setLastLoginTime(new Date());
            entity.setUpdateTime(entity.getLastLoginTime());
            adminService.update(entity); //更新登录信息
            SessionUtil.setLoginAdmin(request, infoService.getUserInfo(entity)); //存入session用户信息
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    /**
     * 校验原密码
     * @param oldPwd
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkOldPwd", method = RequestMethod.POST)
    public JSONResult checkOldPwd(String oldPwd, HttpServletRequest request) {

        try {
            AdminEntity adminEntity = SessionUtil.getLoginAdmin(request);
            if (adminEntity == null || !adminEntity.getPassword().equals(Md5.getMD5(oldPwd))) {
                throw new ResultException("原密码错误");
            }
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    /**
     * 修改密码
     * @param request
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @return
     */
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.system, type = OperationType.change_password)
    public JSONResult changePwd(HttpServletRequest request, String oldPwd, String newPwd, String confirmPwd) {

        try {
            if (StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd) || StringUtil.isEmpty(confirmPwd)) {
                throw new ResultException(ErrMsg.param_error);
            }
            AdminEntity adminEntity = SessionUtil.getLoginAdmin(request);
            if (adminEntity == null || !adminEntity.getPassword().equals(Md5.getMD5(oldPwd))) {
                throw new ResultException("原密码错误");

            } else if (!newPwd.equals(confirmPwd)) {
                throw new ResultException("新密码和确认密码不一致");
            }
            adminEntity.setPassword(Md5.getMD5(newPwd));
            adminService.update(adminEntity);
            SessionUtil.destroy(request);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            return JSONResult.fail();
        }
    }



    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.system, type = OperationType.logout)
    public JSONResult logout(HttpServletRequest request) {

        SessionUtil.destroy(request);
        return JSONResult.success();
    }
}
