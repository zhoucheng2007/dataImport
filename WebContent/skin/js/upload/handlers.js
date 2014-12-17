var formChecker = null;
function swfUploadLoaded() {
	var btnSubmit = document.getElementById("btnSubmit");
	btnSubmit.onclick = doSubmit;
	formChecker = window.setInterval(validateForm, 1000);
	//validateForm();
}

///������������Ƿ�Ϊ�գ���������Զ���
function validateForm() {
	

}


function doSubmit(e) {
	if (formChecker != null) {
		clearInterval(formChecker);
		formChecker = null;
	}
	
	e = e || window.event;
	if (e.stopPropagation) {
		e.stopPropagation();
	}
	e.cancelBubble = true;
	
	try {
		
		//上传数据
		swfu.setPostParams({uid:$("#USERID").val(),name:encodeURI($("#FILE_NAME").val())});
		swfu.startUpload();
		
		
		
	} catch (ex) {

	}
	return false;
}


function uploadDone(id) {
	//alert("开始自己上传啦。。。id为==="+id);
	document.forms[0].action = "attachment.cmd?method=insert";
	document.forms[0].submit();
}

function fileDialogStart() {
	var txtFileName = document.getElementById("txtFileName");
	txtFileName.value = "";

	this.cancelUpload();
}


///ѡ���ļ�����
function fileQueueError(file, errorCode, message)  {
}

function fileQueued(file) {
	//alert(file.name);
	try {
		var rawName=document.getElementById("txtFileName");
		rawName.value=file.name;
		var txtFileName = document.getElementById("FILE_NAME");
		txtFileName.value = file.name;
	} catch (e) {
	}

}
function fileDialogComplete(numFilesSelected, numFilesQueued) {
	
	//validateForm();
}
///�ϴ����
function uploadProgress(file, bytesLoaded, bytesTotal) {
}

function uploadSuccess(file, serverData) {
	try {
		file.id = "singlefile";	
		var progress = new FileProgress(file, this.customSettings.progress_target);
		progress.setComplete();
		progress.setStatus("Complete.");
		progress.toggleCancel(false);
		
		if (serverData === " ") {
			this.customSettings.upload_successful = false;
		} else {
			this.customSettings.upload_successful = true;
			document.getElementById("fileId").value = serverData;
		}
		uploadDone(serverData);
	} catch (e) {
	}
}
///�ϴ���ɺ�ķ���
function uploadComplete(file) {

}
///�ϴ�ʧ�ܷ���
function uploadError(file, errorCode, message) {
}
