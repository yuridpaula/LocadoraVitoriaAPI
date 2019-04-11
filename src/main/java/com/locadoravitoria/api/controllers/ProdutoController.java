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

import com.locadoravitoria.api.services.ProdutoService;
import com.locadoravitoria.api.utils.Response;
import com.locadoravitoria.api.entities.Produto;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin(origins = "*")
public class ProdutoController extends GenericController<Produto, Long> {
	private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

	@Autowired
	private ProdutoService produtoService;

	public ProdutoController() {

	}

	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<List<Produto>>> buscarPorNome(@PathVariable("nome") String nome) {

		log.info("Buscando produtos pelo nome: {}", nome);
		Response<List<Produto>> response = new Response<List<Produto>>();

		List<Produto> produtos = produtoService.buscarPorNome(nome);

		if (produtos.isEmpty()) {
			log.info("Nenhum produto encontrado para o nome: {}", nome);
			response.getErrors().add("Nenhum produto encontrado para o nome: " + nome);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(produtos);
		return ResponseEntity.ok(response);
	}

}
