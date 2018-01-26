package com.daishumovie.third.service;

import com.daishumovie.base.Constants;
import com.daishumovie.third.pojo.wechat.AccessTokenBo;
import com.daishumovie.third.pojo.wechat.TicketBo;
import com.daishumovie.utils.FastJsonUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author zhuruisong on 2017/3/27
 * @since 1.0
 */
@Slf4j
@Service
public class AccessTokenTicketService {

    @Getter
    @Value("${subscription.app-id}")
    public String appId;
    @Value("${subscription.app-secret}")
    private String appSecret;

//    @Autowired
    private StringRedisTemplate redisTemplate;

    private RestTemplate restTemplate;
    @Autowired
    public AccessTokenTicketService(RestTemplateBuilder restTemplateBuilder,StringRedisTemplate redisTemplate) {
        this.restTemplate = restTemplateBuilder.build();
        this.redisTemplate = redisTemplate;
    }

    /**
     * 刷新token
     */
    public void refreshAccessToken() {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;

        //访问微信服务器 获取数据
        String result = restTemplate.getForObject(url, String.class);
        log.info("微信服务器返回token数据，{}", result);
        AccessTokenBo accessToken = FastJsonUtils.toBean(result,AccessTokenBo.class);

        //缓存存储
        redisTemplate.opsForValue().set(Constants.CACHE_ACCESS_TOKEN + appId, accessToken.getAccess_token(), accessToken.getExpires_in(), TimeUnit.SECONDS);

    }

    /**
     * 如果...可以提供一个接口提供给外界调用（同样频率限制)，不建议
     * @return
     */
    public String getAccessToken() {
        String token = redisTemplate.opsForValue().get(Constants.CACHE_ACCESS_TOKEN + appId);
        log.info("缓存中获取到token=" + token);
        if(token!=null) {
            return  token;
        }
        //刷新token
        refreshAccessToken();
        token = redisTemplate.opsForValue().get(Constants.CACHE_ACCESS_TOKEN + appId);
        //如果还获取不到 说明非本线程刷新 尝试3次
        if(token==null) {
            int retry=0;
            do {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                token = redisTemplate.opsForValue().get(Constants.CACHE_ACCESS_TOKEN + appId);
                retry++;

            } while (token == null && retry < 3);
        }
        return token;

    }

    /**
     * 获取jsapi_ticket
     */
    public String getJsapiTicket(){
        String type = TicketType.jsapi.name();
        String jsapiTicket = redisTemplate.opsForValue().get(Constants.CACHE_TICKET + type + appId);
        log.info("缓存中获取到jsapi_ticket=" + jsapiTicket);
        return jsapiTicket;
    }

    /**
     * 刷新jsapi_ticket
     */
    public void refreshJsapiTicket(){
        getTicket(TicketType.jsapi);
    }

    private void getTicket(TicketType ticketType){
        String type = ticketType.name();
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + getAccessToken() + "&type=" + type;

        //访问微信服务器 获取数据
        String result = restTemplate.getForObject(url, String.class);
        log.info("微信服务器返回ticket数据，{}", result);
        TicketBo ticketBo = FastJsonUtils.toBean(result,TicketBo.class);

        //缓存存储
        redisTemplate.opsForValue().set(Constants.CACHE_TICKET + type + appId, ticketBo.getTicket(), ticketBo.getExpires_in(), TimeUnit.SECONDS);
    }

    public enum TicketType{
       jsapi,wx_card;
    }


}
