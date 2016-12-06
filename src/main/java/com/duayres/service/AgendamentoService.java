package com.duayres.service;

import java.util.List;

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

	public void exclude(Long idAgendamento) {
		this.agendamentoRepository.delete(idAgendamento);
	}

}
