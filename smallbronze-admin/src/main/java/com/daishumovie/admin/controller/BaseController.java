package com.daishumovie.admin.controller;


import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.dao.mapper.smallbronzeadmin.AdminRoleEntityMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.OtButtonAuthorityMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.OtButtonAuthorityRoleMapper;
import com.daishumovie.dao.model.auth.*;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public abstract class BaseController {

	private @Autowired OtButtonAuthorityMapper authorityMapper;
	private @Autowired AdminRoleEntityMapper roleMapper;
	private @Autowired OtButtonAuthorityRoleMapper buttonAuthorityRoleMapper;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}


	protected  <T> Page<T> buildPage(HttpServletRequest request) {
		int iDisplayStart = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		int iDisplayLength = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		int pageIndex = 1;
		if (iDisplayLength > 0)
			pageIndex = iDisplayStart;
		return new Page<>(pageIndex, iDisplayLength);
	}


	protected final <T> ReturnDto pagerResponse(Page<T> page) {

		return new ReturnDto<>(page);
	}


	/**
	 * JSON序列化工具
	 */
	protected final static ObjectMapper JSON_SERIALIZER = new ObjectMapper();
	static {
		JSON_SERIALIZER.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		JSON_SERIALIZER.getSerializationConfig().withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
	}


	public List<OtButtonAuthority> authorityList(UserInfo userInfo) {

		List<OtButtonAuthority> authorityList = Lists.newArrayList();
		if (null != userInfo) {
			AdminEntity entity = userInfo.getAdmin();
			AdminRoleEntity role = new AdminRoleEntity();
			role.setAdminId(entity.getId());
			List<AdminRoleEntity> roleList = roleMapper.selectByEntity(role); //获取当前用户的所有角色
			List<Long> roleIdList = Lists.newArrayList();
			if (!CollectionUtils.isNullOrEmpty(roleList)) {
				roleList.forEach(roleEntity ->roleIdList.add(roleEntity.getRoleId())); //获取角色ID
				OtButtonAuthorityRoleExample example = new OtButtonAuthorityRoleExample();
				OtButtonAuthorityRoleExample.Criteria cnd = example.createCriteria();
				cnd.andRoleIdIn(roleIdList);

				OtButtonAuthorityExample authorityExample = new OtButtonAuthorityExample();
				List<OtButtonAuthorityRole> authorityRoleList = buttonAuthorityRoleMapper.selectByExample(example); //获取角色ID对应的button
				if (!CollectionUtils.isNullOrEmpty(authorityRoleList)) {
					OtButtonAuthorityExample.Criteria authCnd = authorityExample.createCriteria();
					Set<Integer> buttonAuthoritySet = Sets.newHashSet();
					authorityRoleList.forEach(otButtonAuthorityRole -> buttonAuthoritySet.add(otButtonAuthorityRole.getButtonAuthorityId()));
					authCnd.andIdNotIn(Lists.newArrayList(buttonAuthoritySet));
				}
				authorityList = authorityMapper.selectByExample(authorityExample);
			}
		}
		return authorityList;
	}
}
