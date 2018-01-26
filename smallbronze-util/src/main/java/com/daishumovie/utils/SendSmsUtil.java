package com.daishumovie.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 发送短信
 * @author zhuruisong on 2017/2/15
 * @since 1.0
 */
@Component
@Slf4j
public class SendSmsUtil {

    /**
     * 发送短信
     * @param signName 签名
     * @param templateCode 短信模板
     * @param paramString 模板中变量
     * @param recNum 接受号码
     */
    public static void send(String signName,String templateCode, Map<String,String> paramString, String recNum) throws ClientException {

        String ENDPOINT_NAME = "北京袋鼠";
        String REGION_ID = "cn_beijing";//地区 中国北京
        String ACCESS_KEY = "LTAIgVrVvbCZlCJy";
        String ACCESS_SECRET = "1WyOv8PFtNQxwi0p8es0zIxqje3x9R";

        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY, ACCESS_SECRET);
        DefaultProfile.addEndpoint(ENDPOINT_NAME, REGION_ID, "Sms", "sms.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendSmsRequest request = new SingleSendSmsRequest();
        request.setSignName(signName);//控制台创建的签名名称
        request.setTemplateCode(templateCode);//控制台创建的模板CODE
        request.setParamString(FastJsonUtils.toJSONString(paramString));//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
        request.setRecNum(recNum);//接收号码
        SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
        String requestId = httpResponse.getRequestId();
//        String model = httpResponse.getModel();
        log.info("短信发送返回,{}",requestId);


    }

}
