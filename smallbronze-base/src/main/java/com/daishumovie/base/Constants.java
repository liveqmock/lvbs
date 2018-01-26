package com.daishumovie.base;

/**
 * @author zhuruisong on 2017/3/28
 * @since 1.0
 */
public interface Constants {

    String WECHAT = "wechat_";
    String CACHE_ACCESS_TOKEN = WECHAT + "cache_access_token_";
    String CACHE_TICKET = WECHAT + "cache_ticket_";

    String USER_SEND_CAPTCHA = "user_send_captcha_";
    String USER_LOGIN_KEY = "video_user_login_key_";
    String USER_SEND_SMS_NUM_KEY = "user_send_sms_num_key_";
    String USER_SEND_SMS_KEY = "user_send_sms_key_";

    String USER_AWARDS_ENROL_SEND_SMS_NUM_KEY = "user_awards_enrol_send_sms_num_key_";
    String USER_AWARDS_ENROL_SEND_SMS_KEY = "user_awards_enrol_send_sms_key_";

    String SEPARATOR = "[|]";


    String QUEUE_RECOMMEND = "queue_recommend";
    String EXCHANGE_RECOMMEND = "exchange_recommend";
    String ROUTING_KEY_RECOMMEND = "routing_key_recommend";


    String CACHEABLE_LIST_C = "cacheable_name_list_c";
    String CACHEABLE_LIST_T = "cacheable_name_list_t";
    String CACHEABLE_LIST_L = "cacheable_name_list_l";
//    String CACHEABLE_DETAIL_VIDEO = "cacheable_name_detail_video";
//    String CACHEABLE_DETAIL_EPISODE = "cacheable_name_detail_episode";
//    String CACHEABLE_TAG = "cacheable_name_tag";
//    String CACHEABLE_RECOMMEND = "cacheable_name_recommend";

}
