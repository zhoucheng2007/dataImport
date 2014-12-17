package test;

import java.util.List;

import org.junit.Test;

import com.blogzhou.dao.PubStruDao;
import com.blogzhou.dao.PubStruExtDao;
import com.blogzhou.entity.PubStru;
import com.blogzhou.entity.PubStruExt;

public class StruforStruExtManager {

	@Test
	public void StruToStruExt() {
		PubStruDao psh=new PubStruDao();
		List<PubStru> lists=psh.getAll() ;
		System.out.println(lists.size());
		int i=5697;
		for(PubStru ps:lists){
			//if(ps.getStruId().compareTo("S0000000000000001446")<0){
			if(ps.getStruId().compareTo("S0000000000000002808")<0){
				//updateOrgan(ps);		
				continue;
			}
			//��ڵ�ȥ��
			if(ps.getStruLevel()==1) {
				continue;
			}
			System.out.println(i);
			updateStruExt(ps,i);		
			System.out.println(i);
			i=i+2;
		}
	}

	/**
	 * ��ƴװ���·���ͼ���д����ݿ�
	 * @param struid
	 * @return
	 */
	public void updateStruExt(PubStru ps,int temp){
		PubStruExtDao pseh=new PubStruExtDao();
		PubStruExt pse=new PubStruExt();
		String s="";
        if(temp<10){
        	s="0000000000000000000"+temp;	                    	
        }else if(temp<100){
        	 s="000000000000000000"+temp;	
        }else if(temp<1000){
        	 s="00000000000000000"+temp;	
        }else if(temp<10000){
        	s="0000000000000000"+temp;	
        }else if(temp<100000){
        	s="000000000000000"+temp;	
        }
		
        

        
		//������
		pse.setId(s);
		pse.setPubStru(ps.getStruId());
		pse.setPubStruType("00");
		pse.setSrcId(ps.getOrganId());
		pse.setTargetId(ps.getParentId());
		pse.setType("00");
		pseh.attachDirty(pse);
		System.out.println("����ɹ�"+pse.getPubStru());
		
		
		PubStruExt pse2=new PubStruExt();
		
        temp=temp+1;
        if(temp<10){
        	s="0000000000000000000"+temp;	                    	
        }else if(temp<100){
        	 s="000000000000000000"+temp;	
        }else if(temp<1000){
        	 s="00000000000000000"+temp;	
        }else if(temp<10000){
        	s="0000000000000000"+temp;	
        }else if(temp<100000){
        	s="000000000000000"+temp;	
        }
		
		pse2.setId(s);
		//������
		pse2.setPubStru(ps.getStruId());
		pse2.setPubStruType("00");
		pse2.setSrcId(ps.getOrganId());
		pse2.setTargetId(ps.getOrganId().substring(0, 6));
		pse2.setType("01");
		
		pseh.attachDirty(pse2);
		System.out.println("����ɹ�"+pse2.getPubStru());
	}
	
	
}
