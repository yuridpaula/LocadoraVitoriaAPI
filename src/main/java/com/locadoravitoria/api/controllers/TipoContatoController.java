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
	 * Retorna uma lista de todos TipoContatos
	 * 
	 * @return ResponseEntity<Response<List<TipoContato>>>
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
	 * Remove um tipoContato
	 * 
	 * @param id
	 * @return ResponseEntity<Response<String>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Deletando cliente para o ID: {}", id);

		Response<String> response = new Response<String>();
		Optional<TipoContato> tipoContato = this.tipoContatoService.buscarPorId(id);

		if (!tipoContato.isPresent()) {
			log.info("Erro ao remover tipoContato. TipoContato não encontrado para o ID: {}", id);
			response.getErrors().add("Erro ao remover tipoContato. TipoContato não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.tipoContatoService.remover(tipoContato.get());
		response.setData("TipoContato ID " + id + " deleteado.");
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de atualização do tipoContato
	 * 
	 * @param id
	 * @param TipoContato
	 * @param result
	 * @return ResponseEntity<Response<TipoContato>>
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<TipoContato>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody TipoContato tipoContato,
			BindingResult result) {

		log.info("Atualizando tipoContato: {}", tipoContato);
		Response<TipoContato> response = new Response<TipoContato>();

		Optional<TipoContato> tipoContatoBusca = this.tipoContatoService.buscarPorId(id);

		if (!tipoContatoBusca.isPresent()) {
			log.error("TipoContato não encontrado para o id: {}", id);
			response.getErrors().add("TipoContato não encontrado para o id : " + id);
			return ResponseEntity.badRequest().body(response);
		} else if (!tipoContato.getId().equals(id)) {
			log.error("Identificador do TipoContato diferente do informado: {}", id);
			response.getErrors().add("Identificador do TipoContato diferente do informado!");
			return ResponseEntity.badRequest().body(response);
		}

		this.tipoContatoService.persistir(tipoContato);
		response.setData(tipoContatoBusca.get());
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca por nome
	 * 
	 * @param nome
	 * @return ResponseEntity<Response<List<TipoContato>>>
	 */
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<List<TipoContato>>> buscarPorCpf(@PathVariable("nome") String nome) {

		log.info("Buscando tipoContatos pelo nome: {}", nome);
		Response<List<TipoContato>> response = new Response<List<TipoContato>>();

		List<TipoContato> tipoContatos = tipoContatoService.buscarPorNome(nome);

		if (tipoContatos.isEmpty()) {
			log.info("Nenhum tipoContato encontrado para o nome: {}", nome);
			response.getErrors().add("Nenhum tipoContato encontrado para o nome: " + nome);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(tipoContatos);
		return ResponseEntity.ok(response);
	}

	/**
	 * Validações especificas
	 * 
	 * @param TipoContato
	 * @param result
	 */
	public void validarDadosExistentes(TipoContato tipoContato, BindingResult result) {
		if (tipoContato.getId() != null) {
			result.addError(new ObjectError("Id", "TipoContato possui id, utilizar método PUT para atualizar dados."));
		}
	}

}
