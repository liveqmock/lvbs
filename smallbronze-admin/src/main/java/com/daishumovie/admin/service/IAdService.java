package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.AdDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.dao.model.SbAd;

import java.util.List;

public interface IAdService  {


    ReturnDto<AdDto> paginate(ParamDto param, String name, String createTime, Integer status, Integer orders);

    void save(SbAd ad, Integer operatorId);

    void update(SbAd ad, Integer operatorId);

    void delete(Integer id, Integer operatorId);
}
