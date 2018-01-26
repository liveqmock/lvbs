package com.daishumovie.api.configuration;

import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.model.Response;
import com.daishumovie.utils.FastJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ControllerAdvice  控制层的切面 捕获异常
 * @author zhuruisong on 2017-05-24 14:10:03
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void aliyuncsClientException(HttpServletRequest req, HttpServletResponse response ,MissingServletRequestParameterException e){
        print(response, FastJsonUtils.toJSONString(new Response(RespStatusEnum.PARAM_FAIL)));
    }

    @ExceptionHandler(com.aliyuncs.exceptions.ClientException.class)
    public void aliyuncsClientException(HttpServletRequest req, HttpServletResponse response ,com.aliyuncs.exceptions.ClientException e){
        log.info("SMS ERROR: return{\"status\": " + e.getErrCode() + ",\"desc\": \"" + e.getErrMsg() + "\"}");
        print(response, FastJsonUtils.toJSONString(new Response<>(RespStatusEnum.SEND_SMS_LIMIT)));
    }

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, HttpServletResponse response , Exception e)  {
        log.error("异常信息", e);
        print(response, FastJsonUtils.toJSONString(new Response(RespStatusEnum.ERROR)));
    }

    private void print(HttpServletResponse response,String msg){
        response.setCharacterEncoding("UTF-8");
        try {
            log.info("数据返回，{}",msg);
            response.getWriter().write(msg);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        log.info("异常捕获，{}", statusCode);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}