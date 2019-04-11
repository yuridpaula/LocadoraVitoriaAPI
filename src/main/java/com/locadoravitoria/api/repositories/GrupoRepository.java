package com.locadoravitoria.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadoravitoria.api.entities.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
	List<Grupo> findByNomeIgnoreCaseContaining(String nome);
	
	List<Grupo> findAllByOrderByNome();
}
