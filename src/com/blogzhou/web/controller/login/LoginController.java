package com.blogzhou.web.controller.login;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.blogzhou.common.WebConstants;
import com.blogzhou.common.exception.WebException;
import com.util.IpUtils;
import com.util.ReturnCode;
import com.util.enumclass.UserStatusEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends MultiActionController {


    @RequestMapping(value = {"welcome", ""})
    public String welcome() throws Exception {
        return "login";
    }

    /**
     * 登录验证
     *
     * @param loginName 用户名
     * @param password  密码
     * @param theme     主题
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"login"})
    public ModelAndView login(@RequestParam(required = true) String loginName, @RequestParam(required = true) String password,
                        String theme, HttpServletRequest request) {
        long timer = System.currentTimeMillis();
        long timeOfLogin = 0;
        
        String yzm_init="";
        String yzm_session="";
        String errorInfo = "登录不成功";
        boolean loginValid = true;
        HttpSession session=request.getSession();
    	String loginUrltemp=request.getContextPath()+"/jsp/login_new.jsp";
        String indexUrl=request.getContextPath()+"/jsp/wsbs/nsr_main.jsp";

    	/* eai调用错误*/
    	boolean eaierror = false; 
    	
    	// 页面访问权限控制：实用session标志：NSR_LOGIN_URL_FLAG：
    	Object obj_url_flag = session.getAttribute("NSR_LOGIN_URL_FLAG");
    		
    	if(obj_url_flag==null){
    		throw new WebException("","非法路径！");
    	}

    	Object obj = request.getSession().getAttribute(WebConstants.SESSION_ATTRIBUTE_USER);
    	User user = null;
    	if(obj!=null&&(obj instanceof User)){
    		user = (User)obj;	
    	}else{
    		throw new WebException("","非法路径！");
    	}

    	String loadFlag=(String)session.getAttribute("NSR_LOGIN_LOAD_TYPE");
    	
    	if(loadFlag.equalsIgnoreCase("1")){
    	
    		// 手机动态密码登录
    		if(((String)obj_url_flag).equals("MASSAGELOAD")){
    		
    			Object yzm_obj = session.getAttribute("NSR_LOGIN_MASSAGE_CODE");
    			if(yzm_obj==null||yzm_obj.equals("")){
    				loginValid=false;
    				errorInfo="动态登录验证码丢失！";
    			}else{
    				yzm_session = ((String)yzm_obj).trim();
    			}
    			
    			yzm_init = request.getParameter("yzm_init_MasSecurityLoad");
    		}
    		// 设置联系手机登录
    		else if(((String)obj_url_flag).equals("SETMOBILE")){
    			// 验证并且保存手机成功！
    		}else if(((String)obj_url_flag).equals("LOADNOMAL")){
    		// 普通登录模式！
    		}else{
    			throw new WebException("","非法路径！");
    		}
    		
       	}
       
        /*系统输入参数*/
        String username = user.getLoginName();

        Toolkit.createSession(request, username);
    	
       	LoginInfo loginInfo = Toolkit.getNsrjbxx(request);      
		String nsrzt = loginInfo.getDjzt();
		/** 00	登记受理 10	正常户 11	自动复业户 21	延期复业户 30	非正常户 40	失踪户 50	准注销户 60	注销户 61	非正常注销户 */
		if(nsrzt == null || (nsrzt.equals("30")||nsrzt.equals("40")||nsrzt.equals("50")||nsrzt.equals("60")||nsrzt.equals("61") )){
			throw new SecurityException("登录失败：您不是正常状态下的纳税户,不能登陆网上办税服务厅");
		}
        
		timer = System.currentTimeMillis() - timer;
	    String timeOfEai = (String) request.getAttribute(WebConstants.ATTRIBUTE_TIME_OF_CONTROLLER);
	    if (timeOfEai == null) {
	        timeOfEai = timeOfLogin + "." + timer;
	    } else {
	        timeOfEai = timeOfEai + "." + timeOfLogin + "." + timer;
	    }
		
		if(loginValid){
			session.removeAttribute("NSR_LOGIN_URL_FLAG");
			session.removeAttribute("NSR_LOGIN_URL_SAVEUSER");
			session.removeAttribute("NSR_SETMOTO_URL_FLAG");
			session.removeAttribute("NSR_LOGIN_MASSAGE_CODE");
			session.removeAttribute("NSR_LOGIN_LOAD_TYPE");
		}
	    
        return result;
    }

}
