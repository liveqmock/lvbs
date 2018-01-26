package com.daishumovie.base.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhuruisong on 2017/2/10
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class Header {

    private String did;

    private String sessionId;

    /**
     * 请使用以下方式获取用户id
     * DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
     * Integer uid = user.getUid()
     */
    @Deprecated
    private Integer uid;

    private String version;
    private String screen;
    private String networkType;
    private String language;
    private String device;
    private String os;
    private String osVersion;

    private String appId;

    private OsEnum osEnum;


    public OsEnum getOsEnum() {
        return OsEnum.get(this.os);
    }


    public enum OsEnum{
        IOS(1), Android(2)
        ;

        @Getter
        private Integer os;

        OsEnum(Integer os){
            this.os = os;
        }

        public static OsEnum get(String name){
            OsEnum[] values = values();
            for (OsEnum value : values) {
                if(value.name().equalsIgnoreCase(name)){
                    return value;
                }
            }
            return null;
        }

    }



}
