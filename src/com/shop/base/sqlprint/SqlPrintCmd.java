package com.shop.base.sqlprint;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.dynamic.ChooseSqlNode;
import org.apache.ibatis.builder.xml.dynamic.DynamicSqlSource;
import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.builder.xml.dynamic.IfSqlNode;
import org.apache.ibatis.builder.xml.dynamic.MixedSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SqlNode;
import org.apache.ibatis.builder.xml.dynamic.TextSqlNode;
import org.apache.ibatis.builder.xml.dynamic.WhereSqlNode;
import org.apache.ibatis.session.SqlSessionFactory;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.ComponentFactory;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.cmd.BaseCommandImpl;



/**
 * V6业务模块SQL导出功能 by 韦竞杰 20130924 
 */
public class SqlPrintCmd extends BaseCommandImpl {

	private static Log log = LogFactory.getLog(SqlPrintCmd.class);


	/**
	 * 保存报表快照
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String showSql(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		
		SqlSessionFactory factoryBean=(SqlSessionFactory)ComponentFactory.getBean("sqlSessionFactory");
		String context=req.getContextPath().substring(1);
		Iterator it=factoryBean.getConfiguration().getMappedStatementNames().iterator();
		StringBuffer pageInfo=new StringBuffer();
		pageInfo.append("默认输出到模块所在服务器/v6sql/目录下，或者D:/v6sql 目录下"+"<br/>");
		pageInfo.append("当前模块IP为 "+req.getServerName()+",请查看该IP所在机器<br/>");
		pageInfo.append("由于${} 内包含的内容在未调用前是完全不可以知的，而且这种写法很容易制造SQL注入问题，建议可以的话都换成#{}格式<br/>");
		while(it.hasNext()){
			String msName=(String)it.next();
			if (msName.contains(".")){
				pageInfo.append("<br/><br/>"+"----------------------------------"+msName+"-----------------------------------------------------"+"<br/>");
				DynamicSqlSource dss=(DynamicSqlSource)factoryBean.getConfiguration().getMappedStatement(msName).getSqlSource();
				MixedSqlNode msn=(MixedSqlNode)dss.getRootSqlNode();
				StringBuffer sb=sqlNodeType(msn);
				//浏览器中看到的为#{}拼接的字段
				//out.println(sb);
				//log.debug("当前解析到"+msName);
				//文件中输出的SQL #{}用?号替换;
				while(sb.indexOf("#{")>-1){
					int start=sb.indexOf("#{");
					int end=sb.indexOf("}",start);
					sb.replace(start,end+1,"?");
				}
				pageInfo.append(sb);
				printSqlNote(sb,msName,context);
			}
		}
		
    	try {
			PrintWriter out = rep.getWriter();
			out.print(pageInfo);
			out.flush();
		} catch (Exception e) {
			log.error("页面输出异常，多半是框架问题");
		}
    	
		return null;
	}
	
	String textSqlNodeType(TextSqlNode tsn){
		if (null==tsn.getText()) 
			return "";
		else 
			return tsn.getText(); 
	}
	//对于ifSqlNode类型，过滤掉非当前数据库类型的SQL段，并返回全部可能的内容
	String ifSqlNodeType(IfSqlNode isn){
		StringBuffer sb=new StringBuffer();
		SqlSessionFactory factoryBean=(SqlSessionFactory)ComponentFactory.getBean("sqlSessionFactory");
		String dbId=factoryBean.getConfiguration().getDatabaseId().toUpperCase();
		String dbtype;
		if (dbId.contains("DB2")){
			dbtype=new String("v6dbtype==db2");
		}else if(dbId.contains("ORACLE")){
			dbtype=new String("v6dbtype==oracle");
		}else{
			dbtype=new String("v6dbtype==mysql");
		}
		if(!isn.getTest().contains("v6dbtype")){
			
			sb.append("/*if "+isn.getTest()+" */ ");
			MixedSqlNode msn=(MixedSqlNode)isn.getContents();
			sb.append(sqlNodeType(msn));
		}else if (isn.getTest().contains(dbtype)){
			sb.append("/*if "+isn.getTest()+" */ ");
			MixedSqlNode msn=(MixedSqlNode)isn.getContents();
			sb.append(sqlNodeType(msn));
		}
		return sb.toString();
	}
	
	//对于whereSqlNode 拼接 WHERE 字段与其中的条件
	String whereSqlNodeType(WhereSqlNode wsn){
		StringBuffer sb=new StringBuffer();
		sb.append(" WHERE ");
		MixedSqlNode msn=(MixedSqlNode)wsn.getContents();
		sb.append(sqlNodeType(msn));
		return sb.toString();
	}
	
	//对于forEachSqlNode ,只返回一个参数的情况
	String forEachSqlNodeType(ForEachSqlNode fesn){
		StringBuffer sb=new StringBuffer();
		sb.append(" (");
		MixedSqlNode msn=(MixedSqlNode)fesn.getContents();
		sb.append(sqlNodeType(msn));	
		sb.append(")");
		return sb.toString();
	}
	
	//对于chooseSqlNode,默认只拼接otherwise的内容，如果没有otherwise，就全部拼上
	String chooseSqlNodeType(ChooseSqlNode csn){
		StringBuffer sb=new StringBuffer();
		if (null!=csn.getDefaultContents()){
			MixedSqlNode msn=(MixedSqlNode)csn.getDefaultContents();
			sb.append(sqlNodeType(msn));
		}else{
			Iterator it=csn.getContents().iterator();
			while(it.hasNext()){
				SqlNode sn=(SqlNode)it.next();
				if(sn instanceof IfSqlNode) 
					sb.append((ifSqlNodeType((IfSqlNode)sn)));
			}
		}
		return sb.toString();
	}
	
	//选择继承SqlNode的类型
	StringBuffer sqlNodeType(MixedSqlNode msn){
		StringBuffer sb=new StringBuffer();
		Iterator it=msn.getContents().iterator();
		while(it.hasNext()){
			SqlNode sn=(SqlNode)it.next();
			if(sn instanceof TextSqlNode) 
				sb.append(textSqlNodeType((TextSqlNode)sn)) ;
			else if(sn instanceof IfSqlNode) 
				sb.append((ifSqlNodeType((IfSqlNode)sn)));
			else if(sn instanceof WhereSqlNode) 
				sb.append((whereSqlNodeType((WhereSqlNode)sn)));
			else if(sn instanceof ForEachSqlNode) 
				sb.append((forEachSqlNodeType((ForEachSqlNode)sn)));
			else if(sn instanceof ChooseSqlNode) 
				sb.append((chooseSqlNodeType((ChooseSqlNode)sn)));
		}
		return sb;
	}
	
	//打印SQL文件，存储在服务器/v6sql/ 或者D:\v6sql\中
	public void printSqlNote(StringBuffer sb,String msName,String context){	
		String osname=System.getProperties().getProperty("os.name");
		String filePath;
		if (osname.contains("Window")){
			filePath =new String("D:\\v6sql\\"+context+"\\");
		}else{
			filePath =new String("/v6sql/"+context+"/");
		}

		File fileDir =new File(filePath);
		if (!fileDir.exists()){
			fileDir.mkdirs();
		}
		File file = new File(filePath+msName+".txt");
		try{
			OutputStream ops = new FileOutputStream(file,false);
			BufferedOutputStream bos= new BufferedOutputStream(ops);
			bos.write(sb.toString().getBytes());
			bos.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
