package com.locadoravitoria.api.services;

import java.util.List;

import com.locadoravitoria.api.entities.Cliente;

public interface ClienteService extends GenericService<Cliente, Long> {
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

}
