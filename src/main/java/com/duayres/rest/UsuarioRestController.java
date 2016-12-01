package com.duayres.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	/*
	 * Altera o status (ativo|inativo) do usuario
	 * @param Long idUsuario id do usuario
	 * @param Boolean status O estado de usuario (true=ativo, false=inativo)
	 * @return json de codigo de sucesso (200)
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@RequestMapping(value="/alter-status", method=RequestMethod.POST)
	public String alterStatus(@RequestParam("id") Long idUsuario, @RequestParam("status") Boolean status){
		Usuario usuario = usuarioService.findUserById(idUsuario);//TODO ver os save se tem flush
		usuario.setStatus(status);
		usuarioService.save(usuario);
		return "{code: 200}";
	}
	
	/*
	 * Ativa um usuario
	 * @param Long idUsuario id do usuario para ser ativado
	 * @return json c/ codigo de sucesso (200)
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@RequestMapping(value="/activate/{id}", method=RequestMethod.GET)
	public String activateUser(@PathVariable("id") Long idUsuario){
		Usuario usuario = usuarioService.findUserById(idUsuario);
		usuario.setStatus(true);
		//usuarioService.save(usuario);
		return "{code: 200}";
	}
	
}
