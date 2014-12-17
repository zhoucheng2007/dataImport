	var calendar;
	$(document).ready(function() {
		
		
		querySchList();
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		schinnit("Schedulev6Cmd.cmd?method=querySchedule");
		$(".checkit").focus(function(){
			if($(this).val()==$(this).attr("defaultvalue")){
				$(this).val("");
			}
		});
		$(".checkit").blur(function(){
			var value=$(this).val();
			if(value==""){
				$(this).val($(this).attr("defaultvalue"));
			}
		});
		$(".letterArea span").click(function(){
			$(".letterArea span").removeClass("both");
			$(this).addClass("both");
			firstUserList();
		});
		$("#tomysch").click(function(){
			schinnit('Schedulev6Cmd.cmd?method=querySchedule');
			$(this).hide();
		});
		//日程查询
		
		$("#searchButton").click(function(){
			//$("start").val();
			document.forms[0].action="Schedulev6Cmd.cmd?method=check";
			document.forms[0].submit();
			});
		$("#publicBottom").click(function(){
			queryAttentionList();
			$( "#gettsdialog" ).dialog({
				width: "700",
				modal: true,
				title: "选择关注的同事",
				buttons: [{
	            id: "createfolderbtn",
	            text: "确定",
	            "class": "small-btn btn-blue",
	            click: function(){
	            	var path="";
	            	obj =$(".check_id");
	            	$.each(obj,function(i){
	            		path=path+$(this).text();
	            		if(i<obj.length-1){
	            			path=path+"#";
	            		}
	            		
	            	});	
	            	path=path.replace(/[ ]/g,"");
					$.ajax({
						  type: "post",
						  data:"PATH="+path,
						  url: "Schedulev6Cmd.cmd?method=updateAttention",
						  beforeSend: function(XMLHttpRequest){
						  },
						  success: function(data, textStatus){
							  $( "#gettsdialog" ).dialog( "close" );
							  schinnit("Schedulev6Cmd.cmd?method=queryAttSchedule");
							  $("#tomysch").show();
						  },
						  complete: function(XMLHttpRequest, textStatus){
						  },
						  error: function(){			
						  }
					  });
	            	
	            	}
				},
	            {
	                text: "取消",
	                "class": "small-btn",
	                click: function() {
	                	$( this ).dialog( "close" );
	                }
	            }],
	            open: function() {

	            }
			});
		});
		var menuHaveSon=$(".titlebar");
		  menuHaveSon.click(function(){
			  var browserVersion=$.browser.version;
			  var $this=$(this);
			  $this.toggleClass("cloes");
			  if(browserVersion=="7.0"){
					$("+div",$this).toggle();
			  }else{
					$("+div",$this).slideToggle(200);
			 }
			  //选择li后面紧接着的div
		  });
		timepickInit();
		firstUserList();
	});
	
	//初始化 
	function schinnit(url){
		$('#calendar').html("");
		calendar=$('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'today',
				center: 'prev,title,next',
				right: 'month,agendaWeek,agendaDay'
			},
			titleFormat: {
				month: 'yyyy年MMMM',
				week: "MMM d[ yyyy]{ '&#8212;'[ MMM] d yyyy}",
				day: 'dddd, MMM d, yyyy'
			},
			monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			dayNames: ['周日','周一','周二','周三','周四','周五','周六'],
			dayNamesShort: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
			buttonText: {
				prev: '&nbsp;&#9668;&nbsp;',
				next: '&nbsp;&#9658;&nbsp;',
				prevYear: '&nbsp;&lt;&lt;&nbsp;',
				nextYear: '&nbsp;&gt;&gt;&nbsp;',
				today: '今天	',
				month: '月',
				week: '周',
				day: '天'
			},
			selectable: true,
			selectHelper: true,
			select: function(start, end, allDay) {
				//创建日程
				createsch(start, end, allDay);
			},
			editable: true,
			eventClick: function(calEvent, jsEvent, view) {
				schclick(calEvent, jsEvent, view,calEvent.id);
			},
			///初始化数据
			events: function(start, end, callback) {
				$.getJSON(url, {START: $.fullCalendar.formatDate(start,"yyyy-MM-dd"), END: $.fullCalendar.formatDate(end,"yyyy-MM-dd")
				}, 
				function(result) {
					$.each(result,function(i){
						if(result[i].allDay=="false"){
							result[i].allDay=false;
						}
						if(result[i].editable=="false"){
							result[i].editable=false;
						}
						if(result[i].is_attention=="true"){
							result[i].is_attention=true;
						}else{
							result[i].is_attention=false;
						}
					});
					callback(result);
					}
				);
			},
			///拖动返回事件
			eventDrop: function(calEvent, jsEvent, view){
				var start=$.fullCalendar.formatDate(calEvent.start,"yyyy-MM-dd HH:mm");
				var end =$.fullCalendar.formatDate(calEvent.end,"yyyy-MM-dd HH:mm");
				//变更数据库
				$.ajax({
					  type: "post",
					  data:"ID="+calEvent.id+"&START="+start+"&END="+end,
					  url: "Schedulev6Cmd.cmd?method=updateSchedule",
					  beforeSend: function(XMLHttpRequest){
					  },
					  success: function(data, textStatus){
						  querySchList();
					  },
					  complete: function(XMLHttpRequest, textStatus){
					  },
					  error: function(){			
					  }
				  });
			},
			//改变大小事件
			eventResize: function(calEvent, jsEvent, view){
				var start=$.fullCalendar.formatDate(calEvent.start,"yyyy-MM-dd HH:mm");
				var end =$.fullCalendar.formatDate(calEvent.end,"yyyy-MM-dd HH:mm");
				//变更数据库
				$.ajax({
					  type: "post",
					  data:"ID="+calEvent.id+"&START="+start+"&END="+end,
					  url: "Schedulev6Cmd.cmd?method=updateSchedule",
					  beforeSend: function(XMLHttpRequest){
					  },
					  success: function(data, textStatus){
						  querySchList();
					  },
					  complete: function(XMLHttpRequest, textStatus){
					  },
					  error: function(){			
					  }
				  });
			}
			
		});
	}
	//点击事件
	function schclick(calEvent, jsEvent, view,id){
		var is_selcalEvent = false;
		if(calEvent==1){
			calEvent={id:id,uname:"",nameGroup:"",title:"",start:"",end:"",content:"",place:"",group:"",is_private:"",allDay:"",editable:""};
			is_selcalEvent=true;
		}
		$.ajax({
			  type: "post",
			  data:"ID="+id,
			  url: "Schedulev6Cmd.cmd?method=detailSchedule",
			  beforeSend: function(XMLHttpRequest){
			  },
			  success: function(data, textStatus){
				  var obj=eval(data);
					calEvent.uname=obj[0].uname;
					calEvent.nameGroup=obj[0].nameGroup;
					calEvent.title=obj[0].title;
					calEvent.start=obj[0].start;
					calEvent.end=obj[0].end;
					calEvent.content=obj[0].content;
					calEvent.place=obj[0].place;
					calEvent.group=obj[0].group;
					calEvent.is_private=obj[0].is_private;
					calEvent.allDay=obj[0].allDay==1?true:false;
					calEvent.editable=obj[0].editable=="false"?false:true;
					calendar.fullCalendar( 'updateEvent',calEvent);
					
					//浮动框
					var floatMsgTitle=calEvent.title;
					var floatMsgDate="";
					if(calEvent.end){
						floatMsgDate=$.fullCalendar.formatDate(calEvent.start,"yyyy/MM/dd HH:mm")+"-"+$.fullCalendar.formatDate(calEvent.end,"yyyy/MM/dd HH:mm");
					}else{
						var yy=$.fullCalendar.formatDate(calEvent.start,"yyyy");
						var mm=$.fullCalendar.formatDate(calEvent.start,"MM");
						var dd=$.fullCalendar.formatDate(calEvent.start,"dd");
						var now=calEvent.start;  
						var converted = Date.parse(now);
						var myDate = new Date(converted);
						var day=myDate.getDay();
						var week; 
						var arr_week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六"); 
						week=arr_week[day];
						floatMsgDate=$.fullCalendar.formatDate(calEvent.start,"yyyy年MM月dd日 HH时mm分")+" "+week+" "+GetCNDate(yy,mm,dd);
					}
					
					var $floatMsg;
					var $editorIt;
						if(calEvent.editable){
							$editorIt=$("<div></div>").addClass("detailaction")
							.append(
									$('<a href="javascript:;" class="left">删除</a>').click(function(){
										
										
										//alert("ajax删除日程");
										$("#pubdialog").html("确定删除日程?");
										$("#pubdialog").dialog({
								            width: "400",
								            title: "删除日程",
								            modal: true,
								            buttons: [{
								                id: "createfolderbtn",
								                text: "确定",
								                "class": "small-btn btn-blue",
								                click: function(){
								                	$.ajax({
														  type: "post",
														  data:"ID="+calEvent.id,
														  url: "Schedulev6Cmd.cmd?method=deleteSchedule",
														  beforeSend: function(XMLHttpRequest){
														  },
														  success: function(data, textStatus){
															  calendar.fullCalendar( 'removeEvents',calEvent.id);
															  querySchList();
														  },
														  complete: function(XMLHttpRequest, textStatus){
														  },
														  error: function(){		
															  alert("删除出现问题。");
														  }
													 });
								                	$floatMsg.remove();
								                	$("#pubdialog").dialog("close");
								                }
								            },
								            {
								                text: "取消",
								                "class": "small-btn",
								                click: function() {
								                	$("#pubdialog").dialog("close");
								                }
								            }]
										});
										
									})
							)				
							.append(						
								$('<a href="javascript:;" class="right">编辑日程</a>').click(function(){
								$floatMsg.remove();
								$("#txtCalendarName").val(floatMsgTitle);
								$("#begindate").val($.fullCalendar.formatDate(calEvent.start,"yyyy-MM-dd"));
								$("#enddate").val($.fullCalendar.formatDate(calEvent.end,"yyyy-MM-dd"));
								$("#begintime").val($.fullCalendar.formatDate(calEvent.start,"HH:mm"));
								$("#endtime").val($.fullCalendar.formatDate(calEvent.end,"HH:mm"));
			                	$("#txtAddress").val(calEvent.place);
								$("#txtDesc").val(calEvent.content);
								$("#groupItId").val(calEvent.group);
								$("#groupIt").val(calEvent.nameGroup);
								if(calEvent.is_private==1){
									$("#isprivate").parent().find('.jNiceCheckbox').addClass('jNiceChecked');
									$("#isprivate").attr("checked","checked");
								}else{
									$("#isprivate").parent().find('.jNiceCheckbox').removeClass('jNiceChecked');
									$("#isprivate").removeAttr("checked");
								}
								if(calEvent.allDay){
									$(".timeImg").attr("disabled",true);
									$("#cbAllday").parent().find('.jNiceCheckbox').addClass('jNiceChecked');
									$("#cbAllday").attr("checked","checked");
								}else{
									$(".timeImg").attr("disabled",false);
									$("#cbAllday").parent().find('.jNiceCheckbox').removeClass('jNiceChecked');
									$("#cbAllday").removeAttr("checked");
								}
								$( "#dialog" ).dialog({
									width: "470",
									modal: true,
									title: "编辑日程",
									buttons: [{
					                id: "createfolderbtn",
					                text: "确定",
					                "class": "small-btn btn-blue",
					                click: function(){
					                	title=$("#txtCalendarName").val();
					                	var txtAddress=$("#txtAddress").val();
										var txtDesc=$("#txtDesc").val();
										var groupIt =$("#groupItId").val();
										var nameGroup=$("#groupIt").val();
										var starttime=$("#begindate").val()+" "+$("#begintime").val();
										var endtime=$("#enddate").val()+" "+$("#endtime").val();
										var allchecked=$("#cbAllday").attr("checked");
										var isprivateche=$("#isprivate").attr("checked");
										var allDayObj=0;
										if(allchecked=="checked"){
											allDayObj=1;
										}
										var isprivate=0;
										if(isprivateche=="checked"){
											isprivate=1;
										}
										
										calEvent.title=title;
										calEvent.start=starttime;
										calEvent.end=endtime;
										calEvent.content=txtDesc;
										calEvent.place=txtAddress;
										calEvent.group=groupIt;
										calEvent.nameGroup=nameGroup;
										calEvent.is_private=isprivate;
										calEvent.allDay=allDayObj==1?true:false;
										calendar.fullCalendar( 'updateEvent',calEvent);
										
										//alert("ajax编辑日程存入数据库");
										title=encodeURI(encodeURI(title));
										txtDesc=encodeURI(encodeURI(txtDesc));
										txtAddress=encodeURI(encodeURI(txtAddress));
										/*日程开始时间与结束时间修改
										  wyx  
										  2013-12-11
										*/
										if(starttime>endtime){
											alert("输入的日程开始时间与结束时间矛盾，请重新输入");
											$("#begindate").val($.fullCalendar.formatDate(calEvent.start,"yyyy-MM-dd"));
											$("#enddate").val($.fullCalendar.formatDate(calEvent.end,"yyyy-MM-dd"));
											$("#begintime").val($.fullCalendar.formatDate(calEvent.start,"HH:mm"));
											$("#endtime").val($.fullCalendar.formatDate(calEvent.end,"HH:mm"));
										}else{
										$( this ).dialog( "close" );	
										$.ajax({
											  type: "post",
											  data:"ID="+calEvent.id+"&TITLE="+title+"&CONTENT="+txtDesc+"&START="+starttime+"&END="+endtime+"&PLACE="+txtAddress+"&GROUP="+groupIt+"&IS_PRIVATE="+isprivate+"&ALLDAY="+allDayObj,
											  url: "Schedulev6Cmd.cmd?method=updateSchedule",
											  beforeSend: function(XMLHttpRequest){
											  },
											  success: function(data, textStatus){
												  querySchList();
												//重新加载
													if(is_selcalEvent){
														 $('#calendar').fullCalendar("refetchEvents");
													}
											  },
											  complete: function(XMLHttpRequest, textStatus){
											  },
											  error: function(){			
											  }
										  });
										}
										}
										
									},
						            {
						                text: "取消",
						                "class": "small-btn",
						                click: function() {
						                	$( this ).dialog( "close" );
						                	//window.location.reload(); 
						                }
						            }],
						            open: function() {
						            	$(".dateImgv6").datepicker({
						            		changeMonth : true,
						            		changeYear : true,
						            		changeDay : false,
						            		timePicker :false,
						            		dateFormat : "yy-mm-dd",
						            		altFormat : "yymmdd",
						            		beforeShow: function () { 
						            			setTimeout( 
						            			function () { 
						            			$('#ui-datepicker-div').css("z-index", 1013); 
						            			}, 100 
						            			); 
						            		} 
						            	});
						            }
								});
							})
						)
					}
					$floatMsg=$("<div id=\"diatopTitle\"></div>")
					.css({
						"top":jsEvent.pageY+12+"px",
						"left":jsEvent.pageX-150+"px"
					})
					.append(
						$("<div></div>").addClass("detailtitle")
						.append(
								$('<a href="javascript:;" class="dialogclose"></a>').click(function(){
									$floatMsg.remove();
								})
						)
						.append(
								$('<span id="caltitle"></span>').text(floatMsgTitle)
						)
						
						
					)
					.append(
						$("<div></div>").addClass("detailtime")
						.append(
							$("<img />").addClass("left").attr("src","/portal/skin/css/schedulev6/mycalendar/calTime.gif")
						)
						.append(
							$("<span></span>").addClass("left").text("时间 ：")
						)
						.append(
							$("<span id=\"caldatecode\"></span>").text(floatMsgDate)
						)
					)
					.append(
						$("<div></div>").addClass("detailtime")
						.append(
							$("<img />").addClass("left").attr("src","/portal/skin/css/schedulev6/mycalendar/calTime.gif")
						)
						.append(
							$("<span></span>").addClass("left").text("发起人 ：")
						)
						.append(
							$("<span id=\"caldatecode\"></span>").text(calEvent.uname)
						)
					)
					.append(
						$("<div></div>").addClass("detailplace")
						.append(
							$("<img />").addClass("left").attr("src","/portal/skin/css/schedulev6/mycalendar/calPlace.gif")
						)
						.append(
							$("<span></span>").addClass("left").text("地点 ：")
						)
						.append(
							$("<span id=\"calplace\"></span>").text(calEvent.place)
						)
					)
					.append(
						$("<div></div>").addClass("detailman")
						.append(
							$("<img />").addClass("left").attr("src","/portal/skin/css/schedulev6/mycalendar/calParticipants.gif")
						)
						.append(
							$("<span></span>").addClass("left").text("参与人： ")
						)
						.append(
							$("<span id=\"calman\"></span>").text(calEvent.nameGroup)
						)
					)
					.append(
						$("<div></div>").addClass("detailexp")
						.append(
							$("<img />").addClass("left").attr("src","/portal/skin/css/schedulev6/mycalendar/calExplanatory.gif")
						)
						.append(
							$("<span></span>").addClass("left").text("摘要：")
						)
						.append(
							$("<span id=\"calexp\"></span>").text(calEvent.content)
						)
					)
					.append(
							$editorIt
					)
					.appendTo("body");
					//浮动框结束
					
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(){			
			  }
		  });
	}
	///打开新建日程 
	function createsch(start, end, allDay) {
		if(start!=undefined){		
			$("#begindate").val($.fullCalendar.formatDate(start,"yyyy-MM-dd"));
			$("#enddate").val($.fullCalendar.formatDate(end,"yyyy-MM-dd"));
		}else{
			$(".dateImgv6").val(getToday());
		}
		//initTextAera();
		var title="";
		initTextAera();
		//$("#txtCalendarName").val($("#txtCalendarName").attr("defaultvalue"));
		$( "#dialog" ).dialog({
			width: "470",//470
			modal: true,
			title: "创建新日程",
			buttons: [{
            id: "createfolderbtn",
            text: "确定",
            "class": "small-btn btn-blue",
            click: function(){
            	title=$("#txtCalendarName").val();
            	var txtAddress=$("#txtAddress").val();
				var txtDesc=$("#txtDesc").val();
				var groupIt =$("#groupItId").val();
				var nameGroup=$("#groupIt").val();
				var allchecked=$("#cbAllday").attr("checked");
				var isprivateche=$("#isprivate").attr("checked");
				var allDayObj=0;
				if(allchecked=="checked"){
					allDayObj=1;
				}
				var isprivate=0;
				if(isprivateche=="checked"){
					isprivate=1;
				}
				//$( this ).dialog( "close" );
				if (title!="") {
					var id=(new Date()).getTime()+parseInt(Math.random()*10000);
					var starttime=$("#begindate").val()+" "+$("#begintime").val();
					var endtime=$("#enddate").val()+" "+$("#endtime").val();
					var uid=$("#USER_ID").val();
					var uname = $("#USER_NAME").val();
					calendar.fullCalendar('renderEvent',
						{
							id:id,
							title: title,
							start: starttime,
							uid:uid,
							uname:uname,
							end: endtime,
							content:txtDesc,
							place:txtAddress,
							group:groupIt,
							editable:true,
							nameGroup:nameGroup,
							allDay : allDayObj==1?true:false
						},
						true
					);
					calendar.fullCalendar('unselect');
					//alert("ajax新建日程存入数据库");
					title=encodeURI(encodeURI(title));
					txtDesc=encodeURI(encodeURI(txtDesc));
					txtAddress=encodeURI(encodeURI(txtAddress));
					/*日程开始时间与结束时间修改
					  wyx  
					  2013-12-11
					*/
					if(starttime>endtime){
						alert("您输入的日程开始时间与结束时间矛盾，请重新输入");
					  $(".dateImgv6").val(getToday());
					}else{
					$( this ).dialog( "close" );
					$.ajax({
						  type: "post",
						  data:"ID="+id+"&TITLE="+title+"&CONTENT="+txtDesc+"&START="+starttime+"&END="+endtime+"&PLACE="+txtAddress+"&GROUP="+groupIt+"&IS_PRIVATE="+isprivate+"&ALLDAY="+allDayObj,
						  url: "Schedulev6Cmd.cmd?method=addSchedule",
						  beforeSend: function(XMLHttpRequest){
						  },
						  success: function(data, textStatus){
							  querySchList();
						  },
						  complete: function(XMLHttpRequest, textStatus){
						  },
						  error: function(){			
						  }
					  });
					}
				}
            	}
			},
            {
                text: "取消",
                "class": "small-btn",
                click: function() {
                	$( this ).dialog( "close" );
                }
            }],
            open: function() {
            	$(".dateImgv6").datepicker({
            		changeMonth : true,
            		changeYear : true,
            		changeDay : false,
            		timePicker :false,
            		dateFormat : "yy-mm-dd",
            		altFormat : "yymmdd",
            		beforeShow: function () { 
            			setTimeout( 
            			function () { 
            			$('#ui-datepicker-div').css("z-index", 1013); 
            			}, 100 
            			); 
            		} 
            	});
            }
		});
	}
	//隐藏输入框
	function hidePanel() {
		$( "#dialog" ).dialog("close");
	}
	//今天的日期
	function getToday(){
		var myDate = new Date();
		var year=myDate.getFullYear();
		var month=myDate.getMonth();
		var day=myDate.getDate();
		if(month<9){
			month="0"+(month+1);
		}else{
			month=""+(month+1);
		}
		if(day<=9){
			day="0"+day;
		}
		return year+"-"+month+"-"+day;
	}
	//选择全天事件
	function  checkedAllday(){
		var checked=$("#cbAllday").attr("checked");
		if(checked=="checked"){
			$(".timeImg").attr("disabled",true);
			//$("#begintime").attr("disabled",true);
			$("#begintime").val("00:00");
			$("#endtime").val("23:30");
		}else{
			$(".timeImg").attr("disabled",false);
		}
	}
	///初始化各个输入框
	function initTextAera(){
		var obj = $(".checkit");
		//$(".timeImg").removeAttr("disabled");
		$.each(obj,function(i){
			obj.eq(i).val(obj.eq(i).attr("defaultvalue"));
		});
	}
	/**
	 * 选择职工的帮助框
	 * @param id 回存职工id的input标签名
	 * @param name 回存职工名称的input标签名
	 */
	function getPerson(){
		$('#popupMask').css("z-index", 1006);
		var organId = $("#comId").val();
		//showPopWin('选择','/v6/selOrganHelp.cmd?selType=8&isChkbox=1&isCascadeSelect=0&struType=00&isPreciseMatch=0&isDataPermitControl=false&rootId='+organId,400,400,function (returnVal){
		showPopWin('选择','/v6/selOrganHelp.cmd?selType=8&isChkbox=1&isCascadeSelect=0&struType=00&isPreciseMatch=0&isDataPermitControl=0',400,400,function (returnVal){
				
				var orgpath="";
				var namepath="";
				$.each(returnVal,function(i){
					orgpath=orgpath+returnVal[i].struId;
					namepath=namepath+returnVal[i].organName;
					if(i<returnVal.length-1){
						orgpath=orgpath+"#";
						namepath=namepath+";";
					}
				});
				if(returnVal.length!=0){
					$("#groupIt").attr("value", namepath);
					$("#groupItId").attr("value", orgpath);
				}
			},true,true);
		$("#popupFrame").attr("scrolling","no");
	}
	//时间段选择初始化
	function timepickInit(){
		var html="<ul ><li>00:30</li><li>01:00</li><li>01:30</li><li>02:00</li><li>02:30</li><li>03:00</li><li>03:30</li><li>04:00</li><li>04:30</li><li>05:00</li><li>05:30</li><li>06:00</li><li>06:30</li><li>07:00</li><li>07:30</li><li>08:00</li><li>08:30</li><li>09:00</li><li>09:30</li><li>10:00</li><li>10:30</li><li>12:00</li><li>12:30</li><li>13:00</li><li>13:30</li><li>14:00</li><li>14:30</li><li>15:00</li><li>15:30</li><li>16:00</li><li>16:30</li><li>17:00</li><li>17:30</li><li>18:00</li><li>18:30</li><li>19:00</li><li>19:30</li><li>20:00</li><li>20:30</li><li>21:00</li><li>21:30</li><li>22:00</li><li>22:30</li><li>23:00</li><li>23:30</li></ul>";
		$(".timeul").addClass("ui-widget-content ui-corner-all").html(html);
		$(".timeul li").click(function(){
			$(this).parent().find('li').removeClass('active');
			$(this).addClass('active');
			$(this).parent().parent().parent().find('input').val($(this).text());
			$(this).parent().parent().hide();
		});
		$(".timeImg").click(function(){
			$(this).parent().find('.timeul').show();
		});
	}
	//姓名首字查询列表
	function firstUserList(){
		var fir=$(".letterArea .both").text();
		$.ajax({
			  type: "post",
			  data:"FIRST="+fir,
			  url: "Schedulev6Cmd.cmd?method=queryUserByfir",
			  beforeSend: function(XMLHttpRequest){
				  $(".userList").html("");
			  },
			  success: function(data, textStatus){
				  var obj=eval(data);
				  $.each(obj,function(i){
					  $("<li></li>")
					  .append(
						$("<span></span>").text(obj[i].USER_ID).addClass("baruserid")	  
					  )
					  .append(
						$("<span></span>").text(obj[i].USER_NAME).addClass("barname")  
					  )
					  .append(
						$("<span onclick='addAction(this)'></span>").text("添加").addClass("addaction")  
					  ).appendTo(".userList");
					  
				  });
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(){			
			  }
		  });
	}
	//名字模糊查询
	function queryByNameList(){
		var USER_NAME=$("#rnSearch").val();
		USER_NAME=encodeURI(encodeURI(USER_NAME));
		if(USER_NAME==""){
			alert("请输入查询条件！");
		}
		$.ajax({
			  type: "post",
			  data:"USER_NAME="+USER_NAME,
			  url: "Schedulev6Cmd.cmd?method=queryByName",
			  beforeSend: function(XMLHttpRequest){
				  $(".userList").html("");
			  },
			  success: function(data, textStatus){
				  var obj=eval(data);
				  $.each(obj,function(i){
					  $("<li></li>")
					  .append(
						$("<span></span>").text(obj[i].USER_ID).addClass("baruserid")	
					  )
					  .append(
						$("<span></span>").text(obj[i].USER_NAME).addClass("barname")  
					  )
					  .append(
						$("<span onclick='addAction(this)'></span>").text("添加").addClass("addaction")  
					  ).appendTo(".userList");
					  
				  });
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(){			
			  }
		  });
	}
	//关注列表查询
	function queryAttentionList(){
		$.ajax({
			  type: "post",
			  url: "Schedulev6Cmd.cmd?method=queryAttention",
			  beforeSend: function(XMLHttpRequest){
				  $(".checkuserList").html("");
			  },
			  success: function(data, textStatus){
				  var obj=eval(data);
				  $.each(obj,function(i){
					  $("<li></li>")
					  .append(
						$("<img></img>").attr("src","/portal/skin/css/schedulev6/mycalendar/man.gif")	  
					  )
					  .append(
						$("<span class='check_id'></span>").text(obj[i].USER_ID)  
					  )
					  .append(
						$("<span></span>").text(obj[i].USER_NAME).addClass("barname")  
					  )
					  .append(
						$("<img onclick='removeAction(this)'></img>").attr("src","/portal/skin/css/schedulev6/mycalendar/remove_flag.gif").addClass("deletecation")  
					  ).appendTo(".checkuserList");
					  
				  });
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(){			
			  }
		  });
	}
	function addAction(obj){
			var check_id = $(obj).parent().find(".baruserid").text(); 
			var check_name = $(obj).parent().find(".barname").text();
			var fla=true;
			$(".check_id").each(function(){
        		var it=$(this).text();
        		if(check_id==it){
        			fla=false;
        		}
        		
        	});
			if(!fla){
				alert("该用户已关注！");
				return false;
			}
			$("<li></li>")
			  .append(
				$("<img></img>").attr("src","/portal/skin/css/schedulev6/mycalendar/man.gif")	  
			  )
			  .append(
				$("<span class='check_id'></span>").text(check_id)  
			  )
			  .append(
				$("<span></span>").text(check_name).addClass("barname")  
			  )
			  .append(
				$("<img onclick='removeAction(this)'></img>").attr("src","/portal/skin/css/schedulev6/mycalendar/remove_flag.gif").addClass("deletecation")  
			  ).appendTo(".checkuserList");
	}
	function removeAction(obj){
		$(obj).parent().remove();
	}
	/**
	 * 选择职工的帮助框
	 * @param id 回存职工id的input标签名
	 * @param name 回存职工名称的input标签名
	 */
	function getAttentiontree(){
		$('#popupMask').css("z-index", 1006); 
		showPopWin('选择','/v6/selOrganHelp.cmd?selType=8&isChkbox=1&isCascadeSelect=0&struType=00&isPreciseMatch=0&isDataPermitControl=false',400,400,function (returnVal){				
				var orgpath="";
				var namepath="";
				$.each(returnVal,function(i){
					orgpath=orgpath+returnVal[i].struId;
					namepath=namepath+returnVal[i].organName;
					if(i<returnVal.length-1){
						orgpath=orgpath+"#";
						namepath=namepath+";";
					}
					
				});
				orgpath=orgpath.replace(/[ ]/g,"");
				$.ajax({
					  type: "post",
					  data: "PATH="+orgpath,
					  url: "Schedulev6Cmd.cmd?method=userInfoUtil",
					  beforeSend: function(XMLHttpRequest){
					  },
					  success: function(data, textStatus){
						  var obj=eval(data);
						  $.each(obj,function(i){
							var fla=true;
							$(".check_id").each(function(){
				        		var it=$(this).text();
				        		if(obj[i].USER_ID==it){
				        			fla=false;
				        		}
				        		
				        	});
							if(fla){
							  $("<li></li>")
							  .append(
								$("<img></img>").attr("src","/portal/skin/css/schedulev6/mycalendar/man.gif")	  
							  )
							  .append(
								$("<span class='check_id'></span>").text(obj[i].USER_ID)  
							  )
							  .append(
								$("<span></span>").text(obj[i].USER_NAME).addClass("barname")  
							  )
							  .append(
								$("<img onclick='removeAction(this)'></img>").attr("src","/portal/skin/css/schedulev6/mycalendar/remove_flag.gif").addClass("deletecation")  
							  ).appendTo(".checkuserList");
							}
							  
						  });
					  },
					  complete: function(XMLHttpRequest, textStatus){
					  },
					  error: function(){			
					  }
				  });
				
			},true,true);
		$("#popupFrame").attr("scrolling","no");
	}
	///日程3天列表
	function querySchList(){
		$.ajax({
			  type: "post",
			  url: "Schedulev6Cmd.cmd?method=querySchedule",
			  beforeSend: function(XMLHttpRequest){
				  $(".calendar-bar ul").html("");
			  },
			  success: function(data, textStatus){
				  var obj=eval(data);
					$.each(obj,function(i){
						$("<li></li>").append(
							$("<span></span>").text(obj[i].start.substr(5,2)+"月"+obj[i].start.substr(8,2)+"日")
						).append(
							$("<div></div>").append($("<a></a>").text(obj[i].title).attr("href","javascript:schclick(1, 2, 3,"+obj[i].id+");"))
						).appendTo(".calendar-bar ul");
					});
			  },
			  complete: function(XMLHttpRequest, textStatus){
			  },
			  error: function(){			
			  }
		  });
	}
	//wyx添加查询框判断js
	function check_condition(obj){
		if(obj.value=="")
	     obj.value="日程搜索";
	}