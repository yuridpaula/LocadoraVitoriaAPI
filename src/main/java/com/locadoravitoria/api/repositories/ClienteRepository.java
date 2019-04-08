package com.locadoravitoria.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadoravitoria.api.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByCpfContaining(String cpf);

	List<Cliente> findByContatosValorIgnoreCaseContaining(String valor);

	List<Cliente> findAllByOrderByNome();

}
