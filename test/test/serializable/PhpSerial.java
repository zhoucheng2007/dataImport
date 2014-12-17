package test.serializable;

import com.util.SerializerUtil;
/**
 * 序列化與返序列化累
 * 
 * @author feng
 *su.unserialize(s,String.class)
 *反系列化一定要加上類名否則會報錯
 *@date 2014年11月12日12:57:28
 */
public class PhpSerial {

	public static void main(String[] args) {
		SerializerUtil su=new SerializerUtil();
		String z="@:menuxml=http://10.36.0.17:9081/v3/menuxml.cmd;appType=2;supbrowser=7";
		String s=su.serialize(z);
		System.out.println(s);
		System.out.println(su.unserialize(s,String.class));	

		  TestSerial ts = new TestSerial();  
		  String s1=su.serialize(ts);
		 // System.out.println(s1);
		  TestSerial ts2=su.unserialize(s1, TestSerial.class);
		 // System.out.print(ts2.version);
		
	}

}
