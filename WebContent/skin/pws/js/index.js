var chart;
var skin=getSkin("skin");
function getSkin(Name)    
{   var search = Name + "=";  
	if(document.cookie.length > 0)   
	{   
		var offset = document.cookie.indexOf(search);
        if(offset != -1)    
		{   offset += search.length;
			var end = document.cookie.indexOf(";", offset) ;
			if(end == -1) end = document.cookie.length;  
			return unescape(document.cookie.substring(offset, end)); 
		}   
		else return null
	}  
}
$(document).ready(function()  {
	initDaiban();
	initUserPhoto();
	initCommSys();
	initRecentSys();
	initCollectSys();
		$("#userinfo b").click(function(){
			window.location.href('/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&appType=record&menuId=BSPV60150&text=用户管理&menuUrl=/jsp/bsp/user/user_edit.jsp?userId|'+user);
				});
	$(".doc-tab-ul li").click(function(){
		$(".doc-tab-ul li").removeClass("active");
		$(this).addClass("active");
	});
	
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
		window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_self");
	},
	editable: true,
	eventClick: function(calEvent, jsEvent, view) {
		window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_self");
	},
	events: function(start, end, callback) {
		$.getJSON("Schedulev6Cmd.cmd?method=mainSchedule", {START: $.fullCalendar.formatDate(start,"yyyy-MM-dd"), END: $.fullCalendar.formatDate(end,"yyyy-MM-dd")
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
	
})

       
});
function nouserimg(){
	$("#userinfo img").attr("src","/sns/public/themes/newstyle/images/user_pic_middle.gif");
}
function initUserPhoto(){
	$.ajax({
		  type: "post",
		  url: "/sns/index.php?app=home&mod=User&act=getUserHeadImg&userId="+user,
		  beforeSend: function(XMLHttpRequest){
			 
		  },
		  success: function(data, textStatus){
		
				$("#userinfo img").attr("src",data);
		  },
		  complete: function(XMLHttpRequest, textStatus){

		  },
		  error: function(){			
		  }
	  });
}

function initCommSys(){
	var html='';
	$.ajax({
		  type: "post",
		  url: "pwsbase.cmd?method=commSystem",
		  beforeSend: function(XMLHttpRequest){
			  $(".sys-list").html("");
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval(data);
			  $.each(jsonObj,function(i){
				 var j = i+1;
				 if(i>=10){
				 	j=5;
				 	}
				 $("<li></li>")
				 .click(function(){	
					var href="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&menuId="+jsonObj[i].MENU_ID;
					window.open(href,"_self");
				 })
			      .hover(function() {
			    	$('img',this).attr("src","/skin/"+skin+"/pws/img/3-2.png");
                  },function() {
                	  $('img',this).attr("src","/skin/"+skin+"/pws/img/3.png");
                	   })
				 .append(
				 $("<a></a>")
				 .append(
					$("<img/>")
					  .attr({"src":"/skin/"+skin+"/pws/img/3.png"})
					  
				 )
				 .append(
					$("<span></span>")
					.text(jsonObj[i].MENU_NAME)
				 )
				 .append("<b></b>")
				 .append("<s></s>")
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

function initCollectSys(){
	$.ajax({
		  type: "post",
		  url: "pwsbase.cmd?method=commSystem",
		  beforeSend: function(XMLHttpRequest){			 
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval(data);
			  len=jsonObj.length;
			  $(".collect").text("收藏 "+len).click(function(){
			  	$(this).addClass("menu-button-active");
				  $(".recently").removeClass("menu-button-active");
				  $(".sys-list").html("");
				  $.each(jsonObj,function(i){
					  var j = i+1;
					   if(i>=10){
						 j=5;
					  }
					  $("<li></li>")
					  	.click(function(){	
					  		var href="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&menuId="+jsonObj[i].MENU_ID+"&menuUrl="+jsonObj[i].MENU_URL+"&text="+jsonObj[i].MENU_NAME;
							window.open(href,"_self");
						 })
						 .hover(function() {
			    	     $('img',this).attr("src","/skin/"+skin+"/pws/img/3-2.png");
                        },function() {
                	     $('img',this).attr("src","/skin/"+skin+"/pws/img/3.png");
                	   })
						 .append(
						 $("<a></a>")
						 .append(
							$("<img/>")
							.attr({"src":"/skin/"+skin+"/pws/img/3.png"})
						 )
						 .append(
							$("<span></span>")
							.text(jsonObj[i].MENU_NAME)
						 )
						 .append("<b></b>")
						 .append("<s></s>")
						 )
						 .appendTo(".sys-list");
					 });
			  });
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}

function initRecentSys(){
	$.ajax({
		  type: "post",
		  url: "pwsbase.cmd?method=recordSystem",
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){	
			  var jsonObj=eval(data);
			  len=jsonObj.length;
			  $(".recently").text("最近 "+len).click(function(){
				  $(this).addClass("menu-button-active");
				  $(".collect").removeClass("menu-button-active");
				  $(".sys-list").html("");
				  $.each(jsonObj,function(i){
					  var j = i+1;
					  if(i>=10){
						  return;
					  }
					  
					  $("<li></li>")
					  	.click(function(){	
					  		var href="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&appType=record&menuId="+jsonObj[i].MENU_ID;
							window.open(href,"_self");
						 })
						.hover(function() {
			    	   $('img',this).attr("src","/skin/"+skin+"/pws/img/3-2.png");
                        },function() {
                	  $('img',this).attr("src","/skin/"+skin+"/pws/img/3.png");
                	   })
						 .append(
						 $("<a></a>")
						 .append(
							$("<img/>")
							  .attr({"src":"/skin/"+skin+"/pws/img/3.png"})
						 )
						 .append(
							$("<span></span>")
							.text(jsonObj[i].MENU_NAME)
						 )
						 .append("<b></b>")
						 .append("<s></s>")
						 )
						 .appendTo(".sys-list");
					 });
			  });
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
function initDaiban(){
	$.ajax({
		  type: "post",
		  url: "appCenterInitCmd.cmd?method=queryDaiban",
		  beforeSend: function(XMLHttpRequest){			 
		  },
		  success: function(data, textStatus){
			   var obj=eval("("+data+")");
			   var daibanStr=[];
			   if(obj.daiban==0){
			   	daibanStr.push("<li><div><a  id='daibana'  href='/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&appType=record&menuId=000000000000000000000000005212&text=任务列表&menuUrl=/bpm/daibanwithmini_query_init.cmd ' >您目前没有待办任务");
			   	$(".work-list").html(daibanStr.join(""));
			   	}else
			   { daibanStr.push("<li><div><a  id='daibana' href='/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&appType=record&menuId=000000000000000000000000005212&text=任务列表&menuUrl=/bpm/daibanwithmini_query_init.cmd ' >您还有<span>"+obj.daiban+"</span>个待办任务</a></div></li>");
			  	$(".work-list").html(daibanStr.join(""));
			   }
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}