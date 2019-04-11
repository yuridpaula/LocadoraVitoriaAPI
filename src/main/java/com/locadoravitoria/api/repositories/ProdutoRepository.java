package com.locadoravitoria.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadoravitoria.api.entities.Grupo;
import com.locadoravitoria.api.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	List<Produto> findByNomeIgnoreCaseContaining(String nome);

	List<Produto> findAllByOrderByNome();
	
	List<Produto> findAllByOrderById();
	
	List<Produto> findByGrupo(Grupo grupo);
}
