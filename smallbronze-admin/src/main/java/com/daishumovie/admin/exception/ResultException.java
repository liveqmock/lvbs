package com.daishumovie.admin.exception;

import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.CollectionUtils;
import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/5/11 19:07.
 */
@Getter
public class ResultException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrMsg errCode;
    public ResultException(String ...msg) {

        super(CollectionUtils.arrayIsNullOrEmpty(msg) ? "操作失败" : msg[0]);
    }

    public ResultException(ErrMsg msg) {

        super(msg.value);
        this.errCode = msg;
    }
}
