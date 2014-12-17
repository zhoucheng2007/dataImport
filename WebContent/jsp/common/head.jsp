<%
    String ctx = request.getContextPath();
    String ctxStatic = request.getContextPath()+"/skin";
    request.setAttribute("ctxStatic",ctxStatic);
    request.setAttribute("ctx",ctx);
%>
<meta http-equiv="X-UA-Compatible" content="IE=EDGE;chrome=1" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<meta http-equiv="CacheCache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link rel="shortcut icon" href="${ctxStatic}/img/favicon.ico" />
<script type="text/javascript" charset="utf-8">
    var ctx = "${ctx}";
    var ctxStatic = "${ctxStatic}";
    isSuperUser = function() {
        var isSuperUser = "${sessionInfo.superUser}";
        if(isSuperUser == "true"){
            return true;
        }
        return false;
    }
</script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/default.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/form_style.css" />
<script type="text/javascript" src="${ctxStatic}/js/jquery/jquery-1.10.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery/jquery-migrate-1.2.1.min.js" charset="utf-8"></script>
<%-- jQuery Cookie插件 --%>
<script type="text/javascript" src="${ctxStatic}/js/jquery/jquery.cookie-min.js" charset="utf-8"></script>
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${ctxStatic}/js/easyui-1.4/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/easyui-1.4/extend/icon/easyui-icon.css" />
<script type="text/javascript" src="${ctxStatic}/js/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/easyui-1.4/jquery.easyui.mine.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/easyui-1.4/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/easyui-1.4/extend/my97/my97.css" />
<script type="text/javascript" src="${ctxStatic}/js/easyui-1.4/extend/my97/jquery.easyui.my97.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/easyui-1.4/portal/portal.css">
<script type="text/javascript" src="${ctxStatic}/js/easyui-1.4/portal/jquery.portal-min.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/easyui-1.4/extend/icon/eu-icon.css" />
<script type="text/javascript" src="${ctxStatic}/js/jquery/jquery-extend.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/easyui-1.4/extend/js/easyui-extend.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/easyui-1.4/extend/js/validatebox-extend.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/easyui-1.4/extend/js/validatebox-ajax.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/util.js" charset="utf-8"></script>
