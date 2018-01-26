package com.daishumovie.base.dto;

import lombok.Getter;


import java.io.Serializable;

/**
 * @author zhuruisong on 2017/1/11
 * @since 1.0
 */
public class PageInDto implements Serializable{

    private @Getter int pageIndex;
    private @Getter int pageSize;

    public PageInDto(int pageIndex) {
        this.pageIndex = pageIndex;
        this.pageSize = 10;
    }

    public PageInDto(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (pageIndex - 1) * pageSize;
    }

    public int getOffsetForActivity() {
        return (pageIndex - 2) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }
}
