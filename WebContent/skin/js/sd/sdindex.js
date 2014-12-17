$(document).ready(function()  {

 initDaiYue();

 initDaiBan(); 

 initNotice(); 

 initPersonalDisk();

 initMail();

 initTobaccoReport();

 querySchList();

	/*var host = document.domain; 

	//alert(host);

	if(host !="ta-tec.sd-tobacco.com.cn"){

		//ta-tec.sd-tobacco.com.cn

		$(".head-right").empty();

	}else{

		$(".head-right").removeAttr("background")

	}

 */

 $(".zhibiaocentertab").click(function(){

		$(".zhibiaocentertab1").removeClass("active");

		$(".zhibiaocentertab2").removeClass("active");

		$(".zhibiaocentertab3").removeClass("active");

		$(this).addClass("active");

	});

$(".zhibiaocentertab1").click(function(){

        $(".zhibiaocentertab").removeClass("active");

		$(".zhibiaocentertab2").removeClass("active");

		$(".zhibiaocentertab3").removeClass("active");

		$(this).addClass("active");

	});

$(".zhibiaocentertab2").click(function(){

		 $(".zhibiaocentertab1").removeClass("active");

		 $(".zhibiaocentertab").removeClass("active");

		 $(".zhibiaocentertab3").removeClass("active");

		 $(this).addClass("active");

	});

$(".zhibiaocentertab3").click(function(){

		 $(".zhibiaocentertab").removeClass("active");

		 $(".zhibiaocentertab2").removeClass("active");

		 $(".zhibiaocentertab1").removeClass("active");

		$(this).addClass("active");

	});

$(function(){

   $(".toolstab").click();

//    $(".sysstab").click();

  });

$(".sysstab").click(function(){

$(".toolstab").removeClass("active");

$(this).addClass("active");

initCommT();

});

$(".toolstab ").click(function(){

$(".sysstab").removeClass("active");

$(this).addClass("active");

initCommS();

});

//$(".toolstab").click(function(){

//	 $(".sysstab").removeClass("active");

//	 $(this).addClass("active");

//	 initCommT();

//});

//$(".sysstab").click(function(){

//	 $(".toolstab").removeClass("active");

//	$(this).addClass("active");

//	 initCommS();

//});

//门户日程

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

    	//dayNamesShort: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],

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

	});



	$("#anjian").click(function(){

		$(".tab10").removeClass("active");

		$(this).addClass("active");

	});



	 $("#anzhi").click(function(){

		 $(".tab10").removeClass("active");

		 $(this).addClass("active");

	});



});



//待阅



function initDaiYue(){

	$.ajax({

		  type: "post",

		  url: "pwssdsj.cmd?method=queryDaiYue",

		  beforeSend: function(XMLHttpRequest){

		  },

		  success: function(data, textStatus){



			 var jsonObj=eval("("+data+")");



				 //$("#firstNewTitle").attr("href",jsonObj[0].newUrl).text(jsonObj[0].newTitle);



			 //$("#firstNewContent").attr("href",jsonObj[0].newUrl).html("<p>"+jsonObj[0].firstNewContent.substr(0,85)+"..."+"</p>");



			 //$("#firstNewContent").attr("target","_blank");



			 //$("#firstNewTitle").attr("target","_blank");



			 var len = jsonObj.length;



			 	$("<span></span>").text(len)



			.appendTo(".daiyuenum");



			 for(var i=0;i<6;i++){

				if(jsonObj==null || typeof(jsonObj)=="undefined" ||jsonObj[i]==null || typeof(jsonObj[i])=="undefined" ||jsonObj[i].subject==null || typeof(jsonObj[i].subject) == "undefined"){

					return ;

				}else{

					if(jsonObj[i].subject.length==0){}else{

						$("<li></li>")

					 .append(

						$("<a></a>").attr("href",jsonObj[i].relativePath).text(jsonObj[i].subject)

									.attr("target","_blank")

									.attr("title",jsonObj[i].subject)

					 )

					 .append(

							 $("<span></span>").text(jsonObj[i].createTime)

					)

					 .appendTo("#dy-list");

						$("<p class=\"laiyuan\"></p>").text(jsonObj[i].appCode) 

						.appendTo("#dy-list li");

					   $("#dy-list li:even").addClass("tbgeven"); 

					   $("#dy-list li:odd").addClass("tbgodd");

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



//待办



function initDaiBan(){







	$.ajax({



		  type: "post",



		  url: "pwssdsj.cmd?method=queryDaiBan",



		  beforeSend: function(XMLHttpRequest){



			  



		  },



		  success: function(data, textStatus){



		  	



			 var jsonObj=eval("("+data+")");



				 //$("#firstNewTitle").attr("href",jsonObj[0].newUrl).text(jsonObj[0].newTitle);



			 //$("#firstNewContent").attr("href",jsonObj[0].newUrl).html("<p>"+jsonObj[0].firstNewContent.substr(0,85)+"..."+"</p>");



			 //$("#firstNewContent").attr("target","_blank");



			 //$("#firstNewTitle").attr("target","_blank");



			 var len = jsonObj.length;



			 	$("<span></span>").text(len)



			.appendTo(".daibannum");

			 for(var i=0;i<6;i++){

				if(jsonObj==null || typeof(jsonObj)=="undefined" ||jsonObj[i]==null || typeof(jsonObj[i])=="undefined" ||jsonObj[i].subject==null || typeof(jsonObj[i].subject) == "undefined"){

					return ;

				}else{

					if(jsonObj[i].subject.length==0){

						return;

					}else{

						$("<li></li>")

					 .append(

						$("<a></a>").attr("href",jsonObj[i].relativePath).text(jsonObj[i].subject)

									.attr("target","_blank")

									.attr("title",jsonObj[i].subject)

					 )

					 .append(

						$("<span></span>").text(jsonObj[i].createTime)

					)

					 .appendTo("#db-list");

						$("<p class=\"laiyuan\"></p>").text(jsonObj[i].appCode) 

						.appendTo("#db-list li");

					   $("#db-list li:even").addClass("tbgeven"); 

						$("#db-list li:odd").addClass("tbgodd");

					}

				}

			 }

		  },



		  complete: function(XMLHttpRequest, textStatus){



			  ///$('#slider').nivoSlider();



		  },



		  error: function(){



			



		  }



	  });



}



/*常用功能*/

function initCommT(){

	var html='';

	$.ajax({

		  type: "post",

		  url: "pwssdsj.cmd?method=commSystem",

		  beforeSend: function(XMLHttpRequest){

			  $("#CommSys-list").html("");

		  },

		  success: function(data, textStatus){

			  var jsonObj=eval(data);

			  var len = jsonObj.length;

			  if(jsonObj.length==0) return;

			

			 $.each(jsonObj,function(i){

				if(i>8){

					return;

				}

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

					window.open(href,"_self");

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

/*常用系统*/

function initCommS(){

	var html='';

	$.ajax({

		  type: "post",

		  url: "pwssdsj.cmd?method=commS",

		  beforeSend: function(XMLHttpRequest){

			  $("#CommSys-list").html("");

		  },

		  success: function(data, textStatus){

			  var jsonObj=eval("("+data+")");

			  var len = jsonObj.length;

			  if(jsonObj.length==0) return;

			 $.each(jsonObj,function(i){

				 	if(i>8)

				 	return;

				 	var n=i+1;

				 	if(n>7){

				 		n=i-6;

				 		}

				 $("<li></li>")

				 .click(function(){

					var href="/portal/AuthenService?USERID=" +  jsonObj[i].USER_ID+ "&APP=" +  jsonObj[i].APP_CODE + "&RESOURCE=" + jsonObj[i].NOTE;

					window.open(href,"_blank");

				 })

				 .append(

				 $("<a></a>")

				 .append(

				 $("<div class=\"CommSys-img\"></div>").append(

						 $("<img />")

					  .attr({"src":"/portal/skin/img/sd/"+jsonObj[i].APP_CODE+".png"})

					  )

				 )

				 .append(

					$("<span></span>") .text(jsonObj[i].APP_NAME).attr("title",jsonObj[i].APP_NAME)

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

//简报

function initTobaccoReport(){

		$.ajax({



		  type: "post",



		  url: "tobaccoReport.cmd?method=dataDisplay",



		  beforeSend: function(XMLHttpRequest){



			  



		  },



		  success: function(data, textStatus){



                 $("#marqu").html(data);

                 if(data!="<span style=\"color: #FF0000\">暂无数据！</span>"){

                 var datas = data.replaceAll("<span style=\"color: #FF0000;font-weight:bold;\">","<span style=\"color: #429A73;\">");

                // datas = datas.replaceAll("&nbsp","&nbsp&nbsp&nbsp");

                 /*datas = datas.replace("<br>","");

                 datas = datas.replaceAll(",","");

                 datas = datas.replaceAll(" ","");*/

                //alert(datas);

                 $("#datas").val(datas);

                 }

                 

		  },



		  complete: function(XMLHttpRequest, textStatus){



			  ///$('#slider').nivoSlider();



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

//公告通知



function initNotice(){



	



	$.ajax({



		  type: "post",



		  url: "pwssdsj.cmd?method=queryNotice",



		  beforeSend: function(XMLHttpRequest){



			  



		  },



		  success: function(data, textStatus){



			 var jsonObj=eval("("+data+")");



			 var len = jsonObj.length;



			 for(var i=0;i<len;i++){



			 var str=jsonObj[i].title;



			 



			 var l = str.length;



			



       if(l>29)



       {



       	str=str.substring(0,29)+"......";   //是截取后的字符串  



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



			  ///$('#slider').nivoSlider();



		  },



		  error: function(){



			



		  }



	  });



}







//邮箱



function initMail(){

	$.ajax({

		  type: "post",

		  url: "pwssdsj.cmd?method=getMail",

		  beforeSend: function(XMLHttpRequest){

		  },

		  success: function(data, textStatus){

			 var jsonObj=eval("("+data+")");

			 var len = jsonObj.length;

      for(var i=0;i<len;i++){

    	    num= jsonObj[i].newmsgcnt;

			if(num.length==1){

				$("<span></span>").attr("style","margin-left:2px").text(num)

				.appendTo(".mailnum");

			}else{

				$("<span></span>").text(num)

				.appendTo(".mailnum");

			}

		    $("#mailAddress").attr("href",jsonObj[i].url).attr("target","_blank")

			 .appendTo(".panels-right");

			$("#mailA").attr("href",jsonObj[i].url).attr("target","_blank");

				}

		  },



		  complete: function(XMLHttpRequest, textStatus){

			  ///$('#slider').nivoSlider();

		  },



		  error: function(){





		  }



	  });



}



//个人网盘



function initPersonalDisk(){

//alert("11111");	

    var userId=$("#userId").val();

//    var userId="ZHANGXINGWEN_JN";

//   alert(userId);

	$.ajax({

		 

		  type: "post",

       	 data: "userId="+userId.toUpperCase(),      

		  url: "/doc/index.php?r=/Doccenterapi/getDiskByUid",

		  beforeSend: function(XMLHttpRequest){

		//  alert("2222"); <div class="SkyDrive"  ><div class="all-space"  style="width: 200px; height: 16px; margin-top: 10px; margin-left: 10px;"><div class="use-space" style="margin:;height:16px;width:20%;background-color:#6ED9AF"></div></div><div  class="spacenum" style="width: 100px; height: 18px; margin-left: 30px; margin-top: 5px;float:left;">400M/1G</div><div  class="diskin" style="width: 50px; height: 18px; margin-top: 5px; margin-left: 10px; float: left;"><span> 进入网盘</span></div></div>	

		  },

		  success: function(data, textStatus){

//			  alert(data);

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

                    		$("<span></span>").text(jsonObj[i].use_space+"/"+jsonObj[i].max_space).appendTo(".spacenum");

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







  $(function(){

             var oPic=$('#slider_pic').find('ul');

             var oImg=oPic.find('li');

             var oLen=oImg.length+1;

             var oLi=oImg.width();

             var prev=$("#prev");

             var next=$("#next");

             oPic.width(oLen*204);//计算总长度

             var iNow=0;

             var iTimer=null;

             prev.click(function(){

                  if(iNow>0){  

                   iNow--;

                  }

                 ClickScroll();

             });

             next.click(function(){

                 if(iNow<oLen-4){ 

                     iNow++;

                 }

                 ClickScroll();

             });







             function ClickScroll(){

                 iNow==0? prev.addClass('no_click'): prev.removeClass('no_click');

                 iNow==oLen-3?next.addClass("no_click"):next.removeClass("no_click");

                 oPic.animate({left:-iNow*104});



             }

         });

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