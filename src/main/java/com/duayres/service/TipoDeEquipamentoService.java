package com.duayres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duayres.model.TipoDeEquipamento;
import com.duayres.repository.TipoDeEquipamentoRepository;

@Service
public class TipoDeEquipamentoService {
	@Autowired
	private TipoDeEquipamentoRepository tipoDeEquipamentoRepository;
	
	public TipoDeEquipamento inserir(TipoDeEquipamento tipoDeEquipamento){
		return this.tipoDeEquipamentoRepository.save(tipoDeEquipamento);
		//return usuario;
	}
}
