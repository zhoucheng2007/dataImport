package com.util.database;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.configuration.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogzhou.HibernateSessionFactory;
import com.blogzhou.common.configuration.ConfigurationRegister;
/**
 * 批处理Session管理类. 用于同步多个方法的执行. 同时支持对多个数据库的指向，使用缺省构造时，缺省指向标识名为foresee的数据库连接
 * 当传入新的dbName构造BatchSessionManager时，则指向dbName所标识的数据库连接
 * 
 * @author taoyu
 */
public class BathSessionManager implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2002595623477468433L;

	/**
	 * Logger对象
	 */
	private static Logger logger = LoggerFactory.getLogger(BatchSessionManager.class);

	/**
	 * 数据库名
	 */
	private String dbName = "foresee";

	/**
	 * 实际编码
	 */
	private String realEncoding = null;

	/**
	 * 存储编码
	 */
	private String treatEncoding = null;

	/**
	 * hibernate session对象
	 */
	private Session session = null;

	/**
	 * hibernate transaction对象,transaction对象不需要序列化
	 */
	private transient Transaction transaction = null;

	/**
	 * transaction计数器
	 */
	private int tranCount = 0;

	/**
	 * session计数器
	 */
	private int sessionCount = 0;

	/**
	 * 缺省构造函数
	 * @return 
	 */	
	public BathSessionManager() {
		super();
		// 读取编码配置
		try {
			Configuration conf = ConfigurationRegister.getInstance().getConfiguration("fstax");
			String treatEncoding = conf.getString("database.charset");
			String realEncoding = conf.getString("web.page.outcharset");
			if (treatEncoding != null && realEncoding != null) {
				this.treatEncoding = treatEncoding;
				this.realEncoding = realEncoding;
			}
		} catch (IOException e) {
			logger.warn("read coding configuration error:", e);
		}
	}



	/**
	 * 传入数据库标识的构造函数
	 * 
	 * @param dbName
	 *            数据库名
	 */
	public BatchSessionManager(String dbName) {
		if (dbName != null) {
			this.dbName = dbName;
		}
	}

	/**
	 * 传入数据库标识、实际编码、存储编码的构造函数
	 * 
	 * @param dbName
	 *            数据库名
	 * @param realEncoding
	 *            实际编码
	 * @param treatEncoding
	 *            存储编码
	 */
	public BatchSessionManager(String dbName, String realEncoding, String treatEncoding) {
		if (dbName != null) {
			this.dbName = dbName;
		}

		if (realEncoding != null && treatEncoding != null) {
			this.realEncoding = realEncoding;
			this.treatEncoding = treatEncoding;
		}
	}

	/**
	 * 打开Session
	 * 
	 * @return Session
	 * @throws BatchSessionException
	 *             批处理Session异常
	 */
	public Session openSession() throws BatchSessionException {
		try {
			if (sessionCount <= 0 || (sessionCount > 0 && (session == null || (!session.isOpen())))) {
				sessionCount = 0;
				session = null;

				if (realEncoding != null && treatEncoding != null && !realEncoding.equalsIgnoreCase(treatEncoding)) {
					session = HibernateSessionFactory.openSession(dbName, new CharsetInterceptor(realEncoding,
							treatEncoding));
				} else {
					session = HibernateSessionFactory.openSession(dbName);
				}

			}
			sessionCount++;
			return session;
		} catch (Exception e) {
			logger.warn("open session error:", e);
			throw new BatchSessionException("open session error", e);
		}
	}

	/**
	 * 取得Session
	 * 
	 * @return Session
	 * @throws BatchSessionException
	 *             批处理Session异常
	 */
	public Session getSession() throws BatchSessionException {
		if (session == null) {
			throw new BatchSessionException("get Session failed");
		} else {
			return session;
		}
	}

	/**
	 * 关闭Session
	 * 
	 */
	public void closeSession() {
		try {
			sessionCount--;
			/*
			 * if (logger.isDebugEnabled()) { if (sessionCount < 0) {
			 * logger.debug("Close session, but sessionCount is " +
			 * sessionCount); } else if (sessionCount == 0) {
			 * logger.debug("Close session, the last time."); } else {
			 * logger.debug(" Close session, remain times: " + sessionCount); }
			 * logger.debug(" session's dbname is " + dbName); }
			 */
			if (sessionCount <= 0) {
				sessionCount = 0;
				if (session != null) {
					session.close();
				}
				session = null;
			}
		} catch (Exception e) {
			logger.warn("close session error:", e);
		}
	}

	/**
	 * 开始批处理事务
	 * 
	 * @throws BatchSessionException
	 *             批处理Session异常
	 */
	public void beginBatch() throws BatchSessionException {
		try {
			/*
			 * if (logger.isDebugEnabled()) { if (tranCount < 0) {
			 * logger.debug("Begin transaction, but tranCount is " + tranCount); }
			 * else if (tranCount == 0) { logger.debug("Begin transaction, the
			 * first time."); } else { logger.debug(" Begin transaction, times: " +
			 * tranCount); } }
			 */
			if (tranCount <= 0) {
				tranCount = 0;
				transaction = session.beginTransaction();
			}
			tranCount++;
		} catch (Exception e) {
			logger.warn("begin Batch error:", e);
			throw new BatchSessionException("begin Batch error", e);
		}
	}

	/**
	 * 提交批处理事务
	 * 
	 * @throws BatchSessionException
	 *             批处理Session异常
	 */
	public void commitBatch() throws BatchSessionException {
		try {
			if (tranCount < 1) {
				logger.warn("Commit transaction, but tranCount is " + tranCount);
			}
			if (tranCount <= 1) {
				transaction.commit();
				tranCount = 0;
			} else {
				tranCount--;
			}
		} catch (Exception e) {
			logger.warn("commit Batch error:", e);
			throw new BatchSessionException("commit Batch error", e);
		}
	}

	/**
	 * 回滚批处理事务
	 */
	public void rollbackBatch() {
		try {
			if (tranCount != 0) {
				if (transaction != null) {
					transaction.rollback();
				}
				tranCount = 0;
			}
		} catch (Exception e) {
			logger.warn("rollback Batch error:", e);
		}
	}
}
