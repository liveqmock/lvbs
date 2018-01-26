package com.daishumovie.timer.job;

import com.daishumovie.base.enums.db.TopicSource;
import com.daishumovie.base.enums.db.UploadVideosStatus;
import com.daishumovie.base.enums.db.VideoSource;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUploadVideosMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbUploadVideos;
import com.daishumovie.dao.model.SbUploadVideosExample;
import com.daishumovie.dao.model.SbVideo;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.OSSClientUtil;
import com.daishumovie.utils.RunShellUtils;
import com.daishumovie.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author yangxinwang on 2017/5/17
 * @since 1.0
 * 定时下载后台上传的视频文件
 */
@Slf4j
@RestController
public class VideoUploadJob {

    @Autowired
    private Base base;
    @Autowired
    private SbTopicMapper topicMapper;
    @Autowired
    private SbVideoMapper videoMapper;
    @Autowired
    private SbUploadVideosMapper uploadVideosMapper;

    @Value("${down_video_file_local_prefix}")
    public String downVideoFileLocalPrefix;
    @Value("${ali.oss.endpoint}")
    public String endpoint;
    @Value("${down_video_cover_prefix}")
    public String downVideoFileCoverPrefix;

    private static int num = 0;
    /**
     * 上传视频
     */
    @Scheduled(fixedDelay = 5000)
    void doBusiness() {
        log.info("执行一次视频上传定时任务开始--------》---------》");
        // 加锁 防止并发设置
        boolean lock = base.lock("api_topic_upload_lock_");

        if (lock) {
            videoUploadList();
        }
        log.info("执行一次视频上传定时任务结束 《--------《---------");
    }


    private void videoUploadList() {
        log.info("执行视频上传次数========================="+ num++);
        //step 1 : 取出
        SbUploadVideosExample example = new SbUploadVideosExample();
        SbUploadVideosExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(VideoSource.remote.getValue());
        criteria.andIsDownloadEqualTo(YesNoEnum.NO.getCode());
        criteria.andStatusEqualTo(UploadVideosStatus.WAIT.getValue());
        Long total = uploadVideosMapper.countByExample(example);
        if (total > 0){
            int pageSize = 5;
            int pageNo = total.intValue() / pageSize;
            pageNo = pageNo * pageSize < total.intValue() ? pageNo : pageNo + 1;
            example.setOffset(pageNo > 0 ? (pageNo -1) : 0 * pageSize);
            example.setLimit(5);
            List<SbUploadVideos> list = uploadVideosMapper.selectByExample(example);
            if (!CollectionUtils.isNullOrEmpty(list)) {
                for (SbUploadVideos uploadVideos : list) {
                    //更新上传视频表状态为正在上传
                    uploadVideos.setStatus(UploadVideosStatus.UPLOADING.getValue());
                    uploadVideosMapper.updateByPrimaryKeySelective(uploadVideos);
                    try {
                        //下载视频到本地服务器
                        if (uploadVideos.getDownUrl() != null) {
                            String destFileName = downVideoFileLocalPrefix+StringUtil.uuid()+".mp4";
                            int i = RunShellUtils.getWget(uploadVideos.getDownUrl(),destFileName);
                            if (i == 0){
                                boolean flag = updateTopicInfo(uploadVideos,destFileName);
                                uploadVideos.setStatus(flag ? UploadVideosStatus.FINISH.getValue() :  UploadVideosStatus.UPLOAD_FAIL.getValue());
                                uploadVideos.setIsDownload(flag ? YesNoEnum.YES.getCode() : YesNoEnum.NO.getCode());
                                uploadVideos.setLocalUrl(destFileName);
                                uploadVideosMapper.updateByPrimaryKeySelective(uploadVideos);
                            }else{
                                uploadVideos.setStatus(UploadVideosStatus.UPLOAD_FAIL.getValue());
                                uploadVideos.setIsDownload(YesNoEnum.NO.getCode());
                                uploadVideosMapper.updateByPrimaryKeySelective(uploadVideos);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected boolean updateTopicInfo(SbUploadVideos uploadVideos,String destFileName){
        //下载视频到本地服务器
        boolean flag = false;
        try {
            if (uploadVideos.getDownUrl() != null) {
                SbTopic topic = topicMapper.selectByPrimaryKey(uploadVideos.getTopicId());
                //如果topic表中的视频id为空，更新视频信息
                SbVideo video = saveVideo(destFileName,topic.getVideoId());
                if (video != null && StringUtil.isNotEmpty(video.getOriUrl())){
                    flag = true;
                }
                //更新视频信息成功并且topic表中视频id 为空
                if(flag && (topic.getVideoId() == null || topic.getVideoId() == 0)){
                    topic.setVideoId(video.getId());
                    topic.setModifyTime(new Date());
                    topicMapper.updateByPrimaryKeySelective(topic);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return flag;
        }
        return flag;
    }

    private SbVideo saveVideo(String localVideoFile,Integer videoId){
        SbVideo record = new SbVideo();
        String absolute_path = localVideoFile;
        String cover =  downVideoFileCoverPrefix + UUID.randomUUID()+".jpg";
        RunShellUtils.getCover(absolute_path,cover);
        //5.上传切图
        try {
            record.setCover(icon(cover));
        } catch (Exception e) {
            e.printStackTrace();
        }
        record.setDimension(RunShellUtils.getVideoSize(absolute_path));
        String duration = RunShellUtils.getDuration(absolute_path);
        record.setDuration(StringUtil.isNotEmpty(duration) ? Float.valueOf(duration) : 0);

        String size = RunShellUtils.getFileSize(absolute_path);
        record.setSize(StringUtil.isNotEmpty(size) ? Integer.valueOf(size) : 0);
        //2 上传视频到OSS服务
        try {
            String video_url = uploadToOssByVideo(absolute_path);
            record.setOriUrl(video_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        record.setSource(TopicSource.OWER.getValue());
        if (null == videoId || videoId == 0){
            record.setCreateTime(new Date());
            videoMapper.insertSelective(record);
        }else{
            record.setId(videoId);
            videoMapper.updateByPrimaryKeySelective(record);
        }
        return record;
    }

    /**
     * logo 处理
     *
     * @param previewUrl  本地视频路径
     */
    private String uploadToOssByVideo(String previewUrl) throws Exception {

        String video_url = StringUtil.trim(previewUrl);
        if (video_url.length() > 0) {
            return new OSSClientUtil(endpoint).upload(previewUrl, OSSClientUtil.upload_type.video);
        }
        return null;
    }

    /**
     * 上传切图
     *
     * @param
     */
    private String icon(String localCover) throws Exception {

        String icon = StringUtil.trim(localCover);
        if (icon.length() > 0) {
            return new OSSClientUtil(endpoint).upload(icon, OSSClientUtil.upload_type.video_cover);
        }
        return null;
    }

}
