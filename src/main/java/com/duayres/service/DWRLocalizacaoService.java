package com.duayres.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Localizacao;
import com.duayres.repository.ILocalizacaoRepository;

@RemoteProxy
public class DWRLocalizacaoService {
	@Autowired
	ILocalizacaoRepository localizacaoRepository;
	
	@Transactional
	public Localizacao save(Localizacao localizacao){
		return this.localizacaoRepository.saveAndFlush(localizacao);
	}

	public Localizacao findByIdLocalizacao(Long id){
		return this.localizacaoRepository.getOne(id);
	}
	
	public List<Localizacao> listAll(){
		return this.localizacaoRepository.findAll();
	}

}
