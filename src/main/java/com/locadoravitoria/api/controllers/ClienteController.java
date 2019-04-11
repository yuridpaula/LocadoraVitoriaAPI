package com.locadoravitoria.api.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locadoravitoria.api.entities.Cliente;
import com.locadoravitoria.api.services.ClienteService;
import com.locadoravitoria.api.utils.Response;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController extends GenericController<Cliente, Long> {
	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteService clienteService;

	public ClienteController() {

	}

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
}
