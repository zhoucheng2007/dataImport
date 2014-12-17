package com.shop.base.commonhelp;

// 导入java类
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONException;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.util.Page;
import org.loushang.util.RowSelection;
import org.loushang.waf.mvc.ViewHelper;

import com.lc.v6.jdbc.mybatis.PageUtil;
import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;
import com.shop.base.batis.SqlClient;
import com.shop.base.cmd.BaseCommandImpl;
import com.shop.base.commonhelp.databean.Base;
import com.shop.base.tool.JsonConvertTools;



/**
 * 功能：新版通用帮助支持类
 * 
 * @author 孙振
 * 2014/03/11
 * 
 */
public class PubCommonHelpCmd extends BaseCommandImpl {

	private static Log log = LogFactory.getLog(PubCommonHelpCmd.class);
	
	//用于缓存结果，防止多次查询数据库
	private Map baseMap=new HashMap();
	
	
	/**
	 * 获取pub_common_help信息
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String queryPubCommonHelp(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		
		String helpId=(String)req.getParameter("helpId");
		
		Base base=getCommonHelpInfo(helpId);
		
		baseMap.put(helpId, base);
		
		String jsonStr=JsonConvertTools.object2json(base);
		
		try {
			PrintWriter out = rep.getWriter();
			out.write(jsonStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return "null";
	}
	public String queryTableData(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		
		String helpId=req.getParameter("helpId");
		String firstRow=req.getParameter("firstRow");
		String maxRow=req.getParameter("maxRow");
		
		Base base=getCommonHelpInfo(helpId);

		String sql=getCommonHelpSql(helpId,req,base);
		
		
		RowSelection rowSelection =new RowSelection();
		rowSelection.setFirstRow(Integer.parseInt(firstRow));
		rowSelection.setMaxRows(Integer.parseInt(maxRow));
		rowSelection.setTimeout(1000000);
		
		Map map=new HashMap();
		map.put("value", sql);
		
		map = PageUtil.convertParaMap(map, rowSelection);
		
		SqlSession sqlSession = V6SqlSessionUtil.getSqlSession();
		List datas = sqlSession.selectList("pubCommonHelpSql.getListSql", map);
		Page page=PageUtil.createPage(map, datas);
		Map dataMap=new HashMap();
		dataMap.put("count", page.getCount());
		dataMap.put("datas", page.getDatas());
		
		String jsonStr=JsonConvertTools.object2json(dataMap);
		
		try {
			PrintWriter out = rep.getWriter();
			out.write(jsonStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "null";
	}
	
	
	/**
	 * 根据ID获取数据
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String queryDataById(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		String helpId=req.getParameter("helpId");
		String idStr=req.getParameter("idStr");
		
		String[] parmKey=new String[2];
		parmKey[0]=helpId;
		parmKey[1]=idStr;
	
		Base base=new Base();
		if(baseMap.get(helpId)==null){
			base=getCommonHelpInfo(helpId);
		}else{
			base=(Base)baseMap.get(helpId);
		}
		String sql=getSqlForId(helpId,req,base,idStr);
		List resultList=SqlClient.commonSelect(sql);
		
		String resultStr=JsonConvertTools.object2json(resultList);
		

		
		try {
			PrintWriter out = rep.getWriter();
			out.write(resultStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "null";
	}
	
	private String getSqlForId(String helpId,HttpServletRequest req,Base base,String idStr){
		//组织需要查询的列，去除重复的列，oracle不支持查询重复列
		StringBuffer sqlColumn = new StringBuffer();
		sqlColumn.append(base.getIdField()).append(",");
		sqlColumn.append(base.getCodeField()).append(",");
		sqlColumn.append(base.getNameField()).append(",");
		sqlColumn.append(base.getReturnField()).append(",");
		sqlColumn.append(base.getShowField());
		String sqlColumnStr = sqlColumn.toString();
		String[] forColumn = sqlColumnStr.split(",");
		int columnLength = forColumn.length;
	    List sqlList= new ArrayList();
		for(int i=0;i<columnLength;i++){
			if(!sqlList.contains(forColumn[i])){
				sqlList.add(forColumn[i]);
			};
			
		}
		
		
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT ");
		
		//将去除掉重复列的查询列拼入sql
		int sqlListSize = sqlList.size();
		for(int j=0;j<sqlListSize-1;j++){
			sb.append(sqlList.get(j)).append(", ");
		}
		sb.append(sqlList.get(sqlListSize-1)).append(" ");
		
		sb.append(" FROM ");
		
		String tables=base.getHelpTable();
		String[] tableArr=tables.split("@");
		for(int i=0;i<tableArr.length;i++){
			if(i==tableArr.length-1){
				sb.append(tableArr[i]);
			}else{
				sb.append(tableArr[i]).append(",");
			}
		}
		sb.append(" WHERE ");
		
		String where=base.getHelpWhere();
		if(where==null){
			where=" 1=1 ";
		}else{
			String[] wbs=where.split("@");
			for(int i=1;i<wbs.length;i=i+2){
				String key=wbs[i];
				String value=req.getParameter(key);
				where=where.replaceAll("@"+key+"@", value);
			}
		}

		sb.append(where);
		
		String[] idArr=idStr.split(",");
		StringBuffer inStr =new StringBuffer();
		int len=idArr.length;
		for(int i=0;i<len;i++){
			inStr.append("'").append(idArr[i]).append("'");
			if(i!=len-1){
				inStr.append(",");
			}
		}
		sb.append(" AND ");
		sb.append(base.getIdField()).append(" IN (").append(inStr.toString()).append(")");
		if(log.isDebugEnabled()){
			log.debug("getSqlForIdgetSqlForId---"+sb.toString());	
		}
		return sb.toString();
		
	}
	
	
	
	
	private String getCommonHelpSql(String helpId,HttpServletRequest req,Base base){
		//组织需要查询的列，去除重复的列，oracle不支持查询重复列
		StringBuffer sqlColumn = new StringBuffer();
		sqlColumn.append(base.getIdField()).append(",");
		sqlColumn.append(base.getCodeField()).append(",");
		sqlColumn.append(base.getNameField()).append(",");
		sqlColumn.append(base.getReturnField()).append(",");
		sqlColumn.append(base.getShowField());
		String sqlColumnStr = sqlColumn.toString();
		
		String[] forColumn = sqlColumnStr.split(",");
		int columnLength = forColumn.length;
	    List sqlList= new ArrayList();
		for(int i=0;i<columnLength;i++){
			if(!sqlList.contains(forColumn[i])){
				sqlList.add(forColumn[i]);
			};
			
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT ");
		//将去除掉重复列的查询列拼入sql
		int sqlListSize = sqlList.size();
		for(int j=0;j<sqlListSize-1;j++){
			sb.append(sqlList.get(j)).append(", ");
		}
		sb.append(sqlList.get(sqlListSize-1)).append(" ");
		
		sb.append(" FROM ");
		
		String tables=base.getHelpTable();
		String[] tableArr=tables.split("@");
		for(int i=0;i<tableArr.length;i++){
			if(i==tableArr.length-1){
				sb.append(tableArr[i]);
			}else{
				sb.append(tableArr[i]).append(",");
			}
		}
		sb.append(" WHERE ");
		
		String where=base.getHelpWhere();
		if(where==null){
			where=" 1=1 ";
		}else{
			String[] wbs=where.split("@");
			for(int i=1;i<wbs.length;i=i+2){
				String key=wbs[i];
				String value=req.getParameter(key);
				where=where.replaceAll("@"+key+"@", value);
			}
		}

		sb.append(where);
		
		String q=req.getParameter("q");
		if(null!=q&&!q.trim().equals("")){
			try {
				q=URLDecoder.decode(q,"UTF-8");
			} catch (Exception e) {
				//
			}
			sb.append(" AND (");
			sb.append(base.getCodeField()).append(" LIKE '%").append(q).append("%'");
			sb.append(" OR ");
			sb.append(base.getNameField()).append(" LIKE '%").append(q).append("%'");
			sb.append(")");
		}
		if(log.isDebugEnabled()){
			log.debug("getCommonHelpSql-----"+sb.toString());
		}
		return sb.toString();
	}
	
	
	
	private Base getCommonHelpInfo(String helpId){
		
		String sql="SELECT * FROM PUB_COMMON_HELP WHERE HELP_ID='"+helpId+"'";	
		List resultList=SqlClient.commonSelect(sql);
		Base base=new Base();
		
		if(resultList!=null&&resultList.size()>0){
			
			Map resultMap=(Map) resultList.get(0);
			
			base.setHelpId((String)resultMap.get("HELP_ID"));
			base.setHelpTable((String)resultMap.get("HELP_TABLE"));
			base.setHelpTitle((String)resultMap.get("HELP_TITLE"));
			base.setCodeField((String)resultMap.get("CODE_FIELD"));
			base.setIdField((String)resultMap.get("ID_FIELD"));
			base.setNameField((String)resultMap.get("NAME_FIELD"));
			base.setShowField((String)resultMap.get("SHOW_FIELD"));
			base.setReturnField((String)resultMap.get("RETURN_FIELD"));
			base.setHelpWhere((String)resultMap.get("HELP_WHERE"));
			base.setMultsel(((String)resultMap.get("IS_MULTSEL")).equals("0"));
			base.setShowName((String)resultMap.get("SHOW_NAME"));
			
		}
		
		return base;
	}
	
	
	/**
	 * 通用联想功能实现，借助pub_common_help表
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @throws JSONException
	 */
	public void autoComplete(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws JSONException{
		String helpId=(String)req.getParameter("helpId");
		String q=(String)req.getParameter("q");
		String limit=(String)req.getParameter("limit");
		
		try {
			q=URLDecoder.decode(q,"UTF-8");
		} catch (Exception e) {
			//
		}
		
		Base base=new Base();
		if(baseMap.get(helpId)==null){
			base=getCommonHelpInfo(helpId);
		}else{
			base=(Base)baseMap.get(helpId);
		}
		
		//组织需要查询的列，去除重复的列，oracle不支持查询重复列
		StringBuffer sqlColumn = new StringBuffer();
		sqlColumn.append(base.getIdField()).append(",");
		sqlColumn.append(base.getCodeField()).append(",");
		sqlColumn.append(base.getNameField()).append(",");
		sqlColumn.append(base.getReturnField()).append(",");
		sqlColumn.append(base.getShowField());
		String sqlColumnStr = sqlColumn.toString();
		String[] forColumn = sqlColumnStr.split(",");
		int columnLength = forColumn.length;
	    List sqlList= new ArrayList();
		for(int i=0;i<columnLength;i++){
			if(!sqlList.contains(forColumn[i])){
				sqlList.add(forColumn[i]);
			};
			
		}
		
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM");
		sql.append(" (SELECT ");
		
		int sqlListSize = sqlList.size();
		for(int j=0;j<sqlListSize-1;j++){
			sql.append(sqlList.get(j)).append(", ");
		}
		sql.append(sqlList.get(sqlListSize-1)).append(" ");

		sql.append(" FROM ");
		sql.append(base.getHelpTable().replaceAll("@", ","));
		sql.append(" WHERE ");
		
		String where=base.getHelpWhere();
		String[] wbs=where.split("@");
		if(wbs.length>1){
			for(int i=1;i<wbs.length;i=i+2){
				String key=wbs[i];
				String value=req.getParameter(key);
				where=where.replaceAll("@"+key+"@", value);
			}
		}
		
		//sql拼接like部分
        StringBuffer likeSql = new StringBuffer();
		if(null!=q&&!q.trim().equals("")){
			try {
				q=URLDecoder.decode(q,"UTF-8");
			} catch (Exception e) {
				//
			}
			likeSql.append(" AND (");
			likeSql.append(base.getCodeField()).append(" LIKE '%").append(q).append("%'");
			likeSql.append(" OR ");
			likeSql.append(base.getNameField()).append(" LIKE '%").append(q).append("%'");
			likeSql.append(")");
		}
		//sql.append(where);
		//当jsp中“where=”后面有order by的时候拼接sql需要特殊处理，先判断是否有order by 然后判断后面是否有“）”决定sql中“模糊条件”拼接位置
		String newWhere = "";
		String[] isOrderBy = where.split("ORDER BY");
		int orderL = isOrderBy.length;
        if(orderL>1){
        	String lastOrder = isOrderBy[orderL-1];
        	String[] rightOrder = lastOrder.split("\\)");
        	if(rightOrder.length==1){
        		for(int j=0;j<orderL;j++){
        			if(j<orderL-1){
        				if(j>0){
                 			  newWhere+=" ORDER BY ";      				
              			}
        				newWhere+=isOrderBy[j];
        			}else{
        				newWhere+=likeSql.toString();
        				newWhere+=" ORDER BY ";  
        				newWhere+=isOrderBy[j];
        			}	
        		}
        		sql.append(newWhere);
        	}
        }else{
        	sql.append(where);
        	sql.append(likeSql.toString());	
        } 	
        
       
		sql.append(")");
		
		if(V6SqlSessionUtil.dbTypeIsDB2()){
			sql.append("FETCH FIRST ").append(limit).append(" ROWS ONLY ");
		}else{
			sql.append(" WHERE ROWNUM<=").append(limit);
		}
	
		Map map=new HashMap();
		map.put("value", sql.toString());
		
		SqlSession sqlSession = V6SqlSessionUtil.getSqlSession();		
		List resultList = sqlSession.selectList("pubCommonHelpSql.getListSql", map); 
		String jsonStr=JsonConvertTools.object2json(resultList);    	 
        if(log.isDebugEnabled()){
        	log.debug("aucocompleteSQL==="+sql.toString()); 
        }
		try {
			PrintWriter out = rep.getWriter();
			out.write(jsonStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
