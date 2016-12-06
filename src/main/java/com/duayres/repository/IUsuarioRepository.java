package com.duayres.repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Usuario;

@Repository
@Transactional
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	public List<Usuario> findByNomeIgnoreCaseContainingAndEmailIgnoreCaseContaining(String nome, String email);
	
	public Optional<Usuario> findByEmailIgnoreCaseAndStatusTrue(String email);

	public Optional<Usuario> findByEmailIgnoreCaseAndSenha(String email, String senha);
	
//	public Usuario findByIdUsuario(Long idUsuario);
	
	public Optional<Usuario> findByIdUsuario(Long idUsuario);
	
	@Query("FROM Usuario u "
			+ "WHERE NOT EXISTS ( "
			+ "FROM Membro m "
			+ "WHERE ( m.agendamento.dataInicial between :inicial AND :final "
			+ "OR m.agendamento.dataFinal between :inicial AND :final "
			+ "OR  m.agendamento.dataInicial <= :inicial AND m.agendamento.dataFinal >= :final )  "
			+ "AND m.usuario.id = u.id )")
	public List<Usuario> listUsuariosLivresNoPeriodo(@Param("inicial") Calendar dtInicio, @Param("final") Calendar dtFinal);
}
