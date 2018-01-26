package com.daishumovie.base.enums.db;

import lombok.Getter;

/**
 * Created by yang on 2017/9/6.
 * 审核状态 0 待审核 1 机器审核已通过 2 机器审核未通过 3 人工审核已通过 4 人工审核未通过
 */
@Getter
public enum AuditStatus {

    WAIT("待审核",0),
    MACHINE_AUDIT_PASS("机审已通过",1),
    MACHINE_AUDIT_NOT_PASS("机审未通过",2),
    MAN_AUDIT_NOT_PASS("人工审未通过",3),
    MAN_AUDIT_PASS("人工审已通过",4);

    private final String name; //名称
    private final Integer value; //数据库存储至

    AuditStatus(String name, Integer value) {

        this.name = name;
        this.value = value;
    }

    public static AuditStatus get(Integer value) {

        if (null != value) {
            for (AuditStatus type : AuditStatus.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
        }
        return null;
    }
}
