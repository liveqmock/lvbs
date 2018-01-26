package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.CategoryDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.ICategoryService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.CommonStatus;
import com.daishumovie.dao.mapper.smallbronze.SbCommonCategoryMapper;
import com.daishumovie.dao.mapper.smallbronze.SbMaterialCategoryAppMapper;
import com.daishumovie.dao.mapper.smallbronze.SbMaterialContentMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/8/31 18:30.
 */
@Service
public class CategoryService extends BaseService implements ICategoryService {


    @Autowired
    private SbCommonCategoryMapper categoryMapper;
    @Autowired
    private SbMaterialCategoryAppMapper relMapper;
    @Autowired
    private SbMaterialContentMapper materialMapper;
    @Autowired
    private IAdminService adminService;

    @Override
    public ReturnDto<CategoryDto> paginate(ParamDto param, String name, Integer type, String createTime) {

        try {
            SbCommonCategoryExample example = new SbCommonCategoryExample();
            SbCommonCategoryExample.Criteria criteria = example.createCriteria();
            if (StringUtil.isNotEmpty(name)) {
                criteria.andNameLike(StringUtil.sqlLike(name));
            }
            if (null != type) {
                criteria.andTypeEqualTo(type);
            }
            if (StringUtil.isNotEmpty(createTime)) {
                criteria.andCreateTimeGreaterThan(DateUtil.todayZeroClock(createTime));
            }
            criteria.andStatusEqualTo(CommonStatus.normal.getValue());
            Long total = categoryMapper.countByExample(example);
            List<CategoryDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setOrderByClause("create_time desc");
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                List<SbCommonCategory> categoryList = categoryMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(categoryList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    categoryList.forEach(category -> {
                        CategoryDto dto = new CategoryDto();
                        BeanUtils.copyProperties(category, dto);
                        if (null != category.getOperatorId()) {
                            adminIdSet.add(Long.valueOf(category.getOperatorId()));
                        }
                        dtoList.add(dto);
                    });
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    dtoList.forEach(dto -> {
                        if (null != dto.getOperatorId()) {
                            dto.setOperatorName(adminNameMap.get(dto.getOperatorId()));
                        }
                    });
                }
            }
            Page<CategoryDto> page = param.page();
            page.setItems(dtoList);
            page.setTotal(total.intValue());
            return new ReturnDto<>(page);
        } catch (Exception e) {
            printException("category", "paginate", e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    @Transactional
    public void save(SbCommonCategory category, Integer operatorId) {

        try {
            //1.校验
            validate(category,false);
            //icon上传
            icon(category);
            //3.获取特定规则的ID

            category.setId(getSameTypeId(category.getType()));
            {
                category.setCreateTime(new Date());
                category.setUpdateTime(category.getCreateTime());
                category.setOperatorId(operatorId);
            }
            categoryMapper.insertSelective(category);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("category", "save", e);
            throw new ResultException();
        }
    }

    @Override
    @Transactional
    public void update(SbCommonCategory category, Integer operatorId) {

        try {
            //1.校验
            validate(category, false);
            //2.上传icon
            icon(category);
            {
                category.setUpdateTime(new Date());
                category.setOperatorId(operatorId);
            }
            //3.更新数据库
            categoryMapper.updateByPrimaryKeySelective(category);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("category", "update", e);
            throw new ResultException();
        }

    }

    @Override
    public void delete(Integer id) {

        try {
            { //删除分类
                if (null == id) {
                    throw new ResultException(ErrMsg.param_error);
                }
                SbCommonCategory category = categoryMapper.selectByPrimaryKey(id);
                if (null == category) {
                    throw new ResultException("素材分类不存在");
                }
                category.setStatus(CommonStatus.invalid.getValue());
                category.setUpdateTime(new Date());
                categoryMapper.updateByPrimaryKey(category);
            }
            //删除分类下的素材
            {
                deleteMaterialByCategory(id);
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("category", "delete", e);
            throw new ResultException();
        }
    }

    @Override
    protected void specificVerify(Object model, boolean isUpdate) {

        SbCommonCategory category = (SbCommonCategory) model;

        if (StringUtil.isEmpty(category.getName())) {
            throw new ResultException("分类名称不能为空");
        }

        if (null == category.getType()) {
            throw new ResultException("分类类型不能为空");
        }

        SbCommonCategoryExample example = new SbCommonCategoryExample();
        SbCommonCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(StringUtil.trim(category.getName()));
        criteria.andTypeEqualTo(category.getType());
        if (isUpdate) {
            if (null == category.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbCommonCategory dbCategory = categoryMapper.selectByPrimaryKey(category.getId());
            if (null == dbCategory) {
                throw new ResultException("分类不存在");
            }
            criteria.andIdNotEqualTo(dbCategory.getId());
        }
        //同一类型下不可以有同一个分类
        Long count = categoryMapper.countByExample(example);
        if (count > 0) {
            throw new ResultException("分类名称【" + category.getName() + "】重复");
        }
    }

    /**
     * logo 处理
     *
     * @param category
     */
    private void icon(SbCommonCategory category) throws Exception {

        String icon = StringUtil.trim(category.getIcon());
        if (icon.length() > 0) {
            if (icon.contains(Configuration.temp_path)) { //重新上传的
                icon = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(icon, OSSClientUtil.upload_type.material_category_icon);
                category.setIcon(icon);
            }
        }
    }

    /**
     * 获取指定规则的ID[type * 1000起]
     * @param type
     * @return
     */
    private Integer getSameTypeId(Integer type) {

        SbCommonCategoryExample example = new SbCommonCategoryExample();
        SbCommonCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        example.setOrderByClause("id desc");
        List<SbCommonCategory> sameTypeList = categoryMapper.selectByExample(example);
        Integer id;
        if (CollectionUtils.isNullOrEmpty(sameTypeList)) {
            id = type * 1000;
        } else {
            SbCommonCategory last = sameTypeList.get(0);
            id = last.getId() + 1;
        }
        return id;
    }

    /**
     * 根据分类ID删除素材
     * @param categoryId
     */
    private void deleteMaterialByCategory(Integer categoryId) {

        SbMaterialCategoryAppExample example = new SbMaterialCategoryAppExample();
        SbMaterialCategoryAppExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<SbMaterialCategoryApp> relList = relMapper.selectByExample(example);
        if (!CollectionUtils.isNullOrEmpty(relList)) {
            List<Integer> materialIdList = Lists.newArrayList();
            relList.forEach(rel -> materialIdList.add(rel.getMaterialId()));
            if (!CollectionUtils.isNullOrEmpty(materialIdList)) {
                SbMaterialContentExample materialExample = new SbMaterialContentExample();
                SbMaterialContentExample.Criteria materialCriteria = materialExample.createCriteria();
                materialCriteria.andIdIn(materialIdList);
                List<SbMaterialContent> materialList = materialMapper.selectByExample(materialExample);
                if (!CollectionUtils.isNullOrEmpty(materialList)) {
                    materialList.forEach(material -> {
                        material.setStatus(CommonStatus.invalid.getValue());
                        materialMapper.updateByPrimaryKeySelective(material);
                    });
                }

            }
        }
    }
}
