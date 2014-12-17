package com.blogzhou.common.exception;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger;
public class BlogzhouException extends CustomException{
	  static final long serialVersionUID = 284375963102962861L;
	  private String mId = "";
	  private String mInstanceId = "";
	  private String mSysmsg = "";
	  private String mUsermsg = "";
	  private String mEnsysmsg = "";
	  private String mEnusermsg = "";

	  public BlogzhouException()
	  {
	  }

	  public BlogzhouException(String id, String extmsg, Exception ex)
	  {
	    super(id, ex);
	    this.mId = id;
	     // this.mSysmsg = "{[异常分类: " + ExceptionManager.getSysMsg(id) + "], [详细信息: " + extmsg + "]}";	   
	      //this.mUsermsg = ExceptionManager.getUserMsg(id);	 	  
	      //this.mEnsysmsg = ExceptionManager.getEnUserMsg(id);	   
	     // this.mEnusermsg = ExceptionManager.getEnUserMsg(id);	   
	    if (ex instanceof CustomException)
	      setIsLoged(((CustomException)ex).getIsLoged());

	    if (ex instanceof BlogzhouException) {
		      setInstanceId(((BlogzhouException)ex).getInstanceId());
	    }
	    else {
	    	this.mInstanceId = getNewInstanceId();
	    }
	      
	  }

	  public BlogzhouException(String id, String sysmsg, String ensysmsg, String usermsg, String enusermsg, String extmsg, Exception ex)
	  {
	    super(id, ex);
	    this.mId = id;
	    if (sysmsg.length() > 0)
	      this.mSysmsg = sysmsg + ", " + extmsg;
	    else
	      this.mSysmsg = extmsg;

	    this.mUsermsg = usermsg;
	    if (ensysmsg.length() > 0)
	      this.mEnsysmsg = ensysmsg + ", " + extmsg;
	    else
	      this.mEnsysmsg = extmsg;

	    this.mEnusermsg = enusermsg;
	    if (ex instanceof CustomException)
	      setIsLoged(((CustomException)ex).getIsLoged());

	    if (ex instanceof BlogzhouException)
	      setInstanceId(((BlogzhouException)ex).getInstanceId());
	    else
	      this.mInstanceId = getNewInstanceId();
	  }

	  public void log(Logger logger)
	  {
	    if (!(getIsLoged()))
	    {
	      if (logger.isDebugEnabled()) {
	        logger.debug(getMessage() + ":" + this.mSysmsg, this);
	      }

	      logger.warn(getMessage() + ":" + this.mSysmsg);

	      setIsLoged(true);
	    }
	  }

	  public String getSysMsg()
	  {
	    return this.mSysmsg;
	  }

	  public String getEnSysMsg()
	  {
	    return this.mEnsysmsg;
	  }

	  public String getUserMsg()
	  {
	    return this.mUsermsg;
	  }

	  public String getEnUserMsg()
	  {
	    return this.mEnusermsg;
	  }

	  public String getExceptionId()
	  {
	    return this.mId;
	  }

	  public String getInstanceId()
	  {
	    return this.mInstanceId;
	  }

	  public void setInstanceId(String instanceId)
	  {
	    this.mInstanceId = instanceId;
	  }

	  private String getNewInstanceId()
	  {
	    String ip = "";
	    try {
	      InetAddress addres = InetAddress.getLocalHost();
	      ip = addres.getHostAddress();
	    } catch (UnknownHostException e) {
	      ip = "UnknownHost";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return ip + " " + sdf.format(Calendar.getInstance().getTime()) + " " + String.valueOf(System.currentTimeMillis());
	  }

	  public String getMessage()
	  {
	    return getSysMsg();
	  }
}
