package com.daishumovie.dao.model.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class OtLog implements Serializable {
    private Integer id;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作对象
     */
    private String operationObject;

    /**
     * 操作人,-1时候表示"未登录"
     */
    private Integer operator;

    /**
     * 操作人IP
     */
    private String ip;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 耗时
     */
    private Integer consumeTime;

    /**
     * 操作结果
     */
    private String operationResult;

    /**
     * 操作参数
     */
    private String operationParam;

    /**
     * 描述
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationObject() {
        return operationObject;
    }

    public void setOperationObject(String operationObject) {
        this.operationObject = operationObject;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Integer consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}