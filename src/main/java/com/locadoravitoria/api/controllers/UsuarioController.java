package com.locadoravitoria.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locadoravitoria.api.dtos.UsuarioDto;
import com.locadoravitoria.api.entities.Usuario;
import com.locadoravitoria.api.services.UsuarioService;
import com.locadoravitoria.api.utils.PasswordUtils;
import com.locadoravitoria.api.utils.Response;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;

	public UsuarioController() {

	}

	/**
	 * Cadastra um usuario no sistema
	 * 
	 * @param UsuarioDto
	 * @param result
	 * @return ResponseEntity<Response<UsuarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping(value = "/cadastrar")
	public ResponseEntity<Response<UsuarioDto>> cadastrar(@Valid @RequestBody UsuarioDto dto, BindingResult result)
			throws NoSuchAlgorithmException {

		log.info("Cadastrando usuário: {}", dto);
		Response<UsuarioDto> response = new Response<UsuarioDto>();

		validarDadosExistentes(dto, result);

		Usuario usuario = this.converterDtoParaUsuario(dto);

		if (result.hasErrors()) {
			log.error("Ero validando dados de cadastro de usuario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.persistir(usuario);

		response.setData(this.converterUsuarioParaDto(usuario));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma lista de todos Usuarios
	 * 
	 * @return ResponseEntity<Response<List<UsuarioDto>>>
	 */
	@GetMapping
	public ResponseEntity<Response<List<UsuarioDto>>> listarTodos() {
		Response<List<UsuarioDto>> response = new Response<List<UsuarioDto>>();

		List<Usuario> usuarios = usuarioService.listarTodos();

		if (usuarios.isEmpty()) {
			log.info("Nenhum usuario encontrado.");
			response.getErrors().add("Nenhum usuario encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		List<UsuarioDto> listDto = new ArrayList<UsuarioDto>();

		usuarios
			.stream().sorted((p1, p2) -> p1.getNome().compareTo(p2.getNome()))
				.forEach(usuario -> listDto.add(this.converterUsuarioParaDto(usuario)));

		response.setData(listDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Método de busca do Usuario por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<UsuarioDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDto>> buscarPorId(@PathVariable("id") Long id) {

		log.info("Buscando produto pelo ID: {}", id);
		Response<UsuarioDto> response = new Response<UsuarioDto>();

		Optional<Usuario> usuario = usuarioService.buscarPorId(id);

		if (!usuario.isPresent()) {
			log.info("Usuario não encontrado para o ID: {}", id);
			response.getErrors().add("Usuário não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterUsuarioParaDto(usuario.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Validações Especificas
	 * 
	 * @param UsuarioDto
	 * @param result
	 */
	private void validarDadosExistentes(UsuarioDto UsuarioDto, BindingResult result) {
		this.usuarioService.buscarPorEmail(UsuarioDto.getEmail())
				.ifPresent(u -> result.addError(new ObjectError("email", "Email já cadastrado")));
	}

	/**
	 * Converte UsuarioDto para Usuario
	 * 
	 * @param UsuarioDto
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public Usuario converterDtoParaUsuario(UsuarioDto dto) throws NoSuchAlgorithmException {
		Usuario usuario = new Usuario();
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));

		return usuario;
	}

	/**
	 * Converte Usuario para UsuarioDto
	 * 
	 * @param usuario
	 * @return
	 */
	public UsuarioDto converterUsuarioParaDto(Usuario usuario) {
		UsuarioDto dto = new UsuarioDto();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail());

		return dto;
	}
}
