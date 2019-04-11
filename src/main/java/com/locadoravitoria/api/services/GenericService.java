package com.locadoravitoria.api.services;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {
	/**
	 * retorna a endidade pelo ID
	 * @param id
	 * @return Optional<T>
	 */
	Optional<T> buscarPorId(ID id);
	
	/**
	 * Cadastra uma nova entidade
	 * @param entity
	 * @return T
	 */
	T persistir(T entity);

	/**
	 * Remove uma entidade
	 * @param entity
	 */
	void remover(T entity);

	/**
	 * Lista todas as entidades
	 * @return
	 */
	List<T> listarTodos();

}
