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

import com.locadoravitoria.api.entities.TipoContato;
import com.locadoravitoria.api.services.TipoContatoService;
import com.locadoravitoria.api.utils.Response;

@RestController
@RequestMapping("/api/tipocontato")
@CrossOrigin(origins = "*")
public class TipoContatoController extends GenericController<TipoContato, Long> {
	private static final Logger log = LoggerFactory.getLogger(TipoContatoController.class);

	@Autowired
	private TipoContatoService tipoContatoService;

	public TipoContatoController() {

	}

	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<List<TipoContato>>> buscarPorNome(@PathVariable("nome") String nome) {

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

}
