package org.sharkness.business.entity;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public abstract class Model<IdType> implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract IdType getId();
	
	public abstract void setId(IdType id);
	
	public boolean equals(Object o) {
		
		if (o instanceof Model) {
			
			if (((Model)o).getId().equals(getId())) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public abstract String toString();

}