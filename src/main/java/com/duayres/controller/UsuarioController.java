package com.duayres.controller;

import org.springframework.stereotype.Controller;
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
		return TipoUsuario;
	}*/
}
