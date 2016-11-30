package com.duayres.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duayres.model.TipoUsuario;
import com.duayres.model.Usuario;
import com.duayres.service.UsuarioService;

@RestController
@RequestMapping(value="/api/usuario")
public class UsuarioRestController {
	@Autowired
	UsuarioService usuarioService;
	
	/*
	 * Retorna os tipos de usuários disponíveis
	 */
	@RequestMapping(value="/list-tipos", method=RequestMethod.GET)
	public TipoUsuario[] getTiposDeUsuario(){
		return TipoUsuario.values();
	}
	
	/*
	 * Retorna todos os usuarios cadastrados
	 */
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public List<Usuario> listAll(){
		return usuarioService.listAll();
	}
	
	
	@RequestMapping(value="/alter-status", method=RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> alterStatus(@RequestParam("id") Long idUsuario, @RequestParam("status") Boolean status){
		Usuario user = usuarioService.findUserById(idUsuario);
		user.setStatus(status);
		usuarioService.save(user);
		return new ResponseEntity<String>("{code: 200}",HttpStatus.OK);
	}
	
}
