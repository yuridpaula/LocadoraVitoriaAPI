package com.locadoravitoria.api.services;

import java.util.List;

import com.locadoravitoria.api.entities.Usuario;

public interface UsuarioService extends GenericService<Usuario, Long> {
		
	/**
	 * Retorna uma lista de usuários pelo email
	 * @param email
	 * @return List<Usuario>
	 */
	List<Usuario> buscarPorEmail(String email);
	
	/**
	 * Lista todos Usuários pelo nome
	 */
	List<Usuario> buscarPorNome(String nome);
}
