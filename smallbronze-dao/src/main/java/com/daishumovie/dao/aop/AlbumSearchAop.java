package com.daishumovie.dao.aop;

import com.daishumovie.base.enums.db.AlbumStatus;
import com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper;
import com.daishumovie.dao.model.SbTopicAlbum;
import com.daishumovie.dao.model.SbTopicAlbumExample;
import com.daishumovie.search.enums.IndexType;
import com.daishumovie.search.pojo.AlbumBo;
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
 */
@Aspect
@Slf4j
@Component
public class AlbumSearchAop {


    @Autowired
    private BaseSearch baseSearch;

    @Autowired
    private SbTopicAlbumMapper topicAlbumMapper;


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper.insert*(..))", returning = "num")
    public void insertAop(JoinPoint joinPoint, Integer num) throws Throwable {

        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        SbTopicAlbum sbTopicAlbum = (SbTopicAlbum) args[0];
        SbTopicAlbum topicAlbum = topicAlbumMapper.selectByPrimaryKey(sbTopicAlbum.getId());
        if (!AlbumStatus.published.getValue().equals(topicAlbum.getStatus())) {
            return;
        }
        AlbumBo albumBo = getAlbumBo(sbTopicAlbum);

        baseSearch.add(albumBo, albumBo.getId().toString(), IndexType.album.getIndex(), IndexType.album.getType());
        log.info("album - 同步搜索数据-添加 id={},num={}", albumBo.getId().toString(), num);
    }

    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper.deleteByPrimaryKey(Integer))", returning = "num")
    public void deleteByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {

        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        Integer id = Integer.valueOf(args[0].toString());

        io.searchbox.core.DocumentResult result = baseSearch.delete(id, IndexType.album.getIndex(), IndexType.album.getType());
        log.info("user - 同步搜索数据-删除id={},num={}", id, num);
    }

    @Around(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper.deleteByExample(..))")
    public Integer deleteByExampleAop(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        SbTopicAlbumExample albumExample = (SbTopicAlbumExample) args[0];

        List<SbTopicAlbum> sbTopicAlbums = topicAlbumMapper.selectByExample(albumExample);

        if (CollectionUtils.isNullOrEmpty(sbTopicAlbums)) {
            return 0;
        }
        List<Integer> ids = new ArrayList<>();
        sbTopicAlbums.forEach(t -> ids.add(t.getId()));
        Integer num = (Integer) joinPoint.proceed();
        baseSearch.deleteByIds(ids, IndexType.album.getIndex(), IndexType.album.getType());
        log.info("album - 同步搜索数据-删除ids={},num={}", ids, num);
        return num;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper.updateByPrimaryKey*(..))", returning = "num")
    public void updateByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        SbTopicAlbum album = (SbTopicAlbum) args[0];
        Integer albumId = album.getId();
        SbTopicAlbum topicAlbum = topicAlbumMapper.selectByPrimaryKey(albumId);
        if (null == topicAlbum.getStatus() || AlbumStatus.offline.getValue().equals(topicAlbum.getStatus())) {
            baseSearch.delete(topicAlbum.getId(), IndexType.album.getIndex(), IndexType.album.getType());
            log.info("album - 同步搜索数据 - 下线删除：id:{}", topicAlbum.getId());
            return;
        } else if (AlbumStatus.published.getValue().equals(topicAlbum.getStatus())) {
            AlbumBo albumBo = getAlbumBo(topicAlbum);
            baseSearch.add(albumBo, topicAlbum.getId().toString(), IndexType.album.getIndex(), IndexType.album.getType());
            log.info("album - 同步搜索数据 - 已发布添加：id：{} ,num = {}", topicAlbum.getId(), num);
            return;
        }
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper.updateByExample*(..))")
    public void updateByExampleAop(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        SbTopicAlbum sbTopicAlbum = (SbTopicAlbum) args[0];
        SbTopicAlbumExample sbTopicAlbumExample = (SbTopicAlbumExample) args[1];
        List<SbTopicAlbum> topicAlbums = topicAlbumMapper.selectByExample(sbTopicAlbumExample);
        if (CollectionUtils.isNullOrEmpty(topicAlbums)) {
            return;
        }
        List<Integer> ids = new ArrayList<>();
        List<AlbumBo> albumBos = new ArrayList<>();
        topicAlbums.forEach(t -> {
            ids.add(t.getId());
            albumBos.add(getAlbumBo(t));
        });

        Integer status = sbTopicAlbum.getStatus();
        String methodName = joinPoint.getSignature().getName();
        log.info("album - 同步搜索数据，更新methodName=" + methodName);
        //selective区分
        if (methodName.equals("updateByExample")) {
            if (status == null || status.equals(AlbumStatus.offline.getValue())) {
                baseSearch.deleteByIds(ids, IndexType.album.getIndex(), IndexType.album.getType());
                log.info("album - 同步搜索数据 - 下线，删除 ,ids:{}", ids);
                return;
            }
        } else {
            if (status != null && status.equals(AlbumStatus.offline.getValue())) {
                baseSearch.deleteByIds(ids, IndexType.album.getIndex(), IndexType.album.getType());
                log.info("album - 同步搜索数据 -下线，删除 ,ids:{}", ids);
                return;
            }
        }
        if (AlbumStatus.published.getValue().equals(status)) {
            List<BulkableAction> actions = new LinkedList<>();
            for (AlbumBo bo : albumBos) {
                actions.add(new Index.Builder(FastJsonUtils.toJSONString(bo)).id(bo.getId().toString()).build());
            }
            baseSearch.adds(actions, IndexType.album.getIndex(), IndexType.album.getType());
            log.info("album - 同步搜索数据 - 添加 ids={}", ids);
            return;
        }
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper.selfPlusMinusByPrimaryKey(..))", returning = "num")
    public void selfPlusMinusByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        String columnName = (String) args[0];
        Integer id = (Integer) args[3];

        SbTopicAlbum album = topicAlbumMapper.selectByPrimaryKey(id);
        if ("reply_num".equals(columnName)) {
            AlbumBo albumBo = getAlbumBo(album);
            baseSearch.add(albumBo, album.getId().toString(), IndexType.album.getIndex(), IndexType.album.getType());
            log.info("album - 同步搜索数据 - 已发布添加：id：{} ,num = {}", album.getId(), num);
            return;
        }
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper.selfPlusMinus(..))", returning = "num")
    public void selfPlusMinusAop(JoinPoint joinPoint, Integer num) throws Throwable {

        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        String columnName = (String) args[0];
        SbTopicAlbumExample sbTopicAlbumExample = (SbTopicAlbumExample)args[3];
        if ("reply_num".equals(columnName)) {
            List<SbTopicAlbum> topicAlbums = topicAlbumMapper.selectByExample(sbTopicAlbumExample);
            if (CollectionUtils.isNullOrEmpty(topicAlbums)) {
                return ;
            }
            List<AlbumBo> albumBos = new ArrayList<>();
            List<Integer> ids = new ArrayList<>();
            topicAlbums.forEach(t->{albumBos.add(getAlbumBo(t));ids.add(t.getId());});

            List<BulkableAction> actions = new LinkedList<>();
            for (AlbumBo bo : albumBos) {
                actions.add(new Index.Builder(FastJsonUtils.toJSONString(bo)).id(bo.getId().toString()).build());
            }
            baseSearch.adds(actions, IndexType.album.getIndex(), IndexType.album.getType());
            log.info("album - 同步搜索数据-删除ids={},num={}", ids, num);
            return ;
        }
        return ;
    }
    private AlbumBo getAlbumBo(SbTopicAlbum topicAlbum) {
        AlbumBo albumBo = new AlbumBo();
        BeanUtils.copyProperties(topicAlbum, albumBo);
        return albumBo;
    }
}
