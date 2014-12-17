/**
 *  Copyright (c) 2012-2014 http://www.eryansky.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.zc.orm.hibernate;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.zc.util.refelect.Reflector;

/**
 * 含默认泛型DAO的EntityManager.
 * 
 * @param <T>
 *            领域对象类型
 * @param <PK>
 *            领域对象的主键类
 *            eg. public class UserManager extends
 *            DefaultEntityManager<User,Long>{ }
 * 
 */
public class DefaultEntityManager<T, PK extends Serializable> extends
		EntityManager<T, PK> {

	protected HibernateDao<T, PK> entityDao;// 默认的泛型DAO成员变量.

	/**
	 * 通过注入的sessionFactory初始化默认的泛型DAO成员变量.
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		Class<T> entityClass = Reflector.getClassGenricType(getClass());
		entityDao = new HibernateDao<T, PK>(sessionFactory, entityClass);
	}

	@Override
	protected HibernateDao<T, PK> getEntityDao() {
		return entityDao;
	}

}