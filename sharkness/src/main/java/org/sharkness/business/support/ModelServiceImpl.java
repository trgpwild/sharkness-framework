package org.sharkness.business.support;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.jsf.support.SortOrder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("serial")
public class ModelServiceImpl<IdType extends Serializable, T extends Model<IdType>, Dao extends ModelDao<IdType, T>> implements ModelService<IdType, T> {

	private Dao dao;
	
	public void setDefaultDao(Dao dao) {
		this.dao = dao;
	}
	
	public Dao getDao() {
		if (this.dao == null) setDefaultDao(getDaoFromContext());
		return this.dao;
	}

	private Dao getDaoFromContext() {
		
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		
		Class<Dao> daoService = ModelFactory.getClassDao(this);
		
		Dao dao = wac.getBean(daoService);
		
		if (dao != null) {
			
			return dao;
			
		} else {
			
			return null;
			
		}
		
	}

	@Override
	public List<T> list() throws Exception {
		return getDao().list();
	}

	@Override
	public void insert(T obj) throws Exception {
		getDao().insert(obj);
	}

	@Override
	public void update(T obj) throws Exception {
		getDao().update(obj);
	}

	@Override
	public void delete(IdType id) throws Exception {
		delete(getDao().getById(id));
	}

	@Override
	public void delete(T obj) throws Exception {
		getDao().delete(obj);
	}

	@Override
	public Boolean existsById(IdType id) throws Exception {
		T bean = getDao().getById(id);
		return (bean==null)? false : true;
	}

	@Override
	public T getById(IdType id) throws Exception {
		return getDao().getById(id);
	}

	@Override
	public Integer getSizePagination(Map<String, String> filters, Locale locale) {
		return getDao().getSizePagination(filters, locale);
	}

	@Override
	public List<T> getListPagination(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters, Locale locale) {
		return getDao().getListPagination(first, pageSize, sortField, sortOrder, filters, locale);
	}

}