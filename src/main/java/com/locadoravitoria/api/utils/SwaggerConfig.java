package com.locadoravitoria.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
//@Profile("prod")
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.locadoravitoria.api.controllers")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}

	
private ApiInfo apiInfo() {
    	
    	ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
    	apiInfoBuilder.title("Locadora Vitoria API");
    	apiInfoBuilder.description("Documentação da API de acesso aos endpoints do projeto Locadora Vitoria.");
    	apiInfoBuilder.version("1.0");
    	apiInfoBuilder.contact(new Contact("Yuri de Paula", "https://github.com/yuridpaula", "yuriodp@gmail.com"));
    	
    	return apiInfoBuilder.build();
    }

	/**
	 * Faz a autenticação direta pelo JWT fixa com o email informado, apenas para
	 * testes da API.
	 * 
	 * @return SecurityConfiguration
	 
	@Bean
	public SecurityConfiguration security() {
		String token;
		try {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername("yuriodp@gmail.com");
			token = this.jwtTokenUtil.obterToken(userDetails);
		} catch (Exception e) {
			token = "";
		}

		return new SecurityConfiguration(null, null, null, null, "Bearer " + token, ApiKeyVehicle.HEADER,
				"Authorization", ",");
	}
	*/

}
