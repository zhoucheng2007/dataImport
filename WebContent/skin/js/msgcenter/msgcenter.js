$(document).ready(function(){
	var iframe=$("#main-iframe");
	$(".menu-list li").click(function(){
		var $this=$(this);
		$this.addClass("current").siblings().removeClass("current");
		var src=$this.attr("link");
		iframe.attr("src",src);
	});
	$(".menu-list li .new").click(function(event){
		
		var $this=$(this);
		var src=$this.attr("link");
		iframe.attr("src",src);
		event.stopPropagation();
	});
	
	var msgType=$("#msgType").val();
	if(msgType!=""){
		$("#"+msgType).closest("li").click();
	}
	
	resetHeight();
	
	$(window).resize(function(){
		resetHeight();
	});
	setTimeout(function(){
		getNotice();
	},500);
	

});
function getNotice(){
	for(var one in noticeObj){
		noticeObj[one]();
	};
	setInterval(function(){
		for(var one in noticeObj){
			if(one != "getWarn"){
				noticeObj[one]();
			}
		}
	},10000);
}
var noticeObj={
		//获取部门通知的消息个数
		noticeData:function(){
			$.ajax({
			  type: "post",
			  url:"/portal/portal/msgcenterbase.cmd?method=queryMsg",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				   var $target=$("#m5");
				   $target.siblings(".num").remove();
				   if(data.length<5&&data>0){
					   $target.after("<span class='num' >（"+data+"）</span>");
				   }
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(xhr){
			  }
		  	});
		},
		//获取at我的信息个数
		atmeData:function(){
			var userId=$("#userId").val();
			var b = new Base64();
			var userIdForSns=b.encode("123456789_"+userId+"@1234567890");
			var url="/sns/index.php?app=api&mod=Statuses&act=getAtMe&p=2&sid="+userIdForSns;
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
				   var $target=$("#m7");
				   $target.siblings(".num").remove();
				   if(obj.count&&obj.count>0){ 
					   $target.after("<span class='num' >（"+obj.count+"）</span>");
				   }
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(xhr){
			  }
		  	});
		},
		getUnDoData:function(){
			$.ajax({
			  type: "post",
			  url:"/portal/portal/msgcenterbase.cmd?method=queryDaiban",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				   var $target=$("#m3");
				   $target.siblings(".num").remove();
				   if(data.length<5&&data>0){
					   $target.after("<span class='num' >（"+data+"）</span>");
				   }
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(xhr){
			  }
	  		});
		},
		//获取业务消息
		getBusinessData:function(){
			$.ajax({
			  type: "post",
			  url:"/portal/portal/msgcenterbase.cmd?method=queryBusimsg",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				   var $target=$("#m4");
				   $target.siblings(".num").remove();
				   if(data.length<5&&data>0){ 
					   $target.after("<span class='num' >（"+data+"）</span>");
				   }
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(xhr){
			  }
	  		});
		},
		//未读公司通告数
		getUnReadAnnouncement:function(){
			$.ajax({
			  type: "post",
			  url:"/portal/portal/msgcenterbase.cmd?method=queryComAnnouncement",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				  var $target=$("#m1");
				  $target.siblings(".num").remove();
				  if(data.length<5&&data>0){
					  $target.after("<span class='num' >（"+data+"）</span>");
				  }
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(xhr){
			  }
	  		});
		},
		getMyUnReadMail:function(){
			$.ajax({
				  type: "post",
				  url:"/portal/portal/msgcenterbase.cmd?method=getMail",
				  beforeSend: function(XMLHttpRequest){
				  },
				  success: function(data, textStatus){
					  var $target=$("#m2");
					  $target.siblings(".num").remove();
					  if(data.length<5&&data>0){
						  $target.after("<span class='num' >（"+data+"）</span>");
					  }
				  },
				  complete: function(XMLHttpRequest, textStatus){
				  },
				  error: function(xhr){
				  }
		  	});
		},
		getWarn:function(){
			$.ajax({
				  type: "post",
				  url:"/portal/portal/msgcenterbase.cmd?method=queryWarn",
				  beforeSend: function(XMLHttpRequest){
				  },
				  success: function(data, textStatus){
					  var $target=$("#m6");
					  $target.siblings(".num").remove();
					  if(data.length<5&&data>0){
						  $target.after("<span class='num' >（"+data+"）</span>");
					  }
				  },
				  complete: function(XMLHttpRequest, textStatus){
				  },
				  error: function(xhr){
				  }
		  	});
		}
};
function resetHeight(){
	var screenHeight=$(window).height();
	var contentTop=$("#mc-content").offset().top;
	var height=screenHeight-contentTop;
	$("#menu").height(height);
	$("#main").height(height);
	$("#main-iframe").height(height-20);
}
