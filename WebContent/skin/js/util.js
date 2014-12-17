// 文件大小显示通用Ｋ，M，Ｇ，Ｔ显示

function formatByte(byteSize){
	var size;
	var length;
	if(typeof(byteSize)=='number'){
		size=byteSize.toString();
	};
	if(byteSize==undefined){
		size='0';
	}
	length=size.length;
	switch (true){
		case length>12: 
			size=(size/(1024*1024*1024*1024)).toFixed(2)+'T';
			break;
		case length>9: 
			size=(size/(1024*1024*1024)).toFixed(2)+'G';
			break;
		case length>6: 
			size=(size/(1024*1024)).toFixed(2)+'M';
			break;
		case length>3: 
			size=(size/1024).toFixed(2)+'K';
			break;
		default:
			size=size.substring(0,3)+'B';
	}
	return size;
}

//convert string to xml object 
function String2XML(xmlstring) { 
// for IE 
	if (window.ActiveXObject) { 
		var xmlobject = new ActiveXObject("Microsoft.XMLDOM"); 
		xmlobject.async = "false"; 
		xmlobject.loadXML(xmlstring); 
		return xmlobject; 
	} 
	// for other browsers 
	else { 
		var parser = new DOMParser(); 
		var xmlobject = parser.parseFromString(xmlstring, "text/xml"); 
		return xmlobject; 
	} 
} 

function xmlToJson(xml) { 

	// Create the return object 
	var obj = {}; 
	
	if (xml.nodeType == 1) { // element 
		// do attributes 
		if (xml.attributes.length > 0) { 
			obj["attributes"] = {}; 
			for (var j = 0; j < xml.attributes.length; j++) { 
				var attribute = xml.attributes.item(j); 
				obj["attributes"][attribute.nodeName] = attribute.nodeValue; 
			} 
		} else if (xml.nodeType == 3) { // text 
			obj = xml.nodeValue; 
		} 
	
		// do children 
		if (xml.hasChildNodes()) { 
			for (var i = 0; i < xml.childNodes.length; i++) { 
				var item = xml.childNodes.item(i); 
				var nodeName = item.nodeName; 
				if (typeof (obj[nodeName]) == "undefined") { 
					obj[nodeName] = xmlToJson(item); 
				} else { 
					if (typeof (obj[nodeName].length) == "undefined") { 
						var old = obj[nodeName]; 
						obj[nodeName] = []; 
						obj[nodeName].push(old); 
					} 
					obj[nodeName].push(xmlToJson(item)); 
				}
			} 
		} 
	} 
	return obj; 
};


function XML2String(xmlobject) { 
	// for IE 
	if (window.ActiveXObject) { 
		return xmlobject.xml; 
	} 
	// for other browsers 
	else { 
		return (new XMLSerializer()).serializeToString(xmlobject); 
	} 
} 