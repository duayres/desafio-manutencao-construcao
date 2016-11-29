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
import org.springframework.stereotype.Service;

import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;

@Service
public class AuthenticationService implements UserDetailsService
{
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailIgnoreCaseAndStatusTrue(email);
		Usuario user = usuarioOpt.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha não encontrado"));
		System.out.println(user.getEmail());
		return new User(user.getEmail(), user.getSenha(), true, true, true, true, getTipoUsuario(user));
	}
	
	private Collection<? extends GrantedAuthority> getTipoUsuario(Usuario user) {
		List<GrantedAuthority> permissoes = new ArrayList<GrantedAuthority>();
		permissoes.add(new SimpleGrantedAuthority("ROLE_"+user.getTipoUsuario()));
		return permissoes;
	}
	
	
}