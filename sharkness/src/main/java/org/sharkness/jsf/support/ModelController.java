package org.sharkness.jsf.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.sharkness.business.entity.Model;
import org.sharkness.business.factory.ModelFactory;
import org.sharkness.business.support.ModelService;
import org.sharkness.helper.I18n;
import org.sharkness.logging.support.LoggerFactory;
import org.sharkness.web.component.ControllerComponent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@SessionScoped
@SuppressWarnings({"serial","rawtypes","unchecked"})
public abstract class ModelController<IdType extends Serializable, T extends Model<IdType>, Service extends ModelService> extends SimpleController implements ControllerComponent {

	private T model;

	private Service modelService;

	private Class<T> klassModel;

	private LazyDataModel<T> dataModel;

	private boolean editModel = false;

	protected Logger getLogger() {
		return LoggerFactory.getLogger();
	}
	
	public void setModelService(Service modelService) {
		this.modelService = modelService;
	}

	public Service getModelService() {
		if (this.modelService == null) setModelService(getModelServiceFromContext());
		return this.modelService;
	}

	private Service getModelServiceFromContext() {
		
		try {
			
			getLogger().info("ModelController.getModelServiceFromContext: Loading...");
			
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			
			Class<Service> klassService = ModelFactory.getClassModelService(this);
			
			Service service = wac.getBean(klassService);
			
			getLogger().info("ModelController.getModelServiceFromContext: Loaded.");

			return service;
		
		} catch (Exception e) {
			
			getLogger().error("ModelController.getModelServiceFromContext", e);
			
			return null;
		
		}
	
	}

	public ModelController() {
		
		try {
			
			getLogger().info(new StringBuilder("ModelController.constructor[")
				.append(this.getClass().getSimpleName()).append("]: Loading...")
			.toString());
			
			this.klassModel = ModelFactory.getClass(this);
			
			model = newInstanceModel();
			
			createLazyModel();
			
			getLogger().info(new StringBuilder("ModelController.constructor[")
				.append(this.getClass().getSimpleName()).append("]: Loaded.")
			.toString());
		
		} catch (Exception e) {
			
			getLogger().error("ModelController.constructor", e);
		
		}
	
	}

	private void createLazyModel() {
		
		getLogger().info("ModelController.createLazyModel: Creating...");
		
		this.dataModel = new LazyDataModel<T>() {
			
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
			
				List<T> models = (List<T>) getModelService().getListPagination(first, pageSize, sortField, convertSortOrder(sortOrder), filters, getLocaleContext());
				
				this.setRowCount(getModelService().getSizePagination(filters, getLocaleContext()));
				
				this.setPageSize(pageSize);
				
				return models;
			
			}
		
		};
	
		getLogger().info("ModelController.createLazyModel: Created...");

	}

	public List<T> getListModel() throws Exception {
	
		try {
			
			getLogger().debug(new StringBuilder("ModelController.getListModel: ")
				.append(getModelService().getObjClass().getSimpleName())
			.toString());

			return getModelService().list();

		} catch (Exception e) {
			
			getLogger().error("ModelController.list", e);
			
			throw e;
			
		}

	}

	public LazyDataModel<T> getListDataModel() throws Exception {

		getLogger().info("ModelController.getListDataModel: getting...");
		
		return dataModel;

	}

	public T getModel() {

		getLogger().info("ModelController.getModel: getting...");
		
		return model;
	
	}

	public void setModel(T model) {

		getLogger().info("ModelController.setModel: setting...");
		
		this.model = model;
	
	}

	public void prepareAddModel(ActionEvent actionEvent) {
		
		getLogger().info("ModelController.prepareAddModel: preparing...");

		refresh();
		
		editModel = false;
		
		model = newInstanceModel();

		getLogger().info("ModelController.prepareAddModel: prepared.");

	}

	public void prepareEditModel(ActionEvent actionEvent) {

		getLogger().info("ModelController.prepareEditModel: preparing...");

		refresh();
		
		editModel = true;
		
		model = dataModel.getRowData();
	
		getLogger().info("ModelController.prepareEditModel: prepared.");

	}

	public void selectModel() {

		getLogger().info("ModelController.selectModel: selecting...");
		
		setModel(dataModel.getRowData());

	}

	public String deleteModel() throws Exception {

		try {
			
			getLogger().debug(new StringBuilder("ModelController.delete[")
				.append(getModelService().getObjClass().getSimpleName()).append("](id=").append(getModel().getId()).append(")")
			.toString());

			getModelService().delete(getModel().getId());
		
		} catch (DataIntegrityViolationException e) {
			
			I18n i18n = I18n.get("global.delete.dataIntegrityViolationException");
		
			message(i18n);
			
			getLogger().error(i18n.toString(), e);
		
		} catch (Exception e) {
			
			I18n i18n = I18n.get("global.delete.exception").add(" ERR: ").add(e.getMessage());
			
			message(i18n);
			
			getLogger().error(i18n.toString(), e);
		
		}
		
		return "index";
	
	}

	public boolean addModel(ActionEvent actionEvent) throws Exception {

		try {
			
			getLogger().debug(new StringBuilder("ModelController.addModel[")
				.append(getModelService().getObjClass().getSimpleName()).append("]: adding...")
			.toString());
	
			getModelService().insert(modelProcessBeforeSave(model));
		
			return true;

		} catch (Exception e) {
			
			getLogger().error("ModelController.editModel", e);
			
			throw e;
			
		}

	}

	public boolean editModel(ActionEvent actionEvent) throws Exception {
		
		try {
			
			getLogger().debug(new StringBuilder("ModelController.editModel[")
				.append(getModelService().getObjClass().getSimpleName()).append("]: editing...")
			.toString());

			getModelService().update(modelProcessBeforeSave(model));

			return true;

		} catch (Exception e) {
			
			getLogger().error("ModelController.editModel", e);
			
			throw e;
			
		}
	
	}

	public void saveModel(ActionEvent actionEvent) throws Exception {
		
		getLogger().debug(new StringBuilder("ModelController.saveModel[")
			.append(getModelService().getObjClass().getSimpleName()).append("]: saving...")
		.toString());

		try {

			boolean saved = false;
			
			if (editModel) {
				
				saved = editModel(actionEvent);
			
			} else {
				
				saved = addModel(actionEvent);
			
			}
			
			getLogger().debug(new StringBuilder("ModelController.saveModel[")
				.append(getModelService().getObjClass().getSimpleName()).append("]: saved = ").append(saved)
			.toString());

			if (saved) {
				
				I18n i18n = I18n.get("global.save.success");
				
				message(i18n);
				
				getLogger().debug(new StringBuilder("ModelController.saveModel[")
					.append(getModelService().getObjClass().getSimpleName()).append("]: message = ").append(i18n.toString())
				.toString());

				editModel = true;
			
			}
		
		} catch (Exception e) {
			
			I18n i18n = I18n.get("global.save.failure");
			
			message(i18n);
			
			getLogger().error(i18n.toString(), e);
		
		}
	
	}

	protected T modelProcessBeforeSave(T model) {

		getLogger().debug("ModelController.modelProcessBeforeSave: Not implemented...");
		
		return model;

	}

	private T newInstanceModel() {

		try {
			
			getLogger().debug("ModelController.newInstanceModel: instantiating...");

			return klassModel.newInstance();

		} catch (Exception e) {
			
			getLogger().error("ModelController.newInstanceModel", e);

			return null;

		}

	}

}