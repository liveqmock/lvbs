package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.MaterialDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.enums.db.CategoryType;
import com.daishumovie.dao.model.SbCommonCategory;
import com.daishumovie.dao.model.SbMaterialCategoryApp;
import com.daishumovie.dao.model.SbMaterialContent;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/8/28 15:32.
 */
public interface IMaterialService {


    ReturnDto<MaterialDto> paginate(ParamDto param, String name, String createTime,Integer categoryType, Integer categoryId, Integer appId, Integer isOnShelf);

    List<SbCommonCategory> categoryType(Integer type);

    void save(SbMaterialContent material, Integer operatorId, CategoryType categoryType, App app, Integer categoryId);

    void update(SbMaterialContent material, Integer operatorId, Integer categoryId, App app);

    void delete(Integer id, Integer operatorId);

    void offShelf(Integer id, Integer operatorId);

    void onShelf(Integer id, Integer operatorId);

    List<SbMaterialCategoryApp> getRelListByMaterial(Integer material);
}
