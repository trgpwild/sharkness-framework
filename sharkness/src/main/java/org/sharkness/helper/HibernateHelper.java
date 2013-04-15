package org.sharkness.helper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

@SuppressWarnings("serial")
public class HibernateHelper implements Serializable {

	private SessionFactory sessionFactory;
	
	public HibernateHelper(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ClassMetadata getClassMetadata(Class<?> classModel) {
		return sessionFactory.getClassMetadata(classModel);
	}
	
	public Type getHibernateTypeByNameProperty(Class<?> classModel, String nameProperty) {
		
		ClassMetadata hibernateMetadata = getClassMetadata(classModel);
		if (hibernateMetadata != null) {
			if (hibernateMetadata instanceof AbstractEntityPersister) {
			     AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
			     return persister.getPropertyType(nameProperty);
			}
		}
		return null;
	}
	
	public List<String> getHibernatePropertyNames(Class<?> classModel) {
		
		ClassMetadata hibernateMetadata = getClassMetadata(classModel);
		if (hibernateMetadata != null) {
			if (hibernateMetadata instanceof AbstractEntityPersister) {
			     AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
			     return Arrays.asList(persister.getPropertyNames());
			}
		}
		return null;
	}
	
	public StringBuilder putHqlParameters(Class<?> classModel, final Map<String, String> filters, StringBuilder hql) {

		if (filters != null && filters.size() > 0) {

			hql.append("where ");

			int quantidadeDeParametros = 0;
			
			for (Map.Entry<String, String> entry : filters.entrySet()) {
			
				String propertyTypeStr = entry.getKey().split("\\.")[0];
				Type propertyType = getHibernateTypeByNameProperty(classModel, propertyTypeStr);
				
				if (quantidadeDeParametros > 0) hql.append("and ");
				
				if (propertyType instanceof IntegerType || propertyType instanceof LongType) {
					hql.append("o.").append(entry.getKey()).append(" = ").append(entry.getValue()).append(" ");
				} else {
					hql.append("o.").append(entry.getKey()).append(" like '%").append(entry.getValue()).append("%' ");
				}
				
				quantidadeDeParametros++;
			
			}
		
		}
		
		return hql;
		
	}

}