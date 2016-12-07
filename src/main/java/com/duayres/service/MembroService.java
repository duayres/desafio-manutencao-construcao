package com.duayres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Agendamento;
import com.duayres.model.Membro;
import com.duayres.repository.IMembroRepository;

@Service
public class MembroService {
	@Autowired
	IMembroRepository membroRepository;
	
	@Transactional
	public Membro save(Membro membro){
		return this.membroRepository.saveAndFlush(membro);
	}
	
	@Transactional
	public void deleteWithAgendamento(Agendamento agendamento){
		this.membroRepository.deleteWithAgendamento(agendamento);
	}

}
