package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.dto.AlbumDto;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IAlbumService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.dao.model.SbTopicAlbum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by feiFan.gou on 2017/10/25 11:14.
 */
@RestController
@RequestMapping("/ajax/album/")
public class AjaxAlbumController {

    @Autowired
    private IAlbumService albumService;


    @RequestMapping(value = "paginate", method = RequestMethod.POST)
    public ReturnDto<AlbumDto> paginate(ParamDto param, String title, String subtitle, Integer status, String paramTime) {

        return albumService.paginate(param, title, subtitle, status, paramTime);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.album, type = OperationType.insert)
    public JSONResult save(SbTopicAlbum album, HttpServletRequest request) {

        try {
            albumService.save(album, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.album, type = OperationType.edit)
    public JSONResult update(SbTopicAlbum album, HttpServletRequest request) {

        try {
            albumService.update(album, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "publish", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.album, type = OperationType.publish)
    public JSONResult publish(Integer albumId, HttpServletRequest request) {

        try {
            albumService.publish(albumId, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }


    @RequestMapping(value = "offline", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.album, type = OperationType.off_shelf)
    public JSONResult offline(Integer albumId, HttpServletRequest request) {

        try {
            albumService.offline(albumId, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "putVideo", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.album, type = OperationType.put_video)
    public JSONResult putVideo(Integer albumId, String topicIds, HttpServletRequest request) {

        try {
            albumService.putVideo(albumId, topicIds, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "video/paginate", method = RequestMethod.POST)
    public ReturnDto<SbTopicDto> paginate(ParamDto param, String ids, Integer channelId, String createTime, String username, String topicIds) {

        return albumService.paginate(param, ids, channelId, createTime, username, topicIds);
    }
}
