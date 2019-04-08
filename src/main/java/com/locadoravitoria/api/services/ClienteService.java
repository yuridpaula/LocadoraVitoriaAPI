package com.locadoravitoria.api.services;

import java.util.List;
import java.util.Optional;

import com.locadoravitoria.api.entities.Cliente;

public interface ClienteService {
	/**
	 * Retorna o cliente
	 * 
	 * @param id
	 * @return Optional<Cliente>
	 */
	Optional<Cliente> buscarPorId(Long id);

	/**
	 * Retorna uma lista de clientes
	 * 
	 * @param cpf
	 * @return List<Cliente>
	 */
	List<Cliente> buscarPorCpf(String cpf);

	/**
	 * Retorna o cliente
	 * 
	 * @param contatoValor
	 * @return Optional<Cliente>
	 */
	List<Cliente> buscarPorContatoValor(String contatoValor);

	/**
	 * Cadastra um novo cliente
	 * 
	 * @param Cliente
	 * @return Cliente
	 */
	Cliente persistir(Cliente cliente);

	/**
	 * Remove um Cliente
	 * 
	 * @param Cliente
	 */
	void removerCliente(Cliente cliente);

	/**
	 * Lista todos os clientes
	 * 
	 * @return List<Cliente>
	 */
	List<Cliente> listarTodos();

}
