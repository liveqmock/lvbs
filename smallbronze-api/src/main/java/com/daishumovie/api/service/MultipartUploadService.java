package com.daishumovie.api.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.daishumovie.base.dto.KeyValue;
import com.daishumovie.base.enums.db.MultipartUploadStatusEnum;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmMultipartuploadMapper;
import com.daishumovie.dao.mapper.smallbronze.DsmMultipartuploadPartMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.DsmMultipartupload;
import com.daishumovie.dao.model.DsmMultipartuploadExample;
import com.daishumovie.dao.model.DsmMultipartuploadPart;
import com.daishumovie.dao.model.DsmMultipartuploadPartExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.LogUtil;
import com.daishumovie.utils.OSSClientUtil;
import com.daishumovie.utils.RunShellUtils;
import com.daishumovie.utils.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 分块上传
 *
 * @author Cheng Yufei
 * @create 2017-08-09 15:50
 **/
@Service
@Slf4j
public class MultipartUploadService {

    @Autowired
    private DsmMultipartuploadMapper dsmMultipartuploadMapper;

    @Autowired
    private DsmMultipartuploadPartMapper dsmMultipartuploadPartMapper;

    @Autowired
    private SbVideoMapper sbVideoMapper;

    @Value("${ali.oss.endpoint}")
    private String endpoint;


    @Transactional
    public Response<Map<String, String>> initiateMultipartUpload(String name, int partNum, int uid, String fileMD5, Integer appId) {
        String uploadId = StringUtil.uuid();

        DsmMultipartupload dsmMultipartupload = new DsmMultipartupload();
        dsmMultipartupload.setUid(uid);
        dsmMultipartupload.setUploadId(uploadId);
        dsmMultipartupload.setName(name);
        dsmMultipartupload.setPartNum(partNum);
        dsmMultipartupload.setEtag(fileMD5);
        if (null != appId) {
            dsmMultipartupload.setAppId(appId);
        }
        int result = dsmMultipartuploadMapper.insertSelective(dsmMultipartupload);
        if (result > 0) {
            Map<String, String> map = new HashMap<>();
            map.put("uploadId", uploadId);
            return new Response<>(map);
        }
        return new Response<>(RespStatusEnum.ERROR);
    }

    @Transactional
    public Response uploadPart(int partNumber, String uploadId, int uid, String partMD5, MultipartFile file, HttpServletRequest request, String partPath, Long partSize, String appId) throws IOException {
        try {
            //校验 uploadid
            DsmMultipartupload multipartupload = checkUploadId(uploadId, uid, appId);
            if (null == multipartupload) {
                KeyValue kv = new KeyValue();
                kv.setValue(RespStatusEnum.MULTIPARTUPLOAD_UPLOADID_ERROR.getDesc());
                kv.setKey(RespStatusEnum.MULTIPARTUPLOAD_UPLOADID_ERROR.getStatus() + "");
                return new Response<>(kv);
            }
            // 需前端分块 partNumber 依次递增
            int partNum = multipartupload.getPartNum();
            if (partNumber < partNum) {
                if (file.getSize() < partSize) {
                    KeyValue kv = new KeyValue();
                    kv.setValue(RespStatusEnum.PARTSIZE_ERROR.getDesc());
                    kv.setKey(RespStatusEnum.PARTSIZE_ERROR.getStatus() + "");
                    return new Response<>(kv);
                }
            }
            //校验MD5
            String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(file.getInputStream()));
            if (!md5.equals(partMD5)) {
                KeyValue kv = new KeyValue();
                kv.setValue(RespStatusEnum.PARTMD5_ERROR.getDesc());
                kv.setKey(RespStatusEnum.PARTMD5_ERROR.getStatus() + "");
                return new Response<>(kv);
            }
            IOUtils.closeQuietly(file.getInputStream());

            String newPartPath = partPath + File.separator + String.valueOf(uid) + File.separator + uploadId;
            File newFile = new File(newPartPath);
            if (!newFile.exists()) {
                //注意设置创建文件夹的权限
                newFile.mkdirs();
            }
            //上传
            String newPath = newPartPath + File.separator + uploadId + "_" + String.valueOf(partNumber);
            file.transferTo(new File(newPath));

            DsmMultipartuploadPartExample partExample = new DsmMultipartuploadPartExample();
            partExample.setLimit(1);
            DsmMultipartuploadPartExample.Criteria criteria = partExample.createCriteria();
            criteria.andUidEqualTo(uid);
            criteria.andUploadIdEqualTo(uploadId);
            criteria.andPartNumberEqualTo(partNumber);
            criteria.andStatusEqualTo(MultipartUploadStatusEnum.UPLOAD_NO_SUCCESS.getCode().shortValue());
            if (StringUtils.isNotBlank(appId)) {
                criteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            List<DsmMultipartuploadPart> dsmMultipartuploadParts = dsmMultipartuploadPartMapper.selectByExample(partExample);
            if (CollectionUtils.isNullOrEmpty(dsmMultipartuploadParts)) {
                //insert part
                DsmMultipartuploadPart dsmMultipartuploadPart = new DsmMultipartuploadPart();
                dsmMultipartuploadPart.setUploadId(uploadId);
                dsmMultipartuploadPart.setUid(uid);
                dsmMultipartuploadPart.setPartNumber(partNumber);
                dsmMultipartuploadPart.setEtag(partMD5);
                dsmMultipartuploadPart.setLocation(newPath);
                if (StringUtils.isNotBlank(appId)) {
                    dsmMultipartuploadPart.setAppId(Integer.valueOf(appId));
                }
                dsmMultipartuploadPartMapper.insertSelective(dsmMultipartuploadPart);
            } else {
                DsmMultipartuploadPart part = new DsmMultipartuploadPart();
                part.setId(dsmMultipartuploadParts.get(0).getId());
                part.setEtag(partMD5);
                part.setLocation(newPath);
                dsmMultipartuploadPartMapper.updateByPrimaryKeySelective(part);
            }

            return new Response(newPath);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new Response(RespStatusEnum.ERROR);
        }
    }

    @Transactional
    public Response completeMultipartUpload(String uploadId, int uid, String path, boolean uploadIsAliyun, HttpServletRequest request, String appId) {
        try {
            //校验uploadid
            DsmMultipartupload multipartupload = checkUploadId(uploadId, uid, appId);
            if (null == multipartupload) {
                KeyValue kv = new KeyValue();
                kv.setValue(RespStatusEnum.MULTIPARTUPLOAD_UPLOADID_ERROR.getDesc());
                kv.setKey(RespStatusEnum.MULTIPARTUPLOAD_UPLOADID_ERROR.getStatus() + "");
                return new Response<>(kv);
            }
            //获取 part
            DsmMultipartuploadPartExample example = new DsmMultipartuploadPartExample();
            example.setOrderByClause("part_number ASC");
            DsmMultipartuploadPartExample.Criteria criteria = example.createCriteria();
            criteria.andUidEqualTo(uid);
            criteria.andUploadIdEqualTo(uploadId);
            if (StringUtils.isNotBlank(appId)) {
                criteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            criteria.andStatusEqualTo(MultipartUploadStatusEnum.UPLOAD_NO_SUCCESS.getCode().shortValue());
            List<DsmMultipartuploadPart> dsmMultipartuploadParts = dsmMultipartuploadPartMapper.selectByExample(example);
            if (CollectionUtils.isNullOrEmpty(dsmMultipartuploadParts)) {
                KeyValue kv = new KeyValue();
                kv.setValue(RespStatusEnum.COMPLETE_NOPART.getDesc());
                kv.setKey(RespStatusEnum.COMPLETE_NOPART.getStatus() + "");
                return new Response<>(kv);
            }
            List<File> fileList = new ArrayList<>();
            List<Integer> partIdList = new ArrayList<>();
            for (DsmMultipartuploadPart part : dsmMultipartuploadParts) {
                File file = new File(part.getLocation());
                fileList.add(file);
                partIdList.add(part.getId());
            }
            // 合并part
            String completePath = path + File.separator + String.valueOf(uid) + File.separator + uploadId;
            String newCompletePath = completePath + File.separator + uploadId + multipartupload.getName().substring(multipartupload.getName().lastIndexOf("."));

            File completeFile = new File(newCompletePath);
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(completeFile));
            byte[] b = new byte[1024 * 100];
            for (File file : fileList) {
                fileInputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                int len;
                while ((len = bufferedInputStream.read(b)) != -1) {
                    bufferedOutputStream.write(b, 0, len);
                }
                fileInputStream.close();
                bufferedInputStream.close();
                bufferedOutputStream.flush();
            }
            //上传 视频 aliyun
            Map<String, Object> ossMap = new HashMap<>();
            String coverImage = null;
            int resultVideoId = 0;
            if (uploadIsAliyun) {

                OSSClientUtil ossClientUtil = new OSSClientUtil(endpoint);
                ossMap = ossClientUtil.uploadFile2OSSMap(newCompletePath, 2);

                String folder = System.getProperty("java.io.tmpdir");
                String coverPath = folder + File.separator + UUID.randomUUID().toString() + ".jpg";
                LogUtil.debug(log, "截取封面path={}", coverPath);
                String url = ossMap.get("url").toString();
                RunShellUtils.getCover(url, coverPath, "0.000");
                coverImage = ossClientUtil.upload(coverPath, OSSClientUtil.upload_type.image);
                LogUtil.debug(log, "截取封面coverImage={}", coverImage);

                //保存到sb_video
                SbVideo sbVideo = new SbVideo();
                sbVideo.setDimension(String.valueOf(ossMap.get("dimension")));
                if (null != ossMap.get("url")) {
                    sbVideo.setOriUrl(ossMap.get("url").toString());
                } else {
                    sbVideo.setOriUrl(newCompletePath);
                }
                sbVideo.setDuration(Float.valueOf(ossMap.get("duration").toString()));
                sbVideo.setSize(Integer.valueOf(ossMap.get("size").toString()));
                sbVideo.setCover(coverImage);
                sbVideo.setUid(uid);
                if (StringUtils.isNotBlank(appId)) {
                    sbVideo.setAppId(Integer.valueOf(appId));
                }
                //保存短片
                sbVideoMapper.insertSelective(sbVideo);
                resultVideoId = sbVideo.getId();
            }
            //uploadId 置为完成 , part 置为完成
            DsmMultipartupload dsmMultipartupload = new DsmMultipartupload();
            dsmMultipartupload.setId(multipartupload.getId());
            dsmMultipartupload.setLocation(newCompletePath);
            dsmMultipartupload.setVideoId(resultVideoId);
            if (null != ossMap.get("url")) {
                dsmMultipartupload.setUrl(ossMap.get("url").toString());
            }
            dsmMultipartupload.setStatus(MultipartUploadStatusEnum.UPLOAD_SUCCESS.getCode().shortValue());
            dsmMultipartuploadMapper.updateByPrimaryKeySelective(dsmMultipartupload);

            for (Integer partId : partIdList) {
                DsmMultipartuploadPart part = new DsmMultipartuploadPart();
                part.setId(partId);
                part.setStatus(MultipartUploadStatusEnum.UPLOAD_SUCCESS.getCode().shortValue());
                dsmMultipartuploadPartMapper.updateByPrimaryKeySelective(part);
            }
            Map<String, Integer> map = new HashMap<>();
            map.put("videoId", resultVideoId);
            return new Response(map);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new Response(RespStatusEnum.ERROR);
        }
    }

    public Response getVideoIdByUpload(String uploadId, int uid, String appId) {
        DsmMultipartuploadExample multipartuploadExample = new DsmMultipartuploadExample();
        multipartuploadExample.setLimit(1);
        DsmMultipartuploadExample.Criteria uploadCriteria = multipartuploadExample.createCriteria();
        uploadCriteria.andUploadIdEqualTo(uploadId);
        if (StringUtils.isNotBlank(appId)) {
            uploadCriteria.andAppIdEqualTo(Integer.valueOf(appId));
        }
        uploadCriteria.andUidEqualTo(uid);
        List<DsmMultipartupload> dsmMultipartuploads = dsmMultipartuploadMapper.selectByExample(multipartuploadExample);
        if (CollectionUtils.isNullOrEmpty(dsmMultipartuploads)) {
            return new Response(RespStatusEnum.VIDEOID_ERROR);
        }
        DsmMultipartupload multipartupload = dsmMultipartuploads.get(0);
        Map<String, Integer> map = new HashMap<>();
        map.put("videoId", multipartupload.getVideoId());
        return new Response(map);

    }


    @Transactional
    public Response abortUpload(String uploadId, int uid, String appId) {
        try {
           /* DsmMultipartupload multipartupload = checkUploadId(uploadId, uid, appId);
            if (null == multipartupload) {
                return new Response(RespStatusEnum.MULTIPARTUPLOAD_UPLOADID_ERROR);
            }*/
            DsmMultipartuploadExample multipartuploadExample = new DsmMultipartuploadExample();
            multipartuploadExample.setLimit(1);
            DsmMultipartuploadExample.Criteria uploadCriteria = multipartuploadExample.createCriteria();
            uploadCriteria.andUploadIdEqualTo(uploadId);
            if (StringUtils.isNotBlank(appId)) {
                uploadCriteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            uploadCriteria.andUidEqualTo(uid);
            List<DsmMultipartupload> multipartuploads = dsmMultipartuploadMapper.selectByExample(multipartuploadExample);

            if (CollectionUtils.isNullOrEmpty(multipartuploads)) {
                KeyValue kv = new KeyValue();
                kv.setValue(RespStatusEnum.MULTIPARTUPLOAD_UPLOADID_ERROR.getDesc());
                kv.setKey(RespStatusEnum.MULTIPARTUPLOAD_UPLOADID_ERROR.getStatus() + "");
                return new Response<>(kv);
            }

            DsmMultipartupload dsmMultipartupload = new DsmMultipartupload();
            dsmMultipartupload.setId(multipartuploads.get(0).getId());
            dsmMultipartupload.setStatus(MultipartUploadStatusEnum.DELETE_OR_ABORT.getCode().shortValue());
            dsmMultipartuploadMapper.updateByPrimaryKeySelective(dsmMultipartupload);

            DsmMultipartuploadPartExample example = new DsmMultipartuploadPartExample();
            DsmMultipartuploadPartExample.Criteria criteria = example.createCriteria();
            criteria.andUidEqualTo(uid);
            criteria.andUploadIdEqualTo(uploadId);
            if (StringUtils.isNotBlank(appId)) {
                criteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            List<DsmMultipartuploadPart> dsmMultipartuploadParts = dsmMultipartuploadPartMapper.selectByExample(example);
            if (CollectionUtils.isNullOrEmpty(dsmMultipartuploadParts)) {
                return new Response();
            }
            for (DsmMultipartuploadPart part : dsmMultipartuploadParts) {
                DsmMultipartuploadPart multipartuploadPart = new DsmMultipartuploadPart();
                multipartuploadPart.setId(part.getId());
                multipartuploadPart.setStatus(MultipartUploadStatusEnum.DELETE_OR_ABORT.getCode().shortValue());
                dsmMultipartuploadPartMapper.updateByPrimaryKeySelective(multipartuploadPart);
            }
            return new Response();
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }
    }

    public Response listPart(String uploadId, int uid, String appId) {
        try {

            DsmMultipartuploadPartExample example = new DsmMultipartuploadPartExample();
            DsmMultipartuploadPartExample.Criteria criteria = example.createCriteria();
            criteria.andUidEqualTo(uid);
            criteria.andUploadIdEqualTo(uploadId);
            if (StringUtils.isNotBlank(appId)) {
                criteria.andAppIdEqualTo(Integer.valueOf(appId));
            }
            criteria.andStatusEqualTo(MultipartUploadStatusEnum.UPLOAD_SUCCESS.getCode().shortValue());
            List<DsmMultipartuploadPart> dsmMultipartuploadParts = dsmMultipartuploadPartMapper.selectByExample(example);
            return new Response(dsmMultipartuploadParts);
        } catch (Exception e) {
            return new Response(RespStatusEnum.ERROR);
        }
    }

    public DsmMultipartupload checkUploadId(String uploadId, int uid, String appId) {
        DsmMultipartuploadExample example = new DsmMultipartuploadExample();
        example.setLimit(1);
        DsmMultipartuploadExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(MultipartUploadStatusEnum.UPLOAD_NO_SUCCESS.getCode().shortValue());
        criteria.andUploadIdEqualTo(uploadId);
        criteria.andUidEqualTo(uid);
        if (StringUtils.isNotBlank(appId)) {
            criteria.andAppIdEqualTo(Integer.valueOf(appId));
        }
        List<DsmMultipartupload> dsmMultipartuploads = dsmMultipartuploadMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(dsmMultipartuploads)) {
            return null;
        }
        return dsmMultipartuploads.get(0);
    }
}
