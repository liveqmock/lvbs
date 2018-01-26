package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationResult;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.model.auth.OtLog;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/9/11 16:23.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LogDto extends OtLog {

    private String operatorName;

    private String paramTime;

    public String getTypeName() {

        return OperationType.getValue(super.getOperationType());
    }

    public String getObjectName() {

        return OperationObject.getValue(super.getOperationObject());
    }

    public String getResultName() {

        return OperationResult.getValue(super.getOperationResult());
    }

    public String getTimeFormat() {

        if (null != super.getOperationTime()) {
            return DateUtil.BASIC.format(super.getOperationTime());
        }
        return StringUtil.empty;
    }
}
