package com.locadoravitoria.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locadoravitoria.api.entities.TipoContato;
import com.locadoravitoria.api.services.TipoContatoService;
import com.locadoravitoria.api.utils.Response;

@RestController
@RequestMapping("/api/tipocontato")
@CrossOrigin(origins = "*")
public class TipoContatoController {
	private static final Logger log = LoggerFactory.getLogger(TipoContatoController.class);

	@Autowired
	private TipoContatoService tipoContatoService;
	
	public TipoContatoController() {

	}

	/**
	 * Cadastra um TipoContato no sistema
	 * 
	 * @param TipoContato
	 * @param result
	 * @return ResponseEntity<Response<TipoContato>>
	 */
	@PostMapping
	public ResponseEntity<Response<TipoContato>> cadastrar(@Valid @RequestBody TipoContato tipoContato, BindingResult result) {

		log.info("Cadastrando tipoContato: {}", tipoContato);
		Response<TipoContato> response = new Response<TipoContato>();

		validarDadosExistentes(tipoContato, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro do tipoContato: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.tipoContatoService.persistir(tipoContato);

		response.setData(tipoContato);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna uma lista de todos TipoContatos
	 * 
	 * @return ResponseEntity<Response<List<TipoContatoDto>>>
	 */
	@GetMapping
	public ResponseEntity<Response<List<TipoContato>>> listarTodos() {
		Response<List<TipoContato>> response = new Response<List<TipoContato>>();

		List<TipoContato> tipoContatos = tipoContatoService.listarTodos();

		if (tipoContatos.isEmpty()) {
			log.info("Nenhum tipoContato encontrado.");
			response.getErrors().add("Nenhum tipoContato encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		tipoContatos.stream().sorted((p1, p2) -> p1.getNome().compareTo(p2.getNome()));

		response.setData(tipoContatos);
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca do TipoContato por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<TipoContato>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<TipoContato>> buscarPorId(@PathVariable("id") Long id) {

		log.info("Buscando tipoContato pelo ID: {}", id);
		Response<TipoContato> response = new Response<TipoContato>();

		Optional<TipoContato> tipoContato = tipoContatoService.buscarPorId(id);

		if (!tipoContato.isPresent()) {
			log.info("TipoContato não encontrado para o ID: {}", id);
			response.getErrors().add("TipoContato não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(tipoContato.get());
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Validações especificas
	 * 
	 * @param TipoContatoDto
	 * @param result
	 */
	public void validarDadosExistentes(TipoContato tipoContato, BindingResult result) {
		// sem validações especificas
	}

}
