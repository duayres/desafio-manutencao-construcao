package com.duayres.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.TipoUsuario;
import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;

@Service
public class UsuarioService {
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
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

	public Optional<Usuario> findByEmailIgnoreCaseAndStatusTrue(String username) {
		return usuarioRepository.findByEmailIgnoreCaseAndStatusTrue(username);
	}
	
	
	public Usuario login(Usuario usuario) {
		System.out.println(encoder.encode(usuario.getSenha()));
		Optional<Usuario> userOpt = usuarioRepository.findByEmailIgnoreCaseAndStatusTrue(usuario.getEmail());
		
		Usuario user = userOpt.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha não encontrados"));
		
		if (!encoder.matches(usuario.getSenha(), user.getSenha())){
			throw new UsernameNotFoundException("Usuario e/ou senha não encontrados");
		}
		
		return user;
	}
}
