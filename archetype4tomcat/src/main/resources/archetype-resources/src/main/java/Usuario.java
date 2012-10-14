#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.entity;

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
import javax.persistence.Transient;

import org.sharkness.artifacts.annotation.NoPanelForm;
import org.sharkness.artifacts.annotation.RemoteBean;
import org.sharkness.artifacts.annotation.View;
import org.sharkness.business.entity.User;

@Entity
@RemoteBean
@Table(name="usuario")
public class Usuario extends User<Perfil> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@NoPanelForm
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="login")
	private String username;

	@Column(name="senha")
	@View(dataTable=false, password=true)
	private String password;
	
	@Column(name="email")
	private String email;

	@Column(name="ativo")
	private boolean ativo;

	@Column(name="tentativas_login")
	private int tries;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="usuario_perfil",
			joinColumns=@JoinColumn(name="id_usuario"),
			inverseJoinColumns=@JoinColumn(name="id_perfil"))
	private Set<Perfil> roles;

	@Transient @NoPanelForm
	private boolean accountNonExpired;

	@Transient @NoPanelForm
	private boolean accountNonLocked;

	@Transient @NoPanelForm
	private boolean credentialsNonExpired;

	@Transient @NoPanelForm
	private boolean enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public Set<Perfil> getRoles() {
		return roles;
	}

	public void setRoles(Set<Perfil> roles) {
		this.roles = roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@Override
	public String toString() {
		return username;
	}

}