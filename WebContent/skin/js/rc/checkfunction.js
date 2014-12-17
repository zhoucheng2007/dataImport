function myReturn(s)
{
    window.location=s;
}

var today = new Date();
var sError="";

function __CommCheckInput() {
	var eSrc = null;
	var bReturn = false;
	sError = "";
	for (var i = 0; i < document.all.tags("INPUT").length;i++) {
		eSrc = document.all.tags("INPUT")[i];
		if (eSrc.type == "text") {
			eSrc.value = trim(eSrc.value);
			bReturn = __CheckInput(eSrc);
			if (false == bReturn)
			{
				eSrc.focus();
				return false;
			}
			bReturn = __CheckId(eSrc);
			if (false == bReturn) {
				var s = "����ȷ������ֵ:" + "[" + ( eSrc.title == "" ? eSrc.name : eSrc.title ) +"]:[" + eSrc.value + "]";
				s += "\n";
				s += sError;
				window.alert(s);
				eSrc.focus();
				return false;
			}
		}
	}
	return true;
}

function __CheckId(eSrc)
{
	if (eSrc.readonly == true || eSrc.disabled == true)
		return true;
	if ("" == eSrc.value)
		return true;
	var id = eSrc.id;
	var i = id.indexOf("_");
	if (i > 0) {
		var len = id.length;
		var suffix = id.substring(i+1, len);
		if (suffix == "PrimaryKey") {
			var bReturn = __isQuoteIn(eSrc.value);
			if (true == bReturn) {
				sError = "�벻Ҫ�������Ż���˫����";
				return false;
			}
		}
		id = id.substring(0, i);
	}

	if (id == "") {
		return true;
	} else {
		if (id == "idShortDate") 	//��ʽΪ3/15/00;
		{

		}
		else if (id == "idStdDate") //��ʽΪ20000315
		{
			return __checkStdDate(eSrc);
		}
		else if (id == "idSlashStdDate")	//��ʽΪ2000/03/07
		{
				return __checkSlashStdDate(eSrc);
		}
		else if (id == "idNum")
		{
			return __checkNum(eSrc);
		}
		else if (id == "idFloat")
		{
			return __checkFloat(eSrc);
		}
		else if (id == "idStdTime")
		{
			return __checkStdTime(eSrc);	//��ʽΪhhmmss
		}
	}
	return true;
}

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
	//var month = value.substring(4,6) - 1;
	var month = value.substring(4,6);//update by fanggsh 20051124
	var bReturn = __isLegalMonth(year,month);
	return bReturn;
}
function __checkMonth(eSrc)
{
    var value = eSrc.value;
    if (!__isNum(value)) {
		sError = "����������";
		return false;
	}
    if(value < 0 || value > 12)
     {
     	sError = "�·ݲ���С��1��Ҳ���ܴ���12";
     	return false;
     }
   	return true;
}
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
function __checkTime(eSrc)
{
		var value = eSrc.value;
	if (value.length != 8) {
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
	var month = value.substring(5,7) - 1;
	var day = value.substring(8,10);
	var bReturn = __isLegalDate(year,month,day);
	if (false == bReturn)
		return false;
	return true;
}

function __checkNum(eSrc)
{
	var value = eSrc.value;
	return __isNum(value);

}

function __checkFloat(eSrc)
{
	var value = eSrc.value;
	return __isFloat(value);
}

function __CheckInput(eSrc)
{
	if (eSrc.className == "clsReadonly" || eSrc.className == "clsDisabled")
		return true;
	if (eSrc.className == "clsCanInput")
	{
		if (eSrc.value == "") {
			return true;
		}
	}
	if (eSrc.className == "clsMustInput")
	{
		if (eSrc.value == "") {
			var s = "�����롰"+( eSrc.title == "" ? eSrc.name : eSrc.title )+"����ֵ";
			window.alert(s);
			return false;
		}
	}
	return true;
}

function __isNumChar(ch)
{
	return (ch >= '0' && ch <= '9');
}

function __isBlankChar(ch)
{
	//ȥ����'\v';20020522   chenmq
	return (ch == ' ' || ch == '\f' || ch == '\n' || ch == '\r' || ch=='\t');
}

function ltrim(s)
{
		var len = s.length;
		if (len < 1)
			return "";
		var i = 0;
		while ( i < len && __isBlankChar(s.charAt(i++)) )
			; //ʲôҲ����
		return s.substring(--i, len);
}

function rtrim(s)
{
	var len = s.length;
	if (len < 1)
		return "";
	var i = len;
	while ( i > -1 && __isBlankChar(s.charAt(--i)) )
		; //ʲôҲ����
	return s.substring(0, ++i);

}

function trim(s)
{
	var s1 = ltrim(s);
	return (rtrim(s1));
}

function __isLeapYear(year)
{
	if( ( year % 400 == 0) || (( year % 4 == 0) && (year % 100 != 0)) )
		return true;
	else
		return false;
}

//�Ƿ�Ϸ������ڣ�month ����0
function __isLegalDate(year,month,day)
{
     if(year < 1900 || year > 3000)
     {
     	sError = "��ݴ���";
     	return false;
     }
     if(month < 0 || month > 12)
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
//�Ƿ�Ϸ����£�
function __isLegalMonth(year,month)
{
     if(year < 1900 || year > 3000)
     {
     	sError = "��ݴ���";
     	return false;
     }
     //if(month < 0 || month > 12)
     if(month < 1 || month > 12)//update  by fanggsh 20051124
     {
     	sError = "�·ݲ���С��1��Ҳ���ܴ���12";
     	return false;
     }
	return true;
}
//�Ƿ�Ϸ����ꣿ
function __isLegalYear(year)
{
     if(year < 1900 || year > 3000)
     {
     	sError = "��ݴ���";
     	return false;
     }
	return true;
}
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

function __isNum(s)
{
	var re = /^[+|-]*[0-9]+$/;
	return re.test(s);
}

function __isFloat(s)
{
	if (__isNum(s)) return true;
	var re = /^[+|-]{0,1}\d*\.\d*$/;
	return re.test(s);
}

function _isEFloat(s)
{
    var re = /^[+|-]{0,1}\d*\.[\.\d+]{0,1}\e[-|+]{0,1}\d+$/;
    return re.test(s);
}


function __isQuoteIn(s)
{
	var re = /\"|\'|\��|\��|\��|\��/;
	return re.test(s);
}

function tst_QuoteIN()
{
	__isQuoteIn("'");
	__isQuoteIn("\"");
}

function tst_IsNum()
{
	__isNum("0");
	__isNum("");
	__isNum("1 1");
	__isNum("111");
	__isNum("aa");
	__isNum("1a1");
	__isNum("a1a");
}

function tst_IsFloat()
{
	__isFloat("0");
	__isFloat("0.");
	__isFloat(".0");
	__isFloat("0.0");
	__isFloat("000");
	__isFloat("1111.1111");
	__isFloat("11.1");
	__isFloat("1.111");
}

function tst_trim(s)
{
	var s1 = "aabb";
	var s2 = " aabb";
	var s3 = "aabb ";
	var s4 = " aa bb ";
	var s5 = "  aa  bb   ";
	var s6 = "";
	var s7 = "      ";
	var s8 = " ";
	var s9 = "a";
	var s = trim(s1);
	window.alert("trim(" + s1 + ")=[" + s +"]");
	s = trim(s2);
	window.alert("trim(" + s2 + ")=[" + s +"]");
	s = trim(s3);
	window.alert("trim(" + s3 + ")=[" + s +"]");
	s = trim(s4);
	window.alert("trim(" + s4 + ")=[" + s +"]");
	s = trim(s5);
	window.alert("trim(" + s5 + ")=[" + s +"]");
	s = trim(s6);
	window.alert("trim(" + s6 + ")=[" + s +"]");
	s = trim(s7);
	window.alert("trim(" + s7 + ")=[" + s +"]");
	s = trim(s8);
	window.alert("trim(" + s8 + ")=[" + s +"]");
	s = trim(s9);
	window.alert("trim(" + s9 + ")=[" + s +"]");
}


//���س�����ʱ,/=47,*=42,+=43
function keypress(form0)
{
	if(event.keyCode==13||event.keyCode==42)	//�س�,*
	{
		var oEl = event.srcElement;
		var name=oEl.name;
		if(form0.tab==null) return false;
		var tab = form0.tab.value;
		if(name==null||tab==null)
		{
			event.keyCode=0;
			return false;
		}
		var ie = StringTokenizer(tab,",");
		for(var i=0;i<ie.length;i++)
		{
			if(name!=ie[i]) continue;
			if(i==ie.length-1)		//���һ����һ�����ύ
				return;		//���޸�keyCode
			var nextname=ie[i+1];
			var eSrc=getInputByName(nextname);
			if(eSrc==null) return false;
			//������input��search��ť�����Զ��ύ
			if((nextname.substring(nextname.length-6,nextname.length)=="search"||nextname.substring(nextname.length-4,nextname.length)=="help") && event.keyCode==13)
			{
				eSrc.click();
				return false;
			}else{
				//Ҫ����search��ť
				if(nextname.substring(nextname.length-6,nextname.length)=="search"||nextname.substring(nextname.length-4,nextname.length)=="help"){
					if(i<ie.length-1){
						nextname=ie[i+2];
						eSrc=getInputByName(nextname);
						if(eSrc==null) return false;
					}
				}

				eSrc.focus();
				if(eSrc.type=="text"||eSrc.type=="submit")
					eSrc.select();
				else if(eSrc.type=="select")
					eSrc.click();
				event.keyCode=0;
				return false;
			}
		}
		event.keyCode=0;
		return false;
	}else if(event.keyCode==47||event.keyCode==43)		//��'/'��'+'��Ϊ�ύ��
	{
		var oEl = event.srcElement;
		var name=oEl.name;
		if(form0.tab==null) return false;
		var tab = form0.tab.value;
		if(name==null||tab==null)
		{
			event.keyCode=0;
			return false;
		}
		var ie = StringTokenizer(tab,",");
		var submitname = ie[ie.length -1];
		var eSrc=getInputByName(submitname);
		if(eSrc==null) return false;
		eSrc.click();
		return false;
	}

}
//JavaScript�����麯��
function newArray(){
	this.length = newArray.arguments[0];
//	for (var i = 0; i < this.length; i++)
//		this[i] = newArray.arguments[i]
}
//���ַ�����ָ���ķָ����ָ�Ϊ�ַ�������
function StringTokenizer(str,delim)
{
	if(str==null) return null;
	var count=0;
	var pos=-1;
	do{
		count++;
		pos=str.indexOf(delim,pos+1);
	}while(pos>=0)
	var ret = new Array(count);
	pos=0;
	var end;
	for(var i=0;i<count;i++){
		end=str.indexOf(delim,pos);
		if(end<0) end=str.length;
		ret[i]=str.substring(pos,end);
		pos=end+1;
	}
	return ret;
}
function getInputByName(name)
{
	for (var j = 0; j < document.all.tags("INPUT").length;j++) {
		var eSrc = document.all.tags("INPUT")[j];
		if (eSrc.name==name) return eSrc;
	}
	for (var j = 0; j < document.all.tags("SELECT").length;j++) {
		var eSrc = document.all.tags("SELECT")[j];
		if (eSrc.name==name) return eSrc;
	}
	return null;
}
function getCurrentDate()
{
	var date = new Date();
	var year = ""+date.getFullYear();
	var month = date.getMonth()+1;
	if(month<10){ month="0"+month;}
	var day = date.getDate();
	if(day<10){ day = "0"+day;}
	return year+month+day;
}

function addKey(keyValue,buttonName) {
	f = function () {
		if (window.event.keyCode == keyValue && window.event.ctrlKey) {
			window.event.returnValue = false;
			var oBtn = document.getElementsByTagName("INPUT");
			for (var i=0;i<oBtn.length;i++)
			{
				if ( (oBtn[i].type == "button" || oBtn[i].type=="submit" ) )
				{
		            var ie = StringTokenizer(buttonName,",");
				    for (var j=0; j < ie.length; j++)
				    {
				        if (oBtn[i].value == ie[j])
				        {
					        oBtn[i].click();
					        return;
					    }
					}
				}
			}
		}
	}
	document.attachEvent("onkeydown",f);
}

function myPrint()
{
var tst = document.all.tblWorkArea.innerHTML ;
printWindow=window.open("","printWindow");
printWindow.document.body.innerHTML = tst;
printWindow.print();
}

function __beChecked(elem) {
	if (typeof(elem.length) == "undefined") {
        if (elem.checked == true) {
            return 1;
        } else {
            return 0;
        }
    } else {
        var ret = 0;
        var len = elem.length;
        for (var i = 0; i < len; i++) {
            if (elem[i].checked == true) {
                ret++;
            }
        }
        return ret;
    }
}
function checkSearch(){
	var stbl = document.getElementById("tblSearch");
	for(i=0;i<stbl.rows.length;i++){
		for(j=0;j<stbl.rows[i].cells.length;j++){
			if(stbl.rows[i].cells[j].children[0] != null){
				if(stbl.rows[i].cells[j].children[0].type=="text"){
					if(__isQuoteIn(stbl.rows[i].cells[j].children[0].value)){
						alert("��ѯ�������ܴ������Ż����ţ�");
						return false;
						return;
					}
				}
			}
			
		}
	}
	return true;
	
}	
function checkTurnPage(){
	var numtext = document.forms[0].pageNumber;
	var num = document.forms[0].pageNumber.value;
	if(trim(num) == ""){
		numtext.value = "";
		numtext.focus();
		return false;
	}
	if(isNaN(trim(num))){
		alert("����������ҳ����ҳ��");
		numtext.select();
		numtext.focus();
		return false;
	}
	else{
		if(num.indexOf(".") != -1){
			alert("����������ҳ����ҳ��");
			numtext.select();
			numtext.focus();
			return false;
		}else{
			document.forms[0].pageNumber.value = trim(num);
			return true;
		}
	}

}	