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

	@Override
	public Class<T> getObjClass() {
		return getDao().getObjClass();
	}
	
	private Dao getDaoFromContext() {

		try {

			getLogger().info("ModelService.getDaoFromContext: Loading...");
			
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			
			Class<Dao> daoService = ModelFactory.getClassDao(this);
			
			Dao dao = wac.getBean(daoService);

			getLogger().info("ModelService.getDaoFromContext: Loaded.");
			
			return dao;
			
		} catch (Exception e) {
			
			getLogger().error("ModelService.getDaoFromContext", e);
			
			return null;
			
		}

	}

	@Override
	public List<T> list() throws Exception {
		
		try {
		
			getLogger().debug(new StringBuilder("ModelService.list: ")
				.append(getDao().getObjClass().getSimpleName())
			.toString());

			return getDao().list();

		} catch (Exception e) {
		
			getLogger().error("ModelService.list", e);
			
			throw e;
			
		}
		
	}

	@Override
	public void insert(T obj) throws Exception {

		try {
		
			getLogger().debug(new StringBuilder("ModelService.insert[")
				.append(getDao().getObjClass().getSimpleName()).append("](").append(obj).append(")")
			.toString());

			getDao().insert(obj);
		
		} catch (Exception e) {
			
			getLogger().error("ModelService.insert", e);
			
			throw e;
			
		}
		
	}

	@Override
	public void update(T obj) throws Exception {
		
		try {
			
			getLogger().debug(new StringBuilder("ModelService.update[")
				.append(getDao().getObjClass().getSimpleName()).append("](").append(obj).append(")")
			.toString());

			getDao().update(obj);
		
		} catch (Exception e) {
			
			getLogger().error("ModelService.update", e);
			
			throw e;
		
		}

	}

	@Override
	public void delete(IdType id) throws Exception {

		try {
		
			getLogger().debug(new StringBuilder("ModelService.delete[")
				.append(getDao().getObjClass().getSimpleName()).append("](id=").append(id).append(")")
			.toString());

			delete(getById(id));
		
		} catch (Exception e) {
		
			getLogger().error("ModelService.delete(id)");
			
			throw e;
		
		}
	
	}

	@Override
	public void delete(T obj) throws Exception {

		try {
		
			getLogger().debug(new StringBuilder("ModelService.delete[")
				.append(getDao().getObjClass().getSimpleName()).append("](").append(obj).append(")")
			.toString());

			getDao().delete(obj);
		
		} catch (Exception e) {
		
			getLogger().error("ModelService.delete(obj)", e);
			
			throw e;
		
		}
	
	}

	@Override
	public Boolean existsById(IdType id) throws Exception {

		try {

			getLogger().debug(new StringBuilder("ModelService.exists[")
				.append(getDao().getObjClass().getSimpleName()).append("](id=").append(id).append(")")
			.toString());

			return (getById(id) != null)? true : false;

		} catch (Exception e) {

			getLogger().error("ModelService.existsById");

			throw e;

		}

	}

	@Override
	public T getById(IdType id) throws Exception {

		try {

			getLogger().debug(new StringBuilder("ModelService.getById[")
				.append(getDao().getObjClass().getSimpleName()).append("](id").append(id).append(")")
			.toString());

			return getDao().getById(id);

		} catch (Exception e) {

			getLogger().error("ModelService.getById", e);

			throw e;

		}

	}

	@Override
	public Integer getSizePagination(Map<String, String> filters, Locale locale) {

		try {
		
			getLogger().debug(new StringBuilder("ModelService.getSizePagination[")
				.append(getDao().getObjClass().getSimpleName()).append("](filters=").append(filters)
				.append(", locale=").append(locale.getDisplayName()).append(")")
			.toString());

			return getDao().getSizePagination(filters, locale);
		
		} catch (Exception e) {

			getLogger().error("ModelService.getSizePagination", e);

			return 0;

		}
		
	}

	@Override
	public List<T> getListPagination(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters, Locale locale) {

		try {

			getLogger().debug(new StringBuilder("ModelService.getListPagination[")
				.append(getDao().getObjClass().getSimpleName()).append("](filters=").append(filters)
				.append(", first=").append(first).append(", pageSize=").append(pageSize)
				.append(", sortField=").append(sortField).append(", sortOrder=").append(sortOrder)
				.append(", locale=").append(locale.getDisplayName()).append(")")
			.toString());

			return getDao().getListPagination(first, pageSize, sortField, sortOrder, filters, locale);

		} catch (Exception e) {

			getLogger().error("ModelService.getListPagination", e);

			return new ArrayList<T>();

		}

	}

}