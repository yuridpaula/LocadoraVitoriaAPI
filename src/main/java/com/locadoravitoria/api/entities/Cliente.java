package com.locadoravitoria.api.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Table;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = -3093362864518823057L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CPF
	@Column(nullable = false)
	private String cpf;

	@Column(nullable = false)
	private String rg;

	@Column(name = "data_nascimento", nullable = false)
	private Date dataNascimento;

	@Column(nullable = false)
	private String nome;

	@Column(name = "nome_conjuge")
	private String nomeConjuge;

	@Column(nullable = false)
	private String cep;

	@Column(nullable = false)
	private String rua;

	@Column(nullable = false)
	private String numero;

	private String complemento;

	@Column(nullable = false)
	private String bairro;

	@Column(nullable = false)
	private String cidade;

	@Column(nullable = false)
	private String uf;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id")
	List<Contato> contatos = new ArrayList<>();

	public Cliente() {
		super();
	}

	public Cliente(Long id, @CPF String cpf, String rg, Date dataNascimento, String nome, String nomeConjuge,
			String cep, String rua, String numero, String complemento, String bairro, String cidade, String uf,
			List<Contato> contatos) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
		this.nome = nome;
		this.nomeConjuge = nomeConjuge;
		this.cep = cep;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.contatos = contatos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeConjuge() {
		return nomeConjuge;
	}

	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", cpf=" + cpf + ", rg=" + rg + ", dataNascimento=" + dataNascimento + ", nome="
				+ nome + ", nomeConjuge=" + nomeConjuge + ", cep=" + cep + ", rua=" + rua + ", numero=" + numero
				+ ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", uf=" + uf + "]";
	}

}
