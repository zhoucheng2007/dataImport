	
	/****************************************************************

	jQuery ���.

	����: �̶������


	���÷���:
	$('#myTable').fixEditgrid(
	    pCol, //�ɹ��������е��к�
	    splitColor, //(��ѡ)�̶��������������ķָ�����ɫ
	);


	****************************************************************/

	jQuery.fn.extend({ fixEditgrid: function(pCol,splitColor){
	    //���������
	    var scrW = 16;

	    //���÷ָ�����ɫ
	    if(!splitColor){
	       splitColor = '#333';
	    }

	    //�õ������
	    var t = $(this);
	    var pid = 'editgridfixbox';
	    
	    t.show();

	    //�õ����ʵ�ʴ�С
	    var tw = t.outerWidth(true);
	    var th = t.outerHeight(true);

	    //���ⲿ��һ��DIV,������ȡ������ʾ�����С
	    t.wrap("<div id='"+pid+"' ></div>");
	    var p = $('#'+pid);
	    p.css({
	        width: '100%',
	        height: '100%',
	        border: '0px',
	        margin: '0 0 0 0',
	        padding: '0 0 0 0'
	    });

	    //������ʾ�����С
	    t.hide();
	    var cw = p.outerWidth(true);
	    var ch = th+scrW;
	    
	    t.show();
	    
	    //�õ�����HTML����
	    var thtml = p.html();
        var table = p.clone(true);
	    //�ж��Ƿ���Ҫ�̶�����ͷ
	    if(tw<=cw && th<=ch){
	        return;
	    }
	    //�̶���Ԫ���λ��
	    var w1 = 0;
	    var h1 = 0;

	    var post = t.offset();

	    var p1, p2, p3, p4;
	        var pos = $("th",t).eq(pCol).offset();
	        w1 = pos.left - post.left;

	        var tmp='<table style="background: #ECE9D8;" ';
	        tmp+='border="0" cellspacing="0" cellpadding="0">';
	        tmp+='<tr><td valign="top" style="border-right: 1px solid '+splitColor+'">';
	        tmp+='<div id="'+pid+'1"></div></td>';
	        tmp+='<td><div id="'+pid+'2"></div></td></tr>';
	        tmp+='</table>';

	        p.before(tmp);
	        
	        $('div[id^='+pid+']').each(function(){
	            $(this).css({
	                background: 'white',
	                overflow: 'hidden',
	                margin: '0 0 0 0',
	                padding: '0 0 0 0',
	                border: '0'
	            });
	        });
	        p1 = $('#'+pid+'1');
	        p2 = $('#'+pid+'2');
	        //�Ϸ�����
	        p1.html(thtml).css({width: w1-1, height: th});
	       // p1.append(table).css({width: w1-1, height: th});
	        p1.find('table:first').attr('edit1',true);
	        p1.find('table:first').attr('fixheader','header1');
	        $('thead tr th:gt('+(pCol-1)+')',p1).remove();
	        var trs = $("tbody tr",p1);
	        $('td:gt('+(pCol-1)+')',trs).remove();
	        //������
	        p2.append(p).css({
	            width: cw-w1, 
	            height: ch+1,
	            overflow: 'auto'
	        });
	        t.css({
	            position: 'relative',
	            top: 0,
	            left: 0
	        });
	        $('thead tr th:lt('+pCol+')',p2).remove();
	        var tr2s = $("tbody tr",p2);
	        $('td:lt('+pCol+')',tr2s).remove();
	        p2.find('table:first').attr('edit2',true);
	        p2.find('table:first').attr('fixheader','header2');
	        p.css({width: tw-w1, height: th, overflow: 'hidden'});
	        $("table[edit1]").editgridFixedHeader();
	        $("table[edit2]").editgridFixedHeader();
	        
	    
	}});
