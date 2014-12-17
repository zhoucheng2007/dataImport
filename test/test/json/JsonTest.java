/**
*
 */
package test.json;

import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

/**
 * 下午3:31:31
 * mailto: zhoucheng2007@gmail.com 
 */
public class JsonTest {
	@Test
	public void think() {
		JSONObject menuObj = new JSONObject();
		menuObj.put("number", 123);
		System.out.println(menuObj.toString());
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.put("7");
		jsonArray.put("8");
		jsonArray.put("9");
		menuObj.put("array", jsonArray);
		System.out.println(menuObj.toString());
		
		JSONObject s = new JSONObject();
		s.put("dong", "dong");
		menuObj.put("dong", s);
		System.out.println(menuObj.toString());
		/**
		 * PrintWriter out = rep.getWriter();
		 * 	out.write(menuObj.toString());
		 * out.close();
		 */
	}
}
