package com.blogzhou.dao;

// Generated 2014-9-24 15:31:15 by Hibernate Tools 4.0.0

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.blogzhou.entity.PubStru;

/**
 * Home object for domain model class PubStru.
 * @see com.blogzhou.entity.PubStru
 * @author Hibernate Tools
 */
public class PubStruDao extends BaseDao<PubStru>{

	private static final Log log = LogFactory.getLog(PubStruDao.class);

	@Override
	public PubStru findById(String id) {
		log.debug("getting instance with id: " + id);
		try {
			PubStru instance = (PubStru) currentSession.get(PubStru.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	
	/*
	 * 鑾峰彇鎵�湁鐨勭粍缁囨満鏋勫璞�
	 * @ Return 鏁版嵁搴撲腑鎵�湁鐨勫璞�
	 * @ 
	 */
    
	public List<PubStru> getAll() {
		String hql="from PubStru where pubStruType='00'";
		try {
			List<PubStru> results = getByHql(hql);
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
