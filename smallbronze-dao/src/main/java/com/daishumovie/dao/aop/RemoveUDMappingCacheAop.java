package com.daishumovie.dao.aop;

import com.daishumovie.dao.cache.MyBatisRedisCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 清除自定义sql影响的缓存
 * @author zhuruisong on 2017/7/11
 * @since 1.0
 */
@Aspect
@Component
@Slf4j
public class RemoveUDMappingCacheAop {


    @AfterReturning(value = "execution(* com.daishumovie.dao.mapper.smallbronze.UDUserWatchHistoryMapper.removeDuplicateData(..))",returning = "num")
    public void uDUserWatchHistoryMapperRemoveDuplicateDataAop(Integer num) throws IOException {
        if (num > 0) {
            new MyBatisRedisCache("com.daishumovie.dao.mapper.smallbronze.SbUserWatchHistoryMapper").clear();
            log.info("相关缓存已清除");
        }
    }
}
