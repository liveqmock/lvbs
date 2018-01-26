package com.daishumovie.timer.job;

import com.daishumovie.timer.service.ActivityService;
import com.daishumovie.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by feiFan.gou on 2017/10/24 10:14.
 */
@RestController
public class ActivityJob extends Base {

    @Autowired
    private Base base;

    @Autowired
    private ActivityService service;

    private static final Logger log = LoggerFactory.getLogger(SchedulePushJob.class);

    @Scheduled(fixedDelay = 1000 * 10)//10秒钟扫描一次
    public void scanSchedule() {

        log.info("======================== activity_timer start[" + DateUtil.BASIC.format(new Date()) + "] ========================");
        // 加锁 防止并发设置
        boolean lock = base.lock("activity_timer");
        if (lock) {
            service.run();
        }
        log.info("======================== activity_timer end[" + DateUtil.BASIC.format(new Date()) + "] ========================");
    }
}
