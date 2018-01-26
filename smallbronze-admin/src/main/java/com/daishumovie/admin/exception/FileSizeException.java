package com.daishumovie.admin.exception;




/**
 * Base exception that all other PoweruserException exceptions extend.
 * 文件大小异常
 */
public class FileSizeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileSizeException() {
        super();
    }

    public FileSizeException(String s, Throwable t) {
        super(s, t);
    }

    public FileSizeException(String s) {
        super(s);
    }

    public FileSizeException(Throwable t) {
        super(t);
    }

}