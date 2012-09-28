package ${basePackage}.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import ${basePackage}.entity.${model};
import ${basePackage}.service.${model}Service;
import org.sharkness.jsf.support.ModelController;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class ${model}Controller extends ModelController<${model}, ${model}Service> {

}