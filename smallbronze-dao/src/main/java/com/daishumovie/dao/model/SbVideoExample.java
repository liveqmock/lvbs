package com.daishumovie.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SbVideoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public SbVideoExample() {
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

        public Criteria andRefIdIsNull() {
            addCriterion("ref_id is null");
            return (Criteria) this;
        }

        public Criteria andRefIdIsNotNull() {
            addCriterion("ref_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefIdEqualTo(Integer value) {
            addCriterion("ref_id =", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotEqualTo(Integer value) {
            addCriterion("ref_id <>", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdGreaterThan(Integer value) {
            addCriterion("ref_id >", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ref_id >=", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdLessThan(Integer value) {
            addCriterion("ref_id <", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdLessThanOrEqualTo(Integer value) {
            addCriterion("ref_id <=", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdIn(List<Integer> values) {
            addCriterion("ref_id in", values, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotIn(List<Integer> values) {
            addCriterion("ref_id not in", values, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdBetween(Integer value1, Integer value2) {
            addCriterion("ref_id between", value1, value2, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ref_id not between", value1, value2, "refId");
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

        public Criteria andOriUrlIsNull() {
            addCriterion("ori_url is null");
            return (Criteria) this;
        }

        public Criteria andOriUrlIsNotNull() {
            addCriterion("ori_url is not null");
            return (Criteria) this;
        }

        public Criteria andOriUrlEqualTo(String value) {
            addCriterion("ori_url =", value, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlNotEqualTo(String value) {
            addCriterion("ori_url <>", value, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlGreaterThan(String value) {
            addCriterion("ori_url >", value, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlGreaterThanOrEqualTo(String value) {
            addCriterion("ori_url >=", value, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlLessThan(String value) {
            addCriterion("ori_url <", value, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlLessThanOrEqualTo(String value) {
            addCriterion("ori_url <=", value, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlLike(String value) {
            addCriterion("ori_url like", value, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlNotLike(String value) {
            addCriterion("ori_url not like", value, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlIn(List<String> values) {
            addCriterion("ori_url in", values, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlNotIn(List<String> values) {
            addCriterion("ori_url not in", values, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlBetween(String value1, String value2) {
            addCriterion("ori_url between", value1, value2, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andOriUrlNotBetween(String value1, String value2) {
            addCriterion("ori_url not between", value1, value2, "oriUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlIsNull() {
            addCriterion("format_url is null");
            return (Criteria) this;
        }

        public Criteria andFormatUrlIsNotNull() {
            addCriterion("format_url is not null");
            return (Criteria) this;
        }

        public Criteria andFormatUrlEqualTo(String value) {
            addCriterion("format_url =", value, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlNotEqualTo(String value) {
            addCriterion("format_url <>", value, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlGreaterThan(String value) {
            addCriterion("format_url >", value, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlGreaterThanOrEqualTo(String value) {
            addCriterion("format_url >=", value, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlLessThan(String value) {
            addCriterion("format_url <", value, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlLessThanOrEqualTo(String value) {
            addCriterion("format_url <=", value, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlLike(String value) {
            addCriterion("format_url like", value, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlNotLike(String value) {
            addCriterion("format_url not like", value, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlIn(List<String> values) {
            addCriterion("format_url in", values, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlNotIn(List<String> values) {
            addCriterion("format_url not in", values, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlBetween(String value1, String value2) {
            addCriterion("format_url between", value1, value2, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andFormatUrlNotBetween(String value1, String value2) {
            addCriterion("format_url not between", value1, value2, "formatUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlIsNull() {
            addCriterion("hls_url is null");
            return (Criteria) this;
        }

        public Criteria andHlsUrlIsNotNull() {
            addCriterion("hls_url is not null");
            return (Criteria) this;
        }

        public Criteria andHlsUrlEqualTo(String value) {
            addCriterion("hls_url =", value, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlNotEqualTo(String value) {
            addCriterion("hls_url <>", value, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlGreaterThan(String value) {
            addCriterion("hls_url >", value, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlGreaterThanOrEqualTo(String value) {
            addCriterion("hls_url >=", value, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlLessThan(String value) {
            addCriterion("hls_url <", value, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlLessThanOrEqualTo(String value) {
            addCriterion("hls_url <=", value, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlLike(String value) {
            addCriterion("hls_url like", value, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlNotLike(String value) {
            addCriterion("hls_url not like", value, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlIn(List<String> values) {
            addCriterion("hls_url in", values, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlNotIn(List<String> values) {
            addCriterion("hls_url not in", values, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlBetween(String value1, String value2) {
            addCriterion("hls_url between", value1, value2, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andHlsUrlNotBetween(String value1, String value2) {
            addCriterion("hls_url not between", value1, value2, "hlsUrl");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Float value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Float value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Float value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Float value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Float value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Float value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Float> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Float> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Float value1, Float value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Float value1, Float value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDimensionIsNull() {
            addCriterion("dimension is null");
            return (Criteria) this;
        }

        public Criteria andDimensionIsNotNull() {
            addCriterion("dimension is not null");
            return (Criteria) this;
        }

        public Criteria andDimensionEqualTo(String value) {
            addCriterion("dimension =", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionNotEqualTo(String value) {
            addCriterion("dimension <>", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionGreaterThan(String value) {
            addCriterion("dimension >", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionGreaterThanOrEqualTo(String value) {
            addCriterion("dimension >=", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionLessThan(String value) {
            addCriterion("dimension <", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionLessThanOrEqualTo(String value) {
            addCriterion("dimension <=", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionLike(String value) {
            addCriterion("dimension like", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionNotLike(String value) {
            addCriterion("dimension not like", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionIn(List<String> values) {
            addCriterion("dimension in", values, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionNotIn(List<String> values) {
            addCriterion("dimension not in", values, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionBetween(String value1, String value2) {
            addCriterion("dimension between", value1, value2, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionNotBetween(String value1, String value2) {
            addCriterion("dimension not between", value1, value2, "dimension");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("size is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("size is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(Integer value) {
            addCriterion("size =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(Integer value) {
            addCriterion("size <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(Integer value) {
            addCriterion("size >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("size >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(Integer value) {
            addCriterion("size <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(Integer value) {
            addCriterion("size <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<Integer> values) {
            addCriterion("size in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<Integer> values) {
            addCriterion("size not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(Integer value1, Integer value2) {
            addCriterion("size between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("size not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andCoverIsNull() {
            addCriterion("cover is null");
            return (Criteria) this;
        }

        public Criteria andCoverIsNotNull() {
            addCriterion("cover is not null");
            return (Criteria) this;
        }

        public Criteria andCoverEqualTo(String value) {
            addCriterion("cover =", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotEqualTo(String value) {
            addCriterion("cover <>", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverGreaterThan(String value) {
            addCriterion("cover >", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverGreaterThanOrEqualTo(String value) {
            addCriterion("cover >=", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLessThan(String value) {
            addCriterion("cover <", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLessThanOrEqualTo(String value) {
            addCriterion("cover <=", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLike(String value) {
            addCriterion("cover like", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotLike(String value) {
            addCriterion("cover not like", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverIn(List<String> values) {
            addCriterion("cover in", values, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotIn(List<String> values) {
            addCriterion("cover not in", values, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverBetween(String value1, String value2) {
            addCriterion("cover between", value1, value2, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotBetween(String value1, String value2) {
            addCriterion("cover not between", value1, value2, "cover");
            return (Criteria) this;
        }

        public Criteria andPlayNumIsNull() {
            addCriterion("play_num is null");
            return (Criteria) this;
        }

        public Criteria andPlayNumIsNotNull() {
            addCriterion("play_num is not null");
            return (Criteria) this;
        }

        public Criteria andPlayNumEqualTo(Integer value) {
            addCriterion("play_num =", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumNotEqualTo(Integer value) {
            addCriterion("play_num <>", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumGreaterThan(Integer value) {
            addCriterion("play_num >", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("play_num >=", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumLessThan(Integer value) {
            addCriterion("play_num <", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumLessThanOrEqualTo(Integer value) {
            addCriterion("play_num <=", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumIn(List<Integer> values) {
            addCriterion("play_num in", values, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumNotIn(List<Integer> values) {
            addCriterion("play_num not in", values, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumBetween(Integer value1, Integer value2) {
            addCriterion("play_num between", value1, value2, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumNotBetween(Integer value1, Integer value2) {
            addCriterion("play_num not between", value1, value2, "playNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumIsNull() {
            addCriterion("v_play_num is null");
            return (Criteria) this;
        }

        public Criteria andVPlayNumIsNotNull() {
            addCriterion("v_play_num is not null");
            return (Criteria) this;
        }

        public Criteria andVPlayNumEqualTo(Integer value) {
            addCriterion("v_play_num =", value, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumNotEqualTo(Integer value) {
            addCriterion("v_play_num <>", value, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumGreaterThan(Integer value) {
            addCriterion("v_play_num >", value, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("v_play_num >=", value, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumLessThan(Integer value) {
            addCriterion("v_play_num <", value, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumLessThanOrEqualTo(Integer value) {
            addCriterion("v_play_num <=", value, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumIn(List<Integer> values) {
            addCriterion("v_play_num in", values, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumNotIn(List<Integer> values) {
            addCriterion("v_play_num not in", values, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumBetween(Integer value1, Integer value2) {
            addCriterion("v_play_num between", value1, value2, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andVPlayNumNotBetween(Integer value1, Integer value2) {
            addCriterion("v_play_num not between", value1, value2, "vPlayNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumIsNull() {
            addCriterion("barrage_num is null");
            return (Criteria) this;
        }

        public Criteria andBarrageNumIsNotNull() {
            addCriterion("barrage_num is not null");
            return (Criteria) this;
        }

        public Criteria andBarrageNumEqualTo(Integer value) {
            addCriterion("barrage_num =", value, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumNotEqualTo(Integer value) {
            addCriterion("barrage_num <>", value, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumGreaterThan(Integer value) {
            addCriterion("barrage_num >", value, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("barrage_num >=", value, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumLessThan(Integer value) {
            addCriterion("barrage_num <", value, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumLessThanOrEqualTo(Integer value) {
            addCriterion("barrage_num <=", value, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumIn(List<Integer> values) {
            addCriterion("barrage_num in", values, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumNotIn(List<Integer> values) {
            addCriterion("barrage_num not in", values, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumBetween(Integer value1, Integer value2) {
            addCriterion("barrage_num between", value1, value2, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andBarrageNumNotBetween(Integer value1, Integer value2) {
            addCriterion("barrage_num not between", value1, value2, "barrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumIsNull() {
            addCriterion("v_barrage_num is null");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumIsNotNull() {
            addCriterion("v_barrage_num is not null");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumEqualTo(Integer value) {
            addCriterion("v_barrage_num =", value, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumNotEqualTo(Integer value) {
            addCriterion("v_barrage_num <>", value, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumGreaterThan(Integer value) {
            addCriterion("v_barrage_num >", value, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("v_barrage_num >=", value, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumLessThan(Integer value) {
            addCriterion("v_barrage_num <", value, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumLessThanOrEqualTo(Integer value) {
            addCriterion("v_barrage_num <=", value, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumIn(List<Integer> values) {
            addCriterion("v_barrage_num in", values, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumNotIn(List<Integer> values) {
            addCriterion("v_barrage_num not in", values, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumBetween(Integer value1, Integer value2) {
            addCriterion("v_barrage_num between", value1, value2, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andVBarrageNumNotBetween(Integer value1, Integer value2) {
            addCriterion("v_barrage_num not between", value1, value2, "vBarrageNum");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(Integer value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(Integer value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(Integer value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(Integer value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(Integer value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<Integer> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<Integer> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(Integer value1, Integer value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("source not between", value1, value2, "source");
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