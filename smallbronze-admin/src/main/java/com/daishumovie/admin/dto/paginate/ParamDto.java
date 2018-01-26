package com.daishumovie.admin.dto.paginate;

import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/8/28 15:24.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParamDto {

    public enum order {
        asc,desc
    }

    private order sort_order;

    private String sort_name;

    private Integer page_number;

    private Integer page_size;

    public <T> Page<T> page() {

        if (null == page_number || page_number <= 0) {
            page_number = 1;
            page_size = 20;
        }
        return new Page<>(page_number,page_size);
    }

    public String orderString() {

        if (StringUtil.isNotEmpty(sort_name) && null != sort_order) {
            return sort_name + " " + sort_order.name();
        }
        return StringUtil.empty;
    }

    public int offset() {

        return (page_number - 1) * page_size;

    }

    public int limit() {

        return page_size;
    }

    public boolean isSort() {

        return StringUtil.isNotEmpty(sort_name) && null != sort_order;
    }
}
