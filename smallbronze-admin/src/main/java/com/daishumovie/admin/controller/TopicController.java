package com.daishumovie.admin.controller;

import com.daishumovie.admin.dto.ChannelDto;
import com.daishumovie.admin.service.IChannelService;
import com.daishumovie.admin.service.ITopicService;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.dao.mapper.smallbronzeadmin.AdminEntityMapper;
import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.OtAdminsExample;
import com.daishumovie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/8/28 10:23.
 * 视频审核管理列表，同步请求相关接口
 */
@Controller
@RequestMapping("/topic/")
public class TopicController extends BaseController {

    @Autowired
    private IChannelService channelService;
    @Autowired
    private AdminEntityMapper adminMapper;



    @RequestMapping(value = "index", name = "话题审核列表页", method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView view = new ModelAndView();
        Map<Integer,String> map = new LinkedHashMap<>();
        map.put(AuditStatus.WAIT.getValue(),AuditStatus.WAIT.getName());
        map.put(AuditStatus.MAN_AUDIT_NOT_PASS.getValue(),AuditStatus.MAN_AUDIT_NOT_PASS.getName());
        map.put(AuditStatus.MAN_AUDIT_PASS.getValue(),AuditStatus.MAN_AUDIT_PASS.getName());
        view.addObject("audit_status", map);
        view.setViewName("videos/topic/index");

        return view;
    }

    @RequestMapping(value = "audit", method = RequestMethod.GET)
    public ModelAndView audit() {
        ModelAndView view = new ModelAndView();
        view.addObject("reportProblemList", ReportProblem.values());
        view.setViewName("videos/topic/audit");
        return view;
    }

    @RequestMapping(value = "publish", method = RequestMethod.GET)
    public ModelAndView publish(@RequestParam("from_edit") boolean fromEdit) {
        List<ChannelDto> channelList = channelService.firstLevelList(null);
        ModelAndView view = new ModelAndView("/videos/publish/index");
        view.addObject("publish_status", TopicStatus.values());
        view.addObject("channel_list", channelList);
        OtAdminsExample example = new OtAdminsExample();
        example.createCriteria().andStatusEqualTo(Whether.yes.getValue());
        List<AdminEntity> adminList = adminMapper.selectByExample(example);
        view.addObject("admin_list", adminList);
        view.addObject("from_edit", fromEdit);
        return view;
    }


    @RequestMapping(value = "initEdit", method = RequestMethod.GET)
    public ModelAndView initEdit(Integer id, String videoUrl, String cover, String title) {

        ModelAndView view = new ModelAndView("/videos/publish/edit");
        view.addObject("id", id);
        view.addObject("video_url", videoUrl);
        view.addObject("cover", cover);
        view.addObject("title", title);
        return view;
    }

}
