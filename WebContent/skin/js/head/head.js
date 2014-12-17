/**
 * 2013-6-9 孙振重构代码
 * 2014-2-11 孙振实现头部项目可配置
 */

var v6head={
	bspcontent:"v6",
	userId:"",
	userIdForSns:"",
	userName:"",
	skin:"",
	logout:0,//控制弹出会话失效提示
	mesCount:0//统计所有消息的数量累计
};

//为uba在cookie中存放公司id，用户id
try{
	$.ajax({
		  type: "post",
		  url:"/portal/portal/appCenterInitCmd.cmd?method=queryForUba",
		  success: function(data, textStatus){
			  document.cookie="v6uba="+data+";path=/uba"; 			  
		  },
		  error: function(xhr){	  
		  }
	  });
}catch(e){	
}

$(document).ready(function(){
	initHeadDom();
});


function initHeadDom(){
	var headMenuList=[];//头部大模块配置
	var message={};//消息中心配置
	if("headConfig" in config){
		headMenuList=config.headConfig.menus.list;	
	}else{
		alert("config.ini缺少必要的配置信息");
	}
	var str=[];
	str.push("<div id='headcontainer'>");
	str.push("<div id='center-layout'>");
	//str.push("	<div id='v6headlogo' class='v6logo'></div>");
	str.push("	<ul class='flexinav_menu'>");
	for(var i=0;i<headMenuList.length;i++){
		var menu=headMenuList[i];
		if(menu.mainPageBtn){
			str.push("<li id='mainPage'>");
		}else if(menu.dataCenterBtn){
			str.push("<li id='datacenter'>");
		}else{
			str.push("<li>");
		}
		str.push("<i class='item-icon icon"+i+"'></i>");
		str.push("<span class='item-text'>"+menu.text+"</span>");
		if(menu.businessAppBtn){
			str.push("<div id='applist' class='appbox'>");
			str.push("	<div class='bottomInfo'>");
			str.push("		<span class='menumap'>我的菜单</span>");
			str.push("	</div>");
			str.push("</div>");
		}else if("menuTypeId" in menu){
			str.push("<div class='appbox' menuTypeId='"+menu.menuTypeId+"'>");
			str.push("</div>");
		}
		
		str.push("</li>");
	}
	str.push("	</ul>");
	
	str.push("<div id='v6searchbox'>");
	str.push("	<input id='v6searchtext'/>");
	str.push("	<div id='v6searchbtn'></div>");
	str.push("</div>");
	
	str.push("<div id='headUserName'>");
	str.push("	<span id='loginUserName'>账号</span>");
	str.push("	<ul><li id='setpassword'>修改密码</li><li id='setemail'>邮箱管理</li></ul>");
	str.push("</div>");
	
	if("message" in config.headConfig){
		message=config.headConfig.message;
	    str.push("<div id='headmsg'>");
	    str.push("	<ul>");
	    if(("contactme" in message)&&message.contactme){
	    	str.push("<li id='contactme'>查看@我(<span id='headatme' style='color:red'>0</span>)</li>");
	    };
	    if(("personalmsg" in message)&&message.personalmsg){
	    	str.push("<li id='personalmsg'>部门通知(<span id='headsime' style='color:red'>0</span>)</li>");
	    };
	    if(("undo" in message)&&message.undo){
	    	str.push("<li id='undo'>待办任务(<span id='undonum' style='color:red'>0</span>)</li>");
	    };
	    if(("busimsg" in message)&&message.busimsg){
	    	str.push("<li id='busimsg'>业务消息(<span id='busimsgnum' style='color:red'>0</span>)</li>");
	    };
	    if(("comAnnouncement" in message)&&message.comAnnouncement){
	    	str.push("<li id='comAnnouncement'>公司通告(<span id='comAnnouncementNum' style='color:red'>0</span>)</li>");
	    };
	    if(("selfMail" in message)&&message.selfMail){
	    	 str.push("<li id='selfMail'>个人信件(<span id='myMail' style='color:red'>0</span>)</li>");	
	    };
	    //"busialert":true,"busialertText":"业务预警"
	    if(("busialert" in message)&&message.busialert){
	    	 str.push("<li id='busialert'>业务预警(<span id='busiWarn' style='color:red'>0</span>)</li>");
	    };	   
	    str.push("</ul>");
	    str.push("<div id='msgalert' class='new'></div>");
	    str.push("</div>");
	}
	str.push("<div class='logout'>");
	str.push("</div>");
	
	str.push("<div style='clear:both'></div>");

	str.push("</div>");
	str.push("</div>");
	
	str.push("<form name='v6_app_form' method='post'>");
	str.push("<input id='v6_app_id' name='v6_app_id' type='hidden' value='' />");
	str.push("<input id='v6_app_code' name='v6_app_code' type='hidden' value='' />");
	str.push("<input id='v6_app_url' name='v6_app_url' type='hidden' value='' />");
	str.push("<input id='v6_app_type' name='v6_app_type' type='hidden' value='' />");
	str.push("<input id='v6_app_icon' name='v6_app_icon' type='hidden' value='' />");
	str.push("<input id='v6_app_text' name='v6_app_text' type='hidden' value='' />");
	str.push("<input id='menu_type_id' name='menu_type_id' type='hidden' value='' />");
	str.push("</form>");
	
	$("#headholder").html(str.join(""));

	getAppList();

}

function getAppList(){
	var $appList=$(".appbox");
	var k=0;
	$appList.each(function(i){
		var $this=$(this);
		var menuTypeId="";
		var url="";
		if($this.attr("id")==="applist"){
			url="/"+v6head.bspcontent+"/topMenuQuery.cmd";
		}else{
			menuTypeId=$this.attr("menuTypeId");
			url="/"+v6head.bspcontent+"/topMenuQueryByMenuType.cmd?menuTypeId="+menuTypeId;
		}				
			$.ajax({
				  type: "post",
				  url:url,
				  beforeSend: function(XMLHttpRequest){
					$this.append("<div id='loading'>正在加载...请稍候</div>");
				  },
				  success: function(data, textStatus){
					getAppListHtml($this,data,menuTypeId,$appList.size());
				  },
				  error: function(xhr){
					      v6head.logout=v6head.logout+1;
						  headLoadDataFromBspOnError();				  
				  }
			  });			
	})
}


var k=0;
function getAppListHtml(box,data,menuTypeId,appListSize){
	var topMenuJson;
	var topMenuStr=[];
	var iopApp=[];
	var lastApp=[];
	k++;
	try{
		topMenuJson=eval("("+data+")");
	}catch(e){
		v6head.logout=v6head.logout+1;
		headLoadDataFromBspOnError();
	}
	var topMenus=topMenuJson.menu.rows;
	for(var i=0;i<topMenus.length;i++){
		var menu=topMenus[i];
		var url=menu.url;
		if(url.indexOf("appHidden")>-1){
			continue;
		}
		var id=menu.id;
		var appCode=menu.appCode;
		var target=menu.target;
		var text=menu.text;
		var icon=menu.icon;
		if(id=='BSPV601'){
			icon="/skin/icons/32.png";
		}
		
		if(url.indexOf("putLast")>-1){
			lastApp.push("<div class='v6app business' appCode='"+id+"' appId='"+appCode+"' appTarget='"+target+"' appUrl='"+url+"' appIcon='"+icon+"' appText='"+text+"' menuTypeId='"+menuTypeId+"'>"  );
			lastApp.push("  <a title='"+text+"' class='v6appicon'>");
			lastApp.push("    <img width='59' height='59' src='"+icon+"'>");
			lastApp.push("    </img>");
			lastApp.push("	 <div class='v6apptext'>"+text+"</div>");	
			lastApp.push("  </a>");
			lastApp.push("</div>");
		}else{
			topMenuStr.push("<div class='v6app business' appCode='"+id+"' appId='"+appCode+"' appTarget='"+target+"' appUrl='"+url+"' appIcon='"+icon+"' appText='"+text+"' menuTypeId='"+menuTypeId+"'>"  );
			topMenuStr.push("  <a title='"+text+"' class='v6appicon'>");
			topMenuStr.push("    <img width='59' height='59' src='"+icon+"'>");
			topMenuStr.push("    </img>");
			topMenuStr.push("	 <div class='v6apptext'>"+text+"</div>");	
			topMenuStr.push("  </a>");
			topMenuStr.push("</div>");
		}
	}
	
	var iopApp=[];
	
	if(config.v6IOP && menuTypeId==""){
		$.ajax({
			  type: "post",
			  url:"/app/store/appRestService.do?method=getMyCollectedAppList",
			  success: function(obj, textStatus){
				  var arr=[];
				  try{
					arr=eval("("+obj+")");
					
				  }catch(e){
					//  
				  }
				 
				  for(var i=0;i<arr.length;i++){
						var app=arr[i];
						var appstr;
						if(app.individual_information==null){
							appstr="";
							}else appstr=app.individual_information[0].description ;
						iopApp.push("<div class='v6app business iop' appCode='"+app.app_id+"' appId='"+app.app_id+"' versionId='"+app.version_id+"'  appUrl='"+appstr+"' appIcon='"+app.app_icon+"' appText='"+app.app_name+"' menuTypeId=''>"  );
						iopApp.push("  <a title='"+app.app_name+"' class='v6appicon'>");
						iopApp.push("    <img width='59' height='59' src='"+app.app_icon+"'>");
						iopApp.push("    </img>");
						iopApp.push("	 <div class='v6apptext'>"+app.app_name+"</div>");	
						iopApp.push("  </a>");
						iopApp.push("  <div class='close'>");
						iopApp.push("  </div>");
						iopApp.push("</div>");
				  }
				  box.append(topMenuStr.concat(iopApp).concat(lastApp).join(""));
			  },
			  complete: function(XMLHttpRequest, textStatus){
				  if(k==appListSize){
					  for(one in Events){
							Events[one]();
					  }
				  }
				  $("#loading").remove();
			  },
			  error:function(){
				  box.append(topMenuStr.concat(iopApp).concat(lastApp).join(""));
				  if(k==appListSize){
					  for(one in Events){
							Events[one]();
					  }
				  }
				  $("#loading").remove();
			  }
		 });
	}else{
		box.append(topMenuStr.concat(iopApp).concat(lastApp).join(""));
		if(k==appListSize){
			  for(one in Events){
					Events[one]();
			  }
		}
		 $("#loading").remove();
	}
	
}

var Events={
	//获取cookie中skin的样式
	getSkin:function(){
		v6head.skin=getCookie("skin");
	},
	//计算apps容器的宽
	fixAppBoxWidth:function(){
		$(".appbox").each(function(i){
			var $this=$(this);
			var num=$this.find(".v6app").size();
			var w;
			if(num==1){
				w=97;
			}else if(num<6){
				w=num*90;
			}else{
				var id=$(this).attr("id");
				if(id=="applist"){
					w=720;
				}else{
					w=540;;
				}
			}
			$this.width(w);
		})
	},
	//为app添加事件
	app:function(){
		$(".v6app.business").click(function(){
			var appId=$(this).attr("appId");
			var appCode=$(this).attr("appCode");
			var appUrl=$(this).attr("appUrl");
			var appTarget=$(this).attr("appTarget");
			var appIcon=$(this).attr("appIcon");
			var appText=$(this).attr("appText");
			var menuTypeId=$(this).attr("menuTypeId");
			var appType="1";
			
			var parms=getParmFormUrl(appUrl);
			if("appType" in parms){
				appType=parms.appType;
			}
			//V6应用
			if(appType=="1"||appType=="6"){
				var open=$("#mt-menu").size()==0?false:true;//找一下应用中心的mt-menu标签，以判断应用中心是否已经打开了
				var supbrowser=$("#supbrowser").size()==0?false:true;//找一下应用中心的supbrowser标签，以判断当前是否是降浏览器版本访问模式
				if(appUrl==null){
					appUrl="";
				}
				if("indexUrl" in parms){
					if("blank"==appTarget){
					window.open( parms.indexUrl);
					return;
				}
				window.location.href = parms.indexUrl;
				}else if(appUrl.indexOf("supbrowser")==-1&&open&&!supbrowser){
					var opts={
						appId:appId,
						appCode:appCode,
						appType:appType,
						appIcon:appIcon,
						appText:appText,
						appUrl:appUrl,
						menuTypeId:menuTypeId
					}
					AppFrame.init(opts);//调用dcframe.js中的方法,以实现局部刷新
				}else{
					document.forms[0].action="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit";
					$("#v6_app_id").val(appId);
					$("#v6_app_code").val(appCode);
					$("#v6_app_url").val(appUrl);
					$("#v6_app_type").val(appType);
					$("#v6_app_icon").val(appIcon);
					$("#v6_app_text").val(appText);
					$("#menu_type_id").val(menuTypeId);
					document.forms[0].submit();
				}
			}else if(appType=="2"||appType=="4"){//楼上3应用
				document.forms[0].action="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit";
				$("#v6_app_id").val(appId);
				$("#v6_app_code").val(appCode);
				$("#v6_app_url").val(appUrl);
				$("#v6_app_type").val(appType);
				$("#v6_app_icon").val(appIcon);
				$("#v6_app_text").val(appText);
				document.forms[0].submit();
			}else if(appType=="3"){//其他单点登录应用
				var url="/portal/AuthenService" + "?USERID=" + v6head.userId + "&APP="+ appId + "&sampleSSO=1&IsAuthenNew=";
				window.open(url);
			}
				if("blank"==appTarget){
					window.open(appUrl);
					return;
				}
		})
	},
	topMenu:function(){
		$(".flexinav_menu li").hover(
			function(){
				var $this=$(this);
				$this.addClass("current").stop().fadeTo(0, 0.33).fadeTo(200, 1);
				$this.children(".appbox").show().stop().fadeTo(200, 1);
			},
			function(){
				var $this=$(this);
				$this.removeClass("current").stop().fadeTo(0, 0.33).fadeTo(200, 1);
				$this.children(".appbox").stop().fadeTo(200, 0,function(){
					$(this).hide();
				});
			}
		)
	},
	//为我的应用添加事件
	myMenu:function(){
		$(".menumap").click(function(){
			document.forms[0].action="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&menuId=menuMap";
			$("#v6_app_id").val("mymenu");
			$("#v6_app_code").val("mymenu");
			$("#v6_app_type").val("mymenu");
			$("#v6_app_icon").val("/skin/icons/32.png");
			$("#v6_app_text").val("我的应用");
			document.forms[0].submit();
		});
	},
	//为其他应用添加事件
	otherApp:function(){
		//首页
		$("#mainPage").click(function(){
			   var url="/portal/portal/cqpageinitcmd.cmd?method=goHomePage";
				if("mainPage" in config){
					url=config.mainPage;	
				}
			$.ajax({
				  type: "post",
				  url:"/portal/pubdatacmd.cmd?method=mainPageUrl",
				  beforeSend: function(XMLHttpRequest){ 
				  },
				  dataType: "text",
				  success: function( data, textStatus){
					  if(data!="error"){
						  url=data;
					  }
				  },
				  complete:function(){
					  window.location.href=url;
				  },
				  error:function(){
					  window.location.href=url;
				  }
			 })
		});
		$("#datacenter").click(					 
				function (){
					//改成直接访问首页
					var dataCenterUrl="http://10.36.98.239:8080/a/login/welcome";
					var sid = v6head.userId;
					var url = "/portal/AuthenService?USERID=" + sid + "&APP=10266&RESOURCE=" +dataCenterUrl + "&IsAuthenNew=1";
					window.open(url,"_blank");
				}
			)
	},
			//删除IOP应用
	removeIopApp:function(){
		$(".v6app.business.iop .close").click(function(event){
			event.stopPropagation();
			var app=$(this).closest(".iop");
			var versionId=app.attr("versionId");
			$.post("/app/store/appRestService.do?method=delCollectedAppByVersionId&versionId="+versionId,function(){
				app.remove();
			});
		})
	},
	//消息相关按钮
	msgBtn:function(){
		//@我的
		$("#contactme").click(function(){
	 	 	window.location.href="/portal/portal/msgcenterbase.cmd?method=queryPageInit&msgType=m7";
 		});
		//私信（部门通知）
		$("#personalmsg").click(function(){
			window.location.href="/portal/portal/msgcenterbase.cmd?method=queryPageInit&msgType=m5";
		});
		//待办任务
		$("#undo").click(function(){
			window.location.href="/portal/portal/msgcenterbase.cmd?method=queryPageInit&msgType=m3";
		});
		//业务消息
		$("#busimsg").click(function(){
			window.location.href="/portal/portal/msgcenterbase.cmd?method=queryPageInit&msgType=m4";
		});
		//个人信件
		$("#selfMail").click(function(){
			window.location.href="/portal/portal/msgcenterbase.cmd?method=queryPageInit&msgType=m2";		
		});
		//公司通告
		$("#comAnnouncement").click(function(){
			window.location.href="/portal/portal/msgcenterbase.cmd?method=queryPageInit&msgType=m1";
		});
		//业务预警
		$("#busialert").click(function(){
			window.location.href="/portal/portal/msgcenterbase.cmd?method=queryPageInit&msgType=m6";
		});
		
		
	},
	loginOut:function(){
		$(".logout").click(function(){
			document.location.href="/"+v6head.bspcontent+"/logout";
		})
	},
	setPassWord:function(){
		$("#setpassword").click(function(){
			window.open("/"+v6head.bspcontent+"/jsp/bsp/user/user_passwordmgt.jsp");
		})
	},
	setEmail:function(){
		$("#setemail").click(function(){
			window.open("/portal/portal/email.cmd?method=emailmgt");
		})
	},
	logoHelp:function(){
		$("#v6headlogo").click(function(){
			document.location.href="/portal/jsp/help/help.html";
		})
	},
	getUserInfo:function(){
		var v6u=getCookie("v6u");
		var b = new Base64();
		var userInfo=[];
		try{
			userInfo=b.decode(v6u).split("#");
		}catch(e){
			v6head.logout=v6head.logout+1;
			headLoadDataFromBspOnError();
		}
		
		v6head.userId=userInfo[0];
		v6head.userName=userInfo[1];
		v6head.userIdForSns=b.encode("123456789_"+v6head.userId+"@1234567890");
		if(v6head.userName){
			$("#loginUserName").text(v6head.userName);
		}
	},
	//搜索
	search:function(){
		$("#v6searchbtn").click(function(){
			 var searchText=$("#v6searchtext").val();
			 var textAfterEnCode = encodeURIComponent(searchText);
			 var b = new Base64();
			 var userIdAfterEncode = b.encode(v6head.userId);
			 var url="/sch/all.jsp?q="+textAfterEnCode+"&version=2.2&start=0&rows=10&userId="+userIdAfterEncode+"&type=0";
			 window.location.href=url;
		})
		$("#v6searchtext").keypress(function(e){
			  var ev= window.event||e;
			  if (ev.keyCode == 13) {
			  	$("#v6searchbtn").click();
			  }
		})
	},
	getNotice:function(){
		//如果配置了消息则获取异步获取消息个数
		if("message" in config.headConfig){
		   for(var one in headNoticeObj){
			   headNoticeObj[one]();
		   }
		};
		var msgInterval=600000;
		if("msgInterval" in config){
			msgInterval=config.msgInterval;
		}
		setInterval(function(){
			v6head.mesCount=0;//每次统计消息的个数时，先置0
			for(var one in headNoticeObj){
				if(one != "getWarn"){
					headNoticeObj[one]();
				}
			}
		},msgInterval);
	}
};
var headNoticeObj={
		//获取通知的消息个数
		noticeData:function(){
			if(("personalmsg" in config.headConfig.message)&&config.headConfig.message.personalmsg){
			$.ajax({
			  type: "post",
			  url:"/portal/portal/msgcenterbase.cmd?method=queryMsg",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				   if(data.length<10&&data>=0&&data!=""){ 
				   		$("#msgalert").addClass("new");
				  		$("#headsime").html(data);
				  		v6head.mesCount=v6head.mesCount+parseInt(data);
				   };				  
			  },
			  complete: function(XMLHttpRequest, textStatus){
				  headNoticeObj.meseageState();
			  },
			  error: function(xhr){
			  }
		  	});
			}
		},
		//获取at我的信息个数
		atmeData:function(){
			if(("contactme" in config.headConfig.message)&&config.headConfig.message.contactme){
			var url="/sns/index.php?app=api&mod=Statuses&act=getAtMe&p=2&sid="+v6head.userIdForSns;
			$.ajax({
			  type: "post",
			  url:url,
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				  var obj={};
				   try{
					   obj=eval("("+data+")");
				   }catch(e){
					   //
				   }
				   if(obj&&obj.count&&obj.count>0&&obj.count!=""){ 
					    $("#msgalert").addClass("new");
				  		$("#headatme").html(obj.count);
				  		v6head.mesCount=v6head.mesCount+parseInt(obj.count);
				   }
				   
			  },
			  complete: function(XMLHttpRequest, textStatus){
				  headNoticeObj.meseageState();
			  },
			  error: function(xhr){
			  }
		  	});
			}
		},
		getUnDoData:function(){
			if(("undo" in config.headConfig.message)&&config.headConfig.message.undo){
			$.ajax({
			  type: "post",
			  url:"/portal/portal/msgcenterbase.cmd?method=queryDaiban",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				   if(data.length<10&&data>=0&&data!=""){ 
					    $("#msgalert").addClass("new");
						$("#undonum").html(data);
						v6head.mesCount=v6head.mesCount+parseInt(data);
				   }
			  },
			  complete: function(XMLHttpRequest, textStatus){
				  headNoticeObj.meseageState();
			  },
			  error: function(xhr){
			  }
	  		});
			}
		},
		//获取业务消息
		getBusinessData:function(){
			if(("busimsg" in config.headConfig.message)&&config.headConfig.message.busimsg){
			$.ajax({
			  type: "post",
			  url:"/portal/portal/msgcenterbase.cmd?method=queryBusimsg",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				   if(data.length<10&&data>=0&&data!=""){ 
					    $("#msgalert").addClass("new");
						$("#busimsgnum").html(data);
						v6head.mesCount=v6head.mesCount+parseInt(data);
				   }
			  },
			  complete: function(XMLHttpRequest, textStatus){
				  headNoticeObj.meseageState();
			  },
			  error: function(xhr){
			  }
	  		});
			}
		},
		//未读公司通告数
		getUnReadAnnouncement:function(){
			if(("comAnnouncement" in config.headConfig.message)&&config.headConfig.message.comAnnouncement){
			$.ajax({
			  type: "post",
			  url:"/portal/portal/msgcenterbase.cmd?method=queryComAnnouncement",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				  if(data.length<10&&data>=0&&data!=""){ 				   
					    $("#msgalert").addClass("new");
						$("#comAnnouncementNum").html(data);
						v6head.mesCount=v6head.mesCount+parseInt(data);
				  }
			  },
			  complete: function(XMLHttpRequest, textStatus){
				  headNoticeObj.meseageState();
			  },
			  error: function(xhr){
			  }
	  		});
			}
		},
		getMyUnReadMail:function(){
			if(("selfMail" in config.headConfig.message)&&config.headConfig.message.selfMail){
			$.ajax({
				  type: "post",
				  url:"/portal/portal/msgcenterbase.cmd?method=getMail",
				  beforeSend: function(XMLHttpRequest){
				  },
				  success: function(data, textStatus){
					  if(data.length<10&&data>=0&&data!=""){ 				   
						    $("#msgalert").addClass("new");
							$("#myMail").html(data);
							v6head.mesCount=v6head.mesCount+parseInt(data);
					  }
				  },
				  complete: function(XMLHttpRequest, textStatus){
					  headNoticeObj.meseageState();
				  },
				  error: function(xhr){
				  }
		  	});
			}
		},
		getWarn:function(){
			if(("busialert" in config.headConfig.message)&&config.headConfig.message.busialert){
			$.ajax({
				  type: "post",
				  url:"/portal/portal/msgcenterbase.cmd?method=queryWarn",
				  beforeSend: function(XMLHttpRequest){
				  },
				  success: function(data, textStatus){
					  if(data.length<10&&data>=0&&data!=""){ 				   
						   $("#msgalert").addClass("new");
							$("#busiWarn").html(data);
							v6head.mesCount=v6head.mesCount+parseInt(data);
					  }			  
				  },
				  complete: function(XMLHttpRequest, textStatus){
					  headNoticeObj.meseageState();
				  },
				  error: function(xhr){
				  }
		  	});
			}
		}, 
		//根据所有消息的个数判断是否有小红点提示
		meseageState:function(){
			if(v6head.mesCount==0&&$("#msgalert").hasClass("new")){
				  $("#msgalert").removeClass("new");
			  }else if(v6head.mesCount>0&&!($("#msgalert").hasClass("new"))){
				  $("#msgalert").addClass("new");	  
			  }
		}
		
};

function getParmFormUrl(parmUrl) {
	var theRequest = new Object();
	if(parmUrl.indexOf("@:")!=-1){
		var str = parmUrl.substring(parmUrl.indexOf("@:")+2, parmUrl.length);
		strs = str.split(";");
		for ( var i = 0; i < strs.length; i++) {
			var n=strs[i].indexOf("=");
			theRequest[strs[i].substring(0,n)] = strs[i].substring(n+1,strs[i].length);
		}
	}
	return theRequest;
}

function headLoadDataFromBspOnError(){
    if(v6head.logout=="1"){
	   if(confirm("系统会话已失效，重新登录吗？")){
		  document.location.href="/"+v6head.bspcontent+"/logout";
	   }
    }	
}

function changeAppNoFlush(appId,appCode,appUrl,appIcon,appText){
	
}

function noicon(){
//	var img=event.srcElement;
//	img.src="/skin/icons/1.png";
//	img.onerror=null;
}
//读取cookie
function getCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]); 
    else 
        return null; 
}
//base64解码
function Base64() {
 
	// private property
	_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
 
	// public method for encoding
	this.encode = function (input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
		input = _utf8_encode(input);
		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output +
			_keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
			_keyStr.charAt(enc3) + _keyStr.charAt(enc4);
		}
		return output;
	}
 
	// public method for decoding
	this.decode = function (input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
		while (i < input.length) {
			enc1 = _keyStr.indexOf(input.charAt(i++));
			enc2 = _keyStr.indexOf(input.charAt(i++));
			enc3 = _keyStr.indexOf(input.charAt(i++));
			enc4 = _keyStr.indexOf(input.charAt(i++));
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
			output = output + String.fromCharCode(chr1);
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}
		output = _utf8_decode(output);
		return output;
	}
 
	// private method for UTF-8 encoding
	_utf8_encode = function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
 
		}
		return utftext;
	}
 
	// private method for UTF-8 decoding
	_utf8_decode = function (utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
		while ( i < utftext.length ) {
			c = utftext.charCodeAt(i);
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			} else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return string;
	}
}


