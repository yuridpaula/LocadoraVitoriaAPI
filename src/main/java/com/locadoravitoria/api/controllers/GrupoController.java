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
import com.locadoravitoria.api.response.Response;
import com.locadoravitoria.api.services.GrupoService;
import com.locadoravitoria.api.entities.Grupo;

@RestController
@RequestMapping("/api/grupo")
@CrossOrigin(origins = "*")
public class GrupoController {
	private static final Logger log = LoggerFactory.getLogger(GrupoController.class);

	@Autowired
	private GrupoService grupoService;

	public GrupoController() {

	}

	/**
	 * Cadastra um Grupo no sistema
	 * 
	 * @param Grupo
	 * @param result
	 * @return ResponseEntity<Response<GrupoDto>>
	 */
	@PostMapping
	public ResponseEntity<Response<Grupo>> cadastrar(@Valid @RequestBody Grupo grupo, BindingResult result) {

		log.info("Cadastrando grupo: {}", grupo);
		Response<Grupo> response = new Response<Grupo>();

		validarDadosExistentes(grupo, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro do grupo: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.grupoService.persistir(grupo);

		response.setData(grupo);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna uma lista de todos Grupos
	 * 
	 * @return ResponseEntity<Response<List<GrupoDto>>>
	 */
	@GetMapping
	public ResponseEntity<Response<List<Grupo>>> listarTodos() {
		Response<List<Grupo>> response = new Response<List<Grupo>>();

		List<Grupo> grupos = grupoService.listarTodos();

		if (grupos.isEmpty()) {
			log.info("Nenhum grupo encontrado.");
			response.getErrors().add("Nenhum grupo encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		grupos.stream().sorted((p1, p2) -> p1.getNome().compareTo(p2.getNome()));

		response.setData(grupos);
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca do Grupo por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Grupo>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Grupo>> buscarPorId(@PathVariable("id") Long id) {

		log.info("Buscando grupo pelo ID: {}", id);
		Response<Grupo> response = new Response<Grupo>();

		Optional<Grupo> grupo = grupoService.buscarPorId(id);

		if (!grupo.isPresent()) {
			log.info("Grupo não encontrado para o ID: {}", id);
			response.getErrors().add("Grupo não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(grupo.get());
		return ResponseEntity.ok(response);
	}

	/**
	 * Validações especificas
	 * 
	 * @param GrupoDto
	 * @param result
	 */
	public void validarDadosExistentes(Grupo grupo, BindingResult result) {
		// sem validações especificas
	}

}
