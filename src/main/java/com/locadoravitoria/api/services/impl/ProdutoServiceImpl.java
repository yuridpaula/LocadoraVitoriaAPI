package com.locadoravitoria.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locadoravitoria.api.entities.Produto;
import com.locadoravitoria.api.entities.ProdutoFilho;
import com.locadoravitoria.api.entities.Sub;
import com.locadoravitoria.api.repositories.ProdutoRepository;
import com.locadoravitoria.api.services.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	private static final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Optional<Produto> buscarPorId(Long id) {
		log.info("Buscando produto pelo ID {}", id);
		return produtoRepository.findById(id);
	}

	@Override
	public Produto persistir(Produto produto) {
		log.info("Persistindo produto: {}", produto);
		
		if (produto.getSubs().isEmpty()) {
			Sub sub = new Sub();
			sub.setProdutoFilho(new ProdutoFilho(produto.getId()));
			sub.setQuantidade((long) 1);
			
			produto.getSubs().add(sub);
		}
		
		return this.produtoRepository.save(produto);
	}

	@Override
	public void remover(Produto produto) {
		log.info("Removendo produto: {}", produto);
		this.produtoRepository.delete(produto);
	}

	@Override
	public List<Produto> listarTodos() {
		log.info("Buscando todos produtos");
		return this.produtoRepository.findAllByOrderByNome();
	}

	@Override
	public List<Produto> buscarPorNome(String nome) {
		log.info("Buscando todos produtos pelo nome {}", nome);
		return this.produtoRepository.findByNomeIgnoreCaseContaining(nome);
	}

}
