package com.daishumovie.api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhuruisong on 2017/2/23
 * @since 1.0
 */
@Component
@Data
@ConfigurationProperties(prefix = "aliyun")
public class AliYunSmsProperties {

   private Boolean smsIsSend;
   private String smsSign;
   private String smsTemplateRegister;
   private String smsTemplateBinding;

}
