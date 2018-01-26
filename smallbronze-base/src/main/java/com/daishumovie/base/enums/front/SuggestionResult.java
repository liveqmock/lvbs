package com.daishumovie.base.enums.front;

import lombok.Getter;

/**
 * Created by yang on 2017/9/11.
 * 图片鉴黄结果(阿里返回值)
 * 建议用户处理，取值范围：[“pass”, “review”, “block”], pass:图片正常，
 * review：需要人工审核，block：图片违规，可以直接删除或者做限制处理
 */
@Getter
public enum SuggestionResult {

    PASS("pass","图片正常"),
    REVIEW("review","需要人工审核"),
    BLOCK("block","图片违规");
    private final String value;
    private final String name;

    SuggestionResult(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static SuggestionResult get(String value) {

        if (null != value) {
            SuggestionResult[] values = SuggestionResult.values();
            for (SuggestionResult v : values) {
                if (v.value.equals(value)) {
                    return v;
                }
            }
        }
        return null;
    }
}
