package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.ReportDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.base.enums.db.ReportStatusEnum;
import com.daishumovie.base.enums.db.ReportType;

/**
 * Created by yang on 2017/9/21.
 */
public interface IReportService {

    ReturnDto<ReportDto> paginate(ParamDto param, ReportType type, String createTime, Integer auditStatus, Integer appId);

    void passOrNot(Integer id,Integer contentId, Integer reason,ReportType type, ReportStatusEnum auditStatus, Integer operatorId);
}
