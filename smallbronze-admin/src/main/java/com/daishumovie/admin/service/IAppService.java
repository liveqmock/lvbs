package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.DsmAppVersionDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.model.DsmAppVersion;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/5/12 14:04.
 */
public interface IAppService {


    public ReturnDto<DsmAppVersionDto> getDsmAppVersionByConditions(ParamDto param,Integer appId, String version_num, Integer plat);

    public Response save(DsmAppVersion record);

    public Response update(DsmAppVersion record);

    public DsmAppVersionDto getDsmAppVersionDtoById(Integer id);

    public List<DsmAppVersion> getDsmAppVersionList(Integer appId,Integer plat);
}
