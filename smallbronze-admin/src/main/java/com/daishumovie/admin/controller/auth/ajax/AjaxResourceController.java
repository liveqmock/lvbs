package com.daishumovie.admin.controller.auth.ajax;

import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.auth.IDataResourceService;
import com.daishumovie.dao.mapper.smallbronzeadmin.ResourceEntityMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.ResourceRoleEntityMapper;
import com.daishumovie.dao.model.auth.ResourceEntity;
import com.daishumovie.dao.model.auth.ResourceRoleEntity;
import com.daishumovie.dao.model.auth.enums.BooleanNumEnum;
import com.daishumovie.dao.model.auth.enums.EnumResource;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/8/23 18:45.
 */
@RestController
@RequestMapping("/ajax/resource/")
@Log4j
public class AjaxResourceController {


    private static final Logger LOGGER = Logger.getLogger(AjaxResourceController.class);
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private ResourceEntityMapper resourceEntityMapper;
    @Autowired
    private ResourceRoleEntityMapper resourceRoleEntityMapper;

    /**
     * 异步加载资源树数据
     * @param request
     * @return
     */
    @RequestMapping(value = "treeList", method = RequestMethod.POST)
    public List<ResourceEntity> treeList(HttpServletRequest request) {

        try {
            String parentUuid = ServletRequestUtils.getStringParameter(request, "parentUuid", "");
            String name = ServletRequestUtils.getStringParameter(request, "name", "");
            int type = ServletRequestUtils.getIntParameter(request, "type", -1);
            String path = ServletRequestUtils.getStringParameter(request, "path", "");

            Map<String,Object> conditions = new HashMap<>();
            if (!StringUtil.isEmpty(parentUuid))
                conditions.put("parentUuid", parentUuid);
            if (!StringUtil.isEmpty(name))
                conditions.put("name", name);
            if (type != -1)
                conditions.put("type", type);
            if (!StringUtil.isEmpty(path))
                conditions.put("path", path);

            return resourceService.queryByCondition(conditions);
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    /**
     * 保存资源
     * @param request
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JSONResult save(HttpServletRequest request) {

        try {
            String parentUuid = ServletRequestUtils.getStringParameter(request, "parentUuid", "");
            String parentsUuid = ServletRequestUtils.getStringParameter(request, "parentsUuids", "");
            String name = ServletRequestUtils.getStringParameter(request, "name", "");
            int type = ServletRequestUtils.getIntParameter(request, "type", EnumResource.MENU.getIndex());
            String path = ServletRequestUtils.getStringParameter(request, "path", "");
            String desc = ServletRequestUtils.getStringParameter(request, "descn", "");

            if (StringUtil.isEmpty(name)) {
                throw new ResultException("请填写节点名称");
            }

            ResourceEntity entity = new ResourceEntity();
            entity.setParentUuid(parentUuid);
            entity.setParentsUuids(parentsUuid);
            entity.setName(name);
            entity.setType(type);
            entity.setPath(path);
            entity.setStatus(BooleanNumEnum.TRUE.getIndex());
            entity.setDescn(desc);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());

            entity = resourceService.save(entity);
            if (entity == null) {
                throw new ResultException();
            }
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            return JSONResult.fail();
        }
    }

    /**
     * 更新资源
     * @param request
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public JSONResult update(HttpServletRequest request) {

        try {
            String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
            String name = ServletRequestUtils.getStringParameter(request, "name", "");
            int type = ServletRequestUtils.getIntParameter(request, "type", 0);
            String path = ServletRequestUtils.getStringParameter(request, "path", "");
            String desc = ServletRequestUtils.getStringParameter(request, "descn", "");

            if (StringUtil.isEmpty(name)) {
                throw new ResultException("请填写节点名称");
            }
            ResourceEntity entity = resourceService.getByUuid(uuid);
            if (entity == null) {
                throw new ResultException("资源不存在");
            }
            entity.setName(name);
            entity.setType(type);
            entity.setPath(path);
            entity.setStatus(BooleanNumEnum.TRUE.getIndex());
            entity.setDescn(desc);
            entity.setUpdateTime(new Date());

            if (!resourceService.update(entity)) {
                throw new ResultException();
            }
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Controller exception.", e);
            return JSONResult.fail();
        }
    }

    /**
     * 删除资源
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public JSONResult delete(HttpServletRequest request) {

        try {
            String uuid = ServletRequestUtils.getStringParameter(request, "uuids", "");

            if (StringUtil.isEmpty(uuid)) {
                throw new ResultException("请选择要删除的资源");
            }
            uuid = uuid.replaceAll("'',", "").replaceAll("'", "");
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("uuids", uuid);

            List<String> resourceIdList = Lists.newArrayList();
            List<ResourceEntity> resourceList = resourceService.queryByCondition(conditions);
            for (ResourceEntity entity : resourceList) {
                resourceIdList.add(entity.getUuid());
            }
            List<ResourceRoleEntity> entityList = resourceRoleEntityMapper.selectByResourceIds(resourceIdList);
            entityList.forEach(resourceRoleEntity -> resourceRoleEntityMapper.deleteByPrimaryKey(resourceRoleEntity.getId()));
            resourceList.forEach(resourceEntity -> resourceEntityMapper.deleteByPrimaryKey(resourceEntity.getId()));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Controller exception.", e);
            return JSONResult.fail();
        }
    }
}
