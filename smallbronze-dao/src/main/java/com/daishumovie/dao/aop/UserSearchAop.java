package com.daishumovie.dao.aop;

import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.DsmUserExample;
import com.daishumovie.search.enums.IndexType;
import com.daishumovie.search.pojo.UserBo;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2017-11-03 15:08
 **/
@Aspect
@Slf4j
@Component
public class UserSearchAop {

    @Autowired
    private DsmUserMapper userMapper;

    @Autowired
    private BaseSearch baseSearch;


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.DsmUserMapper.insert*(..))", returning = "num")
    public void insertAop(JoinPoint joinPoint, Integer num) throws Throwable {

        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        DsmUser user = (DsmUser) args[0];
        System.out.println(new Date());
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("-----aop----" + new Date());
                DsmUser dsmUser = userMapper.selectByPrimaryKey(user.getUid());
                if (YesNoEnum.NO.getCode().equals(dsmUser.getStatus())) {
                    return;
                }
                UserBo bo = getUserBo(dsmUser);
                baseSearch.add(bo, bo.getUid().toString(), IndexType.user.getIndex(), IndexType.user.getType());
                log.info("user - 同步搜索数据-添加 id={},num={}", bo.getUid().toString(), num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.DsmUserMapper.deleteByPrimaryKey(Integer))", returning = "num")
    public void deleteByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {

        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        Integer id = Integer.valueOf(args[0].toString());

        io.searchbox.core.DocumentResult result = baseSearch.delete(id, IndexType.user.getIndex(), IndexType.user.getType());
        log.info("user - 同步搜索数据-删除id={},num={}", id, num);
    }

    @Around(value = "execution(* com.daishumovie.dao.mapper.smallbronze.DsmUserMapper.deleteByExample(..))")
    public Integer deleteByExampleAop(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        DsmUserExample userExample = (DsmUserExample) args[0];

        List<DsmUser> users = userMapper.selectByExample(userExample);

        if (CollectionUtils.isNullOrEmpty(users)) {
            return 0;
        }
        List<Integer> ids = new ArrayList<>();
        users.forEach(t -> ids.add(t.getUid()));
        Integer num = (Integer) joinPoint.proceed();
        baseSearch.deleteByIds(ids, IndexType.user.getIndex(), IndexType.user.getType());
        log.info("user - 同步搜索数据-删除ids={},num={}", ids, num);
        return num;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.DsmUserMapper.updateByPrimaryKey*(..))", returning = "num")
    public void updateByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        DsmUser user = (DsmUser) args[0];
        Integer userId = user.getUid();
        DsmUser dsmUser = userMapper.selectByPrimaryKey(userId);
        if (null == dsmUser.getStatus() || YesNoEnum.NO.getCode().equals(dsmUser.getStatus())) {
            baseSearch.delete(dsmUser.getUid(), IndexType.user.getIndex(), IndexType.user.getType());
            log.info("user - 同步搜索数据 - 下线删除：id:{}", dsmUser.getUid());
            return;
        }
        UserBo userBo = getUserBo(dsmUser);
        baseSearch.add(userBo, dsmUser.getUid().toString(), IndexType.user.getIndex(), IndexType.user.getType());
        log.info("user - 同步搜索数据 - 已发布添加：id：{} ,num = {}", dsmUser.getUid(), num);
        return;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.DsmUserMapper.updateByExample*(..))")
    public void updateByExampleAop(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        DsmUser dsmUser = (DsmUser) args[0];
        DsmUserExample dsmUserExample = (DsmUserExample) args[1];
        List<DsmUser> users = userMapper.selectByExample(dsmUserExample);
        if (CollectionUtils.isNullOrEmpty(users)) {
            return;
        }
        List<Integer> ids = new ArrayList<>();
        List<UserBo> userBos = new ArrayList<>();
        users.forEach(t -> {
            ids.add(t.getUid());
            userBos.add(getUserBo(t));
        });

        Integer status = dsmUser.getStatus();
        String methodName = joinPoint.getSignature().getName();
        log.info("user - 同步搜索数据，更新methodName=" + methodName);
        //selective区分
        if (methodName.equals("updateByExample")) {
            if (status == null || status.equals(YesNoEnum.NO.getCode())) {
                baseSearch.deleteByIds(ids, IndexType.user.getIndex(), IndexType.user.getType());
                log.info("user - 同步搜索数据 - 下线，删除 ,ids:{}", ids);
                return;
            }
        } else {
            if (status != null && status.equals(YesNoEnum.NO.getCode())) {
                baseSearch.deleteByIds(ids, IndexType.user.getIndex(), IndexType.user.getType());
                log.info("user - 同步搜索数据 -下线，删除 ,ids:{}", ids);
                return;
            }
        }
        List<BulkableAction> actions = new LinkedList<>();
        for (UserBo bo : userBos) {
            actions.add(new Index.Builder(FastJsonUtils.toJSONString(bo)).id(bo.getUid().toString()).build());
        }
        baseSearch.adds(actions, IndexType.user.getIndex(), IndexType.user.getType());
        log.info("user - 同步搜索数据 - 添加 ids={}", ids);
        return;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.DsmUserMapper.selfPlusMinusByPrimaryKey(..))", returning = "num")
    public void selfPlusMinusByPrimaryKeyAop(JoinPoint joinPoint, Integer num) throws IOException {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        Integer id = (Integer) args[3];

        DsmUser user = userMapper.selectByPrimaryKey(id);
        UserBo userBo = getUserBo(user);
        baseSearch.add(userBo, user.getUid().toString(), IndexType.user.getIndex(), IndexType.user.getType());
        log.info("user - 同步搜索数据 - 已发布添加：id：{} ,num = {}", user.getUid(), num);
        return;
    }


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.DsmUserMapper.selfPlusMinus(..))", returning = "num")
    public void selfPlusMinusAop(JoinPoint joinPoint, Integer num) throws Throwable {
        if (num == 0) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        DsmUserExample dsmUserExample = (DsmUserExample) args[3];
        List<DsmUser> users = userMapper.selectByExample(dsmUserExample);
        if (CollectionUtils.isNullOrEmpty(users)) {
            return;
        }
        List<UserBo> userBos = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        users.forEach(t -> {
            userBos.add(getUserBo(t));
            ids.add(t.getUid());
        });

        List<BulkableAction> actions = new LinkedList<>();
        for (UserBo bo : userBos) {
            actions.add(new Index.Builder(FastJsonUtils.toJSONString(bo)).id(bo.getUid().toString()).build());
        }
        baseSearch.adds(actions, IndexType.user.getIndex(), IndexType.user.getType());
        log.info("user - 同步搜索数据-删除ids={},num={}", ids, num);
        return;
    }

    private UserBo getUserBo(DsmUser user) {
        UserBo bo = new UserBo();
        BeanUtils.copyProperties(user, bo);
        return bo;
    }

}
