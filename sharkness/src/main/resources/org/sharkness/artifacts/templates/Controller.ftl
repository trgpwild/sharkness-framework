package ${basePackage}.controller;

import javax.faces.bean.ManagedBean;

import ${basePackage}.entity.${model};
import ${basePackage}.service.${model}Service;
import org.sharkness.jsf.support.ModelController;

@ManagedBean
@SuppressWarnings("serial")
public class ${model}Controller extends ModelController<${classId}, ${model}, ${model}Service> {

}