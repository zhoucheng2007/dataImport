/* 人力*/
function openNCNode(funcode,usercode){
	try
	{
	//	alert(funcode+"  1 "+usercode);
		execNCAppletFunction("nc.ui.sm.webcall.OpenNCNode","openNode",funcode,usercode);
	}
	catch(error)
	{
	}	
};


function execNCAppletFunction(className,methodName,funcode,usercode)
{ 
try{
 	var ncFrame = document.getElementById("ncf");  
 	if(ncFrame == null)
 	//	alert(funcode+"  2"+usercode);
 		ncFrame = initNCFrame(usercode);
			waitLoadNCApplet(className,methodName,funcode);
}catch(error){
  }

} 

function waitLoadNCApplet(className,methodName,funcode,usercode)
{
	var applet = null; 
	try{
		var ncFrame =document.getElementById("ncf");
		if(ncFrame != null)
		applet =ncFrame.contentWindow.document.applets["NCApplet"];
	}
	catch(error){ 
		showErrorDialog("get applet error:" + error.name + ":" + error.message);
		return;
	}


	if(applet == null)
	{
		setTimeout("waitLoadNCApplet('"+ className +"','" + methodName + "','" + funcode + "')", 100);
		return;
	}
	 openNCApplet(funcode,usercode);
}; 

function openNCApplet(funcode,usercode){
	try{ 
	    var nf = document.getElementById("ncf");
	    if(nf!=null){
	    //    alert("ncIFrame ok");
	    }else{
	   		alert("ncIFrame null");
	    } 
	var ncapplet; 
		 //alert(nf.src);
		 ncapplet = nf.contentWindow.document.applets["NCApplet"];
	   // alert(ncapplet);
	    if(ncapplet!=null){
	    //    alert("ncapplet ok");
	    }else{
	         alert("ncapplet null");
	    } 
		 //ncapplet.callNC("nc.bs.webservice.open.OpenClient","openMsgPanel","");
		 ncapplet.callNC("nc.ui.sm.webcall.OpenNCNode","openNode",funcode);

  }catch(error){
  }
  
};	

function initNCFrame(usercode) {
  var frameID = "ncf";
  var frame = document.createElement("iframe");
  frame.id = frameID;
  frame.style.position = "relative";
  frame.style.left = "0";
  frame.style.top = "0";
  frame.style.width = 1;
  frame.style.height = 0;
  frame.frameBorder = 0;
  frame.width = 0;
  frame.height = 0;
  document.body.appendChild(frame);
  getHtmlCode(usercode);
  return frame;
};

function getHtmlCode(usercode) {
  $.ajax({
      url: '/portal/getCode',
      type: 'POST',
      data: {
          usercode: usercode
      },
      dataType: 'html',
      timeout: 50000,
      success: function(result) {
          document.getElementById('ncf').contentWindow.document.open();
          document.getElementById('ncf').contentWindow.document.write(result);
          document.getElementById('ncf').contentWindow.document.close();
      },
      error: function() {
          alert('11');
      }

  });
}


function clearNCFrame()
{
	var frameID = "ncf";
	var ncFrame = document.getElementById(frameID);
	if(ncFrame != null)
	{
		ncFrame.src = "";
		ncFrame.parentNode.removeChild(ncFrame);
	}
}
