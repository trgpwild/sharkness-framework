package org.sharkness.business.support;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.helper.HibernateCriteriaHelper;
import org.sharkness.helper.HibernateHelper;
import org.sharkness.jsf.support.SortOrder;
import org.sharkness.logging.support.LoggerFactory;
import org.sharkness.web.component.DaoComponent;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({ "unchecked", "serial" })
public class ModelDao<IdType, T extends Model<IdType>> implements DaoComponent {

	private Class<T> objClass;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private HibernateHelper hibernateHelper;
	
	private HibernateCriteriaHelper hibernateCriteriaHelper;

	public Logger getLogger() {
		return LoggerFactory.getLogger();
	}

	public ModelDao() {
		this.setObjectClass((Class<T>) ModelFactory.getClassModel(this));
    }

	public ModelDao(SessionFactory sessionFactory) {
		this.setObjectClass((Class<T>) ModelFactory.getClassModel(this));
        this.sessionFactory = sessionFactory;
    }
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setHibernateHelper(HibernateHelper hibernateHelper) {
		this.hibernateHelper = hibernateHelper;
	}
	
	public HibernateHelper getHibernateHelper() {
		if (this.hibernateHelper == null) setHibernateHelper(new HibernateHelper(getSessionFactory()));
		return hibernateHelper;
	}
	
	protected void setHibernateCriteriaHelper(HibernateCriteriaHelper hibernateCriteriaHelper) {
		this.hibernateCriteriaHelper = hibernateCriteriaHelper;
	}
	
	protected HibernateCriteriaHelper getHibernateCriteriaHelper() {
		if (this.hibernateCriteriaHelper == null) setHibernateCriteriaHelper(new HibernateCriteriaHelper(sessionFactory,getObjClass()));
		return hibernateCriteriaHelper;
	}
    
	protected void setObjectClass(Class<T> objectKlass) {
        this.objClass = objectKlass;
    }
	
	protected Class<T> getObjClass() {
		return objClass;
	}
	
	public void insert(T obj) throws Exception {
		getSession().save(obj);
	}

	public void update(T obj) throws Exception {
		getSession().update(obj);
	}
	
	public void delete(T obj) throws Exception {
		getSession().delete(obj);
	}

	public List<T> list() throws Exception {
		Criteria c = getSession().createCriteria(getObjClass());
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		String defaultOrderField = getDefaultOrderField();
		if (defaultOrderField != null) {
			c.addOrder(Order.asc(defaultOrderField));
		}
		return c.list();
	}

	public T getById(final IdType id) throws Exception {
		return (T)getSession().get(getObjClass(), (Serializable) id);
	}
	
	public Integer getSizePagination(final Map<String,String> filters, Locale locale) throws Exception {
		
		StringBuilder hql = new StringBuilder("select count(o) from ").append(getObjClass().getSimpleName()).append(" o ");
		
		hql = getHibernateHelper().putHqlParameters(getObjClass(), filters, hql);
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return Integer.parseInt(query.uniqueResult().toString());
		
	}

	public List<T> getListPagination(final int first, final int pageSize, final String sortField, final SortOrder sortOrder, final Map<String,String> filters, Locale locale) throws Exception {
		
		StringBuilder hql = new StringBuilder("select o from ").append(getObjClass().getSimpleName()).append(" o ");
		
		hql = getHibernateHelper().putHqlParameters(getObjClass(), filters, hql);
		
		if (sortOrder != null && sortField != null && sortField.length() > 0) {
			if (sortOrder.equals(SortOrder.ASCENDING)) {
				hql.append("order by ").append(sortField).append(" asc");
			} else if (sortOrder.equals(SortOrder.DESCENDING)) {
				hql.append("order by ").append(sortField).append(" desc");
			}
		} else if (getDefaultOrderField() != null && getDefaultOrderField().length() > 0) {
			hql.append("order by ").append(getDefaultOrderField()).append(" asc");
		}
		
		Query query = getSession().createQuery(hql.toString());
		
		if (first > -1) query.setFirstResult(first);
		if (pageSize > -1) query.setMaxResults(pageSize);

		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return query.list();

	}

	protected String getDefaultOrderField() {
		return null;
	}

}