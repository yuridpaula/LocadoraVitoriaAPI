package com.locadoravitoria.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class ProdutoFilho implements Serializable {

	private static final long serialVersionUID = -4267544497494589888L;

	@Id
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "caracteristica", nullable = false)
	private String caracteristica;

	public ProdutoFilho() {
	}
	
	public ProdutoFilho(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}
	

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", caracteristica=" + caracteristica +  "]";
	}

}
