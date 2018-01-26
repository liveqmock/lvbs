package com.daishumovie.admin.controller;

import com.daishumovie.admin.dto.ChannelDto;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.base.enums.db.AlbumStatus;
import com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper;
import com.daishumovie.dao.model.SbTopicAlbum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/10/25 11:20.
 */
@Controller
@RequestMapping("/album/")
public class AlbumController extends BaseController{

    @Autowired
    private SbTopicAlbumMapper albumMapper;
    @Autowired
    private IChannelService channelService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(String title, String subtitle, Integer status, String paramTime) {

        ModelAndView view = new ModelAndView("videos/album/index");
        view.addObject("album_status", AlbumStatus.values());
        {
            view.addObject("title", title);
            view.addObject("subtitle", subtitle);
            view.addObject("param_status", status);
            view.addObject("param_time", paramTime);
        }
        return view;
    }

    @RequestMapping(value = "initAdd", method = RequestMethod.GET)
    public ModelAndView initAdd() {

        return new ModelAndView("videos/album/add");
    }

    @RequestMapping(value = "initEdit", method = RequestMethod.GET)
    public ModelAndView initEdit(Integer id, String title, String subtitle, Integer status, String paramTime) {

        ModelAndView view = new ModelAndView("videos/album/edit");
        SbTopicAlbum album = albumMapper.selectByPrimaryKey(id);
        view.addObject("album", album);
        {
            view.addObject("title", title);
            view.addObject("subtitle", subtitle);
            view.addObject("status", status);
            view.addObject("param_time", paramTime);
        }
        return view;
    }

    @RequestMapping(value = "putVideo", method = RequestMethod.GET)
    public ModelAndView putVideo(Integer id) {

        ModelAndView view = new ModelAndView("videos/album/put");
        view.addObject("album_id", id);
        List<ChannelDto> channelList = channelService.firstLevelList(null);
        view.addObject("channel_list", channelList);
        SbTopicAlbum album = albumMapper.selectByPrimaryKey(id);
        if (null != album) {
            view.addObject("topic_ids", album.getTopicIds());
        }
        return view;
    }
}
