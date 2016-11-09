package com.duayres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duayres.model.Agendamento;
import com.duayres.repository.IAgendamentoRepository;

@Service
public class AgendamentoService {
	@Autowired
	IAgendamentoRepository agendamentoRepository;
	
	public Agendamento save(Agendamento agendamento){
		return agendamentoRepository.save(agendamento);
	}
	
}
