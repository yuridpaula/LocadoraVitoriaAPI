package com.locadoravitoria.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadoravitoria.api.entities.TipoContato;

public interface TipoContatoRepository extends JpaRepository<TipoContato, Long> {
}
