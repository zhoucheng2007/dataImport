package test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.blogzhou.dao.PubStruDao;
import com.blogzhou.entity.PubStru;
import com.blogzhou.manager.BaseImportManager;
import com.blogzhou.manager.EmployerImportManager;
import com.blogzhou.manager.EmployerOtherImportManager;

@SuppressWarnings(value = { "unchecked" })
public class employeeImport {

	private static final Log log = LogFactory.getLog(employeeImport.class);
	
	PubStruDao psh=new PubStruDao();
	
	//@Test
	public  void  ImportOrganitionData() throws Exception{
		
		//BaseImportManager dim=new EmployerImportManager();
		BaseImportManager dim=new EmployerOtherImportManager();
        
        List<PubStru> infos= dim.importEmployeeByPoi();
        
        
        for(PubStru ps:infos){
        	psh.attachDirty(ps);
        	System.out.println("组织机构代码为："+ps.getStruId());
        }
        
        log.debug(infos.size());       
	}
	
	@Test
	public void updateAll() throws InterruptedException{
		List<PubStru> lists=psh.getAll();
		System.out.println(lists.size());
		for(PubStru ps:lists){
			//if(ps.getStruId().compareTo("S0000000000000001744")<0){
				//if(ps.getStruId().compareTo("S0000000000000002221")<0){
					if(ps.getStruId().compareTo("S0000000000000002832")<0){				
				continue;
			}
					if(!ps.getStruPath().equals("rootId#1")){				
						continue;
					}
			updatePath(ps);				
		}
	}
	
	/**
	 * ��ƴװ���·���ͼ���д����ݿ�
	 * @param struid
	 * @return
	 */
	public void updatePath(PubStru ps){
		int level=0;
		PubStru ps2=getParentStru(ps);
		String path=ps2.getStruPath()+"#"+ps.getStruId();
		level=ps2.getStruLevel()+1;
		ps.setStruPath(path);
		ps.setStruLevel(level);
		psh.attachDirty(ps);
		System.out.println("����ɹ���"+ps.getStruId());
	}
	
	/**
	 * ��ȡ���ڵ�
	 * @param �ӽڵ�
	 * @return ���ڵ�
	 */
	public PubStru getParentStru(PubStru ps){
			String parentId=ps.getParentId();
			if(parentId=="") {
				return null;
			}
			String hql="from PubStru where organId="+parentId;
			PubStru parentPubStru=psh.getByHql(hql).get(0);
		     return parentPubStru;
	}
}
