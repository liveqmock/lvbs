package com.daishumovie.timer.job;

import com.daishumovie.timer.service.SchedulePushService;
import com.daishumovie.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by feiFan.gou on 2017/10/19 15:45.
 */
@RestController
public class SchedulePushJob extends Base {

    @Autowired
    private Base base;
    @Autowired
    private SchedulePushService pushService;

    private static final Logger log = LoggerFactory.getLogger(SchedulePushJob.class);

    @Scheduled(fixedDelay = 1000 * 60 * 5)//5分钟扫描一次
    public void scanSchedule() {

        log.info("======================== schedule_push start[" + DateUtil.BASIC.format(new Date()) + "] ========================");
        // 加锁 防止并发设置
        boolean lock = base.lock("schedule_push_timer");
        if (lock) {
            Runnable pushRunnable = this::push;
            new Thread(pushRunnable).start();
//            Runnable reportRunnable = this::report;
//            new Thread(reportRunnable).start();
        }
        log.info("======================== schedule_push end[" + DateUtil.BASIC.format(new Date()) + "] ========================");
    }

    /**
     * 推送
     */
    private void push() {

        pushService.push(pushService.scheduleList());
    }

    private void report() {

        pushService.report(pushService.reportList());
    }
}
