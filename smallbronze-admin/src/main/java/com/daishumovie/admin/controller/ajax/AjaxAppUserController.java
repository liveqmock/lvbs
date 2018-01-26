package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.UserDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IUserService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.dao.model.DsmUser;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/9/6 15:06.
 */
@RestController
@RequestMapping("/ajax/app/user/")
public class AjaxAppUserController {


    @Autowired
    private IUserService userService;

    @RequestMapping(value = "paginate", method = RequestMethod.POST)
    public ReturnDto<UserDto> paginate(ParamDto param, DsmUser user, String registerTime) {

        return userService.paginate(param,user,registerTime);
    }

    @RequestMapping(value = "virtualUserList", method = RequestMethod.GET)
    public ModelAndView virtualUserList(@RequestParam("q_word[]") String q_word, int pageNumber, int pageSize) {

        ModelMap model = new ModelMap();
        model.put("gridResult", userService.getFictitiousUsers(q_word, pageNumber, pageSize));
        return new ModelAndView(new MappingJackson2JsonView(),model);
    }

    /**
     * 修改用户回复评论权限
     */
    @RequestMapping(value = "replyComment", method = RequestMethod.POST)
    public JSONResult replyComment(Integer id, HttpServletRequest request) {

        try {
            userService.replyComment(id, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }
    /**
     * 修改用户发布话题权限
     */
    @RequestMapping(value = "publishTopic", method = RequestMethod.POST)
    public JSONResult publishTopic(Integer id, HttpServletRequest request) {

        try {
            userService.publishTopic(id, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "fictitiousList", method = RequestMethod.GET)
    public Map<String, Object> fictitiousUserList(@RequestParam("q_word[]") String name,int pageNumber,int pageSize) {

        Map<String, Object> result = Maps.newHashMap();
        result.put("gridResult", userService.fictitiousUserList(name, pageNumber, pageSize));
        return result;
    }
}
