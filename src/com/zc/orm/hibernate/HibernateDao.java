/**
 *  Copyright (c) 2012-2014 http://www.eryansky.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.zc.orm.hibernate;

import java.io.Serializable;
import java.util.*;

import com.zc.orm.Page;
import com.zc.util.refelect.Reflector;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;

/**
 * 封装SpringSide扩展功能的Hibernate DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查�? 可在Service层直接使�?也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T>
 *            DAO操作的对象类�?
 * @param <PK>
 *            主键类型
 * 
 */
public class HibernateDao<T, PK extends Serializable> extends
		SimpleHibernateDao<T, PK> {
	/**
	 * 用于Dao层子类的构�?函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * HibernateDao<User, Long>{ }
	 */
	public HibernateDao() {
		super();
	}

	/**
	 * 用于省略Dao�? Service层直接使用�?用HibernateDao的构造函�? 在构造函数中定义对象类型Class. eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User,
	 * Long>(sessionFactory, User.class);
	 */
	public HibernateDao(final SessionFactory sessionFactory,
			final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	// -- 分页查询函数 --//

	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数. 注意不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参�?按顺序绑�?
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参�?
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql,
			final Object... values) {

		Query q = createQuery(hql, values);

		if (page.isCountTotal()) {
			long totalItems = countHqlResult(hql, values);
			page.setTotalItems(totalItems);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数. 注意不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            命名参数,按名称绑�?
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参�?
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql,
			final Map<String, ?> values) {

		Query q = createQuery(hql, values);

		if (page.isCountTotal()) {
			long totalItems = countHqlResult(hql, values);
			page.setTotalItems(totalItems);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参�?
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {

		Criteria c = createCriteria(criterions);

		if (page.isCountTotal()) {
			long totalItems = countCriteriaResult(c);
			page.setTotalItems(totalItems);
		}

		setPageParameterToCriteria(c, page);

		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
    public Query setPageParameterToQuery(final Query q, final Page<T> page) {		
		q.setFirstResult(page.getFirstResult() - 1);// hibernate的firstResult的序号从0开始
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c,final Page<T> page) {
		c.setFirstResult(page.getFirstResult() - 1);
		c.setMaxResults(page.getPageSize());
		//设置排序
		super.setPageParameterToCriteria(c, page.getOrderBy(), page.getOrderDir());
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询�?��获得的对象�?�?
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
    public long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询�?��获得的对象�?�?
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		// select子句与order by子句会影响count查询,进行�?��的排�?
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	/**
	 * 执行count查询获得本次Criteria查询�?��获得的对象�?�?
	 */
	@SuppressWarnings("unchecked")
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出�?清空三�?后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) Reflector.getFieldValue(impl,
					"orderEntries");
			Reflector
					.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Long totalCountObject = (Long) c.setProjection(Projections.rowCount())
				.uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回�?
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			Reflector.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	
	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * 
	 * @param uniquePropertyNames
	 *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public boolean isUnique(T entity, String uniquePropertyNames) {
		Criteria criteria = getSession().createCriteria(entityClass).setProjection(
				Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (int i = 0; i < nameList.length; i++) {
				criteria.add(Restrictions.eq(nameList[i],PropertyUtils.getProperty(entity, nameList[i])));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getSessionFactory().getClassMetadata(entity.getClass()).getIdentifierPropertyName();
			if (idName != null) {
				// 取得entity的主键值
				Serializable id = (Serializable) PropertyUtils.getProperty(entity, idName);

				// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
				if (id != null)
					criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
			}
		} catch (Exception e) {
			Reflector.convertReflectionExceptionToUnchecked(e);
		}
		return ((Number) criteria.uniqueResult()).intValue() == 0;
	}
}
