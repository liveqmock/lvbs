package com.daishumovie.base.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author zhuruisong on 2017/1/13
 * @since 1.0
 */
@Data
public class KeyValue implements Serializable {

	private static final long serialVersionUID = -8156274477958549559L;
	
	private String key;
	
    private String value;

    public KeyValue() {
    }

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
