package com.duayres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duayres.model.TipoDeEquipamento;
import com.duayres.repository.ITipoDeEquipamentoRepository;

@Service
public class TipoDeEquipamentoService {
	@Autowired
	private ITipoDeEquipamentoRepository tipoDeEquipamentoRepository;
	
	public TipoDeEquipamento save(TipoDeEquipamento tipoDeEquipamento){
		return this.tipoDeEquipamentoRepository.save(tipoDeEquipamento);
	}
}
