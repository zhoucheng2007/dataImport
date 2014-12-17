package com.blogzhou.common.code;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.blogzhou.common.code.exception.CodeException;
import com.blogzhou.common.code.exception.CodeExceptionDefine;

public class CodeManager {

    /**
     * 当前类自己的logger
     */
    private static Logger logger = Logger.getLogger(CodeManager.class);

    /**
     * 初始化文件缺省名称
     */
    private static final String CONFIG_FILE_NAME_DEFAULT = "codetable-mapping.xml";

    /**
     * 数据库连接配置名称
     */
    private static final String HIBERNATE_CONFIG_NAME = "codetable";

    /**
     * HashMap存储比率，过小不利于散列，过大浪费空间
     */
    private static final int MAP_RATIO = 5;

    /**
     * 缓存编码表信息
     */
    private static Map codetable = null;

    /**
     * 装载失败的列表
     */
    private static List failLoad = new ArrayList();
    
    /**
     * 缓存编码表信息(重新装载时临时使用)
     */
    private static Map codetableTmp = null; 

    /**
     * 正在重新装载
     */
    private static boolean isReloadCodeTable = false;     
    
    /**
     * 公共管理类，无需构造
     */
    private CodeManager() {
    }

    /**
     * 配置项懒装载
     * 实现同步，避免2个线程同时加载，当第2个线程加载时判断第一个线程是否已经加载，若已经加载并且不是强制刷新，返回
     * @throws CodeException 一般为配置文件存在错误或数据访问异常
     */
    private static synchronized void lazyInit() throws CodeException {
        if (codetable != null && !isReloadCodeTable) {
            return;
        }
        logger.debug("Lazy initialization starting.");
        init(null);
        logger.debug("Lazy initialization done.");
    }

    /**
     * 初始化方法，装载CodeTable
     * @param filePath 配置文件路径
     * @throws CodeException 一般为配置文件存在错误或数据访问异常
     */
    public static void init(String filePath) throws CodeException {
        if (filePath == null) {
            filePath = CONFIG_FILE_NAME_DEFAULT;
        }
        SAXReader saxReader = new SAXReader();
        InputStream fis = null;

        logger.info("Loading code-table with: " + filePath);
        try {
            fis = getResourceAsStream(filePath);
            long timer = System.currentTimeMillis();
            load(saxReader.read(fis));
            timer = System.currentTimeMillis() - timer;
            logger.info("Load code-table done, cost timer(ms): " + timer);
        } catch (IOException ex) {
            CodeException ce = new CodeException(CodeExceptionDefine.FSTAX_COMMON_CODE_CONF_ACCESS_FAILED,
                    "Codetable config file access failed.", ex);
            ce.log(logger);
            throw ce;
        } catch (DocumentException ex) {
            CodeException ce = new CodeException(CodeExceptionDefine.FSTAX_COMMON_CODE_CONF_PARSE_FAILED,
                    "Codetable config file parse by XML failed.", ex);
            ce.log(logger);
            throw ce;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // Nothing to do
                    e.printStackTrace();
                }
            }
        }
        
        /** 启动监视线程 **/
        logger.debug("Try to start TimerManager.");
        TimerManager.init();
        logger.debug("start TimerManager done.");
    }

    /**
     * 根据配置文件装载编码表
     * @param docConfig 配置文件
     * @throws CodeException 一般为配置文件存在错误或数据访问异常
     */
    private static void load(Document docConfig) throws CodeException {
        List codeNodes = docConfig.selectNodes("/codetable/code");
        if (codeNodes != null && codeNodes.size() > 0) {
            codetable = new HashMap(codeNodes.size() * MAP_RATIO);
            BatchSessionManager bsm = new BatchSessionManager(HIBERNATE_CONFIG_NAME);
            try {
                Session session = bsm.openSession();
                for (int i = 0; i < codeNodes.size(); i++) {

                    CodeConfigItem item = CodeConfigItem.parseConfig((Element) codeNodes.get(i));
                    try {
                        loadItem(item, session);
                    } catch (CodeException ex) {
                        failLoad.add(item);
                    }
                }
            } catch (BatchSessionException ex) {
                CodeException ce = new CodeException(CodeExceptionDefine.FSTAX_COMMON_CODE_DATABASE_FAILED,
                        "Database access failed.", ex);
                ce.log(logger);
                throw ce;
            } finally {
                bsm.closeSession();
            }
        } else {
            codetable = new HashMap();
        }
        if (failLoad.size() > 0) {
            StringBuffer sb = new StringBuffer("Failload code-table：[");
            for (int i = 0; i < failLoad.size(); i++) {
                CodeConfigItem item = (CodeConfigItem) failLoad.get(i);
                sb.append(item.getTableName());
                sb.append(",");
                sb.append(item.getName());
                sb.append(",");
                sb.append(item.getDescription());
                sb.append("; ");
            }
            sb.append("]");
            logger.warn(sb.toString());
        }
    }

    /**
     * 装载某项编码表项
     * @param item 编码表配置项
     * @param session 数据库会话，用于读取数据
     * @return 装载表项
     * @throws CodeException 数据库访问失败
     */
    private static Map loadItem(CodeConfigItem item, Session session) throws CodeException {
        /* 构造查询SQL */
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        if (item.getFieldCode().indexOf(",") > 0) { // 复合主键选择
            String[] tmpFields = item.getFieldCode().split(",");
            sb.append("(");
            for (int i = 0; i < tmpFields.length; i++) {
                if (i > 0) {
                    sb.append(" || '*' || ");
                }
                sb.append(tmpFields[i]);
            }
            sb.append(") as code,").append(item.getFieldValue());
        } else { // 单主键选择
            sb.append("(").append(item.getFieldCode()).append(") as code, ").append(item.getFieldValue());
        }
        sb.append(" FROM ").append(item.getTableName());
        if (item.getFilter() != null) {
            sb.append(" WHERE ").append(item.getFilter());
        }
        
        // 增加排序功能, at 20071220 by qwm
        if (item.getFieldCode().indexOf(",") > 0) { // 复合主键选择
        	String[] tmpFields = item.getFieldCode().split(",");
        	 sb.append(" order by "+tmpFields[0]);
        }else{
        	 sb.append(" order by "+item.getFieldCode());
        }
       
        /* 准备装载编码表 */
        if (logger.isDebugEnabled()) {
            logger.debug("Try to load code-table " + item.getName() + "[" + item.getDescription() + "], SQL: " + sb);
        }

        try {
            ResultSet rs = session.connection() .createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(sb.toString());

            /* 装载编码表 */
            int cntRow = 0;
            Map mapRet = new TreeMap();
            
            //Map mapRet=new TreeMap();
            while (rs.next()) {
                cntRow++;
                mapRet.put(rs.getString(1), rs.getString(2).replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
            }
            if (mapRet.size() != cntRow) {
                logger.warn("Loading code-table [" + item.getName() + "] warn, result has " + cntRow + ", but only "
                        + mapRet.size() + " loaded successful.");
            } else {
                logger.debug("Loaded code-table [" + item.getName() + "] row: " + cntRow);
            }

            /* 放入主编码映射Hash表，包括映射别名 */
            if (mapRet.size() > 0) {
                codetable.put(item.getName(), mapRet);
                if (item.getAlias() != null && item.getAlias().trim().length() > 0) {
                    String[] alias = item.getAlias().trim().split(",");
                    for (int cntAlias = 0; cntAlias < alias.length; cntAlias++) {
                        codetable.put(alias[cntAlias], mapRet);
                    }
                }
            }

            return mapRet;

        } catch (HibernateException ex) {
            CodeException ce = new CodeException(CodeExceptionDefine.FSTAX_COMMON_CODE_DATABASE_FAILED,
                    "Database access failed while loading [" + item.getName() + "], SQL" + sb, ex);
            ce.log(logger);
            throw ce;
        } catch (SQLException ex) {
            CodeException ce = new CodeException(CodeExceptionDefine.FSTAX_COMMON_CODE_DATABASE_FAILED,
                    "Database access failed while loading [" + item.getName() + "], SQL" + sb, ex);
            ce.log(logger);
            throw ce;
        }

    }

    /**
     * 组合主键
     * @param keys 组合主键
     * @return 组合主键
     */
    private String composeKey(String keys) {
        if (keys.indexOf(",") > 0) {
            return keys.replaceAll(", ", "*").replaceAll(",", "*");
        } else {
            return keys;
        }
    }

    /**
     * 编码翻译
     * @param table 编码表名称（可用别名）
     * @param code 编码
     * @return 编码对应值
     */
    public static String translate(String table, String code) {
        Map map = getCodeTable(table);
        if (map != null) {
            return (String) map.get(code);
        }
        return null;
    }

    /**
     * 获取指定的编码表
     * @param table 编码表名称（可用别名）
     * @return 编码表
     */
    public static Map getCodeTable(String table) {
        if (codetable == null || codetable.get(table) == null) {
            try {
                lazyInit();
            } catch (CodeException ex) {
                logger.warn("Codetable init failed, codetable unavailable.");
                return null;
            }
        }
        if (isReloadCodeTable)
            return (Map) codetableTmp.get(table);
        else
            return (Map) codetable.get(table);
    }
    
    /**
     * 重新装载编码表
     * 将原来的codetable.clone()
     */
    public static void reLoadCodeTable() {
        if (codetable != null) {
            codetableTmp = (Map) ((HashMap)codetable).clone();
            isReloadCodeTable = true;
            try {
                lazyInit();
            } catch (CodeException ex) {
                logger.warn("Codetable init failed, codetable unavailable.");
                return;
            }
            /* 重新加载成功了,恢复使用codetable */
            isReloadCodeTable = false;
            codetableTmp = null;
        }
    }    

    /**
     * 获取资源对象
     * @param resource
     * @return
     * @throws IOException
     */
    private static InputStream getResourceAsStream(String resource) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = null;
        if (loader != null) {
            in = loader.getResourceAsStream(resource);
        }
        if (in == null) {
            in = ClassLoader.getSystemResourceAsStream(resource);
        }
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        }

        return in;
    }
}
