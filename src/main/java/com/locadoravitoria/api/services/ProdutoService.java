package com.locadoravitoria.api.services;

import java.util.List;

import com.locadoravitoria.api.entities.Produto;

public interface ProdutoService extends GenericService<Produto, Long>{

	/**
	 * Lista todos os produtos pelo nome
	 * 
	 * @param nome
	 * 
	 * @return List<Produto>
	 */
	List<Produto> buscarPorNome(String nome);
}
