package com.duayres.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.TipoDeEquipamento;
import com.duayres.repository.ITipoDeEquipamentoRepository;

@RemoteProxy
public class DWRTipoDeEquipamentoService {
	@Autowired
	private ITipoDeEquipamentoRepository tipoDeEquipamentoRepository;
	
	@Transactional
	public TipoDeEquipamento save(TipoDeEquipamento tipoDeEquipamento){
		return this.tipoDeEquipamentoRepository.saveAndFlush(tipoDeEquipamento);
	}
	
	public List<TipoDeEquipamento> listAll(){
		return this.tipoDeEquipamentoRepository.findAll();
	}

	public List<TipoDeEquipamento> listActives(){
		return this.tipoDeEquipamentoRepository.findByStatusTrue();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public void logicExclusion(Long idTipoDeEquipamento) throws Exception {
		TipoDeEquipamento tipoDeEquipamento=this.tipoDeEquipamentoRepository.findOne(idTipoDeEquipamento);
		if (tipoDeEquipamento==null){
			throw new Exception("Tipo de equipamento não encontrado");
		}
		tipoDeEquipamento.setStatus(false);
	}
}