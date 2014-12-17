/**
 * 下午2:25:06
 * mailto: zhoucheng2007@gmail.com 
 */
package com.util;
import java.util.concurrent.*;

public class ThreadPoolManager
	{

	    private static ThreadPoolExecutor dispatcher = null;
	    private static int maxThreads = 50;
	    private static int minThreads = 5;
	
	    private ThreadPoolManager()
	    {
	    }

	    private static synchronized ThreadPoolExecutor getThreadPoolExecutor()
	    {
	        if(dispatcher == null)
	            dispatcher = new ThreadPoolExecutor(minThreads, maxThreads, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(5000), new java.util.concurrent.ThreadPoolExecutor.AbortPolicy());
	        return dispatcher;
	    }

	    public static synchronized void execute(Runnable command)
	    {
	        if(dispatcher == null)
	            dispatcher = getThreadPoolExecutor();
	        dispatcher.execute(command);
	    }

	    private static synchronized boolean isShutdown()
	    {
	        return dispatcher == null || dispatcher.isShutdown();
	    }

	    public static synchronized void shutdown()
	    {
	        if(!isShutdown())
	        {
	            dispatcher.shutdownNow();
	            dispatcher = null;
	        }
	    }

	    public static int getMaxThreads()
	    {
	        return maxThreads;
	    }

	    public static void setMaxThreads(int maxThreads)
	    {
	        maxThreads = maxThreads;
	    }

	    public static int getMinThreads()
	    {
	        return minThreads;
	    }

	    public static void setMinThreads(int minThreads)
	    {
	        minThreads = minThreads;
	    }



	}
