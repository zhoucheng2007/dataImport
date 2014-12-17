$(document).ready(function(){
	
	//增加返回首页按钮
	$("#goHome").live('click',function(){
		window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forFrameDp","_self");
	});
	
	//返回首页
	$(".goBackHomePage").click(function(){
		window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forFrameDp","_self");
	});
	//返回专卖首页
	$(".goBackZhuanMaiPage").click(function(){
		window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forZhuanMai&target=dp&type=noanimal","_self");
	});
	//返回营销首页
	$(".goBackYingXiaoPage").click(function(){
		window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forMainDp&type=noanimal","_self");
	});
	//打开专卖管理系统
	$("#zhuanMaiSys").click(function(){
		//window.open("http://zm.bjtobacco.com","_blank");
		window.open("http://10.15.6.91:9080/zm","_blank");
		
	});
	//打开辅助决策系统
	$("#fuzhuJCSys").click(function(){
		window.open("http://10.15.6.35:9080/fzjc/login.jsp","_blank");
	});
	//打开物流系统
	$("#wuliuSys").click(function(){
		window.open("http://10.15.57.78:83/bjwl/logist/monitor/dp_monitormain_sso.jsp?isplay=1&uid=88","_blank");
		//window.open('http://10.15.57.78:83/bjwl',"_blank");
	});
	
	
	//帮助指标说明
	if($("#menuBtin-container").length>0){
		var $helpBtnObj = $("<div></div>").attr("id","helpBtn").attr("title","业务标准");
		$("#menuBtin-container").append($helpBtnObj);
		var $pubMeasureObj = $("<div></div>").attr("id","pub-measure-help-container");
		$pubMeasureObj.html("<iframe id=\"measureIframePage\" src=\"/datacenter/jsp/com/v6/screen/bi/project/bjycyth/frame/measureHelp.html\" scrolling=\"no\" border=\"0\" frameBorder=\"0\" style=\"width:100%;height:716px;\" allowTransparency=\"true\"></iframe>");
		$pubMeasureObj.appendTo($("#main-page"));
		
		$("#helpBtn").live("click",function(){
			if($pubMeasureObj.hasClass("opened")){
				$pubMeasureObj.removeClass("opened").animate({width:0,left:2460},1000);
			}else{
				$pubMeasureObj.addClass("opened").animate({width:747,left:1713},1000);
			}
		});
	}	
});
//返回首页
function goHomePage(){
	window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forMainDp&type=noanimal","_self");
}

//返回专卖首页
function goZhuanMaiHome(){
	window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forZhuanMai&target=dp&type=noanimal","_self");
}

//打开专卖系统
function openZhuanMaiSys(){
	window.open("http://10.15.6.91:9080/zm","_blank");
}

//打开辅助决策系统
function openFuzhuJCSys(){
	window.open("http://10.15.6.35:9080/fzjc/login.jsp","_blank");
}

//打开物流系统
function openWuliuSys(){
	//window.open('http://10.15.57.78:83/bjwl',"_blank");
	window.open("http://10.15.57.78:83/bjwl/logist/monitor/dp_monitormain_sso.jsp?isplay=1&uid=88","_blank");
}

//打开经济运行-销售分析
function openSaleAnalysis(){
	window.open("/datacenter/bi/bjycMainCmd.cmd?method=forSaleAnalysis&target=dp","_self");
}

//打开经济运行-外部数据
function openMarkAnalysis(){
	window.open("/datacenter/bi/bjycMainMarkCmd.cmd?method=query&target=dp","_self");
}


//返回
//function goBackPage(name){
//	var url = "";
//	if(name=="gongyinglian"){
//		url = "/datacenter/bi/bjycFrameCmd.cmd?method=forGongYingLian";
//	}else if(name=="zhuanmaiguanli"){
//		url = "/datacenter/bi/bjycFrameCmd.cmd?method=forZhuanMai";
//	}else if(name=="juanyanyingxiao"){
//		url = "/datacenter/bi/bjycFrameCmd.cmd?method=forYingXiao";
//	}
	
//	if(url!=""){
//		window.open(url,"_self");
//	}
//}

//$(document).ready(function(){
//	var parmByGet=getParmFormUrl();
//	var url=parmByGet["goback"];
//	if(url!=undefined){
//		setTimeout(function(){
//			$("#goBack").removeAttr("onclick").click(function(){
//					window.location.href=url;
//			})
////		},500)
//	}
//})

//function getParmFormUrl() {
//		var url = location.search; // 获取url中"?"符后的字串
//		var theRequest = new Object();
//		if (url.indexOf("?") != -1) {
//			var str = url.substr(1);
//			strs = str.split("&");
//			for ( var i = 0; i < strs.length; i++) {
//				theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
//			}
//		}
//		return theRequest;
//	}

//function setCookie(name,value)
//{
//	var Days = 30;
//	var exp = new Date(); 
//	exp.setTime(exp.getTime() + Days*24*60*60*1000);
//	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";
//}
//function getCookie(name)
//{
//	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
//	if(arr=document.cookie.match(reg)) return unescape(arr[2]);
//	else return null;
//}

$.post("/v6/jsp/keepsession.jsp")