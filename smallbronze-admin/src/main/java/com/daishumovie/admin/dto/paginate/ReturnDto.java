package com.daishumovie.admin.dto.paginate;

import com.daishumovie.utils.Page;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/8/25 10:25.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class ReturnDto<T> {

    private Integer Total;
    private Integer pageNo;
    private Integer pageSize;
    private List<T> rows;

    public ReturnDto(Page<T> page){

        if (null == page) {
            this.Total = 0;
            this.pageSize = 0;
            this.pageNo = 0;
            this.rows = Lists.newArrayList();
        }
        else {
            this.Total = page.getTotal();
            this.pageNo = page.getPageIndex();
            this.pageSize = page.getPageSize();
            this.rows = null == page.getItems() ? Lists.newArrayList() : page.getItems();
        }
    }
}
