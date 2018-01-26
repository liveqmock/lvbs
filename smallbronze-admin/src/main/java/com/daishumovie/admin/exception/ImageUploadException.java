package com.daishumovie.admin.exception;




/**
 * Base exception that all other PoweruserException exceptions extend.
 * 文件上传异常
 * 
 */
public class ImageUploadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ImageUploadException() {
        super();
    }

    public ImageUploadException(String s, Throwable t) {
        super(s, t);
    }

    public ImageUploadException(String s) {
        super(s);
    }

    public ImageUploadException(Throwable t) {
        super(t);
    }

}