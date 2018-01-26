package com.daishumovie.admin.dto;

import com.daishumovie.dao.model.SbTopicBucket;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/9/21 16:37.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BucketDto extends SbTopicBucket {


    private String operatorName;
    private Integer videoCount;

    public String getCreateTimeFormat() {

        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }
        return StringUtil.empty;
    }

}
