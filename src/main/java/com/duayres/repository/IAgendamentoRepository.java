package com.duayres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Agendamento;

@Repository
@Transactional
public interface IAgendamentoRepository extends JpaRepository<Agendamento, Long> {
	
//	List<Agendamento> findAll();
	
	Optional<Agendamento> findByIdAgendamento(Long id);
	


}
