/*************

flash 轮播器

*************/
function isIE(){
	if(document.all)
		return true;
	else
		return false;
}
//var pic_1,pic_1_s,pic_1_title,pic_1_url,pic_2,pic_2_s,pic_2_title,pic_2_url,pic_3,pic_3_s,pic_3_title,pic_3_url,pic_4,pic_4_s,pic_4_title,pic_4_url,pic_5,pic_5_s,pic_5_title,pic_5_url,pic_6,pic_6_s,pic_6_title,pic_6_url;  
/*	
document.getElementById("pic_1");
alert(pic_1);
document.getElementById("pic_2");
document.getElementById("pic_3");
document.getElementById("pic_4");
document.getElementById("pic_5");
document.getElementById("pic_6");
var pic_1 =jsonObj[0].newPic;
var pic_1_s = "/portal/skin/img/qh/num1.png";
var pic_1_title =jsonObj[0].newTitle";
var pic_1_url =jsonObj[0].newUrl.;
var pic_2 =jsonObj[1].newPic;
var pic_2_s = "/portal/skin/img/qh/num2.png";
var pic_2_title =jsonObj[1].newTitle";
var pic_2_url = "#";
var pic_3 =jsonObj[2].newPic;
var pic_3_s = "/portal/skin/img/qh/num3.png";
var pic_3_title =jsonObj[2].newTitle;
var pic_3_url = "#";
var pic_4 =jsonObj[3].newPic;
var pic_4_s = "/portal/skin/img/qh/num4.png";
var pic_4_title =jsonObj[3].newTitle;
var pic_4_url = "#";
pic_5 =jsonObj[4].newPic;
var pic_5_s = "/portal/skin/img/qh/num5.png";
var pic_5_title =jsonObj[4].newTitle;
var pic_5_url = "#";
pic_6 =jsonObj[5].newPic;
var pic_6_s = "/portal/skin/img/qh/num6.png";
var pic_6_title = jsonObj[5].newTitle;
var pic_6_url = "#";*/
//var oInterval = "";
//oInterval = window.setInterval("flashpic()",5000);
var oInterval = "";
oInterval = window.setInterval("flashpic()",5000);
//alert(oInterval+"oInterval");
function resetfliter(){
	if(isIE()){
		document.getElementById("s_pic_1").style.filter = "alpha(opacity=40)";
		document.getElementById("s_pic_2").style.filter = "alpha(opacity=40)";
		document.getElementById("s_pic_3").style.filter = "alpha(opacity=40)";
		document.getElementById("s_pic_4").style.filter = "alpha(opacity=40)";
		document.getElementById("s_pic_5").style.filter = "alpha(opacity=40)";
		document.getElementById("s_pic_6").style.filter = "alpha(opacity=40)";

		document.getElementById("s_pic_1").style.backgroundColor = "rgb(0, 0, 0)";
		document.getElementById("s_pic_2").style.backgroundColor = "rgb(0, 0, 0)";
		document.getElementById("s_pic_3").style.backgroundColor = "rgb(0, 0, 0)";
		document.getElementById("s_pic_4").style.backgroundColor = "rgb(0, 0, 0)";
		document.getElementById("s_pic_5").style.backgroundColor = "rgb(0, 0, 0)";
		document.getElementById("s_pic_6").style.backgroundColor = "rgb(0, 0, 0)";
	}
}
function flashpic(){
	//alert(1);
		
	var currentpic = document.getElementById("current_img").src;
	if(currentpic.search(pic_1) != -1){
		if(isIE()){
			document.getElementById("current_img").filters[0].apply();

			document.getElementById("current_img").src = pic_2;
			document.getElementById("current_img").parentNode.href = pic_2_url;
			document.getElementById("c_f_title").firstChild.href=pic_2_url;
			document.getElementById("c_f_title").title = pic_2_title;
			document.getElementById("c_f_title").innerHTML = pic_2_title;
			document.getElementById("current_img").filters[0].play();
        //   alert("pic_1");
			resetfliter();
			document.getElementById("s_pic_2").style.filter = "Alpha(Opacity=100,FinishOpacity=100,Style=0)";
			document.getElementById("s_pic_2").style.backgroundColor="#B6DAEB";
		}else{
			document.getElementById("current_img").src = pic_2;
			document.getElementById("current_img").parentNode.setAttribute("href",pic_2_url);
			document.getElementById("c_f_title").firstChild.setAttribute("href",pic_2_url);
			document.getElementById("c_f_title").innerHTML = pic_2_title;
			document.getElementById("c_f_title").title = pic_2_title;
		}
	}
	if(currentpic.search(pic_2) != -1){
		if(isIE()){
			document.getElementById("current_img").filters[0].apply();
			document.getElementById("current_img").src = pic_3;
			document.getElementById("current_img").parentNode.href = pic_3_url;
			document.getElementById("c_f_title").firstChild.href=pic_3_url;
			document.getElementById("c_f_title").innerHTML = pic_3_title;
			document.getElementById("c_f_title").title = pic_3_title;
			document.getElementById("current_img").filters[0].play();
			resetfliter();
			document.getElementById("s_pic_3").style.filter = "Alpha(Opacity=100,FinishOpacity=100,Style=0)";
			document.getElementById("s_pic_3").style.backgroundColor="#B6DAEB";
		}else{
			document.getElementById("current_img").src = pic_3;
			document.getElementById("current_img").parentNode.setAttribute("href",pic_3_url);
			document.getElementById("c_f_title").firstChild.setAttribute("href",pic_3_url);
			document.getElementById("c_f_title").innerHTML = pic_3_title;
			document.getElementById("c_f_title").title = pic_3_title;
		}
	}
	if(currentpic.search(pic_3) != -1){
		if(isIE()){
			document.getElementById("current_img").filters[0].apply();
			document.getElementById("current_img").src = pic_4;
			document.getElementById("current_img").parentNode.href = pic_4_url;
			document.getElementById("c_f_title").firstChild.href=pic_4_url;
			document.getElementById("c_f_title").innerHTML = pic_4_title;
			document.getElementById("c_f_title").title = pic_4_title;
			document.getElementById("current_img").filters[0].play();
			resetfliter();
			document.getElementById("s_pic_4").style.filter = "Alpha(Opacity=100,FinishOpacity=100,Style=0)";
			document.getElementById("s_pic_4").style.backgroundColor="#B6DAEB";
		}else{
			document.getElementById("current_img").src = pic_4;
			document.getElementById("current_img").parentNode.setAttribute("href",pic_4_url);
			document.getElementById("c_f_title").firstChild.setAttribute("href",pic_4_url);
			document.getElementById("c_f_title").innerHTML = pic_4_title;
			document.getElementById("c_f_title").title = pic_4_title;
		}
	}
	if(currentpic.search(pic_4) != -1){
		if(isIE()){
			document.getElementById("current_img").filters[0].apply();
			document.getElementById("current_img").src = pic_5;
			document.getElementById("current_img").parentNode.href = pic_1_url;
			document.getElementById("c_f_title").firstChild.href=pic_1_url;
			document.getElementById("c_f_title").innerText = pic_5_title;
			document.getElementById("c_f_title").title = pic_5_title;
			document.getElementById("current_img").filters[0].play();
			resetfliter();
			document.getElementById("s_pic_5").style.filter = "Alpha(Opacity=100,FinishOpacity=100,Style=0)";
			document.getElementById("s_pic_5").style.backgroundColor="#B6DAEB";
		}else{
			document.getElementById("current_img").src = pic_5;
			document.getElementById("current_img").parentNode.setAttribute("href",pic_1_url);
			document.getElementById("c_f_title").firstChild.setAttribute("href",pic_1_url);
			document.getElementById("c_f_title").innerHTML = pic_5_title;
			document.getElementById("c_f_title").title = pic_5_title;
		}
	}
	if(currentpic.search(pic_5) != -1){
		if(isIE()){
			document.getElementById("current_img").filters[0].apply();
			document.getElementById("current_img").src = pic_6;
			document.getElementById("current_img").parentNode.href = pic_3_url;
			document.getElementById("c_f_title").firstChild.href=pic_3_url;
			document.getElementById("c_f_title").innerHTML = pic_6_title;
			document.getElementById("c_f_title").title = pic_6_title;
			document.getElementById("current_img").filters[0].play();
			resetfliter();
			document.getElementById("s_pic_6").style.filter = "Alpha(Opacity=100,FinishOpacity=100,Style=0)";
			document.getElementById("s_pic_6").style.backgroundColor="#B6DAEB";
		}else{
			document.getElementById("current_img").src = pic_6;
			document.getElementById("current_img").parentNode.setAttribute("href",pic_3_url);
			document.getElementById("c_f_title").firstChild.setAttribute("href",pic_3_url);
			document.getElementById("c_f_title").innerHTML = pic_6_title;
			document.getElementById("c_f_title").title = pic_6_title;
		}
	}
	if(currentpic.search(pic_6) != -1){
		if(isIE()){
			document.getElementById("current_img").filters[0].apply();
			document.getElementById("current_img").src = pic_1;
			document.getElementById("current_img").parentNode.href = pic_3_url;
			document.getElementById("c_f_title").firstChild.href=pic_3_url;
			document.getElementById("c_f_title").innerHTML = pic_1_title;
			document.getElementById("c_f_title").title = pic_1_title;
			document.getElementById("current_img").filters[0].play();
			resetfliter();
			document.getElementById("s_pic_1").style.filter = "Alpha(Opacity=100,FinishOpacity=100,Style=0)";
			document.getElementById("s_pic_1").style.backgroundColor="#B6DAEB";
		}else{
			document.getElementById("current_img").src = pic_1;
			document.getElementById("current_img").parentNode.setAttribute("href",pic_3_url);
			document.getElementById("c_f_title").firstChild.setAttribute("href",pic_3_url);
			document.getElementById("c_f_title").innerHTML = pic_1_title;
			document.getElementById("c_f_title").title = pic_1_title;
		}
	}
}
//
function flashthis(obj,pos){
	
	if(isIE()){
		window.clearInterval(oInterval);
		resetfliter();
		obj.style.filter = "Alpha(Opacity=100,FinishOpacity=100,Style=0)";
		obj.style.backgroundColor="#B6DAEB";
		var currentpic = document.getElementById("current_img").src;
		
		document.getElementById("current_img").filters[0].apply();
		//var picsrc = "pic_1" + pos;
		currentpic = eval("pic_" + pos);
		document.getElementById("current_img").src = currentpic;
		document.getElementById("current_img").filters[0].play();
		//var picurl = "pic_";
		//var pictitle = "pic_";
		document.getElementById("c_f_title").innerText = eval("pic_"+pos+"_title");
		document.getElementById("current_img").parentNode.href = eval("pic_"+pos+"_url");
		document.getElementById("c_f_title").firstChild.href= eval("pic_"+pos+"_url");
		

		oInterval = window.setInterval("flashpic()",5000);
	}else{
		window.clearInterval(oInterval);
		//alert(eval("pic_" + pos));
		var currentpic = document.getElementById("current_img").src;
		currentpic = eval("pic_" + pos);
		//alert(currentpic);
		document.getElementById("current_img").src = currentpic;

		document.getElementById("current_img").parentNode.setAttribute("href",eval("pic_"+pos+"_url"));
		document.getElementById("c_f_title").firstChild.setAttribute("href",eval("pic_"+pos+"_url"));
		document.getElementById("c_f_title").innerHTML = eval("pic_"+pos+"_title");
		document.getElementById("c_f_title").title = eval("pic_"+pos+"_title");;

		oInterval = window.setInterval("flashpic()",5000);
	}
}