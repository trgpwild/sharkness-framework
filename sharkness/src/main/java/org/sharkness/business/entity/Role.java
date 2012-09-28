package org.sharkness.business.entity;

import org.sharkness.business.entity.Model;
import org.springframework.security.core.GrantedAuthority;

public abstract class Role extends Model<Long> implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	public abstract Long getId();

	public abstract void setId(Long id);

//	public abstract Set<U> getUsers();

//	public abstract void setUsers(Set<U> users);

	public abstract String getAuthority();
	
	public abstract void setAuthority(String authority);
	
}