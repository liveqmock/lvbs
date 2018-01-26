package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.MaterialDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IMaterialService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.enums.db.CommonStatus;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.base.enums.db.CategoryType;
import com.daishumovie.dao.mapper.smallbronze.SbCommonCategoryMapper;
import com.daishumovie.dao.mapper.smallbronze.SbMaterialCategoryAppMapper;
import com.daishumovie.dao.mapper.smallbronze.SbMaterialContentMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/8/28 15:37.
 * 素材管理service
 */
@Service
public class MaterialService extends BaseService implements IMaterialService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialService.class);

    @Autowired
    private SbMaterialCategoryAppMapper relMapper;
    @Autowired
    private SbMaterialContentMapper materialMapper;
    @Autowired
    private SbCommonCategoryMapper categoryMapper;
    @Autowired
    private IAdminService adminService;

    @Override
    public ReturnDto<MaterialDto> paginate(ParamDto param, String name, String createTime, Integer categoryType, Integer categoryId, Integer appId, Integer isOnShelf) {

        try {
            SbMaterialContentExample example = new SbMaterialContentExample();
            SbMaterialContentExample.Criteria criteria = example.createCriteria();
            {
                if (StringUtil.isNotEmpty(name)) {
                    criteria.andNameLike(StringUtil.sqlLike(name));
                }
                if (StringUtil.isNotEmpty(createTime)) {
                    criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(createTime));
                }
                List<Integer> materialIdList = materialIdList(appId, categoryId, categoryType);
                if (null != materialIdList) {
                    if (!materialIdList.isEmpty()) {
                        criteria.andIdIn(materialIdList);
                    } else {
                        throw new ResultException("关联表中没有数据");
                    }
                }
                if (null != isOnShelf) {
                    criteria.andIsOnShelfEqualTo(isOnShelf);
                }
            }
            criteria.andStatusEqualTo(CommonStatus.normal.getValue());
            Page<MaterialDto> page = param.page();
            Long total = materialMapper.countByExample(example);
            List<MaterialDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setOrderByClause("create_time desc");
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                List<SbMaterialContent> materialList = materialMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(materialList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    materialList.forEach(material -> {
                        MaterialDto dto = new MaterialDto();
                        if (null != material.getOperatorId()) {
                            adminIdSet.add(Long.valueOf(material.getOperatorId()));
                        }
                        BeanUtils.copyProperties(material, dto);
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
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (ResultException e) {
            LOGGER.info("relation table have no data, so return empty -----");
            return new ReturnDto<>(null);
        } catch (Exception e) {
            LOGGER.info("material paginate error --- > \r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    public List<SbCommonCategory> categoryType(Integer type) {

        List<SbCommonCategory> categoryList = Lists.newArrayList();
        try {
            if (null != type) {
                SbCommonCategoryExample example = new SbCommonCategoryExample();
                SbCommonCategoryExample.Criteria criteria = example.createCriteria();
                criteria.andTypeEqualTo(type);
                criteria.andStatusEqualTo(CommonStatus.normal.getValue());
                categoryList = categoryMapper.selectByExample(example);
            }
        } catch (Exception e) {
            printException("material", "categoryType", e);
        }
        return categoryList;
    }

    @Override
    @Transactional
    public void save(SbMaterialContent material, Integer operatorId, CategoryType categoryType, App app, Integer categoryId) {

        try {
            //1.校验
            validate(material, false);
            //2.文件上传
            uploadFile(material, categoryType);
            //3.icon上传
            icon(material);
            //4.预览图上传
            preview(material);
            {
                material.setOperatorId(operatorId); //操作人
                material.setStatus(CommonStatus.normal.getValue());
                material.setCreateTime(new Date());
                material.setUpdateTime(material.getCreateTime());
                if (null == material.getDuration()) {
                    material.setDuration(0);
                }
            }
            //5.保存数据库
            materialMapper.insertSelective(material);
            //6.关联表保存
            relationTb(app, categoryId, material.getId(), categoryType);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("material", "save", e);
            throw new ResultException();
        }


    }

    @Override
    @Transactional
    public void update(SbMaterialContent material, Integer operatorId, Integer categoryId, App app) {

        try {
            //1.校验
            validate(material, true);
            //2.获取关联信息[1中已经校验关系表肯定存在,所以此处可以直接get(0)]
            SbMaterialCategoryApp rel = getRelListByMaterial(material.getId()).get(0);
            CategoryType categoryType = CategoryType.get(rel.getType());
            //2.文件上传
            uploadFile(material, categoryType);
            //3.icon上传
            icon(material);
            //4.预览图上传
            preview(material);
            //5.更新素材
            {
                material.setOperatorId(operatorId); //操作人
                material.setUpdateTime(new Date());
            }
            materialMapper.updateByPrimaryKeySelective(material);
            rel.setCategoryId(categoryId);
            rel.setAppId(app.getId());
            relMapper.updateByPrimaryKeySelective(rel);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("material", "update", e);
            throw new ResultException();
        }
    }

    @Override
    @Transactional
    public void delete(Integer id, Integer operatorId) {

        try {
            if (null == id) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbMaterialContent material = materialMapper.selectByPrimaryKey(id);
            if (null == material) {
                throw new ResultException("素材不存在");
            }
            material.setOperatorId(operatorId);
            material.setStatus(CommonStatus.invalid.getValue());
            material.setUpdateTime(new Date());
            materialMapper.updateByPrimaryKeySelective(material);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("material", "delete", e);
            throw new ResultException();
        }

    }

    @Override
    @Transactional
    public void offShelf(Integer id, Integer operatorId) {

        try {
            if (null == id) {
                throw new ResultException(ErrMsg.param_error);
            }
            updateStatus(id, operatorId, Whether.no);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("material", "offShelf", e);
            throw new ResultException();
        }
    }
    @Override
    @Transactional
    public void onShelf(Integer id, Integer operatorId) {

        try {
            if (null == id) {
                throw new ResultException(ErrMsg.param_error);
            }
            updateStatus(id, operatorId, Whether.yes);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("material", "onShelf", e);
            throw new ResultException();
        }
    }

    @Override
    protected void specificVerify(Object model, boolean isUpdate) {

        SbMaterialContent material = (SbMaterialContent) model;
        if (StringUtil.isEmpty(material.getName())) {
            throw new ResultException("名称不能为空");
        }
        if (StringUtil.isEmpty(material.getContentPath())) {
            throw new ResultException("请上传素材文件");
        }
        if (isUpdate) {
            if (null == material.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbMaterialContent dbMaterial = materialMapper.selectByPrimaryKey(material.getId());
            if (null == dbMaterial) {
                throw new ResultException("素材不存在");
            }
            List<SbMaterialCategoryApp> relList = getRelListByMaterial(material.getId());
            if (relList.isEmpty()) {
                throw new ResultException("关联数据不存在");
            }
        }
    }

    /**
     * 根据素材ID获取关系列表
     * @param material
     * @return
     */
    public List<SbMaterialCategoryApp> getRelListByMaterial(Integer material) {

        List<SbMaterialCategoryApp> relList = Lists.newArrayList();
        if (null != material) {
            SbMaterialCategoryAppExample example = new SbMaterialCategoryAppExample();
            SbMaterialCategoryAppExample.Criteria criteria = example.createCriteria();
            criteria.andMaterialIdEqualTo(material);
            relList = relMapper.selectByExample(example);
        }
        return relList;
    }

    private List<Integer> materialIdList(Integer appId, Integer categoryId, Integer categoryType) {

        SbMaterialCategoryAppExample example = new SbMaterialCategoryAppExample();
        SbMaterialCategoryAppExample.Criteria criteria = example.createCriteria();
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        if (null != categoryId) {
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if (null != categoryType) {
            criteria.andTypeEqualTo(categoryType);
        }
        if (criteria.getCriteria().isEmpty()) { //没有传入关联表相关的参数,则不加此条件
            return null;
        }
        List<SbMaterialCategoryApp> relList = relMapper.selectByExample(example);
        List<Integer> materialIdList = Lists.newArrayList();
        if (!CollectionUtils.isNullOrEmpty(relList)) {
            relList.forEach(rel -> materialIdList.add(rel.getMaterialId()));
        }
        return materialIdList;
    }

    /**
     * logo 处理
     *
     * @param material
     */
    private void icon(SbMaterialContent material) throws Exception {

        String icon = StringUtil.trim(material.getIcon());
        if (icon.length() > 0) {
            if (icon.contains(Configuration.temp_path)) { //重新上传的
                icon = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(icon, OSSClientUtil.upload_type.material_icon);
                material.setIcon(icon);
            }
        }
    }
    /**
     * logo 处理
     *
     * @param material
     */
    private void preview(SbMaterialContent material) throws Exception {

        String preview = StringUtil.trim(material.getPreviewUrl());
        if (preview.length() > 0) {
            if (preview.contains(Configuration.temp_path)) { //重新上传的
                preview = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(preview, OSSClientUtil.upload_type.material_preview);
                material.setPreviewUrl(preview);
            }
        }
    }

    /**
     * 文件上传
     *
     * @param material
     */
    private void uploadFile(SbMaterialContent material, CategoryType type) throws Exception {

        String contentPath = StringUtil.trim(material.getContentPath());
        if (contentPath.length() > 0) {
            if (contentPath.contains(Configuration.temp_path)) { //重新上传的
                if (null == type) {
                    throw new ResultException("上传素材是，类型不能为空");
                }
                contentPath = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(contentPath, OSSClientUtil.upload_type.getByCategoryType(type.name()));
                material.setContentPath(contentPath);
            }
        }
    }

    /**
     * 保存关联表
     * @param app
     * @param categoryId
     * @param materialId
     * @param categoryType
     */
    private void relationTb(App app, Integer categoryId, Integer materialId, CategoryType categoryType) {

        SbMaterialCategoryApp rel = new SbMaterialCategoryApp();
        rel.setAppId(app.getId());
        rel.setCategoryId(categoryId);
        rel.setMaterialId(materialId);
        rel.setType(categoryType.getValue());
        relMapper.insertSelective(rel);
    }

    /**
     * 修改状态
     * @param id
     * @param operatorId
     * @param whether
     */
    private void updateStatus(Integer id, Integer operatorId, Whether whether){

        SbMaterialContent material = materialMapper.selectByPrimaryKey(id);
        if (null == material) {
            throw new ResultException("素材不存在");
        }
        material.setOperatorId(operatorId);
        material.setIsOnShelf(whether.getValue());
        material.setUpdateTime(new Date());
        materialMapper.updateByPrimaryKeySelective(material);
    }
}
