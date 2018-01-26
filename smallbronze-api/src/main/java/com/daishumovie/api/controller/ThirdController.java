package com.daishumovie.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author zhuruisong on 2017/10/9
 * @since 1.0
 */
@Slf4j
@RequestMapping
@RestController
public class ThirdController {

    //用户授权 微博方调用
    @RequestMapping("/weibo/call/agree")
    public void call(HttpServletRequest request){
        log.info("微博用户授权");
        print(request);

    }

    //用户取消授权 微博方调用
    @RequestMapping("/weibo/call/cancel")
    public void cancleCall(HttpServletRequest request){
        log.info("微博用户取消授权");
        print(request);
    }

    private void print(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            log.info("hear参，{}={}", s, request.getHeader(s));
        }
        String method = request.getMethod();
        log.info("method方式，{}", method);

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            log.info("入参，{}={}", s, request.getParameter(s));
        }
    }
}
