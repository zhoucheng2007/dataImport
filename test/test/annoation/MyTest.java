package test.annoation;
/**
 * 
 */

/**
 * @author feng
 *
 */
public class MyTest {

	@MyAnnotation(world = "shanghai",array={1,2,3})
	 public void output()  
	 {  
	  System.out.println("output something!");  
	 }  
	
}
