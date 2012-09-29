package org.contato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.sharkness.artifacts.annotation.NoPanelForm;
import org.sharkness.artifacts.annotation.RemoteBean;
import org.sharkness.artifacts.annotation.View;
import org.sharkness.business.entity.Model;

@Entity
@RemoteBean
@Table(name="contato")
public class Contato extends Model<Long> {

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

	@NotEmpty
	@Column(name="endereco")
	private String endereco;

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
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	@Override
	public String toString() {
		return getNome();
	}

}