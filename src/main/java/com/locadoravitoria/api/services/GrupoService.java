package com.locadoravitoria.api.services;

import java.util.List;
import java.util.Optional;

import com.locadoravitoria.api.entities.Grupo;

public interface GrupoService {
	/**
	 * Retorna o grupo
	 * @param id
	 * @return Optional<Grupo>
	 */
	Optional<Grupo> buscarPorId(Long id);
	
	/**
	 * Cadastra um novo Grupo
	 * @param Grupo
	 * @return Grupo
	 */
	Grupo persistir(Grupo grupo);
	
	/**
	 * Remove um Grupo
	 * 
	 * @param Grupo
	 */
	void removerGrupo(Grupo grupo);
	
	/**
	 * Lista todos os grupos
	 * @return List<Grupo>
	 */
	List<Grupo> listarTodos();
}
