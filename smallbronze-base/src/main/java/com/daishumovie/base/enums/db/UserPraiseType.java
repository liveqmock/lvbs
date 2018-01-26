package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum UserPraiseType {
	PRAISE(1, "顶"),
	CRITICISM(2, "踩"),
    FOLLOW(3, "关注"),
    BLACKLIST(4, "黑名单"),
    ;

    private Integer value;
    private String name;
    
	UserPraiseType(Integer value, String name) {

		this.value = value;
		this.name = name;
	}

	public static UserPraiseType get(Integer value) {

		if (null != value) {
			for (UserPraiseType type : UserPraiseType.values()) {
				if (Objects.equals(value, type.getValue())) {
					return type;
				}
			}
		}
		return null;
	}
}
