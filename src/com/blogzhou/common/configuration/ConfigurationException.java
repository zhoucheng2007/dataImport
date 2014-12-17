package com.blogzhou.common.configuration;

import com.blogzhou.common.exception.CustomException;

public class ConfigurationException extends CustomException{
	  private static final long serialVersionUID = -4841784799981831495L;

	  public ConfigurationException()
	  {
	  }

	  public ConfigurationException(String msg)
	  {
	    super(msg);
	  }

	  public ConfigurationException(String msg, Exception e)
	  {
	    super(msg, e);
	  }
}
