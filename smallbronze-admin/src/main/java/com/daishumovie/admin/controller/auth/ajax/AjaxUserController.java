package com.daishumovie.admin.controller.auth.ajax;

import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.auth.IAdminRoleService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.admin.service.auth.IRoleService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.dao.mapper.smallbronzeadmin.AdminRoleEntityMapper;
import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.AdminRoleEntity;
import com.daishumovie.dao.model.auth.RoleEntity;
import com.daishumovie.dao.model.auth.UserInfo;
import com.daishumovie.dao.model.auth.enums.BooleanNumEnum;
import com.daishumovie.dao.model.auth.enums.EnumRoleType;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.Md5;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by feiFan.gou on 2017/8/24 19:02.
 */
@RestController
@RequestMapping("/ajax/user/")
@Log4j
public class AjaxUserController extends BaseController{

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IAdminRoleService adminRoleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private AdminRoleEntityMapper arMapper;

    /**
     * 新增/更新用户
     * @param request
     * @return
     */
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public JSONResult saveOrUpdate(HttpServletRequest request) {
        try {

            AdminEntity adminEntity = new AdminEntity();
            adminEntity.setUsername(ServletRequestUtils.getStringParameter(request, "userName", ""));
            adminEntity.setPassword(Md5.getMD5(ServletRequestUtils.getStringParameter(request, "passWord", "")));
            adminEntity.setRealName(ServletRequestUtils.getStringParameter(request, "realName", ""));
            if (!StringUtil.isEmpty(request.getParameter("id"))){
                adminEntity.setId(ServletRequestUtils.getLongParameter(request, "id"));
                adminEntity.setUpdateTime(new Date());
                this.adminService.update(adminEntity);
            }else{
                adminEntity.setCreateTime(new Date());
                adminEntity.setUpdateTime(new Date());
                adminEntity.setStatus(1);
                this.adminService.save(adminEntity);
            }
            return JSONResult.success();
        } catch (Exception e) {
            log.info("Controller exception.", e);
            return JSONResult.fail();
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    public JSONResult delete(@PathVariable("id") long id) {
        try {
            AdminEntity adminEntity = new AdminEntity();
            adminEntity.setId(id);
            adminService.delAdmin(adminEntity);
            return JSONResult.success();
        } catch (Exception e) {
            log.info("Controller exception.", e);
            return JSONResult.fail();
        }
    }

    /**
     * 分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "pagerForUser", method = RequestMethod.POST)
    public ReturnDto pager(HttpServletRequest request) {


        try {
            UserInfo userInfo = SessionUtil.getLoginUserInfo(request);
            AdminEntity user = userInfo.getAdmin();
            String username = ServletRequestUtils.getStringParameter(request, "username", null);
            String status = ServletRequestUtils.getStringParameter(request, "status", null);

            Map<String,Object> condition = new HashMap<>();
            if (!StringUtils.isEmpty(username))
                condition.put("username", username);
            if (!StringUtils.isEmpty(status))
                condition.put("status", status);
            if (!getRoleTypeSet(userInfo).contains(EnumRoleType.ADMIN.getIndex())) {
                //如果不是系统管理员，只能查询本部门的人
                Map<String,String> c = new HashMap<>();
                c.put("username", user.getUsername());
                String otherCondition = "(id IN (SELECT a.adminId FROM `ot_admin_role` a, `ot_roles` r WHERE a.roleId = r.id AND r.type > " + EnumRoleType.MANAGER.getIndex() + ")" +
                        " OR id NOT IN (SELECT adminId FROM ot_admin_role))";
                condition.put("other", otherCondition);
            }

            Page<AdminEntity> page = buildPage(request);
            page = adminService.queryAdminByPage(page, condition);
            return pagerResponse(page);
        } catch (Exception e) {
            log.info("Controller exception.", e);
        }
        return pagerResponse(null);
    }

    private Set getRoleTypeSet(UserInfo userInfo) {
        Set<Integer> roleTypeSet = new HashSet<>();
        for (RoleEntity role : userInfo.getRoles()) {
            roleTypeSet.add(role.getType());
        }
        return roleTypeSet;
    }

    /**
     * 修改状态
     * @param request
     * @return
     */
    @RequestMapping(value = "editUserStatus", method = RequestMethod.POST)
    public JSONResult editUserStatus(HttpServletRequest request) {

        try {
            UserInfo userInfo = SessionUtil.getLoginUserInfo(request);
            if (!userInfo.containRoleType(EnumRoleType.ADMIN) && !userInfo.containRoleType(EnumRoleType.MANAGER)) {
                throw new ResultException("无操作权限");
            }
            long adminId = ServletRequestUtils.getLongParameter(request, "id", -1);
            if (adminId == -1) {
                throw new ResultException("无效的用户");
            }
            AdminEntity admin = adminService.get(adminId);
            if (admin == null) {
                throw new ResultException("用户不存在");
            }
            if (admin.getStatus() == BooleanNumEnum.TRUE.getIndex()) {
                admin.setStatus(BooleanNumEnum.FALSE.getIndex());
            } else
                admin.setStatus(BooleanNumEnum.TRUE.getIndex());
            admin.setUpdateTime(new Date());
            if (!adminService.update(admin)) {
                throw new ResultException("更改失败");
            }
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            log.info("Controller exception.", e);
            return JSONResult.fail();
        }
    }


    /**
     * 用户角色
     * @param request
     * @return
     */
    @RequestMapping(value = "userRoleRel")
    public ReturnDto userRoleRel(HttpServletRequest request) {

        try {
            Page<AdminRoleEntity> page = buildPage(request);
            Long adminId = Long.valueOf(request.getParameter("adminId"));
            Integer total = 3;
            page.setTotal(total);
            if (total > 0){
                AdminEntity admin = adminService.get(adminId);
                List<AdminRoleEntity> adminRoles = adminRoleService.query(adminId, null);
                for (AdminRoleEntity adminRole : adminRoles) {
                    adminRole.setAdmin(admin);
                    adminRole.setRole(roleService.get(adminRole.getRoleId()));
                }
                page.setItems(adminRoles);
            }
            return pagerResponse(page);
        } catch (Exception e) {
            log.info("Controller exception.", e);
            return pagerResponse(null);
        }
    }

    /**
     * 分页数据(角色)
     * @param request
     * @return
     */
    @RequestMapping(value = "pagerForRole", method = RequestMethod.POST)
    public ReturnDto pagerForRole(HttpServletRequest request) {

        try {
            UserInfo userInfo = SessionUtil.getLoginUserInfo(request);
            AdminEntity user = userInfo.getAdmin();

            String departmentId = ServletRequestUtils.getStringParameter(request, "departmentId", null);
            String name = ServletRequestUtils.getStringParameter(request, "name", null);
            String type = ServletRequestUtils.getStringParameter(request, "type", null);
            String status = ServletRequestUtils.getStringParameter(request, "status", String.valueOf(BooleanNumEnum.TRUE.getIndex()));

            Map<String,Object> condition = new HashMap<>();
            if (!StringUtils.isEmpty(departmentId))
                condition.put("departmentId", departmentId);
            if (!StringUtils.isEmpty(name))
                condition.put("name", name);
            if (!StringUtils.isEmpty(type))
                condition.put("type", type);
            if (!getRoleTypeSet(userInfo).contains(EnumRoleType.ADMIN.getIndex())) {
                //如果不是管理员，只能查询三级角色
                condition.put("type", EnumRoleType.OPERATOR.getIndex());
            }
            if (!StringUtils.isEmpty(status))
                condition.put("status", status);

            Page<RoleEntity> page = buildPage(request);
            page = roleService.queryByPage(page, condition);
            return pagerResponse(page);
        } catch (Exception e) {
            log.info("Controller exception.", e);
            return pagerResponse(null);
        }
    }

    /**
     * 关联用户角色
     * @param request
     * @return
     */
    @RequestMapping(value = "saveAdminRole", method = RequestMethod.POST)
    public JSONResult saveAdminRole(HttpServletRequest request) {

        try {
            long adminId = ServletRequestUtils.getLongParameter(request, "adminId", -1);
            long roleId = ServletRequestUtils.getLongParameter(request, "roleId", -1);
            AdminEntity admin = adminService.get(adminId);
            RoleEntity role = roleService.get(roleId);
            if (admin == null) {
                throw new ResultException("用户不存在");
            }
            if (role == null) {
                throw new ResultException("角色不存在");
            }
            if (role.getStatus() == BooleanNumEnum.FALSE.getIndex()) {
                throw new ResultException("角色已经停用");
            }

            List<AdminRoleEntity> list = adminRoleService.query(adminId, roleId);
            if (list != null && list.size() > 0) {
                throw new ResultException("用户已关联该角色");
            } else {
                AdminRoleEntity adminRole = new AdminRoleEntity();
                adminRole.setAdminId(adminId);
                adminRole.setRoleId(roleId);
                if (adminRoleService.save(adminRole) == null) {
                    throw new ResultException("关联异常");
                }
            }

            return JSONResult.success(new ObjectMapper().writeValueAsString(role));
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            log.info("Controller exception.", e);
            return JSONResult.fail();
        }
    }

    /**
     * 移除角色
     * @param request
     * @return
     */
    @RequestMapping(value = "removeRole", method = RequestMethod.POST)
    public JSONResult removeRole(HttpServletRequest request) {

        try {
            long adminId = ServletRequestUtils.getLongParameter(request, "adminId", -1);
            long roleId = ServletRequestUtils.getLongParameter(request, "roleId", -1);
            List<AdminRoleEntity> adminRoles = adminRoleService.query(adminId, roleId);
            AdminEntity admin = adminService.get(adminId);
            RoleEntity role = roleService.get(roleId);
            if (CollectionUtils.isNullOrEmpty(adminRoles)) {
                throw new ResultException("关联不存在");
            }
            if (admin == null) {
                throw new ResultException("用户不存在");
            }
            if (role == null) {
                throw new ResultException("角色不存在");
            }
            if (!adminRoleService.delete(adminRoles.get(0))) {
                throw new ResultException("删除异常");
            }
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            log.info("Controller exception.", e);
            return JSONResult.fail();
        }
    }

    @RequestMapping(value = "allotRole", method = RequestMethod.POST)
    @Transactional
    public JSONResult allotRole(@RequestParam("admin_id") long adminId, @RequestParam("role_ids[]") long[] roleIds) {

        try {
            if (roleIds.length == 0) {
                throw new ResultException(ErrMsg.param_error);
            }
            //删除旧的角色
            AdminRoleEntity entity = new AdminRoleEntity();
            entity.setAdminId(adminId);
            arMapper.deleteByCondition(entity);
            //保存新的角色
            for (long roleId : roleIds) {
                AdminRoleEntity newEntity = new AdminRoleEntity();
                newEntity.setAdminId(adminId);
                newEntity.setRoleId(roleId);
                arMapper.insert(newEntity);
            }
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            return JSONResult.fail();
        }

    }

}
