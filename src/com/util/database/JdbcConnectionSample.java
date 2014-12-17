package com.util.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
/**
 * jdbc链接数据库操作示例
 * 这种方式缺点是错误比较难找
 * 查出来的结果没有对象化
 * 上午9:00:34
 * mailto: zhoucheng2007@gmail.com
 */
public class JdbcConnectionSample implements IDataSource{
	
	public static synchronized void getConnection() {
		Connection conn=null;
		try {
			/**
			 * 载JDBC驱动程序：   
    在连接数据库之前，首先要加载想要连接的数据库的驱动到JVM（Java虚拟机），   
    这通过java.lang.Class类的静态方法forName(String  className)实现。   
			 */
		   Class.forName("com.mysql.jdbc.Driver",true, Thread.currentThread().getContextClassLoader());
		   /**
		    *  成功加载后，会将Driver类的实例注册到DriverManager类中。
		    *  提供JDBC连接的URL   
   •连接URL定义了连接数据库时的协议、子协议、数据源标识。   
    •书写形式：协议：子协议：数据源标识   协议：在JDBC中总是以jdbc开始   
    子协议：是桥连接的驱动程序或是数据库管理系统名称。   
    数据源标识：标记找到数据库来源的地址与连接端口。   
    例如：（MySql的连接URL）   
    jdbc:mysql: //localhost:3306/test?useUnicode=true&characterEncoding=gbk ;   
   useUnicode=true：表示使用Unicode字符集。如果characterEncoding设置为   
   gb2312或GBK，本参数必须设置为true 。characterEncoding=gbk：字符编码方式。  
		    */
		   conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/es?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true","root","password");
		   /**
		    * 创建一个Statement 要执行SQL语句，必须获得java.sql.Statement实例，Statement实例分为以下3   种类型：   
      1、执行静态SQL语句。通常通过Statement实例实现。   
      2、执行动态SQL语句。通常通过PreparedStatement实例实现。   
      3、执行数据库存储过程。通常通过CallableStatement实例实现。  
		    */
		   String sql="select * from sys_user";
		   Statement stmt = conn.createStatement() ;   
		   //PreparedStatement pstmt = conn.prepareStatement(sql) ;   
	      // CallableStatement cstmt =  conn.prepareCall("{CALL demoSp(? , ?)}") ;   
	       /**
	        * 执行SQL语句 Statement接口提供了三种执行SQL语句的方法：executeQuery 、executeUpdate   和execute   
    1、ResultSet executeQuery(String sqlString)：执行查询数据库的SQL语句，返回一个结果集（ResultSet）对象。   
     2、int executeUpdate(String sqlString)：用于执行INSERT、UPDATE或  DELETE语句以及SQL DDL语句，如：CREATE TABLE和DROP TABLE等   
     3、execute(sqlString):用于执行返回多个结果集、多个更新计数或二者组合的   语句。
	        */
	       ResultSet rs = stmt.executeQuery(sql) ;   
	      // int rows = stmt.executeUpdate(sql) ;   
	      // boolean flag = stmt.execute(sql) ;   
	       /**
	        * 处理结果   
    两种情况：   
     1、执行更新返回的是本次操作影响到的记录数。   
     2、执行查询返回的结果是一个ResultSet对象。   
    • ResultSet包含符合SQL语句中条件的所有行，并且它通过一套get方法提供了对这些   
      行中数据的访问。   
    • 使用结果集（ResultSet）对象的访问方法获取数据：    
     }   
    （列是从左到右编号的，并且从列1开始）   
	        */
	       while(rs.next()){   
	           String name = rs.getString("username") ;   
	           String email = rs.getString(3) ; // 此方法比较高效  
	           System.out.println(name+"   "+email);
	       }
	       /**
	        * 关闭JDBC对象操作完成以后要把所有使用的JDBC对象全都关闭，以释放JDBC资源，关闭顺序和声明顺序相反：   
     1、关闭记录集   
     2、关闭声明   
     3、关闭连接对象   
	        */
	       if(rs != null){   // 关闭记录集   
	           try{   
	               rs.close() ;   
	           }catch(SQLException e){   
	               e.printStackTrace() ;   
	           }   
	             }   
	             if(stmt != null){   // 关闭声明   
	           try{   
	               stmt.close() ;   
	           }catch(SQLException e){   
	               e.printStackTrace() ;   
	           }   
	             }   
	             if(conn != null){  // 关闭连接对象   
	            try{   
	               conn.close() ;   
	            }catch(SQLException e){   
	               e.printStackTrace() ;   
	            }   
	 		   if(conn!=null) {
	 			     try {
	 			       conn.close();
	 			     } catch(SQLException e) {}
	 			   }
		}
		}
		catch(Exception e) {
		   e.printStackTrace();
		}
		finally {
		   if(conn!=null) {
		     try {
		       conn.close();
		     } catch(SQLException e) {}
		   }
		}
	}
	


	public static void main(String[] args) {
		JdbcConnectionSample.getConnection();
	}



	@Override
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

}
