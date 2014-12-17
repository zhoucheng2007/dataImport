package com.blogzhou.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.blogzhou.HibernateSessionFactory;
import com.blogzhou.entity.PubStru;


public abstract class BaseDao<T> {	

	private static final Log log = LogFactory.getLog(BaseDao.class);
	
	protected final Session currentSession = getCurrentSession();
	
	protected Session getCurrentSession() {
		return HibernateSessionFactory.currentSession();
	}
	
	public void persist(T transientInstance) {
		try {
			currentSession.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(T instance) {
		log.debug("attaching dirty PubOrgan instance");
		try {
			currentSession.beginTransaction();
			currentSession.saveOrUpdate(instance);
			currentSession.getTransaction().commit(); 
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(T instance) {
		log.debug("attaching clean PubOrgan instance");
		try {
			currentSession.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(T persistentInstance) {
		log.debug("deleting PubOrgan instance");
		try {
			currentSession.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public T merge(T detachedInstance) {
		log.debug("merging PubOrgan instance");
		try {
			T result = (T)currentSession.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public abstract T findById(java.lang.String id) ;

	public List<T> findByExample(T instance) {
		log.debug("finding  instance by example");
		try {
			List<T> results = currentSession.createCriteria(instance.getClass()).add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	/**
	 * 根据hql获取list对象
	 * @param hql
	 * @return
	 */
	public List<T> getByHql(String hql) {
		try {
			List<T> results = currentSession.createQuery(hql).list();
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
