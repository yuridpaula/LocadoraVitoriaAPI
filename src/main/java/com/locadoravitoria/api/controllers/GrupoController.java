package com.locadoravitoria.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locadoravitoria.api.services.GrupoService;
import com.locadoravitoria.api.utils.Response;
import com.locadoravitoria.api.entities.Grupo;

@RestController
@RequestMapping("/api/grupo")
@CrossOrigin(origins = "*")
public class GrupoController extends GenericController<Grupo, Long> {
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
	 * @return ResponseEntity<Response<Grupo>>
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
	 * Método de busca por nome
	 * 
	 * @param nome
	 * @return ResponseEntity<Response<List<Grupo>>>
	 */
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<List<Grupo>>> buscarPorNome(@PathVariable("nome") String nome) {

		log.info("Buscando grupos pelo nome: {}", nome);
		Response<List<Grupo>> response = new Response<List<Grupo>>();

		List<Grupo> grupos = grupoService.buscarPorNome(nome);

		if (grupos.isEmpty()) {
			log.info("Nenhum grupo encontrado para o nome: {}", nome);
			response.getErrors().add("Nenhum grupo encontrado para o nome: " + nome);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(grupos);
		return ResponseEntity.ok(response);
	}

	/**
	 * Validações especificas
	 * 
	 * @param Grupo
	 * @param result
	 */
	public void validarDadosExistentes(Grupo grupo, BindingResult result) {
		if (grupo.getId() != null) {
			result.addError(new ObjectError("Id", "Grupo possui id, utilizar método PUT para atualizar dados."));
		}
	}

}
