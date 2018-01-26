package com.daishumovie.admin.aop;

import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.util.RequestUtil;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationResult;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.mapper.smallbronzeadmin.OtLogMapper;
import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.OtLog;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/9/9 17:23.
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
    public static final String log_required = "log_required";
    public static final String log_type = "log_type";
    public static final String log_object = "log_object";

    @Autowired
    private OtLogMapper logMapper;

    @Pointcut("execution(* com.daishumovie.admin.controller.ajax.Ajax*Controller.*(..)) || execution(* com.daishumovie.admin.controller.auth.ajax.Ajax*Controller.*(..))")
    public void ajaxRequest() {}

    @Around("ajaxRequest()")
    public Object doLog(ProceedingJoinPoint point) throws Throwable {

        long startTime = System.currentTimeMillis();
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
        HttpServletRequest request = requestAttributes.getRequest();
        AdminEntity admin = SessionUtil.getLoginAdmin(request);
        Object result = point.proceed();
        if (null == admin) {
            admin = SessionUtil.getLoginAdmin(request);
            //登录失败处理
            if (null == admin) {
                admin = new AdminEntity();
                admin.setId(-1L);
                admin.setRealName(request.getParameter("admin_name"));
            }
        }
        if (null != request && null != result) {
            boolean logRequired = false;
            if (null != request.getAttribute(log_required)) {
                logRequired = (boolean) request.getAttribute(log_required);
            }
            if (RequestUtil.isAjax(request) && logRequired) { //异步请求&&需要记录日志
                try {
                    Map<String, Object> parameter = Maps.newHashMap();
                    parameter.put("uri", StringUtil.trim(request.getRequestURI()));
                    Map<String, String[]> paramMap = new HashMap<>(request.getParameterMap());//
                    if (paramMap.containsKey("instruction")) {
                        paramMap.remove("instruction");
                    }
                    parameter.put("params", paramMap);
                    OperationType type = (OperationType) request.getAttribute(log_type);
                    OperationObject object = (OperationObject) request.getAttribute(log_object);
                    OperationResult opResult = OperationResult.success;
                    String ip = StringUtil.trim(RequestUtil.getClientIp(request));
                    JSONResult jsonResult = (JSONResult) result;
                    String errorMsg = StringUtil.empty;
                    if (jsonResult.getStatus().equals(JSONResult.STATUS.FAIL)) {
                        opResult = OperationResult.fail;
                        errorMsg = jsonResult.getMsg();
                    }
                    //保存数据库
                    saveLog(object, type, opResult, parameter, startTime, ip, admin, errorMsg);
                    //清除request中的信息
                    removeLogInfo(request);
                } catch (Exception e) {
                    LOGGER.info("log_aspect doLog error ===>\r" + e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /**
     * 插入数据库
     *
     * @param object    操作对象
     * @param type      操作类型
     * @param result    操作结果
     * @param parameter 参数
     * @param startTime 开始时间
     * @param ip        客户端IP
     * @param admin     用户
     */
    private void saveLog(OperationObject object, OperationType type, OperationResult result,
                         Map<String, Object> parameter, long startTime, String ip, AdminEntity admin, String errorMsg) {

        OtLog log = new OtLog();
        log.setOperationObject(object.name());
        log.setOperationType(type.name());
        log.setConsumeTime((int) (System.currentTimeMillis() - startTime));
        log.setIp(ip);
        log.setOperator(admin.getId().intValue());
        log.setOperationParam(FastJsonUtils.collectToString(parameter));
        log.setOperationTime(new Date());
        log.setOperationResult(result.name());
        String remark = String.format("【%s】在【%s】,对【%s】进行了【%s】操作,结果为【%s】,",
                admin.getRealName(),
                DateUtil.BASIC.format(log.getOperationTime()),
                OperationObject.getValue(log.getOperationObject()),
                OperationType.getValue(log.getOperationType()),
                OperationResult.getValue(log.getOperationResult())
        );
        if (log.getOperationType().equals(OperationType.login.name()) ||
                log.getOperationType().equals(OperationType.upload.name())) {
            remark = String.format("【%s】在【%s】,进行了【%s】操作,结果为【%s】,",
                    admin.getRealName(),
                    DateUtil.BASIC.format(log.getOperationTime()),
                    OperationType.getValue(log.getOperationType()),
                    OperationResult.getValue(log.getOperationResult())
            );
        }
        if (OperationResult.fail.name().equals(log.getOperationResult())) {
            remark += "错误原因为【" + errorMsg + "】";
        }
        remark += "耗时【" + log.getConsumeTime() + "】毫秒。";
        log.setRemark(remark);
        logMapper.insertSelective(log);
    }

    private void removeLogInfo(HttpServletRequest request) {

        request.removeAttribute(log_required);
        request.removeAttribute(log_type);
        request.removeAttribute(log_object);
    }
}
