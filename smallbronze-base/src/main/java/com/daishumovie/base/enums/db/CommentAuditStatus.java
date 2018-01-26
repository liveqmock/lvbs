package com.daishumovie.base.enums.db;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by feiFan.gou on 2017/9/20 11:24.
 */
@Getter
public enum CommentAuditStatus {

    auditing(0,"待审核"),
    audit_not_pass(3,"审核未通过"),
    audit_pass(4, "审核通过");

    private Integer value;
    private String name;
    CommentAuditStatus(Integer value, String name) {

        this.value = value;
        this.name = name;
    }

    public static CommentAuditStatus get(Integer value) {

        if (null != value) {
            for (CommentAuditStatus status : CommentAuditStatus.values()) {
                if (Objects.equals(status.value, value)) {
                    return status;
                }
            }
        }
        return null;
    }
}
