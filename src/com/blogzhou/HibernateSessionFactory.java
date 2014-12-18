/**
 * 
 */
package com.blogzhou;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @author feng
 *
 */
public class HibernateSessionFactory {

	/**
	 * 
	 */
	public HibernateSessionFactory() {
	}
	
	/** 
     * Location of hibernate.cfg.xml file. 
     * NOTICE: Location should be on the classpath as Hibernate uses 
     * #resourceAsStream style lookup for its configuration file. That 
     * is place the config file in a Java package - the default location 
     * is the default Java package.<br><br> 
     * Examples: <br> 
     * <code>CONFIG_FILE_LOCATION = "/hibernate.conf.xml". 
     * CONFIG_FILE_LOCATION = "/com/foo/bar/myhiberstuff.conf.xml".</code> 
     */ 
    private static String CONFIG_FILE_LOCATION = "hibernate.cfg.xml"; 

    /** Holds a single instance of Session */ 
    private static final ThreadLocal threadLocal = new ThreadLocal(); 

    /** The single instance of hibernate configuration */ 
    private static final Configuration cfg = new Configuration(); 

    /** The single instance of hibernate SessionFactory */ 
    private static SessionFactory sessionFactory; 

    
    /** 
     * Returns the ThreadLocal Session instance.  Lazy initialize 
     * the <code>SessionFactory</code> if needed. 
     * 
     *  @return Session 
     *  @throws HibernateException 
     */ 
    public static SessionFactory getSessionFactory() throws HibernateException { 
				if (sessionFactory == null) { 
					try { 
					cfg.configure(CONFIG_FILE_LOCATION); 
					ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings( cfg.getProperties()).build(); 

					sessionFactory = cfg.buildSessionFactory(serviceRegistry); 
					} catch (Exception e) { 
					System.err .println("%%%% Error Creating SessionFactory %%%%"); 
					e.printStackTrace(); 
					} 
				} 
			return sessionFactory; 
		    } 
    
    /** 
     * Returns the ThreadLocal Session instance.  Lazy initialize 
     * the <code>SessionFactory</code> if needed. 
     * 
     *  @return Session 
     *  @throws HibernateException 
     */ 
    public static Session currentSession() throws HibernateException { 
        	Session session = (Session) threadLocal.get(); 
			if (session == null || !session.isOpen()) { 
				if (sessionFactory == null) { 
					sessionFactory = getSessionFactory();
				}
				session = (sessionFactory != null) ? sessionFactory.openSession(): null; 
				threadLocal.set(session); 
			} 
			return session; 
	    } 
		
    	
    
		    /** 
		     *  Close the single hibernate session instance. 
		     * 
		     *  @throws HibernateException 
		     */ 
		    public static void closeSession() throws HibernateException { 
		        Session session = (Session) threadLocal.get(); 
		        threadLocal.set(null); 	
		        if (session != null) { 
		            session.close(); 
		        } 
		    } 
}
