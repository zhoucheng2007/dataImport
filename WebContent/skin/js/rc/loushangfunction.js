/*****************************************************
**
**				���� ���� 2004��04��26��
**				
**
**
******************************************************/

var today = new Date();
var sError="";
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function(){
	return this.replace(/^\s*/g,"");
}
String.prototype.rtrim = function(){
	return this.replace(/\s*$/g,"");
}

function trim(){
	if(arguments.length<1)
		return null;
	if(typeof(arguments[0])=="string")
		return arguments[0].replace(/(^\s*)|(\s*$)/g, "");
	else
		return "";
}
function ltrim(){
	if(arguments.length<1)
		return null;
	if(typeof(arguments[0])=="string")
		return arguments[0].replace(/(^\s*)/g, "");
	else
		return "";
}
//�ַ�ȥ�����ҿո���
function rtrim(){
	if(arguments.length<1)
		return null;
	if(typeof(arguments[0])=="string")
		return arguments[0].replace(/(\s*$)/g, "");
	else
		return "";
}
//����У�麯��  �Ƿ������� 
function __isNum(s)
{
	var re = /^[0-9]+$/;
	return re.test(s);
}
//�Ƿ���Float
function __isFloat(s)
{
	if (__isNum(s)) return true;
	var re = /^[+|-]{0,1}\d*\.?\d+$/;
	return re.test(s);
}
//�Ƿ��ǿ�ѧ������Float
function __isEFloat(s)
{
    var re = /^[+|-]{0,1}\d*\.[\.\d+]{0,1}\e[-|+]{0,1}\d+$/;
    return re.test(s);
}
//�Ƿ����������
function __isQuoteIn(s)
{
	var re = /\"|\'|\��|\��|\��|\��|\%|\$|\��|\����|\��|\��|\-|\����|\!|\��|\#|\@|\~|\^|\*|\,|\.|\��|\��|\?|\��|\;|\��|\:|\��|\{|\}|\\|\/|\[|\]|\(|\)|\+|\-|\��|\||\<|\>/;
	return re.test(s);
}
//���� ����С��max������min ���� ����object ��max��min
function isIntegerMaxMin(ob,max,min){
	var value=parseInt(ob.value);
	return ((value==ob.value)&&(value<max)&&(value>min));
}
//�Ƿ�������  ����  ����
function isInteger(ob,type){
	//var value=parseInt(ob.value);
	//return (value==ob.value);
	if(type=="positive")
	return /^\+?\d+$/g.test(ob.value);
	else if (type=="negative")
	return /^-\d+$/g.test(ob.value);
	else
	return /^(?:-?|\+?)\d+$/g.test(ob.value);
}
//�ж϶����ֵ�Ƿ������� ����  ����
function checkNum(eSrc)
{
	var value = eSrc.value;
	return __isNum(value);

}
//�ж϶����ֵ�Ƿ���С������  ���� ����
function checkFloat(eSrc)
{
	var value = eSrc.value;
	return __isFloat(value);
}
//�ж϶����ֵ�Ƿ��ǿ�ѧ��������  ���� ����
function checkEFloat(eSrc)
{
	var value = eSrc.value;
	return __isEFloat(value);
}
//�ַ��Ƿ�
function __isNumChar(ch)
{
	return (ch >= '0' && ch <= '9');
}
//�Ƿ��ǿո�ת�е�
function __isBlankChar(ch)
{
	//ȥ����'\v';20020522   
	return (ch == ' ' || ch == '\f' || ch == '\n' || ch == '\r' || ch=='\t');
}
//����ַ����ĳ���   һ������ռ2���ֽ�
function byteLength(str)
{
	var re=/[\u4E00-\u9FA5]/g;
	if(re.test(str))
    return str.length+str.match(re).length;
	else return str.length;

}
//У���ַ����ȹ������� ����ob,��󳤶�maxLen����Ϣmessage
function stringLengthCheck(ob,maxLen,message){
	if(byteLength(trim(ob.value))>maxLen){
		alert(message);
		return false;
	}
	return true;
}
//��õ�ǰ����
function __getCurrentDate()
{
	var date = new Date();
	var year = ""+date.getFullYear();
	var month = date.getMonth()+1;
	if(month<10){ month="0"+month;}
	var day = date.getDate();
	if(day<10){ day = "0"+day;}
	return year+month+day;
}
//��ѧ���� ��ʽ������(��ȡС����������)   ���� ����ֵvalue��С��λ
function __formatNum(value,scale){
	var f = parseFloat(value);
	if(scale<0)scale=0;
	return f.toFixed(scale);
}
//��ѧ���� ��ʽ������   ���� ����ֵvalue��С��λ
function __setScale( value , scale )
{
	if( isNaN( value ) || isNaN( scale ) || parseInt( scale ) < 0 )
		return new String( value ) ;

	var strValue = new String( value ) ;
	var fValue = parseFloat( value ) ;
	var iScale = parseInt( scale ) ;
	
	if( iScale == 0 )
		return new String( ( fValue < 0.0 ? "-" : "" ) + Math.round( Math.abs( fValue ) ) ) ;
		
	if( fValue == parseInt( strValue ) )  //����Ĳ���û��С������
	{	
		strValue = new String( parseInt( strValue ) ) + "." ;

		for( var i = 0 ; i < iScale ; i ++ )
			strValue += "0" ;
	}
	else 
	{
		var fDecimal = Math.abs( fValue - parseInt( strValue ) ) ;
		fDecimal = Math.round( fDecimal * Math.pow( 10 , iScale ) ) ;
		var strDecimal = new String( fDecimal ) ;
		//������ new String( Math.round( 0.07 * Math.pow( 10 , 2 ) ) ) == "7" but not "07" �����������Ҫ�����²��㴦��
		if( strDecimal.length < iScale ) 
		{
			var iDecimalLength = strDecimal.length ;
			
			for( var i = 0 ; i < iScale - iDecimalLength ; i ++ )
				strDecimal = "0" + strDecimal ;
	
			strValue = new String( parseInt( strValue ) ) + "." + strDecimal ;
		}
		//������ new String( Math.round( 0.995 * Math.pow( 10 , 2 ) ) ) == "100" �����������Ҫ�����²��㴦��
		else if( strDecimal.length > iScale ) 
		{
			strValue = new String( parseInt( strValue ) + 1 ) ;			
			
			strValue += "." ;
			for( var i = 0 ; i < iScale ; i ++ )
				strValue += "0" ;
		}
		else
			strValue = new String( parseInt( strValue ) ) + "." + strDecimal ;
	}
	
	return strValue ;
}


//���֤����֤  ���� ֵ
function __isIdCardNo(num)
{
	var a=null;
	var len = num.length, re; 
	if (isNaN(num)) {	
		//x��β
		re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([xX])$/);
		a=num.match(re);
		if(a!=null){			
		}else{
			alert("����Ĳ��ǺϷ������֤��!");
			return false;
		}
	}else{	
	if (len == 15)
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
	else if (len == 18)
	re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);
	else {alert("��������֤��λ�����ԣ�"); return false;}
	var a = num.match(re);
	}
	if (a != null)
	{
	if (len==15)
	{
		var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
		var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
	}
	else
	{
		var D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
		var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
	}
	if (!B) {alert("��������֤�� "+ a[0] +" ��������ڲ��ԣ�"); return false;}
}
return true;
}
//У�����Ƿ�ϸ�  like 9.9999
function __checkMoney(str){
	if (trim(str)==""){
		sError="�������";		
		return false;
	}
	if(isNaN(trim(str))){
		sError="��������ֵ�������֣�";		
		return false;
	}
	var nreg = /(^[0-9]+$)|(^[0-9]+)(.[0-9]+$)/;
	var nr = str.match(nreg); 
	if(nr==null){
		sError="����Ľ���ʽ���ԣ�";		
		return false;
	}else{
		return true;
	}	
}
//ͨ���������λ  like 9.99
function __checkMoneyFix(str,n){
	if (trim(str)==""){
		sError="�������";		
		return false;
	}
	if(isNaN(trim(str))){
		sError="��������ֵ�������֣�";		
		return false;
	}
	var nreg = /(^[0-9]+$)|(^[0-9]+)(.[0-9]+$)/;
	var nr = str.match(nreg); 
	if(nr==null){
		sError="����Ľ���ʽ���ԣ�";		
		return false;
	}else{
		var index=str.indexOf(".")+1;
		var ss=str.substring(index);
		if(ss.length==n){
		return true;
		}else{
			return false;
		}
	}	
}
//У������  ���� ����.value
function __checkEmail(email)
{
// a very simple email validation checking. 
// you can add more complex email checking if it helps 
    var splitted = email.match("^(.+)@(.+)$");
    if(splitted == null) return false;
    if(splitted[1] != null )
    {
      var regexp_user=/^\"?[\w-_\.]*\"?$/;
      if(splitted[1].match(regexp_user) == null) return false;
    }
    if(splitted[2] != null)
    {
      var regexp_domain=/^[\w-\.]*\.[A-Za-z]{2,4}$/;
      if(splitted[2].match(regexp_domain) == null) 
      {
	    var regexp_ip =/^\[\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\]$/;
	    if(splitted[2].match(regexp_ip) == null) return false;
      }// if
      return true;
    }
return false;
}

//checkbox ȫѡ�� ���� checkbox��name, �Ƿ�ture,false
function __checkAll(name,tf)
{
  var a = document.getElementsByName(name);
  var n = a.length;
  for (var i=0; i<n; i++)
  a[i].checked = tf;
}
//checkbox �Ƿ���ѡ�� ���� checkbox��name  ����ֵΪѡ�еĸ�����û��ѡ��ʱ���� 0
function __checkSelectCheckbox(name)
{
  var a = document.getElementsByName(name);
  var n = a.length;
  var isCheck=0;
  for (var i=0; i<n; i++){
	  if(a[i].checked){
		  isCheck++;
	  }
  }
  return isCheck;
}
//����У�麯��  20040501

function __checkStdDate(eSrc)
{
	var value = eSrc.value;
	if (!__isNum(value)) {
		sError = "���ǺϷ�������";
		return false;
	}
	if (value.length != 8) {
		sError = "���ڳ��Ȳ���";
		return false;
	}

	var year = value.substring(0,4);
	var month = value.substring(4,6);
	var day = value.substring(6,8);
	var y = year * 1;
	var m = month * 1;
	var d = day * 1;
	var bReturn = __isLegalDate(y,m,d);
	return bReturn;
}
//����У�麯��  200405
function __checkStdMonth(eSrc)
{
	var value = eSrc.value;
	if (!__isNum(value)) {
		sError = "���ǺϷ�������";
		return false;
	}
	if (value.length != 6) {
		sError = "���ڳ��Ȳ���";
		return false;
	}

    var year = value.substring(0,4);
	var month = value.substring(4,6);
	var bReturn = __isLegalMonth(year,month);
	return bReturn;
}
//�·�У�� 1--12
function __checkMonth(eSrc)
{
    var value = eSrc.value;
    if (!__isNum(value)) {
		sError = "����������";
		return false;
	}
    if(value < 1 || value > 12)
     {
     	sError = "�·ݲ���С��1��Ҳ���ܴ���12";
     	return false;
     }
   	return true;
}
//���У��
function __checkStdYear(eSrc)
{
	var value = eSrc.value;
	if (value.length != 4) {
		sError = "���ڳ��Ȳ���";
		return false;
	}
	if (!__isNum(value)) {
		sError = "���ǺϷ�������";
		return false;
	}
	var year = value;
	var bReturn = __isLegalYear(year);
	return bReturn;
}
//121212   12��12��12��
function __checkStdTime(eSrc)
{
		var value = eSrc.value;
	if (value.length != 6) {
		sError = "ʱ�䳤�Ȳ���";
		return false;
	}
	if (!__isNum(value)) {
		sError = "���ǺϷ���ʱ��";
		return false;
	}
	var hour = value.substring(0,2);
	var minute = value.substring(2,4);
	var second = value.substring(4,6);
	var bReturn = __isLegalTime(hour, minute, second);
	return bReturn;
}
//������У��2004/05/01
function __checkSlashStdDate(eSrc)
{
	var value = eSrc.value;
	if (value.length != 10) {
		sError = "���ڳ��Ȳ���";
		return false;
	}
	var re = /\d{4}\/\d{2}\/\d{2}/;
	if (!re.test(value)) {
		sError = "����ȷ�����ڸ�ʽ����ȷ��ʽΪYYYY/MM/DD";
		return false;
	}
	var year = value.substring(0,4);
	var month = value.substring(5,7);
	var day = value.substring(8,10);
	var bReturn = __isLegalDate(year,month,day);
	if (false == bReturn)
		return false;
	return true;
}

//�Ƿ�Ϸ������ڣ�month ����0
function __isLegalDate(year,month,day)
{
     if(year < 0 || year > 9999)
     {
     	sError = "��ݴ���";
     	return false;
     }
     if(month < 1 || month > 12)
     {
     	sError = "�·ݲ���С��1��Ҳ���ܴ���12";
     	return false;
     }
     if(day < 1)
     {
     	sError = "���ڲ���С��1";
     	return false;
     }
     if (day > 31)
     {
     	sError = "���ڲ��ܴ���31";
     	return false;
     }
     if(month == 2)
     {
     	if( __isLeapYear(year)  )
     	{
        	if(day > 29)
        	{
         		sError = "���꣬2�·����ڲ��ܴ���29";
         		return false;
         	}
		}
		else
		{
			if(day > 28)
			{
				sError = "�����꣬2�·����ڲ��ܴ���28";
				return false;
			}
		}
	} else
	{
		var d1, m1=month;
		if(m1 > 7)
			m1 = m1 + 1;
		d1 = 30 + m1 % 2;
		if(day > d1)
		{
			sError = "�������ڲ��ܴ���" + d1 +"��";
			return false;
		}
	}
	return true;
}
//�Ƿ�������
function __isLeapYear(year)
{
	if( ( year % 400 == 0) || (( year % 4 == 0) && (year % 100 != 0)) )
		return true;
	else
		return false;
}
//�Ƿ�Ϸ����£�
function __isLegalMonth(year,month)
{
     if(year < 0 || year > 9999)
     {
     	sError = "��ݴ���";
     	return false;
     }
     if(month < 1 || month > 12)
     {
     	sError = "�·ݲ���С��1��Ҳ���ܴ���12";
     	return false;
     }
	return true;
}
//�Ƿ�Ϸ����ꣿ
function __isLegalYear(year)
{
     if(year < 0 || year > 9999)
     {
     	sError = "��ݴ���";
     	return false;
     }
	return true;
}
//�Ƿ��ǺϷ���ʱ����
function __isLegalTime(hour, minute, second)
{
     if(hour < 0 || hour > 23)
     {
     	sError = "ʱ�Ӵ���";
     	return false;
     }
     if(minute < 0 || minute > 59)
     {
     	sError = "���Ӵ���";
     	return false;
     }
     if (second < 0 || second > 59)
     {
     	sError = "���Ӵ���";
     	return false;
     }
	return true;
}

/*********************************
*�ж��Ƿ��ǵ��ֽ�a-zA-Z0-9_-֮��
**********************************/
function isSingleFormat(str)
{
  var singleFormat = /^[.a-zA-Z0-9_-]+$/;
  if(!singleFormat.test(str)) 
	{
	  	return false;
	 }
	 return true;
}

/**************************************
*�ж��Ƿ��Ǹ���
***************************************/
function checkNegative(arg){		
	if(isNaN(arg)){
		if(arg.value<0){
			return true;
		}
		return false;
	}
	if(arg*1<0){
		return true;
	}
	return false;
}
//ʱ���ʽ�ļ���  �ʺ���20:30:59��ʽ
function __checkTime(s){
   var rs= /^\d{2}\:\d{2}\:\d{2}$/;
   if(!rs.test(s)){
     	return false;
   }	
   var arr = s.split(":");
   if(parseInt(arr[0])>24||parseInt(arr[1])>59||parseInt(arr[2])>59){
   	return false;
   }
   return true;
}
//�ȽϿ�ʼ���ںͽ������� baixch���20050416
function __compareDate(startDate,endDate){
  var startD  = startDate.value;
  var endD = endDate.value;
 if(startD==endD){
   return 0;
   }
 else if(startD>endD){
   return -1 
  }
 else if(startD<endD){
   return 1; 
  }   
} 
//��������ʽ  һ������´���ֻ����Ӣ���ַ����������,���ܺ�������
function __checkCode(s){
 	var rs = /^([0-9]|[a-z]|[A-Z])+$/;
 	if(!rs.test(s)){
 		return false;
 	}
 	return true;
}

//����IP��ַ�ĺϷ��� ����:10.162.11.104����ȷ��IP��ַ
function __checkIp(s){
  var re = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
  if(!re.test(s)){
     return false;
  }
  var ips=s.split(".");
  if((ips[0].indexOf("0")==0)||(ips[1].indexOf("0")==0)||(ips[2].indexOf("0")==0)||(ips[3].indexOf("0")==0)){
     return false; 
  }
  if(ips[0]>255 || ips[1]>255|| ips[2]>255 || ips[3]>255 ){
     return false;
  }
  return true;
}

//�����й��ʱ� ֻ����6λ����
function __checkChinaPostCode(s){
	var rs=/\d{6}/;
	if(!rs.test(s)){
		return false;
	}
        return true;
}
//�ж��ַ����Ƿ��ǹ̶��绰�����ַ����������򷵻�true�����򷵻�false
function __isFixedTelephone(s){
	return /^(\d{2,4}-)?((\(\d{3,5}\))|(\d{3,5}-))?\d{3,8}(-\d+)?$/g.test(s);
}
//�ж��ַ����Ƿ����ֻ��绰�����ַ����������򷵻�true�����򷵻�false
function __isMobileTelephone(s) {
	return /^1(3\d|(58|59|56|52|53|50))\d{8}$/g.test(s);
}
//�ж��ַ����Ƿ��ǵ绰�����ַ����������򷵻�true�����򷵻�false
function __isTelephone(s) {
	return __isMobileTelephone(s) || __isFixedTelephone(s);
}
//�ж��ַ����Ƿ�����ĸ��ͷ������Ϊ��ĸ���������
function __checkCodeFormat(s) {
	return /^[A-Za-z][A-Za-z0-9_]{0,}$/g.test(s);
}
/**
* XMLHTTP �ύ
**/
//ͨ��xmlhttpȡֵ�������ַ���
function getHtmlStringByXmlhttp(url)
	{
	if(url==null)
	{
	alert("���ݵ�URL����Ϊ�գ�");
	return;
	}
	try
		{
		var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if(xmlhttp==null)
		{
		alert("����XMLHTTP����ʧ��!"+ex.description);
		return ;
		}
		xmlhttp.open("POST",url,false);
		xmlhttp.send();
		return xmlhttp.responseText;
		}catch(ex)
		{
		alert("XMLHTTP���󴫵�����ʧ��!"+ex.description);
		return "";
		}
	}
	
function getArrayByXmlHttp(urll){
	 var values=getHtmlStringByXmlhttp(urll);	
     if(values.indexOf("û�м�¼")>-1){
       return -1;
     }
	 var arr=new Array();
	 var obs=values.split("@|@");	 
	 for(var i=0;i<obs.length;i++){	 
	 	 obsv=obs[i].split("@,@");	
	 	 arr[i]=obsv;		 	
	 } 
	 return arr;
}

function setButtonDisabled(condition) {
   var canDisable = (window.event != null && window.event.srcElement != null && window.event.srcElement.tagName.toLowerCase() == "button");
   if (canDisable&&condition) {
    window.event.srcElement.disabled = true;
   }
   }
//����У��
	function checkPassword(check_password,updatePassword){
		if(check_password!=""){
			if(!check_password.test(updatePassword)){
	 			return false;
	 		} 
		}	
		return true;	
	}
