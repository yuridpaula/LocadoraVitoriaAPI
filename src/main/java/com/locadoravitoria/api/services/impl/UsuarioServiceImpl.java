package com.locadoravitoria.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locadoravitoria.api.entities.Usuario;
import com.locadoravitoria.api.repositories.UsuarioRepository;
import com.locadoravitoria.api.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository; 
	
	@Override
	public Optional<Usuario> buscarPorId(Long id) {
		log.info("Buscando usuario pelo ID {}", id);
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario persistir(Usuario usuario) {
		log.info("Persistindo usuario: {}", usuario);
		return this.usuarioRepository.save(usuario);
	}

	@Override
	public List<Usuario> buscarPorEmail(String email) {
		log.info("Buscando usuario pelo Email {}", email);
		return usuarioRepository.findByEmailIgnoreCaseContaining(email);
	}

	@Override
	public void remover(Usuario usuario) {
		log.info("Removendo usuario: {}", usuario);
		this.usuarioRepository.delete(usuario);
	}

	@Override
	public List<Usuario> listarTodos() {
		log.info("Buscando Todos usuarios");
		return this.usuarioRepository.findAllByOrderByNome();
	}

	@Override
	public List<Usuario> buscarPorNome(String nome) {
		log.info("Buscando Todos usuarios pelo nome {}", nome);
		return this.usuarioRepository.findAllByOrderByNome();
	}	
}
