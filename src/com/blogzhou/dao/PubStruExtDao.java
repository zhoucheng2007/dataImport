package com.blogzhou.dao;

// Generated 2014-9-24 15:31:15 by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.blogzhou.entity.PubStruExt;

/**
 * Home object for domain model class PubStruExt.
 * @see com.blogzhou.entity.PubStruExt
 * @author Hibernate Tools
 */
public class PubStruExtDao extends BaseDao<PubStruExt>{

	private static final Log log = LogFactory.getLog(PubStruExtDao.class);

	@Override
	public PubStruExt findById(String id) {
		log.debug("getting instance with id: " + id);
		try {
			PubStruExt instance = (PubStruExt) currentSession.get(PubStruExt.class, id);
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

	
	
}
