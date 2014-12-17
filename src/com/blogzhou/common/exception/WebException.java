package com.blogzhou.common.exception;

public class WebException extends CustomException {
    public WebException()
    {
    }

    public WebException(String s)
    {
        super(s);
    }

    public WebException(Exception e)
    {
        super(e);
    }

    public WebException(String msg, Exception e)
    {
        super(msg, e);
    }

	/**
     * 根据异常id,扩展异常信息，原始异常构造项目的自定义异常
     * 本函数为快捷构造函数，正规定义的异常应使用本构造函数
     * @param id 异常id
     * @param extmsg 扩展异常信息，该信息将会纪录到系统日志中
     * @param ex 原始异常信息
     */
    public WebException(String id, String extmsg) {
        super(id, extmsg, null);
    }
    
    /**
     * 根据异常id,扩展异常信息，原始异常构造项目的自定义异常
     * 本函数为快捷构造函数，正规定义的异常应使用本构造函数
     * @param id 异常id
     * @param extmsg 扩展异常信息，该信息将会纪录到系统日志中
     * @param ex 原始异常信息
     */
    public WebException(String id, String extmsg, Exception ex) {
        super(id, extmsg, ex);
    }
    
    private static final long serialVersionUID = -3213389201272319019L;
}
