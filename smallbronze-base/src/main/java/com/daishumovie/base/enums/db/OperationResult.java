package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/6/29 20:11.
 */
@Getter
public enum OperationResult {

    success("成功"),
    fail("失败");

    public final String value;
    OperationResult(String value) {

        this.value = value;
    }

    public static String getValue(String name) {

        if (null != name && name.length() > 0) {
            OperationResult[] results = OperationResult.values();
            for (OperationResult result : results) {
                if (name.equals(result.name())) {
                    return result.value;
                }
            }
        }
        return "";
    }
}
