package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.dao.model.SbMaterialContent;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/8/28 15:33.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialDto extends SbMaterialContent {

    private String operatorName;

    public String getCreateTimeFormat() {

        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }
        return StringUtil.empty;
    }

    public String getUpdateTimeFormat() {

        if (null != super.getUpdateTime()) {
            return DateUtil.BASIC.format(super.getUpdateTime());
        }
        return StringUtil.empty;
    }

    public String getShelfStatus() {

        if (null != super.getIsOnShelf()) {
            Integer isOnShelf = super.getIsOnShelf();
            Whether whether = Whether.get(isOnShelf);
            if (null != whether) {
                if (whether.equals(Whether.yes)) {
                    return "上架";
                } else {
                    return "下架";
                }
            }
        }
        return StringUtil.empty;
    }

}
