package com.locadoravitoria.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.locadoravitoria.api.entities.Usuario;

@Transactional(readOnly = true)
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
		
	List<Usuario> findByEmailIgnoreCaseContaining(String email);
	
	List<Usuario> findAllByOrderByNome();

}
