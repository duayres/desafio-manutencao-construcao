package com.duayres.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class UsuarioController {
	/**
	 * Método que retorna os tipos de usuarios
	 * @return Valores por extenso do Enum TipoUsuario
	 */
	/*@RequestMapping(value="/tipoUsuario", method=RequestMethod.GET)
	public @ResponseBody TipoUsuario[] getTiposDeUsuario(){
		return TipoUsuario.values();
	}*/
	
	@RequestMapping("/isSuperUser")
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public String isSuperUser(HttpServletRequest request){
		return "{response: yes}";//caso contrário 403
	}
	
}
