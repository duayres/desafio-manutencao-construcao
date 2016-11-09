package com.duayres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duayres.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	public List<Usuario> findByNomeUsuarioIgnoreCaseContainingAndEmailIgnoreCaseContaining(String nomeUsuario, String email);
	
	public Optional<Usuario> findByEmailIgnoreCase(String email);
	
	public Optional<Usuario> findByEmailIgnoreCaseAndStatusTrue(String email);

	public Optional<Usuario> findByEmailIgnoreCaseAndSenha(String email, String senha);
	
	public Usuario findByIdUsuario(Long idUsuario);
}
