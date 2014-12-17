(function($) {	
	//查询条件
	$.fn.rss = function(options){
		var defaults = { 			
			parentObj:$(this),	//父div
			reportName:"",		//报表名称
			reportUrl:"",		//报表url
			timeType:"",		//时间类型
			timePeriod:"",		//时间
			timeParmsKey:"",	//时间字段编码
			refreshRate:"",		//数据刷新频率
			queryString:"",		//url参数
			queryDescString:"",	//查询参数描述
			hasSubscribe:false,	//是否已订阅
			timeRssPeriod:"",   //订阅时间
			rssType:"",			//订阅频率
			rssId:""//已订阅id
		};  
		var opts = $.extend(defaults, options); 
		
		init();
		var buttonLeft;
		var buttonTop;
		var buttonWidth;
		var rssPopupFlag;
		//初始化
		function init(){
			 
			//刷新报表是否已订阅
			initStatus();
			
			//鼠标放到订阅按钮，弹出订阅时间区间
			var timeId;
			opts.parentObj.hover(function(){
				timeId = setTimeout(function(){
					rssRateCheckDiv();
				},200);
			},function(){
				clearTimeout(timeId);
				rssPopupFlag=true;
				setTimeout(function(){
					rssPopupDisappear();
				},200);
			});
			$("#rssRateCheckDiv").live({
				mouseenter:function(){
					rssPopupFlag=false;		
				},mouseleave:function(){
					rssPopupFlag=true;
			  		rssPopupDisappear();
				}
			});
			
			//订阅报表
			$(".saveRss").live('click',function(){
				var parentObj = $(this).parent();
				opts.rssType = parentObj.attr("rT");//订阅频率
				opts.timeRssPeriod = parentObj.attr("tP");//时间
				
				//组织参数
				getRptParms();
				//保存报表订阅信息
				saveRptRss();
				rssPopupFlag=true;
				rssPopupDisappear();	
			});
			
			//取消订阅
			$(".canCelRssBtn").live('click',function(){
				//删除订阅信息
				deleteRptRss();
				rssPopupFlag=true;
				rssPopupDisappear();	
			});
		}
		
		function rssPopupDisappear(){
			if(rssPopupFlag){
				$("#rssRateCheckDiv").remove();		
			}				  
		}
		
		//保存报表订阅信息
		function saveRptRss(){
			if(opts.reportUrl==""){
				alert("报表路径 reportUrl 定义有误！");
			}else{
				$.ajax({
					 type:"post",
				 	 url: "/portal/portal/rptrss.cmd",
				 	 data:"method=saveRptRss&reportName="+encodeURIComponent(opts.reportName)+"&reportUrl="+opts.reportUrl+"&queryString="+encodeURIComponent(opts.queryString)+"&queryDescString="+encodeURIComponent(opts.queryDescString)+"&timeType="+opts.timeType+"&timePeriod="+opts.timeRssPeriod+"&timeParmsKey="+opts.timeParmsKey+"&refreshRate="+opts.refreshRate+"&rssType="+opts.rssType,
				 	 success:function(jsonstr){
				 		var jsonObj=eval("("+jsonstr+")");
				 		if(jsonObj.flag==1){
				 			$(".rssbtn-center",opts.parentObj).html("已订阅");
				 			opts.hasSubscribe = true;
				 			opts.rssId=jsonObj.rssId;
				 			opts.parentObj.addClass("active");
				 		}else{
				 			alert("报表订阅保存失败！");
				 		}		 		
				 	 },
				 	 error:function(){
				 		alert("报表订阅保存失败！");
					 }
				});
			}
		}
		
		//删除订阅信息
		function deleteRptRss(){
			if(opts.rssId==""){
				alert("报表订阅信息定义有误！");
			}else{
				$.ajax({
					 type:"post",
				 	 url: "/portal/portal/rptrss.cmd",
				 	 data:"method=deleteRptRss&rssId="+opts.rssId,
				 	 success:function(jsonstr){
				 		if(jsonstr==1){
				 			$(".rssbtn-center",opts.parentObj).html("报表订阅");
				 			opts.hasSubscribe = false;
				 			opts.parentObj.removeClass("active");
				 		}else{
				 			alert("取消报表订阅失败！");
				 		}		 		
				 	 },
				 	 error:function(){
				 		alert("取消报表订阅失败！");
					 }
				});
			}
		}
		
		//刷新报表是否已订阅
		function initStatus(){
			opts.parentObj.html("<span class=\"rssbtn-left\"></span><span class=\"rssbtn-center\"></span><span class=\"rssbtn-right\"></span>");

			$.ajax({
				 type:"post",
			 	 url: "/portal/portal/rptrss.cmd",
			 	 data:"method=hasRptRss&reportUrl="+opts.reportUrl,
			 	 success:function(jsonstr){
			 		var jsonObj=eval("("+jsonstr+")");
			 		if(jsonObj.length>0){
			 			opts.parentObj.addClass("active");
			 			$(".rssbtn-center",opts.parentObj).html("已订阅");	
			 			opts.hasSubscribe = true;
			 			opts.rssId=jsonObj[0].RSS_ID;
			 			opts.queryString=jsonObj[0].REPORT_PARMS;
			 			opts.queryDescString=jsonObj[0].REPORT_PARMS_DESC;
			 			opts.timeType=jsonObj[0].TIME_TYPE;
			 			opts.timeRssPeriod=jsonObj[0].TIME_PERIOD;
			 			opts.refreshRate=jsonObj[0].REFRESH_RATE;
			 			opts.rssType=jsonObj[0].RSS_TYPE;
			 		}else{
			 			$(".rssbtn-center",opts.parentObj).html("报表订阅");	
			 		}	
					buttonLeft=opts.parentObj.offset().left;
					buttonTop=opts.parentObj.offset().top;
					buttonWidth=$(".rssbtn-center",opts.parentObj).width()+6;
			 	 },
			 	 error:function(){
				 }
			});
		}
		
		//订阅信息浮动框
		function rssRateCheckDiv(){
			var $rssRateCheckDiv = $("<div></div>").attr("id","rssRateCheckDiv").addClass("rssRateCheckDiv");
			var $rssRateCheckDivContainer = $("<div></div>").attr("id","rssRateCheckDiv-container").addClass("rssRateCheckDiv-container").appendTo($rssRateCheckDiv);
			
			$rssdivTop = $("<div></div>").addClass("rssdiv-top").appendTo($rssRateCheckDivContainer);
			$("<div></div>").addClass("rssdiv-top-left").appendTo($rssdivTop);
			$("<div></div>").addClass("rssdiv-top-center").appendTo($rssdivTop);
			$("<div></div>").addClass("rssdiv-top-right").appendTo($rssdivTop);
			
			$rssdivCenter = $("<div></div>").addClass("rssdiv-center").appendTo($rssRateCheckDivContainer);
			$("<div></div>").addClass("rssdiv-center-left").appendTo($rssdivCenter);
			var $rssdivCenterCenter = $("<div></div>").addClass("rssdiv-center-center");
			$rssdivCenterCenter.appendTo($rssdivCenter);
			$("<div></div>").addClass("rssdiv-center-right").appendTo($rssdivCenter);
			
			$rssdivBottom = $("<div></div>").addClass("rssdiv-bottom").appendTo($rssRateCheckDivContainer);
			$("<div></div>").addClass("rssdiv-bottom-left").appendTo($rssdivBottom);
			$("<div></div>").addClass("rssdiv-bottom-center").appendTo($rssdivBottom);
			$("<div></div>").addClass("rssdiv-bottom-right").appendTo($rssdivBottom);
			
			var timePeriodArr = opts.timePeriod.split(",");
			if(opts.hasSubscribe){//已订阅信息
				var rssHtml = "<div class=\"rss-ctn\">订阅周期为";
				if(opts.rssType=="D"){
					if(opts.timeRssPeriod=="0"){
						rssHtml += "“本日报表”，每天会将当天的报表推送给您。";
					}else if(opts.timeRssPeriod=="-1"){
						rssHtml += "“昨日报表”，每天会将前一天的报表推送给您。";
					}				
				}else if(opts.rssType=="M"){
					if(opts.timeRssPeriod=="0"){
						rssHtml += "“本月报表”，每天会将当月的报表推送给您。";
					}else if(opts.timeRssPeriod=="-1"){
						rssHtml += "“上月报表”，每月初会将上月报表推送给您。";
					}
				}
				rssHtml+="<a class=\"canCelRssBtn\">取消订阅</a></div>";
				
				rssHtml+="<div class=\"rss-ctn\">订阅条件：</div><div class=\"rss-ctn rss-ctn-datail\">"+opts.queryDescString+"</div>";
				
				$rssdivCenterCenter.append(rssHtml);
			}else{//订阅信息
				var rssHtml;
				if(opts.timeType=="D"){
					//日级报表
					for(var i=0;itimePeriodArr.length;i++){
						if(timePeriodArr[i]==0){
							rssHtml = "<div class=\"ckoption rssbtn\" tP=\"0\"  rT=\"D\">";
							rssHtml +="<span class=\"rssbtn-left\"></span><span class=\"rssbtn-center saveRss\">本日报表</span><span class=\"rssbtn-right\"></span>";
							rssHtml +="每天会将当天的报表推送给您</div>";							
						}else if(timePeriodArr[i]==-1){
							rssHtml = "<div class=\"ckoption rssbtn\" tP=\"0\"  rT=\"D\">";
							rssHtml +="<span class=\"rssbtn-left\"></span><span class=\"rssbtn-center saveRss\">昨日报表</span><span class=\"rssbtn-right\"></span>";
							rssHtml +="每天会将前一天的报表推送给您</div>";
						}						
						$rssdivCenterCenter.append(rssHtml);
					}
				}else if(opts.timeType=="M"){
					//月级报表
					for(var i=0;i<timePeriodArr.length;i++){
						if(timePeriodArr[i]==0){
							rssHtml = "<div class=\"ckoption rssbtn\" tP=\"0\"  rT=\"D\">";
							rssHtml +="<span class=\"rssbtn-left\"></span><span class=\"rssbtn-center saveRss\">本月报表</span><span class=\"rssbtn-right\"></span>";
							rssHtml +="每天会将当月的报表推送给您</div>";
						}else if(timePeriodArr[i]==-1){
							rssHtml = "<div class=\"ckoption rssbtn\" tP=\"0\"  rT=\"D\">";
							rssHtml +="<span class=\"rssbtn-left\"></span><span class=\"rssbtn-center saveRss\">上月报表</span><span class=\"rssbtn-right\"></span>";
							rssHtml +="每月初会将上月报表推送给您</div>";
						}
						$rssdivCenterCenter.append(rssHtml);
					}
				}else{
					alert("timeType参数错误，只能是“D”或者“M”！");
				}
			}

			$rssRateCheckDiv.appendTo($("body"));
			
			//重置弹出框的位置
			var rssLeft = $rssRateCheckDivContainer.width();
			if(buttonLeft+buttonWidth>rssLeft){
				rssLeft = buttonLeft+buttonWidth-rssLeft-10;
			}else{
				rssLeft = buttonLeft;
			}
			var rssTop = buttonTop+50;		
			$rssRateCheckDiv.css({"left":rssLeft,"top":rssTop});
			
			//重置弹出框的高度
			var divheight=0;
			var rssObjs;
			if(opts.hasSubscribe){//已订阅信息
				rssObjs = $(".rss-ctn");
			}else{
				rssObjs = $(".ckoption");
			}
			rssObjs.each(function(){
				divheight+=10+$(this).height();
			});
			$rssRateCheckDivContainer.height(divheight+10);
		}		
		
		//组织参数 
		function getRptParms(){
			var timeParmsKey=opts.timeParmsKey;
			var queryString = "";
			var queryDescString = "";
			var and = "";
			
			var queryTableObj = $("#queryTable");
			var tdOjbs = $(".queryTable").find("td");

			if(tdOjbs.length>0){
				var item=""; 
				var itemValue="";
				var itemText="";
				var itemName="";
				var lastItem="";
				$.each(tdOjbs,function(i) {
					if($(this).hasClass("queryLabelTd")){
						itemText = $(this).text();
					}else if($(this).hasClass("queryCaseTd")){	
						lastItem = "";
						itemName = "";
						itemValue = "";
						$("input,select",this).each(function(i){		
							item = $(this)[0];
							if (item.name != '' && item.name != timeParmsKey) {
								if (item.type == 'select-one') {							
									itemValue = item.options[item.selectedIndex].value;
									itemName = item.options[item.selectedIndex].text;
								} else if(item.type =='select-multiple'){
									itemValue += item.value+",";
									itemName +=  item.value+",";
									lastItem = item.name;
								}else if (item.type == 'checkbox') {
									if (item.checked == false) {
										return true;
									}
									itemValue += item.value+",";
									itemName += item.value+",";
									lastItem = item.name;
								}else if (item.type == 'radio') {
									if (item.checked == false) {
										return true;
									}
									itemValue = item.value;
									itemName = item.value;
								} else if (item.type == 'button' || item.type == 'submit'|| item.type == 'reset' || item.type == 'image') {
									return true;
								} else {
									itemValue = item.value;
									if(item.type != 'hidden'){
										itemName += item.value;
									}									
								}
								if(lastItem==""){
									queryString += and + item.name + '=' + itemValue;
									and = "&";
								}
							}							
						});
						if(lastItem!=""){
							if(itemValue!=""){
								itemValue = itemValue.substr(0,itemValue.length-1);
								itemName = itemName.substr(0,itemName.length-1);
								queryString += and + lastItem + '=' + itemValue;
								and = "&";
							}						
						}
						if(itemName!=""){
							queryDescString += itemText+itemName+"；";
						}					
					}
				});
				opts.queryString=queryString;
				opts.queryDescString=queryDescString;
			}
		}
	}
		   
})(jQuery);  
