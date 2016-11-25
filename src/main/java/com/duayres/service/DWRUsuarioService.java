package com.duayres.service;

import java.util.List;
import java.util.Optional;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.duayres.model.TipoUsuario;
import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;

@RemoteProxy
public class DWRUsuarioService {
	
	private ShaPasswordEncoder encoder = new ShaPasswordEncoder();
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario save(Usuario usuario){
		
		return this.usuarioRepository.saveAndFlush(usuario);
	}
	
	public Usuario findUserById( Long id )
	{
		Assert.notNull(id, "id nao informado");
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
			usuario.setNome("");
		}
		
		if(usuario.getEmail() == null){
			usuario.setEmail("");
		}
		
		return usuarioRepository.findByNomeIgnoreCaseContainingAndEmailIgnoreCaseContaining(usuario.getNome(), usuario.getEmail());
	}
	
	public Usuario login(Usuario usuario) {
		usuario.setSenha(encoder.encodePassword(usuario.getSenha(), "palavraaleatoria"));
		Optional<Usuario> userOpt = usuarioRepository.findByEmailIgnoreCaseAndSenha(usuario.getEmail(), usuario.getSenha());
		
		Usuario user = userOpt.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha não encontrados"));
		return user;
	}
	
}
