/**
* Styleswitch stylesheet switcher built on jQuery
* Under an Attribution, Share Alike License
* By Kelvin Luck ( http://www.kelvinluck.com/ )
**/

$(document).ready(function() {
	$('.styleswitch').click(function()
	{
		switchStylestyle(this.getAttribute("rel"));
		return false;
	});					

});
var c = readCookie('style');
if (!c) {
	c="css3";
};
switchStylestyle(c)

function switchStylestyle(styleName)
{
	
	//$('link[rel*=style][title]').each(function(i) 
	//{
	//	this.disabled = true;
	//	if (this.getAttribute('title') == styleName) this.disabled = false;
	//});
	if(styleName=='css1'){//hong
		$("#changeStyle").attr("href","/d3/skins/default/css/cq/css/change1.css");
		$("#mainStyle").attr("href","/d3/skins/default/css/cq/css/mainr.css");
		$("#headStyle").attr("href","/d3/skins/default/css/cq/css/headr.css");
		$("#bannerStyle").attr("href","/d3/skins/default/css/cq/css/bannerr.css");
		$("#banner-content li img").attr("src","/d3/skins/default/css/cq/imgsr/banner-box.png");	
	}else if(styleName=='css2'){//lu
		$("#changeStyle").attr("href","/d3/skins/default/css/cq/css/change2.css");
		$("#mainStyle").attr("href","/d3/skins/default/css/cq/css/maing.css");
		$("#headStyle").attr("href","/d3/skins/default/css/cq/css/headg.css");
		$("#bannerStyle").attr("href","/d3/skins/default/css/cq/css/bannerg.css");
		$("#banner-content li img").attr("src","/d3/skins/default/css/cq/imgsg/banner-box.png");	
	}else{
		$("#changeStyle").attr("href","/d3/skins/default/css/cq/css/change3.css");
		$("#mainStyle").attr("href","/d3/skins/default/css/cq/css/main.css");
		$("#headStyle").attr("href","/d3/skins/default/css/cq/css/head.css");
		$("#bannerStyle").attr("href","/d3/skins/default/css/cq/css/banner.css");
		$("#banner-content li img").attr("src","/d3/skins/default/css/cq/imgs/banner-box.png");	
	}
	
	
	
	createCookie('style', styleName, 365);
}

// cookie functions http://www.quirksmode.org/js/cookies.html
function createCookie(name,value,days)
{
	if (days)
	{
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}
function readCookie(name)
{
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++)
	{
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}
function eraseCookie(name)
{
	createCookie(name,"",-1);
}
// /cookie functions