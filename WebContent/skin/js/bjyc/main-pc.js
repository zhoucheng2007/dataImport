//var iframeWidthDefault = 0;
//var iframeHeightDefault = 0;
$(document).ready(function(){
	
	//增加返回首页按钮
	$("#goHome").live('click',function(){
		window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forFramePc","_self");			
	});
	
	//返回首页
	$(".goBackHomePage").click(function(){
		window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forFramePc","_self");
	});
	//返回专卖首页
	$(".goBackZhuanMaiPage").click(function(){
		window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forZhuanMai&target=pc&type=noanimal","_self");
	});
	//返回营销首页
	$(".goBackYingXiaoPage").click(function(){
		window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forMainPc&type=noanimal","_self");
	});
	//打开专卖管理系统
	$("#zhuanMaiSys").click(function(){
		window.open("http://10.15.6.91:9080/zm","_blank");		
	});
	//打开辅助决策系统
	$("#fuzhuJCSys").click(function(){
		window.open("http://10.15.6.35:9080/fzjc/login.jsp","_blank");
	});
	//打开物流系统
	$("#wuliuSys").click(function(){
		//window.open('http://10.15.57.78:83/bjwl',"_blank");
		window.open("http://10.15.57.78:83/bjwl/logist/monitor/dp_monitormain_sso.jsp?isplay=1&uid=88","_blank");
	});
	
	//帮助指标说明
	if($("#menuBtin-container").length>0 && $("#helpBtn").length==0){
		var $helpBtnObj = $("<div></div>").attr("id","helpBtn").attr("title","业务标准");
		$("#menuBtin-container").append($helpBtnObj);
		var $pubMeasureObj = $("<div></div>").attr("id","pub-measure-help-container");
		$pubMeasureObj.html("<iframe id=\"measureIframePage\" src=\"/datacenter/jsp/com/v6/screen/bi/project/bjycyth/frame/measureHelp.html\" scrolling=\"no\" border=\"0\" frameBorder=\"0\" style=\"width:100%;height:716px;\" allowTransparency=\"true\"></iframe>");
		$pubMeasureObj.appendTo($('body'));

		$("#helpBtn").live("click",function(){
			if($pubMeasureObj.hasClass("opened")){
				$pubMeasureObj.removeClass("opened").animate({width:0},1000);
			}else{
				$pubMeasureObj.addClass("opened").animate({width:747},1000);
			}
		});
	}	
});

//返回首页
function goHomePage(){
	window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forMainPc&type=noanimal","_self");
}

//返回专卖首页
function goZhuanMaiHome(){
	window.open("/datacenter/bi/bjycFrameCmd.cmd?method=forZhuanMai&target=pc&type=noanimal","_self");
}

//打开专卖系统
function openZhuanMaiSys(){
	window.open("http://10.15.6.91:9080/zm","_blank");
}

//打开辅助决策系统
function openFuzhuJCSys(){
	window.open("http://10.15.6.35:9080/fzjc/login.jsp","_blank");
}

//打开辅助决策系统
function openWuliuSys(){
	//window.open('http://10.15.57.78:83/bjwl',"_blank");
	window.open("http://10.15.57.78:83/bjwl/logist/monitor/dp_monitormain_sso.jsp?isplay=1&uid=88","_blank");
}

//打开经济运行-销售分析
function openSaleAnalysis(){
	window.open("/datacenter/bi/bjycMainCmd.cmd?method=forSaleAnalysis&target=pc","_self");
}


//打开经济运行-外部数据
function openMarkAnalysis(){
	window.open("/datacenter/bi/bjycMainMarkCmd.cmd?method=query&target=pc","_self");
}

//jquery对象
(function($) {   
	//内容框样式  带标题
	$.fn.addBoxStyle = function(options){
		var defaults = { 			
			parentObj:$(this),//父div
			title:{
				name:"",
				picPath:"/skin/skin2/bjyc/img/title-2.png",
				clickParams:"", //点击方法参数 
				width:0,
				align:""
			},
			childId:"",
			userFor:""//用途： table
		};  
		var opts = $.extend(defaults, options); 
		initStyle();
		function initStyle(){
			var parentHtml = opts.parentObj.html();
			var height = opts.parentObj.height();
			var width = opts.parentObj.width();
			
			var chiledObj = $("<div></div>");
			
			//表格框
			if(opts.userFor=="table"){				
				chiledObj.addClass("box-table-child");	
				opts.parentObj.html("");
				if(opts.title.name==null||opts.title.name==""||typeof(opts.title.name)=='undefined'){//无标题
					opts.parentObj.addClass("box-parent-container").height(height-5);
					height=height-7;
				}else{//有标题
					var titleAlign = opts.title.align;
					if(titleAlign==null||titleAlign==""||typeof(titleAlign)=='undefined'){
						titleAlign = "center"
					}
					height=height-48;
					var titleObj = $("<div></div>").addClass("box-container-title").css("margin-top","0");
					titleObj.append($("<div></div>").addClass("box-container-table-title-left"))
						.append($("<div></div>").addClass("box-container-table-title-center").width(width-41).css("text-align",titleAlign).html(opts.title.name))
						.append($("<div></div>").addClass("box-container-table-title-right"));
					opts.parentObj.append(titleObj);
					chiledObj.css({"border":"3px solid #33BFFF","border-top":"0"})
				}				
				if(opts.childId!="" && typeof(opts.childId!="undefined")){
					chiledObj.attr("id",opts.childId);
				}
				chiledObj.height(height).html(parentHtml);
				opts.parentObj.append(chiledObj);
				
			}else{
				chiledObj.addClass("box-child");
				if(opts.title.name==null||opts.title.name==""||typeof(opts.title.name)=='undefined'){//无标题
					height=height-40;	
					chiledObj.height(height).css("top","20px");
					
					chiledObj.append($("<div></div>").addClass("box-container-top-left-2"))
						.append($("<div></div>").addClass("box-container-top-center-2"))
						.append($("<div></div>").addClass("box-container-top-right-2"))
			
				}else{//有标题
					height = height-66;
					chiledObj.height(height);
					
					var titleWidth = 0;
					var marginleft = 0
					if(opts.title.width>0){
						if(opts.title.width>width){
							titleWidth = width
						}else{
							titleWidth = opts.title.width
						}					
					}else{
						titleWidth = opts.title.name.length*14+100;
					}
					if(titleWidth>width){
						titleWidth = width
					}
					if(opts.title.align=="left"){
						marginleft = 0;
					}else if(opts.title.name.length>10 || opts.title.align=="center"){
						marginleft = (width-titleWidth)/2;
					}					
					
					var titleObj = $("<div></div>").addClass("box-container-title").width(titleWidth).css("margin-left",marginleft+"px");
					titleObj.append($("<div></div>").addClass("box-container-title-left"))
						.append($("<div></div>").addClass("box-container-title-center").width(titleWidth-40).html(opts.title.name))
						.append($("<div></div>").addClass("box-container-title-right"));
					
					chiledObj.append($("<div></div>").addClass("box-container-top-left"))
						.append($("<div></div>").addClass("box-container-top-center").append(titleObj))
						.append($("<div></div>").addClass("box-container-top-right"))				
				}
				
				var centerObj = $("<div></div>").addClass("box-container-center-center");
				if(opts.childId!="" && typeof(opts.childId!="undefined")){
					centerObj.attr("id",opts.childId);
				}
				
				chiledObj.append($("<div></div>").addClass("box-container-center-left"))
						.append(centerObj.html(parentHtml))
						.append($("<div></div>").addClass("box-container-center-right"))
						.append($("<div></div>").addClass("box-container-bottom-left"))
						.append($("<div></div>").addClass("box-container-bottom-center"))
						.append($("<div></div>").addClass("box-container-bottom-right"))
							
				opts.parentObj.html("").append(chiledObj);	
			}
			
			//点击事件
			if(opts.title.clickParams!=null && opts.title.clickParams!="" && typeof(opts.title.clickParams)!="undefined"){
				$(".box-container-title-center",opts.parentObj).css("cursor","pointer");
				$(".box-container-title-center",opts.parentObj).click(function(){
					titleClick(opts.title.clickParams);
				});
			}
			
		}
	}

})(jQuery);    

//更新标题内容
function updateBoxTitle(id,content,userFor){
	if(userFor=="table"){
		$("#"+id+" .box-container-table-title-center").html(content);
	}else{
		$("#"+id+" .box-container-title-center").html(content);
	}	
}

//获取flash文件填充内容
function getFlashHtml(id,flashFile,flashXml,width,height){
	var flashHtml = "";
	flashHtml += "<object id=\""+id+"\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\""+width+"\" height=\""+height+"\"> ";
	flashHtml += "<param name=\"movie\" value=\"" + flashFile + "\">";
	flashHtml += "<param name=\"flashvars\" value=\"xmlpath="+flashXml+"\"/>";
	flashHtml += "<param name=\"quality\" value=\"high\"> ";
	flashHtml += "<param name=\"wmode\" value=\"transparent\"> ";
	flashHtml += "<param name=\"menu\" value=\"false\"> ";

	flashHtml += "<embed name=\""+id+"\" swliveconnect=\"true\" src=\"" + flashFile + "\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\""+width+"\" height=\""+height+"\"></embed> ";
	flashHtml += "</object>";
    
    return flashHtml;
}