package com.daishumovie.admin.controller;

import com.daishumovie.admin.dto.ChannelDto;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.dao.mapper.smallbronze.SbTopicBucketMapper;
import com.daishumovie.dao.model.SbTopicBucket;
import com.daishumovie.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/9/21 14:18.
 */
@Controller
@RequestMapping("/bucket/")
public class BucketController extends BaseController  {

    @Autowired
    private IChannelService channelService;

    @Autowired
    private SbTopicBucketMapper bucketMapper;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {

        return new ModelAndView("videos/bucket/index");
    }

    @RequestMapping(value = "initAdd", method = RequestMethod.GET)
    public ModelAndView initAdd() {

        List<ChannelDto> channelList = channelService.firstLevelList(null);
        ModelAndView view = new ModelAndView("videos/bucket/add");
        view.addObject("channel_list", channelList);
        view.addObject("whether_list", Whether.values());
        return view;
    }

    @RequestMapping(value = "initEdit", method = RequestMethod.GET)
    public ModelAndView initEdit(Integer id) {

        List<ChannelDto> channelList = channelService.firstLevelList(null);
        ModelAndView view = new ModelAndView("videos/bucket/edit");
        view.addObject("whether_list", Whether.values());
        view.addObject("channel_list", channelList);
        SbTopicBucket bucket = bucketMapper.selectByPrimaryKey(id);
        if (null != bucket) {
            view.addObject("bucket_id", id);
            view.addObject("topic_ids", bucket.getTopicIds());
            view.addObject("remarks", StringUtil.trim(bucket.getRemarks()));
        }
        return view;
    }
}
