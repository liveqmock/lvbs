package com.daishumovie.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DsmAppVersionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public DsmAppVersionExample() {
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

        public Criteria andVersionNumIsNull() {
            addCriterion("version_num is null");
            return (Criteria) this;
        }

        public Criteria andVersionNumIsNotNull() {
            addCriterion("version_num is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNumEqualTo(String value) {
            addCriterion("version_num =", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotEqualTo(String value) {
            addCriterion("version_num <>", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumGreaterThan(String value) {
            addCriterion("version_num >", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumGreaterThanOrEqualTo(String value) {
            addCriterion("version_num >=", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLessThan(String value) {
            addCriterion("version_num <", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLessThanOrEqualTo(String value) {
            addCriterion("version_num <=", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLike(String value) {
            addCriterion("version_num like", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotLike(String value) {
            addCriterion("version_num not like", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumIn(List<String> values) {
            addCriterion("version_num in", values, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotIn(List<String> values) {
            addCriterion("version_num not in", values, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumBetween(String value1, String value2) {
            addCriterion("version_num between", value1, value2, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotBetween(String value1, String value2) {
            addCriterion("version_num not between", value1, value2, "versionNum");
            return (Criteria) this;
        }

        public Criteria andBuildIsNull() {
            addCriterion("build is null");
            return (Criteria) this;
        }

        public Criteria andBuildIsNotNull() {
            addCriterion("build is not null");
            return (Criteria) this;
        }

        public Criteria andBuildEqualTo(Integer value) {
            addCriterion("build =", value, "build");
            return (Criteria) this;
        }

        public Criteria andBuildNotEqualTo(Integer value) {
            addCriterion("build <>", value, "build");
            return (Criteria) this;
        }

        public Criteria andBuildGreaterThan(Integer value) {
            addCriterion("build >", value, "build");
            return (Criteria) this;
        }

        public Criteria andBuildGreaterThanOrEqualTo(Integer value) {
            addCriterion("build >=", value, "build");
            return (Criteria) this;
        }

        public Criteria andBuildLessThan(Integer value) {
            addCriterion("build <", value, "build");
            return (Criteria) this;
        }

        public Criteria andBuildLessThanOrEqualTo(Integer value) {
            addCriterion("build <=", value, "build");
            return (Criteria) this;
        }

        public Criteria andBuildIn(List<Integer> values) {
            addCriterion("build in", values, "build");
            return (Criteria) this;
        }

        public Criteria andBuildNotIn(List<Integer> values) {
            addCriterion("build not in", values, "build");
            return (Criteria) this;
        }

        public Criteria andBuildBetween(Integer value1, Integer value2) {
            addCriterion("build between", value1, value2, "build");
            return (Criteria) this;
        }

        public Criteria andBuildNotBetween(Integer value1, Integer value2) {
            addCriterion("build not between", value1, value2, "build");
            return (Criteria) this;
        }

        public Criteria andPlatIsNull() {
            addCriterion("plat is null");
            return (Criteria) this;
        }

        public Criteria andPlatIsNotNull() {
            addCriterion("plat is not null");
            return (Criteria) this;
        }

        public Criteria andPlatEqualTo(Integer value) {
            addCriterion("plat =", value, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatNotEqualTo(Integer value) {
            addCriterion("plat <>", value, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatGreaterThan(Integer value) {
            addCriterion("plat >", value, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatGreaterThanOrEqualTo(Integer value) {
            addCriterion("plat >=", value, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatLessThan(Integer value) {
            addCriterion("plat <", value, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatLessThanOrEqualTo(Integer value) {
            addCriterion("plat <=", value, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatIn(List<Integer> values) {
            addCriterion("plat in", values, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatNotIn(List<Integer> values) {
            addCriterion("plat not in", values, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatBetween(Integer value1, Integer value2) {
            addCriterion("plat between", value1, value2, "plat");
            return (Criteria) this;
        }

        public Criteria andPlatNotBetween(Integer value1, Integer value2) {
            addCriterion("plat not between", value1, value2, "plat");
            return (Criteria) this;
        }

        public Criteria andMinVersionIsNull() {
            addCriterion("min_version is null");
            return (Criteria) this;
        }

        public Criteria andMinVersionIsNotNull() {
            addCriterion("min_version is not null");
            return (Criteria) this;
        }

        public Criteria andMinVersionEqualTo(String value) {
            addCriterion("min_version =", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionNotEqualTo(String value) {
            addCriterion("min_version <>", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionGreaterThan(String value) {
            addCriterion("min_version >", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionGreaterThanOrEqualTo(String value) {
            addCriterion("min_version >=", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionLessThan(String value) {
            addCriterion("min_version <", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionLessThanOrEqualTo(String value) {
            addCriterion("min_version <=", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionLike(String value) {
            addCriterion("min_version like", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionNotLike(String value) {
            addCriterion("min_version not like", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionIn(List<String> values) {
            addCriterion("min_version in", values, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionNotIn(List<String> values) {
            addCriterion("min_version not in", values, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionBetween(String value1, String value2) {
            addCriterion("min_version between", value1, value2, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionNotBetween(String value1, String value2) {
            addCriterion("min_version not between", value1, value2, "minVersion");
            return (Criteria) this;
        }

        public Criteria andUpdateDescIsNull() {
            addCriterion("update_desc is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDescIsNotNull() {
            addCriterion("update_desc is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDescEqualTo(String value) {
            addCriterion("update_desc =", value, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescNotEqualTo(String value) {
            addCriterion("update_desc <>", value, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescGreaterThan(String value) {
            addCriterion("update_desc >", value, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescGreaterThanOrEqualTo(String value) {
            addCriterion("update_desc >=", value, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescLessThan(String value) {
            addCriterion("update_desc <", value, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescLessThanOrEqualTo(String value) {
            addCriterion("update_desc <=", value, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescLike(String value) {
            addCriterion("update_desc like", value, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescNotLike(String value) {
            addCriterion("update_desc not like", value, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescIn(List<String> values) {
            addCriterion("update_desc in", values, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescNotIn(List<String> values) {
            addCriterion("update_desc not in", values, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescBetween(String value1, String value2) {
            addCriterion("update_desc between", value1, value2, "updateDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateDescNotBetween(String value1, String value2) {
            addCriterion("update_desc not between", value1, value2, "updateDesc");
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

        public Criteria andDownUrlIsNull() {
            addCriterion("down_url is null");
            return (Criteria) this;
        }

        public Criteria andDownUrlIsNotNull() {
            addCriterion("down_url is not null");
            return (Criteria) this;
        }

        public Criteria andDownUrlEqualTo(String value) {
            addCriterion("down_url =", value, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlNotEqualTo(String value) {
            addCriterion("down_url <>", value, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlGreaterThan(String value) {
            addCriterion("down_url >", value, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlGreaterThanOrEqualTo(String value) {
            addCriterion("down_url >=", value, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlLessThan(String value) {
            addCriterion("down_url <", value, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlLessThanOrEqualTo(String value) {
            addCriterion("down_url <=", value, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlLike(String value) {
            addCriterion("down_url like", value, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlNotLike(String value) {
            addCriterion("down_url not like", value, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlIn(List<String> values) {
            addCriterion("down_url in", values, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlNotIn(List<String> values) {
            addCriterion("down_url not in", values, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlBetween(String value1, String value2) {
            addCriterion("down_url between", value1, value2, "downUrl");
            return (Criteria) this;
        }

        public Criteria andDownUrlNotBetween(String value1, String value2) {
            addCriterion("down_url not between", value1, value2, "downUrl");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(String value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(String value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(String value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(String value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(String value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(String value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLike(String value) {
            addCriterion("channel_id like", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotLike(String value) {
            addCriterion("channel_id not like", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<String> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<String> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(String value1, String value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(String value1, String value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIsNull() {
            addCriterion("operator_id is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIsNotNull() {
            addCriterion("operator_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorIdEqualTo(Integer value) {
            addCriterion("operator_id =", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotEqualTo(Integer value) {
            addCriterion("operator_id <>", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdGreaterThan(Integer value) {
            addCriterion("operator_id >", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("operator_id >=", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdLessThan(Integer value) {
            addCriterion("operator_id <", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdLessThanOrEqualTo(Integer value) {
            addCriterion("operator_id <=", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIn(List<Integer> values) {
            addCriterion("operator_id in", values, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotIn(List<Integer> values) {
            addCriterion("operator_id not in", values, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdBetween(Integer value1, Integer value2) {
            addCriterion("operator_id between", value1, value2, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("operator_id not between", value1, value2, "operatorId");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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