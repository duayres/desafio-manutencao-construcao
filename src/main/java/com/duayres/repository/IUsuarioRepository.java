package com.duayres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duayres.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	public List<Usuario> findByNomeIgnoreCaseContainingAndEmailIgnoreCaseContaining(String nome, String email);
	
	public Optional<Usuario> findByEmailIgnoreCaseAndStatusTrue(String email);

	public Optional<Usuario> findByEmailIgnoreCaseAndSenha(String email, String senha);
	
	public Usuario findByIdUsuario(Long idUsuario);
}
