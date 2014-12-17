package com.blogzhou.common.exception;

import org.apache.log4j.Logger;

public class CustomException extends RuntimeException{
	
    private static final long serialVersionUID = -6431052477112872438L;
    private boolean isLoged;
    
    private String  id;
    public CustomException()
    {
        isLoged = false;
    }

    public CustomException(String msg)
    {
        super(msg);
        isLoged = false;
    }

    public CustomException(Exception e)
    {
        super(e);
        isLoged = false;
        if(e instanceof CustomException)
            setIsLoged(((CustomException)e).getIsLoged());
    }

    public CustomException(String msg, Exception e)
    {
        super(msg, e);
        isLoged = false;
        if(e instanceof CustomException)
            setIsLoged(((CustomException)e).getIsLoged());
    }

    public CustomException(String id,String msg, Exception e)
    {
        super(msg, e);
        this.id=id;
        isLoged = false;
        if(e instanceof CustomException)
            setIsLoged(((CustomException)e).getIsLoged());
    }
    
    public void log(Logger logger)
    {
        if(!isLoged)
        {
            logger.debug(getMessage(), this);
            logger.warn(getMessage());
            setIsLoged(true);
        }
    }

    public boolean getIsLoged()
    {
        return isLoged;
    }

    public void setIsLoged(boolean loged)
    {
        isLoged = loged;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
