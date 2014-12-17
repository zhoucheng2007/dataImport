package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.blogzhou.dao.PubOrganDao;
import com.blogzhou.dao.PubStruDao;
import com.blogzhou.entity.PubOrgan;
import com.blogzhou.entity.PubStru;

public class OrganforStruManager {

	PubOrganDao poh=new PubOrganDao();
	@Test
	public void OrganToStru() {
		PubStruDao psh=new PubStruDao();
		List<PubStru> lists=psh.getAll() ;
		System.out.println(lists.size());
		for(PubStru ps:lists){
			//if(ps.getStruId().compareTo("S0000000000000001446")<0){
			if(ps.getStruId().compareTo("S0000000000000009836")<0){
				//updateOrgan(ps);		
				continue;
			}
			List list=new ArrayList();
			//重复的记录过滤掉
			list.add("1136100100000340");
			list.add("1136100100000341");
			list.add("1136100100000342");
			list.add("1136100100000343");
			list.add("1136100100000344");
			list.add("1136100100000345");
			list.add("1136100100000346");
			list.add("1136100100000347");
			list.add("1136100100000348");
			list.add("1136100100000349");
			list.add("1136080100000294");
			list.add("1136080100000293");
			list.add("1136070100000360");
			if(list.contains(ps.getOrganId())||ps.getOrganId()==null) {
				continue;
			}
			
			updateOrgan(ps);				
		}
	}

	/**
	 * ��ƴװ���·���ͼ���д����ݿ�
	 * @param struid
	 * @return
	 */
	public void updateOrgan(PubStru ps){

		PubOrgan po=new PubOrgan();	
		po.setCtime(new Date());
        char u='1';
        po.setInUse(u);
        po.setOrganCode(ps.getOrganId());
		po.setOrganId(ps.getOrganId());
		po.setOrganName(ps.getStruName());
		if(String.valueOf(ps.getIsLeaf()).equals("1")) {
			po.setPubOrganType("8");
		}else {
			po.setPubOrganType("2");
		}
				if(ps.getStruName().length()>20) {
					po.setShortName("");
				}else {
					po.setShortName(ps.getStruName());				
				}

		poh.attachDirty(po);
		System.out.println(ps.getStruId());
	}
	
	
}
