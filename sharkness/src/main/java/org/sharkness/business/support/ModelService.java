package org.sharkness.business.support;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sharkness.jsf.support.SortOrder;
import org.sharkness.web.component.ServiceComponent;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor=Exception.class)
public interface ModelService<IdType extends Serializable, T> extends ServiceComponent {

	public Logger getLogger();
	
	public void insert(T obj) throws Exception;
	
	public void update(T obj) throws Exception;
	
	public void delete(T obj) throws Exception;
	
	public void delete(IdType id) throws Exception;

	public Boolean existsById(IdType id) throws Exception;

	public T getById(IdType id) throws Exception;
	
	public List<T> list() throws Exception;
	
	public Integer getSizePagination(Map<String,String> filters, Locale locale);
	
	public List<T> getListPagination(int first, int pageSize, 
		String sortField, SortOrder sortOrder, Map<String,String> filters, Locale locale);
	
}