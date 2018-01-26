package com.daishumovie.admin.service.auth.impl;

import com.daishumovie.admin.service.auth.IDataResourceService;
import com.daishumovie.dao.mapper.smallbronzeadmin.ResourceEntityMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.ResourceRoleEntityMapper;
import com.daishumovie.dao.model.auth.ResourceEntity;
import com.daishumovie.dao.model.auth.enums.EnumResource;
import com.daishumovie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 资源服务接口实现类 User: tianrui.lin@renren-inc.com Date: 13-6-1 Time: 下午2:21 To
 * change this template use File | Settings | File Templates.
 */
@Service("resourceService")
public class DataResourceServiceImpl implements IDataResourceService {

	@Autowired
	private ResourceEntityMapper resourceEntityMapper;

	@Autowired
	private ResourceRoleEntityMapper resourceRoleEntityMapper;

	@Override
	public ResourceEntity get(long id) {
		return resourceEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResourceEntity getByUuid(String uuid) {
		if (StringUtils.isEmpty(uuid))
			return null;
		ResourceEntity condition = new ResourceEntity();
		condition.setUuid(uuid);
		List<ResourceEntity> records = resourceEntityMapper.selectByParam(condition, null, null, null, null, null, null, null);
		if (records != null && !records.isEmpty())
			return (ResourceEntity) records.get(0);
		return null;
	}

	@Override
	public List<ResourceEntity> queryByCondition(Map conditions) {
		ResourceEntity condition = new ResourceEntity();
		List<String> uuids = null;
		if (conditions.get("uuid") != null)
			condition.setUuid(conditions.get("uuid").toString());
		if (conditions.get("uuids") != null) {
			String[] arr = conditions.get("uuids").toString().split(",");
			uuids = new ArrayList<String>();
			for (String uuid : arr) {
				uuids.add(uuid);
			}
		}
		if (conditions.get("parentUuid") != null)
			condition.setParentUuid(conditions.get("parentUuid").toString());
		if (conditions.get("type") != null)
			condition.setType(Integer.valueOf(conditions.get("type").toString()));
		if (conditions.get("path") != null)
			condition.setPath(conditions.get("path").toString());
		if (conditions.get("status") != null)
			condition.setStatus(Integer.valueOf(conditions.get("status").toString()));
		List<ResourceEntity> records = resourceEntityMapper.selectByParam(condition, null, null, null, null, null, null, uuids);
		return records;
	}

	@Override
	public ResourceEntity save(ResourceEntity entity) {
		entity.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		resourceEntityMapper.insert(entity);
		return entity;
	}

	@Override
	public boolean update(ResourceEntity entity) {
		return resourceEntityMapper.updateByPrimaryKeySelective(entity) > 0 ? true : false;
	}

	@Override
	public void delete(ResourceEntity entity) {
		resourceEntityMapper.deleteByCondition(entity);
		resourceEntityMapper.deleteByPrimaryKey(entity.getId());
	}

	@Override
	public List<ResourceEntity> queryResourceLinkWithPath(Collection<String> uuids) {
		if (CollectionUtils.isNullOrEmpty(uuids)) {
			return null;
		}
		List<ResourceEntity> resources = resourceEntityMapper.selectByParam(null, null, null, null, null, null, null, uuids);
		if (CollectionUtils.isNullOrEmpty(resources)) {
			return null;
		}
		uuids.clear();
		for (ResourceEntity entity : resources) {
			String parentsUUids = entity.getParentsUuids();
			if (!StringUtils.isEmpty(parentsUUids)) {
				String[] ids = parentsUUids.split(",");
				uuids.addAll(Arrays.asList(ids));
			}
		}
		if (!CollectionUtils.isNullOrEmpty(uuids)) {
			resources.addAll(resourceEntityMapper.selectByParam(null, null, null, null, null, "sort", "desc", uuids));
		}
		return resources;
	}

	@Override
	public List<ResourceEntity> queryMenuEntities(Collection<String> uuids) {
		if (CollectionUtils.isNullOrEmpty(uuids)) {
			return null;
		}
		ResourceEntity condition = new ResourceEntity();
		condition.setType(EnumResource.MENU.getIndex());
		return resourceEntityMapper.selectByParam(condition, null, null, null, null, "sort", "desc", uuids);
	}

	@Override
	public List<ResourceEntity> queryRoleResource(Long id) {
		return resourceEntityMapper.queryByRoleId(id);
	}

	@Override
	public List<ResourceEntity> queryByUsername(String username) {
		return resourceEntityMapper.queryByUserName(username);
	}
}
