
function reportInit()
{

	if(DCellWeb1.Login( "�˳���³���", "", "13040257", "5000-1207-7711-6004" ) == 0)
		{ 
			alert("ע��ʧ��!");
		}
		
		filename=filename+setfilename();
		//alert(filename);
		var result=CellRpt.OpenFile(filename,"") ;

         	if(result!=1){
			alert("���ļ�����");
			}


		//���ô�ӡ��Ҳ�߾�
		CellRpt.PrintSetMargin(100,100,100,100);
		CellRpt.PrintSetAlign(1,0);
		CellRpt.Border=false;
		filldata();
		
		var num;  
		for(var sheet = 0 ; sheet <= CellRpt.GetTotalSheets();sheet++){
			for(m=1; m<=CellRpt.getRows(sheet); m++) {
				for(n=1; n<=CellRpt.getCols(sheet); n++) {
					CellRpt.SetCellTextStyle( n, m, sheet, 2 );
				}
				//��������п�ʹ�ñ���̫�����ܣ�������20050906�޸�Ϊ��ѣ�10
				if(isBestHeight){
				    num = CellRpt.GetRowBestHeight(m);
					CellRpt.SetRowHeight(1,num+10,m,sheet);
				}
			}
			for(n=1; n<=CellRpt.getCols(sheet); n++) {
			 	if(isBestWidth){
				num = CellRpt.GetColBestWidth(n);
				CellRpt.SetColWidth(1,num+10,n,sheet);
				}
			}
		}
		//update by lxy20020929
		CellRpt.Readonly=true;//Readonly����ʹCell�����ܱ༭��ͬʱҲ�������¹���ͷ
		CellRpt.WndBkColor = 0xFFFFFF;
		CellRpt.AllowDragDrop =false;
		//CellRpt.SetSelectMode(0,0);
		CellRpt.SetSelectMode(0,1);
		document.getElementById("Msg").style.display="none";
		document.getElementById("Content").style.display="block";
  }
