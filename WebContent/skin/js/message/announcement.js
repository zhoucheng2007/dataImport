$(document).ready(function(){
    $.ajax({
		  type: "post",
		  url:"ann_show_cmd.cmd?method=queryAnnSize",
		  dataType:"text",
		  beforeSend: function(XMLHttpRequest){		  				  						 
		  },
		  success: function(data, textStatus){		 
			  if((!isNaN(data))&&data>0){			    	
			     //组织dom结构
					 var body=$("body"); 
					 //遮罩div
					 var annShareAllScreen = document.createElement('div');
					 annShareAllScreen.id = 'annShareAllScreen';
					//容器div
					 var announcementContent = document.createElement('div');
					 announcementContent.id = 'announcementContent';
					 var announcementHead = document.createElement('div');
					 announcementHead.id='announcementHead';
					 var announcementBody = document.createElement('div');
					 announcementBody.id='announcementBody';
					 var announcementFoot = document.createElement('div');
					 announcementFoot.id='announcementFoot';
//					 $(announcementContent).append(announcementHead);
//					 $(announcementContent).append(announcementBody);
//					 $(announcementContent).append(announcementFoot);
					 
					 var iframeing="<iframe src='ann_show_cmd.cmd?method=queryAnn' width=\"900px\" height=\"450px\" scrolling=\"no\"border=\"0\" frameborder=\"0\" allowtransparency=\"true\"></iframe>";
					 $(announcementContent).append(iframeing);
					 
					 $(annShareAllScreen).append(announcementContent);
					 body.append(annShareAllScreen); 
						 
					 //处理样式
					 var height=document.documentElement.clientHeight;
					 var width=document.documentElement.clientWidth;	
					 $(annShareAllScreen).css({
						  "height":height,
						  "width":width,
						  "display":"block"
					 });
					var contentLeft=(parseInt(width)-parseInt($(announcementContent).width()))/2;
					var contentTop=(parseInt(height)-parseInt($(announcementContent).height()))/2;

					$(announcementContent).css({
						"top":contentTop+"px",
						"left":contentLeft+"px"
						});
					$(announcementHead).html("<div class='announcementLab'></div><div class='annAnnouncement'>公司通告</div><div class='close'></div>");
					$(".close").click(function(){
						$("#annShareAllScreen").remove();
					});			    	
			    }			  			  			  
		  },
		  complete: function(XMLHttpRequest, textStatus){
		  },
		  error: function(xhr){
			  //jError("设置失败");
		  }
	  });
    

	
});

