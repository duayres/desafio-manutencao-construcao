package com.duayres.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Agendamento;
import com.duayres.model.Localizacao;
import com.duayres.model.Membro;
import com.duayres.model.Usuario;
import com.duayres.repository.IAgendamentoRepository;
import com.duayres.repository.IMembroRepository;

@RemoteProxy
public class DWRAgendamentoService {
	@Autowired
	IAgendamentoRepository agendamentoRepository;
	
	@Autowired
	IMembroRepository membroRepository;
	
	
	
	@Transactional
	public Agendamento save(Agendamento agendamento){
		return this.agendamentoRepository.saveAndFlush(agendamento);
	}

	@Transactional
	public Agendamento save(Agendamento agendamento, Localizacao localizacao, List<Usuario> usuarios){
		if (localizacao.getIdLocalizacao()==null){
			new com.duayres.service.DWRLocalizacaoService().save(localizacao);
		}
		agendamento = this.agendamentoRepository.saveAndFlush(agendamento);
		for (Usuario usuario : usuarios){
			Membro membro = new Membro();
			membro.setUsuario(usuario);
			membro.setAgendamento(agendamento);
			//agendamento.getMembros().add(membro);
			membroRepository.saveAndFlush(membro);
		}
		return agendamento;
	}
	
	public Agendamento findByIdAgendamento(Long id){
		Agendamento agendamento = this.agendamentoRepository.findByIdAgendamento(id).get();
		return agendamento;
		//return this.agendamentoRepository.getOne(id);
	}
	
	public List<Agendamento> listAll(){
		return this.agendamentoRepository.findAll();
	}
	
	public void exclude(Long idAgendamento) {
		this.agendamentoRepository.delete(idAgendamento);
	}
}
