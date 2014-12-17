/**
 * 
 */
package test.serializable;

import java.io.Serializable;

/**
 * @author feng
 *
 */
public class TestSerial implements Serializable{

	public byte version = 100;  
	 
    public byte count = 0;

	@Override
	public String toString() {
		return "TestSerial [version=" + version + ", count=" + count + "]";
	}

    
    
}
