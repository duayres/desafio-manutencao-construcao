package com.duayres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.duayres.model.Usuario;
import com.duayres.service.UsuarioService;

@Controller
public class MainController {

	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping("/teste")
	public String teste(){
		System.out.println("testão! xD");
		
		Usuario u = new Usuario();
		u.setTipo(com.duayres.model.TipoUsuario.ADMINISTRADOR);
		u.setEmail("cebola@cebola.com");
		u.setNomeUsuario("Eduardo Cebola");
		u.setStatus(true);
		u.setSenha("ahue1234");
		u.setConfSenha("ahue1234");
		this.usuarioService.inserir(u);
		
		return "xD";
	}
}
