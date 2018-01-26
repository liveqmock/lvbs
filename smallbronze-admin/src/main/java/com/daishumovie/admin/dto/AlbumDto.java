package com.daishumovie.admin.dto;

import com.daishumovie.base.enums.db.AlbumStatus;
import com.daishumovie.dao.model.SbTopicAlbum;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by feiFan.gou on 2017/10/25 10:24.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AlbumDto extends SbTopicAlbum {

    private String operator;
    private String modifier;

    public String getStatusName() {

        if (null != super.getStatus()) {
            AlbumStatus status = AlbumStatus.get(super.getStatus());
            if (null != status) {
                return status.getName();
            }
        }
        return StringUtil.empty;
    }

    public String getCreateTimeFormat() {

        if (null != super.getCreateTime()) {
            return DateUtil.BASIC.format(super.getCreateTime());
        }
        return StringUtil.empty;
    }
    public String getModifyTimeFormat() {

        if (null != super.getModifyTime()) {
            return DateUtil.BASIC.format(super.getModifyTime());
        }
        return StringUtil.empty;
    }
}
