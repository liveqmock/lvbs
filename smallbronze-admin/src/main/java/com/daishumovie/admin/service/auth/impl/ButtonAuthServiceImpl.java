package com.daishumovie.admin.service.auth.impl;

import com.daishumovie.admin.dto.ButtonDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.auth.IButtonAuthService;
import com.daishumovie.dao.mapper.smallbronzeadmin.OtButtonAuthorityMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.OtButtonAuthorityRoleMapper;
import com.daishumovie.dao.mapper.smallbronzeadmin.ResourceEntityMapper;
import com.daishumovie.dao.model.auth.*;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/7/14 14:13.
 */
@Service
@Log4j
public class ButtonAuthServiceImpl implements IButtonAuthService {

    @Autowired
    private OtButtonAuthorityMapper authorityMapper;
    @Autowired
    private OtButtonAuthorityRoleMapper roleMapper;
    @Autowired
    private ResourceEntityMapper resourceMapper;

    public static final String top_id = "top";

    @Override
    public List<ButtonDto> buttonList(String resourceId, Long roleId) {

        List<ButtonDto> buttonList = Lists.newArrayList();
        if (StringUtil.isNotEmpty(resourceId)) {
            OtButtonAuthorityExample example = new OtButtonAuthorityExample();
            OtButtonAuthorityExample.Criteria cnd = example.createCriteria();
            cnd.andResourceIdEqualTo(Long.valueOf(resourceId));
           List<OtButtonAuthority> authorityList = authorityMapper.selectByExample(example);
            if (!authorityList.isEmpty()) {
                Map<String, List<ButtonDto>> data = Maps.newHashMap();
                authorityList.forEach(authority ->{
                    ButtonDto tempDto = new ButtonDto();
                    BeanUtils.copyProperties(authority, tempDto);
                    String parentId = StringUtil.trim(authority.getParentId());
                    if (data.containsKey(parentId)) {
                        data.get(parentId).add(tempDto);
                    } else {
                        List<ButtonDto> temp = Lists.newArrayList();
                        temp.add(tempDto);
                        data.put(parentId, temp);
                    }
                });
                buttonList = data.get(top_id);
                List<Integer> buttonIdList = buttonIdList(roleId);
                if (!buttonList.isEmpty()) {
                    buttonList.forEach(buttonDto -> {
                        if (buttonIdList.contains(buttonDto.getId())) {
                            buttonDto.setHas(true);
                        }
                        buttonDto.setChildren(recursion(data, buttonIdList, String.valueOf(buttonDto.getId())));
                    });
                }
            }
        }
        return buttonList;
    }

    @Override
    public void update(OtButtonAuthority authority) {
        try {
            if (null == authority) {
                throw new ResultException(ErrMsg.param_error);
            }
            if (null == authority.getId()) {
                validate(authority, false);
                authorityMapper.insertSelective(authority);
            } else {
                validate(authority, true);
                authorityMapper.updateByPrimaryKeySelective(authority);
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            log.info("update/insert button error --->\r" + e.getMessage(), e);
            throw new ResultException();
        }
    }

    @Override
    public void delete(Integer id) {

        try {
            if (null == id) {
                throw new ResultException(ErrMsg.param_error);
            }
            OtButtonAuthority dbAuthority = authorityMapper.selectByPrimaryKey(id);
            if (null == dbAuthority) {
                throw new ResultException("权限按钮不存在");
            }
            recursion2delete(id);
            authorityMapper.deleteByPrimaryKey(id);
            deleteRelation(id);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            log.info("delete button error --->\r" + e.getMessage(), e);
            throw new ResultException();
        }
    }

    @Override
    public List<ButtonDto> menuList(Long roleId) {

        List<ButtonDto> menuList = Lists.newArrayList();
        List<ResourceEntity> resourceList = resourceMapper.queryTopMenu();
        if (!CollectionUtils.isNullOrEmpty(resourceList)) {
            resourceList.forEach(resourceEntity -> {
                ButtonDto dto = new ButtonDto();
                dto.setButtonName(StringUtil.trim(resourceEntity.getName()));
                dto.setUuid(StringUtil.trim(resourceEntity.getUuid()));
                menuList.add(dto);
            });
            menuList.forEach(menu -> menu.setChildren(functionMenuList(roleId, menu.getUuid())));
        }
        return menuList;
    }

    @Override
    public void save(Long roleId, Integer[] buttonIds) {

        try {
            if (null == roleId || CollectionUtils.arrayIsNullOrEmpty(buttonIds)) {
                throw new ResultException(ErrMsg.param_error);
            }
            //先删除旧的数据
            OtButtonAuthorityRoleExample example = new OtButtonAuthorityRoleExample();
            OtButtonAuthorityRoleExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(roleId);
            roleMapper.deleteByExample(example);
            for (Integer buttonId : buttonIds) {
                OtButtonAuthorityRole authorityRole = new OtButtonAuthorityRole();
                authorityRole.setButtonAuthorityId(buttonId);
                authorityRole.setRoleId(roleId);
                roleMapper.insertSelective(authorityRole);
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            log.info("button authority save error --->\r" + e.getMessage(), e);
            throw new ResultException();
        }
    }

    private void recursion2delete(Integer id) {

        OtButtonAuthorityExample example = new OtButtonAuthorityExample();
        OtButtonAuthorityExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(String.valueOf(id));
        List<OtButtonAuthority> authorityList = authorityMapper.selectByExample(example);
        if (!CollectionUtils.isNullOrEmpty(authorityList)) {
            OtButtonAuthority authority = authorityList.get(0);
            recursion2delete(authority.getId());
            authorityMapper.deleteByPrimaryKey(authority.getId());
            deleteRelation(authority.getId());
        }
    }

    private void deleteRelation(Integer authorityId) {

        OtButtonAuthorityRoleExample roleExample = new OtButtonAuthorityRoleExample();
        OtButtonAuthorityRoleExample.Criteria roleExampleCriteria = roleExample.createCriteria();
        roleExampleCriteria.andButtonAuthorityIdEqualTo(authorityId);
        roleMapper.deleteByExample(roleExample);
    }

    private List<ButtonDto> functionMenuList(Long roleId, String parentId) {

        List<ButtonDto> children = Lists.newArrayList();
        if (StringUtil.isNotEmpty(parentId)) {
            List<ResourceEntity> resourceList = resourceMapper.queryFunctionMenuByOrigin(StringUtil.sqlLike(parentId));
            if (!CollectionUtils.isNullOrEmpty(resourceList)) {
                resourceList.forEach(resourceEntity -> {
                    ButtonDto dto = new ButtonDto();
                    dto.setButtonName(resourceEntity.getName());
                    dto.setId(resourceEntity.getId().intValue());
                    children.add(dto);
                });
            }
            children.forEach(buttonDto -> buttonDto.setChildren(buttonList(String.valueOf(buttonDto.getId()),roleId)));
        }
        return children;
    }

    private void validate(OtButtonAuthority authority,boolean isEdit) {

        if (isEdit) {
            if (null == authority.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
            if (StringUtil.isEmpty(authority.getParentId())) {
                authority.setParentId(null); //不做更新
            }
            OtButtonAuthority dbAuthority = authorityMapper.selectByPrimaryKey(authority.getId());
            if (null == dbAuthority) {
                throw new ResultException("权限按钮不存在");
            }
        } else {
            if (StringUtil.isEmpty(authority.getParentId())) {
                throw new ResultException(ErrMsg.param_error);
            }
        }
        if (StringUtil.isEmpty(authority.getClassName())) {
            throw new ResultException("类名不能为空");
        }
        OtButtonAuthorityExample example = new OtButtonAuthorityExample();
        OtButtonAuthorityExample.Criteria cnd = example.createCriteria();
        cnd.andClassNameEqualTo(authority.getClassName());
        if (isEdit) {
            cnd.andIdNotEqualTo(authority.getId());
        }
        if (authorityMapper.countByExample(example) > 0) {
            throw new ResultException("类名重复");
        }
        if (StringUtil.isEmpty(authority.getButtonName())) {
            throw new ResultException("按钮名称不能为空");
        }
        if (null == authority.getResourceId()) {
            throw new ResultException(ErrMsg.param_error);
        }
    }

    private List<ButtonDto> recursion(Map<String, List<ButtonDto>> data,List<Integer> buttonIdList, String parentId) {

        List<ButtonDto> children = data.get(parentId);
        if (!CollectionUtils.isNullOrEmpty(children)) {
            children.forEach(buttonDto -> {
                if (buttonIdList.contains(buttonDto.getId())) {
                    buttonDto.setHas(true);
                }
                buttonDto.setChildren(recursion(data, buttonIdList, String.valueOf(buttonDto.getId())));
            });
        }
        return children;
    }

    private List<Integer> buttonIdList(Long roleId) {

        List<Integer> buttonIdList = Lists.newArrayList();
        if (null != roleId) {
            OtButtonAuthorityRoleExample example = new OtButtonAuthorityRoleExample();
            OtButtonAuthorityRoleExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(roleId);
            List<OtButtonAuthorityRole> authorityList = roleMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(authorityList)) {
                authorityList.forEach(authority -> buttonIdList.add(authority.getButtonAuthorityId()));
            }
        }
        return buttonIdList;
    }
}
