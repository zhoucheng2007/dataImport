
var swfUpload;	
	var flashUrl = "/skin/pws/js/upload/swfupload.swf";
	var button = $("#updatabutton");
	var pid=1; 
	var fileSelectedNum =0;
	var filesQueuedNum=0;
	var flush=0;
	var is_check=0;

	function initView(key) {
		flush=key;
		try{
			swfUpload=$("#fsUploadProgress").multiupload({
			debugEnabled:true,
            upload_url:"/DocCenterService/upload",
            file_post_name:'uploadfile',
            file_size_limit:"100 MB",                
            file_types:"*.*",
            file_types_description:"All files",
            flash_url:'/skin/pws/js/upload/swfupload.swf',
            button_image_url:'/skin/pws/img/upload/upload-btn.png',
            button_width:105,
            button_height:33,
            button_class:"",
            post_params:{},
            button_placeholder:$('#uploadfirstwrapper')[0]
        })
        .bind('fileQueued', swfFileQueued)
        .bind('fileQueueError',fileQueueErrors)
        .bind('fileDialogComplete',fileDialogComplete)
        .bind('uploadStart', uploadStart)
        .bind('uploadProgress',uploadProgress)
        .bind('uploadSuccess', uploadSuccess)
        .bind('uploadComplete', uploadComplete)
        .bind('debug', function(msg) {
        	//alert(msg);
        })
        .bind('flashReady', function(msg) {
        });
   		
		}catch(ex){
			alert(ex);
		}
    	var postdata = {id: $("#currentfolder").val()};
    	$("#folder_id").val($("#currentfolder").val());
		$("#updatabutton").click(function() {
			if(filesQueuedNum==0){
				var content = "选择文件";
				alert("选择文件");
				return false;
			}
			
		 $.multiupload.getInstance($('#fsUploadProgress')).setPostParams({uid:"BIADMIN",type:'doc',folder_id:0,update_uid:"BIADMIN",is_update:'0'});
		 swfUpload.multiupload('startUpload');
		   
		});
		
	}
	function freshData(){
		fileSelectedNum --;
		filesQueuedNum --;
		if(filesQueuedNum ==0){
			$('#queuestatus').text("");
		}else{
		}
	}
		function swfFileQueued(event, file) {
			var thmeurl=""
			 var listitem = '<li id="' + file.id + '" >' +
			 '<img class="upico" src="/skin/pws/img/upload/'+uploadico(file.name)+'" border="0"/>'+
             '<div>' + file.name + '(' + Math.round(file.size / (1024)) + ' KB)<span class="statu"></span></div>' +
             '<span class="stopUpload" ></span>' +
             '</li>';
			 $('#log').append(listitem);
			 $("#name").val(file.name);
			 $('li#' + file.id + ' .stopUpload').bind('click', function () {
				 var swfu = $.multiupload.getInstance('#fsUploadProgress');
				 swfu.cancelUpload(file.id);
				 $('#log li#' + file.id).find('p.status').text('Canceled');
				 $('#log li#' + file.id).find('span.stopUpload').css('background-image','none')
				 $('#log li#' + file.id).find('span.stopUpload').css('cursor','defult')
				 $('#log li#' + file.id).remove();
				 freshData();
			 });		
        }
		function uploadico(name){
			var type=name.split(".");
			var ext=type[1]?type[1]:"";
			
			return showico(ext);
		}
		function fileQueueErrors(event, file, errorCode, message) {
            alert(errorCode+":"+message)

        }

		function fileDialogComplete(event, numFilesSelected, numFilesQueued) {
			//alert("fileDialogComplete");
			fileSelectedNum = numFilesSelected;
			filesQueuedNum += numFilesQueued;
			 $('#queuestatus').show("normal");
        }
		function uploadStart(event, file) {
        }
		function uploadProgress(event, file, bytesLoaded,total) {
        	if(total==null)total = file.size;
            var percentage = Math.round((bytesLoaded / total) * 100);
            $('#log li#' + file.id).find('div.progress').css('width', percentage + '%');
            $('#log li#' + file.id).find('span.progressvalue').text(percentage + '%');
        }
		function uploadSuccess(event, file, serverData) {
            var item = $('#log li#' + file.id);
            item.find('div.progress').css('width', '100%');
            item.find('span.progressvalue').text('100%');
            item.find('.statu').html('上传成功');
            item.addClass("success");
            $('#log li#' + file.id).find('span.stopUpload').css('background-image','none') ;
            $('#log li#' + file.id).find('span.stopUpload').css('cursor','default') ;
            freshData();
		}
		function uploadComplete(event, file) {
            $(this).multiupload('startUpload');

        }
		function showico(ext_id){
			var icoimg="";
			switch (ext_id) {
			case "doc":
				icoimg="ms-word.png";
				break;
			case "docx":
				icoimg="ms-word.png";
				break;
			case "xls":
				icoimg="ms-excel.png";
				break;
			case "xlsx":
				icoimg="ms-excel.png";
				break;
			case "xlsm":
				icoimg="ms-excel.png";
				break;
			case "ppt":
				icoimg="ms-powerpoint.png";
				break;
			case "pptx":
				icoimg="ms-powerpoint.png";
				break;
			case "rar":
				icoimg="archive.png";
				break;
			case "zip":
				icoimg="archive.png";
				break;
			case "jpg":
				icoimg="image.png";
				break;
			case "gif":
				icoimg="image.png";
				break;
			case "png":
				icoimg="image.png";
				break;
			case "html":
				icoimg="html.png";
				break;
			case "txt":
				icoimg="document.png";
				break;
			case "xml":
				icoimg="xml.gif";
				break;
			case "rmvb":
				icoimg="video.png";
				break;
			default:
				icoimg="binary.png";
				break;
			}
			return icoimg;
		}
        initView(0);