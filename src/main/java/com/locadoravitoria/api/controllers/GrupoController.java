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

}
