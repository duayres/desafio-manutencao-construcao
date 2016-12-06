package com.duayres.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Agendamento;
import com.duayres.repository.IAgendamentoRepository;

@RemoteProxy
public class DWRAgendamentoService {
	@Autowired
	IAgendamentoRepository agendamentoRepository;
	
	@Transactional
	public Agendamento save(Agendamento agendamento){
		return this.agendamentoRepository.saveAndFlush(agendamento);
	}
	
	public Agendamento findByIdAgendamento(Long id){
		Agendamento agendamento = this.agendamentoRepository.findByIdAgendamento(id).get();
		return agendamento;
		//return this.agendamentoRepository.getOne(id);
	}
	
	public List<Agendamento> listAll(){
		List<Agendamento>	agendamentos = this.agendamentoRepository.findAll();
//		for (Agendamento agendamento : agendamentos) {
//			agendamento.setTipoEquipamento(null);
//		}
		return agendamentos;
	}
	
	public void exclude(Long idAgendamento) {
		this.agendamentoRepository.delete(idAgendamento);
	}
}
