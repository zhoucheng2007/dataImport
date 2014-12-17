function pubEdit(methodName){
	this.func = new Function(methodName+"();");
	}

function pubChoice(methodName,data){
	this.func = new Function(methodName+"('"+data+"');");
	}

//弹出编辑框
function BJShowEditDialog(callBackOK,callBackCancle,title,message){
//	alert(callBackOK);
//	alert(callBackCancle);
    $("#mobile_edit_title").siblings().remove();
	   var returnVal=null;
	   $("#mobile_all").css("display","block");
	   $("#mobile_all").css("z-index",9999);
	   if(message==""){
			$("#mobile_edit_title").before('<div id="mobile_cancel_button">取消</div>');	
		} else{
			$("#mobile_edit_title").before('<div id="mobile_cancel_button">删除</div>');
		};
		$("#mobile_edit_title").before('<div id="mobile_sure_button">确定</div>');
		$("#mobile_edit_title").text(title);
		$("#mobile_edit_message").text(message);
		$("#mobile_sure_button").live('click',function(){
		    $("#mobile_all").css("display","none");
		    var obj =$("#mobile_edit_message");
		    returnVal=obj.val();
		    if(false){
		    	BJCallFun(callBackOK,returnVal);
			}else{
			    var frames= window.document.getElementById("pciframe"); 
			    frames.contentWindow.BJCallFun(callBackOK,returnVal);
		    }
//		    alert(returnVal);
		    $("#mobile_edit_title").siblings().remove();
		    //alert(returnVal);
		    //$(this)[callBackOK]
//		    this.callBackOK = new Function(callBackOK+"();");
		    
		    
//		    callBackOK();
//		    if(callBackOK) callBackOK();//点击确定的回调函数
//		    function callBackOK();
		    return returnVal;
		});

		
		$("#mobile_cancel_button").live('click',function(){
			if($("#mobile_cancel_button").text()=="取消"){
				$("#mobile_all").css("display","none");
				$("#mobile_edit_title").siblings().remove();
				 return null;
			}
			if($("#mobile_cancel_button").text()=="删除"){	
				$("#mobile_all").css("display","none");
				if(false){
			    	BJCallFun(callBackCancle,returnVal);
				}else{
					var frames= window.document.getElementById("pciframe"); 
				    frames.contentWindow.BJCallFun(callBackCancle,returnVal);
				    var obj =$("#mobile_edit_message");
				    obj.val("");
			    }
				
//			   if(callBackCancle) callBackCancle(); //点击删除的回调函数
			  // alert('调用删除的方法');
            if($("#mobile_edit_message").text()==""){
				$("#mobile_cancel_button").text('取消');
			   }
			}
			
		}); 			
}

//弹出选择框
function BJShowSingleChoiceDialog(array,title){
     //alert(navigator.userAgent);
//	   alert(array[0]);
	   $("#mobile_choise_content").children().remove();
	   $("#mobile_choise").css("display","block");
	   $("#mobile_choise").css("z-index",9999);
	   if(title==""||title=="undefined"){
		   title="请选择";
	   }
	   $("#mobile_choise_title").html(''); 
	   $("#mobile_choise_title").html(title); 

	   var callBackOK=array[0];
	 //  getArgs(array);
		 for(var i=1; i<array.length;i++){
				var pairs = array[i].split('#');
				//alert(pairs[0]);
				//alert(pairs[1]);
				//alert(pairs[2]);
				if(pairs[2]==0){
			     $tmpHTML='<div id=\"message_array'+i+'\"class=\"row_message\">'
	    	                  +'<span id=\"message_choise_text'+i+'\"class=\"message_choise_text\">'+pairs[1]+'</span>'
	    	                  +'<span class=\"message_choice_all\" style=\"display:none\">'+array[i]+'</span>'
	    	                  +'<span id=\"message_radio'+i+'\"class=\"message_radio\"></span>'
	    	                  +'</div>';
	    	                  $("#mobile_choise_content").append($tmpHTML); 
				}else{
				$tmpHTML='<div id=\"message_array'+i+'\"class=\"row_message\">'
	                  +'<span id=\"message_text'+i+'\"class=\"message_choise_text\">'+pairs[1]+'</span>'
	                  +'<span class=\"message_choice_all\" style=\"display:none\">'+array[i]+'</span>'
	                  +'<span id=\"message_radio'+i+'\"class=\"message_radio_checked\"></span>'
	                  +'</div>';
	                  $("#mobile_choise_content").append($tmpHTML); 
					
				}
	                
		 }
	    $(".message_radio").click(function(){
	    	var backVal=null;
	    	backVal=$(this).siblings('.message_choice_all').text();
	    	//alert(backVal);
	    	$(this).removeClass("message_radio");
	    	$(this).addClass("message_radio_checked");
	    	if(false){
	    		BJCallFun(callBackOK,backVal);
			}else{
				var frames= window.document.getElementById("pciframe"); 
			    frames.contentWindow.BJCallFun(callBackOK,backVal);
//				BJCallFun(callBackOK,backVal);
		    }
	    	
	       // alert(backVal+"222");
	    	//if(arrayCallback) arrayCallback(backVal);
	    	setTimeout($("#mobile_choise").css("display","none"),2000);
	        
	    	 
	    	//$("#mobile_content").children().remove();
	    	//$(this).siblings().remove();
	    	//$(this).parents(".row_message").remove();
	    	return backVal;
	    });
	   $(".message_radio_checked").click(function(){
		    var backVal=null;
	    	backVal=$(this).siblings('.message_choice_all').text();
	    	$(this).removeClass("message_radio");
	    	//alert(backVal);
	    	if(false){
	    		BJCallFun(callBackOK,backVal);
			}else{
				var frames= window.document.getElementById("pciframe"); 
			    frames.contentWindow.BJCallFun(callBackOK,backVal);
//				BJCallFun(callBackOK,backVal);
		    }
//	    	var frames= window.document.getElementById("pciframe"); 
//		    frames.contentWindow.BJCallFun(callBackOK,backVal);
	    	//if(arrayCallback) arrayCallback(backVal);
	    	setTimeout($("#mobile_choise").css("display","none"),2000); 
	    	
	    	return backVal;
	   });
	   $("#mobile_choise_close").click(function(){
			  $("#mobile_choise").css("display","none");
		  });

	   
	   
}	
(function($){

$.alerts = {
		
		// These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time
		
		verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
		horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
		repositionOnResize: true,           // re-centers the dialog on window resize
		overlayOpacity: .01,                // transparency level of overlay
		overlayColor: '#FFF',               // base color of overlay
		draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)
		okButton: '&nbsp;确定&nbsp;',         // text for the OK button
		cancelButton: '&nbsp;取消&nbsp;', // text for the Cancel button
		dialogClass: null,                  // if specified, this class will be applied to all dialogs
		
		// Public methods
		
		alert: function(message, title, callback) {
			if( title == null ) title = 'Alert';
			$.alerts._show(title, message, null, 'alert', function(result) {
				if( callback ) callback(result);
			});
		},
		success: function(message, title, callback) {
			if( title == null ) title = 'autoFade';
			$.alerts._show(title, message, null, 'jsuccess', function(result) {
				if( callback ) callback(result);
			});
		},
		mobile: function(message, title, callback) {
			if( title == null ) title = 'autoFade';
			$.alerts._show(title, message, null, 'jmobile', function(result) {
				if( callback ) callback(result);
			});
		},
		error: function(message, title, callback) {
			if( title == null ) title = 'autoFade';
			$.alerts._show(title, message, null, 'jerror', function(result) {
				if( callback ) callback(result);
			});
		},
		
		confirm: function(message, title, callback) {
			if( title == null ) title = 'Confirm';
			$.alerts._show(title, message, null, 'confirm', function(result) {
				if( callback ) callback(result);
			});
		},
			
		prompt: function(message, value, title, callback) {
			if( title == null ) title = 'Prompt';
			$.alerts._show(title, message, value, 'prompt', function(result) {
				if( callback ) callback(result);
			});
		},
		
		// Private methods
		
		_show : function(title, msg, value, type, callback) {

			$.alerts._hide();
			$.alerts._overlay('show');

			$("BODY").append(
					'<div id="popupMaskAlert" style="display: block;"></div>'
							+ '<div id="popup_container">'
							+ '<h1 id="popup_title"></h1>'
							+ '<div id="popup_content">'
							+ '<div id="popup_message"></div>' + '</div>'
							+ '</div>');

			// add background shadow
			var popupMask = document.getElementById("popupMaskAlert");
			var theBody = document.getElementsByTagName("BODY")[0];
			theBody.style.overflow = "hidden";

			var scTop = parseInt(theBody.scrollTop, 10);
			var scLeft = parseInt(theBody.scrollLeft, 10);

			popupMask.style.top = scTop + "px";
			popupMask.style.left = scLeft + "px";

			/*var fullHeight = getViewportHeight();
			var fullWidth = getViewportWidth();

			// Determine what's bigger, scrollHeight or fullHeight / width
			if (fullHeight > theBody.scrollHeight) {
				popHeight = fullHeight;
			} else {
				popHeight = theBody.scrollHeight;
			}

			if (fullWidth > theBody.scrollWidth) {
				popWidth = fullWidth;
			} else {
				popWidth = theBody.scrollWidth;
			}

			popupMask.style.height = popHeight + "px";
			popupMask.style.width = popWidth + "px";*/

			if ($.alerts.dialogClass)
				$("#popup_container").addClass($.alerts.dialogClass);

			// IE6 Fix
			var pos = ($.browser.msie && parseInt($.browser.version) <= 6) ? 'absolute'
					: 'fixed';

			$("#popup_container").css({
				position : pos,
				zIndex : 99999,
				padding : 0,
				margin : 10
			});

			$("#popup_title").text(title);
			$("#popup_content").addClass(type);
			$("#popup_message").text(msg);
			$("#popup_message").html(
					$("#popup_message").text().replace(/\n/g, '<br />'));

			// $("#popup_container").css({
			// minWidth: $("#popup_container").outerWidth(),
			// maxWidth: $("#popup_container").outerWidth()
			// });

			$.alerts._reposition();
			$.alerts._maintainPosition(true);

			switch (type) {
			case 'alert':
				$("#popup_message").after(
						'<div id="popup_panel"><input type="button" value="'
								+ $.alerts.okButton
								+ '" id="popup_ok" /></div>');
				$("#popup_ok").click(function() {
					$.alerts._hide();
					callback(true);
				});
				$("#popup_ok").focus().keypress(function(e) {
					if (e.keyCode == 13 || e.keyCode == 27)
						$("#popup_ok").trigger('click');
				});
				break;
			case 'confirm':
				$("#popup_message")
						.after(
								'<div id="popup_panel"><input type="button" value="'
										+ $.alerts.okButton
										+ '" id="popup_ok" /> <input type="button" value="'
										+ $.alerts.cancelButton
										+ '" id="popup_cancel" /></div>');
				$("#popup_ok").click(function() {
					$.alerts._hide();
					if (callback)
						callback(true);
				});
				$("#popup_cancel").click(function() {
					$.alerts._hide();
					if (callback)
						callback(false);
				});
				$("#popup_ok").focus();
				$("#popup_ok, #popup_cancel").keypress(function(e) {
					if (e.keyCode == 13)
						$("#popup_ok").trigger('click');
					if (e.keyCode == 27)
						$("#popup_cancel").trigger('click');
				});
				break;
			case 'prompt':
				$("#popup_message")
						.append(
								'<br /><input type="text" size="30" id="popup_prompt" />')
						.after(
								'<div id="popup_panel"><input type="button" value="'
										+ $.alerts.okButton
										+ '" id="popup_ok" /> <input type="button" value="'
										+ $.alerts.cancelButton
										+ '" id="popup_cancel" /></div>');
				$("#popup_prompt").width($("#popup_message").width());
				$("#popup_ok").click(function() {
					var val = $("#popup_prompt").val();
					$.alerts._hide();
					if (callback)
						callback(val);
				});
				$("#popup_cancel").click(function() {
					$.alerts._hide();
					if (callback)
						callback(null);
				});
				$("#popup_prompt, #popup_ok, #popup_cancel").keypress(
						function(e) {
							if (e.keyCode == 13)
								$("#popup_ok").trigger('click');
							if (e.keyCode == 27)
								$("#popup_cancel").trigger('click');
						});
				if (value)
					$("#popup_prompt").val(value);
				$("#popup_prompt").focus().select();
				$.alerts._reposition();
				break;
			case 'jerror':
				;
			case 'jsuccess':
				$("#popup_message").after('<div id="popup_panel"></div>');

				setTimeout(function() {
					$.alerts._hide();
					callback(true);
				}, 2000);
				break;
			case 'jmobile':
				$("#popup_message").css("background","#000");
				$("#popup_message").css("color","#fff");
				$("#popup_message").css("padding-left","20px");
				$("#popup_message").css("padding-right","20px");
				$("#popup_message").css("padding-top","5px");
				$("#popup_message").css("padding-bottom","5px");
				$("#popup_message").css("font-weight","700");
				setTimeout(function() {
					$.alerts._hide();
					callback(true);
				}, 2000);
				break;
			}

			// Make draggable
			// if( $.alerts.draggable ) {
			// try {
			// $("#popup_container").draggable({ handle: $("#popup_title") });
			//					$("#popup_title").css({ cursor: 'move' });
			//				} catch(e) { /* requires jQuery UI draggables */ }
			//			}
		},
		
		_hide: function() {
			$("#popup_container").remove();
			$("#popupMaskAlert").remove();
			$.alerts._overlay('hide');
			$.alerts._maintainPosition(false);
			 
		},
		
		_overlay: function(status) {
			switch( status ) {
				case 'show':
					$.alerts._overlay('hide');
					$("BODY").append('<div id="popup_overlay"></div>');
					$("#popup_overlay").css({
						position: 'absolute',
						zIndex: 99998,
						top: '0px',
						left: '0px',
						width: '100%',
						height: $(document).height(),
						background: $.alerts.overlayColor,
						opacity: $.alerts.overlayOpacity
					});
				break;
				case 'hide':
					$("#popup_overlay").remove();
				break;
			}
		},
		
		_reposition: function() {
			var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
			var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset-10;
			if( top < 0 ) top = 0;
			if( left < 0 ) left = 0;
			
			// IE6 fix
			if( $.browser.msie && parseInt($.browser.version) <= 6 ) top = top + $(window).scrollTop();
			
			$("#popup_container").css({
				top: top + 'px',
				left: left + 'px'
			});
			$("#popup_overlay").height( $(document).height() );
		},
		
		_maintainPosition: function(status) {
			if( $.alerts.repositionOnResize ) {
				switch(status) {
					case true:
						$(window).bind('resize', $.alerts._reposition);
					break;
					case false:
						$(window).unbind('resize', $.alerts._reposition);
					break;
				}
			}
		}
		
	}
	
	// Shortuct functions
	jAlert = function(message,  callback) {
		$.alerts.alert(message,"",  callback);
	};
	jSuccess = function(message ,callback) {
		$.alerts.success(message,"",  callback);
	};
	jMobile=function(message,callback){
		$.alerts.mobile(message,"",  callback);
	};
	jError = function(message,  callback) {
		$.alerts.error(message, "", callback);
	};
	
	jConfirm = function(message,  callback) {
		$.alerts.confirm(message, "", callback);
	};
		
	jPrompt = function(message, value, callback) {
		$.alerts.prompt(message, value,"", callback);
	};
})(jQuery);	