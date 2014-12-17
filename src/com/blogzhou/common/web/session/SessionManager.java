package com.blogzhou.common.web.session;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogzhou.common.exception.WebException;


	public final class SessionManager{
		
	    private static Logger logger= LoggerFactory.getLogger(SessionManager.class);;
	    SessionStore sessionStore;

	    public SessionManager(HttpServletRequest req)
	    {
	        sessionStore = SessionStoreManager.getNakeSessionStore(req);
	    }

	    public SessionManager(Object req)
	    {
	    }

	    public void setLoginNsrInfo(LoginNsrInfo loginUser)
	    {
	        sessionStore.setAttribute("login_user", loginUser);
	    }

	    public LoginNsrInfo getLoginNsrInfo()
	    {
	        LoginNsrInfo info = null;
	        if(sessionStore.getAttribute("login_user") != null)
	            info = (LoginNsrInfo)sessionStore.getAttribute("login_user");
	        return info;
	    }

	    public PermissionSet getPermissions() throws WebException
	    {
	        LoginNsrInfo info = getLoginNsrInfo();
	        if(info == null)
	        {
	            logger.warn("\u6CA1\u6709\u627E\u5230\u7528\u6237\u5BF9\u8C61,\u7528\u6237\u53EF\u80FD\u6CA1\u6709\u6B63\u5E38\u767B\u5F55!");
	            throw new WebException("\u6CA1\u6709\u627E\u5230\u7528\u6237\u5BF9\u8C61,\u7528\u6237\u53EF\u80FD\u6CA1\u6709\u6B63\u5E38\u767B\u5F55!");
	        }
	        PermissionSet permissions = null;
	        if(sessionStore.getAttribute("permissions") != null)
	            permissions = (PermissionSet)sessionStore.getAttribute("permissions");
	        else
	            try
	            {
	                SecurityManagerFactory factory = SecurityManagerFactory.getInstance("DBMS", "SIMPLE");
	                UserManager userManager = factory.getUserManager();
	                permissions = userManager.getPermissionsByUserId(info.getIntUserID());
	            }
	            catch(SecurityException e)
	            {
	                logger.info(e.getMessage());
	                throw new WebException(e);
	            }
	        return permissions;
	    }

	    public void setPermissions(PermissionSet permissions)
	    {
	        sessionStore.setAttribute("permissions", permissions);
	    }

	    public String getPSID()
	    {
	        String psid = null;
	        if(sessionStore.getAttribute("psid") != null)
	            psid = (String)sessionStore.getAttribute("psid");
	        return psid;
	    }

	    public void setPermissions(String psid)
	    {
	        sessionStore.setAttribute("psid", psid);
	    }


	}
