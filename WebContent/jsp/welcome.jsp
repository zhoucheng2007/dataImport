<%@ page import="com.util.CookieUtils" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="../jsp/common/head.jsp"%>
<%@ include file="../jsp/common/autocomplete.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>用户登录</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/skin/css/login/normalize.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/skin/css/login/demo_new.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/skin/css/login/component.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/skin/css/login/bootstrap.min.css">	  
    <script type="text/javascript" src="<%=request.getContextPath()%>/skin/js/login/modernizr.custom.js" ></script>
	
    <%
        String loginNameOrName = CookieUtils.getCookie(request, "loginName");
        request.setAttribute("loginNameOrName",loginNameOrName);
    %>
    <script type="text/javascript">
       var loginForm;
       var login_linkbutton;
       var $loginName,$password,$autoLogin;
         $(function(){
            $loginName = $("#loginName");
            $password = $("#password");
            $autoLogin = $("#autoLogin");
            loginForm = $('#loginForm').form();
            $loginName.val("${cookie.loginName.value}");
           	$password.val("${cookie.password.value}");
            $autoLogin.prop("checked", "${cookie.autoLogin.value}" == "" ? false : true);
            
            if("${cookie.autoLogin.value}" != ""){
                 login();
            }else{
                $loginName.focus();
            }

            $autoLogin.click(function(){
                var checked = $(this).prop('checked');
                if(checked){
                	$.cookie('loginName', $loginName.val(), {
                        expires : 7
                    });
                    $.cookie('password', $password.val(), {
                        expires : 7
                    });
                    $.cookie('autoLogin', checked, {
                        expires : 7
                    });
                }else{
                    $.cookie('password', "", {
                        expires : 7
                    });
                     $.cookie('loginName', "", {
                        expires : 7
                    });
                    $.cookie('autoLogin', "", {
                        expires : 7
                    });
                }
            });

             $loginName = $("#loginName").autocomplete('${ctx}/sys/user/autoComplete', {
                 remoteDataType:'json',
                 minChars: 0,
                 maxItemsToShow: 10
             });
             var ac = $loginName.data('autocompleter');
             //添加查询属性
             ac.setExtraParam("rows",ac.options.maxItemsToShow);
        });
       
        // 登录
        function login() {    
       		var checked = $autoLogin.prop("checked");    	
            if(checked){
                $.cookie('loginName', $loginName.val(), {
                        expires : 7
                    });
                $.cookie('password', $password.val(), {
                    expires : 7
                });
                $.cookie('autoLogin', checked, {
                    expires : 7
                });
           	}
            if(loginForm.form('validate')){
                var cookieThemeType = "${cookie.themeType.value}"; //cookie初访的登录管理界面类型
                $.post('${ctx}/login/login?theme='+cookieThemeType,$.serializeObject(loginForm), function(data) {
                    if (data.code ==1){
                        window.location = data.obj;//操作结果提示
                    }else {
                        $('#validateCode').val('');
                        eu.showMsg(data.msg);
                    }
                }, 'json');
            }
        }
       
    </script>
</head>
<body>
		<div class="container" >
			<div class="panel"><img src="${ctx}/skin/img/login/bg.jpg" width="100%" height="100%"/></div>           
			<header class="codrops-header">
				<h1 align="center" style="color:white">刀剑神域</h1>
                <form class="loginform" role="form" method="post" id="loginForm" >
   					<div class="sanjiao"></div>
					 <label for="loginName"> 用户名</label>
                  <input type="text" name="loginName" id="loginName" value="" placeholder="用户名" />
					<label for="password">密码</label>
					<input type="password" id="password" placeholder="密码" class="form-control" name="password">
    				 
                   <label for="autoLogin" class="checkbox"><input type="checkbox" name="autoLogin" id="autoLogin" />自动登录</label>
    				 <button id="login_linkbutton"  onclick="login()" class="btn btn-info">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
				</form>
			</header>
		</div>
      
	
	</body>

</html>
