package com.locadoravitoria.api.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "sub")
public class Sub implements Serializable {

	private static final long serialVersionUID = -8490406484356537261L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long quantidade;

	@OneToOne
	private ProdutoFilho produtoFilho;

	public Sub(Long id) {
		super();
	}

	public Sub() {
		super();
	}

	public Sub(Long id, Long quantidade, ProdutoFilho produtoFlho) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.produtoFilho = produtoFlho;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public ProdutoFilho getProdutoFilho() {
		return produtoFilho;
	}

	public void setProdutoFilho(ProdutoFilho produtoFilho) {
		this.produtoFilho = produtoFilho;
	}

	@Override
	public String toString() {
		return "Sub [id=" + id + ", quantidade=" + quantidade + "]";
	}

}
