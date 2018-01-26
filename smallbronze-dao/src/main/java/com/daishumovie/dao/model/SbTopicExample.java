package com.daishumovie.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SbTopicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public SbTopicExample() {
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
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

        public Criteria andChannelIdEqualTo(Integer value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(Integer value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(Integer value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(Integer value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(Integer value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<Integer> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<Integer> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(Integer value1, Integer value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
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

        public Criteria andVideoCollectionIsNull() {
            addCriterion("video_collection is null");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionIsNotNull() {
            addCriterion("video_collection is not null");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionEqualTo(String value) {
            addCriterion("video_collection =", value, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionNotEqualTo(String value) {
            addCriterion("video_collection <>", value, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionGreaterThan(String value) {
            addCriterion("video_collection >", value, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionGreaterThanOrEqualTo(String value) {
            addCriterion("video_collection >=", value, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionLessThan(String value) {
            addCriterion("video_collection <", value, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionLessThanOrEqualTo(String value) {
            addCriterion("video_collection <=", value, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionLike(String value) {
            addCriterion("video_collection like", value, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionNotLike(String value) {
            addCriterion("video_collection not like", value, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionIn(List<String> values) {
            addCriterion("video_collection in", values, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionNotIn(List<String> values) {
            addCriterion("video_collection not in", values, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionBetween(String value1, String value2) {
            addCriterion("video_collection between", value1, value2, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andVideoCollectionNotBetween(String value1, String value2) {
            addCriterion("video_collection not between", value1, value2, "videoCollection");
            return (Criteria) this;
        }

        public Criteria andFollowNumIsNull() {
            addCriterion("follow_num is null");
            return (Criteria) this;
        }

        public Criteria andFollowNumIsNotNull() {
            addCriterion("follow_num is not null");
            return (Criteria) this;
        }

        public Criteria andFollowNumEqualTo(Integer value) {
            addCriterion("follow_num =", value, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumNotEqualTo(Integer value) {
            addCriterion("follow_num <>", value, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumGreaterThan(Integer value) {
            addCriterion("follow_num >", value, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("follow_num >=", value, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumLessThan(Integer value) {
            addCriterion("follow_num <", value, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumLessThanOrEqualTo(Integer value) {
            addCriterion("follow_num <=", value, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumIn(List<Integer> values) {
            addCriterion("follow_num in", values, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumNotIn(List<Integer> values) {
            addCriterion("follow_num not in", values, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumBetween(Integer value1, Integer value2) {
            addCriterion("follow_num between", value1, value2, "followNum");
            return (Criteria) this;
        }

        public Criteria andFollowNumNotBetween(Integer value1, Integer value2) {
            addCriterion("follow_num not between", value1, value2, "followNum");
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

        public Criteria andCreateOpeUidIsNull() {
            addCriterion("create_ope_uid is null");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidIsNotNull() {
            addCriterion("create_ope_uid is not null");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidEqualTo(Integer value) {
            addCriterion("create_ope_uid =", value, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidNotEqualTo(Integer value) {
            addCriterion("create_ope_uid <>", value, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidGreaterThan(Integer value) {
            addCriterion("create_ope_uid >", value, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_ope_uid >=", value, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidLessThan(Integer value) {
            addCriterion("create_ope_uid <", value, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidLessThanOrEqualTo(Integer value) {
            addCriterion("create_ope_uid <=", value, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidIn(List<Integer> values) {
            addCriterion("create_ope_uid in", values, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidNotIn(List<Integer> values) {
            addCriterion("create_ope_uid not in", values, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidBetween(Integer value1, Integer value2) {
            addCriterion("create_ope_uid between", value1, value2, "createOpeUid");
            return (Criteria) this;
        }

        public Criteria andCreateOpeUidNotBetween(Integer value1, Integer value2) {
            addCriterion("create_ope_uid not between", value1, value2, "createOpeUid");
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

        public Criteria andRateEqualTo(Double value) {
            addCriterion("rate =", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotEqualTo(Double value) {
            addCriterion("rate <>", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThan(Double value) {
            addCriterion("rate >", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThanOrEqualTo(Double value) {
            addCriterion("rate >=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThan(Double value) {
            addCriterion("rate <", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThanOrEqualTo(Double value) {
            addCriterion("rate <=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateIn(List<Double> values) {
            addCriterion("rate in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotIn(List<Double> values) {
            addCriterion("rate not in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateBetween(Double value1, Double value2) {
            addCriterion("rate between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotBetween(Double value1, Double value2) {
            addCriterion("rate not between", value1, value2, "rate");
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

        public Criteria andPublisherIsNull() {
            addCriterion("publisher is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNotNull() {
            addCriterion("publisher is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherEqualTo(Integer value) {
            addCriterion("publisher =", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotEqualTo(Integer value) {
            addCriterion("publisher <>", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThan(Integer value) {
            addCriterion("publisher >", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThanOrEqualTo(Integer value) {
            addCriterion("publisher >=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThan(Integer value) {
            addCriterion("publisher <", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThanOrEqualTo(Integer value) {
            addCriterion("publisher <=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherIn(List<Integer> values) {
            addCriterion("publisher in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotIn(List<Integer> values) {
            addCriterion("publisher not in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherBetween(Integer value1, Integer value2) {
            addCriterion("publisher between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotBetween(Integer value1, Integer value2) {
            addCriterion("publisher not between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNull() {
            addCriterion("activity_id is null");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNotNull() {
            addCriterion("activity_id is not null");
            return (Criteria) this;
        }

        public Criteria andActivityIdEqualTo(Integer value) {
            addCriterion("activity_id =", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotEqualTo(Integer value) {
            addCriterion("activity_id <>", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThan(Integer value) {
            addCriterion("activity_id >", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("activity_id >=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThan(Integer value) {
            addCriterion("activity_id <", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThanOrEqualTo(Integer value) {
            addCriterion("activity_id <=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdIn(List<Integer> values) {
            addCriterion("activity_id in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotIn(List<Integer> values) {
            addCriterion("activity_id not in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdBetween(Integer value1, Integer value2) {
            addCriterion("activity_id between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("activity_id not between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andOrdersIsNull() {
            addCriterion("orders is null");
            return (Criteria) this;
        }

        public Criteria andOrdersIsNotNull() {
            addCriterion("orders is not null");
            return (Criteria) this;
        }

        public Criteria andOrdersEqualTo(Integer value) {
            addCriterion("orders =", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersNotEqualTo(Integer value) {
            addCriterion("orders <>", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersGreaterThan(Integer value) {
            addCriterion("orders >", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersGreaterThanOrEqualTo(Integer value) {
            addCriterion("orders >=", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersLessThan(Integer value) {
            addCriterion("orders <", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersLessThanOrEqualTo(Integer value) {
            addCriterion("orders <=", value, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersIn(List<Integer> values) {
            addCriterion("orders in", values, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersNotIn(List<Integer> values) {
            addCriterion("orders not in", values, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersBetween(Integer value1, Integer value2) {
            addCriterion("orders between", value1, value2, "orders");
            return (Criteria) this;
        }

        public Criteria andOrdersNotBetween(Integer value1, Integer value2) {
            addCriterion("orders not between", value1, value2, "orders");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIsNull() {
            addCriterion("publish_time is null");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIsNotNull() {
            addCriterion("publish_time is not null");
            return (Criteria) this;
        }

        public Criteria andPublishTimeEqualTo(Date value) {
            addCriterion("publish_time =", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotEqualTo(Date value) {
            addCriterion("publish_time <>", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeGreaterThan(Date value) {
            addCriterion("publish_time >", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("publish_time >=", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeLessThan(Date value) {
            addCriterion("publish_time <", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeLessThanOrEqualTo(Date value) {
            addCriterion("publish_time <=", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIn(List<Date> values) {
            addCriterion("publish_time in", values, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotIn(List<Date> values) {
            addCriterion("publish_time not in", values, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeBetween(Date value1, Date value2) {
            addCriterion("publish_time between", value1, value2, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotBetween(Date value1, Date value2) {
            addCriterion("publish_time not between", value1, value2, "publishTime");
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