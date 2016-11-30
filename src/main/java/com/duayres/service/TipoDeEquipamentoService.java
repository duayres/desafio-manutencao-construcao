package com.duayres.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}
