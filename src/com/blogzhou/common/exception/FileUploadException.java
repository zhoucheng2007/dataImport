package com.blogzhou.common.exception;

public class FileUploadException extends CustomException {
    public FileUploadException()
    {
    }

    public FileUploadException(String s)
    {
        super(s);
    }

    public FileUploadException(Exception e)
    {
        super(e);
    }

    public FileUploadException(String msg, Exception e)
    {
        super(msg, e);
    }

    private static final long serialVersionUID = -3213389201272319019L;
}
