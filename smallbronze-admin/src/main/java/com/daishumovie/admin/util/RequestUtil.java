package com.daishumovie.admin.util;

import com.daishumovie.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by feiFan.gou on 2017/9/11 15:02.
 */
public class RequestUtil {

    /**
     * 获取客户端IP
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {

        String clientIp = request.getHeader("X-Real-IP");
        if (StringUtil.isEmpty(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("x-forwarded-for");
        }
        if (StringUtil.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(clientIp) || "localhost".equals(clientIp)) {
            try {
                clientIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return clientIp;
    }

    /**
     * 是否是异步请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {

        String actionKey = request.getServletPath();
        boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        boolean ajaxFlag = actionKey.contains("ajax");

        return ajax || ajaxFlag;
    }
}
