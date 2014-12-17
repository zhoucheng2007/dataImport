var order = new Object();
order.type= "QTY_SOLD"; // 字段排名
order.desc= "DESC"; // 字段排名
//var descB = "DESC"; // 本期字段排名
//var descT = "DESC"; // 同期字段排名
//var descZF = "DESC"; // 同比增幅字段排名
//var descZB = "DESC"; // 占比字段排名
//var descRJ = "DESC"; // 人均条数字段排名
function orderSold() {
	var comId =  $("#brandref").attr("name");
	var tabId = $("#tabId").val();
	if(order.type!="QTY_SOLD"){
		order.type = "QTY_SOLD";
		order.desc= "DESC";
	}else{
		if(order.desc=="DESC"){
			order.desc = "ASC";
		}else{
			order.desc = "DESC";
		}
	}
	// alert(tabId);
	// brandOrder(comId);
	brandOrderKpi1(tabId, comId, 1,order.type,order.desc);
	$("#brandOrder-report").hide();// hide()
	$("#brandOrder-report").fadeIn();
}
function orderPop() {
	var comId = $("#brandref").attr("name");
	var tabId = $("#tabId").val();
	if(order.type!="POP_NUM"){
		order.type = "POP_NUM";
		order.desc= "DESC";
	}else{
		if(order.desc=="DESC"){
			order.desc = "ASC";
		}else{
			order.desc = "DESC";
		}
	}
	// alert(tabId);
	// brandOrder(comId);
	brandOrderKpi1(tabId, comId, 1,order.type,order.desc);
	$("#brandOrder-report").hide();// hide()
	$("#brandOrder-report").fadeIn();
}
function orderSoldT() {
	var comId = $("#brandref").attr("name");
	var tabId = $("#tabId").val();
	if(order.type!="QTY_SOLD_T"){
		order.type = "QTY_SOLD_T";
		order.desc= "DESC";
	}else{
		if(order.desc=="DESC"){
			order.desc = "ASC";
		}else{
			order.desc = "DESC";
		}
	}
	// alert(tabId);
	// brandOrder(comId);
	brandOrderKpi1(tabId, comId, 1,order.type,order.desc);
	$("#brandOrder-report").hide();// hide()
	$("#brandOrder-report").fadeIn();
}
function orderSoldZF() {
	var comId = $("#brandref").attr("name");
	var tabId = $("#tabId").val();
	if(order.type!="RATE_TB"){
		order.type = "RATE_TB";
		order.desc= "DESC";
	}else{
		if(order.desc=="DESC"){
			order.desc = "ASC";
		}else{
			order.desc = "DESC";
		}
	}
	// alert(tabId);
	// brandOrder(comId);
	brandOrderKpi1(tabId, comId, 1,order.type,order.desc);
	$("#brandOrder-report").hide();// hide()
	$("#brandOrder-report").fadeIn();
}
$(document)
		.ready(
				function() {
					var map_name = $("#map_name").val();
					if (map_name == undefined || map_name == "undefined"
							|| map_name == null || map_name == "")
						map_name = "山东省";
					$(".returnProvinceH font").html(
							map_name +"    " + currdate() );
					// 省市地图的切换
					$("#shandongMap h1").click(
							function() {
								var imgName = $(this).attr("imgName");
								var mapName = $(this).attr("mapName");
								var mapID = $(this).attr("id");
								// alert(mapID);
								$("#com_id_hid").val(mapID);
								runsys($('.active', leadModleBox), mapID);
								// totalPlan(mapID);
								$("#shandongMap").hide();
								$("#" + mapName).fadeIn();
								$(".returnProvince").fadeIn();
							
								$(".returnProvince").attr("mapId", mapName);
								$("#map_name").val($('a', $(this)).html());
								map_name = $("#map_name").val();
								$(".returnProvinceH font").html(
										map_name + "     " + currdate());
							});
					$(".returnProvince").click(
							function() {
								var mapId = $(this).attr("mapId");
								$("#" + mapId).hide();
								$("#shandongMap").fadeIn();
								$(".returnProvince").fadeOut();
								
								$("#com_id_hid").val("");
								runsys($('.active', leadModleBox));
								$("#map_name").val("山东省");
								map_name = $("#map_name").val();
								$(".returnProvinceH font").html(
										map_name + "截止" + currdate() );
							});
					// 头部导航
					var leadModleBox = $(".leadModleBox");
					$(".leadModleBox div").each(function() {
						$(this).click(function() {

							$(this).addClass("active");
							$(this).siblings().removeClass("active");
							var mapId = $("#com_id_hid").val();
							// $("#"+mapId).hide();
							// $("#shandongMap").fadeIn();
							// $(".returnProvince").fadeOut();
							runsys($('.active', leadModleBox), mapId);
						});
					});

					$("#brandOrder-report div td").each(function() {
						$(this).click(function() {
							// alert("111");
							$(this).addClass("tabletdactive");
							$(this).siblings().removeClass("active");
						});
					});

					// 默认点击一下头部最左侧的菜单
					$("#totalPlan").click();

					// 运行分析
					function runsys(obj, comId) {
						// alert(comId);
						var activeId = obj.attr("id");
						switch (activeId) {
						case 'totalPlan':
							totalPlan(comId);
							break;
						case 'saleCount':
							saleCount(comId);
							break;
						case 'terminalBuild':
							terminalBuild
							terminalBuild(comId);
							break;
						case 'aaaa':
							brandOrder(comId);
							break;
						}
						// totalPlan();
						// saleCount();
						// terminalBuild();
					}

					$("#totalPlan-head-refresh").live("click", function() {

						var tabId = $(this).attr('name');
						// alert(tabId);
						// totalPlan(tabId);
						changeJ();
						$("#totalPlan-left").hide();// hide()
						$("#totalPlan-right").hide();// hide()
						$("#totalPlan-left").fadeIn();
						$("#totalPlan-right").fadeIn();

					});

					$("#single-head-refresh").live("click", function() {

						var tabId = $(this).attr('name');
						// alert(tabId);
						// totalPlan(tabId);
						changeD();
						$("#single-right").hide();// hide()
						$("#single-right").fadeIn();

					});

					$("#stock-head-refresh").live("click", function() {

						var tabId = $(this).attr('name');
						// alert(tabId);
						// totalPlan(tabId);
						changeK();
						$("#stock-right").hide();// hide()
						$("#stock-right").fadeIn();

					});

					// 运行分析 总计划
					function totalPlan(comId) {
						if (comId == undefined || comId == "comId"
								|| comId == "undefined" || comId == null)
							comId = "";
						$("#content-area").html("");
						// 总计划
						var html = "<div class='yunxingfenxiContent'>"
								+ "<div class=totalPlan-bg>"
								+ "<div id=totalPlan-head>"
								+ "<div id=totalPlan-head-title>销量</div>"
								+ "<div id=totalPlan-head-refresh name='"
								+ comId
								+ "'></div>"
								+ "</div>"
								+ "<div id=totalPlan-right>"
								+ "<div id=totalPlan-right1>"
								+ "<table>"
								+ "<input type='hidden' id='comIdD' name='comIdD' value='"
								+ comId
								+ "'/>"
							
								+ "<tr><td class=td-left width=25%>年度计划：</td><td class=td-right1 width=27% id='yearplan'></td>"
								+ "</tr>"
								+ "<tr> <td class=td-left width=5%>年度销售：</td><td class=td-right1 width=25% id='yearsell'></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td class=td-left width=25%>同比增幅：</td><td class=td-right1 width=25% id='yearrate'></td>"
								+ "</tr>"
							
								+ "</table>"
								+ "</div>"
								+ "<div id=totalPlan-right2>"
								+ "<table>"
								+ "<input type='hidden' id='comIdD' name='comIdD' value='"
								+ comId
								+ "'/>"							
								+ "<tr><td class=td-left width=25%>月度计划：</td><td class=td-right1 width=27% id='monthplan'></td></tr>"								
								+ "<tr><td class=td-left width=5%>月度销售：</td><td class=td-right1 width=25% id='monthsell'></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td class=td-left width=25%>同比增幅：</td><td class=td-right1 width=25% id='monthrate'></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td class=td-left width=25%>昨日访销：</td><td class=td-right1 width=25% id='nowsell'></td>"
								+ "</tr>"
								+ "</table>"
								+ "</div>"
								+ "</div>"
								+ "<div id=totalPlan-left>"
								+ "<div id=totalPlan-left-head>"
								+ "<font color='#1B6C49'>计划完成率"
								+ "</font>"
								+ "</div>"
								+ "<div id=progressDiv>"
								+ "<div class='firstProgress' >"
								+ "<div class='progressTttle' id='firstprogress'></div>"
								+ "</div>"
								+ "<div class='secondProgress'>"
								+ "<div class='progressTttle' id='secondprogress'></div>"
								+ "</div>"
								+ "</div>"
								+ "</div>"
								+ "<div class='mingxi'>"
								+ "<a href='javascript:checkDetail()'>"
								+ "<font color='white'>"
								+ "<div class='checkDetailArrow'></div>"
								+ "<div class='checkDetail'></div>"
								+ "</font>"
								+ "</a>" + "</div>" + "</div>";
						// 单箱值
						html = html
								+ "<div style='margin-top:5px;'></div>"
								+ "<div class=single-bg>"
								+ "<div id=single-head>"
								+ "<div id=single-head-title>单箱值</div>"
								+ "<div id=single-head-refresh  name='"
								+ comId
								+ "'></div>"
								+ "</div>"
								+ "<div id=single-right>"
								+ "<div id=single-right1>"
								+ "<table>"
								+"<tr><td class=td-left width=12%>月度计划:</td></tr>"
								+"<tr><td class=td-right width=30% id='monthboxplan'></td></tr>"
								+ "<tr><td class=td-left  width=12%>月度单箱值:</td>"
								+ "</tr>"
								+ "<tr><td class=td-right width=30% id='monthbox'></td>"
								+ "</tr>"												
							//	+ "<tr><td class=td-left  width=12%>月同比增幅:</td>"
								//+ "</tr>"
								//+ "<tr><td class=td-right  id='monthboxrate'  width=12%></td></tr>"
								
								+ "</table>"	
								+ "</div>"
								+ "<div id=single-right2>"
								+ "<table>"
							+"<tr><td class=td-left width=12%>年度计划:</td></tr>"
								+"<tr><td class=td-right width=30% id='yearboxplan'></td></tr>"
								+ "<tr><td class=td-left width=12%>年度单箱值:</td>"
								+ "</tr>"
							+ "<tr><td class=td-right width=30% id='yearbox'></td>"
								+ "</tr>"		
								//+ "<tr><td class=td-left   width=12%>年同比增幅:</td>"
								//+ "</tr>"
								//+ "<tr><td class=td-right  id='yearboxrate'  width=12%></td></tr>"
								+ "</table>"
								+ "</div>"
								+ "</div>"
								+ "<div id=single-left>"
								+ "<a href='javascript:boxdetail()'><font color='white'><div class='singleDetail' id='boxdetail'></div>"
								+ "<div class='singleDetailBar'></div></font></a>"
								+ "</div>" + "</div>";
						// 库存
						html = html
								+ "<div class=stock-bg>"
								+ "<div id=stock-head>"
								+ "<div id=stock-head-title >库存</div>"
								+ "<div id=stock-head-refresh name='"
								+ comId
								+ "'></div>"
								+ "</div>"
								+ "<div id=stock-right >"
								+ "<table>"
								+ "<tr><td class=td-left width=30%>昨日库存:</td><td class=td-right width=40% id='kucunnum'></td></tr>"
								+ "<tr><td class=td-left width=30%>库存上限:</td><td class=td-right width=40% id='maxnum'></td></tr>"
								+ "<tr><td class=td-left width=30%>存销比:</td><td class=td-right width=40% id='kucunrate'></td></tr>"
								+ "</table>"
								+ "</div>"
								+ "<div id=stock-left >"
								+ "<a href='javascript:kucundetail()'><font color='white'><div class='singleDetail' id='kucundetail'></div><div class='singleDetailBar'></div></font></a>"
								+ "</div>" + "</div>" + "</div>";
						$("#content-area").html(html);
						if (comId == undefined || comId == "comId")
							comId = "";

						if (comId == "") {
							$("#kucundetail").css("color", "white");
						} else {
							$("#kucundetail").css("color", "gray");
						}
						var url = "LeadWorkspaceCmd.cmd?method=analysisPageInitJ&comId="
								+ comId;

						$
								.ajax({
									url : url,
									type : "post",
									timeout : 60000,
									dataType : "json",
									async : true,

									success : function(data, textStatus) {
										if (textStatus == "success") {
											var sb1 = data.firstprogress;
											sb1 = sb1.split("%");
											sb1 = sb1[0];
											sb1 = "[" + sb1 + "]";
											var char5 = new Highcharts.Chart(
													{
														chart : {
															renderTo : 'firstprogress',
															width : '160',
															height : '160',
															backgroundColor : 'rgba(255, 255, 255, 0)',
															plotBorderColor : null,
															plotBackgroundColor : null,
															plotBackgroundImage : null,
															plotBorderWidth : null,
															plotShadow : false,
															borderColor : 'rgba(255, 255, 255, 0)',// 去边框
															shadow : false, // 去阴影
														},
														title : {
															text : ''
														},
														subtitle : {
															text : ''
														},
														legend : {
															enabled : false

														},

														xAxis : {
															categories : [ '年度计划完成率' ],
															labels : {
																style : {
																	color : '#1b6c49',
																	fontWeight : 'normal',
																	fontSize : "12px",
																	marginTop:"5px",
																	fontFamily:"Microsoft YaHei"
																}
															},
														},
														yAxis : {
															min : 0,
															max:120,
															tickPositions: [0,50 ,100,120] ,
															title : {
																text : ''
															}

														},

														plotOptions : {
															column : {
																pointPadding : 0.2,
																borderWidth : 1,
																borderColor: '#FFFFFF',
																dataLabels:{
														            enabled:true //是否显示数据标签
														        }

															}
														
														},
														series : [ {
															name : '百分比',
															type : 'column',
															color : '#46BD28',
															
															data : eval(sb1)
														} ]
													});

											var sb2 = data.secondprogress;
											sb2 = sb2.split("%");
											sb2 = sb2[0];
											sb2 = "[" + sb2 + "]";
											
											var char4 = new Highcharts.Chart(
													{
														chart : {
															renderTo : 'secondprogress',
															width : '160',
															height : '160',
															backgroundColor : 'rgba(255, 255, 255, 0)',
															plotBorderColor : null,
															plotBackgroundColor : null,
															plotBackgroundImage : null,
															plotBorderWidth : null,
															plotShadow : false,
															borderColor : 'rgba(255, 255, 255, 0)',// 去边框
															shadow : false, // 去阴影
														},
														title : {
															text : ''
														},
														subtitle : {
															text : ''
														},
														legend : {
															enabled : false

														},

														xAxis : {
															categories : [ '月度计划完成率' ],
															labels : {
																style : {
																	color : '#1b6c49',
																	fontWeight : 'normal',
																	fontSize : "12px",
																	marginTop:"5px",
																	fontFamily:"Microsoft YaHei"
																}
															},
														},
														yAxis : {
															min : 0,
															max:120,
															tickPositions: [0,50,100,120] ,
															title : {
																text : ''
															}

														},

														plotOptions : {
															column : {
																pointPadding : 0.2,
																borderWidth : 1,
																borderColor: '#FFFFFF',
																dataLabels:{
														            enabled:true //是否显示数据标签
														        }
															}
														},
														series : [ {
															name : '百分比',
															type : 'column',
															color : '#46BD28',
															data : eval(sb2)

														} ]
													});

											$("#yearplan").html(data.yearplan);
											$("#yearsell").html(data.yearsell);
											$("#monthplan")
													.html(data.monthplan);
											$("#monthsell")
													.html(data.monthsell);
											$("#nowsell").html(data.nowsell);
											$("#yearrate").html(data.yearrate);
											$("#monthrate")
													.html(data.monthrate);

										} else {
											jAlert("请求出错！");
										}
									},
									error : function(XMLHttpRequest, textStatus) {
										// 请求出错处理
										alert("请求出错！错误类型=" + textStatus);
									}
								});
						var url2 = "LeadWorkspaceCmd.cmd?method=analysisPageInitD&comId="
								+ comId;
						$.ajax({
							url : url2,
							type : "post",
							timeout : 60000,
							dataType : "json",
							async : true,
							success : function(data, textStatus) {
								if (textStatus == "success") {
								
									$("#yearbox").html(data.yearbox);
									$("#yearboxrate").html(data.yearboxrate);
									$("#monthbox").html(data.monthbox);
									$("#monthboxrate").html(data.monthboxrate);
									$("#daybox").html(data.daybox);
									$("#yearboxplan").html(data.yearboxplan);
									$("#monthboxplan").html(data.monthboxplan);
								} else {
									jAlert("请求出错！");
								}
							},
							error : function(XMLHttpRequest, textStatus) {
								// 请求出错处理
								alert("请求出错！错误类型=" + textStatus);
							}
						});
						var url3 = "LeadWorkspaceCmd.cmd?method=analysisPageInitK&comId="
								+ comId;
						$.ajax({
							url : url3,
							type : "post",
							timeout : 60000,
							dataType : "json",
							async : true,
							success : function(data, textStatus) {
								if (textStatus == "success") {

									$("#kucunnum").html(data.kucunnum);
									$("#maxnum").html(data.maxnum);
									$("#kucunrate").html(data.kucunrate);
								} else {
									jAlert("请求出错！");
								}
							},
							error : function(XMLHttpRequest, textStatus) {
								// 请求出错处理
								alert("请求出错！错误类型=" + textStatus);
							}
						});

					}

					function changeJ() {
						var comId = $("#comIdD").val();
						var url = "LeadWorkspaceCmd.cmd?method=analysisPageInitJ&comId="
								+ comId;
						$
								.ajax({
									url : url,
									type : "post",
									timeout : 60000,
									dataType : "json",
									async : true,
									success : function(data, textStatus) {
										if (textStatus == "success") {
											$("#yearplan").html(data.yearplan);
											$("#yearsell").html(data.yearsell);
											$("#monthplan")
													.html(data.monthplan);
											$("#monthsell")
													.html(data.monthsell);
											$("#nowsell").html(data.nowsell);
											$("#yearrate").html(data.yearrate);
											$("#monthrate")
													.html(data.monthrate);
											var sb1 = data.firstprogress;
											sb1 = sb1.split("%");
											sb1 = sb1[0];
											sb1 = "[" + sb1 + "]";
										//	sb1 = "[" +100 + "]";
											var char5 = new Highcharts.Chart(
													{
														chart : {
															renderTo : 'firstprogress',
															width : '160',
															height : '160',
															backgroundColor : 'rgba(255, 255, 255, 0)',
															plotBorderColor : null,
															plotBackgroundColor : null,
															plotBackgroundImage : null,
															plotBorderWidth : null,
															plotShadow : false,
															borderColor : 'rgba(255, 255, 255, 0)',// 去边框
															shadow : false, // 去阴影
														},
														title : {
															text : ''
														},
														subtitle : {
															text : ''
														},
														legend : {
															enabled : false

														},

														xAxis : {
															categories : [ '年度计划完成率' ],
															labels : {
																style : {
																	color : '#1b6c49',
																	fontWeight : 'normal',
																	fontSize : "12px",
																	marginTop:"5px",
																	fontFamily:"Microsoft YaHei"
																}
															},
														},
														yAxis : {
															min : 0,
															max:120,
															tickPositions: [0,50 ,100,120] ,
															title : {
																text : ''
															}

														},

														plotOptions : {
															column : {
																pointPadding : 0.2,
																borderWidth : 1,
																borderColor: '#FFFFFF',
																dataLabels:{
														            enabled:true //是否显示数据标签
														        }
															}
														},
														series : [ {
															name : '百分比',
															type : 'column',
															color : '#46BD28',
															
															data : eval(sb1)
														} ]
													});
											var sb2 = data.secondprogress;
											sb2 = sb2.split("%");
											sb2 = sb2[0];
											sb2 = "[" + sb2 + "]";
											
											var char4 = new Highcharts.Chart(
													{
														chart : {
															renderTo : 'secondprogress',
															width : '160',
															height : '160',
															backgroundColor : 'rgba(255, 255, 255, 0)',
															plotBorderColor : null,
															plotBackgroundColor : null,
															plotBackgroundImage : null,
															plotBorderWidth : null,
															plotShadow : false,
															borderColor : 'rgba(255, 255, 255, 0)',// 去边框
															shadow : false, // 去阴影
														},
														title : {
															text : ''
														},
														subtitle : {
															text : ''
														},
														legend : {
															enabled : false

														},

														xAxis : {
															categories : [ '月度计划完成率' ],
															labels : {
																style : {
																	color : '#1b6c49',
																	fontWeight : 'normal',
																	fontSize : "12px",
																	marginTop:"5px",
																	fontFamily:"Microsoft YaHei"
																}
															
															},
														},
														yAxis : {
															min : 0,
															max:120,
															tickPositions: [0,50,100,120] ,
															title : {
																text : ''
															}

														},

														plotOptions : {
															column : {
																pointPadding : 0.2,
																borderWidth : 1,
																borderColor: '#FFFFFF',
																dataLabels:{
														            enabled:true //是否显示数据标签
														        }
															}
														},
														series : [ {
															name : '百分比',
															type : 'column',
															color : '#46BD28',
															data : eval(sb2)

														} ]
													});

										} else {
											jAlert("请求出错！");
										}
									},
									error : function(XMLHttpRequest, textStatus) {
										// 请求出错处理
										alert("请求出错！错误类型=" + textStatus);
									}
								});
					}

					function changeD() {
						var comId = $("#comIdD").val();
						var url2 = "LeadWorkspaceCmd.cmd?method=analysisPageInitD&comId="
								+ comId;
						$.ajax({
							url : url2,
							type : "post",
							timeout : 60000,
							dataType : "json",
							async : true,
							success : function(data, textStatus) {
								if (textStatus == "success") {
									$("#yearbox").html(data.yearbox);
									$("#yearboxrate").html(data.yearboxrate);
									$("#monthbox").html(data.monthbox);
									$("#monthboxrate").html(data.monthboxrate);
									$("#daybox").html(data.daybox);
									$("#yearboxplan").html(data.yearboxplan);
									$("#monthboxplan").html(data.monthboxplan);

									// var sb5=data.yearboxrate;
									// sb5 = sb5.split("%");
									// sb5 = sb5[0];
									// sb5="["+sb5+"]";
									// var char1 = new Highcharts.Chart({
									// chart: {
									// renderTo: 'yearboxrate',
									// width:'80',
									// height:'130',
									// backgroundColor: 'rgba(255, 255, 255,
									// 0)',
									// plotBorderColor : null,
									// plotBackgroundColor: null,
									// plotBackgroundImage:null,
									// plotBorderWidth: null,
									// plotShadow: false,
									// borderColor:'rgba(255, 255, 255,
									// 0)',//去边框
									// shadow: false , //去阴影
									// },
									// title: {
									// text: ''
									// },
									// subtitle: {
									// text: ''
									// },
									// legend: {
									// enabled:false
									//		               
									// },
									//
									// xAxis: {
									// categories: [
									// '年同比'
									// ],
									// labels: {
									// style: {
									// color: '#f1f815',
									// fontWeight: 'bold',
									// fontSize:"13px"
									// }
									// },
									// },
									// yAxis: {
									// min: 0,
									// title: {
									// text: ''
									// }
									//		                
									// },
									//
									// plotOptions: {
									// column: {
									// pointPadding: 0.2,
									// borderWidth: 0
									// }
									// },
									// series: [{
									// name: '%',
									// type: 'column',
									// color: '#F0C924',
									// data:eval(sb5)
									//		    
									// }
									// ]
									// });
									// var sb6=data.monthboxrate;
									// sb6 = sb6.split("%");
									// sb6 = sb6[0];
									// sb6="["+sb6+"]";
									// var char0 = new Highcharts.Chart({
									// chart: {
									// renderTo: 'monthboxrate',
									// width:'80',
									// height:'130',
									// backgroundColor: 'rgba(255, 255, 255,
									// 0)',
									// plotBorderColor : null,
									// plotBackgroundColor: null,
									// plotBackgroundImage:null,
									// plotBorderWidth: null,
									// plotShadow: false,
									// borderColor:'rgba(255, 255, 255,
									// 0)',//去边框
									// shadow: false , //去阴影
									// },
									// title: {
									// text: ''
									// },
									// subtitle: {
									// text: ''
									// },
									// legend: {
									// enabled:false
									//		               
									// },
									//
									// xAxis: {
									// categories: [
									// '月同比'
									// ],
									// labels: {
									// style: {
									// color: '#f1f815',
									// fontWeight: 'bold',
									// fontSize:"13px"
									// }
									// },
									// },
									// yAxis: {
									// min: 0,
									// title: {
									// text: ''
									// }
									//		                
									// },
									//
									// plotOptions: {
									// column: {
									// pointPadding: 0.2,
									// borderWidth: 0
									// }
									// },
									// series: [{
									// name: '%',
									// type: 'column',
									// color: '#F0C924',
									// data: eval(sb6)
									//		    
									// }
									// ]
									// });
								} else {
									jAlert("请求出错！");
								}
							},
							error : function(XMLHttpRequest, textStatus) {
								// 请求出错处理
								alert("请求出错！错误类型=" + textStatus);
							}
						});
					}
					function changeK() {
						var comId = $("#comIdD").val();
						var url3 = "LeadWorkspaceCmd.cmd?method=analysisPageInitK&comId="
								+ comId;
						$.ajax({
							url : url3,
							type : "post",
							timeout : 60000,
							dataType : "json",
							async : true,
							success : function(data, textStatus) {
								if (textStatus == "success") {

									$("#kucunnum").html(data.kucunnum);
									$("#maxnum").html(data.maxnum);
									$("#kucunrate").html(data.kucunrate);
								} else {
									jAlert("请求出错！");
								}
							},
							error : function(XMLHttpRequest, textStatus) {
								// 请求出错处理
								alert("请求出错！错误类型=" + textStatus);
							}
						});

					}

					$("#saleref").live("click", function() {

						var tabId = $(this).attr('name');
						// alert(tabId);
						saleCount(tabId);
						$("#xiaoshoutj").hide();// hide()
						$("#xiaoshoutongjiBody_bottom_right").hide();// hide()
						$("#xiaoshoutongjiBody_bottom_left").hide();// hide()
						$("#xiaoshoutongjiBody_bottom_left").fadeIn();
						$("#xiaoshoutongjiBody_bottom_right").fadeIn();
						$("#xiaoshoutj").fadeIn();

					});

					// 销售统计
					function saleCount(comId) {

						if (comId == undefined || comId == "comId"
								|| comId == "undefined" || comId == null)
							comId = "";
						// alert(comId);
						$("#content-area").html("");
						// 总计划
						var html = "<div  class='xiaoshoutongjiContent'>"
								+ "<div class='xiaoshoutongjiHead'>"
								+ "<div class='xiaoshouTitle'>销售统计</div>"
								+ "<div class='reflesh'style='float:right;margin-right:20px;margin-top:-28px;' id='saleref' name='"
								+ comId
								+ "'></div>"
								+ "</div>"
								+ "<div class='xiaoshoutongjiBody'>"
								+ "<div class='xiaoshoutongjiBody_top'>"
								+ "<div class='xiaoshouBody_top_left'style='width: 50%; height: 230px; float: left;'>"
								+ "<div id='xiaoshouChart'style='margin-left:-20px;width:100%;height:100%;'></div>"
								+ "</div>"
								+ "<div class='xiaoshouBody_top_right'style='float:right;width:50%;height:230px;'>"
								+ "<div  id ='xiaoshoutj' style='height: 195px; color: #ffffff;padding-left:15px;padding-right:15px;'>"
								+ "<table style='width:100%;'>"
								+ "<thead>"
								+ "<tr>"
								+ "<th >价类</th>"
								+ "<th>销量(万箱)</th>"
								+ "<th>同比增幅</th>"
								+ "<th>占比</th>"
								+ "</tr>"
								+ "</thead>"
								+ "<tbody>"
								+ "<tr>"
								+ "<td>一类</td>"
								+ "<td id='qty01'></td>"
								+ "<td id='rate01'></td>"
								+ "<td id='zhan01'></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>二类</td>"
								+ "<td id='qty02'></td>"
								+ "<td id='rate02'></td>"
								+ "<td id='zhan02'></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td >三类</td>"
								+ "<td id='qty03'></td>"
								+ "<td id='rate03'></td>"
								+ "<td id='zhan03'></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>四类</td>"
								+ "<td id='qty04'></td>"
								+ "<td id='rate04'></td>"
								+ "<td id='zhan04'></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>五类</td>"
								+ "<td id='qty05'></td>"
								+ "<td id='rate05'></td>"
								+ "<td id='zhan05'></td>"
								+ "</tr>"
								/*
								 * +"<tr>" +"<td>无价类</td>" +"<td id='qty06'></td>" +"<td id='rate06'></td>" +"</tr>"
								 */
								+ "<tr>"
								+ "<td>合计</td>"
								+ "<td id='qtyhe'></td>"
								+ "<td id='ratehe'></td>"
								+ "<input type='hidden' id='comIdD' name='comIdD' value='"
								+ comId
								+ "'/>"
								+ "</tr>"
								+ "<tr style='height:20px'>"
								+ "<td></td>"
								+ "<td></td>"
								+ "<td></td>"
								+ "</tr>"
								+ "</tbody>"
								+ "</table>"
								+ "</div>"
								+ "<div class='mingxi1'>"
								+ "<a href=javascript:catograypricedetail()><font color='white'><div class='checkDetailArrow' style='margin-left:10px;float: left;'></div>"
								+ "<div class='checkDetail' style=''></div></font></a>"
								+ "</div>"
								+ "</div>"
								+ "</div>"
								+ "<div class='xiaoshoutongjiBody_bottom'>"
								+ "<div id='xiaoshoutongjiBody_bottom_left' class='xiaoshoutongjiBody_bottom_left' style=' margin-left: 10px;'>"
								+ "<table>"
								+ "<tr><td style='text-align:left'><span>一二类烟销量：</span><span id='QTY_SOLD_A' style='margin-left:10px;'></span><span id='salerateA' style='margin-left:20px;'></span><td></tr>"
								+ "<tr><td  style='text-align:left'><span>泰山品牌销量：</span><span id='QTY_SOLD_B' style='margin-left:10px;'></span><span id='salerateB' style='margin-left:20px;'></span><td></tr>"
								+ "<tr><td  style='text-align:left'><span>重点品牌销量：</span><span id='QTY_SOLD_C' style='margin-left:10px;'></span><span id='salerateC' style='margin-left:20px;'></span><td></tr>"
								+ "</table>"
								+ "</div>"
								+ "<div id='xiaoshoutongjiBody_bottom_right' class='xiaoshoutongjiBody_bottom_right'>"
								+ "<table>"
								+ "<tr><td  style='text-align:left'><span>鲁产烟销量：</span><span id='QTY_SOLD_D' style='margin-left:10px;'></span><span id='salerateD' style='margin-left:20px;'></span><td></tr>"
								+ "<tr><td  style='text-align:left'><span>泰山百元以上烟销量：</span><span id='QTY_SOLD_E' style='margin-left:10px;'></span><span id='salerateE' style='margin-left:20px;'></span><td></tr>"
								+ "<tr><td  style='text-align:left'><span>低焦油卷烟销量：</span><span id='QTY_SOLD_F' style='margin-left:10px;'></span><span id='salerateF' style='margin-left:20px;'></span><td></tr>"
								+ "</table>"
								+ "</div>"
								+ "</div>"
								+ "</div>"

								+ " <div class='xiaoshoutongjiFoot'>"
								+ "<a href='javascript:salecountbottomdetail()'><font color='white'><div class='checkDetailArrow'></div>	"
								+ "<div class='checkDetail'></div></font></a>"
								+ "</div>"

								+ "</div>";
						$("#content-area").html(html);
						if (comId == undefined || comId == "comId")
							comId = "";
						var url = "SaleCountCmd.cmd?method=saleCountPageInit&comId="
								+ comId;
						var sb;
						$.ajax({
							url : url,
							type : "post",
							timeout : 60000,
							dataType : "json",
							async : false,
							success : function(data, textStatus) {
								if (textStatus == "success") {
									$("#qtyhe").html(data.saleid0);
									$("#ratehe").html(data.rateid0);

									$("#qty01").html(data.saleid1);
									$("#rate01").html(data.rateid1);
									$("#zhan01").html(data.zhanid1);

									$("#qty02").html(data.saleid2);
									$("#rate02").html(data.rateid2);
									$("#zhan02").html(data.zhanid2);

									$("#qty03").html(data.saleid3);
									$("#rate03").html(data.rateid3);
									$("#zhan03").html(data.zhanid3);
									
									$("#qty04").html(data.saleid4);
									$("#rate04").html(data.rateid4);
									$("#zhan04").html(data.zhanid4);

									$("#qty05").html(data.saleid5);
									$("#rate05").html(data.rateid5);
									$("#zhan05").html(data.zhanid5);

									/*
									 * $("#qty06").html(data.saleid6);
									 * $("#rate06").html(data.rateid6);
									 */
									sb = String(data.sb).toString();

									$("#QTY_SOLD_A").html(data.QTY_SOLD_A);
									$("#salerateA")
											.html("占比:" + data.salerateA);

									$("#QTY_SOLD_B").html(data.QTY_SOLD_B);
									$("#salerateB")
											.html("占比:" + data.salerateB);

									$("#QTY_SOLD_C").html(data.QTY_SOLD_C);
									$("#salerateC")
											.html("占比:" + data.salerateC);

									$("#QTY_SOLD_D").html(data.QTY_SOLD_D);
									$("#salerateD")
											.html("占比:" + data.salerateD);

									$("#QTY_SOLD_E").html(data.QTY_SOLD_E);
									$("#salerateE")
											.html("占比:" + data.salerateE);

									$("#QTY_SOLD_F").html(data.QTY_SOLD_F);
									$("#salerateF")
											.html("占比:" + data.salerateF);

								} else {
									jAlert("请求出错！");
								}
							},
							error : function(XMLHttpRequest, textStatus) {
								// 请求出错处理
								alert("请求出错！错误类型=" + textStatus);
							}
						});

						xiaotongH(sb);
					}
					
					$("#brandref").live("click", function() {

						var comId = $(this).attr('name');
						var tabId = $("#tabId").val();
						// alert(tabId);
						// brandOrder(comId);
						brandOrderKpi1(tabId, comId, 1,order.type,order.desc);
						$("#brandOrder-report").hide();// hide()
						$("#brandOrder-report").fadeIn();

					});
					// 排名统计
					function brandOrder(comId) {

						if (comId == undefined || comId == "comId"
								|| comId == "undefined" || comId == null)
							comId = "";
						if (comId == "" || comId == undefined
								|| comId == "undefined" || comId == null) {
							$("#content-area").html("");
							// alert(comId);
							var html = "<div id='brandOrdlj' class='brandOrderContent'>"
									+ "<div id=brandOrder_head class='brandOrder_head'>"
									+ "<div class='reflesh' style='float:right;margin-right:20px;margin-top:8px;' id='brandref' name='"
									+ comId
									+ "'></div>"
									+ "<div id=brandOrderTitle class='brandOrderTitle'>"

									+ "<div id=brandOrderTitle-tab-title-area class='brandOrderTitle-tab-title-area'>"
									+ "<input type='hidden' id='comIdD' name='comIdD' value='"
									+ comId
									+ "'/>"
									+ "<div id=gyxl class='brandOrderTitle-tab-text' >工业销量排名</div>"
									+ "<div id=ppxl class='brandOrderTitle-tab-text'>品牌销量排名</div>"
									+ "<div id=gdsxl class='brandOrderTitle-tab-text'>市公司销量排名</div>"
									+ "<div id=gdsdxz class='brandOrderTitle-tab-text' style='width:130'>市公司单箱值排名</div>"
									+ "</div>"
									+ "</div>"

									+ "</div>"
									+ "<div class='brandOrder_body'>"
									+ "<div id=brandOrder-report></div>"
									+ "<div id=brandOrder-slider>"
									// +"<div id='aaa'><a
									// href='javascript:changeR1()'><font
									// color='white'>1</font></a>&nbsp;&nbsp;"
									// +"<a href='javascript:changeR2()'><font
									// color='white'>2</font></a>&nbsp;&nbsp;"
									// +"<a href='javascript:changeR3()'><font
									// color='white'>3</font></a></div>"
									+ "</div>"
									+ "<div id=brandOrder-kpi></div>"
									+ "</div>"
									+ " <div class='brandOrder_foot'  id='brandOrder_foot'>"
							//		+ "<a href='javascript:brandOrderDtail()'><font color='white'><div class='checkDetailArrow'></div>	"
									+ "<a href='javascript:brandOrderDtail()'><font color='white'><div class=''></div>	"
									+ "<div class='checkDetail' id='brandOrderDtaildiv'></div></font></a>"
									+ "</div>" + "</div>";
							$("#content-area").html(html);
							// $("#brandOrderDtaildiv").css("color","white");
						} else {
							$("#content-area").html("");
							// alert(comId);
							var html = "<div class='brandOrderContent'>"
									+ "<div id=brandOrder_head class='brandOrder_head'>"
									+ "<div id=brandOrderTitle class='brandOrderTitle' style='width:580px ;margin-top: 30px;'>"
									+ "<div id=brandOrderTitle-tab-title-area class='brandOrderTitle-tab-title-area'>"
									+ "<input type='hidden' id='comIdD' name='comIdD' value='"
									+ comId
									+ "'/>"
									+ "<div id=gyxl class='brandOrderTitle-tab-text' >工业销量排名</div>"
									+ "<div id=ppxl class='brandOrderTitle-tab-text'>品牌销量排名</div>"
									+ "<div id=gdsxl class='brandOrderTitle-tab-text'>营销部销量排名</div>"
									+ "<div id=gdsdxz class='brandOrderTitle-tab-text'>营销部单箱值排名</div>"
									+ "</div>"
									+ "</div>"
									+ "<div class='reflesh' style='float:right;margin-right:20px;margin-top:-58px;' id='brandref' name='"
									+ comId
									+ "'></div>"
									+ "</div>"
									+ "<div class='brandOrder_body'>"
									+ "<div id=brandOrder-report></div>"
									+ "<div id=brandOrder-slider>"
									// +"<div id='aaa'><a
									// href='javascript:changeR1()'><font
									// color='white'>1</font></a>&nbsp;&nbsp;"
									// +"<a href='javascript:changeR2()'><font
									// color='white'>2</font></a>&nbsp;&nbsp;"
									// +"<a href='javascript:changeR3()'><font
									// color='white'>3</font></a></div>"
									+ "</div>"
									+ "<div id=brandOrder-kpi></div>"
									+ "</div>"
									+ " <div class='brandOrder_foot' id='brandOrder_foot'>"
									+ "<a href='javascript:brandOrderDtail()'><font color='white'><div class='checkDetailArrow'></div>	"
									+ "<div class='checkDetail' id='brandOrderDtaildiv'></div></font></a>"
									+ "</div>" + "</div>";
							$("#content-area").html(html);
						}

						$("#gyxl").click();

					}

					$("#terminalref").live("click", function() {

						var tabId = $(this).attr('name');
						// alert(tabId);
						terminalBuild(tabId);
						$("#aChart").hide();// hide()
						$("#bChart").hide();// hide()
						$("#cChart").hide();// hide()
						$("#dChart").hide();// hide()
						$("#aChart").fadeIn("show");
						$("#bChart").fadeIn("show");
						$("#cChart").fadeIn("show");
						$("#dChart").fadeIn("show");

					});

					// 终端建设
					function terminalBuild(comId) {

						if (comId == undefined || comId == "comId"
								|| comId == "undefined" || comId == null)
							comId = "";
						$("#content-area").html("");
						// alert(comId);
						var html = "<div id='zhongduanlj' class='zhongduanjiansheContent'>"
								+ "<div id='zhongduan_head' class='zhongduan_head'>"
								+ "<div class='reflesh' style='float:right;margin-right:20px;margin-top:8px;' id='terminalref' name='"
								+ comId
								+ "'></div>"
								// +"<div class='zhongduanTitle'>终端建设</div>"
								+ "<input type='hidden' id='comIdD' name='comIdD' value='"
								+ comId
								+ "'/>"
								+ "</div>"
								+ "<div class='zhongduan_body'>"
								+ "<div class='chartBox' style='float:left;margin-left:2%;width:47%'>"
								+ " <div id='aChart' style='width:100%;height:100%;' ></div>"
								+ " </div>"
								+ " <div class='chartBox' style='float:right;margin-right:2%;width:47%'>"
								+ "<div id='bChart' style='width:100%;height:100%;' ></div>"
								+ "</div>"
								+ "	<div class='chartBox' style='float:left;margin-left:2%;width:47%;margin-top:10px;'>"
								+ " <div id='cChart' style='width:100%;height:100%;'></div>"
								+ "</div>"
								+ "<div class='chartBox' style='float:right;margin-right:2%;width:47%;margin-top:10px;'>"
								+ " <div id='dChart' style='width:100%;height:100%;' ></div>"
								+ "</div>"
								+ "</div>"
								+ " <div class='zhongduan_foot'>"
								+ "<a href='javascript:terminalBuildDetail()'><font color='white'><div class='checkDetailArrow'></div>	"
								+ "<div class='checkDetail'></div></font></a>"
								+ "</div>" + "</div>";
						$("#content-area").html(html);

						if (comId == undefined || comId == "comId")
							comId = "";
						var urlw = "TerminalBuildCmd.cmd?method=terminalBuildPageInit&comId="
								+ comId;
						var sb1;
						var sb2;
						var sb3;
						var sb4;
						$.ajax({
							url : urlw,
							type : "post",
							timeout : 60000,
							dataType : "json",
							async : false,
							success : function(data, textStatus) {
								if (textStatus == "success") {

									sb1 = String(data.sb1).toString();
									sb2 = String(data.sb2).toString();
									sb3 = String(data.sb3).toString();
									sb4 = String(data.sb4).toString();

								} else {
									jAlert("请求出错！");
								}
							},
							error : function(XMLHttpRequest, textStatus) {
								// 请求出错处理
								console.log("textStatus=" + textStatus);
								alert("请求出错！错误类型=" + textStatus);
							}
						});

						zhongjianH(sb1, sb2, sb3, sb4);

					}

				});
// 终端建设highchart
function zhongjianH(sb1, sb2, sb3, sb4) {

	var char1, char2, char3, char4;
	char1 = new Highcharts.Chart({
		chart : {
			renderTo : 'aChart',
			backgroundColor : 'none'
		},
		title : {
			style : {
				color : '#ffffff',
				fontSize : '16px',
				fontFamily : 'Microsoft Yahei'
			},
			text : '经营业态',

		},
		legend : {
			enabled : true,
			layout : "vertical",
			align : "right"
		},
		colors : [ '#42BB25', '#50BC93', '#910000', '#8bbc21', '#77a1e5',
				'#c42525', '#a6c96a', '#0d233a', '#1aadce', '#492970', ],
		tooltip : {
			// pointFormat: '<b>{point.percentage}%</b>'+this.y,

			formatter : function() {
				var s = '' + this.point.name + ':' + '<br>'
						+ Highcharts.numberFormat(this.percentage, 2) + '%<br>'
						+ this.y;
				return s;
			}
		},
		plotOptions : {
			pie : {
				size : '90%',
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}
		},
		series : [ {
			type : 'pie',
			name : 'Browser share',
			data : []
		} ]
	});

	char2 = new Highcharts.Chart({
		chart : {
			renderTo : 'bChart',
			backgroundColor : 'none'
		},
		title : {
			style : {
				color : '#FFFFFF',
				fontSize : '16px',
				fontFamily : 'Microsoft Yahei'
			},
			text : '地理位置细分',

		},
		legend : {
			layout : "vertical",
			align : "right",
		},
		colors : [ '#42BB25', '#50BC93', '#910000', '#8bbc21', '#77a1e5',
				'#c42525', '#a6c96a', '#0d233a', '#1aadce', '#492970', ],
		tooltip : {
			formatter : function() {
				var s = '' + this.point.name + ':' + '<br>'
						+ Highcharts.numberFormat(this.percentage, 2) + '%<br>'
						+ this.y;
				return s;
			}
		},
		plotOptions : {
			pie : {
				size : '90%',
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}
		},
		series : [ {
			type : 'pie',
			name : 'Browser share',
			data : []
		} ]
	});

	char3 = new Highcharts.Chart({
		chart : {
			renderTo : 'cChart',
			backgroundColor : 'none'
		},
		title : {
			style : {
				color : '#FFFFFF',
				fontSize : '16px',
				fontFamily : 'Microsoft Yahei'
			},
			text : '订货方式',
		},
		legend : {
			enabled : true,
			layout : "vertical",
			align : "right"
		},
		colors : [ '#42BB25', '#50BC93', '#910000', '#8bbc21', '#77a1e5',
				'#c42525', '#a6c96a', '#0d233a', '#1aadce', '#492970', ],
		tooltip : {
			formatter : function() {
				var s = '' + this.point.name + ':' + '<br>'
						+ Highcharts.numberFormat(this.percentage, 2) + '%<br>'
						+ this.y;
				return s;
			}
		},
		plotOptions : {
			pie : {
				size : '90%',
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}
		},
		series : [ {
			type : 'pie',
			name : 'Browser share',
			data : []
		} ]
	});

	char4 = new Highcharts.Chart({
		chart : {
			renderTo : 'dChart',
			backgroundColor : 'none'
		},
		title : {
			style : {
				color : '#FFFFFF',
				fontSize : '16px',
				fontFamily : 'Microsoft Yahei'
			},
			text : '结算方式',

		},
		colors : [ '#42BB25', '#50BC93', '#910000', '#8bbc21', '#77a1e5',
				'#c42525', '#a6c96a', '#0d233a', '#1aadce', '#492970', ],
		legend : {
			enabled : true,
			layout : "vertical",
			align : "right"
		},
		tooltip : {
			formatter : function() {
				var s = '' + this.point.name + ':' + '<br>'
						+ Highcharts.numberFormat(this.percentage, 2) + '%<br>'
						+ this.y;
				return s;
			}
		},
		plotOptions : {
			pie : {
				size : '90%',
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}
		},
		series : [ {
			type : 'pie',
			name : 'Browser share',
			data : []
		} ]
	});

	char1.series[0].setData(eval(sb1));
	char2.series[0].setData(eval(sb2));
	char3.series[0].setData(eval(sb3));
	char4.series[0].setData(eval(sb4));

}

// 销售统计
function xiaotongH(sb) {
	// alert(sb);
	var xiaoshouChart = new Highcharts.Chart({
		chart : {
			renderTo : 'xiaoshouChart',
			backgroundColor : 'none'
		},
		title : {
			 text:'占比',
             style:{
             	fontFamily:'Microsoft YaHei',
                 color:'#FFFFFF',
                 fontSize:'16px'
             } 
		},

		tooltip : {
			pointFormat : '<b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>:{point.percentage:.1f}%'
				}
			}
		},

		legend : {
			enabled : true,
			layout : "vertical",
			align : "right"
		},
		tooltip : {
			pointFormat : '<b>{point.percentage}%</b>',
			percentageDecimals : 1
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}
		},
		series : [ {
			type : 'pie',
			name : 'Browser share',
			data : []
		} ]
	});

	xiaoshouChart.series[0].setData(eval(sb));

	// $("#xiaoshouChart").v6report(options);
	// xiaoshouChart.series.type="pie";
	// xiaoshouChart.series.name="Browser share";
	// xiaoshouChart.series.data=eval("["+sb+"]");
	// alert("111");
}

function currdate() {
	var myDate = new Date();
	//myDate.setTime(myDate.getTime() - 24 * 60 * 60 * 1000);
	myDate.setTime(myDate.getTime());
	var year = myDate.getFullYear();
	var month = myDate.getMonth()+1;
	var day = myDate.getDay()-1;
	var currdate = String(year) + "年" + String(month) + "月" + String(day) + "日";
	return currdate;
}

// //品牌排名统计点击tab
// function clickTab(tabId){
// alert(tabId);
// // 页面刷新加载tab页效果
// $(tabId).addClass("active");
// }
// $("#gdsxl").on("click",function(){

// alert("dadas");
// });
$(".brandOrderTitle-tab-text").live("click", function() {
	var j = 1;
	$(".brandOrderTitle-tab-text").removeClass("active");
	$(this).addClass("active");
	var tabId = $(this).attr('id');
	var comId = $("#comIdD").val();
	// alert(comId);
	order.type = "QTY_SOLD";
	order.desc = "DESC";
	if ("gyxl" == tabId) {
		brandOrderKpi1(tabId, comId, j,order.type,order.desc);
		$("#brandOrderDtaildiv").css("color", "white");
		document.getElementById('brandOrder_foot').style.display='block';
	} else if ("ppxl" == tabId) {
		brandOrderKpi1(tabId, comId, j,order.type,order.desc);
		$("#brandOrderDtaildiv").css("color", "white");
		document.getElementById('brandOrder_foot').style.display='block';
	} else if ("gdsxl" == tabId) {
		brandOrderKpi1(tabId, comId, j,order.type,order.desc);
		$("#brandOrderDtaildiv").css("color", "gray");
		document.getElementById('brandOrder_foot').style.display='none';
	} else if ("gdsdxz" == tabId) {
		brandOrderKpi1(tabId, comId, j,order.type,order.desc);
		$("#brandOrderDtaildiv").css("color", "gray");
		document.getElementById('brandOrder_foot').style.display='none';
	}
});

function brandOrderKpi1(tabId, comId, k,order,desc) {
	$("#brandOrder-report").html("");
	$("#brandOrder-slider").html("");// 翻页
	var slider = "";
	var report1 = "";
	var q = 6 * (k - 1);
	// alert(q);
	var p = 6 * k - 1;
	// alert(p);
	var a = "万箱";

	if (comId == undefined || comId == "comId" || comId == "undefined"
			|| comId == null || comId == "") {
		// a="万箱";
		comId = "";
	} else {
		// a="箱";
	}

	var url = "";

	if ("gyxl" == tabId) {
		url = "BrandOrderCmd.cmd?method=brandOrdergyxlPageInit&comId=" + comId
				+ "&tabId=" + tabId+"&order="+order;

	} else if ("ppxl" == tabId) {
		url = "BrandOrderCmd.cmd?method=brandOrdergyxlPageInit&comId=" + comId
				+ "&tabId=" + tabId+"&order="+order;
	} else if ("gdsxl" == tabId) {
		url = "BrandOrderCmd.cmd?method=brandOrdergdsxlPageInit&comId=" + comId
				+ "&tabId=" + tabId+"&order="+order;
	} else if ("gdsdxz" == tabId) {
		url = "BrandOrderCmd.cmd?method=brandOrdergdsxlPageInit&comId=" + comId
				+ "&tabId=" + tabId+"&order="+order;
	}
	url=url+"&desc="+desc;
	var strval; // 组织串
	var arr1 = new Array();
	var arr2 = new Array();
	var j = 1;
	$
			.ajax({
				url : url,
				type : "post",
				timeout : 60000,
				dataType : "json",
				async : false,
				success : function(data, textStatus) {
					if (textStatus == "success") {
						// alert("success");
						strval = String(data.strval).toString();// 工业，序号，销量等|工业，序号，销量等|...
						arr1 = strval.split("|");// 工业，序号，销量等
						// alert("strval:::"+strval);
						// alert(arr1.length);
						for ( var i = 0; i < arr1.length; i++) {
							arr2 = arr1[i].split(",");
							// arr2[0]:工业
							// arr2[1]:序号
							// arr2[2]:销量
							// arr2[3]:同期
							// arr2[4]:同比
							// arr2[5]:占比条
							// arr2[6]:占比值
							if (i >= q && i <= p) {// 显示的页数限制

								if (i % 6 < 5 && i < arr1.length - 1) {
									if (i % 6 == 0) {// 每页第一条记录
										report1 = report1
												+ "<input type='hidden' id='tabId' name='tabId' value='"
												+ tabId
												+ "'/>"
												+ "<input type='hidden' id='comIdD' name='comIdD' value='"
												+ comId
												+ "'/>"
												+ "<div"
												+ "style='background-color:#4fbc93;height:196px;padding-top:10px;padding-bottom:10px;color:#FFFFFF;padding-left:15px;padding-right:15px;'>"
												+ "<table style='width:100%;'>"
												// +"divId"+j
												+ "<thead>" + "<tr>";

										if ("gyxl" == tabId) {

											report1 = report1
													+ "<th style=\"width:30%;\">公司名称</th>"
													+ "<th>序号</th>" + "<th><a href=javascript:orderSold()><font color='white'>销量("
													+ a + ")</font></a></th>" + "<th><a href=javascript:orderSoldT()><font color='white'>同期("
													+ a + ")</font></a></th>"
													+ "<th><a href=javascript:orderSoldZF()><font color='white'>同比增幅(%)</font></a></th>"
													+ "<th><a href=javascript:orderSold()><font color='white'><font color='white'>占比</font></a></th>"
													// +"<th></th>"
													+ "</tr>" + "</thead>"
													+ "<tbody>";

										} else if ("ppxl" == tabId) {
											report1 = report1 + "<th>品牌</th>"
													+ "<th>序号</th>" + "<th><a href=javascript:orderSold()><font color='white'>销量("
													+ a + ")</font></a></th>" + "<th><a href=javascript:orderSoldT()><font color='white'>同期("
													+ a + ")</font></a></th>"
													+ "<th><a href=javascript:orderSoldZF()><font color='white'>同比增幅(%)</font></a></th>"
													+ "<th><a href=javascript:orderSold()><font color='white'><font color='white'>占比</font></a></th>"
													// +"<th></th>"
													+ "</tr>" + "</thead>"
													+ "<tbody>";
										} else if ("gdsxl" == tabId) {
											report1 = report1 + "<th>公司</th>"
													+ "<th>序号</th>" + "<th><a href=javascript:orderSold()><font color='white'>销量("
													+ a + ")</font></a></th>" +
															"<th><a href=javascript:orderPop()>" +
															"<font color='white'>人均条数</font>" +
															"</a></th>"
													+ "<th><a href=javascript:orderSoldT()><font color='white'><font color='white'>同期("
													+ a + ")</font></a></th>"
													+ "<th><a href=javascript:orderSoldZF()><font color='white'><font color='white'>同比增幅(%)</font></a></th>"
													+ "<th><a href=javascript:orderSold()><font color='white'><font color='white'>占比</font></a></th>"
													// +"<th></th>"
													+ "</tr>" + "</thead>"
													+ "<tbody>";
										} else if ("gdsdxz" == tabId) {
											report1 = report1 + "<th>公司</th>"
													+ "<th>序号</th>"
													+ "<th><a href=javascript:orderSold()><font color='white'>单箱值(元/箱)</font></a></th>"
													+ "<th><a href=javascript:orderSoldT()><font color='white'>同期(元/箱)</font></a></th>"
													+ "<th><a href=javascript:orderSoldZF()><font color='white'>同比增幅(%)</font></a></th>"
													+ "</tr>" + "</thead>"
													+ "<tbody>";
										}

										report1 = report1
												+ "<input type='hidden' id='NowIDY' name='NowIDY' value='"
												+ arr2[6]
												+ "'/>"
												+ "<input type='hidden' id='NowNameY' name='NowNameY' value='"
												+ arr2[0] + "'/>";
									}
									if (i % 6 == 0) {
										report1 = report1
												+ "<tr style='background-color:#0c6;'>";
									} else {
										report1 = report1 + "<tr>";
									}
									if ("gyxl" == tabId) {
										report1 = report1
												+ "<td style='text-align:center;'>&nbsp;";

									} else {
										report1 = report1 + "<td>";
									}
									report1 = report1;
									if(tabId !="gdsxl" )	{
										report1 = report1
										+ "<a href=javascript:brandOrder1('"
										+ tabId + "','" + comId + "','"
										+ arr2[6] + "','" + arr2[0]
										+ "')><font color='white'>"
										+ arr2[0] + "</font></a></td>"
										+ "<td>" + arr2[1] + "</td>"
										+ "<td>" + arr2[2] + "</td>"
										+ "<td>" + arr2[3] + "</td>"
										+ "<td>" + arr2[4] + "</td>"
										if(tabId !="gdsdxz"){
											report1 = report1
											+ "<td>" + arr2[5] + "</td>";
										}
									}	
									if("gdsxl"==tabId){
										report1 = report1
										+ "<a href=javascript:brandOrder1('"
										+ tabId + "','" + comId + "','"
										+ arr2[7] + "','" + arr2[0]
										+ "')><font color='white'>"
										+ arr2[0] + "</font></a></td>"
										+ "<td>" + arr2[1] + "</td>"
										+ "<td>" + arr2[2] + "</td>"
										+ "<td>" + arr2[3] + "</td>"
										+ "<td>" + arr2[4] + "</td>"
										+ "<td>" + arr2[5] + "</td>"
										+ "<td>" + arr2[6] + "</td>";
									} 
									report1 = report1 + "</tr>";
								}

								if (arr1.length % 6 == 0) {// 当记录数正好为6的倍数
									// alert("6");
									/*
									 * if(i==0) { slider=slider +"<div>"; }
									 */
									if (i % 6 == 5) { // 每页最后一条记录
										report1 = report1 + "<tr>";
										if ("gyxl" == tabId) {
											report1 = report1
													+ "<td style='text-align:center;'>&nbsp;";

										} else {
											report1 = report1 + "<td>";
										}
										report1 = report1;
										if(tabId !="gdsxl" )	{
											report1 = report1
											+ "<a href=javascript:brandOrder1('"
											+ tabId + "','" + comId + "','"
											+ arr2[6] + "','" + arr2[0]
											+ "')><font color='white'>"
											+ arr2[0] + "</font></a></td>"
											+ "<td>" + arr2[1] + "</td>"
											+ "<td>" + arr2[2] + "</td>"
											+ "<td>" + arr2[3] + "</td>"
											+ "<td>" + arr2[4] + "</td>";
											if(tabId !="gdsdxz"){
												report1 = report1
												+ "<td>" + arr2[5] + "</td>";
											}
										}	
										if("gdsxl"==tabId){
											report1 = report1
											+ "<a href=javascript:brandOrder1('"
											+ tabId + "','" + comId + "','"
											+ arr2[7] + "','" + arr2[0]
											+ "')><font color='white'>"
											+ arr2[0] + "</font></a></td>"
											+ "<td>" + arr2[1] + "</td>"
											+ "<td>" + arr2[2] + "</td>"
											+ "<td>" + arr2[3] + "</td>"
											+ "<td>" + arr2[4] + "</td>"
											+ "<td>" + arr2[5] + "</td>"
											+ "<td>" + arr2[6] + "</td>";
										} 
										report1 = report1 + "</tr>";
										report1 = report1 + "</tbody>"
												+ "</table>" + "</div>";

									}


								} else {// 当最后一页记录数不足6条

									if (i % 6 == 5) { // 每页最后一条记录
										report1 = report1 + "<tr>";
										if ("gyxl" == tabId) {
											report1 = report1
													+ "<td style='text-align:center;'>&nbsp;";

										} else {
											report1 = report1 + "<td>";
										}
										report1 = report1;
										if(tabId !="gdsxl" )	{
											report1 = report1
											+ "<a href=javascript:brandOrder1('"
											+ tabId + "','" + comId + "','"
											+ arr2[6] + "','" + arr2[0]
											+ "')><font color='white'>"
											+ arr2[0] + "</font></a></td>"
											+ "<td>" + arr2[1] + "</td>"
											+ "<td>" + arr2[2] + "</td>"
											+ "<td>" + arr2[3] + "</td>"
											+ "<td>" + arr2[4] + "</td>";
											if(tabId !="gdsdxz"){
												report1 = report1
												+ "<td>" + arr2[5] + "</td>";
											}
										}	
										if("gdsxl"==tabId){
											report1 = report1
											+ "<a href=javascript:brandOrder1('"
											+ tabId + "','" + comId + "','"
											+ arr2[7] + "','" + arr2[0]
											+ "')><font color='white'>"
											+ arr2[0] + "</font></a></td>"
											+ "<td>" + arr2[1] + "</td>"
											+ "<td>" + arr2[2] + "</td>"
											+ "<td>" + arr2[3] + "</td>"
											+ "<td>" + arr2[4] + "</td>"
											+ "<td>" + arr2[5] + "</td>"
											+ "<td>" + arr2[6] + "</td>";
										} 
										report1 = report1 + "</tr>";
										report1 = report1 + "</tbody>"
												+ "</table>" + "</div>";
						
									}
									if (i == arr1.length - 1 && i % 6 == 0) {
										report1 = report1
												+ "<input type='hidden' id='tabId' name='tabId' value='"
												+ tabId
												+ "'/>"
												+ "<input type='hidden' id='comIdD' name='comIdD' value='"
												+ comId
												+ "'/>"
												+ "<div"
												+ "style='background-color:#4fbc93;height:196px;padding-top:10px;padding-bottom:10px;color:#FFFFFF;padding-left:15px;padding-right:15px;'>"
												+ "<table style='width:100%;'>"
												// +"divId"+j
												+ "<thead>" + "<tr>";

										if ("gyxl" == tabId) {

											report1 = report1
													+ "<th style=\"width:30%;\">公司名称</th>"
													+ "<th>序号</th>" + "<th><a href=javascript:orderSold()><font color='white'>销量("
													+ a + ")</font></a></th>" + "<th><a href=javascript:orderSoldT()><font color='white'>同期("
													+ a + ")</font></a></th>"
													+ "<th><a href=javascript:orderSoldZF()><font color='white'>同比增幅(%)</font></a></th>"
													+ "<th><a href=javascript:orderSold()><font color='white'><font color='white'>占比</font></a></th>"
													// +"<th></th>"
													+ "</tr>" + "</thead>"
													+ "<tbody>";

										} else if ("ppxl" == tabId) {
											report1 = report1 + "<th>品牌</th>"
													+ "<th>序号</th>" + "<th><a href=javascript:orderSold()><font color='white'>销量("
													+ a + ")</font></a></th>" + "<th><a href=javascript:orderSoldT()><font color='white'>同期("
													+ a + ")</font></a></th>"
													+ "<th><a href=javascript:orderSoldZF()><font color='white'>同比增幅(%)</font></a></th>"
													+ "<th><a href=javascript:orderSold()><font color='white'><font color='white'>占比</font></a></th>"
													// +"<th></th>"
													+ "</tr>" + "</thead>"
													+ "<tbody>";
										} else if ("gdsxl" == tabId) {
											report1 = report1 + "<th>公司</th>"
													+ "<th>序号</th>" + "<th><a href=javascript:orderSold()><font color='white'>销量("
													+ a + ")</font></a></th>" +
															"<th><a href=javascript:orderPop()>" +
															"<font color='white'>人均条数</font>" +
															"</a></th>"
													+ "<th><a href=javascript:orderSoldT()><font color='white'><font color='white'>同期("
													+ a + ")</font></a></th>"
													+ "<th><a href=javascript:orderSoldZF()><font color='white'><font color='white'>同比增幅(%)</font></a></th>"
													+ "<th><a href=javascript:orderSold()><font color='white'><font color='white'>占比</font></a></th>"
													// +"<th></th>"
													+ "</tr>" + "</thead>"
													+ "<tbody>";
										} else if ("gdsdxz" == tabId) {
											report1 = report1 + "<th>公司</th>"
													+ "<th>序号</th>"
													+ "<th><a href=javascript:orderSold()><font color='white'>单箱值(元/箱)</font></a></th>"
													+ "<th><a href=javascript:orderSoldT()><font color='white'>同期(元/箱)</font></a></th>"
													+ "<th><a href=javascript:orderSoldZF()><font color='white'>同比增幅(%)</font></a></th>"
													+ "</tr>" + "</thead>"
													+ "<tbody>";
										}

										report1 = report1
												+ "<input type='hidden' id='NowIDY' name='NowIDY' value='"
												+ arr2[6]
												+ "'/>"
												+ "<input type='hidden' id='NowNameY' name='NowNameY' value='"
												+ arr2[0] + "'/>";
										report1 = report1 + "<tr>";
										if ("gyxl" == tabId) {
											report1 = report1
													+ "<td style='text-align:center;width:30%'>&nbsp;";

										} else {
											report1 = report1 + "<td >";
										}
										report1 = report1;
										if(tabId !="gdsxl" )	{
											report1 = report1
											+ "<a href=javascript:brandOrder1('"
											+ tabId + "','" + comId + "','"
											+ arr2[6] + "','" + arr2[0]
											+ "')><font color='white'>"
											+ arr2[0] + "</font></a></td>"
											+ "<td>" + arr2[1] + "</td>"
											+ "<td>" + arr2[2] + "</td>"
											+ "<td>" + arr2[3] + "</td>"
											+ "<td>" + arr2[4] + "</td>";
											if(tabId !="gdsdxz"){
												report1 = report1
												+ "<td>" + arr2[5] + "</td>";
											}
										}	
										if("gdsxl"==tabId){
											report1 = report1
											+ "<a href=javascript:brandOrder1('"
											+ tabId + "','" + comId + "','"
											+ arr2[7] + "','" + arr2[0]
											+ "')><font color='white'>"
											+ arr2[0] + "</font></a></td>"
											+ "<td>" + arr2[1] + "</td>"
											+ "<td>" + arr2[2] + "</td>"
											+ "<td>" + arr2[3] + "</td>"
											+ "<td>" + arr2[4] + "</td>"
											+ "<td>" + arr2[5] + "</td>"
											+ "<td>" + arr2[6] + "</td>";
										} 
										report1 = report1 + "</tr>";
										report1 = report1 + "</tbody>"
												+ "</table>" + "</div>";
									}
									if (i == arr1.length - 1 && i % 6 != 0) {

										report1 = report1 + "<tr>";
										if ("gyxl" == tabId) {
											report1 = report1
													+ "<td style='text-align:center;width:30%'>&nbsp;";

										} else {
											report1 = report1
													+ "<td width=30%>";
										}
										report1 = report1;
										if(tabId !="gdsxl" )	{
											report1 = report1
											+ "<a href=javascript:brandOrder1('"
											+ tabId + "','" + comId + "','"
											+ arr2[6] + "','" + arr2[0]
											+ "')><font color='white'>"
											+ arr2[0] + "</font></a></td>"
											+ "<td>" + arr2[1] + "</td>"
											+ "<td>" + arr2[2] + "</td>"
											+ "<td>" + arr2[3] + "</td>"
											+ "<td>" + arr2[4] + "</td>";
											if(tabId !="gdsdxz"){
												report1 = report1
												+ "<td>" + arr2[5] + "</td>";
											}
										}	
										if("gdsxl"==tabId){
											report1 = report1
											+ "<a href=javascript:brandOrder1('"
											+ tabId + "','" + comId + "','"
											+ arr2[7] + "','" + arr2[0]
											+ "')><font color='white'>"
											+ arr2[0] + "</font></a></td>"
											+ "<td>" + arr2[1] + "</td>"
											+ "<td>" + arr2[2] + "</td>"
											+ "<td>" + arr2[3] + "</td>"
											+ "<td>" + arr2[4] + "</td>"
											+ "<td>" + arr2[5] + "</td>"
											+ "<td>" + arr2[6] + "</td>";
										} 
										report1 = report1 + "</tr>";
										report1 = report1 + "</tbody>"
												+ "</table>" + "</div>";
										/*
										 * slider = slider +"<a
										 * href=javascript:changeR('"+j+"','"+tabId+"','"+comId+"')><font
										 * color='white'>"+j+"</font></a>&nbsp;&nbsp" +"</div>";
										 */
									}
								}
							}
							if (arr1.length % 6 == 0) {// 当记录数正好为6的倍数
								// alert("6");
								if (i == 0) {
									slider = slider + "<div>";
								}
								if (i % 6 == 5) { // 每页最后一条记录
									/*
									 * report1 = report1 +"</tbody>" +"</table>" +"</div>";
									 */
									slider = slider
											+ "<a href=javascript:changeR('"
											+ j + "','" + tabId + "','" + comId
											+ "')><font color='white'>" + j
											+ "</font></a>&nbsp;&nbsp";
									j++;
								}
								if (i == arr1.length - 1) {
									slider = slider + "</div>";
								}

							} else {// 当最后一页记录数不足6条
								// alert("!6");
								if (i == 0) {
									slider = slider + "<div>";
								}
								if (i % 6 == 5) { // 每页最后一条记录
									/*
									 * report1 = report1 +"</tbody>" +"</table>" +"</div>";
									 */
									slider = slider
											+ "<a href=javascript:changeR('"
											+ j + "','" + tabId + "','" + comId
											+ "')><font color='white'>" + j
											+ "</font></a>&nbsp;&nbsp";
									j++;
								}
								if (i == arr1.length - 1) {
									/*
									 * report1 = report1 +"</tbody>" +"</table>" +"</div>";
									 */
									slider = slider
											+ "<a href=javascript:changeR('"
											+ j + "','" + tabId + "','" + comId
											+ "')><font color='white'>" + j
											+ "</font></a>&nbsp;&nbsp"
											+ "</div>";
								}
							}

						}
					} else {
						jAlert("请求出错！");
					}
				},
				error : function(XMLHttpRequest, textStatus) {
					// 请求出错处理
					console.log("textStatus=" + textStatus);
					alert("请求出错！错误类型=" + textStatus);
				}
			});

	$("#brandOrder-report").html(report1);
	$("#brandOrder-slider").html(slider);// 翻页
	var NowIDY = $("#NowIDY").val();
	var NowNameY = $("#NowNameY").val();
	brandOrder1(tabId, comId, NowIDY, NowNameY);
}

function changeR(j, tabId, comId) {
	// alert(j);
	// alert(tabId);
	// alert(comId);
	brandOrderKpi1(tabId, comId, j,order.type,order.desc);

}

// 销售统计
function brandOrder1(tabId, comId, changeId, changeName) {

	var url = "BrandOrderCmd.cmd?method=brandOrderTuPageInit&tabId=" + tabId
			+ "&comId=" + comId + "&changeId=" + changeId + "&changName="
			+ encodeURI(encodeURI(changeName));
	var sb = "";
	$.ajax({
		url : url,
		type : "post",
		timeout : 60000,
		dataType : "json",
		async : false,
		success : function(data, textStatus) {
			if (textStatus == "success") {
				sb = data.sb;
			
			} else {
				jAlert("请求出错！");
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			// 请求出错处理
			alert("请求出错！错误类型=" + textStatus);
		}
	});

	var arr1 = new Array();
	var arr2 = new Array();
	arr1 = sb.split("|");
	var heng = arr1[0];// 横坐标
	var name = arr1[1];// name
	var dataD = arr1[2];

	var danwei = "";
	if (tabId == "gdsdxz") {
		danwei = "元/箱";
	} else {
		danwei = "万箱";
	}

	var xiaoshouChart;
	xiaoshouChart = new Highcharts.Chart({
		chart : {
			renderTo : 'brandOrder-kpi',
			type : 'line',
			marginRight : 130,
			marginBottom : 25,

		},
		title : {
			text : ''
		},
		xAxis : {
			categories : eval(heng)

		},
		yAxis : {
			title : {
				text : danwei
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '</b><br/>' + this.x + ': '
						+ this.y + danwei;
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'top',
			x : -10,
			y : 100,
			borderWidth : 0
		},
		series : [ {
			name : '',
			data : []
		} ]
	});
	xiaoshouChart.series[0].remove();
	xiaoshouChart.addSeries({
		name : name,
		data : eval(dataD)
	});

}



$("#brandOrder-report tbody tr").live("click", function() {
	$(this).add("cd").css("background-color", "#0c6");
	$(this).siblings().css("background-color", "");

});