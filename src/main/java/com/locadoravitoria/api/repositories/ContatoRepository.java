package com.locadoravitoria.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadoravitoria.api.entities.Cliente;
import com.locadoravitoria.api.entities.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

	List<Contato> findByCliente(Cliente cliente);

}
