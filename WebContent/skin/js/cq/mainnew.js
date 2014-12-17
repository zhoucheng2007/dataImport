$(document).ready(function(){
	//
	
	//initIsHapping();//微博【正在发生】
//	initNotices();//通知公告
//	initDocument();//局发文件
//	initEcoReport();//经济运行
	initPicNews();
	initWordNews();
	initSubject();//专题频道
	initSitlink();//行业连接
	
	initTabs();
	
	initContactTree();//通讯录
	initTodaySchedule();
	initBanner();//7个报表	
	//initWork();//待办
	//initWorkNum();
	//initMZ();//办事公开
	//initMail();//邮件
	/*initStationLetter();//站内信*/
	//readRss("http://10.11.2.21:8083/eap/335.news.rss");
	/*

	
		
	
	
	
	initWordNews();以前的文字新闻
	//initWeather();初始化天气，没发现有用。
	
	//initProcedure();//我的流程

	//initPoliticsNew();//时政要闻
		
	
	//*/

	
	//initHeadImage();//头像初始化
	initDisk(); //网盘
	
	
	
//	$("#notice").click(function(){
//		$(this).addClass("active");
//		$("#meeting").removeClass("active");
//		$("#affairs").removeClass("active");
//		initNotices();
//	})
//	$("#meeting").click(function(){
//		$(this).addClass("active");
//		$("#notice").removeClass("active");
//		$("#affairs").removeClass("active");
//		var url = "http://10.11.2.160:9080/ycportal/webpublish/contentblock.block.862.view.list.newslist";
//		$("#moretz").attr("href",url);
//		initMeeting();
//	})
//	$("#affairs").click(function(){
//		$(this).addClass("active");
//		$("#notice").removeClass("active");
//		$("#meeting").removeClass("active");
//		var url= "http://10.11.2.160:9080/ycportal/webpublish/contentblock.block.1072.view.list.newslist";
//		$("#moretz").attr("href",url);
//		initAffairs();
//	})
	//站内信
	$("#myLetterStation").click(function(){
		/*var url= "/portal/portal/msgcenterbase.cmd?method=queryPageInit&msgType=m2";
		window.location.target= "_self"; 
		window.location.href(url);*/
	})
	
	$("#document").click(function(){
		$(this).addClass("active");
		$("#dynamic").removeClass("active");
		$("#pointNew").removeClass("active");
		initDocument();
	})
	$("#dynamic").click(function(){
		$(this).addClass("active");
		$("#document").removeClass("active");
		$("#pointNew").removeClass("active");
		var url = "http://10.11.2.160:9080/ycportal/webpublish/contentblock.block.863.view.list.newslist";
		$("#morezw").attr("href",url);
		initDynamic();
	})
	$("#pointNew").click(function(){
		$(this).addClass("active");
		$("#document").removeClass("active");
		$("#dynamic").removeClass("active");
		var url="http://10.11.2.160:9080/ycportal/webpublish/contentblock.block.860.view.list.newslist";
		$("#morezw").attr("href",url);
		initPointNew();
	})
	
	$("#ecoReport").click(function(){
		$(this).addClass("active");
		$("#media").removeClass("active");
		$("#academic").removeClass("active");
		initEcoReport();
	})
	$("#media").click(function(){
		$(this).addClass("active");
		$("#ecoReport").removeClass("active");
		$("#academic").removeClass("active");
		var url = "http://10.11.2.160:9080/ycportal/webpublish/contentblock.block.857.view.list.newslist";
		$("#morejj").attr("href",url);
		initMedia();
	})
	
	$("#academic").click(function(){
		$(this).addClass("active");
		$("#ecoReport").removeClass("active");
		$("#media").removeClass("active");
		var url = "http://10.11.2.160:9080/ycportal/webpublish/contentblock.block.867.view.list.newslist";
		$("#morejj").attr("href",url);
		initAcademic();
	})
	$("#needdo").click(function(){
		var url="pwscq.cmd?method=moreBacklog";
		window.open(url,"_blank");
	})
	//待办链接的页面
	/*$("#needdo").click(function(){
		//var url= "/cqpageinitcmd.cmd?method=queryMoreBacklog&sid="+document.getElementById("userId").value;
		//window.location.target= "_blank"; 
		//window.location.href(url);
	})
	$("#done").click(function(){
		initWorkDone();
	})
	$("#daiyue").click(function(){
		$("#needdo").removeClass("active");
		$(this).addClass("active");
		initDaiYue();
	})*/
	/*$("#needdo").click(function(){
		$("#daiyue").removeClass("active");
		$(this).addClass("active");
		initWork();
	})
	$("#done").click(function(){
		initWorkDone();
	})
	$("#daiyue").click(function(){
		$("#needdo").removeClass("active");
		$(this).addClass("active");
		initDaiYue();
	})
	$(".search-tab-btn").click(function(){
		$(".search-tab-btn").removeClass("active");
		$(this).addClass("active");
		var id=$(this).attr("id");
		if(id=="mysit"){
			$('#mysitshe').css("display","block");
			$('#mysitshedata').css("display","none");	
		}else {
			$('#mysitshedata').css("display","block");
			$('#mysitshe').css("display","none");	
		}
	})*/
	//banner条
	$("#banner-content").roundabout();
	$(".banner-btn").click(function(){
		$(".banner-btn").removeClass("active");
		$(this).addClass("active");
		var refId=$(this).attr("refId");
		$("#"+refId).click();
	})
	//$(".news-list-container").css("height","175px")
	$("#RssNew").mouseout(function(){
		$('#RssNew').css("display","none");
	})
	$("#RssNew").mouseover(function(){
		$('#RssNew').css("display","block");
	})
	$("#todaySchedule").mouseout(function(){
		$('#todaySchedule').css("display","none");
	})
	$("#todaySchedule").mouseover(function(){
		$('#todaySchedule').css("display","block");
	})
	$("#orgselectboxes").change(function(){
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
	$("#szyw").click(function(){
		var url="http://10.11.2.21:8083/portal/main";
		window.open (url,'','top=0,left=0,toolbar=no,menubar=no,scrollbars=yes resizable=yes,location=yes, status=no') ;
	})
	$("#mzgk").click(function(){
		var url="http://10.11.2.21:8083/portal/main";
		window.open (url,'','top=0,left=0,toolbar=no,menubar=no,scrollbars=yes resizable=yes,location=yes, status=no') ;
	})
	$("#myProcedure").click(function(){
		var url= "/portal/portal/cqpersonwork.cmd?method=goPersonWork";
		window.location.target= "_self "; 
		window.location.href(url);
	})
	$("#ishapping").click(function(){
		$("#atme").removeClass("active");
		$(this).addClass("active");
		initIsHapping();
	})
	$("#atme").click(function(){
		$("#ishapping").removeClass("active");
		$(this).addClass("active");
		initAtme();
	})
	$("#selfimg").click(function(){
		showDialogP();
	})
	//网盘
	$("#selfWorkPlatform-bottom").click(function(){
		var url= "http://10.11.2.23/doc/index.php?r=/home/indexnetdisk";
		window.location.target= "_self "; 
		window.location.href(url);
	})
	//跳到个人工作台
	$("#toSelfWorkPlat").click(function(){
		var url= "/portal/portal/cqpersonwork.cmd?method=goPersonWork";
		window.location.target= "_self "; 
		window.location.href(url);
	})
	/**
	$("#calendar").click(function(){
		var url= "/portal/portal/Schedulev6Cmd.cmd?method=queryPage";
		window.location.target= "_self "; 
		window.location.href(url);
	})**/

	//日程
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
    	// dayNamesShort: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
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
    		window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_blank");
    	},
    	editable: true,
    	eventClick: function(calEvent, jsEvent, view) {
    		window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_blank");
    	},
    	events: function(start, end, callback){
    		$.getJSON("Schedulev6Cmd.cmd?method=mainSchedule&date="+parseInt(Math.random()*10000), {START:$.fullCalendar.formatDate(start,"yyyy-MM-dd"), END: $.fullCalendar.formatDate(end,"yyyy-MM-dd")}, 			
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
		$("#dongtai").click();
	});
    $("#juanyan").click(function(){
		$(".tab10").removeClass("active");
		$(this).addClass("active");
		// document.getElementById("zhibiaoiframe").src=""
	})
	$("#anjian").click(function(){
		$(".tab10").removeClass("active");
		$(this).addClass("active");
		// document.getElementById("zhibiaoiframe").src=""
	})
	$("#anzhi").click(function(){
		$(".tab10").removeClass("active");
		$(this).addClass("active");
		// document.getElementById("zhibiaoiframe").src=""
	})
	 var iWidth=400;
	 var iHeight=300;
	 //获得窗口的垂直位置
	 var iTop = (window.screen.availHeight-30-iHeight)/2;  
	 //获得窗口的水平位置
	 var iLeft = (window.screen.availWidth-10-iWidth)/2;  
	$('#pup_message').click(function(){
		window.open('message.html','newwindow','height='+iHeight+',width='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no' );
		return false;
	});
})
//头像的初始化
function showDialogP() {
	var user = document.getElementById("userId").value;
	showPopWin('个人头像','/portal/portal/cqUserCenter.cmd?method=getDetail&userId='+user.toUpperCase(),460,460,true,true);//弹出帮助框
	var inParam = '{"param1":"参数1","param2":"参数2"}';
	setInParam(inParam);
}
//站内信
function initStationLetter() {
	$.ajax({
		  type: "post",
		  url:"/portal/portal/msgcenterbase.cmd?method=getMail",
		  beforeSend: function(XMLHttpRequest){
		  },
		  success: function(data, textStatus){
			  if(data.length<10&&data>=0){ 				   
					$(".atLetterNum").html(data);
			  }
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(xhr){
		  }
	});
}
//个人头像的展示
function initHeadImage(){
	$.ajax({
		  async:false,
		  type: "post",
		  url:"cqUserCenter.cmd?method=getPictureId",
		  beforeSend: function(XMLHttpRequest){
				$("#selfHeadImg").html();
		  },
		  complete: function(XMLHttpRequest, textStatus){
				
		  },
		  success: function(data, textStatus){
			var jsonObj=eval("("+data+")");
			var picture = jsonObj[0].PICTURE;
			if(picture != null && picture != "" && picture !="undefined"&& picture !="null"){
				$("#selfimg").attr("src","/DocCenterService/doc?doc_id="+ picture + "&type=thumbnail");
			}else{
				$("#selfimg").attr("src","/portal/skin/css/cq/usercenter/morentouxiang.jpg");
			}
		  },
		  error: function (xhr, ajaxOptions, thrownError) {
			
		 }
	});  
}
//微博【正在发生】
function initIsHapping(){
	var user = document.getElementById("userId").value;
	var vsid=base64encode('123456789_'+user+'@1234567890');
	$.ajax({
		  type: "post",
		  url: "http://10.11.2.23/sns/index.php?app=api&mod=Statuses&act=public_timeline&page=1&count=6&since_id=0&max_id=300&sid="+vsid,
		  beforeSend: function(XMLHttpRequest){
		  	
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
			 //alert(jsonObj["data"][0].uname);
			 if(jsonObj["count"]==0){
				 $("#setcontent").html(""); 
				 return;
			 }
			var vhtml=[];
			for(var i=0;i<jsonObj["data"].length&&i<6;i++){
				vhtml.push(' <div style="width:240px;height:20px;"> <div class="showsnsmess" style="width:70px;;height:20px;float:left;text-align: right;"><span>'+cutstr(jsonObj["data"][i].uname,7)+'：</span></div><div style="width:170px;;height:20px;float:left;"><span>'+cutstr(jsonObj["data"][i].content,20)+'</span></div><div>');
			}
			vhtml.push(' <div style="width:240px;height:20px;"> <div style="width:70px;;height:20px;float:left;text-align: left;"><a href="http://10.11.2.23/sns/index.php?app=home&mod=user&act=index&type=2" class="more">&gt;&gt;更多</a></div><div style="width:170px;;height:20px;float:left;"><span></span></div><div>');
			$("#setcontent").html(vhtml.join(''));
		  },
		  complete: function(XMLHttpRequest, textStatus){ 
		  	
		  },
		  error: function(){	
		  			
		  }
	  });
}
function initAtme(){
	var user = document.getElementById("userId").value;
	var vsid=base64encode('123456789_'+user+'@1234567890');
	$.ajax({
		type: "post",
		url: "http://10.11.2.23/sns/index.php?app=api&mod=Statuses&act=getAtMe&p=1&sid="+vsid,
		beforeSend: function(XMLHttpRequest){
		},
		success: function(data, textStatus){
			var jsonObj=eval("("+data+")");
			if(jsonObj["count"]==0){
				$("#setcontent").html(""); 
				return;
			}
			var vhtml=[];
			for(var i=0;i<jsonObj["data"].length&&i<6;i++){
				vhtml.push(' <div style="width:240px;height:20px;"> <div class="showsnsmess" style="width:70px;;height:20px;float:left;text-align: right;"><span>'+cutstr(jsonObj["data"][i].uname,7)+'：</span></div><div style="width:170px;;height:20px;float:left;"><span>'+cutstr(jsonObj["data"][i].content,20)+'</span></div><div>');
			}
			vhtml.push(' <div style="width:240px;height:20px;"> <div style="width:70px;;height:20px;float:left;text-align: left;"><a href="http://10.11.2.136/sns/index.php?app=home&mod=user&act=atme" class="more">&gt;&gt;更多</a></div><div style="width:170px;;height:20px;float:left;"><span></span></div><div>');
			$("#setcontent").html(vhtml.join(''));
		},
		complete: function(XMLHttpRequest, textStatus){  
		},
		error: function(){			
		}
	});
}

//办事公开
function initMZ(){
	$.ajax({
		  type: "post",
		  url: "pwscq.cmd?method=queryMZ",
		  beforeSend: function(XMLHttpRequest){
			//$("#orgselectboxes").html("<option selected=\"selected\" value=\"\">重庆市公司</option>");
		  },
		  success: function(data, textStatus){
			  $('#bsgk').html("");
			 var jsonObj=eval("("+data+")");
			 var htmlstr="<ul class='bsgkul'>";
			 for(var i=0;i<jsonObj.length&&i<=8;i++){
				 htmlstr+= '<li class="bsgkli"><span class="bsgksp" title="'+jsonObj[i].meetTitle+'" onclick="window.open(\''+jsonObj[i].meetUrl+'\',\'_blank\')">'+cutstr(jsonObj[i].meetTitle,25)+'</span><span class="bsgkdate">'+jsonObj[i].meetDate+'</span></li>';
			 }
			 htmlstr+='</ul>';
			 $('#bsgk').html(htmlstr);
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }
	  });
}

function cutstr(str,len)  
{  
	var str_length = 0;  
	var str_len = 0;  
	str_cut = new String();  
	str_len = str.length;  
	for(var i = 0; i < str_len; i++)  
	{  
		a = str.charAt(i);  
        str_length++;  
        if(escape(a).length > 4)  
        {  
         	//中文字符的长度经编码之后大于4  
         	str_length++;  
   		}  
    	str_cut = str_cut.concat(a);  
    	if(str_length>=len)  
     	{  
   			str_cut = str_cut.concat("...");  
         	return str_cut;  
      	}  
	}  
    //如果给定字符串小于指定长度，则返回源字符串；  
    if(str_length < len){  
     	return  str;  
	}  
}

//子公司下拉
function initCompany(){
	var companys = [];
	companys.push({id:"0",name:"本公司数据"});
	companys.push({id:"1",name:"省公司数据"});
	companys.push({id:"2",name:"九江市公司数据"});
	companys.push({id:"3",name:"上饶市公司数据"});
	companys.push({id:"4",name:"抚州市公司数据"});
	companys.push({id:"5",name:"宜春市公司数据"});
	companys.push({id:"6",name:"吉安市公司数据"});
	companys.push({id:"7",name:"赣州市公司数据"});
	companys.push({id:"8",name:"景德镇市公司数据"});
	companys.push({id:"9",name:"萍乡市公司数据"});
	companys.push({id:"10",name:"新余市公司数据"});
	companys.push({id:"11",name:"鹰潭市公司数据"});
	$.each(companys,function(i){
		$("<option></option>").attr("value",companys[i].id).text(companys[i].name).appendTo($("#orgselectboxes"));
	});
//	$.ajax({
//		  type: "post",
//		  url: "pwscq.cmd?method=queryCompany",
//		  beforeSend: function(XMLHttpRequest){
//			$("#orgselectboxes").html("<option id = \"orgselected\" selected=\"selected\" value=\"\">重庆市公司</option>");
//		  },
//		  success: function(data, textStatus){
//			 var jsonObj=eval("("+data+")");
//			 $.each(jsonObj,function(i){
//				 $("<option></option>").attr("value",jsonObj[i].companyId).text(jsonObj[i].companyName).appendTo($("#orgselectboxes"));
//			 })			 
//		  },
//		  complete: function(XMLHttpRequest, textStatus){
//			  
//		  },
//		  error: function(){
//			
//		  }
//	  });
}
function initImgPath() {
    var styleName = readCookie('style');
    var imgpath = "/d3/skins/default/css/cq/imgs/banner-box.png";
    if (styleName == 'css1') {//hong
        imgpath="/d3/skins/default/css/cq/imgsr/banner-box.png";
    } else if (styleName == 'css2') {//lu
        imgpath = "/d3/skins/default/css/cq/imgsg/banner-box.png";
    } else {//lan
        imgpath = "/portal/skin/css/cq/imgs/banner-box.png";
    }
    return imgpath;
}
function initImgPathForNew() {
    var imgpath = "/portal/skin/css/cq/imgs/banner-boxfornew.png";
    return imgpath;
 
}
//barnner指标信息
function initMeasure(){
    $("#juanyan").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">卷烟销售</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>年度</td><td>年同比</td></tr><tr id=\"CG_S0100\"><td>卷烟销售数量</td></tr><tr id=\"CG_S0200\"><td>卷烟销售收入</td></tr><tr id=\"CG_S01213\"><td>重点销量(总量)</td></tr><tr id=\"CG_S01211\"><td>重点销量(一二类)</td></tr></table></div>");
	$("#caiwu").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">财务指标</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>年度</td><td>年同比</td></tr><tr id=\"FI_I1001\"><td>销售利润率</td></tr><tr id=\"FI_Z0210\"><td>三项费用率</td></tr><tr id=\"FI_I0400\"><td>三项费用</td></tr></table></div>");
	$("#lingshou").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">零售价格</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>年度</td></tr><tr id=\"ST_0100\"><td>价格到位率</td></tr><tr id=\"ST_0200\"><td>社会库存</td></tr><tr id=\"ST_0300\"><td>毛利率</td></tr><tr id=\"ST_0600\"><td>装机户数</td></tr><tr id=\"ST_0700\"><td>会员户数</td></tr></table></div>");
	$("#zhuanmai").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">专卖管理</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>本年累计</td></tr><tr id=\"RM_C0500\"><td>案件总数（起）</td></tr><tr id=\"RM_C0600\"><td>违法专卖品案值（万元）</td></tr><tr id=\"RM_C0700\"><td>假冒卷烟（万支）</td></tr><tr id=\"RM_C0900\"><td>五万元以上大要案件（起）</td></tr><tr id=\"RM_C0800\"><td>抓捕人数</td></tr></table></div>");
	$("#xiaxing").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">下行数据</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>本年累计</td></tr><tr id=\"XX_C0500\"><td>案件总数（起）</td></tr><tr id=\"XX_C0600\"><td>违法专卖品案值（万元）</td></tr><tr id=\"XX_C0700\"><td>假冒卷烟（万支）</td></tr><tr id=\"XX_C0900\"><td>五万元以上大要案件（起）</td></tr><tr id=\"XX_C0800\"><td>抓捕人数</td></tr></table></div>");
	 
	//卷烟销售
	$("#CG_S0100").append("<td>3321</td><td>2%</td><td>12001</td><td>2.3%</td>");
	$("#CG_S0200").append("<td>3345</td><td>1.9%</td><td>13333</td><td>1.4%</td>");
	$("#CG_S01213").append("<td>4501</td><td>3%</td><td>11111</td><td>3.3%</td>");
	$("#CG_S01211").append("<td>2901</td><td>1.5%</td><td>34511</td><td>1.9%</td>");
	
	//财务指标
	$("#FI_I1001").append("<td>300%</td><td>1.6%</td><td>12%</td><td>5.9%</td>");
	$("#FI_Z0210").append("<td>33%</td><td>4.5%</td><td>13.7%</td><td>3.9%</td>");
	$("#FI_I0400").append("<td>11%</td><td>1.9%</td><td>16.9%</td><td>2.9%</td>");
	
	//零售价格
	$("#ST_0100").append("<td>30.1%</td><td>44%</td>");
	$("#ST_0200").append("<td>11%</td><td>12.2%</td>");
	$("#ST_0300").append("<td>2.7%</td><td>3%</td>");
	$("#ST_0600").append("<td>4.8%</td><td>5%</td>");
	$("#ST_0700").append("<td>11.1%</td><td>7.8%</td>");
	
	//专卖管理
	$("#RM_C0500").append("<td>19</td><td>3.1%</td><td>-4%</td>");
	$("#RM_C0600").append("<td>10</td><td>3.3%</td><td>-3%</td>");
	$("#RM_C0700").append("<td>2.3</td><td>4.3%</td><td>-3.3%</td>");
	$("#RM_C0800").append("<td>5</td><td>-1%</td><td>1.2%</td>");
	$("#RM_C0900").append("<td>13</td><td>-4%</td><td>3.3%</td>");
	
	//下行数据
	$("#XX_C0500").append("<td>19</td><td>3.1%</td><td>-4%</td>");
	$("#XX_C0600").append("<td>10</td><td>3.3%</td><td>-3%</td>");
	$("#XX_C0700").append("<td>2.3</td><td>4.3%</td><td>-3.3%</td>");
	$("#XX_C0800").append("<td>5</td><td>-1%</td><td>1.2%</td>");
	$("#XX_C0900").append("<td>13</td><td>-4%</td><td>3.3%</td>");
	
//    var testId= $("#orgselectboxes").val();
//   // alert(testId);
//	$.ajax({
//		  type: "post",
//		  data: "companyId="+testId+"&measurId=CG_S0200,CG_S01213,CG_S0217,CG_S0100,CG_S01211,TB_F2200,TB_F2600,TB_F0900,FI_I0400,FI_I1001,FI_Z0210,ST_0100,ST_0200,ST_0300,ST_0600,ST_0700,RM_C0500,RM_C0600,RM_C0700,RM_C0800,RM_C0900,CG_S0220",
//		  url: "pwscq.cmd?method=queryMeasure",
//		  beforeSend: function(XMLHttpRequest){
//		    $("#juanyan").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">卷烟销售</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>年度</td><td>年同比</td></tr><tr id=\"CG_S0100\"><td>卷烟销售数量</td></tr><tr id=\"CG_S0200\"><td>卷烟销售收入</td></tr><tr id=\"CG_S01213\"><td>重点销量(总量)</td></tr><tr id=\"CG_S01211\"><td>重点销量(一二类)</td></tr></table></div>");
//			$("#caiwu").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">财务指标</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>年度</td><td>年同比</td></tr><tr id=\"FI_I1001\"><td>销售利润率</td></tr><tr id=\"FI_Z0210\"><td>三项费用率</td></tr><tr id=\"FI_I0400\"><td>三项费用</td></tr></table></div>");
//			$("#lingshou").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">零售终端</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>年度</td></tr><tr id=\"ST_0100\"><td>价格到位率</td></tr><tr id=\"ST_0200\"><td>社会库存</td></tr><tr id=\"ST_0300\"><td>毛利率</td></tr><tr id=\"ST_0600\"><td>装机户数</td></tr><tr id=\"ST_0700\"><td>会员户数</td></tr></table></div>");
//			$("#zhuanmai").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">专卖管理</div><table class=\"banner-table\"><tr><td>指标名称</td><td>上月</td><td>月同比</td><td>本年累计</td></tr><tr id=\"RM_C0500\"><td>案件总数（起）</td></tr><tr id=\"RM_C0600\"><td>违法专卖品案值（万元）</td></tr><tr id=\"RM_C0700\"><td>假冒卷烟（万支）</td></tr><tr id=\"RM_C0900\"><td>五万元以上大要案件（起）</td></tr><tr id=\"RM_C0800\"><td>抓捕人数</td></tr></table></div>");
//		  },
//		  success: function(data, textStatus){
//			 var jsonObj=eval("("+data+")");
//			 $.each(jsonObj,function(i){
//			 	var html="";
//			   if(jsonObj[i].measureName=="ST_0100"||jsonObj[i].measureName=="ST_0200"||jsonObj[i].measureName=="ST_0300"||jsonObj[i].measureName=="ST_0600"||jsonObj[i].measureName=="ST_0700"){
//               html="<td>"+jsonObj[i].measureMonth+"</td><td>"+jsonObj[i].measureYear+"</td>";
//               } else if(jsonObj[i].measureName=="RM_C0500"||jsonObj[i].measureName=="RM_C0600"||jsonObj[i].measureName=="RM_C0700"||jsonObj[i].measureName=="RM_C0800"||jsonObj[i].measureName=="RM_C0900"){
//            	   html="<td>"+jsonObj[i].measureMonth+"</td><td>"+jsonObj[i].monthPortion+"</td><td>"+jsonObj[i].measureYear+"</td>";
//               }else{
//               html="<td>"+jsonObj[i].measureMonth+"</td><td>"+jsonObj[i].monthPortion+"</td><td>"+jsonObj[i].measureYear+"</td><td>"+jsonObj[i].yearPortion+"</td>";
//                }//<tr id=\"CG_S0217\"><td>低焦销量（6mg↓）</td></tr>
//			   if(jsonObj[i].measureName=="TB_F0900")
//			   {
//				var id="#"+jsonObj[i].measureName+"yx";
//				
//			   }
//			   else{var id="#"+jsonObj[i].measureName;}
//				
//				var innerHtml=$(id).html()+html;
//				$(id).html(innerHtml);
//			 })
//		  },
//		  complete: function(XMLHttpRequest, textStatus){
//		  },
//		  error: function(){
//		  }
//	  });
}
//barnner烟叶收购
function initYanYeShouGou(){
	$("#yanye").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">烟叶收购</div><table class=\"banner-table\"><tr><td>指标名称</td><td>昨日</td><td>累计</td><td>累计同比</td></tr><tr id=\"item1\"><td>烤烟收购量</td></tr><tr id=\"item2\"><td>烤烟收购完成率</td></tr><tr id=\"item3\"><td>烤烟上中等比例</td></tr><tr id=\"item4\"><td>烤烟均价</td></tr><tr id=\"item5\"><td>白肋烟收购量</td></tr><tr id=\"item6\"><td>白肋烟均价</td></tr></table></div>");
	$("#item1").append("<td>12</td><td>113</td><td>10%</td>");
	$("#item2").append("<td>93%</td><td>90%</td><td>11.1%</td>");
	$("#item3").append("<td>53%</td><td>57%</td><td>12.5%</td>");
	$("#item4").append("<td>14</td><td>15.4</td><td>16.7%</td>");
	$("#item5").append("<td>31</td><td>77</td><td>18%</td>");
	$("#item6").append("<td>32</td><td>34</td><td>19.9%</td>");
//    var testId= $("#orgselectboxes").val();
//	$.ajax({
//		  type: "post",
//		  data: "",
//		  url: "pwscq.cmd?method=queryYanYeShouGou",
//		  beforeSend: function(XMLHttpRequest){
//			$("#yanye").html("<img src=\"" + initImgPath() + "\"/><div class=\"data-container\"><div class=\"data-title\">烟叶收购</div><table class=\"banner-table\"><tr><td>指标名称</td><td>昨日</td><td>累计</td><td>累计同比</td></tr><tr id=\"item1\"><td>烤烟收购量</td></tr><tr id=\"item2\"><td>烤烟收购完成率</td></tr><tr id=\"item3\"><td>烤烟上中等比例</td></tr><tr id=\"item4\"><td>烤烟均价</td></tr><tr id=\"item5\"><td>白肋烟收购量</td></tr><tr id=\"item6\"><td>白肋烟均价</td></tr></table></div>");
//		  },
//		  success: function(data, textStatus){
//			 var jsonObj=eval("("+data+")");
//			 $.each(jsonObj,function(i){
//			 	var html="";
//               html="<td>"+jsonObj[i].item1+"</td><td>"+jsonObj[i].item2+"</td><td>"+jsonObj[i].item3+"%</td>";
//               var j = i+1;
//				var innerHtml=$("#item"+j).html()+html;
//				$("#item"+j).html(innerHtml);
//			 })
//		  },
//		  complete: function(XMLHttpRequest, textStatus){
//		  },
//		  error: function(){
//		  }
//	  });
}
//barnner动态指标
function initDyMeasure(){
	$("#dongtai").html("<img src=\""+initImgPath()+"\"/><div class=\"data-container\"><div class=\"data-title\">动态信息</div><table class=\"banner-table\"><tr><td>指标名称</td><td>昨日</td><td>本月累计</td><td>累计同比</td></tr><tr id=\"CG_S0010\"><td>卷烟销量</td></tr><tr id=\"CG_S0020\"><td>销售收入</td></tr><tr id=\"CG_S0020dx\"><td>单箱收入</td></tr><tr id=\"CG_S0030\"><td>重点品牌</td></tr></table></div>");
	$("#CG_S0010").append("<td>999</td><td>1000.11</td><td>5%</td>");
	$("#CG_S0020").append("<td>879</td><td>24334</td><td>3.5%</td>");
	$("#CG_S0020dx").append("<td>312</td><td>3122.33</td><td>7%</td>");
	$("#CG_S0030").append("<td>312</td><td>311</td><td>2%</td>");
//	var testId= $("#orgselectboxes").val();
//	$.ajax({
//		  type: "post",
//		  data: "companyId="+testId,
//		  url: "pwscq.cmd?method=queryDyMeasure",
//		  beforeSend: function(XMLHttpRequest){
//		  $("#dongtai").html("<img src=\""+initImgPath()+"\"/><div class=\"data-container\"><div class=\"data-title\">动态信息</div><table class=\"banner-table\"><tr><td>指标名称</td><td>昨日</td><td>本月累计</td><td>累计同比</td></tr><tr id=\"CG_S0010\"><td>卷烟销量</td></tr><tr id=\"CG_S0020\"><td>销售收入</td></tr><tr id=\"CG_S0020dx\"><td>单箱收入</td></tr><tr id=\"CG_S0030\"><td>重点品牌</td></tr></table></div>");
//		  },
//		  success: function(data, textStatus){
//			 var jsonObj=eval("("+data+")");
//			 $.each(jsonObj,function(i){
//				
//			 	var html="<td>"+isNull(jsonObj[i].measureValue)+"</td><td>"+isNull(jsonObj[i].measureValueAdd)+"</td><td>"+isNull(jsonObj[i].sameTimeAd)+"</td><td>";
//				var id="#"+jsonObj[i].measureId;
//				var innerHtml=$(id).html()+html;
//				$(id).html(innerHtml);
//			 })
//		  },
//		  complete: function(XMLHttpRequest, textStatus){  
//		  },
//		  error: function(){
//		  }
//	  });
}
//barnner烟叶指标信息
function initYanYeMeasure(){
	$.ajax({
		  type: "post",
		  data: "",
		  url: "pwscq.cmd?method=queryYanYeMeasure",
		  beforeSend: function(XMLHttpRequest){
			$("#yanyehuimin").html("<img src=\"" + initImgPathForNew() + "\"/><div class=\"data-container\"><div class=\"data-title\">烟叶惠民</div><table class=\"banner-table\"><tr><td>项目</td><td>奉节</td><td>巫山</td><td>巫溪</td><td>万州</td><td>丰都</td><td>武隆</td><td>南川</td><td>涪陵</td><td>黔江</td><td>酉阳</td><td>彭水</td><td>石柱</td></tr><tr id=\"yanyeplan\"><td>计   划</td></tr><tr id=\"yanyewcsj\"><td>完成设计</td></tr><tr id=\"yanyewczb\"><td>完成招标</td></tr><tr id=\"yanyejsjd\"><td>建设阶段</td></tr><tr id=\"yanyewcys\"><td>完成验收</td></tr></table></div>");
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval("("+data+")");
			  $.each(jsonObj,function(i){
					var htmlItem1="<td>"+isNull(jsonObj[i].item1)+"</td>";
					var htmlItem2="<td>"+isNull(jsonObj[i].item2)+"</td>";
					var htmlItem3="<td>"+isNull(jsonObj[i].item3)+"</td>";
					var htmlItem4="<td>"+isNull(jsonObj[i].item4)+"</td>";
					var htmlItem5="<td>"+isNull(jsonObj[i].item5)+"</td>";
					var innerHtml1 = $("#yanyeplan").html()+htmlItem1;
					var innerHtml2 = $("#yanyewcsj").html()+htmlItem2;
					var innerHtml3 = $("#yanyewczb").html()+htmlItem3;
					var innerHtml4 = $("#yanyejsjd").html()+htmlItem4;
					var innerHtml5 = $("#yanyewcys").html()+htmlItem5;
					$("#yanyeplan").html(innerHtml1);
					$("#yanyewcsj").html(innerHtml2);
					$("#yanyewczb").html(innerHtml3);
					$("#yanyejsjd").html(innerHtml4);
					$("#yanyewcys").html(innerHtml5);
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

//图片新闻
function initPicNews(){
	$.ajax({
		  type: "post",
		  url: "pwsjx.cmd?method=getNews",
		  data:{categoryId:"210",count:5},
		  dataType:"json",
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  success: function(jsonObj, textStatus){
			 var code = jsonObj.code;
			 if(code == "0"){
				 var list = jsonObj.dataList;
				 var slider=$("#focusData_01");
				 $.each(list,function(i){
				 $("<DL></DL>") .append($("<DT></DT>").append($("<a></a>").attr({"href":list[i].url})
				 .attr({"target":"_blank"}).text(list[i].title))).append($("<DD></DD>").text(list[i].image)
				 )
				 .append($("<DD></DD>")
				 .text(0)
				 )
				 .appendTo(slider);
				 });
			 }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  var FocusPic_02 = new FocusPic("FocusImg_2","BigPic_2","Number_2","TitleBox_2", 260, 210, 'foc_player');
				FocusPic_02.TimeOut = 5000;
				FocusPic_02.autoPlay = false;
				FocusPic_02.htmlData = document.getElementById('focusData_01').getElementsByTagName('dl');
				for(var i=0;i<FocusPic_02.htmlData.length;i++){
				var jsonData = {
					title : FocusPic_02.htmlData[i].getElementsByTagName('a')[0].innerHTML,
					url : FocusPic_02.htmlData[i].getElementsByTagName('a')[0].href,
					pic : FocusPic_02.htmlData[i].getElementsByTagName('dd')[0].innerText || FocusPic_02.htmlData[i].getElementsByTagName('dd')[0].textContent,
					showPlayer: FocusPic_02.htmlData[i].getElementsByTagName('dd')[1].innerText || FocusPic_02.htmlData[i].getElementsByTagName('dd')[1].textContent
				};
				FocusPic_02.Add(jsonData);
			}
			FocusPic_02.begin();
		},
		error: function(){
		
		  }
	  });
}

function cutstr(str,len)  
{  
	var str_length = 0;  
	var str_len = 0;  
	str_cut = new String();  
	str_len = str.length;  
	for(var i = 0; i < str_len; i++)  
	{  
		a = str.charAt(i);  
        str_length++;  
        if(escape(a).length > 4)  
        {  
         	//中文字符的长度经编码之后大于4  
         	str_length++;  
   		}  
    	str_cut = str_cut.concat(a);  
    	if(str_length>=len)  
     	{  
   			str_cut = str_cut.concat("...");  
         	return str_cut;  
      	}  
	}  
    //如果给定字符串小于指定长度，则返回源字符串；  
    if(str_length < len){  
     	return  str;  
	}  
}
//文字新闻
function initWordNews() { 
	$.ajax({
		  type: "post",
		  url: "pwsjx.cmd?method=getNews",
		  data:{categoryId:"211",count:9},
		  dataType:"json",
		  success:function(data){
			  var html = [];
			  html.push("<div> <strong></strong> </div> <ul>");
			  var code = data.code;
			  if(code == "0"){
				  var list = data.dataList;
				  for (var i=0; i<9&&i<list.length; i++) { 
					  html.push("<li> <a href=\""+list[i].url+"\" target=\"_blank\">"+list[i].title+"</a><span>"+list[i].date+"</span> </li>");
				  }
			  }
			  html.push("</ul>");
			  document.getElementById("wordNews").innerHTML += html.join(""); 
		  }
	});
	
} 
//天气
function initWeather(){
	$("<iframe></iframe")
	.attr({
		"src":"http://m.weather.com.cn/m/pn6/weather.htm",
		"width":"140",
		"height":"20",
		"marginwidth":"0",
		"hspace":"0",
		"vspace":"0",
		"frameborder":"0",
		"scrolling":"no",
		"allowtransparency":"true"
	}).appendTo($("#weather"));
}
//邮件
function initMail(){
	$.ajax({
		  type: "post",
		  url: "pwscq.cmd?method=queryMail",
		  beforeSend: function(XMLHttpRequest){
			  
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
			 var unReadNum=jsonObj.length;
			 $("#unreadnum").text(unReadNum);
			 for(var i=0;i<unReadNum;i++){
				 if (i==6){break};
				 $("<li></li>")
				 .addClass("mail-list-item")
				 .append(
					$("<a></a>")
					.attr("href",jsonObj[i].mailUrl)
					.attr("target","_blank")
					.attr("title",jsonObj[i].mailTitle)
					.text(jsonObj[i].mailTitle)
				  )
				 .appendTo($(".mail-list"));
			 }
			 if(unReadNum>3){
				 $(".mail-list").after($("<a class='more' href=\"https://10.11.2.153/owa/\">>>更多</a>"));
			 }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }
	  });
}

//待办
function initWork(){
	var unReadNum = 0;
	$.ajax({
		  type: "post",
		  //data: "table=wf_dai_ban_task",
		  url: "pwscq.cmd?method=queryWorkForV6",
		 /* beforeSend: function(XMLHttpRequest){
			//$(".daiban-list").html("");
		  },
		  success: function(data, textStatus){
		  	//data = "[{\"backlogDate\":\"2014-06-20\",\"backlogTitle\":\"dafdfa\",\"backlogType\":\"oa\",\"backlogUrl\":\"http:\\\/\\\/10.11.2.23\\\/oa\\\/command\\\/dispatcher\\\/org.loushang.workflow.tasklist.forward.TaskListDispatcherCmd\\\/daiBanTaskForward?assignmentId=8a8b821c46a3a0fb0146b86504040481\"},{\"backlogDate\":\"2014-06-18\",\"backlogTitle\":\"oa测试待办\",\"backlogType\":\"oa\",\"backlogUrl\":\"http:\\\/\\\/10.11.2.23\\\/oa\\\/command\\\/dispatcher\\\/org.loushang.workflow.tasklist.forward.TaskListDispatcherCmd\\\/daiBanTaskForward?assignmentId=8a8b821c46a3a0fb0146ad2f32ed3e99\"},{\"backlogDate\":\"2014-06-26\",\"backlogTitle\":\"中国烟草总公司重庆市公司关于下达丰都分公司新建莲花烟叶收购点竣工决算审计结论的通知\",\"backlogType\":\"oa\",\"backlogUrl\":\"http:\\\/\\\/10.11.2.23\\\/oa\\\/command\\\/dispatcher\\\/org.loushang.workflow.tasklist.forward.TaskListDispatcherCmd\\\/daiBanTaskForward?assignmentId=8a8b821c46a3a0fb0146d5aef79179ed\"},{\"backlogDate\":\"2013-10-29\",\"backlogTitle\":\"qqqq\",\"backlogType\":\"sns\",\"backlogUrl\":\"http:\\\/\\\/10.11.2.23\\\/sns\\\/index.php?app=group&mod=Topic&act=topic&gid=270&tid=812\"}]";
			var jsonObj=eval(data);
			 var numb = jsonObj.length;
			
			 $("<a></a>").text(numb).appendTo(".atnum");
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }*/
		   beforeSend: function(XMLHttpRequest){
		  //$(".daiban-list").html("");
		  },
		  success: function(data, textStatus){
			$(".daiban-list").html("");
			 var jsonObj=eval("("+data+")");
			 $.each(jsonObj,function(i){
			 	if(i<7){
				 $("<li></li>")
				 .addClass("daiban-item")
				 .append(
					$("<a></a>")
					.addClass("daiban-item-content")
					.attr("href",jsonObj[i].backlogUrl)
					.attr("target","_blank")
					.attr("title",jsonObj[i].backlogTitle)
					.text(jsonObj[i].backlogTitle)
				 )
				 .append(
					 $("<a></a>")
					 .addClass("daiban-item-btn")
					 .attr("href",jsonObj[i].backlogUrl)
					 .attr("target","_blank")
					 .text("办理")
				 )
				 .appendTo(".daiban-list");
				 unReadNum++;
				}
			 })
			if(unReadNum>=7){
				 $(".daiban-list").append(
					 $("<a></a>")
					 .addClass("more")
					 .attr("href","")
					 .attr("onclick","showdaiban();return false")
					 .attr("target","_blank")
					 .text(">>更多")
				 );
			 }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }

	  });
}
//待办
function initWorkNum(){
	var unReadNum = 0;
	$.ajax({
		  type: "post",
		  //data: "table=wf_dai_ban_task",
		  url: "pwscq.cmd?method=queryWorkForV6",
		 beforeSend: function(XMLHttpRequest){
			//$(".daiban-list").html("");
		  },
		  success: function(data, textStatus){
		  	//data = "[{\"backlogDate\":\"2014-06-20\",\"backlogTitle\":\"dafdfa\",\"backlogType\":\"oa\",\"backlogUrl\":\"http:\\\/\\\/10.11.2.23\\\/oa\\\/command\\\/dispatcher\\\/org.loushang.workflow.tasklist.forward.TaskListDispatcherCmd\\\/daiBanTaskForward?assignmentId=8a8b821c46a3a0fb0146b86504040481\"},{\"backlogDate\":\"2014-06-18\",\"backlogTitle\":\"oa测试待办\",\"backlogType\":\"oa\",\"backlogUrl\":\"http:\\\/\\\/10.11.2.23\\\/oa\\\/command\\\/dispatcher\\\/org.loushang.workflow.tasklist.forward.TaskListDispatcherCmd\\\/daiBanTaskForward?assignmentId=8a8b821c46a3a0fb0146ad2f32ed3e99\"},{\"backlogDate\":\"2014-06-26\",\"backlogTitle\":\"中国烟草总公司重庆市公司关于下达丰都分公司新建莲花烟叶收购点竣工决算审计结论的通知\",\"backlogType\":\"oa\",\"backlogUrl\":\"http:\\\/\\\/10.11.2.23\\\/oa\\\/command\\\/dispatcher\\\/org.loushang.workflow.tasklist.forward.TaskListDispatcherCmd\\\/daiBanTaskForward?assignmentId=8a8b821c46a3a0fb0146d5aef79179ed\"},{\"backlogDate\":\"2013-10-29\",\"backlogTitle\":\"qqqq\",\"backlogType\":\"sns\",\"backlogUrl\":\"http:\\\/\\\/10.11.2.23\\\/sns\\\/index.php?app=group&mod=Topic&act=topic&gid=270&tid=812\"}]";
			var jsonObj=eval(data);
			 var numb = jsonObj.length;
			
			 $("<a></a>").text(numb).appendTo(".atnum");
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }
		

	  });
}
//待阅
function initDaiYue(){
	var unReadNum = 0;
	$.ajax({
		  type: "post",
		  data: "table=wf_dai_ban_task",
		  url: "pwscq.cmd?method=queryDaiYueForV6",
		  beforeSend: function(XMLHttpRequest){
		  $(".daiban-list").html("");
		  },
		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");
			 $.each(jsonObj,function(i){
			 	if(i<7){
				 $("<li></li>")
				 .addClass("daiban-item")
				 .append(
					$("<a></a>")
					.addClass("daiban-item-content")
					.attr("href",jsonObj[i].backlogUrl)
					.attr("target","_blank")
					.attr("title",jsonObj[i].backlogTitle)
					.text(jsonObj[i].backlogTitle)
				 )
				 .append(
					 $("<a></a>")
					 .addClass("daiban-item-btn")
					 .attr("href",jsonObj[i].backlogUrl)
					 .attr("target","_blank")
					 .text("办理")
				 )
				 .appendTo(".daiban-list");
				 unReadNum++;
				}
			 })
			if(unReadNum>=7){
				 $(".daiban-list").append(
					 $("<a></a>")
					 .addClass("more")
					 .attr("href","")
					 .attr("onclick","showdaiban();return false")
					 .attr("target","_blank")
					 .text(">>更多")
				 );
			 }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }
	  });
}
//我的流程
function initProcedure(){
	$.ajax({
		  type: "post",
		  url: "cqpersonwork.cmd?method=getMyProcedure",
		  beforeSend: function(XMLHttpRequest){
			 
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval(data);
			 var numb = jsonObj.length;
			 $("<a></a>").text(numb).appendTo(".atProcedureNum");
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }
	  });
}
//已办
function initWorkDone(){
//改成直接访问首页
	///var yibanUrl="http://10.11.2.163:9080/dcwork/taskinfooldquery.cmd";
	//var yibanUrl="http://10.11.2.163:9080/dcwork/menu.cmd";
	var yibanUrl="http://10.11.2.23/oa/jsp/portal/index1.jsp";
	var sid = document.getElementById("sid").value;
	//var url="http://10.11.2.136:9080/eai/jsp/public/menuMain.jsp?userId="+sid+"&_url="+yibanUrl;
	var url = "http://10.11.2.23/portal/AuthenService?USERID=" + sid + "&APP=1&RESOURCE=" +yibanUrl + "&IsAuthenNew=1";
	window.open(url,"_blank");
}

//通知通告
function initNotices(){
		var url = "http://10.11.2.21/eap/322.news.list";
		$("#moretz").attr("href",url);
		$("#notic-content").html("");
		$("#notic-content").append("<div id='layer1' style='overflow-y:hidden;width:220;'><div id='layer2'><ul id='notices-container' class='notice-list-container'></ul></div><div id='layer3'></div></div>");
		$("#notices-container").html(""); 
		
		var xml = new ActiveXObject("Microsoft.XMLDOM"); 
		xml.async = "false"; 
		xml.load("http://10.11.2.21/eap/322.news.rss"); 
		var str = " <div> <strong>"; 
		str += " </strong> </div> <ul>"; 
		var nodes = xml.selectNodes("/rss/channel/item"); 
		for (var i=0; i<9&&i<nodes.length; i++) { 
			var title = nodes[i].selectSingleNode("title").text; 
			var link = nodes[i].selectSingleNode("link").text; 
			var date = nodes[i].selectSingleNode("pubDate").text; 
			str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
		} 
		str += " </ul>"; 
		document.getElementById("notices-container").innerHTML += str; 

}
//增加，通知公告 滚动
var iFrame = 1; // 定义每帧移动的象素.
var iFrequency = 100; // 定义帧频率.
var timer; // 定义时间句柄.
function marquee(){ 
	var layerHeight = 200;//parseInt(document.all("img").height)-95; // 定义滚动区域的高度.
	if(document.getElementById("layer2").offsetHeight >= layerHeight)
     document.getElementById("layer1").style.height = layerHeight;
	else
     document.getElementById("layer1").style.height = document.getElementById("layer2").offsetHeight;
	document.getElementById("layer3").innerHTML = document.getElementById("layer2").innerHTML;
	timer = setInterval("move()",iFrequency);
	document.getElementById("layer1").onmouseover=function() {clearInterval(timer);}
    document.getElementById("layer1").onmouseout=function() {timer=setInterval("move()",iFrequency);}
}
function move(){
     if(document.getElementById("layer1").scrollTop >= document.getElementById("layer2").offsetHeight)
      document.getElementById("layer1").scrollTop -= (document.getElementById("layer2").offsetHeight - iFrame)
  else
      document.getElementById("layer1").scrollTop += iFrame
}
//取消移动时间
function unmarquee(){
	clearInterval(timer);
	var layer1=document.getElementById("layer1");
	if(layer1){
		layer1.onmouseover=function() {}
    	layer1.onmouseout=function() {}
	}

}
//局发文件
function initDocument(){
	var url = "http://10.11.2.21:8083/eap/457.virtual.list";
		$("#morezw").attr("href",url);
		$("#document-container").html("");
			
		var xml = new ActiveXObject("Microsoft.XMLDOM"); 
		xml.async = "false"; 
		xml.load("http://10.11.2.21:8083/eap/457.virtual.rss"); 
		var str = " <div> <strong>"; 
		str += " </strong> </div> <ul>"; 
		var nodes = xml.selectNodes("/rss/channel/item"); 
		for (var i=0; i<9&&i<nodes.length; i++) { 
			var title = nodes[i].selectSingleNode("title").text; 
			var link = nodes[i].selectSingleNode("link").text; 
			var date = nodes[i].selectSingleNode("pubDate").text; 
			str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
		} 
		str += " </ul>"; 
		document.getElementById("document-container").innerHTML += str; 

}
//经济运行
function initEcoReport(){
		var url = "http://10.11.2.21:8083/eap/458.news.list";
		$("#morejj").attr("href",url);
		$("#ecoReport-container").html("");
			
		var xml = new ActiveXObject("Microsoft.XMLDOM"); 
		xml.async = "false"; 
		xml.load("http://10.11.2.21:8083/eap/458.news.rss"); 
		var str = " <div> <strong>"; 
		str += " </strong> </div> <ul>"; 
		var nodes = xml.selectNodes("/rss/channel/item"); 
		for (var i=0; i<9&&i<nodes.length; i++) { 
			var title = nodes[i].selectSingleNode("title").text; 
			var link = nodes[i].selectSingleNode("link").text; 
			var date = nodes[i].selectSingleNode("pubDate").text; 
			str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
		} 
		str += " </ul>"; 
		document.getElementById("ecoReport-container").innerHTML += str; 
}
//会议专题
function initMeeting(){
		  unmarquee();
	  $("#notic-content").html("");
	  $("#notic-content").append("<ul id='notices-container' class='notice-list-container'></ul>");
	  $("#notices-container").html("");	
	  var url = "http://10.11.2.21/eap/316.news.list";
	  $("#moretz").attr("href",url);
		
		var xml = new ActiveXObject("Microsoft.XMLDOM"); 
		xml.async = "false"; 
		xml.load("http://10.11.2.21/eap/316.news.rss"); 
		var str = " <div> <strong>"; 
		str += " </strong> </div> <ul>"; 
		var nodes = xml.selectNodes("/rss/channel/item"); 
		for (var i=0; i<9&&i<nodes.length; i++) { 
			var title = nodes[i].selectSingleNode("title").text; 
			var link = nodes[i].selectSingleNode("link").text; 
			var date = nodes[i].selectSingleNode("pubDate").text; 
			str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
		} 
		str += " </ul>"; 
		document.getElementById("notices-container").innerHTML += str; 
}
//工作简报
function initPointNew(){
		var url = "http://10.11.2.21/eap/319.news.list";
		$("#morezw").attr("href",url);
		$("#document-container").html("");
			
		var xml = new ActiveXObject("Microsoft.XMLDOM"); 
		xml.async = "false"; 
		xml.load("http://10.11.2.21/eap/319.news.rss"); 
		var str = " <div> <strong>"; 
		str += " </strong> </div> <ul>"; 
		var nodes = xml.selectNodes("/rss/channel/item"); 
		for (var i=0; i<9&&i<nodes.length; i++) { 
			var title = nodes[i].selectSingleNode("title").text; 
			var link = nodes[i].selectSingleNode("link").text; 
			var date = nodes[i].selectSingleNode("pubDate").text; 
			str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
		} 
		str += " </ul>"; 
		document.getElementById("document-container").innerHTML += str; 
}
//政务要情
function initAffairs(){
		$("#notices-container").html("");
	var url = "http://10.11.2.21/eap/317.news.list";
	$("#moretz").attr("href",url);
	
	var xml = new ActiveXObject("Microsoft.XMLDOM"); 
	xml.async = "false"; 
	xml.load("http://10.11.2.21/eap/317.news.rss"); 
	var str = " <div> <strong>"; 
	str += " </strong> </div> <ul>"; 
	var nodes = xml.selectNodes("/rss/channel/item"); 
	for (var i=0; i<9&&i<nodes.length; i++) { 
		var title = nodes[i].selectSingleNode("title").text; 
		var link = nodes[i].selectSingleNode("link").text; 
		var date = nodes[i].selectSingleNode("pubDate").text; 
		str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
	} 
	str += " </ul>"; 
	document.getElementById("notices-container").innerHTML += str; 
}
//媒体关注
function initMedia(){
			var url = "http://10.11.2.21/eap/320.news.list";
		$("#morejj").attr("href",url);
		$("#ecoReport-container").html("");
			
		var xml = new ActiveXObject("Microsoft.XMLDOM"); 
		xml.async = "false"; 
		xml.load("http://10.11.2.21/eap/320.news.rss"); 
		var str = " <div> <strong>"; 
		str += " </strong> </div> <ul>"; 
		var nodes = xml.selectNodes("/rss/channel/item"); 
		for (var i=0; i<9&&i<nodes.length; i++) { 
			var title = nodes[i].selectSingleNode("title").text; 
			var link = nodes[i].selectSingleNode("link").text; 
			var date = nodes[i].selectSingleNode("pubDate").text; 
			str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
		} 
		str += " </ul>"; 
		document.getElementById("ecoReport-container").innerHTML += str; 
}
//规章制度
function initDynamic(){
		var url = "http://10.11.2.21:8083/eap/339.virtual.list";
		$("#morezw").attr("href",url);
		$("#document-container").html("");
			
		var xml = new ActiveXObject("Microsoft.XMLDOM"); 
		xml.async = "false"; 
		xml.load("http://10.11.2.21:8083/eap/339.virtual.rss"); 
		var str = " <div> <strong>"; 
		str += " </strong> </div> <ul>"; 
		var nodes = xml.selectNodes("/rss/channel/item"); 
		for (var i=0; i<9&&i<nodes.length; i++) { 
			var title = nodes[i].selectSingleNode("title").text; 
			var link = nodes[i].selectSingleNode("link").text; 
			var date = nodes[i].selectSingleNode("pubDate").text; 
			str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
		} 
		str += " </ul>"; 
		document.getElementById("document-container").innerHTML += str; 
}
//学术研究
function initAcademic(){
			var url = "http://10.11.2.21/eap/321.news.list";
		$("#morejj").attr("href",url);
		$("#ecoReport-container").html("");
			
		var xml = new ActiveXObject("Microsoft.XMLDOM"); 
		xml.async = "false"; 
		xml.load("http://10.11.2.21/eap/321.news.rss"); 
		var str = " <div> <strong>"; 
		str += " </strong> </div> <ul>"; 
		var nodes = xml.selectNodes("/rss/channel/item"); 
		for (var i=0; i<9&&i<nodes.length; i++) { 
			var title = nodes[i].selectSingleNode("title").text; 
			var link = nodes[i].selectSingleNode("link").text; 
			var date = nodes[i].selectSingleNode("pubDate").text; 
			str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+date+'</span> </li>'; 
		} 
		str += " </ul>"; 
		document.getElementById("ecoReport-container").innerHTML += str; 
}
//时政要闻
function initPoliticsNew(){
	$("#politicsNew-container").html("");
	var rssUrl= $.cookie('RssNew_cookie');
	var xml = new ActiveXObject("Microsoft.XMLDOM"); 
	xml.async = "false"; 
	xml.load(rssUrl); 
	var str = " <div> <strong>"; 
	str += " </strong> </div> <ul>"; 
	var nodes = xml.selectNodes("/rss/channel/item"); 
	for (var i=0; i<9&&i<nodes.length; i++) { 
		var title = nodes[i].selectSingleNode("title").text; 
		var link = nodes[i].selectSingleNode("link").text; 
		var date = nodes[i].selectSingleNode("pubDate").text; 
		var dateNew = date.substring(5,11);
		var mon = dateNew.substring(3,6);
		if(mon=="Jan"){
			mon="01";
		}else if(mon=="Feb"){
			mon="02";
		}else if(mon=="Mar"){
			mon="03";
		}else if(mon=="Apr"){
			mon="04";
		}else if(mon=="May"){
			mon="05";
		}else if(mon=="Jun"){
			mon="06";
		}else if(mon=="Jul"){
			mon="07";
		}else if(mon=="Aug"){
			mon="08";
		}else if(mon=="Sep"){
			mon="09";
		}else if(mon=="Oct"){
			mon="10";
		}else if(mon=="Nov"){
			mon="11";
		}else if(mon=="Dec"){
			mon="12";
		}
		var day = dateNew.substring(0,3);
		dateNew = mon+"-"+day;
		if(title.length>15){
			title = title.substring(0,15)+"...";
		}
		str += ' <li> <a href="'+link+'" target="_blank">'+title+' </a><span>'+dateNew+'</span> </li>'; 
	} 
	str += " </ul>"; 
	document.getElementById("politicsNew-container").innerHTML += str; 
	/*$.ajax({
		  type: "post",
		  data: "rssUrl="+rssUrl,
		  url: "cqpageinitcmd.cmd?method=queryPoliticsNew",
		  beforeSend: function(XMLHttpRequest){
			  $("#politicsNew-container").html("");
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
			 $.each(jsonObj,function(i){
			 if(i<9){
				 $("<li></li>")
				 .append(
					$("<a></a>")
					.attr("href",jsonObj[i].politicsNewUrl)
					.attr("target","_blank")
					.attr("title",jsonObj[i].politicsNewTitle)
					.css("width","250px")
					.text(jsonObj[i].politicsNewTitle)
				 )
				 .appendTo("#politicsNew-container")
				 }
			 })
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
		  },
		  error: function(){
			
		  }
	  });*/
}
// 首页的个人网盘

function initDisk(){
	var userId=$("#userId").val();
	$.ajax({
		type: "post",
		data: "userId="+userId.toUpperCase(),      
		url: "/doc/index.php?r=/Doccenterapi/getDiskByUid",
		beforeSend: function(XMLHttpRequest){
			
		},
		success: function(data, textStatus){
			var jsonObj=eval("("+data+")");
			//alert(data);
			var len = jsonObj.length;
			for(var i=0;i<len;i++){
                 if(jsonObj[i].owner_id==userId){
					var num=GetPercent(jsonObj[i].disk_use_space, jsonObj[i].disk_max_space);
					document.getElementById('diskGotSpace').style.width=num;
					$("<span></span>").text(jsonObj[i].use_space+"/"+jsonObj[i].max_space).appendTo("#diskNum");
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
//专题频道
function initSubjectbak(){
	$.ajax({
		  type: "post",
		  url: "pwscq.cmd?method=querySubject",
		  beforeSend: function(XMLHttpRequest){
			 $("#subject-item-container").html("");
		  },
		  success: function(data, textStatus){
			 var jsonObj=eval("("+data+")");
			 $.each(jsonObj,function(i){
				 $("<li></li>")
				 .append(
					$("<a></a>")
					.attr({
						"href":jsonObj[i].subjectUrl,
						"target":"_blank",
						"title":jsonObj[i].subjectTitle
					})
					.append(
						$("<img />")
						.attr({
							"src":jsonObj[i].imgUrl,
							"width":"203",
							"height":"48",
							"alt":jsonObj[i].subjectTitle
						})
					)
					
				 )
				 .appendTo("#subject-item-container")
			 })
			 

		  },
		  complete: function(XMLHttpRequest, textStatus){
				var autoscrolling = true;

				$('.infiniteCarousel').infiniteCarousel()
				.mouseover(function () {
					autoscrolling = false;
				}).mouseout(function () {
					autoscrolling = true;
				});
				
				setInterval(function () {
					if (autoscrolling) {
						$('.infiniteCarousel').trigger('next');
					}
				}, 5000);
		  },
		  error: function(){
			
		  }
	  });
}
//专题频道
function initSubject(){
	var url = document.getElementById("reqUrl").value;
	url=url+"/portal/skin/css/cq/imgs/subject/";
	$("#subject-item-container").html("");
	var imgg=["1271905288126.gif","wn2013_xtb.png","1262834242109.jpg","1262834292047.jpg","1262834350688.jpg","1262834387109.jpg","1263265948386.jpg","1297842078078.jpg","1297842093547.jpg","1298280292266.gif","1262594160421.gif","hmzxd_tb1.jpg","sbdjs_tb.jpg","hjy_tb.jpg"];
	var title=["财务管理","温暖2013","对标管理","招标信息公告","管理体系建设","内部监管","内部审计","纪检监督","行动者","法规体制改革","经济信息","惠民工程","学习贯彻党的十八大精神","摄影大赛"];
	var ul=["http://10.11.2.160:9080/ycportal/webpublish/module.cwgl","http://10.11.0.11/ycportal/webpublish/module.wn2013","http://10.11.2.160:9081/ycportal/webpublish/module.duibiao","http://www.966599.com/ycportal/webpublish/block.904.edit.list.newslist","http://10.11.2.160:9081/ycportal/webpublish/module.txjsh","http://10.11.2.160:9081/ycportal/webpublish/module.ychyjqnbjdglzt","http://10.11.2.160:9080/ycportal/webpublish/module.shenji","http://10.11.2.160:9080/ycportal/webpublish/module.jijianjiancha","http://10.14.11.128:802/","http://10.11.2.160:9080/ycportal/webpublish/module.wuwupufa","http://10.11.2.160:9080/ycportal/webpublish/module.jjxx","http://www.966599.com/ycportal/webpublish/module.hmzxd","http://10.11.2.160:9080/ycportal/webpublish/module.sbdjs","http://10.11.0.11/ycportal/webpublish/block.89.edit.function.picmatchblock"];
	var inhtml = "";
	for(i=0;i<imgg.length;i++){
	inhtml+="<li><a href=\""+ul[i]+"\" target=\"_blank\" title=\""+title[i]+"\"><img src=\""+url+imgg[i]+"\" width=\"203\" height=\"48\"/></a></li>"
	}
	$("#subject-item-container").html(inhtml);
	var autoscrolling = true;
	$('.infiniteCarousel').infiniteCarousel()
	.mouseover(function () {
		autoscrolling = false;
	}).mouseout(function () {
		autoscrolling = true;
	});
	setInterval(function () {
		if (autoscrolling) {
			$('.infiniteCarousel').trigger('next');
		}
	}, 5000);
}
//首页的banner报表
function initBanner(){
	  initDyMeasure();
	  initCompany();
	  initMeasure();
	  initYanYeShouGou();
	  //initYanYeMeasure();				  
	  $("#banner").css("display","block");
	  //$("#bannerqywh").css("display","none");
//	$.ajax({
//		  type: "post",
//		  url: "pwscq.cmd?method=queryControlUser",
//		  beforeSend: function(XMLHttpRequest){
//			  
//		  },
//		  success: function(data, textStatus){
//			  var jsonObj=eval("("+data+")");
//			  var isUserInTable=jsonObj.isUserInTable;//pub_user表里面用户名的个数
//			  if(isUserInTable==0){
//			  	$("#banner").css("display","none");
//				  $("#bannerqywh").css("display","block");
//				  $("#bannerqywh").html("<object id=\"movie\" classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" width=\"961\" height=\"343\" align=\"middle\"> <param name=\"allowScriptAccess\" value=\"sameDomain\" /><param name=\"movie\" value=\"/portal/skin/css/cq/imgs/banner.swf\"/> <param name=\"menu\" value=\"false\" /> <param name=\"quality\" value=\"high\" /> <param name=\"wmode\" value=\"transparent\"><param name=\"bgcolor\" value=\"#ffcc33\" /> <embed src=\"/portal/skin/css/cq/imgs/banner.swf\" menu=\"false\" quality=\"high\" bgcolor=\"#ffcc33\" width=\"961\" height=\"343\" name=\"movie\" align=\"middle\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" /></object>");
//			  }else{
//			  	  initDyMeasure();
//				  initCompany();
//				  initMeasure();
//				  initYanYeShouGou();
//				  initYanYeMeasure();				  
//				  $("#banner").css("display","block");
//				  $("#bannerqywh").css("display","none");
//			  }
//		  },
//		  complete: function(XMLHttpRequest, textStatus){
//			  
//		  },
//		  error: function(){
//			
//		  }
//	  });
}

//今日日程
function initTodaySchedule(){
    var now = new Date();
    var year = now.getFullYear();       //年
    var month = now.getMonth();     //月
    var day = now.getDate();            //日
	var url = "queryScheduleCommand.cmd?method=querymain&sendDate="+year+"-"+month+"-"+day
	$.ajax({
		  type: "post",
		  url: "cqpageinitcmd.cmd?method=querySchedule",
		  beforeSend: function(XMLHttpRequest){
			  $("#Schedule").html("");
			  $("#schedleTitle").html("");
		  },
		  success: function(data, textStatus){
		  	if(data!="[]"){
		  		$("#schedleTitle").html("<div id=\"todaySd\"></div>");				
		  	}
			 var jsonObj=eval("{"+data+"}");
			 $.each(jsonObj,function(i){
			 	var cont=jsonObj[0].content;
			 	if(cont.length>140){
			 	 	cont=cont.substr(0,135)+"...";
			 	}
				$("#firstScheduleTitle").attr("href",url).text(jsonObj[0].title);
				$("#firstScheduleContent").attr("href",url).html("<p style='text-indent: 2em;'>"+cont+"</p>");
				$("#firstScheduleContent").attr("target","_blank");
				$("#firstScheduleTitle").attr("target","_blank");
			 })
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  
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
	inhtml+="<TD><A href=\"http://10.11.0.11\" target=_blank>市局外网 </A></TD></TR>";
	inhtml+="<TR align=middle>";
	inhtml+="<TD><A href=\"http://www.eastobacco.com\" target=_blank>东方烟草报</A></TD>";
	inhtml+="<TD><A href=\"http://www.echinatobacco.com\" target=_blank>中国烟草资讯网</A></TD>";
	inhtml+="<TD><A href=\"http://www.tobacco.org.cn\" target=_blank>中国烟草学会</A></TD></TR></TBODY></TABLE>";
	$('#hysitlinktable').html(inhtml);
	var jcinhtml2="<TABLE id=mytable border=0 cellSpacing=0 cellPadding=0 width=780 align=left height=250><TBODY><TR align=middle><TD><A href=\"http://10.11.3.228/\" target=_blank>销售公司</A></TD><TD><A href=\"http://10.11.4.200\" target=_blank>烟叶公司</A></TD><TD><A href=\"http://10.11.7.143/\" target=_blank>物流公司</A></TD><TD><A href=\"http://10.11.70.100/\" target=_blank>投资公司</A></TD><TD><A href=\"http://10.11.2.206:8002/\" target=_blank>烟 科 所</A></TD><TD><A href=\"http://10.11.77.2/\" target=_blank>金益公司</A></TD><TD><A href=\"http://10.11.56.5/\" target=_blank>万兴公司</A></TD><TD><A href=\"http://10.11.16.100:8080/\" target=_blank>渝 中 区</A></TD><TD><A href=\"http://10.11.17.100/\" target=_blank>江 北 区</A></TD></TR><TR align=middle><TD><A href=\"http://10.11.19.3/\" target=_blank>南 岸 区</A></TD><TD><A href=\"http://10.11.21.109/\" target=_blank>沙坪坝区</A></TD><TD><A href=\"http://10.11.22.200/\" target=_blank>大渡口区</A></TD><TD><A href=\"http://10.11.20.200/\" target=_blank>九龙坡区</A></TD><TD><A href=\"http://10.11.24.100/\" target=_blank>北 碚 区</A></TD><TD><A href=\"http://10.11.26.100/\" target=_blank>万 盛 区</A></TD><TD><A href=\"http://10.11.23.201/\" target=_blank>巴 南 区</A></TD><TD><A href=\"http://10.11.18.100/\" target=_blank>渝 北 区</A></TD><TD><A href=\"http://10.11.27.202/\" target=_blank>长 寿 区</A></TD></TR><TR align=middle><TD><A href=\"http://10.11.36.100/\" target=_blank>綦 江 县</A></TD><TD><A href=\"http://10.11.28.100:8080/\" target=_blank>江 津 区</A></TD><TD><A href=\"http://10.11.31.113/\" target=_blank>合 川 区</A></TD><TD><A href=\"http://10.11.32.5/\" target=_blank>永 川 区</A></TD><TD><A href=\"http://10.11.35.218/\" target=_blank>潼 南 县</A></TD><TD><A href=\"http://10.11.34.170/\" target=_blank>铜 梁 县</A></TD><TD><A href=\"http://10.11.30.125/\" target=_blank>大 足 县</A></TD><TD><A href=\"http://10.11.33.252/\" target=_blank>荣 昌 县</A></TD><TD><A href=\"http://10.11.29.100/\" target=_blank>璧 山 县</A></TD></TR><TR align=middle><TD><A href=\"http://10.11.42.2/\" target=_blank>万 州 区</A></TD><TD><A href=\"http://10.11.45.102/\" target=_blank>梁 平 县</A></TD><TD><A href=\"http://10.11.46.100/\" target=_blank>云 阳 县</A></TD><TD><A href=\"http://10.11.43.202/\" target=_blank>开　　县</A></TD><TD><A href=\"http://10.11.44.100/\" target=_blank>忠　　县</A></TD><TD><A href=\"http://10.11.47.100/\" target=_blank>奉 节 县</A></TD><TD><A href=\"http://10.11.48.53/\" target=_blank>巫 山 县</A></TD><TD><A href=\"http://10.11.49.169/\" target=_blank>巫 溪 县</A></TD><TD><A href=\"http://10.11.50.160/\" target=_blank>城 口 县</A></TD></TR><TR align=middle><TD><A href=\"http://10.11.37.100/\" target=_blank>涪 陵 区</A></TD><TD><A href=\"http://10.11.40.125/\" target=_blank>南 川 区</A></TD><TD><A href=\"http://10.11.41.61/\" target=_blank>垫 江 县</A></TD><TD><A href=\"http://10.11.39.34/\" target=_blank>武 隆 县</A></TD><TD><A href=\"http://10.11.38.5/\" target=_blank>丰 都 县</A></TD><TD><A href=\"http://10.11.54.222/?ClassID=1640\" target=_blank>黔 江 区</A></TD><TD><A href=\"http://10.11.52.200/\" target=_blank>石 柱 县</A></TD><TD><A href=\"http://10.11.51.30/?ClassID=1642\" target=_blank>彭 水 县</A></TD><TD><A href=\"http://10.11.55.99/?ClassID=1643\" target=_blank>酉 阳 县</A></TD></TR><TR align=middle><TD><A href=\"http://10.11.53.3/?ClassID=1644\" target=_blank>秀 山 县</A></TD></TR></TBODY></TABLE>";
	$('#jcsitlinktable').html(jcinhtml2);
}
function onselect(){
	initDyMeasure();
	initMeasure();
	initYanYeShouGou();	
	initYanYeMeasure();
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
	initPoliticsNew();
}
//站内搜索
function gosearch(){
	var k=document.getElementById("search-text").value;
	var utf8k=encodeURIComponent(k);
	window.open("http://10.11.2.21:8181/sch/all.jsp?q="+utf8k+"&version=2.2&start=0&rows=10&userId=fsdfsdf&orgPath=&type=2&subtp=0");
}
//数据搜索
function gosearchd(){
	var uid=document.getElementById("search-text").value;
	var useridsearch=document.getElementById("sid").value;
	var searchUrl= "http://10.11.2.23/portal/AuthenService?USERID="+useridsearch+"&APP=1&RESOURCE=http://10.11.2.23/d3/querySeachResultCommand.cmd?method=seachResult|sid=SUPERADMIN|q="+uid+"&IsAuthenNew=1";
	document.datasearch.action =searchUrl;
	document.datasearch.submit();
}

function todayschedle(){
	initTodaySchedule();
	$("#scheduler_here").css("display","none");
	$("#todaySchedule").css("display","block");
}
//当天日程返回
function gobackSche(){
	$("#todaySchedule").css("display","none");
	$("#scheduler_here").css("display","block");
}
//显示更多代办
//function showdaiban(){
//	var daibanUrl="http://10.11.2.163:9080/dcwork/taskinfoquery.cmd";
//	var sid = document.getElementById("sid").value;
//	var url="http://10.11.2.136:9080/eai/jsp/public/menuMain.jsp?userId="+sid+"&_url="+daibanUrl;
//	window.open(url,"_blank");
//}
function showdaiban(){
	//var daibanUrl="http://10.11.2.136/d3/cqpageinitcmd.cmd";
	//var sid = document.getElementById("sid").value;
	//var url="http://10.11.2.136/d3/jsp/com/lc/d3/cq/home/moreBacklog.jsp?userId="+sid+"&_url="+daibanUrl;
	var url="pwscq.cmd?method=moreBacklog";
	window.open(url,"_blank");
}
/** 联系人列表 开始 **/
var zTree1;
function getAsyncUrl(treeId, treeNode) {
	var url = "cqpageinitcmd.cmd?method=getRootList";
	if(zTree1){
		if (treeNode) {
			if(treeNode.id!="0"){
				url  = "cqpageinitcmd.cmd?method=getListById&id="+treeNode.id;
			}
		}
	}
	return url;
};
var setting = {
	check: {
		enable: false,
		chkStyle: "checkbox",
		chkboxType: { "Y": "ps", "N": "ps" }
	},
	view: {
		showLine: true,
		selectedMulti: false
	},
	data: {
		simple: {
			enable: true
		}
	} ,
	async: {
		enable: true,
		url: getAsyncUrl,   
		autoParam:["id", "name"],
		otherParam:{"otherParam":"zTreeAsyncTest"},
		dataFilter: filter
	},
	callback: {
		beforeClick: beforeClick,
		onClick: onClick,
		onAsyncSuccess: zTreeOnAsyncSuccess
	}
};
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var rootNodes = treeObj.getNodes();
	treeObj.expandNode(rootNodes[0], true, false, true,false);
	
};

var zNodes=[];
function filter(treeId, parentNode, responseData) {
	if (responseData) {
		for(var i =0; i < responseData.length; i++) {
			responseData[i].name += "";
		}
	}
	return responseData;
}
function beforeClick(treeId, treeNode, clickFlag) {
	return ( true);
}
function onClick(event, treeId, treeNode, clickFlag) {
	alert("start");
	console.log(treeNode);
	$('.pop-up-hidden').toggle();
}
function initContactTree(){
	zTree1=$.fn.zTree.init($("#treeDemo"), setting,zNodes);
	var rootNodes = zTree1.getNodes();
	if (rootNodes.length>0) {
		treeObj.expandNode(rootNodes[0], true, true, true);
	}
}
/** 联系人列表 结束 **/


function showdaibansearch(str){
	//var daibanUrl="http://10.11.2.23/d3/cqpageinitcmd.cmd";
	var sid = document.getElementById("sid").value;
	//var url="http://10.11.2.136/d3/jsp/com/lc/d3/cq/home/moreBacklog.jsp?userId="+sid+"&_url="+daibanUrl;
	var url="http://10.11.2.23/d3/cqpageinitcmd.cmd?method=queryMoreBacklog&sid="+sid+str;
	window.open(url,"_self");
}
/** 联系人搜索 结束 **/                  

function initTabs(){
	$(".sub-right-bar").each(function(i){
		$(this).find("div").not(":last").each(function(j){
			var $id = $(this).attr("id");
			var $categoryId = $(this).attr("categoryId");
			if(j===0){
				loadConent($id,$categoryId,i+1);
			}
			$(this).click(function(){
				loadConent($id,$categoryId,i+1);
				toggleCss($(this));
			});
		});
	});
}

function loadConent(id,cid,index){
	$.ajax({
		  type: "post",
		  url: "pwsjx.cmd?method=getNews",
		  data:{categoryId:cid,count:9},
		  dataType:"json",
		  success:function(data){
			  var html = [];
			  var code = data.code;
			  if(code == "0"){
				  $("#more"+index).attr("href",data.more);
				  var list = data.dataList;
				  for (var i=0; i<list.length; i++) { 
					  html.push("<li> <a href=\""+list[i].url+"\" target=\"_blank\">"+list[i].title+"</a><span>"+list[i].date+"</span> </li>");
				  }
			  }
			  $("#content"+index).html(html.join("")); 
		  }
	});
}

function toggleCss(obj){
	obj.addClass("active").siblings("div").removeClass("active");
}