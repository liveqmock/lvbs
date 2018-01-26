package com.daishumovie.timer.service;

import com.daishumovie.base.enums.db.ActivityStatus;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.dao.model.SbActivityExample;
import com.daishumovie.utils.CollectionUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/10/24 10:16.
 */
@Service
public class ActivityService {

    private @Autowired
    SbActivityMapper activityMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);

    private List<SbActivity> unStartedList() {

        SbActivityExample example = new SbActivityExample();
        example.createCriteria().andStatusEqualTo(ActivityStatus.un_started.getValue());
        return activityMapper.selectByExample(example);
    }

    private List<SbActivity> preheatingList() {

        SbActivityExample example = new SbActivityExample();
        example.createCriteria().andStatusEqualTo(ActivityStatus.preheating.getValue());
        return activityMapper.selectByExample(example);
    }

    private List<SbActivity> ongoingList() {

        SbActivityExample example = new SbActivityExample();
        example.createCriteria().andStatusEqualTo(ActivityStatus.ongoing.getValue());
        return activityMapper.selectByExample(example);
    }

    public void run() {

        Date now = new Date();
        Runnable unStarted = () -> {
            List<SbActivity> unStartedList = unStartedList();
            LOGGER.info("activity_un_started_list size === [{}]",unStartedList.size());
            if (!CollectionUtils.isNullOrEmpty(unStartedList)) {
                unStartedList.forEach(activity -> {
                    if (null != activity) {
                        if (null != activity.getPreTime()) {
                            if (now.after(activity.getPreTime())) {
                                activity.setStatus(ActivityStatus.preheating.getValue());
                                activity.setModifyTime(new Date());
                                activityMapper.updateByPrimaryKeySelective(activity);
                                LOGGER.info("activity[{}] status from un_started[{}] to preheating[{}]",activity.getTitle(),ActivityStatus.un_started.getName(),ActivityStatus.preheating.getName());
                            }
                        }
                        else {
                            if (now.after(activity.getStartTime())) {
                                activity.setStatus(ActivityStatus.ongoing.getValue());
                                activity.setModifyTime(new Date());
                                activityMapper.updateByPrimaryKeySelective(activity);
                                LOGGER.info("activity[{}] status from un_started[{}] to ongoing[{}]",activity.getTitle(),ActivityStatus.un_started.getName(),ActivityStatus.ongoing.getName());
                            }
                        }
                    }
                });
            }
        };
        Runnable preheating = () -> {
            List<SbActivity> preheatingList = preheatingList();
            LOGGER.info("activity_preheating_list size === [{}]",preheatingList.size());
            if (!CollectionUtils.isNullOrEmpty(preheatingList)) {
                preheatingList.forEach(activity -> {
                    if (null != activity && null != activity.getStartTime()) {
                        if (now.after(activity.getStartTime())) {
                            activity.setStatus(ActivityStatus.ongoing.getValue());
                            activity.setModifyTime(new Date());
                            activityMapper.updateByPrimaryKeySelective(activity);
                            LOGGER.info("activity[{}] status from un_started[{}] to preheating[{}]",activity.getTitle(),ActivityStatus.preheating.getName(),ActivityStatus.ongoing.getName());
                        }
                    }
                });
            }
        };
        Runnable ongoing = () -> {
            List<SbActivity> ongoingList = ongoingList();
            LOGGER.info("activity_ongoing_list size === [{}]",ongoingList.size());
            if (!CollectionUtils.isNullOrEmpty(ongoingList)) {
                ongoingList.forEach(activity -> {
                    if (null != activity && null != activity.getEndTime()) {
                        if (now.after(activity.getEndTime())) {

                            activity.setStatus(ActivityStatus.ended.getValue());
                            activity.setModifyTime(new Date());
                            activityMapper.updateByPrimaryKeySelective(activity);
                            LOGGER.info("activity[{}] status from un_started[{}] to preheating[{}]",activity.getTitle(),ActivityStatus.ongoing.getName(),ActivityStatus.ended.getName());
                        }
                    }
                });
            }
        };
        Lists.newArrayList(new Thread(unStarted), new Thread(preheating), new Thread(ongoing)).forEach(Thread::start);
    }
}
