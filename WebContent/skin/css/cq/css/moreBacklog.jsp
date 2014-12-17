<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%
	String json = (String)request.getAttribute("moreBacklog");
		//获取sid   
	String sid= (String)request.getAttribute("sid");
	String vcate = (String)request.getAttribute("vcate");
//	out.print("vcate " + vcate);
	
	/**日程*/
	String userId =(String)request.getAttribute("id");
    String userName =(String)request.getAttribute("name");
    //out.print("userId " + userId);
    //out.print("userName " + userName);
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待办任务</title>
<script type="text/javascript" src="/skin/js/jquery.js"></script>
<script type="text/javascript" src="/skin/js/headindex.js"></script>
<script type="text/javascript">initHeadBar(false)</script>
<script type='text/javascript' src='<%=request.getContextPath()%>/skin/js/cq/cncal.js'></script>
<style type="text/css"></style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/picnews/nivo.slider.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/daiban.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/picnews/default/default.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/dhtmlxschedulerIndex.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/zTreeStyle/zTreeStyle.css">

<link id="changeStyle" title=css3  href="<%=request.getContextPath()%>/skin/css/cq/css/change3.css" rel="stylesheet" type="text/css" />
<link id="mainStyle" href="<%=request.getContextPath()%>/skin/css/cq/css/main.css" rel="stylesheet" type="text/css" />
<link  href="<%=request.getContextPath()%>/skin/css/cq/css/head.css" rel="stylesheet" type="text/css" />
<link id="bannerStyle" href="<%=request.getContextPath()%>/skin/css/cq/css/banner.css" rel="stylesheet" type="text/css" />
	

<script type="text/javascript" src="jquery.js"></script>
<script>
	$(document).ready(function(){
		var vcate = $("#vcate").val();
		$(".daiban_serch_sel").val(vcate);
		initDaiBan();
	})
</script>
</head>
<body>
<jsp:include page="../headnew.jsp" flush="true" />
<div id="headholder"></div>
<input type="hidden" id="userId" size=10 value="<%=userId%>" />
<input type="hidden" id="userName" size=10 value="<%=userName%>" />
<input type="hidden" id="sid" size=10 value="<%=userId%>" />
<input type="hidden" id="vcate" size=10 value="<%=vcate%>" />
<div class="daiban_div">
<div class="daiban_title">
</div>
<div class="daiban_title_search">
<div class="daiban_serch_div">

<select class="daiban_serch_sel" id="sel" onchange="serchbykeys()">
		<option value="all">&nbsp;&nbsp;&nbsp;全部 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
		<option value="oa">&nbsp;&nbsp;OA办公</option>
		<option value="schedule">&nbsp;日程管理</option>
		<option value="sns">&nbsp;协作平台</option>
		<option value="zr">&nbsp;运维管理</option>
		<option value="cw">&nbsp;财务管理</option>
</select>

<input class="daiban_search_input" type="text" id="keys" />
<div class="daiban_search_btn" onclick="serchbykeys()"></div>
</div>
</div>
<div id="showdata">

</div>

</div>
</body>
<script type="text/javascript">
function serchbykeys(){
var vkeys=$("#keys").val();
var vsel=$("#sel").val();
var str="&keys="+encodeURI(encodeURI(vkeys))+"&cate="+vsel;
showdaibansearch(str);
}

//待办
function initDaiBan(){
	
$.ajax({
	  type: "post",
	  url: "pwscq.cmd?method=queryWorkForV6",
	  beforeSend: function(XMLHttpRequest){	
		 
	  },
	  success: function(data, textStatus){
		  
		var jsonObj=eval("("+data+")");
		var len = jsonObj.length;
		var showdata=document.getElementById('showdata');
		var showdatacontent="";
		//alert("jsonObj.length="+jsonObj.length);
		if(len==0){
		 showdatacontent+='<div>没有此项目的数据!!!</div>';
		}
		for(var i=0;i<len;i++){
		var nameimg=jsonObj[i].backlogType;


		showdatacontent+='<div  class="daiban_row"><div class="daiban_left"><div class="daiban_left_img_'+nameimg+'"></div><div class="daiban_left_imgname"><span class="daibanname">'+writes(nameimg)+'</span></div></div><div class="daiban_center"><div class="daiban_quanquan"></div><div class="daiban_shuxian"></div></div><div class="daiban_right"><div class="daiban_right_left"></div><div class="daiban_right_center"><div class="daiban_content">    <div class="daiban_content_title"><span>标题：</span><a href="#"  onclick="showclick(\''+jsonObj[i].backlogUrl+'\')" >'+jsonObj[i].backlogTitle+'</a></div><div class="daiban_content_time"><span>时间：</span></span>'+jsonObj[i].backlogDate+'</span></div> </div></div><div class="daiban_right_right"><div class="daiban_right_right_btn" onclick="showclick(\''+jsonObj[i].backlogUrl+'\')"></div></div></div></div>';

		}
		showdata.innerHTML=showdatacontent;
	  },
	  complete: function(XMLHttpRequest, textStatus){
	  },
	  error: function(){
		
	  }
  });
}

 
//根据Type的值返回具体名称
function writes(Type){
//alert(Type);
	if(Type=="oa"){
		return "OA协同办公";
	}else if(Type=="sns"){
		return "团队空间";
	}else if(Type=="schedule"){
		return "日程管理";
	}else if(Type=="zr"){
		return "运维管理";
	}else if(Type=="cw"){
		return "财务管理";
	}else{
		return Type;
	}
}
function showclick(str){
	window.open(str,'_blank');
	}
	
function showdaibansearch(str){
	//var daibanUrl="http://10.11.2.136/d3/cqpageinitcmd.cmd";
	var sid = document.getElementById("sid").value;
	//var url="http://10.11.2.136/d3/jsp/com/lc/d3/cq/home/moreBacklog.jsp?userId="+sid+"&_url="+daibanUrl;
	//http://10.11.2.23/portal/portal/pwscq.cmd?method=moreBacklog
	var url="http://10.11.2.23/portal/portal/pwscq.cmd?method=queryDaibanByKeys&sid="+sid+str;
	window.open(url,"_self");
}


</script>
</html>