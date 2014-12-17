package com.zc.orm.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

public class PrepareStatementWork extends BaseWork {

	public PrepareStatementWork() {
		// TODO Auto-generated constructor stub
	}

	public PrepareStatementWork(String sql) {
		super(sql);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see org.hibernate.jdbc.Work#execute(java.sql.Connection)
	 */
	@Override
	public void execute(Connection connection) throws SQLException {
		
		connection.prepareStatement(sql).execute();			
	}
}
