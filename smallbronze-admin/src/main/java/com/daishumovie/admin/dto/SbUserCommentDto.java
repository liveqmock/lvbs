package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.AuditStatus;
import com.daishumovie.base.enums.db.UserCommentTargetType;
import com.daishumovie.dao.model.SbUserComment;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by yang on 2017/9/8.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SbUserCommentDto extends SbUserComment implements Serializable {

    private String reviewer;

    private String mobile;

    private String userType;

    private String videoTitle;

    private String auditor;

    public String getTargetTypeName(){
        return UserCommentTargetType.get(this.getTargetType()).getName();
    }

    public String getCommentTime() {

        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }
        return StringUtil.empty;
    }

    public String getAuditTimeFormat() {

        if (null != super.getAuditTime()) {
            return DateUtil.BASIC.format(super.getAuditTime());
        }
        return StringUtil.empty;
    }

    public String getAuditStatusName() {

        if (null != super.getAuditStatus()) {
            AuditStatus status = AuditStatus.get(super.getAuditStatus());
            if (null != status) {
                return status.getName();
            }
        }
        return StringUtil.empty;
    }

}
