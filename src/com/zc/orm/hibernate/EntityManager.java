/**
 *  Copyright (c) 2012-2014 http://www.eryansky.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.zc.orm.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zc.exception.*;
import com.zc.orm.Page;

/**
 * Service层领域对象业务管理类基类.
 * 使用HibernateDao<T,PK>进行业务对象的CRUD操作,子类�?��载getEntityDao()函数提供该DAO.
 * 
 * @param <T>
 *            领域对象类型
 * @param <PK>
 *            领域对象的主键类�?
 * 
 */
public abstract class EntityManager<T, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected abstract HibernateDao<T, PK> getEntityDao();

	// CRUD函数 //

	/**
	 * 保存新增的对�?
	 * 
	 * @param entity
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void save(final T entity) throws DaoException, SystemException,
			ServiceException {
		getEntityDao().save(entity);
	}

	/**
	 * 保存修改的对�?
	 * 
	 * @param entity
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void update(final T entity) throws DaoException, SystemException,
			ServiceException {
		getEntityDao().update(entity);
	}

    /**
     * 保存新增或修改的对象.
     * <br>自定义保存实体方�?内部使用saveOrUpdate
     * <br>�?保存之前会清空session 调用了clear()
     * @param entity
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public void saveEntity(final T entity) throws DaoException,
            SystemException, ServiceException {
        getEntityDao().saveEntity(entity);
    }

	/**
	 * 保存新增或修改的对象.
	 * 
	 * @param entity
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void saveOrUpdate(final T entity) throws DaoException,
			SystemException, ServiceException {
		getEntityDao().saveOrUpdate(entity);
	}

	/**
	 * 保存新增或修改的对象集合.
	 * 
	 * @param entitys
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void saveOrUpdate(final Collection<T> entitys) throws DaoException,
			SystemException, ServiceException {
		getEntityDao().saveOrUpdate(entitys);
	}
	
	/**
	 * 清除当前session.
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void clear() throws DaoException,
		    SystemException, ServiceException {
	    getEntityDao().clear();
	}
	
	/**
	 * 将对象变为游离状�?
	 * @param entity
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void evict(T entity) throws DaoException,
		    SystemException, ServiceException {
		getEntityDao().evict(entity);
	}

    /**
	 * 修改合并.
	 * 
	 * @param entity
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void merge(final T entity) throws DaoException, SystemException,ServiceException {
		getEntityDao().merge(entity);
	}

	/**
	 * 删除(根据主键ID).
	 * 
	 * @param id
	 *            主键ID
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void deleteById(final PK id) throws DaoException, SystemException,
			ServiceException {
		getEntityDao().delete(id);
	}

	/**
	 * 删除(根据对象).
	 * 
	 * @param entity
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void delete(final T entity) throws DaoException, SystemException,
			ServiceException {
		getEntityDao().delete(entity);
	}

	/**
	 * 删除全部�?
	 * 
	 * @param entitys
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public void deleteAll(final Collection<T> entitys) throws DaoException,
			SystemException, ServiceException {
		if (!CollectionUtils.isEmpty(entitys)) {
			for (T entity : entitys) {
				getEntityDao().delete(entity);
			}
		} else {
			logger.warn("参数[entitys]为空.");
		}
	}

	/**
	 * 根据id集合删除全部�?
	 * 
	 * @param ids
	 *            id集合
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public void deleteByIds(final List<PK> ids) throws DaoException,
			SystemException, ServiceException {
		if (!CollectionUtils.isEmpty(ids)) {
			for (PK id : ids) {
				getEntityDao().delete(id);
			}
		} else {
			logger.warn("参数[ids]为空.");
		}
	}

	/**
	 * 按id获取对象(代理 延迟加载).
	 * 
	 * @param id
	 *            主键ID
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public T loadById(final PK id) throws DaoException, SystemException,
			ServiceException {
		return getEntityDao().load(id);
	}

	/**
	 * 按id获取对象(直接返回实体�?.
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public T getById(final PK id) throws DaoException, SystemException,
			ServiceException {
		return getEntityDao().get(id);
	}

	/**
	 * 自定义属性查�?
	 * 
	 * @param propertyName
	 *            属�?名称
	 * @param propertyValue
	 *            属�?�?
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	
	public List<T> findBy(String propertyName, Object propertyValue)
			throws DaoException, SystemException, ServiceException {
		return getEntityDao().findBy(propertyName, propertyValue);
	}

	/**
	 * 自定义属性查找唯�??.
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public T findUniqueBy(String propertyName, Object value)
			throws DaoException, SystemException, ServiceException {
		return getEntityDao().findUniqueBy(propertyName, value);
	}

	/**
	 * 查询�?��分页.
	 * 
	 * @param page
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public Page<T> getAll(final Page<T> page) throws DaoException,
			SystemException, ServiceException {
		return getEntityDao().getAll(page);
	}

	/**
	 * 查询�?��.
	 * 
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public List<T> getAll() throws DaoException, SystemException,
			ServiceException {
		return getEntityDao().getAll();
	}

	/**
	 * 查询�?��(排序).
	 * 
	 * @param orderBy
	 *            排序字段 多个排序字段时用','分隔.
	 * @param order
	 *            排序方式"asc"�?desc" 中间�?,"分割
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public List<T> getAll(String orderBy, String order) throws DaoException,
			SystemException, ServiceException {
		return getEntityDao().getAll(orderBy, order);
	}
	

	/**
	 * 自定义Criterion分页查询.
	 * 
	 * @param page
	 *            第几�?
	 * @param rows
	 *            页大�?
	 * @param sort
	 *            排序字段
	 * @param order
	 *            排序方式 增序:'asc',降序:'desc'
	 * @param criterions
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public Page<T> findByCriteria(int page, int rows, String sort,String order, Criterion... criterions) 
			throws DaoException,SystemException, ServiceException {
		Page<T> p = new Page<T>();	
		p.setPageNo(page);
		p.setOrderBy(sort);
		return getEntityDao().findPage(p, criterions);
	}

	/**
	 * 自定义Criterion分页查询.
	 * 
	 * @param page
	 *            分页对象.
	 * @param criterions
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	
	public Page<T> findByCriteria(Page<T> page, Criterion... criterions)
			throws DaoException, SystemException, ServiceException {
		return getEntityDao().findPage(page, criterions);
	}

	/**
	 * 自定义Criterion查询.
	 * 
	 * @param criterions
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	
	public List<T> findByCriteria(Criterion... criterions) throws DaoException,
			SystemException, ServiceException {
		return getEntityDao().find(criterions);
	}

	/**
	 * 判断对象某些属�?的�?在数据库中是否唯�?
	 * 
	 * @param uniquePropertyNames
	 *            在POJO里不能重复的属�?列表,以�?号分�?�?name,loginid,password"
	 * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
	 */
	public boolean isUnique(T entity, String uniquePropertyNames)throws DaoException, SystemException, ServiceException {
		return getEntityDao().isUnique(entity, uniquePropertyNames);
	}


    /**
     * 初始化对�?
     * @param proxy   目标对象
     */

    public void initProxyObject(Object proxy) {
        getEntityDao().initProxyObject(proxy);
    }
}