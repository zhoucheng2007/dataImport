$(document).ready(function(){
	var map_name=$("#map_name").val();
	if(map_name==undefined||map_name=="undefined"||map_name==null||map_name=="")
		map_name = "山东省";
	$(".returnProvinceH font").html(map_name+"截止"+currdate()+"专卖数据");
	//省市地图的切换
	$("#shandongMap h1").click(function(){
		 var imgName=$(this).attr("imgName");
		 var mapName=$(this).attr("mapName");
		 var mapID=$(this).attr("id");
		 //alert(mapID);
		 $("#com_id_hid").val(mapID);
		 //runsys($('.active',leadModleBox),mapID);
		 //alert($('.active',leadModleBox).attr("id"));
		 if(($('.active',leadModleBox).attr("id")=='homePage')){
			 homePage(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='market')){
				marketGraph(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='caseStatus')){
			 caseStatusGraph(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='identify')){
			 identifyGraph(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='staffBuild')){
			 staffGraph(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='navicert')){
			 navicertGraph(mapID);
		 }
		 $("#shandongMap").hide();
		 $("#"+mapName).fadeIn();	
		 $(".returnProvince").fadeIn();
		 $(".returnProvince").attr("mapId",mapName);
		 $("#map_name").val($('a',$(this)).html());
		 map_name=$("#map_name").val();
		 $(".returnProvinceH font").html(map_name+"截止"+currdate()+"营销数据");

});	
	
	$(".everyMap h1").click(function(){
		 var imgName=$(this).attr("imgName");
		 var mapName=$(this).attr("mapName");
		 var mapID=$(this).attr("id");
		 //alert(mapID);
		 $("#com_id_hid").val(mapID);
		 //runsys($('.active',leadModleBox),mapID);
		 //alert($('.active',leadModleBox).attr("id"));
		 if(($('.active',leadModleBox).attr("id")=='homePage')){
			 homePage(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='market')){
				marketGraph(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='caseStatus')){
			 caseStatusGraph(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='identify')){
			 identifyGraph(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='staffBuild')){
			 staffGraph(mapID);
		 }else  if(($('.active',leadModleBox).attr("id")=='navicert')){
			 navicertGraph(mapID);
		 }
		 

});
	
	$(".returnProvince").click(function(){
		  var mapId = $(this).attr("mapId");
		  $("#"+mapId).hide();
		  $("#shandongMap").fadeIn();
		  $(".returnProvince").fadeOut();
		  $("#com_id_hid").val("");
		  //runsys($('.active',leadModleBox));
		 if(($('.active',leadModleBox).attr("id")=='homePage')){
				 homePage(mapId);
		 }else  if(($('.active',leadModleBox).attr("id")=='market')){
				marketGraph(mapId);
		 } if(($('.active',leadModleBox).attr("id")=='caseStatus')){
			 caseStatusGraph(mapId);
		 }else  if(($('.active',leadModleBox).attr("id")=='identify')){
			 identifyGraph(mapId);
		 }else if(($('.active',leadModleBox).attr("id")=='staffBuild')){
			 staffGraph(mapId);
		 }else  if(($('.active',leadModleBox).attr("id")=='navicert')){
			 navicertGraph(mapId);
		 }
		  $("#map_name").val("山东省");
		  map_name=$("#map_name").val();
		  $(".returnProvinceH font").html(map_name+"截止"+currdate()+"专卖数据");
		});
	//头部导航
	var leadModleBox=$(".leadModleBox");
	$(".leadModleBox div").each(function(){
		  $(this).click(function(){
			//   alert("head!");
			   $(this).addClass("active");
			   $(this).siblings().removeClass("active");
			   var mapId = $("#com_id_hid").val();
			   //$("#"+mapId).hide();
			   //$("#shandongMap").fadeIn();
			   //$(".returnProvince").fadeOut();
			   //alert($('.active',leadModleBox));
			   runsys($('.active',leadModleBox),mapId);
			 });
	});
	
	
	$("#caseStatus-report div td").each(function(){
		  $(this).click(function(){
			   $(this).addClass("tabletdactive");
			   $(this).siblings().removeClass("active");
			 });
	});
	//默认点击一下头部最左侧的菜单
	$("#homePage").click();
});


	

	
//运行分析
function runsys(obj,comId){
	var activeId=obj.attr("id");
	activeId=$.trim(activeId);
//		alert(activeId);
		//console.log("activeIdactiveId="+activeId);
	switch (activeId) {  
					case 'caseStatus':
						caseStatus(comId);
					    break;
					case 'identify':
						identify(comId);
							break;
					case 'homePage':
						homePage(comId);
							break;	
					case 'market':
						market(comId);
							break;	
					case 'staffBuild':
						staffBuild(comId);
							break;	
					case 'navicert':
						navicert(comId);
							break;		
	}
}	






//首页
function   homePage(comId){	
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
	comId="";
$("#content-area1").html("");
var html="<div id='homelj' class='zhongduanjiansheContent'>"
+"<div id='home_head' class='zhongduan_head'>"	 
+"<input type='hidden' id='comIdD' name='comIdD' value='"+comId+"'/>"
+"<div class='reflesh' style='float:right;margin-right:20px;margin-top:8px;' id='homePageR'  onClick='homePageRefresh("+$("#com_id_hid").val()+")' name='"+comId+"'></div>"			        
+"</div>"			 
+"<div class='zhongduan_body'>"			 
+"<div class='chartBox' style='float:left;margin-left:2%;width:47%'>"			        
+" <div id='aChart' style='width:100%;height:100%;' ></div>"			          
+" </div>"				   
+" <div class='chartBox' style='float:right;margin-right:2%;width:47%;height: 360px;'>"				   
+"<div id='bChart' style='width:100%;height:100%;' ></div>"			            
+"</div>"					
+"	<div class='chartBox' style='float:left;margin-left:2%;width:47%;margin-top:10px;'>"				
+" <div id='cChart' style='width:100%;height:100%;'></div>"			           
+"</div>"					
/*+"<div class='chartBox' style='float:right;margin-right:2%;width:47%;margin-top:10px;'>"					
+" <div id='dChart' style='width:100%;height:100%;' ></div>"				     
+"</div>"*/					 
+"</div>"			  		  
+"</div>"	;
$("#content-area1").html(html);
if(comId==undefined||comId=="comId")
comId="";
var url = "/portal/AuthenService?USERID=chensong&APP=REG&RESOURCE=http://10.60.4.83/v3/zmmonitorfrontpage.cmd?method=graphics|regieId="+comId;

var netCase0;
var netCase1 = new Array();
var netCase2 = new Array();
var nopunish0 ="";
var nopunish1 =new Array();
var nopunish2 =new Array();
var realItem0 =""
var realItem1 =new Array();
var realItem2 =new Array();

$.ajax({
url:url,
type:"post",
timeout:60000,
dataType:"json",
async: false,
success: function(data, textStatus){
	if(textStatus=="success"){
		netCase0 = (String)(data.netCase).toString();
		netCase1 = netCase0.split("##");
		netCase2 = netCase1[1].split("|");
        //alert("netCase1[0]-->"+netCase1[0]+"<--netCase2[0]-->"+netCase2[0]+"<--netCase2[1]-->"+netCase2[1]);
		nopunish0 = (String)(data.nopunish).toString();
		nopunish1 = nopunish0.split("##");
		nopunish2 = nopunish1[1].split("|");
		realItem0 = (String)(data.realItem).toString();
		realItem1 = realItem0.split("##");
		realItem2 = realItem1[1].split("|");
	}else{
		jAlert("请求出错！");
	}
},
error: function(XMLHttpRequest,textStatus){
	//请求出错处理
	alert("请求出错！错误类型="+textStatus);
}
});

homeData(netCase1,netCase2,nopunish1,nopunish2,realItem1,realItem2);
}
function homePageRefresh(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
	/*if(($("#tab_first").attr("class"))==("tab_first active")){
		marketTable(comId);
	}else{*/
	homePage(comId);
	//}	
}
//首页数据
function homeData(netCase1,netCase2,nopunish1,nopunish2,realItem1,realItem2){
var char1,char2,char3,char4;	
char1 = new Highcharts.Chart(
		{
            chart: {
                renderTo: 'aChart',
                backgroundColor:'none'
            },
            title: {
                //text: '国家局标准网络案件数',
            	text: eval(netCase2[0]),
                style:{
                	fontFamily:'Microsoft YaHei',
                    color:'#1b6c49',
                    fontSize:'16px'
                } 
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: 
                    //'本期累计142个',
                    //'同期累计156个'
                    eval(netCase1[0])
                ,
                labels: {
					style: {
						color : '#1b6c49',
						fontSize : '12px'
					}
				}
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
                
            },
            tooltip: {
            	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0;color:black"><b>{point.y}起</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
        		},
            legend: {
			    enabled:false
               
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    color:'#FF8200',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                        style: {
                            textShadow: '0 0 3px black, 0 0 3px black'
                        },
                        formatter: function() {                   
                            return this.y + '起';               
                            
                            }
                    }
                },
                series: {
                    borderWidth: 1,
                    borderColor: '#FFFFFF'
                }
            },
            series: [{
                name: eval(netCase2[0]), 
                type: 'column',
                data: eval(netCase2[1])
    
            }]
        });
	
		 char2 = new Highcharts.Chart({
	            chart: {
	                renderTo: 'bChart',
	                backgroundColor:'none'
	            },
	            title: {
	               // text: '真品卷烟数量',
	                text:eval(realItem2[0]),
	                style:{
	                	fontFamily:'Microsoft YaHei',
	                    color:'#1b6c49',
	                    fontSize:'16px'
	                } 
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                categories: eval(realItem1[0]),
	                labels: {
						style: {
							color : '#1b6c49',
							fontSize : '12px'
						}
					}
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                }
	                
	            },
	            tooltip: {
	            	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:#42BA21;padding:0">{series.name}: </td>' +
	                    '<td style="padding:0;color:black"><b>{point.y}万支</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	        		},
	            legend: {
				    enabled:false
	               
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0,
	                    color:'#FFC710',
	                    dataLabels: {
	                        enabled: true,
	                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
	                        style: {
	                            textShadow: '0 0 3px black, 0 0 3px black'
	                        },
	                        formatter: function() {                   
	                            return this.y + '万支';               
	                            
	                            }
	                    }
	                },
	                series: {
	                    borderWidth: 1,
	                    borderColor: '#FFFFFF'
	                }
	            },
	            series: [{
	                name: eval(realItem2[0]), 
	                type: 'column',
	                data: eval(realItem2[1])
	    
	            }]
	        });		
		 char3 = new Highcharts.Chart({
	            chart: {
	                renderTo: 'cChart',
	                backgroundColor:'none'
	            },
	            title: {
	                text: eval(nopunish2[0]),
	                style:{
	                	fontFamily:'Microsoft YaHei',
	                    color:'#1b6c49',
	                    fontSize:'16px'
	                } 
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                categories:  eval(nopunish1[0]),
	                labels: {
						style: {
							color : '#1b6c49',
							fontSize : '12px'
						}
					}
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                }
	                
	            },
	            /*tooltip: {
	    			formatter: function() {
	    			return this.series.name + ' ' + this.y;
	    			}
	    			},*/
	            tooltip: {
	            	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                    '<td style="padding:0;color:black"><b>{point.y}起</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	        		},
	            legend: {
				    enabled:false
	               
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0,
	                    color:'#42BA21',
	                    dataLabels: {
	                        enabled: true,
	                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
	                        style: {
	                            textShadow: '0 0 3px black, 0 0 3px black'
	                        },
	                        formatter: function() {                   
	                            return this.y + '起';               
	                            
	                            }
	                    }
	                },
	                series: {
	                    borderWidth: 1,
	                    borderColor: '#FFFFFF'
	                }
	            },
	            series: [{
	                name: eval(nopunish2[0]), 
	                type: 'column',
	                data: eval(nopunish2[1])
	    
	            }]
	        });	
	
		 
//		 char1.series[0].setData(eval(sb1));
//		 char2.series[0].setData(eval(sb2));
/*		 char3.addSeries({name: '许可证占人口比例', data: eval("[\'许可证占人口比例\',"+sb3+"]")});
		 char3.addSeries({name: '非许可证占人口比例', data: eval("[\'非许可证占人口比例\',"+sb4+"]")});
		 char4.addSeries({name: '违规零售户比例', data: eval("[\'违规零售户比例\',"+sb5+"]")});
		 char4.addSeries({name: '合法零售户比例', data: eval("[\'合法零售户比例\',"+sb6+"]")});*/
	
	}

//市场基础
function   market(comId){	
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
	comId="";
$("#content-area1").html("");
// alert(comId);
var html="<div id='homelj' class='zhongduanjiansheContent'>"
+"<div id='home_head' class='zhongduan_head'>"	
+"<div class='tabs'>"
+"<div class='tab_first'  id='tab_first' onClick='marketTable("+$("#com_id_hid").val()+")'>报表信息</div>"
/*+"<div class='tab_second' id='tab_second' onClick='marketGraph("+$("#com_id_hid").val()+")'>图形展示</div></div>"*/
+"<input type='hidden' id='comIdD' name='comIdD' value='"+comId+"'/>"
+"</div>"	
+"<div class='reflesh' id='marketR' onClick='marketRefresh("+$("#com_id_hid").val()+")' style='float:right;margin-right:-100px;margin-top:8px;' id='terminalref' name='"+comId+"'></div>"			        
+"</div>"		 
+"<div class='zhongduan_body' id='zhongduan_body'>"						 			  		  
+"</div>";	
$("#content-area1").html(html);

marketGraph(comId);
}
function marketGraph(comId){
    comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
	var custRatio0;
	var custRatio1 = new Array();
	var custRatio2 = new Array();

	/*$(".tab_second").addClass("active");
	$(".tab_first").removeClass("active");*/
	$("#zhongduan_body").html("");
	var html = "<div class='chartBox' style='height:174px;margin-left:5px;float:left;width:640px;margin-top:10px'>"			        
	           +" <div id='aChart' style='width:100%;height:100%;' ></div>"			          
	           +" </div>"				   					
	           +"<div class='chartBox' style='height:174px;margin-left:5px;float:left;width:640px;margin-top:10px;'>"					
	           +" <div id='bChart' style='width:100%;height:100%;' ></div>"				     
	           +"</div>";					 
	$("#zhongduan_body").html(html);
	if(comId==undefined||comId=="comId")
		comId="";
		var url = "/portal/AuthenService?USERID=chensong&APP=REG&RESOURCE=http://10.60.4.83/v3/zmmonitormarketgraphics.cmd?method=graphics|regieId="+comId;
		$.ajax({
		url:url,
		type:"post",
		timeout:60000,
		dataType:"json",
		async: false,
		success: function(data, textStatus){
			if(textStatus=="success"){
				custRatio0 = (data.custRatio).toString();
				custRatio1 = custRatio0.split("##");
				custRatio2 = custRatio1[1].split("|");
				cust0 = (data.cust).toString();
				cust1 = cust0.split("##");
				cust2 = cust1[1].split("|");
                //alert("custRatio0--"+custRatio0+"--custRatio1--"+custRatio1+"--custRatio2--"+custRatio2);
				
			}else{
				jAlert("请求出错！");
			}
		},
		error: function(XMLHttpRequest,textStatus){
			//请求出错处理
			alert("请求出错！错误类型="+textStatus);
		}
		});
		marketData(custRatio1,custRatio2,cust1,cust2);
}
function marketTable(comId){
	comId = $("#com_id_hid").val();
	/*$(".tab_first").addClass("active");
	$(".tab_second").removeClass("active");*/
    	/*var htmls = "<iframe style='height:400px;width:600px;' class='marketIframe' src='/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorMarketRpt.jsp'></iframe>";
    	 $(".zhongduan_body").html(""); 
    	 $(".zhongduan_body").html(htmls);*/ 
	showPopWin('市场基础','/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorMarketRpt.jsp', 1100,460,true,true);//弹出帮助框
    var inParam = '{"param1":"参数1","param2":"参数2"}';
    setInParam(inParam);
}
function marketRefresh(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
	/*if(($("#tab_first").attr("class"))==("tab_first active")){
		marketTable(comId);
	}else{*/
		marketGraph(comId);
	//}
	
}
//市场基础数据
function marketData(custRatio1,custRatio2,cust1,cust2){
	
var char1,char2,char3,char4;

char1 = new Highcharts.Chart({
    chart: {
        renderTo: 'aChart',
        backgroundColor:'none'
    },
    title: {
        text: eval(custRatio2[0]),
        style:{
        	fontFamily:'Microsoft YaHei',
            color:'#1b6c49',
            fontSize:'16px'
        } 
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: eval(custRatio1[0])
    },
    yAxis: {
        min: 0,
        title: {
            text: ''
        }
    },
    /*tooltip: {
		formatter: function() {
		return this.series.name + ' ' + this.y;
		}
		},*/
    tooltip: {
    	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:#42BA21;padding:0">{series.name}: </td>' +
            '<td style="padding:0;color:black"><b>{point.y}</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
		},
    legend: {
	    enabled:false
       
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0,
            color:'#FFC710',
            dataLabels: {
                    enabled: true,
                    rotation: -45,
                    y: -15,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                    style: {
                        textShadow: '0 0 3px black, 0 0 3px black'
                    },
                    formatter: function() {                   
                        return this.y ;               
                        
                        }
                }	
        },
        series: {
            borderWidth: 1,
            borderColor: '#FFFFFF'
        }
    },
    series: [{
        name: eval(custRatio2[0]), 
        type: 'column',
        data: eval(custRatio2[1])

    }]
});	

char2 = new Highcharts.Chart(
		{
            chart: {
                renderTo: 'bChart',
                backgroundColor:'none'	
            },
            title: {
                text: eval(cust2[0]),
                style:{
                	fontFamily:'Microsoft YaHei',
                    color:'#1b6c49',
                    fontSize:'16px'
                } 
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: eval(cust1[0])
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
                
            },
            tooltip: {
            	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0;color:black"><b>{point.y}户</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
        		},
            legend: {
			    enabled:false
               
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    color:'#00CC33',
                    dataLabels: {
                            enabled: true,
                            color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                            style: {
                                textShadow: '0 0 3px black, 0 0 3px black'
                            },
                            formatter: function() {                   
                                return this.y+'户' ;               
                                
                                }
                        }	
                },
                series: {
                    borderWidth: 1,
                    borderColor: '#FFFFFF'
                }
            },
            series: [{
                name: eval(cust2[0]), 
                type: 'column',
                data: eval(cust2[1])
    
            }]
        });

		
		 
	
		 
//		 char1.series[0].setData(eval(sb1));
//		 char2.series[0].setData(eval(sb2));
/*		 char3.addSeries({name: '许可证占人口比例', data: eval("[\'许可证占人口比例\',"+sb3+"]")});
		 char3.addSeries({name: '非许可证占人口比例', data: eval("[\'非许可证占人口比例\',"+sb4+"]")});
		 char4.addSeries({name: '违规零售户比例', data: eval("[\'违规零售户比例\',"+sb5+"]")});
		 char4.addSeries({name: '合法零售户比例', data: eval("[\'合法零售户比例\',"+sb6+"]")});*/
	
	}

//案件管理

function   caseStatus(comId){	
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
	comId="";
$("#content-area1").html("");
// alert(comId);
var html="<div id='homelj' class='zhongduanjiansheContent'>"
	
+"<div id='first' class='first'>"

+"<div id='home_head' class='zhongduan_head'>"	
+"<div class='tabs'>"
+"<div class='tab_first'  id='tab_first' onClick='caseTable("+comId+")'>报表信息</div>"
/*+"<div class='tab_second' id='tab_second' onClick='caseStatusGraph("+comId+")'>图形展示</div></div>"*/
+"<input type='hidden' id='comIdD' name='comIdD' value='"+comId+"'/>"
+"</div>"	
+"<div class='reflesh' onClick='caseRefresh("+comId+")' id='caseStatusR' style='float:right;margin-right:-100px;margin-top:8px;' id='terminalref' name='"+comId+"'></div>"			        
+"</div>"			 
+"<div class='zhongduan_body' id='zhongduan_body' style='margin-top:20px;'>"	
					 
		  		  
+"</div>";

$("#content-area1").html(html);
caseStatusGraph(comId);

}
function caseStatusGraph(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
	//var url = "http://10.60.1.1:9080/v3/zmmonitorcaseinfographics.cmd?method=caseCharaGraphics&regieId="+comId+"&tk=<Result><UserID>SUPERADMIN</UserID><Certificate>pwd</Certificate><expirationTime>1392773247593</expirationTime><signatureValue>NTM1NTUwNDU1MjQxNDQ0ZDQ5NGUzYTMxMzMzOTMyMzczNzMzMzIzNDM3MzUzOTMz</signatureValue></Result>";
	//var url = "/v3/zmmonitorcaseinfographics.cmd?method=graphics&regieId="+comId+"&tk=<Result><UserID>MAHW</UserID><Certificate>pwd</Certificate><expirationTime>1393464955253</expirationTime><signatureValue>NGQ0MTQ4NTczYTMxMzMzOTMzMzQzNjM0MzkzNTM1MzIzNTMz</signatureValue></Result>";

	var url ="/portal/AuthenService?USERID=chensong&APP=REG&RESOURCE=http://10.60.4.83/v3/zmmonitorcaseinfographics.cmd?method=graphics|regieId="+comId;
	/*$(".tab_second").addClass("active");
	$(".tab_first").removeClass("active");*/
	$("#zhongduan_body").html("");
	var caseInfo0;
	var caseInfo1 = new Array();
	var caseInfo2 = new Array();
	var itemQuantity0;
	var itemQuantity1 = new Array();
	var itemQuantity2 = new Array();
	var itemValue0;
	var itemValue1 = new Array();
	var itemValue2 = new Array();
	var caseChara0;
	var caseChara1 = new Array();
	var caseChara2 = new Array();
	var dealParty0;
	var dealParty1 = new Array();
	var dealParty2 = new Array();
	var condemn0;
	var condemn1 = new Array();
	var condemn2 = new Array();
	var html = 	"	<div  class='chartBox' style='float:left;margin-left:2%;width:47%'>"
	+"<div id='left-tab0'  class='left_bt bt0' onclick='zuoyi0()' style='margin-top:50px;width:20px;height:104px;position:absolute;z-index:1;'></div>"


	+" <div id='main0' class='main0' style='position:absolute;width:305px;height:174px;z-index:0;overflow:hidden'>"
	+" <div id='imgs0' class='imgs0' style='position:absolute;width:905px;height:174px;z-index:0'>"
	+" <ul id='ul_10' class='ul_10' style='position:absolute;width:305px;height:174px;z-index:0'>"
	+" <li><div id='aChart' style='position:absolute;width:305px;height:174px;z-index:0'></div></li>"
	+" <li><div id='gChart' style='position:absolute;width:305px;height:174px;z-index:0'></div></li>"
	+" <li><div id='hChart' style='position:absolute;width:305px;height:174px;z-index:0'></div></li>"
	+"</ul>"
	+"</div>"
	+"</div>"

	+"<div id='right-tab0' class='right_bt bt0' onclick='youyi0()' style='margin-top:50px;margin-left:285px;width:20px;height:104px;position:absolute;z-index:1;'></div>"
	+"</div>"	
	
	+" <div class='chartBox' style='float:right;margin-right:2%;width:47%'>"				   
	+"<div id='bChart' style='width:100%;height:100%;' ></div>"			            
	+"</div>"					
	+"	<div  class='chartBox' style='float:left;margin-left:2%;width:47%;margin-top:10px;'>"
	+"<div id='left-tab'  class='left_bt bt' onclick='zuoyi()' style='margin-top:50px;width:20px;height:104px;position:absolute;z-index:1;'></div>"


	+" <div id='main' class='main' style='position:absolute;width:305px;height:174px;z-index:0;overflow:hidden'>"
	+" <div id='imgs' class='imgs' style='position:absolute;width:905px;height:174px;z-index:0'>"
	+" <ul id='ul_1' class='ul_1' style='position:absolute;width:305px;height:174px;z-index:0'>"
	+" <li><div id='cChart' style='position:absolute;width:305px;height:174px;z-index:0'></div></li>"
	+" <li><div id='eChart' style='position:absolute;width:305px;height:174px;z-index:0'></div></li>"
	+" <li><div id='fChart' style='position:absolute;width:305px;height:174px;z-index:0'></div></li>"
	+"</ul>"
	+"</div>"
	+"</div>"

	+"<div id='right-tab' class='right_bt bt' onclick='youyi()' style='margin-top:50px;margin-left:285px;width:20px;height:104px;position:absolute;z-index:1;'></div>"
	+"</div>"					
	+"<div class='chartBox' style='float:right;margin-right:2%;width:47%;margin-top:10px;'>"					
	+" <div id='dChart' style='width:100%;height:100%;' ></div>"				     
	+"</div>";
	$("#zhongduan_body").html(html);
	$.ajax({
		url:url,
		type:"post",
		timeout:60000,
		dataType:"json",
		async: false,
		success: function(data, textStatus){
			
			if(textStatus=="success"){
				/*testSb="['2009','2010','2011','2012','2013']##案件起数2|[1221.7441,1111.8377,1354.3515,2114.4288,2312.4573]&&案值2|[2218.7441,1923.8377,2134.3515,3215.4288,2417.4573]";
				sbs=testSb.split("##");
				sbs1 = sbs[1].split("&&");
				sbs2 = sbs1[0].split("|");
				anjian = "[{name:'便利店',y:40},{name:'娱乐服务',y:30},{name:'商场',y:10},{name:'其他',y:10},{name:'超市',y:5},{name:'烟酒商店',y:5}]";*/
				
				caseInfo0 = (data.caseInfo).toString();
				caseInfo1 = caseInfo0.split("##");
				caseInfo2 = caseInfo1[1].split("&&");
				caseInfo31 = caseInfo2[0].split("|");
				caseInfo32 = caseInfo2[1].split("|");
				caseInfo33 = caseInfo2[2].split("|");
				itemQuantity0 = (data.itemQuantity).toString();
				
				itemValue0 = (data.itemValue).toString();

				caseChara0 = (data.caseChara).toString();

				dealParty0 = (data.dealParty).toString();
				dealParty1 = dealParty0.split("##");
				dealParty2 = dealParty1[1].split("&&");
				dealParty31 = dealParty2[0].split("|");
				dealParty32 = dealParty2[1].split("|");
				dealParty33 = dealParty2[2].split("|");
				condemn0 = (data.condemn).toString();
				condemn1 = condemn0.split("##");
				condemn2 = condemn1[1].split("|");
			}else{
				jAlert("请求出错！");
			}
		},
		error: function(XMLHttpRequest,textStatus){
			//请求出错处理
			alert("请求出错！错误类型="+textStatus);
		}
		});
	caseStatusData(caseInfo1,caseInfo31,caseInfo32,caseInfo33,itemQuantity0,itemValue0,caseChara0,dealParty1,dealParty31,dealParty32,dealParty33,condemn1,condemn2);
}
function caseTable(comId){
	comId = $("#com_id_hid").val();
	$(".tab_first").addClass("active");
	$(".tab_second").removeClass("active");
    	/*var htmls = "<iframe style='height:400px;width:600px;' class='marketIframe' src='/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorCaseInfRpt.jsp'></iframe>";
    	 $(".zhongduan_body").html(""); 
    	 $(".zhongduan_body").html(htmls); */
    	 
	showPopWin('案件管理','/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorCaseInfRpt.jsp', 1100,460,true,true);//弹出帮助框
    var inParam = '{"param1":"参数1","param2":"参数2"}';
    setInParam(inParam);
}
function caseRefresh(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
	/*if(($("#tab_first").attr("class"))==("tab_first active")){
		marketTable(comId);
	}else{*/
		caseStatusGraph(comId);
	//}
	
}
//案件管理数据
function caseStatusData(caseInfo1,caseInfo31,caseInfo32,caseInfo33,itemQuantity0,itemValue0,caseChara0,dealParty1,dealParty31,dealParty32,dealParty33,condemn1,condemn2){	
var char1,char2,char3,char4,chart5,chart6,chart7,chart8;	
/*char1 = new Highcharts.Chart(
		{
            chart: {
                renderTo: 'aChart'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: eval(caseInfo1[0])
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
                
            },
            tooltip: {
    			formatter: function() {
    			return this.series.name + ' ' + this.y;
    			}
    			},
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    color:'#00CC33'
                },
                series: {
                    borderWidth: 1,
                    borderColor: '#FFFFFF'
                }
            },
            series: [{
                name: eval(caseInfo31[0]),
                data: eval(caseInfo31[1])
            }, {
                name: eval(caseInfo32[0]),
                data: eval(caseInfo32[1])
            }, {
                name: eval(caseInfo33[0]),
                data: eval(caseInfo33[1])
            }]
        });*/

char1 = new Highcharts.Chart(
		{
            chart: {
                renderTo: 'aChart',
                backgroundColor:'#8CBACE',
                borderColor: '#5ACFAD',
                borderWidth: 1
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: eval(caseInfo1[0])
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
                
            },
            tooltip: {
    			formatter: function() {
    			return this.series.name + ':' + this.y;
    			}
    			},
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    color:'#00CC33',
                },
                series: {
                    borderWidth: 1,
                    borderColor: '#FFFFFF',
                    dataLabels: {
                        enabled: true,
                        rotation: -45,
                        y: -15,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                        style: {
                            textShadow: '0 0 3px black, 0 0 3px black'
                        },
                        formatter: function() {                   
                            return this.y +'起';               
                            
                            }
                    }
                }
            },
            series: [{
                name: eval(caseInfo31[0]),
                data: eval(caseInfo31[1])
            }]
        });

char7 = new Highcharts.Chart(
		{
            chart: {
                renderTo: 'gChart',
                backgroundColor:'#8CBACE',
                borderColor: '#5ACFAD',
                borderWidth: 1
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: eval(caseInfo1[0])
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
                
            },
            tooltip: {
    			formatter: function() {
    			return this.series.name + ':' + this.y;
    			}
    			},
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    color:'#00CC33'
                },
                series: {
                    borderWidth: 1,
                    borderColor: '#FFFFFF',
                    dataLabels: {
                        enabled: true,
                        rotation: -45,
                        y: 10,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                        style: {
                            textShadow: '0 0 3px black, 0 0 3px black'
                        },
                        formatter: function() {                   
                            return this.y +'万支';               
                            
                            }
                    }
                }
            },
            series: [{
                name: eval(caseInfo32[0]),
                data: eval(caseInfo32[1])
            }]
        });

		
		 char8 = new Highcharts.Chart({
	            chart: {
	                renderTo: 'hChart',
	                backgroundColor:'#8CBACE',
	                borderColor: '#5ACFAD',
	                borderWidth: 1
	            },
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                categories: eval(caseInfo1[0])
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                }
	                
	            },
	            tooltip: {
	            	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                    '<td style="padding:0;color:black"><b>{point.y}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	        		},
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0	                    
	                },
	                series: {
	                    borderWidth: 1,
	                    borderColor: '#FFFFFF',
	                    dataLabels: {
	                        enabled: true,
	                        rotation: -45,
	                        y: -15,
	                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
	                        style: {
	                            textShadow: '0 0 3px black, 0 0 3px black'
	                        },
	                        formatter: function() {                   
	                            return this.y +'万元';               
	                            
	                            }
	                    }
	                }
	            },
	            series: [{
	            	name: eval(caseInfo33[0]),
	                data: eval(caseInfo33[1])
	    
	            }]
	        });
		 char2 = new Highcharts.Chart({
	            chart: {
	                renderTo: 'bChart',
	                type: 'column',
	                backgroundColor:'#8CBACE',
	                borderColor: '#5ACFAD',
	                borderWidth: 1
	            },
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                categories: eval(dealParty1[0])
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                }
	                
	            },
	            tooltip: {
	            	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                    '<td style="padding:0;color:black"><b>{point.y}人</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	        		},
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0,
	                    dataLabels: {
	                        enabled: true,
	                        rotation: -45,
	                        y: -15,
	                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
	                        style: {
	                            textShadow: '0 0 3px black, 0 0 3px black'
	                        },
	                        formatter: function() {                   
	                            return this.y ;               
	                            
	                            }
	                    }
	                    
	                },
	                series: {
	                    borderWidth: 1,
	                    borderColor: '#FFFFFF'
	                }
	            },
	            series: [{
	                name: eval(dealParty31[0]),
	                data: eval(dealParty31[1])
	    
	            }, {
	            	name: eval(dealParty32[0]),
	                data: eval(dealParty32[1])
	    
	            }, {
	            	name: eval(dealParty33[0]),
	                data: eval(dealParty33[1])
	    
	            }]
	        });
		 char3 = new Highcharts.Chart({
	            
	            chart: {
	            	renderTo: 'cChart',
	                backgroundColor:'#8CBACE',
	                borderColor: '#5ACFAD',
	                borderWidth: 1,
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false
	            },
	            title: {
	                text: '案件性质',
	                style:{
	                	fontFamily:'Microsoft YaHei',
	                    color:'#1b6c49',
	                    fontSize:'12px'
	                } 
	            },
	            plotOptions: {
	                pie: {
	                	//size : '90%',
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
                            distance:0,                            
	                        color: '#000000',
	                        connectorColor: '#ffffff',
	                        formatter:function(){
	                        	return this.y; 
	                         } ,
	                         style: {
	                             fontSize:'10px'
	                         }
	                    },
	                    showInLegend: true
	                }
	            },
	            legend: {
	                layout: 'vertical',
	                align: 'right',
	                verticalAlign: 'middle',
	                borderWidth: 0
	            },
	            series: [{
	                type: 'pie',
	                name: '起',
	                data: [
	                    ['非法渠道进货案件',   45.0],
	                    ['无证经营案件',       26.8],
	                    {
	                        name: '无证运输案件',
	                        y: 12.8,
	                        sliced: true,
	                        selected: true
	                    },
	                    ['假冒案件',    8.5],
	                    ['走私案件',     6.2],
	                    ['其他',   0.7]
	                ]
	            }]
	        });
		
		 char4 = new Highcharts.Chart({
	            chart: {
	                renderTo: 'dChart',
	                backgroundColor:'#8CBACE',
	                borderColor: '#5ACFAD',
	                borderWidth: 1
	            },
	            title: {
	                text: eval(condemn2[0]),
	                style:{
	                	fontFamily:'Microsoft YaHei',
	                    color:'#1b6c49',
	                    fontSize:'12px'
	                } 
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                categories: eval(condemn1[0]),
	                labels: {
	                    rotation: -90,
	                    x:5,
	                    style: {
	                    	fontSize: '12px',
	                        fontFamily: 'Verdana, sans-serif'
	                    }
	                }
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                }
	                
	            },
	            tooltip: {
	            	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                    '<td style="padding:0;color:black"><b>{point.y}人</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	    			},
	            legend: {
				    enabled:false
	               
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: eval(condemn2[0]), 
	                data: eval(condemn2[1])
	    
	            }]
	        });	
		 char5 = new Highcharts.Chart({
		        
		        chart: {
		        	renderTo: 'eChart',
		            backgroundColor:'#8CBACE',
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
	                borderColor: '#5ACFAD',
	                borderWidth: 1
		        },
		        title: {
		            text: '案值',
		            style:{
	                	fontFamily:'Microsoft YaHei',
	                    color:'#1b6c49',
	                    fontSize:'12px'
	                } 
		        },
		        plotOptions: {
		            pie: {
		            	//size : '90%',
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
	                        enabled: true,
                            distance:0,                            
	                        color: '#000000',
	                        connectorColor: '#ffffff',
	                        formatter:function(){
	                        	return this.y; 
	                         } ,
	                         style: {
	                             fontSize:'10px'
	                         }
	                    },
		                showInLegend: true
		            }
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            type: 'pie',
		            name: '万元',
		            data: [
		                ['Firefox',   35.0],
		                ['IE',       36.8],
		                {
		                    name: 'Chrome',
		                    y: 12.8,
		                    sliced: true,
		                    selected: true
		                },
		                ['Safari',    8.5],
		                ['Opera',     6.2],
		                ['Others',   0.7]
		            ]
		        }]
		    });
		 char6 = new Highcharts.Chart({
		        
		        chart: {
		        	renderTo: 'fChart',
		            backgroundColor:'#8CBACE',
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
	                borderColor: '#5ACFAD',
	                borderWidth: 1
		        },
		        title: {
		            text: '涉案物品数量',
		            style:{
	                	fontFamily:'Microsoft YaHei',
	                    color:'#1b6c49',
	                    fontSize:'12px'
	                } 
		        },
		        plotOptions: {
		            pie: {
		            	//size : '90%',
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
	                        enabled: true,
                            distance:0,                            
	                        color: '#000000',
	                        connectorColor: '#ffffff',
	                        formatter:function(){
	                        	return this.y; 
	                         } ,
	                         style: {
	                             fontSize:'10px'
	                         }
	                    },
		                showInLegend: true
		            }
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            type: 'pie',
		            name: '数量',
		            data: [
		                ['Firefox',   45.0],
		                ['IE',       26.8],
		                {
		                    name: 'Chrome',
		                    y: 12.8,
		                    sliced: true,
		                    selected: true
		                },
		                ['Safari',    8.5],
		                ['Opera',     6.2],
		                ['Others',   0.7]
		            ]
		        }]
		    });
//alert(sbs2[0]+"--"+sbs2[1]);		 
//		 char1.series[0].setData(eval(sb1));
//		 char2.series[0].setData(eval(sb2));
//		 alert(sbs2[1]);
/*		 char1.addSeries({name:eval("\'"+sbs2[0]+"\'"), data: eval(sbs2[1])});
		 char2.addSeries({name:eval("\'"+sbs2[0]+"\'"), data: eval(sbs2[1])});*/
		 char3.series[0].setData(eval(caseChara0));
		 char5.series[0].setData(eval(itemValue0));
		 char6.series[0].setData(eval(itemQuantity0));
		 /*char4.addSeries({name: '违规零售户比例', data: eval("[\'违规零售户比例\',"+sb5+"]")});
		 char4.addSeries({name: '合法零售户比例', data: eval("[\'合法零售户比例\',"+sb6+"]")});*/
	
	}
//许可证
function   identify(comId){	
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
	comId="";
$("#content-area1").html("");
// alert(comId);
var html="<div id='homelj' class='zhongduanjiansheContent'>"
+"<div id='home_head' class='zhongduan_head'>"
+"<div class='tabs'>"
+"<div class='tab_first'  id='tab_first' onClick='identifyTable("+comId+")'>报表信息</div>"
/*+"<div class='tab_second' id='tab_second' onClick='identifyGraph("+comId+")'>图形展示</div></div>"*/
+"<input type='hidden' id='comIdD' name='comIdD' value='"+comId+"'/>"
+"</div>"
+"<div class='reflesh' id='identifyR' onClick='identifyRefresh("+comId+")' style='float:right;margin-right:-100px;margin-top:8px;' id='terminalref' name='"+comId+"'></div>"			        
+"</div>"				 
+"<div class='zhongduan_body' id='zhongduan_body'>"			 			   										 	  		  
+"</div>";
$("#content-area1").html(html);
identifyGraph(comId);
}
function identifyGraph(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
	$(".tab_second").addClass("active");
	$(".tab_first").removeClass("active");
	var html = "<div class='chartBox' style='margin-top:40px;float:left;margin-left:2%;width:96%;height:75%;'>"			        
	+" <div id='aChart' style='width:100%;height:100%;' ></div>"			          
	+" </div>";	
	$("#zhongduan_body").html("");
	$("#zhongduan_body").html(html);
	
	var url = "/portal/AuthenService?USERID=chensong&APP=REG&RESOURCE=http://10.60.4.83/v3/zmmonitorlicensegraphics.cmd?method=graphics|regieId="+comId;
	var license0;
	var license1 = new Array();
	var license2 = new Array();

	$.ajax({
	url:url,
	type:"post",
	timeout:60000,
	dataType:"json",
	async: false,
	success: function(data, textStatus){
		if(textStatus=="success"){
			license0 = (data.license).toString();
			license1 = license0.split("##");
			license2 = license1[1].split("|");
		}else{
			jAlert("请求出错！");
		}
	},
	error: function(XMLHttpRequest,textStatus){
		//请求出错处理
		alert("请求出错！错误类型="+textStatus);
	}
	});
	identifyData(license1,license2);
}
function identifyTable(comId){
	comId = $("#com_id_hid").val();
	/*$(".tab_first").addClass("active");
	$(".tab_second").removeClass("active");*/
    /*	var htmls = "<iframe style='height:400px;width:600px;' class='marketIframe' src='/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorLicenseRpt.jsp'></iframe>";
    	 $(".zhongduan_body").html(""); 
    	 $(".zhongduan_body").html(htmls); */
    showPopWin('许可证','/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorLicenseRpt.jsp', 1100,460,true,true);//弹出帮助框
    var inParam = '{"param1":"参数1","param2":"参数2"}';
    setInParam(inParam);
}
function identifyRefresh(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
	/*if(($("#tab_first").attr("class"))==("tab_first active")){
		identifyTable(comId);
	}else{*/
		identifyGraph(comId);
	//}
	
}
//许可证数据
function identifyData(license1,license2){

var char1;

char1 = new Highcharts.Chart({
    chart: {
        renderTo: 'aChart',
        backgroundColor:'none'
    },
    title: {
        text: eval(license2[0]),
        style:{
        	fontFamily:'Microsoft YaHei',
            color:'#1b6c49',
            fontSize:'16px'
        } 
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: eval(license1[0]),
        labels: {
			style: {
				color : '#f1f815',
				fontWeight : 'bold',
				fontSize : '12px'
			}
		}
    },
    tooltip: {
    	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0;color:black"><b>{point.y}个</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
		},
    yAxis: {
        min: 0,
        title: {
            text: ''
        }
        
    },
    legend: {
	    enabled:false
       
    },
    plotOptions: {
    	column: {
            pointPadding: 0.2,
            borderWidth: 1,
            color:'#42BA21',
            dataLabels: {
                enabled: true,
                rotation: -45,
                y: -15,
                color:  'white',
                /*style: {
                    textShadow: '0 0 3px black, 0 0 3px black'
                },*/
                formatter: function() {                   
                    return this.y+'个' ;               
                    
                    }
            }
        },
        series: {
            borderWidth: 1,
            borderColor: '#FFFFFF'
        }
    },
    series: [{
        name: eval(license2[0]), 
        type: 'column',
        data: eval(license2[1])

    }]
});	
	 
	
		 
//		 char1.series[0].setData(eval(sb1));
//		 char2.series[0].setData(eval(sb2));
/*		 char3.addSeries({name: '许可证占人口比例', data: eval("[\'许可证占人口比例\',"+sb3+"]")});
		 char3.addSeries({name: '非许可证占人口比例', data: eval("[\'非许可证占人口比例\',"+sb4+"]")});
		 char4.addSeries({name: '违规零售户比例', data: eval("[\'违规零售户比例\',"+sb5+"]")});
		 char4.addSeries({name: '合法零售户比例', data: eval("[\'合法零售户比例\',"+sb6+"]")});*/
	
	}
//队伍建设
function   staffBuild(comId){	
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
	comId="";
$("#content-area1").html("");
// alert(comId);
var html="<div id='homelj' class='zhongduanjiansheContent'>"
+"<div id='home_head' class='zhongduan_head'>"	
+"<div class='tabs'>"
+"<div class='tab_first'  id='tab_first' onClick='staffTable("+comId+")'>报表信息</div>"
/*+"<div class='tab_second' id='tab_second' onClick='staffGraph("+comId+")'>图形展示</div></div>"*/
+"<input type='hidden' id='comIdD' name='comIdD' value='"+comId+"'/>"
+"</div>"	
+"<div class='reflesh' id='staffBuildR'  onClick='staffRefresh("+comId+")' style='float:right;margin-right:-100px;margin-top:8px;' id='terminalref' name='"+comId+"'></div>"			        	 
+"</div>"	
+"<div class='zhongduan_body' id='zhongduan_body'>"			 		  		  
+"</div>";
$("#content-area1").html(html);
staffGraph(comId);
}
function staffGraph(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
/*	$(".tab_second").addClass("active");
	$(".tab_first").removeClass("active");*/
	$("#zhongduan_body").html("");
	var html = "<div class='chartBox' style='float:left;margin-left:2%;width:96%'>"			        
	+" <div id='aChart' style='width:100%;height:100%;' ></div>"			          
	+" </div>"	
	+"<div class='chartBox' style='float:left;margin-left:2%;width:96%'>"			        
	+" <div id='bChart' style='width:100%;height:100%;' ></div>"			          
	+" </div>";
	$("#zhongduan_body").html(html);
	if(comId==undefined||comId=="comId")
		comId="";
		var url = "/portal/AuthenService?USERID=chensong&APP=REG&RESOURCE=http://10.60.4.83/v3/zmmonitorteamgraphics.cmd?method=graphics|regieId="+comId;
		//var url ="#";
		var age,edu;
		$.ajax({
		url:url,
		type:"post",
		timeout:60000,
		dataType:"json",
		async: false,
		success: function(data, textStatus){
			if(textStatus=="success"){
				age = (data.age).toString();
				edu = (data.edu).toString();
			}else{
				jAlert("请求出错！");
			}
		},
		error: function(XMLHttpRequest,textStatus){
			//请求出错处理
			alert("请求出错！错误类型="+textStatus);
		}
		});
		staffBuildData(age,edu);
}
function staffTable(comId){
	comId = $("#com_id_hid").val();
	/*$(".tab_first").addClass("active");
	$(".tab_second").removeClass("active");*/
    showPopWin('队伍建设','/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorTeamRpt.jsp', 1100,460,true,true);//弹出帮助框
    var inParam = '{"param1":"参数1","param2":"参数2"}';
    setInParam(inParam);
}
function staffRefresh(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
		staffGraph(comId);
	
}
//队伍建设数据
function staffBuildData(age,edu){
	
var char1,char2,char3,char4;

//var strs = [{name:'便利店',y:64189},{name:'娱乐服务',y:9326},{name:'商场',y:680},{name:'其他',y:7360},{name:'超市',y:11892},{name:'烟酒商店',y:8053},{name:'食杂店',y:235318}]

char1 = new Highcharts.Chart({
    
    chart: {
    	renderTo: 'aChart',
        backgroundColor:'none',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
    },
    title: {
        text: '人员学历状况',
        	style: {
        		marginLeft:'-50px',
                fontFamily: 'Microsoft Yahei',
                color: '#1b6c49'	
            }
    },
    plotOptions: {
        pie: {
        	//size : '90%',
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
            	  enabled: true,
                  distance:0,
                  softConnector:false,                    
                  color: '#000000',
                  connectorColor: '#ffffff',
                 formatter:function(){
                	return this.y; 
                 } ,
                 style: {
                     fontSize:'12px'
                 }
            },
            showInLegend: true
        }
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
    colors : [ '#c42525', '#a6c96a', '#1aadce', '#492970', ],
    series: [{
        type: 'pie',
        name: '人数',
        data: [
            ['大专',       36.8],
            ['本科以上',       47.8],
            ['高中、中专',    8.5],
            ['初中以下',     6.9]
        ]
    }]
});
char2 = new Highcharts.Chart({
    
    chart: {
    	renderTo: 'bChart',
        backgroundColor:'none',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
    },
    title: {
        text: '人员年龄状况',
        style:{
        	fontFamily:'Microsoft YaHei',
            color:'#1b6c49',
            fontSize:'16px',
            marginLeft:'-50px'
        } 
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
          	  enabled: true,
                distance:0,
                softConnector:false,                    
                color: '#000000',
                connectorColor: '#ffffff',
               formatter:function(){
              	return this.y; 
               } ,
               style: {
                   fontSize:'12px'
               }
          },
            showInLegend: true
        }
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
    colors : [ '#42BB25', '#50BC93', '#910000', '#77a1e5' ],
    series: [{
        type: 'pie',
        name: '人数',
        data: [
            ['31-40岁',       47.8],  
            ['41-50岁',       36.8],
            ['31岁以下',    8.5],
            ['51岁以上',     6.9]
        ]
    }]
});	 
	
	 
		 char1.series[0].setData(eval(edu));
		 
		 char2.series[0].setData(eval(age));
//		 char2.series[0].setData(eval(sb2));
/*		 char3.addSeries({name: '许可证占人口比例', data: eval("[\'许可证占人口比例\',"+sb3+"]")});
		 char3.addSeries({name: '非许可证占人口比例', data: eval("[\'非许可证占人口比例\',"+sb4+"]")});
		 char4.addSeries({name: '违规零售户比例', data: eval("[\'违规零售户比例\',"+sb5+"]")});
		 char4.addSeries({name: '合法零售户比例', data: eval("[\'合法零售户比例\',"+sb6+"]")});*/
	
	}
//准运证
function   navicert(comId){	
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
	comId="";
$("#content-area1").html("");
// alert(comId);
var html="<div id='homelj' class='zhongduanjiansheContent'>"
	+"<div id='home_head' class='zhongduan_head'>"	
	+"<div class='tabs'>"
	+"<div class='tab_first'  id='tab_first' onClick='navicertTable("+comId+")'>报表信息</div>"
	/*+"<div class='tab_second' id='tab_second' onClick='staffGraph("+comId+")'>图形展示</div></div>"*/
	+"<input type='hidden' id='comIdD' name='comIdD' value='"+comId+"'/>"
	+"</div>"	
	+"<div class='reflesh' id='staffBuildR'  onClick='navicertRefresh("+comId+")' style='float:right;margin-right:-100px;margin-top:8px;' id='terminalref' name='"+comId+"'></div>"			        	 
	+"</div>"	
	+"<div class='zhongduan_body' id='zhongduan_body'>"			 		  		  
	+"</div>";
	$("#content-area1").html(html);
	navicertGraph(comId);	
}
function navicertGraph(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
/*	$(".tab_second").addClass("active");
	$(".tab_first").removeClass("active");*/
	$("#zhongduan_body").html("");
	var html = "<div class='chartBox' style='float:left;margin-left:0px;width:96%;height:100%'>"			        
	+" <div id='aChart' style='width:800px;height:100%;margin-left:-150px' ></div>"			          
	+" </div>"	;
	$("#zhongduan_body").html(html);
	if(comId==undefined||comId=="comId")
		comId="";
		var url = "/portal/AuthenService?USERID=chensong&APP=REG&RESOURCE=http://10.60.4.83/v3/zmmonitortransgraphics.cmd?method=graphics|regieId="+comId;
		//var url ="#";
		var trans;
		$.ajax({
		url:url,
		type:"post",
		timeout:60000,
		dataType:"json",
		async: false,
		success: function(data, textStatus){
			//alert(data);
			if(textStatus=="success"){
				trans = (data.trans).toString();
			}else{
				jAlert("请求出错！");
			}
		},
		error: function(XMLHttpRequest,textStatus){
			//请求出错处理
			alert("请求出错！错误类型="+textStatus);
		}
		});
		navicertData(trans);
}
function navicertTable(comId){
	comId = $("#com_id_hid").val();
	/*$(".tab_first").addClass("active");
	$(".tab_second").removeClass("active");*/
    /*	var htmls = "<iframe style='height:400px;width:600px;' class='marketIframe' src='/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorTeamRpt.jsp'></iframe>";
    	 $(".zhongduan_body").html(""); 
    	 $(".zhongduan_body").html(htmls); */
    showPopWin('准运证','/portal/jsp/com/v6/screen/portal/leadworkspace/ZmMonitorTransRpt.jsp', 1100,460,true,true);//弹出帮助框
    var inParam = '{"param1":"参数1","param2":"参数2"}';
    setInParam(inParam);
}
function navicertRefresh(comId){
	comId = $("#com_id_hid").val();
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
		comId="";
	navicertGraph(comId);
	
}
//准运证数据
function navicertData(trans){
	
var char1;

char1 = new Highcharts.Chart({
    
    chart: {
    	renderTo: 'aChart',
        backgroundColor:'none',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
    },
    title: {
        text: '准运证',
        	style: {
        		marginLeft:'-40px',
                fontFamily: 'Microsoft Yahei',
                color: '#1b6c49'	
            }
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
          	  enabled: true,
                distance:0,
                softConnector:false,                    
                color: '#000000',
                connectorColor: '#ffffff',
               formatter:function(){
              	return this.y; 
               } ,
               style: {
                   fontSize:'12px'
               }
          },
            showInLegend: true
        }
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
    colors : [ '#c42525', '#a6c96a', '#1aadce', '#492970', ],
    series: [{
        type: 'pie',
        name: '占比',
        data: [
            ['大专',       36.8],
            ['本科以上',       47.8],
            ['高中、中专',    8.5],
            ['初中以下',     6.9]
        ]
    }]
}); 
	
	 
		 char1.series[0].setData(eval(trans));
//		 char2.series[0].setData(eval(sb2));
/*		 char3.addSeries({name: '许可证占人口比例', data: eval("[\'许可证占人口比例\',"+sb3+"]")});
		 char3.addSeries({name: '非许可证占人口比例', data: eval("[\'非许可证占人口比例\',"+sb4+"]")});
		 char4.addSeries({name: '违规零售户比例', data: eval("[\'违规零售户比例\',"+sb5+"]")});
		 char4.addSeries({name: '合法零售户比例', data: eval("[\'合法零售户比例\',"+sb6+"]")});*/
	
	}

function currdate(){
	var myDate = new Date();
	myDate.setTime(myDate.getTime()-24*60*60*1000);
	var year=myDate.getFullYear();
	var month =myDate.getMonth()+1;
	var day = myDate.getDate();
	var currdate=String(year)+String(month)+String(day);  
	return currdate;
}