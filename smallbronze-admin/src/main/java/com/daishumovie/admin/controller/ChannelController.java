package com.daishumovie.admin.controller;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.enums.db.CommonStatus;
import com.daishumovie.dao.mapper.smallbronze.SbChannelMapper;
import com.daishumovie.dao.model.SbChannel;
import com.daishumovie.dao.model.SbChannelExample;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/9/7 15:31.
 */
@Controller
@RequestMapping("/channel/")
public class ChannelController extends BaseController  {

    @Autowired
    private IChannelService channelService;
    @Autowired
    private SbChannelMapper channelMapper;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(Integer appId, Integer firstLevelId) {

        ModelAndView view = new ModelAndView();
        view.addObject("first_level_list", channelService.firstLevelList(appId));
        view.addObject("second_level_list", channelService.secondLevelList(null, appId));
        view.addObject("app_id", null == appId ? Configuration.current_app.getId() : appId);
        view.addObject("app_list", App.values());
        if (null != firstLevelId && firstLevelId == -999) {
            SbChannelExample example = new SbChannelExample();
            example.setOrderByClause("create_time desc");
            example.createCriteria().andStatusEqualTo(CommonStatus.normal.getValue()).andPidEqualTo(0);
            List<SbChannel> channelList = channelMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(channelList)) {
                firstLevelId = channelList.get(0).getId();
            }
        }
        view.addObject("first_level_id", firstLevelId);
        view.setViewName("videos/channel/index");
        return view;
    }

    @RequestMapping(value = "initAdd", method = RequestMethod.GET)
    public ModelAndView initAdd(Integer pid, Integer appId) {

        ModelAndView view = new ModelAndView();
        view.addObject("pid", pid);
        String title = "一级频道";
        if (pid != 0) {
            SbChannel channel = channelMapper.selectByPrimaryKey(pid);
            if (null != channel) {
                title = "上级频道：" + StringUtil.trim(channel.getName());
            }
        }
        view.addObject("app_list", App.values());
        view.addObject("channel_title", title);
        view.addObject("app_id", appId);
        view.setViewName("videos/channel/add");
        return view;
    }

    @RequestMapping(value = "initEdit", method = RequestMethod.GET)
    public ModelAndView initEdit(Integer id) {

        ModelAndView view = new ModelAndView();
        SbChannel channel = channelMapper.selectByPrimaryKey(id);
        view.addObject("channel", channel);
        view.addObject("app_list", App.values());
        view.setViewName("videos/channel/edit");
        return view;
    }
}
