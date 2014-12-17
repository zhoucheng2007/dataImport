document.writeln("<div id=measureRightMenu style='display:none'>");
document.writeln("<table border='1' width='100%' height='100%' bgcolor='#3c79c6' style='border:thin;' cellspacing='0'>");
document.writeln("<tr><td style='cursor:hand;border:outset 1;font-size:9pt;' align='center'>元数据描述</td></tr>");
document.writeln("</table></div>");
//调用元数据系统
function metaHelp(objid,objtype){
	var metaurl = "";
	
    var typename = "对象";
    if(objtype == "01"){
    	typename = "指标";
    }else if(objtype == "03"){
    	typename = "报表";
    }else if(objtype == "04"){
    	typename = "稽核指标";
    }else if(objtype == "05"){
    	typename = "业务术语";
    }
    
	if(objid == undefined || objid == "undefined" || objid == null || objid == "null"){
		alert("该"+typename+"不存在！");
	}else{
		var url ="pubSql.cmd?SQL_4HNMETAURL= SELECT META_URL FROM DC_OBJ_META_MAPPING WHERE OBJECT_ID='"+objid+"' "; 
		if(objtype != undefined && objtype != "undefined" && objtype != ""){
			url = url + "AND OBJECT_TYPE='"+objtype+"' ";
		}
		
		var arr=getArrayByXmlHttp(url);
		if(arr != -1){
			metaurl=trim(arr[0][0]);
			//alert("执行url："+metaurl);
			window.open(metaurl);
		}else{
			alert("该"+typename+"在元数据系统中没有相应信息！");
		}
	}
}
function showRightMenu(kpiMeasureId,objtype)
{     
	 popRightMenu(measureRightMenu,100,kpiMeasureId,objtype);
  event.returnValue=false;
  event.cancelBubble=true;
  return false;
}
/**
*显示弹出菜单
*menuDiv:右键菜单的内容
*width:行显示的宽度
*measureId:指标ID
*objtype:类型
*/
function popRightMenu(menuDiv,width,measureId,objtype)
{
  //创建弹出菜单
  var pop=window.createPopup();
  //设置弹出菜单的内容
  pop.document.body.innerHTML=menuDiv.innerHTML;
	//屏蔽菜单的右键
  pop.document.oncontextmenu=function(){
        return false;
  }
  var rowObjs=pop.document.body.all[0].rows;
  rowObjs[0].cells[0].onclick=function sss(){
 	        var metaurl = "";
	      
          var typename = "对象";
          if(objtype == "01"){
          	typename = "指标";
          }else if(objtype == "03"){
          	typename = "报表";
          }else if(objtype == "04"){
    		typename = "稽核指标";
    	  }else if(objtype == "05"){
    		typename = "业务术语";
    	 }
          if(measureId == undefined || measureId == "undefined" || measureId == null || measureId == "null"){
	        	alert("该"+typename+"不存在！");
	        }else{
	      	   var url ="pubSql.cmd?SQL_4HNMETAURL= SELECT META_URL FROM DC_OBJ_META_MAPPING WHERE OBJECT_ID='"+measureId+"' "; 
	      	   if(objtype != undefined && objtype != "undefined" && objtype != ""){
	      	   	url = url + "AND OBJECT_TYPE='"+objtype+"' ";
	      	   }
             var arr=getArrayByXmlHttp(url);
	      	   if(arr != -1){
	      	   	metaurl=trim(arr[0][0]);
	      	  //alert("执行url："+metaurl);
	      	   	window.open(metaurl);
	      	   }else{
	      	   	   alert("该"+typename+"在元数据系统中没有相应信息！");
	      	   }
	        }
 	  };
    //获得弹出菜单的行数
    var rowCount=rowObjs.length;
    //循环设置每行的属性
    for(var i=0;i<rowObjs.length;i++)
    {
       //设置鼠标滑入该行时的效果
       rowObjs[i].cells[0].onmouseover=function(){
            this.style.background="#6395D1";
            this.style.color="yellow";
       }
       //设置鼠标滑出该行时的效果
       rowObjs[i].cells[0].onmouseout=function(){
           this.style.background="#3c79c6";
           this.style.color="white";
      }
    }                
    //选择右键菜单的一项后，菜单隐藏
    pop.document.onclick=function(){
          pop.hide();
    }
    //显示菜单
    pop.show(event.clientX-1,event.clientY,width,25,document.body);
    return true;
}