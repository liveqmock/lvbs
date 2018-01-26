package com.daishumovie.admin.service.auth;

import com.daishumovie.admin.dto.LogDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;

/**
 * Created by feiFan.gou on 2017/9/11 16:27.
 */
public interface ILogService {

    ReturnDto<LogDto> paginate(LogDto log, ParamDto param);

}
