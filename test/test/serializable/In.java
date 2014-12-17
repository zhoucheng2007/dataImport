package test.serializable;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class In {

	public static void main(String[] args) throws Exception {

	       FileInputStream fis = new FileInputStream("temp.out");  
	 
	       ObjectInputStream oin = new ObjectInputStream(fis);  
	 
	       TestSerial ts = (TestSerial) oin.readObject();  
	 
	       System.out.println("version="+ts.version);  

	}

}
