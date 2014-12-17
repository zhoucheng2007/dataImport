package com.blogzhou.common.web.session;

import javax.servlet.http.HttpServletRequest;

import com.blogzhou.common.web.session.imp.NakeSessionStore;

public class SessionStoreManager {
    private SessionStoreManager()
    {
    }

    public static SessionStore getNakeSessionStore(HttpServletRequest req)
    {
        return new NakeSessionStore(req);
    }
}
