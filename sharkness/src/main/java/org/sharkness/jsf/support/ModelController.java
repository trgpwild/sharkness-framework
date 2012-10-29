package org.sharkness.jsf.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.business.support.ModelService;
import org.sharkness.helper.I18n;
import org.sharkness.web.component.ControllerComponent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings({"serial","rawtypes","unchecked"})
public abstract class ModelController<IdType extends Serializable, T extends Model<IdType>, Service extends ModelService> extends SimpleController implements ControllerComponent {

	private static final long serialVersionUID = 1L;

	private T model;

	private Service modelService;

	private Class<T> klassModel;

	private LazyDataModel<T> dataModel;

	private boolean editModel = false;

	protected Logger getLogger() {
		return getModelService().getLogger();
	}
	
	public void setModelService(Service modelService) {
		this.modelService = modelService;
	}

	public Service getModelService() {
		if (this.modelService == null)
			setModelService(getModelServiceFromContext());
		return this.modelService;
	}

	private Service getModelServiceFromContext() {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Class<Service> klassService = ModelFactory.getClassModelService(this);
		Service service = wac.getBean(klassService);
		if (service != null) {
			return service;
		} else {
			return null;
		}
	}

	public ModelController() {
		try {
			this.klassModel = ModelFactory.getClass(this);
			model = newInstanceModel();
			createLazyModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createLazyModel() {
		this.dataModel = new LazyDataModel<T>() {
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
				List<T> models = (List<T>) getModelService().getListPagination(first, pageSize, sortField, convertSortOrder(sortOrder), filters, getLocaleContext());
				this.setRowCount(getModelService().getSizePagination(filters, getLocaleContext()));
				this.setPageSize(pageSize);
				return models;
			}
		};
	}

	public List<T> getListModel() throws Exception {
		return getModelService().list();
	}

	public LazyDataModel<T> getListDataModel() throws Exception {
		return dataModel;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public void prepareAddModel(ActionEvent actionEvent) {
		refresh();
		editModel = false;
		model = newInstanceModel();
	}

	public void prepareEditModel(ActionEvent actionEvent) {
		refresh();
		editModel = true;
		model = dataModel.getRowData();
	}

	public void selectModel() {
		setModel(dataModel.getRowData());
	}

	public String deleteModel() throws Exception {
		try {
			getModelService().delete(model.getId());
		} catch (DataIntegrityViolationException e) {
			message(I18n.get("global.delete.dataIntegrityViolationException"));
		} catch (Exception e) {
			message(I18n.get("global.delete.exception").add(" ERR: ").add(e.getMessage()));
		}
		return "index";
	}

	public boolean addModel(ActionEvent actionEvent) throws Exception {
		getModelService().insert(modelProcessBeforeSave(model));
		return true;
	}

	public boolean editModel(ActionEvent actionEvent) throws Exception {
		getModelService().update(modelProcessBeforeSave(model));
		return true;
	}

	public void saveModel(ActionEvent actionEvent) throws Exception {
		try {
			boolean saved = false;
			if (editModel) {
				saved = editModel(actionEvent);
			} else {
				saved = addModel(actionEvent);
			}
			if (saved) {
				message(I18n.get("global.save.success"));
				editModel = true;
			}
		} catch (Exception e) {
			message(I18n.get("global.save.failure"));
		}
	}

	protected T modelProcessBeforeSave(T model) {
		return model;
	}

	private T newInstanceModel() {
		try {
			return klassModel.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

}