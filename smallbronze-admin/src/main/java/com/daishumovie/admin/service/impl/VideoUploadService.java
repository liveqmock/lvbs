package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.SbUploadVideosDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IVideoUploadService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.admin.util.FileUtil;
import com.daishumovie.base.enums.db.*;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUploadVideosMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbUploadVideos;
import com.daishumovie.dao.model.SbUploadVideosExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.daishumovie.admin.constant.Configuration.INSTANCE;

/**
 * Created by feiFan.gou on 2017/10/10 16:19.
 */
@Service
public class VideoUploadService implements IVideoUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoUploadService.class);

    private @Autowired
    SbTopicMapper topicMapper;
    private @Autowired
    SbUploadVideosMapper uploadMapper;
    private @Autowired
    SbVideoMapper videoMapper;
    private @Autowired
    IAdminService adminService;


    @Override
    @Transactional
    public List<SbTopic> handleDB(VideoSource source, String[] titles, Integer operatorId, String[] urls) {

        if (CollectionUtils.arrayIsNullOrEmpty(titles) || CollectionUtils.arrayIsNullOrEmpty(urls) || titles.length != urls.length) {
            throw new ResultException(ErrMsg.param_error);
        }
        List<SbTopic> topicList = Lists.newArrayList();
        try {
            for (int i = 0; i < titles.length; i++) {
                // 1. 插入Topic
                SbTopic topic = new SbTopic();
                topic.setTitle(StringUtil.trim(titles[i]));
                topic.setCreateTime(new Date());
                topic.setCreateOpeUid(operatorId);
                topic.setStatus(TopicStatus.publishing.getValue());
                topic.setSource(TopicSource.OWER.getValue());
                topic.setAppId(Configuration.current_app.getId());
                topicMapper.insertSelective(topic);
                {// 2. 插入上传视频
                    SbUploadVideos video = new SbUploadVideos();
                    video.setCreateTime(topic.getCreateTime());
                    video.setTopicId(topic.getId());
                    String downUrl = StringUtil.empty, localUrl = StringUtil.empty;
                    switch (source) {
                        case local:
                            localUrl = urls[i];
                            break;
                        case remote:
                            downUrl = Configuration.video_remote_url + urls[i];
                            break;
                        case crawler:
                            downUrl = urls[i];

                    }
                    video.setDownUrl(downUrl);
                    video.setLocalUrl(localUrl);
                    video.setName(titles[i]);
                    video.setIsDownload(Whether.no.getValue());
                    video.setStatus(UploadVideosStatus.WAIT.getValue());
                    video.setOperator(operatorId);
                    video.setType(source.getValue());
                    uploadMapper.insertSelective(video);
                }
                topicList.add(topic);
            }
        } catch (Exception e) {
            LOGGER.info("insert topic/upload_video error " + e.getMessage(), e);
            throw new ResultException();
        }
        return topicList;
    }

    @Override
    public void asyncUpload(List<SbTopic> topicList, List<String> filePathList) {

        //多线程处理
        List<Thread> threadList = Lists.newArrayList();
        filePathList.forEach(path -> {
            Runnable job = () -> {
                int index = filePathList.indexOf(path);
                SbTopic topic = topicList.get(index);
                if (null == topic) {
                    LOGGER.info("========== topic missing !!! topic is null ============");
                } else {
                    //1.修改视频状态为正在上传
                    SbUploadVideosExample example = new SbUploadVideosExample();
                    SbUploadVideosExample.Criteria criteria = example.createCriteria();
                    criteria.andTopicIdEqualTo(topic.getId());
                    List<SbUploadVideos> videoList = uploadMapper.selectByExample(example);
                    if (!CollectionUtils.isNullOrEmpty(videoList)) {
                        SbUploadVideos video = videoList.get(0);
                        video.setStatus(UploadVideosStatus.UPLOADING.getValue());
                        uploadMapper.updateByPrimaryKeySelective(video);
                        // 2.保存视频信息并上传到阿里云
                        try {
                            Integer videoId = insertVideo(path);
                            //3.topic和video表建立关系
                            topic.setVideoId(videoId);
                            topicMapper.updateByPrimaryKeySelective(topic);
                            video.setStatus(UploadVideosStatus.FINISH.getValue());
                        } catch (Exception e) {
                            LOGGER.error("========== upload to oss fail !!! ============");
                            video.setStatus(UploadVideosStatus.UPLOAD_FAIL.getValue());
                        }
                        uploadMapper.updateByPrimaryKeySelective(video);
                    } else {
                        LOGGER.error("========== video missing !!!!! topic_id [" + topic.getId() + "]  ============");
                    }
                }
            };
            threadList.add(new Thread(job));
        });
        threadList.forEach(Thread::start);
    }

    public List<String> upload2Local(List<MultipartFile> fileList) {

        List<String> localUrlList = Lists.newArrayList();
        try {
            fileList.forEach(multipartFile -> {
                File newFile = FileUtil.upload2local(multipartFile);
                localUrlList.add(newFile.getAbsolutePath());
            });
            if (localUrlList.isEmpty() || localUrlList.size() != fileList.size()) {
                throw new ResultException("上传文件失败");
            }
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.info("upload2Local error ===> \r" + e.getMessage(), e);
            throw new ResultException();
        }
        return localUrlList;
    }

    @Override
    public ReturnDto<SbUploadVideosDto> paginate(ParamDto param, String name, Integer userId, Integer status) {

        try {
            SbUploadVideosExample example = new SbUploadVideosExample();
            SbUploadVideosExample.Criteria criteria = example.createCriteria();
            {
                if (StringUtil.isNotEmpty(name)) {
                    criteria.andNameLike(StringUtil.sqlLike(name));
                }
                if (null != status) {
                    criteria.andStatusEqualTo(UploadVideosStatus.get(status).getValue());
                }
                if (null != userId) {
                    criteria.andOperatorEqualTo(userId);
                }
            }
            Page<SbUploadVideosDto> page = param.page();
            Long total = uploadMapper.countByExample(example);
            List<SbUploadVideosDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setOrderByClause(param.orderString());
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                List<SbUploadVideos> uploadVideosList = uploadMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(uploadVideosList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    uploadVideosList.forEach(uploadVideos -> {
                        SbUploadVideosDto dto = new SbUploadVideosDto();
                        if (null != uploadVideos.getOperator()) {
                            adminIdSet.add(Long.valueOf(uploadVideos.getOperator()));
                        }
                        BeanUtils.copyProperties(uploadVideos, dto);
                        dtoList.add(dto);
                    });
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    dtoList.forEach(dto -> {
                        if (null != dto.getOperator()) {
                            dto.setOperatorName(adminNameMap.get(dto.getOperator()));
                        }
                    });
                }
            }
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("topic paginate error --- > \r" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }

    @Override
    public void uploadAgain(Integer id) {

        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        SbUploadVideos video = uploadMapper.selectByPrimaryKey(id);
        if (null == video) {
            throw new ResultException("视频数据不存在");
        }
        try {
            if (video.getType().intValue() == VideoSource.crawler.getValue())
                video.setErrNum(0);
            video.setStatus(UploadVideosStatus.WAIT.getValue());
            uploadMapper.updateByPrimaryKeySelective(video);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            throw new ResultException();
        }
    }

    @Override
    public Response syndownload(Integer uploadvideoId) {
        Runnable job = () -> {
            LOGGER.error("========== syndownload api start !!! ============");
            SbUploadVideos uploadVideos = uploadMapper.selectByPrimaryKey(uploadvideoId);
            //根据id查询已下载的视频
            if (uploadVideos != null && uploadVideos.getIsDownload().equals(YesNoEnum.YES.getCode())){
                //判断视频相对路径是否存在,如果存在进行切图处理
                if (StringUtil.isNotEmpty(uploadVideos.getLocalUrl())) {
                    // 2.保存视频信息并上传到阿里云
                    try {
                        Integer videoId = insertVideo(uploadVideos.getLocalUrl());
                        SbTopic topic = topicMapper.selectByPrimaryKey(uploadVideos.getTopicId());
                        if (topic != null && videoId != null){
                            //3.topic和video表建立关系
                            topic.setVideoId(videoId);
                            topicMapper.updateByPrimaryKeySelective(topic);
                            uploadVideos.setStatus(UploadVideosStatus.FINISH.getValue());
                        }
                    } catch (Exception e) {
                        LOGGER.error("========== syndownload api to oss fail !!! ============");
                        uploadVideos.setStatus(UploadVideosStatus.UPLOAD_FAIL.getValue());
                    }
                    uploadMapper.updateByPrimaryKeySelective(uploadVideos);
                }
            }else{
                LOGGER.info("上传视频对象不存在,或者是没有下载成功，视频对象id:"+uploadvideoId);
            }
        };
        Thread thread = new Thread(job);
        thread.start();
        return new Response();
    }

    private Integer insertVideo(String localFilePath) throws Exception {

        SbVideo video = new SbVideo();
        video.setAppId(Configuration.current_app.getId());
        String coverPath = Configuration.local_image_path + StringUtil.uuid() + OSSClientUtil.upload_type.image.suffix;
        System.out.println("coverPath=========================================="+coverPath);
        //1.获取封面
        RunShellUtils.getCover(localFilePath, coverPath);
        //2.上传封面到阿里云
        coverPath = new OSSClientUtil(INSTANCE.endpoint).upload(coverPath, OSSClientUtil.upload_type.video_cover);
        video.setCover(coverPath);
        //3.尺寸
        video.setDimension(RunShellUtils.getVideoSize(localFilePath));
        //4.时长
        String duration = RunShellUtils.getDuration(localFilePath);
        video.setDuration(StringUtil.isNotEmpty(duration) ? Float.valueOf(duration) : 0);
        //5.大小
        String size = RunShellUtils.getFileSize(localFilePath);
        video.setSize(StringUtil.isNotEmpty(size) ? Integer.valueOf(size) : 0);
        //6.上传视频到阿里云
        String oriUrl = new OSSClientUtil(INSTANCE.endpoint).upload(localFilePath, OSSClientUtil.upload_type.video);
        video.setOriUrl(oriUrl);
        video.setCreateTime(new Date());
        video.setSource(TopicSource.OWER.getValue());
        video.setModifyTime(video.getCreateTime());
        videoMapper.insertSelective(video);
        return video.getId();
    }
}
