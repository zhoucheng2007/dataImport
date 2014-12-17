//
var global={};
$(document).ready(function(){
	var optsByPost={
		appId:$("#_appId").val(),
		appCode:$("#_appCode").val(),
		appType:$("#_appType").val(),
		appIcon:$("#_appIcon").val(),
		appText:$("#_appText").val(),
		appUrl:$("#_appUrl").val(),
		menuTypeId:$("#_menuTypeId").val()
	};
	var optsByGet=Tools.getParmFormUrl();
	var opts=$.extend({},optsByPost,optsByGet);
	global={
		leftMenuWidth:220,
		bspContent:"v6",
		IsAuthenNew:false, //是否使用旧的登录方式，只重庆使用
		browserVersion:$.browser.version,
		skin:Tools.getCookie("skin"),
		subMenuUrl:"",
		screenHeight:$(window).height(),
		screenWidth:$(window).width()
	};
	AppFrame.init(opts);
});

var t,curDragNodes,iframe_v;
var AppFrame={
	init:function(opts){
		
		global.appType=opts.appType;
		global.appCode=opts.appCode;
		global.appId=opts.appId;
		if(opts.appIcon==""){
			opts.appIcon="/skin/icons/32.png";
		}
		if(opts.appText==""){
			opts.appText="我的应用";
		}
		if(opts.appCode=="BSPV601"){
			opts.appCode="-1";//为bsp应用特殊处理
			global.appCode="-1";
		}
		if(opts.appType==""){
			opts.appType="mymenu";
		}
		if(opts.appType=="1"){
			global.subMenuUrl="/"+global.bspContent+"/subMenuQuery.cmd?pId=" + opts.appCode;
			if(opts.menuTypeId!=""&&opts.menuTypeId){
				global.subMenuUrl+="&menuType="+opts.menuTypeId;
			}
		}else if(opts.appType == '2'||opts.appType == '4'){		
			if(typeof(opts.positionId)== 'undefined'){
			global.subMenuUrl = '/portal/portal/appCenterInitCmd.cmd?method=queryMenuList&appCode='+ opts.appId;
		  }else{
		  global.subMenuUrl = '/portal/portal/appCenterInitCmd.cmd?method=queryMenuList&appCode='+ opts.appId+'&positionId='+ opts.positionId;	
		  	}
		}else if (opts.appType == 'mymenu' || opts.appType == 'record') {
			global.subMenuUrl = '/portal/portal/appCenterInitCmd.cmd?method=queryUserCollectMenu';
		}
		var parms = Tools.getParmsByUrl(opts.appUrl);//解析URL获得参数列表
		opts.parms=parms;
		if("IsAuthenNew" in parms){
			global.IsAuthenNew=true;
		}
		if("apphr" in parms){
			global.apphr=true;
		}
		if("supbrowser" in parms){
			global.browserVersion=parms["supbrowser"]+".0";
		}
		if("appbg" in parms){
			$("#subheadcontainer").css({
				"background" : "url(/skin/"+global.skin+"/app/img/headbg/"+ parms["appbg"] + ".png)"
			});
		}
		
		if(opts["appIcon"]!=""){
			$("#sublogo").attr("src",opts["appIcon"]);
		}
		if(opts["appText"]!=""){
			$("#subheadtext").text(opts["appText"]);
		}
		
		$("#cursorBar").hide().css("top",-10);
		
		AppFrame.start(opts);
	},
	start:function(opts){
		$.ajax({
			type : "post",
			url : global.subMenuUrl,
			beforeSend : function(XMLHttpRequest) {
				Tools.showLoading("加载中,请稍候...");
			},
			success : function(data, textStatus) {				
				var obj=null;
				try{
		           obj = eval("(" + data + ")");
		        }catch(e){
                   if(confirm("系统会话已失效，重新登录吗？")){
		           document.location.href="/"+global.bspContent+"/logout";
	               }
		        }
				if (opts.appType == '2') {
					menuJson = obj;
				}else if(opts.appType == '4')
					{
	        menuJson=obj.map.menu.rows;
           } 
				else{
					menuJson = obj.menu.rows;
				}
				
				var setting = {
					view: {
						removeHoverDom: AppFrame.removeHoverDom,
						addHoverDom: AppFrame.addHoverDom,
						addDiyDom: AppFrame.addDiyDom
						
					},
					callback: {
						onClick:AppFrame.menuClick,
						onCollapse:AppFrame.ztreeOnCollapse,
						onExpand:AppFrame.ztreeOnExpand,
						beforeExpand:AppFrame.ztreeBeforeExpand
					}
				};
				
				//当展现收藏时，为setting添加一些属性，用于拖拽排序
				if(global.appType=="mymenu"|| global.appType == 'record'){
					var edit={
							drag:{
								isCopy:false,
								isMove:true,
								prev:AppFrame.dropPrev,
								next:AppFrame.dropNext,
								inner:false
							},
							enable:true,
							showRemoveBtn: false,
							showRenameBtn: false
					};
					setting.edit=edit;
					setting.callback.beforeDrag=AppFrame.ztreeBeforeDrag;
					setting.callback.onDrop=AppFrame.ztreeOnDrop;
					//setting.callback.onDrag=AppFrame.ztreeOnDrag;
					//setting.callback.onDrop=AppFrame.ztreeOnDrop;
				}
				
				AppFrame.initleftMenuByZtreeType(menuJson, 0);
				
				var zNodes=menuJson;
				
				$.fn.zTree.init($("#menutree"), setting, zNodes);
			},
			complete : function(XMLHttpRequest, textStatus) {
				Tools.hideLoading();
				AppFrame.leftMenuBoxShow();
				for(one in AppEvents){
					AppEvents[one](opts);
				}
				AppFrame.showDefaultPage(opts);
				Tools.refixHeight();
				AppFrame.fixZtreeHeight();
				AppFrame.initScrollBar();
			},
			error : function(xhr) {
			}
		});
	},
	menuClick:function(event,treeId, treeNode){
		var $target=$(event.target);
		$("#cursorBar").show().animate({
			height:$target.height()+2,
			top:$target.offset().top-$("#menubox").offset().top+$("#menubox").scrollTop()-1
		});
		if("children" in treeNode && treeNode.children!=''){
			var zTree = $.fn.zTree.getZTreeObj("menutree");
			zTree.expandNode(treeNode,null,null,null,true);
			//下面的这个分支解决了收藏菜单为空是异常跳转的问题
		}else if("children" in treeNode && treeNode.children.length == 0 && (!treeNode.isLeaf || "false"==treeNode.isLeaf)){
			return;
		} else{
			$("#menutree.ztree a.active").removeClass("active");
			$("#"+treeNode.tId+"_a").addClass("active");
			var menuId = treeNode.id;
			var url = treeNode.menuUrl;
			var target = treeNode.target;
			var text = treeNode.name;
			if(!url){
				return false;
			}
			if (global.apphr) {	
				funcCode=url.substring(url.indexOf("funcode=")+8, url.indexOf("usercode=")-1);			
				userCode=url.substring(url.indexOf("usercode=")+9,url.length);				
				openNCNode(funcCode,userCode);
			}else{
			if (global.appCode == -1) {
				url = "/"+global.bspContent+ url;
			}
			if (global.appType == '2'||global.appType == '4') {
				url=url.replace(/\&/g,"|");
				url = "/portal/AuthenService?USERID=" + v6head.userId + "&APP=" + global.appId
				+ "&RESOURCE=" + url;
				if(global.IsAuthenNew){
					url=url+"&IsAuthenNew=1";
				}
			}
			AppFrame.showScreen(menuId, url, target, text);
			AppFrame.updateSubPageLable(text);
		}
			// 使用记录
			$.ajax({
				type : "post",
				url : "/portal/portal/appCenterInitCmd.cmd?method=insertRecord",
				data : {
					menuId : menuId,
					menuUrl : url,
					menuName : text,
					appType : global.appType
				}
			});
		}
	},
	addHoverDom:function(treeId, treeNode){
		if(treeNode.children==undefined||treeNode.children==""){
			
			//根节点不显示删除图标
			if("children" in treeNode && treeNode.children.length == 0&&treeNode.isLeaf&&treeNode.isLeaf=="false"){
				return;
				}
			
			if($("#fav-btn-"+treeNode.id).length>0){
				return;
			}
			if(global.appType=="mymenu"|| global.appType == 'record'){
				var aObj=$("#"+treeNode.tId+"_a");
				var editStr="<div id='fav-btn-"+treeNode.id+"' title='删除此收藏' class='fav-btn del' onfocus='this.blur();'></div>";
				aObj.append($(editStr).data("treeNode",treeNode));
				$("#fav-btn-"+treeNode.id).bind("click",function(){
					AppFrame.delFavorite(this);
				});
			}else{
				var aObj=$("#"+treeNode.tId+"_a");
				var editStr="<div id='fav-btn-"+treeNode.id+"' title='收藏此菜单' class='fav-btn' onfocus='this.blur();'></div>";
				aObj.append($(editStr).data("treeNode",treeNode));
				$("#fav-btn-"+treeNode.id).bind("click",function(){
					AppFrame.favorite(this);
				});
			}
		}
	},
	removeHoverDom:function(treeId, treeNode){
		if(treeNode.children==undefined){
			$("#fav-btn-"+treeNode.id).unbind().remove();
		}
	},
	addDiyDom:function(treeId, treeNode){
		if(treeNode.getParentNode()==null){
			var switchObj = $("#" + treeNode.tId + "_switch"),
			icoObj = $("#" + treeNode.tId + "_ico"),
			spanObj = $("#" + treeNode.tId + "_span");
			switchObj.remove();
			icoObj.before(switchObj);
			spanObj.addClass("menu_span");
		}else{
			spanObj = $("#" + treeNode.tId + "_span");
			spanObj.addClass("menu_span");
		}
		
	},
	initScrollBar:function(event, treeId, treeNode){
		$("#menubox").slimscroll({
			height:$("#mt-menu").height()-$("#mt_menu_titile").height(),
			position:'right',
			distance:"7px",
			opacity:0.3
		});
	},
	ztreeBeforeExpand:function(treeId, treeNode){
		$("#"+treeNode.tId).addClass("open");
	},
	ztreeOnCollapse:function(event, treeId, treeNode){
		$("#"+treeNode.tId).removeClass("open");
		//AppFrame.fixZtreeHeight();
	},
	ztreeOnExpand:function(event, treeId, treeNode){
		//AppFrame.fixZtreeHeight();
		//jquery.slimscroll.js里定义了showbar事件
		$("#menubox").trigger("showbar");
	},
	fixZtreeHeight:function(){
		$("#menutree").height("auto").height(function(){
			var meHeight=$(this).height();
			var parentHeight=$(this).parent().height();
			if(meHeight<=parentHeight){
				return "100%";
			}else{
				return "auto";
			}
		})
	},
	initleftMenuByZtreeType:function(menuJson,index){
		index++;
		for(var i=0;i<menuJson.length;i++){
			menuJson[i].name=menuJson[i].text;
			menuJson[i].menuUrl=menuJson[i].url;
			menuJson[i].url="";
			menuJson[i].icon="";
			if("children" in menuJson[i]){
				AppFrame.initleftMenuByZtreeType(menuJson[i].children,index);
			}
		}
	},
	leftMenuBoxShow:function(){
		var mtMenuStyle=Tools.getCookie("mtMenuStyle");   //获取cookie中值
		var mtMenuObj=$("#mt-menu");
		var menuScreen=$("#menuscreen");
		var mtContent=$("#mt-content");
		if(mtMenuStyle=="fixed"){                   //如果是固定住得         
			mtMenuObj.addClass("close"); 
			$("#submenu").addClass("close");
			mtContent.css("margin-left",global.leftMenuWidth);
			mtMenuObj.css("left","0px").addClass("close");  
		}else if(mtMenuStyle=="unfixed"){              //如果是非固定得
			mtMenuObj.removeClass("close");
			$("#submenu").removeClass("close");
			mtContent.css("margin-left","25px");
		};
	},
	showScreen:function(menuId, url, target, text){
		$.post("/"+global.bspContent+"/jsp/bsp/keepSession.jsp");//访问一个bsp的空页，为了延长BSP会话的失效时间
		if(target=="self"){
			window.location.href="url";
			return false;
		}else{
			AppFrame.showTabTitle(menuId,text);
			AppFrame.showContentIframe(menuId,url);
			Tools.refixHeight();//计算iframe的高度
		}
	},
	showTabTitle:function(menuId,menuText){
		// 如果tab标签已经存在，就模拟点击这个tab
		var tab = $("#menubar li[menuId='" + menuId + "']");
		if (tab.size() > 0) {
			tab.click();
			return false;
		}
		// 判断目前已经存在tab一共有多少个，如果大于maxTabNum，则将第一个remove
		var tabs = $("#menubar li");
		var tabFirst = $("#menubar li:first");
		var tabFirstMenuId = tabFirst.attr("menuId");
		if (tabs.size() == 5) {
			tabFirst.remove();
			$("#" + tabFirstMenuId).remove();
			$("#menubar li:first").addClass("firstTabLi");
		}
		$("#menubar li.active").removeClass("active");
	
		// 创建一个新的tab标签
		var newTab = $("<li></li>").attr({
			"menuId" : menuId
		}).addClass("active").append(
				$("<s></s><span class='text'>" + menuText + "</span>")).append(
				$("<span class='closebtn'></span>").click(function() {
					if ($("#menubar li").length == 1)
							return;					
					var closestli = $(this).closest("li");
					if (closestli.hasClass("active")) {
						  if(closestli.hasClass("firstTabLi")){
						  	var activeTab=closestli.next();
						  	activeTab.addClass("active");
						  	activeTab.addClass("firstTabLi");
						  	var tabActiveMenuId=activeTab.attr("menuId");
						  	$("#" + tabActiveMenuId).show();
						  	}
						  else{
						  	var activeTab=closestli.prev();					  	
						  	activeTab.addClass("active");
						  	var tabActiveMenuId=activeTab.attr("menuId");
						  	$("#" + tabActiveMenuId).show();	
						  	}
					}
					$(this).parent().remove();
					$("#" + menuId).remove();
				})).append($("<b></b>")).click(function() {
			$("#menubar li.active").removeClass("active");
			$(this).addClass("active");
			$(".content-iframe").hide();
			$("#" + menuId).show();
		}).appendTo("#menubar");
		if($("#menubar li").length==1){
			$("#menubar li").addClass("firstTabLi");
		};
		$("ul#menubar li:first").css("margin-left","17px");
	},
	showContentIframe:function(menuId,menuUrl){
		$("#" + menuId).remove();// 如果此menuId的iframe已存在就remove
		$(".content-iframe").hide();// 将所有的iframe隐藏
		// 创建iframe
		$("<iframe></iframe>").addClass("content-iframe").attr({
			"id" : menuId,
			"frameborder" : "no",
			"allowTransparency" : "true",
			"scrolling" : "auto",
			"src" : menuUrl,
			"margin-top" : "-4px"
		}).appendTo($("#menuscreen"));
	},
	showDefaultPage:function(opts){
		//有主页显示主页
		if("appwelcome" in opts.parms){
			this.showScreen("menuId", opts.parms.appwelcome, "main","主页");
		}
		//没有主页显示有可能通过get方式传过来的需要显示的页
		else if("menuId" in opts&&"menuText" in opts&&"menuUrl" in opts){
			var _url=opts.menuUrl.replace("|","=");
			this.showScreen(opts.menuId,_url, "main",opts.menuText);
		}
		//如果只有menuId参数就尝试点击这个菜单
		else if("menuId" in opts){
			if(opts["menuId"]=="menuMap"){
				this.showScreen("menuMap", "/portal/jsp/map/map.jsp","main", "菜单地图");
			}else{
				var zTree = $.fn.zTree.getZTreeObj("menutree");
				var node = zTree.getNodeByParam("id", opts["menuId"]);
				if(node!=null){
					var parentNode=node.getParentNode();
					zTree.expandNode(parentNode,null,null,null,true);
					$("#" + node.tId + "_a").click();
				}
				
				
			}
			
		}
		else{
			this.showScreen("menuId", "/portal/jsp/test.html","main", "欢迎");
		}
	},
	//固定左侧菜单
   fixedLeftMenu:function(){
	   var menushade = $("#mt-menu");
	   var fixedMtMenu= $("#submenu");
	   var w1=$("#mt-menu").width();
	   var isFixed = menushade.hasClass("close");
		menushade.addClass("close"); 
		fixedMtMenu.addClass("close");
		Tools.setCookie("mtMenuStyle","fixed");
		$("#mt-content").animate({
			'margin-left': global.leftMenuWidth},50);
		$("#mt-menu").animate({'left': 0},50, function() {
			menushade.addClass("close"); 
		});
		$("#dragbar").show();
		 	
   },
   //放开左侧菜单
   unflxedLeftMenu:function(){
	   var menushade = $("#mt-menu");
	   var fixedMtMenu= $("#submenu");
	   var w1=$("#mt-menu").width();
	   var isFixed = menushade.hasClass("close");
	   Tools.setCookie("mtMenuStyle","unfixed");
	   menushade.removeClass("close"); 
	   fixedMtMenu.removeClass("close");
	   $("#dragbar").hide();
	   
   },
   leftMenuStyle:function(){
	   var mtMenuStyle=Tools.getCookie("mtMenuStyle");
	   if(mtMenuStyle=="fixed"){                   //如果是固定住得         
		    return "close"; 
		}else if(mtMenuStyle=="unfixed"){              //如果是非固定得
			return "open";
		};
   },
   logoShow:function(){
	   var subhcb = $("#subheadcontorlbtn");
	   var isClose = $("#subheadcontorlbtn").hasClass("close");
		if (isClose) {
			$("#subheadcontainer").animate({
				'height' : 70
			}, 200, function() {
				$("#sublogo").fadeIn();
				$("#subheadtext").fadeIn();
				$("#topmenu").fadeIn();
				subhcb.removeClass("close");
				Tools.refixHeight();
			});
		}   
   },
   logoHide:function(){
	   var subhcb = $("#subheadcontorlbtn");
	   var isClose = $("#subheadcontorlbtn").hasClass("close");
	   if(!isClose){
		$("#subheadcontainer").animate({
			'height' : 27
		}, 200, function() {
			$("#sublogo").hide();
			$("#subheadtext").hide();
			$("#topmenu").hide();
			subhcb.addClass("close");
			Tools.refixHeight();
		});
	   }   
   },
   logoStyle:function(){
   	 if($("#subheadcontorlbtn").hasClass("close")){
   	  	return "close";
     }else{
   	  	return "open";
   	 }
   },
   favorite:function(target){
		Tools.stopBubble(target);
		var $this = $(target);
		var treeNode=$this.data("treeNode");
		var menuId = treeNode.id;
		var menuUrl = treeNode.menuUrl;
		var text = treeNode.name;
		if (global.appCode == -1) {
			menuUrl = "/"+global.bspContent+ menuUrl;
		}else if(global.appType == '2'||global.appType == '4') {
			menuUrl=menuUrl.replace(/\&/g,"|");
			//alert("url1==="+url);
			menuUrl = "/portal/AuthenService?USERID=" + v6head.userId + "&APP=" + global.appId
			+ "&RESOURCE=" + menuUrl;
			//alert("url2==="+url);
			if(global.IsAuthenNew){
				menuUrl=menuUrl+"&IsAuthenNew=1";
				//alert("url4==="+url);
			}
		}
		$.ajax({
			type : "post",
			url : "/portal/portal/appCenterInitCmd.cmd?method=insertCollect",
			data : {
				menuId : menuId,
				menuUrl : menuUrl,
				menuName : text,
				appType : global.appType
			},
			beforeSend : function(XMLHttpRequest) {
				
			},
			success : function(data, textStatus) {
				Tools.showLoading("收藏菜单成功");
				setTimeout(function() {
					Tools.hideLoading();
				}, 500);
			},
			error : function(xhr) {
				alert("菜单收藏失败，请联系系统管理员");
			}
		});
	},
	delFavorite:function(target){
		Tools.stopBubble(this);
		var treeNode=$(target).data("treeNode");
		var menuId = treeNode.id;
		if (confirm("确认要删除此收藏吗?")) {
			$.ajax({
				type : "post",
				url : "/portal/portal/appCenterInitCmd.cmd?method=delCollect",
				data : {
					menuId : menuId
				},
				success : function(data, textStatus) {
					Tools.showLoading("删除成功");
					setTimeout(function() {
						var zTree = $.fn.zTree.getZTreeObj("menutree");
						zTree.removeNode(treeNode);
						Tools.hideLoading();
					}, 500);
				},
				error : function(xhr) {
					alert("删除菜单失败，请联系系统管理员");
				}
			});
		}
	},
   createModellessDialog:function(title,url,width,height,top,left){
	   if(title==null)title="";
	   if(width==null)width=1000;
	   if(height==null)height=600;
	   var win=window.showModelessDialog(url,window,"dialogWidth="+width+"px;dialogHeight="+height+"px;dialogTop="+top+"px;resizable=yes;status=0");
	   win=null;
	   return win;
   },
   updateSubPageLable:function(text){
	   clearInterval(t);
	   try{
		   $(".content-iframe:visible").contents();
	   }catch(e){
		   return false;
	   }
	   if(!$(".content-iframe:visible").contents()){
		   return false;
	   }
	   var i=0;
	   var finded=false;
	   t=setInterval(function(){
		    var subPageLable=$(".content-iframe:visible").contents().find(".pageLable");
		   	if(subPageLable.size()>0){
		   		finded=true;
		   		subPageLable.text(text);
		   	}
		    i++; 
		    if(finded||i>4){	
		   		  clearInterval(t);
		    }   
		   	  
	   },500);
   },
   dropPrev:function(treeId, nodes, targetNode) {
		var pNode = targetNode.getParentNode();
		if (pNode && pNode.dropInner === "false") {
			return false;
		} else {
			for (var i=0,l=curDragNodes.length; i<l; i++) {
				var curPNode = curDragNodes[i].getParentNode();
				if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === "false") {
					return false;
				}
			}
		}
		return true;
	},
	dropNext:function(treeId, nodes, targetNode) {
		var pNode = targetNode.getParentNode();
		if (pNode && pNode.dropInner === "false") {
			return false;
		} else {
			for (var i=0,l=curDragNodes.length; i<l; i++) {
				var curPNode = curDragNodes[i].getParentNode();
				if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === "false") {
					return false;
				}
			}
		}
		return true;
	},
	ztreeBeforeDrag:function(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === "false") {
				curDragNodes = null;
				return false;
			}
		}
		curDragNodes = treeNodes;
		if(global.browserVersion<9){
			iframe_v=$("iframe.content-iframe:visible").hide();
		}
		return true;
	},
	ztreeOnDrop:function(event, treeId, treeNodes, targetNode, moveType, isCopy) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);	
		var pNode=treeObj.getNodeByParam("id","COLLECT");
		var nodes=pNode.children;
		var str="";
		for(var i=0,len=nodes.length;i<len;i++){
			str+=nodes[i].id;
			if(i!=len-1){
				str+=",";
			}
		}
		$.post("appCenterInitCmd.cmd?method=updateCollectMenuOrder",{ids:str});
		if(global.browserVersion<9){
			iframe_v.show();;
		}
	}
};

var AppEvents={
	unbind:function(){
		//清除左边栏相关事件，重新注册，否则在不刷新页面切换应用时会重复注册事件产生错误。
		$("#mt-menu").unbind();
		$("#submenu").unbind();
	},
	leftMenuToggle:function(){
		var menushade = $("#mt-menu");
		var isFixed = menushade.hasClass("close");
		var t;
		menushade.hover(function() {
			clearTimeout(t);
			isFixed = menushade.hasClass("close");
			if (!isFixed) {
				$("#mt-content").animate({
					'margin-left': 25
				},200);
				menushade.animate({'left': -5},200);
			}
		},
		function() {
			var e = event || window.event;
			if (parseInt(e.clientX) > 20) { //为解决在ie7，8下浏览器左侧有间距的bug
				t=setTimeout(function() { //鼠标移走让延迟50毫秒再执行，等click方法执行完
					isFixed = menushade.hasClass("close");
					if (!isFixed) {
						$("#mt-content").animate({'margin-left': 25},200,function() {
							menushade.animate({'left': 25-global.leftMenuWidth},100);
						});
					}
				},300);
			}
		}); 
	},
	leftMenuFix:function(){
	   //固定左侧菜单
	   var fixedMtMenu= $("#submenu");
	   var menushade = $("#mt-menu");
	   fixedMtMenu.click(function(){
	   var 	isFixed=menushade.hasClass("close");
		 if(!isFixed){
			 AppFrame.fixedLeftMenu();
		 }else{
			 AppFrame.unflxedLeftMenu();
		 }   	
	   	});
	},
	logoToogle:function(){
		// logo栏隐藏、显示
		var subhcb = $("#subheadcontorlbtn");
		subhcb.click(function() {
			var isClose = $("#subheadcontorlbtn").hasClass("close");
			if (isClose) {
				AppFrame.logoShow();
				shortMsg.open();
			} else {
				AppFrame.logoHide();
				shortMsg.close();
			}
		});
	},
	leftMenuResizeBarDraggable:function(){
	   var iframeVisible;
	   $("#dragbar").draggable({
		   axis: 'x',
		   iframeFix:true,
		   containment:[200,0,400,0],
		   start:function(event, ui){
			   iframeVisible=$("iframe.content-iframe:visible");
			   iframeVisible.hide();
			   $(this).addClass("draging");
		   },
		   stop:function(event, ui){
			   $(this).removeClass("draging");
			   AppEvents.leftMenuChangeWidth(ui.offset.left);
			   global.leftMenuWidth=ui.offset.left;
			   $(this).css("left","auto");
			   iframeVisible.show();
		   }
	   });
	},
	leftMenuChangeWidth:function(width){
		if(!isNaN(width)){
			$("#sublogo").hide();
			$("#subheadtext").hide();
			$("#mt-menu").animate({"width":width});
			$("#mt-content").animate({"margin-left":width},function(){
				$("#sublogo").show();
				$("#subheadtext").show();
			});
		}
		
	},
	menuBaike:function(){
		$("#menubaikebtn").click(function(){
			var menuId=$("#menubar li.active").attr("menuid");
			var menuText=$("#menubar li.active").find(".text").text();
			MenuBaike.init(menuId,menuText);
			
		})
	},
	shortMsg:function(){
		shortMsg.init();
	},
	windowResize:function(){
		$(window).resize(function(){
			global.screenHeight=$(window).height();
			global.screenWidth=$(window).width();
			Tools.refixHeight();
			AppFrame.fixZtreeHeight();
			AppFrame.initScrollBar();
		})
	}
};


var Tools={
	getParmsByUrl:function(appUrl){
		var parms={};
		var n=appUrl.indexOf("@:");
		if(n!=-1){
			appUrl=appUrl.substring(n+2,appUrl.length);
			var arr=appUrl.split(";");
			for(var i=0;i<arr.length;i++){
				var p=arr[i];
				var m=p.indexOf("=");
				var name=p.substring(0,m);
				var value=p.substring(m+1,p.length);
				parms[name]=value;
			}
		}
		return parms;
	},
	getCookie:function(c_name){
		if(document.cookie.length>0){
		   c_start=document.cookie.indexOf(c_name + "=");
		   if(c_start!=-1){ 
			 c_start=c_start + c_name.length+1 ;
			 c_end=document.cookie.indexOf(";",c_start);
			 if(c_end==-1) c_end=document.cookie.length;
			 return unescape(document.cookie.substring(c_start,c_end));
		   }
		}
		return "";
	},
	setCookie:function(c_name,c_value){
		var Days = 365; 
		var exp = new Date(); 
		exp.setTime(exp.getTime() + Days*24*60*60*1000); 
		document.cookie = c_name + "="+ escape (c_value) + ";expires=" + exp.toGMTString(); 
	},
	showLoading:function(text){
		$overlay = $(".overlay");
		$overlay.css({"height" : global.screenHeight,'display' : 'block','opacity' : '0.7'});
		$("#AjaxLoading")
			.show().css("opacity", "1")
			.html("<div class=loadingWord><img alt='' src='/portal/skin/img/waiting.gif'>"+text+"</div>");
	},
	hideLoading:function(){
		$("#AjaxLoading").hide();
		$(".overlay").hide();
	},
	/*nofind:function(){
		var img = event.srcElement;
		img.src = "/skin/"+global.skin+"/icons/cyhead.png";
		img.onerror = null;
	},*/
	refixHeight:function(){
		var screenH=global.screenHeight;
		var h1=$("#headholder").height();
		var h2=$("#subheadcontainer").height();
		var h3=$("#menubar").height();
		var h4=$("#mt_menu_titile").height();
		var h5=0;
		if($("#head-layout-from-d3").size()>0){
			h5=$("#head-layout-from-d3").height();
		}
		$(".content-iframe").height(screenH-h1-h2-h5);
		$("#menuscreen").height(screenH-h1-h2+27-h5);
		$("#mt-content").height(screenH-h1-h2+h3-h5);
		$("#mt-menu").height(screenH-h1-h2+h3-h5);
		$(".slimScrollDiv").height(screenH-h1-h2+h3-h4-h5);
		$("#menubox").height(screenH-h1-h2+h3-h4-h5);
		
	},
	stopBubble:function(e){
		if (e && e.stopPropagation) {
			e.stopPropagation();
		} else {
			window.event.cancelBubble = true;
		}
	},
	getParmFormUrl:function() {
		var url = location.search; // 获取url中"?"符后的字串
		var theRequest = new Object();
		if (url.indexOf("?") != -1) {
			var str = url.substr(1);
			strs = str.split("&");
			for ( var i = 0; i < strs.length; i++) {
				theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
			}
		}
		return theRequest;
	}

};
//对外暴露的方法
var InterfaceObj={
    //固定左侧的菜单
	fixedLeftMenu:function(){
		 AppFrame.fixedLeftMenu();
	},
	//放开左侧菜单
	unflxedLeftMenu:function(){
		 AppFrame.unflxedLeftMenu();	
	},
    //隐藏左侧菜单
	hideLeftMenu:function(){
		$("#mt-menu").hide();
		$("#mt-content").css("margin-left","25px");
	},
	//显示左侧菜单
	showLeftMenu:function(){
		$("#mt-menu").show();
		if(AppFrame.leftMenuStyle()=="close"){
			$("#mt-content").css("margin-left",global.leftMenuWidth);	
		}	
	},
	//当前左侧菜单状态
	leftMenuStyle:function(){
	  return AppFrame.leftMenuStyle();
	},
	//展示头部
	logoShow:function(){
		AppFrame.logoShow();	
	},
	//隐藏头部
	logoHide:function(){
		AppFrame.logoHide();	
	},
	logoStyle:function(){
		return AppFrame.logoStyle();
	},
	//打开一个新的tab页
	creatTabWindow:function(menuId, url, text) {
		AppFrame.showScreen(menuId, url, "", text);
	},
	//打开一个modelless样式的对话框
	createModellessDialog:function(title,url,width,height,top,left){
		return AppFrame.createModellessDialog(title,url,width,height,top,left);
	},
	// 报表全屏
	fullScreen:function(width,tableContent,columnTD){   
		  //调用方法时先清除掉全屏的div，防止出错
		   $('#exitFullScreen').remove();
		   $('#fullScreen').children().remove();
		   $('#fullScreen').remove();
		  //创建div，存放全屏的表格
		   var theBody = document.getElementsByTagName('BODY')[0];
		   var  fullScreen = document.createElement('div');
		   fullScreen.id = 'fullScreen';
		   theBody.appendChild(fullScreen);
		    //获取浏览器宽度高度
			var visibleWidth=document.documentElement.clientWidth;
			var visibleHeight=document.documentElement.clientHeight;
			//设置全屏div的宽和高
			$('#fullScreen').css("width",visibleWidth+"px");
			$('#fullScreen').css("height",visibleHeight+"px");
			//克隆表格
			var cloneTable=$('#fullScreen');
			//在全屏div中拼如4个div
			cloneTable.html("<div class=\"report_table_all_head_fixeddiv\"><table class=\"allScreenTable2\" width="+width+"px>"+tableContent+"</table></div>"
			               +"<div class=\"report_table_fullscreen_content\"><table class=\"allScreenTable\" width="+width+"px>"+tableContent+"</table></div>"
			               +"<div id=\"exitFullScreen\"></div>"
			               +"<div class=\"report_table_fullscreen_top_fixeddiv\"><table class=\"allScreenTable3\"  width="+width+"px>"+tableContent+"</table></div>"
			               +"<div class=\"report_table_fullscreen_left_fixeddiv\"><table class=\"allScreenTable4\"  width="+width+"px>"+tableContent+"</table></div>");
			//为创建的元素定义定义实例
			var allScTab=$(".allScreenTable");
			var allScreenTab2=$(".allScreenTable2");
			var reportAllHeadTable=$(".report_table_all_head_fixeddiv");
			var reportTopTable=$(".report_table_fullscreen_top_fixeddiv");
			var reportLeftTable=$(".report_table_fullscreen_left_fixeddiv");
			var tableHeadHei=$("thead",allScTab).height();
			var reportLeftDivWid=0;
			var reportTopWid=0;
			//计算要固定的列的总宽度
			for( var i=0;i<columnTD;i++){
				var thisThwidth=$('th:eq(' + i + ')',allScreenTab2).width();
				reportLeftDivWid=reportLeftDivWid+thisThwidth+3;
				reportTopWid=reportTopWid+thisThwidth+1;//加1是因为border为1
			};
			//定义头部div的高度
			reportAllHeadTable.height(tableHeadHei);
		    //为左侧的div定义宽度
			reportLeftTable.width(reportLeftDivWid);
			//为左上角的div定义宽度和高度
			reportTopTable.width(reportLeftDivWid);
			reportTopTable.height(tableHeadHei+2);
			//初始化变量
	        var scrollX=0;
	        var scrollY=0;
			$('#fullScreen').scroll(function(){
				$('thead th',".allScreenTable3").css("background-color","");
					var scrollTop = $('#fullScreen').scrollTop();   //向下滚动距离
				    var scrollLeft = $('#fullScreen').scrollLeft(); //向右滚动距离
				    //判断横向还是竖向滚动条滚动，改变左上角div中table的th的背景色
				    if(scrollTop!=scrollY){ 
				    	$(".allScreenTable3 thead th").css("background","#d8e7f2");
				    }
				    scrollY=scrollTop;
	                if(scrollLeft!=scrollX){
	                	$(".allScreenTable3 thead th").css("background","#e4f7fa");
				    }
	                scrollX=scrollLeft;
	                
	                //根据滚动条的滚动定位左上角，上侧和左侧的div位置
				    reportAllHeadTable.animate({
				    	top:scrollTop+"px"
				    },10);
				    reportTopTable.animate({
				    	top:scrollTop+"px",
				    	left:scrollLeft+"px"
				    },1);
				    reportLeftTable.animate({
				    	left:scrollLeft+"px"
				    },10);
					/* 
					 allScreenTab2.css("top",scrollTop+"px");
					 reportTopTable.css("left",scrollLeft+"px");
					 reportTopTable.css("top",scrollTop+"px");
					 reportLeftTable.css("left",scrollLeft+"px");   
					 */
			          /*       $(".allScreenTable2").scrollTop(scrollTop);
		                        $(".report_table_fullscreen_left_fixeddiv").scrollLeft(scrollLeft);
		                        $(".report_table_fullscreen_top_fixeddiv").scrollTop(scrollTop);
		                        $(".report_table_fullscreen_top_fixeddiv").scrollLeft(scrollLeft);  
					   */ 
				});
			    /*
			           以后扩充，给退出按钮绑定esc键盘事件
				$('.allScreanBtn').on("keydown", function(event) {
					if(event.keyCode==27){
						alert(1);
					}
				});	*/
			//给退出按钮绑定click事件
			$('#exitFullScreen').bind('click',function(){
					$('#exitFullScreen').remove();
					$('#fullScreen').children().remove();
					$('#fullScreen').children().children().remove();
					$('#fullScreen').remove();
				});	
				
		}
	
};
var baikeBj,
baikeBox
;
var MenuBaike={
	init:function(menuId,menuText){
		$.post("appCenterInitCmd.cmd?method=menuBaikeQuery&menuId="+menuId,function(data){
			if($(baikeBj).length>0){
				baikeBj.remove();
			}
			if($(baikeBox).length>0){
				baikeBox.remove();
			}
			baikeBj=$("<div class='baikebj'></div>")
			.css({
				width:global.screenWidth,
				height:global.screenHeight,
				opacity:"0.2"
			}).appendTo("body");
			baikeBox=$("<div id='baike'></div>")
			.css({
				top:(global.screenHeight-450)/2,
				left:(global.screenWidth-900)/2
			}).appendTo("body");
			var title=$("<div class='title'></div>")
			.append($("<div class='icon'><div>"))
			.append($("<div class='text'>功能百科<div>"))
			.append($("<div class='subtext'>人人可编辑的帮助文档<div>"))
			.append($("<div class='closebtn'><div>"))
			.append($("<div class='editbtn'><div>"))
			.append($("<div class='savebtn'><div>"))
			.appendTo(baikeBox);
			var contentBox=$("<div class='content'></div>").appendTo(baikeBox);
			
			var obj;
			try{
				obj=eval("("+data+")");
			}catch(e){
				obj={};
			}
			var content="";
			if("CONTENT" in obj&&obj["CONTENT"]!=""&&obj["CONTENT"]!=null){
				content=obj["CONTENT"];
			}
			
			MenuBaike.loadContent(contentBox,content,menuText);
			for(var one in BaikeEvents){
				BaikeEvents[one](menuId,menuText,content);	
			}
		});
		
	},
	loadContent:function(contentBox,content,menuText){
		if(content!=""){
			var html="<!DOCTYPE html><html><head></head><body>"+content+"</body></html>"
			var iframe=$("<iframe id='baikeIframe'></iframe>")
			.attr({
				width:"100%",
				height:"100%",
				frameborder:"no",
				scrolling:"auto",
				src:"about:blank"
			}).appendTo(contentBox);
			var isLoad=true;
			//采用两种load方法配合判断，是为了防止谷歌因为其他东西没有加载完导致代码不能执行
			$(document).ready(function(){
				if(isLoad){
				   setTimeout(function(){
					$(document.getElementById('baikeIframe').contentWindow.document.body).html(content);
				   },0);
			 	   isLoad=false; 
				   }
		 	    });
		   document.getElementById('baikeIframe').onload=function(){ 	
		  	    if(isLoad){
			       $(document.getElementById('baikeIframe').contentWindow.document.body).html(content);
			    }
		  };		
		}else{
			var subTitle=$("<div class='subTitle'></div>")
			.append($("<div class='text'>"+menuText+"</div>"))
			.appendTo(contentBox);
			$("#baike").addClass("tuzi");
			$("<div class='noneBox'>功能百科尚未收录此功能的词条。</br>欢迎您点击<a class='doEidt' href='#'>编辑</a>按钮创建词条，与同事分享关于此功能的信息。</div>")
			.appendTo(contentBox);
		}	
	}
	
}
var editor;
var BaikeEvents={
	editBtn:function(menuId,menuText,content){
		$("#baike .editbtn,.noneBox a.doEidt").click(function(){
			
			var defaultContent="<p style=\"text-align: left; text-indent: 2em;\" dir=\"rtl\">"
				+"<span style=\"border: currentColor; text-decoration: none;\"><strong>"+menuText	+"</strong></span>"
				+"</p><hr/><p></p>";
			if(content==""){
				content=defaultContent;			
			}
			$("#baike .editbtn").hide();
			$("#baike .savebtn").show();
			$("#baike .content").html("<script type='text/plain' id='editor'></script>");
			try{
				editor.destroy();
			}catch(e){
				//
			}
			editor=UE.getEditor('editor',{
			        initialFrameWidth : "100%",
			        initialFrameHeight: 290,
			        autoHeightEnabled:false
			 });
			 editor.ready(function(){
				  editor.setContent(content);
				  editor.focus(true);
			 });
		});
	},
	closeBtn:function(){
		$("#baike .closebtn").click(function(){
			try{
				editor.destroy();
			}catch(e){
				//
			}
			baikeBj.remove();
			baikeBox.remove();
		})
	},
	saveBtn:function(menuId,menuText){
		$("#baike .savebtn").click(function(){
			$.post("appCenterInitCmd.cmd?method=menuBaikeUpdate",
				{menuId:menuId,content:editor.getContent()},
				function(){
					MenuBaike.init(menuId,menuText,editor.getContent());
				}
			)
		})
	}
};
var msgLengthcache=0;;
var shortMsg={
	init:function(){
		$("#marquee-container").remove();
		$.post("/portal/portal/msgcenterbase.cmd?method=getShortMsg",function(data){
			var msgs=eval("("+data+")");
			msgLengthcache=msgs.length;
			if(msgs.length>0){
				var marquee=$("<div id='marquee-container'></div>")
				.append("<div class='marquee-icon'></div>")
				.append("<marquee class='marquee' onMouseOver='this.stop()' onMouseOut='this.start()'></marquee>")
				.appendTo("#subheadcontainer");
				shortMsg.fillData(msgs);
			}
			shortMsg.bindEvents();
		});
		shortMsg.flush();
		
	},
	fillData:function(msgs){
		var container=$("#marquee-container .marquee");
		container.html("");
		for(var i=0;i<msgs.length;i++){
			var msg=msgs[i];
			$("<span class='msg' mailId='"+msg.mailId+"'></span>")
			.append(msg.mailContent)
			.append("<div mailId='"+msg.mailId+"' class='remove'></div>")
			.appendTo(container);
		}
	},
	close:function(){
		$("#marquee-container").css("top",0);
	},
	open:function(){
		$("#marquee-container").css("top","10px");
	},
	flush:function(){
		setInterval(function(){
			$.post("/portal/portal/msgcenterbase.cmd?method=getShortMsg",function(data){
				var msgs=eval("("+data+")");
				if(msgs.length!=msgLengthcache){
					if($("#marquee-container").size()>0){
						shortMsg.fillData(msgs);
					}else{
						shortMsg.init();
					}
				}
			})
		},60000);
	},
	bindEvents:function(){
		$(".marquee .msg .remove").live("click",function(event){
			event.stopPropagation();
			var $this=$(this);
			var mailId=$(this).attr("mailId");
			$.post("/portal/portal/msgcenterbase.cmd?method=setShortMsgReaded&mailId="+mailId,function(){
				$this.closest(".msg").remove();
				msgLengthcache--;
			});
		});
		$(".marquee .msg").live("click",function(){
			var mailId=$(this).attr("mailId");
			InterfaceObj.creatTabWindow(mailId, "/base/mail/personMail.cmd?method=detail&mailId="+mailId, "我的短消息");
		});
	}
};

