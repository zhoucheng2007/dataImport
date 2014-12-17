initPicNews();

$(document).ready(function()  {
 initWordNews();
 initDaiBan(); 
 initAnnounce();  
 initSitlink(); 
 initCommSys(); 
 initSchedule();
 initMoreTasks();
 initTopic();
 initKgxd();
 
 var initUrl = "http://10.192.0.205/datacenter/bi/bi/qhMeasureCmd.cmd?method=pageInit&measureId=";
    $(function(){
    $("#yingxiao").click();
    document.getElementById("zhibiaoiframe").src=initUrl+"CG_P0100";
  });
		$("#yingxiao").click(function(){

		$(".tab10").removeClass("active");
		$(this).addClass("active");
		document.getElementById("zhibiaoiframe").src=initUrl+"CG_P0100";

	})
	 	$("#wangjian").click(function(){
		 $(".tab10").removeClass("active");
		$(this).addClass("active");
		document.getElementById("zhibiaoiframe").src=initUrl+"RM_C0500";

	})
		$("#caiwu").click(function(){
		$(".tab10").removeClass("active");
		$(this).addClass("active");
		document.getElementById("zhibiaoiframe").src=initUrl+"FI_I0430";

	})
		$("#wuliu").click(function(){
		$(".tab10").removeClass("active");
		$(this).addClass("active");
		document.getElementById("zhibiaoiframe").src=initUrl+"CG_W0210";
	
	})
	  	$("#duibiao").click(function(){
		$(".tab10").removeClass("active");
		$(this).addClass("active");
		document.getElementById("zhibiaoiframe").src=initUrl+"ME_03100";

	})
		$("#neiguan").click(function(){
		$(".tab10").removeClass("active");
		$(this).addClass("active");
		document.getElementById("zhibiaoiframe").src=initUrl+"HR_N0200";
	
	})
	
		$("#renzi").click(function(){
	    $(".tab10").removeClass("active");
		$(this).addClass("active");
		document.getElementById("zhibiaoiframe").src=initUrl+"HR_E0100"
	
	})

 	$(".tab1").mouseover(function(){
  $(".portalicon").css("background","url(/portal/skin/img/qh/anniu1.png)  16px 8px no-repeat");
  	$(".tab1").css("color","#000");
  	$(".tab2").css("color","#fff");
  	$(".portaltab").css("display","block");
  	$(".sys-list").css("display","none");
});
 	$(".tab2").mouseover(function(){
  $(".portalicon").css("background","url(/portal/skin/img/qh/anniu2.png)  16px 8px no-repeat");
    $(".tab1").css("color","#fff");
  	$(".tab2").css("color","#000");
  	$(".portaltab").css("display","none");
  	$(".sys-list").css("display","block");
});
 $(".lftTxt1").click(function(){
		window.open("/portal/AuthenService" + "?USERID=" + v6head.userId + "&APP=dcwork&RESOURCE=http://10.192.0.33:80/rone/login?&HTTPCLIENT=1&IsAuthenNew=");
	});
 $(".lftTxt3").click(function(){
		window.open("/portal/AuthenService?USERID="+v6head.userId+"&APP=v3&RESOURCE=http://10.192.0.21/v3/menu.cmd&HTTPCLIENT=1");
	});
	$(".lftTxt4").click(function(){
		window.open("/portal/AuthenService?USERID="+v6head.userId+"&APP=ris&RESOURCE=http://10.192.0.23:9081/v3/menu.cmd&HTTPCLIENT=1");
	});
	$(".lftTxt2").click(function(){
		window.open("/portal/AuthenService" + "?USERID=" + v6head.userId + "&APP=ncsso-qinghai&RESOURCE=http://10.192.0.30:80/ncsso-qinghai/SSOLogin.do&HTTPCLIENT=1&IsAuthenNew=");
	});
	$(".lftTxt6").click(function(){
		window.open("/portal/AuthenService" + "?USERID=" + v6head.userId + "&APP=ldm&sampleSSO=1");
	});
	$(".lftTxt5").click(function(){
		window.open("/portal/AuthenService" + "?USERID=" + v6head.userId + "&APP=cgs&RESOURCE=http://10.192.0.6:9080/TFM/main.jsp&HTTPCLIENT=1&IsAuthenNew=");
	});
	$(".lftTxt7").click(function(){
		window.open("/portal/AuthenService" + "?USERID=" + v6head.userId + "&APP=HR&RESOURCE=http://10.192.0.37:8080/login.jsp&HTTPCLIENT=1&IsAuthenNew=");
	});
});
Date.prototype.format = function(format)
{
 var o = {
 "M+" : this.getMonth()+1, //month
 "d+" : this.getDate(),    //day
 "h+" : this.getHours(),   //hour
 "m+" : this.getMinutes(), //minute
 "s+" : this.getSeconds(), //second
 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
 "S" : this.getMilliseconds() //millisecond
 }
 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
 (this.getFullYear()+"").substr(4 - RegExp.$1.length));
 for(var k in o)if(new RegExp("("+ k +")").test(format))
 format = format.replace(RegExp.$1,
 RegExp.$1.length==1 ? o[k] :
 ("00"+ o[k]).substr((""+ o[k]).length));
 return format;
}
initAtInfo();
//文字新闻
function initWordNews(){
	$.ajax({
		  type: "post",
		  url: "pwsqh.cmd?method=queryWordNews",
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
				 //$("#firstNewTitle").attr("href",jsonObj[0].newUrl).text(jsonObj[0].newTitle);
			 //$("#firstNewContent").attr("href",jsonObj[0].newUrl).html("<p>"+jsonObj[0].firstNewContent.substr(0,85)+"..."+"</p>");
			 //$("#firstNewContent").attr("target","_blank");
			 //$("#firstNewTitle").attr("target","_blank");
			 var len = jsonObj.length;
			 for(var i=0;i<7;i++){
			 	if(jsonObj[i].newTitle.length==0){}else{
				 $("<li></li>")
				 .append(
					$("<a></a>").attr("href",jsonObj[i].newUrl).text(jsonObj[i].newTitle)
								.attr("target","_blank")
								.attr("title",jsonObj[i].newTitle)
				 )
				 
				// .append(
				//	$("<span></span>").text(jsonObj[i].newDate)
				//)
				 .appendTo("#wordNews");
				}
			 }

		  },
		  complete: function(XMLHttpRequest, textStatus){
			  ///$('#slider').nivoSlider();
		  },
		  error: function(){
			
		  }
	  });
}
//会议专题
function initMeeting(){
	}
	//待办任务
	function initDaiBan(){
		//alert("daiban");
	$.ajax({
		  type: "post",
		  url: "pwsqh.cmd?method=queryDaiBan",
		  beforeSend: function(XMLHttpRequest){	  
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval(data);
			 //alert(jsonObj);
			 var len = jsonObj.length;
			 for(var i=0;i<7;i++){
				 $("<li></li>")
				 .append(
				 $("<a></a>")
					      .attr("href",jsonObj[i].workUrl).text(jsonObj[i].workTitle)
						  .attr("target","_blank")
						  .attr("title",jsonObj[i].workTitle)
				 )
      .appendTo("#daiban");
			 }

		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){
			
		  }
	  });
}
//通知公告
function initAnnounce(){
	$.ajax({
		  type: "post",
		  url: "pwsqh.cmd?method=queryNotice",
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval("("+data+")");
			 var len = jsonObj.length;
			 for(var i=0;i<8;i++){
			 	if(jsonObj[i].noticeTitle.length==0){}else{
				 $("<li></li>")
				 .append(
					$("<a></a>").attr("href",jsonObj[i].noticeUrl).text(jsonObj[i].noticeTitle)
								.attr("target","_blank")
								.attr("title",jsonObj[i].noticeTitle)
				 )
				// .append(
				//	$("<span></span>").text(jsonObj[i].newDate)
				//)
				 .appendTo("#announce");
			 }
      }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  ///$('#slider').nivoSlider();
		  },
		  error: function(){
			
		  }
	  });
}





//行业连接
function initSitlink(){
var	inhtml="<TABLE border=0 cellSpacing=0 cellPadding=0 width=250 height=100><TBODY><TR align=middle>";
	inhtml+="<TD><A href=\"http://10.1.0.7/application/login.jsp\" target=_blank>国家局内网</A></TD>";
	inhtml+="<TD><A href=\"http://www.tobacco.gov.cn\" target=_blank>国家局外网</A></TD>";
	inhtml+="<TD><A href=\"http://qh.tobacco.com.cn\" target=_blank>省局外网 </A></TD></TR>";
	inhtml+="<TR align=middle>";
	inhtml+="<TD><A href=\"http://www.eastobacco.com\" target=_blank>东方烟草报</A></TD>";
	inhtml+="<TD><A href=\"http://www.echinatobacco.com\" target=_blank>中国烟草资讯网</A></TD>";
	inhtml+="<TD><A href=\"http://www.tobacco.org.cn\" target=_blank>中国烟草学会</A></TD></TR></TBODY></TABLE>";
$('#hysitlinktable').html(inhtml);
var jcinhtml2="<TABLE id=mytable border=0 cellSpacing=0 cellPadding=0 width=410 height=100><TBODY><TR align=middle><TD>"
+"<A href=\"#\" target=_blank>西宁市公司</A></TD><TD><A href=\"#\" target=_blank>海东市公司</A></TD>"
+"<TD><A href=\"#\" target=_blank>海西市公司</A></TD><TD><A href=\"#\" target=_blank>海南市公司</A></TD>"
+"<TD><A href=\"#\" target=_blank>海北市公司</A></TD></TR><TR  align=middle><TD><A href=\"#\" target=_blank>格尔木市公司</A></TD>"
+"<TD><A href=\"#\" target=_blank>黄南市公司</A></TD><TD><A href=\"#\" target=_blank>玉树市公司</A></TD>"
+"<TD><A href=\"#\" target=_blank>果洛市公司</A></TD></TR></TBODY></TABLE>";
$('#jcsitlinktable').html(jcinhtml2);
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


/*常用*/
function initCommSys(){
	var html='';
	
	$.ajax({
		  type: "post",
		  url: "pwsbase.cmd?method=commSystem",
		  beforeSend: function(XMLHttpRequest){
			  $(".sys-list").html("");
		  },
		  success: function(data, textStatus){
			 //alert(data);
			  var jsonObj=eval(data);
			 //alert(jsonObj);
			  var len = jsonObj.length;
			 // alert(len);
			  if(jsonObj.length==0) retrun;
			 $.each(jsonObj,function(i){
				 	if(i>4) retrun;
				 	//alert(i)
				 $("<li></li>")
				 .click(function(){
					var href="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&appType=mymenu&menuId="+jsonObj[i].MENU_ID+"&appSSO="+jsonObj[i].APP_TYPE;
					window.open(href,"_self");
				 })
			      .hover(function() {
			    	$('img',this).attr("src","/skin/pws/img/3-2.png");
                  },function() {
                	  $('img',this).attr("src","/skin/pws/img/3.png");
                	   })
				 .append(
				 $("<a></a>")
				 .append(
					$("<img/>")
					  .attr({"src":"/skin/pws/img/3.png"})
					  
				 )
				 .append(
					$("<span></span>")
					.text(jsonObj[i].MENU_NAME)
				 )
				 )
				 .appendTo(".sys-list");
			 });
		  },
		  complete: function(XMLHttpRequest, textStatus){

		  },
		  error: function(){			
		  }
	  });
	
}

//日程
function initSchedule(){
	 var mydate=new Date();
	
	$.ajax({
		type:"post",
		url:"ScheduleCmd.cmd?method=querySchedule&START="+mydate.format("yyyy-MM-dd")+"&END="+mydate.format("yyyy-MM-dd"),
		beforeSend:function(XMLHttpRequest){
			
		},
		success:function(data,textStatus){
			
			var jsonObj	= eval(data);
			
			var len = jsonObj.length;
           
			$("<span></span>").text(len)
			.appendTo(".schedulenum");
			
		},
		complete:function(XMLHttpRequest,textStatus){	
		},
		error:function(){
			
		}
	});
}
//daibanmore
function initMoreTasks(){
	$.ajax({
		type:"post",
		url:"pwsqh.cmd?method=queryDaiBan",
		beforeSend:function(XMLHttpRequest){
			
		},
		success:function(data,textStatus){
			var jsonObj	=eval("("+data+")");
			var len = jsonObj.length;
			$("<span></span>").text(len)
			.appendTo(".daibannum");
			
		},
		complete:function(XMLHttpRequest,textStatus){	
		},
		error:function(){
			
		}
	});
}
var pic_1,pic_1_s,pic_1_title,pic_1_url,pic_2,pic_2_s,pic_2_title,pic_2_url,pic_3,pic_3_s,pic_3_title,pic_3_url,pic_4,pic_4_s,pic_4_title,pic_4_url,pic_5,pic_5_s,pic_5_title,pic_5_url,pic_6,pic_6_s,pic_6_title,pic_6_url;
 //图片新闻
function initPicNews(){
	$.ajax({
		  type: "post",
		  url: "pwsqh.cmd?method=queryPicNews",
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
		
 pic_1 =jsonObj[0].newPic;

 pic_1_s = "/portal/skin/img/qh/num1.png";
 pic_1_title =jsonObj[0].newTitle;
 pic_1_url =jsonObj[0].newUrl;
 pic_2 =jsonObj[1].newPic;
 pic_2_s = "/portal/skin/img/qh/num2.png";
 pic_2_title =jsonObj[1].newTitle;
 //alert(pic_1_title+pic_2_title);
 pic_2_url = jsonObj[1].newUrl;
 pic_3 =jsonObj[2].newPic;
 pic_3_s = "/portal/skin/img/qh/num3.png";
 pic_3_title =jsonObj[2].newTitle;
 pic_3_url = jsonObj[2].newUrl;
 pic_4 =jsonObj[3].newPic;
 pic_4_s = "/portal/skin/img/qh/num4.png";
 pic_4_title =jsonObj[3].newTitle;
 pic_4_url =jsonObj[3].newUrl;
 pic_5 =jsonObj[4].newPic;
 pic_5_s = "/portal/skin/img/qh/num5.png";
 pic_5_title =jsonObj[4].newTitle;
 pic_5_url =jsonObj[4].newUrl;
 pic_6 =jsonObj[5].newPic;
 pic_6_s = "/portal/skin/img/qh/num6.png";
 pic_6_title = jsonObj[5].newTitle;
 pic_6_url =jsonObj[5].newUrl;

var outtxt="<div id=\"currentPic\" style=\"width:324px;height:156px;margin:2px 2px 2px 2px;\">";
outtxt += "<a href=\""+pic_1_url+"\"><img src = \""+pic_1+"\" border=0 id=\"current_img\"  width=322 height = 156 style=\"filter:revealTrans(Transition=10,Duration=2)\" /></a>";

outtxt += "<div id=\"listpic\" style=\"width:200px;text-align:center;margin-top:-17px;margin-left:142px;height:12px;\">";
outtxt += "<image class=\"pic_1_s\" src=\""+pic_1_s+"\"  width=25 height=12 id=\"s_pic_1\" onmouseover=\"flashthis(this,1);\" />";
outtxt += "<image class=\"pic_2_s\" src=\""+pic_2_s+"\"  width=25 height=12 id=\"s_pic_2\" onmouseover=\"flashthis(this,2);\" />";
outtxt += "<image class=\"pic_3_s\" src=\""+pic_3_s+"\"  width=25 height=12 id=\"s_pic_3\" onmouseover=\"flashthis(this,3);\" />";
outtxt += "<image class=\"pic_4_s\" src=\""+pic_4_s+"\"  width=25 height=12 id=\"s_pic_4\" onmouseover=\"flashthis(this,4);\" />";
outtxt += "<image class=\"pic_5_s\" src=\""+pic_5_s+"\"  width=25 height=12 id=\"s_pic_5\" onmouseover=\"flashthis(this,5);\" />";
outtxt += "<image class=\"pic_6_s\" src=\""+pic_6_s+"\"  width=25 height=12 id=\"s_pic_6\" onmouseover=\"flashthis(this,6);\" />";
outtxt += "</div>";
outtxt += "<a  href=\""+pic_1_url+"\"><p id=\"c_f_title\" class=\"zhaiyao\" title=\""+pic_1_title+"\" style=\"width:328px;line-height:30px;text-align:center;height:30px;color:#000;margin-top:2px;\" >"+pic_1_title+"</p></a>"
outtxt += "</div>";


document.getElementById("flashpic").innerHTML = outtxt;

		  },
		  complete: function(XMLHttpRequest, textStatus){
			 
		  },
		  error: function(){
			
		  }
	  });
}
/*热门话题*/
function initTopic(){
	//alert(1000);
	$.ajax({
		  type: "post",
		  url: "/sns/index.php?app=api&mod=Statuses&act=getHotTopic&count=6&type=auto&sid=MTIzNDU2Nzg5X1NVUEVSQURNSU5AMTIzNDU2Nzg5MA==",
		 
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  success: function(data, textStatus){
			// alert(data);
			var jsonObj=eval(data);
			 var len = jsonObj.length;
			 for(var i=0;i<7;i++){
			    var title=jsonObj[i].name;
			    var m=encodeURI(title);
			    m=encodeURI(m);
				 $("<li></li>").append(
					$("<a></a>").attr("href","/sns/index.php?app=home&mod=User&act=topics&k="+m).text(title)
					.attr("target","_self")
				)
				 .appendTo(".topic");
			 }

		  },
		  complete: function(XMLHttpRequest, textStatus){
			  ///$('#slider').nivoSlider();
		  }, 
		  error: function(){
			
		  }
	  });
	}
	/*知识贡献度*/

function initKgxd(){
	//alert(1000);
	var postdata={num:8,userid:'SUPERADMIN'};
	$.ajax({
		  type: "post",
		  url: "/doc/index.php?r=/rc/getInfoUidrankApi",
		 	data:postdata,
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  success: function(data, textStatus){
			 // alert(data);
			var jsonObj=eval(data);
			 var len = jsonObj.length;
			 for(var i=0;i<7;i++){
				//alert(jsonObj[i].author);
				 $("<li></li>").append(
				 $("<a href='/doc/index.php?r=/Fileshare&id="+jsonObj[i].doc_id+"&type=doc'></a>").text(jsonObj[i].name)
				 )
				.append(
				$("<span></span>").text(jsonObj[i].read_count)			
				).append(
				$("<span>次</span>")		
				)
				 .appendTo(".kgxd");
			 }

		  },
		  complete: function(XMLHttpRequest, textStatus){
			  ///$('#slider').nivoSlider();
		  },
		  error: function(){
				//alert("error");
		  }
	  });
	}
		//获取at我的信息个数
function initAtInfo(){
			$.ajax({
			  type: "post",
			  url:"/sns/index.php?app=api&mod=Statuses&act=getAtMe&p=2&sid=MTIzNDU2Nzg5X1NVUEVSQURNSU5AMTIzNDU2Nzg5MA==",
			
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				  var obj="";
				  try{
					  obj=eval("("+data+")"); 
					 
				  }catch(e){
					  
				  }
				   if(obj.count>0){ 
				   		
				  		$(".atnum").html(obj.count);
				   }
			  },
		  complete: function(XMLHttpRequest, textStatus){
			  ///$('#slider').nivoSlider();
		  },
		  error: function(){
				//alert("error");
		  }
	  });
		}

