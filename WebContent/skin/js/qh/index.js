$(document).ready(function()  {
 
	
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
		var title="";
		$("#dlg_title").val("");
		$("#dlg_start_date").text($.fullCalendar.formatDate(start,"yyyy年MM月dd日"));
		$( "#dialog" ).dialog({
			width:310,
			modal: true,
			buttons: {
				Ok: function() {
					title=$("#dlg_title").val();
					encodeURI
					$( this ).dialog( "close" );
					if (title!="") {						
						var id=(new Date()).getTime()+parseInt(Math.random()*10000);
						calendar.fullCalendar('renderEvent',
							{
								id:id,
								title: title,
								start: start,
								end: end,
								allDay: allDay
							},
							true // make the event "stick"
						);
						calendar.fullCalendar('unselect');
						title=encodeURI(encodeURI(title));
						$.ajax({
							  type: "post",
							  data:"ID="+id+"&TITLE="+title+"&CONTENT="+title+"&START="+$.fullCalendar.formatDate(start,"yyyy-MM-dd")+"&END="+$.fullCalendar.formatDate(end,"yyyy-MM-dd"),
							  url: "ScheduleCmd.cmd?method=addSchedule",
							  beforeSend: function(XMLHttpRequest){
							  },
							  success: function(data, textStatus){
							  },
							  complete: function(XMLHttpRequest, textStatus){
							  },
							  error: function(){			
							  }
						  });
					}
					
				}
			}
		});
	},
	editable: true,
	eventClick: function(calEvent, jsEvent, view) {
		
		//浮动框
		var floatMsgTitle=calEvent.title;
		var floatMsgDate="";
		if(calEvent.end){
			floatMsgDate=$.fullCalendar.formatDate(calEvent.start,"yyyy年MM月dd日")+"-"+$.fullCalendar.formatDate(calEvent.end,"yyyy年MM月dd日");
		}else{
			var yy=$.fullCalendar.formatDate(calEvent.start,"yyyy");
			var mm=$.fullCalendar.formatDate(calEvent.start,"MM");
			var dd=$.fullCalendar.formatDate(calEvent.start,"dd");
			var now=calEvent.start;  
			var day=now.getDay();
			var week; 
			var arr_week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六"); 
			week=arr_week[day];
			floatMsgDate=$.fullCalendar.formatDate(calEvent.start,"yyyy年MM月dd日")+" "+week+" "+GetCNDate(yy,mm,dd);
		}
		var m_top=jsEvent.pageY+12;
		var m_left=jsEvent.pageX-150;
		if(m_left<=0){
			m_left=2;
		}
		var $floatMsg=$("<div></div>")
		.addClass("floatMsg")
		.css({
			"top":m_top+"px",
			"left":m_left+"px"
		})
		.append(
			$("<div></div>").addClass("clostBtn")
			.click(function(){
				$floatMsg.remove();
			})
		)
		.append(
			$("<div></div>").addClass("content")
			.append(
				$("<div id='fm_date'></div>").text(floatMsgDate)
			)
			.append(
				$("<div id='fm_title'></div>").text(floatMsgTitle)
			)
			
		)
		.append(
			$("<div></div>").addClass("option")
			.append(
				$("<span>删除</span>").click(function(){
					calendar.fullCalendar( 'removeEvents',calEvent.id);
					$floatMsg.remove();
					$.ajax({
						  type: "post",
						  data:"ID="+calEvent.id,
						  url: "ScheduleCmd.cmd?method=deleteSchedule",
						  beforeSend: function(XMLHttpRequest){
						  },
						  success: function(data, textStatus){
						  },
						  complete: function(XMLHttpRequest, textStatus){
						  },
						  error: function(){			
						  }
					  });
				})
			)
			.append(
				$("<span>修改</span>").click(function(){
					$floatMsg.remove();
					$("#dlg_title").val(floatMsgTitle);
					$("#dlg_start_date").text($.fullCalendar.formatDate(calEvent.start,"yyyy年MM月dd日"))
					$( "#dialog" ).dialog({
						width:310,
						modal: true,
						buttons: {
							Ok: function() {
								title=$("#dlg_title").val();
								calEvent.title=title;
								calendar.fullCalendar( 'updateEvent',calEvent);
								title=encodeURI(encodeURI(title));
								$.ajax({
									  type: "post",
									  data:"ID="+calEvent.id+"&TITLE="+title+"&CONTENT="+title,
									  url: "ScheduleCmd.cmd?method=updateSchedule",
									  beforeSend: function(XMLHttpRequest){
									  },
									  success: function(data, textStatus){
									  },
									  complete: function(XMLHttpRequest, textStatus){
									  },
									  error: function(){			
									  }
								  });
								$( this ).dialog( "close" );
							}
						}
					});
				})
			)
		)
		.appendTo("body")
		//alert('Event: ' + calEvent.title);
		//alert('start: ' + calEvent.start);
		//alert('end: ' + calEvent.end);
		//alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
//		alert('View: ' + view.name);
		
	

	},
	events: function(start, end, callback) {
		$.getJSON("ScheduleCmd.cmd?method=querySchedule", {START: $.fullCalendar.formatDate(start,"yyyy-MM-dd"), END: $.fullCalendar.formatDate(end,"yyyy-MM-dd")
			}, function(result) {callback(result);}
			)
		}
	
})      
});



function formatDt(date){
	if((date==null)||(date=="")){
		return "";
	}else{
		return date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
	}
}


