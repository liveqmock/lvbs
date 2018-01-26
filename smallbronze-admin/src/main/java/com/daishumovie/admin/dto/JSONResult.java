package com.daishumovie.admin.dto;

import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.ui.ModelMap;

/**
 * Created by feiFan.gou on 2017/8/8 15:05.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JSONResult {

    public enum STATUS {

        SUCCESS, FAIL, UN_LOGIN, WITHOUT_AUTH
    }

    private STATUS status;
    private String msg;
    private Object data;

    private JSONResult(STATUS status, String msg, Object data) {

        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static JSONResult success() {

        return onlyStatus(STATUS.SUCCESS);
    }

    public static JSONResult success(Object data) {

        return new JSONResult(STATUS.SUCCESS, StringUtil.empty, data);
    }

    public static JSONResult fail(String... msg) {

        String errMsg = "操作失败";
        if (!CollectionUtils.arrayIsNullOrEmpty(msg)) {
            errMsg = msg[0];
        }
        return new JSONResult(STATUS.FAIL, errMsg, StringUtil.empty);
    }

    public static JSONResult unLogin() {

        return onlyStatus(STATUS.UN_LOGIN);
    }

    public static JSONResult onlyStatus(STATUS status) {

        return new JSONResult(status, StringUtil.empty, StringUtil.empty);
    }
}
