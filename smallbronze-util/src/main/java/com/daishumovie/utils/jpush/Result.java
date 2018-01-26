package com.daishumovie.utils.jpush;

import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/10/16 15:47.
 * 极光推送返回结果
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Result {

    private STATUS status;
    private String msg;
    private Object data;

    @Getter
    public enum STATUS {

        success("推送成功"),
        fail("推送失败"),
        ;
        private String msg;

        STATUS(String msg) {

            this.msg = msg;
        }
    }

    public static final Result instance = new Result();

    private Result() {

    }

    private Result(STATUS status, String msg, Object data) {

        this.status = status;
        this.msg = StringUtil.trim(msg);
        this.data = data;
    }

    public boolean isOk() {

        return this.status.equals(STATUS.success);
    }

    public Result success() {

        return success(StringUtil.empty);
    }

    public Result success(Object data) {

        return new Result(STATUS.success, StringUtil.empty, data);
    }

    public Result fail(String ...msg) {

        if (CollectionUtils.arrayIsNullOrEmpty(msg)) {
            return new Result(STATUS.fail, "操作失败", StringUtil.empty);
        }
        return new Result(STATUS.fail, msg[0], StringUtil.empty);
    }
}
