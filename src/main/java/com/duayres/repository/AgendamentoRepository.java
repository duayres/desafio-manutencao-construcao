package com.duayres.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duayres.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

}
