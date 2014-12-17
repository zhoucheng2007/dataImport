package com.blogzhou.common.web.session.imp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.blogzhou.common.web.session.SessionStore;
import com.blogzhou.common.web.session.SessionStoreException;

public class NakeSessionStore implements SessionStore {
	
    private HttpSession session;
    public NakeSessionStore(HttpServletRequest req)
    {
        session = null;
        if(req != null)
            session = req.getSession();
    }

    public void setAttribute(String name, Object value)
    {
        session.setAttribute(name, value);
    }

    public Object getAttribute(String name)
    {
        Object o = null;
        o = session.getAttribute(name);
        return o;
    }

    public void removeAttribute(String name)
    {
        session.removeAttribute(name);
    }

    public int getMaxInactiveInterval()
    {
        int i = 0;
        i = session.getMaxInactiveInterval();
        return i;
    }

    public void setMaxInactiveInterval(int interval)
    {
        session.setMaxInactiveInterval(interval);
    }

    public void invalidate() throws SessionStoreException
    {
        session.invalidate();
    }


}
