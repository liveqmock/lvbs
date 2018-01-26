package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by feiFan.gou on 2017/8/28 16:42.
 */
public abstract class BaseService {

    private static final Logger log = LoggerFactory.getLogger(BaseService.class);

    protected void validate(Object model, boolean isUpdate){

        // 通用校验
        if (null == model) {
            throw new ResultException(ErrMsg.param_error);
        }
        //具体校验
        specificVerify(model, isUpdate);
    }


    protected abstract void specificVerify(Object model, boolean isUpdate);

    protected enum log_position {

        before, after
    }

    protected void printLog(log_position position, String model, String method, Object ...params) {

        StringBuffer buffer = new StringBuffer("\n position ---> 【{}】 \n model ---> 【{}】 \n method ---> 【{}】 \n param ---> ");

        if (!CollectionUtils.arrayIsNullOrEmpty(params)) {
            for (int i = 0; i < params.length; i++)buffer.append("【{}】  ");
        }
        log.info(buffer.toString(), position, model, method, params);
    }
    protected void printException(String model, String method, Exception e) {

        StringBuffer buffer = new StringBuffer(" \n model ---> 【{}】 \n method ---> 【{}】 \n exception ---> ");

        log.info(buffer.toString(), model, method, e.getMessage());
        e.printStackTrace();
    }
}
