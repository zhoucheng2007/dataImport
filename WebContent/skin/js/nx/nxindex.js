$(document).ready(function()  {
initTools();
 initDaiBan(); 
 initNotice(); 
 initPersonalDisk();
 //initMail();
 initZhiBiao();
 initTobaccoReport();
 //指标子窗口  导航按钮绑定
 /*
$(".zhibiaotab-left").click(function(){
		$(".zhibiaotab-center").removeClass("active");
		$(".zhibiaotab-right").removeClass("active");
		$(this).addClass("active");
	});
$(".zhibiaotab-center").click(function(){
        $(".zhibiaotab-left").removeClass("active");
		$(".zhibiaotab-right").removeClass("active");
		$(this).addClass("active");
	});
$(".zhibiaotab-right").click(function(){
		 $(".zhibiaotab-left").removeClass("active");
		 $(".zhibiaotab-center").removeClass("active");
		 $(this).addClass("active");
	});
	*/

//工具导航按钮事件绑定
$("div[name='toolbutton']").click(function(){
	$("div[name='toolbutton']").removeClass("active");
	$(this).addClass("active");

	var s=$(this).attr("class").substr(12,1);

	if(s==1)initTool1();
	if(s==2)initTool2();
	if(s==3)initTool3();
	if(s==4)initTool4();
	//处理程序
	//var id=$(this).attr("id");
});

//待办信息tab导航按钮事件绑定
$("div[name='daiban']").click(function(){
	$("div[name='daiban']").removeClass("daibanactive");
	$(this).addClass("daibanactive");
	
			  
	//一下添加对应tab的按钮触发程序
});
$(function(){
   $(".toolstab").click();
//    $(".sysstab").click();
  });
$(".sysstab").click(function(){
$(".toolstab").removeClass("active");
$(this).addClass("active");
initCommT();
});
$(".toolstab ").click(function(){
$(".sysstab").removeClass("active");
$(this).addClass("active");
alert($(this).attr("class"));

});
//$(".toolstab").click(function(){
//	 $(".sysstab").removeClass("active");
//	 $(this).addClass("active");
//	 initCommT();
//});
//$(".sysstab").click(function(){
//	 $(".toolstab").removeClass("active");
//	$(this).addClass("active");
//	 initCommS();
//});
//门户日程
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
    	dayNames: ['周日','周一','周二','周三','周四','周五','周六'],
    	//dayNamesShort: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
    	dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
    	buttonText: {
    		prev: '&nbsp;&#9668;&nbsp;',
    		next: '&nbsp;&#9658;&nbsp;',
    		prevYear: '&nbsp;&lt;&lt;&nbsp;',
    		nextYear: '&nbsp;&gt;&gt;&nbsp;',
    		today: '',
    		month: 'month',
    		week: 'week',
    		day: 'day'
    	},
    	selectable: true,
    	selectHelper: true,
    	select: function(start, end, allDay) {
    		window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_self");
    	},
    	editable: true,
    	eventClick: function(calEvent, jsEvent, view) {
    		window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_self");
    	},
    	events: function(start, end, callback) {

    		$.getJSON("Schedulev6Cmd.cmd?method=mainSchedule&date="+parseInt(Math.random()*10000), {START: 



$.fullCalendar.formatDate(start,"yyyy-MM-dd"), END: $.fullCalendar.formatDate(end,"yyyy-MM-dd")

    			}, 			

    			function(result) {

    				$.each(result,function(i){

    					if(result[i].allDay=="false"){

    						result[i].allDay=false;

    					}

    					if(result[i].editable=="false"){

    						result[i].editable=false;

    					}

    				});

    				callback(result);

    				}

    			);

    		}

    	

    }); 





 $(function(){
	 $("#juanyan").click();
});

 	$("#juanyan").click(function(){
 		$(".tab10").removeClass("active");
 		$(this).addClass("active");
	});

	$("#anjian").click(function(){
		$(".tab10").removeClass("active");
		$(this).addClass("active");
	});

	 $("#anzhi").click(function(){
		 $(".tab10").removeClass("active");
		 $(this).addClass("active");
	});

});

function initTool1(){
	$.ajax({
		  type: "post",
		  url: "http://10.10.10.48/doc/index.php?r=/Doccenterapi/getModuleId",
		  data:{id:"1"},
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;
			 $(".toolslisttab1 p").text(jsonObj[0].MODULE_NAME);
			}
	});
		$.ajax({
		  type: "post",
		  url: "http://10.10.10.48/doc/index.php?r=/Doccenterapi/getPostBox",
		  data:{moduleId:"1", 		  
		  isDelete:"0", 
		  userId:user,
		  isPrivate:"0" },
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;
			 /*

			 	$("<span></span>").text(len)

			.appendTo(".tools-container");
			*/

			 for(var i=0;i<6;i++){

			 	if(jsonObj[i].DOC_NAME.length==0){}else{

			 		

			 		$("<li></li>")

				 .append(

					$("<a></a>").attr("href","").text(jsonObj[i].DOC_NAME)

								.attr("target","_blank")

								.attr("title",jsonObj[i].DOC_NAME)

				 )

				 .append(
						 $("<span></span>").text(jsonObj[i].CTIME)
				)
				 .appendTo("#tool-list");

				   $("#tool-list li:even").addClass("tbgeven"); 
				   $("#tool-list li:odd").addClass("tbgodd");
				}
			 }
		  },

		  complete: function(XMLHttpRequest, textStatus){
	  },

		  error: function(){

			

		  }

	  });

}
function initTool2(){

	$.ajax({
		  type: "post",
		  url: "http://10.10.10.48/doc/index.php?r=/Doccenterapi/getModuleId",
		  data:{id:"2"},
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;
			 $(".toolslisttab2 p").text(jsonObj[1].MODULE_NAME);
			}
	});
		$.ajax({
		  type: "post",
		  url: "http://10.10.10.48/doc/index.php?r=/Doccenterapi/getPostBox",
		  data:{moduleId:"2", 		  
		  isDelete:"0", 
		  userId:user,
		  isPrivate:"0" },
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;
			 /*

			 	$("<span></span>").text(len)

			.appendTo(".tools-container");
			*/

			 for(var i=0;i<6;i++){

			 	if(jsonObj[i].DOC_NAME.length==0){}else{

			 		

			 		$("<li></li>")

				 .append(

					$("<a></a>").attr("href","").text(jsonObj[i].DOC_NAME)

								.attr("target","_blank")

								.attr("title",jsonObj[i].DOC_NAME)

				 )

				 .append(
						 $("<span></span>").text(jsonObj[i].CTIME)
				)
				 .appendTo("#tool-list");

				   $("#tool-list li:even").addClass("tbgeven"); 
				   $("#tool-list li:odd").addClass("tbgodd");
				}
			 }
		  },

		  complete: function(XMLHttpRequest, textStatus){
	  },

		  error: function(){

			

		  }

	  });

}
function initTool3(){
	$.ajax({
		  type: "post",
		  url: "http://10.10.10.48/doc/index.php?r=/Doccenterapi/getModuleId",
		  data:{id:"3"},
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;
			 $(".toolslisttab3 p").text(jsonObj[2].MODULE_NAME);
			}
	});
		$.ajax({
		  type: "post",
		  url: "http://10.10.10.48/doc/index.php?r=/Doccenterapi/getPostBox",
		  data:{moduleId:"3", 		  
		  isDelete:"0", 
		  userId:user,
		  isPrivate:"0" },
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;
			 /*

			 	$("<span></span>").text(len)

			.appendTo(".tools-container");
			*/

			 for(var i=0;i<6;i++){

			 	if(jsonObj[i].DOC_NAME.length==0){}else{

			 		

			 		$("<li></li>")

				 .append(

					$("<a></a>").attr("href","").text(jsonObj[i].DOC_NAME)

								.attr("target","_blank")

								.attr("title",jsonObj[i].DOC_NAME)

				 )

				 .append(
						 $("<span></span>").text(jsonObj[i].CTIME)
				)
				 .appendTo("#tool-list");

				   $("#tool-list li:even").addClass("tbgeven"); 
				   $("#tool-list li:odd").addClass("tbgodd");
				}
			 }
		  },

		  complete: function(XMLHttpRequest, textStatus){
	  },

		  error: function(){

			

		  }

	  });

}
function initTool4(){
	$.ajax({
		  type: "post",
		  url: "http://10.10.10.48/doc/index.php?r=/Doccenterapi/getModuleId",
		  data:{id:"4"},
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;
			 $(".toolslisttab4 p").text(jsonObj[3].MODULE_NAME);
			}
	});
		$.ajax({
		  type: "post",
		  url: "http://10.10.10.48/doc/index.php?r=/Doccenterapi/getPostBox",
		  data:{moduleId:"4", 		  
		  isDelete:"0", 
		  userId:user,
		  isPrivate:"0" },
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;
			 /*

			 	$("<span></span>").text(len)

			.appendTo(".tools-container");
			*/

			 for(var i=0;i<6;i++){

			 	if(jsonObj[i].DOC_NAME.length==0){}else{

			 		

			 		$("<li></li>")

				 .append(

					$("<a></a>").attr("href","").text(jsonObj[i].DOC_NAME)

								.attr("target","_blank")

								.attr("title",jsonObj[i].DOC_NAME)

				 )

				 .append(
						 $("<span></span>").text(jsonObj[i].CTIME)
				)
				 .appendTo("#tool-list");

				   $("#tool-list li:even").addClass("tbgeven"); 
				   $("#tool-list li:odd").addClass("tbgodd");
				}
			 }
		  },

		  complete: function(XMLHttpRequest, textStatus){
	  },

		  error: function(){

			

		  }

	  });

}
//tools

function initTools(){
	
	initTool2();
	initTool3();
	initTool4();
	initTool1();
}
function getWork(){

	//alert("daiban init success");
	$.ajax({
		  type: "post",
		  url: "nxmianpage.cmd?method=getWork",
		  beforeSend: function(XMLHttpRequest){
		  		  $("#db-list").html("");
		  },
		  success: function(data, textStatus){
			  //alert(data);
			 var jsonObj=eval("("+data+")");
			 var maxnum;
			 //统计待办任务数
			 var len = jsonObj.length;
			for(var i=0;i<6;i++){      
				 	if(jsonObj[i].backlogTitle.length==0){
				 		return;
				 	}else{
				 		$("<li></li>")
					 .append(
						$("<a></a>").attr("href",jsonObj[i].backlogUrl).text(jsonObj[i].backlogTitle)
									.attr("target","_blank")
									.attr("title",jsonObj[i].backlogTitle)
					 )
					 .append(		 
						$("<span></span>").text(jsonObj[i].backlogDate)
					)
					 .appendTo("#db-list");
				 		$("<p class=\"laiyuan\"></p>").html("<img style='margin-top:12px' src='"+bPath+"/skin/img/nx/homepage/new.png' />") 
				 		.appendTo("#db-list li:eq("+i+")");
					   $("#db-list li:even").addClass("tbgeven"); 
				  $("#db-list li:odd").addClass("tbgodd");
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
function getZjin(){

	//alert("daiban init success");
	$.ajax({
		  type: "post",
		  url: "nxmianpage.cmd?method=getZjin",
		  beforeSend: function(XMLHttpRequest){
		  $("#db-list").html("");
		  },
		  success: function(data, textStatus){
			  //alert(data);
			 var jsonObj=eval("("+data+")");
			 var maxnum;
			 //统计待办任务数
			 var len = jsonObj.length;

			 
			for(var i=0;i<6;i++){      
				 	if(jsonObj[i].backlogTitle.length==0){
				 		return;
				 	}else{
				 		$("<li></li>")
					 .append(
						$("<a></a>").attr("href",jsonObj[i].backlogUrl).text(jsonObj[i].backlogTitle)
									.attr("target","_blank")
									.attr("title",jsonObj[i].backlogTitle)
					 )
					 .append(		 
						$("<span></span>").text(jsonObj[i].backlogDate)
					)
					 .appendTo("#db-list");
				 		$("<p class=\"laiyuan\"></p>").html("<img style='margin-top:12px' src='"+bPath+"/skin/img/nx/homepage/new.png' />")
				 		.appendTo("#db-list li:eq("+i+")");
					   $("#db-list li:even").addClass("tbgeven"); 
				  $("#db-list li:odd").addClass("tbgodd");
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
function getZhiliang(){

	//alert("daiban init success");
	$.ajax({
		  type: "post",
		  url: "nxmianpage.cmd?method=getZhiliang",
		  beforeSend: function(XMLHttpRequest){
		  		  $("#db-list").html("");
		  },
		  success: function(data, textStatus){
			  //alert(data);
			 var jsonObj=eval("("+data+")");
			 var maxnum;
			 //统计待办任务数
			 var len = jsonObj.length;

			 
			for(var i=0;i<6;i++){      
				 	if(jsonObj[i].backlogTitle.length==0){
				 		return;
				 	}else{
				 		$("<li></li>")
					 .append(
						$("<a></a>").attr("href",jsonObj[i].backlogUrl).text(jsonObj[i].backlogTitle)
									.attr("target","_blank")
									.attr("title",jsonObj[i].backlogTitle)
					 )
					 .append(		 
						$("<span></span>").text(jsonObj[i].backlogDate)
					)
					 .appendTo("#db-list");
				 		$("<p class=\"laiyuan\"></p>").html("<img style='margin-top:12px' src='"+bPath+"/skin/img/nx/homepage/new.png' />")
				 		.appendTo("#db-list li:eq("+i+")");
					   $("#db-list li:even").addClass("tbgeven"); 
				  $("#db-list li:odd").addClass("tbgodd");
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
//待办信息初始化

function initDaiBan(){
	//alert("daiban init");
	$(".daibantab1").addClass("daibanactive");
	//alert("daiban init success");
	$.ajax({
		  type: "post",
		  url: "nxmianpage.cmd?method=queryDaiBan",
		  beforeSend: function(XMLHttpRequest){
		  		  $("#db-list").html("");
		  },
		  success: function(data, textStatus){
			  //alert(data);
			 var jsonObj=eval("("+data+")");
			 var maxnum;
			 //统计待办任务数
			 var len = jsonObj.length;
			 
			for(var i=0;i<6;i++){      
				 	if(jsonObj[i].backlogTitle.length==0){
				 		return;
				 	}else{
				 		$("<li></li>")
					 .append(
						$("<a></a>").attr("href",jsonObj[i].backlogUrl).text(jsonObj[i].backlogTitle)
									.attr("target","_blank")
									.attr("title",jsonObj[i].backlogTitle)
					 )
					 .append(		 
						$("<span></span>").text(jsonObj[i].backlogDate)
					)
					 .appendTo("#db-list");
				 		$("<p class=\"laiyuan\"></p>").html("<img style='margin-top:12px' src='"+bPath+"/skin/img/nx/homepage/new.png' />")
				 		.appendTo("#db-list li:eq("+i+")");
					   $("#db-list li:even").addClass("tbgeven"); 
				  $("#db-list li:odd").addClass("tbgodd");
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

/*待办任务  分页查询功能*/
function checkdb(pageid,maxnum,jsonObj,obj){
	alert(pageid);
	var i=5*(pageid-1);
	if(maxnum>(5*i)) maxnum=5*i;
	for(;i<maxnum;i++){      
	 	if(jsonObj[i].backlogTitle.length==0){
	 		return;
	 	}else{
	 		$("<li></li>")
					 .append(
						$("<a></a>").attr("href",jsonObj[i].backlogUrl).text(jsonObj[i].backlogTitle)
									.attr("target","_blank")
									.attr("title",jsonObj[i].backlogTitle)
					 )
					 .append(		 
						$("<span></span>").text(jsonObj[i].backlogDate)
					)
					 .appendTo("#db-list");
				 		$("<p class=\"laiyuan\"></p>").html("<img style='margin-top:12px' src='"+bPath+"/skin/img/nx/homepage/new.png' />")
				 		.appendTo("#db-list li:eq("+i+")");
					   $("#db-list li:even").addClass("tbgeven"); 
				  $("#db-list li:odd").addClass("tbgodd");
		}
	 }
  
	//改变按钮对饮的背景颜色
	$(".db-page").removeClass(".db-active");
	$(obj).addClass(".db-active");
}

/*常用功能*/
function initCommT(){
	var html='';
	$.ajax({
		  type: "post",
		  url: "pwssd.cmd?method=commSystem",
		  beforeSend: function(XMLHttpRequest){
			  $("#CommSys-list").html("");
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval(data);
			  var len = jsonObj.length;
			  if(jsonObj.length==0) return;
			 $.each(jsonObj,function(i){
				 	if(i>8){
				 	return;
				 	}
				 	var n=i+1;
				 	if(n>7){
				 		n=i-6;
				 		}
				 $("<li></li>")
				 .click(function(){
					var href="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&appType=mymenu&menuId="+jsonObj[i].MENU_ID+"&appSSO="+jsonObj[i].APP_TYPE;
					window.open(href,"_self");
				 })
				 .append(
				 $("<a></a>")
				 .append(
				 $("<div class=\"CommSys-img\"></div>").append(
						 $("<img />")
					  .attr({"src":"/portal/skin/img/sd/"+n+".png"})
					  )
				 )
				 .append(
					$("<span></span>") .text(jsonObj[i].MENU_NAME).attr("title",jsonObj[i].MENU_NAME)
				 )
				 )
				 .appendTo("#CommSys-list");
			 });
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
//初始化指标
function initZhiBiao(){
	var objlist;//指标列表
	//$(".zhibiaotab1").addClass("zhibiaoactive");
	//$(".zhibiaotab").html("");
	//查询数据库获得对应的指标列表
	//查询结果临时用list代替
	
	var list=[{"zbn":"财务A"},{"zbn":"财务B"},{"zbn":"财务C"},{"zbn":"财务D"},{"zbn":"财务E"}];
	 $.ajax({
		type:"post",
		url:"platform.cmd?method=gsonreturn",//需要绑定与实际指标查询的cmd
		beforeSend:function(XMLHttpRequest){
		 
	 },
	 success:function(data,textStatus){
		// alert("begin initzhibao");
		 data=list;
		 objlist=eval(data);
		// alert(objlist.length);
		 //showDialogP("window",data, "自定义指标", "400");
		 
	 },
	 complete: function(XMLHttpRequest, textStatus){
	  },
	  error: function(){			
	  }
	 });
	 $(".zhibiaotab").append("<div class='zhibiaotab1 zhibiaoactive' id='11' name='zhibiaobutton'><p>营销a</p></div><div id='21' class='zhibiaotab2' name='zhibiaobutton'><p>财务B</p></div> ");
	 $(".zhibiao-container").html("<img src='"+bPath+"/skin/img/nx/homepage/z/11.png' style='width:470px;height:233px'/>");
	 //为动态生成指标导航添加click事件
	 $("div[name='zhibiaobutton']").live("click",function(){
			$("div[name='zhibiaobutton']").removeClass("zhibiaoactive");
			$(this).addClass("zhibiaoactive");
			$(".zhibiao-container").html("<img src='"+bPath+"/skin/img/nx/homepage//z/"+$(this).attr('id')+".png' style='width:470px;height:233px'/>");
			//处理程序
			//var id=$(this).attr("id")
		});
		
	 
}
//自定义指标
function zhibiaodefine(){
	 //showPopWin('选择指标','/portal/portal/nxmianpage.cmd?method=getZhiBiaoDetail',400,400,true,true);
	 showPopWin('选择指标','/portal/portal/nxmianpage.cmd?method=getZhiBiaoDetail', 400,400,updatetab,true,true);//弹出帮助框
}
//更新指标导航
function updatetab(returnVal){
	var s="";
	for(var i=0;i<returnVal.length;i++){
		var j=i+1;
		if(i==4) break;
		if(j==1){
			s+="<div id='"+returnVal[i].id +"' class='zhibiaotab"+j+" zhibiaoactive' name='zhibiaobutton'><p>"+returnVal[i].name+"</p></div>";
			$(".zhibiao-container").html("<img src='"+bPath+"/skin/img/nx/homepage//z/"+returnVal[i].id+".png' style='width:470px;height:233px'/>");
			continue;
		}
		s+="<div id='"+returnVal[i].id +"' class='zhibiaotab"+j+"' name='zhibiaobutton'><p>"+returnVal[i].name+"</p></div>";
	}
	$(".zhibiaotab").html(s);
}
/*常用系统*/
function initCommS(){
	var html='';
	$.ajax({
		  type: "post",
		  url: "pwssd.cmd?method=commS",
		  beforeSend: function(XMLHttpRequest){
			  $("#CommSys-list").html("");
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval("("+data+")");
			  var len = jsonObj.length;
			  if(jsonObj.length==0) return;
			 $.each(jsonObj,function(i){
				 	if(i>8)
				 	return;
				 	var n=i+1;
				 	if(n>7){
				 		n=i-6;
				 		}
				 $("<li></li>")
				 .click(function(){
					var href="/portal/AuthenService?USERID=" +  jsonObj[i].USER_ID+ "&APP=" +  jsonObj[i].APP_CODE + "&RESOURCE=" + jsonObj[i].NOTE;
					window.open(href,"_blank");
				 })
				 .append(
				 $("<a></a>")
				 .append(
				 $("<div class=\"CommSys-img\"></div>").append(
						 $("<img />")
					  .attr({"src":"/portal/skin/img/sd/"+n+".png"})
					  )
				 )
				 .append(
					$("<span></span>") .text(jsonObj[i].APP_NAME).attr("title",jsonObj[i].APP_NAME)
				 )
				 )
				 .appendTo("#CommSys-list");
			 });
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
//简报
function initTobaccoReport(){
		$.ajax({

		  type: "post",
         //开发人员自己定义的url，测试用
		  url: "platform.cmd?method=gsonreturn",//"tobaccoReport.cmd?method=dataDisplay",

		  beforeSend: function(XMLHttpRequest){

			  

		  },

		  success: function(data, textStatus){
                 //alert("jianbao is accepted successfully")
				 data="2014年3月1日至昨日（3月26日） 卷烟销售量7万箱，销售额293400万元，单箱值4万元";
                 $("#jianb").append(data);
                 
		  },

		  complete: function(XMLHttpRequest, textStatus){

			  ///$('#slider').nivoSlider();

		  },

		  error: function(){

			alert("简报获取错误！");

		  }

	  });
}

//公告通知  notice初始化

function initNotice(){
	var jsonObj;//公告通知全局变量，在页面加载时获得
	$.ajax({

		  type: "post",

		  url: "nxmianpage.cmd?method=getNotice",

		  beforeSend: function(XMLHttpRequest){  

		  },

		  success: function(data, textStatus){
			  
			 jsonObj=eval("("+data+")");
			 
			 $(".noticenum").append("1");

			 $(".prev-button").click(function(){
				
				 shownotice(jsonObj,-1);
			 });
			 $(".next-button").click(function(){
				 shownotice(jsonObj,1);
			 });
			 shownotice(jsonObj,0);
			// var str=jsonObj[i].title;
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  ///$('#slider').nivoSlider();
		  },
		  error: function(){
		  }

	  });

}
//公告通知   分条显示
function shownotice(jsonObj,towards){
	var nowid=$(".noticenum").html();
	
	if(towards==0) nowid=0;
	if(towards==-1) nowid=nowid-2; 
	if(nowid<1) nowid=0;
	if(nowid>=jsonObj.length-1) nowid=jsonObj.length-1;
	//alert("将要查询"+nowid);
	$("#notice-list").html(' <marquee  id="notice-marquee" onmouseover="this.stop()"  scrollamount="2"  onmouseout="this.start()"  direction=up style="width:280;height:165px;"></marquee> ');
	

	$("<li></li>")
	 .append(
			 $("<span></span>").text(jsonObj[nowid].noticeTitle+"      "+jsonObj[nowid].noticeDate.substring(0,10))
	 )
	 .append(
		$("<a></a>").attr("href",jsonObj[nowid].noticeUrl).html(jsonObj[nowid].noticeContent)//用html格式jsonObj[nowid].title   text
					.attr("target","_blank")
					.attr("title",jsonObj[nowid].noticeTitle)
	 )
	 .appendTo("#notice-marquee");
	$(".noticenum").html("");
	$(".noticenum").html(parseInt(nowid)+1);

}


//邮箱

function initMail(){
	$.ajax({
		  type: "post",
		  url: "pwssd.cmd?method=getMail",
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
			 var len = jsonObj.length;
      for(var i=0;i<len;i++){
    	    num= jsonObj[i].newmsgcnt;
    		$("<span></span>").text(num)
			.appendTo(".mailnum");
		    $("#mailAddress").attr("href",jsonObj[i].url).attr("target","_blank")
			 .appendTo(".panels-right");
			$("#mailA").attr("href",jsonObj[i].url).attr("target","_blank");
				}
		  },

		  complete: function(XMLHttpRequest, textStatus){
			  ///$('#slider').nivoSlider();
		  },

		  error: function(){


		  }

	  });

}

//个人网盘

function initPersonalDisk(){
//alert("11111");	
    var userId=$("#userId").val();
//    var userId="ZHANGXINGWEN_JN";
//   alert(userId);
	$.ajax({
		 
		  type: "post",
       	 data: "userId="+userId.toUpperCase(),      
		  url: "/doc/index.php?r=/Doccenterapi/getDiskByUid",
		  beforeSend: function(XMLHttpRequest){
		//  alert("2222"); <div class="SkyDrive"  ><div class="all-space"  style="width: 200px; height: 16px; margin-top: 10px; margin-left: 10px;"><div class="use-space" style="margin:;height:16px;width:20%;background-color:#6ED9AF"></div></div><div  class="spacenum" style="width: 100px; height: 18px; margin-left: 30px; margin-top: 5px;float:left;">400M/1G</div><div  class="diskin" style="width: 50px; height: 18px; margin-top: 5px; margin-left: 10px; float: left;"><span> 进入网盘</span></div></div>	
		  },
		  success: function(data, textStatus){
//			  alert(data);
			 var jsonObj=eval("("+data+")");
			var len = jsonObj.length;
			 for(var i=0;i<len;i++){
                    if(jsonObj[i].owner_id==userId){
                    	  $(".SkyDrive").append("<div class=\"all-space\"  style=\"width: 178px; height: 12px; margin-top: 10px; margin-left: 20px;\">"
                    			  +"<div id=\"forword\"></div>"
                    			  +"<div id=\"use-space\" style=\"float:left;height:12px;background-color:#6ED9AF\"></div>"
                    			  +"<div id=\"after\"></div>"
                    			  +"</div>"
                    			  +"<div  class=\"spacenum\" style=\"width: 120px; height: 18px; margin-left: 10px; margin-top: 5px;float:left;font-family: Microsoft Yahei; \"></div>"
                    			  +"<div  class=\"diskin\" style=\"width: 60px; height: 18px; margin-top: 5px; margin-left: 25px; float: left;font-family: Microsoft Yahei; \">"
                    			  +"<span> 个人网盘</span></div>");
                    	  var num=GetPercent(jsonObj[i].disk_use_space, jsonObj[i].disk_max_space);
                    	  document.getElementById('use-space').style.width=num;
                    		$("<span></span>").text(jsonObj[i].use_space+"/"+jsonObj[i].max_space).appendTo(".spacenum");
                    }else{
                    	return;
		  }
			 }
			 },

		  complete: function(XMLHttpRequest, textStatus){
		  },

		  error: function(){
		  }

	  });

}

function GetPercent(num, total) { 
	num = parseFloat(num); 
	total = parseFloat(total); 
	if (isNaN(num) || isNaN(total)) { 
	return "-"; 
	} 
	return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00 + "%"); 
	} 



  $(function(){
             var oPic=$('#slider_pic').find('ul');
             var oImg=oPic.find('li');
             var oLen=oImg.length+1;
             var oLi=oImg.width();
             var prev=$("#prev");
             var next=$("#next");
             oPic.width(oLen*204);//计算总长度
             var iNow=0;
             var iTimer=null;
             prev.click(function(){
                  if(iNow>0){  
                   iNow--;
                  }
                 ClickScroll();
             });
             next.click(function(){
                 if(iNow<oLen-4){ 
                     iNow++;
                 }
                 ClickScroll();
             });



             function ClickScroll(){
                 iNow==0? prev.addClass('no_click'): prev.removeClass('no_click');
                 iNow==oLen-3?next.addClass("no_click"):next.removeClass("no_click");
                 oPic.animate({left:-iNow*104});

             }
         });

