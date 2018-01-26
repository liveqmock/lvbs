package com.daishumovie.dao.model.auth.enums;


import java.util.List;

/**
 * 布尔所对应的数据类型
 * <p/>
 * User: yin.liu1@renren-inc.com
 * Date: 12-9-19
 * Time: 下午3:01
 */
public enum BooleanNumEnum implements IndexedEnum {
    FALSE(0,"否"),
    TRUE(1,"是");

    private static final List<BooleanNumEnum> INDEXS = IndexedEnumUtil.toIndexes(BooleanNumEnum.values());

    private int index;
    private String name;

    BooleanNumEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static BooleanNumEnum indexOf(final int index) {
        return IndexedEnumUtil.valueOf(INDEXS, index);
    }
}
