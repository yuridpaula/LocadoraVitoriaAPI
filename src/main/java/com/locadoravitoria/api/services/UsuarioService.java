package com.locadoravitoria.api.services;

import java.util.List;
import java.util.Optional;

import com.locadoravitoria.api.entities.Usuario;

public interface UsuarioService {
	/**
	 * Retorna o usuário
	 * @param id
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorId(Long id);
	
	/**
	 * Cadastra um novo usuário
	 * @param usuario
	 * @return Usuario
	 */
	Usuario persistir(Usuario usuario);
	
	/**
	 * Retorna o usuário
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorEmail(String email);
	
	/**
	 * Remove um Usuario
	 * 
	 * @param usuario
	 */
	void removerUsuario(Usuario usuario);
	
	/**
	 * Lista todos Usuários
	 */
	
	List<Usuario> listarTodos();
}
