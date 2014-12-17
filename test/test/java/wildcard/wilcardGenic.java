package test.java.wildcard;

import java.util.ArrayList;
import java.util.List;
/**
 * 通配符范型测试
 * 通配符范型用？表示
 * @author feng
 *
 */
public class wilcardGenic {

	public wilcardGenic() {
		// TODO Auto-generated constructor stub
	}

	public void genic() {
		Integer iNums[]= {1,2,3,4,5};
		List<? extends Number> cl=null;
		cl=new ArrayList<Integer>();
	}
	
}
