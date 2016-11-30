package com.duayres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Agendamento;
import com.duayres.repository.IAgendamentoRepository;

@Service
public class AgendamentoService {
	@Autowired
	IAgendamentoRepository agendamentoRepository;
	
	@Transactional
	public Agendamento save(Agendamento agendamento){
		return agendamentoRepository.save(agendamento);
	}
	
	/*
	 * Método que retorna se já existe um agendamento no mesmo intervalo do sendo inserido/editado
	 * @param agendamento
	 * @return Boolean
	 */
	public Boolean verificaDataIntervaloAgendamentoColisao(Agendamento agendamento){
		//this.agendamentoRepository.findAngendamentosOnDatasInterval(agendamento);
		
		return true;
	}
	
	/*
	 * Método que retorna se já existe um agendamento no mesmo intervalo do sendo inserido/editado
	 * @param agendamento
	 * @return Boolean
	 */
	public Boolean listaUsuariosDisponiveisNoIntervalo(Agendamento agendamento){
		//this.agendamentoRepository.findFreeUsuariosOnDateInterval(agendamento);
		
		return true;
	}

}
