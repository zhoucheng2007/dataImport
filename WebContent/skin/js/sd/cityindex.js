

$(document).ready(function()  {

 // initDaiYue();
	initCommS();
 	initAllDisk();
  	toDolist();  
    getNotifierDatas();
    getMail();
  //initPersonalDisk();
  initTobaccoReport();
     querySchList();
	 initCommT();
	
/*	$(function(){
	    $(".sysstab").click();
	 
	  });

	$(".toolstab").click(function(){
		 $(".sysstab").removeClass("active");
		 $(this).addClass("active");
		 initCommS();
	});
	$(".sysstab").click(function(){
		 $(".toolstab").removeClass("active");
		$(this).addClass("active");
		 initCommT();
	});*/
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

    	// dayNamesShort: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
    	dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],

    	buttonText: {

    		prev: '&nbsp;&#9668;&nbsp;',

    		next: '&nbsp;&#9658;&nbsp;',

    		prevYear: '&nbsp;&lt;&lt;&nbsp;',

    		nextYear: '&nbsp;&gt;&gt;&nbsp;',

    		today: '',

    		month: 'month',

    		week: 'week',

    		day: 'day'

    	},

    	selectable: true,

    	selectHelper: true,

    	select: function(start, end, allDay) {

    		window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_self");

    	},

    	editable: true,

    	eventClick: function(calEvent, jsEvent, view) {

    		window.open("/portal/portal/Schedulev6Cmd.cmd?method=queryPage","_self");

    	},

    	events: function(start, end, callback) {

    		$.getJSON("Schedulev6Cmd.cmd?method=mainSchedule&date="+parseInt(Math.random()*10000), {START: 



$.fullCalendar.formatDate(start,"yyyy-MM-dd"), END: $.fullCalendar.formatDate(end,"yyyy-MM-dd")

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

    	

    }); 





 $(function(){

$("#juanyan").click();

});

    $("#juanyan").click(function(){

	  $(".tab10").removeClass("active");

		$(this).addClass("active");

		// document.getElementById("zhibiaoiframe").src=""

	})

		$("#anjian").click(function(){

		$(".tab10").removeClass("active");

		$(this).addClass("active");

		// document.getElementById("zhibiaoiframe").src=""

	})

	 	$("#anzhi").click(function(){

		 $(".tab10").removeClass("active");

		$(this).addClass("active");

		// document.getElementById("zhibiaoiframe").src=""

	})

});


// 个人网盘

function initPersonalDisk(){
// alert("11111");
    var userId=$("#userId").val();
// alert(userId);
	$.ajax({
		 
		  type: "post",
       	 data: "userId="+userId.toUpperCase(),      
		  url: "/doc/index.php?r=/Doccenterapi/getDiskByUid",
		  beforeSend: function(XMLHttpRequest){
		// alert("2222"); <div class="SkyDrive" ><div class="all-space"
		// style="width: 200px; height: 16px; margin-top: 10px; margin-left:
		// 10px;"><div class="use-space"
		// style="margin:;height:16px;width:20%;background-color:#6ED9AF"></div></div><div
		// class="spacenum" style="width: 100px; height: 18px; margin-left:
		// 30px; margin-top: 5px;float:left;">400M/1G</div><div class="diskin"
		// style="width: 50px; height: 18px; margin-top: 5px; margin-left: 10px;
		// float: left;"><span> 进入网盘</span></div></div>
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval("("+data+")");
			var len = jsonObj.length;
			 for(var i=0;i<len;i++){
                    if(jsonObj[i].owner_id==userId){
                    	  $(".SkyDrive").append("<div class=\"all-space\"  style=\"width: 178px; height: 12px; margin-top: 10px; margin-left: 20px;\">"
                    			  +"<div id=\"forword\"></div>"
                    			  +"<div id=\"use-space\" style=\"float:left;height:12px;background-color:#6ED9AF\"></div>"
                    			  +"<div id=\"after\"></div>"
                    			  +"</div>"
                    			  +"<div  class=\"spacenum\" style=\"width: 120px; height: 18px; margin-left: 10px; margin-top: 5px;float:left;font-family: Microsoft Yahei; \"></div>"
                    			  +"<div  class=\"diskin\" style=\"width: 60px; height: 18px; margin-top: 5px; margin-left: 25px; float: left;font-family: Microsoft Yahei; \">"
                    			  +"<span> 个人网盘</span></div>");
                    	  var num=GetPercent(jsonObj[i].disk_use_space, jsonObj[i].disk_max_space);
                    	  document.getElementById('use-space').style.width=num;
                    		$("<span></span>").text(jsonObj[i].use_space+"/200MB").appendTo(".spacenum");
                    }else{
                    	return;
		  }
			 }
			 },

		  complete: function(XMLHttpRequest, textStatus){
		  },

		  error: function(){
		  }

	  });

}

function GetPercent(num, total) { 
	num = parseFloat(num); 
	total = parseFloat(total); 
	if (isNaN(num) || isNaN(total)) { 
	return "-"; 
	} 
	return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00 + "%"); 
	} 

// 简报
function initTobaccoReport(){
		$.ajax({

		  type: "post",

		  url: "cityReport.cmd?method=dataDisplay",

		  beforeSend: function(XMLHttpRequest){

			  

		  },

		  success: function(data, textStatus){
                  
			  $("#marqu").html(data);

              if(data!="<span style=\"color: #FF0000\">暂无数据！</span>"){

              var datas = data.replaceAll("<span style=\"color: #FF0000;font-weight:bold;\">","<span style=\"color: #429A73;\">");

              $("#datas").val(datas);
              }
                 
		  },

		  complete: function(XMLHttpRequest, textStatus){

			  // /$('#slider').nivoSlider();

		  },

		  error: function(){

			

		  }

	  });
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  

	   if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  

	       return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  

	   } else {  

	       return this.replace(reallyDo, replaceWith);  

	   }  

	}

/* 常用功能 */
function initCommT(){
	var html='';
	$.ajax({
		  type: "post",
		  url: "cityCmd.cmd?method=commSystem",
		  beforeSend: function(XMLHttpRequest){
			  $("#CommSys-list").html("");
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval(data);
			  var len = jsonObj.length;
			  if(jsonObj.length==0) return;
			 $.each(jsonObj,function(i){
				 	if(i>8)
				 	return;
				 	var n=i+1;
				 	if(n>7){
				 		n=i-6;
				 		}
				var arr = new Array();
				arr = jsonObj[i].MENU_URL.split("&");
				if(arr.length==3){
					var result = arr[1].split("=");
					jsonObj[i].MENU_URL =result[1];
				}else{
					jsonObj[i].MENU_URL = "noapp";
				}	
				
				 $("<li></li>")
				 .click(function(){
					var href="/portal/portal/appCenterInitCmd.cmd?method=queryPageInit&appType=mymenu&menuId="+jsonObj[i].MENU_ID+"&appSSO="+jsonObj[i].APP_TYPE;
					window.open(href,"_blank");
				 })
				 .append(
				 $("<a></a>")
				 .append(
				 $("<div class=\"CommSys-img\"></div>").append(
						 $("<img />")
					  .attr({"src":"/portal/skin/img/sd/"+jsonObj[i].MENU_URL+".png"})
					  )
				 )
				 .append(
					$("<span></span>") .text(jsonObj[i].MENU_NAME).attr("title",jsonObj[i].MENU_NAME)
				 )
				 )
				 .appendTo("#CommSys-list");
			 });
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}
/* 常用系统 */
function initCommS(){
	
	var html='';
	$.ajax({
		  type: "post",
		  url: "cityCmd.cmd?method=commS",
		  beforeSend: function(XMLHttpRequest){
			  $("#toolslist").html("");
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval(data);
				var len = jsonObj.length;
			
				 for(var i=0;i<len;i++){
	                 var ii=i+1;
	                
	                 var width=155*i+58;
	                    	  $("<li></li>")
	                    	  .append("<a  target=_blank href=\"/portal/AuthenService?USERID=" +  jsonObj[i].USER_ID+ "&APP=" +  jsonObj[i].APP_CODE + "&RESOURCE=" + jsonObj[i].NOTE+"\"><div style=\"width:140px; height:72px;padding-top:3px;\"><div id=\"toolimg"+ jsonObj[i].APP_CODE+"\"></div><span  style=\"left: "+width+"px; top: 15px; width: 92px; height: 72px; text-align: left; color: #007457; line-height: 72px; letter-spacing: 2px; font-family: Microsoft Yahei; font-size: 14px; font-weight: bold; position: absolute;\">"+jsonObj[i].APP_NAME+"</span></div></a>"
	                    	  ).appendTo("#toolslist");
	                    													
//	 						 $("<img />")
//	    					  .attr({"src":"/portal/skin/img/sd/topico01.png)"})
//	    					  .appendTo("#toolimg"+i);
				 }
				 inittoolsforco(len);
				 },
//			  var len = jsonObj.length;
//			  if(jsonObj.length==0) return;
//			 $.each(jsonObj,function(i){
//				 	if(i>8)
//				 	return;
//				 	var n=i+1;
//				 	if(n>7){
//				 		n=i-6;
//				 	}
//				 $("<li></li>")
//				 .click(function(){
//					var href="/portal/AuthenService?USERID=" +  jsonObj[i].USER_ID+ "&APP=" +  jsonObj[i].APP_CODE + "&RESOURCE=" + jsonObj[i].NOTE;
//					window.open(href,"_blank");
//				 })
//				 .append(
//				 $("<a></a>")
//				 .append(
//				 $("<div class=\"CommSys-img\"></div>").append(
//						 $("<img />")
//					  .attr({"src":"/portal/skin/img/sd/"+ jsonObj[i].APP_CODE+".png"})
//					  )
//				 )
//				 .append(
//					$("<span></span>") .text(jsonObj[i].APP_NAME).attr("title",jsonObj[i].APP_NAME)
//				 )
//				 )
//				 .appendTo("#CommSys-list");
//			 });
//		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}

/* 常用系统 */
function  inittoolsforco(len){

	var html='';
	$.ajax({
		  type: "post",
		  url: "cityCmd.cmd?method=SysTools",
		  beforeSend: function(XMLHttpRequest){
			 
		  },
		  success: function(data, textStatus){
			  var jsonObj=eval(data);
				var len1 = jsonObj.length;
				 for(var i=0;i<len1;i++){
	             
	                 var width=155*(len+i)+55;
	              
	                    	  $("<li></li>")
	                    	  .append("<div style=\"width:140px; height:72px;padding-top:3px;\"><div id=\"toolimgsys"+ jsonObj[i].APP_CODE+"\"></div><span  style=\"left: "+width+"px; top: 15px; width: 92px; height: 72px; text-align: left; color: #979797; line-height: 72px; letter-spacing: 2px; font-family: Microsoft Yahei; font-size: 14px; font-weight: bold; position: absolute;\">"+jsonObj[i].APP_NAME+"</span></div>"
	                    	  ).appendTo("#toolslist");
	                    													
//	 						 $("<img />")
//	    					  .attr({"src":"/portal/skin/img/sd/topico01.png)"})
//	    					  .appendTo("#toolimg"+i);
				 }
				
				 },

		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(){			
		  }
	  });
}

  $(function(){

             var oPic=$('#slider_pic').find('ul');

             var oImg=oPic.find('li');

             var oLen=oImg.length+1;

             var oLi=oImg.width();

             var prev=$("#prev");

             var next=$("#next");



           oPic.width(oLen*254);// 计算总长度

             var iNow=0;

             var iTimer=null;

             prev.click(function(){

                  if(iNow>0){  

                   iNow--;



                  }

                 ClickScroll();

             })

             next.click(function(){

                 if(iNow<oLen-4){ 

                     iNow++

                 }

                 ClickScroll();

             })



             function ClickScroll(){



                 iNow==0? prev.addClass('no_click'): prev.removeClass('no_click');

                 iNow==oLen-3?next.addClass("no_click"):next.removeClass("no_click");



                 oPic.animate({left:-iNow*104})

             }



         })
 function toDolist(){
	  var userId=$("#userId").val(); 
    $.ajax({
    	// type: "post",
        url:"cityCmd.cmd?method=uniformTodo&userId="+userId.toUpperCase(),		
        dataType:'html',
        beforeSend: function(XMLHttpRequest){

        },
        success: function(data, textStatus){
        	if(data!=null||data!=""){
        	var jsonObj = eval('(' + data + ')'); 
			 	// $("<span></span>").text(jsonObj.records).appendTo(".daibannum");

			 for(var i=0;i<6;i++){

			 	if(jsonObj.todolist[i]==('undefined')||jsonObj.todolist[i]==null||jsonObj.todolist[i].subject==null||jsonObj.todolist[i].subject.length==0){}else{

			 		

			 		$("<li></li>").append(

					$("<a></a>").attr("href",daiban_uri1 + user.toUpperCase()
							+ daiban_uri2+encodeURIComponent(jsonObj.todolist[i].relative_path)).text(jsonObj.todolist[i].subject)

								.attr("target","_blank")

								.attr("title",jsonObj.todolist[i].subject)

				 ).append(

				 

					$("<span></span>").text(jsonObj.todolist[i].create_time.substr(4,2)+'-'+jsonObj.todolist[i].create_time.substr(6,8))

				)

				 .appendTo("#db-list");

			 		 $("<p class=\"laiyuan\"></p>").text(jsonObj.todolist[i].app_name) 

			 		.appendTo("#db-list li"); 

				    $("#db-list li:even").addClass("tbgeven"); 

			  $("#db-list li:odd").addClass("tbgodd"); 

				}

			 }  
			 
			 }else{
				 jsonObj.records=0;
			 }
        	bpmTodo(jsonObj.records);
         },
        complete: function(XMLHttpRequest, textStatus){  
        },
        error: function (xhr, ajaxOptions, thrownError) {
          
        }
      });
  } 
 
	 function bpmTodo(num){
		 var userId=$("#userId").val(); 
			$.ajax({

				  type: "post",
                  dataType:'html',
				  url: "cityCmd.cmd?method=bpmTodo&userId="+userId.toUpperCase(),

				  beforeSend: function(XMLHttpRequest){

					  

				  },

				  success: function(data, textStatus){
          if(data!=null&&data!=""){
					 var jsonObj=eval('('+data+')');
              // alert("if---jsonObj.length-->"+jsonObj.records);
               
					 var len1 = jsonObj.records+num;

					 /*	$("<span></span>").text(len)

					.appendTo(".daibannum");*/
                     if(num<6){
					 for(var i=0;i<6-num;i++){

						 	if(jsonObj.todolist[i]==('undefined')||jsonObj.todolist[i]==null||jsonObj.todolist[i].subject==null||jsonObj.todolist[i].subject.length==0){}else{

						 		

						 		$("<li></li>").append(

								$("<a></a>").attr("href",jsonObj.todolist[i].relative_path).text(jsonObj.todolist[i].subject)

											.attr("target","_blank")

											.attr("title",jsonObj.todolist[i].subject)

							 ).append(

							 

								$("<span></span>").text(jsonObj.todolist[i].create_time)

							)

							 .appendTo("#db-list");

						 		 $("<p class=\"laiyuan\"></p>").text("入职") 

						 		.appendTo("#db-list li"); 

							    $("#db-list li:even").addClass("tbgeven"); 

						  $("#db-list li:odd").addClass("tbgodd"); 

							}

						 }
                     }
                     }
v6Todo(len1);
				  },

				  complete: function(XMLHttpRequest, textStatus){

					  // /$('#slider').nivoSlider();

				  },

				  error: function(){

					

				  }

			  });
			
		}
	 
	 function v6Todo(len1){
	 
		 var userId=$("#userId").val(); 
			$.ajax({

				  type: "post",
                  dataType:'html',
				  url: "cityCmd.cmd?method=V6Todo&userId="+userId.toUpperCase(),

				  beforeSend: function(XMLHttpRequest){

					  

				  },

				  success: function(data, textStatus){
          if(data!=null&&data!=""){
					 var jsonObj=eval('('+data+')');
					 //	alert("aaa"+len1);
					 var len = jsonObj.length+len1;

					 	$("<span></span>").text(len)

					.appendTo(".daibannum");
                     if(len1<6){
					 for(var i=0;i<6-len1;i++){

						 	if(jsonObj[i]==('undefined')||jsonObj[i]==null||jsonObj[i].subject==null||jsonObj[i].subject.length==0){}else{

						 		

						 		$("<li></li>").append(

								$("<a></a>").attr("href",getRootPath()+"/bpm/command/dispatcher/org.loushang.workflow.tasklist.forward.TaskListDispatcherCmd/daiBanTaskForward?assignmentId="+jsonObj[i].id1).text(jsonObj[i].subject)

											.attr("target","_blank")

											.attr("title",jsonObj[i].subject)

							 ).append(

							 

								$("<span></span>").text(jsonObj[i].createTime.substring(4,6)+'-'+jsonObj[i].createTime.substring(6,8))

							)

							 .appendTo("#db-list");

						 		 $("<p class=\"laiyuan\"></p>").text("营销") 

						 		.appendTo("#db-list li"); 

							    $("#db-list li:even").addClass("tbgeven"); 

						  $("#db-list li:odd").addClass("tbgodd"); 

							}

						 }
                     }
                     }else{
                    	 $("<span></span>").text(num)

     					.appendTo(".daibannum");
                     }

				  },

				  complete: function(XMLHttpRequest, textStatus){

					  // /$('#slider').nivoSlider();

				  },

				  error: function(){

					

				  }

			  });

		}
	 
	 function getRootPath(){
		 var strFullPath=window.document.location.href;
		 var strPath=window.document.location.pathname;
		 var pos=strFullPath.indexOf(strPath);
		 var prePath=strFullPath.substring(0,pos);
		 //var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);

		 return(prePath);
		 }
  // 公告通知

    function getNotifierDatas(){

    	 var userId=$("#userId").val(); 

    	$.ajax({

    		  type: "post",

    		  url: "cityCmd.cmd?method=uniformNews&userId="+userId.toUpperCase(),

    		  beforeSend: function(XMLHttpRequest){

    			  

    		  },

    		  success: function(data, textStatus){

    			 var jsonObj=data;
    			 var len = jsonObj.length;
    			 

    			 for(var i=0;i<len;i++){

    			 var str=jsonObj[i].title;

    			 

    			 var l = str.length;

    			

           if(l>30)

           {

           	str=str.substring(0,28)+"...";   // 是截取后的字符串

           }  

          

    		 	 if(jsonObj[i].title.length==0){}

    			 	else{

    				 $("<li></li>")

    				 .append(

    						 $("<span></span>").text(jsonObj[i].type+"      "+jsonObj[i].time.substring(0,10))

    				 )

    				 .append(

    					$("<a></a>").attr("href",jsonObj[i].path).text(str)

    								.attr("target","_blank")

    								.attr("title",jsonObj[i].title)

    				 )

    		

    				 .appendTo("#notice-list");

    				}

    			 }



    		  },

    		  complete: function(XMLHttpRequest, textStatus){

    			  // /$('#slider').nivoSlider();

    		  },

    		  error: function(){

    			

    		  }

    	  });

    }
    
    
    
    function getMail(){
    	var userId=$("#userId").val(); 
  $.ajax({
      type: "post",
      dataType: "text",
      data: "userId="+userId.toUpperCase(),
      url:"cityCmd.cmd?method=getMail",
      beforeSend: function(XMLHttpRequest){
      // $("#unreadmai").hide();
      },
      success: function(data, textStatus){
       if(data!=null&&data!=""){
       var jsonObj=eval("("+data+")");
       //alert(jsonObj.length);
       var len = jsonObj.length;
       
    

      for(var i=0;i<len;i++){

          num= jsonObj[i].newmsgcnt;

		  	if(num.length==1){
				$("<span></span>").attr("style","margin-left:3px").text(num)
				.appendTo(".mailnum");
			}else{
				$("<span></span>").text(num)
				.appendTo(".mailnum");
			}

         $("#mailAddress").attr("href",jsonObj[i].url).attr("target","_blank")

             .appendTo(".panels-right");
              $("#mailA").attr("href",jsonObj[i].url).attr("target","_blank")

        }
       }else{
    	   $("<span></span>").text(0)

    	      .appendTo(".mailnum"); 
       }
       }
        });
    }

    function openTongZhi(i){
    	var userId=$("#userId").val(); 
    $.ajax({
          url:"cityCmd.cmd?method=uniformNews&userId="+userId.toUpperCase(),
          dataType: "json",
          beforeSend: function(XMLHttpRequest){

          },
          success: function(data, textStatus){
              window.open(data[i].path,'_blank');

           },
          complete: function(XMLHttpRequest, textStatus){
            
          },
          error: function (xhr, ajaxOptions, thrownError) {

          }
        });

  }
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

						if(obj[i].title.length>14){

							obj[i].title = obj[i].title.substr(0,14)+"...";

						}

						$("<li></li>").append(

							$("<span></span>").text("["+obj[i].start.substr(11,5)+"]")

						).append(

							$("<div></div>").append($("<a></a>").text(obj[i].title))

						).appendTo("#shedule ul");

					});

			  },

			  complete: function(XMLHttpRequest, textStatus){

			  },

			  error: function(){			

			  }

		  });

	}
	
	
	// 网盘

function initAllDisk(){
// alert("11111");
    var userId=$("#userId").val();
     var depId=$("#deptId").val();
      var comId=$("#comId").val();
// alert(userId);
	$.ajax({
		 
		  type: "post",
       	 data: "userId="+userId.toUpperCase(),      
		  url: "/doc/index.php?r=/Doccenterapi/getDiskByUid",
		  beforeSend: function(XMLHttpRequest){
		// alert("2222"); <div class="SkyDrive" ><div class="all-space"
		// style="width: 200px; height: 16px; margin-top: 10px; margin-left:
		// 10px;"><div class="use-space"
		// style="margin:;height:16px;width:20%;background-color:#6ED9AF"></div></div><div
		// class="spacenum" style="width: 100px; height: 18px; margin-left:
		// 30px; margin-top: 5px;float:left;">400M/1G</div><div class="diskin"
		// style="width: 50px; height: 18px; margin-top: 5px; margin-left: 10px;
		// float: left;"><span> 进入网盘</span></div></div>
		  },
		  success: function(data, textStatus){
//	alert(data);
			var jsonObj=eval("("+data+")");
			var len = jsonObj.length;
			 for(var i=0;i<len;i++){
                 var ii=i+1;
             
                 if(jsonObj[i].owner_id!=1){
                    	  $("<li></li>")
                    	  .append("<a href=\"/doc/index.php?r=/home#list|"+jsonObj[i].disk_id+"\"><div class=\"diskleft\" style=\"height:65px;float:left;width:39px;margin-left:10px;\"></div><div class=\"diskright\" style=\"height:65px;float:left;\"><div class=\"owner_name"+i+"\"   style=\"width: 178px; height: 12px; margin-top: 10px; margin-left: 20px;\"></div>"
                    	      +"<div class=\"all-space\"  style=\"width: 178px; height: 12px; margin-top: 10px; margin-left: 20px;\">"
                    			  +"<div id=\"forword\"></div>"
                    			  +"<div id=\"use-space"+i+"\" style=\"float:left;height:12px;background-color:#6ED9AF\"></div>"
                    			  +"<div id=\"after\"></div>"
                    			  +"</div>"
                    			  +"<div  class=\"spacenum"+i+"\" style=\"width: 120px; height: 18px; margin-left: 30px; margin-top: 5px;float:left;font-family: Microsoft Yahei; \"></div>"

                    			  +"</div></a>").appendTo(".link-list");
                    			//	$("<span></span>").text(jsonObj[i].owner_name).appendTo(".link-list");
                    			$("<span></span>").text(jsonObj[i].name).appendTo(".owner_name"+i);
                    	  var num=GetPercent(jsonObj[i].disk_use_space, jsonObj[i].disk_max_space);
                    	  document.getElementById('use-space'+i).style.width=num;
                    		$("<span></span>").text(jsonObj[i].use_space+"/"+jsonObj[i].max_space).appendTo(".spacenum"+i);
			 } 
			 }
			 },

		  complete: function(XMLHttpRequest, textStatus){
		  },

		  error: function(){
		  }

	  });

}


$(function(){

             var oPic=$('#slider_pic1').find('ul');

             var oImg=oPic.find('li');

             var oLen=oImg.length-1;

             var oLi=oImg.width();

             var prev=$("#prev1");

             var next=$("#next1");



           oPic.width(oLen*204);// 计算总长度

             var iNow=0;

             var iTimer=null;

             prev.click(function(){

                  if(iNow>0){  

                   iNow--;



                  }

                 ClickScroll();

             })

             next.click(function(){

                 if(iNow<oLen-4){ 

                     iNow++

                 }

                 ClickScroll();

             })



             function ClickScroll(){



                 iNow==0? prev.addClass('no_click1'): prev.removeClass('no_click1');

                 iNow==oLen-3?next.addClass("no_click1"):next.removeClass("no_click1");



                 oPic.animate({left:-iNow*204})

             }



         })