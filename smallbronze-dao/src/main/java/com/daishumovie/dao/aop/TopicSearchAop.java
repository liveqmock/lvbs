package com.daishumovie.dao.aop;

/**
 * @author Cheng Yufei
 * @create 2017-11-03 16:20
 **/

import com.daishumovie.base.enums.db.AuditStatus;
import com.daishumovie.base.enums.db.TopicStatus;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.dao.model.SbVideoExample;
import com.daishumovie.search.enums.IndexType;
import com.daishumovie.search.pojo.TopicBo;
import com.daishumovie.search.service.BaseSearch;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.FastJsonUtils;
import io.searchbox.action.BulkableAction;
import io.searchbox.core.Index;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2017-11-03 15:08
 **/
@Aspect
@Slf4j
@Component
public class TopicSearchAop {

    @Autowired
    private SbTopicMapper topicMapper;

    @Autowired
    private SbVideoMapper videoMapper;

    @Autowired
    private BaseSearch baseSearch;


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicMapper.insert*(..))", returning = "num")
    public void insertAop(JoinPoint joinPoint, Integer num) throws Throwable {

        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        SbTopic sbTopic = (SbTopic) args[0];
        SbTopic topic = topicMapper.selectByPrimaryKey(sbTopic.getId());
        if (!(TopicStatus.published.getValue().equals(topic.getStatus()) && AuditStatus.MAN_AUDIT_PASS.getValue().equals(topic.getAuditStatus()))) {
            return;
        }
        TopicBo bo = getTopicBo(topic);
        baseSearch.add(bo, bo.getId().toString(), IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据-添加 id={},num={}", bo.getId().toString(), num);
    }

    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicMapper.deleteByPrimaryKey(Integer))", returning = "num")
    public void deleteByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {

        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        Integer id = Integer.valueOf(args[0].toString());

        io.searchbox.core.DocumentResult result = baseSearch.delete(id, IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据-删除id={},num={}", id, num);
    }

    @Around(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicMapper.deleteByExample(..))")
    public Integer deleteByExampleAop(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        SbTopicExample topicExample = (SbTopicExample) args[0];

        List<SbTopic> topics = topicMapper.selectByExample(topicExample);

        if (CollectionUtils.isNullOrEmpty(topics)) {
            return 0;
        }
        List<Integer> ids = new ArrayList<>();
        topics.forEach(t -> ids.add(t.getId()));
        Integer num = (Integer) joinPoint.proceed();
        baseSearch.deleteByIds(ids, IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据-删除ids={},num={}", ids, num);
        return num;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicMapper.updateByPrimaryKey*(..))", returning = "num")
    public void updateByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        SbTopic topic = (SbTopic) args[0];
        Integer topicId = topic.getId();
        SbTopic sbTopic = topicMapper.selectByPrimaryKey(topicId);
        if (null == sbTopic.getStatus() || null == sbTopic.getAuditStatus() || !TopicStatus.published.getValue().equals(sbTopic.getStatus()) || !AuditStatus.MAN_AUDIT_PASS.getValue().equals(sbTopic.getAuditStatus())) {
            baseSearch.delete(sbTopic.getId(), IndexType.topic.getIndex(), IndexType.topic.getType());
            log.info("topic - 同步搜索数据 - 下线删除：id:{}", sbTopic.getId());
            return;
        }
        TopicBo topicBo = getTopicBo(sbTopic);
        baseSearch.add(topicBo, sbTopic.getId().toString(), IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据 - 已发布添加：id：{} ,num = {}", sbTopic.getId(), num);
        return;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicMapper.updateByExample*(..))")
    public void updateByExampleAop(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        SbTopic sbTopic = (SbTopic) args[0];
        SbTopicExample sbTopicExample = (SbTopicExample) args[1];
        List<SbTopic> topics = topicMapper.selectByExample(sbTopicExample);
        if (CollectionUtils.isNullOrEmpty(topics)) {
            return;
        }
        List<Integer> ids = new ArrayList<>();
        List<TopicBo> topicBos = new ArrayList<>();
        topics.forEach(t -> {
            ids.add(t.getId());
            topicBos.add(getTopicBo(t));
        });

        Integer status = sbTopic.getStatus();
        Integer auditStatus = sbTopic.getAuditStatus();

        String methodName = joinPoint.getSignature().getName();
        log.info("topic - 同步搜索数据，更新methodName=" + methodName);
        //selective区分
        if (methodName.equals("updateByExample")) {
            if (status == null || auditStatus == null || !status.equals(TopicStatus.published.getValue()) || !auditStatus.equals(AuditStatus.MAN_AUDIT_PASS.getValue())) {
                baseSearch.deleteByIds(ids, IndexType.topic.getIndex(), IndexType.topic.getType());
                log.info("topic - 同步搜索数据 - 下线，删除 ,ids:{}", ids);
                return;
            }
        } else {
            if ((status != null && !status.equals(TopicStatus.published.getValue())) || (auditStatus != null && !auditStatus.equals(AuditStatus.MAN_AUDIT_PASS.getValue()))) {
                baseSearch.deleteByIds(ids, IndexType.topic.getIndex(), IndexType.topic.getType());
                log.info("topic - 同步搜索数据 -下线，删除 ,ids:{}", ids);
                return;
            }
        }
        List<BulkableAction> actions = new LinkedList<>();
        for (TopicBo bo : topicBos) {
            actions.add(new Index.Builder(FastJsonUtils.toJSONString(bo)).id(bo.getId().toString()).build());
        }
        baseSearch.adds(actions, IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据 - 添加 ids={}", ids);
        return;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicMapper.selfPlusMinusByPrimaryKey(..))", returning = "num")
    public void selfPlusMinusByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        Integer id = (Integer) args[3];

        SbTopic topic = topicMapper.selectByPrimaryKey(id);
        TopicBo topicBo = getTopicBo(topic);
        baseSearch.add(topicBo, topic.getId().toString(), IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据 - 已发布添加：id：{} ,num = {}", topic.getId(), num);
        return;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicMapper.selfPlusMinus(..))", returning = "num")
    public void selfPlusMinusAop(JoinPoint joinPoint,Integer num) throws Throwable {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        SbTopicExample sbTopicExample = (SbTopicExample) args[3];
        List<SbTopic> topics = topicMapper.selectByExample(sbTopicExample);
        if (CollectionUtils.isNullOrEmpty(topics)) {
            return ;
        }
        List<TopicBo> topicBos = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        topics.forEach(t -> {
            topicBos.add(getTopicBo(t));
            ids.add(t.getId());
        });

        List<BulkableAction> actions = new LinkedList<>();
        for (TopicBo bo : topicBos) {
            actions.add(new Index.Builder(FastJsonUtils.toJSONString(bo)).id(bo.getId().toString()).build());
        }
        baseSearch.adds(actions, IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据-删除ids={},num={}", ids, num);
        return ;
    }

    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbVideoMapper.selfPlusMinusByPrimaryKey(..))", returning = "num")
    public void videoSelfPlusMinusByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        Integer id = (Integer) args[3];

        SbVideo video = videoMapper.selectByPrimaryKey(id);
        TopicBo bo = getTopicBoByVideoId(video.getId());
        if (null == bo) {
            return;
        }
        bo.setPlayNum(video.getPlayNum());
        baseSearch.add(bo, bo.getId().toString(), IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据 - 已发布添加：id：{} ,num = {}", bo.getId(), num);
        return;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbVideoMapper.selfPlusMinus(..))", returning = "num")
    public void videoSelfPlusMinusAop(JoinPoint joinPoint, Integer num) throws Throwable {

        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        SbVideoExample videoExample = (SbVideoExample) args[3];
        List<SbVideo> videos = videoMapper.selectByExample(videoExample);
        if (CollectionUtils.isNullOrEmpty(videos)) {
            return;
        }
        List<TopicBo> topicBos = new ArrayList<>();

        List<Integer> ids = new ArrayList<>();



        videos.forEach(t -> {
            TopicBo topicBo = getTopicBoByVideoId(t.getId());
            if (null != topicBo) {
                topicBo.setPlayNum(t.getPlayNum());
                ids.add(topicBo.getId());
                topicBos.add(topicBo);
            }
        });

        if (CollectionUtils.isNullOrEmpty(topicBos)) {
            return;
        }
        List<BulkableAction> actions = new LinkedList<>();
        for (TopicBo bo : topicBos) {
            actions.add(new Index.Builder(FastJsonUtils.toJSONString(bo)).id(bo.getId().toString()).build());
        }
        baseSearch.adds(actions, IndexType.topic.getIndex(), IndexType.topic.getType());
        log.info("topic - 同步搜索数据-删除ids={},num={}", ids, num);
        return;
    }

    private TopicBo getTopicBo(SbTopic topic) {
        TopicBo bo = new TopicBo();
        BeanUtils.copyProperties(topic, bo);
        Integer videoId = topic.getVideoId();
        SbVideo video = videoMapper.selectByPrimaryKey(videoId);
        if (null != video) {
            bo.setPlayNum(video.getPlayNum());
        }
        return bo;
    }

    private TopicBo getTopicBoByVideoId(Integer videoId) {
        TopicBo bo = new TopicBo();
        SbTopicExample example = new SbTopicExample();
        example.setLimit(1);
        example.createCriteria().andVideoIdEqualTo(videoId).andStatusEqualTo(TopicStatus.published.getValue())
                .andAuditStatusEqualTo(AuditStatus.MAN_AUDIT_PASS.getValue());
        List<SbTopic> topics = topicMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(topics)) {
            return null;
        }
        SbTopic topic = topics.get(0);
        BeanUtils.copyProperties(topic, bo);
        return bo;
    }
}
