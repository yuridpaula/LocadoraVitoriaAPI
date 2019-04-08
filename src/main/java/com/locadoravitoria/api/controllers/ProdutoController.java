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

import com.locadoravitoria.api.entities.Produto;
import com.locadoravitoria.api.response.Response;
import com.locadoravitoria.api.services.ProdutoService;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin(origins = "*")
public class ProdutoController {
	private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

	@Autowired
	private ProdutoService produtoService;
	
	public ProdutoController() {

	}

	/**
	 * Cadastra um Produto no sistema
	 * 
	 * @param Produto
	 * @param result
	 * @return ResponseEntity<Response<Produto>>
	 */
	@PostMapping
	public ResponseEntity<Response<Produto>> cadastrar(@Valid @RequestBody Produto produto, BindingResult result) {

		log.info("Cadastrando produto: {}", produto);
		Response<Produto> response = new Response<Produto>();

		validarDadosExistentes(produto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro do produto: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.produtoService.persistir(produto);

		response.setData(produto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna uma lista de todos Produtos
	 * 
	 * @return ResponseEntity<Response<List<ProdutoDto>>>
	 */
	@GetMapping
	public ResponseEntity<Response<List<Produto>>> listarTodos() {
		Response<List<Produto>> response = new Response<List<Produto>>();

		List<Produto> produtos = produtoService.listarTodos();

		if (produtos.isEmpty()) {
			log.info("Nenhum produto encontrado.");
			response.getErrors().add("Nenhum produto encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		produtos.stream().sorted((p1, p2) -> p1.getNome().compareTo(p2.getNome()));

		response.setData(produtos);
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca do Produto por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Produto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Produto>> buscarPorId(@PathVariable("id") Long id) {

		log.info("Buscando produto pelo ID: {}", id);
		Response<Produto> response = new Response<Produto>();

		Optional<Produto> produto = produtoService.buscarPorId(id);

		if (!produto.isPresent()) {
			log.info("Produto não encontrado para o ID: {}", id);
			response.getErrors().add("Produto não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(produto.get());
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Método de busca do Grupo Id
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Produto>>
	 */
	@GetMapping(value = "/grupo/{id}")
	public ResponseEntity<Response<List<Produto>>> buscarPorGrupoId(@PathVariable("id") Long id) {

		log.info("Buscando produtos pelo ID grupo: {}", id);
		Response<List<Produto>> response = new Response<List<Produto>>();

		List<Produto> produtos = produtoService.buscarPorGrupo(id);

		if (produtos.isEmpty()) {
			log.info("Produtos não encontrados para o Gruopo ID: {}", id);
			response.getErrors().add("Produtos não encontrados para o Gruopo ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(produtos);
		return ResponseEntity.ok(response);
	}

	/**
	 * Validações especificas
	 * 
	 * @param ProdutoDto
	 * @param result
	 */
	public void validarDadosExistentes(Produto produto, BindingResult result) {
		// sem validações especificas
	}

}
