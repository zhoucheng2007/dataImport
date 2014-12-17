/*
 	mobileJs ::A js interface for hybrid app
  	Author: sunwq
    Version: 0.2.0 
    MJxxx is a interface for js;
    MCxxx is a interface for native java.
 */

//document.addEventListener("deviceready", MJAnylHead, false);
//document.addEventListener("DOMContentLoaded", MJAnylHead, false)
//    document.addEventListener("deviceready", onDeviceReady, false);
//function onDeviceReady() {
//	// Register the event listener
//	document.addEventListener("backbutton", MJOnBackKeyDown, false);
//}
/**
 * this method for web to excute callbackfunction
 */
function BJCallFun(methodName, data) {
	this.func = new Function(methodName + "('" + data + "');");
	this.func();
}

document.addEventListener("DOMContentLoaded", getCurrentOS, false);// 新增
function validataOS() {
	if ((navigator.userAgent.match(/iPhone/i))
			|| (navigator.userAgent.match(/iPod/i))
			|| (navigator.userAgent.match(/iPad/i))) {
		return "ios";

	} else if (navigator.userAgent.match(/Android/i)) {
		return "Android";
	} else if (navigator.userAgent.match(/Mac/i)
			|| (navigator.userAgent.match(/Windows/i))) {
		return "PC";
	} else {
	}
}
var os;
function getCurrentOS() {
	os = validataOS();
}
/**
 * js 异常处理
 */
onerror = function(msg, url, l) {
	/*txt = "本页中存在错误。\n\n";
	txt += "错误：" + msg + "\n";
	txt += "URL: " + url + "\n";
	txt += "行：" + l + "\n\n";
	txt += "点击“确定”继续。\n\n";
	alert(txt);*/
};
var message = "your divice is not supported ";
/**
 * 调用日期选择框 ; param obj：input对象 ; param callback：回调函数名
 */
function MJGetDate(obj, callBack) {
	tempInput = obj;
	try {
		if (os == "ios") {
			// ios add js here

			if (callBack) {
				window.location.href = "objc:getDate:" + callBack;
			} else {
				window.location.href = "objc:getDate:MCSetDate";
			}
		} else if (os == "Android") {
			if (callBack) {
				window.inspur.getDate(callBack);
			} else {
				window.inspur.getDate("MCSetDate");
			}
		}

	} catch (e) {
		tempInput.value = "today";
	}
}

/**
 * 暂时不可用 ; param obj：input对象 ; param callback：回调函数名
 */
function MJGetMonth(obj, callBack) {
	tempInput = obj;
	try {
		if (os == "ios") {
			if (callBack) {
				window.location.href = "objc:getMonth:" + callBack;
			} else {
				window.location.href = "objc:getMonth:MCSetMonth";
			}
		} else if (os == "Android") {
			if (callBack) {
				window.inspur.getMonth(callBack);
			} else {
				window.inspur.getMonth("MCSetMonth");
			}
		}
	} catch (e) {
		tempInput.value = "month";
	}
}
/**
 * 调用时间选择框 ; param obj：input对象 ; param callback：回调函数名
 */
function MJGetTime(obj, callBack) {
	tempInput = obj;
	try {
		if (os == "ios") {
			 if (callBack) {
					window.location.href = "objc:getTime:" + callBack;
				} else {
					window.location.href = "objc:getTime:MCSetTime";
				}
		} else if (os == "Android") {
			if (callBack) {
				window.inspur.getTime(callBack);
			} else {
				window.inspur.getTime("MCSetTime");
			}
		}
	} catch (e) {
		tempInput.value = "time";
	}
}
var tempInput;
/**
 * 默认日期选择框回调函数 ; param date：日期
 */
function MCSetDate(date) {
	tempInput.value = date;
}
/**
 * 默认日期选择框回调函数（暂时不可用） ; param month：月份
 */
function MCSetMonth(month) {
	tempInput.value = month;
}
/**
 * 默认时间选择框回调函数 ; param time：时间
 */
function MCSetTime(time) {
	tempInput.value = time;
}
/**
 * 弹出等待提示框
 */
function MJStartProgress(message) {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:showWaitView:::" + message + "";
		} else if (os == "Android") {
			window.inspur.startProgressDialog(message);
		}
	} catch (e) {
		console.log("Exception: MJStartProgress " + message);
	}
}
/**
 * 消失 等待提示框
 */
function MJStopProgress() {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:hiddenShowWaitView";
		} else if (os == "Android") {
			window.inspur.stopProgressDialog();
		}
	} catch (e) {
		console.log("Exception: MJStopProgress ");
	}
}
/**
 * 登陆成功主页调用
 */
function MJLoginOK(json) {
	try {
		if (os == "ios") {
			 window.location.href = "objc:loginOK:::" + json + "";
		} else if (os == "Android") {
			window.inspur.loginOK(json);
		} else if (os == "PC") {
			window.parent.loginOk(json);
		}
	} catch (e) {
		console.log("Exception: MJLoginOK ");
	}
}
// /**
// * 登陆页面调用
// */
// function MJLoginNotOK() {
// try {
// window.inspur.loginNotOK();
// } catch (e) {
// console.log("Exception: MJLoginNotOK ");
// }
// }
/**
 * 设置footermenu ; param json
 */
function MJSetUIParm(json) {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:setUIParm:::" + json + "";
		} else if (os == "Android") {
			window.inspur.setUIParm(json);
		}
	} catch (e) {
		console.log("Exception: MJSetUIParm ");
	}
}
/**
 * 调用短信发送功能 ; param tel:电话号码
 */
function MJCallTel(tel) {
	try {
		if (os == "ios") {
			// ios add js here
			 window.location.href = "objc:callTel:::"+tel+"";
		} else if (os == "Android") {
			window.inspur.callTel(tel);
		}
	} catch (e) {
		console.log("Exception: MJCallTel ");
	}
}
/**
 * 调用电话 ; param tel:电话号码 ; param message:消息内容
 */
function MJSendMS(tel, message) {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:sendMS:::"+ tel + "|"
			+ message + "";
		} else if (os == "Android") {
			window.inspur.sendMS(tel, message);
		}
	} catch (e) {
		console.log("Exception: MJSendMS ");
	}
}

/**
 * 调用帅选框 ; param arrary:数据内容 ; param title:标题头
 */
function MJShowSingleChoiceDialog(arrary, title) {
	try {
		var OriginalArg = arguments.length;
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:showFilterDialog:::" + arrary + "|"
					+ title + "";
		} else if (os == "Android") {
			if (OriginalArg == 2) {
				window.inspur.showSingleChoiceDialog(arrary, title);
			} else {
				window.inspur.showSingleChoiceDialog(arrary);
			}
		} else if (os == "PC") {
			window.parent.BJShowSingleChoiceDialog(arrary, title);
		}
	} catch (e) {
		console.log("Exception: MJShowSingleChoiceDialog ");
	}
}
/**
 * 消息提示框 ; param message:消息内容
 */
function MJShowToastMsg(message) {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:showAlertView:::" + message + "";
		} else if (os == "Android") {
			window.inspur.showToastMsg(message);
		} else if (os == "PC") {
			window.parent.BJJMobile(message);
		}

	} catch (e) {
		console.log("Exception: MJShowToastMsg ");
	}
}
/**
 * 文本编辑框; param callBackOK：回调方法名称 ; param callBackCancle：回调方法名称 ; param
 * message:消息内容 ; param title:标题内容
 */
function MJShowEditDialog(callBackOK, callBackCancle, title, message) {
	try {
		if (callBackOK == null || callBackOK == "") {
			callBackOK = "MCOnEditDialogOK";
		}
		if (callBackCancle == null || callBackCancle == "") {
			callBackCancle = "MCOnEditDialogCancle";
		}
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:showEditDialog:" + callBackOK + ":"
					+ callBackCancle + ":" + title + "|" + message + "";
		} else if (os == "Android") {

			var OriginalArg = arguments.length;
			if (OriginalArg == 4) {
				window.inspur.showEditDialog(callBackOK, message, title,
						callBackCancle);
			} else {
				window.inspur.showEditDialog(callBackOK, title, callBackCancle);
			}
		} else if (os == "PC") {
			window.parent.BJShowEditDialog(callBackOK, "MCOnEditDialogCancle",
					title, message);
		}
	} catch (e) {
		console.log("Exception: MJShowEditDialog ");
	}
}
/**
 * 默认扫文本编辑框回调函数 ; param content:文本内容
 */
function MCOnEditDialogOK(content) {

	alert("文本编辑框   MCOnEditDialogOK(content)  content =" + content);

}
/**
 * 默认扫文本编辑框回调函数 ; param content:文本内容
 */
function MCOnEditDialogCancle(content) {
	alert("文本编辑框   MCOnEditDialogCancle(content)  content =" + content);
}

/**
 * 扫描条码二维码 ; param callBack：回调方法名称
 */
function MJScanCode(callBack) {
	try {
		var OriginalArg = arguments.length;
		if (os == "ios") {
			// ios add js here
			if (OriginalArg == 1) {
				window.location.href = "objc:scanCode:"+callBack;
			} else {
				window.location.href = "objc:scanCode:MCScanCode";
			}
		} else if (os == "Android") {
			if (OriginalArg == 1) {
				window.inspur.scanCode(callBack);
			} else {
				window.inspur.scanCode("MCScanCode");
			}
		}
	} catch (e) {
		console.log("Exception: MJScanCode ");
	}
}
/**
 * 默认扫描条码二维码回调函数 ; param code：数据
 */
function MJGetOSType(scanCode) {
	alert("扫描返回结果   MJScanCode(scanCode)  scanCode =" + scanCode);
}

/**
 * 退出系统
 */
function MJExitSYS() {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:exitSYS";
		} else if (os == "Android") {
			window.inspur.exitSYS();
		}
	} catch (e) {
		console.log("Exception: MJExitSYS ");
	}
}

/**
 * 获取系统类型
 */
function MJGetOSType() {
	try {
		if (os == "ios") {
			// ios add js here

		} else if (os == "Android") {
			return window.inspur.getOSType();
		}
		return message;
	} catch (e) {
		console.log("Exception: MJGetOSType ");
	}
}
/**
 * 获取程序版本信息
 */
function MJGetAppVersionName() {
	 if (!callback)
			callback = "MCSetAppVersionName"
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:getAppVersionName:" + callback;
		} else if (os == "Android") {
			return window.inspur.getAppVersionName();
		}
		return message;
	} catch (e) {
		console.log("Exception: MJGetAppVersionName ");
	}
}
/**
 * 启动自动更新 param url：更新程序下载地址
 */
function MJUpdateApp(url) {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:updateApp:::" + url + "";
		} else if (os == "Android") {
			return window.inspur.updateApp(url);
		}
		return message;
	} catch (e) {
		console.log("Exception: MJUpdateApp ");
	}
}

/**
 * 打开地图 param json :坐标数据
 */
function MJUpdateMapData(json) {
	try {
		if (os == "ios") {
			// ios add js here

		} else if (os == "Android") {
			return window.inspur.upDateMapData(json);
		}
		return message;
	} catch (e) {
		console.log("Exception: MJUpdataMapData ");
	}
}
/**
 * 打开地图 param json :坐标数据
 */
function MJOpenMap(json) {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:openMap:::" + json + "";
		} else if (os == "Android") {
			return window.inspur.openMap(json);
		}
		return message;
	} catch (e) {
		console.log("Exception: MJOpenMap ");
	}
}
/**
 * 隐藏tab页
 */

function MJTabHidden() {
	try {
		if (os == "ios") {
			// ios add js here

		} else if (os == "Android") {
			return window.inspur.tabHidden();
		}
		return message;
	} catch (e) {
		console.log("Exception: MJTabHidden ");
	}
}
/**
 * 开启gps
 */
function MJOpenGps(upLoadUrl, comCode, visitorCode, visitorType, gpsFrequency,
		upLoadFrequency) {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:openGps:::"+ upLoadUrl + "|"
            + comCode + "|"+ visitorCode + "|"+ visitorType + "|"+ gpsFrequency + "|"+ upLoadFrequency + "";
		} else if (os == "Android") {
			return window.inspur.openGps(upLoadUrl, comCode, visitorCode,
					visitorType, gpsFrequency, upLoadFrequency);
		}
		return message;
	} catch (e) {
		console.log("Exception: MJOpenGps ");
	}
}

/**
 * 关闭gps
 */
function MJCloseGps() {
	try {
		if (os == "ios") {
			// ios add js here
			window.location.href = "objc:closeGps";
		} else if (os == "Android") {
			return window.inspur.closeGps();

		}
		return message;
	} catch (e) {
		console.log("Exception: MJCloseGps ");
	}
}

/**
 * 获取地理坐标
 */
function MJGetLcoation(callback) {
	if (!callback)
		callback = "MCSetLocation"
	try {
		if (os == "ios") {
			// ios add js here
			 window.location.href = "objc:getLocation:" + callback;
		} else if (os == "Android") {
			return window.inspur.getLocation(callback);
		}
		return message;
	} catch (e) {
		console.log("Exception: MJGetLcoation ");
	}
}

/**
 * location默认回调函数
 */
function MCSetLocation(lat, lng) {
	alert("默认方法MCSetlocation( lat,lng) 当前坐标：lat=" + lat + " lng=" + lng);
}

/**
 * openNewWebView 打开一个新的webview
 *
 * url：新窗口网页地址
 */
function MJOpenNewWebView(url) {

	try {
		if (os == "ios") {
			// ios add js here
            //M 6.20
            window.location.href = "objc:openNewWebView:::" + url + "";

		} else if (os == "Android") {
//			return window.inspur.openNewWebView(url);
		}
		return message;
	} catch (e) {
		console.log("Exception: MJOpenNewWebView ");
	}
}
/**
 * 展示图片 param json :图片url
 */
function MJShowImag(json) {
	try {
		if (os == "ios") {
			// ios add js here
            
             //M 6.20
            window.location.href = "objc:showImag:::" + json + "";
            
		} else if (os == "Android") {
			return window.inspur.imageShow(json);
		}
		return message;
	} catch (e) {
		console.log("Exception: MJOpenMap ");
	}
}