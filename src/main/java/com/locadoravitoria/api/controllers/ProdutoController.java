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

import com.locadoravitoria.api.services.ProdutoService;
import com.locadoravitoria.api.utils.Response;
import com.locadoravitoria.api.entities.Produto;

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
	 * Retorna uma lista de todos Produtos
	 * 
	 * @return ResponseEntity<Response<List<Produto>>>
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
	 * Remove um produto
	 * 
	 * @param id
	 * @return ResponseEntity<Response<String>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Deletando cliente para o ID: {}", id);

		Response<String> response = new Response<String>();
		Optional<Produto> produto = this.produtoService.buscarPorId(id);

		if (!produto.isPresent()) {
			log.info("Erro ao remover produto. Produto não encontrado para o ID: {}", id);
			response.getErrors().add("Erro ao remover produto. Produto não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.produtoService.remover(produto.get());
		response.setData("Produto ID " + id + " deleteado.");
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de atualização do produto
	 * 
	 * @param id
	 * @param Produto
	 * @param result
	 * @return ResponseEntity<Response<Produto>>
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<Produto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody Produto produto,
			BindingResult result) {

		log.info("Atualizando produto: {}", produto);
		Response<Produto> response = new Response<Produto>();

		Optional<Produto> produtoBusca = this.produtoService.buscarPorId(id);

		if (!produtoBusca.isPresent()) {
			log.error("Produto não encontrado para o id: {}", id);
			response.getErrors().add("Produto não encontrado para o id : " + id);
			return ResponseEntity.badRequest().body(response);
		} else if (!produto.getId().equals(id)) {
			log.error("Identificador do Produto diferente do informado: {}", id);
			response.getErrors().add("Identificador do Produto diferente do informado!");
			return ResponseEntity.badRequest().body(response);
		}

		this.produtoService.persistir(produto);
		response.setData(produtoBusca.get());
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca por nome
	 * 
	 * @param nome
	 * @return ResponseEntity<Response<List<Produto>>>
	 */
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

	/**
	 * Validações especificas
	 * 
	 * @param Produto
	 * @param result
	 */
	public void validarDadosExistentes(Produto produto, BindingResult result) {
		if (produto.getId() != null) {
			result.addError(new ObjectError("Id", "Produto possui id, utilizar método PUT para atualizar dados."));
		}
	}

}
