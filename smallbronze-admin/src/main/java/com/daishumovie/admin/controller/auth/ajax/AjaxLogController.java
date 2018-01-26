package com.daishumovie.admin.controller.auth.ajax;

import com.daishumovie.admin.dto.LogDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.service.auth.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by feiFan.gou on 2017/9/11 16:42.
 */
@RestController
@RequestMapping("/ajax/log/")
public class AjaxLogController {

    @Autowired
    private ILogService logService;

    @RequestMapping(value = "paginate", method = RequestMethod.POST)
    public ReturnDto<LogDto> paginate(LogDto log, ParamDto paramDto) {
        return logService.paginate(log, paramDto);
    }
}
