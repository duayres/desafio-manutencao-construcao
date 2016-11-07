package com.duayres.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.duayres.service.UsuarioService;


/**
 * Classe que procura e mapeia as classes de serviço
 * @author Eduardo Ayres
 */
@Configuration
@ComponentScan(basePackageClasses = UsuarioService.class)
public class ServiceConfig {
	
}
