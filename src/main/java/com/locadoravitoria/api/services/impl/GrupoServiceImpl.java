package com.locadoravitoria.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locadoravitoria.api.entities.Grupo;
import com.locadoravitoria.api.repositories.GrupoRepository;
import com.locadoravitoria.api.services.GrupoService;

@Service
public class GrupoServiceImpl implements GrupoService {

	private static final Logger log = LoggerFactory.getLogger(GrupoServiceImpl.class);

	@Autowired
	private GrupoRepository grupoRepository;

	@Override
	public Optional<Grupo> buscarPorId(Long id) {
		log.info("Buscando grupo pelo ID {}", id);
		return grupoRepository.findById(id);
	}

	@Override
	public Grupo persistir(Grupo grupo) {
		log.info("Persistindo grupo: {}", grupo);
		return this.grupoRepository.save(grupo);
	}

	@Override
	public void remover(Grupo grupo) {
		log.info("Removendo grupo: {}", grupo);
		this.grupoRepository.delete(grupo);
	}

	@Override
	public List<Grupo> listarTodos() {
		log.info("Buscando todos grupos");
		return this.grupoRepository.findAllByOrderByNome();
	}

	@Override
	public List<Grupo> buscarPorNome(String nome) {
		log.info("Buscando todos grupos pelo nome {}", nome);
		return this.grupoRepository.findByNomeIgnoreCaseContaining(nome);
	}

}
