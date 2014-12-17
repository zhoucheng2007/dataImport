package com.zc.orm.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zc.exception.DaoException;
import com.zc.util.refelect.Reflector;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * 可在Service层直接使�? 也可以扩展泛型DAO子类使用, 见两个构造函数的注释.
 * 参�?Spring2.5自带的Petlinc例子, 取消了HibernateTemplate, 直接使用Hibernate原生API.
 * 
 * @param <T> DAO操作的对象类�?
 * @param <PK> 主键类型
 */
@SuppressWarnings("unchecked")
public class SimpleHibernateDao<T, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构�?函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernateDao() {
		this.entityClass = Reflector.getClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao�? 在Service层直接使用�?用SimpleHibernateDao的构造函�?
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public SimpleHibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/**
	 * 取得sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 取得当前Session.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存新增的对�?
	 */
	public void save(final T entity) {
		getSession().save(entity);
		logger.debug("save entity: {}", entity);
	}
	
	/**
	 * 保存修改的对�?
	 */
	public void update(final T entity) {
		getSession().update(entity);
		logger.debug("update entity: {}", entity);
	}

    /**
     * 保存新增或修改的对象.
     * <br>自定义保存实体方�?内部使用saveOrUpdate
     * <br>�?保存之前会清空session 调用了clear()
     */
    public void saveEntity(final T entity) {
        clear();//清空缓存
        getSession().saveOrUpdate(entity);
        logger.debug("saveOrUpdate entity: {}", entity);
    }

	/**
	 * 保存新增或修改的对象.
	 */
	public void saveOrUpdate(final T entity) {
		getSession().saveOrUpdate(entity);
		logger.debug("saveOrUpdate entity: {}", entity);
	}
	
	/**
	 * 保存新增或修改的对象集合.
	 */
	public void saveOrUpdate(final Collection<T> entitys) {
		for (T entity : entitys) {
			this.saveOrUpdate(entity);
		}
	}
	
	
	/**
	 * 刷新对象.
	 */
	public void refresh(final T entity) {
		getSession().refresh(entity);
		logger.debug("refresh entity: {}", entity);
	}
	
	/**
	 * 刷新对象.
	 * <br>参数lockOptions可为null
	 * @param entity 操作对象
	 * @param lockOptions Hibernate LockOptions 
	 */
	public void refresh(T entity,LockOptions lockOptions) {
		if (lockOptions == null) {
			refresh(entity);
		} else {
			getSession().refresh(entity, lockOptions);
		}
	}
	
	/**
	 * 将对象变为游离状�?
	 */
	public void evict(final T entity) {
		getSession().evict(entity);
		logger.debug("evict entity: {}", entity);
	}

	/**
	 * 合并修改的对�?
	 */
	public void merge(final T entity) {
		getSession().merge(entity);
		logger.debug("merge entity: {}", entity);
	}
	
	/**
	 * 如果session中存在相同持久化识别的实例，用给出的对象的状态覆盖持久化实例
	 * 
	 * @param entityName 持久化对象名�?
	 * @param entity 持久化实�?
	 */
	public void merge(String entityName,T entity) {
		getSession().merge(entityName, entity);
	}

	/**
	 * 删除对象.
	 * <br>逻辑删除 实体包含StatusDelete注解�?将对象标记为注解的删除状态�?.
	 * @param entity 对象必须是session中的对象或含id属�?的transient对象.
	 */
	public void delete(final T entity) {
		/**
		*Delete delete = Reflector.getAnnotation(entityClass,Delete.class);
		*if (delete != null) {
		*	Object value = ConvertUtils.convertToObject(delete.value(), delete.type().getValue());
		*	Reflector.invokeSetter(entity, delete.propertyName(), value);
		*	update(entity);
		*} else {
		*/
		getSession().delete(entity);
		//}
		logger.debug("delete entity: {}", entity);
	}
	
	/**
	 * 删除全部�?
	 * 
	 * @param entitys
	 */
	public void deleteAll(Collection<T> entitys) {
		for (Object entity : entitys) {
			getSession().delete(entity);
			getSession().flush();
		}
	}

	/**
	 * 按id删除对象.
	 */
	public void delete(final PK id) {
		delete(get(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/**
	 * 按id获取对象(直接返回实体�?.
	 */
	public T get(final PK id) {
		return (T) getSession().get(entityClass, id);
	}
	/**
	 * 按id获取对象(实体的代理类实例,延迟缓存).
	 */
	public T load(final PK id) {
		return (T) getSession().load(entityClass, id);
	}
	/**
	 * 按id获取对象(实体的代理类实例,是否枷锁�?.
	 * @param id
	 * @param lockOptions
	 * @return
	 */
	public T load(Serializable id, LockOptions lockOptions) {
		T entity = null;
		if (lockOptions !=null) {
			entity = (T) getSession().load(entityClass, id,lockOptions);
		} else {
			entity = (T) getSession().load(entityClass, id);
		}
		return entity;
	}
	
	/**
	 * 按id列表获取对象列表.
	 */
	public List<T> get(final Collection<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}
	

	/**
	 *	获取全部对象.
	 */
	public List<T> getAll() {
		return find();
	}

	/**
	 *	获取全部对象, 支持按属性行�?
	 */
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}
	
	/**
	 * 获取全部对象, 支持按属性行�?
	 * 
	 * @param orderBy
	 *            排序字段 多个排序字段时用','分隔.
	 * @param order
	 *            排序方式"asc"�?desc" 中间�?,"分割
	 */
	public List<T> getAll(String orderBy, String order) {
		Criteria c = createCriteria();
		//设置排序
		setPageParameterToCriteria(c, orderBy, order);
		return c.list();
	}

	/**
	 * 按属性查找对象列�? 匹配方式为相�?
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按属性查找唯�?���? 匹配方式为相�?
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 数量可变的参�?按顺序绑�?
	 */
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 命名参数,按名称绑�?
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}
	
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参�?按顺序绑�?
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 命名参数,按名称绑�?
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 数量可变的参�?按顺序绑�?
	 * @return 更新记录�?
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 命名参数,按名称绑�?
	 * @return 更新记录�?
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 数量可变的参�?按顺序绑�?
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 命名参数,按名称绑�?
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}
	
	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param orderBy 多个排序字段时用','分隔.
	 * @param orderBy 排序字段 多个排序字段时用','分隔.
	 * @param order 排序方式"asc"�?desc" 中间�?,"分割
	 * @return
	 */
	public List<T> find(final String orderBy, final String order,final Criterion... criterions) {
		Criteria c = createCriteria(criterions);
		//设置排序
		setPageParameterToCriteria(c, orderBy, order);
		return c.list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 初始化对�?
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前�?��进行初始�?
	 * 如果传入entity, 则只初始化entity的直接属�?但不会初始化延迟加载的关联集合和属�?.
	 * 如需初始化关联属�?�?���?
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属�?.
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * clear当前Session.
	 */
	public void clear() {
		getSession().clear();
	}
	
	/**
	 * 设置FlushMode.
	 */
	public void setFlushMode(FlushMode mode) {
		getSession().setFlushMode(mode);
	}
	
	/**
	 * 为Query添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, �?��进行distinct处理.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, �?��进行distinct处理.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}
	
	/**
	 * 获取实体名称
	 * 
	 * @return String
	 */
	public String getEntityName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
		return meta.getEntityName();
	}

	/**
	 * 判断对象的属性�?在数据库内是否唯�?
	 * 
	 * 在修改对象的情景�?如果属�?新修改的�?value)等于属�?原来的�?(orgValue)则不作比�?
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
	//原生SQL
	/**
	 * 根据查询SQL与参数列表创建SQLQuery对象.
	 * 
	 * @param values 数量可变的参�?按顺序绑�?
	 */
	public SQLQuery createSQLQuery(final String sql, final Object... values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				sqlQuery.setParameter(i, values[i]);
			}
		}
		return sqlQuery;
	}
	
	/**
	 * 根据查询SQL与参数列表创建SQLQuery对象.
	 * 
	 * @param sql
	 * @param values 命名参数,按名称绑�?
	 * @return
	 */
	public SQLQuery createSQLQuery(final String sql,final Map<String, ?> values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if (values != null) {
			Set<String> set = values.keySet();
			for(String s:set){
				sqlQuery.setParameter(s,values.get(s));
			}
		}
		return sqlQuery;
	}
	
	/**
	 * 执行原生sql.
	 */
	public void executeJdbcUpdate(String sql) throws DaoException{
			getSession().doWork(new PrepareStatementWork(sql));
	}
	
	
	/**
	 * 判断entity实例是否已经与session缓存关联,是返回true,否则返回false
	 * 
	 * @param entity 实例
	 * 
	 * @return boolean
	 */
	public boolean contains(Object entity) {
		return getSession().contains(entity);
	}
	
	
	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c,final String orderBy,final String order) {
			if (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order)) {
				String[] orderByArray = StringUtils.split(orderBy, ',');
				String[] orderArray = StringUtils.split(order, ',');
				for (int i = 0; i < orderByArray.length; i++) {
					if ("asc".equals(orderArray[i])) {
						c.addOrder(Order.asc(orderByArray[i]));
					} else {
						c.addOrder(Order.desc(orderByArray[i]));
					}
				}
			}
		return c;
	}
}