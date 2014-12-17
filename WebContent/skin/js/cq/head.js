$(document).ready(function(){
	$('#mainpage').click(function(){	
		var url='http://d3.cq.tobacco.gov.cn/sit/firstpage.aspx';
		window.location.href(url);
	})
	
	$('#survey').click(function(){
		var url='http://10.11.2.160:9080/ycportal/webpublish/block.888.view.detail.newsdetail?key=10411';
		window.location.href(url);
	})
	$('#leaders').click(function(){
		var url='http://10.11.2.160:9080/ycportal/webpublish/block.888.view.detail.newsdetail?key=10412';
		window.location.href(url);
	})
	$('#dptduty').click(function(){
		var url='http://10.11.2.160:9080/ycportal/webpublish/block.881.view.list.newslist';
		window.location.href(url);
	})
	$('#importnote').click(function(){
		var url='http://10.11.2.160:9080/ycportal/webpublish/block.886.view.list.newslist';
		window.location.href(url);
	})

	
	$('#myspace').click(function(){
		var url='http://d3.cq.tobacco.gov.cn/sit/personalspace.aspx';
		window.location.href(url);
	})
	$('#mygroup').click(function(){
		var url='http://d3.cq.tobacco.gov.cn/sit/groupspace.aspx';
		window.location.href(url);
	})
	$('#pspace').click(function(){
		var url='http://d3.cq.tobacco.gov.cn/sit/myspace.aspx';
		window.location.href(url);
	})
	$('#decisioncenter').click(function(){
		var url='http://d3.cq.tobacco.gov.cn/sit/decisioncenter.aspx';
		window.location.href(url);
	})
	$('#busicenter').click(function(){
		var url='http://d3.cq.tobacco.gov.cn/sit/businesscenter.aspx';
		window.location.href(url);
	})
	$('#reportcenter').click(function(){
		var url='http://d3.cq.tobacco.gov.cn/sit/reportscenter.aspx';
		window.location.href(url);
	})
	$('#konwledgecenter').click(function(){
		var url='http://d3.cq.tobacco.gov.cn/documentlibrary/default.aspx';
		window.location.href(url);
	})
	$('#gkcenter').click(function(){
		var url='http://d3.cq.tobacco.gov.cn/sit/gkcenter.aspx';
		window.location.href(url);
	})
	$('.fixIframe').css('opacity','0');
	$("#husername").text($("#userName").val());
	
	
	var months = new Array("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
	var dateNow = new Date();
    var yearNow = dateNow.getFullYear();
    var monthNow = months[dateNow.getMonth()];
    var dayNow = dateNow.getDate();
    $("#today").text(" "+yearNow+"年"+monthNow+dayNow+"日");
    
    
})