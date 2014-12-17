package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.blogzhou.dao.PubStruDao;
import com.blogzhou.entity.PubStru;
import com.blogzhou.manager.BaseImportManager;
import com.blogzhou.manager.EmployerImportManager;
import com.blogzhou.manager.OraginationImportManager;

@SuppressWarnings(value = { "unchecked" })
public class organizationImport {

	private static final Log log = LogFactory.getLog(organizationImport.class);
	
	@Test
	public  void  ImportOrganitionData() throws Exception{
		BaseImportManager dim=new OraginationImportManager();
        
        List<PubStru> infos= dim.importEmployeeByPoi();
        
        PubStruDao psh=new PubStruDao();
        
        for(PubStru ps:infos){
        	psh.attachDirty(ps);
        	System.out.println("��֯�ṹIDΪ��"+ps.getStruId());
        }
        
        log.debug(infos.size());       
	}
	
	@Deprecated
	public void updateAll() throws InterruptedException{
		PubStruDao psh=new PubStruDao();
		List<PubStru> lists=psh.getAll() ;
		System.out.println(lists.size());
		for(PubStru ps:lists){
			if(ps.getStruId().compareTo("S0000000000000001609")<0){
				continue;
			}
			updatePath(ps);				
		}
	}
	
	public List<PubStru> updateAllInOneTran() throws InterruptedException{
		PubStruDao psh=new PubStruDao();
		List<PubStru> lists=psh.getAll() ;
		System.out.println(lists.size());
		List<PubStru> lists2=new ArrayList<PubStru>();
		for(PubStru ps:lists){
			if(ps.getStruId().compareTo("S0000000000000001609")<0){
				continue;
			}
			PubStru ps3=updatePathOnly(ps);				
			lists2.add(ps3);
		}
		return lists2;
	}
	
	/**
	 * 
	public void getPubStr(){
		PubStruHome psh=new PubStruHome();
		PubStru ps=psh.findById("S0000000000000000020");
		ps.setStruOrder(399);
		psh.attachDirty(ps);
		System.out.println(ps.getStruId());
	}
		 */
	
	/**
	 * ��ƴװ���·���ͼ���д����ݿ�
	 * @param struid
	 * @return
	 */
	public void updatePath(PubStru ps){
		PubStruDao psh=new PubStruDao();
		String s=" ";
		int level=0;
		PubStru ps2=new PubStru();
		ps2=getStruPath(ps,s,level,ps2);
		//ȥ��rootId#S0000000000000000001#113604#11360403#113604030201#113604030202#
		//ĩβ��#��
		String path=ps2.getStruPath().substring(0,ps2.getStruPath().length()-2);

		ps.setStruPath(path);
		ps.setStruLevel(ps2.getStruLevel());
		psh.attachDirty(ps);
		System.out.println(ps.getStruId());
	}
	
	/**
	 * ��ƴװ���·���ͼ���д����ݿ�
	 * @param struid
	 * @return
	 */
	public PubStru updatePathOnly(PubStru ps){
		PubStruDao psh=new PubStruDao();
		String s=" ";
		int level=0;
		PubStru ps2=new PubStru();
		ps2=getStruPath(ps,s,level,ps2);
		//ȥ��rootId#S0000000000000000001#113604#11360403#113604030201#113604030202#
		//ĩβ��#��
		String path=ps2.getStruPath().substring(0,ps2.getStruPath().length()-2);

		ps.setStruPath(path);
		ps.setStruLevel(ps2.getStruLevel());
		return ps;
	}
	
	/**
	 * ��ƴװ���·���ͼ���д����ݿ�
	 * @param struid
	 * @return
	 */
	public PubStru updatePath(String struid){
		PubStruDao psh=new PubStruDao();
		PubStru ps=psh.findById(struid);
		String s=" ";
		int level=0;
		PubStru ps2=new PubStru();
		ps2=getStruPath(ps,s,level,ps2);
		//ȥ��rootId#S0000000000000000001#113604#11360403#113604030201#113604030202#
		//ĩβ��#��
		String path=ps2.getStruPath().substring(0,ps2.getStruPath().length()-2);

		ps.setStruPath(path);
		ps.setStruLevel(ps2.getStruLevel());
		psh.attachDirty(ps);
		return ps2;
	}
	
	public PubStru getStruPath(PubStru ps,String s,int level,PubStru ps2){

		if(ps.getOrganId().equals("113600")){
			//��ڵ�
			ps2.setStruPath("rootId#S0000000000000000001#"+ps2.getStruPath());
			ps2.setStruLevel(level+1);
		}else{
			PubStruDao psh=new PubStruDao();
			String parentId=ps.getParentId();
			PubStru parentPubStru=psh.findById(parentId);
			if(null==parentPubStru){
				ps2.setStruPath("the parent node was null!!!!");
				return ps2;
			}
			//String path=ps.getOrganId()+"#"+s;
			String path=ps.getStruId()+"#"+s;
			level=level+1;
			ps2.setStruPath(path);
			ps2.setStruLevel(level);
			getStruPath(parentPubStru,path,level,ps2);
		}	
		return ps2;
	}
}
