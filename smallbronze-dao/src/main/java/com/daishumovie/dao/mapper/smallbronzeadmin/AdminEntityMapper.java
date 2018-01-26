package com.daishumovie.dao.mapper.smallbronzeadmin;


import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.OtAdminsExample;

import java.util.List;

public interface AdminEntityMapper extends GenericMapper<Long, AdminEntity> {

    List<AdminEntity> selectByExample(OtAdminsExample example);
}