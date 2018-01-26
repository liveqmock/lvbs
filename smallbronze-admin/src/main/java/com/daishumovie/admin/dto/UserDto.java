package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.UserType;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.base.enums.db.Gender;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/9/6 15:08.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends DsmUser {

    private String operatorName;

    public String getTypeName() {

        if (null != super.getType()) {
            return UserType.get(super.getType()).getName();
        }
        return StringUtil.empty;
    }

    public String getGender() {

        if (null != super.getSex()) {
            Gender gender = Gender.get(super.getSex());
            if (null != gender) {
                return gender.getName();
            }
        }
        return StringUtil.empty;
    }

    public String getIsTopicAuthName() {

        if (null != super.getIsTopicAuth()) {
            Whether whether = Whether.get(super.getIsTopicAuth());
            if (null != whether) {
                return whether.getName();
            }
        }
        return StringUtil.empty;
    }

    public String getIsReplyAuthName() {

        if (null != super.getIsReplyAuth()) {
            Whether whether = Whether.get(super.getIsReplyAuth());
            if (null != whether) {
                return whether.getName();
            }
        }
        return StringUtil.empty;
    }

    public String getRegisterTime() {

        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }

        return StringUtil.empty;
    }

    public String getLastLoginTimeFormat() {


        if (null != super.getLastLoginTime()) {
            return DateUtil.BASIC.format(super.getLastLoginTime());
        }

        return StringUtil.empty;
    }

}
