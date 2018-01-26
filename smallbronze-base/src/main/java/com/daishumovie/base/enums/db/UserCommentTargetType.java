package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum UserCommentTargetType {
	// 1 话题 2 频道 3用户 4回复 5 合辑 6 活动
    TOPIC(1, "话题"),
    CHANNEL(2, "频道"),
    USER(3, "用户"),
    COMMENT(4, "评论"),
    ALBUM(5, "合辑"),
    ACTIVITY(6, "活动"),
    ;

    private Integer value;
    private String name;
    
	UserCommentTargetType(Integer value, String name) {

		this.value = value;
		this.name = name;
	}

	public static UserCommentTargetType get(Integer value) {

		if (null != value) {
			for (UserCommentTargetType type : UserCommentTargetType.values()) {
				if (Objects.equals(value, type.getValue())) {
					return type;
				}
			}
		}
		return null;
	}
}
