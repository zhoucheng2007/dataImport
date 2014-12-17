package com.blogzhou.common.web.session;

import java.io.PrintStream;
import java.io.PrintWriter; 

public class SessionStoreException extends Exception{
	
    private static final long serialVersionUID = 2870512577491061155L;
    private Throwable previousThrowable;
	
	   public SessionStoreException()
	    {
	        previousThrowable = null;
	    }

	    public SessionStoreException(String inMessage)
	    {
	        super(inMessage);
	        previousThrowable = null;
	    }

	    public SessionStoreException(String inMessage, Throwable inThrowable)
	    {
	        super(inMessage);
	        previousThrowable = null;
	        previousThrowable = inThrowable;
	    }

	    public SessionStoreException(Throwable inThrowable)
	    {
	        super(inThrowable.getMessage());
	        previousThrowable = null;
	        previousThrowable = inThrowable;
	    }

	    public void printStackTrace()
	    {
	        super.printStackTrace();
	        if(previousThrowable != null)
	            previousThrowable.printStackTrace();
	    }

	    public void printStackTrace(PrintStream inPrintStream)
	    {
	        super.printStackTrace(inPrintStream);
	        if(previousThrowable != null)
	            previousThrowable.printStackTrace(inPrintStream);
	    }

	    public void printStackTrace(PrintWriter inPrintWriter)
	    {
	        super.printStackTrace(inPrintWriter);
	        if(previousThrowable != null)
	            previousThrowable.printStackTrace(inPrintWriter);
	    }

}
