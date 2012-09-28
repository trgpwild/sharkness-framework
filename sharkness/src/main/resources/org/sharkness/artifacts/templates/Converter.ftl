package ${basePackage}.converter;

import javax.faces.convert.FacesConverter;

import ${completePathOfClassId};
import ${basePackage}.entity.${model};
import ${basePackage}.service.${model}Service;
import org.sharkness.jsf.support.ModelConverter;

@SuppressWarnings("serial")
@FacesConverter("${model}FacesConverter")
public class ${model}Converter extends ModelConverter<${classId}, ${model}, ${model}Service> {

}