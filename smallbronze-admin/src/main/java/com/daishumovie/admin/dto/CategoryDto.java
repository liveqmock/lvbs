package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.CategoryType;
import com.daishumovie.dao.model.SbCommonCategory;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/8/31 18:27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryDto extends SbCommonCategory {

    private String operatorName;

    public String getCreateTimeFormat() {

        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }
        return StringUtil.empty;
    }

    public String getTypeName() {

        if (null != super.getType()) {
            CategoryType type = CategoryType.get(super.getType());
            if (null != type) {
                return type.getName();
            }
        }
        return StringUtil.empty;
    }
}
