package com.duayres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duayres.model.Agendamento;

public interface IAgendamentoRepository extends JpaRepository<Agendamento, Long> {
	
	List<Agendamento> findAll();
	
	Optional<Agendamento> findByIdAgendamento(Long id);
	


}
