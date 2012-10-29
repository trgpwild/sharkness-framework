package org.sharkness.business.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.jsf.support.SortOrder;
import org.sharkness.logging.support.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("serial")
public class ModelServiceImpl<IdType extends Serializable, T extends Model<IdType>, Dao extends ModelDao<IdType, T>> implements ModelService<IdType, T> {

	private Dao dao;

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger();
	}
	
	public void setDefaultDao(Dao dao) {
		this.dao = dao;
	}
	
	public Dao getDao() {
		if (this.dao == null) setDefaultDao(getDaoFromContext());
		return this.dao;
	}

	private Dao getDaoFromContext() {

		try {

			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			
			Class<Dao> daoService = ModelFactory.getClassDao(this);
			
			Dao dao = wac.getBean(daoService);
			
			return dao;
			
		} catch (Exception e) {
			
			getLogger().error("ModelService.getDaoFromContext", e);
			
			return null;
			
		}

	}

	@Override
	public List<T> list() throws Exception {
		try {
			return getDao().list();
		} catch (Exception e) {
			getLogger().error("ModelService.list", e);
			throw new Exception(e.getCause());
		}
	}

	@Override
	public void insert(T obj) throws Exception {
		try {
			getDao().insert(obj);
		} catch (Exception e) {
			getLogger().error("ModelService.insert", e);
			throw new Exception(e.getCause());
		}
	}

	@Override
	public void update(T obj) throws Exception {
		try {
			getDao().update(obj);
		} catch (Exception e) {
			getLogger().error("ModelService.update", e);
			throw new Exception(e.getCause());
		}
	}

	@Override
	public void delete(IdType id) throws Exception {
		try {
			delete(getById(id));
		} catch (Exception e) {
			getLogger().error("ModelService.delete(id)");
			throw new Exception(e.getCause());
		}
	}

	@Override
	public void delete(T obj) throws Exception {
		try {
			getDao().delete(obj);
		} catch (Exception e) {
			getLogger().error("ModelService.delete(obj)", e);
			throw new Exception(e.getCause());
		}
	}

	@Override
	public Boolean existsById(IdType id) throws Exception {
		try {
			return (getById(id) != null)? true : false;
		} catch (Exception e) {
			getLogger().error("ModelService.existsById");
			throw new Exception(e.getCause());
		}
	}

	@Override
	public T getById(IdType id) throws Exception {
		try {
			return getDao().getById(id);
		} catch (Exception e) {
			getLogger().error("ModelService.getById", e);
			throw new Exception(e.getCause());
		}
	}

	@Override
	public Integer getSizePagination(Map<String, String> filters, Locale locale) {
		try {
			return getDao().getSizePagination(filters, locale);
		} catch (Exception e) {
			getLogger().error("ModelService.getSizePagination", e);
			return 0;
		}
	}

	@Override
	public List<T> getListPagination(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters, Locale locale) {
		try {
			return getDao().getListPagination(first, pageSize, sortField, sortOrder, filters, locale);
		} catch (Exception e) {
			getLogger().error("ModelService.getListPagination", e);
			return new ArrayList<T>();
		}
	}

}