package com.daishumovie.api.controller;

import com.daishumovie.base.Constants;
import com.daishumovie.base.dto.user.UserRecordDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuruisong on 2017/9/18
 * @since 1.0
 */
@RestController
@RequestMapping
public class TestMq {


    @Autowired
    RabbitTemplate rabbitTemplate;

    //实体
    @GetMapping("/send2")
    public String send2(@RequestParam String msg){
        UserRecordDto userRecordDto = new UserRecordDto();
        userRecordDto.setAppId(2000);

        rabbitTemplate.convertAndSend(Constants.EXCHANGE_RECOMMEND, Constants.ROUTING_KEY_RECOMMEND, userRecordDto);
        return "已发送："+msg;
    }
}
