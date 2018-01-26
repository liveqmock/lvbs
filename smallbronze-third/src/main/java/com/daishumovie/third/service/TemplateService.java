package com.daishumovie.third.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 模板消息
 * @author zhuruisong on 2017/4/28
 * @since 1.0
 */
@Service
@Slf4j
public class TemplateService {

    @Autowired
    private  AccessTokenTicketService accessTokenService;

    private RestTemplate restTemplate;
    @Autowired
    public TemplateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void send(){

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessTokenService.getAccessToken();


        String jsonObject = "  {\n" +
                "           \"touser\":\"opfdEuNA4LnI2X_L0LA--OIc04u0\",\n" +
                "           \"template_id\":\"V-lLp0Q7fuBNhXFMpup4TaPg8_9m55SekgFy6SGX5r4\",          \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        String result = restTemplate.postForObject(url, jsonObject, String.class);

        log.info("返回数据：{}",result);
    }

}
