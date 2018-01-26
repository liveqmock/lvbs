package com.daishumovie.admin.util;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by feiFan.gou on 2017/10/11 10:34.
 * 文件操作工具
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static File upload2local(MultipartFile multipartFile) {

        FileOutputStream fos = null;
        InputStream is = null;
        try {
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String fileName = StringUtil.uuid();
            String filePath = Configuration.local_video_path + fileName + suffix;
            is = multipartFile.getInputStream();
            if (null != is) {
                fos = new FileOutputStream(filePath);
                int temp;
                byte[] buffer = new byte[1024];
                while (-1 != (temp = is.read(buffer))) {
                    fos.write(buffer, 0, temp);
                }
                fos.flush();
            }
            return new File(filePath);
        } catch (Exception e) {
            LOGGER.info("upload error ---> \r", e);
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (Exception ignored) {}
        }
        throw new RuntimeException("上传失败");
    }

//    public static File upload2local(File file)
}
