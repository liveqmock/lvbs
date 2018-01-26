package com.daishumovie.admin.controller.ajax;

import com.daishumovie.admin.dto.JSONResult;
import com.daishumovie.admin.dto.UploadResult;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/8/30 17:42.
 * 异步上传文件控制器
 */
@RestController
@RequestMapping("/ajax/upload")
public class AjaxUploadController {

    public static final String resource_temp_path = "/statics/temporary/";
    private static final String temp_url = "/temporary/";
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxUploadController.class);


    @RequestMapping(value = StringUtil.empty, method = RequestMethod.POST)
    public JSONResult upload(HttpServletRequest request) {

        try {
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
            if (!resolver.isMultipart(request)) {
                throw new ResultException("上传文件为空");
            }
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multiRequest.getFileNames();
            List<UploadResult> resultList = Lists.newArrayList();
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                List<MultipartFile> fileList = multiRequest.getFiles(fileName);
                for (MultipartFile file : fileList) resultList.add(upload2LocalTmp(file));
            }
            return JSONResult.success(resultList);
        } catch (ResultException e) {
            return JSONResult.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.fail();
        }
    }


    /**
     * 上传视频到本地临时路径
     */
    private UploadResult upload2LocalTmp(MultipartFile multipartFile) throws Exception {


        FileOutputStream fos = null;
        InputStream is = null;
        try {
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().indexOf("."));
            String tmpFileName = StringUtil.uuid()+suffix;
            String tmpAbsolutePath = this.getClass().getResource(resource_temp_path) + tmpFileName;
            is = multipartFile.getInputStream();
            if (null != is) {
                tmpAbsolutePath = tmpAbsolutePath.substring(5);
                fos = new FileOutputStream(tmpAbsolutePath);
                int temp;
                byte[] buffer = new byte[1024];
                while (-1 != (temp = is.read(buffer))) {
                    fos.write(buffer, 0, temp);
                }
                fos.flush();
                fos.close();
            }
            return new UploadResult(temp_url + tmpFileName, tmpAbsolutePath, new File(tmpAbsolutePath));
        } catch (Exception e) {
            LOGGER.info("upload error ---> \r", e);
        } finally {
            if (null != is) {
                is.close();
            }
            if (null != fos) {
                fos.close();
            }
        }
        return null;
    }


}
