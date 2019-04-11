package com.locadoravitoria.api.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.locadoravitoria.api.services.GenericService;
import com.locadoravitoria.api.utils.Response;

public abstract class GenericController<T, ID extends Serializable> {

	@Autowired
	private GenericService<T, ID> service;

	private static final Logger log = LoggerFactory.getLogger(GenericController.class);

	public GenericController() {

	}
	
	@GetMapping
	public ResponseEntity<Response<List<T>>> listarTodos() {
		Response<List<T>> response = new Response<List<T>>();

		List<T> entities = this.service.listarTodos();

		if (entities.isEmpty()) {
			log.info("Nenhuma entidade encontrada.");
			response.getErrors().add("Nenhuam entidade encontrada.");
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(entities);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<T>> buscarPorId(@PathVariable("id") ID id) {

		log.info("Buscando entidade pelo ID: {}", id);
		Response<T> response = new Response<T>();

		Optional<T> entity = this.service.buscarPorId(id);

		if (!entity.isPresent()) {
			log.info("Entidade não encontrado para o ID: {}", id);
			response.getErrors().add("Entidade não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(entity.get());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<Response<T>> cadastrar(@Valid @RequestBody T entity, BindingResult result) {

		log.info("Cadastrando entidade: {}", entity);
		Response<T> response = new Response<T>();

		this.service.persistir(entity);

		response.setData(entity);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") ID id) {
		log.info("Deletando entidade para o ID: {}", id);

		Response<String> response = new Response<String>();
		Optional<T> entity = this.service.buscarPorId(id);

		if (!entity.isPresent()) {
			log.info("Erro ao remover entidade. Não encontrada para o ID: {}", id);
			response.getErrors().add("Erro ao remover entidade. Não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.service.remover(entity.get());
		response.setData("Entidade ID " + id + " deleteado.");
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<T>> atualizar(@PathVariable("id") ID id, @Valid @RequestBody T entity,
			BindingResult result) {

		log.info("Atualizando entidade: {}", entity);
		Response<T> response = new Response<T>();

		Optional<T> entityBusca = this.service.buscarPorId(id);

		if (!entityBusca.isPresent()) {
			log.error("Grupo não encontrado para o id: {}", id);
			response.getErrors().add("Grupo não encontrado para o id : " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.service.persistir(entity);
		response.setData(entityBusca.get());
		return ResponseEntity.ok(response);
	}
}
