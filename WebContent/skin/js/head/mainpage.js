var version=getCookie("version");
if(version=="city"){
	window.location.href="/portal/portal/sAppPageInitCmd.cmd?method=query";
}
function getCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]); 
    else 
        return null; 
}