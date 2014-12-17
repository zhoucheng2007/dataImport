package com.util.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchConnectionManager {
	
    private static Logger m_log=LoggerFactory.getLogger(BatchConnectionManager.class);
    private String dbName;
    private boolean isInBatch;
    private Connection conn;
    private int tranCount;
    private int connCount;
	
    public BatchConnectionManager()
    {
        dbName = "foresee";
        isInBatch = false;
        conn = null;
        tranCount = 0;
        connCount = 0;
    }

    public BatchConnectionManager(String dbname)
    {
        dbName = "foresee";
        isInBatch = false;
        conn = null;
        tranCount = 0;
        connCount = 0;
        dbName = dbname;
    }

    public Connection openConnection() throws DBConnectionException
    {
        if(connCount <= 0)
        {
            connCount = 0;
            conn = null;
            conn = ConnectionManager.getConnection(dbName);
        } else
			try {
				if(connCount > 0 && (conn == null || conn.isClosed()))
				{
				    connCount = 0;
				    conn = null;
				    conn = ConnectionManager.getConnection(dbName);
				}
			} catch (SQLException e) {
				throw new DBConnectionException(e.getMessage());
			}
        connCount++;
        return conn;
        }

    public Connection getConnection()
        throws DBConnectionException
    {
        if(conn == null)
            throw new DBConnectionException("\u8FDE\u63A5\u672A\u6253\u5F00");
        else
            return conn;
    }

    public void setConnection(Connection ctxConn)
        throws DBConnectionException
    {
        if(conn == null)
        {
            conn = ctxConn;
            connCount = 1;
            tranCount = 1;
            return;
        } else
        {
            throw new DBConnectionException("\u5F53\u524D\u8FDE\u63A5\u5DF2\u7ECF\u5B58\u5728,\u4E0D\u5F97\u91CD\u590D\u8BBE\u7F6E!");
        }
    }

    public void closeConnection()
    {
        try
        {
            connCount--;
            if(connCount <= 0)
            {
                connCount = 0;
                ConnectionManager.closeConnection(conn);
                conn = null;
            }
        }
        catch(Exception e)
        {
            m_log.warn("\u5173\u8054\u63A5\u5931\u8D25", e);
        }
    }

    public void beginBatch()
        throws DBConnectionException
    {
        try
        {
            if(tranCount <= 0)
            {
                tranCount = 0;
                conn.setAutoCommit(false);
            }
            tranCount++;
        }
        catch(Exception e)
        {
            m_log.warn("\u542F\u52A8\u6279\u5904\u7406\u5931\u8D25", e);
            throw new DBConnectionException("\u542F\u52A8\u6279\u5904\u7406\u5931\u8D25");
        }
    }

    public void commitBatch()
        throws DBConnectionException
    {
        try
        {
            tranCount--;
            if(tranCount <= 0)
            {
                tranCount = 0;
                conn.commit();
                conn.setAutoCommit(true);
            }
        }
        catch(Exception e)
        {
            m_log.warn("\u63D0\u4EA4\u6279\u5904\u7406\u5931\u8D25", e);
            throw new DBConnectionException("\u63D0\u4EA4\u6279\u5904\u7406\u5931\u8D25");
        }
    }

    public void rollbackBatch()
    {
        try
        {
            if(tranCount != 0)
            {
                if(conn != null)
                {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
                tranCount = 0;
            }
        }
        catch(Exception e)
        {
            m_log.warn("\u63D0\u4EA4\u6279\u5904\u7406\u5931\u8D25", e);
        }
    }

}
