package com.daishumovie.admin.service.auth.impl;

import com.daishumovie.admin.dto.LogDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.admin.service.auth.ILogService;
import com.daishumovie.dao.mapper.smallbronzeadmin.OtLogMapper;
import com.daishumovie.dao.model.auth.OtLog;
import com.daishumovie.dao.model.auth.OtLogExample;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/9/11 16:28.
 */
@Service
public class LogService implements ILogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private IAdminService adminService;

    @Autowired
    private OtLogMapper logMapper;

    @Override
    public ReturnDto<LogDto> paginate(LogDto log, ParamDto param) {

        try {
            OtLogExample example = new OtLogExample();
            OtLogExample.Criteria cnd = example.createCriteria();
            if (StringUtil.isNotEmpty(log.getOperationType())) {
                cnd.andOperationTypeEqualTo(log.getOperationType());
            }
            if (StringUtil.isNotEmpty(log.getOperationObject())) {
                cnd.andOperationObjectEqualTo(log.getOperationObject());
            }
            if (StringUtil.isNotEmpty(log.getOperationResult())) {
                cnd.andOperationResultEqualTo(log.getOperationResult());
            }
            if (StringUtil.isNotEmpty(log.getOperationParam())) {
                cnd.andOperationParamLike(StringUtil.sqlLike(log.getOperationParam()));
            }
            if (StringUtil.isNotEmpty(log.getParamTime())) {
                cnd.andOperationTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(log.getParamTime()));
            }
            if (StringUtil.isNotEmpty(log.getOperatorName())) {
                List<Integer> userIdList = adminService.userIdListByNameLike(log.getOperatorName());
                if (!CollectionUtils.isNullOrEmpty(userIdList)) {
                    cnd.andOperatorIn(userIdList);
                } else {
                    return new ReturnDto<>(null);
                }
            }
            Long total = logMapper.countByExample(example);
            List<LogDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                example.setOrderByClause("operation_time desc");
                List<OtLog> logList = logMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(logList)) {
                    Set<Long> userIdSet = Sets.newHashSet();
                    logList.forEach(dsmLog -> {
                        LogDto dto = new LogDto();
                        BeanUtils.copyProperties(dsmLog, dto);
                        dtoList.add(dto);
                        userIdSet.add(Long.valueOf(dsmLog.getOperator()));
                    });
                    Map<Integer, String> userNameMap = adminService.userNameMap(userIdSet);
                    dtoList.forEach(dto ->{
                        Integer operator = dto.getOperator();
                        if (null != operator && userNameMap.containsKey(operator)) {
                            dto.setOperatorName(userNameMap.get(operator));
                        }
                    });
                }
            }
            Page<LogDto> page = param.page();
            page.setItems(dtoList);
            page.setTotal(total.intValue());
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("log paginate error ===>" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }
}
