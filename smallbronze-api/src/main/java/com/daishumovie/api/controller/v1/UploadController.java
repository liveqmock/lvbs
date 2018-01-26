package com.daishumovie.api.controller.v1;

import com.daishumovie.api.service.MultipartUploadService;
import com.daishumovie.api.service.UserService;
import com.daishumovie.base.dto.upload.UploadDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.LogUtil;
import com.daishumovie.utils.OSSClientUtil;
import com.daishumovie.utils.RunShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhuruisong on 2017/9/6
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class UploadController {

    @Value("${ali.oss.endpoint}")
    private String endpoint;

    @Autowired
    private SbVideoMapper sbVideoMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private MultipartUploadService multipartUploadService;

    @Value("${multipartupload.partPath}")
    private String partPath;

    @Value("${multipartupload.partSize}")
    private Long partSize;

    @Value("${multipartupload.upload_is_aliyun}")
    private boolean uploadIsAliyun;

    /**
     * 上传文件
     * @param file file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Response<UploadDto> uploadVideo(@RequestParam MultipartFile file,
                                           @RequestParam(required = false) Integer refId) throws Exception {

        DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
        DsmUser userByUid = userService.getUserByUid(user.getUid());
        if (file.isEmpty()) {
            return new Response<>(RespStatusEnum.PARAM_FAIL);
        }
        UploadDto uploadDto = new UploadDto();

        String contentType = file.getContentType();
        LogUtil.debug(log,"contentType={}",contentType);
        // 非图片，即视频时才校验权限；
        if (!contentType.startsWith("image/") && YesNoEnum.YES.getCode().intValue() != userByUid.getIsTopicAuth()) {
        	return new Response<>(RespStatusEnum.TOPIC_NOT_AUTH);
        }
        uploadDto.setContentType(contentType);

        OSSClientUtil ossClientUtil = new OSSClientUtil(endpoint);

        Map<String, Object> stringObjectMap = ossClientUtil.uploadFile(file);
        String url = stringObjectMap.get("url").toString();
        String dimension = stringObjectMap.get("dimension").toString();
        uploadDto.setDimension(dimension);
        if(contentType.startsWith("image/")){
            uploadDto.setUrl(url);
            return new Response<>(uploadDto);
        }

        Integer size = Integer.valueOf(stringObjectMap.get("size").toString());
        Float duration = Float.valueOf(stringObjectMap.get("duration").toString());

        String folder=System.getProperty("java.io.tmpdir");
        String path = folder + File.separator + UUID.randomUUID().toString()+".jpg";
        LogUtil.debug(log,"截取封面path={}",path);
        RunShellUtils.getCover(url,path,"0.000");
        String coverImage = ossClientUtil.upload(path, OSSClientUtil.upload_type.image);
        LogUtil.debug(log,"截取封面coverImage={}",coverImage);

        SbVideo sbVideo = new SbVideo();
        sbVideo.setDimension(dimension);
        sbVideo.setOriUrl(url);
        sbVideo.setDuration(duration);
        sbVideo.setSize(size);
        sbVideo.setRefId(refId);
        sbVideo.setCover(coverImage);

        sbVideo.setUid(user.getUid());

        Header header = LocalData.HEADER.get();
        sbVideo.setAppId(Integer.valueOf(header.getAppId()));

        //保存短片
        sbVideoMapper.insertSelective(sbVideo);
        uploadDto.setId(sbVideo.getId());
        return new Response<>(uploadDto);
    }

    /**
     * 获取 uploadId
     *
     * @return
     */
    @RequestMapping(value = "/initiateUpload", method = RequestMethod.POST)
    public Response initiateUpload(@RequestParam String name, @RequestParam Integer partNum,
                                   @RequestParam String fileMD5,@RequestHeader(value = "appId", required = false) Integer appId) {
        if (StringUtils.isBlank(name) || partNum < 1 || StringUtils.isBlank(fileMD5)) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();


        return multipartUploadService.initiateMultipartUpload(name, partNum, uid, fileMD5,appId);
    }

    /**
     * uploadPart
     *
     * @param uploadId   uploadId
     * @param partNumber 标识号码
     * @param partMD5    用于前后端校验
     * @return
     */
    @RequestMapping("/uploadPart")
    public Response uploadPart(@RequestParam(value = "uploadId") String uploadId, @RequestParam(value = "partNumber") int partNumber,
                               @RequestParam(value = "partMD5") String partMD5,
                               @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (StringUtils.isBlank(uploadId) || !(partNumber > 0 && partNumber < 10001)) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();

        try {
            return multipartUploadService.uploadPart(partNumber, uploadId, uid, partMD5, file, request, partPath, partSize,header.getAppId());
        } catch (IOException e) {
            e.printStackTrace();
            return new Response(RespStatusEnum.ERROR);
        }
    }

    /**
     * 合并
     *
     * @param uploadId uploadId
     * @return
     */
    @RequestMapping("/completeUpload/{uploadId}")
    public Response completeUpload(@PathVariable String uploadId, HttpServletRequest request) {
        if (StringUtils.isBlank(uploadId)) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();

        return multipartUploadService.completeMultipartUpload(uploadId, uid, partPath,uploadIsAliyun, request,header.getAppId());
    }

    /**
     * 根据 uploadId 获取 videoId
     * @param uploadId
     * @param request
     * @return
     */
    @RequestMapping("/getVideoIdByUpload/{uploadId}")
    public Response getVideoIdByUpload(@PathVariable String uploadId, HttpServletRequest request) {
        if (StringUtils.isBlank(uploadId)) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();

        return multipartUploadService.getVideoIdByUpload(uploadId,uid,header.getAppId());
    }

    /**
     * 中止
     *
     * @param uploadId
     * @return
     */
    @RequestMapping("/abortUpload/{uploadId}")
    public Response abortUpload(@PathVariable String uploadId) {
        if (StringUtils.isBlank(uploadId)) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();

        return multipartUploadService.abortUpload(uploadId, uid,header.getAppId());
    }

    /**
     * 获取 UploadID所属的所有已经上传成功Part
     *
     * @param uploadId
     * @return
     */
    @RequestMapping("/listPart/{uploadId}")
    public Response listPart(@PathVariable String uploadId) {
        if (StringUtils.isBlank(uploadId)) {
            return new Response(RespStatusEnum.PARAM_FAIL);
        }
        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();

        return multipartUploadService.listPart(uploadId, uid,header.getAppId());
    }

}
