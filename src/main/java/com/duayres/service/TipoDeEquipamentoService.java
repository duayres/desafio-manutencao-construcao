package com.duayres.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.TipoDeEquipamento;
import com.duayres.repository.ITipoDeEquipamentoRepository;

@Service
public class TipoDeEquipamentoService {
	@Autowired
	private ITipoDeEquipamentoRepository tipoDeEquipamentoRepository;
	
	@Transactional
	public TipoDeEquipamento save(TipoDeEquipamento tipoDeEquipamento){
		return this.tipoDeEquipamentoRepository.saveAndFlush(tipoDeEquipamento);
	}
	
	public List<TipoDeEquipamento> listAll(){
		return this.tipoDeEquipamentoRepository.findAll();
	}

	public TipoDeEquipamento findTipoDeEquipamentoById(Long idTipoDeEquipamento) {
		return this.tipoDeEquipamentoRepository.findOne(idTipoDeEquipamento);
	}

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public void logicExclusion(Long idTipoDeEquipamento) {
		TipoDeEquipamento tipoDeEquipamento=this.tipoDeEquipamentoRepository.findOne(idTipoDeEquipamento);
		if (tipoDeEquipamento==null){
			new Exception("Tipo de equipamento não encontrado");
			return;
		}
		tipoDeEquipamento.setStatus(false);
	}
	
}
