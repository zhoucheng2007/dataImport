document.writeln("<div id=measureRightMenu style='display:none'>");
document.writeln("<table border='1' width='100%' height='100%' bgcolor='#3c79c6' style='border:thin;' cellspacing='0'>");
document.writeln("<tr><td style='cursor:hand;border:outset 1;font-size:9pt;' align='center'>Ԫ��������</td></tr>");
document.writeln("</table></div>");
//����Ԫ����ϵͳ
function metaHelp(objid,objtype){
	var metaurl = "";
	
    var typename = "����";
    if(objtype == "01"){
    	typename = "ָ��";
    }else if(objtype == "03"){
    	typename = "����";
    }else if(objtype == "04"){
    	typename = "����ָ��";
    }else if(objtype == "05"){
    	typename = "ҵ������";
    }
    
	if(objid == undefined || objid == "undefined" || objid == null || objid == "null"){
		alert("��"+typename+"�����ڣ�");
	}else{
		var url ="pubSql.cmd?SQL_4HNMETAURL= SELECT META_URL FROM DC_OBJ_META_MAPPING WHERE OBJECT_ID='"+objid+"' "; 
		if(objtype != undefined && objtype != "undefined" && objtype != ""){
			url = url + "AND OBJECT_TYPE='"+objtype+"' ";
		}
		
		var arr=getArrayByXmlHttp(url);
		if(arr != -1){
			metaurl=trim(arr[0][0]);
			//alert("ִ��url��"+metaurl);
			window.open(metaurl);
		}else{
			alert("��"+typename+"��Ԫ����ϵͳ��û����Ӧ��Ϣ��");
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
*��ʾ�����˵�
*menuDiv:�Ҽ��˵�������
*width:����ʾ�Ŀ��
*measureId:ָ��ID
*objtype:����
*/
function popRightMenu(menuDiv,width,measureId,objtype)
{
  //���������˵�
  var pop=window.createPopup();
  //���õ����˵�������
  pop.document.body.innerHTML=menuDiv.innerHTML;
	//���β˵����Ҽ�
  pop.document.oncontextmenu=function(){
        return false;
  }
  var rowObjs=pop.document.body.all[0].rows;
  rowObjs[0].cells[0].onclick=function sss(){
 	        var metaurl = "";
	      
          var typename = "����";
          if(objtype == "01"){
          	typename = "ָ��";
          }else if(objtype == "03"){
          	typename = "����";
          }else if(objtype == "04"){
    		typename = "����ָ��";
    	  }else if(objtype == "05"){
    		typename = "ҵ������";
    	 }
          if(measureId == undefined || measureId == "undefined" || measureId == null || measureId == "null"){
	        	alert("��"+typename+"�����ڣ�");
	        }else{
	      	   var url ="pubSql.cmd?SQL_4HNMETAURL= SELECT META_URL FROM DC_OBJ_META_MAPPING WHERE OBJECT_ID='"+measureId+"' "; 
	      	   if(objtype != undefined && objtype != "undefined" && objtype != ""){
	      	   	url = url + "AND OBJECT_TYPE='"+objtype+"' ";
	      	   }
             var arr=getArrayByXmlHttp(url);
	      	   if(arr != -1){
	      	   	metaurl=trim(arr[0][0]);
	      	  //alert("ִ��url��"+metaurl);
	      	   	window.open(metaurl);
	      	   }else{
	      	   	   alert("��"+typename+"��Ԫ����ϵͳ��û����Ӧ��Ϣ��");
	      	   }
	        }
 	  };
    //��õ����˵�������
    var rowCount=rowObjs.length;
    //ѭ������ÿ�е�����
    for(var i=0;i<rowObjs.length;i++)
    {
       //������껬�����ʱ��Ч��
       rowObjs[i].cells[0].onmouseover=function(){
            this.style.background="#6395D1";
            this.style.color="yellow";
       }
       //������껬������ʱ��Ч��
       rowObjs[i].cells[0].onmouseout=function(){
           this.style.background="#3c79c6";
           this.style.color="white";
      }
    }                
    //ѡ���Ҽ��˵���һ��󣬲˵�����
    pop.document.onclick=function(){
          pop.hide();
    }
    //��ʾ�˵�
    pop.show(event.clientX-1,event.clientY,width,25,document.body);
    return true;
}