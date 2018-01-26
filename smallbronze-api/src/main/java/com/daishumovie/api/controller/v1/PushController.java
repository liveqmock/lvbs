package com.daishumovie.api.controller.v1;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.daishumovie.api.service.PushService;
import com.daishumovie.base.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhuruisong on 2017/5/24
 * @since 1.0
 */
@RestController("v1.PushController")
@RequestMapping("/v1/token")
public class PushController {

    @Autowired
    private PushService pushService;

    @RequestMapping("/save")
    public Response saveToken(@RequestParam(required=false) String token, @RequestParam(required=false) String registrationId) throws APIConnectionException, APIRequestException {

        return pushService.saveToken(token, registrationId);
    }


}
