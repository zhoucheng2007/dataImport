<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<title>江西烟草应用集成平台</title>
		<script type="text/javascript" src="/skin/js/headindex.js"></script>	
		<script type="text/javascript">initHeadBar(false)</script>
		<!--<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/zTreeStyle/zTreeStyle.css">-->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/picnews/nivo.slider.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/reset.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/picnews/default/default.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/dhtmlxschedulerIndex.css">
		<link id="bannerStyle" href="<%=request.getContextPath()%>/skin/css/cq/css/banner.css" rel="stylesheet" type="text/css" />

		<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/cq/jquery.cookie.js"></script>
		<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/cq/styleswitch.js"></script>
		<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/cq/picnews/nivo.slider.js"></script>		
		<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/cq/jquery-scrollimg.js"></script>
		<link id="changeStyle" title=css3  href="<%=request.getContextPath()%>/skin/css/cq/css/change3.css" rel="stylesheet" type="text/css" />
		<link id="mainStyle" href="<%=request.getContextPath()%>/skin/css/cq/css/main.css" rel="stylesheet" type="text/css" />
		<link  href="<%=request.getContextPath()%>/skin/css/cq/css/head.css" rel="stylesheet" type="text/css" />


		<script type='text/javascript' src='<%=request.getContextPath()%>/skin/js/cq/fullcalendar.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/skin/js/cq/cncal.js'></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/fullcalendar.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/mycalendar.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/pupup.css">
		<!--<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/metaurl.js"></script>-->
		<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/cq/base64.js"></script>
		
		<!--<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/lync.js"></script>
		<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/pubcommonhelp.js"></script>
		
		<script language="javascript" src="/skin/js/head/conf.js"></script>-->
		
		
	<!--	<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/jquery.ztree-lync-2.6.js"></script>-->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/skin/css/cq/css/zTreeStyle/zTreeStyle.css">
		<script type="text/javascript" src="<%=request.getContextPath()%>/skin/js/cq/ztree/jquery.ztree.core-3.5.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function(){
				document.getElementById('orgselectboxes').style.display="inline";
				$("#dqlx").click(function(){
					window.open("http://10.11.2.21:8083/eap/jysjhd");
				});
				
				//init(1);
				$("#head-layout .menu .login-userinfo")
				.before(
					$("<div class='sit-option'></div>")
					.append(
						$("<div id='setfavorite' class='sit-btn'>收藏此页</div>")
						.click(function(){
							AddFavorite("http://d3.cq.tobacco.gov.cn/sit/firstpage.aspx","智慧渝烟应用平台");
						})
					)
				)
			})
			function AddFavorite(sURL, sTitle) { 
				try 
				{ 
					window.external.addFavorite(sURL, sTitle); 
				} 
				catch (e) 
				{ 
					try 
					{ 
						window.sidebar.addPanel(sTitle, sURL, ""); 
					} 
					catch (e) 
					{ 
						alert("加入收藏失败，请使用Ctrl+D进行添加"); 
					} 
				} 
			}
			var _speed=30;
			var _slide = $("#slide");  
			var _slideli1 = $(".slideli1");
			var _slideli2 = $(".slideli2");
			_slideli2.html(_slideli1.html());
			function Marquee(){
				if(_slide.scrollLeft() >= _slideli1.width())
					_slide.scrollLeft(0);
				else{
					_slide.scrollLeft(_slide.scrollLeft()+1);
				}
			}
			$(function(){
				//两秒后调用
				var sliding=setInterval(Marquee,_speed)
				_slide.hover(function() {
					//鼠标移动DIV上停止
					clearInterval(sliding);
				},function(){
					//离开继续调用
					sliding=setInterval(Marquee,_speed);
				});
			});
			function yanyekanban(){
					$("#yanyehuimindiv").show();
					$(".banner-btn-container").css("margin-top","25px");
					$("#banner-content").hide();
					$("#orgselect").hide();
			}
			function otherkanban(){
					$("#banner-content").show();
					$("#yanyehuimindiv").hide();
					$("#orgselect").show();
					$(".banner-btn-container").css("margin-top","0px");
			}
			function init(parm) {
				scheduler.config.multi_day = true;
				scheduler.config.xml_date="%Y-%m-%d %H:%i";
				scheduler.init('scheduler_here',null,"month");
				var url1 = document.getElementById("reqUrl").value;
				var from = document.getElementById("begin").value;
				var to = document.getElementById("end").value;
				var user = document.getElementById("userId").value;
				var convert = scheduler.date.date_to_str("%Y-%m-%d %H:%i:%s");
				var some = convert( scheduler._date );
				var sd = scheduler.date.str_to_date("%Y-%m-%d %H:%i:%s");
				var tmpd = sd(from);
				var nm = scheduler.date.week_start(tmpd);
				from = convert(nm);
				if(from.length>10){
					from = from.substring(0,10);
				}
				if(to.length>10){
					to = to.substring(0,10);
				}
				var url = url1+"queryScheduleCommand.cmd?method=getStatisticsJson&user="+user+"&from="+from+"&to="+to;
				$("#ur").attr("value",url);
				scheduler.load(url,"json");
			}
		</script>

	</head>
	<body>
		<div id='head-layout' style="height: 98px;">
			<div class="logo">
				<object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="960" height="61">
  					<param name="movie" value="yyjc.swf" />
					  <param name="quality" value="high" />
					  <param name="wmode" value="opaque" />
					  <param name="swfversion" value="15.0.0.0" />
					  <!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
					  <param name="expressinstall" value="Scripts/expressInstall.swf" />
					  <!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
					  <!--[if !IE]>-->
					  <object type="application/x-shockwave-flash" data="<%=request.getContextPath()%>/skin/css/cq/imgs/yyjc.swf" width="960" height="61">
					    <!--<![endif]-->
					    <param name="quality" value="high" />
					    <param name="wmode" value="opaque" />
					    <param name="swfversion" value="15.0.0.0" />
					    <param name="expressinstall" value="Scripts/expressInstall.swf" />
					    <!-- 浏览器将以下替代内容显示给使用 Flash Player 6.0 和更低版本的用户。 -->
					    <div>
					      <h4>此页面上的内容需要较新版本的 Adobe Flash Player。</h4>
					      <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="获取 Adobe Flash Player" width="112" height="33" /></a></p>
					    </div>
					    <!--[if !IE]>-->
					  </object>
					  <!--<![endif]-->
					</object>
				<script type="text/javascript">
					//swfobject.registerObject("FlashID");
				</script>
			</div>
		 	<div id="headholder"></div>
		</div>
	<script type='text/javascript' src="<%=request.getContextPath()%>/skin/js/cq/mainnew.js"></script>
</html>