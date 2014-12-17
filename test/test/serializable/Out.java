package test.serializable;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Out {

	public static void main(String[] args) throws Exception {
	    FileOutputStream fos = new FileOutputStream("temp.out");  
	    
	       ObjectOutputStream oos = new ObjectOutputStream(fos);  
	 
	       TestSerial ts = new TestSerial();  
	 
	       oos.writeObject(ts);  
	 
	       oos.flush();  
	 
	       oos.close();  

	}

}
