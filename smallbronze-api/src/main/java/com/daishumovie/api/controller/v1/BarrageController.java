package com.daishumovie.api.controller.v1;

import com.daishumovie.api.service.BarrageService;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.utils.sensitiveword.SensitivewordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Cheng Yufei
 * @create 2017-09-12 10:39
 **/
@RestController
@RequestMapping("/v1")
public class BarrageController {


    @Autowired
    private BarrageService barrageService;

    /**
     * 弹幕 save
     *
     * @param videoId
     * @param content
     * @param timeMillisecond
     * @param appId
     * @return
     */
    @RequestMapping(value = "/saveBarrage", method = RequestMethod.POST)
    public Response insert(@RequestParam Integer videoId, @RequestParam String content, @RequestParam Integer timeMillisecond,
                           @RequestHeader(value = "appId", required = false) Integer appId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        if (null == videoId || StringUtils.isBlank(content) || null == timeMillisecond) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }

        //敏感词
        if(SensitivewordUtil.isContaintSensitiveWord(content, SensitivewordUtil.minMatchTYpe)){
            return new Response(RespStatusEnum.PARAM_SENSITIVE_WORD);
        }
        return barrageService.save(videoId, content, timeMillisecond, appId, uid);
    }

    /**
     * 弹幕 获取
     *
     * @param videoId
     * @param pageIndex
     * @param appId
     * @return
     */
    @RequestMapping(value = "/getBarrage/{videoId:\\d+}/{pageIndex}", method = {RequestMethod.GET,RequestMethod.POST})
    public Response getBarrage(@PathVariable Integer videoId, @PathVariable Integer pageIndex, @RequestHeader(value = "appId", required = false) Integer appId) {
        if (null == videoId) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        PageInDto pageInDto = new PageInDto(pageIndex, 100);
        return barrageService.getBarrageList(videoId, appId, pageInDto);
    }

}
