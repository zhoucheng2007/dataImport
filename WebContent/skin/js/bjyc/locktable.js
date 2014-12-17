var isIe7 = 0;//如果浏览器是ie7,则为1，否则为0
var brow=$.browser; 
if(brow.msie) {
	if(brow.version==7.0){
		isIe7 = 1;
	}
} 

//锁定表格，行列都锁定
function FixTable(TableID, FixColumnNumber, width, height) {
    if ($("#" + TableID + "_tableLayout").length != 0) {
        $("#" + TableID + "_tableLayout").before($("#" + TableID));
        $("#" + TableID + "_tableLayout").empty();
    }
    else {
        $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
    }
    $('<div id="' + TableID + '_tableFix"></div>'
    + '<div id="' + TableID + '_tableHead"></div>'
    + '<div id="' + TableID + '_tableColumn"></div>'
    + '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout");
    var oldtable = $("#" + TableID);
    var tableFixClone = oldtable.clone(true);
    tableFixClone.attr("id", TableID + "_tableFixClone");
    $("#" + TableID + "_tableFix").append(tableFixClone);
    var tableHeadClone = oldtable.clone(true);
    tableHeadClone.attr("id", TableID + "_tableHeadClone");
    $("#" + TableID + "_tableHead").append(tableHeadClone);
    var tableColumnClone = oldtable.clone(true);
    tableColumnClone.attr("id", TableID + "_tableColumnClone");
    $("#" + TableID + "_tableColumn").append(tableColumnClone);
    $("#" + TableID + "_tableData").append(oldtable);
    $("#" + TableID + "_tableLayout table").each(function () {
        $(this).css("margin", "0");
    });
    var HeadHeight = $("#" + TableID + "_tableHead thead").height();
    HeadHeight += 2;
    $("#" + TableID + "_tableHead").css("height", HeadHeight);
    $("#" + TableID + "_tableFix").css("height", HeadHeight);
    var ColumnsWidth = 0;
    var ColumnsNumber = 0;
    $("#" + TableID + "_tableColumn tr:first th:lt(" + FixColumnNumber + ")").each(function () {
    	var colspan = $(this).attr("colspan");
    	ColumnsNumber = parseInt(ColumnsNumber)
		if(!isNaN(colspan)){
			ColumnsNumber += parseInt(colspan);
		}
    	if(ColumnsNumber<=FixColumnNumber){
    		 ColumnsWidth += $(this).outerWidth(true);
    	}
    });
    ColumnsWidth += 2;
    if ($.browser.msie) {
        switch ($.browser.version) {
            case "7.0":
                if (ColumnsNumber >= 3) ColumnsWidth--;
                break;
            case "8.0":
                if (ColumnsNumber >= 2) ColumnsWidth--;
                break;
        }
    }
    $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);
    $("#" + TableID + "_tableFix").css("width", ColumnsWidth);
    $("#" + TableID + "_tableData").scroll(function () {
        $("#" + TableID + "_tableHead").scrollLeft($("#" + TableID + "_tableData").scrollLeft());
        $("#" + TableID + "_tableColumn").scrollTop($("#" + TableID + "_tableData").scrollTop());
    });
    $("#" + TableID + "_tableFix").css({ "overflow": "hidden", "position": "relative", "z-index": "50", "background-color": "#e7f2ff" });
    $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "width": width - 17, "position": "relative", "z-index": "45", "background-color": "#e7f2ff" });
    $("#" + TableID + "_tableColumn").css({ "overflow": "hidden", "height": height - 17, "position": "relative", "z-index": "40", "background-color": "#e7f2ff" });
    $("#" + TableID + "_tableData").css({ "overflow": "auto", "width": width, "height": height, "position": "relative", "z-index": "35","scrollbar-face-color":"#1560B1","scrollbar-track-color":"#162C55","scrollbar-3dlight-color":"#2D9DDC" });
    if ($("#" + TableID + "_tableHead").width() > $("#" + TableID + "_tableFix table").width()) {
        $("#" + TableID + "_tableHead").css("width", $("#" + TableID + "_tableFix table").width()+2);
        $("#" + TableID + "_tableData").css("width", $("#" + TableID + "_tableFix table").width() + 19);
    }
    if ($("#" + TableID + "_tableColumn").height() > $("#" + TableID + "_tableColumn table").height()) {
        $("#" + TableID + "_tableColumn").css("height", $("#" + TableID + "_tableColumn table").height()+2);
        $("#" + TableID + "_tableData").css("height", $("#" + TableID + "_tableColumn table").height() + 19);
    }
    var fixHeight = $("#" + TableID + "_tableFix").height();
    var headHeight = $("#" + TableID + "_tableHead").height();
    var columnHeight = $("#" + TableID + "_tableColumn").height();

    $("#" + TableID + "_tableHead").css("top",-fixHeight+"px");
    $("#" + TableID + "_tableColumn").css("top",-(fixHeight+headHeight)+"px");
    $("#" + TableID + "_tableData").css("top",-(fixHeight+headHeight+columnHeight)+"px");
}

//锁定表格，只锁定列
function FixTableColumn(TableID, FixColumnNumber, width) {
	var oldtable = $("#" + TableID);
	var height=oldtable.height()+17;
    if ($("#" + TableID + "_tableLayout").length != 0) {
        $("#" + TableID + "_tableLayout").before($("#" + TableID));
        $("#" + TableID + "_tableLayout").empty();
    }
    else {
        $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
    }
    $('<div id="' + TableID + '_tableColumn"></div>'
    + '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout"); 
    var tableColumnClone = oldtable.clone(true);
    tableColumnClone.attr("id", TableID + "_tableColumnClone");
    $("#" + TableID + "_tableColumn").append(tableColumnClone);
    $("#" + TableID + "_tableData").append(oldtable);
    $("#" + TableID + "_tableLayout table").each(function () {
        $(this).css("margin", "0");
    });
    var ColumnsWidth = 0;
    var ColumnsNumber = 0;
    $("#" + TableID + "_tableColumn tr:first th:lt(" + FixColumnNumber + ")").each(function () {
    	var colspan = $(this).attr("colspan");
    	ColumnsNumber = parseInt(ColumnsNumber)
		if(!isNaN(colspan)){
			ColumnsNumber += parseInt(colspan);
		}
    	if(ColumnsNumber<=FixColumnNumber){
    		 ColumnsWidth += $(this).outerWidth(true);
    	}
    });
    ColumnsWidth += 2;
    if ($.browser.msie) {
        switch ($.browser.version) {
            case "7.0":
                if (ColumnsNumber >= 3) ColumnsWidth--;
                break;
            case "8.0":
                if (ColumnsNumber >= 2) ColumnsWidth--;
                break;
        }
    }
    $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);
    $("#" + TableID + "_tableData").scroll(function () {
        $("#" + TableID + "_tableColumn").scrollTop($("#" + TableID + "_tableData").scrollTop());
    });
    $("#" + TableID + "_tableColumn").css({ "overflow": "hidden", "height": height - 17, "position": "relative", "z-index": "40", "background-color": "#e7f2ff" });
    $("#" + TableID + "_tableData").css({ "overflow-x": "auto","overflow-y": "hidden", "width": width, "height": height, "position": "relative", "z-index": "35","scrollbar-face-color":"#1560B1","scrollbar-track-color":"#162C55","scrollbar-3dlight-color":"#2D9DDC" });
    if ($("#" + TableID + "_tableColumn").height() > $("#" + TableID + "_tableColumn table").height()) {
        $("#" + TableID + "_tableColumn").css("height", $("#" + TableID + "_tableColumn table").height()+2);
        $("#" + TableID + "_tableData").css("height", $("#" + TableID + "_tableColumn table").height() + 19);
    }
    var columnHeight = $("#" + TableID + "_tableColumn").height();

    $("#" + TableID + "_tableData").css("top",-(columnHeight)+"px");
}


//锁定表格，只锁定行
function FixTableRow(TableID,width, height) {
	if($("#" + TableID).height()<height){
		return false;
	}else{
		
	    if ($("#" + TableID + "_tableLayout").length != 0) {
	        $("#" + TableID + "_tableLayout").before($("#" + TableID));
	        $("#" + TableID + "_tableLayout").empty();
	    }
	    else {
	        $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
	    }
	    $('<div id="' + TableID + '_tableHead"></div>'
	    + '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout");
	
	 	var oldtable = $("#" + TableID);
		$("#" + TableID + "_tableData").append(oldtable);
	
		
	//锁定表头部分开始
		var tableHeadClone = oldtable.clone(true);
	    tableHeadClone.attr("id", TableID + "_tableHeadClone");
	    $("#" + TableID + "_tableHead").append(tableHeadClone);
	
	 //锁定表头部分结束   
	
		
	    $("#" + TableID + "_tableLayout table").each(function () {
	        $(this).css("margin", "0");
	    });
	    var HeadHeight = $("#" + TableID + "_tableHead thead").height();	
	    HeadHeight += 2;
	    $("#" + TableID + "_tableHead").css("height", HeadHeight);
	    var ColumnsWidth = 0;
	    ColumnsWidth += 2;
	   
	    $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);
	    
	    $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "width": width - 17, "position": "relative", "z-index": "45", "background-color": "#e7f2ff" });
	    $("#" + TableID + "_tableData").css({ "overflow-x": "hidden","overflow-y": "auto", "width": width, "height": height, "position": "relative", "z-index": "35","scrollbar-face-color":"#1560B1","scrollbar-track-color":"#162C55","scrollbar-3dlight-color":"#2D9DDC" });
	    
	    var tableWidth = width - 17;
	    $("#" + TableID + "_tableHeadClone").css("width",tableWidth+"px");
	    $("#" + TableID).css("width",tableWidth+"px");
	
	    var headHeight = $("#" + TableID + "_tableHead").height();
	
	    $("#" + TableID + "_tableHead").css("top","0px");
	    $("#" + TableID + "_tableData").css("top",-(headHeight)+"px");
	}
}

//锁定表格适应浏览器变化
function resizeTable(TableID,width, height){
	$("#" + TableID + "_tableLayout").css({"width":width+"px","height":height+"px"});
	var tfixwidth = $("#" + TableID+ "_tableFix").width();
	var tfixheight = $("#" + TableID+ "_tableFix").height();
	var theadwidth = width-17;
	var tcolumnheight = height - 17;
	var tdatawidth = width;
	var tdataheight = height;
	var tfixtablewidth = $("#" + TableID + "_tableFix table").width();
	if (theadwidth > tfixtablewidth) {
	 	theadwidth = tfixtablewidth+2;
	 	tdatawidth = tfixtablewidth+19;
   	}
    var tcolumntableheight = $("#" + TableID + "_tableColumn table").height();
    if (tcolumnheight > tcolumntableheight) {
       tcolumnheight = tcolumntableheight+2;
       tdataheight = tcolumntableheight+19;
   	}
	$("#" + TableID + "_tableHead").css("width",theadwidth+2+"px");
	$("#" + TableID + "_tableColumn").css("height",tcolumnheight+"px");
	$("#" + TableID + "_tableData").css({"width":tdatawidth+"px","height":tdataheight+"px"});
	
    var theadHeight = $("#" + TableID + "_tableHead").height();

    $("#" + TableID + "_tableHead").css("top",-tfixheight+"px");
    $("#" + TableID + "_tableColumn").css("top",-(tfixheight+theadHeight)+"px");
    $("#" + TableID + "_tableData").css("top",-(tfixheight+theadHeight+tcolumnheight)+"px");
}

