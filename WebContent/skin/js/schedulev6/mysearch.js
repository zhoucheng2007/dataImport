function showinfo(id){
	//alert("能进入showinfo方法"+id);
	 // alert(document.getElementById("start").value);
  	$.ajax({
		  type: "post",
		  data:"ID="+id,
		  url: "Schedulev6Cmd.cmd?method=selectByID",
		  beforeSend: function(XMLHttpRequest){
			//alert("111");
		  },
		  success: function(data,textStatus){
	    //alert(data);
			var jsonObj='{"root":'+data+'}';//eval(data.result)
			var obj=eval("("+jsonObj+")"); 
      //alert(obj);
			var secret="";
			//var j=JSON.parse(data) ;
       //alert("访问数据库成功1"+obj.root[0].ID);
       if(obj.root[0].IS_PRIVATE=='1') secret="私密";
       //$('#dialog').append(
       document.getElementById("dialog").innerHTML="<div id='diatopTitle' style='top:155px;left:440px'>"+
    		"<div class='detailtitle'>"+
    			"<a href='javascript:;' class='dialogclose' onclick='remove();'></a>"+
    			"<span id='caltitle' class='checkit'>"+obj.root[0].TITLE+"("+secret+")</span>"+
    		"</div>"+
    		"<div class='detailtime'>"+
    			"<img class='left' src='/portal/skin/css/schedulev6/mycalendar/calTime.gif'/>"+
    			"<span class='left'>时间：</span>"+
    			"<span id='caldatecode'>"+obj.root[0].START+"</span>-<span id='caldatecode'>"+obj.root[0].END+"</span>"+
    		"</div>"+
    		"<div class='detailtime'>"+
    			"<img class='left' src='/portal/skin/css/schedulev6/mycalendar/calTime.gif'/>"+
    			"<span class='left'>发起人：</span>"+
    			"<span id='caldatecode'>"+obj.root[0].UID+"</span>"+
    		"</div>"+
    	  "<div class='detailplace'>"+
    			"<img class='left' src='/portal/skin/css/schedulev6/mycalendar/calPlace.gif'/>"+
    			"<span class='left'>地点：</span>"+
    			"<span id='calplace'>"+obj.root[0].PLACE+"</span>"+
    		"</div>"+
    		"<div class='detailman'>"+
    			"<img class='left' src='/portal/skin/css/schedulev6/mycalendar/calParticipants.gif'/>"+
    			"<span class='left'>参与人：</span>"+
    			"<span id='calman'>"+obj.root[0].GROUP+"</span>"+
    		"</div>"+
    		"<div class='detailexp'>"+
    			"<img class='left' src='/portal/skin/css/schedulev6/mycalendar/calExplanatory.gif'/>"+
    			"<span class='left'>摘要：</span>"+
    			"<span id='calexp'>"+obj.root[0].CONTENT+"</span>"+
    		"</div>	"+
		  "</div>"
		  //");
       $('#dialog').show();
			 
		  },
		  complete: function(XMLHttpRequest, textStatus){
			  //alert("ajax执行成功");
		  },
		  error: function(){
				alert("error");
		  }
	  });
  }
  function remove(){
  	document.getElementById("dialog").style.display="none";
  }
	