package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.PushTaskDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.dao.model.SbPushTask;

import java.util.Map;

/**
 * Created by feiFan.gou on 2017/10/16 11:02.
 */
public interface IPushService {

    void push(SbPushTask task, Integer operator);

    void cancelSchedule(Integer taskId, Integer operatorId);

    ReturnDto<PushTaskDto> paginate(ParamDto param, String pushTime, Integer status, String alert, Integer targetType, Integer platform);

    String getAlert(Integer targetType, String targetId);
}
