package com.locadoravitoria.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadoravitoria.api.entities.TipoContato;

public interface TipoContatoRepository extends JpaRepository<TipoContato, Long> {
	List<TipoContato> findByNomeIgnoreCaseContaining(String nome);
	
	List<TipoContato> findAllByOrderByNome();
}
