package com.blogzhou.common.web.session;

	public interface SessionStore
	{

	    public abstract void setAttribute(String s, Object obj);

	    public abstract Object getAttribute(String s);

	    public abstract void removeAttribute(String s);

	    public abstract int getMaxInactiveInterval();

	    public abstract void setMaxInactiveInterval(int i);

	    public abstract void invalidate()  throws SessionStoreException;

}
