package com.duayres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duayres.model.Agendamento;
import com.duayres.repository.AgendamentoRepository;

@Service
public class AgendamentoService {
	@Autowired
	AgendamentoRepository agendamentoRepository;
	
	public Agendamento inserir(Agendamento agendamento){
		return agendamentoRepository.save(agendamento);
	}
	
}
