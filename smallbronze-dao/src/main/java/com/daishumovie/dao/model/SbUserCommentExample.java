package com.daishumovie.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SbUserCommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public SbUserCommentExample() {
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

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(Integer value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Integer value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Integer value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Integer value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Integer value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Integer> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Integer> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Integer value1, Integer value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Integer value1, Integer value2) {
            addCriterion("app_id not between", value1, value2, "appId");
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

        public Criteria andTargetIdEqualTo(Integer value) {
            addCriterion("target_id =", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotEqualTo(Integer value) {
            addCriterion("target_id <>", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThan(Integer value) {
            addCriterion("target_id >", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("target_id >=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThan(Integer value) {
            addCriterion("target_id <", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThanOrEqualTo(Integer value) {
            addCriterion("target_id <=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdIn(List<Integer> values) {
            addCriterion("target_id in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotIn(List<Integer> values) {
            addCriterion("target_id not in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdBetween(Integer value1, Integer value2) {
            addCriterion("target_id between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotBetween(Integer value1, Integer value2) {
            addCriterion("target_id not between", value1, value2, "targetId");
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

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPuidIsNull() {
            addCriterion("puid is null");
            return (Criteria) this;
        }

        public Criteria andPuidIsNotNull() {
            addCriterion("puid is not null");
            return (Criteria) this;
        }

        public Criteria andPuidEqualTo(Integer value) {
            addCriterion("puid =", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidNotEqualTo(Integer value) {
            addCriterion("puid <>", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidGreaterThan(Integer value) {
            addCriterion("puid >", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("puid >=", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidLessThan(Integer value) {
            addCriterion("puid <", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidLessThanOrEqualTo(Integer value) {
            addCriterion("puid <=", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidIn(List<Integer> values) {
            addCriterion("puid in", values, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidNotIn(List<Integer> values) {
            addCriterion("puid not in", values, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidBetween(Integer value1, Integer value2) {
            addCriterion("puid between", value1, value2, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidNotBetween(Integer value1, Integer value2) {
            addCriterion("puid not between", value1, value2, "puid");
            return (Criteria) this;
        }

        public Criteria andPcidIsNull() {
            addCriterion("pcid is null");
            return (Criteria) this;
        }

        public Criteria andPcidIsNotNull() {
            addCriterion("pcid is not null");
            return (Criteria) this;
        }

        public Criteria andPcidEqualTo(Integer value) {
            addCriterion("pcid =", value, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidNotEqualTo(Integer value) {
            addCriterion("pcid <>", value, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidGreaterThan(Integer value) {
            addCriterion("pcid >", value, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pcid >=", value, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidLessThan(Integer value) {
            addCriterion("pcid <", value, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidLessThanOrEqualTo(Integer value) {
            addCriterion("pcid <=", value, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidIn(List<Integer> values) {
            addCriterion("pcid in", values, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidNotIn(List<Integer> values) {
            addCriterion("pcid not in", values, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidBetween(Integer value1, Integer value2) {
            addCriterion("pcid between", value1, value2, "pcid");
            return (Criteria) this;
        }

        public Criteria andPcidNotBetween(Integer value1, Integer value2) {
            addCriterion("pcid not between", value1, value2, "pcid");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andPraiseNumIsNull() {
            addCriterion("praise_num is null");
            return (Criteria) this;
        }

        public Criteria andPraiseNumIsNotNull() {
            addCriterion("praise_num is not null");
            return (Criteria) this;
        }

        public Criteria andPraiseNumEqualTo(Integer value) {
            addCriterion("praise_num =", value, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumNotEqualTo(Integer value) {
            addCriterion("praise_num <>", value, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumGreaterThan(Integer value) {
            addCriterion("praise_num >", value, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("praise_num >=", value, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumLessThan(Integer value) {
            addCriterion("praise_num <", value, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumLessThanOrEqualTo(Integer value) {
            addCriterion("praise_num <=", value, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumIn(List<Integer> values) {
            addCriterion("praise_num in", values, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumNotIn(List<Integer> values) {
            addCriterion("praise_num not in", values, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumBetween(Integer value1, Integer value2) {
            addCriterion("praise_num between", value1, value2, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andPraiseNumNotBetween(Integer value1, Integer value2) {
            addCriterion("praise_num not between", value1, value2, "praiseNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumIsNull() {
            addCriterion("criticism_num is null");
            return (Criteria) this;
        }

        public Criteria andCriticismNumIsNotNull() {
            addCriterion("criticism_num is not null");
            return (Criteria) this;
        }

        public Criteria andCriticismNumEqualTo(Integer value) {
            addCriterion("criticism_num =", value, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumNotEqualTo(Integer value) {
            addCriterion("criticism_num <>", value, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumGreaterThan(Integer value) {
            addCriterion("criticism_num >", value, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("criticism_num >=", value, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumLessThan(Integer value) {
            addCriterion("criticism_num <", value, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumLessThanOrEqualTo(Integer value) {
            addCriterion("criticism_num <=", value, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumIn(List<Integer> values) {
            addCriterion("criticism_num in", values, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumNotIn(List<Integer> values) {
            addCriterion("criticism_num not in", values, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumBetween(Integer value1, Integer value2) {
            addCriterion("criticism_num between", value1, value2, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andCriticismNumNotBetween(Integer value1, Integer value2) {
            addCriterion("criticism_num not between", value1, value2, "criticismNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumIsNull() {
            addCriterion("reply_num is null");
            return (Criteria) this;
        }

        public Criteria andReplyNumIsNotNull() {
            addCriterion("reply_num is not null");
            return (Criteria) this;
        }

        public Criteria andReplyNumEqualTo(Integer value) {
            addCriterion("reply_num =", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotEqualTo(Integer value) {
            addCriterion("reply_num <>", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumGreaterThan(Integer value) {
            addCriterion("reply_num >", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_num >=", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumLessThan(Integer value) {
            addCriterion("reply_num <", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumLessThanOrEqualTo(Integer value) {
            addCriterion("reply_num <=", value, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumIn(List<Integer> values) {
            addCriterion("reply_num in", values, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotIn(List<Integer> values) {
            addCriterion("reply_num not in", values, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumBetween(Integer value1, Integer value2) {
            addCriterion("reply_num between", value1, value2, "replyNum");
            return (Criteria) this;
        }

        public Criteria andReplyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_num not between", value1, value2, "replyNum");
            return (Criteria) this;
        }

        public Criteria andDiffValueIsNull() {
            addCriterion("diff_value is null");
            return (Criteria) this;
        }

        public Criteria andDiffValueIsNotNull() {
            addCriterion("diff_value is not null");
            return (Criteria) this;
        }

        public Criteria andDiffValueEqualTo(Integer value) {
            addCriterion("diff_value =", value, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueNotEqualTo(Integer value) {
            addCriterion("diff_value <>", value, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueGreaterThan(Integer value) {
            addCriterion("diff_value >", value, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("diff_value >=", value, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueLessThan(Integer value) {
            addCriterion("diff_value <", value, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueLessThanOrEqualTo(Integer value) {
            addCriterion("diff_value <=", value, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueIn(List<Integer> values) {
            addCriterion("diff_value in", values, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueNotIn(List<Integer> values) {
            addCriterion("diff_value not in", values, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueBetween(Integer value1, Integer value2) {
            addCriterion("diff_value between", value1, value2, "diffValue");
            return (Criteria) this;
        }

        public Criteria andDiffValueNotBetween(Integer value1, Integer value2) {
            addCriterion("diff_value not between", value1, value2, "diffValue");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andImgListIsNull() {
            addCriterion("img_list is null");
            return (Criteria) this;
        }

        public Criteria andImgListIsNotNull() {
            addCriterion("img_list is not null");
            return (Criteria) this;
        }

        public Criteria andImgListEqualTo(String value) {
            addCriterion("img_list =", value, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListNotEqualTo(String value) {
            addCriterion("img_list <>", value, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListGreaterThan(String value) {
            addCriterion("img_list >", value, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListGreaterThanOrEqualTo(String value) {
            addCriterion("img_list >=", value, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListLessThan(String value) {
            addCriterion("img_list <", value, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListLessThanOrEqualTo(String value) {
            addCriterion("img_list <=", value, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListLike(String value) {
            addCriterion("img_list like", value, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListNotLike(String value) {
            addCriterion("img_list not like", value, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListIn(List<String> values) {
            addCriterion("img_list in", values, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListNotIn(List<String> values) {
            addCriterion("img_list not in", values, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListBetween(String value1, String value2) {
            addCriterion("img_list between", value1, value2, "imgList");
            return (Criteria) this;
        }

        public Criteria andImgListNotBetween(String value1, String value2) {
            addCriterion("img_list not between", value1, value2, "imgList");
            return (Criteria) this;
        }

        public Criteria andVideoIdIsNull() {
            addCriterion("video_id is null");
            return (Criteria) this;
        }

        public Criteria andVideoIdIsNotNull() {
            addCriterion("video_id is not null");
            return (Criteria) this;
        }

        public Criteria andVideoIdEqualTo(Integer value) {
            addCriterion("video_id =", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotEqualTo(Integer value) {
            addCriterion("video_id <>", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdGreaterThan(Integer value) {
            addCriterion("video_id >", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("video_id >=", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdLessThan(Integer value) {
            addCriterion("video_id <", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdLessThanOrEqualTo(Integer value) {
            addCriterion("video_id <=", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdIn(List<Integer> values) {
            addCriterion("video_id in", values, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotIn(List<Integer> values) {
            addCriterion("video_id not in", values, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdBetween(Integer value1, Integer value2) {
            addCriterion("video_id between", value1, value2, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("video_id not between", value1, value2, "videoId");
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

        public Criteria andAuditStatusIsNull() {
            addCriterion("audit_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNotNull() {
            addCriterion("audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusEqualTo(Integer value) {
            addCriterion("audit_status =", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotEqualTo(Integer value) {
            addCriterion("audit_status <>", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThan(Integer value) {
            addCriterion("audit_status >", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_status >=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThan(Integer value) {
            addCriterion("audit_status <", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("audit_status <=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIn(List<Integer> values) {
            addCriterion("audit_status in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotIn(List<Integer> values) {
            addCriterion("audit_status not in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusBetween(Integer value1, Integer value2) {
            addCriterion("audit_status between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_status not between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidIsNull() {
            addCriterion("audit_op_uid is null");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidIsNotNull() {
            addCriterion("audit_op_uid is not null");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidEqualTo(Integer value) {
            addCriterion("audit_op_uid =", value, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidNotEqualTo(Integer value) {
            addCriterion("audit_op_uid <>", value, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidGreaterThan(Integer value) {
            addCriterion("audit_op_uid >", value, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_op_uid >=", value, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidLessThan(Integer value) {
            addCriterion("audit_op_uid <", value, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidLessThanOrEqualTo(Integer value) {
            addCriterion("audit_op_uid <=", value, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidIn(List<Integer> values) {
            addCriterion("audit_op_uid in", values, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidNotIn(List<Integer> values) {
            addCriterion("audit_op_uid not in", values, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidBetween(Integer value1, Integer value2) {
            addCriterion("audit_op_uid between", value1, value2, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditOpUidNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_op_uid not between", value1, value2, "auditOpUid");
            return (Criteria) this;
        }

        public Criteria andAuditDescIsNull() {
            addCriterion("audit_desc is null");
            return (Criteria) this;
        }

        public Criteria andAuditDescIsNotNull() {
            addCriterion("audit_desc is not null");
            return (Criteria) this;
        }

        public Criteria andAuditDescEqualTo(String value) {
            addCriterion("audit_desc =", value, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescNotEqualTo(String value) {
            addCriterion("audit_desc <>", value, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescGreaterThan(String value) {
            addCriterion("audit_desc >", value, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescGreaterThanOrEqualTo(String value) {
            addCriterion("audit_desc >=", value, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescLessThan(String value) {
            addCriterion("audit_desc <", value, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescLessThanOrEqualTo(String value) {
            addCriterion("audit_desc <=", value, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescLike(String value) {
            addCriterion("audit_desc like", value, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescNotLike(String value) {
            addCriterion("audit_desc not like", value, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescIn(List<String> values) {
            addCriterion("audit_desc in", values, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescNotIn(List<String> values) {
            addCriterion("audit_desc not in", values, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescBetween(String value1, String value2) {
            addCriterion("audit_desc between", value1, value2, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andAuditDescNotBetween(String value1, String value2) {
            addCriterion("audit_desc not between", value1, value2, "auditDesc");
            return (Criteria) this;
        }

        public Criteria andSuggestionIsNull() {
            addCriterion("suggestion is null");
            return (Criteria) this;
        }

        public Criteria andSuggestionIsNotNull() {
            addCriterion("suggestion is not null");
            return (Criteria) this;
        }

        public Criteria andSuggestionEqualTo(String value) {
            addCriterion("suggestion =", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionNotEqualTo(String value) {
            addCriterion("suggestion <>", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionGreaterThan(String value) {
            addCriterion("suggestion >", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionGreaterThanOrEqualTo(String value) {
            addCriterion("suggestion >=", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionLessThan(String value) {
            addCriterion("suggestion <", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionLessThanOrEqualTo(String value) {
            addCriterion("suggestion <=", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionLike(String value) {
            addCriterion("suggestion like", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionNotLike(String value) {
            addCriterion("suggestion not like", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionIn(List<String> values) {
            addCriterion("suggestion in", values, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionNotIn(List<String> values) {
            addCriterion("suggestion not in", values, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionBetween(String value1, String value2) {
            addCriterion("suggestion between", value1, value2, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionNotBetween(String value1, String value2) {
            addCriterion("suggestion not between", value1, value2, "suggestion");
            return (Criteria) this;
        }

        public Criteria andRateIsNull() {
            addCriterion("rate is null");
            return (Criteria) this;
        }

        public Criteria andRateIsNotNull() {
            addCriterion("rate is not null");
            return (Criteria) this;
        }

        public Criteria andRateEqualTo(Float value) {
            addCriterion("rate =", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotEqualTo(Float value) {
            addCriterion("rate <>", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThan(Float value) {
            addCriterion("rate >", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThanOrEqualTo(Float value) {
            addCriterion("rate >=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThan(Float value) {
            addCriterion("rate <", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThanOrEqualTo(Float value) {
            addCriterion("rate <=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateIn(List<Float> values) {
            addCriterion("rate in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotIn(List<Float> values) {
            addCriterion("rate not in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateBetween(Float value1, Float value2) {
            addCriterion("rate between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotBetween(Float value1, Float value2) {
            addCriterion("rate not between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andLabelIsNull() {
            addCriterion("label is null");
            return (Criteria) this;
        }

        public Criteria andLabelIsNotNull() {
            addCriterion("label is not null");
            return (Criteria) this;
        }

        public Criteria andLabelEqualTo(String value) {
            addCriterion("label =", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotEqualTo(String value) {
            addCriterion("label <>", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThan(String value) {
            addCriterion("label >", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThanOrEqualTo(String value) {
            addCriterion("label >=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThan(String value) {
            addCriterion("label <", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThanOrEqualTo(String value) {
            addCriterion("label <=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLike(String value) {
            addCriterion("label like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotLike(String value) {
            addCriterion("label not like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelIn(List<String> values) {
            addCriterion("label in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotIn(List<String> values) {
            addCriterion("label not in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelBetween(String value1, String value2) {
            addCriterion("label between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotBetween(String value1, String value2) {
            addCriterion("label not between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNull() {
            addCriterion("audit_time is null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNotNull() {
            addCriterion("audit_time is not null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeEqualTo(Date value) {
            addCriterion("audit_time =", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotEqualTo(Date value) {
            addCriterion("audit_time <>", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThan(Date value) {
            addCriterion("audit_time >", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("audit_time >=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThan(Date value) {
            addCriterion("audit_time <", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThanOrEqualTo(Date value) {
            addCriterion("audit_time <=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIn(List<Date> values) {
            addCriterion("audit_time in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotIn(List<Date> values) {
            addCriterion("audit_time not in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeBetween(Date value1, Date value2) {
            addCriterion("audit_time between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotBetween(Date value1, Date value2) {
            addCriterion("audit_time not between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeIsNull() {
            addCriterion("machine_audit_time is null");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeIsNotNull() {
            addCriterion("machine_audit_time is not null");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeEqualTo(Date value) {
            addCriterion("machine_audit_time =", value, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeNotEqualTo(Date value) {
            addCriterion("machine_audit_time <>", value, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeGreaterThan(Date value) {
            addCriterion("machine_audit_time >", value, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("machine_audit_time >=", value, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeLessThan(Date value) {
            addCriterion("machine_audit_time <", value, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeLessThanOrEqualTo(Date value) {
            addCriterion("machine_audit_time <=", value, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeIn(List<Date> values) {
            addCriterion("machine_audit_time in", values, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeNotIn(List<Date> values) {
            addCriterion("machine_audit_time not in", values, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeBetween(Date value1, Date value2) {
            addCriterion("machine_audit_time between", value1, value2, "machineAuditTime");
            return (Criteria) this;
        }

        public Criteria andMachineAuditTimeNotBetween(Date value1, Date value2) {
            addCriterion("machine_audit_time not between", value1, value2, "machineAuditTime");
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