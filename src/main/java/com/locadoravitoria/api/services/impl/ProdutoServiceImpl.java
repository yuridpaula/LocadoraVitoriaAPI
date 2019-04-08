package com.locadoravitoria.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locadoravitoria.api.entities.Grupo;
import com.locadoravitoria.api.entities.Produto;
import com.locadoravitoria.api.repositories.GrupoRepository;
import com.locadoravitoria.api.repositories.ProdutoRepository;
import com.locadoravitoria.api.services.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private static final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;

	@Override
	public Optional<Produto> buscarPorId(Long id) {
		log.info("Buscando produto pelo ID {}", id);
		return produtoRepository.findById(id);
	}

	@Override
	public Produto persistir(Produto produto) {
		log.info("Persistindo produto: {}", produto);
		return this.produtoRepository.save(produto);
	}

	@Override
	public void removerProduto(Produto produto) {
		log.info("Removendo produto: {}", produto);
		this.produtoRepository.delete(produto);
	}

	@Override
	public List<Produto> listarTodos() {
		log.info("Buscando todos produtos");
		return this.produtoRepository.findAll();
	}

	@Override
	public List<Produto> buscarPorGrupo(Long grupoId) {
		log.info("Buscando todos produtos do grupo {}", grupoId);
		
		Optional<Grupo> grupo = this.grupoRepository.findById(grupoId);
		List<Produto> list = new ArrayList<>();
		
		if (grupo.isPresent()) {
			list = this.produtoRepository.findByGrupo(grupo.get());
		}
		
		return list;
	}

}
