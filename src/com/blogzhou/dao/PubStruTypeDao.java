package com.blogzhou.dao;

// Generated 2014-9-24 15:31:15 by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.blogzhou.entity.PubStruType;

/**
 * Home object for domain model class PubStruType.
 * @see com.blogzhou.entity.PubStruType
 * @author Hibernate Tools
 */
public class PubStruTypeDao extends BaseDao<PubStruType>{

	private static final Log log = LogFactory.getLog(PubStruTypeDao.class);

	@Override
	public PubStruType findById(String id) {
		log.debug("getting instance with id: " + id);
		try {
			PubStruType instance = (PubStruType) currentSession.get(PubStruType.class, id);
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
