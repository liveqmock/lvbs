package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.dto.BucketDto;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.SbTopicDto;
import com.daishumovie.admin.dto.VideoInfoDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IBucketService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/9/21 16:23.
 */
@RestController
@RequestMapping("/ajax/bucket/")
public class AjaxBucketController {

    @Autowired
    private IBucketService bucketService;

    @RequestMapping(value = "paginate", method = RequestMethod.POST)
    public ReturnDto<BucketDto> paginate(ParamDto param, Integer id, String createTime, String adminName) {

        return bucketService.paginate(param, id, createTime, adminName);
    }
    @RequestMapping(value = "video/paginate", method = RequestMethod.POST)
    public ReturnDto<SbTopicDto> paginate(ParamDto param, String ids, Integer channelId, String createTime, Integer inside, String username, String topicIds, Boolean isAdd) {

        return bucketService.paginate(param, ids, channelId, createTime, inside, username, topicIds, isAdd);
    }

    @RequestMapping(value = "videoList", method = RequestMethod.GET)
    public List<VideoInfoDto> videoList(@RequestParam("topic_ids[]") Integer[] topicIds) {

        return bucketService.videoList(topicIds);
    }

    @RequestMapping(value = "videoInfo", method = RequestMethod.GET)
    public VideoInfoDto videoInfo(Integer videoId) {

        return bucketService.videoInfo(videoId);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.bucket, type = OperationType.insert)
    public JSONResult save(String videoIds, String remarks, HttpServletRequest request) {

        try {
            bucketService.save(videoIds, remarks, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.bucket, type = OperationType.edit)
    public JSONResult update(String videoIds, Integer id, String remarks, HttpServletRequest request) {

        try {
            bucketService.update(id, videoIds, remarks, SessionUtil.getLoginAdminUid(request));
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

}
