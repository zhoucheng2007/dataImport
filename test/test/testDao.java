package test;

import java.util.List;

import org.junit.Test;

import com.blogzhou.dao.PubStruDao;
import com.blogzhou.entity.PubStru;

public class testDao {

	@Test
	public void mainMethod() {
		PubStruDao psd=new PubStruDao();
		List<PubStru> list=psd.getAll();
		System.out.println(list.size());
		System.out.println(list.get(0).getStruId());
	}

}
