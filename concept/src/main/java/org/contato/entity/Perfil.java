package org.contato.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.sharkness.artifacts.annotation.NoPanelForm;
import org.sharkness.artifacts.annotation.View;
import org.sharkness.business.entity.Role;

@Entity
@Table(name="perfil")
public class Perfil extends Role {

	private static final long serialVersionUID = 1L;

	@Id
	@NoPanelForm
	@View(dataTable=false)
	@GeneratedValue
	@Column(name="id_perfil")
	private Long id;
	
	@NotEmpty
	@Column(name="descricao")
	private String authority;
	
	@NoPanelForm
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="usuario_perfil",
			joinColumns=@JoinColumn(name="id_perfil"),
			inverseJoinColumns=@JoinColumn(name="id_usuario"))
	private Set<Usuario> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Usuario> getUsers() {
		return users;
	}

	public void setUsers(Set<Usuario> users) {
		this.users = users;
	}

	public String getAuthority() {
		return this.authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String toString() {
		return authority;
	}

}