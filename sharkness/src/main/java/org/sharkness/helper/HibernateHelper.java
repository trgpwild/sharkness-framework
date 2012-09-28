package org.sharkness.helper;

import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.Type;

public class HibernateHelper {

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

}