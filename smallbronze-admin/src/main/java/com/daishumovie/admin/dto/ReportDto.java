package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.model.SbUserReport;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by yang on 2017/9/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ReportDto extends SbUserReport {

    private String auditName;

    private String contentName;
    //分类名称
    private String categoryName;

    private String cover;

    private String videoUrl;

    private String nickName;

    private String beNickName;

    private String mobile;

    private String beMobile;

    private String beUserType;

    private String userType;

    private String videoReleaseTime;

    public String getAuditDesc(){
        if (StringUtil.isNotEmpty(super.getAuditDesc())){
            return ReportResultEnum.get(Integer.valueOf(super.getAuditDesc())).getName();
        }
        return "";
    }

    public String getAuditStatusName(){
        return ReportStatusEnum.get(this.getStatus()).getName();
    }
    public String  getProblemContent(){
        if (this.getType().intValue() == ReportType.VIDEO.getCode()){
            return ReportProblem.get(this.getProblem()).getName();
        }else if (this.getType().intValue() == ReportType.COMMENT.getCode()){
            return CommentReportProblem.get(this.getProblem()).getName();
        }else if (this.getType().intValue() == ReportType.USER.getCode()){
            return UserReportProblem.get(this.getProblem()).getName();
        }
        return "";
    }
    public String getCreate_time(){
        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }
        return StringUtil.empty;
    }

    public String getAudit_time(){
        if (null != super.getAuditTime()){
            return DateUtil.BASIC.format(super.getAuditTime());
        }
        return StringUtil.empty;
    }
}
