/**
*
 */
package test.springheadannotition;

/**
 * 下午3:06:33
 * mailto: zhoucheng2007@gmail.com 
 */
public class BeanCase {

	String name;
	
	String jdbcUrl;	

	public String getName() {
		return name;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public BeanCase(String name, String jdbcUrl) {
		this.name = name;
		this.jdbcUrl = jdbcUrl;
	}
	
	public BeanCase() {
	}
	
}
