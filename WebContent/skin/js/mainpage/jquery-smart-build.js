
(function($) { 
	$.fn.smartBuild = function(options){
		var opts = $.extend({}, $.fn.smartBuild.defaults, options);
		
		var rows=opts.data;;
			

		var number=0,store=[];
		
		var html="";
		
		html+=buildRow(rows);

		
		$(this).html(html);
		
		$.ajaxSetup({  
		    async : false  
		});  
		for(var i=0;i<store.length;i++){
			
			var column=store[i];
			var template=column.template;
			if(template){
				$.post(template,function(data){
					data.url=encodeURIComponent(data.url);
					var str = Mustache.render(data, column);//模板渲染
					var $target=$("#app"+i);
					$target.html(str);
				})
			}
		}
		$.ajaxSetup({  
		    async : true  
		}); 

		
		function buildRow(rows){
			
			var html="";
			for(var i=0;i<rows.length;i++){
				html+="<div class='row clearfix'>";
				var columns=rows[i];
				html+=buildColumn(columns);
				html+="</div>";
			}
			return html;
		}
		function buildColumn(columns){
			var html="";
			for(var i=0;i<columns.length;i++){
				var column=columns[i];
				var span=column.span;
				html+="<div class='col-md-"+span+" column'>";
				html+=buildBox(column);
				html+="</div>";
			}
			return html;
		}
		
		function buildBox(column){
			var html="";
			html+="<div class='box'><div id='app"+number+"' class='inner'></div></div>";
			store.push(column);
			number++;
			return html;
		}
		
	}
	$.fn.smartBuild.defaults = {};  
})(jQuery);



