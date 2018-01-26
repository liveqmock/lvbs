package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.UploadVideosStatus;
import com.daishumovie.base.enums.db.VideoSource;
import com.daishumovie.dao.model.SbUploadVideos;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by yang on 2017/9/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SbUploadVideosDto extends SbUploadVideos implements Serializable {

    private String operatorName;

    public String getStatusName(){
        return UploadVideosStatus.get(this.getStatus()).getName();
    }

    public String getTypeName(){

        if (null != super.getType()) {
            VideoSource source = VideoSource.get(super.getType());
            if (null != source) {
                return source.getName();
            }
        }
        return StringUtil.empty;
    }
    public String getCreate_time(){
        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }
        return StringUtil.empty;
    }
}
