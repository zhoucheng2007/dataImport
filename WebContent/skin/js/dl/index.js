var delaytime=600000;//1000*60*n分钟

//退出
function initQuit(){
	$('#quitSpan').bind('click', function() {
	if(!confirm("您确定要退出系统吗？"))return false;
	//销毁会话
	//alert("coret="+coret);
	var portal = $.post("/portal/logout","text");
	//alert("cscret="+cscret);
	var oaret = $.post("/oa/logout","text");
	//alert("baseret="+baseret);
	var bpmret = $.post("/bpm/logout","text");
	//alert("crmret="+crmret);
	document.location.href="/v6/logout";
	});
}
//欢迎信息
function initPersonal(){
	var html='<a href="dlportal.cmd?method=forChangeInformation"><s class="movepoint"></s></a><div class="movepoint"><span class="user-info">尊敬的用户:  您好！</span><span class="changepassword" id="changepassword"><a href="#" >修改密码</a></span><span class="logout" id="quitSpan"><a href="#" id="quit">退出系统</a></span></div><b></b>';
	$("#personal").html(html);	
	$.ajax({
		  type: "post",
		  url: "dlportal.cmd?method=getPersonal",
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval("("+data+")");
//			 $(".user-date").text(jsonObj[0].DATE+" "+jsonObj[0].WEEK);
			 $(".user-info").html('尊敬的用户：<a style="font-weight: bold; font-size: 14px; color: #2a7bc2;" href="dlportal.cmd?method=forChangeInformation">'+jsonObj[0].USERNAME+'</a> 您好！');
			 $("#userName").val(jsonObj[0].USERNAME);
			  
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
//内网连接
function initLink(){
	$("#top-nwsy").bind('click', function() {
		//alert(90);
		var tk = encodeURIComponent(CryptoJS.AES.encrypt($("#userIdN2").val(),"12AW23"));
		//alert(tk);
		//alert( CryptoJS.AES.decrypt("U2FsdGVkX1+76RxpPCrcninGes+2z/uYdIOTnLEoyPc=","12AW23").toString(CryptoJS.enc.Utf8))
		window.location.href="http://10.13.1.110/eap/main?tk="+tk;
		//window.location.href="/portal/jsp/sd.jsp?tk="+tk;
	});
}
//我的邮件
function initMailData(){
	$.ajax({
		  type: "post",
		  url: "dlportal.cmd?method=getMail",
		  beforeSend: function(XMLHttpRequest){
			 $("#mail-content").html('');
		  },
		  success: function(data, textStatus){
			  $("#mail-content").html("");
			 var userId = $("#userId2").val();
			 var str=[];
			 var jsonObj=eval("("+data+")");
			 var inj = 1;
			 $.each(jsonObj,function(i){
				 if(inj<8){
				 var title = jsonObj[i].mailTitle;
				 if((title==null)||(title=='')) title = "无主题";
				 var obj='<li class="mainnew"></li>';
				 if(jsonObj[i].mailStatus==1){
					 obj='<li class="mainread"></li>';
				 }
				 $(obj)
				 .append(
					$("<div></div>")
					.append(
					$("<a></a>")
					.attr({
						"href":"http://mail.dltobacco.com/owa/auth/logon.aspx?replaceCurrent=1&url=http%3a%2f%2fmail.dltobacco.com%2fowa%2f&fromportal=1&ssoUserName="+userId,//jsonObj[i].mailUrl
						"target":"_blank",
						"title":title
					})
					.text(title)
					
				 )
				 )

				 .appendTo("#mail-content");
			 }
			 inj++;
			 })
			 

		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
function initMail(){
	var str=[];
	str.push('<div class="top-title movepoint"><s class="notice-img"></s><div>我的邮件</div></div>');
	str.push('<ul class="mail-content" id="mail-content">');
	str.push('</ul>');
	$("#mail").html(str.join(''));
	initMailData();
	setInterval("initMailData()",delaytime); 
}
//日程安排
function initSedeul(){
	var str=[];
	str.push('<div class="top-title movepoint"><s class="comm-sedeul-img"></s><div>我的日程</div></div>');
	str.push('<div id="calendar"></div>');
	str.push('<div id="dialog" title="创建日程" style=" font-size:12px;display:none" >');
	str.push('<div class="createContent">');
	str.push('<div class="timePart">');
	str.push('<div style="float:left">时间:</div>');
	str.push('<div style="float:left">');
	str.push('<span id="dlg_start_date"></span>');
	str.push('</div>');
	str.push('<div style="clear:both"></div>');
	str.push('</div>');
	str.push('<div class="schContent" style="margin-top:10px">');
	str.push('<div style="float:left">内容:</div>');
	str.push('<div style="float:left">');
	str.push('<textarea id="dlg_title" style="width:225px; height:66px;" ></textarea>');
	str.push('</div>');
	str.push('<div style="clear:both"></div>');
	str.push('</div>');
	str.push('</div>');
	str.push('</div>');
	$("#sedeul").html(str.join(''));
}

 
//代办任务
function initDaibanData(id){
	//alert(78);
	$.ajax({
		  type: "post",
		  url: "dlportal.cmd?method=getWorkTask&id="+id,
		  beforeSend: function(XMLHttpRequest){
			 $("#daiban-table").html('');
		  },
		  success: function(data, textStatus){
			  $("#daiban-table").html("");
			 var str=[];
			var json=eval("("+data+")");
		//	 var std = '[{"PAS_CNT":"4","OA_CNT":"3"},[{"workDate":"20121226","workTitle":"[zw]大连电子2政务项目测试yeqing","workType":"0","workUrl":""},{"workDate":"20121226","workTitle":"[zw]大连电子2政务项目测试yeqing","workType":"0","workUrl":""},{"workDate":"20121226","workTitle":"[zw]大连电子2政务项目qwe测试yeqing","workType":"0","workUrl":""},{"workDate":"20121226","workTitle":"[zw]大连电子2政务项目测试yeqing","workType":"0","workUrl":""},{"workDate":"20121226","workTitle":"[zw]大连电子政务项目测试yeqing","workType":"0","workUrl":""},{"workDate":"20121226","workTitle":"[zw]大连电子政务项目测试2222222yeqing","workType":"0","workUrl":""},{"workDate":"20121226","workTitle":"[zw]大连电子2ss务项目测试yeqing","workType":"0","workUrl":""},{"workDate":"20121226","workTitle":"[项目]大连电子政务项目测试yeqing","workType":"1","workUrl":""},{"workDate":"20121226","workTitle":"[项目]大连电子政务项目测试yeqing","workType":"1","workUrl":""},{"workDate":"20121226","workTitle":"[项目]大连电子政务项目测试yeqing","workType":"1","workUrl":""},{"workDate":"20121226","workTitle":"[项目]大连电子政务项目测试yeqing","workType":"1","workUrl":""}]]';
		//   var json = eval("("+std+")");
			 var pcnt = json[0].PAS_CNT-0;
			 var ocnt = json[0].OA_CNT-0;
			 var ncnt = json[0].NW_CNT-0;
			 var twocnt = json[0].TWO_CNT-0;
			  $("#twonum").html(twocnt);
			  $("#nwnum").html(ncnt);
			  $("#gwnum").html(ocnt);
			  $("#swnum").html(pcnt);
			 var jsonObj = json[1];	

			 $.each(jsonObj,function(i){			 
				 var title = jsonObj[i].workTitle;
				 if((title==null)||(title=='')){
					 title = jsonObj[i].workTitle;
				 }
				 $("<tr></tr>")
				 .append(
					$("<td></td>")
					.append(
						$("<div></div>")
						.attr({
							"class":"title"
						})
						.append(
							$("<a></a>")
							.attr({
								"href":jsonObj[i].workUrl,
								"target":"_blank",
								"title":title
							})
							.text(title)
							.click(function(){
								initDaibanData('gongwen');
							}
							)
						)
					)
				 )
				 .append(
					$("<td></td>")
					.attr({
							"class":"w80"
						})
					.text(formatDt(jsonObj[i].workDate))
				)
				 .appendTo("#daiban-table");		 
			 })
		  },
		  complete: function(XMLHttpRequest, textStatus){
				$('#daiban-table tr:odd').addClass('odd');
				$('#daiban-table tr:even').addClass('even');
		  },
		  error: function(){			
		  }
	  });
}
function initDaiban(){
	var str=[];
	  str.push('<div class="daibantitle">	<div id="gongwen" class="title active"><span class="words">公文<span class="num" id="gwnum">6</span></span></div>');
  	str.push('<div id="shiwu" class="title"><span class="words">事务<span class="num" id="swnum">0</span></span></div><div id="twowork" class="title"><span class="words">两项工作<span class="num" id="twonum">1</span></span></div><div id="nwsite" class="title"><span class="words">内网<span class="num" id="nwnum">0</span></span></div>');
   	str.push('<div  class="more"><a id="moretz" href="#"></a></div></div>');
	//str.push('<div class="min-top-title movepoint"><s class="daiban-img"></s><div class="title">待办事项</div><div class="time">时间</div><div class="doactive"><a href="jsp/com/v6/screen/portal/pws/dl/more_task.jsp">更多</a></div>');
	str.push('<div id="daiban-div">');
	str.push('<table id="daiban-table">');
	str.push('</table></div>');
	$("#daiban").html(str.join(''));
	initDaibanData('gongwen');
	setInterval("initDaibanData('gongwen')",delaytime); 
	
}
function formatDt(date){
	if((date==null)||(date=="")){
		return "";
	}else{
		return date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
	}
}
//通知公告
function initNoticeData(){
	$.ajax({
		  type: "post",
		  url: "dlportal.cmd?method=getNotice&num=6",
		  beforeSend: function(XMLHttpRequest){
			 $("#notic-content").html('');
		  },
success: function(data, textStatus){
	//var data = '[{"noticeDate":"20121224","noticeTitle":"关于开展电子政务系统培训的通知","noticeUrl":"http://10.13.1.110//eap/45.news.detail?news_id=121"},{"noticeDate":"20121224","noticeTitle":"关于2012年下半年绩效考核检查情况的通报","noticeUrl":"http://10.13.1.110//eap/45.news.detail?news_id=120"},{"noticeDate":"20121219","noticeTitle":"关于党组理论中心组进行2012年第四季度政治理论学习的通知","noticeUrl":"http://10.13.1.110//eap/45.news.detail?news_id=3176"},{"noticeDate":"20121217","noticeTitle":"关于召开2012年下半年安全委员会会议的通知","noticeUrl":"http://10.13.1.110//eap/45.news.detail?news_id=3172"},	{"noticeDate":"20121217","noticeTitle":"关于开展2012年四季度安全大检查的通知","noticeUrl":"http://10.13.1.110//eap/45.news.detail?news_id=3173"},{"noticeDate":"20121217","noticeTitle":"关于召开大连烟草2012年卷烟销售工作会议的通知","noticeUrl":"http://10.13.1.110//eap/45.news.detail?news_id=3174"},{"noticeDate":"20121217","noticeTitle":"关于开展企业主要负责人及安全管理人员安全培训的通知","noticeUrl":"http://10.13.1.110//eap/45.news.detail?news_id=3175"}]';
	$("#notic-content").html("");
	 var str=[];
	 var jsonObj=eval("("+data+")");
	 $.each(jsonObj,function(i){
		 var title = jsonObj[i].noticeTitle;
		 if((title==null)||(title=='')){
			 title = jsonObj[i].noticeTitle;
		 }
		 if(i>5) return;
		 var obj='<li></li>';
		 $(obj)
		 .append(
			$("<div></div>")
				.append(
				$("<a></a>")
				.attr({
					"href":jsonObj[i].noticeUrl,
					"target":"_blank",
					"title":title
				})
				.text(title)						
			 )
		 )
		 .append(
			$("<span></span>")
			.text(formatDt(jsonObj[i].noticeDate))
		 )
		 .appendTo("#notic-content")
	 })
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
function initNotice(){
	var str=[];
	str.push('<div class="min-top-title movepoint"><s class="mail-img"></s><div class="title">通知公告</div><div class="time"></div><div class="doactive"><a href="/portal/jsp/com/v6/screen/portal/pws/dl/more_task.jsp?type=2"></a></div></div>');
	str.push('<ul class="notice-list" id="notic-content">');
	str.push('</ul>');
	$("#notice").html(str.join(''));
	initNoticeData();
	setInterval("initNoticeData()",delaytime); 
}
 



//民主公开
function initMinzuData(){
	$.ajax({
		  type: "post",
		  url: "dlportal.cmd?method=getPublicity",
		  beforeSend: function(XMLHttpRequest){
			 $("#minzu-content").html('');
		  },
		  success: function(data, textStatus){
			  $("#minzu-content").html("");
			 var str=[];
			 var jsonObj=eval("("+data+")");
			 
			 $.each(jsonObj,function(i){
				 var titile = jsonObj[i].getPublicityTitle;
				 if(titile==null || titile==''){
					 titile = "无主题";
				 }
				 if(i>5) return;
				 $("<li></li>")
				 .append(
					$("<div></div>")
						.append(
						$("<a></a>")
						.attr({
							"href":jsonObj[i].getPublicityUrl,
							"target":"_blank",
							"title":titile
						})
						.text(titile)						
					 )
				 )
				 .append(
					$("<span></span>")
					.text(formatDt(jsonObj[i].getPublicityDate))
				 )
				 .appendTo("#minzu-content")
			 })

		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
function initMinzu(){
	var str=[];
	str.push('<div class="min-top-title movepoint"><s class="minzu-img"></s><div class="title">民主公开</div><div class="time"></div><div class="doactive"><a href="/portal/jsp/com/v6/screen/portal/pws/dl/more_task.jsp?type=3"></a></div></div>');
	str.push('<ul class="minzu-list" id="minzu-content">');
	str.push('</ul>');
	$("#minzu").html(str.join(''));
	initMinzuData();
	 setInterval("initMinzuData()",delaytime); 
}
//常用系统
function initCommsys(){
	var str =[];
	str.push('<div class="right_top-title movepoint"><s class="comm-system-img"></s><div style="width:100px;">常用系统</div><b></b></div>');
	str.push('<ul class="comm-system"><li class="comm-sys-1"><a title="公文管理" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">公文管理</a></li><li class="comm-sys-1"><a title="待办任务" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">待办任务</a></li><li class="comm-sys-1"><a title="两项工作" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">两项工作</a></li><li class="comm-sys-1"><a title="电子商务系统" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">电子商务系统</a></li><li class="comm-sys-1"><a title="内网管理" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">内网管理</a></li><li class="comm-sys-1"><a title="系统管理" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">系统管理</a></li><li class="comm-sys-1"><a title="两项工作" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">两项工作</a></li>');
	str.push('</ul>');
	$("#comm-sys").html(str.join(''));
	//var sysurl = "/bsp/topMenuQuery.cmd";
	  //var sysurl ="/v6/topMenuQuery.cmd";
  /*  var sysurl ="/v6/topMenuQuery.cmd";
  	$.post(sysurl,function(str){
		//alert(str);
		var data = eval(str);
		var jsonObj = data;
		$.each(jsonObj,function(i){
			if(jsonObj[i].text.indexOf("门户")==-1){			
				var toUrl = "";
				var target = jsonObj[i].target;
			if(target!="self"){
				toUrl = "/v6/jsp/public/v6basic/index.jsp?appcode="+jsonObj[i].id;
			}else{
				if(jsonObj[i].url.indexOf("?")==-1){
					toUrl = jsonObj[i].url+"?appcode="+jsonObj[i].id;
				}else{
					toUrl = jsonObj[i].url;
				}
			}	
			$("<li></li>")
			 .addClass(showico(i))
			.append(
				$("<a></a>")
				.attr({
					"href":toUrl,
					"target":"_blank",
					"title":jsonObj[i].text
				})
				.text(jsonObj[i].text)						
			 )
			 .appendTo(".comm-system")
			 
			 
			}// jsonObj[i].text.indexOf("门户")==-1  
		});
		
		
	});*/
//	$.ajax({
//		  type: "post",
//		  url: "dlportal.cmd?method=getApp",
//		  beforeSend: function(XMLHttpRequest){
//			 $(".comm-system").html('');
//		  },
//		  success: function(data, textStatus){
//			 var str=[];
//			 var jsonObj=eval("("+data+")");
//			 $.each(jsonObj,function(i){
//				 $("<li></li>")
//				 .addClass(showico(jsonObj[i].appCode))
//				.append(
//					$("<a></a>")
//					.attr({
//						"href":"/portal/authenService?userId=SUPERADMIN&SSO=V3&source=http://10.10.1.11:9083/d3/pcyanyeshougou.cmd",
//						"target":"_blank",
//						"title":jsonObj[i].appCode
//					})
//					.text(jsonObj[i].appName)						
//				 )
//				 .appendTo(".comm-system")
//			 })
//
//		  },
//		  complete: function(XMLHttpRequest, textStatus){
//		  },
//		  error: function(){			
//		  }
//	  });
}
//常用操作
function initOperation(){
	var str=[];
	str.push('<div class="right_top-title movepoint"><s class="comm-oper-img"></s><div style="width:100px;">常用操作</div><b></b></div>');
  str.push('<ul class="comm-oper"><li class="comm-sys-9"><a title="发文" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">发文</a></li>');
  str.push('<li onmouseover="showhysitlink(\'shiwu\')" onmouseout="dis_showhysitlink(\'shiwu\')" class="comm-sys-9"><a title="综合事务"   href="#" target="_blank">综合事务</a></li>');
  str.push('<li onmouseover="showhysitlink(\'twow\')" onmouseout="dis_showhysitlink(\'twow\')" class="comm-sys-9"><a title="两项工作" href="/v6/jsp/public/v6basic/index.jsp?appcode=BSPV601" target="_blank">两项工作</a></li>');
	str.push('</ul>');
	str.push('<div id="floatmenu"   onmouseover="showhysitlink(\'shiwu\')" onmouseout="dis_showhysitlink(\'shiwu\')"  class="floatmenu" style="border:2px solid #0095c6;display:none; overflow:auto;position:absolute;width:450px;height:400px;background:#fff;top:-150px;right:100px"></div>');
	str.push('<div id="twowfloatmenu" onmouseover="showhysitlink(\'twow\')" onmouseout="dis_showhysitlink(\'twow\')"  class="floatmenu" style="border:2px solid #0095c6;display:none; overflow:auto;position:absolute;width:450px;height:400px;background:#fff;top:-100px;right:100px"></div>');
	$("#comm-oper").html(str.join(''));
	//var sysurl = "/bsp/topMenuQuery.cmd";
	var sysurl = "dlportal.cmd?method=getNotice";

	$.post(sysurl,function(str){
		//	 var std = '[{"text":"年度计划","children":[{"text":"年度计划审批流程","url":"querymeasuremf.cmd?sysFunctionId=measuremffun","id":"000000000000000000000000001376"},{"text":"工程投资实施流程","url":"querymeasureplan.cmd?sysFunctionId=measureplanfun","id":"000000000000000000000000001382"}],"id":"000000000000000000000000001374"},{"text":"物资采购","children":[{"text":"物资采购立项流程","url":"querymeasuremf.cmd?sysFunctionId=measuremffun","id":"000000000000000000000000001376"},{"text":"物资采购实施流程","url":"querymeasureplan.cmd?sysFunctionId=measureplanfun","id":"000000000000000000000000001382"}],"id":"000000000000000000000000001374"}]';
		//   var jsonObj = eval("("+std+")");
		var data = eval(str);
		var jsonObj = data;
				 $.each(jsonObj,function(i){
				 $("<div></div>").addClass("fenlei").text(jsonObj[i].text).appendTo("#floatmenu");
				 $("<div></div>").addClass("tanchuang")
						.append(
						function(n){
							var jsonstr="";
							$.each(jsonObj[i].children,function(j){
								 jsonstr=jsonstr+(' <div class="l">'+jsonObj[i].children[j].text+'</div>');
								});
							return	jsonstr;
							}
						)		
				 .appendTo("#floatmenu")
			 })
	
	});
		$.post(sysurl+"&param=twow",function(str){
		var data = eval(str);
		var jsonObj = data;
				 $.each(jsonObj,function(i){
				 $("<div></div>").addClass("fenlei").text(jsonObj[i].text).appendTo("#twowfloatmenu");
				 $("<div></div>").addClass("tanchuang")
						.append(
						function(n){
							var jsonstr="";
							$.each(jsonObj[i].children,function(j){
								 jsonstr=jsonstr+(' <div class="l">'+jsonObj[i].children[j].text+'</div>');
								});
							return	jsonstr;
							}
						)		
				 .appendTo("#twowfloatmenu")
			 })
	
	});
//	$.ajax({
//		  type: "post",
//		  url: "dlportal.cmd?method=getApp",
//		  beforeSend: function(XMLHttpRequest){
//			 $(".comm-system").html('');
//		  },
//		  success: function(data, textStatus){
//			 var str=[];
//			 var jsonObj=eval("("+data+")");
//			 $.each(jsonObj,function(i){
//				 $("<li></li>")
//				 .addClass(showico(jsonObj[i].appCode))
//				.append(
//					$("<a></a>")
//					.attr({
//						"href":"/portal/authenService?userId=SUPERADMIN&SSO=V3&source=http://10.10.1.11:9083/d3/pcyanyeshougou.cmd",
//						"target":"_blank",
//						"title":jsonObj[i].appCode
//					})
//					.text(jsonObj[i].appName)						
//				 )
//				 .appendTo(".comm-system")
//			 })
//
//		  },
//		  complete: function(XMLHttpRequest, textStatus){
//		  },
//		  error: function(){			
//		  }
//	  });
}
$(function() {
	initPersonal();
	initNotice();
	initSedeul();
	initDaiban();
	initMail();
	initMinzu();
	initCommsys();
	initQuit();
	initLink();
	initOperation();
	//initLink();
		$("#shiwu").mouseover(function(){
		$(this).addClass("active");
		$("#gongwen").removeClass("active");
		$("#twowork").removeClass("active");
	  $("#nwsite").removeClass("active");
		var url = "#";
		$("#moretz").attr("href",url);
	 initDaibanData('shiwu');
	})
		$("#twowork").mouseover(function(){
		$(this).addClass("active");
		$("#gongwen").removeClass("active");
		$("#shiwu").removeClass("active");
	  $("#nwsite").removeClass("active");
		var url = "#";
		$("#moretz").attr("href",url);
	 initDaibanData('twowork');
	})
	$("#gongwen").mouseover(function(){
		$(this).addClass("active");
		$("#shiwu").removeClass("active");
		$("#twowork").removeClass("active");
	  $("#nwsite").removeClass("active");
		var url = "#";
		$("#moretz").attr("href",url);
	 initDaibanData('gongwen');
	})
		$("#nwsite").mouseover(function(){
		$(this).addClass("active");
		$("#shiwu").removeClass("active");
		$("#twowork").removeClass("active");
	  $("#gongwen").removeClass("active");
		var url = "#";
		$("#moretz").attr("href",url);
	 initDaibanData('nwsite');
	})
	$( ".main-container-left" ).sortable({
		axis: 'y',
		addClasses: false,
		update: function(event, ui) {
			update($(this),"LEFT_PATH")
		},
		stop: function(event, ui) {
			$("#sedeul").removeAttr("style"); 
		},
		handle:".movepoint"
	});
	$( ".main-container-center" ).sortable({
		axis: 'y',
		addClasses: false,
		update: function(event, ui) {
			update($(this),"CENTER_PATH")
		},
		handle:".movepoint"
	});
	$( ".main-container-right" ).sortable({
		axis: 'y',
		addClasses: false,
		update: function(event, ui) {
			update($(this),"RIGHT_PATH")
		},
		handle:".movepoint"
	});

	$( ".column" ).disableSelection();

	
	
	
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	var calendar=$('#calendar').fullCalendar({
		header:{
			left:'prev',
			center:'today,title',
			right:'next'
		},
		titleFormat: {
			month: 'yyyy年MMMM',
			week: "MMM d[ yyyy]{ '&#8212;'[ MMM] d yyyy}",
			day: 'dddd, MMM d, yyyy'
		},
		monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		//dayNames: ['周一','周二','周三','周四','周五','周六','周日'],
		//dayNamesShort: ['一','二','三','四','五','六','日'],
		dayNames: ['周日','周一','周二','周三','周四','周五','周六'],
		dayNamesShort: ['日','一','二','三','四','五','六'],
		buttonText: {
			prev: '&nbsp;&#9668;&nbsp;',
			next: '&nbsp;&#9658;&nbsp;',
			prevYear: '&nbsp;&lt;&lt;&nbsp;',
			nextYear: '&nbsp;&gt;&gt;&nbsp;',
			today: '今天	',
			month: 'month',
			week: 'week',
			day: 'day'
		},
		selectable: true,
		selectHelper: true,
		select: function(start, end, allDay) {
			var title="";
			$("#dlg_title").val("");
			$("#dlg_start_date").text($.fullCalendar.formatDate(start,"yyyy年MM月dd日"));
			$( "#dialog" ).dialog({
				width:310,
				modal: true,
				buttons: {
					Ok: function() {
						title=$("#dlg_title").val();
						$( this ).dialog( "close" );
						if (title!="") {
							var id=(new Date()).getTime()+parseInt(Math.random()*10000);
							calendar.fullCalendar('renderEvent',
								{
									id:id,
									title: title,
									start: start,
									end: end,
									allDay: allDay
								},
								true // make the event "stick"
							);
							calendar.fullCalendar('unselect');
							$.ajax({
								  type: "post",
								  data:"ID="+id+"&TITLE="+encodeURI(title)+"&CONTENT="+encodeURI(title)+"&START="+$.fullCalendar.formatDate(start,"yyyy-MM-dd")+"&END="+$.fullCalendar.formatDate(end,"yyyy-MM-dd"),
								  url: "dlportal.cmd?method=addSchedule",
								  beforeSend: function(XMLHttpRequest){
								  },
								  success: function(data, textStatus){
								  },
								  complete: function(XMLHttpRequest, textStatus){
								  },
								  error: function(){			
								  }
							  });
						}
						
					}
				}
			});
		},
		editable: true,
		eventClick: function(calEvent, jsEvent, view) {

			$(".floatMsg").remove();
			
			//浮动框
			var floatMsgTitle=calEvent.title;
			var floatMsgDate="";
			if(calEvent.end){
				floatMsgDate=$.fullCalendar.formatDate(calEvent.start,"yyyy年MM月dd日")+"-"+$.fullCalendar.formatDate(calEvent.end,"yyyy年MM月dd日");
			}else{
				var yy=$.fullCalendar.formatDate(calEvent.start,"yyyy");
				var mm=$.fullCalendar.formatDate(calEvent.start,"MM");
				var dd=$.fullCalendar.formatDate(calEvent.start,"dd");
				var now=calEvent.start;  
				var day=now.getDay();
				var week; 
				var arr_week=new Array("星期一","星期二","星期三","星期四","星期五","星期六","星期日"); 
				week=arr_week[day];
				floatMsgDate=$.fullCalendar.formatDate(calEvent.start,"yyyy年MM月dd日")+" "+week+" "+GetCNDate(yy,mm,dd);
			}
			
			var $floatMsg=$("<div></div>")
			.addClass("floatMsg")
			
			.append(
				$("<div></div>").addClass("clostBtn")
				.click(function(){
					$floatMsg.remove();
				})
			)
			.append(
				$("<div></div>").addClass("content")
				.append(
					$("<div id='fm_date'></div>").text(floatMsgDate)
				)
				.append(
					$("<div id='fm_title'></div>").text(floatMsgTitle)
				)
				
			)
			.append(
				$("<div></div>").addClass("option")
				.append(
					$("<span>删除</span>").click(function(){
						calendar.fullCalendar( 'removeEvents',calEvent.id);
						$floatMsg.remove();
						$.ajax({
							  type: "post",
							  data:"ID="+calEvent.id,
							  url: "dlportal.cmd?method=deleteSchedule",
							  beforeSend: function(XMLHttpRequest){
							  },
							  success: function(data, textStatus){
							  },
							  complete: function(XMLHttpRequest, textStatus){
							  },
							  error: function(){			
							  }
						  });
					})
				)
				.append(
					$("<span>修改</span>").click(function(){
						$floatMsg.remove();
						$("#dlg_title").val(floatMsgTitle);
						$("#dlg_start_date").text($.fullCalendar.formatDate(calEvent.start,"yyyy年MM月dd日"))
						$( "#dialog" ).dialog({
							width:310,
							modal: true,
							buttons: {
								Ok: function() {
									title=$("#dlg_title").val();
									calEvent.title=title;
									calendar.fullCalendar( 'updateEvent',calEvent);
									$.ajax({
										  type: "post",
										  data:"ID="+calEvent.id+"&TITLE="+encodeURI(title)+"&CONTENT="+encodeURI(title),
										  url: "dlportal.cmd?method=updateSchedule",
										  beforeSend: function(XMLHttpRequest){
										  },
										  success: function(data, textStatus){
										  },
										  complete: function(XMLHttpRequest, textStatus){
										  },
										  error: function(){			
										  }
									  });
									$( this ).dialog( "close" );
								}
							}
						});
					})
				)
			)
			.appendTo("body");
			//floatMsg widht 300px min-height 60px
			var msgTop=jsEvent.pageY+12,msgLeft=jsEvent.pageX-150;
			if(msgTop> $("body").height()){//页面底部
				msgTop = jsEvent.pageY-$(".floatMsg").height()<$(window).height()?jsEvent.pageY-$(".floatMsg").height():$(window).height()-$(".floatMsg").height();								
				//msgTop = $("body").height()-$(".floatMsg").height();								
			}
			if((msgLeft+$("#sedeul").offset().left) < 0){
				msgLeft = jsEvent.pageX+$("#sedeul").offset().left;
			}
			$(".floatMsg")
			.css({
				"top":msgTop+"px",
				"left":msgLeft+"px"
			})
			
			//alert('Event: ' + calEvent.title);
			//alert('start: ' + calEvent.start);
			//alert('end: ' + calEvent.end);
			//alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
//			alert('View: ' + view.name);
			
		
	
		},
		events: function(start, end, callback) {
			$.getJSON("dlportal.cmd?method=querySchedule", {START: $.fullCalendar.formatDate(start,"yyyy-MM-dd"), END: $.fullCalendar.formatDate(end,"yyyy-MM-dd")
				}, function(result) {callback(result);}
				)
			}
		
	})
	
});
function update(obj,id){
	var objs=obj.sortable('toArray');
	var sort_str="";
	$.each(objs,function(i){
		if(i==0) {
			sort_str=objs[i];
		}else{
			sort_str=sort_str+"#"+objs[i];
		}
	})
	$.ajax({
		  type: "post",
		  data:"TYPE="+id+"&PATH="+sort_str,
		  url: "dlportal.cmd?method=updateModule",
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){
			  if(id=="LEFT_PATH"){
				  $("#sedeul").removeAttr("style"); 
			  }
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
//function showico(appCode){
//	var icoimg="";
//	switch (appCode) {
//	case "OA":
//		icoimg="comm-sys-1";
//		break;
//	case "V3":
//		icoimg="comm-sys-2";
//		break;
//	default:
//		icoimg="comm-sys-1";
//		break;
//	}
//	return icoimg;
//}
function showico(i){ 
	
	return "comm-sys-"+((i+0)%12+1);
}
function showhysitlink(id){
	if(id=='shiwu'){
document.getElementById('floatmenu').style.display="block";}
 if(id=='twow'){
  document.getElementById('twowfloatmenu').style.display="block";}
}
function dis_showhysitlink(id){
		if(id=='shiwu'){
document.getElementById('floatmenu').style.display="none";}
  if(id=='twow'){
  document.getElementById('twowfloatmenu').style.display="none";}
}

