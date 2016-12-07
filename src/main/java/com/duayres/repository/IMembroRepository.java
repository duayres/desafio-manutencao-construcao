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
	
	public List<Usuario> findByAgendamento(Agendamento agendamento);
	
	@Modifying
	@Query("DELETE FROM Membro m WHERE m.agendamento = :agendamento ")
	public void deleteWithAgendamento(@Param("agendamento") Agendamento agendamento);

}
