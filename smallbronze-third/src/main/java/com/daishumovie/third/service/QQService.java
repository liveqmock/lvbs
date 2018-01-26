package com.daishumovie.third.service;

import com.daishumovie.dao.mapper.smallbronze.DsmQqUserMapper;
import com.daishumovie.dao.model.DsmQqUser;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.third.pojo.qq.QQUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author zhuruisong on 2017/5/11
 * @since 1.0
 */
@Slf4j
@Service
public class QQService {

    @Value("${qq.app-id}")
    private String appId;
    @Value("${qq.app-key}")
    private String appkey;
    @Value("${qq.host}")
    private String host;

    @Autowired
    private DsmQqUserMapper dsmQqUserMapper;

    private RestTemplate restTemplate;
    @Autowired
    public QQService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * 获取QQ用户信息
     * @param openId 与APP通信的用户key
     * @param openKey session key
     * @param pf 应用的来源平台
     */
    public DsmQqUser getQQUser(String openId, String openKey, String pf) throws UnsupportedEncodingException {

        //手动加密
        String url = "/v3/user/get_info";
        String param = "appid=" + appId + "&openid=" + openId + "&openkey=" + openKey + "&pf=" + pf;
        String original = "POST&"+ URLEncoder.encode(url, "UTF-8")+"&"+URLEncoder.encode(param,"UTF-8");
        String sig = Base64.encodeBase64String(HmacUtils.hmacSha1(appkey+"&",original));

        MultiValueMap<String, String> multiMap = new LinkedMultiValueMap<>();
        multiMap.add("openid",openId);
        multiMap.add("openkey",openKey);
        multiMap.add("appid",appId);
        multiMap.add("pf",pf);
        multiMap.add("sig",sig);


        String userInfo = restTemplate.postForObject(host + url,  multiMap,String.class);
        log.info("qq获取用户信息返回：{}",userInfo);

        QQUserInfo qqUserInfo = FastJsonUtils.toBean(userInfo, QQUserInfo.class);
        if(qqUserInfo.getRet() != 0){
            return null;
        }

        DsmQqUser dsmQqUser = dsmQqUserMapper.selectByPrimaryKey(openId);

        //保存用户数据
        if(dsmQqUser == null){
            dsmQqUser = new DsmQqUser();
            BeanUtils.copyProperties(qqUserInfo, dsmQqUser);
            String replace = userInfo.replace("\n", "").replace("    ", "");
            dsmQqUser.setInfo(replace);
            dsmQqUser.setOpenid(openId);
            dsmQqUserMapper.insertSelective(dsmQqUser);
        }

        return dsmQqUser;

    }



}
