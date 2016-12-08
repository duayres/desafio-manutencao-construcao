package com.duayres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Agendamento;
import com.duayres.model.Membro;
import com.duayres.model.Usuario;

@Repository
@Transactional
public interface IMembroRepository extends JpaRepository<Membro, Long> {
	
	public List<Membro> findByAgendamento(Agendamento agendamento);
	
	@Query("SELECT m.usuario FROM Membro m WHERE m.agendamento.idAgendamento = :id ")
	public List<Usuario> findByAgendamentoId(@Param("id") Long idAgendamento);
	
	
	@Modifying
	@Query("DELETE FROM Membro m WHERE m.agendamento = :agendamento ")
	public void deleteWithAgendamento(@Param("agendamento") Agendamento agendamento);

}
