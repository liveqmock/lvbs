package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * @author zhuruisong on 2017/1/5
 * @since 1.0
 */
@Getter
public enum RespStatusEnum {

    //1000一下的状态码为通用状态码
    OK(0,"success"),
    ERROR(1,"系统错误"),
    PARAM_FAIL(2,"参数错误"),
    PARAM_FAIL_NOT_EMOJI(3,"参数不支持emoji表情"),
    PARAM_SENSITIVE_WORD(4,"内容违规，请修改"),

    AUTH_FAIL(3000,"请在微信中打开"),
    WECHAT_CODE_FAIL(3001,"微信code错误"),
    WECHAT_AUTH_FAIL(3002,"需要先发起网页微信授权"),

    USER_NOT_LOGIN(6000,"未登录"),
	USER_SESSION_NOT_EXIST(6001, "会话不存在"),
    USER_LOGIN_FAIL(6002,"登录失败"),
    USER_NOT_EXIST(6003, "用户不存在"),
    USER_PASSWORD_ERROR(6004, "密码错误"),

    MOBILE_ERROR(6007,"手机号码格式错误"),
    SEND_SMS_LIMIT(6008,"请60秒后再次获取验证码"),
    MOBILE_EXISTED(6009,"该手机号已注册"),
    SMS_ERROR(6010,"短信验证码错误"),
    SMS_OLD_ERROR(6011,"原手机短信验证码错误"),
    MOBILE_NOT_BIND(6012,"手机号尚未绑定，无法参加活动"),

    TARGET_NOT_EXIST(4004, "目标对象不存在"),
    PRAISE_REPEAT(4000,"不可重复顶"),
    CRITICIZE_REPEAT(4001,"不可重复踩"),
    CANCEL_REPEAT(4002,"不可重复取消"),
    FOLLOW_REPEAT(4003,"不可重复关注"),
    BLACK_NOT_FOLLOW(4004,"对方已将你拉黑，无法关注"),
    NOT_BLACK_FOLLOW(4005,"该用户已被你拉黑"),

    TOPIC_NOT_EXIST(5001,"目标话题不存在"),
    TOPIC_NOT_AUTH(5002,"暂无权限，请联系管理员"),

    COMMENT_NOT_EXIST(7000,"目标评论不存在"),
    COMMENT_DEL_NOT_AUTH(7001,"无权限删除"),
    COMMENT_BLANK_CONTENT(7002,"请输入评论内容"),
    COMMENT_ALREADY_REPORT(7003,"该评论已被举报"),

    MULTIPARTUPLOAD_UPLOADID_ERROR(8000,"uploadId无效"),
    PARTMD5_ERROR(8001,"partMD5不正确"),
    COMPLETE_NOPART(8002,"无可合并part"),
    PARTSIZE_ERROR(8003,"part大小不符合分块规定"),
    VIDEOID_ERROR(8004,"根据uploadId没有找到videoId"),

    ACTIVITY_INVALID(9000,"此活动不存在或已下线"),
    ACTIVITY_NOT_ALLOW_SIGN_UP(9001,"非报名期间，不能报名"),
    ACTIVITY_ALREADY_SIGN_UP(9002,"已经报名，敬请期待活动开始"),
    ;

    private int status;
    private String desc;

    RespStatusEnum(int status, String desc){
        this.status = status;
        this.desc = desc;
    }
    
	public static RespStatusEnum get(Integer status) {
		if (status == null) {
			return null;
		}
		for (RespStatusEnum respStatusEnum : RespStatusEnum.values()) {
			if (status.equals(respStatusEnum.getStatus())) {
				return respStatusEnum;
			}
		}
		return null;
	}
}
