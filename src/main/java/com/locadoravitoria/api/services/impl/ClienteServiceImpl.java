package com.locadoravitoria.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locadoravitoria.api.entities.Cliente;
import com.locadoravitoria.api.repositories.ClienteRepository;
import com.locadoravitoria.api.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Optional<Cliente> buscarPorId(Long id) {
		log.info("Buscando cliente pelo ID {}", id);
		return clienteRepository.findById(id);
	}

	@Override
	public List<Cliente> buscarPorCpf(String cpf) {
		log.info("Buscando clientes pelo cpf: {}", cpf);
		return clienteRepository.findByCpfContaining(cpf);
	}

	@Override
	public List<Cliente> buscarPorContatoValor(String contatoValor) {
		log.info("Buscando cliente pelo Contato valor {}", contatoValor);
		return clienteRepository.findByContatosValorIgnoreCaseContaining(contatoValor);
	}

	@Override
	public Cliente persistir(Cliente cliente) {
		log.info("Persistindo cliente: {}", cliente);
		return clienteRepository.save(cliente);
	}

	@Override
	public void removerCliente(Cliente cliente) {
		log.info("Removendo cliente: {}", cliente);
		this.clienteRepository.delete(cliente);
	}

	@Override
	public List<Cliente> listarTodos() {
		log.info("Buscando todos clientes");
		return this.clienteRepository.findAllByOrderByNome();
	}
}
