package com.util.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConnectionManager {
	
    private static Logger m_log=LoggerFactory.getLogger(BatchConnectionManager.class);;
    public static final String TORQUE_CONFIG = "mercury.configure.torque-prop-path";
    public static final String DEFAULT_DB = "foresee";

	
    public static void initTorque()  throws DBConnectionException
        {
 
        }

        public static Connection getConnection(String dbname)
            throws DBConnectionException
        {
            Connection conn;
            initTorque();
            conn = null;
           // conn = Torque.getConnection(dbname);
            try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DBConnectionException(e.getMessage());
			}
            return conn;
         }

        public static Connection getConnection()
            throws DBConnectionException
        {
            return getConnection("foresee");
        }

        public static Connection getTxConnection(String dbname) throws DBConnectionException
        {
            Connection conn;
            initTorque();
            conn = null;
            //conn = Torque.getConnection(dbname);
            return conn;
          }

        public static Connection getTxConnection()
            throws DBConnectionException
        {
            return getTxConnection("foresee");
        }

        public static void closeConnection(Connection con)
        {
            try
            {
                if(con != null)
                {
                    con.setAutoCommit(true);
                    con.close();
                    con = null;
                }
            }
            catch(Exception e)
            {
                m_log.warn(e.getMessage(), e);
            }
        }

        public static void closeStatement(Statement stat)
        {
            try
            {
                if(stat != null)
                {
                    stat.close();
                    stat = null;
                }
            }
            catch(Exception e)
            {
                m_log.warn(e.getMessage(), e);
            }
        }

        public static void closePreparedStatement(PreparedStatement psmt)
        {
            try
            {
                if(psmt != null)
                {
                    psmt.close();
                    psmt = null;
                }
            }
            catch(Exception e)
            {
                m_log.warn(e.getMessage(), e);
            }
        }

        public static void closeCallableStatement(CallableStatement stat)
        {
            try
            {
                if(stat != null)
                {
                    stat.close();
                    stat = null;
                }
            }
            catch(Exception e)
            {
                m_log.warn(e.getMessage(), e);
            }
        }

}
