package com.duayres.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.duayres.model.Usuario;
import com.duayres.service.UsuarioService;


public class AppUserDetailsService implements UserDetailsService {
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOpt = usuarioService.findByEmailIgnoreCaseAndStatusTrue(username);
		Usuario user = usuarioOpt.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha não encontrado"));
		System.out.println("usuario:"+username);
		return new User(user.getEmail(), user.getSenha(), true, true, true, true, getUserTipo(user));	
	}

	private Collection<? extends GrantedAuthority> getUserTipo(Usuario user) {
		List<GrantedAuthority> permissoes = new ArrayList<GrantedAuthority>();
		permissoes.add(new SimpleGrantedAuthority("ROLE_"+user.getTipoUsuario()));
		return permissoes;
	}
	
}
