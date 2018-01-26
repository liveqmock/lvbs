package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.PushPlatform;
import com.daishumovie.base.enums.db.PushStatus;
import com.daishumovie.base.enums.db.PushTargetType;
import com.daishumovie.base.enums.db.PushWay;
import com.daishumovie.dao.model.SbPushTask;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/10/18 10:14.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PushTaskDto extends SbPushTask {

    private String pusher;
    private String canceler;

    public String getStatusName() {

        if (null != super.getStatus()) {
            PushStatus status = PushStatus.get(super.getStatus());
            if (null != status) {
                return status.getName();
            }
        }
        return StringUtil.empty;
    }

    public String getTypeName() {

        if (null != super.getTargetType()) {
            PushTargetType targetType = PushTargetType.get(super.getTargetType());
            if (null != targetType) {
                return targetType.getName();
            }
        }
        return StringUtil.empty;
    }

    public String pushTimeFormat() {

        if (null != super.getPushTime()) {
            return DateUtil.BASIC.format(super.getPushTime());
        }
        return StringUtil.empty;
    }

    public String getPushWay() {

        if (null != super.getWay()) {
            PushWay way = PushWay.get(super.getWay());
            if (null != way) {
                return way.getName();
            }
        }
        return StringUtil.empty;
    }

    public String getCancelTime() {

        if (null != super.getModifyTime()) {
            return DateUtil.BASIC.format(super.getModifyTime());
        }
        return StringUtil.empty;
    }

    public String getCreateTimeFormat() {

        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }
        return StringUtil.empty;
    }

    public String getPlatformName() {

        if (null != super.getPlatform()) {
            PushPlatform platform = PushPlatform.get(super.getPlatform());
            if (null != platform) {
                return platform.getName();
            }
        }
        return StringUtil.empty;
    }

    public Integer getReceivedCount() {

        Integer iosReceivedCount = null == super.getIosReceivedCount() ? 0 : super.getIosReceivedCount();
        Integer androidReceivedCount = null == super.getAndroidReceivedCount() ? 0 : super.getAndroidReceivedCount();
        return iosReceivedCount + androidReceivedCount;
    }

    public Integer getClickTimes() {

        Integer iosClickTimes = null == super.getIosClickTimes() ? 0 : super.getIosClickTimes();
        Integer androidClickTimes = null == super.getAndroidClickTimes() ? 0 : super.getAndroidClickTimes();
        return iosClickTimes + androidClickTimes;
    }

    public String getClickRate() {

        Integer totalReceivedCount = getReceivedCount();
        Integer totalClickTimes = getClickTimes();

        if (totalReceivedCount == 0 || totalClickTimes == 0) {
            return "0%";
        } else {
            Double clickRate = Double.valueOf(totalClickTimes) / Double.valueOf(totalReceivedCount) * 100;
            return String.format("%4f", clickRate) + "%";
        }
    }

}
