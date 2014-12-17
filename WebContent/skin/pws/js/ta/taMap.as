package {
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.external.ExternalInterface;
	import flash.filters.GlowFilter;
	//主类
	public class taMap extends Sprite {
		private var loader:URLLoader=new URLLoader();
		private var configXml:XML;
		private var stageW:Number;
		private var stageH:Number;
		private var infoBoxW:Number;
		private var infoBoxH:Number;
		private var cssReq:URLRequest;
		private var cssLoader:URLLoader;
		private var url:String="";
		//构造函数，入口
		public function taMap() {
			infobox.visible=false;
			var obj:Object=root.loaderInfo.parameters;
			url=obj["xmlPath"];
			loadXml(url);
			stageW=stage.width;//获取舞台和信息框的大小，以用于控制信息框的位置
			stageH=stage.height;
			infoBoxW=infobox.width;
			infoBoxH=infobox.height;
			//对外提供一个方法，供JS调用以刷新数据，444页
			ExternalInterface.addCallback("refreshMapData",refreshData);
		}

		//读取xml配置文件,429页
		function loadXml(url:String):void {
			var request:URLRequest=new URLRequest(url);
			loader.load(request);
			loader.addEventListener(Event.COMPLETE,onComplete);
		}
		//解析xml

		function onComplete(event:Event):void {
			configXml=new XML(loader.data);
			var message:String=configXml.message;
			mingcheng.visible=true;
			mingcheng.text=message;
			//遍历xml,46页
			for (var i:int = 0; i < configXml.area.length(); i++) {
				var mapId:String=configXml.area[i].@id;//访问XML元素和属性，432页
				var color:String=configXml.area[i].color;
				var name:String=configXml.area[i].name;
				var info:String=configXml.area[i].info;
				this["mov_"+mapId].gotoAndStop(color);//处理MovieClip，让其显示对应的颜色，这里的this指舞台，gotoAndStop参考144页
				this["mov_"+mapId].myFilter=this["mov_"+mapId].filters;
				this["mov_"+mapId].myName=name;
				this["mov_"+mapId].mapId=mapId;
				this["mov_"+mapId].myInfo=info;
				this["mov_"+mapId].myMeasures=configXml.area[i].info.measure;
				this["mov_"+mapId].addEventListener(MouseEvent.MOUSE_OVER,mcOnMouseOver);//69页
				this["mov_"+mapId].addEventListener(MouseEvent.MOUSE_OUT,mcOnMouseOut);
				this["mov_"+mapId].addEventListener(MouseEvent.CLICK,mcOnClick);////////////////
			}
		}

		//每个地区的mouseover方法
		function mcOnMouseOver(event:MouseEvent):void {
			infobox.visible=true;
			infobox.addEventListener(Event.ENTER_FRAME,moveInfoBox);//159页
			//给mouseOver对象添加一个滤镜效果
			var filter:GlowFilter=new GlowFilter();
			filter.color=0xFF6100;
			filter.inner=true;
			filter.strength=3;
			var filtersArr:Array=new Array(filter);
			event.target.filters=filtersArr;
			infobox.mapSightTitle.text=event.target.myName;
			var measureList:XMLList=event.target.myMeasures;
			for (var j:int=0; j<measureList.length(); j++) {
				infobox["lingxing"+j].text=measureList[j].mName;
				infobox["shuzhi"+j].text=measureList[j].mValue;
				infobox["danwei"+j].text=measureList[j].mUite;
				infobox["paiming"+j].text=measureList[j].mLabel;
				infobox["mingci"+j].text=measureList[j].mRank;
			}
		}
		function mcOnMouseOut(event:MouseEvent):void {
			infobox.visible=false;
			event.target.filters=event.target.myFilter;
		}
		//计算信息框的位置
		function moveInfoBox(evt:Event):void {//159页
			if ((mouseX+infoBoxW)>stageW) {
				infobox.x=stageW-infoBoxW;
			} else {
				infobox.x=mouseX;
			}
			if ((mouseY+infoBoxH)>stageH) {
				infobox.y=mouseY-infoBoxH-5;
			} else {
				infobox.y=mouseY;
			}
		}

		//重新加载XML，以刷新数据
		function refreshData():void {
			loadXml(url);
		}

		function mcOnClick(event:MouseEvent):void {
			var mapId:String=event.target.mapId;
			ExternalInterface.call("forMapClick",mapId);
		}
	}
}