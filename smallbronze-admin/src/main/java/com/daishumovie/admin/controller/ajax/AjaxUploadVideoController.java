package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.annotation.LogConfig;
import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IVideoUploadService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.admin.util.SessionUtil;
import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.VideoSource;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * create by feifan.gou
 * 视频上传
 */
@RestController
@RequestMapping("/ajax/upload/v/")
public class AjaxUploadVideoController extends BaseController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IVideoUploadService uploadService;

    @RequestMapping(value = "paginate",method = RequestMethod.POST)
    public ReturnDto paginate(HttpServletRequest request,ParamDto param, String title, String userName,Integer status) {
        param.setSort_name("create_time");
        param.setSort_order(ParamDto.order.desc);
        Integer userId = null;
        if (StringUtil.isNotEmpty(userName)){
            AdminEntity adminEntity = adminService.getAdmin(userName);
            if (adminEntity != null){
                userId = adminEntity.getId().intValue();
            }
        }else{
            userId = SessionUtil.getLoginAdminUid(request);
        }
        return uploadService.paginate(param, title,userId,status);
    }


    @RequestMapping(value = "saveVideoInfo", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic, type = OperationType.upload)
    public JSONResult saveVideoInfo(@RequestParam("titles[]") String[] titles, @RequestParam("urls[]") String[] urls, Integer source, HttpServletRequest request) {

        try {
            VideoSource videoSource = VideoSource.get(source);
            if (null == videoSource) {
                throw new ResultException(ErrMsg.param_error);
            }
            uploadService.handleDB(videoSource, titles, SessionUtil.getLoginAdminUid(request), urls);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "saveLocalVideo", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic, type = OperationType.upload)
    public JSONResult saveLocalVideo(String titles, HttpServletRequest request) {

        try {
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
            if (!resolver.isMultipart(request)) {
                throw new ResultException("上传文件为空");
            }

            // 1.上传到本地
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multiRequest.getFileNames();
            if (!iterator.hasNext()) {
                throw new ResultException("上传文件为空");
            }
            List<MultipartFile> fileList = multiRequest.getFiles(iterator.next());
            List<String> localUrlList = uploadService.upload2Local(fileList);
            String[] urls = new String[localUrlList.size()];
            urls = localUrlList.toArray(urls);
            // 2.数据处理
            List<SbTopic> topicList = uploadService.handleDB(VideoSource.local, titles.split(","), SessionUtil.getLoginAdminUid(request), urls);
            // 3.异步上传到阿里云
            uploadService.asyncUpload(topicList, localUrlList);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "uploadAgain", method = RequestMethod.POST)
    @LogConfig(object = OperationObject.topic, type = OperationType.upload)

    public JSONResult uploadAgain(Integer id) {

        try {
            uploadService.uploadAgain(id);
            return JSONResult.success();
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "syndownload")
    public Response ajaxUpdateScore(HttpServletRequest request) {
        try {
            String uploadvideoId = request.getParameter("uploadvideoId");
            if (StringUtil.isNotEmpty(uploadvideoId))
                return uploadService.syndownload(Integer.valueOf(uploadvideoId));
            else
                return new Response(RespStatusEnum.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(RespStatusEnum.ERROR);
    }
}
