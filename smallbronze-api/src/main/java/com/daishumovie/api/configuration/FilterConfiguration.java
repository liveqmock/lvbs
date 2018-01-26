package com.daishumovie.api.configuration;

import com.daishumovie.base.Constants;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 过滤器配置类
 * @author zhuruisong on 2017/3/31
 * @since 1.0
 */
@Configuration
@Slf4j
public class FilterConfiguration {

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 通用设置
     *  跨域 编码 header参数
     * @return commentFilterRegistration
     */
    @Bean
    public FilterRegistrationBean commentFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

                request.setCharacterEncoding("utf-8");
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");

                LogUtil.uuid.set("["+UUID.randomUUID().toString()+"] ");

                if (log.isDebugEnabled()) {

                    if(request.getRequestURI().contains("/app/op")){
                        log.debug("/app/op 不打印日志，太长");
                    }else {
                        Enumeration<String> headerNames = request.getHeaderNames();
                        while (headerNames.hasMoreElements()) {
                            String s = headerNames.nextElement();
                            log.debug("hear参，{}={}", s, request.getHeader(s));
                        }
                        String method = request.getMethod();
                        log.debug("method方式，{}", method);

                        Enumeration<String> parameterNames = request.getParameterNames();
                        while (parameterNames.hasMoreElements()) {
                            String s = parameterNames.nextElement();
                            log.debug("入参，{}={}", s, request.getParameter(s));
                        }
                    }
                }

                if (response.getHeader("Access-Control-Allow-Methods") == null) {
                    String allowOrigin = "http://m.daishumovie.com";
                    String referer = request.getHeader("referer");
                    if (referer != null) {
                    	if( referer.contains("https://m.daishumovie.com")){
                    		allowOrigin = "https://m.daishumovie.com";
                    	} else if(referer.contains("https://m1.daishumovie.com")){
                    		allowOrigin = "https://m1.daishumovie.com";
                    	} else if(referer.contains("https://am.daishumovie.com")){
                    		allowOrigin = "https://am.daishumovie.com";
                    	} else if(referer.contains("https://am1.daishumovie.com")){
                    		allowOrigin = "https://am1.daishumovie.com";
                    	} else if (referer.contains("http://m.daishumovie.com")) {
                            allowOrigin = "http://m.daishumovie.com";
                    	} else if (referer.contains("http://m1.daishumovie.com")) {
                            allowOrigin = "http://m1.daishumovie.com";
                    	} else if (referer.contains("http://m.daishumovie.cn")) {
                            allowOrigin = "http://m.daishumovie.cn";
                    	} else if (referer.contains("http://m.lanhaimian.com")) {
                            allowOrigin = "http://m.lanhaimian.com";
                    	} else if(referer.contains("http://m1.lanhaimian.com")){
                    		allowOrigin = "http://m1.lanhaimian.com";
                    	} else if(referer.contains("http://mpre.lanhaimian.com")){
                    		allowOrigin = "http://mpre.lanhaimian.com";
                    	} else if(referer.contains("http://mpre1.lanhaimian.com")){
                    		allowOrigin = "http://mpre1.lanhaimian.com";
                    	} else if(referer.contains("http://m.pre.lanhaimian.com")){
                    		allowOrigin = "http://m.pre.lanhaimian.com";
                    	} else if(referer.contains("https://m.lanhaimian.com")){
                    		allowOrigin = "https://m.lanhaimian.com";
                    	} else if(referer.contains("https://m1.lanhaimian.com")){
                    		allowOrigin = "https://m1.lanhaimian.com";
                    	} else if(referer.contains("https://am.lanhaimian.com")){
                    		allowOrigin = "https://am.lanhaimian.com";
                    	} else if(referer.contains("https://am1.lanhaimian.com")){
                    		allowOrigin = "https://am1.lanhaimian.com";
                    	} else if (referer.contains("http://m.movie.com")) {
                            allowOrigin = "http://m.movie.com";
                    	} else if (referer.contains("http://m1.movie.com")) {
                            allowOrigin = "http://m1.movie.com";
                    	} else if (referer.contains("http://daishumovie.f3322.net")) {
                            allowOrigin = "http://daishumovie.f3322.net";
                    	} else if (referer.contains("localhost")) {
                            allowOrigin = "http://localhost:8080";
                        } else if (referer.contains("192.168")) {
                            allowOrigin = "http://192.168.1.200";
                        } else if (referer.contains("http://101.200.127.174:8083")) {
                            allowOrigin = "http://101.200.127.174:8083";
                        } else if (referer.contains("http://m1.movie.com:8080")) {
                            allowOrigin = "http://m1.movie.com:8080";
                        } else if (referer.contains("http://m2.movie.com:8080")) {
                            allowOrigin = "http://m2.movie.com:8080";
                        } else if (referer.contains("http://47.93.113.202:8080")) {
                            allowOrigin = "http://47.93.113.202:8080";
                        }
                    }
                    response.addHeader("Access-Control-Allow-Origin", allowOrigin);
                    response.addHeader("Access-Control-Allow-Credentials", "true");
                    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                    response.addHeader("Access-Control-Allow-Headers",
                            "Origin, No-Cache, X-Requested-With, If-Modified-Since, sessionid, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, wechatCode, openId, LoadwechatCode,did");
                    response.addHeader("Access-Control-Max-Age", "1000");
                }

                filterChain.doFilter(request,response);

            }
        });

        registration.addUrlPatterns("/*");

        registration.setOrder(1);
        registration.setName("commentFilterRegistration");
        return registration;
    }


    @Bean
    public FilterRegistrationBean headerFilterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter filter = new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                //反射映射header
                Header header = new Header();
                Field[] fields = header.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName();
                    field.setAccessible(true);//设置允许访问
                    try {
                        field.set(header, request.getHeader(name));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                String sessionId = header.getSessionId();
                if(StringUtils.isNotBlank(sessionId)){
                    String userJson = redisTemplate.opsForValue().get(Constants.USER_LOGIN_KEY + sessionId);
                    if(StringUtils.isNotBlank(userJson)){
                        LocalData.USER_JSON.set(userJson);
                        DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
                        header.setUid(user.getUid());
                        log.info("用户信息已设置,{}", userJson);
                    }

                    //TODO TEST
                    if("7dfk884GodkUEfoasjfpqjk]698eeEFdd7fefefJFFHdgeE".equals(sessionId)){
                        LocalData.USER_JSON.set("{\"uid\":1}");
                        header.setUid(1);
                    }

                }

                LocalData.HEADER.set(header);

                filterChain.doFilter(request,response);

                LocalData.USER_JSON.remove();
                LocalData.HEADER.remove();
            }
        };
        registration.addUrlPatterns("/*");
        registration.setFilter(filter);
        registration.setName("headerFilterRegistration");
        registration.setOrder(2);
        return registration;
    }


    /**
     * 必须登陆
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean apploginFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();



        Filter filter = new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

                String sessionId = LocalData.HEADER.get().getSessionId();
                log.info("登陆过滤器 sessionId={}",sessionId);

                if(sessionId == null || LocalData.USER_JSON.get() == null){
                    log.info("用户未登录");
                    String s;
                    if(sessionId == null) {
                        s = FastJsonUtils.toJSONString(new Response<>(RespStatusEnum.USER_NOT_LOGIN));
                    }
                    else {
                        s = FastJsonUtils.toJSONString(new Response<>(RespStatusEnum.USER_SESSION_NOT_EXIST));
                    }
                    PrintWriter writer = response.getWriter();
                    writer.print(s);
                    writer.close();
                    return;
                }

                filterChain.doFilter(request,response);

            }
        };

        registration.setFilter(filter);

        String[] urlPattern = new String[]{
                "/v1/user/publish/*",
                "/v1/comment/add/*",
                "/v1/comment/del",
                "/v1/user/uploadAvatar",
                "/v1/user/uploadVideo",
                "/v1/user/modify",

                "/v1/user/sendSms",
                "/v1/user/old/sendSms",
                "/v1/user/isBindMobile",

                "/v1/profile/myTopicList/*",
                "/v1/profile/myInfo",
                "/v1/profile/myCommentList/*",
                "/v1/profile/myDislike",
                "/v1/profile/myFollowAlbum/*",
                "/v1/profile/myActivifyList/*",

                "/v1/upload",
                "/v1/v1/topic/*",
                "/v1/v1/topic/comment/*",
                "/v1/saveBarrage",
                "/v1/praise/*",
                "/v1/criticize/*",
                "/v1/cancel/*",
                "/v1/follow/*",
                "/v1/followTopics/*",
                "/v1/addBlack/*",
                "/v1/removeBlack/*",
                "/v1/getBlackList/*",
                "/v1/activity/signUp/*",
                "/v1/initiateUpload",
                "/v1/uploadPart",
                "/v1/completeUpload/*",
                "/v1/abortUpload/*",
                "/v1/listPart/*",
                "/v1/activity/enrolNew/*",
                "/v1/activity/isEnrol/*",


        };
        registration.setEnabled(true);
        registration.addUrlPatterns(urlPattern);
        registration.setOrder(110);
        registration.setName("apploginFilterRegistration");
        return registration;
    }

}
