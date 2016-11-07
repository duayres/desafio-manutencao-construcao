package com.duayres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duayres.model.Usuario;
import com.duayres.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario inserir(Usuario usuario){
		return this.usuarioRepository.save(usuario);
		//return usuario;
	}
}
