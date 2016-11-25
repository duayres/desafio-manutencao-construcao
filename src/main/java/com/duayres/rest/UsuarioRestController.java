package com.duayres.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.duayres.model.TipoUsuario;
import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;

@RestController
@RequestMapping(value="/api/usuario")
public class UsuarioRestController {
	@Autowired
	IUsuarioRepository usuarioRepository;
	
	@RequestMapping(value="/listTiposUsuario", method=RequestMethod.GET)
	public TipoUsuario[] getTiposDeUsuario(){
		return TipoUsuario.values();
	}
	
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public List<Usuario> listAll(){
		return usuarioRepository.findAll();
	}
}
