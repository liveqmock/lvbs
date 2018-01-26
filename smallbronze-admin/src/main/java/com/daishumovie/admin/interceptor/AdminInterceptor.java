package com.daishumovie.admin.interceptor;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.aop.LogAspect;
import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.util.RequestUtil;
import com.daishumovie.admin.util.SessionUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by feiFan.gou on 2017/7/13 15:03.
 */
public class AdminInterceptor implements HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod method = (HandlerMethod) handler;
        try { //权限部分
            if (null != SessionUtil.getLoginUserInfo(request) && request.getMethod().equalsIgnoreCase("GET") && !handler.getClass().equals(ParameterizableViewController.class)) {
                BaseController controller = (BaseController) method.getBean();
                request.setAttribute("authority_list", controller.authorityList(SessionUtil.getLoginUserInfo(request)));
            }
        } catch (Exception ignored) {}

        { //是否需要通过logAop记录日志,再此做标识
            if (RequestUtil.isAjax(request)) {
                LogConfig logConfig = method.getMethodAnnotation(LogConfig.class);
                if (null != logConfig) {
                    request.setAttribute(LogAspect.log_required, logConfig.required());
                    request.setAttribute(LogAspect.log_type, logConfig.type());
                    request.setAttribute(LogAspect.log_object, logConfig.object());
                }
            }
        }
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {


    }
}
