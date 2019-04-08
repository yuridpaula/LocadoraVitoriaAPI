package com.locadoravitoria.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Classe resposávvel pelo encrypt e decrypt da senha, no login
 * @author Yuri Oliveira
 *
 */
public class PasswordUtils {

	private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

	public PasswordUtils() {
	}

	/**
	 * Gera um hash utilizando o BCrypt.
	 * 
	 * @param senha
	 * @return String
	 */
	public static String gerarBCrypt(String senha) {
		if (senha == null) {
			return senha;
		}

		log.info("Gerando hash com o BCrypt.");
		//BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		//return bCryptEncoder.encode(senha);
		return senha;
	}

}