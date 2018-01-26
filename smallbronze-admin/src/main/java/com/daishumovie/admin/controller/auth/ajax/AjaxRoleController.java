package com.daishumovie.admin.controller.auth.ajax;

import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.service.auth.*;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.dao.model.auth.*;
import com.daishumovie.dao.model.auth.enums.BooleanNumEnum;
import com.daishumovie.dao.model.auth.enums.EnumRoleType;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by feiFan.gou on 2017/8/25 15:41.
 */
@RestController
@RequestMapping("/ajax/role")
@Log4j
public class AjaxRoleController extends BaseController {


    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private IRoleResourceService roleResourceService;
    @Autowired
    private IAdminRoleService adminRoleService;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IUserInfoService userInfoService;


    /**
     * 分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "pager", method = RequestMethod.POST)
    public ReturnDto pager(HttpServletRequest request) {

        Page<RoleEntity> page = buildPage(request);
        try {
            return pagerResponse(roleService.queryByPage(page, Maps.newHashMap()));
        } catch (Exception e) {
            log.error("Controller exception.", e);
        }
        return pagerResponse(null);
    }

    /**
     * 保存角色
     * @param request
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JSONResult save(HttpServletRequest request) {

        try {
            RoleEntity roleEntity = new RoleEntity();
            UserInfo userInfo = SessionUtil.getLoginUserInfo(request);
            RoleEntity nowRoleEntity = userInfo.getRoles().iterator().next();
            long roleType = nowRoleEntity.getType();
            roleEntity.setDescn(ServletRequestUtils.getStringParameter(request, "roleDesc", ""));
            roleEntity.setName(ServletRequestUtils.getStringParameter(request, "roleName", ""));
            roleEntity.setCreateTime(new Date());
            roleEntity.setStatus(BooleanNumEnum.TRUE.getIndex());
            if (roleType == EnumRoleType.MANAGER.getIndex()) {
                roleEntity.setType(EnumRoleType.OPERATOR.getIndex());
            } else {
                roleEntity.setType(EnumRoleType.MANAGER.getIndex());
            }

            if (!StringUtil.isEmpty(request.getParameter("id"))){
                roleEntity.setId(Long.valueOf(request.getParameter("id")));
                this.roleService.updateRole(roleEntity);
            }else{
                this.roleService.addRole(roleEntity);
            }
            return JSONResult.success();
        } catch (Exception e) {
            log.error("Controller exception.", e);
            return JSONResult.fail();
        }
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    public JSONResult delete(@PathVariable("id") Long id) {

        try {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(id);
            this.roleService.deleteRole(roleEntity);
            return JSONResult.success();
        } catch (Exception e) {
            log.error("Controller exception.", e);
            return JSONResult.fail();
        }
    }

    /**
     * 修改状态
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "setStatus/{id}", method = RequestMethod.POST)
    public JSONResult setStatus(@PathVariable("id") Integer id, HttpServletRequest request) {

        try {
            RoleEntity roleEntity = this.roleService.queryRoleById(id).get(0);
            roleEntity.setStatus(ServletRequestUtils.getIntParameter(request, "status"));
            this.roleService.updateRole(roleEntity);
            return JSONResult.success();
        } catch (Exception e) {
            log.error("Controller exception.", e);
            return JSONResult.fail();
        }
    }

    /**
     * 获取资源
     * @return
     */
    @RequestMapping(value = "resourceQuery", method = RequestMethod.POST)
    public List<ResourceEntity> resourceQuery() {

        try {
            return resourceService.queryByCondition(Maps.newHashMap());
        } catch (Exception e) {
            log.error("Controller exception.", e);
            return Lists.newArrayList();
        }
    }

    /**
     * 保存角色,资源关系
     * @param request
     * @return
     */
    @RequestMapping(value = "saveRoleSource", method = RequestMethod.POST)
    public JSONResult saveRoleSource(HttpServletRequest request) {

        try {
            // 角色 - 资源参数
            Long roleId = ServletRequestUtils.getLongParameter(request, "roleId", -1);
            if (roleId == -1) {
                throw new RuntimeException("无效的角色");
            }
            String resourceIds = ServletRequestUtils.getStringParameter(request, "resourceUuid", "");
            // 新的资源关联列表
            List<ResourceEntity> newResources = new ArrayList<>();
            if (!StringUtils.isEmpty(resourceIds)) {
                Map<String,Object> conditions = new HashMap<>();
                conditions.put("uuids", resourceIds);
                newResources = resourceService.queryByCondition(conditions);
            }
            // 旧的资源关联列表
            List<ResourceEntity> oldResources = resourceService.queryRoleResource(roleId);

            // 需要新增的角色-资源关联
            for (ResourceEntity entity : newResources) {
                if (oldResources == null || !oldResources.contains(entity)) { // 旧关联表中不存在，则新增关联
                    ResourceRoleEntity resourceRoleEntity = new ResourceRoleEntity();
                    resourceRoleEntity.setRoleId(roleId);
                    resourceRoleEntity.setResourceUuid(entity.getUuid());
                    roleResourceService.addRoleResource(resourceRoleEntity);
                }
            }
            // 需要删除的旧的角色-资源关联
            if (!CollectionUtils.isNullOrEmpty(oldResources)) {
                for (ResourceEntity entity : oldResources) {
                    if (!newResources.contains(entity)) {
                        roleResourceService.deleteRoleResourceByResourceId(roleId, entity.getUuid());
                    }
                }
            }

            // 重置 当前在线且包含该角色的用户信息
            for (AdminRoleEntity ar : adminRoleService.query(null, roleId)) {
                AdminEntity admin = adminService.get(ar.getAdminId());
                UserInfo userInfo = userInfoService.getUserInfo(admin.getUsername());
                SessionUtil.setLoginAdmin(request, userInfo);
            }

            return JSONResult.success();
        } catch (Exception e) {
            log.error("Controller exception.", e);
            return JSONResult.fail();
        }
    }
}
