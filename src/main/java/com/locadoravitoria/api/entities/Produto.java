package com.locadoravitoria.api.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = -4267544497494589888L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@OneToOne
	private Grupo grupo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_pai_id")
	List<Sub> subs = new ArrayList<>();

//	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
//	   @JoinTable(name="combo",
//	             joinColumns={@JoinColumn(name="produto_id",
//	              referencedColumnName="id")},
//	             inverseJoinColumns={@JoinColumn(name="produto_combo_id",
//	               referencedColumnName="id")})
//	@JsonManagedReference   
//	private List<Produto> combo;

	@Column(name = "caracteristica", nullable = false)
	private String caracteristica;

	public Produto() {
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

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public List<Sub> getSubs() {
		return subs;
	}

	public void setSubs(List<Sub> subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", grupo=" + grupo + ", caracteristica=" + caracteristica + "]";
	}

}
