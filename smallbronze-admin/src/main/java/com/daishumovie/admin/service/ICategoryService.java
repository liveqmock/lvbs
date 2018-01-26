package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.CategoryDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.dao.model.SbCommonCategory;

/**
 * Created by feiFan.gou on 2017/8/31 18:26.
 */
public interface ICategoryService {

    ReturnDto<CategoryDto> paginate(ParamDto param, String name, Integer type, String createTime);

    void save(SbCommonCategory category,Integer operatorId);

    void update(SbCommonCategory category,Integer operatorId);

    void delete(Integer id);
}
