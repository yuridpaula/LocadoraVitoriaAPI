package com.locadoravitoria.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadoravitoria.api.entities.Grupo;
import com.locadoravitoria.api.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	Optional<Produto> findByNome(String nome);
	
	List<Produto> findByGrupo(Grupo grupo);
	
}
