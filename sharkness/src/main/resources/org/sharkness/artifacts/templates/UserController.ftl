package ${basePackage}.controller;

import javax.faces.bean.ManagedBean;

import ${basePackage}.entity.${model};
import ${basePackage}.service.${model}Service;
import org.sharkness.jsf.support.ModelController;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

@ManagedBean
@SuppressWarnings("serial")
public class ${model}Controller extends ModelController<${classId}, ${model}, ${model}Service> {

	@Override
	protected ${model} modelProcessBeforeSave(${model} model) {
		try {
			MessageDigestPasswordEncoder passwordEncoder = ctx().getBean(MessageDigestPasswordEncoder.class);
			${model} ${model?uncap_first} = model;
			if (${model?uncap_first}.getPassword() != null && ${model?uncap_first}.getPassword().trim().length() > 0) {
				${model?uncap_first}.setPassword(passwordEncoder.encodePassword(${model?uncap_first}.getPassword(), null));
			} else {
				${model?uncap_first}.setPassword(getModelService().getById(${model?uncap_first}.getId()).getPassword());
			}
			return ${model?uncap_first};
		} catch (Exception e) {
			return model;
		}
	}

}