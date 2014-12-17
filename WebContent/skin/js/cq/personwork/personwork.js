$(document).ready(function(){
	initUserApps();
	initDaiBan();//我的待办
	initTarget();//我的目标
	//initProcedure();//我的流程
	MyDuty();
	initReportList();
	initDisk();
	$(".tab1").mouseover(function(){
  $(".portalicon").css("background","url(/portal/skin/img/cq/anniu1.png)  16px 8px no-repeat");
  	$(".tab1").css("color","#000");
  	$(".tab2").css("color","#fff");
  	$(".portaltab").css("display","block");
  	$(".sys-list").css("display","none");
  	initDisk();
});
 	$(".tab2").mouseover(function(){
  $(".portalicon").css("background","url(/portal/skin/img/cq/anniu2.png)  16px 8px no-repeat");
    $(".tab1").css("color","#fff");
  	$(".tab2").css("color","#000");
  	$(".portaltab").css("display","none");
  	$(".sys-list").css("display","block");
  	initCollect();
});

});
function initReportList(){
	var userId=$("#userId").val(); 
	//alert(userId);
	$.ajax({
		type: "post",
		url: "/portal/AuthenService?USERID="+userId+"&APP=I3&RESOURCE=http://10.11.2.136/I3/formCataManageCmd.cmd?method=getAllRptListByUser",
		beforeSend: function(XMLHttpRequest){
			//alert("before2");
		},
		success: function(data, textStatus){
			//alert(data);
			var jsonObj=eval(data);
			
			var len = jsonObj.length;
			//alert(len);
			if(len==0){
			 	
				return;
			}else{
				for(var i=0;i<jsonObj.length && i<6;i++){ 			
					if(!jsonObj[i]){
						break;
					}else{
						var title=jsonObj[i].REPORT_NAME;
						if(jsonObj[i].REPORT_NAME.length>10){
							title=jsonObj[i].REPORT_NAME.substring(0,10)+"..";
						
						}
					/*	if(jsonObj[i].procedureApproval.length>5){
							jsonObj[i].procedureApproval=jsonObj[i].procedureApproval.substring(0,5)+"..";
						}
						if(jsonObj[i].procedureSegment.length>5){
							jsonObj[i].procedureSegment=jsonObj[i].procedureSegment.substring(0,5)+"..";
						}*/
						$("<tr><td title=\""+jsonObj[i].REPORT_NAME+"\" style=\"width:30%;cursor:pointer;\"><a href=\"/portal/AuthenService?USERID="+userId+"&APP=I3&RESOURCE=http://10.11.2.136/I3/queryreport_page_init.cmd?method=query&visitFlag=1&reportId="+jsonObj[i].REPORT_ID+"&version="+jsonObj[i].VERSION+"\">"+title+"</a></td></tr>").appendTo("#reporttablebody");
						
					}
					
				}
			}

		},
		complete: function(XMLHttpRequest, textStatus){
		
		},
		error: function(){
		//alert("error");
		}
	});  
}
//我的待办
function initDaiBan(){
	$.ajax({
		type: "post",
		url: "cqpersonwork.cmd?method=getWork",
		beforeSend: function(XMLHttpRequest){
			$("#work-list").html("");
			$("<li></li>").append(
				$("<a></a>").attr("href","#").text("暂无条目")
			).appendTo("#work-list");
		},
		success: function(data, textStatus){
			var jsonObj=eval(data);
			$("#work-list").html("");
			 //统计待办任务数
			var len = jsonObj.length;
			if(len==0){
			 	$("<li></li>").append(
					$("<a></a>").attr("href","#").text("暂无条目")
				).appendTo("#work-list");
				return;
			}
			for(var i=0;i<6;i++){ 			
				if(!jsonObj[i]){
				 	break;
				}else{
				 	$("<li></li>").append(
						$("<a></a>").attr("href",jsonObj[i].backlogUrl).text(jsonObj[i].backlogTitle)
							.attr("target","_blank")
							.attr("title",jsonObj[i].backlogTitle)
					 ).append(		 
						$("<span class='date'></span>").text(jsonObj[i].backlogDate)
					 ).appendTo("#work-list");
					$("#work-list li:even").addClass("tbgeven"); 
					$("#work-list li:odd").addClass("tbgodd");
				}
			}
		},
		complete: function(XMLHttpRequest, textStatus){
		
		},
		error: function(){
		
		}
	});  
}
//我的目标
function initProcedure(){
	$.ajax({
		type: "post",
		url: "cqpersonwork.cmd?method=getMyProcedure",
		beforeSend: function(XMLHttpRequest){
			
		},
		success: function(data, textStatus){
			var jsonObj=eval(data);
			var len = jsonObj.length;
			if(len==0){
			 	
				return;
			}else{
				for(var i=0;i<jsonObj.length && i<6;i++){ 			
					if(!jsonObj[i]){
						break;
					}else{
						var title=jsonObj[i].procedureTitle;
						if(jsonObj[i].procedureTitle.length>5){
							title=jsonObj[i].procedureTitle.substring(0,5)+"..";
						
						}
						if(jsonObj[i].procedureApproval.length>5){
							jsonObj[i].procedureApproval=jsonObj[i].procedureApproval.substring(0,5)+"..";
						}
						if(jsonObj[i].procedureSegment.length>5){
							jsonObj[i].procedureSegment=jsonObj[i].procedureSegment.substring(0,5)+"..";
						}
						$("<tr><td title=\""+jsonObj[i].procedureTitle+"\" style=\"width:30%;text-align: center;cursor:pointer;\"><a href = "+jsonObj[i].procedureUrl+">"+title+"</a></td><td style=\"width:30%;text-align: center;\">"+jsonObj[i].procedureApproval+"</td><td style=\"width:30%;text-align: center;\">"+jsonObj[i].procedureSegment+"</td></tr>").appendTo("#proceduretablebody");
						
					}
					
				}
			}

		},
		complete: function(XMLHttpRequest, textStatus){
		
		},
		error: function(){
		
		}
	});  
}
//我的流程
function initTarget(){
	$.ajax({
		type: "post",
		url: "cqpersonwork.cmd?method=getMyTarget",
		beforeSend: function(XMLHttpRequest){
			
		},
		success: function(data, textStatus){
		var jsonObj=data;
		//alert(jsonObj);
			var len = jsonObj.DATA.length;
			//alert(len);
			if(len==0){
			 	
				return;
			}else{
				for(var i=0;i<len && i<5;i++){ 
					var title=jsonObj.DATA[i].orgName;
					if(!jsonObj.DATA[i]){
						break;
					}else{
						if(jsonObj.DATA[i].orgName.length>4){
						tiltle=jsonObj.DATA[i].orgName.substring(0,4)+"..";
						}
					//	alert(title);
						$("<tr><td title=\""+jsonObj.DATA[i].targetName+"\" style=\"width:30%;text-align: left;\">"+tiltle+"</td><td style=\"width:20%;text-align: center;\">"+jsonObj.DATA[i].expirDate+"</td><td style=\"width:20%;text-align: center;\">"+jsonObj.DATA[i].targetNum+"</td><td style=\"width:10%;text-align: center\">"+"100%"+"</td></tr>").appendTo("#targettablebody");
					}
				}
			}

		},
		complete: function(XMLHttpRequest, textStatus){
		
		},
		error: function(){
		
		}
	});  
}
function initUserApps(){
	 var userId=$("#userId").val(); 
	 $.ajax({
	  type: "post",
	  url: "cqpersonwork.cmd?method=commSys",
	  beforeSend: function(XMLHttpRequest){
		  $(".appmodel-container").html("");  
		
	  },
	  success: function(data, textStatus){
		  $(".appmodel-container").html(""); 
	//alert(data);
		 var jsonObj=eval("("+data+")");
		 var len = jsonObj.length;
	//alert(len);
		 var n;
		 if(len<10){
			 n=len;
		 }else{
			 n=10;
		 }
		 for(var i=0;i<n;i++){
//			alert(jsonObj[i].appname.substr(0,5));
			 var  appurl=jsonObj[i].appurl;
				$("<li></li>")
				 .append(
						 $("<a></a>")
						 .attr("href",appurl)
						 .attr("target","_blank")
						 .attr("title",jsonObj[i].appname)
						 .append(
							$("<div style=\"height:115px;\"></div>").html("<img style='margin-left: 24px; margin-top: 20px;' src='/portal/skin/img/cq/personwork/commS"+i+".png' /><p style=\"  font-family:Microsoft YaHei;color:#6A6A6A;height:25px;line-height:25px;font-size:14px;text-align:center\">"+jsonObj[i].appname.substr(0,5)+"</p>")
						  )
				 )
				 .appendTo(".appmodel-container");				
				}
	  },
	  complete: function(XMLHttpRequest, textStatus){
	  },
	  error: function(){
	  }

 });

}
//我的业绩
function MyDuty(){
	$.ajax({
		type: "post",
		url: "cqpersonwork.cmd?method=MyDuty",
		beforeSend: function(XMLHttpRequest){
			
		},
		success: function(data, textStatus){
			var jsonObj=eval(data);
			var len = jsonObj.length;		
			if(len==0){
			 	
				return;
			}else{
				for(var i=0;i<len&& i<6;i++){ 
					var title=jsonObj[i].dutydescribtion;
					if(!jsonObj[i]){
						break;
					}else{
						//alert(jsonObj[i].dutydescribtion.length);
						if(jsonObj[i].dutydescribtion.length>8){
						title=jsonObj[i].dutydescribtion.substring(0,8)+"..";
						}
					/*	if(jsonObj[i].duty.length>4){
						jsonObj[i].duty=jsonObj[i].duty.substring(0,4)+"..";
						}*/
					//	alert(title);
					$("<tr><td style=\"width:20%;text-align: center;\">"+jsonObj[i].duty+"</td><td title=\""+jsonObj[i].dutydescribtion+"\" style=\"width:30%;text-align: left;\">"+title+"</td><td style=\"width:20%;text-align: center;\"><a href=\""+jsonObj[i].dutyurl+"\">详细</a></td></tr>").appendTo("#MyDutytablebody");
					}
				}
			}

		},
		complete: function(XMLHttpRequest, textStatus){
		
		},
		error: function(){
		
		}
	});  
}
//网盘
function initDisk(){	
	var userId=$("#userId").val();	
	$.ajax({
		type: "post",
		data: "userId="+userId.toUpperCase(),      
		url: "/doc/index.php?r=/Doccenterapi/getDiskByUid",
		beforeSend: function(XMLHttpRequest){
	},
		success: function(data, textStatus){
		//  data="[{\"owner_type\":\"0\",\"use_space\":\"2.11GB\",\"disk_permit\":\"4\",\"owner_name\":\"SUPERADMIN\",\"name\":\"\u4e2a\u4eba\u7f51\u76d8\",\"create_time\":\"2013-11-25 17:57:00\",\"disk_type\":\"1\",\"owner_id\":\"SUPERADMIN\",\"up_time\":\"2014-01-22 10:41:20\",\"max_space\":\"10.00GB\",\"disk_id\":1,\"disk_max_space\":\"10737418240\",\"disk_use_space\":\"2266795858\",\"percent\":\"21\"},{\"owner_type\":\"1\",\"use_space\":\"62.48MB\",\"disk_permit\":\"0\",\"owner_name\":\"\u7701\u70df\u8349\u4e13\u5356\u5c40(\u516c\u53f8)\",\"name\":\"\u7701\u70df\u8349\u4e13\u5356\u5c40(\u516c\u53f8)\",\"create_time\":\"2013-11-26 15:01:27\",\"disk_type\":\"0\",\"owner_id\":\"QH10630001\",\"up_time\":\"2013-11-26 15:57:14\",\"max_space\":\"15.00GB\",\"disk_id\":5,\"disk_max_space\":\"16106127360\",\"disk_use_space\":\"65519324\",\"percent\":\"2\"},{\"owner_type\":\"1\",\"use_space\":\"1.16KB\",\"disk_permit\":\"0\",\"owner_name\":\"\u7ecf\u6d4e\u4fe1\u606f\u4e2d\u5fc3\",\"name\":\"\u7ecf\u6d4e\u4fe1\u606f\u4e2d\u5fc3\",\"create_time\":\"2013-11-26 15:45:52\",\"disk_type\":\"0\",\"owner_id\":\"QH106300012599\",\"up_time\":\"2013-11-26 15:47:48\",\"max_space\":\"15.00GB\",\"disk_id\":10,\"disk_max_space\":\"16106127360\",\"disk_use_space\":\"1190\",\"percent\":\"2\"}]";
		var jsonObj=eval("("+data+")");
		var len = jsonObj.length;
		for(var i=0;i<len;i++){
			var ii=i+1;
			if(jsonObj[i].owner_id==userId){
				initMyFile(jsonObj[i].disk_id);
			}  
		 }
		 },
	  complete: function(XMLHttpRequest, textStatus){
	  },
	  error: function(){
	  }

  });
}
//我的文件
function initMyFile(str){
	$.ajax({
		type: "post",
		data: {userId:userId.toUpperCase(),disk_id:str,pid:'0',_order:'up_time desc',filetype:'1'},   
		url: "/doc/index.php?r=/Doccenterapi/list",
		beforeSend: function(XMLHttpRequest){
			
		},
		success: function(data, textStatus){
			$("#MyFiletablebody").html("");
			//alert(data);
			var jsonObj=eval(data);
			var len = jsonObj.length;	
			//alert(len);	
			if(len==0){
			 	
				return;
			}else{
				for(var i=0;i<len&& i<5;i++){ 
					var title=jsonObj[i].name;
					if(!jsonObj[i]){
						break;
					}else{
						//alert(jsonObj[i].dutydescribtion.length);
						if(jsonObj[i].name.length>14){
						title=jsonObj[i].name.substring(0,14)+"..";
						}
					//	alert(title);
					$("<tr><td title=\""+jsonObj[i].name+"\" style=\"width:30%;text-align: left;\"><a href=\"/doc/index.php?r=/homead/indexnetdisk\">"+title+"</a></td><td style=\"width:20%;text-align: center;\">"+jsonObj[i].up_time.split(" ")[0]+"</td></tr>").appendTo("#MyFiletablebody");
					}
				}
			}

		},
		complete: function(XMLHttpRequest, textStatus){
		
		},
		error: function(){
		
		}
	});  
}

//我的文件
function initCollect(){
	var userId=$("#userId").val();
	$.ajax({
		type: "post",
		data: {uid:userId.toUpperCase(),version:'advanced',folder_id:'0'},   
		url: "/doc/index.php?r=/Doccenterapi/favList",
		beforeSend: function(XMLHttpRequest){
			$("#MyFiletablebody").html("");
		},
		success: function(data, textStatus){
			$("#MyFiletablebody").html("");
			//alert(data);
			var jsonObj=eval(data);
			var len = jsonObj.length;	
			
			if(len==0){
			 	
				return;
			}else{
				for(var i=0;i<len&& i<5;i++){ 
					var title=jsonObj[i].name;
					if(!jsonObj[i]){
						break;
					}else{
						//alert(jsonObj[i].dutydescribtion.length);
						if(jsonObj[i].name.length>9){
						title=jsonObj[i].name.substring(0,9)+"..";
						}
					//	alert(title);
					$("<tr><td title=\""+jsonObj[i].name+"\" style=\"width:30%;text-align: left;\"><a href=\"/doc/index.php?r=/homead/indexnetdisk\">"+title+"</a></td><td style=\"width:20%;text-align: center;\">"+jsonObj[i].create_time.split(" ")[0]+"</td></tr>").appendTo("#MyFiletablebody");
					}
				}
			}

		},
		complete: function(XMLHttpRequest, textStatus){
		
		},
		error: function(){
		
		}
	});  
}

