function help(helpId,parms){
	
	var url ="jsp/help/help.jsp?url=pubcommonhelpcontrol.cmd&clear=true&HELP_ID="+helpId;
	if(parms!=null && parms!="")
	{
		var urlStr = url+"&"+parms; 
		if(urlStr.length>2000 ||parms.indexOf("#")>-1) //ӦΪ2048��������url̫����������2000Ϊ׼��
		{
  	 		url = url + "&helpLongString=1";  
  			var bodyHtml = document.body.innerHTML;
  			var oTxa = document.createElement('<textarea id="helpLongString"   style="display:none"></textarea>');
				document.body.insertAdjacentElement("beforeEnd", oTxa);
  			var objStr = "";
		    for(var i=0;i<parms.split("&").length;i++)
				{
				  objStr = objStr + '<input type="hidden" name="' + parms.split("&")[i].split("=")[0] + '" value="' + parms.split("&")[i].split("=")[1] + '">' ;
				}
				document.getElementById("helpLongString").value = objStr;
			 
  	  	} 
  	 		else
		  		url=url+"&"+parms;
	}
		var win = showModalDialog(url,window,"scroll:auto;status:no;dialogWidth:600px;dialogHeight:500px");
	return win;
}
//ʵ���ⲿ����,�û��Լ�дͨ�ð���ʱ�����磺doHelp("self.cmd","para1=1&para2=2")
function doHelp(URL,parms,style){
	
	var url ="jsp/help/help.jsp?url="+ URL +"&clear=true";
	if(parms!=null && parms!="")
	{
		var urlStr = url+"&"+parms; 
		if(urlStr.length>2000 ||parms.indexOf("#")>-1) //ӦΪ2048��������url̫����������2000Ϊ׼��
		{
  	 		url = url + "&helpLongString=1";  
  			var bodyHtml = document.body.innerHTML;
  			var oTxa = document.createElement('<textarea id="helpLongString"   style="display:none"></textarea>');
				document.body.insertAdjacentElement("beforeEnd", oTxa);
  			var objStr = "";
		    for(var i=0;i<parms.split("&").length;i++)
				{
				  objStr = objStr + '<input type="hidden" name="' + parms.split("&")[i].split("=")[0] + '" value="' + parms.split("&")[i].split("=")[1] + '">' ;
				}
				document.getElementById("helpLongString").value = objStr;
			 
  	  	} 
  	 		else
		  		url=url+"&"+parms;
		}
	if(style==null || style=="")
		style = "scroll:auto;status:no;dialogWidth:600px;dialogHeight:500px";
	var win = showModalDialog(url,window,style);
	return win;
}


function getIHelpUrl(helpNo,parms,objId,method,addEmpty){
	var url = "pubcommonhelp.cmd?method=objectHelp&iframeHelp=true&helpno="+helpNo;
	url=url+"&help_method="+method;
	url=url+"&addEmpty="+addEmpty;
	if( parms != null && parms !="")
		url = url + "&"+parms;
	if( objId != null && objId !="")
		url = url + "&primaryKey="+objId;
	//alert("objid:" + objId);
	return url;
}
//
function fill_List(list_forms,win,customMethod){
     //alert("fill:" + list_forms.length+", win="+win);
	 var contain=false;
	 for(var j = 0; j < list_forms.length; j ++ ){
	    var sel_form = list_forms[j];
	    var pvalue = sel_form.value;
		var options = sel_form.options;
		//
		if(options){
			var len = options.length;
			for( var i=0;i<len;i++){
				options.remove(0);
			}		
			if(win!=null && win.length>0){
			   for(var i = 0; i < win.length; i ++){
			      var object = document.createElement("OPTION");
				  object.value=win[i][0];
				  object.text= win[i][1];
				  sel_form.add(object);
				  if(object.value==pvalue)
				  	contain=true;
			   }
			}
			if( contain )
				sel_form.value=pvalue;
			else
				sel_form.value="";
		 }
		 //
		 try{
		 	 eval(customMethod);
		 }
		 catch(ex){	}
	 }
}

function quickHelp(helpNo,parms,objId){
	var url = "pubcommonhelp.cmd?method=objectHelp&helpno="+helpNo;
	if( parms != null && parms !="")
		url = url + "&"+parms;
	if( objId != null && objId !="")
		url = url + "&primaryKey="+objId;
	var win = showModalDialog(url,window,"scroll:auto;status:no;dialogHide:yes;dialogTop:0px;dialogLeft:0px;dialogWidth:0px;dialogHeight:0px");
	return win;
}
function fillHelpList(list_forms,help_no,condition,addEmpty){
	 var win = quickHelp(help_no,condition,null);
	 fill_List(win);
}