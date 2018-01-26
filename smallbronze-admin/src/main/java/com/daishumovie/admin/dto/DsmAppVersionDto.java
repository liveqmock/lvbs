package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.AppPlatEnum;
import com.daishumovie.dao.model.DsmAppVersion;
import com.daishumovie.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yang on 2017/5/26.
 */
@Setter
@Getter
public class DsmAppVersionDto extends DsmAppVersion {

    private String platName;

    private String updateTimeF;

    private String appName;

    public String getPlatName() {
        return AppPlatEnum.get(this.getPlat()).getName();
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getUpdateTimeF() {
        if(this.getUpdateTime() != null)
            return DateUtil.BASIC.format(this.getUpdateTime());
        return updateTimeF;
    }

    public void setUpdateTimeF(String updateTimeF) {
        this.updateTimeF = updateTimeF;
    }
}
