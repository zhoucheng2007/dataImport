//添加用户
  function add_user(){
  	document.forms[0].action="UserCenterCmd.cmd?method=forinsert";
  	document.forms[0].submit();
  }
  //删除用户
  function forDelete(user_id){
  	 jConfirm('确认要删除吗?', function(r) {       
            if(r){
            	alert(user_id);
              document.forms[0].action ="UserCenterCmd.cmd?method=delUserCenter&id="+user_id;  
              document.forms[0].submit();   
            }
        });
  }
  //修改用户信息
  function forUpdate(user_id){
  	 document.forms[0].action ="UserCenterCmd.cmd?method=getDetail&id="+user_id;  
     document.forms[0].submit(); 
  }
   //判断上传文件大小及格式
  function checkfile(fileid) 
	{ 
		 var flag=false;
		 var a = false;
		 var b = false;
		 var c = false;
		 var d = false;
	    filename=document.getElementById(fileid).value;
	    filepath=document.getElementById(fileid);
		 var fileText=filename.substring(filename.lastIndexOf("."),filename.length); 
		 fileText=fileText.toLowerCase();
		//主要识别常见图片格式jpg/gif/png/bmp/
		 if (fileText=='.jpg' || fileText=='.gif' || fileText=='.png' ||fileText=='.bmp')
		 {//判断文件
		 	flag=true;
		}else{
			document.getElementById(fileid).focus();
			document.getElementById(fileid).outerHTML=document.getElementById(fileid).outerHTML;
			document.getElementById('lb1').innerHTML ="您选择的图片文件不是合法";
		}
		var fileUp = document.getElementById(fileid);
		//alert(fileUp.value+"@"+fileUp.value.length)
		if (fileUp.value ==null || fileUp.value.length<1 ){
			b = false;
		}else{
			b= true;
		}
		//alert(flag&b);
		if(flag&b) {
			//alert("1");
			document.forms["frmList"].action ="http://10.10.10.48/DocCenterService/upload";
			//alert("2");
			document.forms["frmList"].target= "resultiframe";
			//alert("3");
			document.forms["frmList"].submit();
		}	else{
			alert("每一项都必须填写，您有未填写的选项,或者您的上传文档格式不正确，请查看上传文件的格式。");
			return false;
		}
		
   }
   function getResult(){
   	//alert("ifram");
		var resulturl=window.parent.frames['resultiframe'];
		//alert("1")
		var ret=$(resulturl.document.body).html();
		//alert("2")
		var arr=ret.substring(ret.indexOf("{"),ret.indexOf("}")+1);
		//alert(arr);
		if(ret==null||ret==""){
			
		}else{
		   var data=eval("("+arr+")");
		  // alert(data);
		   //alert(data.docid);
		   $('#pictureid').val(data.docid);
		  // alert($('#pictureid').val());
		}
	}
 
	