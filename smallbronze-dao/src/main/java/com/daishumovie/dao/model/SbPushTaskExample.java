package com.daishumovie.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SbPushTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public SbPushTaskExample() {
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

        public Criteria andAlertIsNull() {
            addCriterion("alert is null");
            return (Criteria) this;
        }

        public Criteria andAlertIsNotNull() {
            addCriterion("alert is not null");
            return (Criteria) this;
        }

        public Criteria andAlertEqualTo(String value) {
            addCriterion("alert =", value, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertNotEqualTo(String value) {
            addCriterion("alert <>", value, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertGreaterThan(String value) {
            addCriterion("alert >", value, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertGreaterThanOrEqualTo(String value) {
            addCriterion("alert >=", value, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertLessThan(String value) {
            addCriterion("alert <", value, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertLessThanOrEqualTo(String value) {
            addCriterion("alert <=", value, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertLike(String value) {
            addCriterion("alert like", value, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertNotLike(String value) {
            addCriterion("alert not like", value, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertIn(List<String> values) {
            addCriterion("alert in", values, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertNotIn(List<String> values) {
            addCriterion("alert not in", values, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertBetween(String value1, String value2) {
            addCriterion("alert between", value1, value2, "alert");
            return (Criteria) this;
        }

        public Criteria andAlertNotBetween(String value1, String value2) {
            addCriterion("alert not between", value1, value2, "alert");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNull() {
            addCriterion("platform is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNotNull() {
            addCriterion("platform is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformEqualTo(Integer value) {
            addCriterion("platform =", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotEqualTo(Integer value) {
            addCriterion("platform <>", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThan(Integer value) {
            addCriterion("platform >", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThanOrEqualTo(Integer value) {
            addCriterion("platform >=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThan(Integer value) {
            addCriterion("platform <", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThanOrEqualTo(Integer value) {
            addCriterion("platform <=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformIn(List<Integer> values) {
            addCriterion("platform in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotIn(List<Integer> values) {
            addCriterion("platform not in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformBetween(Integer value1, Integer value2) {
            addCriterion("platform between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotBetween(Integer value1, Integer value2) {
            addCriterion("platform not between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andWayIsNull() {
            addCriterion("way is null");
            return (Criteria) this;
        }

        public Criteria andWayIsNotNull() {
            addCriterion("way is not null");
            return (Criteria) this;
        }

        public Criteria andWayEqualTo(Integer value) {
            addCriterion("way =", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotEqualTo(Integer value) {
            addCriterion("way <>", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayGreaterThan(Integer value) {
            addCriterion("way >", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayGreaterThanOrEqualTo(Integer value) {
            addCriterion("way >=", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayLessThan(Integer value) {
            addCriterion("way <", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayLessThanOrEqualTo(Integer value) {
            addCriterion("way <=", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayIn(List<Integer> values) {
            addCriterion("way in", values, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotIn(List<Integer> values) {
            addCriterion("way not in", values, "way");
            return (Criteria) this;
        }

        public Criteria andWayBetween(Integer value1, Integer value2) {
            addCriterion("way between", value1, value2, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotBetween(Integer value1, Integer value2) {
            addCriterion("way not between", value1, value2, "way");
            return (Criteria) this;
        }

        public Criteria andPushTimeIsNull() {
            addCriterion("push_time is null");
            return (Criteria) this;
        }

        public Criteria andPushTimeIsNotNull() {
            addCriterion("push_time is not null");
            return (Criteria) this;
        }

        public Criteria andPushTimeEqualTo(Date value) {
            addCriterion("push_time =", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotEqualTo(Date value) {
            addCriterion("push_time <>", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeGreaterThan(Date value) {
            addCriterion("push_time >", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("push_time >=", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeLessThan(Date value) {
            addCriterion("push_time <", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeLessThanOrEqualTo(Date value) {
            addCriterion("push_time <=", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeIn(List<Date> values) {
            addCriterion("push_time in", values, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotIn(List<Date> values) {
            addCriterion("push_time not in", values, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeBetween(Date value1, Date value2) {
            addCriterion("push_time between", value1, value2, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotBetween(Date value1, Date value2) {
            addCriterion("push_time not between", value1, value2, "pushTime");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIsNull() {
            addCriterion("target_type is null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIsNotNull() {
            addCriterion("target_type is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeEqualTo(Integer value) {
            addCriterion("target_type =", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotEqualTo(Integer value) {
            addCriterion("target_type <>", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeGreaterThan(Integer value) {
            addCriterion("target_type >", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("target_type >=", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeLessThan(Integer value) {
            addCriterion("target_type <", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("target_type <=", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIn(List<Integer> values) {
            addCriterion("target_type in", values, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotIn(List<Integer> values) {
            addCriterion("target_type not in", values, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBetween(Integer value1, Integer value2) {
            addCriterion("target_type between", value1, value2, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("target_type not between", value1, value2, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetIdIsNull() {
            addCriterion("target_id is null");
            return (Criteria) this;
        }

        public Criteria andTargetIdIsNotNull() {
            addCriterion("target_id is not null");
            return (Criteria) this;
        }

        public Criteria andTargetIdEqualTo(String value) {
            addCriterion("target_id =", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotEqualTo(String value) {
            addCriterion("target_id <>", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThan(String value) {
            addCriterion("target_id >", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThanOrEqualTo(String value) {
            addCriterion("target_id >=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThan(String value) {
            addCriterion("target_id <", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThanOrEqualTo(String value) {
            addCriterion("target_id <=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLike(String value) {
            addCriterion("target_id like", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotLike(String value) {
            addCriterion("target_id not like", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdIn(List<String> values) {
            addCriterion("target_id in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotIn(List<String> values) {
            addCriterion("target_id not in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdBetween(String value1, String value2) {
            addCriterion("target_id between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotBetween(String value1, String value2) {
            addCriterion("target_id not between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andInputJsonIsNull() {
            addCriterion("input_json is null");
            return (Criteria) this;
        }

        public Criteria andInputJsonIsNotNull() {
            addCriterion("input_json is not null");
            return (Criteria) this;
        }

        public Criteria andInputJsonEqualTo(String value) {
            addCriterion("input_json =", value, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonNotEqualTo(String value) {
            addCriterion("input_json <>", value, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonGreaterThan(String value) {
            addCriterion("input_json >", value, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonGreaterThanOrEqualTo(String value) {
            addCriterion("input_json >=", value, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonLessThan(String value) {
            addCriterion("input_json <", value, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonLessThanOrEqualTo(String value) {
            addCriterion("input_json <=", value, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonLike(String value) {
            addCriterion("input_json like", value, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonNotLike(String value) {
            addCriterion("input_json not like", value, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonIn(List<String> values) {
            addCriterion("input_json in", values, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonNotIn(List<String> values) {
            addCriterion("input_json not in", values, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonBetween(String value1, String value2) {
            addCriterion("input_json between", value1, value2, "inputJson");
            return (Criteria) this;
        }

        public Criteria andInputJsonNotBetween(String value1, String value2) {
            addCriterion("input_json not between", value1, value2, "inputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonIsNull() {
            addCriterion("output_json is null");
            return (Criteria) this;
        }

        public Criteria andOutputJsonIsNotNull() {
            addCriterion("output_json is not null");
            return (Criteria) this;
        }

        public Criteria andOutputJsonEqualTo(String value) {
            addCriterion("output_json =", value, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonNotEqualTo(String value) {
            addCriterion("output_json <>", value, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonGreaterThan(String value) {
            addCriterion("output_json >", value, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonGreaterThanOrEqualTo(String value) {
            addCriterion("output_json >=", value, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonLessThan(String value) {
            addCriterion("output_json <", value, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonLessThanOrEqualTo(String value) {
            addCriterion("output_json <=", value, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonLike(String value) {
            addCriterion("output_json like", value, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonNotLike(String value) {
            addCriterion("output_json not like", value, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonIn(List<String> values) {
            addCriterion("output_json in", values, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonNotIn(List<String> values) {
            addCriterion("output_json not in", values, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonBetween(String value1, String value2) {
            addCriterion("output_json between", value1, value2, "outputJson");
            return (Criteria) this;
        }

        public Criteria andOutputJsonNotBetween(String value1, String value2) {
            addCriterion("output_json not between", value1, value2, "outputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonIsNull() {
            addCriterion("report_output_json is null");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonIsNotNull() {
            addCriterion("report_output_json is not null");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonEqualTo(String value) {
            addCriterion("report_output_json =", value, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonNotEqualTo(String value) {
            addCriterion("report_output_json <>", value, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonGreaterThan(String value) {
            addCriterion("report_output_json >", value, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonGreaterThanOrEqualTo(String value) {
            addCriterion("report_output_json >=", value, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonLessThan(String value) {
            addCriterion("report_output_json <", value, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonLessThanOrEqualTo(String value) {
            addCriterion("report_output_json <=", value, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonLike(String value) {
            addCriterion("report_output_json like", value, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonNotLike(String value) {
            addCriterion("report_output_json not like", value, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonIn(List<String> values) {
            addCriterion("report_output_json in", values, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonNotIn(List<String> values) {
            addCriterion("report_output_json not in", values, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonBetween(String value1, String value2) {
            addCriterion("report_output_json between", value1, value2, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andReportOutputJsonNotBetween(String value1, String value2) {
            addCriterion("report_output_json not between", value1, value2, "reportOutputJson");
            return (Criteria) this;
        }

        public Criteria andMsgIdIsNull() {
            addCriterion("msg_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgIdIsNotNull() {
            addCriterion("msg_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgIdEqualTo(String value) {
            addCriterion("msg_id =", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotEqualTo(String value) {
            addCriterion("msg_id <>", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThan(String value) {
            addCriterion("msg_id >", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_id >=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThan(String value) {
            addCriterion("msg_id <", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThanOrEqualTo(String value) {
            addCriterion("msg_id <=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLike(String value) {
            addCriterion("msg_id like", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotLike(String value) {
            addCriterion("msg_id not like", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdIn(List<String> values) {
            addCriterion("msg_id in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotIn(List<String> values) {
            addCriterion("msg_id not in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdBetween(String value1, String value2) {
            addCriterion("msg_id between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotBetween(String value1, String value2) {
            addCriterion("msg_id not between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountIsNull() {
            addCriterion("ios_received_count is null");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountIsNotNull() {
            addCriterion("ios_received_count is not null");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountEqualTo(Integer value) {
            addCriterion("ios_received_count =", value, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountNotEqualTo(Integer value) {
            addCriterion("ios_received_count <>", value, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountGreaterThan(Integer value) {
            addCriterion("ios_received_count >", value, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("ios_received_count >=", value, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountLessThan(Integer value) {
            addCriterion("ios_received_count <", value, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountLessThanOrEqualTo(Integer value) {
            addCriterion("ios_received_count <=", value, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountIn(List<Integer> values) {
            addCriterion("ios_received_count in", values, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountNotIn(List<Integer> values) {
            addCriterion("ios_received_count not in", values, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountBetween(Integer value1, Integer value2) {
            addCriterion("ios_received_count between", value1, value2, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosReceivedCountNotBetween(Integer value1, Integer value2) {
            addCriterion("ios_received_count not between", value1, value2, "iosReceivedCount");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesIsNull() {
            addCriterion("ios_click_times is null");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesIsNotNull() {
            addCriterion("ios_click_times is not null");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesEqualTo(Integer value) {
            addCriterion("ios_click_times =", value, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesNotEqualTo(Integer value) {
            addCriterion("ios_click_times <>", value, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesGreaterThan(Integer value) {
            addCriterion("ios_click_times >", value, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("ios_click_times >=", value, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesLessThan(Integer value) {
            addCriterion("ios_click_times <", value, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesLessThanOrEqualTo(Integer value) {
            addCriterion("ios_click_times <=", value, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesIn(List<Integer> values) {
            addCriterion("ios_click_times in", values, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesNotIn(List<Integer> values) {
            addCriterion("ios_click_times not in", values, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesBetween(Integer value1, Integer value2) {
            addCriterion("ios_click_times between", value1, value2, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andIosClickTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("ios_click_times not between", value1, value2, "iosClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountIsNull() {
            addCriterion("android_received_count is null");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountIsNotNull() {
            addCriterion("android_received_count is not null");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountEqualTo(Integer value) {
            addCriterion("android_received_count =", value, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountNotEqualTo(Integer value) {
            addCriterion("android_received_count <>", value, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountGreaterThan(Integer value) {
            addCriterion("android_received_count >", value, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("android_received_count >=", value, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountLessThan(Integer value) {
            addCriterion("android_received_count <", value, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountLessThanOrEqualTo(Integer value) {
            addCriterion("android_received_count <=", value, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountIn(List<Integer> values) {
            addCriterion("android_received_count in", values, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountNotIn(List<Integer> values) {
            addCriterion("android_received_count not in", values, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountBetween(Integer value1, Integer value2) {
            addCriterion("android_received_count between", value1, value2, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidReceivedCountNotBetween(Integer value1, Integer value2) {
            addCriterion("android_received_count not between", value1, value2, "androidReceivedCount");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesIsNull() {
            addCriterion("android_click_times is null");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesIsNotNull() {
            addCriterion("android_click_times is not null");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesEqualTo(Integer value) {
            addCriterion("android_click_times =", value, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesNotEqualTo(Integer value) {
            addCriterion("android_click_times <>", value, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesGreaterThan(Integer value) {
            addCriterion("android_click_times >", value, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("android_click_times >=", value, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesLessThan(Integer value) {
            addCriterion("android_click_times <", value, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesLessThanOrEqualTo(Integer value) {
            addCriterion("android_click_times <=", value, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesIn(List<Integer> values) {
            addCriterion("android_click_times in", values, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesNotIn(List<Integer> values) {
            addCriterion("android_click_times not in", values, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesBetween(Integer value1, Integer value2) {
            addCriterion("android_click_times between", value1, value2, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andAndroidClickTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("android_click_times not between", value1, value2, "androidClickTimes");
            return (Criteria) this;
        }

        public Criteria andPusherIdIsNull() {
            addCriterion("pusher_id is null");
            return (Criteria) this;
        }

        public Criteria andPusherIdIsNotNull() {
            addCriterion("pusher_id is not null");
            return (Criteria) this;
        }

        public Criteria andPusherIdEqualTo(Integer value) {
            addCriterion("pusher_id =", value, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdNotEqualTo(Integer value) {
            addCriterion("pusher_id <>", value, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdGreaterThan(Integer value) {
            addCriterion("pusher_id >", value, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pusher_id >=", value, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdLessThan(Integer value) {
            addCriterion("pusher_id <", value, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdLessThanOrEqualTo(Integer value) {
            addCriterion("pusher_id <=", value, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdIn(List<Integer> values) {
            addCriterion("pusher_id in", values, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdNotIn(List<Integer> values) {
            addCriterion("pusher_id not in", values, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdBetween(Integer value1, Integer value2) {
            addCriterion("pusher_id between", value1, value2, "pusherId");
            return (Criteria) this;
        }

        public Criteria andPusherIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pusher_id not between", value1, value2, "pusherId");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(Integer value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(Integer value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(Integer value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(Integer value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(Integer value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(Integer value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<Integer> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<Integer> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(Integer value1, Integer value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(Integer value1, Integer value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
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