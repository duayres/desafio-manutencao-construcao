package com.duayres.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.TipoUsuario;
import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;

@RemoteProxy
public class DWRUsuarioService {
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario save(Usuario usuario){
		return this.usuarioRepository.saveAndFlush(usuario);
	}
	
	//@RemoteMethod
	public Usuario findUserById( Long id )
	{
		return this.usuarioRepository.findOne( id );
	}

	public List<Usuario> listAll(){
		return usuarioRepository.findAll();
	}
	
	public TipoUsuario[] listTiposUsuario(){
		return TipoUsuario.values();
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
