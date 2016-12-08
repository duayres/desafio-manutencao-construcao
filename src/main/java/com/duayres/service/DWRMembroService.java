package com.duayres.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Agendamento;
import com.duayres.model.Membro;
import com.duayres.model.Usuario;
import com.duayres.repository.IMembroRepository;

@RemoteProxy
public class DWRMembroService {
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
	
	//TODO responsabilidade incorreta.. mas na pressa né... :/
	public List<Usuario> findByAgendamentoId(Long idAgendamento){
		return this.membroRepository.findByAgendamentoId(idAgendamento);
	}
	
	public List<Membro> findByAgendamento(Agendamento agendamento){
		return this.membroRepository.findByAgendamento(agendamento);
	}

}
