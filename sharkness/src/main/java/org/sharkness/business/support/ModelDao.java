package org.sharkness.business.support;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.helper.HibernateCriteriaHelper;
import org.sharkness.jsf.support.SortOrder;
import org.sharkness.logging.support.LoggerFactory;
import org.sharkness.web.component.DaoComponent;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({ "unchecked", "serial" })
public class ModelDao<IdType, T extends Model<IdType>> implements DaoComponent {

	private Class<T> objClass;
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
	
	protected void setHibernateCriteriaHelper(HibernateCriteriaHelper hibernateCriteriaHelper) {
		this.hibernateCriteriaHelper = hibernateCriteriaHelper;
	}
	
	protected HibernateCriteriaHelper getHibernateCriteriaHelper() {
		if (hibernateCriteriaHelper == null) {
	        this.setHibernateCriteriaHelper(new HibernateCriteriaHelper(sessionFactory,getObjClass()));
		}
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
		Criteria c = getSession().createCriteria(getObjClass());
		c.setProjection(Projections.rowCount());
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c = getHibernateCriteriaHelper().setFilters(filters, c, locale);
		return Integer.parseInt(c.uniqueResult().toString());
	}

	public List<T> getListPagination(final int first, final int pageSize, final String sortField, final SortOrder sortOrder, final Map<String,String> filters, Locale locale) throws Exception {
		
		Criteria c = getSession().createCriteria(getObjClass());
				
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				
		c = getHibernateCriteriaHelper().setFilters(filters, c, locale);
				
		c.setFirstResult(first);
				
		c.setMaxResults(pageSize);
				
		if (sortField != null && !sortField.isEmpty()) {
			 if (sortOrder.equals(SortOrder.DESCENDING)) {
				 c.addOrder(Order.desc(sortField));
			 } else {
				 c.addOrder(Order.asc(sortField));
			 }
		} else {
			String defaultOrderField = getDefaultOrderField();
			if (defaultOrderField != null) {
				c.addOrder(Order.asc(defaultOrderField));
			}
		}
		
		List<T> list = c.list();
		
		return list;

	}
	
	protected String getDefaultOrderField() {
		return null;
	}

}