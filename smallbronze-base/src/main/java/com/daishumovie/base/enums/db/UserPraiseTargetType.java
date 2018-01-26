package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum UserPraiseTargetType {
	// 1 话题 2 频道 3用户 4回复 5 合辑
    TOPIC(1, "话题"),
    CHANNEL(2, "频道"),
    USER(3, "用户"),
    COMMENT(4, "评论"),
    ALBUM(5, "合辑"),
    ;

    private Integer value;
    private String name;
    
	UserPraiseTargetType(Integer value, String name) {

		this.value = value;
		this.name = name;
	}

	public static UserPraiseTargetType get(Integer value) {

		if (null != value) {
			for (UserPraiseTargetType type : UserPraiseTargetType.values()) {
				if (Objects.equals(value, type.getValue())) {
					return type;
				}
			}
		}
		return null;
	}
}
