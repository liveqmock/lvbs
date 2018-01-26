package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.AdTargetType;
import com.daishumovie.base.enums.db.AdType;
import com.daishumovie.dao.model.SbAd;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Cheng Yufei
 * @create 2017-10-25 15:00
 *
 *
 **/
@Data
@EqualsAndHashCode(callSuper =  false)
public class AdDto extends SbAd {

    private String creatorName;

    public String getTargetTypeName() {

        if (null != super.getTargetType()) {
            AdTargetType targetType = AdTargetType.get(super.getTargetType());
            if (null != targetType) {
                return targetType.getName();
            }
        }
        return StringUtil.empty;
    }

    public String getAdTypeName() {

        if (null != super.getAdType()) {
            AdType type = AdType.get(super.getAdType());
            if (null != type) {
                return type.getName();
            }
        }
        return StringUtil.empty;
    }

}
