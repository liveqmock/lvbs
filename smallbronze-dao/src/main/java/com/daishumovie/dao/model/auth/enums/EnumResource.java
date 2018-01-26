package com.daishumovie.dao.model.auth.enums;


import java.util.List;

/**
 * 资源类型枚举 User: tianrui.lin@renren-inc.com Date: 13-6-6 Time: 下午7:08 To change
 * this template use File | Settings | File Templates.
 */
public enum EnumResource implements IndexedEnum {

	MENU(0), FUNCTION(1);

	private int index;

	EnumResource(int index) {
		this.index = index;
	}

	private static final List<EnumResource> INDEXS = IndexedEnumUtil.toIndexes(EnumResource.values());

	@Override
	public int getIndex() {
		return index;
	}

	public static EnumResource indexOf(final int index) {
		return IndexedEnumUtil.valueOf(INDEXS, index);
	}
}
