package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.ActivityStatus;
import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/10/20 11:39.
 */
@Data
@EqualsAndHashCode(callSuper =  false)
public class ActivityDto extends SbActivity {

    private String operatorName;
    private String modifierName;
    private int topicCount = 0;

    public String getStatusName() {

        if (null != super.getStatus()) {
            ActivityStatus status = ActivityStatus.get(super.getStatus());
            if (null != status) {
                return status.getName();
            }
        }
        return StringUtil.empty;
    }
}
