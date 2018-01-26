package com.daishumovie.third.service;

import com.daishumovie.dao.mapper.smallbronze.DsmWeiboUserMapper;
import com.daishumovie.dao.model.DsmWeiboUser;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.third.pojo.weibo.WeiBoTokenBo;
import com.daishumovie.third.pojo.weibo.WeiBoUserInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 微博相关
 * @author zhuruisong on 2017/5/11
 * @since 1.0
 */
@Slf4j
@Service
public class WeiboService {

    @Value("${weibo.client-id}")
    private String appId;
    @Value("${weibo.client-secret}")
    private String appSecret;
    @Value("${weibo.redirect-uri}")
    private String redirectUri;

    @Autowired
    private DsmWeiboUserMapper dsmWeiboUserMapper;

    private RestTemplate restTemplate;
    @Autowired
    public WeiboService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * 获取凭证
     * @param code
     * @return
     */
    public WeiBoTokenBo getPageToken(String code){

        String url = "https://api.weibo.com/oauth2/access_token";

        MultiValueMap<String, String> multiMap = new LinkedMultiValueMap<>();
        multiMap.add("client_id",appId);
        multiMap.add("client_secret",appSecret);
        multiMap.add("grant_type","authorization_code");
        multiMap.add("code",code);
        multiMap.add("redirect_uri",redirectUri);

        String postForObject = restTemplate.postForObject(url, null, String.class, multiMap);
        log.info("微博返回数据={}",postForObject);
        return FastJsonUtils.toBean(postForObject, WeiBoTokenBo.class);
    }

    /**
     * 获取微博用户信息
     * @param code
     */
    public DsmWeiboUser getWeiBoUser(String code){

        WeiBoTokenBo pageToken = getPageToken(code);

        if(pageToken == null){
            return null;
        }

        return getWeiBoUser(pageToken.getAccess_token(),pageToken.getUid());

    }

    /**
     * 获取微博用户信息
     * @param accessToken
     * @param uid
     */
    public DsmWeiboUser getWeiBoUser(String accessToken, String uid){

        String url = "https://api.weibo.com/2/users/show.json?access_token=" + accessToken + "&uid=" + uid;

        String userInfo = restTemplate.getForObject(url, String.class);
        WeiBoUserInfo weiBoUserInfo = FastJsonUtils.toBean(userInfo, WeiBoUserInfo.class);

        DsmWeiboUser dsmWeiboUser = dsmWeiboUserMapper.selectByPrimaryKey(weiBoUserInfo.getId());

        //保存用户数据
        if(dsmWeiboUser == null) {

            dsmWeiboUser = new DsmWeiboUser();
            BeanUtils.copyProperties(weiBoUserInfo, dsmWeiboUser);
            dsmWeiboUser.setScreenName(weiBoUserInfo.getScreen_name());
            dsmWeiboUser.setProfileImageUrl(weiBoUserInfo.getProfile_image_url());
            dsmWeiboUser.setInfo(userInfo);
            dsmWeiboUserMapper.insertSelective(dsmWeiboUser);
        }

        return dsmWeiboUser;
    }

}
