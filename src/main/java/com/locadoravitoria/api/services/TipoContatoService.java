package com.locadoravitoria.api.services;

import java.util.List;
import java.util.Optional;

import com.locadoravitoria.api.entities.TipoContato;

public interface TipoContatoService {
	/**
	 * Retorna o tipoContato
	 * 
	 * @param id
	 * @return Optional<TipoContato>
	 */
	Optional<TipoContato> buscarPorId(Long id);

	/**
	 * Cadastra um novo tipoContato
	 * 
	 * @param TipoContato
	 * @return TipoContato
	 */
	TipoContato persistir(TipoContato tipoContato);

	/**
	 * Remove um TipoContato
	 * 
	 * @param TipoContato
	 */
	void removerTipoContato(TipoContato tipoContato);

	/**
	 * Lista todos os tipoContatos
	 * 
	 * @return List<TipoContato>
	 */
	List<TipoContato> listarTodos();

}
