$(document).ready(function(){
	var map_name=$("#map_name").val();
	if(map_name==undefined||map_name=="undefined"||map_name==null||map_name=="")
		map_name = "山东省";
	$(".returnProvinceH font").html(map_name+"截止"+currdate()+"烟叶数据");
	//省市地图的切换
	$("#shandongMap h1").click(function(){
		 var imgName=$(this).attr("imgName");
		 var mapName=$(this).attr("mapName");
		 var mapID=$(this).attr("id");
		 //alert(mapID);
		 if(!(mapID==""||mapID=="10370701"||mapID=="10370301" ||mapID=="10371201"||mapID=="10370201"||mapID=="10371101"||mapID=="10371301"))
			{
			alert("该市不生产烟叶");
			return;
			}
		 $("#com_id_hid").val(mapID);
		 runsys($('.active',leadModleBox),mapID);
		// totalPlan(mapID);
		 $("#shandongMap").hide();
		 $("#"+mapName).fadeIn();	
		 $(".returnProvince").fadeIn();
		 $(".returnProvince").attr("mapId",mapName);
		 $("#map_name").val($('a',$(this)).html());
		 map_name=$("#map_name").val();
		 $(".returnProvinceH font").html(map_name+"截止"+currdate()+"烟叶数据");
	});	
	$(".returnProvince").click(function(){
		  var mapId = $(this).attr("mapId");
		  $("#"+mapId).hide();
		  $("#shandongMap").fadeIn();
		  $(".returnProvince").fadeOut();
		  $("#com_id_hid").val("");
		  runsys($('.active',leadModleBox));
		  $("#map_name").val("山东省");
		  map_name=$("#map_name").val();
		  $(".returnProvinceH font").html(map_name+"截止"+currdate()+"烟叶数据");
		});
	//头部导航
	var leadModleBox=$(".leadModleBox");
	$(".leadModleBox div").each(function(){
		  $(this).click(function(){
			  
			   $(this).addClass("active");
			   $(this).siblings().removeClass("active");
			   var mapId = $("#com_id_hid").val();
			   //$("#"+mapId).hide();
			   //$("#shandongMap").fadeIn();
			   //$(".returnProvince").fadeOut();
			   runsys($('.active',leadModleBox),mapId);
			 });
	});
	
	$("#caseStatus-report div td").each(function(){
		  $(this).click(function(){
			 // alert("111");
			   $(this).addClass("tabletdactive");
			   $(this).siblings().removeClass("active");
			 });
	});



	//默认点击一下头部最左侧的菜单
	$("#tobacco").click();
});


	

	
//运行分析
function runsys(obj,comId){
	//alert(comId);
	var activeId=obj.attr("id");
	switch (activeId) {
					case 'tobacco':
						tobacco(comId);
					    break;
	}
}	







//证件监控
function   tobacco(comId){	
	if(comId==undefined||comId=="comId"||comId=="undefined"||comId==null)
	comId="";
	if(!(comId==""||comId=="10370701"||comId=="10370301" ||comId=="10371201"||comId=="10370201"||comId=="10371101"||comId=="10371301"))
		{
		alert(comId);
		return;
		}
$("#content-area1").html("");
// alert(comId);
var html="<div id='zhongduanlj' class='zhongduanjiansheContent'>"
+"<div id='zhongduan_head' class='zhongduan_head'>"	
+"<div class='zhongduanTitle'>烟叶监控</div>"  
+"<input type='hidden' id='comIdD' name='comIdD' value='"+comId+"'/>"
+"<div class='reflesh' style='float:right;margin-right:20px;margin-top:8px;' id='terminalref' name='"+comId+"'></div>"			        
+"</div>"			 
+"<div class='zhongduan_body'>"			 
+"<div class='chartBox' style='float:left;margin-left:2%;width:35%;margin-top: 20px;'>"			        
+" <div id='aChart' style='width:100%;height:100%;' ></div>"			          
+" </div>"				   
+" <div class='chartBox' style='float:left;margin-right:2%;width:35%;margin-top: 20px;'>"				   
+"<div id='bChart' style='width:100%;height:100%;' ></div>"			            
+"</div>"					
+"	<div class='chartBox' style='float:left;margin-left:2%;width:35%;margin-top:10px;'>"				
+" <div id='cChart' style='width:100%;height:100%;'></div>"			           
+"</div>"					
+"<div class='chartBox' style='float:left;margin-right:2%;width:35%;margin-top:10px;'>"					
+" <div id='dChart' style='width:100%;height:100%;' ></div>"				     
+"</div>"					 

+"<div  id='yanyejihua_body' style='width: 20%; right: 107px; float: left; position: absolute;'>"					
+" <div id='eChart' style='' ></div>"
+"</div>"
+"</div>"	
+"</div>"	;
$("#content-area1").html(html);

if(comId==undefined||comId=="comId")
comId="";
var url = "MonopolyCmd.cmd?method=identyTobaccoPageInit&comId="+comId;
var sb1 ;
var sb2 ;
var sb3 ;
var sb4 ;
var sb5 ;
var datas = new Array();
$.ajax({
url:url,
type:"post",
timeout:60000,
dataType:"text",
async: false,
success: function(data, textStatus){
	//console.log(data);
	if(data!="none data"&&textStatus=="success"){
		datas = data.split("#");
		sb1 = datas[0];
		sb2 = datas[1];
		sb3 = datas[2];
		sb4 = datas[3];
		sb5 = datas[4];
		sb1="["+sb1+"]";
		sb2="["+sb2+"]";
		sb3="["+sb3+"]";
		sb4="["+sb4+"]";
		sb5="["+sb5+"]";
		//console.log(sb3);
		
	}else if(data=="none data"){
		alert("该市不生产烟叶");
	}else{
		jAlert("请求出错！");
	}
},
error: function(XMLHttpRequest,textStatus){
	//请求出错处理
	alert("请求出错！错误类型="+textStatus);
}
});
zhengjianjianshe(sb1,sb2,sb3,sb4,sb5,datas);
}

//终端建设highchart
function zhengjianjianshe(sb1,sb2,sb3,sb4,sb5,datas){
	
var char1,char2,char3,char4,char5;	
char1 = new Highcharts.Chart(
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
                categories: [
                    '生态群数量820个'
                ]
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
                    pointPadding: 0.4,
                    borderWidth: 0
                }
            },
            
            series: [{
                name: '',
                type: 'column',

                data: [820]
    
            }]
        });

		
	
		 char2 = new Highcharts.Chart({
		            chart: {
		                renderTo: 'bChart',
		            },
		            title: {
		                text: ''
		            },
		            xAxis: {
		                categories: [
		                    '收购数量'+datas[2]+'担',
//		              '收购数量100000担'
		                ]
		            },
		            yAxis: {
		                min: 0,
		                title: {
		                    text: '',
		                }
		                
		            },
		            legend: {
					    enabled:false
		               
		            },

		            plotOptions: {
		                column: {
		                    pointPadding: 0.4,
		                    borderWidth: 0
		                }
		            },
		            series: [{
		                name: '',
		                color: '#AA4644', 
		                type: 'column',
		                data: eval(sb3)
//		                data: [820]
		            }]
		        });
			
		 char3 = new Highcharts.Chart({
            chart: {
                renderTo: 'cChart'
            },
            title: {
                text: '烟农户数'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: [
                    '烟农户数'+datas[1]
//                    '烟农户数11111'
                ]
            },
            legend: {
			    enabled:false
               
            },

            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
                
            },

            plotOptions: {
                column: {
                    pointPadding: 0.4,
                    borderWidth: 0
                }
            },
            series: [{
                name: '',
                type: 'column',
                data: eval(sb2)
//                data: [820]
            }]
        });
		
		 char4 = new Highcharts.Chart({
            chart: {
                renderTo: 'dChart'
            },
            title: {
                text: '烟叶种植面积'
            },
            subtitle: {
                text: ''
            },
            legend: {
			    enabled:false
               
            },

            xAxis: {
                categories: [
                    '烟叶种植面积'+datas[0]+'亩'
                ]
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
                
            },

            plotOptions: {
                column: {
                    pointPadding: 0.4,
                    borderWidth: 0
                }
            },
            series: [{
                name: '',
                color: '#AA4644', 
                type: 'column',
                 data: eval(sb1)
//                data: [820]
            }]
        });
	//	 alert(" data: [820]");
		 char5 = new Highcharts.Chart({
	            chart: {
	                renderTo: 'eChart',
	                width:'180',
	                backgroundColor: 'rgba(255, 255, 255, 0)',
	                plotBorderColor : null,
	                plotBackgroundColor: null,
	                plotBackgroundImage:null,
	                plotBorderWidth: null,
	                plotShadow: false
	            },
	            title: {
	                text: ''
	            },
	            subtitle: {
	                text: ''
	            },
	            legend: {
				    enabled:false
	               
	            },

	            xAxis: {
	                categories: [
	                    '签约合同进度100%'
	                ],
	                labels: {
	    				style: {
	    					color: '#FFFFFF',
	    					fontWeight: 'bold',
	    					fontSize:"16px"
	    				}
	                    },
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ''
	                }
	                
	            },

	            plotOptions: {
	                column: {
	                    pointPadding: 0.4,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: '',
	                type: 'column',
	                color: '#806A9B', 
	                 data: [100]
	    
	            }]
	        });
	
//		 char1.series[0].setData(eval(sb1));
//		 char2.series[0].setData(eval(sb2));
//		 char3.series[0].setData(eval(sb3));
//		 char4.series[0].setData(eval(sb4));
	
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