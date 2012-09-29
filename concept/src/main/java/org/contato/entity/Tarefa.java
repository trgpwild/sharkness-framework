package org.contato.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.sharkness.artifacts.annotation.NoPanelForm;
import org.sharkness.artifacts.annotation.RemoteBean;
import org.sharkness.artifacts.annotation.View;
import org.sharkness.business.entity.Model;

@Entity
@RemoteBean
@Table(name="tarefa")
public class Tarefa extends Model<Long> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@NoPanelForm
	@View(dataTable=false)
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@NotEmpty
	@Column(name="nome")
	private String nome;

	@Temporal(TemporalType.DATE)
	private Date dataExecucao;
	
	@OneToOne
	@JoinColumn(name="id_usuario")
	@View(propSortAndFilter="username")
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Date getDataExecucao() {
		return dataExecucao;
	}
	
	public void setDataExecucao(Date dataExecucao) {
		this.dataExecucao = dataExecucao;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return getNome();
	}

}