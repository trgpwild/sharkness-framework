package ${basePackage}.dao;

import ${completePathOfClassId};
import ${basePackage}.dao.${model}Dao;
import ${basePackage}.entity.${model};
import org.sharkness.business.support.ModelDao;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository(value="${model?uncap_first}Dao")
public class ${model}Dao extends ModelDao<${classId}, ${model}> {

}