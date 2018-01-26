package com.daishumovie.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static com.daishumovie.utils.OSSClientUtil.upload_type.m3u8;
import static com.daishumovie.utils.OSSClientUtil.upload_type.m3u8_ts;


/**
 * 阿里云OSS 图片服务器操作相关
 * Created by feiFan.gou on 2017/8/26 18:13.
 */

public class OSSClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OSSClientUtil.class);

    private static final String bucket_name;
    private static final String access_key_id;
    private static final String access_key_secret;
    private static final Date expiration;

    private static OSSClient OSS_CLIENT;

    private static final String img_dir;

    private static final String video_dir;

    static {

        bucket_name = "small-bronze";
        access_key_id = "LTAIYKhjSYiJzOhL";
        access_key_secret = "V9B7hhQpaYiz7zsgb3fod6ZqddFiMx";
        expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        img_dir = "img/";
        video_dir = "v/";
    }

    public enum upload_type {

        material_music("material/music","audio/mp3",".mp3"),
        material_filter("material/filter","application/x-zip-compressed",".zip"),
        material_sticker("material/sticker","application/x-zip-compressed",".zip"),
        material_gif("material/gif","application/x-zip-compressed",".zip"),
        material_subtitle("material/subtitle","application/x-zip-compressed",".zip"),

        material_icon("image/icon","image/jpeg",".jpg"),
        material_category_icon("image/category/icon","image/jpeg",".jpg"),
        material_preview("image/category/preview","image/jpeg",".jpg"),
        channel_icon("image/channel/icon","image/jpeg",".jpg"),
        channel_default("image/channel/default","image/jpeg",".jpg"),
        activity_icon("image/activity/icon","image/jpeg",".jpg"),
        activity_thumb("image/activity/thumb","image/jpeg",".jpg"),
        album_icon("image/album/icon","image/jpeg",".jpg"),
        video_cover("image/video/cover","image/jpeg",".jpg"),
        ad("image/ad/cover","image/jpeg",".jpg"),

        video("video/client","video/mp4",".mp4"),
        image("image/client","image/jpeg",".jpg"),

        m3u8("m3u8","audio/x-mpegurl",".m3u8"),
        m3u8_ts("m3u8","video/vnd.dlna.mpeg-tts",".ts"),
        ;

        public final String suffix;
        public final String contentType;
        public final String topDir;

        /**
         *
         * @param topDir 顶部目录
         * @param contentType contentType
         * @param suffix 后缀
         */
        upload_type(String topDir, String contentType, String suffix) {

            this.topDir = topDir;
            this.contentType = contentType;
            this.suffix = suffix;
        }

        public static upload_type getByCategoryType(String categoryType) {

            for (upload_type dir : upload_type.values()) {
                if (dir.name().equals("material_" + categoryType)) {
                    return dir;
                }
            }
            return null;
        }
    }

    public OSSClientUtil(String endpoint) {

        if (null == OSS_CLIENT || !Objects.equals(endpoint, OSS_CLIENT.getEndpoint())) {
            OSS_CLIENT = new OSSClient(endpoint, access_key_id, access_key_secret);
        }
    }

    /**
     * 上传文件到阿里云
     * @param filePath
     * @param uploadDir
     * @return
     * @throws Exception
     */
    public String upload(String filePath, upload_type uploadDir) throws Exception {

        long startTime = System.currentTimeMillis();
        try {
            if (StringUtil.isEmpty(filePath) || null == uploadDir) {
                throw new Throwable("上传文件参数为空");
            }
            String key = StringUtil.trim(getKey(uploadDir));
            if (uploadDir.equals(m3u8) || uploadDir.equals(m3u8_ts)) {
                key = getM3u8Key(filePath);
            }
            CompleteMultipartUploadResult uploadResult = doUpload(new File(filePath), key, uploadDir);
            LOGGER.info("upload result info === >" + FastJsonUtils.toJSONString(uploadResult));
            LOGGER.info("upload result consuming === >" + (System.currentTimeMillis() - startTime));
            if (!StringUtil.isEmpty(uploadResult.getETag())) {
                String url = StringUtil.trim(getUrl(key));
                if (url.length() > 0) {
                    LOGGER.info("upload result key === >" + url);
                    return url;
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new Exception("上传失败");
        } finally {
//            if (StringUtil.isNotEmpty(filePath)) {
//                File deleteFile = new File(filePath);
//                if (deleteFile.exists()) {
//                    deleteFile.delete();
//                }
//            }
        }
        return "";

    }

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    public Map<String, Object> uploadFile(MultipartFile file) {

        Map<String, Object> videoInfoMap = Maps.newHashMap();
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String tmpFileName = StringUtil.uuid();

            File tempFile = File.createTempFile(tmpFileName, suffix);
            file.transferTo(tempFile);
            if (tempFile.exists()) {
                upload_type uploadDir = getByContentType(file.getContentType());
                if (null != uploadDir) {
                    if (uploadDir.equals(upload_type.video)) {
                        //视频大小
                        videoInfoMap.put("size",file.getSize());
                        //视频时长
                        videoInfoMap.put("duration", RunShellUtils.getDuration(tempFile.getAbsolutePath()));//秒数
                    }
                    //视频信息处理工具
                    videoInfoMap.put("dimension", RunShellUtils.getVideoSize(tempFile.getAbsolutePath()));
                    String url = upload(tempFile.getAbsolutePath(), uploadDir);
                    videoInfoMap.put("url", url);
                }
            }
        } catch (Throwable throwable) {
            LOGGER.info("upload video error ---> " + throwable.getMessage(), throwable);
        }
        return videoInfoMap;
    }

    /**
     * 根据contentType 获取upload_dir
     * @param contentType
     * @return
     */
    private upload_type getByContentType(String contentType) {

        if (StringUtil.isNotEmpty(contentType)) {
            if (contentType.contains("image/"))
                return upload_type.image;
            else if (contentType.contains("video/"))
                return upload_type.video;
        }
        return upload_type.image;
    }

    private CompleteMultipartUploadResult doUpload(File file, String key, upload_type uploadDir) throws Throwable {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(uploadDir.contentType);
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucket_name, key);
        uploadFileRequest.setObjectMetadata(objectMetadata);
        uploadFileRequest.setUploadFile(file.getAbsolutePath());
        // 指定上传并发线程数
        uploadFileRequest.setTaskNum(5);
        // 指定上传的分片大小
        uploadFileRequest.setPartSize(1024 * 1024);
        // 开启断点续传
        uploadFileRequest.setEnableCheckpoint(true);
        // 断点续传上传
        //打印请求基本参数
        UploadFileResult uploadFileResult = OSS_CLIENT.uploadFile(uploadFileRequest);
        LOGGER.info("upload request oss info === > " + FastJsonUtils.toJSONString(requestOSSMap(uploadFileRequest)));
        return uploadFileResult.getMultipartUploadResult();
    }

    /**
     * 得到key
     *
     * @return
     */
    private static String getKey(upload_type uploadDir) {

        String top = uploadDir.topDir + "/";
        LocalDate date = LocalDate.now();
        StringBuilder builder = new StringBuilder(top);
        builder.append(date.getYear())
                .append("/")
                .append(date.getMonthValue())
                .append("/")
                .append(date.getDayOfMonth())
                .append("/")
                .append(StringUtil.uuid()).append(uploadDir.suffix);
            return builder.toString();
        }

    private static String getM3u8Key(String m3u8FilePath) {

        StringBuilder builder = new StringBuilder(m3u8.topDir + "/");
        File m3u8File = new File(m3u8FilePath);
        LocalDate date = LocalDate.now();
        builder.append(date.getYear())
                .append("/")
                .append(date.getMonthValue())
                .append("/")
                .append(date.getDayOfMonth())
                .append("/")
                .append(m3u8File.getParentFile().getName())
                .append("/")
                .append(m3u8File.getName());
        return builder.toString();
    }

    private String getUrl(String key) {

        // 生成URL
        URL url = OSS_CLIENT.generatePresignedUrl(bucket_name, key, expiration);
        if (url != null) {
            return url.toString().substring(0, url.toString().indexOf("?")).replace("-internal", "");
        }
        return null;
    }

    private Map<String, String> requestOSSMap(UploadFileRequest request) {

        Map<String, String> requestOSSMap = Maps.newHashMap();
        if (null != request) {
            requestOSSMap.put("file_path", request.getUploadFile());
            requestOSSMap.put("task_num", String.valueOf(request.getTaskNum()));
            requestOSSMap.put("content_type", request.getObjectMetadata().getContentType());
            requestOSSMap.put("file_size", String.valueOf(new File(request.getUploadFile()).length()));
        }
        return requestOSSMap;
    }

    /**
     *
     * @param type
     * @return
     */
    public Map<String,Object> uploadFile2OSSMap(String filepath, int type) {
        String ret = "";
        File tempFile = new File(filepath);
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            Random random = new Random();
            String prefix = random.nextInt(10000) + System.currentTimeMillis() + "";
            String suffix = "";
            String newFileName = "";
            if (tempFile.getName().lastIndexOf(".") != -1){
                suffix = tempFile.getName().substring(tempFile.getName().lastIndexOf("."))
                        .toLowerCase();
                newFileName = prefix + suffix;
            }else{
                newFileName = prefix;
            }

            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            // 上传文件
            String filedir = "";
            if (type == 1) {
                filedir = img_dir;
                if (newFileName.indexOf(".") == -1){
                    newFileName = newFileName + ".jpg";
                }
                objectMetadata.setContentType("image/jpeg");
            } else if (type == 2) {
                filedir = video_dir;
                objectMetadata.setContentType("video/mp4");
                //视频大小
                map.put("size",RunShellUtils.getFileSize(tempFile.getAbsolutePath()));

                //获取视频尺寸
                map.put("dimension",RunShellUtils.getVideoSize(tempFile.getAbsolutePath()));

                //视频时长
                map.put("duration",RunShellUtils.getDuration(tempFile.getAbsolutePath()));//秒数
                if (newFileName.indexOf(".") == -1){
                    newFileName = newFileName + ".mp4";
                }
            }
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucket_name, filedir + newFileName);
            uploadFileRequest.setObjectMetadata(objectMetadata);
            uploadFileRequest.setUploadFile(tempFile.getAbsolutePath());
            // 指定上传并发线程数
            uploadFileRequest.setTaskNum(5);
            // 指定上传的分片大小
            uploadFileRequest.setPartSize(1024 * 1024 * 1);
            // 开启断点续传
            uploadFileRequest.setEnableCheckpoint(true);
            // 断点续传上传
            UploadFileResult uploadFileResult = OSS_CLIENT.uploadFile(uploadFileRequest);

            CompleteMultipartUploadResult completeMultipartUploadResult = uploadFileResult.getMultipartUploadResult();
            if (!StringUtil.isEmpty(completeMultipartUploadResult.getETag())) {
                String url = getUrl(filedir + newFileName);

                map.put("error", 0);
                map.put("url",url);
                return map;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return map;
    }
}
