package org.sharkness.business.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("deprecation")
public abstract class User<R extends Role> extends Model<Long> implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	public User() {
		this.setAccountNonExpired(true);
		this.setAccountNonLocked(true);
		this.setCredentialsNonExpired(true);
		this.setEnabled(true);
		setPassword("");
	}

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract void setPassword(String password);

	public abstract void setUsername(String username);

	public abstract void setAccountNonExpired(boolean accountNonExpired);

	public abstract void setAccountNonLocked(boolean accountNonLocked);

	public abstract void setCredentialsNonExpired(boolean credentialsNonExpired);

	public abstract void setEnabled(boolean enabled);

	public abstract String getEmail();

	public abstract void setEmail(String email);

	public abstract boolean isAtivo();

	public abstract void setAtivo(boolean ativo);

	public abstract int getTries();

	public abstract void setTries(int tries);

	public abstract Set<R> getRoles();

	public abstract void setRoles(Set<R> roles);

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		for (GrantedAuthority role : getRoles()) {
			list.add(new GrantedAuthorityImpl(role.getAuthority()));
		}
		return list;
	}

	@Override
	public abstract String getPassword();

	@Override
	public abstract String getUsername();

	@Override
	public abstract boolean isAccountNonExpired();

	@Override
	public abstract boolean isAccountNonLocked();

	@Override
	public abstract boolean isCredentialsNonExpired();

	@Override
	public abstract boolean isEnabled();
	
}