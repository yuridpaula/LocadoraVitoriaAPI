package com.locadoravitoria.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locadoravitoria.api.entities.TipoContato;
import com.locadoravitoria.api.repositories.TipoContatoRepository;
import com.locadoravitoria.api.services.TipoContatoService;

@Service
public class TipoContatoServiceImpl implements TipoContatoService {
	
	private static final Logger log = LoggerFactory.getLogger(TipoContatoServiceImpl.class);
	
	@Autowired
	private TipoContatoRepository tipoContatoRepository;
	
	@Override
	public Optional<TipoContato> buscarPorId(Long id) {
		log.info("Buscando tipoContato pelo ID {}", id);
		return tipoContatoRepository.findById(id);
	}

	@Override
	public TipoContato persistir(TipoContato tipoContato) {
		log.info("Persistindo tipoContato: {}", tipoContato);
		return tipoContatoRepository.save(tipoContato);
	
	}

	@Override
	public void remover(TipoContato tipoContato) {
		log.info("Removendo tipoContato: {}", tipoContato);
		this.tipoContatoRepository.delete(tipoContato);
	}

	@Override
	public List<TipoContato> listarTodos() {
		log.info("Buscando todos tipoContatos");
		return this.tipoContatoRepository.findAllByOrderByNome();
	}

	@Override
	public List<TipoContato> buscarPorNome(String nome) {
		log.info("Buscando todos tipoContatos pelo nome {}", nome);
		return this.tipoContatoRepository.findByNomeIgnoreCaseContaining(nome);
	}

}
