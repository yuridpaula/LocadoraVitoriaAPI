package com.locadoravitoria.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

		usuarios.forEach(usuario -> listDto.add(this.converterUsuarioParaDto(usuario)));

		response.setData(listDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDto>> buscarPorId(@PathVariable("id") Long id) {

		log.info("Buscando usuario pelo ID: {}", id);
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

	@PostMapping
	public ResponseEntity<Response<UsuarioDto>> cadastrar(@Valid @RequestBody UsuarioDto dto, BindingResult result)
			throws NoSuchAlgorithmException {

		log.info("Cadastrando usuário: {}", dto);
		Response<UsuarioDto> response = new Response<UsuarioDto>();

		validarDadosExistentes(dto, result);

		Usuario usuario = this.converterDtoParaUsuario(dto);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro de usuario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.persistir(usuario);

		response.setData(this.converterUsuarioParaDto(usuario));
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Deletando usuario para o ID: {}", id);

		Response<String> response = new Response<String>();
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(id);

		if (!usuario.isPresent()) {
			log.info("Erro ao remover usuario. Usuario não encontrado para o ID: {}", id);
			response.getErrors().add("Erro ao remover usuario. Usuario não encontrado para o ID: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.remover(usuario.get());
		response.setData("Usuario ID " + id + " deleteado.");
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDto>> atualizar(@PathVariable("id") Long id, UsuarioDto dto,
			BindingResult result) throws NoSuchAlgorithmException {

		log.info("Atualizando UsuarioDto: {}", dto);
		Response<UsuarioDto> response = new Response<UsuarioDto>();

		Optional<Usuario> usuarioBusca = this.usuarioService.buscarPorId(dto.getId());

		if (!usuarioBusca.isPresent()) {
			log.error("Usuario não encontrado para o id: {}", id);
			response.getErrors().add("Usuario não encontrado para o id : " + id);
			return ResponseEntity.badRequest().body(response);
		} else if (!dto.getId().equals(id)) {
			log.error("Identificador do Usuario diferente do informado: {}", id);
			response.getErrors().add("Identificador do Usuario diferente do informado!");
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.persistir(this.converterDtoParaUsuario(dto));
		response.setData(this.converterUsuarioParaDto(usuarioBusca.get()));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<List<UsuarioDto>>> buscarPorNome(@PathVariable("nome") String nome) {

		log.info("Buscando usuarios pelo nome: {}", nome);
		Response<List<UsuarioDto>> response = new Response<List<UsuarioDto>>();

		List<Usuario> usuarios = usuarioService.buscarPorNome(nome);

		if (usuarios.isEmpty()) {
			log.info("Nenhum usuario encontrado para o nome: {}", nome);
			response.getErrors().add("Nenhum usuario encontrado para o nome: " + nome);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(
				usuarios.stream().map(usuario -> this.converterUsuarioParaDto(usuario)).collect(Collectors.toList()));
		return ResponseEntity.ok(response);
	}

	private void validarDadosExistentes(UsuarioDto usuarioDto, BindingResult result) {
		if (!this.usuarioService.buscarPorEmail(usuarioDto.getEmail()).isEmpty()) {
			result.addError(new ObjectError("email", "Email já cadastrado"));
		}

		if (usuarioDto.getId() != null) {
			result.addError(new ObjectError("Id", "Usuario possui id, utilizar método PUT para atualizar dados."));
		}
	}

	public Usuario converterDtoParaUsuario(UsuarioDto dto) throws NoSuchAlgorithmException {
		Usuario usuario = new Usuario();
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));

		return usuario;
	}

	public UsuarioDto converterUsuarioParaDto(Usuario usuario) {
		UsuarioDto dto = new UsuarioDto();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail());

		return dto;
	}
}
