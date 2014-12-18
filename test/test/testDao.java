package test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blogzhou.dao.PubUsersDao;
import com.blogzhou.entity.PubUsers;

public class testDao {
	
	PubUsersDao pubUserDao;
	
	@Before
	public void beforTest(){
		pubUserDao=new PubUsersDao();
	}

	@Test
	public void mainMethod() {
		List<PubUsers> list=pubUserDao.getAll();
		System.out.println(list.size());
		System.out.println(list.get(0));
	}

	
	@After
	public void tearDown(){
		pubUserDao=null;
	}
}
