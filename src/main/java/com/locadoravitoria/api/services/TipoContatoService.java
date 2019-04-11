package com.locadoravitoria.api.services;

import java.util.List;

import com.locadoravitoria.api.entities.TipoContato;

public interface TipoContatoService extends GenericService<TipoContato, Long> {
	
	/**
	 * Lista todos os tipoContatos pelo nome
	 * 
	 * @return List<TipoContato>
	 */
	List<TipoContato> buscarPorNome(String nome);
}
