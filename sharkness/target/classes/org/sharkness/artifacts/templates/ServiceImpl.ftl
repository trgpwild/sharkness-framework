package ${basePackage}.service.impl;

import ${completePathOfClassId};
import ${basePackage}.dao.${model}Dao;
import ${basePackage}.entity.${model};
import ${basePackage}.service.${model}Service;
import org.sharkness.business.support.ModelServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("serial")
@Service(value = "${model?uncap_first}Service")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ${model}ServiceImpl extends ModelServiceImpl<${classId}, ${model}, ${model}Dao> implements ${model}Service {

}