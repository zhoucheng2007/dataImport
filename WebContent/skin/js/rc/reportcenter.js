//报表中心
var sid;
/*报表树开始*/
$(document).ready(function(){
	sid=$("#sid").val();
	$("#search-btn").mousedown(function(){
		$(this).addClass("onclick");
	}).mouseup(function(){
		$(this).removeClass("onclick");
	})
	//$.post("rcfrommaincmd.cmd?method=getRcDirTree",function(data){
	$.post("/portal/jsp/demo/rcfrommaincmd.jsp",function(data){
		var setting = {
			callback: {
				onClick:treeNodeClick
			}
		};
		var zNodes=eval("("+data+")");
		$.fn.zTree.init($("#reportTree"), setting, zNodes);
	})
})

function treeNodeClick(event, treeId, treeNode, clickFlag){
	var nodeId=treeNode.id;
	var dirType=treeNode.dirType;
	var url="catamage_query_init.cmd?nodeId="+nodeId+"&usefor=view&dirType="+dirType;//"querycatamanage_page_init.cmd?nodeId="+nodeId+"&usefor=view&dirType="+dirType;
	if($("#menu-i2").size()>0){
		$("#menu-i2").attr("src",url);
		$(".tab-btn[menuId='menu-i2']").click();
	}else{
		showMenuContent("menu-i2","报表列表",url);
	}
	
}
/*报表树结束*/


$(document).ready(function(){
var menuData=null;
var holdPage;
$.ajax({
	  type: "post",
	  //url:"cqpubliccmd.cmd?method=queryMenu&menuType=EAI0000008&sid="+sid,
	  url:"/portal/jsp/demo/queryMenu.jsp",
	  beforeSend: function(XMLHttpRequest){
		//不支持IE6,支持IE7及以上
		if($.browser.msie&&$.browser.version == "6.0"){
			alert("检测到您的浏览器版本为ie6，目前系统在IE6下无法正常运行，推荐您升级浏览器到IE8");
			return false;
		}
		holdPage=$("<div class='holdpage'></div>")
		.width(document.documentElement.clientWidth)
		.height(document.documentElement.clientHeight)
		.appendTo("body");
		$(window).resize(function(){
			holdPage.width(document.documentElement.clientWidth)
					.height(document.documentElement.clientHeight)
		})
	
	  },
	  success: function(data, textStatus){
		  var menuObj=eval("("+data+")");
		  menuData=menuObj.map.menu.rows[0];
	  },
	  complete: function(XMLHttpRequest, textStatus){
		  init();
		  holdPage.remove();
	  },
	  error: function(xhr){
	  }
  });
function init(){
	showMenuList(menuData);
	
	$(".nav-tab-btn").click(function(){
		$(".nav-tab-btn").removeClass("active");
		$(this).addClass("active");
	})
	$("#tab-btn1").click(function(){
		$("#nav-tab-content2").hide();
		$("#nav-tab-content1").show();
	})
	$("#tab-btn2").click(function(){
		$("#nav-tab-content1").hide();
		$("#nav-tab-content2").show();
	})

	$("#returnMain").click(function(){
		if($("#menu-i1").size()>0){
			$("#menu-i1").attr("src","cqreportcentercmd.cmd?method=queryMain");
			$(".tab-btn[menuId='menu-i1']").click();
		}else{
			showMenuContent("menu-i1","报表首页","cqreportcentercmd.cmd?method=queryMain");
		}
	})
	
	$("#menu-arrow").css("top",function(){
		var top=($("#menu").height()-39)/2;
		if(top<0){return 0}else{ return top};
	})
	.toggle(function(){
		$("#layout-left").animate({marginLeft:"-258px"},500,function(){
			$("#menu-arrow").addClass("close")
		});
		$("#layout-right").animate({marginLeft:"20px"},500);
	},function(){
		$("#layout-left").animate({marginLeft:"0px"},500,function(){
			$("#menu-arrow").removeClass("close")
		});
		$("#layout-right").animate({marginLeft:"270px"},500);
	})
	
	showMenuContent("menu-i1","报表首页","cqreportcentercmd.cmd?method=queryMain");
} 

})

function showMenuList(thisMenuData){
	var mainBox=$("<div id='container' class='mainBox'></div>")
		.append(
			$("<div class='mainBoxTitle'></div>")
		)
		.click(function(){
			getSubMenu(thisMenuData)
		})
		.appendTo($("#nav-tab-content2"))
	getSubMenu(mainBox,thisMenuData.children,0);
}
function getSubMenu(box,children,index){
	if(children&&children.length>0){
		var ul=$("<ul></ul>");
		if(index==0){
			box.append(ul);
		}else{
			box.after(ul);
		}
		index++;
		$.each(children,function(){
			var menu=this;
			var li=$("<li></li>").appendTo(ul);
			if(this.children&&this.children.length>0){
				li.addClass("haveSon")
				.append(
					$("<div></div>").addClass("title").text(menu.text)
				)
				.css("padding-left",index*15+"px")
				.click(function(){
					var thisSubMenu=$("+ul",li);
					if(thisSubMenu.size()>0){
						thisSubMenu.remove();
						$(this).find(".title").removeClass("active");
					}else{
						getSubMenu(li,menu.children,index);
						$(this).find(".title").addClass("active");
					}
				})
				
			}else{
				li.addClass("haveNoSon")
				  .append(
					  $("<a></a>")
					  .attr("href","#")
					  .text(menu.text))
					  .css("padding-left",index*15+"px")
					  .click(function(){
						  showMenuContent(menu.id,menu.text,menu.url);
					   })
				  
			}
			
		})
	
	}
}

function showMenuContent(menuId,menuText,menuUrl){
	showContentIframe(menuId,menuUrl);
	showTabTitle(menuId,menuText);
}
function showContentIframe(menuId,menuUrl){
	$("#"+menuId).remove();//如果此menuId的iframe已存在就remove
	$(".content-iframe").hide();//将所有的iframe隐藏
	$("<iframe></iframe>")
	.addClass("content-iframe")
	.attr({
		"id":menuId,
		"frameborder":"no",
		"allowTransparency":"true",
		"scrolling":"auto",
		"src":menuUrl
	})
	.appendTo($("#layout-right"));
}
//创建tab标签
function showTabTitle(menuId,menuText){
	//如果tab标签已经存在，就模拟点击这个tab
	var tab=$(".tab-btn[menuId='"+menuId+"']");
	if(tab.size()>0){
		tab.click();
		return;
	}
	//判断目前已经存在tab一共有多少个，如果大于maxTabNum，则将第一个remove
	var tabs=$(".tab-btn");
	var tabFirst=$(".tab-btn:first");
	var tabFirstMenuId=tabFirst.attr("menuId");
	if(tabs.size()==5){
		tabFirst.remove();
		$("#"+tabFirstMenuId).remove();
	}
	$(".tab-btn.active").removeClass("active");
	
	//创建一个新的tab标签
	var newTab=$("<div></div>")
	.attr({"menuId":menuId})
	.addClass("tab-btn active")
	.append(
		$("<s></s><span>"+menuText+"</span><b></b>")
	)
	.append(
		$("<div class='close-btn'></div>").click(function(){
			$("#"+menuId).remove();
			$(this).closest(".tab-btn").remove();
			var tabFirst=$(".tab-btn:first");
			tabFirst.addClass("active");
			$("#"+tabFirstMenuId).show();
		})
	)
	.click(function(){
		$(".tab-btn.active").removeClass("active");
		$(this).addClass("active");
		$(".content-iframe").hide();
		$("#"+menuId).show();
	})
	.appendTo($("#menu-tab-container"))
}