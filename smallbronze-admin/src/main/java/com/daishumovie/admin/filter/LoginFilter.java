package com.daishumovie.admin.filter;

import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.util.RequestUtil;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

    private static final String login_uri = "/ajax/admin/login";

    private static final String login_page = "/admin/login";

    private static final String index_uri = "/";

    private static final Set<String> filter_prefix = Sets.newHashSet(
            "/js", "/images", "/css", "/plugin", "/temporary", login_uri,"/syndownload"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        LOGGER.info("login filter init ----");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = StringUtil.trim(request.getRequestURI());
        boolean isFilter = false; //是否需要过滤(默认不需要,就是任何URL都拦截)
        for (String prefix : filter_prefix) { //除去静态资源,以及登录相关
            if (uri.contains(prefix)) {
                isFilter = true;
                break;
            }
        }
        if (!isFilter || login_page.equals(uri)) {
            LOGGER.info("request-uri ===> " + uri + " [" + request.getMethod() + "]");
            if (SessionUtil.isLogin(request)) { //已经登录
                if (login_page.equals(uri)) { //登录页跳转首页
                    response.sendRedirect(index_uri);
                } else {
                    chain.doFilter(request, response);
                }
            } else { //未登录
                if (login_page.equals(uri)) {
                    chain.doFilter(request, response);
                } else {
                    if (RequestUtil.isAjax(request)) {
                        jsonResult(response, JSONResult.STATUS.UN_LOGIN);
                    } else {
                        response.sendRedirect(login_page);
                    }
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

        LOGGER.info("login filter destroy ----");
    }


    /**
     * 为登录返回json
     *
     * @param response
     */
    private void jsonResult(HttpServletResponse response, JSONResult.STATUS status) {

        JSONObject responseJSONObject = JSONObject.fromObject(JSONResult.onlyStatus(status));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
        } catch (IOException e) {
            LOGGER.info("return json result error ---> " + e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
