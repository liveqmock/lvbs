package com.daishumovie.third.service;

import com.daishumovie.dao.mapper.smallbronze.DsmWechatUserMapper;
import com.daishumovie.dao.model.DsmWechatUser;
import com.daishumovie.third.pojo.wechat.PageTokenBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import static com.daishumovie.utils.FastJsonUtils.toBean;
@Service
@Slf4j
public class WebPageAuthService {

    @Value("${service.app-id}")
    private String serviceAppId;
    @Value("${service.app-secret}")
    private String serviceAppSecret;

    @Value("${app.small-bronze.app-id}")
    private String appId;
    @Value("${app.small-bronze.app-secret}")
    private String appSecret;

    @Autowired
    private DsmWechatUserMapper dsmWechatUserMapper;

    private RestTemplate restTemplate;
    @Autowired
    public WebPageAuthService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * 转发到微信 测试code
     */
    @Deprecated
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        String state = "Ejf891qopUVz5b54nMageaEiGjefj";

        //目前只是get请求
        if (StringUtils.isNotBlank(queryString)) {
            requestURL.append("?").append(queryString);
        }
        //转发到微信
        String redirectUrl = URLEncoder.encode(requestURL.toString(), "UTF-8");
        log.debug("redirectUrl={}", redirectUrl);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + serviceAppId + "&redirect_uri=" + redirectUrl +
                "&response_type=code&scope=snsapi_userinfo&state=" + state + "#wechat_redirect";

        response.sendRedirect(url);

    }

    private PageTokenBo getPageToken(String code, String appId, String appSecret) throws IOException {
        log.debug("code=" + code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
        log.debug("url=" + url);
        String text = restTemplate.getForObject(url, String.class);
        log.debug("微信返回token信息=" + text);
        PageTokenBo pageTokenBo = toBean(text, PageTokenBo.class);
        if(pageTokenBo.getErrcode() != 0){
            return null;
        }
        return pageTokenBo;
    }



    /**
     * 处理微信服务器回调，接收用户openid(服务号)
     */
    public String getOpenidWithService(String code) throws IOException {

        PageTokenBo pageToken = getPageToken(code, serviceAppId, serviceAppSecret);
        if(pageToken == null){
            return null;
        }
        return pageToken.getOpenid();
    }

    /**
     * 获取微信用户信息 使用服务号
     */
    public DsmWechatUser getWechatUserWithService(String code) throws IOException {
        return getWechatUser(code, serviceAppId, serviceAppSecret);
    }
    /**
     * 获取微信用户信息 使用APP蓝海绵
     */
    public DsmWechatUser getWechatUserWithApp(String code) throws IOException {
        return getWechatUser(code, appId, appSecret);
    }

    /**
     * 获取微信用户信息
     * @param code
     * @return
     * @throws IOException
     */
    private DsmWechatUser getWechatUser(String code, String appId, String appSecret) throws IOException {

        PageTokenBo pageToken = getPageToken(code, appId, appSecret);
        if(pageToken == null){
            return null;
        }
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+pageToken.getAccess_token()+"&openid="+pageToken.getOpenid();

        String  text = restTemplate.getForObject(url, String.class);
        log.debug("微信返回用户信息="+text);

        DsmWechatUser dsmWechatUser = toBean(text, DsmWechatUser.class);

        if(dsmWechatUser.getOpenid() == null){
            return null;
        }

        DsmWechatUser dsmWechatUser1 = dsmWechatUserMapper.selectByPrimaryKey(dsmWechatUser.getOpenid());
        if(dsmWechatUser1 == null){
            dsmWechatUserMapper.insertSelective(dsmWechatUser);
            return dsmWechatUser;
        }

        return dsmWechatUser1;
    }

}
