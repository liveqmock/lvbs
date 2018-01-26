package com.daishumovie.admin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;

/**
 * Created by feiFan.gou on 2017/8/30 17:51.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UploadResult {

    private String show_url;
    private String absolute_path;
    private long file_size;

    public UploadResult(String show_url, String absolute_path, File file) {
        this.show_url = show_url;
        this.absolute_path = absolute_path;
        if (null != file && file.exists()) {
            this.file_size = file.length();
        } else {
            this.file_size = 0L;
        }
    }
}
