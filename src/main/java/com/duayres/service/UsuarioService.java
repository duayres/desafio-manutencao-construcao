package com.duayres.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario save(Usuario usuario){
		return this.usuarioRepository.saveAndFlush(usuario);
	}
	
	public Usuario findUserById( Long id )
	{
		return this.usuarioRepository.findOne( id );
	}
	
	public List<Usuario> listAll(){
		return usuarioRepository.findAll();
	}
	

	public List<Usuario> find(Usuario usuario) {
		if(usuario.getNome() == null){
			usuario.setNomeUsuario("");
		}
		
		if(usuario.getEmail() == null){
			usuario.setEmail("");
		}
		
		return usuarioRepository.findByNomeIgnoreCaseContainingAndEmailIgnoreCaseContaining(usuario.getNome(), usuario.getEmail());
	}
	
}
