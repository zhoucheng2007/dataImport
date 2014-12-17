$(document).ready(function(){
	initBanner();

	//banner条
	$("#banner-content").roundabout();
	$(".banner-btn").click(function(){
		$(".banner-btn").removeClass("active");
		$(this).addClass("active");
		var refId=$(this).attr("refId");
		$("#"+refId).click();
	})
	///$(".news-list-container").css("height","175px")
	$("#RssNew").mouseout(function(){
		$('#RssNew').css("display","none");
	})
	$("#RssNew").mouseover(function(){
		$('#RssNew').css("display","block");
	})
	$("#todaySchedule").mouseout(function(){
		//$('#todaySchedule').css("display","none");
	})
	$("#todaySchedule").mouseover(function(){
		$('#todaySchedule').css("display","block");
	})
	$("#orgselect").change(function(){
		onselect();
	})

	$("#bslan").click(function(){
		$("#banner-content li img").attr("src","/d3/skins/default/css/cq/imgs/banner-box.png");		 
	})
	$("#bshong").click(function(){
		$("#banner-content li img").attr("src","/d3/skins/default/css/cq/imgsr/banner-box.png");		 
	})
	$("#bslu").click(function(){
		$("#banner-content li img").attr("src","/d3/skins/default/css/cq/imgsg/banner-box.png");		 
	})
})

///子公司下拉
function initCompany(){
	$.ajax({
		  type: "post",
		  url: "cqpageinitcmd.cmd?method=queryCompany",
		  beforeSend: function(XMLHttpRequest){
			$("#orgselect").html("<option selected=\"selected\" value=\"\">重庆市公司</option>");
		  },
		  success: function(data, textStatus){
	
			 var jsonObj=eval("("+data+")");
			 $.each(jsonObj,function(i){
				 $("<option></option>")
					.attr("value",jsonObj[i].companyId)
					.text(jsonObj[i].companyName)
				    .appendTo("#orgselect")
			 })

			 
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }
	  });
}
function initImgPath() {
    var styleName = readCookie('style');
    var imgpath = "/d3/skins/default/css/cq/imgs/banner-box.png";
    if (styleName == 'css1') {//hong
        imgpath="/d3/skins/default/css/cq/imgsr/banner-box.png";
    } else if (styleName == 'css2') {//lu
        imgpath = "/d3/skins/default/css/cq/imgsg/banner-box.png";
    } else {//lan
        imgpath = "/d3/skins/default/css/cq/imgs/banner-box.png";
    }
    return imgpath;
 
}
//barnner指标信息
function initMeasure(){
    var testId= $("#orgselect").val();
	$.ajax({
		  type: "post",
		  data: "companyId="+testId+"&measurId=CG_S0200,CG_S01211,CG_S0217,CG_S0100,CG_S0220,TB_F2200,TB_F2600,TB_F0900,FI_I0400,FI_I1001,FI_Z0210,ST_0100,ST_0200,ST_0300,ST_0600,ST_0700",
		  url: "cqpageinitcmd.cmd?method=queryMeasure",
		  beforeSend: function(XMLHttpRequest){
		  $("#juanyan").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">卷烟销售</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>年度</td><td>年同比</td></tr><tr id=\"CG_S0100\"><td>卷烟销售数量</td></tr><tr id=\"CG_S0200\"><td>卷烟销售收入</td></tr><tr id=\"CG_S0220\"><td>单箱销售收入</td></tr><tr id=\"CG_S01211\"><td>重点品牌销量</td></tr><tr id=\"CG_S0217\"><td>低焦油销量</td></tr></table></div>");
			$("#yanye").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">烟叶生产</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>年度</td><td>年同比</td></tr><tr id=\"TB_F2200\"><td>收购计划完成率</td></tr><tr id=\"TB_F2600\"><td>烤烟上中等烟比例</td></tr><tr id=\"TB_F0200\"><td>种植面积</td></tr><tr id=\"TB_F0900yx\"><td>烟叶收购量</td></tr></table></div>");
			$("#caiwu").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">财务指标</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>年度</td><td>年同比</td></tr><tr id=\"FI_I1001\"><td>销售利润率</td></tr><tr id=\"FI_Z0210\"><td>三项费用率</td></tr><tr id=\"FI_I0400\"><td>三项费用</td></tr></table></div>");
			$("#lingshou").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">零售终端</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>年度</td></tr><tr id=\"ST_0100\"><td>价格到位率</td></tr><tr id=\"ST_0200\"><td>社会库存</td></tr><tr id=\"ST_0300\"><td>毛利率</td></tr><tr id=\"ST_0600\"><td>装机户数</td></tr><tr id=\"ST_0700\"><td>会员户数</td></tr></table></div>");
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
			 $.each(jsonObj,function(i){
			 	var html="";
			   if(jsonObj[i].measureName=="ST_0100"||jsonObj[i].measureName=="ST_0200"||jsonObj[i].measureName=="ST_0300"||jsonObj[i].measureName=="ST_0600"||jsonObj[i].measureName=="ST_0700"){
               html="<td>"+jsonObj[i].measureMonth+"</td><td>"+jsonObj[i].measureYear+"</td>";
               } else{
               html="<td>"+jsonObj[i].measureMonth+"</td><td>"+jsonObj[i].monthPortion+"</td><td>"+jsonObj[i].measureYear+"</td><td>"+jsonObj[i].yearPortion+"</td>";
                }
			   if(jsonObj[i].measureName=="TB_F0900")
			   {
				var id="#"+jsonObj[i].measureName+"yx";
				
			   }
			   else{var id="#"+jsonObj[i].measureName;}
				
				var innerHtml=$(id).html()+html;
				$(id).html(innerHtml);
			 })
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){
		  }
	  });
}
//barnner动态指标
function initDyMeasure(){
	var testId= $("#orgselect").val();
	$.ajax({
		  type: "post",
		  data: "companyId="+testId,
		  url: "cqpageinitcmd.cmd?method=queryDyMeasure",
		  beforeSend: function(XMLHttpRequest){
		  $("#dongtai").html("<img src=\""+initImgPath()+"\"/><div class=\"data-container\"><div class=\"data-title\">动态信息</div><table class=\"banner-table\"><tr><td>指标名称</td><td>昨日</td><td>本月累计</td><td>累计同比</td></tr><tr id=\"CG_S0010\"><td>卷烟销量</td></tr><tr id=\"CG_S0020\"><td>销售收入</td></tr><tr id=\"CG_S0020dx\"><td>单箱收入</td></tr><tr id=\"CG_S0030\"><td>重点品牌</td></tr><tr id=\"TB_F0900\"><td>烟叶收购量</td></tr></table></div>");
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
			 $.each(jsonObj,function(i){
				
			 	var html="<td>"+isNull(jsonObj[i].measureValue)+"</td><td>"+isNull(jsonObj[i].measureValueAdd)+"</td><td>"+isNull(jsonObj[i].sameTimeAd)+"</td><td>";
				var id="#"+jsonObj[i].measureId;
				var innerHtml=$(id).html()+html;
				$(id).html(innerHtml);
			 })
		  },
		  complete: function(XMLHttpRequest, textStatus){  
		  },
		  error: function(){
		  }
	  });
}
function isNull(data){
	if(data){
		return data;
		}
	else{
	return 0	;
	}
	

}



function initBanner(){
	$.ajax({
		  type: "post",
		  url: "cqpageinitcmd.cmd?method=queryControlUser",
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  success: function(data, textStatus){
		  var jsonObj=eval("("+data+")");
		  var isUserInTable=jsonObj.isUserInTable;
		  if(isUserInTable==0){
		  $("#banner").css("display","none");
		  $("#bannerqywh").css("display","block");
		  $("#bannerqywh").html("<object id=\"movie\" classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" width=\"961\" height=\"343\" align=\"middle\"> <param name=\"allowScriptAccess\" value=\"sameDomain\" /><param name=\"movie\" value=\"/d3/skins/default/css/cq/imgs/banner.swf\"/> <param name=\"menu\" value=\"false\" /> <param name=\"quality\" value=\"high\" /> <param name=\"wmode\" value=\"transparent\"><param name=\"bgcolor\" value=\"#ffcc33\" /> <embed src=\"/d3/skins/default/css/cq/imgs/banner.swf\" menu=\"false\" quality=\"high\" bgcolor=\"#ffcc33\" width=\"961\" height=\"343\" name=\"movie\" align=\"middle\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" /></object>");
		  }else{
		  initMeasure();
		  initDyMeasure();
		  initCompany();
		  $("#banner").css("display","block");
		  $("#bannerqywh").css("display","none");
		  }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }
	  });
}



function onselect(){
	initMeasure();
	initDyMeasure();
}
function showhysitlink(divid){
document.getElementById(divid).style.display="block";
}
function dis_showhysitlink(divid){
document.getElementById(divid).style.display="none";
}
function RssNewSettings(){
$('#RssNew').css("display","block");
var rssUrl= $.cookie('RssNew_cookie');
if(rssUrl==null){
rssUrl="http://news.163.com/special/00011K6L/rss_newstop.xml";
}
document.getElementById(rssUrl).checked="checked";
}
function getRadioValue(name){
    var radios=document.getElementsByName(name);
    var len=radios.length;
    var selectInt= $.cookie('RssNew_cookie');
    for(var i=0;i<len;i++){
     if(radios[i].checked==true){
      selectInt=radios[i].value;
     }
    }
    return selectInt;
}
function returnvalue(){
	var vat=getRadioValue("rss");
	$.cookie('RssNew_cookie',vat,{expires:365});
	$('#RssNew').css("display","none");
}


