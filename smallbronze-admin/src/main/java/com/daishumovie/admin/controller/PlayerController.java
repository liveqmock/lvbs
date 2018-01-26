package com.daishumovie.admin.controller;

import com.daishumovie.admin.dto.VideoInfoDto;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/9/29 10:28.
 */
@Controller
@RequestMapping("/player/")
public class PlayerController extends BaseController {

    @Autowired
    private SbTopicMapper topicMapper;
    @Autowired
    private DsmUserMapper userMapper;
    @Autowired
    private SbVideoMapper videoMapper;

    @RequestMapping("{topicId}")
    public ModelAndView play(@PathVariable Integer topicId) {

        ModelAndView view = new ModelAndView("/videos/player/index");
        try {
            if (null == topicId) {
                throw new Exception();
            } else {
                SbTopic topic = topicMapper.selectByPrimaryKey(topicId);
                if (null == topic) {
                    throw new Exception();
                } else {
                    VideoInfoDto videoInfo = new VideoInfoDto();
                    videoInfo.setTitle(StringUtil.trim(topic.getTitle()));
                    videoInfo.setAgo(DateUtil.timeAgo(topic.getCreateTime()));
                    Integer uid = topic.getUid();
                    if (null != uid) {
                        DsmUser user = userMapper.selectByPrimaryKey(uid);
                        if (null != user) {
                            videoInfo.setPublisher(user.getNickName());
                        }
                    }
                    videoInfo.setLikeNumber(String.valueOf(topic.getPraiseNum()));
                    videoInfo.setCommentNumber(String.valueOf(topic.getReplyNum()));
                    Integer videoId = topic.getVideoId();
                    if (null != videoId) {
                        SbVideo video = videoMapper.selectByPrimaryKey(videoId);
                        if (null != video) {
                            videoInfo.setLink(StringUtil.trim(video.getOriUrl()));
                            videoInfo.setCover(StringUtil.trim(video.getCover()));
                            videoInfo.setPlayNumber(String.valueOf(video.getPlayNum()));
                        }
                    }
                    view.addObject("video", videoInfo);
                }
            }
        } catch (Exception e) {
            view.setViewName("/videos/player/video-404");
        }
        return view;
    }

}
