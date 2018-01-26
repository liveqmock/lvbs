package com.daishumovie.base.enums.front;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum ShareTargetType {
	// 1 话题 2 活动 3 合辑
    TOPIC(1, "话题"),
    ACTIVITY(2, "活动"),
    ALBUM(3, "合辑"),
    ;

    private Integer value;
    private String name;
    
	ShareTargetType(Integer value, String name) {

		this.value = value;
		this.name = name;
	}

	public static ShareTargetType get(Integer value) {

		if (null != value) {
			for (ShareTargetType type : ShareTargetType.values()) {
				if (Objects.equals(value, type.getValue())) {
					return type;
				}
			}
		}
		return null;
	}
}
