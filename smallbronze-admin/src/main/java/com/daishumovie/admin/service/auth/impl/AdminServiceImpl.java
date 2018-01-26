package com.daishumovie.admin.service.auth.impl;

import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.dao.mapper.smallbronzeadmin.AdminEntityMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.AdminRoleEntityMapper;
import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.AdminRoleEntity;
import com.daishumovie.dao.model.auth.OtAdminsExample;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service("adminService")
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private AdminEntityMapper adminEntityMapper;

    @Autowired
    private AdminRoleEntityMapper entityMapper;

	@Override
	public AdminEntity getAdmin(String username) {
		AdminEntity admin = null;
		AdminEntity condition = new AdminEntity();
		condition.setUsername(username);
		List<AdminEntity> admins = adminEntityMapper.selectByParam(condition, null, null, 0, 1, null, null, null);
        if (!CollectionUtils.isNullOrEmpty(admins)) {
            admin = admins.get(0);
        }
		return admin;
	}

	@Override
	public AdminEntity save(AdminEntity entity) {
		adminEntityMapper.insertSelective(entity);
		return entity;
	}

	@Override
	public boolean delAdmin(AdminEntity adminEntity) {

        AdminRoleEntity entity = new AdminRoleEntity();
        entity.setAdminId(adminEntity.getId());
        entityMapper.deleteByCondition(entity);
        return adminEntityMapper.deleteByPrimaryKey(adminEntity.getId()) > 0;
	}

	@Override
	public AdminEntity get(long id) {
		return adminEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean update(AdminEntity entity) {
		return adminEntityMapper.updateByPrimaryKeySelective(entity) > 0;
	}

	@Override
	public Page<AdminEntity> queryAdminByPage(Page<AdminEntity> page, Map conditions) {
		List<Long> ids = null;
		AdminEntity condition = new AdminEntity();
		if (conditions.get("username") != null)
			condition.setUsername(conditions.get("username").toString());
		if (conditions.get("status") != null)
			condition.setStatus(Integer.valueOf(conditions.get("status").toString()));
		int resultCount = adminEntityMapper.selectResultCountByParam(condition, null, null, ids);
		page.setTotal(resultCount);
		if (page.getTotal() > 0) {
			List<AdminEntity> items = adminEntityMapper.selectByParam(condition, null, null, null, null, "status", "desc", ids);
			page.setItems(items);
		} else
			page.setItems(new ArrayList<>(0));
		return page;
	}

	@Override
	public Map<Integer, String> userNameMap(Set<Long> userIdSet) {

		Map<Integer, String> result = Maps.newConcurrentMap();

		if (!CollectionUtils.isNullOrEmpty(userIdSet)) {
			OtAdminsExample example = new OtAdminsExample();
			OtAdminsExample.Criteria cnd = example.createCriteria();
			cnd.andIdIn(new ArrayList<>(userIdSet));
			List<AdminEntity> adminList = adminEntityMapper.selectByExample(example);
			if (!CollectionUtils.isNullOrEmpty(adminList)) {
				adminList.forEach(adminEntity -> {
					if (!result.containsKey(adminEntity.getId().intValue())) {
						result.put(adminEntity.getId().intValue(), adminEntity.getRealName());
					}
				});
			}
		}
		return result;
	}

	public List<Integer> userIdListByNameLike(String name) {

		OtAdminsExample adminExample = new OtAdminsExample();
		OtAdminsExample.Criteria adminCnd = adminExample.createCriteria();
		adminCnd.andRealnameLike(StringUtil.sqlLike(name));
		List<AdminEntity> adminList = adminEntityMapper.selectByExample(adminExample);
		List<Integer> adminIdList = Lists.newArrayList();
		if (!CollectionUtils.isNullOrEmpty(adminList)) {
			adminList.forEach(admin -> adminIdList.add(admin.getId().intValue()));
		}
		return adminIdList;
	}
}
