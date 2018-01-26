package com.daishumovie.timer.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuruisong on 2017/4/6
 * @since 1.0
 */
@Slf4j
@Component
public class Base {

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 锁定30秒
     * @param lockKey
     * @return
     */
    boolean lock(String lockKey){
        return lock (lockKey, 30, TimeUnit.SECONDS);
    }

    boolean lock(String lockKey,final long timeout, final TimeUnit unit ){
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        Boolean status = stringValueOperations.setIfAbsent(lockKey, System.currentTimeMillis() + "");
        if (status) {
            log.info("获取锁成功，锁定{} {}，key={}",timeout,unit,lockKey);
            //锁定指定时间 其他线程在指定时间内不可再执行
            redisTemplate.expire(lockKey, timeout, unit);
        }
        //当前线程没有获取到锁
        if (!status) {

            //没有获取到锁 检查过期时间是否正常 （可能在加锁之后，redis中断导致过期时间没有设置上）
            Long expire = redisTemplate.getExpire(lockKey, unit);

            //过期时间没有设置上
            if (expire == 0) {
                //重新设置
               stringValueOperations.set(lockKey, System.currentTimeMillis() + "",timeout,unit);
               log.info("过期时间过长：{}，重新设置锁，锁定{} {}，key={}",expire,timeout,unit,lockKey);
               return true;
            }
            log.info("没有获取到锁，当前线程不执行");
        }
        return status;
    }
}
