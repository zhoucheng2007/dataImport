/**
 * 
 */
package com.zc.orm.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

/**
 * @author feng
 *
 */
public abstract class BaseWork implements Work {
	
	protected String sql;

	/**
	 * 
	 */
	public BaseWork() {
		// TODO Auto-generated constructor stub
	}
	
	public BaseWork(String sql) {
		super();
		this.sql = sql;
	}



	/* (non-Javadoc)
	 * @see org.hibernate.jdbc.Work#execute(java.sql.Connection)
	 */
	@Override
	public void execute(Connection connection) throws SQLException {
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}	
	
}
