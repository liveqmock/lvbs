package com.daishumovie.api.controller.v1;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.aliyuncs.exceptions.ClientException;
import com.daishumovie.api.configuration.AliYunSmsProperties;
import com.daishumovie.api.service.HistoryService;
import com.daishumovie.api.service.PushService;
import com.daishumovie.api.service.UserService;
import com.daishumovie.base.Constants;
import com.daishumovie.base.dto.user.LoginDto;
import com.daishumovie.base.dto.user.UserInfo;
import com.daishumovie.base.dto.user.UserInfoEdit;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.ThirdLoginType;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.front.BaseUtil;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.model.DsmQqUser;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.DsmWechatUser;
import com.daishumovie.dao.model.DsmWeiboUser;
import com.daishumovie.third.service.QQService;
import com.daishumovie.third.service.WebPageAuthService;
import com.daishumovie.third.service.WeiboService;
import com.daishumovie.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController("v1.UserController")
@RequestMapping("/v1/user/")
@Slf4j
public class UserController {

    @Value("${ali.oss.endpoint}")
    private String endpoint;
    @Autowired
    private AliYunSmsProperties aliyunSmsProperties;
    @Autowired
    private WebPageAuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private PushService pushService;
    @Autowired
    private WeiboService weiboService;
    @Autowired
    private QQService qqService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private DsmUserMapper userMapper;
    @Autowired
    private HistoryService historyService;


    @Value("${app.mustBindMobile}")
    private Integer mustBindMobile;


    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    @RequestMapping("microMessageLogin")
    public Response<LoginDto> microMessageLogin(@RequestParam String code) {

        try {
            //获取微信用户信息
            DsmWechatUser wechatUser = authService.getWechatUserWithApp(code);
            if (wechatUser == null) {
                return new Response<>(RespStatusEnum.USER_LOGIN_FAIL);
            }

            DsmUser user = userService.save(ThirdLoginType.micro_message, wechatUser.getUnionid(), wechatUser.getNickname(), Integer.valueOf(wechatUser.getSex()), wechatUser.getHeadimgurl());
            LoginDto loginDto = afterLogin(user);
            return new Response<>(loginDto);
        } catch (Exception e) {
            log.info("microMessageLogin error --- {}", e.getMessage());
            e.printStackTrace();
            return new Response<>(RespStatusEnum.ERROR);
        }
    }

    /**
     * 微博登录
     *
     * @param accessToken
     * @param uid
     * @return
     */
    @RequestMapping("microBlobLogin")
    public Response<LoginDto> microBlobLogin(@RequestParam String accessToken,@RequestParam  String uid) {

        try {
            //获取微博用户信息
            DsmWeiboUser weiBoUser = weiboService.getWeiBoUser(accessToken, uid);
            if (weiBoUser == null) {
                return new Response<>(RespStatusEnum.USER_LOGIN_FAIL);
            }

            String gender = weiBoUser.getGender();
            Integer sex = "m".equals(gender) ? 1 : ("f".equals(gender) ? 2 : 0);

            DsmUser user = userService.save(ThirdLoginType.micro_blob, weiBoUser.getId(), weiBoUser.getScreenName(), sex, weiBoUser.getProfileImageUrl());
            LoginDto loginDto = afterLogin(user);
            return new Response<>(loginDto);
        } catch (Exception e) {
            log.info("microBlobLogin error ---> {}", e);
            e.printStackTrace();
            return new Response<>(RespStatusEnum.ERROR);
        }
    }

    @RequestMapping("qqLogin")
    public Response<LoginDto> qqLogin(@RequestParam String openId,@RequestParam  String openKey,@RequestParam String pf) {

        try {
            //获取QQ用户信息
            DsmQqUser qqUser = qqService.getQQUser(openId, openKey, pf);
            if (qqUser == null) {
                return new Response<>(RespStatusEnum.USER_LOGIN_FAIL);
            }
            String gender = qqUser.getGender();
            Integer sex = "男".equals(gender) ? 1 : ("女".equals(gender) ? 2 : 0);
            DsmUser user = userService.save(ThirdLoginType.qq, qqUser.getOpenid(), qqUser.getNickname(), sex, qqUser.getFigureurl());
            LoginDto loginDto = afterLogin(user);
            return new Response<>(loginDto);
        } catch (UnsupportedEncodingException e) {
            log.info("qqLogin error --- {}", e.getMessage());
            e.printStackTrace();
            return new Response<>(RespStatusEnum.ERROR);
        }
    }

//     /**
     //     * 向手机发送验证码
     //     *
     //     * @param mobile {
     //     *               redis 验证码 key的格式 :  user_send_sms_key_手机号
     //     *               redis 验证码发送次数 key的格式 :  user_send_sms_key_手机号_当前日期(格式yyyyMMdd)
     //     *               }
     //     * @return
     //     */
//    @RequestMapping(value = "sendCaptcha")
//    public Response sendCaptcha(String mobile) {
//
//        try {
//            if (StringUtil.isEmpty(mobile)) {
//                return new Response(RespStatusEnum.PARAM_FAIL);
//            }
//            //验证手机号是否已注册
//            if (!userService.mobileOnly(mobile)) {
//                log.info("手机号{}已注册", mobile);
//                return new Response(RespStatusEnum.MOBILE_EXISTED);
//            }
//
//            String sendNumStr = redisTemplate.opsForValue().get(Constants.USER_SEND_SMS_NUM_KEY + mobile + "_" + DateUtil.yyyyMMdd.format(new Date()));
//
//            Integer sendNum = sendNumStr == null ? 0 : Integer.valueOf(sendNumStr);
//
//            if (sendNum > 5) {
//                return new Response(RespStatusEnum.SEND_SMS_LIMIT);
//            }
//
//            //发送短信
//            String sixCode = (int) ((Math.random() * 9 + 1) * 100000) + "";
//            if (aliyunSmsProperties.getSmsIsSend()) {
//                Map<String, String> map = new HashMap<>();
//                map.put("code", sixCode);
//                SendSmsUtil.send(aliyunSmsProperties.getSmsSign(), aliyunSmsProperties.getSmsTemplateRegister(), map, mobile);
//            } else {
//                sixCode = "123456";
//                log.info("此环境配置不发短信 默认使用使用{}", sixCode);
//            }
//            redisTemplate.opsForValue().set(Constants.USER_SEND_SMS_KEY + mobile, sixCode, 10, TimeUnit.MINUTES);
//
//            //存储次数
//            redisTemplate.opsForValue().set(Constants.USER_SEND_SMS_NUM_KEY + mobile + "_" + DateUtil.yyyyMMdd.format(new Date()), ++sendNum + "", 1, TimeUnit.DAYS);
//            return new Response();
//        } catch (Exception e) {
//            log.info("sendCaptcha error --- {}", e.getMessage());
//            e.printStackTrace();
//            return new Response<>(RespStatusEnum.ERROR);
//        }
//    }
//
//
//    /**
//     * 手机注册
//     * @param mobile 手机号
//     * @param captcha 验证码
//     * @return
//     */
//    @RequestMapping("registerByMobile")
//    public Response registerByMobile(String mobile, String captcha, String password) {
//
//        try {
//            if (!RegexUtil.isMobile(mobile)) {
//                return new Response(RespStatusEnum.MOBILE_ERROR);
//            }
//            synchronized (this) {
//                //1.验证手机号是否已注册
//                if (!userService.mobileOnly(mobile)) {
//                    log.info("手机号{}已注册", mobile);
//                    return new Response(RespStatusEnum.MOBILE_EXISTED);
//                }
//                //2.校验该用户的短信验证码
//                String sms = redisTemplate.opsForValue().get(Constants.USER_SEND_SMS_KEY + mobile);
//                if (StringUtils.isBlank(sms) || !sms.equals(captcha)) {
//                    return new Response(RespStatusEnum.SMS_ERROR);
//                }
//                //3.校验密码
//                {
//                    if (StringUtil.isEmpty(password)) {
//                        return new Response(RespStatusEnum.PARAM_FAIL);
//                    }
//                }
//                { //保存用户
//                    DsmUser user = new DsmUser();
//                    user.setMobile(mobile);
//                    user.setSecuritySalt(Encryption.createSalt());
//                    user.setPassword(Encryption.newPasswordHash(password, user.getSecuritySalt()));
//                    user.setNickName(mobile.substring(0, 4) + "****" + mobile.substring(8));
//                    if (0 == userMapper.insertSelective(user)) {
//                        return new Response(RespStatusEnum.ERROR);
//                    }
//                }
//            }
//            return new Response();
//        } catch (Exception e) {
//            log.info("registerByMobile error --- {}", e.getMessage());
//            e.printStackTrace();
//            return new Response<>(RespStatusEnum.ERROR);
//        }
//    }
//
//    @RequestMapping("login")
//    public Response login(String mobile, String password) {
//
//        if (StringUtil.isEmpty(mobile) || StringUtil.isEmpty(password)) {
//            return new Response(RespStatusEnum.PARAM_FAIL);
//        }
//        Response<DsmUser> userResponse = userService.login(mobile, password);
//        DsmUser user = userResponse.getResult();
//        if (null == user) {
//            return userResponse;
//        }
//        return new Response<>(afterLogin(user));
//    }

    /**
     * 发送短信
     * @param mobile 手机号
     * @return
     */
    @RequestMapping("/sendSms")
    public Response sendSmsRegister( @RequestParam String mobile) throws ClientException {

        if(!RegexUtil.isMobile(mobile) ){
            return new Response(RespStatusEnum.MOBILE_ERROR);
        }
        DsmUser dsmUser = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        Date currDate = new Date();
        String yyyyMMdd = DateFormatUtils.format(currDate, "yyyyMMdd");
        String sendNumStr = redisTemplate.opsForValue().get(Constants.USER_SEND_SMS_NUM_KEY + dsmUser.getUid() + yyyyMMdd);
        Integer sendNum = sendNumStr == null ? 0 : Integer.valueOf(sendNumStr);
        if (sendNum > 5) {
            return new Response(RespStatusEnum.SEND_SMS_LIMIT);
        }
        //验证手机号是否已注册
        DsmUser userByMobile = userService.getUserByMobile(mobile);
        if(userByMobile != null){
            log.info("手机号{}已注册",mobile);
            return new Response(RespStatusEnum.MOBILE_EXISTED);
        }
        //发送短信
        String sixCode = (int) ((Math.random() * 9 + 1) * 100000) +"";
        if(aliyunSmsProperties.getSmsIsSend()){
            Map<String, String> map = new HashMap<>();
            map.put("code", sixCode);
            SendSmsUtil.send(aliyunSmsProperties.getSmsSign(), aliyunSmsProperties.getSmsTemplateBinding(), map, mobile);
        }else {
            sixCode = "123456";
            log.info("此环境配置不发短信 默认使用使用{}", sixCode);
        }
        redisTemplate.opsForValue().set(Constants.USER_SEND_SMS_KEY + dsmUser.getUid() + mobile, sixCode, 10, TimeUnit.MINUTES);
        //存储次数
        redisTemplate.opsForValue().set(Constants.USER_SEND_SMS_NUM_KEY + dsmUser.getUid() + yyyyMMdd, ++sendNum + "", 1, TimeUnit.DAYS);
        return new Response();
    }

    /**
     * 给原手机发送短信
     * @return
     */
    @RequestMapping("old/sendSms")
    public Response oldSendSmsRegister() throws ClientException {

        DsmUser dsmUser = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        dsmUser = userService.getUserByUid(dsmUser.getUid());
        String mobile = dsmUser.getMobile();
        Date currDate = new Date();
        String yyyyMMdd = DateFormatUtils.format(currDate, "yyyyMMdd");
        String sendNumStr = redisTemplate.opsForValue().get(Constants.USER_SEND_SMS_NUM_KEY + dsmUser.getUid() + yyyyMMdd);
        Integer sendNum = sendNumStr == null ? 0 : Integer.valueOf(sendNumStr);
        if (sendNum > 5) {
            return new Response(RespStatusEnum.SEND_SMS_LIMIT);
        }
        //发送短信
        String sixCode = (int) ((Math.random() * 9 + 1) * 100000) +"";
        if(aliyunSmsProperties.getSmsIsSend()){
            Map<String, String> map = new HashMap<>();
            map.put("code", sixCode);
            SendSmsUtil.send(aliyunSmsProperties.getSmsSign(), aliyunSmsProperties.getSmsTemplateBinding(), map, mobile);
        }else {
            sixCode = "123456";
            log.info("此环境配置不发短信 默认使用使用{}", sixCode);
        }
        redisTemplate.opsForValue().set(Constants.USER_SEND_SMS_KEY + dsmUser.getUid() + mobile, sixCode, 10, TimeUnit.MINUTES);
        //存储次数
        redisTemplate.opsForValue().set(Constants.USER_SEND_SMS_NUM_KEY + dsmUser.getUid() + yyyyMMdd, ++sendNum + "", 1, TimeUnit.DAYS);
        return new Response();
    }



    /**
     * 用户绑定手机号
     * @param mobile 手机号
     * @param smsCode 短信校验码
     * @return
     */
    @RequestMapping("/bindMobile")
    public Response bindMobile(@RequestParam String mobile, @RequestParam String smsCode){

        if(!RegexUtil.isMobile(mobile) ){
            return new Response(RespStatusEnum.MOBILE_ERROR);
        }

        //验证手机号是否已注册
        DsmUser userByMobile = userService.getUserByMobile(mobile);
        if (userByMobile != null) {
            log.info("手机号{}已注册", mobile);
            return new Response(RespStatusEnum.MOBILE_EXISTED);
        }
        DsmUser dsmUser = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        //校验该用户的短信验证码
        String sms = redisTemplate.opsForValue().get(Constants.USER_SEND_SMS_KEY + dsmUser.getUid() + mobile);
        if(StringUtils.isBlank(sms) || !sms.equals(smsCode)){
            return new Response(RespStatusEnum.SMS_ERROR);
        }
        DsmUser update = new DsmUser();
        update.setUid(dsmUser.getUid());
        update.setMobile(mobile);
        userMapper.updateByPrimaryKeySelective(update);
        return new Response();
    }

    /**
     * 校验验证码
     * @param mobile 手机号
     * @param smsCode 短信校验码
     * @return
     */
    @RequestMapping("/verifyCaptcha")
    public Response verifyCaptcha(@RequestParam String mobile, @RequestParam String smsCode){
        if(!RegexUtil.isMobile(mobile) ){
            return new Response(RespStatusEnum.MOBILE_ERROR);
        }
        DsmUser dsmUser = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        //校验该用户的短信验证码
        String sms = redisTemplate.opsForValue().get(Constants.USER_SEND_SMS_KEY + dsmUser.getUid() + mobile);
        if(StringUtils.isBlank(sms) || !sms.equals(smsCode)){
            return new Response(RespStatusEnum.SMS_ERROR);
        }
        return new Response();
    }

    /**
     * 用户更换手机号
     * @param mobile 手机号
     * @param smsCode 短信校验码
     * @return
     */
    @RequestMapping("/changeMobile")
    public Response changeMobile(@RequestParam String mobile, @RequestParam String smsCode,@RequestParam String oldSmsCode){

        if(!RegexUtil.isMobile(mobile) ){
            return new Response(RespStatusEnum.MOBILE_ERROR);
        }
        DsmUser dsmUser = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        dsmUser = userService.getUserByUid(dsmUser.getUid());

        //校验该用户原手机的短信验证码
        String sms = redisTemplate.opsForValue().get(Constants.USER_SEND_SMS_KEY + dsmUser.getUid() + dsmUser.getMobile());
        if(StringUtils.isBlank(sms) || !sms.equals(oldSmsCode)){
            return new Response(RespStatusEnum.SMS_OLD_ERROR);
        }

        //验证手机号是否已注册
        DsmUser userByMobile = userService.getUserByMobile(mobile);
        if(userByMobile != null && !userByMobile.getUid().equals(dsmUser.getUid())){
            log.info("该手机号{}已被其他用户注册",mobile);
            return new Response(RespStatusEnum.MOBILE_EXISTED);
        }

        //校验该用户的短信验证码
        sms = redisTemplate.opsForValue().get(Constants.USER_SEND_SMS_KEY + dsmUser.getUid() + mobile);
        if(StringUtils.isBlank(sms) || !sms.equals(smsCode)){
            return new Response(RespStatusEnum.SMS_ERROR);
        }
        DsmUser update = new DsmUser();
        update.setUid(dsmUser.getUid());
        update.setMobile(mobile);
        userMapper.updateByPrimaryKeySelective(update);
        return new Response();
    }


    /**
     * 登录之后的操作
     * @param user
     * @return
     */
    private LoginDto afterLogin(DsmUser user) {

        String sessionId = StringUtil.uuid();
        Header header = LocalData.HEADER.get();
        redisTemplate.opsForValue().set(Constants.USER_LOGIN_KEY + sessionId, FastJsonUtils.toJSONString(user));

        //异步同步数据
        new Thread(() -> {
            //token信息
            pushService.relevanceData(user.getUid(), header.getDid());
            //浏览历史
            historyService.relevanceData(user.getUid(),header.getDid());
        }).start();

        LoginDto loginDto = new LoginDto();
        if(StringUtils.isNotBlank(user.getMobile())){
            loginDto.setMustBindMobile(YesNoEnum.NO.getCode());
        }else{
            loginDto.setMustBindMobile(mustBindMobile);
        }

        loginDto.setSessionId(sessionId);
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar(user.getAvatar());
        if(StringUtils.isBlank(userInfo.getAvatar())){
            userInfo.setAvatar("http://small-bronze.oss-cn-shanghai.aliyuncs.com/image/common/default/invalidName%403x.png");
        }
        userInfo.setIntroduce(user.getIntroduce());
        userInfo.setNickname(user.getNickName());
        userInfo.setSex(user.getSex());
        userInfo.setUnreadCount(0);//TODO 后去补充
        userInfo.setUid(user.getUid());
        userInfo.setMobile(user.getMobile());
        userInfo.setIsReplyAuth(user.getIsReplyAuth());
        userInfo.setIsTopicAuth(user.getIsTopicAuth());
        userInfo.setFansNum(BaseUtil.instance.numberFormat(user.getFansNum()));
        userInfo.setFollowNum(BaseUtil.instance.numberFormat(user.getFollowNum()));
        userInfo.setLikeTotalNum(BaseUtil.instance.numberFormat(user.getLikeNum()+user.getLikeAlbumNum()));
        userInfo.setLikeNum(BaseUtil.instance.numberFormat(user.getLikeNum()));
        userInfo.setLikeAlbumNum(BaseUtil.instance.numberFormat(user.getLikeAlbumNum()));
        userInfo.setOpusNum(BaseUtil.instance.numberFormat(user.getPublishCount()));
        loginDto.setUserInfo(userInfo);
        return loginDto;
    }

    /**
     * 更新session
     */
    private void updateSession(){

        Header header = LocalData.HEADER.get();
        String sessionId = header.getSessionId();
        DsmUser dsmUser = FastJsonUtils.toBean( LocalData.USER_JSON.get(), DsmUser.class);
        dsmUser = userService.getUserByUid(dsmUser.getUid());
        redisTemplate.opsForValue().set(Constants.USER_LOGIN_KEY + sessionId, FastJsonUtils.toJSONString(dsmUser));
    }

    /**
     * 修改用户基本信息
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response editUserInfo(@RequestParam(value = "nickname",required = false) String nickName,
                                 @RequestParam(value = "avatar",required = false) String avatar,
                                 @RequestParam(value = "introduce",required = false) String introduce,
                                 @RequestParam(value = "sex",required = false) Integer sex) {
        UserInfoEdit userInfoEdit = new UserInfoEdit();
        userInfoEdit.setNickName(nickName);
        userInfoEdit.setAvatar(avatar);
        userInfoEdit.setIntroduce(introduce);
        userInfoEdit.setSex(sex);
        return userService.editUserInfo(userInfoEdit);
    }

    /**
     * 保存或者更新用户设置
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateSettings", method = RequestMethod.POST)
    public Response updateSettings(HttpServletRequest req) throws APIConnectionException, APIRequestException {

        String userStr = LocalData.USER_JSON.get();
        DsmUser user = FastJsonUtils.toBean(userStr, DsmUser.class);
        Integer receiveNotification = StringUtils.isBlank(req.getParameter("receiveNotification")) ? null
                : Integer.parseInt(req.getParameter("receiveNotification"));
        Integer autoplay = StringUtils.isBlank(req.getParameter("autoplay")) ? null
                : Integer.parseInt(req.getParameter("autoplay"));
        Integer allowBulletScreen = StringUtils.isBlank(req.getParameter("allowBulletScreen")) ? null
                : Integer.parseInt(req.getParameter("allowBulletScreen"));
        String registrationId = req.getParameter("registrationId");
        if (StringUtil.isAllBlank(req.getParameter("receiveNotification"), req.getParameter("autoplay"),
                req.getParameter("allowBulletScreen"))) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }

        return userService.updateSettings(user == null ? null : user.getUid(), receiveNotification,
                autoplay, allowBulletScreen,registrationId);

    }


    /**
     * 是否绑定手机号
     * @return
     * @throws ClientException
     */
    @RequestMapping(value = "/isBindMobile")
    public Response<String> isBindMobile() throws ClientException {

        DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        return userService.isBindMobile(user.getUid());
    }
}