package com.locadoravitoria.api.services;

import java.util.List;
import java.util.Optional;

import com.locadoravitoria.api.entities.Produto;

public interface ProdutoService {
	/**
	 * Retorna o produto
	 * 
	 * @param id
	 * @return Optional<Produto>
	 */
	Optional<Produto> buscarPorId(Long id);

	/**
	 * Cadastra um novo produto
	 * 
	 * @param Produto
	 * @return Produto
	 */
	Produto persistir(Produto produto);

	/**
	 * Remove um Produto
	 * 
	 * @param Produto
	 */
	void removerProduto(Produto produto);

	/**
	 * Lista todos os produtos
	 * 
	 * @return List<Produto>
	 */
	List<Produto> listarTodos();

	/**
	 * Lista todos os produtos de um determinado grupo
	 * 
	 * @return List<Produto>
	 */
	List<Produto> buscarPorGrupo(Long grupo);

}
