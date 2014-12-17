/**
*
 */
package com.util.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogzhou.common.configuration.ConfigurationRegister;

/**
 * 下午2:30:49
 * mailto: zhoucheng2007@gmail.com 
 * java jndi服务的调用示例代码
 * 
 * jndi是Java 命名和目录接口（Java Naming and Directory Interface，JNDI）的简称.从一开始就一直是 Java 2平台企业版的核心技术之一。在JMS，JMail,JDBC,EJB等技术中，就大量应用的这种技术。JNDI可访问的现有的目录及服务有：DNS、XNam 、Novell目录服务、LDAP(Lightweight Directory Access Protocol 轻型目录访问协议)、 CORBA对象服务、文件系统、Windows XP/2000/NT/Me/9x的注册表、RMI、DSML v1&v2、NIS。为什么会有jndi
jndi诞生的理由似乎很简单。随着分布式应用的发展，远程访问对象访问成为常用的方法。虽然说通过Socket等编程手段仍然可实现远程通信，但按照模式的理论来说，仍是有其局限性的。RMI技术，RMI-IIOP技术的产生，使远程对象的查找成为了技术焦点。JNDI技术就应运而生。JNDI技术产生后，就可方便的查找远程或是本地对象。
 */
public class JndiDataSource implements IDataSource{
    private final String JNDI_NAME = "registry.jndiName";
    private final Logger logger = LoggerFactory.getLogger(JndiDataSource.class);
    public JndiDataSource(){
    }
    
    public DataSource getDataSource(){
        String jndiName;
        Context ctx;
        Object lookedUp;
		try {
			 ConfigurationRegister conf= ConfigurationRegister.getInstance();
			 Configuration configuration= conf.getConfiguration("fstax");
	         jndiName = configuration.getString(JNDI_NAME);
	        if(jndiName == null || "".equals(jndiName)) {      	
	        }
	        ctx = new InitialContext();
			lookedUp = ctx.lookup(jndiName);
	        if(lookedUp != null) {       	
	        }
	        if(ctx != null) {
					ctx.close();
	        }		
	        DataSource datasource = (DataSource)lookedUp;
	        return datasource;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static void main(String[] args) {
    	JndiDataSource jndiDataSource=new JndiDataSource();
    	DataSource ds=jndiDataSource.getDataSource();
    	Connection conn = null;
    	try {
			conn=ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
			       conn.close();
			     } catch(SQLException e) { }
			   }
		}
    
}
