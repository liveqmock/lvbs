package com.daishumovie.dao.model.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OtLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public OtLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIsNull() {
            addCriterion("operation_type is null");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIsNotNull() {
            addCriterion("operation_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperationTypeEqualTo(String value) {
            addCriterion("operation_type =", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotEqualTo(String value) {
            addCriterion("operation_type <>", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeGreaterThan(String value) {
            addCriterion("operation_type >", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeGreaterThanOrEqualTo(String value) {
            addCriterion("operation_type >=", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeLessThan(String value) {
            addCriterion("operation_type <", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeLessThanOrEqualTo(String value) {
            addCriterion("operation_type <=", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeLike(String value) {
            addCriterion("operation_type like", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotLike(String value) {
            addCriterion("operation_type not like", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIn(List<String> values) {
            addCriterion("operation_type in", values, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotIn(List<String> values) {
            addCriterion("operation_type not in", values, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeBetween(String value1, String value2) {
            addCriterion("operation_type between", value1, value2, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotBetween(String value1, String value2) {
            addCriterion("operation_type not between", value1, value2, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationObjectIsNull() {
            addCriterion("operation_object is null");
            return (Criteria) this;
        }

        public Criteria andOperationObjectIsNotNull() {
            addCriterion("operation_object is not null");
            return (Criteria) this;
        }

        public Criteria andOperationObjectEqualTo(String value) {
            addCriterion("operation_object =", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectNotEqualTo(String value) {
            addCriterion("operation_object <>", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectGreaterThan(String value) {
            addCriterion("operation_object >", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectGreaterThanOrEqualTo(String value) {
            addCriterion("operation_object >=", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectLessThan(String value) {
            addCriterion("operation_object <", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectLessThanOrEqualTo(String value) {
            addCriterion("operation_object <=", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectLike(String value) {
            addCriterion("operation_object like", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectNotLike(String value) {
            addCriterion("operation_object not like", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectIn(List<String> values) {
            addCriterion("operation_object in", values, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectNotIn(List<String> values) {
            addCriterion("operation_object not in", values, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectBetween(String value1, String value2) {
            addCriterion("operation_object between", value1, value2, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectNotBetween(String value1, String value2) {
            addCriterion("operation_object not between", value1, value2, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(Integer value) {
            addCriterion("operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(Integer value) {
            addCriterion("operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(Integer value) {
            addCriterion("operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(Integer value) {
            addCriterion("operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(Integer value) {
            addCriterion("operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<Integer> values) {
            addCriterion("operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<Integer> values) {
            addCriterion("operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(Integer value1, Integer value2) {
            addCriterion("operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(Integer value1, Integer value2) {
            addCriterion("operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andOperationTimeIsNull() {
            addCriterion("operation_time is null");
            return (Criteria) this;
        }

        public Criteria andOperationTimeIsNotNull() {
            addCriterion("operation_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperationTimeEqualTo(Date value) {
            addCriterion("operation_time =", value, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeNotEqualTo(Date value) {
            addCriterion("operation_time <>", value, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeGreaterThan(Date value) {
            addCriterion("operation_time >", value, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("operation_time >=", value, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeLessThan(Date value) {
            addCriterion("operation_time <", value, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeLessThanOrEqualTo(Date value) {
            addCriterion("operation_time <=", value, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeIn(List<Date> values) {
            addCriterion("operation_time in", values, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeNotIn(List<Date> values) {
            addCriterion("operation_time not in", values, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeBetween(Date value1, Date value2) {
            addCriterion("operation_time between", value1, value2, "operationTime");
            return (Criteria) this;
        }

        public Criteria andOperationTimeNotBetween(Date value1, Date value2) {
            addCriterion("operation_time not between", value1, value2, "operationTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeIsNull() {
            addCriterion("consume_time is null");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeIsNotNull() {
            addCriterion("consume_time is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeEqualTo(Integer value) {
            addCriterion("consume_time =", value, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeNotEqualTo(Integer value) {
            addCriterion("consume_time <>", value, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeGreaterThan(Integer value) {
            addCriterion("consume_time >", value, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("consume_time >=", value, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeLessThan(Integer value) {
            addCriterion("consume_time <", value, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeLessThanOrEqualTo(Integer value) {
            addCriterion("consume_time <=", value, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeIn(List<Integer> values) {
            addCriterion("consume_time in", values, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeNotIn(List<Integer> values) {
            addCriterion("consume_time not in", values, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeBetween(Integer value1, Integer value2) {
            addCriterion("consume_time between", value1, value2, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andConsumeTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("consume_time not between", value1, value2, "consumeTime");
            return (Criteria) this;
        }

        public Criteria andOperationResultIsNull() {
            addCriterion("operation_result is null");
            return (Criteria) this;
        }

        public Criteria andOperationResultIsNotNull() {
            addCriterion("operation_result is not null");
            return (Criteria) this;
        }

        public Criteria andOperationResultEqualTo(String value) {
            addCriterion("operation_result =", value, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultNotEqualTo(String value) {
            addCriterion("operation_result <>", value, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultGreaterThan(String value) {
            addCriterion("operation_result >", value, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultGreaterThanOrEqualTo(String value) {
            addCriterion("operation_result >=", value, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultLessThan(String value) {
            addCriterion("operation_result <", value, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultLessThanOrEqualTo(String value) {
            addCriterion("operation_result <=", value, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultLike(String value) {
            addCriterion("operation_result like", value, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultNotLike(String value) {
            addCriterion("operation_result not like", value, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultIn(List<String> values) {
            addCriterion("operation_result in", values, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultNotIn(List<String> values) {
            addCriterion("operation_result not in", values, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultBetween(String value1, String value2) {
            addCriterion("operation_result between", value1, value2, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationResultNotBetween(String value1, String value2) {
            addCriterion("operation_result not between", value1, value2, "operationResult");
            return (Criteria) this;
        }

        public Criteria andOperationParamIsNull() {
            addCriterion("operation_param is null");
            return (Criteria) this;
        }

        public Criteria andOperationParamIsNotNull() {
            addCriterion("operation_param is not null");
            return (Criteria) this;
        }

        public Criteria andOperationParamEqualTo(String value) {
            addCriterion("operation_param =", value, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamNotEqualTo(String value) {
            addCriterion("operation_param <>", value, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamGreaterThan(String value) {
            addCriterion("operation_param >", value, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamGreaterThanOrEqualTo(String value) {
            addCriterion("operation_param >=", value, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamLessThan(String value) {
            addCriterion("operation_param <", value, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamLessThanOrEqualTo(String value) {
            addCriterion("operation_param <=", value, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamLike(String value) {
            addCriterion("operation_param like", value, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamNotLike(String value) {
            addCriterion("operation_param not like", value, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamIn(List<String> values) {
            addCriterion("operation_param in", values, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamNotIn(List<String> values) {
            addCriterion("operation_param not in", values, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamBetween(String value1, String value2) {
            addCriterion("operation_param between", value1, value2, "operationParam");
            return (Criteria) this;
        }

        public Criteria andOperationParamNotBetween(String value1, String value2) {
            addCriterion("operation_param not between", value1, value2, "operationParam");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}