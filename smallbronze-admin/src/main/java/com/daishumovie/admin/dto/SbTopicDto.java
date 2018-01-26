package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.AuditStatus;
import com.daishumovie.base.enums.db.ReportProblem;
import com.daishumovie.base.enums.db.TopicSource;
import com.daishumovie.base.enums.db.TopicStatus;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by yang on 2017/9/6.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SbTopicDto extends SbTopic implements Serializable {
    //操作人名称
    private String operatorName;
    //分类名称
    private String categoryName;

    private String cover;

    private String videoUrl;

    private String nickName;

    private String mobile;

    private String userType;

    private String description;

    private String publisherName;

    private String videoSource;

    private Integer playCount;

    private boolean fictitious;

    //是否是横长视频
    private boolean transverse = false;

    public String getAuditDesc(){
        if (StringUtil.isNotEmpty(super.getAuditDesc())){
            return ReportProblem.get(Integer.valueOf(super.getAuditDesc())).getName();
        }
        return "";
    }

    public Integer getFollow_num(){
        return super.getFollowNum();
    }


    /**
     * 顶数
     */
    public Integer getPraise_num(){
        return super.getPraiseNum();
    }

    /**
     * 踩数
     */
    public Integer getCriticism_num(){
        return super.getCriticismNum();
    }

    /**
     * 顶与踩差值
     */
    public Integer getDiff_value(){
        return super.getDiffValue();
    }

    /**
     * 总回复数
     */
    public Integer getReply_num(){
        return super.getReplyNum();
    }

    public String getSourceName(){
        return TopicSource.get(this.getSource()).getName();
    }

    public String getAuditStatusName(){
        for (AuditStatus as : AuditStatus.values()){
            if (as.WAIT.getValue().intValue() == this.getAuditStatus() || as.MACHINE_AUDIT_PASS.getValue().intValue() == this.getAuditStatus()
                    ||as.MACHINE_AUDIT_NOT_PASS.getValue() == this.getAuditStatus()){
                return as.WAIT.getName();
            }else if (as.MAN_AUDIT_NOT_PASS.getValue().intValue() == this.getAuditStatus()){
                return as.MAN_AUDIT_NOT_PASS.getName();
            }else if (as.MAN_AUDIT_PASS.getValue().intValue() == this.getAuditStatus()){
                return as.MAN_AUDIT_PASS.getName();
            }
        }//return AuditStatus.get(this.getAuditStatus()).getName();
        return null;
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

    public String getPublishTimeFormat() {

        if (null != super.getPublishTime()) {
            return DateUtil.BASIC.format(super.getPublishTime());
        }
        return StringUtil.empty;
    }

    public String getStatusName() {

        if (null != super.getStatus()) {
            TopicStatus status = TopicStatus.get(super.getStatus());
            if (null != status) {
                return status.getName();
            }
        }
        return StringUtil.empty;
    }

}
