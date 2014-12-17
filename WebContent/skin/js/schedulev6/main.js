	var calendar
	$(document).ready(function() {
		
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		var calendar=$('#calendar').fullCalendar({
			theme: true,
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
				window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_blank");
			},
			editable: false,
			eventClick: function(calEvent, jsEvent, view) {
				window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_blank");
			},
			///初始化数据
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