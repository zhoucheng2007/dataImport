package com.blogzhou.dao;

// Generated 2014-9-24 15:31:15 by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.blogzhou.entity.PubOrganType;

/**
 * Home object for domain model class PubOrganType.
 * @see com.blogzhou.entity.PubOrganType
 * @author Hibernate Tools
 */
public class PubOrganTypeDao  extends BaseDao<PubOrganType> {

	private static final Log log = LogFactory.getLog(PubOrganTypeDao.class);

	@Override
	public PubOrganType findById(String id) {
		log.debug("getting instance with id: " + id);
		try {
			PubOrganType instance = (PubOrganType) currentSession.get(PubOrganType.class, id);
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
