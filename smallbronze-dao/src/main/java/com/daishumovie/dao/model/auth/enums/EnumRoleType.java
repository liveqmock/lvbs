package com.daishumovie.dao.model.auth.enums;


import java.util.List;

/**
 * 角色分类 User: tianrui.lin@renren-inc.com Date: 13-6-7 Time: 下午2:00 To change
 * this template use File | Settings | File Templates.
 */
public enum EnumRoleType implements IndexedEnum {

	ADMIN(0),

	MANAGER(1),

	OPERATOR(2);

	private int index;

	EnumRoleType(int index) {
		this.index = index;
	}

	private static final List<EnumRoleType> INDEXS = IndexedEnumUtil.toIndexes(EnumRoleType.values());

	@Override
	public int getIndex() {
		return index;
	}

	public static EnumRoleType indexOf(final int index) {
		return IndexedEnumUtil.valueOf(INDEXS, index);
	}
}
