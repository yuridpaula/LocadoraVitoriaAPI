package com.locadoravitoria.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locadoravitoria.api.entities.Cliente;
import com.locadoravitoria.api.services.ClienteService;
import com.locadoravitoria.api.utils.Response;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {
	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteService clienteService;

	public ClienteController() {

	}

	/**
	 * Retorna uma lista de todos Clientes
	 * 
	 * @return ResponseEntity<Response<List<Cliente>>>
	 */
	@GetMapping
	public ResponseEntity<Response<List<Cliente>>> listarTodos() {
		Response<List<Cliente>> response = new Response<List<Cliente>>();

		List<Cliente> clientes = clienteService.listarTodos();

		if (clientes.isEmpty()) {
			log.info("Nenhum cliente encontrado.");
			response.getErrors().add("Nenhum cliente encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(clientes);
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca do Cliente por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Cliente>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Cliente>> buscarPorId(@PathVariable("id") Long id) {

		log.info("Buscando cliente pelo ID: {}", id);
		Response<Cliente> response = new Response<Cliente>();

		Optional<Cliente> cliente = clienteService.buscarPorId(id);

		if (!cliente.isPresent()) {
			log.info("Cliente não encontrado para o ID: {}", id);
			response.getErrors().add("Cliente não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(cliente.get());

		return ResponseEntity.ok(response);
	}

	/**
	 * Cadastra um Cliente no sistema
	 * 
	 * @param Cliente
	 * @param result
	 * @return ResponseEntity<Response<Cliente>>
	 */
	@PostMapping
	public ResponseEntity<Response<Cliente>> cadastrar(@Valid @RequestBody Cliente cliente, BindingResult result) {

		log.info("Cadastrando cliente: {}", cliente);
		Response<Cliente> response = new Response<Cliente>();

		validarDadosExistentes(cliente, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro do cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.clienteService.persistir(cliente);

		response.setData(cliente);
		return ResponseEntity.ok(response);
	}

	/**
	 * Remove um cliente
	 * 
	 * @param id
	 * @return ResponseEntity<Response<String>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Deletando cliente para o ID: {}", id);

		Response<String> response = new Response<String>();
		Optional<Cliente> cliente = this.clienteService.buscarPorId(id);

		if (!cliente.isPresent()) {
			log.info("Erro ao remover cliente. Cliente não encontrado para o ID: {}", id);
			response.getErrors().add("Erro ao remover cliente. Cliente não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.clienteService.remover(cliente.get());
		response.setData("Cliente ID " + id + " deletado.");
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de atualização do cliente
	 * 
	 * @param id
	 * @param Cliente
	 * @param result
	 * @return ResponseEntity<Response<Cliente>>
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<Cliente>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody Cliente cliente,
			BindingResult result) {

		log.info("Atualizando cliente: {}", cliente);
		Response<Cliente> response = new Response<Cliente>();

		Optional<Cliente> clienteBusca = this.clienteService.buscarPorId(id);

		if (!clienteBusca.isPresent()) {
			log.error("Cliente não encontrado para o id: {}", id);
			response.getErrors().add("cliente não encontrado para o id : " + id);
			return ResponseEntity.badRequest().body(response);
		} else if (!cliente.getId().equals(id)) {
			log.error("Identificador do Cliente diferente do informado: {}", id);
			response.getErrors().add("Identificador do Cliente diferente do informado!");
			return ResponseEntity.badRequest().body(response);
		}

		this.clienteService.persistir(cliente);
		response.setData(clienteBusca.get());
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca por cpf
	 * 
	 * @param cpf
	 * @return ResponseEntity<Response<List<Cliente>>>
	 */
	@GetMapping(value = "/cpf/{cpf}")
	public ResponseEntity<Response<List<Cliente>>> buscarPorCpf(@PathVariable("cpf") String cpf) {

		log.info("Buscando clientes pelo cpf: {}", cpf);
		Response<List<Cliente>> response = new Response<List<Cliente>>();

		List<Cliente> clientes = clienteService.buscarPorCpf(cpf);

		if (clientes.isEmpty()) {
			log.info("Nenhum cliente encontrado para o cpf: {}", cpf);
			response.getErrors().add("Nenhum cliente encontrado para o cpf: " + cpf);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(clientes);
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca por contatoValor
	 * 
	 * @param contatoValor
	 * @return ResponseEntity<Response<List<Cliente>>>
	 */
	@GetMapping(value = "/contatovalor/{contatoValor}")
	public ResponseEntity<Response<List<Cliente>>> buscarPorContatoValor(
			@PathVariable("contatoValor") String contatoValor) {

		log.info("Buscando cliente pelo contatoValor: {}", contatoValor);
		Response<List<Cliente>> response = new Response<List<Cliente>>();

		List<Cliente> clientes = clienteService.buscarPorContatoValor(contatoValor);

		if (clientes.isEmpty()) {
			log.info("Cliente não encontrado para o contatoValor: {}", contatoValor);
			response.getErrors().add("Cliente não encontrado para o contatoValor: " + contatoValor);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(clientes);
		return ResponseEntity.ok(response);
	}

	/**
	 * Validações especificas
	 * 
	 * @param cliente
	 * @param result
	 */
	public void validarDadosExistentes(Cliente cliente, BindingResult result) {
		List<Cliente> lista = this.clienteService.buscarPorCpf(cliente.getCpf());
		if (!lista.isEmpty()) {
			result.addError(new ObjectError("cpf", "Cpf já cadastrado"));
		}

		if (cliente.getId() != null) {
			result.addError(new ObjectError("Id", "Cliente possui id, utilizar método PUT para atualizar dados."));
		}
	}
}
