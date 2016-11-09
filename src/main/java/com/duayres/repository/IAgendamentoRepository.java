package com.duayres.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duayres.model.Agendamento;

public interface IAgendamentoRepository extends JpaRepository<Agendamento, Integer> {

}
