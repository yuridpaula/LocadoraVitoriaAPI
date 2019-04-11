package com.locadoravitoria.api.services;

import java.util.List;

import com.locadoravitoria.api.entities.Grupo;

public interface GrupoService extends GenericService<Grupo, Long> {

	/**
	 * Lista todos os grupos pelo nome
	 * 
	 * @param nome
	 * 
	 * @return List<Grupo>
	 */
	List<Grupo> buscarPorNome(String nome);

}
