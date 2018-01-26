package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.SbUploadVideosDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.base.enums.db.VideoSource;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.model.SbTopic;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/10/10 16:19.
 */
public interface IVideoUploadService {

    /**
     *
     * 数据库处理
     * @param source 视频来源
     * @param titles 标题
     * @param urls 地址
     * @param operatorId 操作人
     */
    List<SbTopic> handleDB(VideoSource source, String[] titles, Integer operatorId, String...urls);

    /**
     * 异步上传视频到阿里云
     * @param topicList 视频集合
     * @param filePathList 文件集合
     */
    void asyncUpload(List<SbTopic> topicList, List<String> filePathList);

    /**
     * 上传文件到本地
     * @param fileList
     * @return
     */
    List<String> upload2Local(List<MultipartFile> fileList);

    ReturnDto<SbUploadVideosDto> paginate(ParamDto param, String name, Integer userId, Integer status);

    void uploadAgain(Integer id);

    /**
     * 该接口是为爬虫抓取视频并成功下载后，接着有该接口处理后续业务，爬虫只管下载视频
     * @param uploadvideoId
     * @return
     */
    public Response syndownload(final Integer uploadvideoId) ;
}
